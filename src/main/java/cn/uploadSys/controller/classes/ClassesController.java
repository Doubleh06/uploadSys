package cn.uploadSys.controller.classes;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dao.ClassesStudentsDao;
import cn.uploadSys.dto.ArrangeClassJqGridParam;
import cn.uploadSys.dto.ArrangeStudentsDto;
import cn.uploadSys.dto.ClassesDto;
import cn.uploadSys.dto.ClassesJqGridParam;
import cn.uploadSys.entity.Classes;
import cn.uploadSys.service.ClassesService;
import cn.uploadSys.service.StudentsDetailService;
import cn.uploadSys.service.StudentsService;
import cn.uploadSys.util.MyUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/classes")
public class ClassesController extends BaseController {

   @Autowired
   private ClassesService classesService;

   @Autowired
   private StudentsService studentsService;

   @Autowired
   private ClassesStudentsDao classesStudentsDao;

   @Autowired
   private StudentsDetailService studentsDetailService;



    @RequestMapping(value = "/list")
    public String classesList(Model model){
        model.addAttribute("menus", getMenus("classes"));
        return "/classes/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result classesGrid(ClassesJqGridParam param) {
        PageInfo<Classes> pageInfo = classesService.selectByJqGridParam(param);
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


    @RequestMapping(value = "/modify")
    public String classModify(Model model,@RequestParam Integer id){
        model.addAttribute("menus", getMenus("classes"));
        if (null!=id) {
            model.addAttribute("classes",classesService.select(id));
        }else{
            model.addAttribute("classes",null);
        }
        return "/classes/modify";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Result classesInsert(@Valid @RequestBody ClassesDto classesDto) {
        Classes classes = new Classes();
        BeanUtils.copyProperties(classesDto,classes);
        String startTime = classes.getStartTime();
        String endTime = classes.getEndTime();
        classes.setDuration(MyUtil.getDurations(startTime,endTime));
        classesService.insert(classes);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result classesDelete(@RequestParam Integer id) {
        classesService.delete(id);
        return OK;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Result classesGet(@RequestParam Integer id) {
        return new JSONResult(classesService.select(id));
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result classesUpdate(@Valid @RequestBody ClassesDto classesDto) {
        Classes classes = new Classes();
        BeanUtils.copyProperties(classesDto,classes);
        String startTime = classes.getStartTime();
        String endTime = classes.getEndTime();
        classes.setDuration(MyUtil.getDurations(startTime,endTime));
        classesService.updateSelective(classes);
        return OK;
    }

    @RequestMapping("/arrangeStudents/list")
    public String arrangeStudentsList(Model model,@RequestParam Integer id){
        model.addAttribute("menus", getMenus("classes"));
        model.addAttribute("id", id);
        model.addAttribute("today",new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
        return "/classes/classesDetailList";
    }

    @RequestMapping(value = "/arrangeStudents/grid")
    @ResponseBody
    public Result arrangeStudentsGrid(ArrangeClassJqGridParam param) {
        PageInfo<Map> pageInfo = classesService.selectByJqGridParam(param);
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

    @RequestMapping("/arrangeStudents/get")
    @ResponseBody
    public Result studentGet() {
        return new JSONResult(studentsService.selectAll());
    }

    @RequestMapping("/arrangeStudents/insert")
    @ResponseBody
    public Result arrangeStudentsInsert(@RequestBody ArrangeStudentsDto arrangeStudentsDto) throws ParseException {
        classesService.arrangeStudents(arrangeStudentsDto);

        return OK;
    }

    @RequestMapping("/arrangeStudents/delete")
    @ResponseBody
    public Result arrangeStudentsDelete(@RequestParam Integer sid,@RequestParam Integer cid) {
        //删除 classes_students
        classesStudentsDao.deleteByCidAndSid(cid,sid);
        //删除 students_detail
        studentsDetailService.deleteByCidAndSid(cid,sid);
        return OK;
    }
}
