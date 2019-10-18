package com.yffd.jysg.springboot.demo.config;

import com.yffd.jysg.springboot.demo.JysgSpringbootDemoApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JysgSpringbootDemoApplication.class);
    }

}
