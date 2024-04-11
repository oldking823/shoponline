package com.oldking.vip.mall.pay.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldking.vip.mall.pay.mapper.PayLogMapper;
import com.oldking.vip.mall.pay.model.PayLog;
import com.oldking.vip.mall.pay.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private PayLogMapper payLogMapper;
    @Override
    public void savePayLog(PayLog payLog) {
//        删除
        payLogMapper.deleteById(payLog.getId());

        payLogMapper.insert(payLog);
    }
}
