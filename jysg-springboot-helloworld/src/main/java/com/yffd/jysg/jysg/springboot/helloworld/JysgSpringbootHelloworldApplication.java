package com.yffd.jysg.jysg.springboot.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringBootServletInitializer类是springboot提供的web程序初始化的入口，当使用外部容器运行项目时会自动加载并且装配
 */
@SpringBootApplication
public class JysgSpringbootHelloworldApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(JysgSpringbootHelloworldApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(JysgSpringbootHelloworldApplication.class, args);
	}

}
