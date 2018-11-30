package com.lusiwei.dto;

import com.lusiwei.pojo.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @Author: lusiwei
 * @Date: 2018/11/28 23:06
 * @Description:
 */
@Setter
@Getter
@NoArgsConstructor
public class ProductManageDetailDto extends Product {
    private int parentCategoryId;
    private String imageHost;
    private String detail;
    private String subImages;

    public static ProductManageDetailDto getProductDetailDto(Product product) {
        ProductManageDetailDto productManageDetailDto = new ProductManageDetailDto();
        BeanUtils.copyProperties(product, productManageDetailDto);
        return productManageDetailDto;
    }
}
