package com.guolala.zxx.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/4/24
 * @Description:订单查询参数封装类
 */
@Data
@ApiModel
public class OrderParam {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户手机")
    private String userPhone;

    @ApiModelProperty(value = "用户订单id")
    private Integer orderId;

    @ApiModelProperty(value = "用户订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "是否发货")
    private Boolean isSend;

    @ApiModelProperty(value = "是否收货")
    private Boolean isReceive;

    @ApiModelProperty(value = "是否评论")
    private Boolean isComment;

    private OrderParam() {

    }

    private OrderParam(Builder builder) {
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.userPhone = builder.userPhone;
        this.orderNo = builder.orderNo;
        this.orderStatus = builder.orderStatus;
        this.isSend = builder.isSend;
        this.isReceive = builder.isReceive;
        this.isComment = builder.isComment;
    }


    public static class Builder {
        @ApiModelProperty(value = "")
        private Integer userId;
        @ApiModelProperty(value = "")
        private String userPhone;
        @ApiModelProperty(value = "")
        private Integer orderId;
        @ApiModelProperty(value = "")
        private String orderNo;
        @ApiModelProperty(value = "")
        private Integer orderStatus;
        @ApiModelProperty(value = "")
        private Boolean isSend;
        @ApiModelProperty(value = "")
        private Boolean isReceive;
        @ApiModelProperty(value = "")
        private Boolean isComment;

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder userPhone(String userPhone) {
            this.userPhone = userPhone;
            return this;
        }

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder orderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }

        public Builder orderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder isSend(Boolean isSend) {
            this.isSend = isSend;
            return this;
        }

        public Builder isReceive(Boolean isReceive) {
            this.isReceive = isReceive;
            return this;
        }

        public Builder isComment(Boolean isComment) {
            this.isComment = isComment;
            return this;
        }


        public OrderParam build() {
            return new OrderParam(this);
        }

    }
}
