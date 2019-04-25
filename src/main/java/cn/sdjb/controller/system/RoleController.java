package cn.sdjb.controller.system;


import cn.sdjb.controller.BaseController;
import cn.sdjb.core.BusinessException;
import cn.sdjb.core.ErrorCode;
import cn.sdjb.core.JSONResult;
import cn.sdjb.core.Result;
import cn.sdjb.core.jqGrid.JqGridResult;
import cn.sdjb.dto.DtoTransfer;
import cn.sdjb.dto.PermissionDto;
import cn.sdjb.dto.RoleDto;
import cn.sdjb.dto.RoleJqGridParam;
import cn.sdjb.entity.Menu;
import cn.sdjb.entity.Role;
import cn.sdjb.service.MenuService;
import cn.sdjb.service.RoleService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

/**
 * @author fonlin
 * @date 2018/4/25
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("menus", getMenus("role"));
        return "/system/role/list";
    }

    @RequestMapping("/grid")
    @ResponseBody
    public Result grid(RoleJqGridParam param) {
        PageInfo<Role> pageInfo =  roleService.selectByPage(param);
        JqGridResult<Role> result = new JqGridResult<>();
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
    public Result insert(@Valid @RequestBody RoleDto dto) {
        if (roleService.select("roleKey", dto.getRoleKey()) != null) {
            throw new BusinessException(ErrorCode.ROLE_KEY_EXIST);
        }
        Role role = DtoTransfer.roleDto2Entity(dto);
        roleService.insert(role);
        return OK;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam Integer id) {
        roleService.deleteRole(id);
        return OK;
    }

    @RequestMapping("/get")
    @ResponseBody
    public Result get(@RequestParam Integer id) {
        return new JSONResult(roleService.select(id));
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid @RequestBody RoleDto dto) {
        Role role = DtoTransfer.roleDto2Entity(dto);
        roleService.update(role);
        return OK;
    }

    @RequestMapping("/permission")
    @ResponseBody
    public Result permission(@RequestParam Integer roleId) {
        List<Menu> all = menuService.selectAll();
        all.sort(Comparator.comparingInt(Menu::getSequence));
        List<Menu> selected = menuService.selectAllByRole(roleId);
        JSONArray jsonArray = new JSONArray();
        for (Menu menu : all) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", menu.getCode());
            jsonObject.put("parent", StringUtils.isEmpty(menu.getPcode()) ? "#" : menu.getPcode());
            jsonObject.put("text", menu.getName());
            jsonObject.put("icon", StringUtils.isNotEmpty(menu.getIcon()) ? "fa " + menu.getIcon() : "");
            JSONObject state = new JSONObject();
            state.put("opened", true);
            state.put("selected", !hasChildren(all, menu) && selected.contains(menu));
            jsonObject.put("state", state);
            jsonArray.add(jsonObject);
        }
        return new JSONResult(jsonArray);
    }

    private boolean hasChildren(List<Menu> all, Menu parent) {
        for (Menu menu : all) {
            if (parent.getCode().equals(menu.getPcode())) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/permission/save", method = RequestMethod.POST)
    @ResponseBody
    public Result savePermission(@Valid @RequestBody PermissionDto permissionDto) {
        roleService.savePermission(permissionDto.getRoleId(), permissionDto.getCodes());
        return OK;
    }
}
