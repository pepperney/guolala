package com.guolala.zxx.controller.app;

import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.vo.ShopCartVo;
import com.guolala.zxx.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/11
 * @Description:
 */
@RestController
@RequestMapping("/app/shopcart")
@Api(value = "【app】 购物车相关接口", tags = {"ShopCartController"})
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    /**
     * 添加到购物车
     *
     * @param userInfo
     * @param shopCartVo
     */
    @PostMapping("/v1/add")
    @ApiOperation(value = "添加到购物车", httpMethod = "GET")
    public void addToCart(@ApiIgnore UserInfo userInfo, @RequestBody ShopCartVo shopCartVo) {
        shopCartService.addToCart(userInfo.getId(), shopCartVo);
    }

    /**
     * 从购物车删除
     *
     * @param userInfo
     * @param shopCartVo
     */
    @PostMapping("/v1/remove")
    @ApiOperation(value = "从购物车删除商品", httpMethod = "POST")
    public void removeFromCart(@ApiIgnore UserInfo userInfo, @RequestBody ShopCartVo shopCartVo) {
        shopCartService.removeFromCart(userInfo.getId(), shopCartVo);
    }

    /**
     * 清空购物车
     *
     * @param userInfo
     */
    @PostMapping("/v1/clear")
    @ApiOperation(value = "清空购物车", httpMethod = "POST")
    public void clearCart(@ApiIgnore UserInfo userInfo) {
        shopCartService.clearCart(userInfo.getId());
    }

    /**
     * 购物车列表
     *
     * @param userInfo
     * @return
     */
    @GetMapping("/v1/list")
    @ApiOperation(value = "查询购物车列表", httpMethod = "GET")
    public List<ShopCartVo> getMyShopCartList(@ApiIgnore UserInfo userInfo) {
        List<ShopCartVo> list = shopCartService.listCart(userInfo.getId());
        return list;
    }


}
