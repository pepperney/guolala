package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.ReceiverAddress;
import java.util.List;

public interface ReceiverAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReceiverAddress record);

    ReceiverAddress selectByPrimaryKey(Integer id);

    List<ReceiverAddress> selectAll();

    int updateByPrimaryKey(ReceiverAddress record);

    List<ReceiverAddress> selectAddressByUserId(Integer userId);
}