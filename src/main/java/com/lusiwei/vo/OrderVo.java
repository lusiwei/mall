package com.lusiwei.vo;

import com.lusiwei.pojo.Shipping;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: lusiwei
 * @Date: 2018/12/2 21:32
 * @Description:
 */
@Setter
@Getter
public class OrderVo {
    /**
     "orderNo": 1485158223095,
     "payment": 2999.11,
     "paymentType": 1,
     "postage": 0,
     "status": 10,
     "paymentTime": null,
     "sendTime": null,
     "endTime": null,
     "closeTime": null,
     "createTime": 1485158223095,
      orderItemVoList
     "shippingId": 5,
     "shippingVo": null
     */
    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private String paymentTypeDesc;
    private Integer postage;
    private Integer status;
    private String statusDesc;
    private String paymentTime;
    private String sendTime;
    private String endTime;
    private String closeTime;
    private String createTime;
    private List<OrderItemVo> orderItemVoList;
    private Integer shippingId;
    private ShippingVo shippingVo;
}
