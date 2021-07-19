package cn.nlf.service;


import cn.nlf.core.AbstractService;
import cn.nlf.core.BaseDao;
import cn.nlf.core.jqGrid.JqGridParam;
import cn.nlf.dao.StudentsDao;
import cn.nlf.dto.StudentsJqGridParam;
import cn.nlf.entity.Students;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsService extends AbstractService<Students> {


    @Autowired
    private StudentsDao studentsDao;

    @Override
    protected BaseDao<Students> getDao() {
        return studentsDao;
    }

    @Override
    protected List<Students> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return studentsDao.selectBySql("students",sql.toString());
    }


    public PageInfo<Students> selectByJqGridParam(StudentsJqGridParam param ){
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and  name like  '%").append(param.getName()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and phone like '%").append(param.getPhone()).append("%'");
        }
        return new PageInfo<>(studentsDao.getStudentsList(sql.toString()));
    }

    public void delete(Integer id){
        Students students = new Students();
        students.setId(id);
        studentsDao.delete(students);
    }
    

}
