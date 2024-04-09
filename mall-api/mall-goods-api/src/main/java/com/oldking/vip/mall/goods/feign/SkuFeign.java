package com.oldking.vip.mall.goods.feign;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.cart.model.Cart;
import com.oldking.vip.mall.goods.model.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "mall-goods")
public interface SkuFeign {
    /**
     * 库存递减
     */
    @PostMapping("/sku/dcount")
    RespResult dcount(@RequestBody List<Cart> carts);

    /****
     * 根据ID获取Sku
     */
    @GetMapping(value = "/sku/{id}")
    public RespResult<Sku> one(@PathVariable(value = "id") String id);

    @GetMapping(value = "/aditems/type")
    public List<Sku> typeItems(@RequestParam(value = "id")Integer id);

    /****
     * 根据推广分类查询推广产品列表
     */
    @DeleteMapping(value = "/sku/aditems/type")
    public RespResult delTypeItems(@RequestParam(value = "id")Integer id);

    /****
     * 根据推广分类查询推广产品列表
     *
     */
    @PutMapping(value = "/sku/aditems/type")
    public RespResult updateTypeItems(@RequestParam(value = "id")Integer id);

}
