package com.lusiwei.common;

/**
 * @Author: lusiwei
 * @Date: 2018/11/26 10:22
 * @Description:
 */
public class Constant {
    public interface User{
        String CURRENT_USER="current_user";
    }
    public interface Cart{

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NULL_SUCCESS";
        /**更新类型为添加*/
        String UPDATE_TYPE_ADD="ADD";
        String UPDATE_TYPE_UPDATE = "UPDATE";
        String PRODUCT_SELECTED="SELECT";
        String PRODUCT_UNSELECTED="UN_SELECT";
        String PRODUCT_SELECTEED_ALL = "SELECT_ALL";
        String PRODUCT_UNSELECTEED_ALL = "UN_SELECT_ALL";
        Integer CHECKED=1;
        Integer UNCHECKED=0;
    }
}
