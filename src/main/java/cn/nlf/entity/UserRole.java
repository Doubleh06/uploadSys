package cn.nlf.entity;

import lombok.Data;

/**
 * 用户角色关联表
 *
 * @author fonlin
 * @date 2018/4/20
 */
@Data
public class UserRole {

    private Integer id;

    private Integer userId;

    private Integer roleId;
}
