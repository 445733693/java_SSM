package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;
import com.sun.jmx.snmp.SnmpAckPdu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class Productontroller {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String list(Integer cid,Model model, Page page){
        Category c = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> ps = productService.list(cid);
        Integer total = (int)new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());
        model.addAttribute("c", c);
        model.addAttribute("ps",ps);
        model.addAttribute("page",page);
        return "admin/listProduct";
    }

    @RequestMapping("admin_product_add")
    public String add(Model model, Product product) throws Exception {//等待测试
        productService.add(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(Integer id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(Integer id,Model model){
        Product p = productService.get(id);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p",p);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product, Model model) throws IOException {
        productService.update(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }
}
