package com.guolala.zxx.service;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.vo.OrderCreateVo;
import com.guolala.zxx.entity.vo.OrderPayVo;
import com.guolala.zxx.entity.vo.OrderVo;

import java.math.BigDecimal;

/**
 * @Author: pei.nie
 * @Date:2019/4/20
 * @Description:
 */
public interface OrderService {
    /**
     * 获取申请单号
     *
     * @return
     */
    String getOrderNo();

    /**
     * 查询用户的订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    OrderVo queryOrderDetail(Integer userId, String orderNo);

    /**
     * 用户提交订单
     *
     * @param user
     * @param orderCreateVo
     * @return
     */
    String createOrder(UserInfo user, OrderCreateVo orderCreateVo);

    /**
     * 分页查询用户订单列表
     *
     * @param userId
     * @param orderStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<OrderVo> getOrderListByPage(Integer userId, Integer orderStatus, int pageNum, int pageSize);

    /**
     * 用户支付订单
     *
     * @param user
     * @param orderPayVo
     * @return
     */
    boolean payForOrder(UserInfo user,OrderPayVo orderPayVo);

    /**
     * 查询订单状态
     *
     * @param userId
     * @param orderNo
     * @return
     */
    OrderVo queryOrderStatus(Integer userId, String orderNo);


}
