package com.google.android.finsky.layout.play;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public final class ScrollLockingTouchDelegate
{
  final Delegater mDelegater;
  View mEventForwardingTargetView;
  float mLastMotionX;
  float mLastMotionY;
  MotionEvent mLastTouchDownEvent;
  boolean mLockHorizontalScroll;
  boolean mLockVerticalScroll;
  final int mScrollThreshold;
  float mXDistanceScrolledSinceLastDown;
  float mYDistanceScrolledSinceLastDown;
  
  public ScrollLockingTouchDelegate(Delegater paramDelegater, Context paramContext)
  {
    this.mDelegater = paramDelegater;
    this.mScrollThreshold = ViewConfiguration.get(paramContext).getScaledTouchSlop();
  }
  
  final void forwardMotionEvent(MotionEvent paramMotionEvent)
  {
    if (this.mEventForwardingTargetView == null) {
      return;
    }
    MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
    View localView = this.mEventForwardingTargetView;
    float f1 = 0.0F;
    float f2 = 0.0F;
    do
    {
      float f3 = f1 - localView.getLeft();
      float f4 = f2 - localView.getTop();
      localView = (View)localView.getParent();
      if (localView == null) {
        break;
      }
      f1 = f3 + localView.getScrollX();
      f2 = f4 + localView.getScrollY();
    } while (localView != this.mDelegater.getDelegatingView());
    localMotionEvent.offsetLocation(f1, f2);
    this.mEventForwardingTargetView.dispatchTouchEvent(localMotionEvent);
    localMotionEvent.recycle();
  }
  
  final void lockVerticalScroll(MotionEvent paramMotionEvent)
  {
    if (this.mEventForwardingTargetView == this.mDelegater.getImmediateChildContainingVerticalScroller()) {}
    do
    {
      return;
      if ((this.mEventForwardingTargetView != null) && (this.mLastTouchDownEvent != null))
      {
        MotionEvent localMotionEvent2 = MotionEvent.obtain(this.mLastTouchDownEvent);
        localMotionEvent2.setAction(3);
        forwardMotionEvent(localMotionEvent2);
        localMotionEvent2.recycle();
        if ((this.mEventForwardingTargetView instanceof HorizontalScrollerContainer)) {
          ((HorizontalScrollerContainer)this.mEventForwardingTargetView).onIgnoreNextTouchSequence();
        }
      }
      this.mEventForwardingTargetView = this.mDelegater.getCurrentVerticalListView();
      this.mLockVerticalScroll = true;
    } while (this.mLastTouchDownEvent == null);
    MotionEvent localMotionEvent1 = MotionEvent.obtainNoHistory(paramMotionEvent);
    localMotionEvent1.setLocation(this.mLastTouchDownEvent.getX(), paramMotionEvent.getY());
    forwardMotionEvent(this.mLastTouchDownEvent);
    this.mLastTouchDownEvent.recycle();
    this.mLastTouchDownEvent = null;
    forwardMotionEvent(localMotionEvent1);
    localMotionEvent1.recycle();
  }
  
  public static abstract interface Delegater
  {
    public abstract int getBackgroundBottomHeightToIgnoreTouches();
    
    public abstract ViewGroup getCurrentVerticalListView();
    
    public abstract View getDelegatingView();
    
    public abstract View getImmediateChildContainingVerticalScroller();
    
    public abstract View getPositionedBackgroundContainer();
    
    public abstract View getRelevantChildUnderTouch(float paramFloat1, float paramFloat2);
    
    public abstract int getSideMargin();
    
    public abstract View getTouchableBackgroundView();
    
    public abstract boolean isBackgroundSpacerView(View paramView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.ScrollLockingTouchDelegate
 * JD-Core Version:    0.7.0.1
 */