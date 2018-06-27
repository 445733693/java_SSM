package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.Orderitem;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderItemService {
    void add(Orderitem orderitem);

    void delete(Integer id);
    void update(Orderitem orderitem);
    Orderitem get(Integer id);
    List<Orderitem> list();

    void fill(Order order);
    void fill(List<Order> orders);
    Integer getSaleCount(Integer pid);

    List<Orderitem> listByUser(Integer uid);


}
