package com.how2java.tmall.service;




import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> list(Integer cid);
    void add(Product property);

    void delete(Integer id);

    void update(Product property);

    Product get(Integer id);

    void setFirstProductImage(Product p);

    void fill(Category category);

    void fill(List<Category> categorys);

    void fillByRow(List<Category> categorys);

    void setSaleAndReviewNumber(Product product);
    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword);
}
