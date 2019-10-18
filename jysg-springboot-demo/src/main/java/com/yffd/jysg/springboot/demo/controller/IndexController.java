package com.yffd.jysg.springboot.demo.controller;

import com.yffd.jysg.springboot.demo.entity.User2Entity;
import com.yffd.jysg.springboot.demo.entity.UserEntity;
import com.yffd.jysg.springboot.demo.mapper.User2Mapper;
import com.yffd.jysg.springboot.demo.mapper.UserMapper;
import com.yffd.jysg.springboot.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User2Mapper user2Mapper;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("name", "隔壁老王");
        return modelAndView;
    }

    @RequestMapping("/jpa")
    public ModelAndView jpa() {
        ModelAndView modelAndView = new ModelAndView("/index");
        userRepo.save(new UserEntity("隔壁老王_jpa",18,"123456"));
        modelAndView.addObject("dataSize", userRepo.findAll().size());
        return modelAndView;
    }

    @RequestMapping("/mybatis")
    public ModelAndView mybatis() {
        User2Entity user = new User2Entity();
        user.setAge(18);
        user.setName("隔壁老王_mybatis");
        user.setPwd("123456");
        userMapper.insert(user);
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("dataSize", userMapper.getAll().size());
        return modelAndView;
    }

    @RequestMapping("/mybatis2")
    public ModelAndView mybatis2() {
        User2Entity user = new User2Entity();
        user.setAge(18);
        user.setName("隔壁老王_mybatis2");
        user.setPwd("123456");
        user2Mapper.insert(user);
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("dataSize", userMapper.getAll().size());
        return modelAndView;
    }

}
