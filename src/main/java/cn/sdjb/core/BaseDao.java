package cn.sdjb.core;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author fonlin
 * @date 2018/4/20
 */
public interface BaseDao<T> extends Mapper<T>, MySqlMapper<T> {

    @Select("select * from ${tableName} where ${sql}")
    List<T> selectBySql(@Param("tableName") String tableName, @Param("sql") String sql);



}
