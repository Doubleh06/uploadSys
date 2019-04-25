package cn.sdjb.core;

/**
 * Created by fonlin on 2017/9/8.
 * 错误Result
 */
public class ErrorResult extends Result {

    public ErrorResult(int code, String msg) {
        super(code, msg);
    }

    public ErrorResult(ErrorCode httpCode) {
        super(httpCode.code(), httpCode.message());
    }
}
