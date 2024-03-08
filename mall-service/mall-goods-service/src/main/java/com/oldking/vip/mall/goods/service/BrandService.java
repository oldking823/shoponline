package com.oldking.vip.mall.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.goods.model.Brand;

import java.util.List;

public interface BrandService extends IService<Brand> {
    /**
     * 条件查询
     * return List<Brand>
     */
    List<Brand> queryList(Brand brand);

    /**
     * 条件分页查询
     * @param brand
     * @param currentPage
     * @param Size
     * @return
     */
    Page<Brand> queryList(Brand brand,Long currentPage,Long Size);

    /**
     * 根据分类ID查询品牌集合
     */
    List<Brand> queryByCategoryId(Integer id);


}



