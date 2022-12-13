package cn.uploadSys.entity.renrenche;

import cn.uploadSys.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author dhao
 * @Description
 * @Date 2022/12/13 
 * @Param  * @param null
 * @return 
 **/
@Data
public class Rrc {
    private String id;
    private String name;
    private String mobile;
    private String city;
    private String brand;
    private String series;
    private String model;
    private String kilometer;
    private Integer licensedDateYear;
    private char isOperation;
    private Integer seatNumber;
    private char isAccidented;
    private String renrencheInfoId;
    private Integer isRepeat;//0:true 1:false
    private Integer status;
    private Date createTime;
}
