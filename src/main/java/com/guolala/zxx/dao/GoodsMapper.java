package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.Goods;
import com.guolala.zxx.entity.param.GoodsParam;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    Goods selectByPrimaryKey(Integer id);

    List<Goods> selectAll();

    int updateByPrimaryKey(Goods record);

    List<Goods> selectGoodsList(GoodsParam goodsParam);
}