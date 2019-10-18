package com.yffd.jysg.springboot.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * 1.验证Springfox是否正常工作
 *  http://localhost:8080/v2/api-docs
 *  结果是带有大量键值对的JSON响应
 * 2.swagger ui地址
 *  http://localhost:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enable:true}")
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yffd.jysg.springboot.demo.controller"))
                .paths(PathSelectors.any()) // and by paths
//                .paths(regex("/api/.*"))
                .build()
                .useDefaultResponseMessages(false);

    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("jysg团队", "www.my.com", "my@my.com");
        return new ApiInfoBuilder()
                .title("用户授权管理中心")
                .description("接口测试")
                .termsOfServiceUrl("http://localhost:8080/swagger-ui.html") // 将“url”换成自己的ip:port
                .contact(contact)
                .version("v1.1.0")
                .build();
    }

    /*@Bean
    public Docket swaggerSpringMvcPlugin() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Spring Boot APIs")
                .description("Spring Boot + Swagger2")
                .version("1.0.0")
                .license("yffd-jysg组织")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yffd.jysg.springboot.demo.controller"))
//                .paths(regex("/api/.*"))
                .build()
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false);
        return docket;
    }*/

}
