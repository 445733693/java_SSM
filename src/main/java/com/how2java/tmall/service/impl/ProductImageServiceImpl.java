package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ProductimageMapper;
import com.how2java.tmall.pojo.Productimage;
import com.how2java.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductimageMapper productimageMapper;
    @Override
    public void add(Productimage pi) {
        productimageMapper.insert(pi);
    }

    @Override
    public void delete(Integer id) {
        productimageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Productimage pi) {

    }

    @Override
    public Productimage get(Integer id) {
        return productimageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Productimage> list(Integer pid, String type) {
        return productimageMapper.selectAll(pid,type);
    }
}
