package com.oldking.vip.mall.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.pay.model.PayLog;

public interface PayLogService extends IService<PayLog> {
    void savePayLog(PayLog payLog);
}
