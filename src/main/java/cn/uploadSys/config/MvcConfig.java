package cn.uploadSys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by admin on 2017/6/14.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //将/路径
        registry.addViewController("/").setViewName("redirect:/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
