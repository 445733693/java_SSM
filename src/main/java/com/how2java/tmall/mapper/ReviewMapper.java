package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Review;
import java.util.List;

public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    Review selectByPrimaryKey(Integer id);

    List<Review> selectAll(Integer pid);

    int updateByPrimaryKey(Review record);

    Integer getCount(Integer pid);
}