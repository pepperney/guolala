package com.guolala.zxx.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
@Data
@ApiModel
public class OrderCreateReq {

    @NotEmpty(message = "订单号不能为空")
    @ApiModelProperty(value = "订单编号")
    private String orderNo;


    @NotEmpty(message = "订单类型不能为空")
    @ApiModelProperty(value = "订单类型")
    private Integer orderType;


    @NotNull(message = "商品数量不能为空")
    @ApiModelProperty(value = "商品数量")
    private Integer totalQuantity;


    @ApiModelProperty(value = "订单总价")
    @NotEmpty(message = "订单总价不能为空")
    private BigDecimal totalAmt;

    @ApiModelProperty(value = "订单时间")
    @NotEmpty(message = "订单时间不能为空")
    private Date orderTime;

    @ApiModelProperty(value = "备注")
    private String orderRemark;

    @ApiModelProperty(value = "收货人姓名")
    @NotEmpty(message = "收货人姓名不能为空")
    private String receiverName;

    @ApiModelProperty(value = "收货人手机")
    @NotEmpty(message = "收货人手机不能为空")
    private String receiverMobile;

    @ApiModelProperty(value = "收货人省份")
    @NotEmpty(message = "收货人省份不能为空")
    private String addrProvince;

    @ApiModelProperty(value = "收货人城市")
    @NotEmpty(message = "收货人城市不能为空")
    private String addrCity;

    @ApiModelProperty(value = "收货人区/县")
    @NotEmpty(message = "收货人区/县不能为空")
    private String addrDistrict;

    @ApiModelProperty(value = "收货人详细地址")
    @NotEmpty(message = "收货人详细地址不能为空")
    private String addrDetails;

    @ApiModelProperty(value = "商品信息")
    @NotEmpty(message = "商品信息不能为空")
    private List<OrderGoodsInfo> goodsInfoList;


    @Data
    public static class OrderGoodsInfo {
        @ApiModelProperty(value = "商品id")
        @NotNull(message = "商品id不能为空")
        private Integer goodsId;

        @ApiModelProperty(value = "商品名称")
        @NotNull(message = "商品名称不能为空")
        private String goodsName;

        @ApiModelProperty(value = "应付价格")
        @NotNull(message = "应付价格不能为空")
        private BigDecimal payPrice;

        @ApiModelProperty(value = "商品数量")
        @NotNull(message = "商品数量不能为空")
        private String goodsQuantity;

        @ApiModelProperty(value = "商品总价")
        @NotNull(message = "商品总价不能为空")
        private BigDecimal totalPrice;
    }

}
