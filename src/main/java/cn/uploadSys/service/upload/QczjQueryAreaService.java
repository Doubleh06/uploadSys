package cn.uploadSys.service.upload;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.QczjDao;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.dto.ArrangeClassJqGridParam;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.util.AjaxUtil;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class QczjQueryAreaService{

    @Autowired
    private Environment env;
    @Autowired
    private QczjGetAccessTokenService qczjGetAccessTokenService;



    public Object queryArea(){
        try {
            String clientId = env.getProperty("qczj.queryArea.client_id");
            String clientSecret = env.getProperty("qczj.queryArea.client_secret");
            String url = env.getProperty("qczj.queryArea.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getQueryAreaAccessToken");

            HttpResponse<JsonNode> json = Unirest.get(String.format(url, accessToken)).asJson();
            System.out.println(json.getBody().toString());
        } catch (UnirestException e) {
            log.info("获取城市区域异常");
        }
        return null;
    }


}
