package cn.sdjb.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EhsDto {
    private Integer accidentType;
    private String date;
    private String time;
    private String accidentMan;
    private String dept;
    private String accidentPlace;
    private String accidentSituation;
    private String reportMan;
    private String uuid;
    private String address;
    private String workNum;

}
