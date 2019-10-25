package com.yffd.jysg.springboot.demo.jwt.repository;

import com.yffd.jysg.springboot.demo.jwt.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserInfoRepo extends JpaRepository<UserInfoEntity, String>, JpaSpecificationExecutor<UserInfoEntity> {
}
