package com.guolala.zxx.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: pei.nie
 * @Date:2019/4/16
 * @Description:
 */
@Data
@ApiModel
public class CategoryReq {


    private Integer id;

    @NotEmpty(message = "品类名称不能为空")
    @ApiModelProperty(value = "品类名称", notes = "")
    private String catName;

    @ApiModelProperty(value = "品类编码", notes = "")
    @NotEmpty(message = "品类编号不能为空")
    private String catNo;

    @ApiModelProperty(value = "父级品类id")
    private Integer parentId;

    @ApiModelProperty(value = "品类状态", notes = "0:下架;1:启用")
    private Integer catStatus;
}
