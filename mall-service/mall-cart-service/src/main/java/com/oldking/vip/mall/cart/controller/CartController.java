package com.oldking.vip.mall.cart.controller;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.cart.model.Cart;
import com.oldking.vip.mall.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangzhengxiang
 */
@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 删除指定ID集合的购物车列表
     */
    @DeleteMapping()
    public RespResult<List<Cart>> delete(@RequestBody List<String> ids){
        cartService.delete(ids);
        return RespResult.ok();
    }


    /**
     * 指定ID集合的购物车信息
     */
    @PostMapping("/list")
    public RespResult<List<Cart>> list(@RequestBody List<String> ids){
        List<Cart> list = cartService.list(ids);
        return RespResult.ok(list);
    }

    /**
     * 增加购物车
     */
    @GetMapping("/{id}/{num}")
    public RespResult add(@PathVariable("id")String id,
                          @PathVariable("num")Integer num){
        String userName = "cus";

        cartService.add(id,userName,num);

        return RespResult.ok();

    }

    /**
     * 购物车列表
     * @return
     */
    @GetMapping("/list")
    public RespResult list(){
        String userName = "cus";
        List<Cart> list = cartService.list(userName);
        return RespResult.ok(list);
    }

}
