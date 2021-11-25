package cn.uploadSys.controller.system;

import cn.uploadSys.controller.BaseController;
import cn.uploadSys.core.BusinessException;
import cn.uploadSys.core.ErrorCode;
import cn.uploadSys.core.JSONResult;
import cn.uploadSys.core.Result;
import cn.uploadSys.dto.DtoTransfer;
import cn.uploadSys.dto.MenuDto;
import cn.uploadSys.entity.Menu;
import cn.uploadSys.service.MenuService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

/**
 * @author fonlin
 * @date 2018/5/2
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("menus", getMenus("menu"));
        return "/system/menu/list";
    }

    @RequestMapping("/grid")
    @ResponseBody
    public List<Menu> grid() {
        List<Menu> menus = menuService.selectAll();
        menus.sort(Comparator.comparingInt(Menu::getSequence));
        return menus;
    }

    @RequestMapping("/jstree")
    @ResponseBody
    public Result jstree() {
        List<Menu> all = menuService.selectAll();
        all.sort(Comparator.comparingInt(Menu::getSequence));
        JSONArray jsonArray = new JSONArray();
        for (Menu menu : all) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", menu.getCode());
            jsonObject.put("parent", StringUtils.isEmpty(menu.getPcode()) ? "#" : menu.getPcode());
            jsonObject.put("text", menu.getName());
            jsonObject.put("icon", StringUtils.isNotEmpty(menu.getIcon()) ? "fa " + menu.getIcon() : "");
            JSONObject state = new JSONObject();
            state.put("opened", false);
            state.put("selected", false);
            jsonObject.put("state", state);
            jsonArray.add(jsonObject);
        }
        return new JSONResult(jsonArray);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(@Valid @RequestBody MenuDto dto) {
        if (menuService.select("code", dto.getName()) != null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "code不允许重复");
        }
        Menu menu = DtoTransfer.menuDto2Entity(dto);
        menuService.insert(menu);
        return OK;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Result get(@RequestParam Integer id) {
        return new JSONResult(menuService.select(id));
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid @RequestBody MenuDto dto) {
        Menu menu = DtoTransfer.menuDto2Entity(dto);
        menuService.updateSelective(menu);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        menuService.deleteMenu(id);
        return OK;
    }
}
