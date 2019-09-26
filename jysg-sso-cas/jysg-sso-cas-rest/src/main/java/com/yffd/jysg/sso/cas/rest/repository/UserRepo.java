package com.yffd.jysg.sso.cas.rest.repository;

import com.yffd.jysg.sso.cas.rest.model.SysUser;
import org.springframework.stereotype.Component;

@Component
public class UserRepo {

    public SysUser getUser(String username) {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        return user;
    }

}
