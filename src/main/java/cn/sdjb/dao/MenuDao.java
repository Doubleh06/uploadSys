package cn.sdjb.dao;


import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fonlin
 * @date 2018/4/20
 */
@Component
public interface MenuDao extends BaseDao<Menu> {

    @Select("SELECT m.* FROM menu m " +
            "INNER JOIN role_menu rm on rm.menu_id = m.id " +
            "INNER JOIN user_role ur on ur.role_id = rm.role_id " +
            "WHERE ur.user_id = #{userId} ")
    List<Menu> selectAllByUser(@Param("userId") Integer userId);

    @Select("SELECT m.* FROM menu m " +
            "INNER JOIN role_menu rm on rm.menu_id = m.id " +
            "INNER JOIN user_role ur on ur.role_id = rm.role_id " +
            "WHERE ur.user_id = #{userId}  AND m.enabled = 1")
    List<Menu> selectAllEnabledByUser(@Param("userId") Integer userId);

    @Select("SELECT * FROM menu m INNER JOIN role_menu rm ON m.id = rm.menu_id WHERE rm.role_id = #{roleId}")
    List<Menu> selectAllByRole(@Param("roleId") Integer roleId);

    @Select("<script>" +
            "SELECT id FROM menu WHERE code IN " +
            "<foreach collection='codes' index='index' item='item' open='(' separator=',' close=')'>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<Integer> selectAllIdByCode(@Param("codes") List<String> codes);
}
