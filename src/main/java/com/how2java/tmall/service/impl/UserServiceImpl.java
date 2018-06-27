package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public void add(User u) {
        userMapper.insert(u);
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        return userMapper.selectAll();
    }

    @Override
    public Boolean isExist(String name) {
        User u = userMapper.getByName(name);
        if(u==null){
            return false;
        }
        return true;
    }

    @Override
    public User get(String name, String password) {
        return userMapper.getByNameAndPassword(name,password);

    }
}
