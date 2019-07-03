package com.guolala.zxx.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
@Data
public class UserInfo {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String nickName;

    private String realName;

    private String password;

    private String mobile;

    private Integer identNo;

    private String email;

    private String userStatus;

    private String userType;

    private String wxOpenid;

    private String wxName;

    private String wxHeadimage;

    private Integer invitedBy;
}
