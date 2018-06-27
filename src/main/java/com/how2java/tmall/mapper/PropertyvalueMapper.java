package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Propertyvalue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PropertyvalueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Propertyvalue record);

    Propertyvalue selectByPrimaryKey(@Param("ptid") Integer ptid, @Param("pid") Integer pid);

    List<Propertyvalue> selectAll(Integer pid);

    int updateByPrimaryKey(Propertyvalue record);
}