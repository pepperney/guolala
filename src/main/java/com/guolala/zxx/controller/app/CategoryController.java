package com.guolala.zxx.controller.app;

import com.guolala.zxx.entity.model.Category;
import com.guolala.zxx.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/11
 * @Description:
 */
@RestController
@RequestMapping("/app/category")
@Api(value = "【app】 品类相关接口",tags = {"CategoryController"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/v1/list")
    @ApiOperation(value = "获取品类列表", httpMethod = "GET", notes = "")
    public List<Category> getCategoryList() {
        List<Category> list = categoryService.getCategoryList();
        return list;
    }


}

