package cn.uploadSys.controller.renrenche;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.entity.renrenche.Rrc;
import cn.uploadSys.service.renrenche.RrcCommonService;
import com.github.pagehelper.PageInfo;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/upload/rrc/common")
public class RrcController extends BaseController {

   @Autowired
   private RrcCommonService rrcCommonService;



    @RequestMapping(value = "/list")
    public String classesList(Model model){
        model.addAttribute("menus", getMenus("common"));
        model.addAttribute("defaultDate", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        return "/rrc/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result classesGrid(AllJqGridParam param) throws ParseException {
        PageInfo<Rrc> pageInfo = rrcCommonService.selectByJqGridParam(param);
        JqGridResult<Rrc> result = new JqGridResult<>();
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

        List<Rrc> rrcs = reader.read(1,2,Rrc.class);
        rrcs.forEach(rrc -> {
            rrcCommonService.interfaceC1(rrc);
        });

        return OK;

    }

    @PostMapping("testInterface")
    @ResponseBody
    public Result testInterface() {
        Rrc rrc = new Rrc();
        rrc.setName("奥特曼");
        rrc.setMobile("13698522214");
        rrc.setCity("北京");
        rrc.setBrand("布加迪");
        rrc.setSeries("土狗");
        rrc.setModel("1");
        rrc.setKilometer("123.2");
        rrc.setLicensedDateYear(2033);
        rrc.setIsOperation('0');
        rrc.setSeatNumber(2);
        rrc.setIsAccidented('0');
        rrcCommonService.interfaceC1(rrc);
        return OK;

    }

    @RequestMapping("export")
    @ResponseBody
    public void exportFile(AllJqGridParam param, HttpServletResponse response) throws IOException, ParseException {
        List<Rrc> rows = rrcCommonService.selectByJqGridParamNoPage(param);

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
//        return OK;
    }

    @RequestMapping("setUrlAndToken")
    @ResponseBody
    public void setUrlAndToken(String url,String token){
        rrcCommonService.setUrlAndToken(url,token);
    }


    @RequestMapping("getUrlAndToken")
    @ResponseBody
    public Map<String,String> getUrlAndToken(){
        return rrcCommonService.getUrlAndToken();
    }

}
