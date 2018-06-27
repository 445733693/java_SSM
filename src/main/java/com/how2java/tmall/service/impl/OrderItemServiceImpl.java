package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderitemMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.Orderitem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderitemMapper orderitemMapper;
    @Autowired
    private ProductService productService;

    @Override
    public void add(Orderitem orderitem) {
        orderitemMapper.insert(orderitem);
    }

    @Override
    public void delete(Integer id) {
        orderitemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Orderitem orderitem) {
        orderitemMapper.updateByPrimaryKey(orderitem);
    }

    @Override
    public Orderitem get(Integer id) {
        Orderitem oi = orderitemMapper.selectByPrimaryKey(id);
        oi.setProduct(productService.get(oi.getPid()));
        return oi;
    }

    @Override
    public List<Orderitem> list() {
        return orderitemMapper.selectAll();
    }

    @Override
    public void fill(Order order) {
        List<Orderitem> orderItemls = order.getOrderItems();
        Integer oid = order.getId();
        List<Orderitem> os = orderitemMapper.selectByOid(oid);
        Float total=new Float(0);
        for(Orderitem oi:os){
            Product p = productService.get(oi.getPid());
            oi.setProduct(p);
            total+=p.getPromotePrice();
            orderItemls.add(oi);
        }
        order.setTotalNumber(orderItemls.size());
        order.setTotal(total);
    }

    @Override
    public void fill(List<Order> orders) {
        for(Order order:orders){
            this.fill(order);
        }
    }

    @Override
    public Integer getSaleCount(Integer pid) {
        List<Orderitem> ois = orderitemMapper.selectByPid(pid);
        Integer sum=0;
        for (Orderitem oi : ois) {
            sum+=oi.getNumber();
        }

        return sum;
    }

    @Override
    public List<Orderitem> listByUser(Integer uid) {
        List<Orderitem> ois = orderitemMapper.listByUser(uid);
        for (Orderitem oi : ois) {
            oi.setProduct(productService.get(oi.getPid()));
        }
        return ois;

    }


}
