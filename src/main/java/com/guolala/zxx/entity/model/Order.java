package com.guolala.zxx.entity.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;

    @ApiModelProperty(value = "订单总数")
    private Integer totalQuantity;

    @ApiModelProperty(value = "订单总金额")
    private Long totalAmt;

    @ApiModelProperty(value = "订单时间")
    private Date orderTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付金额")
    private String payAmt;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "是否发货")
    private Boolean isSend = false;

    @ApiModelProperty(value = "发货时间")
    private Date sendTime;

    @ApiModelProperty(value = "是否收货")
    private Boolean isReceive = false;

    @ApiModelProperty(value = "收货时间")
    private Date receiveTime;

    @ApiModelProperty(value = "是否评论")
    private Boolean isComment = false;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userNickName;

    @ApiModelProperty(value = "用户手机")
    private String userPhone;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private String createBy;

    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private String updateBy;

    @ApiModelProperty(hidden = true)
    private Boolean deleted;

    @ApiModelProperty(value = "")
    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", orderType=").append(orderType);
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", totalAmt=").append(totalAmt);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", payAmt=").append(payAmt);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", isSend=").append(isSend);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", isReceive=").append(isReceive);
        sb.append(", receiveTime=").append(receiveTime);
        sb.append(", isComment=").append(isComment);
        sb.append(", userId=").append(userId);
        sb.append(", userNickName=").append(userNickName);
        sb.append(", userPhone=").append(userPhone);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}