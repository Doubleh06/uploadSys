package cn.sdjb.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Action extends BaseEntity {
    private Integer ehsId;
    private String descriptive;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector;
    private Date closeTime;
    private Date RealCloseTime;
    private String uuid;
    private String imgUrl;
    private String address;
    private String email;
    private String directorEmail;
    private String closeReason;

}
