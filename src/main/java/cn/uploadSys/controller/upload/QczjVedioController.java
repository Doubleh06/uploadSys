package cn.uploadSys.controller.upload;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.service.upload.QczjService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj/vedio")
public class QczjVedioController extends BaseController {

   @Autowired
   private QczjService qczjService;
    @Autowired
    private Environment env;



    @RequestMapping(value = "/list")
    public String classesList(Model model){
        model.addAttribute("menus", getMenus("vedio"));
        model.addAttribute("defaultDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        return "/qczj/listVedio";
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

    @RequestMapping(value = "/getAccessToken")
    @ResponseBody
    public Result getAccessToken() throws  UnirestException {
        return new JSONResult(qczjService.getAccessToken());
    }

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file,@RequestParam("cclid")String cclid,
                       @RequestParam("appid")String appid) throws IOException, UnirestException {
        String accessToken = qczjService.getAccessToken();
        String import_url = env.getProperty("qczj.uploadRecord.url")+accessToken;
        Map<String, Object> body = new HashMap<>();
        body.put("cclid",cclid);
        body.put("appid",appid);
        body.put("recordfile",multipartFileToFile(file));
        HttpResponse<String> upload = Unirest.post(import_url)
//                .header("Content-Type", "multipart/form-data")
                .header("Accept", "application/json")
                .fields(body)
                .asString();

        String result = upload.getBody().toString();
        if (StringUtils.isNotEmpty(result) && result.contains("error_description")) {
            return new Result(1,"上传异常");
        }else{
            JSONObject jsonObject = JSONObject.parseObject(result);
            String returnCode = jsonObject.getString("returncode");
            String message = jsonObject.getString("message");
            return OK;
        }
    }

    public static File multipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

