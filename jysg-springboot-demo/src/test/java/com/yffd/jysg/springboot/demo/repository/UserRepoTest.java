package com.yffd.jysg.springboot.demo.repository;

import com.yffd.jysg.springboot.demo.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    @Test
    @Transactional
    public void saveTest() {
        UserEntity user = new UserEntity("隔壁老王_jpa",18,"123456");
        userRepo.save(user);
        System.out.println("userId:" + user.getId());
        Assert.assertTrue(user.getId()>0);
    }
}
