package com.oldking.vip.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.goods.model.Product;
import com.oldking.vip.mall.goods.model.Spu;


public interface SpuService extends IService<Spu> {

    /****
     * 产品保存
     */
    void saveProduct(Product product);
}
