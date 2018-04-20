package com.himly.springsecuritydemo.security.config;

import com.himly.springsecuritydemo.jwt.config.JwtAuthenticationFilter;
import com.himly.springsecuritydemo.jwt.config.JwtLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
@Configuration

//以下两个注解是开启使用spring security的注解

//详解作用请google
@EnableWebSecurity
//详细作用请谷歌
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    AuthenticationProviderExtend authenticationProviderExtend;

//    定义拦截路径等配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        //使用默认设置
//        super.configure(http);
        //貌似是关闭csrf验证,详细请google
        http.csrf().disable();

        http.authorizeRequests().
                antMatchers(HttpMethod.POST,"/a/login").permitAll()
                .antMatchers("/amchart/**",
                        "/bootstrap/**",
                        "/build/**",
                        "/css/**",
                        "/dist/**",
                        "/documentation/**",
                        "/fonts/**",
                        "/js/**",
                        "/pages/**",
                        "/plugins/**",
                        "/login.html"
                ).permitAll() //默认不拦截静态资源的url pattern （2）
                .anyRequest().authenticated().and()//除此之外所有都拦截
                .formLogin().loginPage("/login.html").loginProcessingUrl("/a/login/a/a/a/a/a/a/a/");// 登录url请求路径 (3)

//        //登录验证
        http.addFilter(new JwtLoginFilter(authenticationManager()));
//
//        //权限验证
        http.addFilter(new JwtAuthenticationFilter(authenticationManager()));

        // 退出默认跳转页面 (5)
        http.logout().logoutSuccessUrl("http://google.com").permitAll();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//
////        将用户权限等信息存放在内存中.
//        //password使用{noop}来指定不使用加密
//        auth.
//                inMemoryAuthentication()
//                .withUser("user")
//                .password("{noop}user").
//                roles("USER");
//
//
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{noop}admin")
//                .roles("ADMIN");
//


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //AuthenticationManager使用我们的 lightSwordUserDetailService 来获取用户信息
//        数据库中的密码目前采用BCryptPasswordEncoder加密,相应的配置加密工具,以便比对数据库密码
//        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());

//        使用自定义验证
        auth.authenticationProvider(authenticationProviderExtend);
    }


//    返回自己的UserDetailsService
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new LightSwordUserDetailService();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);

    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
    }

}
