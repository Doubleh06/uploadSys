package cn.sdjb.entity;

import lombok.Data;

@Data
public class Employee extends BaseEntity {
    private String name;

    public Employee(String name) {
        this.name = name;
    }


    public Employee() {
    }
}
