package cn.nlf.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StudentsDetail extends BaseEntity {

    private Integer sid;
    private String studentName;
    private Integer cid;
    private String className;
    private Date date;
    private Integer week;
    private String startTime;
    private String endTime;
    private BigDecimal duration;
    private Integer status;   //0--未上课     1--已上课
    private String spare;
    private Integer reason; //消课理由   1、常规课， 2、比赛 ，3、考级，4、补课，5、特色课程 6、其他
    private Date createTime;
    private Date deleteDate;
    private BigDecimal fee;




}
