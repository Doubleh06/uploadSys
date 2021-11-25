package cn.uploadSys.core;

import cn.uploadSys.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

/**
 * 全局异常处理器
 *
 * @author fonlin
 * @date 2018/04/21
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 在这里拦截所有的异常
     *
     * 然后根据是ajax请求还是url请求分情况处理
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView jsonErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        //如果是未登录异常直接跳到登录页
        if (e instanceof BusinessException && ((BusinessException) e).getHttpCode().equals(ErrorCode.NOT_LOGIN)) {
            return new ModelAndView("/login?expired");
        }
        e.printStackTrace();
        //如果是业务异常并且是ajax请求，我们直接在response中写入result
        if (HttpUtil.isAjaxRequest(request)) {
            ErrorResult errorResult;
            //如果是业务异常
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                errorResult = createBusinessExceptionErrorResult(businessException);
            } else if (e instanceof MethodArgumentNotValidException) {
                //如果是验证异常
                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
                errorResult = createMethodArgumentNotValidExceptionErrorResult(methodArgumentNotValidException);
            } else {
                errorResult = new ErrorResult(ErrorCode.INTERNAL_SERVER_ERROR.code(), e.getMessage());
            }
            responseResult(response, errorResult);
            return new ModelAndView();
        } else {
            //否则的话跳到error页面
            String url = request.getRequestURL().toString();
            String message = "请求[" + url + "]时系统出错！错误信息：" + e.getMessage();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            modelAndView.addObject("message", message);
            return modelAndView;
        }
    }

    private ErrorResult createBusinessExceptionErrorResult(BusinessException businessException) {
        String message = StringUtils.isEmpty(businessException.getMessage()) ?
                businessException.getHttpCode().message() : businessException.getMessage();
        return new ErrorResult(businessException.getHttpCode().code(), message);
    }

    private ErrorResult createMethodArgumentNotValidExceptionErrorResult(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (Iterator<FieldError> it = result.getFieldErrors().iterator(); it.hasNext();) {
            FieldError fieldError = it.next();
            sb.append(fieldError.getDefaultMessage());
            if (it.hasNext()) {
                sb.append("<br>");
            }
        }
        return new ErrorResult(ErrorCode.BAD_REQUEST.code(), sb.toString());
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
        }
    }
}

