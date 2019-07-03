package com.guolala.zxx.constant;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:用户状态枚举
 */
public enum UserStatus {

    INVALID("0", "注销"),
    NORMAL("1", "正常");

    UserStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
