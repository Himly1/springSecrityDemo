package com.himly.springsecuritydemo.jwt.config;

import com.himly.springsecuritydemo.utils.JwtUitl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader("token");

        //判断是否有token
        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = null;

        try {
            authenticationToken = JwtUitl.getAuthentication(token);
        }catch (Exception e) {
            System.out.println("has an error,see=="+e.getMessage());
            SecurityContextHolder.clearContext();
            return;
        }


        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        chain.doFilter(request, response);
    }

}
