package cn.uploadSys.dao;

import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.Classes;
import cn.uploadSys.entity.upload.Qczj;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface QczjDao extends BaseDao<Qczj> {
    @Select("select * from qczj ${sql}")
    List<Qczj> getStudentsList(@Param("sql") String sql);

    @Select("select c.id cid,c.*,s.id sid,s.`name` sname,s.phone,s.parents_name,cs.id csid from classes c " +
            "LEFT JOIN classes_students cs on c.id = cs.cid " +
            "LEFT JOIN students s on cs.sid = s.id  WHERE cid = #{id}")
    List<Map> getStudentsByClassesId(@Param("id") Integer id);

    @Select("select name from classes where id = #{id}")
    String getClassNameById(@Param("id") Integer id);

    @Select("select c.name className,c.total,c.teacher,cs.cid,cs.sid,cs.create_time createTime,s.`name` studentName,s.fee " +
            "from classes c RIGHT JOIN classes_students cs on c.id = cs.cid LEFT JOIN students s on cs.sid = s.id ${sql}")
    List<Map> getSignUpList(@Param("sql") String sql);

    @Update("update qczj set status = #{status},modify_time = NOW(),message = #{message}" +
            " where cclid = #{cclid}")
    void updateByCclid(@Param("status") Integer status,@Param("cclid")String cclid,@Param("message")String message);

    @Select("SELECT * FROM qczj q where TIMESTAMPDIFF(DAY,q.create_time,NOW())<=45 and q.status not in (1,20)")
    List<Qczj> getUnfinishedInstance();
}
