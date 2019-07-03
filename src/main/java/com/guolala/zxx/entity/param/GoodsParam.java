package com.guolala.zxx.entity.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: pei.nie
 * @Date:2019/4/24
 * @Description:
 */
@Data
public class GoodsParam extends PageParam {

    @ApiModelProperty(value = "商品类型")
    private Boolean goodsType;

    @ApiModelProperty(value = "商品状态")
    private Boolean goodsStatus;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "品类编号")
    private String catNo;

    @ApiModelProperty(value = "品类名称")
    private String catName;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "折扣价格")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "商品标题")
    private String goodsTitle;

    @ApiModelProperty(value = "商品描述")
    private String goodsDesc;

    @ApiModelProperty(value = "商品标签")
    private String goodsTags;

    // 按价格排序
    @ApiModelProperty(value = "按价格排序")
    private Boolean sortByPrice;
    // 按成交量排序
    @ApiModelProperty(value = "按销量排序")
    private Boolean sortBySales;

    public GoodsParam(String catNo) {
        this.catNo = catNo;
    }

    public GoodsParam() {
    }
}
