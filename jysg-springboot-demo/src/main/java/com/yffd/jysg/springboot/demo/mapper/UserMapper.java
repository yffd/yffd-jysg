package com.yffd.jysg.springboot.demo.mapper;

import com.yffd.jysg.springboot.demo.entity.User2Entity;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper {

    @Select("select * from user")
    @Results({
            @Result(property = "name", column = "name")
    })
    List<User2Entity> getAll();

    @Select("select * from user where id=#{id}")
    User2Entity getById(Long id);

    @Insert({"insert into user(name,age,pwd) values(#{name},#{age},#{pwd})"})
    void insert(User2Entity user);

    @Update({"update user set name=#{name} where id=#{id}"})
    void Update(User2Entity user);

    @Delete("delete from user where id=#{id}")
    void delete(Long id);

}
