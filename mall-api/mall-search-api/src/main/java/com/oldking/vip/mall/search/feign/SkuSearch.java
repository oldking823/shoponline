package com.oldking.vip.mall.search.feign;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.search.model.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@FeignClient("mall-search")
public interface SkuSearch {
    /**
     * 搜索
     * @return
     */
    @GetMapping("/search")
    RespResult<Map<String, Objects>> search(@RequestParam(required = false)Map<String,Object> searchMap);

    /**
     *  增加索引
     * @param skuEs
     * @return
     */
    @PostMapping("/search/add")
    public RespResult add(@RequestBody SkuEs skuEs);


    /**
     * 删除索引
     */
    @DeleteMapping("/search/del/{id}")
    public RespResult del(@PathVariable("id")String id);
}
