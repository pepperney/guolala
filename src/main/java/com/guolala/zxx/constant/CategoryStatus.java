package com.guolala.zxx.constant;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
public enum CategoryStatus {

    OFF(0, "注销"),
    ON(1, "正常");

    CategoryStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
