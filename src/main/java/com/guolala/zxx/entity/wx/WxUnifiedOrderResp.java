package com.guolala.zxx.entity.wx;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: pei.nie
 * @Date:2019/7/15
 * @Description:
 */
public class WxUnifiedOrderResp {

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

    @ApiModelProperty(value = "业务结果", required = false, notes = "")
    private String result_code;

    @ApiModelProperty(value = "错误代码", required = false, notes = "")
    private String err_code;

    @ApiModelProperty(value = "错误代码描述", required = false, notes = "")
    private String err_code_des;

    @ApiModelProperty(value = "交易类型", required = false, notes = "")
    private String trade_type;

    @ApiModelProperty(value = "预支付交易会话标识", required = false, notes = "")
    private String prepay_id;

    @ApiModelProperty(value = "二维码链接", required = false, notes = "")
    private String code_url;

}
