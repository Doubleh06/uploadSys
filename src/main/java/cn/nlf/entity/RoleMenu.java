package cn.nlf.entity;

import lombok.Data;

/**
 * 角色菜单关联表
 *
 * @author fonlin
 * @date 2018/4/20
 */

@Data
public class RoleMenu {

    private Integer id;

    private Integer roleId;

    private Integer menuId;

}
