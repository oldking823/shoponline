package com.oldking.vip.mall.cart.feign;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.cart.model.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient("mall-cart")
public interface CartFeign {
    /**
     * 删除指定ID集合的购物车列表
     */
    @DeleteMapping("/cart")
    RespResult<List<Cart>> delete(@RequestBody List<String> ids);


    /**
     * 指定ID集合的购物车信息
     */
    @PostMapping("/cart/list")
    RespResult<List<Cart>> list(@RequestBody List<String> ids);
}
