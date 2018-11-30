package com.lusiwei.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.lusiwei.pojo.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 12:37
 * @Description:
 */
@Setter
@Getter
public class ProductDto  implements Serializable {
    /**
     *  "id": 1,
     *  "categoryId": 3,
     *   name": "iphone7",
     *   subtitle": "双十一促销",
     *   mainImage": "mainimage.jpg",
     *   status":1,
     *   price": 7199.22
     */
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private Integer status;
    private BigDecimal price;
    private String imageHost;
    public static ProductDto getProductDto(Product product){
        ProductDto productDto=new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }

}
