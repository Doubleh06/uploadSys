package cn.uploadSys.controller.upload;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.BusinessException;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dao.ClassesStudentsDao;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.dto.ClassesJqGridParam;
import cn.uploadSys.entity.Classes;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.service.ClassesService;
import cn.uploadSys.service.StudentsDetailService;
import cn.uploadSys.service.StudentsService;
import cn.uploadSys.service.upload.QczjService;
import cn.uploadSys.util.AjaxUtil;
import cn.uploadSys.util.ExcelUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj")
public class QczjController extends BaseController {

   @Autowired
   private QczjService qczjService;



    @RequestMapping(value = "/list")
    public String classesList(Model model){
        model.addAttribute("menus", getMenus("qczj"));
        model.addAttribute("defaultDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        return "/qczj/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result classesGrid(AllJqGridParam param) throws ParseException {
        PageInfo<Qczj> pageInfo = qczjService.selectByJqGridParam(param);
        JqGridResult<Qczj> result = new JqGridResult<>();
        //当前页
        result.setPage(pageInfo.getPageNum());
        //数据总数
        result.setRecords(pageInfo.getTotal());
        //总页数
        result.setTotal(pageInfo.getPages());
        //当前页数据
        result.setRows(pageInfo.getList());
        return new JSONResult(result);
    }


    @PostMapping("import")
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile file) throws IOException {
        //fileName 文件名
        String fileName = file.getOriginalFilename();
        boolean xlsx = fileName.endsWith(".xlsx");
        if (!xlsx) {
            log.error("请上传以.xlsx结尾的文件");
        }
        //得到文件流
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);


        //获取token_access
        String host = "https://openapi.autohome.com.cn";
        String path = "/api/oauth2/authorize";

        HashMap<String,String> accessBody = new HashMap();
        accessBody.put("client_id","");
        accessBody.put("client_secret","");
        accessBody.put("response_type","");
        HashMap<String,Object> header = new HashMap();
        header.put("Content-Type","application/json;charset=utf-8");

        String accessResult = AjaxUtil.doPost(null,host,path,accessBody.toString(),header);

        String path2 = "/api/oauth2/authorize?access_token="+"";
        //轮训每条数据，进行对接
        List<Qczj> qczjs = reader.read(0,2,Qczj.class);
        qczjs.forEach(qczj -> {
            System.out.println(qczj.toString());
            try {
                HashMap<String,String> body = new HashMap();
                body.put("cid",qczj.getCityCode());
                body.put("mobile",qczj.getPhone());
                body.put("appid",qczj.getUid());
                String cityName = qczj.getCityName();
                if (StringUtils.isNotEmpty(cityName)) {
                    body.put("cityname", URLEncoder.encode(qczj.getCityName(),"UTF-8"));
                }
                String brandName = qczj.getBrandName();
                if (StringUtils.isNotEmpty(brandName)) {
                    body.put("brandname", URLEncoder.encode(qczj.getBrandName(),"UTF-8"));
                }
                String specName = qczj.getCarSeriesName();
                if (StringUtils.isNotEmpty(specName)) {
                    body.put("specname", URLEncoder.encode(qczj.getCarSeriesName(),"UTF-8"));
                }
                String seriesName = qczj.getCarName();
                if (StringUtils.isNotEmpty(seriesName)) {
                    body.put("seriesname", URLEncoder.encode(qczj.getCarName(),"UTF-8"));
                }
                String mileage = qczj.getKm();
                if (StringUtils.isNotEmpty(mileage)) {
                    body.put("mileage", qczj.getKm());
                }
                String firstregtime = qczj.getFirstregtime();
                if (StringUtils.isNotEmpty(firstregtime)) {
                    body.put("firstregtime", URLEncoder.encode(qczj.getFirstregtime(),"UTF-8"));
                }

                String result = AjaxUtil.doPost(null,host,path,body.toString(),header);
                JSONObject jsonObject = JSONObject.parseObject(result);
                String returnCode = jsonObject.getString("returncode");
                if (StringUtils.isNotEmpty(returnCode)) {
    //                qczj.setId();
                    qczj.setCreateTime(new Date());
                    qczjService.insert(qczj);
                }else{
                    throw new BusinessException("电话："+qczj.getPhone()+"数据录入异常");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

        return OK;

    }


}
