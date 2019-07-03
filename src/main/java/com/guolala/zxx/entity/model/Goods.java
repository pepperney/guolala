package com.guolala.zxx.entity.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("商品对象")
@Data
public class Goods implements Serializable {
    private Integer id;

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

    private String goodsDetail;

    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", goodsStatus=").append(goodsStatus);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", preSaleQuatity=").append(preSaleQuatity);
        sb.append(", catNo=").append(catNo);
        sb.append(", catName=").append(catName);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", discountPrice=").append(discountPrice);
        sb.append(", goodsTitle=").append(goodsTitle);
        sb.append(", goodsDesc=").append(goodsDesc);
        sb.append(", goodsTags=").append(goodsTags);
        sb.append(", goodsStock=").append(goodsStock);
        sb.append(", goodsBanners=").append(goodsBanners);
        sb.append(", goodsThumb=").append(goodsThumb);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", deleted=").append(deleted);
        sb.append(", goodsDetail=").append(goodsDetail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}