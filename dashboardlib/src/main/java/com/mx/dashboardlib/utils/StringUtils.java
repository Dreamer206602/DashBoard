package com.mx.dashboardlib.utils;

import java.math.BigDecimal;

/**
 * Created by boobooL on 2016/5/17 0017
 * Created 邮箱 ：boobooMX@163.com
 */
public class StringUtils {

    /**
     * float 转换成一位小数
     * @param value
     * @return
     */
    public static String floatFormat(float value){
        BigDecimal bigDecimal=new BigDecimal(value);
        bigDecimal=bigDecimal.setScale(1,BigDecimal.ROUND_HALF_DOWN);
        return bigDecimal.toString();
    }
}
