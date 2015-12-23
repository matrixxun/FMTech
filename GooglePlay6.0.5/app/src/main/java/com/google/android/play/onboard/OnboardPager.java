package com.google.android.play.onboard;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.android.play.widget.UserAwareViewPager;

public class OnboardPager
  extends UserAwareViewPager
{
  protected boolean mAllowSwipeToNext = true;
  protected boolean mAllowSwipeToPrevious = true;
  protected float mLastX;
  
  public OnboardPager(Context paramContext)
  {
    super(paramContext);
  }
  
  public OnboardPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private boolean allowScrolling(int paramInt)
  {
    int i = getCurrentVisualItem();
    int j;
    if (getAdapter() == null) {
      j = 0;
    }
    while ((paramInt < 0) && (i > 0)) {
      if (isRtl())
      {
        return this.mAllowSwipeToNext;
        j = getAdapter().getCount();
      }
      else
      {
        return this.mAllowSwipeToPrevious;
      }
    }
    if ((paramInt > 0) && (i < j - 1))
    {
      if (isRtl()) {
        return this.mAllowSwipeToPrevious;
      }
      return this.mAllowSwipeToNext;
    }
    return true;
  }
  
  private boolean shouldAllowTouchEvent(MotionEvent paramMotionEvent)
  {
    int i;
    boolean bool;
    if (paramMotionEvent.getPointerCount() > 1)
    {
      i = 1;
      int j = paramMotionEvent.getActionMasked();
      bool = false;
      switch (j)
      {
      }
    }
    label60:
    float f2;
    do
    {
      bool = true;
      do
      {
        return bool;
        i = 0;
        break;
        this.mLastX = paramMotionEvent.getX(0);
        break label60;
        bool = false;
      } while (i != 0);
      float f1 = paramMotionEvent.getX(0);
      f2 = f1 - this.mLastX;
      this.mLastX = f1;
    } while (allowScrolling((int)Math.signum(-f2)));
    return false;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getPointerCount() > 1) {}
    while (!shouldAllowTouchEvent(paramMotionEvent)) {
      return false;
    }
    try
    {
      boolean bool = super.onInterceptTouchEvent(paramMotionEvent);
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      paramMotionEvent.setAction(3);
    }
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (shouldAllowTouchEvent(paramMotionEvent)) {
      try
      {
        boolean bool = super.onTouchEvent(paramMotionEvent);
        return bool;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        paramMotionEvent.setAction(3);
        return super.onTouchEvent(paramMotionEvent);
      }
    }
    return true;
  }
  
  protected final boolean pageLeft()
  {
    if (allowScrolling(-1)) {
      return super.pageLeft();
    }
    return false;
  }
  
  protected final boolean pageRight()
  {
    if (allowScrolling(1)) {
      return super.pageRight();
    }
    return false;
  }
  
  public void setAllowSwipeToNext(boolean paramBoolean)
  {
    this.mAllowSwipeToNext = paramBoolean;
  }
  
  public void setAllowSwipeToPrevious(boolean paramBoolean)
  {
    this.mAllowSwipeToPrevious = paramBoolean;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.onboard.OnboardPager
 * JD-Core Version:    0.7.0.1
 */