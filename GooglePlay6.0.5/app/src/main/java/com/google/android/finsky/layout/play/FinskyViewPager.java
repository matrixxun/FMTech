package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;

public class FinskyViewPager
  extends ViewPager
{
  public boolean mAreTouchEventsDisabled = false;
  private MeasureOverrider mMeasureOverrider;
  
  public FinskyViewPager(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FinskyViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return (!this.mAreTouchEventsDisabled) && (super.onInterceptTouchEvent(paramMotionEvent));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mMeasureOverrider == null)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    Pair localPair = this.mMeasureOverrider.overrideMeasure$2816499f(paramInt1);
    super.onMeasure(((Integer)localPair.first).intValue(), ((Integer)localPair.second).intValue());
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return (!this.mAreTouchEventsDisabled) && (super.onTouchEvent(paramMotionEvent));
  }
  
  public void setMeasureOverrider(MeasureOverrider paramMeasureOverrider)
  {
    this.mMeasureOverrider = paramMeasureOverrider;
  }
  
  public static abstract interface MeasureOverrider
  {
    public abstract Pair<Integer, Integer> overrideMeasure$2816499f(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.FinskyViewPager
 * JD-Core Version:    0.7.0.1
 */