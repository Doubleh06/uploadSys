package cn.sdjb.dao;


import cn.sdjb.core.BaseDao;
import cn.sdjb.dto.UserDto;
import cn.sdjb.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author fonlin
 * @date 2018/4/19
 */
@Component
public interface UserDao extends BaseDao<User> {

    @Select("select * from user where username = #{username}")
    User findOneByUsername(@Param("username") String username);

    @Select("select *   from user u   where ${sql}")
    List<Map> selectBySql2(@Param("sql") String sql);

    @Select("select *,uc.company_id as company from user u left join user_company uc on u.id = uc.user_id  where u.id = #{id} ")
    UserDto getJoinUserCompany(@Param("id") Integer id);


}
