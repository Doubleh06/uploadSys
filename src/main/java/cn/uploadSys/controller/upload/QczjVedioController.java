package cn.uploadSys.controller.upload;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.service.upload.QczjService;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Result test(@RequestParam("file") MultipartFile file,@RequestParam("cclid")String cclid,
                       @RequestParam("appid")String appid) throws IOException, UnirestException {
        String accessToken = qczjService.getAccessToken();
        String import_url = env.getProperty("qczj.uploadRecord.url")+accessToken;
        Map<String, Object> body = new HashMap<>();
        body.put("cclid",cclid);
        body.put("appid",appid);
        body.put("recordfile",file);
        HttpResponse<String> upload = Unirest.post(import_url)
                .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundarygmk3KBUQwymfMc8A")
                .header("Accept", "application/json")
                .fields(body)
                .asString();

        String result = upload.getBody().toString();
        System.out.println("result="+result);
        return OK;
    }
}
