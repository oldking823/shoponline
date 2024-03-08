package com.oldking.vip.mall.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oldking.vip.mall.goods.model.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangzhengxiang
 */

public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 根据分类ID查询对应品牌的集合
     * 根据品牌ID集合查询每个品牌信息
     */
    @Select("SELECT brand_id FROM category_brand WHERE category_id=#{id}")
    List<Integer> queryBrandIds(Integer id);
}
