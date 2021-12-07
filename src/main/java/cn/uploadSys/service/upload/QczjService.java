package cn.uploadSys.service.upload;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.QczjDao;
import cn.uploadSys.dto.*;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.util.AjaxUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class QczjService extends AbstractService<Qczj> {


    @Autowired
    private QczjDao qczjDao;
    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Object> template;



    @Override
    protected BaseDao<Qczj> getDao() {
        return qczjDao;
    }

    @Override
    protected List<Qczj> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return qczjDao.selectBySql("qczj",sql.toString());
    }


    public PageInfo<Qczj> selectByJqGridParam(AllJqGridParam param ) throws ParseException {
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and  phone like  '%").append(param.getPhone()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())) {
            sql.append(" and create_time > '").append(param.getStartDate()).append(" 00:00:00' and create_time < '").append(param.getEndDate()).append(" 23:59:59'");
        }
        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }
//        sql.append(" ORDER BY week , start_time ASC ");
        return new PageInfo<>(qczjDao.getStudentsList(sql.toString()));
    }


    public List<Qczj> selectByJqGridParamNoPage(AllJqGridParam param ) throws ParseException {
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and  phone like  '%").append(param.getPhone()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())) {
            sql.append(" and create_time > '").append(param.getStartDate()).append(" 00:00:00' and create_time < '").append(param.getEndDate()).append(" 23:59:59'");
        }
        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }
        return qczjDao.getStudentsList(sql.toString());
    }

    public PageInfo<Map> selectByJqGridParam(ArrangeClassJqGridParam param){
        PageHelper.startPage(param.getPage(), param.getRows());
        return new PageInfo<>(qczjDao.getStudentsByClassesId(param.getId()));
    }

    public int insert(Qczj qczj){
        return qczjDao.insert(qczj);
    }


    public void getUnfinishedInstance(){
        List<Qczj> qczjs = qczjDao.getUnfinishedInstance();
        qczjs.forEach(qczj -> {
            getStatus(qczj.getCclid());
        });
    }

    public void getStatus(String cclid){
        //获取token_access
        String accessToken = "";
        Object obj = template.opsForValue().get("getStatusAccessToken");
        if (null !=  obj&& StringUtils.isNotBlank(obj.toString())) {
            accessToken = obj.toString();
        }else{
            String access_host = env.getProperty("qczj.access_host");
            String access_path = env.getProperty("qczj.access_path");

            HashMap<String,Object> accessHeader = new HashMap();
            accessHeader.put("Content-Type","application/json;charset=utf-8");

            JSONObject accessBody = new JSONObject();
            accessBody.put("client_id",env.getProperty("qczj.getStatus.client_id"));
            accessBody.put("client_secret",env.getProperty("qczj.getStatus.client_secret"));
            accessBody.put("response_type","token");

            String accessResult = AjaxUtil.doPost("https",access_host,access_path,accessBody.toString(),accessHeader);
            JSONObject json = JSONObject.parseObject(accessResult);
            accessToken = json.getJSONObject("data").getString("access_token");
            long expiresIn = json.getJSONObject("data").getLong("expires_in");//失效时间
            try {
                template.opsForValue().set("getStatusAccessToken", accessToken,expiresIn, TimeUnit.SECONDS);
                log.info("存入redis成功，key：{}，value：{}", "accessToken", accessToken);
            } catch (Exception e) {
                log.error("存入redis失败，key：{}，value：{}", "accessToken", accessToken);
                e.printStackTrace();
            }
        }



        String statusHost = env.getProperty("qczj.getStatus.access_host");
        String statusPath = env.getProperty("qczj.getStatus.access_path");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("appid", "936"));
        params.add(new BasicNameValuePair("cclid", cclid));
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("querykey", env.getProperty("qczj.getStatus.queryKey")));

        String result = AjaxUtil.doGet("https",statusHost,statusPath,params);
        System.out.println(result.toString());
        if (StringUtils.isNotEmpty(result) && result.contains("returncode")) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String returnCode = jsonObject.getString("returncode");
            String status = jsonObject.getString("status");
            if (StringUtils.isNotEmpty(returnCode) && returnCode.equals("0")&&StringUtils.isNotEmpty(status)) {
                qczjDao.updateByCclid(Integer.parseInt(status),cclid);
            }

        }
    }



    

}
