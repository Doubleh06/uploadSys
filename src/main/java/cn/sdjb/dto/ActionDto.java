package cn.sdjb.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ActionDto {
    private String descriptive;
    private String address;
    private Integer ehsId;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector;
    private String closeDate;
    private String closeTime;
    private String imgUrl;
    private String uuid;
    private String email;
    private String directorEmail;

}
