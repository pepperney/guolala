package com.guolala.zxx.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: pei.nie
 * @Date:2019/7/3
 * @Description:
 */
@Data
@ApiModel(value = "订单支付对象")
public class OrderPayVo {

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiParam(value = "支付金额")
    private BigDecimal payAmount;
}
