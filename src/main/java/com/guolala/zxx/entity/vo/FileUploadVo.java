package com.guolala.zxx.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: pei.nie
 * @Date:2019/4/18
 * @Description:
 */
@Data
@ApiModel
public class FileUploadVo {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件后缀")
    private String fileSuffix;

    @NotEmpty(message = "文件内容不能为空")
    @ApiModelProperty(value = "文件内容")
    private String fileData;

    @NotEmpty(message = "文件类型不能为空")
    @ApiModelProperty(value = "文件类型")
    private String bizType;

    @ApiModelProperty(value = "业务编号")
    private Integer bizNo;
}
