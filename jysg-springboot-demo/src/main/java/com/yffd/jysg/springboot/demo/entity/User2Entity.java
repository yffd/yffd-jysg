package com.yffd.jysg.springboot.demo.entity;

import java.io.Serializable;

public class User2Entity implements Serializable{
    private static final long serialVersionUID = 5370779417933737108L;
    private Long id;
    private String name;
    private int age;
    private String pwd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
