package cn.nlf.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Students extends BaseEntity {

    private String name;
    private String phone;
    private String school;
    private String parentsName;
    private BigDecimal fee;


}
