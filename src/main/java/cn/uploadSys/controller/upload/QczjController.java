package cn.uploadSys.controller.upload;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.service.upload.QczjService;
import cn.uploadSys.util.AjaxUtil;
import cn.uploadSys.util.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj")
public class QczjController extends BaseController {

   @Autowired
   private QczjService qczjService;
   @Autowired
   private Environment env;



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
        String access_host = env.getProperty("qczj.access_host");
        String access_path = env.getProperty("qczj.access_path");

        JSONObject accessBody = new JSONObject();
        accessBody.put("client_id",env.getProperty("qczj.client_id"));
        accessBody.put("client_secret",env.getProperty("qczj.client_secret"));
        accessBody.put("response_type","token");
        HashMap<String,Object> accessHeader = new HashMap();
        accessHeader.put("Content-Type","application/json;charset=utf-8");

        String accessResult = AjaxUtil.doPost("https",access_host,access_path,accessBody.toString(),accessHeader);
        JSONObject json = JSONObject.parseObject(accessResult);
        String accessToken = json.getJSONObject("data").getString("access_token");

        String import_url = env.getProperty("qczj.url")+accessToken;

        //轮训每条数据，进行对接
        List<Qczj> qczjs = reader.read(1,2,Qczj.class);
        qczjs.forEach(qczj -> {
            try {
                Map<String, Object> body = new HashMap<>();

                String phone = qczj.getPhone();
                String uid = qczj.getUid();
                String cityName = qczj.getCityName();
                if (StringUtils.isNotEmpty(phone) && StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(cityName)) {
                    body.put("access_token",accessToken);
                    body.put("mobile",phone);
                    body.put("appid",uid);
                    body.put("cityname", URLEncoder.encode(cityName,"UTF-8"));
                }else{
                    qczj.setStatus(1);
                    qczj.setCreateTime(new Date());
                    qczj.setMessage("缺少必要参数");
                    qczjService.insert(qczj);
                    return ;
                }

                String cid = qczj.getCityCode();
                if (StringUtils.isNotEmpty(cid)) {
                    body.put("cid",qczj.getCityCode());
                }
                String brandName = qczj.getBrandName();
                if (StringUtils.isNotEmpty(brandName)) {
                    body.put("brandname", URLEncoder.encode(brandName,"UTF-8"));
                }
                String specName = qczj.getCarSeriesName();
                if (StringUtils.isNotEmpty(specName)) {
                    body.put("specname", URLEncoder.encode(specName,"UTF-8"));
                }
                String seriesName = qczj.getCarName();
                if (StringUtils.isNotEmpty(seriesName)) {
                    body.put("seriesname", URLEncoder.encode(seriesName,"UTF-8"));
                }
                String mileage = qczj.getKm();
                if (StringUtils.isNotEmpty(mileage)) {
                    body.put("mileage", qczj.getKm());
                }
                String firstregtime = qczj.getFirstregtime();
                if (StringUtils.isNotEmpty(firstregtime)) {
                    body.put("firstregtime", URLEncoder.encode(firstregtime,"UTF-8"));
                }

                HttpResponse<String> upload = Unirest.post(import_url)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("accept", "application/json")
                        .fields(body)
                        .asString();

                String result = upload.getBody().toString();

                if (StringUtils.isNotEmpty(result) && result.contains("error_description")) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    String errorDescription = jsonObject.getString("error_description");
                    qczj.setStatus(1);
                    qczj.setMessage(errorDescription);
                }else{
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    String returnCode = jsonObject.getString("returncode");
                    String message = jsonObject.getString("message");
                    if (StringUtils.isNotEmpty(returnCode) && returnCode.equals("0")) {
                        String cclid = jsonObject.getString("cclid");
                        qczj.setStatus(0);
                        qczj.setCclid(cclid);
                    }else{
                        qczj.setStatus(1);
                        qczj.setMessage(message);
                    }
                }
                qczj.setCreateTime(new Date());
                qczjService.insert(qczj);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

        return OK;

    }
    @RequestMapping("export")
    @ResponseBody
    public Result exportFile(AllJqGridParam param, HttpServletResponse response) throws IOException, ParseException {
        List<Qczj> rows = qczjService.selectByJqGridParamNoPage(param);

        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        //out为OutputStream，需要写出到的目标流

        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        response.setHeader("Content-Disposition","attachment;filename="+sdf.format(new Date())+".xls");
        ServletOutputStream out=response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);


        return OK;
    }

    @RequestMapping("getStatus")
    @ResponseBody
    public Result getStatus(){
        qczjService.getUnfinishedInstance();
        return OK;
    }

    @RequestMapping("uploadRecord")
    @ResponseBody
    public Result uploadRecord(@RequestParam("file") MultipartFile file,@RequestParam("cclid")String cclid,@RequestParam("appid")String appid){
        qczjService.uploadRecord(cclid,appid);
        return OK;
    }
}
