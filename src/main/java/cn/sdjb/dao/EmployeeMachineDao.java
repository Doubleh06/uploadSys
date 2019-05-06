package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.EmployeeMachine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface EmployeeMachineDao extends BaseDao<EmployeeMachine> {
    @Select("select count(*) from employee_machine where employee_id = #{id}")
    Integer isMoreThanOne(@Param("id")Integer id);

    @Select("SELECT e.id as employeeId ,e.name as employeeName,m.id as machineId,m.name as machineName" +
            "  from employee_machine em LEFT JOIN employee e on em.employee_id = e.id LEFT JOIN machine m on em.machine_id = m.id where em.employee_id = #{employeeId}")
    List<Map> selectEmployeeAndMachine(@Param("employeeId")Integer employeeId);

}