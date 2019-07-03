package com.guolala.zxx.entity.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "用户对象")
public class User implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "用户昵称", notes = "")
    private String nickName;

    @ApiModelProperty(value = "用户真实名字", notes = "")
    private String realName;

    @ApiModelProperty(value = "用户密码", notes = "")
    private String password;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nickName=").append(nickName);
        sb.append(", realName=").append(realName);
        sb.append(", password=").append(password);
        sb.append(", mobile=").append(mobile);
        sb.append(", identNo=").append(identNo);
        sb.append(", email=").append(email);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", userType=").append(userType);
        sb.append(", wxOpenid=").append(wxOpenid);
        sb.append(", wxName=").append(wxName);
        sb.append(", wxHeadimage=").append(wxHeadimage);
        sb.append(", invitedBy=").append(invitedBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}