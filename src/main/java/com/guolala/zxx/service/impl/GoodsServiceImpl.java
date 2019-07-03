package com.guolala.zxx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guolala.zxx.dao.GoodsMapper;
import com.guolala.zxx.entity.model.Goods;
import com.guolala.zxx.entity.param.GoodsParam;
import com.guolala.zxx.entity.vo.GoodsVo;
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
    public PageInfo<GoodsVo> getGoodsListByPage(String categoryNo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        GoodsParam goodsParam = new GoodsParam(categoryNo);
        List<GoodsVo> list = BeanUtil.copyBeans(this.getGoodsList(goodsParam), GoodsVo.class);
        return new PageInfo<>(list);
    }

    @Override
    public GoodsVo getGoodsDetail(Integer goodsId) {
        Goods goods = this.selectGoodsInfo(goodsId);
        GoodsVo goodsVo = BeanUtil.copyProperties(goods, GoodsVo.class);
        return goodsVo;
    }

    @Override
    public PageInfo<GoodsVo> searchGoodsByPage(GoodsParam goodsParam) {
        PageHelper.startPage(goodsParam.getPageNum(), goodsParam.getPageSize());
        List<GoodsVo> list = BeanUtil.copyBeans(this.getGoodsList(goodsParam), GoodsVo.class);
        return new PageInfo<>(list);
    }

    @Override
    public void saveGoods(GoodsVo goodsVo) {
        ValidateUtil.validateParam(goodsVo);
        Goods goods = BeanUtil.copyProperties(goodsVo, Goods.class);
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



