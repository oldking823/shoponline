package com.oldking.vip.mall.search.controller;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.search.model.SkuEs;
import com.oldking.vip.mall.search.service.SkuSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/search")
public class SkuSearchController {

// 查看索引中数据可以访问192.168.114.12:9200,打开EL查看

    @Autowired
    private SkuSearchService skuSearchService;

    /**
     * 搜索
     * @return
     */
    @GetMapping
    public RespResult<Map<String, Objects>> search(@RequestParam(required = false)Map<String,Object> searchMap){
        Map<String,Object> resultMap = skuSearchService.search(searchMap);
        return RespResult.ok(resultMap);
    }

    /**
     * 增加索引
     */
    @PostMapping("/add")
    public RespResult add(@RequestBody SkuEs skuEs){
        skuSearchService.add(skuEs);
        return RespResult.ok();
    }


    /**
     * 删除索引
     */
    @DeleteMapping("/del/{id}")
    public RespResult del(@PathVariable("id")String id){
        skuSearchService.del(id);
        return RespResult.ok();
    }
}
