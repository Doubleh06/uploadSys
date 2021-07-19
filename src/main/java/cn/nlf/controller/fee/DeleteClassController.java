package cn.nlf.controller.fee;

import cn.nlf.controller.BaseController;
import cn.nlf.core.BusinessException;
import cn.nlf.core.ErrorCode;
import cn.nlf.core.JSONResult;
import cn.nlf.core.Result;
import cn.nlf.core.jqGrid.JqGridResult;
import cn.nlf.dto.DeleteClassJqGridParam;
import cn.nlf.dto.SignUpJqGridParam;
import cn.nlf.entity.Role;
import cn.nlf.entity.StudentsDetail;
import cn.nlf.security.UserDetail;
import cn.nlf.service.DeleteClassService;
import cn.nlf.service.SignUpService;
import cn.nlf.service.StudentsDetailService;
import cn.nlf.util.SpringSecurityUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
