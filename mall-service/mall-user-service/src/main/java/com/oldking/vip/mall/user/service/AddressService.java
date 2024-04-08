package com.oldking.vip.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oldking.vip.mall.user.model.Address;

import java.util.List;

public interface AddressService extends IService<Address> {
    /**
     * 查询用户收件列表
     */
    List<Address> list(String userName);
}
