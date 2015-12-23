package android.support.design.widget;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.view.MotionEvent;
import android.view.View;

public class SwipeDismissBehavior<V extends View>
  extends CoordinatorLayout.Behavior<V>
{
  float mAlphaEndSwipeDistance = 0.5F;
  float mAlphaStartSwipeDistance = 0.0F;
  private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback()
  {
    private int mOriginalCapturedViewLeft;
    
    public final int clampViewPositionHorizontal$17e143b0(View paramAnonymousView, int paramAnonymousInt)
    {
      int i;
      int j;
      int k;
      if (ViewCompat.getLayoutDirection(paramAnonymousView) == 1)
      {
        i = 1;
        if (SwipeDismissBehavior.this.mSwipeDirection != 0) {
          break label78;
        }
        if (i == 0) {
          break label58;
        }
        j = this.mOriginalCapturedViewLeft - paramAnonymousView.getWidth();
        k = this.mOriginalCapturedViewLeft;
      }
      for (;;)
      {
        return Math.min(Math.max(j, paramAnonymousInt), k);
        i = 0;
        break;
        label58:
        j = this.mOriginalCapturedViewLeft;
        k = this.mOriginalCapturedViewLeft + paramAnonymousView.getWidth();
        continue;
        label78:
        if (SwipeDismissBehavior.this.mSwipeDirection == 1)
        {
          if (i != 0)
          {
            j = this.mOriginalCapturedViewLeft;
            k = this.mOriginalCapturedViewLeft + paramAnonymousView.getWidth();
          }
          else
          {
            j = this.mOriginalCapturedViewLeft - paramAnonymousView.getWidth();
            k = this.mOriginalCapturedViewLeft;
          }
        }
        else
        {
          j = this.mOriginalCapturedViewLeft - paramAnonymousView.getWidth();
          k = this.mOriginalCapturedViewLeft + paramAnonymousView.getWidth();
        }
      }
    }
    
    public final int clampViewPositionVertical$17e143b0(View paramAnonymousView)
    {
      return paramAnonymousView.getTop();
    }
    
    public final int getViewHorizontalDragRange(View paramAnonymousView)
    {
      return paramAnonymousView.getWidth();
    }
    
    public final void onViewDragStateChanged(int paramAnonymousInt)
    {
      if (SwipeDismissBehavior.this.mListener != null) {
        SwipeDismissBehavior.this.mListener.onDragStateChanged(paramAnonymousInt);
      }
    }
    
    public final void onViewPositionChanged$5b6f797d(View paramAnonymousView, int paramAnonymousInt)
    {
      float f1 = this.mOriginalCapturedViewLeft + paramAnonymousView.getWidth() * SwipeDismissBehavior.this.mAlphaStartSwipeDistance;
      float f2 = this.mOriginalCapturedViewLeft + paramAnonymousView.getWidth() * SwipeDismissBehavior.this.mAlphaEndSwipeDistance;
      if (paramAnonymousInt <= f1)
      {
        ViewCompat.setAlpha(paramAnonymousView, 1.0F);
        return;
      }
      if (paramAnonymousInt >= f2)
      {
        ViewCompat.setAlpha(paramAnonymousView, 0.0F);
        return;
      }
      ViewCompat.setAlpha(paramAnonymousView, SwipeDismissBehavior.clamp(0.0F, 1.0F - (paramAnonymousInt - f1) / (f2 - f1), 1.0F));
    }
    
    public final void onViewReleased$17e2ac03(View paramAnonymousView, float paramAnonymousFloat)
    {
      int i = 1;
      int j = paramAnonymousView.getWidth();
      int i1;
      label36:
      int n;
      label60:
      boolean bool;
      if (paramAnonymousFloat != 0.0F) {
        if (ViewCompat.getLayoutDirection(paramAnonymousView) == i)
        {
          i1 = i;
          if (SwipeDismissBehavior.this.mSwipeDirection != 2) {
            break label107;
          }
          if (i == 0) {
            break label243;
          }
          if (paramAnonymousView.getLeft() >= this.mOriginalCapturedViewLeft) {
            break label231;
          }
          n = this.mOriginalCapturedViewLeft - j;
          bool = true;
          label63:
          if (!SwipeDismissBehavior.this.mViewDragHelper.settleCapturedViewAt(n, paramAnonymousView.getTop())) {
            break label255;
          }
          ViewCompat.postOnAnimation(paramAnonymousView, new SwipeDismissBehavior.SettleRunnable(SwipeDismissBehavior.this, paramAnonymousView, bool));
        }
      }
      label107:
      label243:
      label255:
      while ((!bool) || (SwipeDismissBehavior.this.mListener == null))
      {
        return;
        i1 = 0;
        break;
        if (SwipeDismissBehavior.this.mSwipeDirection == 0)
        {
          if (i1 != 0)
          {
            if (paramAnonymousFloat < 0.0F) {
              break label36;
            }
            i = 0;
            break label36;
          }
          if (paramAnonymousFloat > 0.0F) {
            break label36;
          }
          i = 0;
          break label36;
        }
        if (SwipeDismissBehavior.this.mSwipeDirection == i)
        {
          if (i1 != 0)
          {
            if (paramAnonymousFloat > 0.0F) {
              break label36;
            }
            i = 0;
            break label36;
          }
          if (paramAnonymousFloat < 0.0F) {
            break label36;
          }
          i = 0;
          break label36;
          int k = paramAnonymousView.getLeft() - this.mOriginalCapturedViewLeft;
          int m = Math.round(paramAnonymousView.getWidth() * SwipeDismissBehavior.this.mDragDismissThreshold);
          if (Math.abs(k) >= m) {
            break label36;
          }
          i = 0;
          break label36;
        }
        i = 0;
        break label36;
        n = j + this.mOriginalCapturedViewLeft;
        break label60;
        n = this.mOriginalCapturedViewLeft;
        bool = false;
        break label63;
      }
      label231:
      SwipeDismissBehavior.this.mListener.onDismiss$3c7ec8c3();
    }
    
    public final boolean tryCaptureView$5359dc96(View paramAnonymousView)
    {
      this.mOriginalCapturedViewLeft = paramAnonymousView.getLeft();
      return true;
    }
  };
  float mDragDismissThreshold = 0.5F;
  private boolean mIgnoreEvents;
  OnDismissListener mListener;
  private float mSensitivity = 0.0F;
  private boolean mSensitivitySet;
  int mSwipeDirection = 2;
  ViewDragHelper mViewDragHelper;
  
  static float clamp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return Math.min(Math.max(0.0F, paramFloat2), 1.0F);
  }
  
  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    switch (MotionEventCompat.getActionMasked(paramMotionEvent))
    {
    case 2: 
    default: 
      if (paramCoordinatorLayout.isPointInChildBounds(paramV, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) {
        break;
      }
    }
    for (boolean bool = true;; bool = false)
    {
      this.mIgnoreEvents = bool;
      do
      {
        if (!this.mIgnoreEvents) {
          break;
        }
        return false;
      } while (!this.mIgnoreEvents);
      this.mIgnoreEvents = false;
      return false;
    }
    if (this.mViewDragHelper == null) {
      if (!this.mSensitivitySet) {
        break label131;
      }
    }
    label131:
    for (ViewDragHelper localViewDragHelper = ViewDragHelper.create(paramCoordinatorLayout, this.mSensitivity, this.mDragCallback);; localViewDragHelper = ViewDragHelper.create(paramCoordinatorLayout, this.mDragCallback))
    {
      this.mViewDragHelper = localViewDragHelper;
      return this.mViewDragHelper.shouldInterceptTouchEvent(paramMotionEvent);
    }
  }
  
  public final boolean onTouchEvent$29533e61(MotionEvent paramMotionEvent)
  {
    if (this.mViewDragHelper != null)
    {
      this.mViewDragHelper.processTouchEvent(paramMotionEvent);
      return true;
    }
    return false;
  }
  
  public static abstract interface OnDismissListener
  {
    public abstract void onDismiss$3c7ec8c3();
    
    public abstract void onDragStateChanged(int paramInt);
  }
  
  private final class SettleRunnable
    implements Runnable
  {
    private final boolean mDismiss;
    private final View mView;
    
    SettleRunnable(View paramView, boolean paramBoolean)
    {
      this.mView = paramView;
      this.mDismiss = paramBoolean;
    }
    
    public final void run()
    {
      if ((SwipeDismissBehavior.this.mViewDragHelper != null) && (SwipeDismissBehavior.this.mViewDragHelper.continueSettling$138603())) {
        ViewCompat.postOnAnimation(this.mView, this);
      }
      while ((!this.mDismiss) || (SwipeDismissBehavior.this.mListener == null)) {
        return;
      }
      SwipeDismissBehavior.this.mListener.onDismiss$3c7ec8c3();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.design.widget.SwipeDismissBehavior
 * JD-Core Version:    0.7.0.1
 */