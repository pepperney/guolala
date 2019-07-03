package com.guolala.zxx.controller.app;

import com.guolala.zxx.entity.model.Banner;
import com.guolala.zxx.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/6/30
 * @Description:
 */
@Api(value = "【app】 首页相关接口", tags = {"IndexController"})
@RestController
@RequestMapping("/app/banner")
public class IndexController {


    @Autowired
    private BannerService bannerService;

    @GetMapping("/v1/banners")
    @ApiOperation(value = "获取banner列表", httpMethod = "GET", notes = "")
    public List<Banner> getBannerList(String type) {
        return bannerService.listBanners(type);
    }
}
