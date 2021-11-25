package cn.uploadSys.controller.fee;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dto.SignUpJqGridParam;
import cn.uploadSys.service.DeleteClassService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping(value = "/fee/deleteClass")
public class DeleteClassController extends BaseController {

   @Autowired
   private DeleteClassService deleteClassService;


    @RequestMapping(value = "/list")
    public String studentsList(Model model){
        model.addAttribute("menus", getMenus("deleteClass"));
        return "/fee/deleteClass/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result studentsGrid(SignUpJqGridParam param) {
        PageInfo<Map> pageInfo = deleteClassService.selectByJqGridParam(param);
        BigDecimal totalFee = deleteClassService.getTotalFee();
        JqGridResult<Map> result = new JqGridResult<>();
        //当前页
        result.setPage(pageInfo.getPageNum());
        //数据总数
        result.setRecords(pageInfo.getTotal());
        //总页数
        result.setTotal(pageInfo.getPages());
        //当前页数据
        result.setRows(pageInfo.getList());
        return new JSONResult(result,totalFee);
    }





}
