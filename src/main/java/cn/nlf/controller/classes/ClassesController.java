package cn.nlf.controller.classes;

import cn.nlf.controller.BaseController;
import cn.nlf.core.BusinessException;
import cn.nlf.core.ErrorCode;
import cn.nlf.core.JSONResult;
import cn.nlf.core.Result;
import cn.nlf.core.jqGrid.JqGridResult;
import cn.nlf.dao.ClassesStudentsDao;
import cn.nlf.dto.*;
import cn.nlf.entity.Classes;
import cn.nlf.entity.ClassesStudents;
import cn.nlf.entity.Students;
import cn.nlf.entity.StudentsDetail;
import cn.nlf.service.ClassesService;
import cn.nlf.service.StudentsDetailService;
import cn.nlf.service.StudentsService;
import cn.nlf.util.MyUtil;
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
        List<StudentsDetail> list = new ArrayList<>();
        Integer sid = arrangeStudentsDto.getSid();
        Integer cid = arrangeStudentsDto.getCid();
        //校验是否已经报名次课程
        List<ClassesStudents> lists = classesStudentsDao.isSignUp(cid,sid);
        if (!lists.isEmpty()) {
         throw new BusinessException(ErrorCode.FAIL_REPEATSIGNUP);
        }
        Date date = new SimpleDateFormat("MM/dd/yyyy").parse(arrangeStudentsDto.getDate());
        Date createTime = new Date();
        long oneDay = 604800000L;
        //插入中间表 形成关联
        ClassesStudents classesStudents = new ClassesStudents(sid,cid,createTime);
        classesStudentsDao.insert(classesStudents);
        //形成学生上课明细数据
        //获取课程信息
        Classes classes = classesService.select(cid);
        Integer total = classes.getTotal();
        //获取学生信息
        Students students = studentsService.select(sid);

        //根据上课周期生成日期
        for(int i=0;i<total;i++){
            StudentsDetail studentsDetail = new StudentsDetail();
            studentsDetail.setCid(cid);
            studentsDetail.setClassName(classes.getName());
            studentsDetail.setWeek(classes.getWeek());
            studentsDetail.setDuration(classes.getDuration());
            studentsDetail.setStartTime(classes.getStartTime());
            studentsDetail.setEndTime(classes.getEndTime());
            studentsDetail.setStatus(0);
            studentsDetail.setSid(sid);
            studentsDetail.setStudentName(students.getName());
            studentsDetail.setDate(new Date(date.getTime()+i*oneDay));
            studentsDetail.setReason(0);
            studentsDetail.setCreateTime(createTime);
            list.add(studentsDetail);

        }
        studentsDetailService.insertStudentsDetail(list);

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
