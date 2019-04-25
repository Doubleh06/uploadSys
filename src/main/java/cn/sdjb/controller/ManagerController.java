package cn.sdjb.controller;

import cn.sdjb.core.JSONResult;
import cn.sdjb.core.Result;
import cn.sdjb.core.jqGrid.JqGridResult;
import cn.sdjb.dao.AccidentLevelDao;
import cn.sdjb.dao.AccidentTypeDao;
import cn.sdjb.dao.EhsDao;
import cn.sdjb.dto.EhsJqGridParam;
import cn.sdjb.entity.Ehs;
import cn.sdjb.service.EhsService;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.xdevapi.JsonArray;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/backstage")
public class ManagerController extends BaseController {
    private List<Map> list = new ArrayList<>();
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private AccidentLevelDao accidentLevelDao;
    @Autowired
    private EhsService ehsService;
    @Autowired
    private EhsDao ehsDao;



    @RequestMapping(value = "list")
    public String list(Model model){
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("accidentTypes",accidentTypeDao.selectAll());
        model.addAttribute("accidentLevels",accidentLevelDao.selectAll());
        return "/backstage/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid(EhsJqGridParam param) {
        PageInfo<Map> pageInfo = ehsService.selectByJqGridParam(param);
        JqGridResult<Map> result = new JqGridResult<>();
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

    @RequestMapping(value = "delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        Ehs ehs = new Ehs();
        ehs.setId(id);
        ehsDao.delete(ehs);
        return OK;
    }

    @RequestMapping(value = "photos")
    public String photos(Model model,@RequestParam Integer id){
        model.addAttribute("menus", getMenus("backstage"));
        model.addAttribute("imgUrls",ehsService.getImgUrl(id));
        return "/backstage/photos";
    }


    @RequestMapping(value = "prepareExportData")
    @ResponseBody
    public Result export(@RequestBody EhsJqGridParam ehsJqGridParam) {
        list = ehsService.selectListByJqGridParam(ehsJqGridParam);
        return  OK;
    }

    @RequestMapping(value = "export")
    @ResponseBody
    public void export(HttpServletResponse response,JsonArray data) throws IOException {

        //excel 表头
        String[] columns = new String[]{"序号","事故类型", "事故发生时间", "涉及人员","部门", "事发地点", "事故情况", "汇报人","地址","事故等级"};
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("员工安全事故报告列表");

        //创建表头
        HSSFRow header = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        //i-行   j-列
        for (int i=0;i<list.size();i++){
            HSSFRow row = sheet.createRow(i+1);
            for (int j = 0; j < columns.length; j++) {
                row.createCell(0).setCellValue(list.get(i).get("id").toString());
                row.createCell(1).setCellValue(accidentTypeDao.getNameById(Integer.parseInt(list.get(i).get("accident_type").toString())));
                row.createCell(2).setCellValue(sdf2.format(list.get(i).get("accident_time")));
                row.createCell(3).setCellValue(list.get(i).get("accident_man").toString());
                row.createCell(4).setCellValue(list.get(i).get("dept").toString());
                row.createCell(5).setCellValue(list.get(i).get("accident_place").toString());
                row.createCell(6).setCellValue(list.get(i).get("accident_situation").toString());
                row.createCell(7).setCellValue(list.get(i).get("report_man").toString());
                row.createCell(8).setCellValue(list.get(i).get("address").toString());
                if(null==list.get(i).get("accident_level")){
                    row.createCell(9).setCellValue("");
                }else{
                    row.createCell(9).setCellValue(accidentLevelDao.getNameById(Integer.parseInt(list.get(i).get("accident_level").toString())));
                }


            }
        }



        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=EHS.xls");
        response.setContentType("application/msexcel");
        wb.write(output);
        list.clear();
        output.close();
    }

    @RequestMapping(value = "changeStatus")
    @ResponseBody
    public Result changeStatus(@RequestParam Integer id,@RequestParam Integer status) {
        Ehs ehs = new Ehs();
        ehs.setId(id);
        ehs.setStatus(status);
        ehs.setDealTime(new Date());
        ehsDao.updateByPrimaryKeySelective(ehs);
        return OK;
    }

    @RequestMapping(value = "accept")
    @ResponseBody
    public Result accept(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("ehsId");
        Integer accidentLevel = jsonObject.getInteger("accidentLevel");
        Ehs ehs = new Ehs();
        ehs.setId(id);
        ehs.setAccidentLevel(accidentLevel);
        ehs.setStatus(1);
        ehs.setDealTime(new Date());
        ehsDao.updateByPrimaryKeySelective(ehs);
        return OK;
    }
}
