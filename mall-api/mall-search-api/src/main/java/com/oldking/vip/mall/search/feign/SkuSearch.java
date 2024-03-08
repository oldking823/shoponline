package com.oldking.vip.mall.search.feign;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.search.model.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-search")
public interface SkuSearch {
    @PostMapping("/search/add")
    public RespResult add(@RequestBody SkuEs skuEs);


    /**
     * 删除索引
     */
    @DeleteMapping("/search/del/{id}")
    public RespResult del(@PathVariable("id")String id);
}
