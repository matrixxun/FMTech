package com.android.ex.photo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PhotoViewPager
  extends ViewPager
{
  private float mActivatedX;
  private float mActivatedY;
  private int mActivePointerId;
  private float mLastMotionX;
  private OnInterceptTouchListener mListener;
  
  public PhotoViewPager(Context paramContext)
  {
    super(paramContext);
    initialize();
  }
  
  public PhotoViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize();
  }
  
  private void initialize()
  {
    setPageTransformer$382b7817(new ViewPager.PageTransformer()
    {
      public final void transformPage(View paramAnonymousView, float paramAnonymousFloat)
      {
        if ((paramAnonymousFloat < 0.0F) || (paramAnonymousFloat >= 1.0F))
        {
          paramAnonymousView.setTranslationX(0.0F);
          paramAnonymousView.setAlpha(1.0F);
          paramAnonymousView.setScaleX(1.0F);
          paramAnonymousView.setScaleY(1.0F);
          return;
        }
        paramAnonymousView.setTranslationX(-paramAnonymousFloat * paramAnonymousView.getWidth());
        paramAnonymousView.setAlpha(Math.max(0.0F, 1.0F - paramAnonymousFloat));
        float f = Math.max(0.0F, 1.0F - 0.3F * paramAnonymousFloat);
        paramAnonymousView.setScaleX(f);
        paramAnonymousView.setScaleY(f);
      }
    });
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    InterceptType localInterceptType;
    int i;
    label41:
    int j;
    if (this.mListener != null)
    {
      localInterceptType = this.mListener.onTouchIntercept(this.mActivatedX, this.mActivatedY);
      if ((localInterceptType != InterceptType.BOTH) && (localInterceptType != InterceptType.LEFT)) {
        break label133;
      }
      i = 1;
      if ((localInterceptType != InterceptType.BOTH) && (localInterceptType != InterceptType.RIGHT)) {
        break label138;
      }
      j = 1;
      label58:
      int k = 0xFF & paramMotionEvent.getAction();
      if ((k == 3) || (k == 1)) {
        this.mActivePointerId = -1;
      }
      switch (k)
      {
      }
    }
    label133:
    label138:
    int m;
    do
    {
      for (;;)
      {
        return super.onInterceptTouchEvent(paramMotionEvent);
        localInterceptType = InterceptType.NONE;
        break;
        i = 0;
        break label41;
        j = 0;
        break label58;
        if ((i != 0) || (j != 0))
        {
          int i1 = this.mActivePointerId;
          if (i1 != -1)
          {
            float f = MotionEventCompat.getX(paramMotionEvent, MotionEventCompat.findPointerIndex(paramMotionEvent, i1));
            if ((i != 0) && (j != 0))
            {
              this.mLastMotionX = f;
              return false;
            }
            if ((i != 0) && (f > this.mLastMotionX))
            {
              this.mLastMotionX = f;
              return false;
            }
            if ((j != 0) && (f < this.mLastMotionX))
            {
              this.mLastMotionX = f;
              return false;
              this.mLastMotionX = paramMotionEvent.getX();
              this.mActivatedX = paramMotionEvent.getRawX();
              this.mActivatedY = paramMotionEvent.getRawY();
              this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, 0);
            }
          }
        }
      }
      m = MotionEventCompat.getActionIndex(paramMotionEvent);
    } while (MotionEventCompat.getPointerId(paramMotionEvent, m) != this.mActivePointerId);
    if (m == 0) {}
    for (int n = 1;; n = 0)
    {
      this.mLastMotionX = MotionEventCompat.getX(paramMotionEvent, n);
      this.mActivePointerId = MotionEventCompat.getPointerId(paramMotionEvent, n);
      break;
    }
  }
  
  public void setOnInterceptTouchListener(OnInterceptTouchListener paramOnInterceptTouchListener)
  {
    this.mListener = paramOnInterceptTouchListener;
  }
  
  public static enum InterceptType
  {
    static
    {
      LEFT = new InterceptType("LEFT", 1);
      RIGHT = new InterceptType("RIGHT", 2);
      BOTH = new InterceptType("BOTH", 3);
      InterceptType[] arrayOfInterceptType = new InterceptType[4];
      arrayOfInterceptType[0] = NONE;
      arrayOfInterceptType[1] = LEFT;
      arrayOfInterceptType[2] = RIGHT;
      arrayOfInterceptType[3] = BOTH;
      $VALUES = arrayOfInterceptType;
    }
    
    private InterceptType() {}
  }
  
  public static abstract interface OnInterceptTouchListener
  {
    public abstract PhotoViewPager.InterceptType onTouchIntercept(float paramFloat1, float paramFloat2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.PhotoViewPager
 * JD-Core Version:    0.7.0.1
 */