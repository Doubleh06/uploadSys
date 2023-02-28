package cn.uploadSys.service.upload;


import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class QczjGetAccessTokenService {

    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Object> template;


    /**
     * @Author dhao
     * @Description 获取accessToken
     * @Date 2023/2/28
     * @return java.lang.String
     **/
    public String getAccessToken(String client_id,String client_secret,String redisName) {
        String accessToken = "";
        String url = env.getProperty("qczj.accessToken.url");
        Object obj = template.opsForValue().get(redisName);
        if (null !=  obj&& StringUtils.isNotBlank(obj.toString())) {
            accessToken = obj.toString();
        }else{
            Map<String,Object> map = applyccessToken(url,client_id,client_secret);
            accessToken = map.get("accessToken").toString();
            long expiresIn = Long.parseLong(map.get("expiresIn").toString());//失效时间
            try {
                template.opsForValue().set(redisName, accessToken,expiresIn, TimeUnit.SECONDS);
                log.info("存入redis成功，key：{}，value：{}", redisName, accessToken);
            } catch (Exception e) {
                log.error("存入redis失败，key：{}，value：{}", redisName, accessToken);
            }
        }
        return accessToken;
    }
    /**
     * @Author dhao
     * @Description 去之家获取token
     * @Date 2023/2/28 
     * @Param  * @param url
 * @param clientId
 * @param clientSecret
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> applyccessToken(String url,String clientId,String clientSecret){
        //获取token_access
        Map<String,Object> resultMap = new HashMap<>();
        try {
            HashMap<String,Object> accessHeader = new HashMap();
            accessHeader.put("Content-Type","application/json;charset=utf-8");

            Map<String, Object> body = new HashMap<>();
            body.put("client_id",clientId);
            body.put("client_secret",clientSecret);
            body.put("response_type","token");

            HttpResponse<String> upload = Unirest.post(url)
                    //                .header("Content-Type", "multipart/form-data")
                    .header("Accept", "application/json")
                    .fields(body)
                    .asString();

            String accessResult = upload.getBody().toString();
            JSONObject json = JSONObject.parseObject(accessResult);
            String accessToken = json.getJSONObject("data").getString("access_token");
            long expiresIn = json.getJSONObject("data").getLong("expires_in");//失效时间
            resultMap.put("accessToken",accessToken);
            resultMap.put("expiresIn",expiresIn);
        } catch (UnirestException e) {
            log.info("之家获取token异常");
        }

        return resultMap;
    }
}
