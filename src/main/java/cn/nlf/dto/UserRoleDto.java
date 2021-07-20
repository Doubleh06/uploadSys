package cn.nlf.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fonlin
 * @date 2018/5/24
 */
public class UserRoleDto {

    @NotNull(message = "userId不能为null")
    private Integer userId;

    private List<Integer> roleIds;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
