package com.yffd.jysg.jysg.springboot.helloworld.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloworldController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloworldController.class);

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        LOG.info(">>>>>>>hello:" + System.currentTimeMillis());
        return "hello world";
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        LOG.info(">>>>>>>index:" + System.currentTimeMillis());
        ModelAndView modelAndView = new ModelAndView("/index"); //设置对应JSP的模板文件
        modelAndView.addObject("hi", "hello world!"); //设置${hi}标签的值为hello world!
        return modelAndView;
    }

}
