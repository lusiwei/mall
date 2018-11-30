package com.lusiwei.dto;

import com.lusiwei.pojo.ProductWithBLOBs;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 12:19
 * @Description:
 */
@Getter
@Setter
public class ProductDetailDto extends ProductWithBLOBs {
    private String imageHost;
}
