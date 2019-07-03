package com.guolala.zxx.entity.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "品类对象")
@Data
public class Category implements Serializable {
    private Integer id;

    @ApiModelProperty(value = "品类名称", notes = "")
    private String catName;

    @ApiModelProperty(value = "品类编码", notes = "")
    private String catNo;

    @ApiModelProperty(value = "父级品类id")
    private Integer parentId;

    @ApiModelProperty(value = "品类状态", notes = "0:下架;1:启用")
    private Integer catStatus;

    @ApiModelProperty(value = "品类顺序", notes = "")
    private Integer sortOrder;

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
        sb.append(", catName=").append(catName);
        sb.append(", catNo=").append(catNo);
        sb.append(", parentId=").append(parentId);
        sb.append(", catStatus=").append(catStatus);
        sb.append(", sortOrder=").append(sortOrder);
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