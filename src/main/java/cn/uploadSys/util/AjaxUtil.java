package cn.uploadSys.util;



import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AjaxUtil {

    public static String  doPost(String scheme ,String host,String path,String body,HashMap<String,Object> headerMap) {
//        //测试公司的API接口，将json当做一个字符串传入httppost的请求体
        String result = null;
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        try {

            HttpPost post = new HttpPost(builder.setScheme(scheme).setHost(host).setPath(path).build());
            //设置请求头
            for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue().toString());
            }
            //设置请求体
            post.setEntity(new StringEntity(body,"utf-8"));

            //获取返回信息
            org.apache.http.HttpResponse response = client.execute(post);
            result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("接口请求失败" + e.getStackTrace());
        }
        return null;
    }


    public static String  doGet(String scheme , String host, String path, List<NameValuePair> parameters) {
//        //测试公司的API接口，将json当做一个字符串传入httppost的请求体
        String result = null;
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        try {

            HttpGet get = new HttpGet(builder.setScheme(scheme).setHost(host).setPath(path).setParameters(parameters).build());
//                    .setParameter("name", URLEncoder.encode(ttpcLeads.getName(),"utf-8"))

            //获取返回信息
            org.apache.http.HttpResponse response = client.execute(get);
            result = EntityUtils.toString(response.getEntity(),"utf-8");// 返回json格式：  response.getEntity();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("接口请求失败" + e.getStackTrace());
        }finally {

        }
        return null;
    }



    public static void main(String[] args) {
        String ret = doGet("http", "172.17.40.25:8080", "/rest_api/api/hr/getAllDepartment/CZ", null);
        System.out.println(ret);

    }
}
