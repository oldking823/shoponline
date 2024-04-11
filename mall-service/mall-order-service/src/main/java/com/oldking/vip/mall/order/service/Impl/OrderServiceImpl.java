package com.oldking.vip.mall.order.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.cart.feign.CartFeign;
import com.oldking.vip.mall.cart.model.Cart;
import com.oldking.vip.mall.goods.feign.SkuFeign;
import com.oldking.vip.mall.model.Order;
import com.oldking.vip.mall.model.OrderSku;
import com.oldking.vip.mall.order.mapper.OrderMapper;
import com.oldking.vip.mall.order.mapper.OrderSkuMapper;
import com.oldking.vip.mall.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
   private SkuFeign skuFeign;
    /**
     * 添加订单
     *
     * @param order
     */
    @GlobalTransactional
    @Override
    public boolean add(Order order) {
//        查询购物车数据
        RespResult<List<Cart>> cartResp = cartFeign.list(order.getCartIds());
        List<Cart> data = cartResp.getData();
//        递减库存
        if (data == null || data.size()==0) {
            return false;
        }
        skuFeign.dcount(data);
//        添加订单明细
        int totalNum = 0;
        int money = 0;
        for (Cart cart : data) {
//            将Cart转化成OrderSku
            OrderSku orderSku = JSON.parseObject(JSON.toJSONString(cart), OrderSku.class);
            orderSku.setId(IdWorker.getIdStr());

            orderSku.setOrderId(order.getId()); //提前赋值,此时前端传递数据不含订单编号
            orderSku.setMoney(orderSku.getPrice()*orderSku.getNum());
            //添加
            orderSkuMapper.insert(orderSku);
            totalNum += orderSku.getNum();
            money += orderSku.getMoney();

        }
//        添加订单
        order.setTotalNum(totalNum);
        order.setMoneys(money);
        orderMapper.insert(order);

//        删除购物车
        cartFeign.delete(order.getCartIds());

        return true;
    }

    /**
     * 支付成功修改订单状态
     *
     * @param orderId
     */
    @Override
    public int updateAfterPayStatus(String orderId) {
//        修改后的状态
        Order order = new Order();
        order.setId(orderId);
        order.setOrderStatus(1);
        order.setPayStatus(1);

//        修改条件
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId);
        wrapper.eq("order_status", 0);
        wrapper.eq("pay_status", 0);


        return orderMapper.update(order, wrapper);
    }
}
