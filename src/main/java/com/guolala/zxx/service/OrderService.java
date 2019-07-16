package com.guolala.zxx.service;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.resp.OrderPayResp;
import com.guolala.zxx.entity.req.OrderCreateReq;
import com.guolala.zxx.entity.req.OrderPayReq;
import com.guolala.zxx.entity.req.OrderReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    OrderReq queryOrderDetail(Integer userId, String orderNo);

    /**
     * 用户提交订单
     *
     * @param user
     * @param orderCreateReq
     * @return
     */
    String createOrder(UserInfo user, OrderCreateReq orderCreateReq);

    /**
     * 分页查询用户订单列表
     *
     * @param userId
     * @param orderStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<OrderReq> getOrderListByPage(Integer userId, Integer orderStatus, int pageNum, int pageSize);

    /**
     * 查询订单状态
     *
     * @param userId
     * @param orderNo
     * @return
     */
    OrderReq queryOrderStatus(Integer userId, String orderNo);

    /**
     * 微信支付统一下单接口
     * @param orderPayReq
     * @param userInfo
     * @param request
     * @return
     * @see https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     */
    OrderPayResp wxPay(OrderPayReq orderPayReq, UserInfo userInfo, HttpServletRequest request);

    /**
     * 处理微信支付回调通知
     * @param request
     * @param response
     */
    void handleWxNotify(HttpServletRequest request, HttpServletResponse response)throws IOException;

    /**
     * 查询微信支付结果
     * @param orderNo
     * @param userInfo
     * @return
     */
    Boolean queryPayResult(String orderNo, UserInfo userInfo);
}
