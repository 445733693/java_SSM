package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list();
    void add(Category category);

    void delete(Integer id);

    void update(Category c);

    Category get(Integer id);
}
