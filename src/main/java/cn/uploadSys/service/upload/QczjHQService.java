package cn.uploadSys.service.upload;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.QczjDao;
import cn.uploadSys.dao.QczjHQDao;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.dto.ArrangeClassJqGridParam;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.entity.upload.QczjHQ;
import cn.uploadSys.util.AjaxUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;


@Service
@Slf4j
public class QczjHQService extends AbstractService<QczjHQ> {


    @Autowired
    private QczjHQDao qczjHQDao;
    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Object> template;
    @Autowired
    private QczjGetAccessTokenService qczjGetAccessTokenService;



    @Override
    protected BaseDao<QczjHQ> getDao() {
        return qczjHQDao;
    }

    @Override
    protected List<QczjHQ> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return qczjHQDao.selectBySql("qczj",sql.toString());
    }


    public PageInfo<QczjHQ> selectByJqGridParam(AllJqGridParam param ) throws ParseException {
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and  mobile like  '%").append(param.getPhone()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())) {
            sql.append(" and create_time > '").append(param.getStartDate()).append(" 00:00:00' and create_time < '").append(param.getEndDate()).append(" 23:59:59'");
        }
        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getAppid())) {
            sql.append(" and appid = '").append(param.getAppid()).append("'");
        }
        sql.append(" ORDER BY create_time  desc ");
        return new PageInfo<>(qczjHQDao.getLeadsList(sql.toString()));
    }


    public List<QczjHQ> selectByJqGridParamNoPage(AllJqGridParam param ) throws ParseException {
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and  mobile like  '%").append(param.getPhone()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())) {
            sql.append(" and create_time > '").append(param.getStartDate()).append(" 00:00:00' and create_time < '").append(param.getEndDate()).append(" 23:59:59'");
        }
        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getAppid())) {
            sql.append(" and uid = '").append(param.getAppid()).append("'");
        }
        sql.append(" ORDER BY create_time  desc ");
        return qczjHQDao.getLeadsList(sql.toString());
    }

    public PageInfo<Map> selectByJqGridParam(ArrangeClassJqGridParam param){
        PageHelper.startPage(param.getPage(), param.getRows());
        return new PageInfo<>(qczjHQDao.getStudentsByClassesId(param.getId()));
    }

    public int insert(QczjHQ qczjHQ){
        return qczjHQDao.insert(qczjHQ);
    }


    public void getUnfinishedInstance(){
        List<QczjHQ> qczjHQS = qczjHQDao.getUnfinishedInstance();
        log.info("查询状态例子总数为:{}",qczjHQS.size());
        qczjHQS.forEach(qczjHQ -> {
            getStatusV3(qczjHQ.getCclid(),qczjHQ.getAppid());
        });
    }
    public void getStatusV3(String cclid,String appId) {
        try {
            //获取token_access
            String clientId = env.getProperty("qczj.getStatusV3.client_id");
            String clientSecret = env.getProperty("qczj.getStatusV3.client_secret");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getStatusV3AccessToken");
            String url = env.getProperty("qczj.getStatusV3.url");

            HttpResponse<JsonNode> json = Unirest.get(String.format(url, accessToken, cclid, appId)).asJson();
            //{"result":{"distributeStatus":1,"appealStatus":0},"returncode":0,"message":""}
            org.json.JSONObject result = json.getBody().getObject().getJSONObject("result");
            if (null != result) {
                int distributeStatus = result.getInt("distributeStatus");
                int appealStatus = result.getInt("appealStatus");
                qczjHQDao.updateByCclid(distributeStatus,cclid,appealStatus);
            }else {
                String message = json.getBody().getObject().getString("message");
                log.info("线索cclid:{},异常:{}",cclid,message);
            }
        } catch (Exception e) {
            log.error("高质线索获取状态异常，异常线索cclid:{},异常内容:{}",cclid,e.getStackTrace());
        }
    }


    public void importFile(MultipartFile file) throws IOException {
        //fileName 文件名
        String fileName = file.getOriginalFilename();
        boolean xlsx = fileName.endsWith(".xlsx");
        if (!xlsx) {
            log.error("请上传以.xlsx结尾的文件");
        }
        //得到文件流
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);


        //获取token_access
        String clientId = env.getProperty("qczj.hq.client_id");
        String clientSecret = env.getProperty("qczj.hq.client_secret");
        String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getHQAccessToken");
        String importUrl = env.getProperty("qczj.hq.url")+accessToken;

        //轮训每条数据，进行对接
        List<QczjHQ> qczjHQs = reader.read(1,2,QczjHQ.class);
        qczjHQs.forEach(qczjHQ -> {
            try {
                Map<String, Object> body = new HashMap<>();

                String mobile = qczjHQ.getMobile();
                Integer cid = qczjHQ.getCid();
                Integer countyid = qczjHQ.getCountyid();
                String brandid = qczjHQ.getBrandid();
                String seriesid = qczjHQ.getSeriesid();
                String specid = qczjHQ.getSpecid();
                String firstregtime = qczjHQ.getFirstregtime();
                String platenum = qczjHQ.getPlatenum();
                BigDecimal mileage = qczjHQ.getMileage();
                String appid = qczjHQ.getAppid();

                if (StringUtils.isNotEmpty(mobile) && null != cid && null != countyid && StringUtils.isNotEmpty(brandid) && StringUtils.isNotEmpty(seriesid) && StringUtils.isNotEmpty(firstregtime) && StringUtils.isNotEmpty(appid)) {
                    //必填字段
                    body.put("access_token",accessToken);
                    body.put("mobile",mobile);
                    body.put("cid",cid);
                    body.put("countyid",countyid);
                    body.put("brandid",brandid);
                    body.put("seriesid",seriesid);
                    body.put("firstregtime",firstregtime);
                    body.put("appid",appid);
                }else{
                    qczjHQ.setStatus(1);
                    qczjHQ.setCreateTime(new Date());
                    insert(qczjHQ);
                    return ;
                }
                //非必填字段
                body.put("specid",specid);
                body.put("platenum",platenum);
                body.put("mileage",mileage);

                HttpResponse<String> upload = Unirest.post(importUrl)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("accept", "application/json")
                        .fields(body)
                        .asString();

                JSONObject object = JSON.parseObject(upload.getBody());
                Integer returncode = object.getInteger("returncode");

                //{"result":{"cclid":95384016},"returncode":0,"message":"添加成功"}

                if (null == returncode) {
                    qczjHQ.setStatus(1);
                }else{
                    String cclid = object.getJSONObject("result").getString("cclid");
                    if (0 == returncode) {
                        qczjHQ.setStatus(0);
                        qczjHQ.setCclid(cclid);
                    }else{
                        qczjHQ.setStatus(1);
                    }
                }
                qczjHQ.setCreateTime(new Date());
                insert(qczjHQ);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        String str = "{\"result\":{\"cclid\":95384016},\"returncode\":0,\"message\":\"添加成功\"}";
        System.out.println(JSONObject.parseObject(str).getJSONObject("result").getString("cclid"));
    }

}
