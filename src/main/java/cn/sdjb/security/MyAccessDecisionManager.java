package cn.sdjb.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        //如果是超级管理员，直接通过
        if (userDetail.getUsername().equals("cjadmin")) {
            return;
        }
        for (ConfigAttribute configAttribute : configAttributes) {
            String needPermission = configAttribute.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needPermission.equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        // 没有权限
        throw new AccessDeniedException(" 没有权限访问 ");
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

}