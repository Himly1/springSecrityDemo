package com.himly.springsecuritydemo.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.himly.springsecuritydemo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("hikedu接口文档")//大标题
                .description("hikedu二期接口文档")//详细描述
                .termsOfServiceUrl("http://blog.csdn.net/eacter")
                .contact("himly")//作者
                .version("1.0")//版本
                .build();
    }


//    分组使用
//    @Bean
//    public Docket demoApi() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("demo")//创建多个分组
//                .apiInfo(demoApiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.mycompany.financial.nirvana.demo"))
//                .paths(PathSelectors.any())
//                .build();
//    }

//    private ApiInfo demoApiInfo() {
//        return new ApiInfoBuilder()
//                .title("Spring Boot中使用Swagger2构建RESTful APIs")//大标题
//                .description("Swagger2 demo")//详细描述
//                .termsOfServiceUrl("http://blog.csdn.net/eacter")
//                .contact("有鱼团队")//作者
//                .version("1.0")//版本
//                .license("The Apache License, Version 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .build();
//    }


}
