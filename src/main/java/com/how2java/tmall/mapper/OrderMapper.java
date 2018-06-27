package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    List<Order> selectByUidAndExcludedStatus(@Param("uid") Integer uid,@Param("excludedStatus") String excludedStatus);
}