package com.yffd.jysg.springboot.demo.repository;

import com.yffd.jysg.springboot.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

    UserEntity findByName(String name);

}
