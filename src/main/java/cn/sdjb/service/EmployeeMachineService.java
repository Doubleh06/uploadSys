package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.EmployeeDao;
import cn.sdjb.dao.EmployeeMachineDao;
import cn.sdjb.dto.MachineJqGridParam;
import cn.sdjb.dto.PersonMachineJqGridParam;
import cn.sdjb.entity.Employee;
import cn.sdjb.entity.EmployeeMachine;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeMachineService extends AbstractService<EmployeeMachine> {


    @Autowired
    private EmployeeMachineDao employeeMachineDao;
    @Autowired
    private EmployeeService employeeService;

    @Override
    protected BaseDao<EmployeeMachine> getDao() {
        return employeeMachineDao;
    }

    @Override
    protected List<EmployeeMachine> selectByJqGridParam(JqGridParam jqGridParam) {
        PersonMachineJqGridParam param = (PersonMachineJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return employeeMachineDao.selectBySql("employeeMachine",sql.toString());
    }


    public void delete(Integer employeeId){
//    public void isMoreThanOne(Integer employeeId,Integer machineId){
//        Integer count = employeeMachineDao.isMoreThanOne(employeeId);
        EmployeeMachine employeeMachine = new EmployeeMachine();
//        switch (count){
//            case 0:
//                employeeService.delete(employeeId);
//                break;
//            case 1:
//                employeeService.delete(employeeId);
//                employeeMachine.setEmployeeId(employeeId);
//                employeeMachineDao.delete(employeeMachine);
//                break;
//             default:
                 employeeMachine.setEmployeeId(employeeId);
                 employeeMachineDao.delete(employeeMachine);
                 employeeService.delete(employeeId);

//        }
//        return false;
    }
    public void deleteRelationship(Integer employeeId){
        EmployeeMachine employeeMachine = new EmployeeMachine();
        employeeMachine.setEmployeeId(employeeId);
        employeeMachineDao.delete(employeeMachine);
    }

    public List<Map> selectEmployeeAndMachine(Integer employeeId){
        return employeeMachineDao.selectEmployeeAndMachine(employeeId);
    }
}
