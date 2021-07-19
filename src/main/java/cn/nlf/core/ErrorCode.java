package cn.nlf.core;

/**
 * Created by fonlin on 2017/9/8.
 */
public enum ErrorCode {

    OK(0, "操作成功"),

    CANNOT_DELETE(1, "无法删除"),

    CANNOT_UPDATE(2, "无法更新"),

    CANNOT_UPLOAD(3, "无法上传"),

    FAIL_DELETECLASS(5, "消课时间不正确，请联系店长"),

    FAIL_REPEATSIGNUP(6, "此学生已经报名该课程"),

    BAD_REQUEST(400, "请求有异常"),

    UNAUTHORIZED(401, "未认证"),

    USER_ALREADY_REG(400, "该用户已经注册"),

    ROLE_KEY_EXIST(400, "角色key不能重复"),

    NOT_LOGIN(401, "未登录"),

    FORBIDDEN(403, "访问被禁止"),

    NOT_FOUND(404, "找不到资源"),

    INTERNAL_SERVER_TIMEOUT(-2, "请求超时"),

    INTERNAL_SERVER_ERROR(-1, "服务器内部错误");

    int code;

    String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
