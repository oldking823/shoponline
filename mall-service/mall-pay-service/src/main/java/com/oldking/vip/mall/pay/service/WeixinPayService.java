package com.oldking.vip.mall.pay.service;

import com.oldking.vip.mall.pay.model.PayLog;

import java.util.Map;

public interface WeixinPayService {
    /**
     * 预支付订单创建方法，获取地址
     * @param dataMap
     * @return
     */
    Map<String,String> preOrder(Map<String,String> dataMap) throws Exception;

    /**
     * 主动查询订单状态
     * @param outno
     * @return
     * @throws Exception
     */
    PayLog result(String outno) throws Exception;
}
