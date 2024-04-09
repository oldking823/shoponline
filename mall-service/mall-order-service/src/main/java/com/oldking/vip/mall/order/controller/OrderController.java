package com.oldking.vip.mall.order.controller;

import com.oldking.mall.util.RespCode;
import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.model.Order;
import com.oldking.vip.mall.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    /**
     * 添加订单
     */
    @PostMapping
    public RespResult add(@RequestBody Order order){
//        用户名
        order.setUsername("cus");


//        下单
        boolean add = orderService.add(order);
        return add? RespResult.ok() : RespResult.error(RespCode.ERROR);
    }
}
