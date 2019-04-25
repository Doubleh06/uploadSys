package cn.sdjb.dao;

import cn.sdjb.core.BaseDao;
import cn.sdjb.entity.Image;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.special.InsertListMapper;

import java.util.List;

@Component
public interface ImageDao extends BaseDao<Image>, InsertListMapper<Image> {
    @Delete("delete from image where img_source_name = '${imgSourceName}' and uuid = '${uuid}'")
    void deleteFile(@Param("imgSourceName")String imgSourceName,@Param("uuid")String uuid);

    @Select("select img_name from image where img_source_name = '${imgSourceName}' and uuid = '${uuid}'")
    String selectImgName(@Param("imgSourceName")String imgSourceName,@Param("uuid")String uuid);

    @Select("select img_name,img_source_name from image where uuid = '${uuid}'")
    List<Image> selectImgSourceName(@Param("uuid")String uuid);
}
