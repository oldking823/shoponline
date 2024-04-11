package com.oldking.vip.mall.order.controller;

import com.oldking.mall.util.RespCode;
import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.model.Order;
import com.oldking.vip.mall.order.service.OrderService;
import com.oldking.vip.mall.pay.WeixinPayParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeixinPayParam weixinPayParam;
    /**
     * 添加订单
     */
    @PostMapping
    public RespResult add(@RequestBody Order order, HttpServletRequest request) throws Exception {
//        用户名
        order.setUsername("cus");


//        下单
        boolean add = orderService.add(order);
//        加密数据传输到前台
        String ciptxt = weixinPayParam.weixinParam(order, request);

        return add? RespResult.ok(ciptxt) : RespResult.error(RespCode.ERROR);
    }
}
