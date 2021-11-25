package cn.uploadSys.service;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.StudentsDao;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.entity.Students;
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
        if (-1==param.getCid()){
            return new PageInfo<>(studentsDao.getStudentsList(sql.toString()));
        }
        if(null != param.getCid()) {
            sql.append(" and  c.id = ").append(param.getCid());
        }
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and  s.name like  '%").append(param.getName()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and s.phone like '%").append(param.getPhone()).append("%'");
        }
//
        return new PageInfo<>(studentsDao.getStudentsByCid(sql.toString()));
    }

    public void delete(Integer id){
        Students students = new Students();
        students.setId(id);
        studentsDao.delete(students);
    }
    

}
