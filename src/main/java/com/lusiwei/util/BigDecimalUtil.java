package com.lusiwei.util;

import java.math.BigDecimal;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 13:59
 * @Description:
 */
public class BigDecimalUtil {


    /**加*/
    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }
    public static BigDecimal add(Double b1,Double b2){
        BigDecimal add = BigDecimal.valueOf(b1).add(BigDecimal.valueOf(b2));
        return add;
    }
    /**乘法*/
    public static BigDecimal multiple(BigDecimal b1,BigDecimal b2){
        return b1.multiply(b2);
    }

    public static BigDecimal multiple(BigDecimal b1, Integer b2) {
        return b1.multiply(BigDecimal.valueOf(b2));
    }
}
