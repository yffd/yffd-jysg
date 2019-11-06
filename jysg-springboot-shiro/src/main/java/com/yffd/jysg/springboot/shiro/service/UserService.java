package com.yffd.jysg.springboot.shiro.service;

import com.yffd.jysg.springboot.shiro.entity.User;

public interface UserService {

    /** 根据用户名 查询用户 */
    public User findByUserName(String username);
    /** 修改用户信息 */
    public User updateUser(User user);

}
