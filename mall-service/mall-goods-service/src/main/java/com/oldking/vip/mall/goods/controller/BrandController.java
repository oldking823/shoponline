package com.oldking.vip.mall.goods.controller;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.goods.model.Brand;
import com.oldking.vip.mall.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangzhengxiang
 */
@RestController
@RequestMapping(value = "/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 增加品牌
     * @param brand
     * @return
     */
    @PostMapping
    public RespResult add(@RequestBody Brand brand){
        brandService.save(brand);
        return RespResult.ok();
    }

    /**
     * 修改
     * @param brand
     * @return
     */
    @PutMapping
    public RespResult update(@RequestBody Brand brand){
        brandService.updateById(brand);
        return RespResult.ok();
    }

    @DeleteMapping("/{id}")
    public RespResult update(@PathVariable(value = "id")String id){
        brandService.removeById(id);
        return RespResult.ok();
    }

    /**
     * 条件查询
     */
    @PostMapping("/search")
    public RespResult<List<Brand>> queryList(@RequestBody Brand brand){
        List<Brand> brandList = brandService.queryList(brand);
        return RespResult.ok(brandList);
    }
    /**
     * 根据分类Id查询品牌集合
     *
     */
    public RespResult<List<Brand>> categoryBrands(@PathVariable(value = "pid")Integer pid){
        List<Brand> brands = brandService.queryByCategoryId(pid);
        return RespResult.ok(brands);
    }
}
