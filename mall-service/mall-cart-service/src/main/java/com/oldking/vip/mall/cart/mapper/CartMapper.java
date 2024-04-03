package com.oldking.vip.mall.cart.mapper;

import com.oldking.vip.mall.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author wangzhengxiang
 */
public interface CartMapper extends MongoRepository<Cart,String> {

}
