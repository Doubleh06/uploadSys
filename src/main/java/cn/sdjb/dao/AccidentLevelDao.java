package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.AccidentLevel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccidentLevelDao extends BaseDao<AccidentLevel> {
    @Select("select * from accident_level ${sql}")
    List<AccidentLevel> selectAccidentTypeList(@Param("sql") String sql);

    @Select("select name from accident_level where id = ${id}")
    String getNameById(@Param("id") Integer id);


}
