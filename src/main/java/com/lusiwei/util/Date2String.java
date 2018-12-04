package com.lusiwei.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: lusiwei
 * @Date: 2018/12/2 22:42
 * @Description:
 */
public class Date2String {
    public static String date2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}
