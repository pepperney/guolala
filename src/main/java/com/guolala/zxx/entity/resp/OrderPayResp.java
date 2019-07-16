package com.guolala.zxx.entity.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/7/16
 * @Description:
 */
@Data
@ApiModel(value = "微信支付接口返回数据",description = "客户端使用此数据调用wx.requestPayment(OBJECT)")
public class OrderPayResp {

    @ApiModelProperty(value = "时间戳")
    private String timeStamp;

    @ApiModelProperty(value = "签名")
    private String paySign;

    @ApiModelProperty(value = "小程序ID")
    private String appid;

    @ApiModelProperty(value = "随机字符串")
    private String nonceStr;

    @JsonProperty("package")
    @ApiModelProperty(value = "数据包")
    private String packages;
}
