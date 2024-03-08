package com.oldking.vip.mall.goods.controller;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.goods.model.Category;
import com.oldking.vip.mall.goods.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping(value = "/parent/{id}")
    public RespResult<List<Category>> findByParentId(@PathVariable("id")Integer pid){
        return RespResult.ok(categoryService.finByParentId(pid));
    }
}
