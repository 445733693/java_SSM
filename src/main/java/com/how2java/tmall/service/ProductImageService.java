package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Productimage;

import java.util.List;

public interface ProductImageService {
    String TYPE_SINGLE="type_single";
    String TYPE_DETAIL="type_detail";

    void add(Productimage pi);
    void delete(Integer id);
    void update(Productimage pi);
    Productimage get(Integer id);
    List<Productimage> list(Integer pid,String type);
}
