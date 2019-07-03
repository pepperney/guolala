package com.guolala.zxx.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.guolala.zxx.constant.RedisKey;
import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.dao.ReceiverAddressMapper;
import com.guolala.zxx.entity.model.ReceiverAddress;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.AddressService;
import com.guolala.zxx.util.JsonUtil;
import com.guolala.zxx.util.RedisUtil;
import com.guolala.zxx.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ReceiverAddressMapper receiverAddressMapper;


    @Override
    public void saveAddress(Integer userId, ReceiverAddress address) {
        ValidateUtil.validateParam(address);
        List<ReceiverAddress> existlist = this.listAddress(userId);
        // 如果此地址需要设为默认地址,查询原来是否已有地址数据，如果有则将原来的默认地址置为非默认地址
        if (address.getIsDefault() && !CollectionUtils.isEmpty(existlist)) {
            List<ReceiverAddress> defaultlist = existlist.stream().filter(obj -> Boolean.TRUE.equals(obj.getIsDefault())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(defaultlist)) {
                defaultlist.stream().forEach(obj -> {
                    obj.setIsDefault(Boolean.FALSE);
                    receiverAddressMapper.updateByPrimaryKey(obj);
                });
            }
        }
        address.setUserId(userId);
        if (null == address.getId()) {
            address.setCreateTime(new Date());
            address.setUpdateTime(new Date());
            //只有一条地址的时候设为默认地址
            if (CollectionUtils.isEmpty(existlist)) {
                address.setIsDefault(Boolean.TRUE);
            } else if (existlist.size() > 10) {
                throw new GLLException(SysCode.ILLEGAL_PARAM, "您最多可保存10条收货地址，请删除不常用的地址后再添加");
            }
            receiverAddressMapper.insert(address);
        } else {
            address.setUpdateTime(new Date());
            receiverAddressMapper.updateByPrimaryKey(address);
        }
        this.refreshCacheWhenUpdate(userId);
    }

    @Override
    public void deleteAddress(Integer userId, Integer addressId) {
        ReceiverAddress address = receiverAddressMapper.selectByPrimaryKey(addressId);
        address.setDeleted(Boolean.TRUE);
        address.setUpdateTime(new Date());
        receiverAddressMapper.updateByPrimaryKey(address);
        this.refreshCacheWhenUpdate(userId);
    }

    @Override
    public List<ReceiverAddress> listAddress(Integer userId) {
        List<ReceiverAddress> list = null;
        String cacheValue = redisUtil.get(RedisKey.USER_ADDRESS.key + userId);
        if (StringUtils.isEmpty(cacheValue)) {
            list = receiverAddressMapper.selectAddressByUserId(userId);
        } else {
            list = JsonUtil.jsonToGenericObject(cacheValue, new TypeReference<List<ReceiverAddress>>() {
            });
        }
        return list;
    }

    /**
     * 用户地址变更时刷新缓存
     * @param userId
     */
    private void refreshCacheWhenUpdate(Integer userId) {
        List<ReceiverAddress> addressList = this.listAddress(userId);
        redisUtil.setex(RedisKey.USER_ADDRESS.key + userId, RedisKey.USER_ADDRESS.expireTime, JSON.toJSONString(addressList));
    }

}
