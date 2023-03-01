package cn.uploadSys.service.renrenche;


import cn.hutool.core.lang.UUID;
import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.BusinessException;
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
public class RrcCommonService  {


    @Autowired
    private RrcCommonDao rrcCommonDao;
    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Object> template;


    public PageInfo<Rrc> selectByJqGridParam(AllJqGridParam param ) throws ParseException {
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
        sql.append(" ORDER BY create_time  desc ");
        return new PageInfo<>(rrcCommonDao.getLeadsList(sql.toString()));
    }


    public List<Rrc> selectByJqGridParamNoPage(AllJqGridParam param ) throws ParseException {
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
        return rrcCommonDao.getLeadsList(sql.toString());
    }


    public int insert(Rrc Rrc){
        return rrcCommonDao.insert(Rrc);
    }

    public void interfaceC1(Rrc rrc){
        Object redisUrl = template.opsForValue().get("rrc:url");
        Object redisToken = template.opsForValue().get("rrc:token");
        if (null !=redisUrl && null!= redisToken){
            String url = redisUrl.toString();
            String token = redisToken.toString();
            //C1通用接口
            try {
                Map<String, Object> body = new HashMap<>();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",rrc.getName());
                jsonObject.put("mobile",Long.parseLong(rrc.getMobile()));
                jsonObject.put("city",rrc.getCity());
                jsonObject.put("brand",rrc.getBrand());
                jsonObject.put("series",rrc.getSeries());
                jsonObject.put("model",rrc.getMobile());
                jsonObject.put("kilometer",Float.parseFloat(rrc.getKilometer()));
                jsonObject.put("licensed_date_year",rrc.getLicensedDateYear());
                jsonObject.put("is_operation",0);
                jsonObject.put("seat_number",0);
                jsonObject.put("is_accidented",0);

                Date date = new Date();
                long timestamp = date.getTime()/1000;
                String id = UUID.randomUUID().toString();
                rrc.setId(id);
                rrc.setCreateTime(date);

                body.put("token",token);
                body.put("time",timestamp);
                String sign = DigestUtils.md5Hex(jsonObject.toJSONString()+token+timestamp);
                body.put("sign", sign);
                body.put("data",jsonObject);

                System.out.println("body报文:"+body.toString());

                HttpResponse<String> upload = Unirest.post(url)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("accept", "application/json")
                        .fields(body)
                        .asString();

                String result = upload.getBody().toString();
//                result={"data":{"is_repeat":false,"renrenche_infoid":280006511579795456},"msg":"数据插入成功","status":200}
                log.info("电话号码:{},result={}",rrc.getMobile(),result);

                JSONObject jsonResult = JSONObject.parseObject(result);
                Integer status = jsonResult.getInteger("status");
                if (null != status && status == 200) {
                    String rrcId = jsonResult.getJSONObject("data").getString("renrenche_infoid");
                    Boolean isRepeat = jsonResult.getJSONObject("data").getBoolean("is_repeat");
                    rrc.setStatus(0);
                    rrc.setRenrencheInfoId(rrcId);
                    if (isRepeat) {
                        rrc.setIsRepeat(0);
                    }else {
                        rrc.setIsRepeat(1);
                    }
                }else {
                    rrc.setStatus(1);
                }
                insert(rrc);

            } catch (Exception e) {
                log.error("人人车C1接口调用异常,{}",e.getStackTrace());
            }
        }else{
            throw new BusinessException("人人车url和token获取异常");
        }

    }


    public void setUrlAndToken(String url,String token){
        template.opsForValue().set("rrc:url",url);
        template.opsForValue().set("rrc:token",token);
    }
    public Map<String,String> getUrlAndToken(){
        String url = template.opsForValue().get("rrc:url").toString();
        String token = template.opsForValue().get("rrc:token").toString();
        Map<String,String> map = new HashMap<>();
        map.put("url",url);
        map.put("token",token);
        return map;
    }

}
