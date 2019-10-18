package com.yffd.jysg.springboot.demo.controller;

import com.yffd.jysg.springboot.demo.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Swagger测试管理模块")
@RestController
@RequestMapping("/swagger")
public class Swagger2Controller {

    @ApiOperation(value = "获取所有XXX", notes="查询分页数据")
    @GetMapping(value = "/findPage")
    public String findPage(Integer pageNum, Integer pageSize) {
        System.out.println(pageNum);
        return "ok";
    }

    @ApiOperation(value = "添加新用户")
    @PostMapping("/add")
    public String add(@RequestBody UserEntity user) {
        System.out.println(user.getName());
        return "ok";
    }

    @ApiOperation("修改角色")
    @PostMapping("/update")
    public String update(@RequestBody UserEntity user) {
        return "ok";
    }

    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "form", dataTypeClass = String.class)
    @PostMapping("/delById")
    public String delById(String userId) {
        return "ok";
    }

}
