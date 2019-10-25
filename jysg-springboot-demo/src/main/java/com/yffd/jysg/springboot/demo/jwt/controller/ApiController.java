package com.yffd.jysg.springboot.demo.jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiController{

    @RequestMapping(value = "/index")
    public String index(){
        return "success";
    }
}
