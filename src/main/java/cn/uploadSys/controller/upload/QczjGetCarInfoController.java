package cn.uploadSys.controller.upload;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.service.upload.QczjGetCarInfoService;
import cn.uploadSys.service.upload.QczjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj")
public class QczjGetCarInfoController extends BaseController {

   @Autowired
   private QczjGetCarInfoService qczjGetCarInfoService;




    @RequestMapping(value = "/carBrands")
    @ResponseBody
    public Object carBrands(String appId,String queryKey){
       return qczjGetCarInfoService.getCarBrands(appId,queryKey);
    }


    @RequestMapping(value = "/carSeries")
    @ResponseBody
    public Object carSeries(String appId,String queryKey,Integer brandId){
        return qczjGetCarInfoService.getCarSeries(appId,queryKey,brandId);
    }


    @RequestMapping(value = "/carProducts")
    @ResponseBody
    public Object carProducts(String appId,String queryKey,String seriesId){
        return qczjGetCarInfoService.getCarProducts(appId,queryKey,seriesId);
    }

}

