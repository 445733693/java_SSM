package com.how2java.tmall.service;


import com.how2java.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {
    List<Property> list(Integer cid);
    void add(Property property);

    void delete(Integer id);

    void update(Property property);

    Property get(Integer id);
}
