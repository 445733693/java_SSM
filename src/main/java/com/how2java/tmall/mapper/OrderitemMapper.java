package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.Orderitem;
import java.util.List;

public interface OrderitemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orderitem record);

    Orderitem selectByPrimaryKey(Integer id);

    List<Orderitem> selectAll();

    int updateByPrimaryKey(Orderitem record);

    List<Orderitem> selectByOid(Integer oid);

    List<Orderitem> selectByPid(Integer pid);

    List<Orderitem> listByUser(Integer uid);


}