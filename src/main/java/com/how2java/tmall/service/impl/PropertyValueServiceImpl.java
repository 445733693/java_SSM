package com.how2java.tmall.service.impl;


import com.how2java.tmall.mapper.PropertyMapper;
import com.how2java.tmall.mapper.PropertyvalueMapper;
import com.how2java.tmall.pojo.Product;

import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.Propertyvalue;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    private PropertyvalueMapper propertyvalueMapper;
    @Autowired
    private PropertyMapper propertyMapper;
    @Override
    public void init(Product p) {
        Integer pid = p.getId();
        Integer cid = p.getCid();
        List<Property> properties = propertyMapper.selectAll(cid);
        for (Property property : properties) {
            Integer ptid = property.getId();
            Propertyvalue pv = this.get(ptid, pid);
            if(pv==null){
                pv=new Propertyvalue();
                pv.setPid(pid);
                pv.setPtid(ptid);
                propertyvalueMapper.insert(pv);
            }
        }
    }

    @Override
    public void update(Propertyvalue pv) {
        propertyvalueMapper.updateByPrimaryKey(pv);
    }

    @Override
    public Propertyvalue get(Integer ptid, Integer pid) {
        return propertyvalueMapper.selectByPrimaryKey(ptid,pid);
    }

    @Override
    public List<Propertyvalue> list(Integer pid) {
        List<Propertyvalue> pvs = propertyvalueMapper.selectAll(pid);
        for(Propertyvalue propertyvalue:pvs){
            propertyvalue.setProperty(propertyMapper.selectByPrimaryKey(propertyvalue.getPtid()));
        }
        return pvs;
    }
}
