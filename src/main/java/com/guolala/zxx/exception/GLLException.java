package com.guolala.zxx.exception;

import com.guolala.zxx.constant.SysCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
/**
 * @Author: pei.nie
 * @Date:2019/4/10
 * @Description:
 */
public class GLLException extends RuntimeException {

    private String code;

    private String msg;

    public GLLException(SysCode sysCode) {
        super(sysCode.getMsg());
        this.code = sysCode.getCode();
    }

    public GLLException(String code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
    }

    public GLLException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public GLLException(SysCode syscode, String msg) {
        super(msg);
        this.code = syscode.getCode();
    }


    public GLLException(String msg) {
        this(SysCode.SYS_ERROR.getCode(), msg);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return this.getMessage();
    }
}
