package com.guolala.zxx.entity.wx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/7/16
 * @Description:
 */
@Data
public class WxPayQueryReq {

    @ApiModelProperty(value = "小程序ID", required = true, notes = "")
    private String appid;

    @ApiModelProperty(value = "商户号", required = true, notes = "")
    private String mch_id;

    @ApiModelProperty(value = "微信订单号", required = false, notes = "")
    private String transaction_id;

    @ApiModelProperty(value = "商户订单号", required = true, notes = "")
    private String out_trade_no;

    @ApiModelProperty(value = "随机字符串", required = true, notes = "")
    private String nonce_str;

    @ApiModelProperty(value = "签名", required = true, notes = "")
    private String sign;

    @ApiModelProperty(value = "签名类型", required = false, notes = "")
    private String sign_type = "MD5";
}
