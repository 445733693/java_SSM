package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll(Integer cid);

    int updateByPrimaryKey(Product record);

    List<Product> search(@Param("keyword") String keyword);
}