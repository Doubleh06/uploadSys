package cn.uploadSys.dao;

import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.Classes;
import cn.uploadSys.entity.upload.Qczj;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
