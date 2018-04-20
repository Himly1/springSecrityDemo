package com.himly.springsecuritydemo;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

////    视图映射器,具体用法请google
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("/login.html");
    }
}
