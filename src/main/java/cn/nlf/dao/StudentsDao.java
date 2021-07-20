package cn.nlf.dao;

import cn.nlf.core.BaseDao;
import cn.nlf.entity.Students;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface StudentsDao extends BaseDao<Students> {
    @Select("select * from students ${sql}")
    List<Students> getStudentsList(@Param("sql") String sql);


    @Select("select s.* from students s RIGHT JOIN classes_students cs on s.id = cs.sid RIGHT JOIN classes c on c.id = cs.cid ${sql}")
    List<Students> getStudentsByCid(@Param("sql") String sql);

    @Select("select * from SignUp m where m.id NOT in (select em.machine_id from employee_machine em where em.employee_id = #{employeeId})")
    List<Map> selectNoSelectedMachine(@Param("employeeId")Integer employeeId);
    
}
