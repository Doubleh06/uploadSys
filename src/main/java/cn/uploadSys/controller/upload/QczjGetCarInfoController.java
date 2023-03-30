package cn.uploadSys.controller.upload;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.BusinessException;
import cn.uploadSys.entity.VO.QczjCarInfoVO;
import cn.uploadSys.service.upload.QczjGetCarInfoService;
import cn.uploadSys.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj")
public class QczjGetCarInfoController extends BaseController {

    private static final String stQueryKey = "mxoVEqZ6Two=";
    private static final String ndQueryKey = "6zglgujmr+8=";

   @Autowired
   private QczjGetCarInfoService qczjGetCarInfoService;




    @RequestMapping(value = "/carBrands")
    @ResponseBody
    public List<QczjCarInfoVO> carBrands(String appId) throws UnsupportedEncodingException {
        String queryKey = "";
        switch (appId){
            case "1312" : queryKey = URLEncoder.encode(stQueryKey,"utf-8");break;
            case "1313" : queryKey = URLEncoder.encode(ndQueryKey,"utf-8");break;
            default: throw new BusinessException("appId 或 queryKey异常");
        }
       return qczjGetCarInfoService.getCarBrands(appId, queryKey);
    }


    @RequestMapping(value = "/carSeries")
    @ResponseBody
    public List<QczjCarInfoVO> carSeries(String appId, String queryKey, Integer brandId) throws UnsupportedEncodingException {
        switch (appId){
            case "1312" : queryKey = URLEncoder.encode(stQueryKey,"utf-8");break;
            case "1313" : queryKey = URLEncoder.encode(ndQueryKey,"utf-8");break;
            default: throw new BusinessException("appId 或 queryKey异常");
        }
        return qczjGetCarInfoService.getCarSeries(appId, queryKey,brandId);
    }


    @RequestMapping(value = "/carProducts")
    @ResponseBody
    public List<QczjCarInfoVO> carProducts(String appId,String seriesId) throws UnsupportedEncodingException {
        String queryKey = "";
        switch (appId){
            case "1312" : queryKey = URLEncoder.encode(stQueryKey,"utf-8");break;
            case "1313" : queryKey = URLEncoder.encode(ndQueryKey,"utf-8");break;
            default: throw new BusinessException("appId 或 queryKey异常");
        }
        return qczjGetCarInfoService.getCarProducts(appId,queryKey,seriesId);
    }


    @RequestMapping(value = "/exportCarInfo")
    @ResponseBody
    public void exportCarInfo(HttpServletResponse response,String appId) throws Exception {
        qczjGetCarInfoService.exportCarInfo(response,appId);
    }
}

