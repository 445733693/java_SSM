package com.how2java.tmall.service;

import com.how2java.tmall.pojo.User;

import java.util.List;

public interface UserService {
    void add(User u);

    void delete(Integer id);

    void update(User user);

    User get(Integer id);

    List<User> list();

    Boolean isExist(String name);

    User get(String name,String password);
}
