package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.Employee;
import cn.sdjb.entity.Machine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface MachineDao extends BaseDao<Machine> {
    @Select("select * from Machine ${sql}")
    List<Machine> selectMachineList(@Param("sql") String sql);

    @Select("select * from machine m where m.id NOT in (select em.machine_id from employee_machine em where em.employee_id = #{employeeId})")
    List<Map> selectNoSelectedMachine(@Param("employeeId")Integer employeeId);
    
}
