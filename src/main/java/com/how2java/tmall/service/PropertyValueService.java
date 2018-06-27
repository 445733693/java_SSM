package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.Propertyvalue;

import java.util.List;

public interface PropertyValueService {
    void init(Product p);
    void update(Propertyvalue pv);

    Propertyvalue get(Integer ptid, Integer pid);
    List<Propertyvalue> list(Integer pid);
}
