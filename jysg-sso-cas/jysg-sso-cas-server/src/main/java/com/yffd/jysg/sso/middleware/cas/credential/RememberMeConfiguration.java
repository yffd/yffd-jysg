package com.yffd.jysg.sso.middleware.cas.credential;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("rememberMeConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class RememberMeConfiguration implements AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(customRememberMeUsernamePasswordAuthenticationHandler());
    }

    @Bean
    public AuthenticationHandler customRememberMeUsernamePasswordAuthenticationHandler() {
        CustomRememberMeUsernamePasswordAuthenticationHandler handler = new CustomRememberMeUsernamePasswordAuthenticationHandler(
                CustomRememberMeUsernamePasswordAuthenticationHandler.class.getSimpleName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                11);//放到 shiro(order=10) 验证器的后面 先验证验证码
        return handler;
    }
}
