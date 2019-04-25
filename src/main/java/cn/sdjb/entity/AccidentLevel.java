package cn.sdjb.entity;

import lombok.Data;

@Data
public class AccidentLevel extends BaseEntity {
    private String name;


    public AccidentLevel() {
    }

    public AccidentLevel(String name) {
        super();
        this.name = name;
    }
}
