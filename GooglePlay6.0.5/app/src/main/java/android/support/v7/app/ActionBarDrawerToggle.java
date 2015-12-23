package android.support.v7.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.View;

public final class ActionBarDrawerToggle
  implements DrawerLayout.DrawerListener
{
  private final Delegate mActivityImpl;
  public final int mCloseDrawerContentDescRes;
  public boolean mDrawerIndicatorEnabled = true;
  public final DrawerLayout mDrawerLayout;
  public boolean mHasCustomUpIndicator;
  public Drawable mHomeAsUpIndicator;
  public final int mOpenDrawerContentDescRes;
  public DrawerToggle mSlider;
  private boolean mWarnedForDisplayHomeAsUp = false;
  
  public ActionBarDrawerToggle(Activity paramActivity, DrawerLayout paramDrawerLayout, int paramInt1, int paramInt2)
  {
    this(paramActivity, paramDrawerLayout, paramInt1, paramInt2, (byte)0);
  }
  
  private <T extends Drawable,  extends DrawerToggle> ActionBarDrawerToggle(Activity paramActivity, DrawerLayout paramDrawerLayout, int paramInt1, int paramInt2, byte paramByte)
  {
    if ((paramActivity instanceof DelegateProvider)) {
      this.mActivityImpl = ((DelegateProvider)paramActivity).getDrawerToggleDelegate();
    }
    for (;;)
    {
      this.mDrawerLayout = paramDrawerLayout;
      this.mOpenDrawerContentDescRes = paramInt1;
      this.mCloseDrawerContentDescRes = paramInt2;
      this.mSlider = new DrawerArrowDrawableToggle(paramActivity, this.mActivityImpl.getActionBarThemedContext());
      this.mHomeAsUpIndicator = getThemeUpIndicator();
      return;
      if (Build.VERSION.SDK_INT >= 18) {
        this.mActivityImpl = new JellybeanMr2Delegate(paramActivity, (byte)0);
      } else if (Build.VERSION.SDK_INT >= 11) {
        this.mActivityImpl = new HoneycombDelegate(paramActivity, (byte)0);
      } else {
        this.mActivityImpl = new DummyDelegate(paramActivity);
      }
    }
  }
  
  private void setActionBarDescription(int paramInt)
  {
    this.mActivityImpl.setActionBarDescription(paramInt);
  }
  
  public final Drawable getThemeUpIndicator()
  {
    return this.mActivityImpl.getThemeUpIndicator();
  }
  
  public final void onDrawerClosed(View paramView)
  {
    this.mSlider.setPosition(0.0F);
    if (this.mDrawerIndicatorEnabled) {
      setActionBarDescription(this.mOpenDrawerContentDescRes);
    }
  }
  
  public final void onDrawerOpened(View paramView)
  {
    this.mSlider.setPosition(1.0F);
    if (this.mDrawerIndicatorEnabled) {
      setActionBarDescription(this.mCloseDrawerContentDescRes);
    }
  }
  
  public final void onDrawerSlide(View paramView, float paramFloat)
  {
    this.mSlider.setPosition(Math.min(1.0F, Math.max(0.0F, paramFloat)));
  }
  
  public final void onDrawerStateChanged(int paramInt) {}
  
  public final void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
  {
    if ((!this.mWarnedForDisplayHomeAsUp) && (!this.mActivityImpl.isNavigationVisible()))
    {
      Log.w("ActionBarDrawerToggle", "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
      this.mWarnedForDisplayHomeAsUp = true;
    }
    this.mActivityImpl.setActionBarUpIndicator(paramDrawable, paramInt);
  }
  
  public final void syncState()
  {
    Drawable localDrawable;
    if (this.mDrawerLayout.isDrawerOpen$134632())
    {
      this.mSlider.setPosition(1.0F);
      if (this.mDrawerIndicatorEnabled)
      {
        localDrawable = (Drawable)this.mSlider;
        if (!this.mDrawerLayout.isDrawerOpen$134632()) {
          break label70;
        }
      }
    }
    label70:
    for (int i = this.mCloseDrawerContentDescRes;; i = this.mOpenDrawerContentDescRes)
    {
      setActionBarUpIndicator(localDrawable, i);
      return;
      this.mSlider.setPosition(0.0F);
      break;
    }
  }
  
  public static abstract interface Delegate
  {
    public abstract Context getActionBarThemedContext();
    
    public abstract Drawable getThemeUpIndicator();
    
    public abstract boolean isNavigationVisible();
    
    public abstract void setActionBarDescription(int paramInt);
    
    public abstract void setActionBarUpIndicator(Drawable paramDrawable, int paramInt);
  }
  
  public static abstract interface DelegateProvider
  {
    public abstract ActionBarDrawerToggle.Delegate getDrawerToggleDelegate();
  }
  
  static final class DrawerArrowDrawableToggle
    extends DrawerArrowDrawable
    implements ActionBarDrawerToggle.DrawerToggle
  {
    private final Activity mActivity;
    
    public DrawerArrowDrawableToggle(Activity paramActivity, Context paramContext)
    {
      super();
      this.mActivity = paramActivity;
    }
    
    public final void setPosition(float paramFloat)
    {
      if (paramFloat == 1.0F) {
        setVerticalMirror(true);
      }
      for (;;)
      {
        setProgress(paramFloat);
        return;
        if (paramFloat == 0.0F) {
          setVerticalMirror(false);
        }
      }
    }
  }
  
  static abstract interface DrawerToggle
  {
    public abstract void setPosition(float paramFloat);
  }
  
  static final class DummyDelegate
    implements ActionBarDrawerToggle.Delegate
  {
    final Activity mActivity;
    
    DummyDelegate(Activity paramActivity)
    {
      this.mActivity = paramActivity;
    }
    
    public final Context getActionBarThemedContext()
    {
      return this.mActivity;
    }
    
    public final Drawable getThemeUpIndicator()
    {
      return null;
    }
    
    public final boolean isNavigationVisible()
    {
      return true;
    }
    
    public final void setActionBarDescription(int paramInt) {}
    
    public final void setActionBarUpIndicator(Drawable paramDrawable, int paramInt) {}
  }
  
  private static final class HoneycombDelegate
    implements ActionBarDrawerToggle.Delegate
  {
    final Activity mActivity;
    ActionBarDrawerToggleHoneycomb.SetIndicatorInfo mSetIndicatorInfo;
    
    private HoneycombDelegate(Activity paramActivity)
    {
      this.mActivity = paramActivity;
    }
    
    public final Context getActionBarThemedContext()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null) {
        return localActionBar.getThemedContext();
      }
      return this.mActivity;
    }
    
    public final Drawable getThemeUpIndicator()
    {
      return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(this.mActivity);
    }
    
    public final boolean isNavigationVisible()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      return (localActionBar != null) && ((0x4 & localActionBar.getDisplayOptions()) != 0);
    }
    
    public final void setActionBarDescription(int paramInt)
    {
      this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, paramInt);
    }
    
    public final void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      this.mActivity.getActionBar().setDisplayShowHomeEnabled(true);
      this.mSetIndicatorInfo = ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator$3bf15dea(this.mActivity, paramDrawable, paramInt);
      this.mActivity.getActionBar().setDisplayShowHomeEnabled(false);
    }
  }
  
  private static final class JellybeanMr2Delegate
    implements ActionBarDrawerToggle.Delegate
  {
    final Activity mActivity;
    
    private JellybeanMr2Delegate(Activity paramActivity)
    {
      this.mActivity = paramActivity;
    }
    
    public final Context getActionBarThemedContext()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null) {
        return localActionBar.getThemedContext();
      }
      return this.mActivity;
    }
    
    public final Drawable getThemeUpIndicator()
    {
      TypedArray localTypedArray = getActionBarThemedContext().obtainStyledAttributes(null, new int[] { 16843531 }, 16843470, 0);
      Drawable localDrawable = localTypedArray.getDrawable(0);
      localTypedArray.recycle();
      return localDrawable;
    }
    
    public final boolean isNavigationVisible()
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      return (localActionBar != null) && ((0x4 & localActionBar.getDisplayOptions()) != 0);
    }
    
    public final void setActionBarDescription(int paramInt)
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null) {
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
    
    public final void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = this.mActivity.getActionBar();
      if (localActionBar != null)
      {
        localActionBar.setHomeAsUpIndicator(paramDrawable);
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.app.ActionBarDrawerToggle
 * JD-Core Version:    0.7.0.1
 */