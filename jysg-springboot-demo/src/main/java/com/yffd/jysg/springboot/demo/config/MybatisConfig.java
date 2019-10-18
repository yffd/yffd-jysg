package com.yffd.jysg.springboot.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @MapperScan：可以省去在单独给每个Mapper上标识@Mapper的麻烦；
 */
@Configuration
@MapperScan("com.yffd.jysg.springboot.demo.mapper")
public class MybatisConfig {
}
