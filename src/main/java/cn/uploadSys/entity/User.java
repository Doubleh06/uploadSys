package cn.uploadSys.entity;


import lombok.Data;

/**
 * user
 *
 * @author fonlin
 * @date 2018/4/19
 */
@Data
public class User extends BaseEntity {

    private String username;

    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 是否启用
     */
    private Boolean enable;

}
