package cn.uploadSys.dto;


import cn.uploadSys.core.jqGrid.JqGridParam;

/**
 * @author fonlin
 * @date 2018/4/25
 */
public class RoleJqGridParam extends JqGridParam {

    private String name;

    private String roleKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }
}
