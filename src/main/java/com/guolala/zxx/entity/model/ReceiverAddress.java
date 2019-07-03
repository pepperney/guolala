package com.guolala.zxx.entity.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "收货地址对象")
public class ReceiverAddress implements Serializable {
    @ApiModelProperty(value = "收货地址id", notes = "")
    private Integer id;

    @ApiModelProperty(value = "收货人id", notes = "")
    private Integer userId;

    @NotEmpty(message = "收货人姓名不能为空")
    @ApiModelProperty(value = "收货人姓名", notes = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人手机", notes = "")
    @NotEmpty(message = "收货人手机号不能为空")
    private String receiverMobile;

    @ApiModelProperty(value = "收货人地址-省", notes = "")
    @NotEmpty(message = "收货地址省不能为空")
    private String addrProvince;

    @ApiModelProperty(value = "收货人地址-市", notes = "")
    @NotEmpty(message = "收货地址市不能为空")
    private String addrCity;

    @ApiModelProperty(value = "收货人地址-区/县", notes = "")
    @NotEmpty(message = "收货地址区/县不能为空")
    private String addrDistrict;

    @ApiModelProperty(value = "收货地址详细", notes = "")
    @NotEmpty(message = "收货地址详细不能为空")
    private String addrDetails;

    @ApiModelProperty(value = "邮政编码", notes = "")
    private String zipCode;

    @ApiModelProperty(value = "是否默认地址", notes = "")
    @NotEmpty(message = "是否默认地址未勾选")
    private Boolean isDefault;

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
        sb.append(", userId=").append(userId);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", receiverMobile=").append(receiverMobile);
        sb.append(", addrProvince=").append(addrProvince);
        sb.append(", addrCity=").append(addrCity);
        sb.append(", addrDistrict=").append(addrDistrict);
        sb.append(", addrDetails=").append(addrDetails);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", isDefault=").append(isDefault);
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