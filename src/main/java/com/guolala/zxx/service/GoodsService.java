package com.guolala.zxx.service;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.param.GoodsParam;
import com.guolala.zxx.entity.req.GoodsReq;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
public interface GoodsService {

    /**
     * 根据品类获取商品列表
     * @param categoryNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<GoodsReq> getGoodsListByPage(String categoryNo, Integer pageNum, Integer pageSize);

    /**
     * 查看商品详情
     * @param goodsId
     * @return
     */
    GoodsReq getGoodsDetail(Integer goodsId);

    /**
     * 搜索商品
     * @param goodsParam
     * @return
     */
    PageInfo<GoodsReq> searchGoodsByPage(GoodsParam goodsParam);

    /**
     * 保存商品
     * @param goodsReq
     */
    void saveGoods(GoodsReq goodsReq);
}
