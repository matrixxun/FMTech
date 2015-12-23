package android.support.v4.widget;

import android.content.res.Resources;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public abstract class AutoScrollHelper
  implements View.OnTouchListener
{
  private static final int DEFAULT_ACTIVATION_DELAY = ;
  private int mActivationDelay;
  private boolean mAlreadyDelayed;
  private boolean mAnimating;
  private final Interpolator mEdgeInterpolator = new AccelerateInterpolator();
  private int mEdgeType;
  private boolean mEnabled;
  private boolean mExclusive;
  private float[] mMaximumEdges = { 3.4028235E+38F, 3.4028235E+38F };
  private float[] mMaximumVelocity = { 3.4028235E+38F, 3.4028235E+38F };
  private float[] mMinimumVelocity = { 0.0F, 0.0F };
  private boolean mNeedsCancel;
  private boolean mNeedsReset;
  private float[] mRelativeEdges = { 0.0F, 0.0F };
  private float[] mRelativeVelocity = { 0.0F, 0.0F };
  private Runnable mRunnable;
  private final ClampedScroller mScroller = new ClampedScroller();
  private final View mTarget;
  
  public AutoScrollHelper(View paramView)
  {
    this.mTarget = paramView;
    DisplayMetrics localDisplayMetrics = Resources.getSystem().getDisplayMetrics();
    int i = (int)(0.5F + 1575.0F * localDisplayMetrics.density);
    int j = (int)(0.5F + 315.0F * localDisplayMetrics.density);
    float f1 = i;
    this.mMaximumVelocity[0] = (f1 / 1000.0F);
    this.mMaximumVelocity[1] = (f1 / 1000.0F);
    float f2 = j;
    this.mMinimumVelocity[0] = (f2 / 1000.0F);
    this.mMinimumVelocity[1] = (f2 / 1000.0F);
    this.mEdgeType = 1;
    this.mMaximumEdges[0] = 3.4028235E+38F;
    this.mMaximumEdges[1] = 3.4028235E+38F;
    this.mRelativeEdges[0] = 0.2F;
    this.mRelativeEdges[1] = 0.2F;
    this.mRelativeVelocity[0] = 0.001F;
    this.mRelativeVelocity[1] = 0.001F;
    this.mActivationDelay = DEFAULT_ACTIVATION_DELAY;
    this.mScroller.mRampUpDuration = 500;
    this.mScroller.mRampDownDuration = 500;
  }
  
  private float computeTargetVelocity(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = this.mRelativeEdges[paramInt];
    float f2 = this.mMaximumEdges[paramInt];
    float f3 = constrain(f1 * paramFloat2, 0.0F, f2);
    float f4 = constrainEdgeValue(paramFloat1, f3);
    float f5 = constrainEdgeValue(paramFloat2 - paramFloat1, f3) - f4;
    float f11;
    if (f5 < 0.0F) {
      f11 = -this.mEdgeInterpolator.getInterpolation(-f5);
    }
    for (float f6 = constrain(f11, -1.0F, 1.0F);; f6 = 0.0F)
    {
      if (f6 != 0.0F) {
        break label121;
      }
      return 0.0F;
      if (f5 > 0.0F)
      {
        f11 = this.mEdgeInterpolator.getInterpolation(f5);
        break;
      }
    }
    label121:
    float f7 = this.mRelativeVelocity[paramInt];
    float f8 = this.mMinimumVelocity[paramInt];
    float f9 = this.mMaximumVelocity[paramInt];
    float f10 = f7 * paramFloat3;
    if (f6 > 0.0F) {
      return constrain(f6 * f10, f8, f9);
    }
    return -constrain(f10 * -f6, f8, f9);
  }
  
  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 > paramFloat3) {
      return paramFloat3;
    }
    if (paramFloat1 < paramFloat2) {
      return paramFloat2;
    }
    return paramFloat1;
  }
  
  private float constrainEdgeValue(float paramFloat1, float paramFloat2)
  {
    if (paramFloat2 == 0.0F) {}
    do
    {
      do
      {
        do
        {
          return 0.0F;
          switch (this.mEdgeType)
          {
          default: 
            return 0.0F;
          }
        } while (paramFloat1 >= paramFloat2);
        if (paramFloat1 >= 0.0F) {
          return 1.0F - paramFloat1 / paramFloat2;
        }
      } while ((!this.mAnimating) || (this.mEdgeType != 1));
      return 1.0F;
    } while (paramFloat1 >= 0.0F);
    return paramFloat1 / -paramFloat2;
  }
  
  private void requestStop()
  {
    if (this.mNeedsReset)
    {
      this.mAnimating = false;
      return;
    }
    ClampedScroller localClampedScroller = this.mScroller;
    long l = AnimationUtils.currentAnimationTimeMillis();
    int i = (int)(l - localClampedScroller.mStartTime);
    int j = localClampedScroller.mRampDownDuration;
    if (i > j) {}
    for (;;)
    {
      localClampedScroller.mEffectiveRampDown = j;
      localClampedScroller.mStopValue = localClampedScroller.getValueAt(l);
      localClampedScroller.mStopTime = l;
      return;
      if (i < 0) {
        j = 0;
      } else {
        j = i;
      }
    }
  }
  
  private boolean shouldAnimate()
  {
    ClampedScroller localClampedScroller = this.mScroller;
    int i = (int)(localClampedScroller.mTargetVelocityY / Math.abs(localClampedScroller.mTargetVelocityY));
    int j = (int)(localClampedScroller.mTargetVelocityX / Math.abs(localClampedScroller.mTargetVelocityX));
    if ((i == 0) || (!canTargetScrollVertically(i)))
    {
      if (j != 0) {}
      return false;
    }
    return true;
  }
  
  public abstract boolean canTargetScrollVertically(int paramInt);
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (!this.mEnabled) {}
    for (;;)
    {
      return false;
      switch (MotionEventCompat.getActionMasked(paramMotionEvent))
      {
      }
      while ((this.mExclusive) && (this.mAnimating))
      {
        return true;
        this.mNeedsCancel = true;
        this.mAlreadyDelayed = false;
        float f1 = computeTargetVelocity(0, paramMotionEvent.getX(), paramView.getWidth(), this.mTarget.getWidth());
        float f2 = computeTargetVelocity(1, paramMotionEvent.getY(), paramView.getHeight(), this.mTarget.getHeight());
        ClampedScroller localClampedScroller = this.mScroller;
        localClampedScroller.mTargetVelocityX = f1;
        localClampedScroller.mTargetVelocityY = f2;
        if ((!this.mAnimating) && (shouldAnimate()))
        {
          if (this.mRunnable == null) {
            this.mRunnable = new ScrollAnimationRunnable((byte)0);
          }
          this.mAnimating = true;
          this.mNeedsReset = true;
          if ((!this.mAlreadyDelayed) && (this.mActivationDelay > 0)) {
            ViewCompat.postOnAnimationDelayed(this.mTarget, this.mRunnable, this.mActivationDelay);
          }
          for (;;)
          {
            this.mAlreadyDelayed = true;
            break;
            this.mRunnable.run();
          }
          requestStop();
        }
      }
    }
  }
  
  public abstract void scrollTargetBy$255f295(int paramInt);
  
  public final AutoScrollHelper setEnabled(boolean paramBoolean)
  {
    if ((this.mEnabled) && (!paramBoolean)) {
      requestStop();
    }
    this.mEnabled = paramBoolean;
    return this;
  }
  
  private static final class ClampedScroller
  {
    long mDeltaTime = 0L;
    int mDeltaX = 0;
    int mDeltaY = 0;
    int mEffectiveRampDown;
    int mRampDownDuration;
    int mRampUpDuration;
    long mStartTime = -9223372036854775808L;
    long mStopTime = -1L;
    float mStopValue;
    float mTargetVelocityX;
    float mTargetVelocityY;
    
    final float getValueAt(long paramLong)
    {
      if (paramLong < this.mStartTime) {
        return 0.0F;
      }
      if ((this.mStopTime < 0L) || (paramLong < this.mStopTime)) {
        return 0.5F * AutoScrollHelper.access$900$483d241b((float)(paramLong - this.mStartTime) / this.mRampUpDuration);
      }
      long l = paramLong - this.mStopTime;
      return 1.0F - this.mStopValue + this.mStopValue * AutoScrollHelper.access$900$483d241b((float)l / this.mEffectiveRampDown);
    }
  }
  
  private final class ScrollAnimationRunnable
    implements Runnable
  {
    private ScrollAnimationRunnable() {}
    
    public final void run()
    {
      if (!AutoScrollHelper.this.mAnimating) {
        return;
      }
      if (AutoScrollHelper.this.mNeedsReset)
      {
        AutoScrollHelper.access$202$2149d4c8(AutoScrollHelper.this);
        AutoScrollHelper.ClampedScroller localClampedScroller2 = AutoScrollHelper.this.mScroller;
        localClampedScroller2.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        localClampedScroller2.mStopTime = -1L;
        localClampedScroller2.mDeltaTime = localClampedScroller2.mStartTime;
        localClampedScroller2.mStopValue = 0.5F;
        localClampedScroller2.mDeltaX = 0;
        localClampedScroller2.mDeltaY = 0;
      }
      AutoScrollHelper.ClampedScroller localClampedScroller1 = AutoScrollHelper.this.mScroller;
      boolean bool1 = localClampedScroller1.mStopTime < 0L;
      int i = 0;
      if (bool1)
      {
        boolean bool2 = AnimationUtils.currentAnimationTimeMillis() < localClampedScroller1.mStopTime + localClampedScroller1.mEffectiveRampDown;
        i = 0;
        if (bool2) {
          i = 1;
        }
      }
      if ((i != 0) || (!AutoScrollHelper.this.shouldAnimate()))
      {
        AutoScrollHelper.access$102$2149d4c8(AutoScrollHelper.this);
        return;
      }
      if (AutoScrollHelper.this.mNeedsCancel)
      {
        AutoScrollHelper.access$502$2149d4c8(AutoScrollHelper.this);
        AutoScrollHelper.access$600(AutoScrollHelper.this);
      }
      if (localClampedScroller1.mDeltaTime == 0L) {
        throw new RuntimeException("Cannot compute scroll delta before calling start()");
      }
      long l1 = AnimationUtils.currentAnimationTimeMillis();
      float f1 = localClampedScroller1.getValueAt(l1);
      float f2 = f1 * (-4.0F * f1) + f1 * 4.0F;
      long l2 = l1 - localClampedScroller1.mDeltaTime;
      localClampedScroller1.mDeltaTime = l1;
      localClampedScroller1.mDeltaX = ((int)(f2 * (float)l2 * localClampedScroller1.mTargetVelocityX));
      localClampedScroller1.mDeltaY = ((int)(f2 * (float)l2 * localClampedScroller1.mTargetVelocityY));
      int j = localClampedScroller1.mDeltaY;
      AutoScrollHelper.this.scrollTargetBy$255f295(j);
      ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.AutoScrollHelper
 * JD-Core Version:    0.7.0.1
 */