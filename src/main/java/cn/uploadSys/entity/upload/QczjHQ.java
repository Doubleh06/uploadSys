package cn.uploadSys.entity.upload;

import cn.uploadSys.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.math3.genetics.Fitness;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dh
 * @version 1.0
 * @description: TODO
 * @date 2021/11/29 下午12:45
 */
@Data
@Table(name = "qczj_hq")
public class QczjHQ extends BaseEntity {
    private String mobile;
    private Integer pid;
    private Integer cid;
    private Integer countyid;
    private String brandid;
    private String seriesid;
    private String specid;
    private String firstregtime;
    private String platenum;
    private BigDecimal mileage;
    private String appid;
    private Integer status;//0:成功 1：失败
    private String cclid;
    private Date createTime;
    private Date modifyTime;
    private Integer distributeStatus;
    private Integer appealStatus;
    private String message;
}
