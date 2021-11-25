package cn.uploadSys.dao;

import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.ClassesStudents;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClassesStudentsDao extends BaseDao<ClassesStudents> {

    @Delete("delete from classes_students where cid = #{cid} and sid = #{sid}")
    void deleteByCidAndSid(@Param("cid") Integer cid,@Param("sid") Integer sid);

    @Select("select * from classes_students where cid = #{cid} and sid = #{sid}")
    List<ClassesStudents> isSignUp(@Param("cid") Integer cid,@Param("sid") Integer sid);

    @Select("SELECT COUNT(*) from classes_students WHERE cid = #{cid}")
    Integer getTotalByCid(@Param("cid")Integer cid);
}
