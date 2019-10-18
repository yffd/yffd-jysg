package com.yffd.jysg.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/doTest")
    public String doTest() {
        String _key = "time"; //缓存key
        stringRedisTemplate.opsForValue().set(_key, String.valueOf(new Date().getTime())); //redis存值
        return stringRedisTemplate.opsForValue().get(_key); //redis取值
    }

    @Cacheable(value = "userCacheKeyTime")
    @RequestMapping("/cacheable")
    public String cacheable () {
        System.out.println("set cache");
        return "cache:" + new Date().getTime();
    }

    @CachePut(value = "userCacheKeyTime")
    @RequestMapping("/cachePut")
    public String cachePut() {
        System.out.println("update cache");
        return "update cache:" + new Date().getTime();
    }

    @CacheEvict(value = "userCacheKeyTime")
    @RequestMapping("/cacheEvict")
    public String cacheEvict() {
        System.out.println("缓存删除");
        return "delete cache:" + new Date().getTime();
    }

    @RequestMapping("/uid")
    public String testSession(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

}
