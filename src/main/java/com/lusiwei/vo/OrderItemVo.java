package com.lusiwei.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author: lusiwei
 * @Date: 2018/12/2 21:34
 * @Description:
 */
@Getter
@Setter
public class OrderItemVo {
    /**
     "orderNo": 1485158223095,
     "productId": 2,
     "productName": "oppo R8",
     "productImage": "mainimage.jpg",
     "currentUnitPrice": 2999.11,
     "quantity": 1,
     "totalPrice": 2999.11,
     "createTime": null
     */
    private Long orderNo;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal currentUnitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String createTime;
}
