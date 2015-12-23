package com.google.android.play.drawer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.R.dimen;
import com.google.android.play.R.drawable;
import com.google.android.play.R.id;
import com.google.android.play.utils.PlayCommonLog;

public class PlayDrawerLayout
  extends DrawerLayout
  implements DrawerLayout.DrawerListener
{
  public TextView mDockedActionView;
  public PlayDrawerAdapter mDrawerAdapter;
  public ListView mDrawerList;
  private DrawerLayout.DrawerListener mDrawerListener;
  public ViewGroup mDrawerRoot;
  private float mDrawerSlideOffset;
  private int mDrawerState;
  public ActionBarDrawerToggle mDrawerToggle;
  public boolean mIsConfigured;
  public boolean mIsMiniProfile = false;
  
  public PlayDrawerLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayDrawerLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    int i = R.drawable.drawer_shadow;
    Drawable localDrawable = getResources().getDrawable(i);
    if (!DrawerLayout.SET_DRAWER_SHADOW_FROM_ELEVATION)
    {
      this.mShadowStart = localDrawable;
      super.resolveShadowDrawables();
      invalidate();
    }
    super.setDrawerListener(this);
  }
  
  public final void checkIsConfigured()
  {
    if (!this.mIsConfigured) {
      PlayCommonLog.wtf("Play Drawer configure was not called", new Object[0]);
    }
  }
  
  public final void closeDrawer()
  {
    checkIsConfigured();
    if (isDrawerOpen(this.mDrawerRoot)) {
      closeDrawer(this.mDrawerRoot);
    }
  }
  
  public final boolean isDrawerOpen()
  {
    checkIsConfigured();
    return isDrawerOpen(this.mDrawerRoot);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((this.mIsConfigured) && (this.mDrawerToggle != null))
    {
      ActionBarDrawerToggle localActionBarDrawerToggle = this.mDrawerToggle;
      if (!localActionBarDrawerToggle.mHasCustomUpIndicator) {
        localActionBarDrawerToggle.mHomeAsUpIndicator = localActionBarDrawerToggle.getThemeUpIndicator();
      }
      localActionBarDrawerToggle.syncState();
    }
  }
  
  public void onDrawerClosed(View paramView)
  {
    if (this.mDrawerToggle != null) {
      this.mDrawerToggle.onDrawerClosed(paramView);
    }
    this.mDrawerAdapter.collapseAccountListIfNeeded();
    if (this.mDrawerListener != null) {
      this.mDrawerListener.onDrawerClosed(paramView);
    }
  }
  
  public void onDrawerOpened(View paramView)
  {
    if (this.mDrawerToggle != null) {
      this.mDrawerToggle.onDrawerOpened(paramView);
    }
    if (this.mDrawerListener != null) {
      this.mDrawerListener.onDrawerOpened(paramView);
    }
  }
  
  public final void onDrawerSlide(View paramView, float paramFloat)
  {
    if ((this.mDrawerState == 2) && (paramFloat < this.mDrawerSlideOffset)) {
      this.mDrawerAdapter.collapseAccountListIfNeeded();
    }
    this.mDrawerSlideOffset = paramFloat;
    if (this.mDrawerToggle != null) {
      this.mDrawerToggle.onDrawerSlide(paramView, paramFloat);
    }
    if (this.mDrawerListener != null) {
      this.mDrawerListener.onDrawerSlide(paramView, paramFloat);
    }
  }
  
  public final void onDrawerStateChanged(int paramInt)
  {
    this.mDrawerState = paramInt;
    if (this.mDrawerListener != null) {
      this.mDrawerListener.onDrawerStateChanged(paramInt);
    }
  }
  
  @TargetApi(20)
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDrawerRoot = ((ViewGroup)findViewById(R.id.play_drawer_root));
    this.mDrawerList = ((ListView)findViewById(R.id.play_drawer_list));
    this.mDockedActionView = ((TextView)findViewById(R.id.play_drawer_docked_action));
    this.mDockedActionView.setVisibility(8);
    if (Build.VERSION.SDK_INT >= 21) {
      this.mDrawerRoot.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener()
      {
        @TargetApi(20)
        public final WindowInsets onApplyWindowInsets(View paramAnonymousView, WindowInsets paramAnonymousWindowInsets)
        {
          return paramAnonymousWindowInsets.consumeSystemWindowInsets();
        }
      });
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    try
    {
      boolean bool = super.onInterceptTouchEvent(paramMotionEvent);
      return bool;
    }
    catch (NullPointerException localNullPointerException) {}
    return false;
  }
  
  public void setActionBarHeight(int paramInt)
  {
    checkIsConfigured();
    Resources localResources = getResources();
    int i = localResources.getDisplayMetrics().widthPixels;
    int j = localResources.getDimensionPixelSize(R.dimen.play_drawer_max_width);
    this.mDrawerRoot.getLayoutParams().width = Math.min(j, i - paramInt);
    this.mDrawerRoot.requestLayout();
  }
  
  public void setCurrentAvatarClickable(boolean paramBoolean)
  {
    checkIsConfigured();
    this.mDrawerAdapter.mCurrentAvatarClickable = paramBoolean;
  }
  
  public void setDrawerIndicatorEnabled(boolean paramBoolean)
  {
    checkIsConfigured();
    ActionBarDrawerToggle localActionBarDrawerToggle;
    int i;
    if (this.mDrawerToggle != null)
    {
      localActionBarDrawerToggle = this.mDrawerToggle;
      if (paramBoolean != localActionBarDrawerToggle.mDrawerIndicatorEnabled)
      {
        if (!paramBoolean) {
          break label74;
        }
        Drawable localDrawable = (Drawable)localActionBarDrawerToggle.mSlider;
        if (!localActionBarDrawerToggle.mDrawerLayout.isDrawerOpen$134632()) {
          break label65;
        }
        i = localActionBarDrawerToggle.mCloseDrawerContentDescRes;
        localActionBarDrawerToggle.setActionBarUpIndicator(localDrawable, i);
      }
    }
    for (;;)
    {
      localActionBarDrawerToggle.mDrawerIndicatorEnabled = paramBoolean;
      return;
      label65:
      i = localActionBarDrawerToggle.mOpenDrawerContentDescRes;
      break;
      label74:
      localActionBarDrawerToggle.setActionBarUpIndicator(localActionBarDrawerToggle.mHomeAsUpIndicator, 0);
    }
  }
  
  public final void setDrawerListener(DrawerLayout.DrawerListener paramDrawerListener)
  {
    this.mDrawerListener = paramDrawerListener;
  }
  
  public void setDrawerToggle(ActionBarDrawerToggle paramActionBarDrawerToggle)
  {
    this.mDrawerToggle = paramActionBarDrawerToggle;
    if (this.mDrawerToggle != null) {
      this.mDrawerToggle.syncState();
    }
  }
  
  public void setIsMiniProfile(boolean paramBoolean)
  {
    this.mIsMiniProfile = paramBoolean;
  }
  
  public static abstract interface PlayDrawerContentClickListener
  {
    public abstract void onAccountListToggleButtonClicked(boolean paramBoolean);
    
    public abstract boolean onCurrentAccountClicked(boolean paramBoolean, DocV2 paramDocV2);
    
    public abstract boolean onPrimaryActionClicked(PlayDrawerLayout.PlayDrawerPrimaryAction paramPlayDrawerPrimaryAction);
    
    public abstract boolean onSecondaryAccountClicked(String paramString);
    
    public abstract boolean onSecondaryActionClicked(PlayDrawerLayout.PlayDrawerSecondaryAction paramPlayDrawerSecondaryAction);
  }
  
  public static final class PlayDrawerDownloadSwitchConfig
  {
    public final String actionText;
    public final int checkedTextColor;
    public final boolean isChecked;
    public final int thumbDrawableId;
    public final int trackDrawableId;
  }
  
  public static final class PlayDrawerPrimaryAction
  {
    public final Runnable actionSelectedRunnable;
    public final String actionText;
    public final int activeIconResId;
    public final int activeTextColorResId;
    public final boolean hasNotifications;
    public final int iconResId;
    public final boolean isActive;
    public final boolean isAvailableInDownloadOnly;
    public final boolean isChild;
    public final boolean isSeparator;
    public final int secondaryIconResId;
    
    public PlayDrawerPrimaryAction()
    {
      this.actionText = null;
      this.iconResId = -1;
      this.activeIconResId = -1;
      this.secondaryIconResId = -1;
      this.activeTextColorResId = -1;
      this.isActive = false;
      this.isAvailableInDownloadOnly = false;
      this.actionSelectedRunnable = null;
      this.isSeparator = true;
      this.isChild = false;
      this.hasNotifications = false;
    }
    
    public PlayDrawerPrimaryAction(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, Runnable paramRunnable)
    {
      this.actionText = paramString;
      this.iconResId = paramInt1;
      this.activeIconResId = paramInt2;
      this.secondaryIconResId = paramInt3;
      this.activeTextColorResId = paramInt4;
      this.isActive = paramBoolean1;
      this.isAvailableInDownloadOnly = paramBoolean2;
      this.actionSelectedRunnable = paramRunnable;
      this.isSeparator = false;
      this.isChild = paramBoolean3;
      this.hasNotifications = paramBoolean4;
    }
    
    public PlayDrawerPrimaryAction(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, Runnable paramRunnable)
    {
      this(paramString, paramInt1, paramInt2, paramInt3, paramBoolean, false, false, paramRunnable);
    }
    
    public PlayDrawerPrimaryAction(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Runnable paramRunnable)
    {
      this(paramString, paramInt1, paramInt2, -1, paramInt3, paramBoolean1, false, false, paramBoolean3, paramRunnable);
    }
  }
  
  public static final class PlayDrawerSecondaryAction
  {
    public final Runnable actionSelectedRunnable;
    public final String actionText;
    
    public PlayDrawerSecondaryAction(String paramString, Runnable paramRunnable)
    {
      this.actionText = paramString;
      this.actionSelectedRunnable = paramRunnable;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.drawer.PlayDrawerLayout
 * JD-Core Version:    0.7.0.1
 */