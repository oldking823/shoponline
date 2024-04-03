package com.oldking.vip.mall.goods.feign;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.goods.model.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/sku")
@FeignClient(value = "mall-goods")
public interface SkuFeign {
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
    @DeleteMapping(value = "/aditems/type")
    public RespResult delTypeItems(@RequestParam(value = "id")Integer id);

    /****
     * 根据推广分类查询推广产品列表
     *
     */
    @PutMapping(value = "/aditems/type")
    public RespResult updateTypeItems(@RequestParam(value = "id")Integer id);

}
