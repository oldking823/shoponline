package com.oldking.vip.mall.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;

@Data
@Document(indexName = "shopsearch",type = "skues")
public class SkuEs {

    @Id
    private String id;
    private String name;
    private Integer price;
    private Integer num;
    private String image;
    private String images;
    private Date createTime;
    private Date updateTime;
    private String spuId;
    private Integer categoryId;
    //Keyword：不分词
    @Field(type= FieldType.Keyword)
    private String categoryName;
    private Integer brandId;
    @Field(type=FieldType.Keyword)
    private String brandName;
    @Field(type=FieldType.Keyword)
    private String skuAttribute;
    private Integer status;

    //属性映射(动态创建域信息)
//    key=就业薪资
//    value=1w
//    AttrMap.就业薪资.keyword = 1w
    private Map<String,String> attrMap;
}
