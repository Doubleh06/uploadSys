package cn.uploadSys.controller.upload;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.entity.VO.QczjCarInfoVO;
import cn.uploadSys.service.upload.QczjGetCarInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj")
public class QczjGetCarInfoController extends BaseController {

   @Autowired
   private QczjGetCarInfoService qczjGetCarInfoService;




    @RequestMapping(value = "/carBrands")
    @ResponseBody
    public List<QczjCarInfoVO> carBrands(String appId, String queryKey){
       return qczjGetCarInfoService.getCarBrands(appId,queryKey);
    }


    @RequestMapping(value = "/carSeries")
    @ResponseBody
    public List<QczjCarInfoVO> carSeries(String appId, String queryKey, Integer brandId){
        return qczjGetCarInfoService.getCarSeries(appId,queryKey,brandId);
    }


    @RequestMapping(value = "/carProducts")
    @ResponseBody
    public List<QczjCarInfoVO> carProducts(String appId,String queryKey,String seriesId){
        return qczjGetCarInfoService.getCarProducts(appId,queryKey,seriesId);
    }


    @RequestMapping(value = "/exportCarInfo")
    @ResponseBody
    public void exportCarInfo(HttpServletResponse response,String appId) throws Exception {
        qczjGetCarInfoService.exportCarInfo(response,appId);
    }
}
