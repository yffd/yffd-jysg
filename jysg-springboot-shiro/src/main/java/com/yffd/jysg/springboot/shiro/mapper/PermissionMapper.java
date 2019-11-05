package com.yffd.jysg.springboot.shiro.mapper;

import com.yffd.jysg.springboot.shiro.entity.Permission;
import com.yffd.jysg.springboot.shiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface PermissionMapper {
    Set<Permission> findPermissionsByRoleId(@Param("roles") Set<Role> roles);
}
