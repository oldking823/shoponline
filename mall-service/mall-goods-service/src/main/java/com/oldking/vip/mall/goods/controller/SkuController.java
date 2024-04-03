package com.oldking.vip.mall.goods.controller;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.goods.model.Sku;
import com.oldking.vip.mall.goods.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;
    /****
     * 根据ID获取Sku
     */
    @GetMapping(value = "/{id}")
    public RespResult<Sku> one(@PathVariable(value = "id") String id){
        Sku sku = skuService.getById(id);
        return RespResult.ok(sku);
    }

    /****
     * 根据推广分类查询推广产品列表
     *
     */
    @GetMapping(value = "/aditems/type")
    public List<Sku> typeItems(@RequestParam(value = "id")Integer id){
        //查询
        List<Sku> skus = skuService.typeSkuItems(id);
        return skus;
    }

    /****
     * 根据推广分类查询推广产品列表
     */
    @DeleteMapping(value = "/aditems/type")
    public RespResult delTypeItems(@RequestParam(value = "id")Integer id){
        skuService.delTypeSkuItems(id);
        return RespResult.ok();
    }

    /****
     * 根据推广分类查询推广产品列表
     *
     */
    @PutMapping(value = "/aditems/type")
    public RespResult updateTypeItems(@RequestParam(value = "id")Integer id){
        //修改
        skuService.updateTypeSkuItems(id);
        return RespResult.ok();
    }

}
