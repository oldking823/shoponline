package com.oldking.vip.canal.listener;

import com.alibaba.fastjson.JSON;
import com.oldking.vip.mall.goods.model.Sku;
import com.oldking.vip.mall.page.feign.Pagefeign;
import com.oldking.vip.mall.search.feign.SkuSearch;
import com.oldking.vip.mall.search.model.SkuEs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@CanalTable("sku")
@Component
public class SkuHandler implements EntryHandler<Sku> {

    @Autowired
    private SkuSearch skuSearch;
    @Autowired
    private Pagefeign pagefeign;
    /**
     * 增加数据监听
     * @param sku
     */
    @SneakyThrows
    @Override
    public void insert(Sku sku) {
        if (sku.getStatus().intValue() == 1){
//            将Sku转为Json，再将Json转为SkuEs
            skuSearch.add(JSON.parseObject(JSON.toJSONString(sku), SkuEs.class));
        }
//        生成静态页
        pagefeign.html(sku.getSpuId());
    }

    /**
     * 更新数据监听
     * @param before
     * @param after
     */
    @SneakyThrows
    @Override
    public void update(Sku before, Sku after) {
        if (after.getStatus().intValue() == 2){
//            表示删除索引
            skuSearch.del(after .getId());
        }else {
//            更新索引
            skuSearch.add(JSON.parseObject(JSON.toJSONString(after), SkuEs.class));
        }
//        生成静态页
        pagefeign.html(after.getSpuId());
    }

    /**
     * 删除数据监听
     * @param sku
     */
    @Override
    public void delete(Sku sku) {
        skuSearch.del(sku.getId());
    }
}
