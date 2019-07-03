package com.guolala.zxx.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
import com.guolala.zxx.entity.vo.OrderCreateVo;
import com.guolala.zxx.entity.vo.OrderPayVo;
import com.guolala.zxx.entity.vo.OrderVo;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.OrderService;
import com.guolala.zxx.util.BeanUtil;
import com.guolala.zxx.util.GUtil;
import com.guolala.zxx.util.RedisUtil;
import com.guolala.zxx.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


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


    @Override
    public String getOrderNo() {
        return GUtil.getOrderNo();
    }

    @Override
    public OrderVo queryOrderDetail(Integer userId, String orderNo) {
        return null;
    }

    @Override
    @Transactional
    public String createOrder(UserInfo user, OrderCreateVo orderCreateVo) {
        log.info("订单[]创建入参={}", JSON.toJSONString(orderCreateVo));
        ValidateUtil.validateParam(orderCreateVo);
        ValidateUtil.validateParam(orderCreateVo.getGoodsInfoList());
        String key = RedisKey.ORDER_NO + orderCreateVo.getOrderNo();
        // 先查数据库，如果数据库存在，表示已提交并入库，直接返回成功
        if (null != this.queryOrderInfo(orderCreateVo.getOrderNo())) {
            return orderCreateVo.getOrderNo();
        }
        // 如果数据库没有，则表示未入库或者上次提交未执行完，如果缓存有则表示重复请求，不再处理
        if (!StringUtils.isEmpty(redisUtil.get(key))) {
            throw new GLLException(SysCode.SYS_DOING);
        }
        redisUtil.setex(key, RedisKey.ORDER_NO.expireTime, orderCreateVo.getOrderNo());
        Order order = BeanUtil.copyProperties(orderCreateVo, Order.class);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setOrderStatus(OrderStatus.PRE_ORDER.getCode());
        order.setUserId(user.getId());
        order.setUserNickName(user.getNickName());
        order.setUserPhone(user.getMobile());
        List<OrderGoods> orderGoodsList = Lists.newArrayList();
        orderCreateVo.getGoodsInfoList().stream().forEach(obj -> {
            OrderGoods orderGoods = BeanUtil.copyProperties(obj, OrderGoods.class);
            orderGoods.setCreateTime(new Date());
            orderGoods.setUpdateTime(new Date());
            orderGoods.setDeleted(Boolean.FALSE);
            orderGoods.setOrderNo(orderCreateVo.getOrderNo());
            orderGoodsList.add(orderGoods);
        });
        OrderExtend orderExtend = BeanUtil.copyProperties(orderCreateVo, OrderExtend.class);
        orderExtend.setCreateTime(new Date());
        orderExtend.setUpdateTime(new Date());
        orderExtend.setDeleted(Boolean.FALSE);
        try {
            orderMapper.insert(order);
            orderGoodsList.stream().forEach(obj -> orderGoodsMapper.insert(obj));
            orderExtendMapper.insert(orderExtend);
            log.info("订单[{}]创建成功", orderCreateVo.getOrderNo());
            return orderCreateVo.getOrderNo();
        } catch (Exception e) {
            log.error("订单[{}]创建失败", orderCreateVo.getOrderNo(), e);
            throw new GLLException(SysCode.ORDER_CREATE_FAIL);
        } finally {
            redisUtil.del(key);
        }

    }

    @Override
    public PageInfo<OrderVo> getOrderListByPage(Integer userId, Integer orderStatus, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        OrderParam orderQueryParam = new OrderParam.Builder().userId(userId).orderStatus(orderStatus).build();
        orderQueryParam.setUserId(userId);
        orderQueryParam.setOrderStatus(orderStatus);
        List<OrderVo> list = BeanUtil.copyBeans(this.queryOrderList(orderQueryParam), OrderVo.class);
        return new PageInfo<>(list);
    }

    @Override
    public boolean payForOrder(UserInfo user, OrderPayVo orderPayVo) {
        return false;
    }

    @Override
    public OrderVo queryOrderStatus(Integer userId, String orderNo) {
        Order order = this.queryOrderInfo(orderNo);
        return BeanUtil.copyProperties(order, OrderVo.class);
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
    private OrderVo queryOrderDetail(String orderNo) {
        OrderVo orderVo = null;
        Order order = this.queryOrderInfo(orderNo);
        if (null == order) {
            return orderVo;
        }
        orderVo = BeanUtil.copyProperties(order, OrderVo.class);
        OrderExtend orderExtend = orderExtendMapper.selectByOrderNo(orderNo);
        if (null != orderExtend) {
            BeanUtil.copyProperties(orderExtend, orderVo);
        }
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByOrderNo(orderNo);
        orderVo.setOrderGoodsList(orderGoodsList);
        return orderVo;
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
