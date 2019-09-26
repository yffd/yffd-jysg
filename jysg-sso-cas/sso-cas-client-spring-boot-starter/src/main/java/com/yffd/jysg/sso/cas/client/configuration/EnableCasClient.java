package com.yffd.jysg.sso.cas.client.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用注解方式启动cas功能
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CasClientConfiguration.class)
public @interface EnableCasClient {
}
