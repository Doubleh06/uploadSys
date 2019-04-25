package cn.sdjb.security;

import cn.sdjb.core.ErrorCode;
import cn.sdjb.core.ErrorResult;
import cn.sdjb.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当检测到无效的session或者sessionId被传过来时触发跳转动作，比如说长时间不操作
 * 或者web容器重启导致的session失效。总而言之即根据当前sessionId无法获取到有效Session
 *
 * @see org.springframework.security.web.session.SessionManagementFilter
 *
 * @author fonlin
 * @date 2018/7/27
 */
public class MyInvalidSessionStrategy implements InvalidSessionStrategy {

    private final String destinationUrl;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private boolean createNewSession = true;

    public MyInvalidSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl),
                "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    public void onInvalidSessionDetected(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        if (createNewSession) {
            request.getSession();
        }
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

    /**
     *
     * 是否在重定向前创建一个新的session，以此来避免可能存在的死循环（即传来的sessionId
     * 仍然是之前的）
     *
     * @param createNewSession defaults to {@code true}.
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
