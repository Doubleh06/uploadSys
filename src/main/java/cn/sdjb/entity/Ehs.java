package cn.sdjb.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Ehs extends BaseEntity {
    private Integer accidentType;
    private Date accidentTime;
    private String accidentMan;
    private String dept;
    private String accidentPlace;
    private String accidentSituation;
    private String reportMan;
    private String imgUrl;
    private String uuid;
    private Date submitTime;
    private String address;
    private Integer status;
    private Date dealTime;
    private Integer accidentLevel;
    private String workNum;

}
