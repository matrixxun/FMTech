package com.fmtech.fmdianping.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.util.DimenUtils;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/13 22:02
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/13 22:02  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class NavigationDot extends View {

    private static final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean isLoop;
    private int mSelectDot = 0;
    private int mDotCount;
    private int mPadding;
    private int mHeight;
    private int mWidth;
    private int mDotHeight;
    private int mDotWidth;
    private int mDotNormalResId;
    private int mDotSelectedResId;
    private Bitmap mDotNormal;
    private Bitmap mDotSelected;

    public NavigationDot(Context context) {
        super(context);
    }

    public NavigationDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = context.getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationDot);
        mDotNormalResId = typedArray.getResourceId(R.styleable.NavigationDot_navigationDotNormal, -1);
        mDotSelectedResId =typedArray.getResourceId(R.styleable.NavigationDot_navigationDotSelected, -1);
        mDotCount = typedArray.getInt(R.styleable.NavigationDot_navigationDotCount, 0);
        mPadding = (int)typedArray.getDimension(R.styleable.NavigationDot_navigationDotPadding, 6.0F);
        typedArray.recycle();

        if(mDotNormalResId != -1){
            mDotNormal = BitmapFactory.decodeResource(resources, mDotNormalResId);
        }else{
            mDotNormal = BitmapFactory.decodeResource(resources, R.drawable.navigation_dot_normal);
        }

        if(mDotSelectedResId != -1){
            mDotSelected = BitmapFactory.decodeResource(resources, mDotSelectedResId);
        }else{
            mDotSelected = BitmapFactory.decodeResource(resources, R.drawable.navigation_dot_pressed);
        }

        mDotHeight = mDotNormal.getHeight();
        mDotWidth = mDotNormal.getWidth();
//        mPadding = DimenUtils.dip2px(context, 6.0F);
    }

    public NavigationDot(Context paramContext, boolean loop)
    {
        this(paramContext, null);
        isLoop = loop;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        System.out.println("-------MeasureSpec.EXACTLY: "+MeasureSpec.EXACTLY);
//        System.out.println("-------MeasureSpec.AT_MOST: "+MeasureSpec.AT_MOST);
        if(widthMode == MeasureSpec.EXACTLY){//MeasureSpec.EXACTLY: 1073741824
            mWidth = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){//MeasureSpec.AT_MOST: -2147483648
            int width = mDotWidth*mDotCount + mPadding *(mDotCount -1) + getPaddingLeft() + getPaddingRight();
            mWidth = Math.min(width, widthSize);
        }

        if(heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else if(heightMode == MeasureSpec.AT_MOST){
            int height = mDotHeight + getPaddingTop() + getPaddingBottom();
            mHeight = Math.min(height, heightSize);
        }
       setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0; i<mDotCount ; i++){
            if(i == mSelectDot){
                canvas.drawBitmap(mDotSelected, (mDotWidth + mPadding)*i, 0.0F, mPaint);
            }else{
                canvas.drawBitmap(mDotNormal, (mDotWidth + mPadding)*i, 0.0F, mPaint);
            }
        }
    }

    public void moveToNext(){
        if((mSelectDot >= mDotCount) && (!isLoop)){
            return;
        }
        int index = mSelectDot + 1;
        mSelectDot = (index % mDotCount);
        invalidate();
    }

    public void moveToPosition(int position){
        if(position < 0 || position >= mDotCount ){
            return;
        }
        mSelectDot = position;
        invalidate();
    }

    public void moveToPrevious(){
        if(mSelectDot <=0){
            if(!isLoop){
                return;
            }
            mSelectDot = mDotCount-1;
        }
        mSelectDot = mSelectDot -1;
        invalidate();
    }

    public void setCurrentSelectedDot(int index){
        if(index < 0 || index > mDotCount -1){
            mSelectDot = mDotCount -1;
        }else{
            mSelectDot = index;
        }
        invalidate();
    }

    public void setDotCount(int count){
        if(count > 0){
            mDotCount = count;
            requestLayout();
        }
    }

    public void setDotNormalBitmap(Bitmap dotNormal){
        if(null != dotNormal){
            mDotNormal = dotNormal;
        }
    }

    public void setDotNormalResId(int resId){
        if(!(resId > 0)){
            return;
        }
        mDotNormal = BitmapFactory.decodeResource(getContext().getResources(), resId);
        mDotHeight = mDotNormal.getHeight();
        mDotWidth = mDotNormal.getWidth();
    }

    public void setDotSelectedBitmap(Bitmap dotSelected){
        if(null != dotSelected){
            mDotSelected = dotSelected;
        }
    }

    public void setDotSelectedResId(int resId){
        if(!(resId > 0)){
            return;
        }
        mDotSelected = BitmapFactory.decodeResource(getContext().getResources(), resId);
    }
}
