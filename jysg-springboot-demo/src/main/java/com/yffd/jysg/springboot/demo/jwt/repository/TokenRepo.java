package com.yffd.jysg.springboot.demo.jwt.repository;

import com.yffd.jysg.springboot.demo.jwt.entity.TokenInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TokenRepo extends JpaRepository<TokenInfoEntity,String>, JpaSpecificationExecutor<TokenInfoEntity> {

}
