package cn.uploadSys.controller.upload;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.entity.upload.QczjHQ;
import cn.uploadSys.service.upload.QczjHQService;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj/hq")
public class QczjHQController extends BaseController {

   @Autowired
   private QczjHQService qczjHQService;



    @RequestMapping(value = "/list")
    public String classesList(Model model){
        model.addAttribute("menus", getMenus("hq"));
        model.addAttribute("defaultDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        return "/qczj/listHQ";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result classesGrid(AllJqGridParam param) throws ParseException {
        PageInfo<QczjHQ> pageInfo = qczjHQService.selectByJqGridParam(param);
        JqGridResult<QczjHQ> result = new JqGridResult<>();
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
        qczjHQService.importFile(file);
        return OK;

    }


    @RequestMapping("export")
    @ResponseBody
    public Result exportFile(AllJqGridParam param, HttpServletResponse response) throws IOException, ParseException {
        List<QczjHQ> rows = qczjHQService.selectByJqGridParamNoPage(param);

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

    @RequestMapping("getStatusV3")
    @ResponseBody
    public Result getStatus(){
        qczjHQService.getUnfinishedInstance();
        return OK;
    }

    @RequestMapping("appeal")
    @ResponseBody
    public Result appeal(String cclid,String appId) throws UnirestException {
        qczjHQService.getStatusV3(cclid,appId);
        return OK;
    }
}
