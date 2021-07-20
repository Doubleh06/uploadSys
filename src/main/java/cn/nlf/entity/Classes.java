package cn.nlf.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Classes extends BaseEntity {

    private String name;
    private BigDecimal duration;
    private String startTime;
    private String endTime;
    private Integer week;
    private String teacher;
    private Integer total;
    private BigDecimal fee;

}
