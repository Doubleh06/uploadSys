package cn.uploadSys.controller.upload;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.service.upload.QczjService;
import com.github.pagehelper.PageInfo;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


}
