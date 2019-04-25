package cn.sdjb.controller.maintenanceController;

import cn.sdjb.controller.BaseController;
import cn.sdjb.core.JSONResult;
import cn.sdjb.core.Result;
import cn.sdjb.core.jqGrid.JqGridResult;
import cn.sdjb.dto.AccidentTypeJqGridParam;
import cn.sdjb.entity.AccidentLevel;
import cn.sdjb.service.AccidentLevelService;
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
@RequestMapping(value = "/maintenance/accidentLevel")
public class AccidentLevelMaintenanceController extends BaseController {

    @Autowired
    private AccidentLevelService accidentLevelService;

    @RequestMapping(value = "/list")
    public String deptList(Model model){
        model.addAttribute("menus", getMenus("accidentLevel"));
        return "/maintenance/accidentLevelList";
    }

    @RequestMapping(value = "/grid")
    @ResponseBody
    public Result grid(AccidentTypeJqGridParam param) {
        PageInfo<AccidentLevel> pageInfo = accidentLevelService.selectByJqGridParam(param);
        JqGridResult<AccidentLevel> result = new JqGridResult<>();
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
    public Result insert(@Valid @RequestBody JSONObject dto) {
        String name = dto.getString("name");
        AccidentLevel accidentLevel = new AccidentLevel(name);
        accidentLevelService.insert(accidentLevel);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        accidentLevelService.delete(id);
        return OK;
    }
    @RequestMapping("/get")
    @ResponseBody
    public Result get(@RequestParam Integer id) {
        return new JSONResult(accidentLevelService.select(id));
    }
    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid @RequestBody JSONObject dto) {
        Integer id = dto.getInteger("id");
        String name = dto.getString("name");
        AccidentLevel accidentLevel = new AccidentLevel();
        accidentLevel.setId(id);
        accidentLevel.setName(name);
        accidentLevelService.update(accidentLevel);
        return OK;
    }
}
