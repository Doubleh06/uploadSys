package cn.uploadSys.entity.ttpc;

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
public class SignUp extends BaseEntity {
    private String name;
    private String mobile;
    private String city;
    private String brand;
    private String family;
    private String source;
    private String utmSource;
    private String license;//1 或者 2（1代表08年之前 | 2代表 08后）
    private String remark;
    private Integer status;
    private Date createTime;
    private Date modifyTime;
    private String message;
    private Integer responseId;

}
