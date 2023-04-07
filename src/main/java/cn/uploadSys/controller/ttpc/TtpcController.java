package cn.uploadSys.controller.ttpc;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dao.TtpcDao;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.dto.TtpcJqGridParam;
import cn.uploadSys.entity.ttpc.SignUp;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.service.Ttpc.TtpcService;
import cn.uploadSys.service.upload.QczjService;
import cn.uploadSys.util.AjaxUtil;
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
import org.springframework.web.bind.annotation.*;
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
@RequestMapping(value = "/upload/ttpc")
public class TtpcController extends BaseController {

   @Autowired
   private TtpcService ttpcService;
   @Autowired
   private TtpcDao ttpcDao;
   @Autowired
   private Environment env;



    @RequestMapping(value = "/list")
    public String classesList(Model model){
        model.addAttribute("menus", getMenus("ttpc"));
        model.addAttribute("defaultDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        return "/ttpc/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result classesGrid(TtpcJqGridParam param) throws ParseException {
        PageInfo<SignUp> pageInfo = ttpcService.selectByJqGridParam(param);
        JqGridResult<SignUp> result = new JqGridResult<>();
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

    @GetMapping("packageUrl")
    @ResponseBody
    public Result packageUrl(SignUp signUp) throws Exception {
        return new JSONResult(ttpcService.packageUrl(signUp));
    }
    @GetMapping("ajaxToThirdPart")
    @ResponseBody
    public void packageUrl() throws Exception {
        List<SignUp> signUps = new ArrayList<>();
        SignUp signUp = new SignUp();
        signUp.setName("东方乱败");
        signUp.setMobile("13685223561");
        signUp.setBrand("劳斯莱斯");
        signUp.setCity("上海");
        signUp.setSource("123");
        signUps.add(signUp);
        ttpcService.ajaxToThirdPart(signUps);
    }



    @PostMapping("import")
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile file) throws Exception {
        ttpcService.importFile(file);
        return OK;

    }
    @RequestMapping("export")
    @ResponseBody
    public Result exportFile(AllJqGridParam param, HttpServletResponse response) throws IOException, ParseException {
//        List<Qczj> rows = ttpcService.selectByJqGridParamNoPage(param);
//
//        // 通过工具类创建writer，默认创建xls格式
//        ExcelWriter writer = ExcelUtil.getWriter();
//        // 一次性写出内容，使用默认样式，强制输出标题
//        writer.write(rows, true);
//        //out为OutputStream，需要写出到的目标流
//
//        //response为HttpServletResponse对象
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        response.setHeader("Content-Disposition","attachment;filename="+sdf.format(new Date())+".xls");
//        ServletOutputStream out=response.getOutputStream();
//
//        writer.flush(out, true);
//        // 关闭writer，释放内存
//        writer.close();
//        //此处记得关闭输出Servlet流
//        IoUtil.close(out);


        return OK;
    }

    @RequestMapping("getStatus")
    @ResponseBody
    public Result getStatus(){
        ttpcService.getUnfinishedInstance();
        return OK;
    }

//    @RequestMapping("uploadRecord")
//    @ResponseBody
//    public Result uploadRecord(@RequestParam("file") MultipartFile file,@RequestParam("cclid")String cclid,@RequestParam("appid")String appid) throws UnirestException {
//        qczjService.uploadRecord(file,cclid,appid);
//        return OK;
//    }
}
