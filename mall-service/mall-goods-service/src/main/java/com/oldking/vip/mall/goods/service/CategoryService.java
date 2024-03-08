package com.oldking.vip.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.goods.model.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    /**
     * 根据分类父Id查询所有子类
     * @param pid
     * @return
     */
    List<Category> finByParentId(Integer pid);


}
