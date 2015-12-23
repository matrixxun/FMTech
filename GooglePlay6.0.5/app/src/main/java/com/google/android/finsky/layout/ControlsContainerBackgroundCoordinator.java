package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.headerlist.PlayHeaderListLayout;

public final class ControlsContainerBackgroundCoordinator
{
  static final boolean SUPPORT_ELEVATION;
  public ControlsContainerBackground mBackground;
  public final Context mContext;
  ViewGroup mControlsContainer;
  public PlayHeaderListLayout mHeaderListLayout;
  View mHeroContainer;
  private float mHeroContainerAlpha = -1.0F;
  public int mLastTouchX;
  public final int[] mLocationOnScreen = new int[2];
  final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener()
  {
    public final boolean onPreDraw()
    {
      return !ControlsContainerBackgroundCoordinator.this.updateBackgroundHeightAndFades();
    }
  };
  public Drawable mQueuedBackgroundDrawable;
  private final int mStatusBarHeight;
  public View mTabStrip;
  private float mTabStripAlpha = -1.0F;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORT_ELEVATION = bool;
      return;
    }
  }
  
  public ControlsContainerBackgroundCoordinator(Context paramContext)
  {
    this.mContext = paramContext;
    this.mStatusBarHeight = UiUtils.getStatusBarHeight(this.mContext);
  }
  
  public final void detach()
  {
    if (this.mTabStrip != null)
    {
      if (SUPPORT_ELEVATION) {
        this.mControlsContainer.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
      }
      this.mControlsContainer = null;
      this.mHeroContainer = null;
      this.mTabStrip.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
      this.mTabStrip = null;
    }
  }
  
  public final boolean updateBackgroundHeightAndFades()
  {
    if ((this.mHeaderListLayout == null) || (this.mControlsContainer == null))
    {
      bool1 = false;
      return bool1;
    }
    int i = this.mTabStrip.getMeasuredHeight();
    int j = this.mControlsContainer.getHeight();
    if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
      j += this.mStatusBarHeight;
    }
    boolean bool2 = this.mBackground.setHeights(i, j);
    boolean bool1 = false;
    if (bool2)
    {
      if (SUPPORT_ELEVATION) {
        this.mControlsContainer.invalidateOutline();
      }
      bool1 = true;
    }
    int k = this.mStatusBarHeight + this.mHeaderListLayout.getActionBarHeight();
    this.mTabStrip.getLocationOnScreen(this.mLocationOnScreen);
    int m = this.mLocationOnScreen[1];
    ControlsContainerBackground localControlsContainerBackground = this.mBackground;
    if (m <= k) {}
    for (boolean bool3 = true;; bool3 = false)
    {
      if ((localControlsContainerBackground.mUseMaxHeight != bool3) && (localControlsContainerBackground.mMaxHeight != localControlsContainerBackground.mMinHeight)) {
        break label274;
      }
      float f1 = Math.min(1.0F, Math.max(0.0F, (m - 0.0F) / this.mStatusBarHeight));
      if (this.mTabStripAlpha != f1)
      {
        this.mTabStripAlpha = f1;
        ViewCompat.setAlpha(this.mTabStrip, f1);
      }
      int n = (k + j) / 2;
      int i1 = k + (n - k) / 4;
      float f2 = Math.min(1.0F, Math.max(0.0F, (m - i1) / (n - i1)));
      if (this.mHeroContainerAlpha == f2) {
        break;
      }
      this.mHeroContainerAlpha = f2;
      ViewCompat.setAlpha(this.mHeroContainer, f2);
      return bool1;
    }
    label274:
    localControlsContainerBackground.mUseMaxHeight = bool3;
    int i2;
    label293:
    HeightAnimation localHeightAnimation;
    if (bool3)
    {
      i2 = localControlsContainerBackground.mMaxHeight;
      if (localControlsContainerBackground.mHeightAnimation != null) {
        localControlsContainerBackground.mHeightAnimation.cancelForever();
      }
      localControlsContainerBackground.mHeightAnimation = new HeightAnimation(localControlsContainerBackground);
      localControlsContainerBackground.mHeightAnimation.setHeights(localControlsContainerBackground.getHeight(), i2);
      localControlsContainerBackground.mHeightAnimation.setDuration(300 * Math.abs(localControlsContainerBackground.getHeight() - i2) / Math.abs(localControlsContainerBackground.mMaxHeight - localControlsContainerBackground.mMinHeight));
      localHeightAnimation = localControlsContainerBackground.mHeightAnimation;
      if (!bool3) {
        break label428;
      }
    }
    label428:
    for (Object localObject = new AccelerateInterpolator();; localObject = new DecelerateInterpolator())
    {
      localHeightAnimation.setInterpolator((Interpolator)localObject);
      localControlsContainerBackground.startAnimation(localControlsContainerBackground.mHeightAnimation);
      break;
      i2 = localControlsContainerBackground.mMinHeight;
      break label293;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ControlsContainerBackgroundCoordinator
 * JD-Core Version:    0.7.0.1
 */