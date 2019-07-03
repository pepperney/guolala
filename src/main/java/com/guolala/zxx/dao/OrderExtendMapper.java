package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.OrderExtend;
import java.util.List;

public interface OrderExtendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderExtend record);

    OrderExtend selectByPrimaryKey(Integer id);

    List<OrderExtend> selectAll();

    int updateByPrimaryKey(OrderExtend record);

    OrderExtend selectByOrderNo(String orderNo);
}