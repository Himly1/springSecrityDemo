大体流程为:
http://www.importnew.com/20612.html

**疑难点:**

配置数据库

数据库中role表的role字段前必须前ROLE,如:ROLE_USER,ROLE_ADMIN

登录成功后,用户的权限等信息存放在spring的SecurityContextHolder全局缓存中.

访问api接口时,能获取到当前请求api的用户,并获取到用户的角色,查询是否有权限访问该api接口.

spring security 如何确定用户是否登录?
若未自定义配置,默认是通过session来确定,登录是通过销毁session,可以使用jwt来实现.

spring-jwt 太简单,自己google,整个spring security spring boot spring jwt 查看以下参考资料

**参考资料** 

自定义权限验证,AuthenticationProvider实现以及配置
https://segmentfault.com/a/1190000008893479    

整合jwt,http://blog.csdn.net/itguangit/article/details/78960127# springSecrityDemo
