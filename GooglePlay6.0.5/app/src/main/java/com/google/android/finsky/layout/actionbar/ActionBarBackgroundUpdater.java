package com.google.android.finsky.layout.actionbar;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import com.google.android.play.headerlist.PlayHeaderListLayout.LayoutListener;
import com.google.android.play.search.PlaySearchToolbar.PlaySearchToolbarActionListener;

public final class ActionBarBackgroundUpdater
  implements PlayHeaderListLayout.LayoutListener, PlaySearchToolbar.PlaySearchToolbarActionListener
{
  private Drawable mBaseBackground;
  private PlayHeaderListLayout mHeaderListLayout;
  private final Drawable mProtectionBackground;
  private ObjectAnimator mProtectionBackgroundAnimator;
  private int mSearchStatusBarColor;
  private ObjectAnimator mStatusBarColorAnimator;
  private final Drawable mTransparentBackground;
  private boolean mWasHeaderListFloating;
  private boolean mWasInSearchMode;
  private boolean mWasStatusBarUnderlayProtectingControls;
  private Window mWindow;
  
  public ActionBarBackgroundUpdater(Window paramWindow, PlayHeaderListLayout paramPlayHeaderListLayout)
  {
    this.mWindow = paramWindow;
    this.mHeaderListLayout = paramPlayHeaderListLayout;
    Resources localResources = paramPlayHeaderListLayout.getResources();
    this.mTransparentBackground = new ColorDrawable(0);
    final int i = FinskySearchToolbar.getToolbarHeight(this.mHeaderListLayout.getContext());
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = this.mHeaderListLayout.getResources().getColor(2131689730);
    arrayOfInt[1] = 0;
    this.mProtectionBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, arrayOfInt)
    {
      protected final void onBoundsChange(Rect paramAnonymousRect)
      {
        if (paramAnonymousRect.bottom - paramAnonymousRect.top > i) {
          paramAnonymousRect.bottom = (paramAnonymousRect.top + i);
        }
        super.onBoundsChange(paramAnonymousRect);
      }
    };
    this.mBaseBackground = this.mProtectionBackground;
    this.mSearchStatusBarColor = localResources.getColor(2131689676);
    this.mWasHeaderListFloating = this.mHeaderListLayout.isHeaderFloating();
    this.mWasStatusBarUnderlayProtectingControls = this.mHeaderListLayout.isStatusBarUnderlayProtectingControls();
    updateActionBar();
  }
  
  private void setBackground(Drawable paramDrawable)
  {
    if (this.mHeaderListLayout == null) {
      return;
    }
    this.mHeaderListLayout.getToolbarContainer().setBackgroundDrawable(paramDrawable);
  }
  
  @TargetApi(21)
  private void updateVisualForStatusBar()
  {
    if (Build.VERSION.SDK_INT < 21) {}
    while (this.mHeaderListLayout == null) {
      return;
    }
    int i;
    int j;
    if ((this.mWasInSearchMode) && (!this.mWasStatusBarUnderlayProtectingControls))
    {
      i = 1;
      if (this.mStatusBarColorAnimator != null) {
        this.mStatusBarColorAnimator.cancel();
      }
      if (i == 0) {
        break label147;
      }
      j = this.mSearchStatusBarColor;
      label55:
      this.mStatusBarColorAnimator = ObjectAnimator.ofArgb(this.mWindow, "statusBarColor", new int[] { j });
      this.mStatusBarColorAnimator.setDuration(300L).start();
      if (this.mProtectionBackgroundAnimator != null) {
        this.mProtectionBackgroundAnimator.cancel();
      }
      if (i == 0) {
        break label163;
      }
    }
    label147:
    label163:
    for (int k = 0;; k = 255)
    {
      this.mProtectionBackgroundAnimator = ObjectAnimator.ofInt(this.mProtectionBackground, "alpha", new int[] { k });
      this.mProtectionBackgroundAnimator.setDuration(300L).start();
      return;
      i = 0;
      break;
      j = this.mHeaderListLayout.getResources().getColor(2131689730);
      break label55;
    }
  }
  
  public final void onEnterSearchMode()
  {
    setSearchMode(true);
  }
  
  public final void onExitSearchMode()
  {
    setSearchMode(false);
  }
  
  public final void onPlayHeaderListLayoutChanged()
  {
    if (this.mHeaderListLayout == null) {}
    do
    {
      boolean bool2;
      do
      {
        return;
        boolean bool1 = this.mHeaderListLayout.isHeaderFloating();
        if (this.mWasHeaderListFloating != bool1)
        {
          this.mWasHeaderListFloating = bool1;
          updateActionBar();
        }
        bool2 = this.mHeaderListLayout.isStatusBarUnderlayProtectingControls();
      } while (this.mWasStatusBarUnderlayProtectingControls == bool2);
      this.mWasStatusBarUnderlayProtectingControls = bool2;
    } while (!this.mWasInSearchMode);
    updateVisualForStatusBar();
  }
  
  public final void reset()
  {
    setBackground(this.mTransparentBackground);
    this.mWindow = null;
    this.mHeaderListLayout = null;
  }
  
  public final void setSearchMode(boolean paramBoolean)
  {
    if (this.mWasInSearchMode != paramBoolean)
    {
      this.mWasInSearchMode = paramBoolean;
      if (!this.mWasStatusBarUnderlayProtectingControls) {
        updateVisualForStatusBar();
      }
    }
  }
  
  public final void updateActionBar()
  {
    if (this.mWasHeaderListFloating) {}
    for (Drawable localDrawable = this.mTransparentBackground;; localDrawable = this.mProtectionBackground)
    {
      this.mBaseBackground = localDrawable;
      setBackground(this.mBaseBackground);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.actionbar.ActionBarBackgroundUpdater
 * JD-Core Version:    0.7.0.1
 */