package cn.nlf.entity;


import lombok.Data;

/**
 * 角色
 *
 * @author fonlin
 * @date 2018/4/20
 */
@Data
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色key，唯一
     */
    private String roleKey;

    /**
     * 角色是否可用
     * todo 为什么只有在用包装类时mybatis才会识别这个字段
     */
    private Boolean enabled = true;

    /**
     * 描述
     */
    private String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return getId() != null ? getId().equals(role.getId()) : role.getId() == null;
    }
}
