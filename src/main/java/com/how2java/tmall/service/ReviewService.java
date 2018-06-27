package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {
    void add(Review review);

    void delete(Integer id);

    void update(Review review);

    Review get(Integer id);

    List<Review> list(Integer pid);

    Integer getCount(Integer pid);
}
