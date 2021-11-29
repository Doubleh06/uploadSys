package cn.uploadSys.controller.upload;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.core.jqGrid.JqGridResult;
import cn.uploadSys.dao.ClassesStudentsDao;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.dto.ClassesJqGridParam;
import cn.uploadSys.entity.Classes;
import cn.uploadSys.entity.upload.Qczj;
import cn.uploadSys.service.ClassesService;
import cn.uploadSys.service.StudentsDetailService;
import cn.uploadSys.service.StudentsService;
import cn.uploadSys.service.upload.QczjService;
import cn.uploadSys.util.ExcelUtils;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/upload/qczj")
public class QczjController extends BaseController {

   @Autowired
   private QczjService qczjService;



    @RequestMapping(value = "/list")
    public String classesList(Model model){
        model.addAttribute("menus", getMenus("qczj"));
        return "/qczj/list";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result classesGrid(AllJqGridParam param) {
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


    @PostMapping("import")
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

        //hutool读取excel 1：表示表格头所在行，2：从第几行开始读取，2147483647：行的最大值
        //因为自定义了表格头别名，所以只能使用map接收，如果没有设置别名，可以使用实体接收
//        List<Map<String, Object>> readAll = reader.read(1, 2, 2147483647);

        List<Qczj> qczjs = reader.readAll(Qczj.class);

        System.out.println(qczjs.toString());
        return OK;
//        for (int i = 0; i < readAll.size(); i++) {
//            Map<String, Object> quMap = readAll.get(i);
//
//            //获取表格中的数据
//            String repos = (String) quMap.get("题库名称");
//            String quType = (String) quMap.get("题型");
//            String quContent = (String) quMap.get("题干");
//            String answerContent = (String) quMap.get("选项");
//            String isRight = (String) quMap.get("答案");
//            String quAnalysis = (String) quMap.get("解析");
//            String quScore = String.valueOf(quMap.get("题目分值"));
//            //在这里可以进行格式的校验、持久化到数据库等操作
//
//        }

    }
//    @RequestMapping(value = "/modify")
//    public String classModify(Model model,@RequestParam Integer id){
//        model.addAttribute("menus", getMenus("classes"));
//        if (null!=id) {
//            model.addAttribute("classes",classesService.select(id));
//        }else{
//            model.addAttribute("classes",null);
//        }
//        return "/classes/modify";
//    }
//
//    @RequestMapping("/insert")
//    @ResponseBody
//    public Result classesInsert(@Valid @RequestBody ClassesDto classesDto) {
//        Classes classes = new Classes();
//        BeanUtils.copyProperties(classesDto,classes);
//        String startTime = classes.getStartTime();
//        String endTime = classes.getEndTime();
//        classes.setDuration(MyUtil.getDurations(startTime,endTime));
//        classesService.insert(classes);
//        return OK;
//    }
//
//    @RequestMapping("/delete")
//    @ResponseBody
//    public Result classesDelete(@RequestParam Integer id) {
//        classesService.delete(id);
//        return OK;
//    }
//
//    @RequestMapping("/get")
//    @ResponseBody
//    public Result classesGet(@RequestParam Integer id) {
//        return new JSONResult(classesService.select(id));
//    }
//
//    @RequestMapping("/update")
//    @ResponseBody
//    public Result classesUpdate(@Valid @RequestBody ClassesDto classesDto) {
//        Classes classes = new Classes();
//        BeanUtils.copyProperties(classesDto,classes);
//        String startTime = classes.getStartTime();
//        String endTime = classes.getEndTime();
//        classes.setDuration(MyUtil.getDurations(startTime,endTime));
//        classesService.updateSelective(classes);
//        return OK;
//    }
//
//    @RequestMapping("/arrangeStudents/list")
//    public String arrangeStudentsList(Model model,@RequestParam Integer id){
//        model.addAttribute("menus", getMenus("classes"));
//        model.addAttribute("id", id);
//        model.addAttribute("today",new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
//        return "/classes/classesDetailList";
//    }
//
//    @RequestMapping(value = "/arrangeStudents/grid")
//    @ResponseBody
//    public Result arrangeStudentsGrid(ArrangeClassJqGridParam param) {
//        PageInfo<Map> pageInfo = classesService.selectByJqGridParam(param);
//        JqGridResult<Map> result = new JqGridResult<>();
//        //当前页
//        result.setPage(pageInfo.getPageNum());
//        //数据总数
//        result.setRecords(pageInfo.getTotal());
//        //总页数
//        result.setTotal(pageInfo.getPages());
//        //当前页数据
//        result.setRows(pageInfo.getList());
//        return new JSONResult(result);
//    }
//
//    @RequestMapping("/arrangeStudents/get")
//    @ResponseBody
//    public Result studentGet() {
//        return new JSONResult(studentsService.selectAll());
//    }
//
//    @RequestMapping("/arrangeStudents/insert")
//    @ResponseBody
//    public Result arrangeStudentsInsert(@RequestBody ArrangeStudentsDto arrangeStudentsDto) throws ParseException {
//        classesService.arrangeStudents(arrangeStudentsDto);
//
//        return OK;
//    }
//
//    @RequestMapping("/arrangeStudents/delete")
//    @ResponseBody
//    public Result arrangeStudentsDelete(@RequestParam Integer sid,@RequestParam Integer cid) {
//        //删除 classes_students
//        classesStudentsDao.deleteByCidAndSid(cid,sid);
//        //删除 students_detail
//        studentsDetailService.deleteByCidAndSid(cid,sid);
//        return OK;
//    }
}
