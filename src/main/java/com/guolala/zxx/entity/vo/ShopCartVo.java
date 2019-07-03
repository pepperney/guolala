package com.guolala.zxx.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * @Author: pei.nie
 * @Date:2019/4/19
 * @Description:
 */
@Data
@ApiModel
public class ShopCartVo {

    private Integer id;

    @NotEmpty(message = "商品id不能为空")
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品状态", notes = "1正常;0下架")
    private String goodsStatus;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品加个")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "折扣价格")
    private BigDecimal discountPrice;

    @ApiModelProperty(value = "商品标题")
    private String goodsTitle;

    @ApiModelProperty(value = "商品描述")
    private String goodsDesc;

    @ApiModelProperty(value = "商品数量")
    private Integer quantity = 1;

}
