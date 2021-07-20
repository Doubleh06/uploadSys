package cn.nlf.security;


import cn.nlf.entity.Menu;
import cn.nlf.entity.security.ActivableMenu;
import cn.nlf.entity.security.Constants;
import cn.nlf.entity.security.Resource;
import cn.nlf.entity.security.Resources;
import cn.nlf.service.MenuService;
import cn.nlf.util.MenuTreeBuilder;
import cn.nlf.util.SpringContextUtil;
import cn.nlf.util.SpringSecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Override
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        super.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserDetail userDetail = SpringSecurityUtil.getUser();
        //如果是初始密码跳到修改密码页

        if (new BCryptPasswordEncoder().matches(Constants.DEFAULT_PASSWORD,userDetail.getPassword())){
            getRedirectStrategy().sendRedirect(request, response, "/initPassword");
        } else {
            MenuService menuService = SpringContextUtil.getBean(MenuService.class);
            //所有有权限看到的菜单（包括按钮等）
            List<Menu> all;
            //如果是超级管理员
            if (Constants.SUPER_ADMIN.equals(userDetail.getUsername())) {
                all = menuService.selectAll();
            } else {
                all = menuService.selectAllEnabledByUser(userDetail.getId());
            }
            //去重
            all = new ArrayList<>(new HashSet<>(all));
            //构造菜单树
            List<ActivableMenu> activableMenus = new MenuTreeBuilder(all).buildMenuTree();
            //获取页面对应的资源列表
            List<Resources> resourcesList = getResourcesList(all, activableMenus);
            userDetail.setActivableMenus(activableMenus);
            userDetail.setAll(all);
            userDetail.setResourcesList(resourcesList);
            request.getSession().setAttribute("nickname", userDetail.getNickname());
            request.getSession().setAttribute("id", userDetail.getId());
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private List<Resources> getResourcesList(List<Menu> all, List<ActivableMenu> activableMenus) {
        List<Resources> resourcesList = new ArrayList<>();
        for (ActivableMenu menu : activableMenus) {
            //如果不是父级菜单
            if (!"#".equals(menu.getUrl())) {
                resourcesList.add(getResources(all, menu));
            } else {
                //如果是父级菜单，循环子菜单
                //注意，这里可以这么写是因为系统只支持两级菜单
                for (ActivableMenu m : menu.getChildren()) {
                    resourcesList.add(getResources(all, m));
                }
            }
        }

        return resourcesList;
    }

    private Resources getResources(List<Menu> all, ActivableMenu m) {
        List<Resource> resourceList = getResourceList(all, m.getCode());
        Resources resources = new Resources();
        resources.setDomain(m.getCode());
        resources.setResources(resourceList);
        return resources;
    }

    private List<Resource> getResourceList(List<Menu> all, String pcode) {
        List<Resource> resourceList = new ArrayList<>();
        for (Menu menu : all) {
            if (pcode.equals(menu.getPcode())) {
                Resource resource = new Resource();
                resource.setCode(menu.getCode());
                resource.setName(menu.getName());
                resourceList.add(resource);
            }
        }
        return resourceList;
    }


}
