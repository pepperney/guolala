package com.guolala.zxx.entity.req;

import com.guolala.zxx.entity.model.OrderGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/20
 * @Description:
 */
@Data
@ApiModel
public class OrderReq {


    @ApiModelProperty(value = "订单编号")
    private String orderNo;


    @ApiModelProperty(value = "订单类型")
    private Integer orderType;


    @ApiModelProperty(value = "商品数量")
    private Integer totalQuantity;


    @ApiModelProperty(value = "订单总价")
    private BigDecimal totalAmt;

    @ApiModelProperty(value = "订单时间")
    private Date orderTime;

    @ApiModelProperty(value = "备注")
    private String orderRemark;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付金额")
    private String payAmt;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "是否发送")
    private Boolean isSend;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    @ApiModelProperty(value = "是否收货")
    private Boolean isReceive;

    @ApiModelProperty(value = "收货时间")
    private Date receiveTime;

    @ApiModelProperty(value = "是否评论")
    private Boolean isComment;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userNickName;

    @ApiModelProperty(value = "用户手机")
    private String userPhone;

    @ApiModelProperty(value = "商品列表")
    private List<OrderGoods> orderGoodsList;


}
