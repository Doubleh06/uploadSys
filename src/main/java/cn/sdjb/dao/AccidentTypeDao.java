package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.AccidentType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccidentTypeDao extends BaseDao<AccidentType> {
    @Select("select * from accident_type ${sql}")
    List<AccidentType> selectAccidentTypeList(@Param("sql") String sql);

    @Select("select name from accident_type where id = ${id}")
    String getNameById (@Param("id")Integer id);


}
