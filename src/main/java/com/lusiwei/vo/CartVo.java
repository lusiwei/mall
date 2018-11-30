package com.lusiwei.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 13:29
 * @Description:
 */
@Setter
@Getter
public class CartVo {
    private Boolean allChecked;
    private BigDecimal cartTotalPrice;
    List<CartProductVo> cartProductVoList;
}
