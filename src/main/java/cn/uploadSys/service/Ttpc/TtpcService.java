package cn.uploadSys.service.Ttpc;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.QczjDao;
import cn.uploadSys.dao.TtpcDao;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.dto.ArrangeClassJqGridParam;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.dto.TtpcJqGridParam;
import cn.uploadSys.entity.ttpc.SignUp;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.entity.upload.QczjHQ;
import cn.uploadSys.util.AjaxUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.MD5;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TtpcService extends AbstractService<SignUp> {

    @Value("${ttpc.ttpSignUp.url}")
    private String ttpSignUpurl;
    @Value("${ttpc.appkey}")
    private String appkey;
    @Value("${ttpc.signkey}")
    private String signkey;
    @Value("${ttpc.source}")
    private String source;
    @Value("${ttpc.queryTtpSignUp.url}")
    private String queryTtpSignUp;


    @Autowired
    private TtpcDao ttpcDao;


    @Override
    protected BaseDao<SignUp> getDao() {
        return ttpcDao;
    }

    @Override
    protected List<SignUp> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return ttpcDao.selectBySql("qczj",sql.toString());
    }


    public PageInfo<SignUp> selectByJqGridParam(TtpcJqGridParam param ) throws ParseException {
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getMobile())) {
            sql.append(" and  phone like  '%").append(param.getMobile()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())) {
            sql.append(" and create_time > '").append(param.getStartDate()).append(" 00:00:00' and create_time < '").append(param.getEndDate()).append(" 23:59:59'");
        }
        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }

        sql.append(" ORDER BY create_time  desc ");
        return new PageInfo<>(ttpcDao.getLeadsList(sql.toString()));
    }




    public int insert(SignUp signUp){
        return ttpcDao.insert(signUp);
    }


    public void getUnfinishedInstance(){
        List<SignUp> signUps = ttpcDao.getUnfinishedInstance();
        log.info("查询状态例子总数为:{}",signUps.size());
        signUps.forEach(signUp -> {
            getStatus(signUp);
        });
    }

    public void getStatus(SignUp signUp) {
        String finalUrl = String.format(queryTtpSignUp,signUp.getResponseId(),source);
        HttpResponse<JsonNode> json = null;
        try {
            json = Unirest.get(finalUrl).asJson();
        } catch (UnirestException e) {
            log.error("天天拍车状态查询通讯异常，异常id:{}",signUp.getResponseId());
        }
        boolean error = json.getBody().getObject().getBoolean("error");
        if (error) {
            String message = json.getBody().getObject().getString("message");
            signUp.setMessage(message);
        }else{
            String invite = json.getBody().getObject().getJSONObject("result").getString("invite");
            String detection = json.getBody().getObject().getJSONObject("result").getString("detection");
            String auction = json.getBody().getObject().getJSONObject("result").getString("auction");
            String deal = json.getBody().getObject().getJSONObject("result").getString("deal");
            if (StringUtils.isNotBlank(invite)) {
                if("成功".equals(invite)){
                    signUp.setInvite(0);
                }else{
                    signUp.setInvite(1);
                }
            }

            if (StringUtils.isNotBlank(detection)) {
                if("成功".equals(detection)){
                    signUp.setDetection(0);
                }else{
                    signUp.setDetection(1);
                }
            }

            if (StringUtils.isNotBlank(auction)) {
                if("成功".equals(auction)){
                    signUp.setAuction(0);
                }else{
                    signUp.setAuction(1);
                }
            }

            if (StringUtils.isNotBlank(deal)) {
                if("成功".equals(deal)){
                    signUp.setDeal(0);
                }else{
                    signUp.setDeal(1);
                }
            }
        }
        signUp.setModifyTime(new Date());
        ttpcDao.updateByPrimaryKeySelective(signUp);
    }



    public void importFile(MultipartFile file) throws Exception{
        //fileName 文件名
        String fileName = file.getOriginalFilename();
        boolean xlsx = fileName.endsWith(".xlsx");
        if (!xlsx) {
            log.error("请上传以.xlsx结尾的文件");
        }
        //得到文件流
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);


        //轮训每条数据，进行对接
        List<SignUp> records = reader.read(1,2,SignUp.class);
        ajaxToThirdPart(records);

    }

    public String packageUrl(SignUp record ) throws UnsupportedEncodingException {
        String mobile = URLEncoder.encode(record.getMobile(),"UTF-8");
        String sign = DigestUtils.md5Hex(mobile+signkey);
        String name = URLEncoder.encode(record.getName(),"UTF-8");
        String city = URLEncoder.encode(record.getCity(),"UTF-8");
        String brand = URLEncoder.encode(record.getBrand(),"UTF-8");

        return String.format(ttpSignUpurl,name,mobile,city,brand,appkey,sign,source);
    }

    public void ajaxToThirdPart(List<SignUp> records) throws Exception {
        for (SignUp record : records) {
            String finalUrl = packageUrl(record);
            log.info("finalUrl:{}",finalUrl);

            HttpResponse<JsonNode> json = Unirest.get(finalUrl).asJson();
            boolean error = json.getBody().getObject().getBoolean("error");
            if (error) {
                String message = json.getBody().getObject().getString("message");
                record.setStatus(1);
                record.setMessage(message);
            }else{
                int responseId = json.getBody().getObject().getJSONObject("result").getInt("id");
                record.setStatus(0);
                record.setResponseId(responseId);
            }
            record.setCreateTime(new Date());
            insert(record);
        }
    }


    

}
