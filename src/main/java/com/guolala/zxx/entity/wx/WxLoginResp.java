package com.guolala.zxx.entity.wx;

import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/7/15
 * @Description:
 */
@Data
public class WxLoginResp {

    private String openid;

    private String session_key;

    private String unionid;

    private String errcode;

    private String errmsg;
}
