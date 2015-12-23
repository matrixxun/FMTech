package com.google.android.finsky.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

public abstract class AbsHorizontalStrip
  extends ViewGroup
{
  protected Context mContext;
  protected final float mDeceleration;
  protected int mGapMargin;
  private boolean mIsBeingDragged;
  private float mLastMotionX;
  protected int mLeadingMargin;
  private EdgeEffectCompat mLeftEdge;
  protected float mOriginalPixelOffsetOfFirstChild = 0.0F;
  private EdgeEffectCompat mRightEdge;
  private AnimationRunnable mScrollAnimation;
  private final int mScrollThreshold;
  protected float mTotalChildrenWidth;
  private VelocityTracker mVelocityTracker;
  private float mXDistanceScrolledSinceLastDown;
  
  public AbsHorizontalStrip(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    this.mScrollThreshold = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    this.mDeceleration = (1158.2634F * (160.0F * paramContext.getResources().getDisplayMetrics().density) * ViewConfiguration.getScrollFriction());
    this.mLeftEdge = new EdgeEffectCompat(paramContext);
    this.mRightEdge = new EdgeEffectCompat(paramContext);
    setWillNotDraw(false);
  }
  
  private float clampToTotalStripWidth(float paramFloat)
  {
    if (this.mTotalChildrenWidth == 0.0F) {
      return paramFloat;
    }
    while (paramFloat < 0.0F) {
      paramFloat += this.mTotalChildrenWidth;
    }
    while (paramFloat >= this.mTotalChildrenWidth) {
      paramFloat -= this.mTotalChildrenWidth;
    }
    return paramFloat;
  }
  
  private void initVelocityTrackerIfNotExists()
  {
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
  }
  
  private void runScrollAnimation(float paramFloat1, float paramFloat2)
  {
    long l = Math.abs(1000.0F * paramFloat2);
    this.mOriginalPixelOffsetOfFirstChild = getScrollPosition();
    this.mScrollAnimation = new AnimationRunnable(paramFloat1, l);
    this.mScrollAnimation.scheduleFrame();
  }
  
  private void updateScrollPosition(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      paramFloat = 0.0F;
    }
    int i = (int)(this.mTotalChildrenWidth - getWidth());
    if (-paramFloat > i) {
      paramFloat = -i;
    }
    scrollTo(-(int)paramFloat, 0);
    invalidate();
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    boolean bool1 = this.mLeftEdge.isFinished();
    boolean bool2 = false;
    if (!bool1)
    {
      int k = paramCanvas.save();
      int m = getHeight() - getPaddingTop() - getPaddingBottom();
      paramCanvas.rotate(270.0F);
      paramCanvas.translate(-m + getPaddingTop(), 0.0F);
      this.mLeftEdge.setSize(m, (int)this.mTotalChildrenWidth);
      bool2 = false | this.mLeftEdge.draw(paramCanvas);
      paramCanvas.restoreToCount(k);
    }
    if (!this.mRightEdge.isFinished())
    {
      int i = paramCanvas.save();
      int j = getHeight() - getPaddingTop() - getPaddingBottom();
      paramCanvas.rotate(90.0F);
      paramCanvas.translate(-getPaddingTop(), -this.mTotalChildrenWidth);
      this.mRightEdge.setSize(j, (int)this.mTotalChildrenWidth);
      bool2 |= this.mRightEdge.draw(paramCanvas);
      paramCanvas.restoreToCount(i);
    }
    if (bool2) {
      invalidate();
    }
  }
  
  abstract float getLeftEdgeOfChild(int paramInt);
  
  abstract float getLeftEdgeOfChildOnLeft(float paramFloat);
  
  abstract float getLeftEdgeOfChildOnRight(float paramFloat);
  
  protected float getScrollPosition()
  {
    return -getScrollX();
  }
  
  protected final void onChildAcquiredFocus(int paramInt)
  {
    if (this.mTotalChildrenWidth <= getWidth()) {
      return;
    }
    float f1 = -getLeftEdgeOfChild(paramInt) - getScrollPosition();
    float f2 = (float)Math.sqrt(Math.abs(f1 * (2.0F * this.mDeceleration)));
    float f3 = f2 / this.mDeceleration;
    if (f1 < 0.0F) {
      f2 = -f2;
    }
    runScrollAnimation(f2, f3);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    paramMotionEvent.getY();
    float f1 = paramMotionEvent.getX();
    int i = paramMotionEvent.getAction();
    if ((i == 2) && (this.mIsBeingDragged)) {
      return true;
    }
    switch (i & 0xFF)
    {
    }
    for (;;)
    {
      return this.mIsBeingDragged;
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      for (;;)
      {
        this.mVelocityTracker.addMovement(paramMotionEvent);
        this.mIsBeingDragged = false;
        this.mLastMotionX = f1;
        this.mXDistanceScrolledSinceLastDown = 0.0F;
        break;
        this.mVelocityTracker.clear();
      }
      float f2 = this.mLastMotionX - f1;
      this.mLastMotionX = f1;
      this.mXDistanceScrolledSinceLastDown += Math.abs(f2);
      if (this.mXDistanceScrolledSinceLastDown > this.mScrollThreshold)
      {
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(paramMotionEvent);
        ViewParent localViewParent = getParent();
        if (localViewParent != null) {
          localViewParent.requestDisallowInterceptTouchEvent(true);
        }
        this.mIsBeingDragged = true;
      }
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 0;
    int j;
    for (;;)
    {
      float f1;
      try
      {
        paramMotionEvent.getY();
        f1 = paramMotionEvent.getX();
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(paramMotionEvent);
        j = paramMotionEvent.getAction();
        switch (j & 0xFF)
        {
        default: 
          return true;
        }
      }
      finally {}
      float f17 = this.mLastMotionX - f1;
      this.mLastMotionX = f1;
      int i3 = getWidth();
      if (this.mTotalChildrenWidth > i3) {
        updateScrollPosition(getScrollPosition() - f17);
      }
      boolean bool4;
      if ((getScrollPosition() == 0.0F) && (f17 < 0.0F))
      {
        float f19 = -f17;
        bool4 = this.mLeftEdge.onPull(f19 / i3);
        if (bool4) {
          invalidate();
        }
      }
      else
      {
        float f18 = i3;
        boolean bool3 = f18 - getScrollPosition() < this.mTotalChildrenWidth;
        bool4 = false;
        if (!bool3) {
          bool4 = this.mRightEdge.onPull(f17 / i3);
        }
      }
    }
    this.mVelocityTracker.computeCurrentVelocity(1000, 1250.0F);
    float f2 = this.mVelocityTracker.getXVelocity();
    int k;
    label239:
    float f11;
    int i2;
    label315:
    float f12;
    if (j == 3)
    {
      k = 1;
      float f3 = Math.abs(f2);
      if ((f3 > ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity()) && (this.mTotalChildrenWidth > getWidth()))
      {
        float f10 = f3 / this.mDeceleration;
        f11 = f3 * f10 - f10 * (f10 * this.mDeceleration) / 2.0F;
        if (f2 >= 0.0F) {
          break label751;
        }
        i2 = 1;
        f12 = getScrollPosition();
        if (i2 == 0) {
          break label757;
        }
      }
    }
    label392:
    label556:
    label558:
    label589:
    label751:
    label757:
    for (float f13 = -f11;; f13 = f11)
    {
      float f14 = clampToTotalStripWidth(f13 + f12);
      float f15;
      if (i2 != 0)
      {
        f15 = -(float)Math.sqrt((f11 + (getLeftEdgeOfChildOnRight(clampToTotalStripWidth(this.mTotalChildrenWidth - f14)) - clampToTotalStripWidth(this.mTotalChildrenWidth - f14))) * (2.0F * this.mDeceleration));
        runScrollAnimation(f15, Math.abs(f15) / this.mDeceleration);
      }
      do
      {
        if (this.mIsBeingDragged)
        {
          boolean bool1 = this.mLeftEdge.onRelease();
          boolean bool2 = this.mRightEdge.onRelease();
          if ((bool1) || (bool2)) {
            invalidate();
          }
        }
        this.mIsBeingDragged = false;
        for (;;)
        {
          if (this.mVelocityTracker == null) {
            break label556;
          }
          this.mVelocityTracker.recycle();
          this.mVelocityTracker = null;
          break;
          float f16 = getLeftEdgeOfChildOnLeft(clampToTotalStripWidth(this.mTotalChildrenWidth - f14));
          f15 = (float)Math.sqrt((f11 + (clampToTotalStripWidth(this.mTotalChildrenWidth - f14) - f16)) * (2.0F * this.mDeceleration));
          break label392;
          if ((this.mXDistanceScrolledSinceLastDown > this.mScrollThreshold) || (k != 0) || (this.mIsBeingDragged)) {
            break label558;
          }
          this.mOriginalPixelOffsetOfFirstChild = 0.0F;
        }
        break;
      } while (this.mTotalChildrenWidth <= getWidth());
      float f4 = clampToTotalStripWidth(this.mTotalChildrenWidth - getScrollPosition());
      int m = 0;
      int n;
      float f9;
      if (i < getChildCount())
      {
        n = m + getChildAt(i).getWidth();
        if (n >= f4)
        {
          float f8 = f4 - m;
          if (n - f4 > f8) {
            f9 = getLeftEdgeOfChildOnLeft(f4);
          }
        }
      }
      for (float f5 = f4 - clampToTotalStripWidth(f9);; f5 = 0.0F)
      {
        float f6 = (float)Math.sqrt(Math.abs(f5 * (2.0F * this.mDeceleration)));
        float f7 = f6 / this.mDeceleration;
        if (f5 < 0.0F) {
          f6 = -f6;
        }
        runScrollAnimation(f6, f7);
        break;
        f9 = getLeftEdgeOfChildOnRight(f4);
        break label647;
        int i1 = this.mGapMargin;
        m = i1 + n;
        i++;
        break label589;
      }
      k = 0;
      break label239;
      i2 = 0;
      break label315;
    }
  }
  
  public final void setMargins(int paramInt1, int paramInt2)
  {
    this.mLeadingMargin = paramInt1;
    this.mGapMargin = paramInt2;
    requestLayout();
  }
  
  private final class AnimationRunnable
    implements Runnable
  {
    private float mDurationSec;
    private long mStartTimeNs = System.nanoTime();
    private float mVelocity;
    
    public AnimationRunnable(float paramFloat, long paramLong)
    {
      this.mDurationSec = ((float)paramLong / 1000.0F);
      this.mVelocity = paramFloat;
    }
    
    public final void run()
    {
      float f1 = (float)(System.nanoTime() - this.mStartTimeNs) / 1.0E+009F;
      if (f1 > this.mDurationSec) {
        f1 = this.mDurationSec;
      }
      for (;;)
      {
        float f2 = f1 * Math.abs(this.mVelocity) - f1 * (f1 * AbsHorizontalStrip.this.mDeceleration) / 2.0F;
        if (this.mVelocity < 0.0F) {
          f2 = -f2;
        }
        AbsHorizontalStrip.this.updateScrollPosition(AbsHorizontalStrip.this.mOriginalPixelOffsetOfFirstChild + Math.round(f2));
        return;
        scheduleFrame();
      }
    }
    
    @TargetApi(16)
    final void scheduleFrame()
    {
      if (Build.VERSION.SDK_INT >= 16)
      {
        AbsHorizontalStrip.this.postOnAnimation(this);
        return;
      }
      AbsHorizontalStrip.this.post(this);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AbsHorizontalStrip
 * JD-Core Version:    0.7.0.1
 */