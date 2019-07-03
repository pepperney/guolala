package com.guolala.zxx.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guolala.zxx.constant.RedisKey;
import com.guolala.zxx.dao.ShopCartMapper;
import com.guolala.zxx.entity.model.ShopCart;
import com.guolala.zxx.entity.vo.ShopCartVo;
import com.guolala.zxx.service.ShopCartService;
import com.guolala.zxx.util.BeanUtil;
import com.guolala.zxx.util.JsonUtil;
import com.guolala.zxx.util.RedisUtil;
import com.guolala.zxx.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/19
 * @Description:
 */
@Slf4j
@Service
public class ShopCartServiceImpl implements ShopCartService {


    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void addToCart(Integer userId, ShopCartVo shopCartVo) {
        ValidateUtil.validateParam(shopCartVo);
        ShopCart shopCart = BeanUtil.copyProperties(shopCartVo, ShopCart.class);
        ShopCart existShopCart = shopCartMapper.selectByUserIdAndGoodsId(userId, shopCartVo.getGoodsId());
        if (null != existShopCart) {
            existShopCart.setQuantity(shopCartVo.getQuantity());
            existShopCart.setUpdateTime(new Date());
            shopCartMapper.updateByPrimaryKey(existShopCart);
        }
        shopCart.setUserId(userId);
        shopCart.setCreateTime(new Date());
        shopCart.setUpdateTime(new Date());
        shopCartMapper.insert(shopCart);
    }

    @Override
    public void removeFromCart(Integer userId, ShopCartVo shopCartVo) {
        ValidateUtil.validateParam(shopCartVo);
        ShopCart existShopCart = shopCartMapper.selectByUserIdAndGoodsId(userId, shopCartVo.getGoodsId());
        if (null != existShopCart) {
            existShopCart.setQuantity(shopCartVo.getQuantity());
            existShopCart.setUpdateTime(new Date());
            existShopCart.setDeleted(Boolean.TRUE);
            shopCartMapper.updateByPrimaryKey(existShopCart);
        }
    }

    @Override
    public void clearCart(Integer userId) {
        shopCartMapper.updateToDelByUserId(userId);
    }

    @Override
    public List<ShopCartVo> listCart(Integer userId) {
        List<ShopCartVo> list = null;
        String key = RedisKey.USER_SHOP_CART.key + userId;
        String cacheValue = redisUtil.get(key);
        if (!StringUtils.isEmpty(cacheValue)) {
            list = JsonUtil.jsonToGenericObject(cacheValue, new TypeReference<List<ShopCartVo>>() {
            });
        } else {
            list = shopCartMapper.selectUserShopCart(userId);
            redisUtil.setex(key, RedisKey.USER_SHOP_CART.expireTime, JsonUtil.toJson(list));
        }
        return list;
    }
}
