package cn.nlf.service;


import cn.nlf.core.AbstractService;
import cn.nlf.core.BaseDao;
import cn.nlf.core.jqGrid.JqGridParam;
import cn.nlf.dao.ClassesDao;
import cn.nlf.dto.ArrangeClassJqGridParam;
import cn.nlf.dto.ClassesJqGridParam;
import cn.nlf.dto.StudentsJqGridParam;
import cn.nlf.entity.Classes;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassesService extends AbstractService<Classes> {


    @Autowired
    private ClassesDao classesDao;

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


    

}
