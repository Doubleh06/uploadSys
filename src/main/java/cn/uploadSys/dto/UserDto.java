package cn.uploadSys.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author fonlin
 * @date 2018/4/25
 */
public class UserDto {

    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    private String permission;

    private Integer company;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }
}
