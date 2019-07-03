package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.ShopCart;
import com.guolala.zxx.entity.vo.ShopCartVo;

import java.util.List;

public interface ShopCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopCart record);

    ShopCart selectByPrimaryKey(Integer id);

    List<ShopCart> selectAll();

    int updateByPrimaryKey(ShopCart record);

    List<ShopCartVo> selectUserShopCart(Integer userId);

    ShopCart selectByUserIdAndGoodsId(Integer userId, Integer goodsId);

    void updateToDelByUserId(Integer userId);
}