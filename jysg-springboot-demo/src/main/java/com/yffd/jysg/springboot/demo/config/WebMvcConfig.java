package com.yffd.jysg.springboot.demo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yffd.jysg.springboot.demo.config.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvcConfigurer：SpringBoot内部提供专门处理用户自行添加的配置，如：视图的过滤、拦截器、过滤器、Cors配置等
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns("/**");
    }

    /**
     * 配置自定义静态资源文件路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/login.html").addResourceLocations("classpath:/login.html");
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/index.html");
    }

    /**
     * 配置自定义类型转换器
     * @param registry
     */
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new DateConverter());//将任意字符串形式的日期转换成java.util.Date类型
//    }

    /**
     * 配置自定义消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastJsonConfig());
        //添加到视图消息转换器列表中
        converters.add(converter);
    }

    /**
     * 配置自定义跨域资源共享（cors）
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许被跨域的路径
                .allowCredentials(true)//是否允许证书
                .allowedMethods("*")//允许被跨域的请求方法
                .allowedOrigins("*")//允许被跨域的请求域名
                .allowedHeaders("*")//允许被跨域的请求header
                .maxAge(3600)//允许被跨域的请求超时时间（秒）
                ;
    }

//    @Bean
//    public SpringHandlerExceptionResolver springHandlerExceptionResolver(@Qualifier("fastJsonConfig") FastJsonConfig fastJsonConfig) {
//        return new SpringHandlerExceptionResolver(fastJsonConfig);
//    }

    @Bean("fastJsonConfig")
    public FastJsonConfig fastJsonConfig() {
        /*
        WriteNullListAsEmpty：List字段，如果为null，输出为[]，而非null；
        WriteNullStringAsEmpty：字符类型，字段如果为null，输出为""，而非null
        DisableCircularReferenceDetect：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）；
        WriteNullBooleanAsFalse：Boolean字段，如果为null，输出为false，而非null
        WriteMapNullValue：是否输出值为null的字段，默认为false。
        */
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        return config;
    }
}
