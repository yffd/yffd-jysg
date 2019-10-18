package com.yffd.jysg.springboot.demo.mapper2;

import com.yffd.jysg.springboot.demo.entity2.User2Entity;
import java.util.List;

public interface User2EntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User2Entity record);

    User2Entity selectByPrimaryKey(Long id);

    List<User2Entity> selectAll();

    int updateByPrimaryKey(User2Entity record);
}