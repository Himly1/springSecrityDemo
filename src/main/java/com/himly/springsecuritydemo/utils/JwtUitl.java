package com.himly.springsecuritydemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
public class JwtUitl {

    public static final String JWT_SECRET = "123456";

    public static String getToken(Authentication authResult) {
        String token = Jwts.builder()
//                重写UserDetails 来实现返回关于用户的更新信息,这里仅返回name
                .setSubject(authResult.getName())
                //有效期两小时
                .setExpiration(new Date(System.currentTimeMillis()+(2*60*60*1000) ))
                //采用什么算法是可以自己选择的，不一定非要采用HS512
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET)
                .compact();

        return token;
    }

    /**
     * 解析token中的信息,并判断是否过期
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(String token) throws Exception{


        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        //得到用户名
        String username = claims.getSubject();

        //得到过期时间
        Date expiration = claims.getExpiration();

        //判断是否过期
        Date now = new Date();

        if (now.getTime() > expiration.getTime()) {

            throw new Exception("token timeout.");
        }


        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username, null);
        }
        return null;
    }
}
