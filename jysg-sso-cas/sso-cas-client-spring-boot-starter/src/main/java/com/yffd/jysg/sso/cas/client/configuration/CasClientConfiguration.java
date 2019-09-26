package com.yffd.jysg.sso.cas.client.configuration;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(CasClientConfigurationProperties.class)
@ConditionalOnProperty(
        //存在对应配置信息时初始化该配置类
        prefix = "cas",//存在配置前缀hello
        value = "enabled",//开启
        matchIfMissing = true//缺失检查
)
public class CasClientConfiguration {

    @Autowired
    CasClientConfigurationProperties configProps;

    /**
     * 配置登出过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterSingleRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SingleSignOutFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerUrlPrefix", configProps.getServerUrlPrefix());
        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * 配置Ticket校验过滤器，必须启用，这里用的是Cas30ProxyReceivingTicketValidationFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean filterValidationRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Cas30ProxyReceivingTicketValidationFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerUrlPrefix", configProps.getServerUrlPrefix());
        initParameters.put("serverName", configProps.getClientHostUrl());
        initParameters.put("useSession", "true");
        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(2);
        return registration;
    }

    /**
     * 配置用户认证过滤器，必须启用
     * @return
     */
    @Bean
    public FilterRegistrationBean filterAuthenticationRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthenticationFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerLoginUrl", configProps.getServerLoginUrl());
        initParameters.put("serverName", configProps.getClientHostUrl());

        if(configProps.getIgnorePattern() != null && !"".equals(configProps.getIgnorePattern())){
            initParameters.put("ignorePattern", configProps.getIgnorePattern());
        }

        //自定义UrlPatternMatcherStrategy 验证规则
        if(configProps.getIgnoreUrlPatternType() != null && !"".equals(configProps.getIgnoreUrlPatternType())){
            initParameters.put("ignoreUrlPatternType", configProps.getIgnoreUrlPatternType());
        }

        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(3);
        return registration;
    }

    /**
     * request wraper过滤器，可选配置
     * 允许开发者通过HttpServletRequest的getRemoteUser()方法获得SSO登录用户的登录名
     * @return
     */
    @Bean
    public FilterRegistrationBean filterWrapperRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        // 设定加载的顺序
        registration.setOrder(4);
        return registration;
    }

    /**
     * 添加监听器，监听cas登出事件
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<EventListener> singleSignOutListenerRegistration(){
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<EventListener>();
        registrationBean.setListener(new SingleSignOutHttpSessionListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
