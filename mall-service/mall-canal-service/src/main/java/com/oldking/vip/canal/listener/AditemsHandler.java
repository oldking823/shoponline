package com.oldking.vip.canal.listener;

import com.oldking.vip.mall.goods.feign.SkuFeign;
import com.oldking.vip.mall.goods.model.AdItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@Component
@CanalTable(value = "ad_items")
public class AditemsHandler implements EntryHandler<AdItems> {

    @Autowired
    private SkuFeign skuFeign;
    /**
     * 数据库数据增加脚本
     * @param adItems
     */
    @Override
    public void insert(AdItems adItems) {
//        重新加载缓存
        skuFeign.updateTypeItems(adItems.getType());
    }

    /**
     * 数据库修改数据时执行
     * @param before
     * @param after
     */
    @Override
    public void update(AdItems before, AdItems after) {
        if (before.getType().intValue()!= after.getType().intValue()){
//            重新加载之前的产品
            skuFeign.updateTypeItems(before.getType());
        }
//        重新加载缓存
        skuFeign.updateTypeItems(after.getType());
    }

    /**
     * 数据库删除时执行
     * @param adItems
     */
    @Override
    public void delete(AdItems adItems) {
//        清除之前的缓存
        skuFeign.delTypeItems(adItems.getType());
    }
}
