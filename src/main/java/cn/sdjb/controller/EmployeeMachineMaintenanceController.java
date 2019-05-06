package cn.sdjb.controller;

import cn.sdjb.core.JSONResult;
import cn.sdjb.core.Result;
import cn.sdjb.core.jqGrid.JqGridResult;
import cn.sdjb.dao.MachineDao;
import cn.sdjb.dto.*;
import cn.sdjb.entity.Employee;
import cn.sdjb.entity.EmployeeMachine;
import cn.sdjb.entity.Machine;
import cn.sdjb.service.EmployeeMachineService;
import cn.sdjb.service.EmployeeService;
import cn.sdjb.service.MachineService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/employeeMachine")
public class EmployeeMachineMaintenanceController extends BaseController {

    @Autowired
    private MachineService machineService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMachineService employeeMachineService;
    @Autowired
    private MachineDao machineDao;

    @RequestMapping(value = "/machine/list")
    public String machineList(Model model){
        model.addAttribute("menus", getMenus("machine"));
        return "/employeeMachine/machineList";
    }

    @RequestMapping(value = "/machine/grid")
    @ResponseBody
    public Result machineGrid(MachineJqGridParam param) {
        PageInfo<Machine> pageInfo = machineService.selectByJqGridParam(param);
        JqGridResult<Machine> result = new JqGridResult<>();
        //当前页
        result.setPage(pageInfo.getPageNum());
        //数据总数
        result.setRecords(pageInfo.getTotal());
        //总页数
        result.setTotal(pageInfo.getPages());
        //当前页数据
        result.setRows(pageInfo.getList());
        return new JSONResult(result);
    }

    @RequestMapping("/machine/insert")
    @ResponseBody
    public Result machineInsert(@Valid @RequestBody MachineDto machineDto) {
        Machine machine = new Machine();
        BeanUtils.copyProperties(machineDto,machine );
        machineService.insert(machine);
        return OK;
    }

    @RequestMapping("/machine/delete")
    @ResponseBody
    public Result machineDelete(@RequestParam Integer id) {
        machineService.delete(id);
        return OK;
    }
    @RequestMapping("/machine/get")
    @ResponseBody
    public Result machineGet(@RequestParam Integer id) {
        return new JSONResult(machineService.select(id));
    }

    @RequestMapping("/machine/update")
    @ResponseBody
    public Result machineUpdate(@Valid @RequestBody MachineDto machineDto) {
        Machine machine = new Machine();
        BeanUtils.copyProperties(machineDto,machine );
        machineService.updateSelective(machine);
        return OK;
    }

/*****************************************************人员********************************************************************/
    @RequestMapping(value = "/employee/list")
    public String employeeList(Model model){
        model.addAttribute("menus", getMenus("employee"));
        model.addAttribute("machines", machineService.selectAll());
        return "/employeeMachine/employeeList";
    }

    @RequestMapping(value = "/employee/grid")
    @ResponseBody
    public Result employeeGrid(EmployeeJqGridParam param) {
        PageInfo<Map> pageInfo = employeeService.selectByJqGridParam(param);
        JqGridResult<Map> result = new JqGridResult<>();
        //当前页
        result.setPage(pageInfo.getPageNum());
        //数据总数
        result.setRecords(pageInfo.getTotal());
        //总页数
        result.setTotal(pageInfo.getPages());
        //当前页数据
        result.setRows(pageInfo.getList());
        return new JSONResult(result);
    }
    @RequestMapping("/employee/insert")
    @ResponseBody
    public Result employeeInsert(@RequestBody JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray machineIds = jsonObject.getJSONArray("machineName");
        //保存employee表
        Employee employee = new Employee(name);
        employeeService.insert(employee);
        //保存employee-machine表
        for (Object  machineId : machineIds){
            EmployeeMachine employeeMachine = new EmployeeMachine(employee.getId(),Integer.parseInt(machineId.toString()));
            employeeMachineService.insert(employeeMachine);
        }
        return OK;
    }
    @RequestMapping("/employee/delete")
    @ResponseBody
    public Result employeeDelete(@RequestParam Integer employeeId) {
        employeeMachineService.delete(employeeId);
        return OK;
    }

    @RequestMapping("/employee/get")
    @ResponseBody
    public Result employeeGet(@RequestParam Integer id) {
        //传入员工id，传出员工id，员工姓名，机床id，机床号
        List<Map> noSelectedMachine = machineDao.selectNoSelectedMachine(id);
        List<Map> lists = employeeMachineService.selectEmployeeAndMachine(id);
        return new JSONResult(lists,noSelectedMachine);
    }

    @RequestMapping("/employee/update")
    @ResponseBody
    public Result employeeUpdate(@RequestBody JSONObject jsonObject) {
        Integer employeeId = jsonObject.getInteger("employeeId");
        String name = jsonObject.getString("name");
        JSONArray machineIds = jsonObject.getJSONArray("machineName");
        //保存employee表
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName(name);
        employeeService.updateSelective(employee);
        employeeMachineService.deleteRelationship(employeeId);
        //保存employee-machine表
        for (Object  machineId : machineIds){
            EmployeeMachine employeeMachine = new EmployeeMachine(employee.getId(),Integer.parseInt(machineId.toString()));
            employeeMachineService.insert(employeeMachine);
        }
        return OK;
    }
}
