package com.yffd.jysg.springboot.demo.servlet;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 方式二：
 *      配置类：            @ServletComponentScan，开启自动装配Servlet功能；
 *      自定义servlet类：    @WebServlet(urlPatterns = "/test2")，标识该Servlet可以自动装配到项目；
 */
@Configuration
@ServletComponentScan
public class ServletConfig {

    /**
     * 方式一：使用Bean注册Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new TestServlet(),"/test");
    }

}
