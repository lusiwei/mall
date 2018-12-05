package com.lusiwei.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lusiwei
 * @Date: 2018/11/26 10:22
 * @Description:
 */
public class Constant {
    public interface User {
        String CURRENT_USER = "current_user";
    }

    public interface Cookie {
        Integer COOKIE_MAXAGE=60*60*24;
    }
    public interface RedisLoginExpire {
        Integer EXPIRE=30*60;
    }

    public interface Cart {

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NULL_SUCCESS";
        /**
         * 更新类型为添加
         */
        String UPDATE_TYPE_ADD = "ADD";
        String UPDATE_TYPE_UPDATE = "UPDATE";
        String PRODUCT_SELECTED = "SELECT";
        String PRODUCT_UNSELECTED = "UN_SELECT";
        String PRODUCT_SELECTEED_ALL = "SELECT_ALL";
        String PRODUCT_UNSELECTEED_ALL = "UN_SELECT_ALL";
        Integer CHECKED = 1;
        Integer UNCHECKED = 0;
    }

    @Getter
    public enum PaymentType {
        //支付方式
        ONLINE_PAY(1, "在线支付");
        private int code;
        private String desc;

        PaymentType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static String getDescByCode(int code) {
            if (code == 1) {
                return ONLINE_PAY.getDesc();
            } else {
                return null;
            }
        }
    }

    @Getter
    public enum OrderStatusEnum {
        //订单取消
        CANCELED(0, "已取消"),
        //未支付
        NO_PAY(10, "未支付"),
        //已付款
        PAID(20, "已付款"),
        //已发货
        SHIPPED(40, "已发货"),
        //订单完成
        ORDER_SUCCESS(50, "订单完成"),
        //订单关闭
        ORDER_CLOSE(60, "订单关闭");


        OrderStatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private String desc;
        private int code;

        public static String getDescByCode(int code) {
            String desc;
            switch (code) {
                case 0:
                    desc = CANCELED.getDesc();
                    break;

                case 10:
                    desc = NO_PAY.getDesc();
                    break;

                case 20:
                    desc = PAID.getDesc();
                    break;

                case 40:
                    desc = SHIPPED.getDesc();
                    break;

                case 50:
                    desc = ORDER_SUCCESS.getDesc();
                    break;

                default:
                    desc = ORDER_CLOSE.getDesc();
                    break;
            }
            return desc;
        }
    }

}
