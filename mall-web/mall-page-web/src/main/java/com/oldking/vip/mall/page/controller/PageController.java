package com.oldking.vip.mall.page.controller;

import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangzhengxiang
 * @Description http://localhost:8085/page/
 *
 */
@RequestMapping("/page")
@RestController
public class PageController {

    @Autowired
    private PageService pageService;
    /**
     * 生成静态页
     */
    @GetMapping(value = "/{id}")
    public RespResult html(@PathVariable(value = "id")String id) throws Exception {

        pageService.html(id);
        return RespResult.ok();
    }
    @DeleteMapping(value = "/{id}")
    public RespResult delhtml(@RequestParam(value = "id")String id) throws Exception {
        pageService.delhtml(id);
        return RespResult.ok();
    }

}
