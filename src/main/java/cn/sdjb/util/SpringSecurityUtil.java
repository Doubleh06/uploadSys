package cn.sdjb.util;

import cn.sdjb.core.BusinessException;
import cn.sdjb.core.ErrorCode;
import cn.sdjb.security.UserDetail;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * spring security工具类
 *
 * @author fonlin
 * @date 2018/04/21
 */
public class SpringSecurityUtil {


    public static UserDetail getUser() {
        if (!isGuest()) {
            return (UserDetail)getAuthentication().getPrincipal();
        }
        throw new BusinessException(ErrorCode.NOT_LOGIN);
    }

    public static boolean isGuest() {
        return getAuthentication() == null || getAuthentication() instanceof AnonymousAuthenticationToken;

    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

