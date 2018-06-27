package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.Orderitem;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        order.setStatus(OrderService.delete);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public Order get(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        order.setUser(userMapper.selectByPrimaryKey(order.getUid()));
        orderItemService.fill(order);
        return order;
    }

    @Override
    public List<Order> list() {
        List<Order> orders = orderMapper.selectAll();
        for(Order order:orders){
            order.setUser(userMapper.selectByPrimaryKey(order.getUid()));
            orderItemService.fill(order);
        }
        return orders;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    public Float add(Order order, List<Orderitem> ois) {
        orderMapper.insert(order);
        Integer oid = order.getId();
        Float total=Float.valueOf(0);
        for (Orderitem oi : ois) {
            oi.setOid(oid);
            orderItemService.update(oi);
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
        }
        return total;
    }

    @Override
    public List<Order> list(Integer uid, String excludedStatus) {
        List<Order> os=orderMapper.selectByUidAndExcludedStatus(uid,excludedStatus);

        orderItemService.fill(os);
        return os;
    }
}
