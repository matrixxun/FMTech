package com.google.android.play.utils;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;

public final class PlayTouchDelegate
  extends TouchDelegate
{
  private Rect mPlayBounds;
  private boolean mPlayDelegateTargeted;
  private View mPlayDelegateView;
  private int mPlaySlop;
  private Rect mPlaySlopBounds;
  
  public PlayTouchDelegate(Rect paramRect, View paramView)
  {
    super(paramRect, paramView);
    this.mPlayBounds = paramRect;
    this.mPlaySlop = ViewConfiguration.get(paramView.getContext()).getScaledTouchSlop();
    this.mPlaySlopBounds = new Rect(paramRect);
    this.mPlaySlopBounds.inset(-this.mPlaySlop, -this.mPlaySlop);
    this.mPlayDelegateView = paramView;
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = (int)paramMotionEvent.getX();
    int j = (int)paramMotionEvent.getY();
    int k = 1;
    int m = paramMotionEvent.getAction();
    boolean bool1 = false;
    boolean bool2;
    View localView;
    switch (m)
    {
    default: 
      bool2 = false;
      if (bool1)
      {
        localView = this.mPlayDelegateView;
        if (k == 0) {
          break label192;
        }
        paramMotionEvent.setLocation(localView.getWidth() / 2, localView.getHeight() / 2);
      }
      break;
    }
    for (;;)
    {
      bool2 = localView.dispatchTouchEvent(paramMotionEvent);
      return bool2;
      boolean bool3 = this.mPlayBounds.contains(i, j);
      bool1 = false;
      if (!bool3) {
        break;
      }
      this.mPlayDelegateTargeted = true;
      bool1 = true;
      break;
      bool1 = this.mPlayDelegateTargeted;
      if ((bool1) && (!this.mPlaySlopBounds.contains(i, j))) {
        k = 0;
      }
      if (paramMotionEvent.getAction() != 1) {
        break;
      }
      this.mPlayDelegateTargeted = false;
      break;
      bool1 = this.mPlayDelegateTargeted;
      this.mPlayDelegateTargeted = false;
      break;
      label192:
      int n = this.mPlaySlop;
      paramMotionEvent.setLocation(-(n * 2), -(n * 2));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.utils.PlayTouchDelegate
 * JD-Core Version:    0.7.0.1
 */