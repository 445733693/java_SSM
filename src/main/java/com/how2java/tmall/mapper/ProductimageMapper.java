package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Productimage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductimageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Productimage record);

    Productimage selectByPrimaryKey(Integer id);

    List<Productimage> selectAll(@Param("pid") Integer pid, @Param("type") String type);

    int updateByPrimaryKey(Productimage record);
}