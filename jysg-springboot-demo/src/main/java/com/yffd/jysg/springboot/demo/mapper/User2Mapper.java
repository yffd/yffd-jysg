package com.yffd.jysg.springboot.demo.mapper;

import com.yffd.jysg.springboot.demo.entity.User2Entity;

import java.util.List;

public interface User2Mapper {

    List<User2Entity> findAll();
    User2Entity findById(Long id);
    void insert(User2Entity user);
    void update(User2Entity user);
    void delete(Long id);

}
