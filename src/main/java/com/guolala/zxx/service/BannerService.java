package com.guolala.zxx.service;

import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.Banner;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/6/30
 * @Description:
 */
public interface BannerService {

    List<Banner> listBanners(String type);

    void saveBanners(Banner banner);

    void delBanners(Integer bannerId);
}
