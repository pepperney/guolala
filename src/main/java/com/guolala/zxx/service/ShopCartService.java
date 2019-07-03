package com.guolala.zxx.service;

import com.guolala.zxx.entity.vo.ShopCartVo;

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
     * @param shopCartVo
     */
    void addToCart(Integer userId, ShopCartVo shopCartVo);

    /**
     * 从购物车删除商品
     *
     * @param userId
     * @param shopCartVo
     */
    void removeFromCart(Integer userId, ShopCartVo shopCartVo);

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
    List<ShopCartVo> listCart(Integer userId);
}
