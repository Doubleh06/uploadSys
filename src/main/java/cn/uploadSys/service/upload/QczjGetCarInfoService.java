package cn.uploadSys.service.upload;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class QczjGetCarInfoService{

    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Object> template;
    @Autowired
    private QczjGetAccessTokenService qczjGetAccessTokenService;



    public Object getCarBrands(String appId, String queryKey) {
        try {
            String clientId = env.getProperty("qczj.getCarBrand.client_id");
            String clientSecret = env.getProperty("qczj.getCarBrand.client_secret");
            String url = env.getProperty("qczj.getCarBrand.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getCarBrandsAccessToken");

            HttpResponse<JsonNode> json = Unirest.get(String.format(url, accessToken, appId, queryKey)).asJson();
            System.out.println(json.getBody().toString());
        } catch (UnirestException e) {
            log.info("获取车品牌异常");
        }
        return null;
    }

    public Object getCarSeries(String appId, String queryKey,Integer brandId) {
        try {
            String clientId = env.getProperty("qczj.getCarSeries.client_id");
            String clientSecret = env.getProperty("qczj.getCarSeries.client_secret");
            String url = env.getProperty("qczj.getCarSeries.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getCarSeriesAccessToken");

            String getUrl = String.format(url, accessToken, appId, queryKey, brandId);
            HttpResponse<JsonNode> json = Unirest.get(getUrl).asJson();
            System.out.println(json.getBody().toString());
        } catch (UnirestException e) {
            log.info("获取车系牌异常");
        }
        return null;
    }

    public Object getCarProducts(String appId, String queryKey,String seriesId) {
        try {
            String clientId = env.getProperty("qczj.getCarProduct.client_id");
            String clientSecret = env.getProperty("qczj.getCarProduct.client_secret");
            String url = env.getProperty("qczj.getCarProduct.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getCarProductsAccessToken");

            String getUrl = String.format(url, accessToken, appId, queryKey, seriesId);
            HttpResponse<JsonNode> json = Unirest.get(getUrl).asJson();
            System.out.println(json.getBody().toString());
        } catch (UnirestException e) {
            log.info("获取车型牌异常");
        }
        return null;
    }

}
