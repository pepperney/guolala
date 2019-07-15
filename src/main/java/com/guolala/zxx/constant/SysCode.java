package com.guolala.zxx.constant;

import com.alibaba.druid.util.StringUtils;

/**
 * @Author: pei.nie
 * @Date:2019/4/10
 * @Description:
 */
public enum SysCode {

    SUCCESS                         ("0000", "成功"),

    SYS_ERROR                       ("1000", "系统异常"),
    SYS_BUSY                        ("1001", "系统繁忙"),
    SYS_DOING                       ("1002", "请求处理中，请勿重复提交"),
    ILLEGAL_PARAM                   ("1003", "参数检验不通过"),

    ENCRYPT_ERROR                   ("1004", "加密异常"),
    DECRYPT_ERROR                   ("1005", "解密异常"),
    SIGN_ERROR                      ("1006", "签名异常"),

    TOKEN_ERROR                     ("2000", "token异常"),
    TOKEN_EXPIRE                    ("2001", "登录已过期，请重新登录"),
    USER_ERROR                      ("2002", "用户名或密码不正确"),
    USER_REGISTED                   ("2003", "用户已注册，请直接登录"),
    USER_NOT_EXIST                  ("2004", "用户不存在"),
    WX_LOGIN_FAIL                 ("2015", "登录失败,请重试"),

    FILE_NOT_FOUND                  ("2010", "文件不存在"),
    FILE_UPLOAD_FAIL                ("2011", "文件上传失败"),
    FILE_DOWNLOAD_FAIL              ("2012", "文件下载失败"),

    ORDER_NOT_EXIST                 ("3000", "订单不存在"),
    ORDER_STATUS_ERROR              ("3001", "订单状态异常"),
    ORDER_REPEAT                    ("3002", "订单已存在"),
    ORDER_CREATE_FAIL               ("3003", "订单创建失败"),



    ;
    private String code;
    private String msg;

    SysCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SysCode getByCode(String code) {
        if (StringUtils.isEmpty(code)) return null;
        for (SysCode sysCode : values()) {
            if (sysCode.getCode().equalsIgnoreCase(code)) {
                return sysCode;
            }
        }
        return null;
    }
}
