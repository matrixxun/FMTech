package com.google.android.play.headerlist;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.ItemInfo;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.play.R.attr;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.drawable;
import com.google.android.play.R.id;
import com.google.android.play.R.layout;
import com.google.android.play.animation.AnimationCompat;
import com.google.android.play.animation.AnimationCompat.2;
import com.google.android.play.widget.ScrollProxyView;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

public class PlayHeaderListLayout
  extends FrameLayout
  implements SwipeRefreshLayout.OnRefreshListener
{
  static final boolean SUPPORT_DRAW_STATUS_BAR;
  private static final boolean SUPPORT_ELEVATION;
  private static final boolean USE_ANIMATIONS;
  private static Map<View, Integer> sActionbarAttachedCount;
  private int mAbsoluteY;
  private float mActionBarTitleAlpha = 0.5F;
  private boolean mAllowImmersiveBackground;
  private View mAltPlayBackground;
  private AnimationCompat mAltPlayBackgroundCompat;
  private boolean mAlwaysUseFloatingBackground;
  private Map<String, ObjectAnimator> mAnimatorMap = new HashMap();
  PlayScrollableContentView.OnScrollListener mAppContentViewOnScrollListener;
  AbsListView.OnScrollListener mAppListViewOnScrollListener;
  RecyclerView.OnScrollListener mAppRecyclerViewOnScrollListener;
  private boolean mAttached;
  private boolean mAutoHideToolbarTitle;
  private FrameLayout mBackgroundContainer;
  private AnimationCompat mBackgroundContainerCompat;
  private final float mBackgroundFadeInOffsetThresholdPx;
  private float mBackgroundParallaxRatio;
  private float mBannerFraction;
  private CharSequence mBannerText;
  private TextView mBannerTextView;
  private AnimationCompat mBannerTextViewCompat;
  private boolean mBannerVisible;
  private View mContentContainer;
  private AnimationCompat mContentContainerCompat;
  private Drawable mContentProtectionBackground;
  private boolean mContentProtectionEnabled;
  private boolean mControlsAreFloating;
  private ViewGroup mControlsContainer;
  private AnimationCompat mControlsContainerCompat;
  private ViewPager.OnPageChangeListener mExternalPageChangeListener;
  private Drawable mFloatingControlsBackground;
  private float mFloatingFraction;
  private final Handler mHandler = new Handler();
  private boolean mHasPullToRefresh;
  private boolean mHasViewPager;
  private int mHeaderBottomMargin;
  private int mHeaderHeight;
  private int mHeaderLockMode;
  private int mHeaderMode;
  private View mHeaderShadow;
  private AnimationCompat mHeaderShadowCompat;
  private int mHeaderShadowMode;
  private boolean mHeaderShadowVisible;
  private int mHeroAnimationMode = 0;
  private FrameLayout mHeroContainer;
  private AnimationCompat mHeroContainerCompat;
  private boolean mHeroVisible = true;
  private boolean mLastScrollWasDown = true;
  private boolean mLastSnapControlsWasDown;
  LayoutListener mLayoutListener;
  private int mListViewId;
  private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
  {
    public final void onPageScrollStateChanged(int paramAnonymousInt)
    {
      if (PlayHeaderListLayout.this.mExternalPageChangeListener != null) {
        PlayHeaderListLayout.this.mExternalPageChangeListener.onPageScrollStateChanged(paramAnonymousInt);
      }
      if ((paramAnonymousInt != 0) && ((PlayHeaderListLayout.this.mHeaderMode == 0) || (PlayHeaderListLayout.this.mHeaderMode == 2))) {
        PlayHeaderListLayout.access$200$1350701e(PlayHeaderListLayout.this, true, true);
      }
    }
    
    public final void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2)
    {
      if (PlayHeaderListLayout.this.mPullToRefreshProvider != null) {
        if (paramAnonymousFloat <= 0.5F) {
          break label136;
        }
      }
      label136:
      for (int i = 1;; i = 0)
      {
        int j = i + paramAnonymousInt1;
        if (j != PlayHeaderListLayout.this.mPullToRefreshAdapterPage)
        {
          PlayHeaderListLayout.access$502(PlayHeaderListLayout.this, j);
          PlayHeaderListLayout.this.mSwipeRefreshLayout.setRefreshing(false);
        }
        if (PlayHeaderListLayout.this.mExternalPageChangeListener != null) {
          PlayHeaderListLayout.this.mExternalPageChangeListener.onPageScrolled(paramAnonymousInt1, paramAnonymousFloat, paramAnonymousInt2);
        }
        return;
      }
    }
    
    public final void onPageSelected(int paramAnonymousInt)
    {
      PlayHeaderListLayout.this.updateActiveListView();
      if (PlayHeaderListLayout.this.mExternalPageChangeListener != null) {
        PlayHeaderListLayout.this.mExternalPageChangeListener.onPageSelected(paramAnonymousInt);
      }
    }
  };
  int mPendingListSync;
  private SavedState mPendingSavedState;
  private int mPullToRefreshAdapterPage = -1;
  PullToRefreshProvider mPullToRefreshProvider;
  private ScrollProxyView mScrollProxyView;
  private final Runnable mSnapControlsDownIfNeededRunnable = new Runnable()
  {
    public final void run()
    {
      PlayHeaderListLayout.access$200$1350701e(PlayHeaderListLayout.this, true, false);
    }
  };
  private final Runnable mSnapControlsUpIfNeededRunnable = new Runnable()
  {
    public final void run()
    {
      PlayHeaderListLayout.access$200$1350701e(PlayHeaderListLayout.this, false, false);
    }
  };
  private int mSpacerId;
  private int mStatusBarHeight;
  private PlayHeaderStatusBarUnderlay mStatusBarUnderlay;
  private boolean mSuppressIdleOnScroll;
  private SwipeRefreshLayout mSwipeRefreshLayout;
  private AnimationCompat mSwipeRefreshLayoutCompat;
  private int mTabA11yMode;
  private View mTabBar;
  private TextView mTabBarTitleView;
  private int mTabBgMode;
  private int mTabMode;
  private int mTabPaddingMode;
  public PlayHeaderListTabStrip mTabStrip;
  private Runnable mTemporaryBannerGoneRunnable;
  private final Runnable mTemporaryBannerTimeoutRunnable = new Runnable()
  {
    public final void run()
    {
      if (PlayHeaderListLayout.this.mTemporaryBannerGoneRunnable != null)
      {
        PlayHeaderListLayout.this.mTemporaryBannerGoneRunnable.run();
        PlayHeaderListLayout.access$902$6938b6e4(PlayHeaderListLayout.this);
      }
      PlayHeaderListLayout.this.setBannerText(null);
    }
  };
  private Toolbar mToolbar;
  private ViewGroup mToolbarContainer;
  private int mToolbarContainerA11yOffset;
  private AnimationCompat mToolbarContainerCompat;
  private int mToolbarHeight;
  private final PlayHeaderScrollableContentListener mTrackedListContentViewScrollListener = new PlayHeaderScrollableContentListener(this);
  private final PlayHeaderListRecyclerViewListener mTrackedListRecyclerViewScrollListener = new PlayHeaderListRecyclerViewListener(this);
  private final PlayHeaderListOnScrollListener mTrackedListScrollListener = new PlayHeaderListOnScrollListener(this);
  private ViewGroup mTrackedListView;
  private boolean mUpdateContentPositionOnLayout;
  private boolean mUseBuiltInToolbar;
  private ViewPager mViewPager;
  private int mViewPagerId;
  
  static
  {
    boolean bool1 = true;
    boolean bool2;
    boolean bool3;
    if (Build.VERSION.SDK_INT > 10)
    {
      bool2 = bool1;
      USE_ANIMATIONS = bool2;
      if (Build.VERSION.SDK_INT < 21) {
        break label58;
      }
      bool3 = bool1;
      label26:
      SUPPORT_ELEVATION = bool3;
      if (Build.VERSION.SDK_INT < 21) {
        break label63;
      }
    }
    for (;;)
    {
      SUPPORT_DRAW_STATUS_BAR = bool1;
      sActionbarAttachedCount = new WeakHashMap();
      return;
      bool2 = false;
      break;
      label58:
      bool3 = false;
      break label26;
      label63:
      bool1 = false;
    }
  }
  
  public PlayHeaderListLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayHeaderListLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayHeaderListLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mBackgroundFadeInOffsetThresholdPx = (20.0F * paramContext.getResources().getDisplayMetrics().density);
  }
  
  private float getContentPosition()
  {
    if (this.mAbsoluteY == -1) {
      return this.mHeaderBottomMargin;
    }
    return Math.max(this.mHeaderBottomMargin, this.mHeaderHeight - this.mAbsoluteY);
  }
  
  private int getDesiredContentTop()
  {
    int i;
    if ((this.mControlsAreFloating) && ((this.mHeaderMode == 0) || (this.mHeaderMode == 2)))
    {
      i = 1;
      if (i == 0) {
        break label46;
      }
    }
    label46:
    for (float f = getFullFloatingHeaderHeight();; f = getVisibleHeaderHeight())
    {
      return (int)f + this.mHeaderBottomMargin;
      i = 0;
      break;
    }
  }
  
  @TargetApi(11)
  private ObjectAnimator getFloatAnimator(String paramString, float paramFloat1, float paramFloat2)
  {
    ObjectAnimator localObjectAnimator1 = (ObjectAnimator)this.mAnimatorMap.get(paramString);
    if (localObjectAnimator1 != null) {
      localObjectAnimator1.cancel();
    }
    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(this, paramString, new float[] { paramFloat1, paramFloat2 });
    this.mAnimatorMap.put(paramString, localObjectAnimator2);
    return localObjectAnimator2;
  }
  
  private float getFloatingHeaderElevation()
  {
    return getResources().getDimensionPixelSize(R.dimen.play_header_list_floating_elevation);
  }
  
  private float getFullFloatingHeaderHeight()
  {
    return getActionBarHeight() + getVisibleTabBarHeight();
  }
  
  private ViewGroup getListView(int paramInt)
  {
    if ((this.mViewPager == null) && (paramInt == 1)) {
      return validateListView(this.mContentContainer.findViewById(this.mListViewId));
    }
    int i = relativeToAbsolute(paramInt);
    View localView;
    if ((this.mViewPager == null) || (this.mViewPager.getAdapter() == null) || (i < 0) || (i >= this.mViewPager.getAdapter().getCount())) {
      localView = null;
    }
    while (localView != null)
    {
      return validateListView(localView.findViewById(this.mListViewId));
      int j = 0;
      if (j < this.mViewPager.getChildCount())
      {
        localView = this.mViewPager.getChildAt(j);
        ViewPager.ItemInfo localItemInfo = this.mViewPager.infoForChild(localView);
        if (localItemInfo == null) {}
        for (Integer localInteger = null;; localInteger = Integer.valueOf(localItemInfo.position))
        {
          if ((localInteger != null) && (localInteger.intValue() == i)) {
            break label160;
          }
          j++;
          break;
        }
      }
      else
      {
        label160:
        localView = null;
      }
    }
    return null;
  }
  
  public static int getMinimumHeaderHeight(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt3 + (0 + getTabBarHeight(paramContext, paramInt1));
  }
  
  private float getNonScrollingFloatingHeaderHeight()
  {
    if (this.mHeaderLockMode == 1) {
      return getVisibleTabBarHeight() + getActionBarHeight();
    }
    switch (this.mHeaderMode)
    {
    default: 
      return 0.0F;
    case 1: 
      return 0.0F + getVisibleTabBarHeight();
    case 2: 
      return 0.0F + getActionBarHeight();
    }
    return 0.0F + (getVisibleTabBarHeight() + getActionBarHeight());
  }
  
  private float getScrollingFloatingHeaderHeight()
  {
    if (this.mHeaderLockMode == 1) {
      return 0.0F;
    }
    switch (this.mHeaderMode)
    {
    case 3: 
    default: 
      throw new IllegalStateException();
    case 0: 
      return getVisibleTabBarHeight() + getActionBarHeight();
    case 1: 
      return getActionBarHeight();
    }
    return getVisibleTabBarHeight();
  }
  
  private static int getTabBarHeight(Context paramContext, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException();
    case 0: 
    case 1: 
      return paramContext.getResources().getDimensionPixelSize(R.dimen.play_header_list_tab_strip_height);
    }
    return 0;
  }
  
  private float getVisibleTabBarHeight()
  {
    return getTabBarHeight(getContext(), this.mTabMode);
  }
  
  private boolean isListViewReady(ViewGroup paramViewGroup)
  {
    ListAdapter localListAdapter;
    int i;
    if (paramViewGroup != null)
    {
      if (!(paramViewGroup instanceof ListView)) {
        break label45;
      }
      localListAdapter = ((ListView)paramViewGroup).getAdapter();
      if (localListAdapter != null) {
        break label34;
      }
      i = 0;
    }
    while (i <= 1)
    {
      return false;
      label34:
      i = localListAdapter.getCount();
      continue;
      label45:
      if ((paramViewGroup instanceof RecyclerView))
      {
        RecyclerView.Adapter localAdapter1 = ((RecyclerView)paramViewGroup).getAdapter();
        if (localAdapter1 == null) {
          i = 0;
        } else {
          i = localAdapter1.getItemCount();
        }
      }
      else if ((paramViewGroup instanceof PlayScrollableContentView))
      {
        Adapter localAdapter = ((PlayScrollableContentView)paramViewGroup).getAdapter();
        if (localAdapter == null) {
          i = 0;
        } else {
          i = localAdapter.getCount();
        }
      }
      else
      {
        throw new IllegalStateException("Unexpected listview type: " + paramViewGroup);
      }
    }
    if (paramViewGroup.getChildCount() > 1) {
      return true;
    }
    if (paramViewGroup.getChildCount() == 1) {
      return paramViewGroup.getChildAt(0).getId() != this.mSpacerId;
    }
    return false;
  }
  
  private void layout()
  {
    int i = getBannerHeight();
    int j = getStatusBarHeight();
    float f1 = this.mBannerFraction * (i + j);
    float f2 = f1 - i;
    this.mBannerTextViewCompat.setTranslationY(f2);
    float f3 = Math.max(0.0F, f1 - j);
    this.mContentContainerCompat.setTranslationY(f3);
    float f4 = getContentPosition();
    float f5 = getScrollingFloatingHeaderHeight();
    float f6;
    PlayHeaderStatusBarUnderlay localPlayHeaderStatusBarUnderlay;
    int i1;
    label157:
    boolean bool4;
    boolean bool5;
    label183:
    int i2;
    label246:
    float f7;
    label294:
    float f11;
    float f12;
    if (this.mControlsAreFloating)
    {
      f6 = f3 + (-this.mHeaderHeight + getNonScrollingFloatingHeaderHeight() + f5 * this.mFloatingFraction + this.mHeaderBottomMargin);
      this.mControlsContainerCompat.setTranslationY(f6);
      this.mHeaderShadowCompat.setTranslationY(f6);
      if (this.mAllowImmersiveBackground)
      {
        localPlayHeaderStatusBarUnderlay = this.mStatusBarUnderlay;
        boolean bool3 = this.mControlsAreFloating;
        if (f4 - this.mHeaderBottomMargin >= f5) {
          break label480;
        }
        i1 = 1;
        if (SUPPORT_DRAW_STATUS_BAR)
        {
          bool4 = localPlayHeaderStatusBarUnderlay.mProtectingControls;
          if ((!bool3) && (i1 == 0)) {
            break label486;
          }
          bool5 = true;
          localPlayHeaderStatusBarUnderlay.mProtectingControls = bool5;
          i2 = Math.round(f1);
          if ((localPlayHeaderStatusBarUnderlay.mProtectingControls) && (localPlayHeaderStatusBarUnderlay.mStatusBarHeight > i2)) {
            i2 = localPlayHeaderStatusBarUnderlay.mStatusBarHeight;
          }
          if ((f1 < 1.0F) && (!bool3)) {
            break label492;
          }
          localPlayHeaderStatusBarUnderlay.setDrawHeight(i2);
          localPlayHeaderStatusBarUnderlay.fade(1, false);
        }
      }
      f7 = f3;
      if ((this.mHeaderLockMode != 1) && ((this.mHeaderMode == 0) || (this.mHeaderMode == 1)))
      {
        if (!this.mControlsAreFloating) {
          break label572;
        }
        f7 = f3 - f5 * (1.0F - this.mFloatingFraction);
      }
      this.mToolbarContainerCompat.setTranslationY(f7 + this.mToolbarContainerA11yOffset);
      if (USE_ANIMATIONS)
      {
        f11 = this.mHeroContainer.getMeasuredHeight();
        f12 = 0.5F * (this.mHeaderHeight - f11 - this.mHeaderBottomMargin);
      }
      switch (this.mHeroAnimationMode)
      {
      default: 
        label376:
        float f8 = f3;
        if (this.mContentProtectionEnabled)
        {
          if (this.mTrackedListView != null)
          {
            int n = tryGetContentTop(this.mTrackedListView);
            if (n != -1) {
              f8 = f3 + n;
            }
          }
          this.mAltPlayBackgroundCompat.setTranslationY(f8);
        }
        if (this.mAbsoluteY == -1) {
          this.mBackgroundContainer.setVisibility(4);
        }
        break;
      }
    }
    for (;;)
    {
      updateHeaderShadow();
      if (this.mLayoutListener != null) {
        this.mLayoutListener.onPlayHeaderListLayoutChanged();
      }
      return;
      f6 = f3 + (f4 - this.mHeaderHeight);
      break;
      label480:
      i1 = 0;
      break label157;
      label486:
      bool5 = false;
      break label183;
      label492:
      if (localPlayHeaderStatusBarUnderlay.mProtectingControls) {}
      for (int i3 = 1; localPlayHeaderStatusBarUnderlay.mFadeDirection != i3; i3 = 2)
      {
        if ((!localPlayHeaderStatusBarUnderlay.mProtectingControls) && (bool4)) {
          i2 = localPlayHeaderStatusBarUnderlay.mStatusBarHeight;
        }
        localPlayHeaderStatusBarUnderlay.setDrawHeight(i2);
        if (i2 != 0) {
          break label561;
        }
        localPlayHeaderStatusBarUnderlay.fade(2, false);
        break;
      }
      label561:
      localPlayHeaderStatusBarUnderlay.fade(i3, true);
      break label246;
      label572:
      f7 = Math.min(f3, f3 + (f4 - this.mHeaderBottomMargin) - getVisibleTabBarHeight() - getActionBarHeight());
      break label294;
      boolean bool2;
      if (f6 + f12 >= f7 + getActionBarHeight())
      {
        bool2 = true;
        label624:
        if (this.mHeroVisible == bool2) {
          break label376;
        }
        this.mHeroVisible = bool2;
        if (!this.mHeroVisible) {
          break label665;
        }
      }
      label665:
      for (float f14 = 1.0F;; f14 = 0.0F)
      {
        setHeroAnimationValue(f14, true);
        break;
        bool2 = false;
        break label624;
      }
      float f13 = Math.max(0.0F, (f11 + f6) / f11);
      if (f13 > 0.0F) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        this.mHeroVisible = bool1;
        setHeroAnimationValue(f13, false);
        break;
      }
      int k = this.mBackgroundContainer.getVisibility();
      int m = 0;
      if (k == 4)
      {
        this.mBackgroundContainer.setVisibility(0);
        m = 1;
      }
      float f9 = Math.max(-this.mBackgroundContainer.getMeasuredHeight() / this.mBackgroundParallaxRatio, f3 + -this.mAbsoluteY * this.mBackgroundParallaxRatio);
      this.mBackgroundContainerCompat.setTranslationY(f9);
      float f10 = f9 + this.mBackgroundContainer.getMeasuredHeight() - f3;
      if ((m != 0) && (f10 > this.mBackgroundFadeInOffsetThresholdPx))
      {
        this.mBackgroundContainerCompat.setAlpha(0.0F);
        this.mBackgroundContainerCompat.animateAlpha$2549578(1.0F);
      }
    }
  }
  
  private static Drawable makeNonFloatingBackground()
  {
    return new ColorDrawable(0);
  }
  
  private int relativeToAbsolute(int paramInt)
  {
    int i = this.mViewPager.getCurrentItem();
    if (paramInt == 0) {
      i--;
    }
    if (paramInt == 2) {
      i++;
    }
    return i;
  }
  
  private boolean setActiveListView(ViewGroup paramViewGroup)
  {
    if (this.mTrackedListView == paramViewGroup) {
      return this.mTrackedListView != null;
    }
    int i;
    label64:
    boolean bool;
    if (this.mTrackedListView != null)
    {
      i = 1;
      if (this.mTrackedListView != null)
      {
        if (!(this.mTrackedListView instanceof ListView)) {
          break label176;
        }
        ((ListView)this.mTrackedListView).setOnScrollListener(null);
        this.mTrackedListScrollListener.reset(true);
        this.mSuppressIdleOnScroll = true;
      }
      this.mTrackedListView = paramViewGroup;
      if (this.mTrackedListView == null) {
        break label263;
      }
      bool = this.mSuppressIdleOnScroll;
      if (!this.mSuppressIdleOnScroll) {
        this.mSuppressIdleOnScroll = this.mTrackedListView.isLayoutRequested();
      }
      if (!(this.mTrackedListView instanceof ListView)) {
        break label229;
      }
      ((ListView)this.mTrackedListView).setOnScrollListener(this.mTrackedListScrollListener);
    }
    for (;;)
    {
      this.mSuppressIdleOnScroll = bool;
      if (i != 0) {
        syncListViews(true);
      }
      if (this.mPullToRefreshProvider == null) {
        break label256;
      }
      if (this.mHasViewPager) {
        this.mViewPager.getCurrentItem();
      }
      this.mHasPullToRefresh = true;
      return true;
      i = 0;
      break;
      label176:
      if ((this.mTrackedListView instanceof RecyclerView))
      {
        ((RecyclerView)this.mTrackedListView).setOnScrollListener(null);
        this.mTrackedListRecyclerViewScrollListener.reset(true);
        break label64;
      }
      if (!(this.mTrackedListView instanceof PlayScrollableContentView)) {
        break label64;
      }
      this.mTrackedListContentViewScrollListener.reset(true);
      break label64;
      label229:
      if ((this.mTrackedListView instanceof RecyclerView)) {
        ((RecyclerView)this.mTrackedListView).setOnScrollListener(this.mTrackedListRecyclerViewScrollListener);
      }
    }
    label256:
    this.mHasPullToRefresh = false;
    return true;
    label263:
    return false;
  }
  
  private void setBannerText(CharSequence paramCharSequence, boolean paramBoolean)
  {
    this.mHandler.removeCallbacks(this.mTemporaryBannerTimeoutRunnable);
    this.mTemporaryBannerGoneRunnable = null;
    this.mBannerText = paramCharSequence;
    if (paramCharSequence == null)
    {
      setBannerVisible(false, true);
      return;
    }
    this.mBannerTextView.setText(paramCharSequence);
    setBannerVisible(true, true);
  }
  
  private void setBannerVisible(boolean paramBoolean1, boolean paramBoolean2)
  {
    float f = 1.0F;
    if (paramBoolean1 == this.mBannerVisible) {
      return;
    }
    this.mBannerVisible = paramBoolean1;
    int k;
    if (USE_ANIMATIONS)
    {
      if (paramBoolean2) {
        if (paramBoolean1)
        {
          getFloatAnimator("bannerFraction", getBannerFraction(), f).setDuration(200L).start();
          j = 0;
          if (!paramBoolean1) {
            break label124;
          }
          k = getBannerHeight();
        }
      }
      for (;;)
      {
        FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.mContentContainer.getLayoutParams();
        localLayoutParams.setMargins(0, j + getStatusBarHeight(), 0, k);
        this.mContentContainer.setLayoutParams(localLayoutParams);
        return;
        f = 0.0F;
        break;
        if (paramBoolean1) {}
        for (;;)
        {
          setBannerFraction(f);
          break;
          f = 0.0F;
        }
        label124:
        k = 0;
        j = 0;
      }
    }
    TextView localTextView = this.mBannerTextView;
    int i;
    if (paramBoolean1)
    {
      i = 0;
      label146:
      localTextView.setVisibility(i);
      if (!paramBoolean1) {
        break label176;
      }
    }
    label176:
    for (int j = getBannerHeight();; j = 0)
    {
      k = 0;
      break;
      i = 8;
      break label146;
    }
  }
  
  private static void setChildTopMargin(View paramView, int paramInt)
  {
    ((ViewGroup.MarginLayoutParams)paramView.getLayoutParams()).topMargin = paramInt;
    paramView.requestLayout();
  }
  
  private void setControlsBackground(Drawable paramDrawable, boolean paramBoolean)
  {
    if ((paramBoolean) && (USE_ANIMATIONS))
    {
      Drawable localDrawable = this.mControlsContainer.getBackground();
      if (localDrawable == null) {
        localDrawable = makeNonFloatingBackground();
      }
      if (paramDrawable == null) {
        paramDrawable = makeNonFloatingBackground();
      }
      if (localDrawable == paramDrawable) {
        return;
      }
      TransitionDrawable local6 = new TransitionDrawable(new Drawable[] { localDrawable, paramDrawable })
      {
        @TargetApi(21)
        public final void getOutline(Outline paramAnonymousOutline)
        {
          paramAnonymousOutline.setRect(getBounds());
          paramAnonymousOutline.setAlpha(1.0F);
        }
      };
      local6.setCrossFadeEnabled(true);
      local6.startTransition(300);
      this.mControlsContainer.setBackgroundDrawable(local6);
      return;
    }
    this.mControlsContainer.setBackgroundDrawable(paramDrawable);
  }
  
  private void setControlsContainerHeight(int paramInt)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.mControlsContainer.getLayoutParams();
    localLayoutParams.height = paramInt;
    this.mControlsContainer.setLayoutParams(localLayoutParams);
  }
  
  private void setControlsFloating(boolean paramBoolean1, boolean paramBoolean2)
  {
    float f1 = 1.0F;
    if (this.mControlsAreFloating == paramBoolean1) {
      return;
    }
    float f2;
    float f3;
    if (paramBoolean1)
    {
      f2 = getVisibleHeaderHeight() - getNonScrollingFloatingHeaderHeight();
      f3 = getScrollingFloatingHeaderHeight();
      if (f3 == 0.0F)
      {
        this.mFloatingFraction = f1;
        this.mControlsAreFloating = paramBoolean1;
        if (!this.mAlwaysUseFloatingBackground)
        {
          if (!this.mControlsAreFloating) {
            break label166;
          }
          setControlsBackground(this.mFloatingControlsBackground, true);
        }
        label72:
        if (this.mAutoHideToolbarTitle)
        {
          if (!this.mControlsAreFloating) {
            break label177;
          }
          label86:
          if (f1 != getActionBarTitleAlpha())
          {
            if (Build.VERSION.SDK_INT < 11) {
              break label182;
            }
            getFloatAnimator("actionBarTitleAlpha", getActionBarTitleAlpha(), f1).setDuration(200L).start();
          }
        }
      }
    }
    for (;;)
    {
      updateHeaderShadow();
      updateTabPadding(true);
      updateTabContrast();
      return;
      this.mFloatingFraction = Math.max(0.0F, Math.min(f1, f2 / f3));
      break;
      this.mFloatingFraction = 0.0F;
      break;
      label166:
      setControlsBackground(makeNonFloatingBackground(), true);
      break label72;
      label177:
      f1 = 0.0F;
      break label86;
      label182:
      ActionBarTitleAlphaAnimation localActionBarTitleAlphaAnimation = new ActionBarTitleAlphaAnimation(getActionBarTitleAlpha(), f1);
      localActionBarTitleAlphaAnimation.setDuration(200L);
      startAnimation(localActionBarTitleAlphaAnimation);
    }
  }
  
  private void setFloatingFraction(float paramFloat, boolean paramBoolean)
  {
    if ((this.mControlsAreFloating) && (this.mFloatingFraction != paramFloat))
    {
      this.mFloatingFraction = paramFloat;
      if (paramBoolean)
      {
        layout();
        syncListViews(false);
      }
    }
  }
  
  private void setHeaderShadowTopMargin(int paramInt)
  {
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.mHeaderShadow.getLayoutParams();
    localLayoutParams.setMargins(0, paramInt, 0, 0);
    this.mHeaderShadow.setLayoutParams(localLayoutParams);
  }
  
  @TargetApi(21)
  private void setHeaderShadowVisible(boolean paramBoolean)
  {
    int j;
    float f2;
    if (this.mHeaderShadowVisible != paramBoolean)
    {
      this.mHeaderShadowVisible = paramBoolean;
      if (!SUPPORT_ELEVATION) {
        break label175;
      }
      float f1 = getFloatingFraction();
      if ((!paramBoolean) || (f1 <= 0.25D)) {
        break label157;
      }
      j = 1;
      if (!paramBoolean) {
        break label163;
      }
      f2 = getFloatingHeaderElevation();
      label52:
      if (!paramBoolean) {
        break label169;
      }
    }
    label157:
    label163:
    label169:
    for (int k = 150;; k = 0)
    {
      int m = 0;
      if (j != 0) {
        m = 100;
      }
      this.mControlsContainerCompat.animateZ(f2, k, m);
      this.mToolbarContainerCompat.animateZ(f2, k, m);
      this.mBannerTextView.animate().z(f2).setStartDelay(m).setDuration(k);
      if (this.mAllowImmersiveBackground) {
        this.mStatusBarUnderlay.animate().z(f2).setStartDelay(m).setDuration(k);
      }
      return;
      j = 0;
      break;
      f2 = 0.0F;
      break label52;
    }
    label175:
    View localView = this.mHeaderShadow;
    int i = 0;
    if (paramBoolean) {}
    for (;;)
    {
      localView.setVisibility(i);
      return;
      i = 4;
    }
  }
  
  private void setHeroAnimationValue(float paramFloat, boolean paramBoolean)
  {
    if (paramBoolean) {
      switch (this.mHeroAnimationMode)
      {
      }
    }
    AnimationCompat localAnimationCompat1;
    do
    {
      return;
      AnimationCompat localAnimationCompat2 = this.mHeroContainerCompat;
      if (localAnimationCompat2.mView == null)
      {
        localAnimationCompat2.mScale = paramFloat;
        return;
      }
      if (Build.VERSION.SDK_INT >= 12)
      {
        localAnimationCompat2.mView.animate().scaleX(paramFloat).scaleY(paramFloat).setDuration(100L);
        return;
      }
      ScaleAnimation localScaleAnimation2 = new ScaleAnimation(localAnimationCompat2.mScale, paramFloat, localAnimationCompat2.mScale, paramFloat);
      localScaleAnimation2.setDuration(100L);
      localScaleAnimation2.setFillAfter(true);
      if (Build.VERSION.SDK_INT < 12) {
        localScaleAnimation2.setAnimationListener(new AnimationCompat.2(localAnimationCompat2, paramFloat));
      }
      localAnimationCompat2.mView.startAnimation(localScaleAnimation2);
      return;
      this.mHeroContainerCompat.animateAlpha$2549578(paramFloat);
      return;
      switch (this.mHeroAnimationMode)
      {
      default: 
        return;
      case 0: 
        localAnimationCompat1 = this.mHeroContainerCompat;
        if (localAnimationCompat1.mView == null)
        {
          localAnimationCompat1.mScale = paramFloat;
          return;
        }
        if (Build.VERSION.SDK_INT >= 11)
        {
          localAnimationCompat1.mView.setScaleX(paramFloat);
          localAnimationCompat1.mView.setScaleY(paramFloat);
          return;
        }
        break;
      }
    } while (localAnimationCompat1.mScale == paramFloat);
    localAnimationCompat1.mScale = paramFloat;
    ScaleAnimation localScaleAnimation1 = new ScaleAnimation(localAnimationCompat1.mScale, localAnimationCompat1.mScale, localAnimationCompat1.mScale, localAnimationCompat1.mScale);
    localScaleAnimation1.setDuration(0L);
    localScaleAnimation1.setFillAfter(true);
    localAnimationCompat1.mView.startAnimation(localScaleAnimation1);
    return;
    this.mHeroContainerCompat.setAlpha(paramFloat);
  }
  
  private void setPullToRefreshEnabled(boolean paramBoolean)
  {
    boolean bool1 = true;
    if (this.mScrollProxyView.getScrollY() == 0) {}
    for (boolean bool2 = bool1;; bool2 = false)
    {
      if (paramBoolean != bool2)
      {
        ScrollProxyView localScrollProxyView = this.mScrollProxyView;
        if (paramBoolean) {
          bool1 = false;
        }
        localScrollProxyView.scrollTo(0, bool1);
      }
      return;
    }
  }
  
  private void setSupportActionBar(Toolbar paramToolbar)
  {
    ((ActionBarActivity)getContext()).setSupportActionBar(paramToolbar);
  }
  
  private void setupBackground()
  {
    if (this.mContentProtectionEnabled)
    {
      this.mAltPlayBackground.setBackgroundDrawable(this.mContentProtectionBackground);
      this.mAltPlayBackground.setVisibility(0);
      return;
    }
    this.mAltPlayBackground.setVisibility(8);
  }
  
  private void setupViewPagerIfNeeded()
  {
    if ((this.mHasViewPager) && (this.mViewPager == null))
    {
      ViewPager localViewPager = (ViewPager)this.mContentContainer.findViewById(this.mViewPagerId);
      if (localViewPager != null) {
        setViewPager(localViewPager);
      }
    }
  }
  
  private void syncListViews(boolean paramBoolean)
  {
    int i = 2;
    if (this.mViewPager == null) {
      return;
    }
    boolean bool = false;
    if (paramBoolean)
    {
      bool = syncViewPagerListView(1);
      if (!bool) {
        this.mSuppressIdleOnScroll = false;
      }
    }
    if ((bool | syncViewPagerListView(0) | syncViewPagerListView(i)))
    {
      if (bool) {}
      for (;;)
      {
        this.mPendingListSync = i;
        return;
        i = 1;
      }
    }
    this.mPendingListSync = 0;
  }
  
  private boolean syncViewPagerListView(int paramInt)
  {
    if ((this.mViewPager == null) || (this.mViewPager.getAdapter() == null)) {}
    int j;
    do
    {
      ViewGroup localViewGroup;
      int m;
      do
      {
        int k;
        do
        {
          int i;
          do
          {
            return false;
            i = relativeToAbsolute(paramInt);
          } while ((i < 0) || (i >= this.mViewPager.getAdapter().getCount()));
          localViewGroup = getListView(paramInt);
          if (paramInt == 1) {}
          for (j = 1; !isListViewReady(localViewGroup); j = 0) {
            return true;
          }
          k = tryGetContentTop(localViewGroup);
          if (k != -1) {
            break;
          }
        } while (this.mControlsAreFloating);
        this.mSuppressIdleOnScroll = true;
        if ((localViewGroup instanceof ListView)) {
          ((ListView)localViewGroup).setSelectionFromTop(0, 0);
        }
        for (;;)
        {
          this.mSuppressIdleOnScroll = false;
          return true;
          if ((localViewGroup instanceof RecyclerView)) {
            ((RecyclerView)localViewGroup).scrollToPosition(0);
          }
        }
        m = k - getDesiredContentTop();
      } while ((this.mControlsAreFloating) && (m < 0));
      if ((Math.abs(m) > 0) && (ViewCompat.canScrollVertically(localViewGroup, m)))
      {
        this.mSuppressIdleOnScroll = true;
        ListView localListView;
        if ((localViewGroup instanceof ListView))
        {
          localListView = (ListView)localViewGroup;
          if (Build.VERSION.SDK_INT >= 19) {
            localListView.scrollListBy(m);
          }
        }
        for (;;)
        {
          this.mSuppressIdleOnScroll = false;
          if (j == 0) {
            break;
          }
          this.mUpdateContentPositionOnLayout = true;
          return false;
          localListView.smoothScrollBy(m, 0);
          continue;
          if ((localViewGroup instanceof RecyclerView)) {
            ((RecyclerView)localViewGroup).scrollBy(0, m);
          }
        }
      }
    } while (j == 0);
    updateContentPosition(true);
    return false;
  }
  
  private View tryFindHeaderSpacerView(ViewGroup paramViewGroup)
  {
    for (int i = 0; i < paramViewGroup.getChildCount(); i++)
    {
      View localView = paramViewGroup.getChildAt(i);
      if (localView.getId() == this.mSpacerId) {
        return localView;
      }
    }
    return null;
  }
  
  private void updateActiveListView()
  {
    setActiveListView(getListView(1));
  }
  
  private void updateContentPosition(boolean paramBoolean)
  {
    if (!isListViewReady(this.mTrackedListView)) {
      return;
    }
    this.mAbsoluteY = tryGetCollectionViewAbsoluteY(this.mTrackedListView);
    if (this.mPullToRefreshProvider != null) {
      if (this.mAbsoluteY != 0) {
        break label59;
      }
    }
    label59:
    for (boolean bool = true;; bool = false)
    {
      setPullToRefreshEnabled(bool);
      updateFloatingState();
      if (!paramBoolean) {
        break;
      }
      layout();
      return;
    }
  }
  
  private boolean updateFloatingState()
  {
    boolean bool2;
    if (this.mControlsAreFloating)
    {
      float f2 = this.mHeaderHeight - getFullFloatingHeaderHeight() - this.mHeaderBottomMargin;
      if ((this.mAbsoluteY == -1) || (Math.round(this.mAbsoluteY) > Math.round(f2))) {}
      for (boolean bool3 = true;; bool3 = false)
      {
        bool2 = bool3;
        if (bool2 == this.mControlsAreFloating) {
          break;
        }
        setControlsFloating(bool2, true);
        return true;
      }
    }
    float f1 = this.mHeaderHeight - this.mHeaderBottomMargin - getNonScrollingFloatingHeaderHeight();
    if ((Math.round(this.mAbsoluteY) >= Math.round(f1)) || (this.mAbsoluteY == -1)) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      bool2 = bool1;
      break;
    }
    return false;
  }
  
  private void updateHeaderShadow()
  {
    boolean bool = true;
    int i;
    switch (this.mHeaderShadowMode)
    {
    default: 
      if (((this.mControlsAreFloating) || (this.mAlwaysUseFloatingBackground)) && (getVisibleHeaderHeight() > 0.0F)) {
        if (this.mBackgroundContainer.getVisibility() != 0)
        {
          i = 0;
          if (i != 0) {
            break label188;
          }
        }
      }
      break;
    }
    for (;;)
    {
      setHeaderShadowVisible(bool);
      return;
      bool = false;
      continue;
      if (getVisibleHeaderHeight() <= 0.0F)
      {
        for (;;)
        {
          bool = false;
        }
        if ((!this.mControlsAreFloating) || (getVisibleHeaderHeight() <= 0.0F))
        {
          for (;;)
          {
            bool = false;
          }
          if ((getMeasuredHeight() != 0) && ((this.mControlsAreFloating) || (this.mAlwaysUseFloatingBackground)) && (Math.max(0.0F, Math.max(0.0F, this.mBackgroundContainer.getMeasuredHeight() + this.mBackgroundContainerCompat.getTranslationY()) - getVisibleHeaderHeight()) <= 0.0F))
          {
            i = 0;
            break;
          }
          i = bool;
          break;
          label188:
          bool = false;
        }
      }
    }
  }
  
  private void updateHeaderShadowTopMargin()
  {
    setHeaderShadowTopMargin(-1 + (this.mHeaderHeight - this.mHeaderBottomMargin));
  }
  
  private void updateLayoutForStatusBarHeight(boolean paramBoolean)
  {
    if (this.mAllowImmersiveBackground)
    {
      ((FrameLayout.LayoutParams)this.mStatusBarUnderlay.getLayoutParams()).height = (this.mStatusBarHeight + this.mBannerTextView.getLayoutParams().height);
      PlayHeaderStatusBarUnderlay localPlayHeaderStatusBarUnderlay = this.mStatusBarUnderlay;
      int i = this.mStatusBarHeight;
      if ((SUPPORT_DRAW_STATUS_BAR) && (localPlayHeaderStatusBarUnderlay.mStatusBarHeight != i))
      {
        localPlayHeaderStatusBarUnderlay.mStatusBarHeight = i;
        localPlayHeaderStatusBarUnderlay.invalidate();
      }
      this.mStatusBarUnderlay.requestLayout();
      setChildTopMargin(this.mAltPlayBackground, this.mStatusBarHeight);
      setChildTopMargin(this.mContentContainer, this.mStatusBarHeight);
      setChildTopMargin(this.mControlsContainer, -1 + this.mStatusBarHeight);
      if (this.mUseBuiltInToolbar) {
        setChildTopMargin(this.mToolbarContainer, this.mStatusBarHeight - this.mToolbarContainerA11yOffset);
      }
      if (paramBoolean) {
        layout();
      }
    }
  }
  
  private void updateTabBarVisibility()
  {
    switch (this.mTabMode)
    {
    default: 
      throw new IllegalStateException("Unexpected tab mode: " + this.mTabMode);
    case 0: 
      this.mTabBar.setVisibility(0);
      this.mTabBarTitleView.setVisibility(4);
      this.mTabStrip.setVisibility(0);
      return;
    case 1: 
      this.mTabBar.setVisibility(0);
      this.mTabBarTitleView.setVisibility(0);
      this.mTabStrip.setVisibility(4);
      return;
    }
    this.mTabBar.setVisibility(4);
    this.mTabBarTitleView.setVisibility(0);
    this.mTabStrip.setVisibility(0);
  }
  
  private void updateTabContrast()
  {
    boolean bool;
    int i;
    label43:
    PlayHeaderListTabStrip localPlayHeaderListTabStrip;
    int k;
    label73:
    TextView localTextView;
    switch (this.mTabA11yMode)
    {
    default: 
      bool = false;
      if (this.mTabBgMode == 0)
      {
        i = 1;
        localPlayHeaderListTabStrip = this.mTabStrip;
        if (localPlayHeaderListTabStrip.mUseHighContrast == bool) {
          return;
        }
        localPlayHeaderListTabStrip.mUseHighContrast = bool;
        int j = localPlayHeaderListTabStrip.mTabContainer.getChildCount();
        k = 0;
        if (k >= j) {
          return;
        }
        localTextView = (TextView)localPlayHeaderListTabStrip.mTabContainer.getChildAt(k);
        localPlayHeaderListTabStrip.applyTabContrastMode(localTextView, localPlayHeaderListTabStrip.mUseHighContrast);
        if (i != 0) {
          if (!localPlayHeaderListTabStrip.mUseHighContrast) {
            break label192;
          }
        }
      }
      break;
    }
    label192:
    for (int m = localPlayHeaderListTabStrip.mTabBackgroundResId;; m = 0)
    {
      localTextView.setBackgroundResource(m);
      k++;
      break label73;
      bool = true;
      break;
      if ((this.mControlsAreFloating) || (this.mAlwaysUseFloatingBackground)) {}
      for (bool = true;; bool = false) {
        break;
      }
      if ((!this.mControlsAreFloating) && (!this.mAlwaysUseFloatingBackground)) {}
      for (bool = true;; bool = false) {
        break;
      }
      i = 0;
      break label43;
    }
  }
  
  private void updateTabPadding(boolean paramBoolean)
  {
    boolean bool;
    if (this.mTabMode == 0) {
      switch (this.mTabPaddingMode)
      {
      default: 
        bool = this.mControlsAreFloating;
      }
    }
    for (;;)
    {
      this.mTabStrip.setUseFloatingTabPadding(bool, paramBoolean);
      return;
      bool = true;
      continue;
      bool = false;
    }
  }
  
  private static ViewGroup validateListView(View paramView)
  {
    if ((paramView == null) || ((paramView instanceof ListView)) || ((paramView instanceof RecyclerView)) || ((paramView instanceof PlayScrollableContentView))) {
      return (ViewGroup)paramView;
    }
    throw new IllegalStateException("Found a view that isn't a ListView or a RecyclerView or a PlayScrollableContentView implementation");
  }
  
  public void applyActionBarTitleAlpha(Toolbar paramToolbar, float paramFloat)
  {
    int i = 0xFFFFFF | Math.max(0, Math.min(255, Math.round(255.0F * paramFloat))) << 24;
    paramToolbar.setTitleTextColor(i);
    paramToolbar.setSubtitleTextColor(i);
  }
  
  @SuppressLint({"newapi"})
  public void configure(Configurator paramConfigurator)
  {
    this.mBackgroundParallaxRatio = paramConfigurator.getBackgroundViewParallaxRatio();
    this.mListViewId = paramConfigurator.getListViewId();
    this.mViewPagerId = paramConfigurator.getViewPagerId();
    this.mSpacerId = R.id.play_header_spacer;
    int i;
    boolean bool1;
    label78:
    boolean bool2;
    label101:
    boolean bool3;
    label147:
    int j;
    label165:
    LayoutInflater localLayoutInflater;
    int k;
    if (this.mSpacerId == 0)
    {
      i = R.id.play_header_spacer;
      this.mSpacerId = i;
      this.mUseBuiltInToolbar = false;
      this.mHasViewPager = paramConfigurator.hasViewPager();
      this.mTabMode = paramConfigurator.getTabMode();
      if (paramConfigurator.getContentProtectionMode() != 1) {
        break label1073;
      }
      bool1 = true;
      this.mContentProtectionEnabled = bool1;
      this.mHeroAnimationMode = paramConfigurator.getHeroAnimationMode();
      if (paramConfigurator.getToolbarTitleMode() != 0) {
        break label1078;
      }
      bool2 = true;
      this.mAutoHideToolbarTitle = bool2;
      this.mHeaderMode = paramConfigurator.getHeaderMode();
      this.mHeaderShadowMode = paramConfigurator.getHeaderShadowMode();
      this.mAlwaysUseFloatingBackground = paramConfigurator.alwaysUseFloatingBackground();
      if ((!SUPPORT_DRAW_STATUS_BAR) || (!paramConfigurator.allowImmersiveBackground())) {
        break label1084;
      }
      bool3 = true;
      this.mAllowImmersiveBackground = bool3;
      if (!USE_ANIMATIONS) {
        break label1090;
      }
      j = paramConfigurator.getTabPaddingMode();
      this.mTabPaddingMode = j;
      this.mTabA11yMode = 0;
      this.mTabBgMode = 0;
      localLayoutInflater = LayoutInflater.from(getContext());
      PlayHeaderListTabStrip localPlayHeaderListTabStrip1 = paramConfigurator.getCustomTabStrip(getContext(), localLayoutInflater);
      if (!USE_ANIMATIONS) {
        break label1096;
      }
      k = R.layout.play_header_list_layout;
      label213:
      localLayoutInflater.inflate(k, this);
      this.mBackgroundContainer = ((FrameLayout)findViewById(R.id.background_container));
      this.mBackgroundContainerCompat = new AnimationCompat(this.mBackgroundContainer);
      this.mAltPlayBackground = findViewById(R.id.alt_play_background);
      this.mAltPlayBackgroundCompat = new AnimationCompat(this.mAltPlayBackground);
      this.mContentContainer = findViewById(R.id.content_container);
      this.mContentContainerCompat = new AnimationCompat(this.mContentContainer);
      this.mContentProtectionBackground = new ColorDrawable(paramConfigurator.mContext.getResources().getColor(R.color.play_main_background));
      this.mControlsContainer = ((ViewGroup)findViewById(R.id.controls_container));
      this.mControlsContainerCompat = new AnimationCompat(this.mControlsContainer);
      this.mHeaderShadow = findViewById(R.id.header_shadow);
      this.mHeaderShadowCompat = new AnimationCompat(this.mHeaderShadow);
      this.mHeroContainer = ((FrameLayout)findViewById(R.id.hero_container));
      this.mHeroContainerCompat = new AnimationCompat(this.mHeroContainer);
      this.mTabBar = findViewById(R.id.tab_bar);
      this.mTabStrip = ((PlayHeaderListTabStrip)findViewById(R.id.pager_tab_strip));
      if (localPlayHeaderListTabStrip1 != null)
      {
        PlayHeaderListTabStrip localPlayHeaderListTabStrip2 = this.mTabStrip;
        ViewGroup.LayoutParams localLayoutParams = localPlayHeaderListTabStrip2.getLayoutParams();
        ViewGroup localViewGroup3 = (ViewGroup)localPlayHeaderListTabStrip2.getParent();
        int n = localViewGroup3.indexOfChild(localPlayHeaderListTabStrip2);
        localViewGroup3.removeViewAt(n);
        localViewGroup3.addView(localPlayHeaderListTabStrip1, n, localLayoutParams);
        View localView2 = localPlayHeaderListTabStrip2.getChildAt(0);
        localPlayHeaderListTabStrip2.removeViewAt(0);
        localPlayHeaderListTabStrip1.addView(localView2);
        localPlayHeaderListTabStrip1.getSubViewReferences();
        this.mTabStrip = localPlayHeaderListTabStrip1;
      }
      this.mTabStrip.setExternalOnPageChangeListener(this.mOnPageChangeListener);
      this.mTabStrip.setTabsBackgroundResource(R.drawable.play_header_list_tab_high_contrast_bg);
      this.mTabBarTitleView = ((TextView)findViewById(R.id.tab_bar_title));
      this.mHeaderHeight = paramConfigurator.getHeaderHeight();
      this.mHeaderBottomMargin = 0;
      setControlsContainerHeight(this.mHeaderHeight - this.mHeaderBottomMargin);
      if (!SUPPORT_ELEVATION) {
        updateHeaderShadowTopMargin();
      }
      if (!this.mUseBuiltInToolbar) {
        break label1104;
      }
      ViewGroup localViewGroup2 = getToolbarContainer();
      this.mToolbar = ((Toolbar)localLayoutInflater.inflate(R.layout.play_header_list_toolbar, localViewGroup2, false));
      getToolbarContainer().addView(this.mToolbar);
      setSupportActionBar(this.mToolbar);
      label644:
      this.mToolbarHeight = paramConfigurator.getToolBarHeight(getContext());
      this.mToolbarContainer = getToolbarContainer();
      this.mToolbarContainerCompat = new AnimationCompat(this.mToolbarContainer);
      this.mBannerTextView = ((TextView)findViewById(R.id.play_header_banner));
      this.mBannerTextViewCompat = new AnimationCompat(this.mBannerTextView);
      if (this.mAllowImmersiveBackground)
      {
        this.mStatusBarUnderlay = ((PlayHeaderStatusBarUnderlay)findViewById(R.id.play_header_status_bar_underlay));
        this.mStatusBarUnderlay.setVisibility(0);
        this.mStatusBarUnderlay.setOutlineProvider(null);
        this.mStatusBarUnderlay.initColors(paramConfigurator.getStatusBarOverlayColor(), paramConfigurator.getStatusBarUnderlayColor());
        this.mBannerTextView.setBackground(null);
      }
      this.mSwipeRefreshLayout = ((SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout));
      this.mSwipeRefreshLayoutCompat = new AnimationCompat(this.mSwipeRefreshLayout);
      this.mSwipeRefreshLayout.setOnRefreshListener(this);
      this.mScrollProxyView = ((ScrollProxyView)findViewById(R.id.scroll_proxy));
      setPullToRefreshEnabled(false);
      if (!USE_ANIMATIONS) {
        break label1130;
      }
      paramConfigurator.addHeroView$39fc0c(this.mHeroContainer);
      paramConfigurator.addBackgroundView(localLayoutInflater, this.mBackgroundContainer);
      label848:
      ViewGroup localViewGroup1 = (ViewGroup)this.mContentContainer;
      paramConfigurator.addContentView(localLayoutInflater, localViewGroup1);
      if (localViewGroup1.getChildCount() == 1)
      {
        View localView1 = localViewGroup1.getChildAt(0);
        int m = indexOfChild(this.mContentContainer);
        removeViewAt(m);
        localViewGroup1.removeViewAt(0);
        addView(localView1, m);
        this.mContentContainer = localView1;
        this.mContentContainerCompat = new AnimationCompat(this.mContentContainer);
      }
      if ((USE_ANIMATIONS) && (this.mUseBuiltInToolbar))
      {
        this.mToolbarContainerA11yOffset = (2 * (int)getResources().getDisplayMetrics().density);
        this.mToolbarContainerCompat.setTranslationY(this.mToolbarContainerA11yOffset);
      }
      updateTabBarVisibility();
      setupBackground();
      if (Build.VERSION.SDK_INT >= 14) {
        this.mControlsContainer.setOnHoverListener(new View.OnHoverListener()
        {
          public final boolean onHover(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
          {
            return PlayHeaderListLayout.this.isHeaderFloating();
          }
        });
      }
      if (this.mAlwaysUseFloatingBackground) {
        setControlsBackground(this.mFloatingControlsBackground, false);
      }
      this.mBannerTextViewCompat.setTranslationY(-getBannerHeight());
      if (!this.mAllowImmersiveBackground) {
        break label1150;
      }
      updateLayoutForStatusBarHeight(false);
    }
    for (;;)
    {
      setBannerFraction(this.mBannerFraction);
      updateTabPadding(false);
      updateTabContrast();
      return;
      i = this.mSpacerId;
      break;
      label1073:
      bool1 = false;
      break label78;
      label1078:
      bool2 = false;
      break label101;
      label1084:
      bool3 = false;
      break label147;
      label1090:
      j = 1;
      break label165;
      label1096:
      k = R.layout.play_header_list_layout_gb;
      break label213;
      label1104:
      this.mToolbar = ((Toolbar)((Activity)getContext()).getWindow().findViewById(R.id.action_bar));
      break label644;
      label1130:
      if (this.mAlwaysUseFloatingBackground) {
        break label848;
      }
      paramConfigurator.addBackgroundView(localLayoutInflater, this.mBackgroundContainer);
      break label848;
      label1150:
      if (USE_ANIMATIONS) {
        setChildTopMargin(this.mToolbarContainer, -this.mToolbarContainerA11yOffset);
      }
    }
  }
  
  public final void detachIfNeeded()
  {
    if (!this.mAttached) {
      return;
    }
    this.mAttached = false;
    Integer localInteger1 = (Integer)sActionbarAttachedCount.get(this.mToolbar);
    int i = 0;
    Integer localInteger2;
    if (localInteger1 == null)
    {
      localInteger2 = Integer.valueOf(i);
      if (localInteger2.intValue() != 0) {
        break label112;
      }
      sActionbarAttachedCount.remove(this.mToolbar);
    }
    for (;;)
    {
      setActiveListView(null);
      this.mHandler.removeCallbacksAndMessages(null);
      if ((!this.mUseBuiltInToolbar) && (localInteger2.intValue() == 0)) {
        this.mToolbarContainerCompat.setTranslationY(0.0F);
      }
      this.mTemporaryBannerGoneRunnable = null;
      return;
      i = -1 + localInteger1.intValue();
      break;
      label112:
      sActionbarAttachedCount.put(this.mToolbar, localInteger2);
    }
  }
  
  public int getActionBarHeight()
  {
    return this.mToolbarHeight;
  }
  
  protected final float getActionBarTitleAlpha()
  {
    return this.mActionBarTitleAlpha;
  }
  
  public float getActionBarTranslationY()
  {
    return this.mToolbarContainerCompat.getTranslationY();
  }
  
  protected final float getBannerFraction()
  {
    return this.mBannerFraction;
  }
  
  public int getBannerHeight()
  {
    return getResources().getDimensionPixelSize(R.dimen.play_header_list_banner_height);
  }
  
  public CharSequence getBannerText()
  {
    return this.mBannerText;
  }
  
  public ViewGroup getCurrentListView()
  {
    return getListView(1);
  }
  
  protected final float getFloatingFraction()
  {
    if (this.mControlsAreFloating) {
      return this.mFloatingFraction;
    }
    return 0.0F;
  }
  
  public int getHeaderHeight()
  {
    return this.mHeaderHeight;
  }
  
  public int getHeaderLockMode()
  {
    return this.mHeaderLockMode;
  }
  
  public boolean getHeroElementVisible()
  {
    return this.mHeroVisible;
  }
  
  public boolean getLastSnapControlsWasDown()
  {
    return this.mLastSnapControlsWasDown;
  }
  
  public int getStatusBarHeight()
  {
    if (this.mAllowImmersiveBackground) {
      return this.mStatusBarHeight;
    }
    return 0;
  }
  
  public SwipeRefreshLayout getSwipeRefreshLayout()
  {
    return this.mSwipeRefreshLayout;
  }
  
  public int getTabBarHeight()
  {
    return getTabBarHeight(getContext(), this.mTabMode);
  }
  
  public int getTabMode()
  {
    return this.mTabMode;
  }
  
  public Toolbar getToolbar()
  {
    return this.mToolbar;
  }
  
  public ViewGroup getToolbarContainer()
  {
    ViewGroup localViewGroup1;
    if (this.mToolbarContainer != null) {
      localViewGroup1 = this.mToolbarContainer;
    }
    do
    {
      return localViewGroup1;
      if (this.mUseBuiltInToolbar)
      {
        ViewGroup localViewGroup2 = (ViewGroup)findViewById(R.id.toolbar_container);
        localViewGroup2.setVisibility(0);
        return localViewGroup2;
      }
      localViewGroup1 = (ViewGroup)((Activity)getContext()).getWindow().findViewById(R.id.action_bar_container);
    } while (USE_ANIMATIONS);
    findViewById(R.id.toolbar_container).setMinimumHeight(getActionBarHeight());
    return localViewGroup1;
  }
  
  public float getVisibleHeaderHeight()
  {
    if (this.mControlsAreFloating) {
      return getNonScrollingFloatingHeaderHeight() + getScrollingFloatingHeaderHeight() * this.mFloatingFraction;
    }
    return getContentPosition() - this.mHeaderBottomMargin;
  }
  
  public final boolean isHeaderFloating()
  {
    return (this.mControlsAreFloating) || (this.mAlwaysUseFloatingBackground);
  }
  
  public final boolean isStatusBarUnderlayProtectingControls()
  {
    return (this.mAllowImmersiveBackground) && (this.mStatusBarUnderlay.mProtectingControls);
  }
  
  @TargetApi(21)
  public WindowInsets onApplyWindowInsets(WindowInsets paramWindowInsets)
  {
    int i = paramWindowInsets.getSystemWindowInsetTop();
    int j = this.mStatusBarHeight;
    this.mStatusBarHeight = Math.max(i, j);
    if (this.mAllowImmersiveBackground)
    {
      if (this.mStatusBarHeight != j) {
        updateLayoutForStatusBarHeight(true);
      }
      return paramWindowInsets.replaceSystemWindowInsets(paramWindowInsets.getSystemWindowInsetLeft(), 0, paramWindowInsets.getSystemWindowInsetRight(), paramWindowInsets.getSystemWindowInsetBottom());
    }
    return super.onApplyWindowInsets(paramWindowInsets);
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Integer localInteger1;
    int i;
    if (!this.mAttached)
    {
      this.mAttached = true;
      localInteger1 = (Integer)sActionbarAttachedCount.get(this.mToolbar);
      if (localInteger1 != null) {
        break label82;
      }
      i = 1;
      Integer localInteger2 = Integer.valueOf(i);
      sActionbarAttachedCount.put(this.mToolbar, localInteger2);
      setupViewPagerIfNeeded();
      updateHeaderShadow();
      if (!this.mAutoHideToolbarTitle) {
        break label92;
      }
      setActionBarTitleAlpha(0.0F);
    }
    for (;;)
    {
      updateActiveListView();
      return;
      label82:
      i = 1 + localInteger1.intValue();
      break;
      label92:
      setActionBarTitleAlpha(1.0F);
    }
  }
  
  public void onDetachedFromWindow()
  {
    detachIfNeeded();
    super.onDetachedFromWindow();
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mHasPullToRefresh)
    {
      MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
      boolean bool = this.mSwipeRefreshLayout.onInterceptTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      AnimationCompat localAnimationCompat;
      if (bool)
      {
        localAnimationCompat = this.mSwipeRefreshLayoutCompat;
        if ((localAnimationCompat.mView == null) || (Build.VERSION.SDK_INT < 11)) {
          break label78;
        }
      }
      label78:
      for (float f = localAnimationCompat.mView.getAlpha();; f = localAnimationCompat.mAlpha)
      {
        if (f < 1.0F) {
          this.mSwipeRefreshLayoutCompat.setAlpha(1.0F);
        }
        return bool;
      }
    }
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    setupViewPagerIfNeeded();
    updateActiveListView();
    int i;
    if (this.mPendingSavedState != null)
    {
      i = 1;
      if ((this.mPendingSavedState != null) && (this.mTrackedListView != null))
      {
        updateContentPosition(false);
        setFloatingFraction(this.mPendingSavedState.mFloatingFraction, false);
        layout();
        syncListViews(false);
        this.mPendingSavedState = null;
        this.mSuppressIdleOnScroll = false;
      }
      if (i == 0)
      {
        if (paramBoolean) {
          this.mPendingListSync = 2;
        }
        if (this.mUpdateContentPositionOnLayout)
        {
          updateContentPosition(true);
          this.mUpdateContentPositionOnLayout = false;
        }
      }
      switch (this.mPendingListSync)
      {
      }
    }
    for (;;)
    {
      if (paramBoolean) {
        updateHeaderShadow();
      }
      return;
      i = 0;
      break;
      syncListViews(false);
      continue;
      syncListViews(true);
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable instanceof SavedState))
    {
      SavedState localSavedState = (SavedState)paramParcelable;
      super.onRestoreInstanceState(localSavedState.getSuperState());
      this.mPendingSavedState = localSavedState;
      return;
    }
    super.onRestoreInstanceState(paramParcelable);
  }
  
  public Parcelable onSaveInstanceState()
  {
    return new SavedState(super.onSaveInstanceState(), this);
  }
  
  final void onScroll(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mPullToRefreshProvider != null) {
      if (paramInt3 != 0) {
        break label32;
      }
    }
    label32:
    for (boolean bool2 = true;; bool2 = false)
    {
      setPullToRefreshEnabled(bool2);
      if ((!this.mSuppressIdleOnScroll) || (paramInt1 != 0)) {
        break;
      }
      return;
    }
    this.mAbsoluteY = paramInt3;
    float f;
    switch (paramInt1)
    {
    default: 
      if ((!updateFloatingState()) && (this.mControlsAreFloating))
      {
        if ((getScrollingFloatingHeaderHeight() != 0.0F) && (this.mHeaderLockMode == 0)) {
          break label183;
        }
        if ((this.mHeaderLockMode != 0) && (this.mHeaderLockMode != 1)) {
          break label177;
        }
        f = 1.0F;
      }
      break;
    }
    label116:
    for (this.mFloatingFraction = f;; this.mFloatingFraction = Math.min(1.0F, Math.max(0.0F, this.mFloatingFraction)))
    {
      layout();
      if (paramInt2 != 0) {
        break;
      }
      this.mPendingListSync = 1;
      return;
      boolean bool1 = true;
      PlayHeaderListLayout localPlayHeaderListLayout = this;
      for (;;)
      {
        localPlayHeaderListLayout.mLastScrollWasDown = bool1;
        break;
        if (paramInt2 <= 0.0F)
        {
          bool1 = true;
          localPlayHeaderListLayout = this;
        }
        else
        {
          localPlayHeaderListLayout = this;
          bool1 = false;
        }
      }
      f = 0.0F;
      break label116;
      this.mFloatingFraction -= paramInt2 / getScrollingFloatingHeaderHeight();
    }
  }
  
  final void onScrollStateChanged(int paramInt)
  {
    this.mHandler.removeCallbacks(this.mSnapControlsUpIfNeededRunnable);
    this.mHandler.removeCallbacks(this.mSnapControlsDownIfNeededRunnable);
    boolean bool1;
    boolean bool2;
    label78:
    Handler localHandler;
    if (paramInt == 0)
    {
      if (getContentPosition() <= this.mHeaderBottomMargin) {
        break label116;
      }
      bool1 = true;
      if (!this.mLastScrollWasDown) {
        break label126;
      }
      float f1 = 0.5F * getActionBarHeight();
      float f2 = getVisibleHeaderHeight();
      if ((!bool1) && (f2 < f1)) {
        break label121;
      }
      bool2 = true;
      this.mLastSnapControlsWasDown = bool2;
      localHandler = this.mHandler;
      if (!bool2) {
        break label131;
      }
    }
    label131:
    for (Runnable localRunnable = this.mSnapControlsDownIfNeededRunnable;; localRunnable = this.mSnapControlsUpIfNeededRunnable)
    {
      localHandler.postDelayed(localRunnable, 50L);
      syncListViews(false);
      return;
      label116:
      bool1 = false;
      break;
      label121:
      bool2 = false;
      break label78;
      label126:
      bool2 = bool1;
      break label78;
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mHasPullToRefresh)
    {
      MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
      boolean bool = this.mSwipeRefreshLayout.onTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      return bool;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  protected final void setActionBarTitleAlpha(float paramFloat)
  {
    if (this.mActionBarTitleAlpha != paramFloat)
    {
      this.mActionBarTitleAlpha = paramFloat;
      applyActionBarTitleAlpha(this.mToolbar, paramFloat);
    }
  }
  
  public void setAlwaysUseFloatingBackground(boolean paramBoolean)
  {
    this.mAlwaysUseFloatingBackground = paramBoolean;
    if (this.mAlwaysUseFloatingBackground)
    {
      setControlsBackground(this.mFloatingControlsBackground, false);
      return;
    }
    setControlsBackground(null, true);
  }
  
  public void setBackgroundParallaxRatio(float paramFloat)
  {
    if (this.mBackgroundParallaxRatio != paramFloat)
    {
      this.mBackgroundParallaxRatio = paramFloat;
      layout();
    }
  }
  
  protected void setBannerFraction(float paramFloat)
  {
    if (paramFloat == this.mBannerFraction) {
      return;
    }
    this.mBannerFraction = paramFloat;
    layout();
  }
  
  public void setBannerOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mBannerTextView.setOnClickListener(paramOnClickListener);
  }
  
  public void setBannerText(int paramInt)
  {
    if (paramInt == 0)
    {
      setBannerText(null);
      return;
    }
    setBannerText(getResources().getText(paramInt), true);
  }
  
  public void setBannerText(CharSequence paramCharSequence)
  {
    setBannerText(paramCharSequence, true);
  }
  
  public void setContentProtectionMode(int paramInt)
  {
    int i = 1;
    if (paramInt == i) {}
    for (;;)
    {
      if (this.mContentProtectionEnabled != i)
      {
        this.mContentProtectionEnabled = i;
        setupBackground();
        layout();
      }
      return;
      i = 0;
    }
  }
  
  public void setFloatingControlsBackground(Drawable paramDrawable)
  {
    setFloatingControlsBackground(paramDrawable, false);
  }
  
  public final void setFloatingControlsBackground(Drawable paramDrawable, boolean paramBoolean)
  {
    this.mFloatingControlsBackground = paramDrawable;
    if (((this.mControlsContainer != null) && (this.mControlsAreFloating)) || (this.mAlwaysUseFloatingBackground)) {
      setControlsBackground(this.mFloatingControlsBackground, paramBoolean);
    }
  }
  
  protected void setFloatingFraction(float paramFloat)
  {
    setFloatingFraction(paramFloat, true);
  }
  
  public void setHeaderMode(int paramInt)
  {
    if (this.mHeaderMode != paramInt)
    {
      this.mHeaderMode = paramInt;
      layout();
    }
  }
  
  public void setHeaderShadowMode(int paramInt)
  {
    if (this.mHeaderShadowMode != paramInt)
    {
      this.mHeaderShadowMode = paramInt;
      layout();
    }
  }
  
  public void setOnLayoutChangedListener(LayoutListener paramLayoutListener)
  {
    this.mLayoutListener = paramLayoutListener;
  }
  
  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    this.mExternalPageChangeListener = paramOnPageChangeListener;
  }
  
  public void setOnScrollListener(RecyclerView.OnScrollListener paramOnScrollListener)
  {
    this.mAppRecyclerViewOnScrollListener = paramOnScrollListener;
  }
  
  public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener)
  {
    this.mAppListViewOnScrollListener = paramOnScrollListener;
  }
  
  public void setOnScrollListener(PlayScrollableContentView.OnScrollListener paramOnScrollListener)
  {
    this.mAppContentViewOnScrollListener = paramOnScrollListener;
  }
  
  public void setOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener)
  {
    this.mTabStrip.setOnTabSelectedListener(paramOnTabSelectedListener);
  }
  
  public void setPullToRefreshProvider(PullToRefreshProvider paramPullToRefreshProvider)
  {
    if (this.mSwipeRefreshLayout == null) {
      throw new IllegalStateException("Cannot initialize pull to refresh before HeaderListLayout has been configured");
    }
    this.mSwipeRefreshLayout.setRefreshing(false);
    this.mSwipeRefreshLayoutCompat.setAlpha(1.0F);
    this.mSwipeRefreshLayoutCompat.setTranslationY(0.0F);
    this.mPullToRefreshProvider = paramPullToRefreshProvider;
    updateActiveListView();
    View localView = findViewById(R.id.swipe_refresh_layout_parent);
    if (this.mPullToRefreshProvider != null) {}
    for (int i = 0;; i = 8)
    {
      localView.setVisibility(i);
      if (this.mPullToRefreshProvider == null) {
        break;
      }
      ViewCompat.setPaddingRelative(localView, 0, 0, 0, 0);
      int j = this.mAbsoluteY;
      boolean bool = false;
      if (j == 0) {
        bool = true;
      }
      setPullToRefreshEnabled(bool);
      return;
    }
    setPullToRefreshEnabled(false);
  }
  
  public void setSingleTabContentDescription(CharSequence paramCharSequence)
  {
    this.mTabBarTitleView.setContentDescription(paramCharSequence);
  }
  
  public void setSingleTabTitle(int paramInt)
  {
    this.mTabBarTitleView.setText(paramInt);
  }
  
  public void setSingleTabTitle(CharSequence paramCharSequence)
  {
    this.mTabBarTitleView.setText(paramCharSequence);
  }
  
  public final void setStatusBarColors(int paramInt1, int paramInt2)
  {
    if (this.mStatusBarUnderlay != null)
    {
      this.mStatusBarUnderlay.initColors(paramInt1, paramInt2);
      this.mStatusBarUnderlay.invalidate();
    }
  }
  
  public final void setTabMode(int paramInt1, int paramInt2)
  {
    int i = this.mTabMode;
    int j = 0;
    if (i != paramInt1)
    {
      this.mTabMode = paramInt1;
      updateTabBarVisibility();
      j = 1;
    }
    if (paramInt2 != this.mHeaderHeight)
    {
      this.mHeaderHeight = paramInt2;
      j = 1;
      setControlsContainerHeight(this.mHeaderHeight - this.mHeaderBottomMargin);
      if (!SUPPORT_ELEVATION) {
        updateHeaderShadowTopMargin();
      }
    }
    if (j != 0) {
      syncListViews(true);
    }
    updateTabPadding(false);
    layout();
  }
  
  public void setViewPager(ViewPager paramViewPager)
  {
    this.mViewPager = paramViewPager;
    this.mTabStrip.setViewPager(paramViewPager);
  }
  
  public final int tryGetCollectionViewAbsoluteY(ViewGroup paramViewGroup)
  {
    int i = -1;
    if ((paramViewGroup instanceof ListView))
    {
      ListView localListView = (ListView)paramViewGroup;
      int j = localListView.getFirstVisiblePosition();
      int k = localListView.getChildCount();
      if ((j == 0) && (k > 0)) {
        i = -localListView.getChildAt(0).getTop();
      }
    }
    View localView;
    do
    {
      do
      {
        return i;
      } while ((!(paramViewGroup instanceof RecyclerView)) && (!(paramViewGroup instanceof PlayScrollableContentView)));
      localView = tryFindHeaderSpacerView(paramViewGroup);
    } while (localView == null);
    return -localView.getTop();
  }
  
  public int tryGetContentTop(ViewGroup paramViewGroup)
  {
    int i = -1;
    View localView = tryFindHeaderSpacerView(paramViewGroup);
    if (localView != null) {
      i = localView.getBottom();
    }
    return i;
  }
  
  private final class ActionBarTitleAlphaAnimation
    extends PlayHeaderListLayout.FloatAnimation
  {
    public ActionBarTitleAlphaAnimation(float paramFloat1, float paramFloat2)
    {
      super(paramFloat1, paramFloat2);
    }
    
    protected final void setValue(float paramFloat)
    {
      PlayHeaderListLayout.this.setActionBarTitleAlpha(paramFloat);
    }
  }
  
  public static abstract class Configurator
  {
    public final Context mContext;
    
    public Configurator(Context paramContext)
    {
      this.mContext = paramContext;
    }
    
    public void addBackgroundView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup) {}
    
    public abstract void addContentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup);
    
    public void addHeroView$39fc0c(ViewGroup paramViewGroup) {}
    
    public boolean allowImmersiveBackground()
    {
      return false;
    }
    
    public boolean alwaysUseFloatingBackground()
    {
      return !PlayHeaderListLayout.USE_ANIMATIONS;
    }
    
    public float getBackgroundViewParallaxRatio()
    {
      return 0.7F;
    }
    
    public int getContentProtectionMode()
    {
      return 0;
    }
    
    public PlayHeaderListTabStrip getCustomTabStrip(Context paramContext, LayoutInflater paramLayoutInflater)
    {
      return null;
    }
    
    public abstract int getHeaderHeight();
    
    public int getHeaderMode()
    {
      return 0;
    }
    
    public int getHeaderShadowMode()
    {
      return 0;
    }
    
    public int getHeroAnimationMode()
    {
      return 0;
    }
    
    public int getListViewId()
    {
      return R.id.play_header_listview;
    }
    
    @TargetApi(21)
    public int getStatusBarOverlayColor()
    {
      TypedArray localTypedArray = this.mContext.obtainStyledAttributes(new int[] { 16843857 });
      int i = localTypedArray.getColor(0, 0);
      localTypedArray.recycle();
      return i;
    }
    
    public int getStatusBarUnderlayColor()
    {
      Context localContext = this.mContext;
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = R.attr.colorPrimary;
      TypedArray localTypedArray = localContext.obtainStyledAttributes(arrayOfInt);
      int i = localTypedArray.getColor(0, 0);
      localTypedArray.recycle();
      return i;
    }
    
    public abstract int getTabMode();
    
    public int getTabPaddingMode()
    {
      if (alwaysUseFloatingBackground()) {
        return 1;
      }
      return 0;
    }
    
    public int getToolBarHeight(Context paramContext)
    {
      return PlayHeaderListLayout.access$1000(paramContext);
    }
    
    public int getToolbarTitleMode()
    {
      return 0;
    }
    
    public int getViewPagerId()
    {
      return R.id.play_header_viewpager;
    }
    
    public abstract boolean hasViewPager();
  }
  
  private abstract class FloatAnimation
    extends Animation
  {
    private final float mFrom;
    private final float mTo;
    
    protected FloatAnimation(float paramFloat1, float paramFloat2)
    {
      this.mFrom = paramFloat1;
      this.mTo = paramFloat2;
    }
    
    protected void applyTransformation(float paramFloat, Transformation paramTransformation)
    {
      super.applyTransformation(paramFloat, paramTransformation);
      setValue(paramFloat * (this.mTo - this.mFrom) + this.mFrom);
    }
    
    protected abstract void setValue(float paramFloat);
  }
  
  private final class FloatingFractionAnimation
    extends PlayHeaderListLayout.FloatAnimation
  {
    public FloatingFractionAnimation(float paramFloat1, float paramFloat2)
    {
      super(paramFloat1, paramFloat2);
    }
    
    protected final void setValue(float paramFloat)
    {
      PlayHeaderListLayout.this.setFloatingFraction(paramFloat);
    }
  }
  
  public static abstract interface LayoutListener
  {
    public abstract void onPlayHeaderListLayoutChanged();
  }
  
  public static abstract interface OnTabSelectedListener
  {
    public abstract void onBeforeTabSelected(int paramInt);
    
    public abstract void onTabSelected(int paramInt);
  }
  
  public static abstract class PullToRefreshProvider {}
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() {};
    final float mFloatingFraction;
    
    private SavedState(Parcel paramParcel)
    {
      super();
      this.mFloatingFraction = paramParcel.readFloat();
    }
    
    SavedState(Parcelable paramParcelable, PlayHeaderListLayout paramPlayHeaderListLayout)
    {
      super();
      this.mFloatingFraction = paramPlayHeaderListLayout.mFloatingFraction;
    }
    
    public String toString()
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Float.valueOf(this.mFloatingFraction);
      return String.format(localLocale, "floatingFraction: %f", arrayOfObject);
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeFloat(this.mFloatingFraction);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.headerlist.PlayHeaderListLayout
 * JD-Core Version:    0.7.0.1
 */