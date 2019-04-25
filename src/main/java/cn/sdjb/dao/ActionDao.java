package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.Action;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ActionDao extends BaseDao<Action> {

    @Select("select * from action ${sql}")
    List<Action> selectActionList(@Param("sql") String sql);

    @Select("select * from action ${sql}")
    Action selectAction(@Param("sql") String sql);

    @Select("select img_url from action where id = ${id}")
    String selectImgUrlById(@Param("id") Integer id);

    @Select("select count(*) from action where ehs_id = ${id} ")
    Integer getTotalById(@Param("id")Integer id);



}
