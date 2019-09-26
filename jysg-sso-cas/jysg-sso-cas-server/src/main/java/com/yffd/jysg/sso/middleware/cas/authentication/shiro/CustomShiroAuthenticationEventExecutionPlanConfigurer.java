package com.yffd.jysg.sso.middleware.cas.authentication.shiro;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * cas服务端自定义集成shiro登录认证
 * 注意：注释掉org.apereo.cas:cas-server-support-generic的pom依赖，改jar提供了对shiro的集成，shiro.ini配置方式，否则自定义服务端集成shiro方式不起作用
 */
@Configuration("customShiroAuthenticationEventExecutionPlanConfigurer")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomShiroAuthenticationEventExecutionPlanConfigurer implements AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    private CasConfigurationProperties casProperties;
    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(shiroAuthenticationHandler());
    }

    @Bean
    public AuthenticationHandler shiroAuthenticationHandler() {
        com.yffd.jysg.sso.middleware.cas.authentication.shiro.CustomShiroUsernamePasswordAuthenticationHandler handler = new com.yffd.jysg.sso.middleware.cas.authentication.shiro.CustomShiroUsernamePasswordAuthenticationHandler(
                com.yffd.jysg.sso.middleware.cas.authentication.shiro.CustomShiroUsernamePasswordAuthenticationHandler.class.getSimpleName(), servicesManager,
                new DefaultPrincipalFactory(),10);
        return handler;
    }

    @Bean(name="securityManager")
    public SecurityManager securityManager() {
        DefaultSecurityManager securityManager =  new DefaultSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(customShiroAuthorizingRealm());

        //SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public com.yffd.jysg.sso.middleware.cas.authentication.shiro.CustomShiroAuthorizingRealm customShiroAuthorizingRealm(){
        com.yffd.jysg.sso.middleware.cas.authentication.shiro.CustomShiroAuthorizingRealm realm = new com.yffd.jysg.sso.middleware.cas.authentication.shiro.CustomShiroAuthorizingRealm();
        realm.setCachingEnabled(false);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        realm.setAuthenticationCachingEnabled(false);
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        realm.setAuthorizationCachingEnabled(false);
        return realm;
    }

    /**
     * Spring静态注入
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        //等价于 SecurityUtils.setSecurityManager(securityManager);
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }

}
