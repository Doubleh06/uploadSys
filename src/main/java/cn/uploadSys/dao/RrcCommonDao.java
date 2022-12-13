package cn.uploadSys.dao;

import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.renrenche.Rrc;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RrcCommonDao extends BaseDao<Rrc> {
    @Select("select * from rrc ${sql}")
    List<Rrc> getLeadsList(@Param("sql") String sql);

}
