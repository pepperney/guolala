package com.guolala.zxx.constant;

/**
 * @Author: pei.nie
 * @Date:2019/6/30
 * @Description:
 */
public enum BannerType {

    INDEX("1", "首页"),
    ;

    BannerType(String code, String desc) {
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
