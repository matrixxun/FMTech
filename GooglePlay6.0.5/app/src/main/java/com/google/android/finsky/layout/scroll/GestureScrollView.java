package com.google.android.finsky.layout.scroll;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class GestureScrollView
  extends ScrollView
{
  private FlingToDismissListener mFlingToDismissListener;
  private GestureDetectorCompat mGestureDetector = new GestureDetectorCompat(paramContext, this.mOnGestureListener);
  private GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener()
  {
    public final boolean onFling(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      if (Math.abs(paramAnonymousFloat1) >= Math.abs(paramAnonymousFloat2) / 4.0F) {}
      int i;
      do
      {
        return false;
        i = GestureScrollView.this.getScrollY();
        if (paramAnonymousFloat2 <= 0.0F) {
          break;
        }
      } while ((i > 0) || (GestureScrollView.this.mFlingToDismissListener == null));
      return false;
      GestureScrollView.this.getChildAt(0).getHeight();
      GestureScrollView.this.getHeight();
      return false;
    }
  };
  
  public GestureScrollView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public GestureScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mGestureDetector.onTouchEvent(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setFlingToDismissListener(FlingToDismissListener paramFlingToDismissListener)
  {
    this.mFlingToDismissListener = paramFlingToDismissListener;
  }
  
  public static abstract interface FlingToDismissListener {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.scroll.GestureScrollView
 * JD-Core Version:    0.7.0.1
 */