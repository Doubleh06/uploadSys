package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface EmployeeDao extends BaseDao<Employee> {
    @Select("select e.id,e.name,group_concat(m.name separator '|')as m_name,group_concat(m.id separator '|')as m_id from employee e LEFT JOIN employee_machine em on e.id = em.employee_id " +
            "LEFT JOIN machine m on em.machine_id = m.id ${sql} GROUP BY e.id ")
//    @Select("select e.id,e.name,m.name as m_name,m.id as m_id from employee e LEFT JOIN employee_machine em on e.id = em.employee_id LEFT JOIN machine m on em.machine_id = m.id ${sql}")
    List<Map> selectEmployeeList(@Param("sql") String sql);


}
