package cn.uploadSys.entity.upload;

import cn.uploadSys.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author dh
 * @version 1.0
 * @description: TODO
 * @date 2021/11/29 下午12:45
 */
@Data
public class Qczj extends BaseEntity {
    private String cityCode;
    private String cityName;
    private String province;
    private String phone;
    private String brandId;
    private String brandName;
    private String carId;
    private String carName;
    private String carSeriesId;
    private String carSeriesName;
    private String km;
    private Date firstregtime;
    private String uid;
    private Date createTime;
    private Date modifyTime;
}
