package com.how2java.tmall.service.impl;


import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Productimage;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ReviewService reviewService;

    @Override
    public List<Product> list(Integer cid) {
        List<Product> ps = productMapper.selectAll(cid);
        for (Product p : ps) {
            this.setFirstProductImage(p);
            p.setCategory(categoryMapper.get(p.getCid()));
        }
        return ps;
    }

    @Override
    public void add(Product property) {
        productMapper.insert(property);
    }

    @Override
    public void delete(Integer id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product property) {
        productMapper.updateByPrimaryKey(property);
    }

    @Override
    public Product get(Integer id) {
        Product p = productMapper.selectByPrimaryKey(id);
        p.setCategory(categoryMapper.get(p.getCid()));
        this.setFirstProductImage(p);
        return p;
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<Productimage> pis = productImageService.list(p.getId(), ProductImageService.TYPE_SINGLE);
        if (!pis.isEmpty()) {
            p.setFirstProductImage(pis.get(0));
        }
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            List<Productimage> pis = productImageService.list(p.getId(), ProductImageService.TYPE_SINGLE);
            if (!pis.isEmpty()) {
                p.setFirstProductImage(pis.get(0));
            }
        }
    }

    @Override
    public void fill(Category category) {
        Integer cid = category.getId();
        List<Product> products = this.list(cid);
        category.setProducts(products);

    }

    @Override
    public void fill(List<Category> categorys) {
        for (Category c : categorys) {
            this.fill(c);
        }
    }

    @Override
    public void fillByRow(List<Category> categorys) {
        int productNumberEachRow=8;
        for (Category c : categorys) {
            List<List<Product>> list = new ArrayList<>();
            List<Product> products = c.getProducts();
            int size=products.size();
            for(int i=0;i<size;i+=productNumberEachRow) {
                int end=i+productNumberEachRow>size?size:i+productNumberEachRow;
                List<Product> subList = products.subList(i, end);
                list.add(subList);
            }
            c.setProductsByRow(list);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        product.setSaleCount(orderItemService.getSaleCount(product.getId()));
        product.setReviewCount(reviewService.getCount(product.getId()));
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for(Product p:products){
            this.setSaleAndReviewNumber(p);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        List<Product> ps = productMapper.search(keyword);
        for (Product p : ps) {
            this.setFirstProductImage(p);
        }
        return ps;

    }
}
