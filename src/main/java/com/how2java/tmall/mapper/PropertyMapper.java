package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Property;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    Property selectByPrimaryKey(Integer id);

    List<Property> selectAll(Integer cid);

    int updateByPrimaryKey(Property record);
}