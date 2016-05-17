package com.mx.dashboardlib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.mx.dashboardlib.utils.PxUtils;
import com.mx.dashboardlib.utils.StringUtils;

/**
 * Created by boobooL on 2016/5/17 0017
 * Created 邮箱 ：boobooMX@163.com
 */
public class DashBoardView extends View{

    private int mPercent;//seekBar传入的值

    private int mArcWidth;//第一个弧度的宽度
    private int mSecondArcwidth;//第二个弧度的宽度


    private String  mText=" ";//文字内容
    private String  speed=" ";//速度显示
    private String  unit=" ";//显示单位

    private int mTextSize; //文字的大小


    //设置文字的颜色
    private int mTextColor;
    private int mArcColor;

    //圆弧渐变色
    private int arcStartColor;
    private int arcEndColor;


    //小圆和指针颜色
    private int mPointColor;

    //刻度的个数
    private int mTikeCount;

    //画笔
    private Paint  paintOuter_Arc;//外圈弧画笔
    private Paint  paintOuter_Num;//外弧的刻度的画笔
    private Paint  paintInterArc;//内圈白色画笔
    private Paint  paintInterArc_transform;//内圈蓝色画笔
    private Paint  paint_CenterPoint_Point;//内圈画笔
    private Paint  paint_text;//w文字画笔

    private RectF mRectF1,mRectF2,mRectF3;
    private Shader mShader;//渐变器


    private int OFFSET=80;
    private int START_ARC=150;
    private int DURING_ARC=240;
    private int mMinCircleRadius=15;//中心圆点的半径
    private int mMinRingRadius=30;//中心圆环的半径

    private Context mContext;
    private DashBoardAttr mDashBoardAttr;

