package cn.nlf.entity;


import lombok.Data;

/**
 * 菜单资源类（菜单及页面上的按钮链接等资源）
 *
 * @author fonlin
 * @date 2018/4/19
 */
@Data
public class Menu extends BaseEntity {

    //url
    private String url;

    //名称
    private String name;

    //排序
    private Integer sequence;

    //key，唯一
    private String code;

    //图标
    private String icon;

    //描述
    private String description;

    //上级菜单id
    private String pcode;

    //层级
//    private Integer level;

    //类型 1-菜单 0-不是菜单
    private Integer type;

    //是否启用
    private Boolean enabled;

    //是否显示
    private Boolean displayed;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return getId() != null ? getId().equals(menu.getId()) : menu.getId() == null;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }


}
