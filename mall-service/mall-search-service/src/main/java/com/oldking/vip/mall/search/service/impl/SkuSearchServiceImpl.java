package com.oldking.vip.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.oldking.mall.util.PageInfo;
import com.oldking.vip.mall.search.mapper.SkuSearchMapper;
import com.oldking.vip.mall.search.model.SkuEs;
import com.oldking.vip.mall.search.service.SkuSearchService;
import com.oldking.vip.mall.search.util.HighLightResultMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SkuSearchServiceImpl implements SkuSearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private SkuSearchMapper skuSearchMapper;


    /**
     * 关键词搜索
     *
     * @param searchMap 关键词：keyword --> name
     * @return
     */
    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
//        QueryBuilder -> 构建搜索条件
        NativeSearchQueryBuilder queryBuilder = queryBuilder(searchMap);
//        分组搜索
        group(queryBuilder,searchMap);
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("name")
                .preTags("<span style=\"color:red;\">")
                .postTags("</span>")
                .fragmentSize(100);
        queryBuilder.withHighlightFields(field);
//        skuSearchMapper进行搜索
//        Page<SkuEs> page = skuSearchMapper.search(queryBuilder.build());
//        AggregatedPage<SkuEs> page = (AggregatedPage<SkuEs>) skuSearchMapper.search(queryBuilder.build());
        AggregatedPage<SkuEs> page = elasticsearchRestTemplate.queryForPage(queryBuilder.build(), SkuEs.class, new HighLightResultMapper());
//        解析分组结果
        Map<String,Object> resultMap = new HashMap<String,Object>();
        parseGroup(page.getAggregations(),resultMap);

//        获取结果集：集合列表、总记录数
        List<SkuEs> list = page.getContent();
        resultMap.put("list", list);

//         创建分页对象
        int currentpage =  queryBuilder.build().getPageable().getPageNumber()+1;
        PageInfo pageInfo = new PageInfo(page.getTotalElements(),currentpage,5);


//        resultMap.put("totalElements", page.getTotalElements());
        resultMap.put("pageInfo",pageInfo);
        return resultMap;
    }

    /**
     * 将属性信息合并为Map对象
     */
    public void sttrParse(Map<String,Object> searchMap){
//        先获取attrmaps
        Object attrmaps = searchMap.get("attrmaps");
        if (attrmaps!=null){
            List<String> groupList= (List<String>) attrmaps;
            //        定义一个集合List<Map<String,Set<String>>>
            Map<String, Set<String>> allMaps = new HashMap<String,Set<String>>();
            //        循环集合
            for (String attr : groupList) {
                Map<String,String> attrMap = JSON.parseObject(attr, Map.class);
//                获取每条记录

            }
        }

    }

    /**
     * 分组结果解析
     */
    public void parseGroup(Aggregations aggregations,Map<String,Object> groupMap){
        if (aggregations!= null){
            for (Aggregation aggregation : aggregations) {
//            强转为ParsedStringTerms字符串类型 手机、电子书
                ParsedStringTerms terms = (ParsedStringTerms) aggregation;

//            新建结果集
                List<String> values = new ArrayList<String>();
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    values.add(bucket.getKeyAsString());
                }
//           此时的key就是别名
                String key = aggregation.getName();
                groupMap.put(key,values);
            }
        }
    }

    /**
     * 分组查询
     */
    public void group(NativeSearchQueryBuilder queryBuilder, Map<String, Object> searchMap) {

//        用户如果没有输入分类条件，则需要将分类搜索出来作为可选条件
        if (StringUtils.isEmpty(searchMap.get("category"))) {
            queryBuilder.addAggregation(
                    AggregationBuilders
                            .terms("categoryList")
                            //查询结果别名接受,类似Map的key
                            .field("categoryName")
                            //根据categoryName域进行分组
                            .size(100)
                            //显示结果数量
            );
        }
//        用户如果没有输入品牌条件，则需要将品牌搜索出来作为可选条件
        if (StringUtils.isEmpty(searchMap.get("brand"))) {
            queryBuilder.addAggregation(
                    AggregationBuilders
                            .terms("brandList")
                            //查询结果别名接受
                            .field("brandName")
                            //根据brandName域进行分组
                            .size(100)
                    //显示结果数量
            );
        }
        queryBuilder.addAggregation(
                AggregationBuilders
                        .terms("attrmaps")
                        //查询结果别名接受
                        .field("skuAttribute")
                        //根据skuAttribute域进行分组
                        .size(100000)
                //显示结果数量100000个
        );
    }

    /**
     * 搜索条件构建
     *
     * @param searchMap
     * @return
     */
    public NativeSearchQueryBuilder queryBuilder(Map<String, Object> searchMap) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        判断关键词是否为空，不为空，则设置条件

        if (searchMap != null && searchMap.size() > 0) {
//            关键词条件
            Object keywords = searchMap.get("keywords");
            if (!StringUtils.isEmpty(keywords)) {
                nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("name", keywords.toString()));
            }
        }
        return nativeSearchQueryBuilder;
    }

    /**
     * 增加索引
     *
     * @param skuEs
     */
    @Override
    public void add(SkuEs skuEs) {
//        获取属性
        String attrMap = skuEs.getSkuAttribute();
        if (!StringUtils.isEmpty(attrMap)) {
//            将属性添加到sttrMap中
            skuEs.setAttrMap(JSON.parseObject(attrMap, Map.class));
        }
        skuSearchMapper.save(skuEs);
    }

    /**
     * 删除索引
     *
     * @param id
     */
    @Override
    public void del(String id) {
        skuSearchMapper.deleteById(id);
    }
}
