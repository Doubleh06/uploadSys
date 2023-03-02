package cn.uploadSys.dao;

import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.upload.QczjHQ;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface QczjHQDao extends BaseDao<QczjHQ> {
    @Select("select * from qczj_hq ${sql}")
    List<QczjHQ> getLeadsList(@Param("sql") String sql);

    @Select("select c.id cid,c.*,s.id sid,s.`name` sname,s.phone,s.parents_name,cs.id csid from classes c " +
            "LEFT JOIN classes_students cs on c.id = cs.cid " +
            "LEFT JOIN students s on cs.sid = s.id  WHERE cid = #{id}")
    List<Map> getStudentsByClassesId(@Param("id") Integer id);

    @Select("select name from classes where id = #{id}")
    String getClassNameById(@Param("id") Integer id);

    @Select("select c.name className,c.total,c.teacher,cs.cid,cs.sid,cs.create_time createTime,s.`name` studentName,s.fee " +
            "from classes c RIGHT JOIN classes_students cs on c.id = cs.cid LEFT JOIN students s on cs.sid = s.id ${sql}")
    List<Map> getSignUpList(@Param("sql") String sql);

    @Update("update qczj_hq set distribute_status = #{distributeStatus},modify_time = NOW(),appeal_status = #{appealStatus}" +
            " where cclid = #{cclid}")
    void updateByCclid(@Param("distributeStatus") Integer distributeStatus, @Param("cclid") String cclid, @Param("appealStatus") Integer appealStatus);

    @Update("update qczj_hq set vedio_status = #{vedioStatus},modify_time = NOW()" +
            " where cclid = #{cclid}")
    void updateVedioStatusByCclid(@Param("vedioStatus") Integer vedioStatus, @Param("cclid") String cclid);

    @Select("SELECT * FROM qczj_hq q where TIMESTAMPDIFF(DAY,q.create_time,NOW())<=10 and q.appeal_status=0")
    List<QczjHQ> getUnfinishedInstance();
}
