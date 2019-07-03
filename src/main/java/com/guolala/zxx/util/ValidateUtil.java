package com.guolala.zxx.util;

import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.exception.GLLException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @Author: pei.nie
 * @Date:2019/4/16
 * @Description:
 */
public class ValidateUtil {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 验证参数是否合法
     *
     * @param obj
     */
    public static void validateParam(Object obj) {
        if (null == obj) {
            throw new GLLException(SysCode.ILLEGAL_PARAM, "请求参数为空");
        }
        Set<ConstraintViolation<Object>> validateSet = VALIDATOR.validate(obj, Default.class);
        for (ConstraintViolation<Object> constraintViolation : validateSet) {
            String msg = "字段[" + constraintViolation.getPropertyPath().toString() + "]不合法,原因:" + constraintViolation.getMessage();
            throw new GLLException(SysCode.ILLEGAL_PARAM, msg);
        }

    }
}
