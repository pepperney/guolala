package com.guolala.zxx.service.impl;

import com.guolala.zxx.dao.BannerMapper;
import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.Banner;
import com.guolala.zxx.service.BannerService;
import com.guolala.zxx.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/6/30
 * @Description:
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> listBanners(String type) {
        return bannerMapper.selectBannersByType(type);
    }

    @Override
    public void saveBanners(Banner banner) {
        ValidateUtil.validateParam(banner);
        if (null == banner.getId()) {
            banner.setUrl("/app/file/v1/download?fileId=" + banner.getFileId());
            banner.setCreateTime(new Date());
            banner.setUpdateTime(new Date());
            bannerMapper.insert(banner);
        } else {
            banner.setUpdateTime(new Date());
            bannerMapper.updateByPrimaryKey(banner);
        }
    }

    @Override
    public void delBanners(Integer bannerId) {
        Banner banner = bannerMapper.selectByPrimaryKey(bannerId);
        banner.setDeleted(Boolean.TRUE);
        banner.setUpdateTime(new Date());
        bannerMapper.updateByPrimaryKey(banner);
    }
}
