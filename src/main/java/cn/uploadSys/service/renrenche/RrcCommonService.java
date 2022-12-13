package cn.uploadSys.service.renrenche;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.RrcCommonDao;
import cn.uploadSys.dto.AllJqGridParam;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.entity.renrenche.Rrc;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RrcCommonService extends AbstractService<Rrc> {


    @Autowired
    private RrcCommonDao rrcCommonDao;
    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Object> template;



    @Override
    protected BaseDao<Rrc> getDao() {
        return rrcCommonDao;
    }

    @Override
    protected List<Rrc> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return rrcCommonDao.selectBySql("qczj",sql.toString());
    }


    public PageInfo<Rrc> selectByJqGridParam(AllJqGridParam param ) throws ParseException {
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
        sql.append(" ORDER BY create_time  desc ");
        return new PageInfo<>(rrcCommonDao.getLeadsList(sql.toString()));
    }


    public List<Rrc> selectByJqGridParamNoPage(AllJqGridParam param ) throws ParseException {
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
        if (StringUtils.isNotEmpty(param.getAppid())) {
            sql.append(" and uid = '").append(param.getAppid()).append("'");
        }
        sql.append(" ORDER BY create_time  desc ");
        return rrcCommonDao.getLeadsList(sql.toString());
    }


//    public int insert(Rrc Rrc){
//        return rrcCommonDao.insert(Rrc);
//    }

    public void interfaceC1(Rrc rrc){
            try {
                Map<String, Object> body = new HashMap<>();

//                String name = rrc.getName();
//                int mobile = rrc.getMobile();
//                String city = rrc.getCity();
//                String brand = rrc.getBrand();
//                String series = rrc.getSeries();
//                String model = rrc.getModel();
//                float kilometer = rrc.getKilometer();
//                int licensed_date_year = rrc.getLicensedDateYear();
//                char is_operation = rrc.getIsOperation();
//                int seat_number = rrc.getSeatNumber();
//                char is_accidented = rrc.getIsAccidented();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",rrc.getName());
                jsonObject.put("mobile",rrc.getMobile());
                jsonObject.put("city",rrc.getCity());
                jsonObject.put("brand",rrc.getBrand());
                jsonObject.put("series",rrc.getSeries());
                jsonObject.put("model",rrc.getMobile());
                jsonObject.put("kilometer",rrc.getKilometer());
                jsonObject.put("licensed_date_year",rrc.getLicensedDateYear());
                jsonObject.put("is_operation",rrc.getIsOperation());
                jsonObject.put("seat_number",rrc.getSeatNumber());
                jsonObject.put("is_accidented",rrc.getIsAccidented());


                Date date = new Date();
                long timestamp = date.getTime()/1000;
                rrc.setStatus(0);
                rrc.setCreateTime(date);
                int id = insert(rrc);


                String url = env.getProperty("rrc.c1.host");
                String token = env.getProperty("rrc.c1.token");

                body.put("token",token);
                body.put("time",timestamp);
                String sign = DigestUtils.md5Hex(jsonObject.toJSONString()+token+timestamp);
                body.put("sign", sign);
                body.put("data",jsonObject);

                HttpResponse<String> upload = Unirest.post(url)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("accept", "application/json")
                        .fields(body)
                        .asString();

                String result = upload.getBody().toString();
                System.out.println("result="+result);

//                if (StringUtils.isNotEmpty(result) && result.contains("error_description")) {
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    String errorDescription = jsonObject.getString("error_description");
//                    qczj.setStatus(1);
//                    qczj.setMessage(errorDescription);
//                }else{
//                    JSONObject jsonObject = JSONObject.parseObject(result);
//                    String returnCode = jsonObject.getString("returncode");
//                    String message = jsonObject.getString("message");
//                    if (StringUtils.isNotEmpty(returnCode) && returnCode.equals("0")) {
//                        String cclid = jsonObject.getString("cclid");
//                        qczj.setStatus(0);
//                        qczj.setCclid(cclid);
//                    }else{
//                        qczj.setStatus(1);
//                        qczj.setMessage(message);
//                    }
//                }
//                qczj.setCreateTime(new Date());
//                qczjCommonService.insert(qczj);
            } catch (Exception e) {
                log.error("人人车C1接口调用异常,{}",e.getMessage());
            }
    }


}
