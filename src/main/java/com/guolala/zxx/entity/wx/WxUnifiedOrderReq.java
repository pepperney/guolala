package com.guolala.zxx.entity.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/7/15
 * @Description:
 */
@Data
@ApiModel("统一下单请求对象")
public class WxUnifiedOrderReq {

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
    private String sign_type = "MD5";

    @ApiModelProperty(value = "商品描述", required = true, notes = "")
    private String body;

    @ApiModelProperty(value = "商品详情", required = false, notes = "")
    private String detail;

    @ApiModelProperty(value = "附加数据", required = false, notes = "")
    private String attach;

    @ApiModelProperty(value = "商户订单号", required = true, notes = "")
    private String out_trade_no;

    @ApiModelProperty(value = "标价币种", required = false, notes = "")
    private String fee_type = "CNY";

    @ApiModelProperty(value = "标价金额", required = true, notes = "")
    private int total_fee;

    @ApiModelProperty(value = "终端IP", required = true, notes = "")
    private String spbill_create_ip;

    @ApiModelProperty(value = "交易起始时间", required = false, notes = "")
    private String time_start;

    @ApiModelProperty(value = "交易结束时间", required = false, notes = "")
    private String time_expire;

    @ApiModelProperty(value = "订单优惠标记", required = false, notes = "")
    private String goods_tag;

    @ApiModelProperty(value = "通知地址", required = true, notes = "")
    private String notify_url;

    @ApiModelProperty(value = "交易类型", required = true, notes = "")
    private String trade_type = "JSAPI";

    @ApiModelProperty(value = "商品ID", required = false, notes = "")
    private String product_id;

    @ApiModelProperty(value = "指定支付方式", required = false, notes = "")
    private String limit_pay;

    @ApiModelProperty(value = "用户标识", required = true, notes = "")
    private String openid;

    @ApiModelProperty(value = "电子发票入口开放标识", required = false, notes = "")
    private String receipt;

    @ApiModelProperty(value = "场景信息", required = false, notes = "")
    private String scene_info;


}
