package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.OrderExtend;
import com.guolala.zxx.entity.model.OrderGoods;
import java.util.List;

public interface OrderGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGoods record);

    OrderGoods selectByPrimaryKey(Integer id);

    List<OrderGoods> selectAll();

    int updateByPrimaryKey(OrderGoods record);

    List<OrderGoods> selectByOrderNo(String orderNo);
}