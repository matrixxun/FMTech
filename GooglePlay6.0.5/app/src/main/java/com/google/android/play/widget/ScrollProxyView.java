package com.google.android.play.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScrollProxyView
  extends View
{
  public ScrollProxyView(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public ScrollProxyView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ScrollProxyView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (getVisibility() != 8) {
      setVisibility(8);
    }
  }
  
  public boolean canScrollVertically(int paramInt)
  {
    return (paramInt >= 0) || (getScrollY() > 0);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.widget.ScrollProxyView
 * JD-Core Version:    0.7.0.1
 */