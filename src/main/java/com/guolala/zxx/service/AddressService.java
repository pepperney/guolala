package com.guolala.zxx.service;

import com.guolala.zxx.entity.model.ReceiverAddress;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:
 */
public interface AddressService {

    /**
     * 保存收货地址
     *
     * @param userId
     * @param address
     */
    void saveAddress(Integer userId, ReceiverAddress address);

    /**
     * 删除收货地址
     *
     * @param userId
     * @param addressId
     */
    void deleteAddress(Integer userId, Integer addressId);

    /**
     * 查询收货地址列表
     *
     * @param userId
     * @return
     */
    List<ReceiverAddress> listAddress(Integer userId);
}
