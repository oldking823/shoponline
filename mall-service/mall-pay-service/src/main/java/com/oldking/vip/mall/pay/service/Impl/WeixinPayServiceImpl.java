package com.oldking.vip.mall.pay.service.Impl;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPay;
import com.oldking.vip.mall.pay.config.WeixinPayConfig;
import com.oldking.vip.mall.pay.mapper.PayLogMapper;
import com.oldking.vip.mall.pay.model.PayLog;
import com.oldking.vip.mall.pay.service.WeixinPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Autowired
    private WeixinPayConfig weixinPayConfig;

    @Autowired
    private PayLogMapper payLogMapper;

    @Autowired
    private WXPay wxPay;
    /**
     * 与支付订单创建方法，获取地址
     *
     * @param dataMap
     * @return
     */
    @Override
    public Map<String, String> preOrder(Map<String, String> dataMap) throws Exception {

        return wxPay.unifiedOrder(dataMap);
    }

    /**
     * 主动查询订单状态
     *
     * @param outno
     * @return
     * @throws Exception
     */
    @Override
    public PayLog result(String outno) throws Exception {
//        查询数据库中支付日志
        PayLog payLog = payLogMapper.selectById(outno);
        if (payLog == null) {
//        没有数据则查询微信支付服务
            Map<String,String> map = new HashMap<>();
            map.put("out_trade_no", outno);
            try {
                Map<String, String> resp = wxPay.orderQuery(map);
//          把结果存入数据库中（不可逆转支付结果）
                String tradeState = resp.get("trade_state");
                int status = tradeState(tradeState);
                if (status == 2 || status == 3 || status == 4 || status == 5|| status == 7) {}
                payLog = new PayLog(outno,status, JSON.toJSONString(resp),outno,new Date());
                payLogMapper.insert(payLog);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    /***
     * 支付状态
     * @param tradeState
     * @return
     */
    public int tradeState(String tradeState){
        int state = 1;
        switch (tradeState){
            case "NOTPAY":  //未支付
                state = 1;
                break;
            case "SUCCESS":
                state = 2;  //已支付
                break;
            case "REFUND":
                state = 3;  //转入退款
                break;
            case "CLOSED":
                state = 4;  //已关闭
                break;
            case "REVOKED":
                state = 5;  //已撤销
                break;
            case "USERPAYING":
                state = 6;  //用户支付中
                break;
            case "PAYERROR":
                state = 7;  //支付失败
                break;
            default:
                state=1;
        }
        return state;
    }

}
