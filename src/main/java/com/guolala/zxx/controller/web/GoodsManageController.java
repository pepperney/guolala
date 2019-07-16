package com.guolala.zxx.controller.web;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.param.GoodsParam;
import com.guolala.zxx.entity.req.GoodsReq;
import com.guolala.zxx.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
@RestController
@RequestMapping("/web/goods")
@Api(value = "【web】 商品相关接口", tags = {"GoodsController"})
public class GoodsManageController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/save")
    @ApiOperation(value = "保存商品", httpMethod = "POST", notes = "")
    public void saveGoods(@RequestBody GoodsReq goodsReq) {
        goodsService.saveGoods(goodsReq);
    }


    /**
     * 商品列表
     *
     * @param categoryNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询商品列表", httpMethod = "GET", notes = "")
    public PageInfo<GoodsReq> getGoodsListByPage(@ApiParam(name = "categoryNo", value = "品类编码") String categoryNo,
                                                 @ApiParam(name = "pageNum", value = "分页的页码") @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @ApiParam(name = "pageSize", value = "每页条数") Integer pageSize) {
        return goodsService.getGoodsListByPage(categoryNo, pageNum, pageSize);
    }

    /**
     * 商品详情
     *
     * @param goodsId
     * @return
     */
    @GetMapping("/goodsDetail")
    @ApiOperation(value = "查询商品详情", httpMethod = "GET", notes = "")
    public GoodsReq getGoodsDetail(@ApiParam(name = "goodsId", value = "商品id") Integer goodsId) {
        return goodsService.getGoodsDetail(goodsId);
    }

    /**
     * 搜索商品
     *
     * @param goodsParam
     * @return
     */
    @PostMapping("/search")
    @ApiOperation(value = "搜索商品", httpMethod = "POST", notes = "")
    public PageInfo<GoodsReq> serachGoodsListByPage(@RequestBody GoodsParam goodsParam) {
        return goodsService.searchGoodsByPage(goodsParam);
    }


}
