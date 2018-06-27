package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User getByName(String name);

    User getByNameAndPassword(@Param("name") String name, @Param("password") String password);
}