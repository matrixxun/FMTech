package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.google.android.finsky.layout.InsetsAware;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import com.google.android.play.headerlist.PlayHeaderListLayout.Configurator;
import com.google.android.play.search.PlaySearchToolbar;

public class FinskyHeaderListLayout
  extends PlayHeaderListLayout
  implements InsetsAware
{
  private int mActionBarTitleAlpha;
  private int mActionBarTitleColor;
  private View mBackgroundContainer;
  private View mBackgroundView;
  private FinskyConfigurator mConfigurator;
  public View mContentView;
  private final ScrollLockingTouchDelegate mScrollLockingTouchDelegate;
  private boolean mShouldUseScrollLocking = true;
  private int mSideMargin;
  private final ScrollLockingTouchDelegate.Delegater mTouchDelegater = new ScrollLockingTouchDelegate.Delegater()
  {
    public final int getBackgroundBottomHeightToIgnoreTouches()
    {
      return FinskyHeaderListLayout.this.getTabBarHeight();
    }
    
    public final ViewGroup getCurrentVerticalListView()
    {
      return FinskyHeaderListLayout.this.getCurrentListView();
    }
    
    public final View getDelegatingView()
    {
      return FinskyHeaderListLayout.this;
    }
    
    public final View getImmediateChildContainingVerticalScroller()
    {
      return FinskyHeaderListLayout.this.mContentView;
    }
    
    public final View getPositionedBackgroundContainer()
    {
      return FinskyHeaderListLayout.this.mBackgroundContainer;
    }
    
    public final View getRelevantChildUnderTouch(float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      int i = FinskyHeaderListLayout.this.getChildCount();
      for (int j = i - 1; j >= 0; j--)
      {
        View localView = FinskyHeaderListLayout.this.getChildAt(FinskyHeaderListLayout.this.getChildDrawingOrder(i, j));
        if (localView.getId() != 2131755921)
        {
          float f = localView.getTranslationY();
          if ((paramAnonymousFloat1 >= localView.getLeft()) && (paramAnonymousFloat1 < localView.getRight()) && (paramAnonymousFloat2 >= f + localView.getTop()) && (paramAnonymousFloat2 < f + localView.getBottom())) {
            return localView;
          }
        }
      }
      return null;
    }
    
    public final int getSideMargin()
    {
      return FinskyHeaderListLayout.this.mSideMargin;
    }
    
    public final View getTouchableBackgroundView()
    {
      return FinskyHeaderListLayout.this.mBackgroundView;
    }
    
    public final boolean isBackgroundSpacerView(View paramAnonymousView)
    {
      return paramAnonymousView instanceof FinskyHeaderListLayout.StreamSpacer;
    }
  };
  private final boolean mUseWideLayout;
  
  public FinskyHeaderListLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FinskyHeaderListLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mScrollLockingTouchDelegate = new ScrollLockingTouchDelegate(this.mTouchDelegater, paramContext);
    this.mUseWideLayout = paramContext.getResources().getBoolean(2131427334);
  }
  
  public static int getMinimumHeaderHeight$3047fd86(Context paramContext, int paramInt)
  {
    return PlayHeaderListLayout.getMinimumHeaderHeight(paramContext, paramInt, 0, PlaySearchToolbar.getToolbarHeight(paramContext));
  }
  
  protected final void applyActionBarTitleAlpha(Toolbar paramToolbar, float paramFloat)
  {
    this.mActionBarTitleAlpha = Math.max(0, Math.min(255, Math.round(255.0F * paramFloat)));
    paramToolbar.setTitleTextColor(ColorUtils.setAlphaComponent(this.mActionBarTitleColor, this.mActionBarTitleAlpha));
  }
  
  public final void configure(PlayHeaderListLayout.Configurator paramConfigurator)
  {
    super.configure(paramConfigurator);
    this.mConfigurator = ((FinskyConfigurator)paramConfigurator);
    this.mBackgroundContainer = findViewById(2131755910);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    ScrollLockingTouchDelegate localScrollLockingTouchDelegate;
    float f1;
    float f2;
    ViewGroup localViewGroup;
    int i;
    if (this.mShouldUseScrollLocking)
    {
      localScrollLockingTouchDelegate = this.mScrollLockingTouchDelegate;
      localScrollLockingTouchDelegate.mLockHorizontalScroll = false;
      localScrollLockingTouchDelegate.mLockVerticalScroll = false;
      f1 = paramMotionEvent.getX();
      f2 = paramMotionEvent.getY();
      switch (paramMotionEvent.getAction())
      {
      case 1: 
      default: 
        localViewGroup = localScrollLockingTouchDelegate.mDelegater.getCurrentVerticalListView();
        if ((localViewGroup == null) || (!localViewGroup.isShown())) {
          i = 0;
        }
        break;
      }
    }
    for (;;)
    {
      if (i == 0) {
        if (!super.onInterceptTouchEvent(paramMotionEvent)) {
          break label716;
        }
      }
      return true;
      localScrollLockingTouchDelegate.mXDistanceScrolledSinceLastDown = 0.0F;
      localScrollLockingTouchDelegate.mYDistanceScrolledSinceLastDown = 0.0F;
      if (localScrollLockingTouchDelegate.mLastTouchDownEvent != null) {
        localScrollLockingTouchDelegate.mLastTouchDownEvent.recycle();
      }
      localScrollLockingTouchDelegate.mLastTouchDownEvent = MotionEvent.obtain(paramMotionEvent);
      break;
      localScrollLockingTouchDelegate.mYDistanceScrolledSinceLastDown += Math.abs(f2 - localScrollLockingTouchDelegate.mLastMotionY);
      break;
      localScrollLockingTouchDelegate.mLastMotionX = f1;
      localScrollLockingTouchDelegate.mLastMotionY = f2;
      boolean bool = localViewGroup instanceof RecyclerView;
      if ((bool) && (localScrollLockingTouchDelegate.mYDistanceScrolledSinceLastDown >= localScrollLockingTouchDelegate.mScrollThreshold))
      {
        localScrollLockingTouchDelegate.lockVerticalScroll(paramMotionEvent);
        i = 1;
      }
      else if (paramMotionEvent.getAction() != 0)
      {
        i = 0;
      }
      else
      {
        View localView1 = localScrollLockingTouchDelegate.mDelegater.getTouchableBackgroundView();
        int j = localScrollLockingTouchDelegate.mDelegater.getSideMargin();
        if ((f1 < j) || (f1 >= localScrollLockingTouchDelegate.mDelegater.getDelegatingView().getWidth() - j))
        {
          View localView2 = localScrollLockingTouchDelegate.mDelegater.getPositionedBackgroundContainer();
          if ((localView1 != null) && (localView1.isShown()) && (localView2 != null) && (f2 < localView2.getBottom() + localView2.getTranslationY()))
          {
            localScrollLockingTouchDelegate.mEventForwardingTargetView = localView1;
            i = 1;
            continue;
          }
        }
        if (!bool)
        {
          i = 0;
        }
        else
        {
          RecyclerView localRecyclerView = (RecyclerView)localViewGroup;
          if (localRecyclerView.getScrollState() == 2) {
            localRecyclerView.stopScroll();
          }
          if (localRecyclerView.getScrollState() == 1)
          {
            i = 0;
          }
          else
          {
            if (localScrollLockingTouchDelegate.mLastTouchDownEvent != null) {}
            float f3;
            float f4;
            for (MotionEvent localMotionEvent = localScrollLockingTouchDelegate.mLastTouchDownEvent;; localMotionEvent = paramMotionEvent)
            {
              f3 = localMotionEvent.getX();
              f4 = localMotionEvent.getY();
              for (Object localObject = localRecyclerView; localObject != localScrollLockingTouchDelegate.mDelegater.getDelegatingView(); localObject = (View)((View)localObject).getParent()) {
                f4 -= ((View)localObject).getTop();
              }
            }
            int k = -1 + localRecyclerView.mChildHelper.getChildCount();
            label467:
            View localView3;
            if (k >= 0)
            {
              localView3 = localRecyclerView.mChildHelper.getChildAt(k);
              float f5 = ViewCompat.getTranslationX(localView3);
              float f6 = ViewCompat.getTranslationY(localView3);
              if ((f3 < f5 + localView3.getLeft()) || (f3 > f5 + localView3.getRight()) || (f4 < f6 + localView3.getTop()) || (f4 > f6 + localView3.getBottom())) {}
            }
            for (;;)
            {
              if ((!localScrollLockingTouchDelegate.mDelegater.isBackgroundSpacerView(localView3)) || (f4 >= localView3.getBottom() - localScrollLockingTouchDelegate.mDelegater.getBackgroundBottomHeightToIgnoreTouches())) {
                break label618;
              }
              localScrollLockingTouchDelegate.mEventForwardingTargetView = localView1;
              i = 1;
              break;
              k--;
              break label467;
              localView3 = null;
            }
            label618:
            View localView4 = localScrollLockingTouchDelegate.mDelegater.getRelevantChildUnderTouch(f3, localMotionEvent.getY());
            View localView5 = localScrollLockingTouchDelegate.mDelegater.getImmediateChildContainingVerticalScroller();
            if (localView4 != localView5)
            {
              i = 0;
            }
            else if (((localView3 instanceof HorizontalScrollerContainer)) && (((HorizontalScrollerContainer)localView3).isPointInHorizontalScroller(f3, f4 - localView3.getTop())))
            {
              localScrollLockingTouchDelegate.mEventForwardingTargetView = localView3;
              i = 1;
            }
            else
            {
              localScrollLockingTouchDelegate.mEventForwardingTargetView = localView5;
              i = 1;
            }
          }
        }
      }
    }
    label716:
    return false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    if (this.mUseWideLayout) {}
    for (int j = UiUtils.getGridColumnContentWidth(getResources());; j = i)
    {
      this.mSideMargin = ((i - Math.min(i, j)) / 2);
      return;
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ScrollLockingTouchDelegate localScrollLockingTouchDelegate;
    int i;
    if (this.mShouldUseScrollLocking)
    {
      localScrollLockingTouchDelegate = this.mScrollLockingTouchDelegate;
      if (localScrollLockingTouchDelegate.mEventForwardingTargetView != null) {
        break label45;
      }
      i = 0;
      if (i != 0) {}
    }
    else
    {
      boolean bool1 = super.onTouchEvent(paramMotionEvent);
      bool2 = false;
      if (!bool1) {
        break label43;
      }
    }
    boolean bool2 = true;
    label43:
    return bool2;
    label45:
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    localScrollLockingTouchDelegate.forwardMotionEvent(paramMotionEvent);
    switch (paramMotionEvent.getAction())
    {
    }
    for (;;)
    {
      localScrollLockingTouchDelegate.mLastMotionX = f1;
      localScrollLockingTouchDelegate.mLastMotionY = f2;
      i = 1;
      break;
      if ((!localScrollLockingTouchDelegate.mLockHorizontalScroll) && (!localScrollLockingTouchDelegate.mLockVerticalScroll))
      {
        localScrollLockingTouchDelegate.mXDistanceScrolledSinceLastDown += Math.abs(f1 - localScrollLockingTouchDelegate.mLastMotionX);
        localScrollLockingTouchDelegate.mYDistanceScrolledSinceLastDown += Math.abs(f2 - localScrollLockingTouchDelegate.mLastMotionY);
        if (localScrollLockingTouchDelegate.mXDistanceScrolledSinceLastDown >= localScrollLockingTouchDelegate.mYDistanceScrolledSinceLastDown)
        {
          if (localScrollLockingTouchDelegate.mXDistanceScrolledSinceLastDown >= 10.0F * localScrollLockingTouchDelegate.mScrollThreshold)
          {
            localScrollLockingTouchDelegate.mLockHorizontalScroll = true;
            if (localScrollLockingTouchDelegate.mLastTouchDownEvent != null)
            {
              localScrollLockingTouchDelegate.mLastTouchDownEvent.recycle();
              localScrollLockingTouchDelegate.mLastTouchDownEvent = null;
            }
          }
        }
        else if (localScrollLockingTouchDelegate.mYDistanceScrolledSinceLastDown >= localScrollLockingTouchDelegate.mScrollThreshold)
        {
          localScrollLockingTouchDelegate.lockVerticalScroll(paramMotionEvent);
          localScrollLockingTouchDelegate.forwardMotionEvent(paramMotionEvent);
          continue;
          localScrollLockingTouchDelegate.mEventForwardingTargetView = null;
        }
      }
    }
  }
  
  public void setActionBarTitleColor(int paramInt)
  {
    this.mActionBarTitleColor = paramInt;
    Toolbar localToolbar = getToolbar();
    if (localToolbar != null) {
      applyActionBarTitleAlpha(localToolbar, this.mActionBarTitleAlpha);
    }
  }
  
  public void setBackgroundViewForTouchPassthrough(View paramView)
  {
    this.mBackgroundView = paramView;
  }
  
  public void setContentViewId(int paramInt)
  {
    this.mContentView = findViewById(paramInt);
  }
  
  public void setShouldUseScrollLocking(boolean paramBoolean)
  {
    this.mShouldUseScrollLocking = paramBoolean;
  }
  
  public final boolean shouldApplyWindowInsets()
  {
    return true;
  }
  
  public final int tryGetContentTop(ViewGroup paramViewGroup)
  {
    if ((paramViewGroup.getChildCount() == 0) && (this.mConfigurator != null)) {
      return this.mConfigurator.getLeadingSpacerHeight();
    }
    return super.tryGetContentTop(paramViewGroup);
  }
  
  public static abstract class FinskyConfigurator
    extends PlayHeaderListLayout.Configurator
  {
    public FinskyConfigurator(Context paramContext)
    {
      super();
    }
    
    public void addBackgroundView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup) {}
    
    public void addHeroView$39fc0c(ViewGroup paramViewGroup) {}
    
    public boolean alwaysUseFloatingBackground()
    {
      return true;
    }
    
    public int getContentProtectionMode()
    {
      return 0;
    }
    
    public int getHeaderHeight()
    {
      return FinskySearchToolbar.getToolbarHeight(this.mContext);
    }
    
    public int getHeaderMode()
    {
      return 0;
    }
    
    public int getLeadingSpacerHeight()
    {
      return 0;
    }
    
    public int getTabMode()
    {
      return 2;
    }
    
    public int getToolBarHeight(Context paramContext)
    {
      return PlaySearchToolbar.getToolbarHeight(paramContext);
    }
    
    public int getToolbarTitleMode()
    {
      return 1;
    }
    
    public boolean hasViewPager()
    {
      return false;
    }
  }
  
  public static abstract interface StreamSpacer {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.FinskyHeaderListLayout
 * JD-Core Version:    0.7.0.1
 */