    public DashBoardView(Context context) {
        this(context,null);
        init(context);
    }
    public DashBoardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        init(context);
    }

    public DashBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        mDashBoardAttr=new DashBoardAttr(context,attrs,defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mArcColor=mDashBoardAttr.getArcColor();
        mPointColor=mDashBoardAttr.getPointColor();
        mTikeCount=mDashBoardAttr.getTikeCount();
        mTextSize=mDashBoardAttr.getTextSize();
        mTextColor=mDashBoardAttr.getTextColor();
        mText=mDashBoardAttr.getText();
        mArcWidth=mDashBoardAttr.getArcWidth();
        mSecondArcwidth=mDashBoardAttr.getSecondArcWidth();
        unit=mDashBoardAttr.getUnit();
        arcStartColor=mDashBoardAttr.getArcStartColor();
        arcEndColor=mDashBoardAttr.getArcEndColor();

        //如果手机版本在4.0以上，则开启硬件加速
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }

        //初始化画笔
        paintOuter_Arc=new Paint();
        paintOuter_Arc.setAntiAlias(true);
        paintOuter_Arc.setColor(mArcColor);
        paintOuter_Arc.setStyle(Paint.Style.STROKE);
        paintOuter_Arc.setStrokeWidth(3);


        paintOuter_Num=new Paint();
        paintOuter_Num.setAntiAlias(true);
        paintOuter_Num.setStyle(Paint.Style.FILL);
        paintOuter_Num.setStrokeWidth(1);

        paintInterArc=new Paint();
        paintInterArc.setAntiAlias(true);
        paintInterArc.setStrokeWidth(mSecondArcwidth);
        paintInterArc.setStyle(Paint.Style.STROKE);
        paintInterArc.setColor(Color.WHITE);

        paintInterArc_transform=new Paint();
        paintInterArc_transform.setAntiAlias(true);
        paintInterArc_transform.setStrokeWidth(mSecondArcwidth);
        paintInterArc_transform.setStyle(Paint.Style.STROKE);

        paint_CenterPoint_Point=new Paint();
        paint_CenterPoint_Point.setAntiAlias(true);
        paint_CenterPoint_Point.setColor(mPointColor);
        paint_CenterPoint_Point.setStrokeWidth(3);
        paint_CenterPoint_Point.setStyle(Paint.Style.FILL);


        paint_text=new Paint();
        paint_text.setAntiAlias(true);
        paint_text.setColor(mTextColor);
        paint_text.setStrokeWidth(1);
        paint_text.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int realWidth=startMeasure(widthMeasureSpec);
        int realHeight=startMeasure(heightMeasureSpec);
        setMeasuredDimension(realWidth,realHeight);
    }
    private int startMeasure(int mSpec){
        int result=0;
        int mode=MeasureSpec.getMode(mSpec);
        int size=MeasureSpec.getSize(mSpec);
        if(mode==MeasureSpec.EXACTLY){
            result=size;
        }else{
            result= PxUtils.dpToPx(200,mContext);
        }
        return  result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float percent=mPercent/100f;

        //最外面线条
        drawOutAcr(canvas);

        //绘制刻度
        drawNum(canvas);

        //绘制粗圆弧
        drawInArc(canvas,percent);

        //绘制中间小圆和圆环
        drawInPoint(canvas);

        //绘制指针
        drawPointer(canvas,percent);

        //绘制矩形和文字
        drawRectAndText(canvas,percent);


    }

    private void drawRectAndText(Canvas canvas, float percent) {

        float length=0;
        paint_text.setTextSize(mTextSize);

        length=paint_text.measureText(mText);
        canvas.drawText(mText,getWidth()/2-length/2,
                (float)(getHeight()/2*(1+Math.sqrt(2)/3)),
                paint_text);

        paint_text.setTextSize(mTextSize*1.5f);
        speed= StringUtils.floatFormat(120*percent)+unit;
        length=paint_text.measureText(speed);

        canvas.drawText(speed,getWidth()/2-length/2,
                (float)(getHeight()/2*(1+Math.sqrt(2)/2)),paint_text);


    }

    private void drawPointer(Canvas canvas, float percent) {
        canvas.save();
        float angel=DURING_ARC*(percent-0.5f);
        canvas.rotate(angel,getWidth()/2,getHeight()/2);//指针与外弧边持平
        canvas.drawLine(getWidth()/2,
                getHeight()/2,
                getWidth()/2,
                getHeight()/2-mArcWidth*2-OFFSET-mSecondArcwidth,
                paint_CenterPoint_Point);
        canvas.restore();

    }

    private void drawInPoint(Canvas canvas) {

        canvas.drawCircle(getWidth()/2,getHeight()/2,
                mMinRingRadius,paintOuter_Arc);//中心小圆环
        canvas.drawCircle(getWidth()/2,getHeight()/2,
                mMinCircleRadius,paint_CenterPoint_Point);//中心圆点

    }

    private void drawInArc(Canvas canvas, float percent) {

        mRectF2=new RectF(mArcWidth+OFFSET,
                mArcWidth+OFFSET,
                getWidth()-mArcWidth-OFFSET,
                getHeight()-mArcWidth-OFFSET);

        canvas.drawArc(mRectF2,START_ARC,DURING_ARC,false,paintInterArc);

        mRectF3=new RectF(mArcWidth+OFFSET,
                mArcWidth+OFFSET,
                getWidth()-mArcWidth-OFFSET,
                getHeight()-mArcWidth-OFFSET);
        mShader=new LinearGradient(mArcWidth+OFFSET,
                mArcWidth+OFFSET,
                getWidth()-mArcWidth-OFFSET,
                getHeight()-mArcWidth-OFFSET,
                arcStartColor,arcEndColor,
                Shader.TileMode.REPEAT);


        paintInterArc_transform.setShader(mShader);
        canvas.drawArc(mRectF3,START_ARC,percent*DURING_ARC,false,paintInterArc_transform);

    }

    private void drawNum(Canvas canvas) {

        canvas.save();//记录画布的状态
        canvas.rotate(-(180-START_ARC+90),getWidth()/2,getHeight()/2);
        float rAngle=DURING_ARC/mTikeCount;
        for (int i = 0; i <mTikeCount+1 ; i++) {
            canvas.save();//记录画布的状态
            canvas.rotate(rAngle*i,
                    getWidth()/2,
                    getHeight()/2);
            canvas.drawLine(getWidth()/2,
                    mArcWidth,
                    getWidth()/2,
                    20,
                    paintOuter_Arc);//画刻度线
            canvas.drawText(""+i*10,
                    getWidth()/2-mArcWidth*2,
                    40,
                    paintOuter_Num);//画刻度
            canvas.restore();

        }
        canvas.restore();



    }
  private void drawOutAcr(Canvas canvas) {

        //做外面线条
        mRectF1=new RectF(mArcWidth, mArcWidth,
                getWidth()-mArcWidth,
                getHeight()-mArcWidth);
        canvas.drawArc(mRectF1,
                START_ARC,
                DURING_ARC,
                false,
                paintOuter_Arc);


    }

    /**
     * 设置百分比
     * @param percent
     */
    public void setPercent(int percent){
        mPercent=percent;
        invalidate();
    }

    /**
     * 设置圆弧的颜色
     * @param color
     */
    public void setArcColor(int color){
        mArcColor=color;
        invalidate();
    }

    /**
     * 设置指针的颜色
     * @param color
     */
    public void PointerColor(int color){
        mPointColor=color;
        invalidate();
    }


    /**
     * 设置文字大小
     *
     * @param size
     */
    public void setTextSize(int size) {
        mTextSize = size;
        invalidate();
    }

    /**
     * 设置粗弧的宽度
     *
     * @param width
     */
    public void setArcWidth(int width) {
        mSecondArcwidth = width;
        invalidate();
    }

    /**
     * 设置粗弧起始颜色
     *
     * @param acrStartColor
     */
    public void setAcrStartColor(int acrStartColor) {
        this.arcStartColor = acrStartColor;
    }

    /**
     * 设置粗弧结束颜色
     *
     * @param acrEndColor
     */

    public void setAcrEndColor(int acrEndColor) {
        this.arcEndColor = acrEndColor;
    }

    /**
     * 设置字体颜色
     *
     * @param mTextColor
     */
    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    /**
     * 设置单位
     *
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 设置字体mText
     *
     * @param mText
     */
    public void setmText(String mText) {
        this.mText = mText;

    }

    /**
     * 设置弧的颜色
     *
     * @param mArcColor
     */
    public void setmArcColor(int mArcColor) {
        this.mArcColor = mArcColor;
    }






}
