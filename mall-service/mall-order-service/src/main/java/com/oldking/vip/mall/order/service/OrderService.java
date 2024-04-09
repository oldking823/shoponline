package com.oldking.vip.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.model.Order;

public interface OrderService extends IService<Order> {
    /**
     * 添加订单
     */
    boolean add(Order order);
}
