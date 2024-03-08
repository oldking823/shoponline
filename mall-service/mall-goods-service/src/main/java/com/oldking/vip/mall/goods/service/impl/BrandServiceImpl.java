package com.oldking.vip.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldking.vip.mall.goods.mapper.BrandMapper;
import com.oldking.vip.mall.goods.model.Brand;
import com.oldking.vip.mall.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> queryList(Brand brand) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
//        根据品牌
        queryWrapper.like("name",brand.getName());

        queryWrapper.eq("initial",brand.getInitial());



        return brandMapper.selectList(queryWrapper);
    }


    /**
     * 分页查询
     * @param brand
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public Page<Brand> queryList(Brand brand, Long currentPage, Long size) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",brand.getName());
        return brandMapper.selectPage(new Page<>(currentPage,size),queryWrapper);
    }

    /**
     * 根据分类id查询品牌集合
     * @param id
     * @return
     */
    @Override
    public List<Brand> queryByCategoryId(Integer id) {
//        根据分类id查询品牌id集合
        List<Integer> brandIds = brandMapper.queryBrandIds(id);

//        根据品牌id集合查询品牌集合
        if (brandIds != null && brandIds.size() > 0){
            return brandMapper.selectList(new QueryWrapper<Brand>().in("id",brandIds));
        }
        return null;
    }
}
