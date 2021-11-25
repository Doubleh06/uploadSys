package cn.uploadSys.service;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.ClassesDao;
import cn.uploadSys.dao.ClassesStudentsDao;
import cn.uploadSys.dao.StudentsDetailDao;
import cn.uploadSys.dto.ClassesShowDto;
import cn.uploadSys.dto.DeleteClassJqGridParam;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.dto.StudentsInClassesJqGridParam;
import cn.uploadSys.entity.Classes;
import cn.uploadSys.entity.ClassesStudents;
import cn.uploadSys.entity.StudentsDetail;
import cn.uploadSys.util.MyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentsDetailService extends AbstractService<StudentsDetail> {


    @Autowired
    private StudentsDetailDao studentsDetailDao;

    @Autowired
    private ClassesStudentsDao classesStudentsDao;

    @Autowired
    private ClassesDao classesDao;

    @Override
    protected BaseDao<StudentsDetail> getDao() {
        return studentsDetailDao;
    }

    @Override
    protected List<StudentsDetail> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return studentsDetailDao.selectBySql("students",sql.toString());
    }


    public PageInfo<StudentsDetail> selectByJqGridParam(DeleteClassJqGridParam param ){
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(null != param.getId()) {
            sql.append(" and  sid = ").append(param.getId());
        }
        if (StringUtils.isNotEmpty(param.getClassName())) {
            sql.append( " and class_name like '%").append(param.getClassName()).append("%'");
        }

        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }
        //消课时间范围
        if (null!=param.getYear() && null!=(param.getMonth())) {
            String startDate = param.getYear()+"-"+param.getMonth()+"-1";
            String endDate = param.getYear()+"-"+param.getMonth()+ "-"+MyUtil.getStartAndEndDate(param.getYear(),param.getMonth());
            sql.append(" and delete_date between '").append(startDate).append("' and '").append(endDate).append("'");
        }

        sql.append(" order by date asc");
        List<StudentsDetail> studentsDetails = studentsDetailDao.getStudentsDetailList(sql.toString());
        for (StudentsDetail studentsDetail:studentsDetails){
            studentsDetail.setSpare(getWeek(studentsDetail.getWeek())+" "+studentsDetail.getStartTime()+"-"+studentsDetail.getEndTime());
        }

        return new PageInfo<>(studentsDetails);
    }



    public PageInfo<Classes> selectByJqGridParam(StudentsInClassesJqGridParam param ){
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if (StringUtils.isNotEmpty(param.getClassName())) {
            sql.append( " and name like '%").append(param.getClassName()).append("%'");
        }
        //如果该课程没有学生，则不显示
        List<Classes> classes = classesDao.getStudentsList(sql.toString());
        for (int i=0;i<classes.size();i++){
            Integer count = classesStudentsDao.getTotalByCid(classes.get(i).getId());
            if (count==0){
                classes.remove(i);
            }
        }
        return new PageInfo<>(classes);
    }





    private String getWeek(Integer week){
        String result = "";
        switch (week){
            case 1:result = "星期一";break;
            case 2:result = "星期二";break;
            case 3:result = "星期三";break;
            case 4:result = "星期四";break;
            case 5:result = "星期五";break;
            case 6:result = "星期六";break;
            case 7:result = "星期日";break;
        }
        return result;
    }

    public void insertStudentsDetail(List<StudentsDetail> list){
        studentsDetailDao.insertStudentsDetail(list);
    }

    public void deleteByCidAndSid(Integer cid,Integer sid){
        studentsDetailDao.deleteByCidAndSid(cid,sid);
    }

    public List<ClassesShowDto> show(Integer sid){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("sid = ").append(sid);
        //获取一个人的所有课程
        List<ClassesStudents> classesStudents = classesStudentsDao.selectBySql("classes_students",stringBuffer.toString());
        List<ClassesShowDto> classesShowDtos = new ArrayList<>();
        for (ClassesStudents cs:classesStudents){
            String className = classesDao.getClassNameById(cs.getCid());
            //获取总课程数
            Integer deleteClasses = studentsDetailDao.getCount(cs.getCid(),cs.getSid(),1);
            Integer noDeleteClasses = studentsDetailDao.getCount(cs.getCid(),cs.getSid(),0);
            ClassesShowDto classesShowDto = new ClassesShowDto(className,noDeleteClasses+deleteClasses,noDeleteClasses,deleteClasses);
            classesShowDtos.add(classesShowDto);
        }
        return classesShowDtos;
    }

}
