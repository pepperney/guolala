package com.guolala.zxx.constant;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
public enum UserType {
    COMMON("0", "普通用户"),
    ADMIN("1", "管理员");

    UserType(String code, String desc) {
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
