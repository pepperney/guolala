package com.guolala.zxx.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: pei.nie
 * @Date:2019/4/24
 * @Description:
 */
@Data
@ApiModel
public class GoodsVo implements Serializable {

    private static final long serialVersionUID = -5844954811407577372L;

    @ApiModelProperty(value = "商品类型")
    private Boolean goodsType;

    @ApiModelProperty(value = "商品状态")
    private Boolean goodsStatus;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "预售数量")
    private Integer preSaleQuatity;

    @ApiModelProperty(value = "品类编码")
    private String catNo;

    @ApiModelProperty(value = "品类名称")
    private String catName;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "商品折扣价格")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "商品标题")
    private String goodsTitle;

    @ApiModelProperty(value = "商品描述")
    private String goodsDesc;

    @ApiModelProperty(value = "商品标签")
    private String goodsTags;

    @ApiModelProperty(value = "商品库存")
    private Integer goodsStock;

    @ApiModelProperty(value = "商品banner")
    private String goodsBanners;

    @ApiModelProperty(value = "商品缩略图")
    private String goodsThumb;

}
