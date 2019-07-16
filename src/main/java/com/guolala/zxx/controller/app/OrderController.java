package com.guolala.zxx.controller.app;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.resp.OrderPayResp;
import com.guolala.zxx.entity.req.OrderCreateReq;
import com.guolala.zxx.entity.req.OrderPayReq;
import com.guolala.zxx.entity.req.OrderReq;
import com.guolala.zxx.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: pei.nie
 * @Date:2019/4/11
 * @Description:
 */
@Api(value = "【app】 订单相关接口", tags = {"OrderController"})
@RestController
@RequestMapping("/app/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单号
     *
     * @return
     */
    @GetMapping("/v1/getOrderNo")
    @ApiOperation(value = "获取订单编号", httpMethod = "GET")
    public String getOrderNo() {
        return orderService.getOrderNo();
    }

    /**
     * 创建订单
     *
     * @param user
     * @param orderCreateReq
     * @return
     */
    @PostMapping("/v1/createOrder")
    @ApiOperation(value = "创建订单", httpMethod = "POST")
    public String createOrder(@ApiIgnore UserInfo user,
                              @RequestBody OrderCreateReq orderCreateReq) {
        return orderService.createOrder(user, orderCreateReq);
    }

    /**
     * 查询订单详情
     *
     * @param user
     * @param orderNo
     * @return
     */
    @ApiOperation(value = "查询订单详情", httpMethod = "GET")
    @GetMapping("/v1/orderDetail")
    public OrderReq queryOrderDetail(@ApiIgnore UserInfo user,
                                     @ApiParam(name = "orderNo", value = "订单编号") String orderNo) {
        return orderService.queryOrderDetail(user.getId(), orderNo);
    }

    /**
     * 查询订单状态
     *
     * @param user
     * @param orderNo
     * @return
     */
    @GetMapping("/v1/orderStatus")
    @ApiOperation(value = "查询订单状态", httpMethod = "GET")
    public OrderReq queryOrderStatus(@ApiIgnore UserInfo user,
                                     @ApiParam(name = "orderNo", value = "订单编号") String orderNo) {
        return orderService.queryOrderStatus(user.getId(), orderNo);
    }


    /**
     * 分页查询用户订单
     *
     * @param user
     * @param orderStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/v1/getOrderList")
    @ApiOperation(value = "分页查询用户的订单", httpMethod = "GET")
    public PageInfo<OrderReq> getOrderListByPage(@ApiIgnore UserInfo user,
                                                 @ApiParam(name = "orderStatus", value = "订单状态") @RequestParam("orderStatus") Integer orderStatus,
                                                 @ApiParam(name = "pageNum", value = "分页页码") @RequestParam("pageNum") int pageNum,
                                                 @ApiParam(name = "pageSize", value = "每页条数") @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        return orderService.getOrderListByPage(user.getId(), orderStatus, pageNum, pageSize);
    }


    /**
     * 订单支付
     *
     * @param user
     * @param orderPayReq
     * @return
     */
    @PostMapping("/v1/wxPay")
    @ApiOperation(value = "订单使用微信支付", httpMethod = "POST")
    public OrderPayResp payForOrder(@ApiIgnore UserInfo user, @RequestBody OrderPayReq orderPayReq, HttpServletRequest request) {
        return orderService.wxPay(orderPayReq, user, request);
    }


    /**
     * 微信支付回调通知
     * @param request
     * @param response
     */
    @PostMapping("/v1/wxPayNotify")
    @ApiOperation(value = "微信支付回调通知", httpMethod = "POST", hidden = true)
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        orderService.handleWxNotify(request, response);
    }

}
