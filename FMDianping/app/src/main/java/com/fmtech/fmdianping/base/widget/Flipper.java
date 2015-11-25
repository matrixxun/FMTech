package com.fmtech.fmdianping.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.fmtech.fmdianping.widget.NavigationDot;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/13 21:50
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/13 21:50  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class Flipper<T> extends FrameLayout{

    protected static final int ANIM_NONE = 0;
    private static final int ANIM_TRANS = 2;
    private static final int ANIM_TRANS_DURATION1 = 30;
    private static final int ANIM_TRANS_DURATION2 = 150;
    private static final int ANIM_TRANS_TO_NEXT = 1;
    private static final int ANIM_TRANS_TO_PREVIOUS = -1;
    private static final int FLING_VELOCITY = 500;
    protected FlipperAdapter<T> mAdapter;
    private int mAnimDuration;
    private int mAnimMode = 0;
    private long mAnimStartMs;
    private int mAnimX1;
    private int mAnimX2;
    private T mCurrentItem;
    protected View mCurrentView;
    private float mScrolledDistance = 0.0F;
    protected boolean isScrolling;
    private int mItemSpaceAdjust;
    public NavigationDot mNavigationDot;
    protected T mNextItem;
    protected View mNextView;
    protected T mPreviousItem;
    protected View mPreviousView;
    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
			if(mPreviousView == null && mNextView == null){
				isScrolling = false;
				return true;
			}
			isScrolling = true;
			onScrollX(event1, event2, distanceX);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        /**
         * The user has performed a down {@link MotionEvent} and not performed
         * a move or up yet. This event is commonly used to provide visual
         * feedback to the user to let them know that their action has been
         * recognized i.e. highlight an element.
         *
         * @param e The down motion event
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }
    };
    private GestureDetector mGestureDetector = new GestureDetector(mGestureListener);

    public Flipper(Context context) {
        super(context);
    }

    public Flipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
		}
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
		if(!mGestureDetector.onTouchEvent(event)){
			
		}
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

	/**
	*@return True if an invalidate() was issued
	*/
    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		boolean invalidateIssued = false;
		if(child instanceof NavigationDot){
			invalidateIssued = super.drawChild(canvas, child, drawingTime);
		}
		
		if(child == mPreviousView){
			canvas.save();
			canvas.translate(-getWidth(), 0.0F);
			invalidateIssued = super.drawChild(canvas, child, drawingTime);
			canvas.restore();
		}
		
		if(child == mCurrentView){
			canvas.save();
			canvas.translate(0.0F, 0.0F);
			invalidateIssued = super.drawChild(canvas, child, drawingTime);
			canvas.restore();
		}
		
		if(child == mNextView){
			canvas.save();
			canvas.translate(getWidth(), 0.0F);
			invalidateIssued = super.drawChild(canvas, child,drawingTime);
			canvas.restore();
		}
        return invalidateIssued;
    }

    public void onFling(float velocityX){

    }

    public void onScrollX(MotionEvent e1, MotionEvent e2, float distanceX){
		mScrolledDistance = (mScrolledDistance + distanceX);
		invalidate();
    }

    public void onScrollXEnd(){

    }

    public void onTap(){

    }

	private boolean isEquals(Object obj1, Object obj2){
		return obj1 == obj2;
	}
	
    public void restorePosition(boolean restore){

    }

    public void setAdapter(FlipperAdapter<T> adapter){
        mAdapter = adapter;
    }

    public void setCurrentSelectDot(int index){

    }

    public void setCurrentItem(T currentItem){
		if(null == mAdapter){
			return;
		}
		Object objPre = mPreviousItem;
		Object objCurr = mCurrentItem;
		Object objNext = mNextItem;
		mCurrentItem = currentItem;
		
		if(!isEquals(objPre, mPreviousItem)){
			if(null != mPreviousView){
				removeView(mPreviousView);
			}
			if(null == mPreviousItem){
                mPreviousView = null;
			}
            mPreviousView = mAdapter.getView(mPreviousItem, mPreviousView);
			if(null != mPreviousView){
				addView(mPreviousView);
			}
		}
		
		if(!isEquals(objCurr, mCurrentItem)){
			if(null != mCurrentView){
				removeView(mCurrentView);		
			}
			if(null == mCurrentItem){
				mCurrentView = null;
			}
			mCurrentView = mAdapter.getView(mCurrentItem, mCurrentView);
			if(null != mCurrentView){
				addView(mCurrentView);
			}
		}
		
		if(!isEquals(objNext, mNextItem)){
			if(null != mNextView){
				removeView(mNextView);		
			}
			if(null == mNextItem){
				mNextView = null;
			}
			mNextView = mAdapter.getView(mNextItem, mNextView);
			if(null != mNextView){
				addView(mNextView);
			}
		}
		
    }

    public void setItemSpaceSpanAdjust(int spaceSpan){

    }

	/**
	*
	*/
    public void update(){
		
    }
}
