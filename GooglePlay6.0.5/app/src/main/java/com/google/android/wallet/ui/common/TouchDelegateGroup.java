package com.google.android.wallet.ui.common;

import android.graphics.Rect;
import android.support.v4.util.SimpleArrayMap;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

public final class TouchDelegateGroup
  extends TouchDelegate
{
  private static final Rect IGNORED = new Rect();
  public TouchDelegate mCurrentTouchDelegate;
  public final SimpleArrayMap<View, TouchDelegate> mTouchDelegates = new SimpleArrayMap();
  
  public TouchDelegateGroup(View paramView)
  {
    super(IGNORED, paramView);
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    boolean bool1;
    if (i == 0) {
      if (paramMotionEvent.getPointerCount() > 1) {
        bool1 = false;
      }
    }
    for (;;)
    {
      return bool1;
      int j = 0;
      int k = this.mTouchDelegates.size();
      while (j < k)
      {
        TouchDelegate localTouchDelegate = (TouchDelegate)this.mTouchDelegates.valueAt(j);
        float f1 = paramMotionEvent.getX();
        float f2 = paramMotionEvent.getY();
        boolean bool2 = localTouchDelegate.onTouchEvent(paramMotionEvent);
        paramMotionEvent.setLocation(f1, f2);
        if (bool2)
        {
          this.mCurrentTouchDelegate = localTouchDelegate;
          return true;
        }
        j++;
      }
      return false;
      if ((this.mCurrentTouchDelegate != null) && (this.mCurrentTouchDelegate.onTouchEvent(paramMotionEvent))) {}
      for (bool1 = true; (i == 1) || (i == 32); bool1 = false)
      {
        this.mCurrentTouchDelegate = null;
        return bool1;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.TouchDelegateGroup
 * JD-Core Version:    0.7.0.1
 */