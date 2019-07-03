package com.guolala.zxx.dao;

import com.guolala.zxx.entity.model.Category;
import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    Category selectByPrimaryKey(Integer id);

    List<Category> selectAll();

    int updateByPrimaryKey(Category record);

    /**
     * 根据品类编码获取品类
     * @param catNo
     * @return
     */
    Category selectCategoryByCatNo(String catNo);
}