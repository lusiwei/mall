package com.lusiwei.dto;

import com.lusiwei.pojo.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @Author: lusiwei
 * @Date: 2018/11/28 19:52
 * @Description:
 */
@Setter
@Getter
public class ProductManageDto extends Product implements Serializable {
    private String imageHost;
    private ProductManageDto productManageDto;


    public static ProductManageDto getProductManageDto(Product product) {
        ProductManageDto productManageDto = new ProductManageDto();
        BeanUtils.copyProperties(product, productManageDto);
        return productManageDto;
    }

}
