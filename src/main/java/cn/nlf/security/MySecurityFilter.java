package cn.nlf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

@Component
public class MySecurityFilter extends AbstractSecurityInterceptor implements
        Filter {
	// 与applicationContext-security.xml里的myFilter的属性securityMetadataSource对应�?
	// 其他的两个组件，已经在AbstractSecurityInterceptor定义
	@Autowired
	private MySecurityMetadataSource mySecurityMetadataSource;
	@Autowired
	private MyAccessDecisionManager myAccessDecisionManager;
	@Autowired
	private AuthenticationManager myAuthenticationManager;

	@PostConstruct
	public void init() {
		// System.err.println(" ---------------  MySecurityFilter init--------------- ");
		super.setAuthenticationManager(myAuthenticationManager);
		super.setAccessDecisionManager(myAccessDecisionManager);
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.mySecurityMetadataSource;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}

	private void invoke(FilterInvocation fi) throws IOException,
            ServletException {
		// object为FilterInvocation对象
		// super.beforeInvocation(fi);源码
		// 执行Collection<ConfigAttribute> attributes =
		// SecurityMetadataSource.getAttributes(object);
		// 2.是否拥有权限
		// this.accessDecisionManager.decide(authenticated, object, attributes);
		// System.err.println(" ---------------  MySecurityFilter invoke--------------- ");
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {

	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

}