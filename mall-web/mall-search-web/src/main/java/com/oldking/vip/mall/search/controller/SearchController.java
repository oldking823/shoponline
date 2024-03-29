package com.oldking.vip.mall.search.controller;

import com.oldking.mall.util.RespResult;
import com.oldking.mall.util.UrlUtils;
import com.oldking.vip.mall.search.feign.SkuSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/web/search")
public class SearchController {
    @Autowired
    private SkuSearch skuSearch;
    /**
     * 搜索
     */
    @GetMapping
    public String search(@RequestParam(required = false) Map<String,Object> searchMap, Model model){
//
        RespResult<Map<String, Objects>> result = skuSearch.search(searchMap);
//        组装URl
        model.addAttribute("url", UrlUtils.map2url("/web/search",searchMap,"page"));
        model.addAttribute("urlsort", UrlUtils.map2url("/web/search",searchMap,"sm","sfield"));

        model.addAttribute("result",result.getData());
        model.addAttribute("searchMap",searchMap);
        return "search";
    }
}
