package cn.uploadSys.service.upload;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import cn.uploadSys.entity.VO.QczjCitiesVO;
import cn.uploadSys.entity.VO.QczjCountriesVO;
import cn.uploadSys.entity.VO.QczjProvincesVO;
import cn.uploadSys.entity.VO.QczjQueryAreaVO;


import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

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
            JSONObject result = json.getBody().getObject().getJSONObject("result");
            JSONArray province = result.getJSONArray("province");
            JSONArray city = result.getJSONArray("city");
            JSONArray county = result.getJSONArray("county");
            System.out.println(province);
        } catch (UnirestException e) {
            log.info("获取城市区域异常");
        }
        return null;
    }

//    public void exportArea(HttpServletResponse response) throws Exception {
//        //获取省市区的列表
//        JSONArray provinces = null;
//        JSONArray cities = null;
//        JSONArray counties = null;
//        try {
//            String clientId = env.getProperty("qczj.queryArea.client_id");
//            String clientSecret = env.getProperty("qczj.queryArea.client_secret");
//            String url = env.getProperty("qczj.queryArea.url");
//            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getQueryAreaAccessToken");
//
//            HttpResponse<JsonNode> json = Unirest.get(String.format(url, accessToken)).asJson();
//            JSONObject result = json.getBody().getObject().getJSONObject("result");
//            System.out.println(result.toString());
//            provinces = result.getJSONArray("province");
//            cities = result.getJSONArray("city");
//            counties = result.getJSONArray("county");
//
//        } catch (UnirestException e) {
//            log.info("获取城市区域异常");
//        }
//
//        String column1Name1 = "省份代码";
//        String column1Name2 = "省份名称";
//        String column1Name3 = "城市代码";
//        String column1Name4 = "城市名称";
//        String column1Name5 = "区县代码";
//        String column1Name6 = "区县名称";
//
//        List<String> headList1 = new ArrayList<>();
//        List<String> headList2 = new ArrayList<>();
//        headList1.add(column1Name1);
//        headList1.add(column1Name2);
//        headList2.add(column1Name3);
//        headList2.add(column1Name4);
////        headList.add(column1Name5);
////        headList.add(column1Name6);
//
//        //在内存操作，写到浏览器
//        ExcelWriter writer= ExcelUtil.getWriter(true);
//
//        // 设置表头的宽度
//        writer.setColumnWidth(0, 20);
//        writer.addHeaderAlias("pid",column1Name1);
//        writer.setColumnWidth(1, 15);
//        writer.addHeaderAlias("pname",column1Name2);
//        writer.setColumnWidth(0, 20);
//        writer.addHeaderAlias("cid",column1Name3);
//        writer.setColumnWidth(1, 15);
//        writer.addHeaderAlias("cname",column1Name4);
////        writer.setColumnWidth(0, 20);
////        writer.addHeaderAlias("countyid",column1Name5);
////        writer.setColumnWidth(1, 15);
////        writer.addHeaderAlias("countyname",column1Name6);
//
//        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
//        writer.setOnlyAlias(true);
//
//        // 表格内容【相比上一节新内容】
//        List<QczjQueryAreaVO> excelList1 = new ArrayList<>();
//        List<QczjQueryAreaVO> excelList2 = new ArrayList<>();
//
//
//        for (Object province : provinces) {
//            QczjProvincesVO vo = new QczjProvincesVO();
//            com.alibaba.fastjson.JSONObject object = JSON.parseObject(province.toString());
//            vo.setPid(object.getInteger("pid"));
//            vo.setPname(object.getString("pname"));
//            excelList1.add(vo);
//        }
//        writer.writeHeadRow(headList1).write(excelList1);
//        for (Object city : cities) {
//            QczjCitiesVO vo = new QczjCitiesVO();
//            com.alibaba.fastjson.JSONObject object = JSON.parseObject(city.toString());
//            vo.setCid(object.getInteger("cid"));
//            vo.setCname(object.getString("cname"));
//            excelList2.add(vo);
//        }
//        writer.writeHeadRow(headList2).write(excelList2);
////        for (Object county : counties) {
////            QczjCountriesVO vo = new QczjCountriesVO();
////            com.alibaba.fastjson.JSONObject object = JSON.parseObject(county.toString());
////            vo.setCountyid(object.getInteger("countyid"));
////            vo.setCountyname(object.getString("countyname"));
////            excelList.add(vo);
////        }
//
//
//
////        writer.writeHeadRow(headList).write(excelList);
//        //设置content—type
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");
//
//        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
//        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("省市区模版","UTF-8")+".xlsx");
//        ServletOutputStream outputStream= response.getOutputStream();
//
//        //将Writer刷新到OutPut
//        writer.flush(outputStream,true);
//        outputStream.close();
//        writer.close();
//    }

    public void exportArea(HttpServletResponse response) throws Exception {
        //获取省市区的列表
        JSONArray provinces = null;
        JSONArray cities = null;
        JSONArray counties = null;
        try {
            String clientId = env.getProperty("qczj.queryArea.client_id");
            String clientSecret = env.getProperty("qczj.queryArea.client_secret");
            String url = env.getProperty("qczj.queryArea.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getQueryAreaAccessToken");

            HttpResponse<JsonNode> json = Unirest.get(String.format(url, accessToken)).asJson();
            JSONObject result = json.getBody().getObject().getJSONObject("result");
            System.out.println(result.toString());
            provinces = result.getJSONArray("province");
            cities = result.getJSONArray("city");
            counties = result.getJSONArray("county");

        } catch (UnirestException e) {
            log.info("获取城市区域异常");
        }

        String column1Name1 = "省份代码";
        String column1Name2 = "省份名称";
        String column1Name3 = "城市代码";
        String column1Name4 = "城市名称";
        String column1Name5 = "区县代码";
        String column1Name6 = "区县名称";

        List<String> headList = new ArrayList<>();
        headList.add(column1Name1);
        headList.add(column1Name2);
        headList.add(column1Name3);
        headList.add(column1Name4);
        headList.add(column1Name5);
        headList.add(column1Name6);

        //在内存操作，写到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);

        // 设置表头的宽度
        writer.setColumnWidth(0, 20);
        writer.addHeaderAlias("pid",column1Name1);
        writer.setColumnWidth(1, 15);
        writer.addHeaderAlias("pname",column1Name2);
        writer.setColumnWidth(0, 20);
        writer.addHeaderAlias("cid",column1Name3);
        writer.setColumnWidth(1, 15);
        writer.addHeaderAlias("cname",column1Name4);
        writer.setColumnWidth(0, 20);
        writer.addHeaderAlias("countyid",column1Name5);
        writer.setColumnWidth(1, 15);
        writer.addHeaderAlias("countyname",column1Name6);

        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);

        writer.writeHeadRow(headList);
        for (int i=0;i<provinces.length();i++) {
            com.alibaba.fastjson.JSONObject object = JSON.parseObject(provinces.get(i).toString());
            writer.writeCellValue(0,i+1,object.getInteger("pid"));
            writer.writeCellValue(1,i+1,object.getString("pname"));
        }
        for (int i=0;i<cities.length();i++) {
            com.alibaba.fastjson.JSONObject object = JSON.parseObject(cities.get(i).toString());
            writer.writeCellValue(2,i+1,object.getInteger("cid"));
            writer.writeCellValue(3,i+1,object.getString("cname"));
        }
        for (int i=0;i<counties.length();i++) {
            com.alibaba.fastjson.JSONObject object = JSON.parseObject(counties.get(i).toString());
            writer.writeCellValue(4,i+1,object.getInteger("countyid"));
            writer.writeCellValue(5,i+1,object.getString("countyname"));
        }


        //设置content—type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("省市区模版","UTF-8")+".xlsx");
        ServletOutputStream outputStream= response.getOutputStream();

        //将Writer刷新到OutPut
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }
}
