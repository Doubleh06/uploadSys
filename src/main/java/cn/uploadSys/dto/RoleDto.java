package cn.uploadSys.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author fonlin
 * @date 2018/4/25
 */
public class RoleDto {

    private Integer id;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名不能为空")
    private String name;

    /**
     * 角色key，唯一
     */
    @NotEmpty(message = "角色key不能为空")
    private String roleKey;

    /**
     * 角色是否可用
     */
    private boolean enabled;

    /**
     * 描述
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
