package com.guolala.zxx.service;

import com.guolala.zxx.entity.req.ShopCartReq;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/19
 * @Description:
 */
public interface ShopCartService {

    /**
     * 将商品加入购物车
     *
     * @param userId
     * @param shopCartReq
     */
    void addToCart(Integer userId, ShopCartReq shopCartReq);

    /**
     * 从购物车删除商品
     *
     * @param userId
     * @param shopCartReq
     */
    void removeFromCart(Integer userId, ShopCartReq shopCartReq);

    /**
     * 清空购物车
     *
     * @param userId
     */
    void clearCart(Integer userId);

    /**
     * 获取购物车列表
     *
     * @param userId
     * @return
     */
    List<ShopCartReq> listCart(Integer userId);
}
