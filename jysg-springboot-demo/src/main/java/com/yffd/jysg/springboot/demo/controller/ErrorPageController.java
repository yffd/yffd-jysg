package com.yffd.jysg.springboot.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ep")
public class ErrorPageController {

    //返回视图
    @RequestMapping("/index")
    public String indexHtml(Model model) {
        model.addAttribute("test");
        //返回视图名字
        return "test_ep";
    }

    //返回json
    @RequestMapping("/errorResTest")
    @ResponseBody
    public String errorResTest(){
        int i = 0;
        int j = -1;
        int x = j/i;
        return "erro";
    }

}
