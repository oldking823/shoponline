package com.oldking.vip.mall.page.service.Impl;

import com.alibaba.fastjson.JSON;
import com.oldking.mall.util.RespResult;
import com.oldking.vip.mall.goods.feign.CategoryFeign;
import com.oldking.vip.mall.goods.feign.SpuFeign;
import com.oldking.vip.mall.goods.model.Category;
import com.oldking.vip.mall.goods.model.Product;
import com.oldking.vip.mall.goods.model.Sku;
import com.oldking.vip.mall.goods.model.Spu;
import com.oldking.vip.mall.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private CategoryFeign categoryFeign;


    @Autowired
    private SpuFeign spuFeign;

    @Value("${pagepath}")
    private String pagepath;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 生成静态页
     * @param spuid
     * @throws Exception
     */
    @Override
    public void html(String spuid) throws Exception {
//        创建容器对象（上下文对象）
        Context context = new Context();
//        设置模板数据
        context.setVariables(loadData(spuid));
//        指定文件生成后存储路径
        File file = new File(pagepath, spuid + ".html");
        PrintWriter writer = new PrintWriter(file, "UTF-8");
//        执行合成生成
        templateEngine.process("item",context,writer);

    }

    @Override
    public void delhtml(String spuid) throws Exception {
        File file = new File(pagepath, spuid + ".html");
        file.delete();
    }

    /**
     * 数据加载
     */
    public Map<String,Object> loadData(String spuid){
//        查询数据
        RespResult<Product> productResult = spuFeign.one(spuid);
        Product product = productResult.getData();
        if (product != null) {
//        处理数据
//            Spu

            Map<String,Object> resultMap= new HashMap<String,Object>();
            Spu spu = product.getSpu();
            resultMap.put("spu",spu);
//            图片处理
            resultMap.put("images",spu.getImages().split(","));
//            属性列表
            resultMap.put("attrs",JSON.parseObject(spu.getAttributeList(),Map.class));
//            三级分类
            RespResult<Category> one = categoryFeign.one(spu.getCategoryOneId());
            RespResult<Category> two = categoryFeign.one(spu.getCategoryTwoId());
            RespResult<Category> three = categoryFeign.one(spu.getCategoryThreeId());
            resultMap.put("one",one.getData());
            resultMap.put("two",two.getData());
            resultMap.put("three",three.getData());

            List<Map<String,Object>> skulist = new ArrayList<Map<String,Object>>();
            for (Sku sku : product.getSkus()) {
                Map<String,Object> skuMap = new HashMap<String,Object>();
                skuMap.put("id",sku.getId());
                skuMap.put("name",sku.getName());
                skuMap.put("price",sku.getPrice());
                skuMap.put("sttr",sku.getSkuAttribute());
            }
            resultMap.put("skuList",skulist);
            return resultMap;
        }
        return null;
    }
}
