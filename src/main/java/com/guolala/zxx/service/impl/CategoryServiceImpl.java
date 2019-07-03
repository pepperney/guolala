package com.guolala.zxx.service.impl;

import com.guolala.zxx.constant.Const;
import com.guolala.zxx.constant.SysCode;
import com.guolala.zxx.dao.CategoryMapper;
import com.guolala.zxx.entity.model.Category;
import com.guolala.zxx.entity.vo.CategoryVo;
import com.guolala.zxx.exception.GLLException;
import com.guolala.zxx.service.CategoryService;
import com.guolala.zxx.util.BeanUtil;
import com.guolala.zxx.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/11
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void saveCategory(CategoryVo categoryVo) {
        ValidateUtil.validateParam(categoryVo);
        Category category = BeanUtil.copyProperties(categoryVo, Category.class);
        if (null == category.getId()) {
            // 新增品类
            if (null == category.getParentId()) {
                throw new GLLException(SysCode.ILLEGAL_PARAM, "parentId不能为空");
            }
            if (null != categoryMapper.selectCategoryByCatNo(category.getCatNo())) {
                throw new GLLException(SysCode.ILLEGAL_PARAM, "品类编号重复");
            }
            category.setCreateTime(new Date());
            category.setUpdateTime(new Date());
            categoryMapper.insert(category);
            return;
        }else {
            //更新品类
            category.setUpdateTime(new Date());
            categoryMapper.updateByPrimaryKey(category);
        }

    }

    @Override
    public void deleteCategory(Integer cateId) {
        Category category = this.getCategoryById(cateId);
        category.setCatStatus(Const.ZERO);
        category.setDeleted(Boolean.TRUE);
        category.setUpdateTime(new Date());
        categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public Category getCategoryById(Integer cateId) {
        return categoryMapper.selectByPrimaryKey(cateId);
    }

    @Override
    public List<Category> getChildCateList(Integer parentId) {
        return null;
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryMapper.selectAll();
    }
}