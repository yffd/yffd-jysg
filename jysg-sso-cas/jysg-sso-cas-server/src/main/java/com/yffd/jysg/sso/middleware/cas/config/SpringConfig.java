package com.yffd.jysg.sso.middleware.cas.config;

import com.yffd.jysg.sso.middleware.cas.authentication.CustomAuthenticationEventExecutionPlanConfigurer;
import com.yffd.jysg.sso.middleware.cas.authentication.shiro.CustomShiroAuthenticationEventExecutionPlanConfigurer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * 这个配置是空值,是为了让spring 加载 这个包下 标注了  @Service @Component @Controller 等注解的Bean
 * 并需要在resource/META-INF/spring.factories 中配置
 */
@Configuration
@ComponentScan(basePackages = "com.yffd.jysg.sso.middleware.cas",
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                CustomShiroAuthenticationEventExecutionPlanConfigurer.class,
                CustomAuthenticationEventExecutionPlanConfigurer.class
        })}
)
public class SpringConfig {
}
