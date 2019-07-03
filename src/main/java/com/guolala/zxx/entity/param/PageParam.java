package com.guolala.zxx.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
@ApiModel
public abstract class PageParam {

    @ApiModelProperty(value = "")
    private int pageNum = 1;

    @ApiModelProperty(value = "")
    private int pageSize = 10;

    public int getPageNum() {
        return pageNum < 1 ? 1 : pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return (pageSize < 1 || pageSize > 10) ? 10 : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
