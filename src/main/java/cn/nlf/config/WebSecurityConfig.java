package cn.nlf.config;

import cn.nlf.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/6/12.
 */
@Configuration
@EnableWebSecurity//禁用Boot的默认Security配置，配合@Configuration启用自定义配置（需要扩展WebSecurityConfigurerAdapter）
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf
        http.csrf().disable();
        http.formLogin().loginPage("/login")
                //登录处理url
                .loginProcessingUrl("/j_spring_security_check")
                //登录时对应的用户名密码参数名
                .usernameParameter("username").passwordParameter("password")
                //认证失败处理器
                .failureHandler(authenticationFailureHandler())
                //登录成功处理器
                .successHandler(loginSuccessHandler())
                //登出url及登出成功处理器
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler())
                //设置无权限访问页
                .and().exceptionHandling().accessDeniedPage("/403")
                //无效session处理
                .and().sessionManagement().invalidSessionStrategy(invalidSessionStrategy())
                //单用户登录、session注册器、session过期处理
                .maximumSessions(1).sessionRegistry(sessionRegistry()).expiredSessionStrategy(sessionInformationExpiredStrategy());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //对以下路径忽略过滤
        web.ignoring().antMatchers("/static/**", "/login", "/remote/**", "/test/**", "/checkextuser","/employee/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }


    /**
     * 密码加密
     *
     * @return  BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录成功处理器
     *
     * @return LoginSuccessHandler
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();
        loginSuccessHandler.setDefaultTargetUrl("/main");
        return loginSuccessHandler;
    }

    /**
     * 登出成功处理器
     *
     * @return LogoutSuccessHandler
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        LogoutSuccessHandler logoutSuccessHandler = new LogoutSuccessHandler();
        logoutSuccessHandler.setDefaultTargetUrl("/login?logout");
        return logoutSuccessHandler;
    }


    /**
     * 认证失败处理器
     *
     * @return AuthenticationFailureHandler
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        ExceptionMappingAuthenticationFailureHandler failureHandler = new ExceptionMappingAuthenticationFailureHandler();
        Map<String, String> failureUrlMap = new HashMap<>();
        //当前用户不可用
        failureUrlMap.put(DisabledException.class.getName(), "/login?disabled");
        //用户名密码错误
        failureUrlMap.put(BadCredentialsException.class.getName(), "/login?error");
        failureHandler.setExceptionMappings(failureUrlMap);
        //默认跳转
        failureHandler.setDefaultFailureUrl("/login?error");
        return failureHandler;
    }

    /**
     * {@see MySessionInformationExpiredStrategy}
     *
     * @return  SessionInformationExpiredStrategy
     */
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new MySessionInformationExpiredStrategy("/login?expired");
    }

    /**
     * {@see MyInvalidSessionStrategy}
     *
     * @return  InvalidSessionStrategy
     */
    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new MyInvalidSessionStrategy("/login?expired");
    }


}
