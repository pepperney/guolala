package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.Banner;
import java.util.List;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Banner record);

    Banner selectByPrimaryKey(Integer id);

    List<Banner> selectAll();

    int updateByPrimaryKey(Banner record);

    List<Banner> selectBannersByType(String type);
}