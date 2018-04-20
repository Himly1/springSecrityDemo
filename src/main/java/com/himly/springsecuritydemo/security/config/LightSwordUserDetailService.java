package com.himly.springsecuritydemo.security.config;

import com.himly.springsecuritydemo.dao.RoleDao;
import com.himly.springsecuritydemo.dao.StudentDao;
import com.himly.springsecuritydemo.dao.UserRoleDao;
import com.himly.springsecuritydemo.model.Role;
import com.himly.springsecuritydemo.model.Student;
import com.himly.springsecuritydemo.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
@Service
public class LightSwordUserDetailService implements UserDetailsService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    UserRoleDao userRuleDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {

        //mobile数据库存储的是Long类型
        Long t = Long.valueOf(mobile);


        System.out.println("mobile is=="+mobile);

        Student student = studentDao.getStudentByMobile(t);

        if (null==student) {
            throw new UsernameNotFoundException("user mobile = "+mobile+" not found");
        }


        //拿到用户角色列表
        UserRole userRole = new UserRole();
        userRole.setTargetType(0);
        userRole.setTargetId(student.getId());
        List<UserRole> userRoles = userRuleDao.getUserRolesByTargetIdAndeTargetTyp(userRole);

        //拿到用户所有角色对应的id
        List<Long> roleIds = new ArrayList<>(16);
        for (UserRole userRoleT: userRoles) {

            if (null!=userRoleT) {
                roleIds.add(userRoleT.getRoleId());
            }
        }

        //获取用户角色
        List<Role> roles = null;
        if (null==roleIds||0==roleIds.size()) {
            throw new UsernameNotFoundException("user=="+mobile+"   not found");
        }
        roles = roleDao.getRolesByIds(roleIds);


        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(16);
        for (Role roleT:roles) {
            if (null!=roleT&&null!=roleT.getRole()) {

                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleT.getRole());
                grantedAuthorities.add(grantedAuthority);
            }
        }


//        重写User返回更新信息,如id等
        return new User(student.getMobile()+"",student.getPwd(),grantedAuthorities);
    }


    /**
     * 后台为用户手动登录的方法
     * 如用户注册完毕后需要后台进行登录
     * @param mobile
     * @param password
     * @return
     */
    public boolean autoLogin(String mobile, String password, HttpServletRequest request) {
        UserDetails userDetails = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            userDetails = loadUserByUsername(mobile);
        }catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }

        UsernamePasswordAuthenticationToken token = null;
        if (null!=userDetails) {
            token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password, userDetails.getAuthorities());
        }

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("SPRING_SECURITY_CONTENT",SecurityContextHolder.getContext());
        httpSession.setAttribute("USER_TAG",user);

        return true;
    }


    /**
     * 用户登录成功后将用户信息添加到spring security全局缓存中,以便用户无需登录即可访问拥有相应权限的接口
     * 例如,第三方登录成功回调后,需要将用户的信息放到全局缓存.
     * @param mobile
     * @param request
     * @return
     */
    public boolean addUserInfoToSpringSecurityContent(String mobile,HttpServletRequest request) {

        UserDetails userDetails = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
//            获取用户role等信息
            userDetails = loadUserByUsername(mobile);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        UsernamePasswordAuthenticationToken token = null;
        if (null!=userDetails) {
            token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }

//        这一步是登录操作,因为已经登录过了,无需进行登录,且数据库中拿到是密文,spring security 再进行加密比对就会导致无法成功,调用此方法的前提是token中密码是明文.
//        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(token);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("SPRING_SECURITY_CONTENT",SecurityContextHolder.getContext());



        httpSession.setAttribute("USER_TAG",userDetails);

        return true;
    }
}
