package cn.uploadSys.dao;


import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fonlin
 * @date 2018/4/20
 */
@Component
public interface RoleDao extends BaseDao<Role> {

    @Select("SELECT * FROM role WHERE id IN (SELECT role_id FROM role_menu WHERE menu_id = #{menuId})")
    List<Role> findAllByMenu(@Param("menuId") Integer menuId);

    @Select("SELECT * FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = #{userId})")
    List<Role> selectAllByUser(@Param("userId") Integer userId);

    @Select("SELECT * FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = #{userId}) and enabled = 1  ")
    List<Role> selectRoleByUser(@Param("userId") Integer userId);

//    @Select("SELECT * FROM role WHERE id IN (SELECT role_id FROM user_role WHERE user_id = #{userId}) and enabled = 1 and deleted = 0 ")
    @Select("select * from role where id in (select role_id from user_role where user_id = ${id} ) and enabled = 1 ")
    Role getRoleById(@Param("id") Integer id);
}
