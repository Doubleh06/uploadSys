package cn.nlf.security;


import cn.nlf.core.ErrorCode;
import cn.nlf.core.ErrorResult;
import cn.nlf.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义session失效处理
 * 当ConcurrentSessionFilter检测到当前principal expired时调用
 *
 * @author  fonlin
 * @date    2018/07/23
 */
public class MySessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    private final String destinationUrl;

    private final RedirectStrategy redirectStrategy;


    public MySessionInformationExpiredStrategy(String invalidSessionUrl) {
        this(invalidSessionUrl, new DefaultRedirectStrategy());
    }

    public MySessionInformationExpiredStrategy(String invalidSessionUrl, RedirectStrategy redirectStrategy) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl),
                "url must start with '/' or with 'http(s)'");
        this.destinationUrl=invalidSessionUrl;
        this.redirectStrategy=redirectStrategy;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        if (HttpUtil.isAjaxRequest(request)) {
            //如果是ajax请求
            //设置编码
            response.setCharacterEncoding("UTF-8");
            //写入json
            ErrorResult errorResult = new ErrorResult(ErrorCode.UNAUTHORIZED);
            response.getWriter().print(new ObjectMapper().writeValueAsString(errorResult));
            response.flushBuffer();
        } else {
            this.redirectStrategy.sendRedirect(request, response, destinationUrl);
        }
    }
}
