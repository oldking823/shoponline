package com.oldking.vip.mall.goods.feign;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.goods.model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("mall-goods")
public interface CategoryFeign {

    /**
     * 根据分类查询分类信息
     * @param id
     * @return
     */
    @GetMapping("/category/{id}")
    RespResult<Category> one(@PathVariable(value = "id")Integer id);
}
