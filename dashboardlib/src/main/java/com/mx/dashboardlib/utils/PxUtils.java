package com.mx.dashboardlib.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by boobooL on 2016/5/17 0017
 * Created 邮箱 ：boobooMX@163.com
 */
public class PxUtils {

    public static  int dpToPx(int dp, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,
                context.getResources().getDisplayMetrics());
    }

    public static int spToPx(int sp,Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,
                context.getResources().getDisplayMetrics());
    }



}
