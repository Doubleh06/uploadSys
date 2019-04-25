package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.Email;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmailDao extends BaseDao<Email> {
    @Select("select * from email ${sql}")
    List<Email> selectEmailList(@Param("sql") String sql);

    @Select("select name from email where id = ${id}")
    String getNameById(@Param("id") Integer id);

    @Update("update email set is_using = 0 where address = '${address}' and auth_name = '${authName}'")
    void changeEmailByAuthName(@Param("address")String address,@Param("authName")String authName);

    @Update("update email set is_using = 1 where address = '${address}'")
    void changeAllUsingStatus(@Param("address")String address);

    @Select("select * from email where address = '${address}' and is_using = 0")
    Email getChosenEmailByAddress(@Param("address") String address);

    @Update("update email set is_using = 1 where id = '${id}'")
    void closeInUsing(@Param("id")Integer id);

    @Update("update email set is_using = 0 where id = '${id}'")
    void openInUsing(@Param("id")Integer id);

    @Select("select is_using from email where address = '${address}'")
    List<Integer> checkIsUsing(@Param("address") String address);
}
