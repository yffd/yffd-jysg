package com.yffd.jysg.springboot.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JysgSpringbootDemoApplication {

	public static void main(String[] args) {
		/*//隐藏banner启动方式
		SpringApplication springApplication = new SpringApplication(JysgSpringbootDemoApplication.class);
		//设置banner的模式为隐藏
		springApplication.setBannerMode(Banner.Mode.OFF);
		//启动springboot应用程序
		springApplication.run(args);*/

		//原启动方式
		SpringApplication.run(JysgSpringbootDemoApplication.class, args);
	}

}
