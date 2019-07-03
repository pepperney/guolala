package com.guolala.zxx.service;

import com.github.pagehelper.PageInfo;
import com.guolala.zxx.entity.param.GoodsParam;
import com.guolala.zxx.entity.vo.GoodsVo;
import org.springframework.web.bind.annotation.RequestBody;

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
    PageInfo<GoodsVo> getGoodsListByPage(String categoryNo, Integer pageNum, Integer pageSize);

    /**
     * 查看商品详情
     * @param goodsId
     * @return
     */
    GoodsVo getGoodsDetail(Integer goodsId);

    /**
     * 搜索商品
     * @param goodsParam
     * @return
     */
    PageInfo<GoodsVo> searchGoodsByPage(GoodsParam goodsParam);

    /**
     * 保存商品
     * @param goodsVo
     */
    void saveGoods(GoodsVo goodsVo);
}
