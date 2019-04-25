package cn.sdjb.controller.maintenanceController;

import cn.sdjb.controller.BaseController;
import cn.sdjb.core.JSONResult;
import cn.sdjb.core.Result;
import cn.sdjb.core.jqGrid.JqGridResult;
import cn.sdjb.dto.PersonInfoJqGridParam;
import cn.sdjb.entity.PersonInfo;
import cn.sdjb.service.PersonInfoService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/maintenance/personInfo")
public class PersonInfoMaintenanceController extends BaseController {

    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "/list")
    public String deptList(Model model){
        model.addAttribute("menus", getMenus("personInfo"));
        return "/maintenance/personInfoList";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid(PersonInfoJqGridParam param) {
        PageInfo<PersonInfo> pageInfo = personInfoService.selectByJqGridParam(param);
        JqGridResult<PersonInfo> result = new JqGridResult<>();
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

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(@RequestBody JSONObject dto) {
        String userId = dto.getString("userId");
        String name = dto.getString("name");
        String email = dto.getString("email");
        PersonInfo personInfo = new PersonInfo(userId,name,email);
        personInfoService.insert(personInfo);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        personInfoService.delete(id);
        return OK;
    }
    @RequestMapping("/get")
    @ResponseBody
    public Result get(@RequestParam Integer id) {
        return new JSONResult(personInfoService.select(id));
    }
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid @RequestBody JSONObject dto) {
        Integer id = dto.getInteger("id");
        String userId = dto.getString("userId");
        String name = dto.getString("name");
        String email = dto.getString("email");
        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(id);
        personInfo.setUserId(userId);
        personInfo.setName(name);
        personInfo.setEmail(email);
        personInfoService.update(personInfo);
        return OK;
    }
}
