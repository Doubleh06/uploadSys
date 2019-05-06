package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.EmployeeDao;
import cn.sdjb.dao.MachineDao;
import cn.sdjb.dto.EmployeeJqGridParam;
import cn.sdjb.dto.MachineJqGridParam;
import cn.sdjb.dto.PersonMachineJqGridParam;
import cn.sdjb.entity.Employee;
import cn.sdjb.entity.Machine;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService extends AbstractService<Employee> {


    @Autowired
    private EmployeeDao employeeDao;

    @Override
    protected BaseDao<Employee> getDao() {
        return employeeDao;
    }

    @Override
    protected List<Employee> selectByJqGridParam(JqGridParam jqGridParam) {
        PersonMachineJqGridParam param = (PersonMachineJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return employeeDao.selectBySql("employee",sql.toString());
    }


    public PageInfo<Map> selectByJqGridParam(EmployeeJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and  e.name like  '%").append(param.getName()).append("%'");
        }
        return new PageInfo<>(employeeDao.selectEmployeeList(sql.toString()));
    }

    public void delete(Integer id){
        Employee employee = new Employee();
        employee.setId(id);
        employeeDao.delete(employee);
    }
    

}
