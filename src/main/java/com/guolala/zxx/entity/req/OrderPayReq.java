package com.guolala.zxx.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: pei.nie
 * @Date:2019/7/3
 * @Description:
 */
@Data
@ApiModel(value = "订单支付对象")
public class OrderPayReq {

    @ApiModelProperty(value = "订单编号")
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "支付金额")
    private String payAmount;
}
