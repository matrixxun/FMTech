package com.google.android.wallet.ui.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import com.google.android.wallet.uicomponents.R.styleable;

public class FocusedViewToTopScrollView
  extends ScrollView
  implements ViewTreeObserver.OnGlobalLayoutListener
{
  private static boolean DEBUG_PADDING_INJECTION = false;
  private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
  private boolean mAnimateScroll;
  private float mAvgSpeedInPixelsPerMilli;
  int mCurrentRangeInPixels;
  int mCurrentYTarget;
  private DisplayMetrics mDisplayMetricsWithoutChrome;
  private int mFocusedViewOffsetInPixels;
  private boolean mInjectPaddingForScrollToTopWithAdjustPan;
  private Interpolator mInterpolator;
  private boolean mIsInLayout;
  private boolean mIsLaidOut;
  private boolean mIsSoftKeyboardObscuringViewWithAdjustPan;
  int mLastScrolledToPosition;
  private long mMaxDuration;
  private View mPaddingView;
  Runnable mScrollEvent;
  private boolean mScrollToTop;
  ValueAnimator mScrollValueAnimator;
  private int mThresholdToScrollInPixels;
  private int mWindowSoftInputMode = 0;
  
  public FocusedViewToTopScrollView(Context paramContext)
  {
    super(paramContext);
    this.mScrollToTop = bool;
    if (Build.VERSION.SDK_INT >= 14) {}
    for (;;)
    {
      this.mAnimateScroll = bool;
      this.mIsInLayout = false;
      this.mIsLaidOut = false;
      this.mCurrentYTarget = -1;
      this.mLastScrolledToPosition = -1;
      this.mScrollValueAnimator = null;
      this.mCurrentRangeInPixels = -1;
      this.mInterpolator = DEFAULT_INTERPOLATOR;
      this.mMaxDuration = 500L;
      this.mScrollEvent = null;
      initialize(paramContext, null);
      return;
      bool = false;
    }
  }
  
  public FocusedViewToTopScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mScrollToTop = bool;
    if (Build.VERSION.SDK_INT >= 14) {}
    for (;;)
    {
      this.mAnimateScroll = bool;
      this.mIsInLayout = false;
      this.mIsLaidOut = false;
      this.mCurrentYTarget = -1;
      this.mLastScrolledToPosition = -1;
      this.mScrollValueAnimator = null;
      this.mCurrentRangeInPixels = -1;
      this.mInterpolator = DEFAULT_INTERPOLATOR;
      this.mMaxDuration = 500L;
      this.mScrollEvent = null;
      initialize(paramContext, paramAttributeSet);
      return;
      bool = false;
    }
  }
  
  public FocusedViewToTopScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mScrollToTop = bool;
    if (Build.VERSION.SDK_INT >= 14) {}
    for (;;)
    {
      this.mAnimateScroll = bool;
      this.mIsInLayout = false;
      this.mIsLaidOut = false;
      this.mCurrentYTarget = -1;
      this.mLastScrolledToPosition = -1;
      this.mScrollValueAnimator = null;
      this.mCurrentRangeInPixels = -1;
      this.mInterpolator = DEFAULT_INTERPOLATOR;
      this.mMaxDuration = 500L;
      this.mScrollEvent = null;
      initialize(paramContext, paramAttributeSet);
      return;
      bool = false;
    }
  }
  
  @TargetApi(21)
  public FocusedViewToTopScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    this.mScrollToTop = bool;
    if (Build.VERSION.SDK_INT >= 14) {}
    for (;;)
    {
      this.mAnimateScroll = bool;
      this.mIsInLayout = false;
      this.mIsLaidOut = false;
      this.mCurrentYTarget = -1;
      this.mLastScrolledToPosition = -1;
      this.mScrollValueAnimator = null;
      this.mCurrentRangeInPixels = -1;
      this.mInterpolator = DEFAULT_INTERPOLATOR;
      this.mMaxDuration = 500L;
      this.mScrollEvent = null;
      initialize(paramContext, paramAttributeSet);
      return;
      bool = false;
    }
  }
  
  @TargetApi(14)
  private void cancelScrollAnimationIfNecessary()
  {
    if (this.mScrollValueAnimator != null) {
      this.mScrollValueAnimator.cancel();
    }
  }
  
  private ViewGroup createWrapperViewForChild(View paramView)
  {
    LinearLayout localLinearLayout = new LinearLayout(getContext());
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams(localLayoutParams);
    localLinearLayout.addView(paramView);
    return localLinearLayout;
  }
  
  private static boolean doesViewSupportDummyPaddingView(View paramView)
  {
    return ((paramView instanceof LinearLayout)) && (((LinearLayout)paramView).getOrientation() == 1);
  }
  
  private void initialize(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WalletUicFocusedViewToTopScrollView);
    this.mFocusedViewOffsetInPixels = localTypedArray.getDimensionPixelOffset(R.styleable.WalletUicFocusedViewToTopScrollView_internalUicFocusedViewOffset, 0);
    this.mThresholdToScrollInPixels = localTypedArray.getDimensionPixelOffset(R.styleable.WalletUicFocusedViewToTopScrollView_internalUicThresholdToScroll, 0);
    setMaxDuration(localTypedArray.getInteger(R.styleable.WalletUicFocusedViewToTopScrollView_internalUicMaxDuration, 500));
    setAverageSpeed(localTypedArray.getDimension(R.styleable.WalletUicFocusedViewToTopScrollView_internalUicAverageSpeed, 250.0F));
    localTypedArray.recycle();
    setSmoothScrollingEnabled(false);
    this.mDisplayMetricsWithoutChrome = getContext().getResources().getDisplayMetrics();
  }
  
  private boolean isAdjustPanViewPaddingHeuristicEnabled()
  {
    return (this.mScrollToTop) && (this.mInjectPaddingForScrollToTopWithAdjustPan) && ((0x20 & this.mWindowSoftInputMode) != 0);
  }
  
  private void performScrollToYImmediately(int paramInt)
  {
    super.scrollTo(getScrollX(), paramInt);
  }
  
  private void postScrollToY(final int paramInt, final boolean paramBoolean)
  {
    if ((this.mIsInLayout) && (getScrollY() == paramInt)) {
      performScrollToYImmediately(paramInt);
    }
    do
    {
      return;
      if (this.mScrollEvent != null)
      {
        removeCallbacks(this.mScrollEvent);
        this.mScrollEvent = null;
      }
    } while ((this.mCurrentYTarget != -1) && (Math.abs(this.mCurrentYTarget - paramInt) <= this.mThresholdToScrollInPixels) && (this.mLastScrolledToPosition == getScrollY()));
    this.mScrollEvent = new Runnable()
    {
      public final void run()
      {
        FocusedViewToTopScrollView.this.mScrollEvent = null;
        if (paramBoolean)
        {
          FocusedViewToTopScrollView.access$000(FocusedViewToTopScrollView.this, paramInt);
          return;
        }
        FocusedViewToTopScrollView.this.cancelScrollAnimationIfNecessary();
        FocusedViewToTopScrollView.this.performScrollToYImmediately(paramInt);
      }
    };
    post(this.mScrollEvent);
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((isAdjustPanViewPaddingHeuristicEnabled()) && (!doesViewSupportDummyPaddingView(paramView))) {
      paramView = createWrapperViewForChild(paramView);
    }
    super.addView(paramView, paramInt, paramLayoutParams);
  }
  
  protected int computeScrollDeltaToGetChildRectOnScreen(Rect paramRect)
  {
    int k;
    if (!this.mScrollToTop) {
      k = super.computeScrollDeltaToGetChildRectOnScreen(paramRect);
    }
    for (;;)
    {
      return k;
      int i = getScrollY();
      int j = i + getHeight();
      k = Math.max(paramRect.top - i - this.mFocusedViewOffsetInPixels, -getScrollY());
      if (paramRect.bottom > j + k) {
        if (paramRect.height() <= getHeight()) {
          break label94;
        }
      }
      label94:
      for (k = paramRect.top - i; Math.abs(k) <= this.mThresholdToScrollInPixels; k = paramRect.bottom - j) {
        return 0;
      }
    }
  }
  
  public boolean getAnimateScroll()
  {
    return this.mAnimateScroll;
  }
  
  public boolean getInjectPaddingForScrollToTopWithAdjustPan()
  {
    return this.mInjectPaddingForScrollToTopWithAdjustPan;
  }
  
  public boolean getScrollToTop()
  {
    return this.mScrollToTop;
  }
  
  public void onGlobalLayout()
  {
    Rect localRect = new Rect();
    boolean bool = WalletUiUtils.isSoftKeyboardObscuringViewWithAdjustPan(localRect, this, this.mDisplayMetricsWithoutChrome);
    if (DEBUG_PADDING_INJECTION) {
      Log.v("FocusedViewToTopScr", "onGlobalLayout softKeyboardUp=" + bool);
    }
    if (((!bool) || (this.mPaddingView != null)) && (bool == this.mIsSoftKeyboardObscuringViewWithAdjustPan)) {}
    ViewGroup localViewGroup;
    do
    {
      return;
      this.mIsSoftKeyboardObscuringViewWithAdjustPan = bool;
      localViewGroup = (ViewGroup)getChildAt(0);
      if (this.mPaddingView != null)
      {
        localViewGroup.removeView(this.mPaddingView);
        this.mPaddingView = null;
      }
    } while (!bool);
    int i = localRect.bottom - localRect.top;
    View localView = getRootView().findViewById(16908290);
    int j = localView.getHeight();
    int[] arrayOfInt = new int[2];
    WalletUiUtils.getLocationRelativeToAncestorView(arrayOfInt, this, localView);
    int k = j - (arrayOfInt[1] + getHeight());
    int m = (this.mDisplayMetricsWithoutChrome.heightPixels - j) / 2;
    int n = this.mDisplayMetricsWithoutChrome.heightPixels - i - k - m;
    if (n < 0)
    {
      Log.i("FocusedViewToTopScr", "paddingToInsert=" + n + " < 0. Aborting.");
      return;
    }
    if (WalletUiUtils.getViewHeightWithMargins(localViewGroup) == getHeight())
    {
      int i1 = (int)(0.98F * this.mDisplayMetricsWithoutChrome.heightPixels) - j;
      if (DEBUG_PADDING_INJECTION) {
        Log.v("FocusedViewToTopScr", "paddingToAdd=" + i1);
      }
      n += i1;
    }
    if (DEBUG_PADDING_INJECTION) {
      Log.v("FocusedViewToTopScr", "paddingToInsert=" + n);
    }
    this.mPaddingView = new View(getContext());
    localViewGroup.addView(this.mPaddingView, 0, n);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mIsInLayout = true;
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mIsInLayout = false;
    this.mIsLaidOut = true;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof Bundle))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    Bundle localBundle = (Bundle)paramParcelable;
    super.onRestoreInstanceState(localBundle.getParcelable("superInstanceState"));
    this.mThresholdToScrollInPixels = localBundle.getInt("thresholdToScroll");
    this.mScrollToTop = localBundle.getBoolean("scrollToTop");
    this.mFocusedViewOffsetInPixels = localBundle.getInt("focusedViewOffset");
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("superInstanceState", super.onSaveInstanceState());
    localBundle.putInt("thresholdToScroll", this.mThresholdToScrollInPixels);
    localBundle.putBoolean("scrollToTop", this.mScrollToTop);
    localBundle.putInt("focusedViewOffset", this.mFocusedViewOffsetInPixels);
    return localBundle;
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    boolean bool;
    if (!this.mAnimateScroll) {
      bool = super.requestChildRectangleOnScreen(paramView, paramRect, paramBoolean);
    }
    int i;
    for (;;)
    {
      return bool;
      paramRect.offset(paramView.getLeft() - paramView.getScrollX(), paramView.getTop() - paramView.getScrollY());
      i = computeScrollDeltaToGetChildRectOnScreen(paramRect);
      if (i != 0) {}
      for (bool = true; bool; bool = false)
      {
        if (!paramBoolean) {
          break label86;
        }
        postScrollToY(i + getScrollY(), false);
        return bool;
      }
    }
    label86:
    scrollBy(0, i);
    return bool;
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    if ((this.mAnimateScroll) && (this.mIsLaidOut))
    {
      postScrollToY(paramInt2, true);
      return;
    }
    super.scrollTo(paramInt1, paramInt2);
  }
  
  public void setAnimateScroll(boolean paramBoolean)
  {
    if ((this.mAnimateScroll != paramBoolean) && (Build.VERSION.SDK_INT >= 14))
    {
      this.mAnimateScroll = paramBoolean;
      cancelScrollAnimationIfNecessary();
    }
  }
  
  public void setAverageSpeed(float paramFloat)
  {
    this.mAvgSpeedInPixelsPerMilli = ((int)TypedValue.applyDimension(1, paramFloat, getResources().getDisplayMetrics()) / 1000.0F);
  }
  
  public void setFocusedViewOffsetInPixels(int paramInt)
  {
    this.mFocusedViewOffsetInPixels = paramInt;
  }
  
  public void setInterpolator(Interpolator paramInterpolator)
  {
    this.mInterpolator = paramInterpolator;
  }
  
  public void setMaxDuration(long paramLong)
  {
    this.mMaxDuration = paramLong;
  }
  
  public void setScrollToTop(boolean paramBoolean)
  {
    setScrollToTop(paramBoolean, false, 0);
  }
  
  public final void setScrollToTop(boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    this.mScrollToTop = paramBoolean1;
    this.mInjectPaddingForScrollToTopWithAdjustPan = paramBoolean2;
    this.mWindowSoftInputMode = paramInt;
    getViewTreeObserver().removeGlobalOnLayoutListener(this);
    if (this.mPaddingView != null)
    {
      removeView(this.mPaddingView);
      this.mPaddingView = null;
    }
    if (isAdjustPanViewPaddingHeuristicEnabled())
    {
      getViewTreeObserver().addOnGlobalLayoutListener(this);
      View localView = getChildAt(0);
      if ((localView != null) && (!doesViewSupportDummyPaddingView(localView)))
      {
        removeView(localView);
        addView(createWrapperViewForChild(localView));
      }
    }
  }
  
  public void setThresholdToScrollInPixels(int paramInt)
  {
    this.mThresholdToScrollInPixels = paramInt;
  }
  
  private static final class MomentumInterpolator
    implements Interpolator
  {
    private final Interpolator mBaseInterpolator;
    private final float mInitialVelocity;
    private final float mNormalizationConstant;
    
    public MomentumInterpolator(Interpolator paramInterpolator, float paramFloat)
    {
      this.mInitialVelocity = paramFloat;
      this.mBaseInterpolator = paramInterpolator;
      this.mNormalizationConstant = (1.0F - 0.5F * paramFloat);
    }
    
    public final float getInterpolation(float paramFloat)
    {
      return this.mBaseInterpolator.getInterpolation(paramFloat) * this.mNormalizationConstant + (0.5F * paramFloat + 0.1591549F * (float)Math.sin(3.141592653589793D * paramFloat)) * this.mInitialVelocity;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.FocusedViewToTopScrollView
 * JD-Core Version:    0.7.0.1
 */