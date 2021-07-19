package cn.nlf.security;


import cn.nlf.entity.Menu;
import cn.nlf.entity.Role;
import cn.nlf.entity.security.ActivableMenu;
import cn.nlf.entity.security.Resources;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * spring security的user
 *
 * @author fonlin
 * @date 2018/4/20
 */
public class UserDetail extends User {

    private Integer id;

    private String nickname;


    private List<Role>  roles;


    /**
     * 所有菜单
     */
    private List<Menu> all;

    /**
     * 左侧菜单，有active状态
     */
    private List<ActivableMenu> activableMenus;

    /**
     * 对应每个页面上的按钮等资源
     */
    private List<Resources> resourcesList;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ActivableMenu> getActivableMenus() {
        return activableMenus;
    }

    public void setActivableMenus(List<ActivableMenu> activableMenus) {
        this.activableMenus = activableMenus;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Menu> getAll() {
        return all;
    }

    public void setAll(List<Menu> all) {
        this.all = all;
    }

    public List<Resources> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(List<Resources> resourcesList) {
        this.resourcesList = resourcesList;
    }

    @Override
    public void eraseCredentials() {
        //do nothing
    }
}
