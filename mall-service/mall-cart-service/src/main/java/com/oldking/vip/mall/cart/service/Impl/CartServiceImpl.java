package com.oldking.vip.mall.cart.service.Impl;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.cart.mapper.CartMapper;
import com.oldking.vip.mall.cart.model.Cart;
import com.oldking.vip.mall.cart.service.CartService;
import com.oldking.vip.mall.goods.feign.SkuFeign;
import com.oldking.vip.mall.goods.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author wangzhengxiang
 */
@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SkuFeign skuFeign;
    /**
     * 添加到购物车
     * @param id
     * @param userName
     * @param number
     * @return
     */
    @Override
    public Boolean add(String id, String userName, Integer number) {
//        如果之前由同一个商品，删除之前的购物车
        cartMapper.deleteById(userName+id);

//        查询商品详情
        RespResult<Sku> one = skuFeign.one(id);
//        将当前商品对应的数据加入购物车
        Sku sku = one.getData();
        Cart cart = new Cart(userName+id, userName,sku.getName(),sku.getPrice(),sku.getImage(),id);

        return null;
    }
}
