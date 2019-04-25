package cn.sdjb.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fonlin
 * @date 2018/5/22
 */
public class PermissionDto {

    @NotNull
    private Integer roleId;

    private List<String> codes;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}
