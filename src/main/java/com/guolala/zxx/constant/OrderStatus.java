package com.guolala.zxx.constant;


/**
 * @Author: pei.nie
 * @Date:2019/4/20
 * @Description:订单状态枚举
 */
public enum OrderStatus {

    PRE_ORDER           (0, "预订"),

    TO_CONFIRM          (1, "待确认"),

    TO_PAY              (2, "待支付"),

    TO_SEND             (3, "待发货"),

    TO_RECEIVE          (4, "待收货"),

    TO_COMMONT          (5, "待评价"),

    CLOSED              (6, "已完成"),

    ;

    OrderStatus(int code, String desc) {
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

    public OrderStatus getByCode(int code) {
        OrderStatus result = PRE_ORDER;
        for (OrderStatus orderStatus : values()) {
            if (code == orderStatus.getCode()) {
                result = orderStatus;
                break;
            }
        }
        return result;
    }
}
