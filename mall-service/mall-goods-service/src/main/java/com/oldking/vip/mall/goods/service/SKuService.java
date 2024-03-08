package com.oldking.vip.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.goods.model.Sku;

import java.util.List;


public interface SKuService extends IService<Sku> {

    List<Sku> typeSkuItems(Integer id);

    void delTypeSkuItems(Integer id);

    List<Sku> updateTypeSkuItems(Integer id);
}
