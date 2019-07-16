package com.guolala.zxx.entity.wx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/7/16
 * @Description:
 */
@Data
public class WxPayNotifyReq {

    @ApiModelProperty(value = "返回状态码", required = true, notes = "")
    private String return_code;

    @ApiModelProperty(value = "返回信息", required = false, notes = "")
    private String return_msg;

    @ApiModelProperty(value = "小程序ID", required = true, notes = "")
    private String appid;

    @ApiModelProperty(value = "商户号", required = true, notes = "")
    private String mch_id;

    @ApiModelProperty(value = "设备号", required = false, notes = "")
    private String device_info;

    @ApiModelProperty(value = "随机字符串", required = true, notes = "")
    private String nonce_str;

    @ApiModelProperty(value = "签名", required = true, notes = "")
    private String sign;

    @ApiModelProperty(value = "签名类型", required = false, notes = "")
    private String sign_type;

    @ApiModelProperty(value = "业务结果", required = true, notes = "")
    private String result_code;

    @ApiModelProperty(value = "错误代码", required = false, notes = "")
    private String err_code;

    @ApiModelProperty(value = "错误代码描述", required = false, notes = "")
    private String err_code_des;

    @ApiModelProperty(value = "用户标识", required = true, notes = "")
    private String openid;

    @ApiModelProperty(value = "是否关注公众账号", required = true, notes = "", allowableValues = "Y,N")
    private String is_subscribe;

    @ApiModelProperty(value = "交易类型", required = true, notes = "")
    private String trade_type;

    @ApiModelProperty(value = "付款银行", required = true, notes = "")
    private String bank_type;

    @ApiModelProperty(value = "订单金额", required = true, notes = "")
    private int total_fee;

    @ApiModelProperty(value = "应结订单金额", required = false, notes = "")
    private int settlement_total_fee;

    @ApiModelProperty(value = "货币类型", required = false, notes = "")
    private String fee_type;

    @ApiModelProperty(value = "现金支付金额", required = true, notes = "")
    private int cash_fee;

    @ApiModelProperty(value = "现金支付货币类型", required = false, notes = "")
    private String cash_fee_type;

    @ApiModelProperty(value = "总代金券金额", required = false, notes = "")
    private int coupon_fee;

    @ApiModelProperty(value = "代金券使用数量", required = false, notes = "")
    private int coupon_count;

    @ApiModelProperty(value = "代金券类型", required = false, notes = "")
    private String coupon_type_0;

    @ApiModelProperty(value = "代金券ID", required = false, notes = "")
    private String coupon_id_0;

    @ApiModelProperty(value = "单个代金券支付金额", required = false, notes = "")
    private String coupon_fee_0;

    @ApiModelProperty(value = "微信支付订单号", required = true, notes = "")
    private String transaction_id;

    @ApiModelProperty(value = "商户订单号", required = true, notes = "")
    private String out_trade_no;

    @ApiModelProperty(value = "商家数据包", required = false, notes = "")
    private String attach;

    @ApiModelProperty(value = "支付完成时间", required = true, notes = "")
    private String time_end;


}
