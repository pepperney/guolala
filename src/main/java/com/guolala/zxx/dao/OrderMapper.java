package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.Order;
import com.guolala.zxx.entity.param.OrderParam;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    Order selectOrderInfo(String orderNo);

    void updateOrderStatus(String orderNo, int status);

    List<Order> selectOrders(OrderParam orderParam);
}