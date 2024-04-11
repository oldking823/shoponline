package com.oldking.vip.mall.pay;

import com.oldking.mall.util.Signature;
import com.oldking.vip.mall.model.Order;
import com.oldking.vip.mall.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@Component
public class WeixinPayParam {

    @Autowired
    private Signature signature;

    public String weixinParam(Order order, HttpServletRequest request) throws Exception {
//        预支付下单所需要的操作
        Map<String, String> data = new HashMap<String, String>();
        data.put("body","springCloud Alibaba");
        data.put("out_trade_no",order.getId());//订单号
        data.put("device_info","PC");
        data.put("fee_type","CNY");//币种
        data.put("total_fee",String.valueOf(order.getMoneys()*100));//支付金额
        data.put("spbill_create_ip", IPUtils.getIpAddr(request));
        data.put("notify_url","http://weqwewqeqw.qweqwe.qweqwe:48847/wx/result");//回调地址（支付结果通知地址）
        data.put("trade_type","NATIVE");//扫码支付
//       TreeMap->MD5->Map->JSON->AES
        return signature.security(data);

    }
}
