package com.oldking.vip.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.cart.model.Cart;
import com.oldking.vip.mall.goods.model.Sku;

import java.util.List;


public interface SkuService extends IService<Sku> {
    void dcount(List<Cart> carts);

    List<Sku> typeSkuItems(Integer id);

    void delTypeSkuItems(Integer id);

    List<Sku> updateTypeSkuItems(Integer id);

//    Sku getById(String id);
}
