package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("admin_order_list")
    public String list(Model model,Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> os = orderService.list();
        Integer total = (int)new PageInfo<>(os).getTotal();
        page.setTotal(total);
        model.addAttribute("os",os);
        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(Order order){
        order.setStatus(OrderService.waitConfirm);
        order.setDeliveryDate(new Date());
        orderService.update(order);
        return "redirect:admin_order_list";
    }
}
