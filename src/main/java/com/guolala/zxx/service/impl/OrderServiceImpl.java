package com.guolala.zxx.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.guolala.zxx.constant.Const;
import com.guolala.zxx.constant.OrderStatus;
import com.guolala.zxx.constant.RedisKey;
import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.dao.OrderExtendMapper;
import com.guolala.zxx.dao.OrderGoodsMapper;
import com.guolala.zxx.dao.OrderMapper;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.Order;
import com.guolala.zxx.entity.model.OrderExtend;
import com.guolala.zxx.entity.model.OrderGoods;
import com.guolala.zxx.entity.param.OrderParam;
import com.guolala.zxx.entity.resp.OrderPayResp;
import com.guolala.zxx.entity.req.OrderCreateReq;
import com.guolala.zxx.entity.req.OrderPayReq;
import com.guolala.zxx.entity.req.OrderReq;
import com.guolala.zxx.entity.wx.*;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.OrderService;
import com.guolala.zxx.util.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: pei.nie
 * @Date:2019/4/20
 * @Description:
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;
    @Autowired
    private OrderExtendMapper orderExtendMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.pay.key}")
    private String wxPayKey;
    @Value("${wx.pay.merchantId}")
    private String wxPayMerchantId;
    @Value("${wx.pay.url}")
    private String wxPayUrl;
    @Value("${wx.pay.notifyUrl}")
    private String wxPayNotifyUrl;
    @Value("${wx.pay.queryUrl}")
    private String wxPayQueryUrl;
    private static final XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("_-", "_")));


    @Override
    public String getOrderNo() {
        return GUtil.getOrderNo();
    }

    @Override
    public OrderReq queryOrderDetail(Integer userId, String orderNo) {
        return null;
    }

    @Override
    @Transactional
    public String createOrder(UserInfo user, OrderCreateReq orderCreateReq) {
        log.info("订单[]创建入参={}", JSON.toJSONString(orderCreateReq));
        ValidateUtil.validateParam(orderCreateReq);
        ValidateUtil.validateParam(orderCreateReq.getGoodsInfoList());
        String key = RedisKey.ORDER_NO + orderCreateReq.getOrderNo();
        // 先查数据库，如果数据库存在，表示已提交并入库，直接返回成功
        if (null != this.queryOrderInfo(orderCreateReq.getOrderNo())) {
            return orderCreateReq.getOrderNo();
        }
        // 如果数据库没有，则表示未入库或者上次提交未执行完，如果缓存有则表示重复请求，不再处理
        if (!StringUtils.isEmpty(redisUtil.get(key))) {
            throw new GLLException(SysCode.SYS_DOING);
        }
        redisUtil.setex(key, RedisKey.ORDER_NO.expireTime, orderCreateReq.getOrderNo());
        Order order = BeanUtil.copyProperties(orderCreateReq, Order.class);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setOrderStatus(OrderStatus.PRE_ORDER.getCode());
        order.setUserId(user.getId());
        order.setUserNickName(user.getNickName());
        order.setUserPhone(user.getMobile());
        List<OrderGoods> orderGoodsList = Lists.newArrayList();
        orderCreateReq.getGoodsInfoList().stream().forEach(obj -> {
            OrderGoods orderGoods = BeanUtil.copyProperties(obj, OrderGoods.class);
            orderGoods.setCreateTime(new Date());
            orderGoods.setUpdateTime(new Date());
            orderGoods.setDeleted(Boolean.FALSE);
            orderGoods.setOrderNo(orderCreateReq.getOrderNo());
            orderGoodsList.add(orderGoods);
        });
        OrderExtend orderExtend = BeanUtil.copyProperties(orderCreateReq, OrderExtend.class);
        orderExtend.setCreateTime(new Date());
        orderExtend.setUpdateTime(new Date());
        orderExtend.setDeleted(Boolean.FALSE);
        try {
            orderMapper.insert(order);
            orderGoodsList.stream().forEach(obj -> orderGoodsMapper.insert(obj));
            orderExtendMapper.insert(orderExtend);
            log.info("订单[{}]创建成功", orderCreateReq.getOrderNo());
            return orderCreateReq.getOrderNo();
        } catch (Exception e) {
            log.error("订单[{}]创建失败", orderCreateReq.getOrderNo(), e);
            throw new GLLException(SysCode.ORDER_CREATE_FAIL);
        } finally {
            redisUtil.del(key);
        }

    }

    @Override
    public PageInfo<OrderReq> getOrderListByPage(Integer userId, Integer orderStatus, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        OrderParam orderQueryParam = new OrderParam.Builder().userId(userId).orderStatus(orderStatus).build();
        orderQueryParam.setUserId(userId);
        orderQueryParam.setOrderStatus(orderStatus);
        List<OrderReq> list = BeanUtil.copyBeans(this.queryOrderList(orderQueryParam), OrderReq.class);
        return new PageInfo<>(list);
    }

    @Override
    public OrderReq queryOrderStatus(Integer userId, String orderNo) {
        Order order = this.queryOrderInfo(orderNo);
        return BeanUtil.copyProperties(order, OrderReq.class);
    }

    @Override
    public OrderPayResp wxPay(OrderPayReq orderPayReq, UserInfo userInfo, HttpServletRequest request) {
        ValidateUtil.validateParam(orderPayReq);
        String orderNo = orderPayReq.getOrderNo();
        Order order = orderMapper.selectOrderInfo(orderNo);
        if (null == order) {
            throw new GLLException(SysCode.ORDER_NOT_EXIST);
        }
        WxUnifiedOrderReq req = new WxUnifiedOrderReq();
        req.setAppid(appId);
        req.setMch_id(wxPayMerchantId);
        req.setNonce_str(GUtil.getUUID());
        req.setBody("订单-" + orderNo);
        req.setOut_trade_no(orderNo);
        req.setTotal_fee(Integer.parseInt(order.getPayAmt()) * 100);
        req.setSpbill_create_ip(GUtil.getIpAddr(request));
        req.setTime_start(DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss"));
        req.setNotify_url(wxPayNotifyUrl);
        req.setOpenid(userInfo.getWxOpenid());
        req.setSign(WxPayUtil.sign(WxPayUtil.beanToSortString(req), wxPayKey).toUpperCase());
        xstream.alias("req", WxUnifiedOrderReq.class);
        String xml = xstream.toXML(req);
        log.info("调用微信统一下单接口入参={},url={},订单号={}", xml, wxPayUrl, orderNo);
        String result = HttpUtil.post(wxPayUrl, xml, "text/xml");
        log.info("调用微信统一下单接口出参={},url={},订单号={}", result, wxPayUrl, orderNo);
        if (StringUtils.isEmpty(result)) {
            throw new GLLException(SysCode.ORDER_PAY_FAIL);
        }
        WxUnifiedOrderResp resp = (WxUnifiedOrderResp) xstream.fromXML(result);
        if (Const.FAIL.equals(resp.getReturn_code()) || Const.FAIL.equals(resp.getResult_code())) {
            throw new GLLException(SysCode.ORDER_PAY_FAIL, StringUtils.isEmpty(resp.getErr_code_des()) ? resp.getReturn_msg() : resp.getErr_code_des());
        }
        OrderPayResp orderPayResp = new OrderPayResp();
        orderPayResp.setAppid(appId);
        orderPayResp.setNonceStr(req.getNonce_str());
        orderPayResp.setPackages("prepay_id=" + resp.getPrepay_id());
        orderPayResp.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
        //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
        String text = "appId=" + appId
                + "&nonceStr=" + req.getNonce_str()
                + "&package=prepay_id=" + resp.getPrepay_id()
                + "&signType=MD5&timeStamp=" + orderPayResp.getTimeStamp();
        String paySign = WxPayUtil.sign(text, wxPayKey).toUpperCase();
        orderPayResp.setPaySign(paySign);
        return orderPayResp;
    }

    @Override
    public void handleWxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("return_code", Const.FAIL);
        resultMap.put("return_msg", "ERROR");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        }
        String notityXml = sb.toString();
        WxPayNotifyReq payNotifyReq = (WxPayNotifyReq) xstream.fromXML(notityXml);
        log.info("收到微信支付通知消息,orderNo={},msg={}", payNotifyReq.getOut_trade_no(), JsonUtil.toJson(payNotifyReq));
        String returnCode = payNotifyReq.getReturn_code();
        if (Const.SUCCESS.equals(returnCode)) {
            //回调验签时需要去除sign和空值参数
            Map<String, String> validParams = WxPayUtil.filtParam(payNotifyReq);
            String text = WxPayUtil.beanToSortString(validParams);
            //验证签名是否正确
            if (WxPayUtil.verifySign(text, payNotifyReq.getSign(), wxPayKey)) {
                String orderNo = payNotifyReq.getOut_trade_no();
                Order order = orderMapper.selectOrderInfo(orderNo);
                // 仅当订单状态为待支付才继续处理--可以避免重复通知
                if (null != order && order.getOrderStatus().equals(OrderStatus.TO_PAY.getCode())) {
                    // 验证返回的金额与系统订单的金额是否一致
                    if (Integer.parseInt(order.getPayAmt()) * 100 != payNotifyReq.getTotal_fee()) {
                        resultMap.put("return_code", Const.FAIL);
                        resultMap.put("return_msg", "金额不一致");
                    }
                    // 更新订单状态为待发货(已支付)
                    order.setOrderStatus(OrderStatus.TO_SEND.getCode());
                    order.setUpdateTime(new Date());
                    orderMapper.updateByPrimaryKey(order);
                    resultMap.put("return_code", Const.SUCCESS);
                    resultMap.put("return_msg", "OK");
                }
            } else {
                resultMap.put("return_msg", "验签失败");
            }
        } else {
            resultMap.put("return_msg", "通知报文为空");
        }
        String resultXml = xstream.toXML(resultMap);
        try (BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
            out.write(resultXml.getBytes());
            out.flush();
        }
    }

    @Override
    public Boolean queryPayResult(String orderNo, UserInfo userInfo) {
        Order order = orderMapper.selectOrderInfo(orderNo);
        if (null == order) {
            throw new GLLException(SysCode.ORDER_NOT_EXIST);
        }
        WxPayQueryReq wxPayQueryReq = new WxPayQueryReq();
        wxPayQueryReq.setAppid(appId);
        wxPayQueryReq.setMch_id(wxPayMerchantId);
        wxPayQueryReq.setOut_trade_no(orderNo);
        wxPayQueryReq.setNonce_str(GUtil.getUUID());
        wxPayQueryReq.setSign(WxPayUtil.sign(WxPayUtil.beanToSortString(wxPayQueryReq), wxPayKey).toUpperCase());
        String xml = xstream.toXML(wxPayQueryReq);
        log.info("调用微信支付查询接口入参={},url={},订单号={}", xml, wxPayQueryUrl, orderNo);
        String result = HttpUtil.post(wxPayQueryUrl, xml, "text/xml");
        log.info("调用微信支付查询接口出参={},url={},订单号={}", result, wxPayQueryUrl, orderNo);
        if (StringUtils.isEmpty(result)) {
            throw new GLLException(SysCode.ORDER_PAY_QUERY_FAIL);
        }
        WxPayQueryResp resp = (WxPayQueryResp) xstream.fromXML(result);
        if (Const.FAIL.equals(resp.getReturn_code()) || Const.FAIL.equals(resp.getResult_code())) {
            throw new GLLException(SysCode.ORDER_PAY_QUERY_FAIL, StringUtils.isEmpty(resp.getErr_code_des()) ? resp.getReturn_msg() : resp.getErr_code_des());
        }
        // 交易成功
        if (!Const.SUCCESS.equals(resp.getTrade_state())) return false;
        // 当订单状态为待支付时更新为待发货(已支付)
        if (order.getOrderStatus().equals(OrderStatus.TO_PAY.getCode())) {
            order.setOrderStatus(OrderStatus.TO_SEND.getCode());
            order.setUpdateTime(new Date());
            orderMapper.updateByPrimaryKey(order);
        }
        return true;
    }


    /* --------------------------------------私-----------------------------------------------*/
    /* --------------------------------------有-----------------------------------------------*/
    /* --------------------------------------方-----------------------------------------------*/
    /* --------------------------------------法-----------------------------------------------*/


    /**
     * 查询订单状态
     *
     * @param orderNo
     * @return
     */
    private int queryOrderStatus(String orderNo) {
        Order orderInfo = this.queryOrderInfo(orderNo);
        if (null == orderInfo) {
            throw new GLLException(SysCode.ORDER_NOT_EXIST);
        }
        return orderInfo.getOrderStatus();
    }

    /**
     * 查询订单简单信息
     *
     * @param orderNo
     * @return
     */
    private Order queryOrderInfo(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            throw new GLLException(SysCode.ILLEGAL_PARAM, "订单号不能为空");
        }
        return orderMapper.selectOrderInfo(orderNo);
    }

    /**
     * 查询订单详情
     *
     * @param orderNo
     * @return
     */
    private OrderReq queryOrderDetail(String orderNo) {
        OrderReq orderReq = null;
        Order order = this.queryOrderInfo(orderNo);
        if (null == order) {
            return orderReq;
        }
        orderReq = BeanUtil.copyProperties(order, OrderReq.class);
        OrderExtend orderExtend = orderExtendMapper.selectByOrderNo(orderNo);
        if (null != orderExtend) {
            BeanUtil.copyProperties(orderExtend, orderReq);
        }
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByOrderNo(orderNo);
        orderReq.setOrderGoodsList(orderGoodsList);
        return orderReq;
    }

    /**
     * 更新订单状态
     *
     * @param orderNo       订单号
     * @param currentStatus 订单当前状态
     * @param tartgetStatus 要更新的目标状态
     */
    private void updateOrderStatus(String orderNo, int currentStatus, int tartgetStatus) {
        if (currentStatus != this.queryOrderStatus(orderNo)) {
            throw new GLLException(SysCode.ORDER_STATUS_ERROR);
        }
        orderMapper.updateOrderStatus(orderNo, tartgetStatus);
    }

    /**
     * 查询订单列表
     * 调用此接口时，orderQueryParam的属性不允许全部为空
     *
     * @param orderParam
     * @return
     */
    private List<Order> queryOrderList(OrderParam orderParam) {
        return orderMapper.selectOrders(orderParam);
    }


}
