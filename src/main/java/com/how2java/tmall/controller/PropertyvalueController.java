package com.how2java.tmall.controller;

import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Propertyvalue;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyvalueController {
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;
    @RequestMapping("admin_propertyValue_edit")
    public String edit(Integer pid, Model model) {
        Product p = productService.get(pid);
        propertyValueService.init(p);
        List<Propertyvalue> pvs = propertyValueService.list(pid);
        model.addAttribute("pvs", pvs);
        model.addAttribute("p", p);
        return "admin/editPropertyValue";
    }
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(Propertyvalue pv){
        propertyValueService.update(pv);
        System.out.println("=====ptid:"+pv.getPtid()+";pid:"+pv.getId());
        return "success";
    }
}
