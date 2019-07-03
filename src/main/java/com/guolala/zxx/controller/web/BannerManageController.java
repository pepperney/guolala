package com.guolala.zxx.controller.web;

import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.Banner;
import com.guolala.zxx.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/24
 * @Description:
 */
@Api(value = "【web】 banner图相关接口", tags = {"BannerManageController"})
@RequestMapping("/web/banner")
@RestController
public class BannerManageController {


    @Autowired
    private BannerService bannerService;

    @PostMapping("/saveBanners")
    @ApiOperation(value = "保存banner图", httpMethod = "POST", notes = "新增或者更新使用")
    public void saveBanners(UserInfo userInfo, @RequestBody Banner banner) {
        bannerService.saveBanners(banner);
    }

    @PostMapping("/delBanners")
    @ApiOperation(value = "删除banner图", httpMethod = "POST", notes = "")
    public void delBanners(@ApiParam(name = "bannerId", value = "id") Integer bannerId) {
        bannerService.delBanners(bannerId);
    }


    @GetMapping("/banners")
    @ApiOperation(value = "获取banner列表", httpMethod = "GET", notes = "")
    public List<Banner> getBannerList(String type) {
        return bannerService.listBanners(type);
    }
}
