package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ReviewMapper;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserService userService;

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(Integer id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKey(review);
    }

    @Override
    public Review get(Integer id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> list(Integer pid) {
        List<Review> reviews = reviewMapper.selectAll(pid);
        this.setUser(reviews);
        return reviews;
    }

    @Override
    public Integer getCount(Integer pid) {
        return reviewMapper.getCount(pid);
    }

    public void setUser(Review review){
        User user = userService.get(review.getUid());
        review.setUser(user);
    }
    public void setUser(List<Review> reviews){
        for(Review r:reviews){
            this.setUser(r);
        }
    }
}
