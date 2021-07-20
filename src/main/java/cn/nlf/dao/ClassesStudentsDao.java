package cn.nlf.dao;

import cn.nlf.core.BaseDao;
import cn.nlf.entity.ClassesStudents;
import cn.nlf.entity.Students;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
public interface ClassesStudentsDao extends BaseDao<ClassesStudents> {

    @Delete("delete from classes_students where cid = #{cid} and sid = #{sid}")
    void deleteByCidAndSid(@Param("cid") Integer cid,@Param("sid") Integer sid);

    @Select("select * from classes_students where cid = #{cid} and sid = #{sid}")
    List<ClassesStudents> isSignUp(@Param("cid") Integer cid,@Param("sid") Integer sid);

    @Select("SELECT COUNT(*) from classes_students WHERE cid = #{cid}")
    Integer getTotalByCid(@Param("cid")Integer cid);
}
