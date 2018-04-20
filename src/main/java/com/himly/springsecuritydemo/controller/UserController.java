package com.himly.springsecuritydemo.controller;




import com.himly.springsecuritydemo.dao.RoleDao;
import com.himly.springsecuritydemo.security.config.LightSwordUserDetailService;
import com.himly.springsecuritydemo.utils.JwtUitl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;


@Api(value = "用户模块",description = "用户登录注册登出等接口文档",position = 100,protocols = "http")
/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
@RestController
public class UserController {

    @Autowired
    RoleDao ruleDao;

    @Autowired
    LightSwordUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;


    //数据库中ROLE必须加前缀ROLE_XX
    //使用时ROLE_xxx 的前缀ROLE可省略
    @GetMapping("/a/u/info/student")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUserInfo(HttpServletRequest request) {

        System.out.println(request.getHeader("token"));

        System.out.println("got it!!,/a/u/info/student");
        return "success";
    }

    @GetMapping("/a/u/info/manager")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getManagerInfo(HttpServletRequest request) {

//        System.out.println("进来了");
//        UserDetails userDetails = userDetailService.loadUserByUsername("17601367196");
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
//
//        Authentication authentication = authenticationManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        HttpSession session = request.getSession();
//        session.setAttribute("SPRING_SECURITY_CONTENT",SecurityContextHolder.getContext());
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        session.setAttribute("USER_TAG",user);
//
//        System.out.println("got it!!,/a/u/info/manager");
        return "success";
    }


    @GetMapping("/a/logout")
    @PreAuthorize("isAuthenticated()")
    public String logOut(HttpServletRequest request) {


        request.getSession().removeAttribute("SPRING_SECURITY_CONTENT");
        request.getSession().removeAttribute("USER_TAG");


        return "success";
    }


    @ApiOperation(value = "用户登录",notes = "仅限学生登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "学生手机号码",required = true,dataType = "String",paramType = "form"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,dataType = "String",paramType = "form")}
    )
    @PostMapping("/a/login")
    public LinkedHashMap<String,Object> login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response, HttpServletRequest request) {

        System.out.println(username+"       "+password);
        LinkedHashMap<String,Object> result = new LinkedHashMap<>(4);


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        System.out.println("authentication is=="+authentication);

        if (null==authentication) {
            result.put("code",-1);
            result.put("msg","权限验证失败,密码或账号错误0");
        }else {
            result.put("code",0);
            result.put("msg","权限验证成功");
            String token = JwtUitl.getToken(authentication);
            result.put("token",token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //TODO 重写userDetails,不返回敏感信息
            result.put("data",user);
        }


        return result;
    }

}

