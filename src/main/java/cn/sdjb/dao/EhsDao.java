package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.Ehs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface EhsDao extends BaseDao<Ehs> {

    @Select("select * from ehs ${sql}")
    List<Map> selectEhsList(@Param("sql") String sql);

    @Select("select img_url from ehs where id = ${id}")
    String selectImgUrlById(@Param("id") Integer id);

}
