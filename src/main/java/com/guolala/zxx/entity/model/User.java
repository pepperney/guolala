package com.guolala.zxx.entity.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "用户对象")
@Builder
public class User implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "用户昵称", notes = "")
    private String nickName;

    @ApiModelProperty(value = "用户真实名字", notes = "")
    private String realName;

    @ApiModelProperty(value = "用户密码", notes = "")
    private String password;

    @ApiModelProperty(value = "用户性别", notes = "")
    private String gender;

    @ApiModelProperty(value = "用户手机号", notes = "")
    private String mobile;

    @ApiModelProperty(value = "用户身份证号", notes = "")
    private Integer identNo;

    @ApiModelProperty(value = "用户邮箱", notes = "")
    private String email;

    @ApiModelProperty(value = "用户状态", notes = "")
    private String userStatus;

    @ApiModelProperty(value = "用户类型", notes = "")
    private String userType;

    @ApiModelProperty(value = "微信openid", notes = "")
    private String wxOpenid;

    @ApiModelProperty(value = "微信昵称", notes = "")
    private String wxName;

    @ApiModelProperty(value = "微信头像", notes = "")
    private String wxHeadimage;

    @ApiModelProperty(value = "邀请人id", notes = "")
    private Integer invitedBy;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private String createBy;

    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private String updateBy;

    @ApiModelProperty(hidden = true)
    private Boolean deleted;

    private static final long serialVersionUID = 1L;



}