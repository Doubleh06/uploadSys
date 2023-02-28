package cn.uploadSys.service.upload;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

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

    public void exportArea(HttpServletResponse response) throws Exception {
        //获取省市区的列表
        JSONArray provinces = null;
        JSONArray cities;
        JSONArray counties;
        try {
            String clientId = env.getProperty("qczj.queryArea.client_id");
            String clientSecret = env.getProperty("qczj.queryArea.client_secret");
            String url = env.getProperty("qczj.queryArea.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getQueryAreaAccessToken");

            HttpResponse<JsonNode> json = Unirest.get(String.format(url, accessToken)).asJson();
            JSONObject result = json.getBody().getObject().getJSONObject("result");
            provinces = result.getJSONArray("province");
            cities = result.getJSONArray("city");
            counties = result.getJSONArray("county");

        } catch (UnirestException e) {
            log.info("获取城市区域异常");
        }

        String column1Name1 = "省份代码";
        String column1Name2 = "省份名称";

        List<String> headList = new ArrayList<>();
        headList.add(column1Name1);
        headList.add(column1Name2);

        //在内存操作，写到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);

        // 设置表头的宽度
        writer.setColumnWidth(0, 20);
        writer.addHeaderAlias("timestamp",column1Name1);
        writer.setColumnWidth(1, 15);
        writer.addHeaderAlias("deviceName",column1Name2);

        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);

        // 表格内容【相比上一节新内容】
        List<QczjQueryAreaVO> excelList = new ArrayList<>();


        for (Object province : provinces) {
            QczjQueryAreaVO vo = new QczjQueryAreaVO();
            com.alibaba.fastjson.JSONObject object = JSON.parseObject(province.toString());
            vo.setPid(object.getInteger("pid"));
            vo.setPname(object.getString("pname"));
            excelList.add(vo);
        }



        writer.writeHeadRow(headList).write(excelList);
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
