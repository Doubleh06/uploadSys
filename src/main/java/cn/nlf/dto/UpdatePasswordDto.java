package cn.nlf.dto;

import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String username;
    private Integer userId;
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;

}
