package com.mx.dashboardlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.mx.dashboardlib.R;
import com.mx.dashboardlib.utils.PxUtils;

/**
 * Created by boobooL on 2016/5/17 0017
 * Created 邮箱 ：boobooMX@163.com
 */
public class DashBoardAttr {
    private int mArcColor;
    private int mPointColor;
    private int mTikeCount;
    private int mTextSize;
    private String mText="";
    private int arcWidth;
    private int mSecondArcWidth;
    private String unit;//单位
    private int arcStartColor;
    private int arcEndColor;
    private int textColor;

    public DashBoardAttr(Context context, AttributeSet attrs,int defStyleAttr){
        TypedArray ta=context.obtainStyledAttributes(attrs,
                R.styleable.DashBoard,defStyleAttr,0);
        mArcColor=ta.getColor(R.styleable.DashBoard_arcColor,
                context.getResources().getColor(R.color.colorPrimaryDark));

        mPointColor=ta.getColor(R.styleable.DashBoard_pointColor,
                context.getResources().getColor(R.color.PointerColor));

        mTikeCount=ta.getInt(R.styleable.DashBoard_tikeCount,12);

        mTextSize=ta.getDimensionPixelSize(PxUtils.
                spToPx(R.styleable.DashBoard_android_textSize,context),24);

        mText=ta.getString(R.styleable.DashBoard_android_text);
        arcWidth=ta.getInt(R.styleable.DashBoard_arcWidth,3);

        mSecondArcWidth=ta.getInt(R.styleable.DashBoard_secArcWidth,50);
        unit=ta.getString(R.styleable.DashBoard_Unit);


        arcStartColor=ta.getColor(R.styleable.DashBoard_AcrStartColor,
                context.getResources().getColor(R.color.GREEN));

        arcEndColor=ta.getColor(R.styleable.DashBoard_AcrEndColor,
                context.getResources().getColor(R.color.RED));

        textColor=ta.getColor(R.styleable.DashBoard_textColor,
                context.getResources().getColor(R.color.Yellow));

        ta.recycle();

    }

    public int getArcColor() {
        return mArcColor;
    }

    public int getPointColor() {
        return mPointColor;
    }

    public int getTikeCount() {
        return mTikeCount;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public String getText() {
        return mText;
    }

    public int getArcWidth() {
        return arcWidth;
    }

    public int getSecondArcWidth() {
        return mSecondArcWidth;
    }

    public String getUnit() {
        return unit;
    }

    public int getArcStartColor() {
        return arcStartColor;
    }

    public int getArcEndColor() {
        return arcEndColor;
    }

    public int getTextColor() {
        return textColor;
    }
}
