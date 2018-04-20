package com.himly.springsecuritydemo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 作用请详情google AuthenticationProviderExtend作用
 * 自定义身份验证组件
 */
@Component
public class AuthenticationProviderExtend implements AuthenticationProvider{

    @Autowired
    LightSwordUserDetailService userDetailService;


    /**
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = null;

        System.out.println("aaaaaaaaaaaaa");
        //检查用户有效性
        userDetails = userDetailService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        //密码为null时表明后台手动登录,直接返回凭证,不安全.
        if (null==password) {
            return new UsernamePasswordAuthenticationToken(userDetails,null,authorities);
        }else if (BCrypt.checkpw(password,userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails,password,authorities);
        }else {
//            验证不通过
            return null;
        }
    }

    /**
     * 详情请google
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
