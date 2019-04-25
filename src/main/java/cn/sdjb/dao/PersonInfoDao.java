package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PersonInfoDao extends BaseDao<PersonInfo> {
    @Select("select * from person_info ${sql}")
    List<PersonInfo> selectAccidentTypeList(@Param("sql") String sql);

    @Select("select name from person_info where id = ${id}")
    String getNameById(@Param("id") Integer id);

    @Select("select * from person_info where name = '${name}'")
    List<PersonInfo> selectAllByName(@Param("name") String name);
}
