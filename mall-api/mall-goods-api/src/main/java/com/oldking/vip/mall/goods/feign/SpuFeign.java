package com.oldking.vip.mall.goods.feign;


import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.goods.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*****
 * @Author:
 * @Description:
 ****/
@FeignClient(value = "mall-goods")    //服务名字
public interface SpuFeign {

    /***
     * 查询Product
     */
    @GetMapping(value = "/spu/product/{id}")
    RespResult<Product> one(@PathVariable(value = "id")String id);
}

