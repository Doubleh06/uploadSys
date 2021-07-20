package cn.nlf.controller.students;

import cn.nlf.controller.BaseController;
import cn.nlf.core.BusinessException;
import cn.nlf.core.ErrorCode;
import cn.nlf.core.JSONResult;
import cn.nlf.core.Result;
import cn.nlf.core.jqGrid.JqGridParam;
import cn.nlf.core.jqGrid.JqGridResult;
import cn.nlf.dto.DeleteClassJqGridParam;
import cn.nlf.dto.StudentsDto;
import cn.nlf.dto.StudentsInClassesJqGridParam;
import cn.nlf.dto.StudentsJqGridParam;
import cn.nlf.entity.Classes;
import cn.nlf.entity.Role;
import cn.nlf.entity.Students;
import cn.nlf.entity.StudentsDetail;
import cn.nlf.security.UserDetail;
import cn.nlf.service.StudentsDetailService;
import cn.nlf.service.StudentsService;
import cn.nlf.util.SpringSecurityUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/students")
public class StudentsController extends BaseController {

   @Autowired
   private StudentsService studentsService;

   @Autowired
   private StudentsDetailService studentsDetailService;

    @RequestMapping(value = "/list")
    public String studentsList(Model model,@RequestParam Integer cid){
        model.addAttribute("menus", getMenus("students"));
        model.addAttribute("cid", cid);
        return "/students/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result studentsGrid(StudentsJqGridParam param) {
        PageInfo<Students> pageInfo = studentsService.selectByJqGridParam(param);
        JqGridResult<Students> result = new JqGridResult<>();
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

    @RequestMapping("/insert")
    @ResponseBody
    public Result studentsInsert(@Valid @RequestBody StudentsDto studentsDto) {
        Students students = new Students();
        BeanUtils.copyProperties(studentsDto,students);
        studentsService.insert(students);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result studentsDelete(@RequestParam Integer id) {
        studentsService.delete(id);
        return OK;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Result studentsGet(@RequestParam Integer id) {
        return new JSONResult(studentsService.select(id));
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result studentsUpdate(@Valid @RequestBody StudentsDto studentsDto) {
        Students students = new Students();
        BeanUtils.copyProperties(studentsDto,students);
        studentsService.updateSelective(students);
        return OK;
    }

    @RequestMapping("/show")
    @ResponseBody
    public Result show(@RequestParam Integer id) {
        return new JSONResult(studentsDetailService.show(id));
    }


    @RequestMapping(value = "/detail/list")
    public String getClassesByStudentsId(Model model,@RequestParam Integer id){
        model.addAttribute("menus", getMenus("students"));
        model.addAttribute("id", id);
        return "/students/listDetail";
    }

    @RequestMapping(value = "/detail/grid")
    @ResponseBody
    public Result studentsDetailGrid(DeleteClassJqGridParam param) {
        PageInfo<StudentsDetail> pageInfo = studentsDetailService.selectByJqGridParam(param);
        JqGridResult<StudentsDetail> result = new JqGridResult<>();
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

    /**
     * 消课
     * @param id
     * @return
     */
    @RequestMapping("/detail/deleteClass")
    @ResponseBody
    public Result deleteClass(@RequestParam Integer id,@RequestParam Integer reasonValue) {
        boolean flag = false;
        UserDetail userDetail = SpringSecurityUtil.getUser();
        List<Role> roles = userDetail.getRoles();
        for (Role role : roles){
            if (role.getRoleKey().equals("shopOwner")) {
                flag = true;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StudentsDetail studentsDetail = studentsDetailService.select(id);
        String currentDate = sdf.format(new Date());
        String dbDate = sdf.format(studentsDetail.getDate());
        studentsDetail.setStatus(1);
        studentsDetail.setReason(reasonValue);
        studentsDetail.setId(id);
        studentsDetail.setDeleteDate(new Date());

        if (!currentDate.equals(dbDate)) {
            if(!flag){
                throw new BusinessException(ErrorCode.FAIL_DELETECLASS,ErrorCode.FAIL_DELETECLASS.message());
            }
        }
        studentsDetailService.update(studentsDetail);
        return OK;
    }


    @RequestMapping(value = "/studentsInClasses/list")
    public String studentsInClasses(Model model){
        model.addAttribute("menus", getMenus("students"));
        return "/students/classesList";
    }

    @RequestMapping(value = "/studentsInClasses/grid")
    @ResponseBody
    public Result studentsInClassesGrid(StudentsInClassesJqGridParam param) {
        PageInfo<Classes> pageInfo = studentsDetailService.selectByJqGridParam(param);
        JqGridResult<Classes> result = new JqGridResult<>();
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

}
