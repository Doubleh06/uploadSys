package cn.uploadSys.service;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.BusinessException;
import cn.uploadSys.core.ErrorCode;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.ClassesDao;
import cn.uploadSys.dao.ClassesStudentsDao;
import cn.uploadSys.dto.ArrangeClassJqGridParam;
import cn.uploadSys.dto.ArrangeStudentsDto;
import cn.uploadSys.dto.ClassesJqGridParam;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.entity.Classes;
import cn.uploadSys.entity.ClassesStudents;
import cn.uploadSys.entity.Students;
import cn.uploadSys.entity.StudentsDetail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClassesService extends AbstractService<Classes> {


    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private ClassesStudentsDao classesStudentsDao;

    @Autowired
    private StudentsDetailService studentsDetailService;

    @Autowired
    private ClassesService classesService;

    @Autowired
    private StudentsService studentsService;

    @Override
    protected BaseDao<Classes> getDao() {
        return classesDao;
    }

    @Override
    protected List<Classes> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return classesDao.selectBySql("classes",sql.toString());
    }


    public PageInfo<Classes> selectByJqGridParam(ClassesJqGridParam param ){
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and  name like  '%").append(param.getName()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getTeacher())) {
            sql.append(" and teacher like '%").append(param.getTeacher()).append("%'");
        }
        if (null != param.getWeek()) {
            sql.append(" and week = ").append(param.getWeek());
        }
        sql.append(" ORDER BY week , start_time ASC ");
        return new PageInfo<>(classesDao.getStudentsList(sql.toString()));
    }

    public PageInfo<Map> selectByJqGridParam(ArrangeClassJqGridParam param){
        PageHelper.startPage(param.getPage(), param.getRows());
        return new PageInfo<>(classesDao.getStudentsByClassesId(param.getId()));
    }

    public void delete(Integer id){
       Classes classes = new Classes();
        classes.setId(id);
        classesDao.delete(classes);
    }

    public void arrangeStudents(ArrangeStudentsDto arrangeStudentsDto) throws ParseException {
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
        //获取总课程的金额
        BigDecimal fee = students.getFee();
        //获取报名课程的课时数
        Integer totalClasses = classes.getTotal();

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
            studentsDetail.setFee(fee.divide(new BigDecimal(totalClasses),2,BigDecimal.ROUND_HALF_UP));
            list.add(studentsDetail);

        }
        studentsDetailService.insertStudentsDetail(list);
    }

    

}
