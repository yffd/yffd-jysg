package com.yffd.jysg.springboot.demo.mapper2;

import com.yffd.jysg.springboot.demo.entity2.UserEntity;
import java.util.List;

public interface UserEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserEntity record);

    UserEntity selectByPrimaryKey(Long id);

    List<UserEntity> selectAll();

    int updateByPrimaryKey(UserEntity record);
}