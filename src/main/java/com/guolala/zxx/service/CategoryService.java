package com.guolala.zxx.service;

import com.guolala.zxx.entity.model.Category;
import com.guolala.zxx.entity.req.CategoryReq;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/11
 * @Description:
 */
public interface CategoryService {

    /**
     * 新增/修改品类
     *
     * @param categoryReq
     */
    void saveCategory(CategoryReq categoryReq);

    /**
     * 删除品类
     *
     * @param cateId
     */
    void deleteCategory(Integer cateId);


    /**
     * 根据id获取品类信息
     *
     * @param cateId
     * @return
     */
    Category getCategoryById(Integer cateId);


    /**
     * 获取子品类列表
     *
     * @param parentId
     * @return
     */
    List<Category> getChildCateList(Integer parentId);

    /**
     * 获取品类列表
     *
     * @return
     */
    List<Category> getCategoryList();
}
