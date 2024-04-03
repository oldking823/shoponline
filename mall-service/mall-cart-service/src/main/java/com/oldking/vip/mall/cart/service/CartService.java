package com.oldking.vip.mall.cart.service;

/**
 * @author wangzhengxiang
 */
public interface CartService {
    /**
     * 加入购物车
     */
    Boolean add(String id,String userName,Integer number);
}
