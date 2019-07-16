package com.guolala.zxx.controller.web;

import com.guolala.zxx.entity.model.Category;
import com.guolala.zxx.entity.req.CategoryReq;
import com.guolala.zxx.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/11
 * @Description:
 */
@Slf4j
@RequestMapping("/web/category")
@RestController
@Api(value = "【web】 品类相关接口",tags = {"CategoryManageController"})
public class CategoryManageController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/saveCategory")
    @ApiOperation(value = "保存品类", httpMethod = "POST", notes = "新增或者更新使用")
    public void saveCateGory(@RequestBody CategoryReq categoryReq) {
        categoryService.saveCategory(categoryReq);

    }

    @PostMapping("/deleteCategory")
    @ApiOperation(value = "删除品类", httpMethod = "POST")
    public void deleteCategory(Integer catId) {
        categoryService.deleteCategory(catId);

    }


    @GetMapping("/list")
    @ApiOperation(value = "获取品类列表", httpMethod = "GET", notes = "")
    public List<Category> getCategoryList() {
        List<Category> list = categoryService.getCategoryList();
        return list;
    }


}
