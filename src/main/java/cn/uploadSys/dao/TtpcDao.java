package cn.uploadSys.dao;


import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.ttpc.SignUp;
import cn.uploadSys.entity.upload.Qczj;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface TtpcDao extends BaseDao<SignUp> {
    @Select("select * from sign_up ${sql}")
    List<SignUp> getLeadsList(@Param("sql") String sql);

    @Update("update sign_up set status = #{status},modify_time = NOW(),message = #{message}" +
            " where cclid = #{cclid}")
    void updateByCclid(@Param("status") Integer status, @Param("cclid") String cclid, @Param("message") String message);

    @Update("update sign_up set vedio_status = #{vedioStatus},modify_time = NOW()" +
            " where cclid = #{cclid}")
    void updateVedioStatusByCclid(@Param("vedioStatus") Integer vedioStatus, @Param("cclid") String cclid);

    @Select("SELECT * FROM sign_up q where TIMESTAMPDIFF(DAY,q.create_time,NOW())<=45 and q.status not in (1,20)")
    List<SignUp> getUnfinishedInstance();
}
