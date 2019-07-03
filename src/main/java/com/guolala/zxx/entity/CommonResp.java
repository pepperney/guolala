package com.guolala.zxx.entity;

import com.guolala.zxx.constant.SysCode;
import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/4/10
 * @Description:
 */
@Data
public class CommonResp<T> {

    private String code;

    private String msg;

    private T data;

    public CommonResp() {
    }

    private CommonResp(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private CommonResp(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CommonResp success() {
        return new CommonResp<>(SysCode.SUCCESS.getCode(), SysCode.SUCCESS.getMsg());
    }

    public static <T> CommonResp<T> success(T data) {
        return new CommonResp<>(SysCode.SUCCESS.getCode(), SysCode.SUCCESS.getMsg(), data);
    }

    public static CommonResp fail(String code, String msg) {
        return new CommonResp<>(code, msg);
    }

    public static <T> CommonResp<T> fail(SysCode sysCode) {
        return new CommonResp<>(sysCode.getCode(), sysCode.getMsg());
    }
}
