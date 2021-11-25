package cn.uploadSys.dao;

import cn.uploadSys.core.BaseDao;
import cn.uploadSys.entity.StudentsDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface StudentsDetailDao extends BaseDao<StudentsDetail> {
    @Select("select * from students_detail ${sql}")
    List<StudentsDetail> getStudentsDetailList(@Param("sql") String sql);

    @Select("select * from student_detail where id = #{id})")
    StudentsDetail getStudentDetailById(@Param("id")String id);

    @Select("<script>" +
            "insert into students_detail (sid,student_name,cid,class_name,date,week,start_time,end_time,duration,status,reason,create_time,fee) values " +
            "<foreach collection='studentsDetails' index='index' item='item'  separator=',' >" +
            " (" +
            "#{item.sid},#{item.studentName},#{item.cid},#{item.className},#{item.date},#{item.week}," +
            "#{item.startTime},#{item.endTime},#{item.duration},#{item.status},#{item.reason},#{item.createTime},#{item.fee}" +
            ")" +
            "</foreach>" +
            "</script>")
    void insertStudentsDetail(@Param("studentsDetails") List<StudentsDetail> studentsDetails);

    @Delete("delete from students_detail where cid = #{cid} and sid = #{sid}")
    void deleteByCidAndSid(@Param("cid") Integer cid,@Param("sid") Integer sid);

    @Select("select count(*) from students_detail where cid = #{cid} and sid=#{sid} and status = #{status}")
    Integer getCount(@Param("cid")Integer cid,@Param("sid")Integer sid,@Param("status")Integer status);


    @Select("SELECT sd.student_name studentName,sd.class_name className,sd.delete_date deleteDate,sd.reason,sd.fee,c.total from students_detail sd " +
            "LEFT JOIN classes c on sd.cid = c.id ${sql}" )
    List<Map> getDeleteClassList(@Param("sql") String sql);
}
