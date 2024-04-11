package com.oldking.vip.mall.pay.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.oldking.mall.util.RespResult;
import com.oldking.mall.util.Signature;
import com.oldking.vip.mall.MallPayApplication;
import com.oldking.vip.mall.pay.model.PayLog;
import com.oldking.vip.mall.pay.service.WeixinPayService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx")
@CrossOrigin
public class WeixinPayController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private WeixinPayService weixinPayService;
    @Autowired
    private Signature signature;

    /**
     * 查询订单支付状态
     * @param outno
     * @return
     */
    @GetMapping(value = "/result/{outno}")
    public RespResult<PayLog> result(@PathVariable(value = "outno")String outno) throws Exception {
        PayLog payLog = weixinPayService.result(outno);
        return RespResult.ok(payLog);
    }

    /**
     * 获取二维码链接
     * @param ciphertext 加密后的
     * @return
     * @throws Exception
     */
    @GetMapping("/pay")
//    public RespResult<Map> pay(@RequestParam Map<String,String> dataMap) throws Exception {
    public RespResult<Map> pay(@RequestParam("ciptext") String ciphertext) throws Exception {
//        解密 ciphertext->AES->移除签名数据signature->MD5==signature?
        Map<String, String> dataMap = signature.security(ciphertext);

        Map<String, String> map = weixinPayService.preOrder(dataMap);
        if (map != null) {
            map.put("orderNumber", dataMap.get("out_trade_no"));
            map.put("money", dataMap.get("total_fee"));
            map.put("code_url", map.get("code_url  "));
            return RespResult.ok(map);
        }

        return RespResult.error("支付系统繁忙，请稍后再试");
    }
    /**
     * 支付结果回调
     */
    @RequestMapping("/result")
    public String result(HttpServletRequest request) throws Exception {
//        接收网络输入流数据
//        读取网络输入流
        ServletInputStream inputStream = request.getInputStream();
//        定义接受输入流对象（输出流）
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        将网络输入流读取到输出流中
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
//        关闭资源
        inputStream.close();
//         将支付结果的XMl结构转化成MAP结构
        String xmlResult = new String(byteArrayOutputStream.toByteArray(), "utf-8");
        Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
//        判断支付结果状态 ,日志状态 2.成功    7 失败
//        return_code/result_code
        int status = 7;
        if (map.get("return_code").equals(WXPayConstants.SUCCESS) && map.get("result_code").equals("SUCCESS")) {
            status = 2;
        }
//        创建日志对象
        PayLog payLog = new PayLog(map.get("out_trade_no"),status,JSON.toJSONString(map),map.get("out_trade_no"),new Date());
//        构建消息
        Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(payLog)).build();
//        发消息
        rocketMQTemplate.sendMessageInTransaction("rocket","log",message,null);


//        封装响应数据
        Map<String,String> resultResp = new HashMap<>();
        resultResp.put("return_code",WXPayConstants.SUCCESS);
        resultResp.put("return_msg","OK");
        return WXPayUtil.mapToXml(resultResp);
    }
}
