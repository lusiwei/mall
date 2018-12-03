package com.lusiwei.vo;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: lusiwei
 * @Date: 2018/12/2 23:40
 * @Description:
 */
@Setter
@Getter
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private String imageHost;
    private BigDecimal productTotalPrice;
}
