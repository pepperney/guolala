package com.guolala.zxx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guolala.zxx.dao.GoodsMapper;
import com.guolala.zxx.entity.model.Goods;
import com.guolala.zxx.entity.param.GoodsParam;
import com.guolala.zxx.entity.req.GoodsReq;
import com.guolala.zxx.service.GoodsService;
import com.guolala.zxx.util.BeanUtil;
import com.guolala.zxx.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public PageInfo<GoodsReq> getGoodsListByPage(String categoryNo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        GoodsParam goodsParam = new GoodsParam(categoryNo);
        List<GoodsReq> list = BeanUtil.copyBeans(this.getGoodsList(goodsParam), GoodsReq.class);
        return new PageInfo<>(list);
    }

    @Override
    public GoodsReq getGoodsDetail(Integer goodsId) {
        Goods goods = this.selectGoodsInfo(goodsId);
        GoodsReq goodsReq = BeanUtil.copyProperties(goods, GoodsReq.class);
        return goodsReq;
    }

    @Override
    public PageInfo<GoodsReq> searchGoodsByPage(GoodsParam goodsParam) {
        PageHelper.startPage(goodsParam.getPageNum(), goodsParam.getPageSize());
        List<GoodsReq> list = BeanUtil.copyBeans(this.getGoodsList(goodsParam), GoodsReq.class);
        return new PageInfo<>(list);
    }

    @Override
    public void saveGoods(GoodsReq goodsReq) {
        ValidateUtil.validateParam(goodsReq);
        Goods goods = BeanUtil.copyProperties(goodsReq, Goods.class);
        if (null == goods.getId()) {
            goods.setCreateTime(new Date());
            goods.setUpdateTime(new Date());
            goodsMapper.insert(goods);
        } else {
            goods.setUpdateTime(new Date());
            goodsMapper.updateByPrimaryKey(goods);
        }
    }


    /**
     * 查询商品信息
     *
     * @param goodsId
     * @return
     */
    private Goods selectGoodsInfo(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    /**
     * 查询商品列表
     *
     * @param goodsParam
     * @return
     */
    private List<Goods> getGoodsList(GoodsParam goodsParam) {
        return goodsMapper.selectGoodsList(goodsParam);
    }
}



