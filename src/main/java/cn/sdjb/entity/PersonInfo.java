package cn.sdjb.entity;

import lombok.Data;

@Data
public class PersonInfo extends BaseEntity {
    private String userId;
    private String name;
    private String email;

    public PersonInfo(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public PersonInfo() {
    }
}
