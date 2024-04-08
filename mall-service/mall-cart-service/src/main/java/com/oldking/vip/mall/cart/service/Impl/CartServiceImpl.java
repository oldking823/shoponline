package com.oldking.vip.mall.cart.service.Impl;

import com.google.common.collect.Lists;
import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.cart.mapper.CartMapper;
import com.oldking.vip.mall.cart.model.Cart;
import com.oldking.vip.mall.cart.service.CartService;
import com.oldking.vip.mall.goods.feign.SkuFeign;
import com.oldking.vip.mall.goods.model.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author wangzhengxiang
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 删除指定购物车ID集合的列表
     */
    @Override
    public void delete(List<String> ids) {

        mongoTemplate.remove(Query.query(Criteria.where("_id").in(ids)),Cart.class);
    }

    /**
     * 查询指定购物车ID集合的列表
     */
    @Override
    public List<Cart> list(List<String> ids) {
//        根据ID集合查询
        if (ids != null && ids.size()>0) {
            Iterable<Cart> carts = cartMapper.findAllById(ids);
            return Lists.newArrayList(carts);
        }
        return null;
    }

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
        if (number>0) {
            //        查询商品详情
            RespResult<Sku> one = skuFeign.one(id);
//        将当前商品对应的数据加入购物车
            Sku sku = one.getData();
            Cart cart = new Cart(userName+id, userName,sku.getName(),sku.getPrice(),sku.getImage(),id,number);
            cartMapper.save(cart);
            return true;
        }
        return false;
    }

    @Override
    public List<Cart> list(String userName) {
//        条件构建
        Cart cart = new Cart();
        cart.setUserName(userName);


        return cartMapper.findAll(Example.of(cart), Sort.by("_id"));
    }
}
