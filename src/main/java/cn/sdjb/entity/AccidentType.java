package cn.sdjb.entity;

import lombok.Data;

@Data
public class AccidentType extends BaseEntity {
    private String name;


    public AccidentType() {
    }

    public AccidentType(String name) {
        super();
        this.name = name;
    }
}
