package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.drawable;
import android.support.v7.appcompat.R.id;
import android.support.v7.appcompat.R.string;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.internal.view.menu.ActionMenuItem;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.ExpandedActionViewMenuPresenter;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window.Callback;
import android.widget.TextView;

public final class ToolbarWidgetWrapper
  implements DecorToolbar
{
  private ActionMenuPresenter mActionMenuPresenter;
  private View mCustomView;
  private int mDefaultNavigationContentDescription = 0;
  private Drawable mDefaultNavigationIcon;
  private int mDisplayOpts;
  private CharSequence mHomeDescription;
  private Drawable mIcon;
  private Drawable mLogo;
  boolean mMenuPrepared;
  private Drawable mNavIcon;
  private int mNavigationMode = 0;
  private CharSequence mSubtitle;
  private View mTabView;
  private final TintManager mTintManager;
  CharSequence mTitle;
  private boolean mTitleSet;
  Toolbar mToolbar;
  Window.Callback mWindowCallback;
  
  public ToolbarWidgetWrapper(Toolbar paramToolbar, boolean paramBoolean)
  {
    this(paramToolbar, paramBoolean, R.string.abc_action_bar_up_description, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
  }
  
  private ToolbarWidgetWrapper(Toolbar paramToolbar, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    this.mToolbar = paramToolbar;
    this.mTitle = paramToolbar.getTitle();
    this.mSubtitle = paramToolbar.getSubtitle();
    boolean bool;
    TintTypedArray localTintTypedArray;
    if (this.mTitle != null)
    {
      bool = true;
      this.mTitleSet = bool;
      this.mNavIcon = paramToolbar.getNavigationIcon();
      if (!paramBoolean) {
        break label690;
      }
      localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(paramToolbar.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle);
      CharSequence localCharSequence1 = localTintTypedArray.getText(R.styleable.ActionBar_title);
      if (!TextUtils.isEmpty(localCharSequence1)) {
        setTitle(localCharSequence1);
      }
      CharSequence localCharSequence2 = localTintTypedArray.getText(R.styleable.ActionBar_subtitle);
      if (!TextUtils.isEmpty(localCharSequence2)) {
        setSubtitle(localCharSequence2);
      }
      Drawable localDrawable2 = localTintTypedArray.getDrawable(R.styleable.ActionBar_logo);
      if (localDrawable2 != null) {
        setLogo(localDrawable2);
      }
      Drawable localDrawable3 = localTintTypedArray.getDrawable(R.styleable.ActionBar_icon);
      if ((this.mNavIcon == null) && (localDrawable3 != null)) {
        setIcon(localDrawable3);
      }
      Drawable localDrawable4 = localTintTypedArray.getDrawable(R.styleable.ActionBar_homeAsUpIndicator);
      if (localDrawable4 != null) {
        setNavigationIcon(localDrawable4);
      }
      setDisplayOptions(localTintTypedArray.getInt(R.styleable.ActionBar_displayOptions, 0));
      int j = localTintTypedArray.getResourceId(R.styleable.ActionBar_customNavigationLayout, 0);
      if (j != 0)
      {
        View localView = LayoutInflater.from(this.mToolbar.getContext()).inflate(j, this.mToolbar, false);
        if ((this.mCustomView != null) && ((0x10 & this.mDisplayOpts) != 0)) {
          this.mToolbar.removeView(this.mCustomView);
        }
        this.mCustomView = localView;
        if ((localView != null) && ((0x10 & this.mDisplayOpts) != 0)) {
          this.mToolbar.addView(this.mCustomView);
        }
        setDisplayOptions(0x10 | this.mDisplayOpts);
      }
      int k = localTintTypedArray.getLayoutDimension(R.styleable.ActionBar_height, 0);
      if (k > 0)
      {
        ViewGroup.LayoutParams localLayoutParams = this.mToolbar.getLayoutParams();
        localLayoutParams.height = k;
        this.mToolbar.setLayoutParams(localLayoutParams);
      }
      int m = localTintTypedArray.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetStart, -1);
      int n = localTintTypedArray.getDimensionPixelOffset(R.styleable.ActionBar_contentInsetEnd, -1);
      if ((m >= 0) || (n >= 0))
      {
        Toolbar localToolbar2 = this.mToolbar;
        int i1 = Math.max(m, 0);
        int i2 = Math.max(n, 0);
        localToolbar2.mContentInsets.setRelative(i1, i2);
      }
      int i3 = localTintTypedArray.getResourceId(R.styleable.ActionBar_titleTextStyle, 0);
      if (i3 != 0)
      {
        Toolbar localToolbar4 = this.mToolbar;
        Context localContext2 = this.mToolbar.getContext();
        localToolbar4.mTitleTextAppearance = i3;
        if (localToolbar4.mTitleTextView != null) {
          localToolbar4.mTitleTextView.setTextAppearance(localContext2, i3);
        }
      }
      int i4 = localTintTypedArray.getResourceId(R.styleable.ActionBar_subtitleTextStyle, 0);
      if (i4 != 0)
      {
        Toolbar localToolbar3 = this.mToolbar;
        Context localContext1 = this.mToolbar.getContext();
        localToolbar3.mSubtitleTextAppearance = i4;
        if (localToolbar3.mSubtitleTextView != null) {
          localToolbar3.mSubtitleTextView.setTextAppearance(localContext1, i4);
        }
      }
      int i5 = localTintTypedArray.getResourceId(R.styleable.ActionBar_popupTheme, 0);
      if (i5 != 0) {
        this.mToolbar.setPopupTheme(i5);
      }
      localTintTypedArray.mWrapped.recycle();
    }
    for (this.mTintManager = localTintTypedArray.getTintManager();; this.mTintManager = TintManager.get(paramToolbar.getContext()))
    {
      if (paramInt1 != this.mDefaultNavigationContentDescription)
      {
        this.mDefaultNavigationContentDescription = paramInt1;
        if (TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
          setNavigationContentDescription(this.mDefaultNavigationContentDescription);
        }
      }
      this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
      Drawable localDrawable1 = this.mTintManager.getDrawable(paramInt2, false);
      if (this.mDefaultNavigationIcon != localDrawable1)
      {
        this.mDefaultNavigationIcon = localDrawable1;
        updateNavigationIcon();
      }
      Toolbar localToolbar1 = this.mToolbar;
      View.OnClickListener local1 = new View.OnClickListener()
      {
        final ActionMenuItem mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), ToolbarWidgetWrapper.this.mTitle);
        
        public final void onClick(View paramAnonymousView)
        {
          if ((ToolbarWidgetWrapper.this.mWindowCallback != null) && (ToolbarWidgetWrapper.this.mMenuPrepared)) {
            ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, this.mNavItem);
          }
        }
      };
      localToolbar1.setNavigationOnClickListener(local1);
      return;
      bool = false;
      break;
      label690:
      int i = 11;
      if (this.mToolbar.getNavigationIcon() != null) {
        i = 15;
      }
      this.mDisplayOpts = i;
    }
  }
  
  private void setTitleInt(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    if ((0x8 & this.mDisplayOpts) != 0) {
      this.mToolbar.setTitle(paramCharSequence);
    }
  }
  
  private void updateHomeAccessibility()
  {
    if ((0x4 & this.mDisplayOpts) != 0)
    {
      if (TextUtils.isEmpty(this.mHomeDescription)) {
        this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
      }
    }
    else {
      return;
    }
    this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
  }
  
  private void updateNavigationIcon()
  {
    Toolbar localToolbar;
    if ((0x4 & this.mDisplayOpts) != 0)
    {
      localToolbar = this.mToolbar;
      if (this.mNavIcon == null) {
        break label32;
      }
    }
    label32:
    for (Drawable localDrawable = this.mNavIcon;; localDrawable = this.mDefaultNavigationIcon)
    {
      localToolbar.setNavigationIcon(localDrawable);
      return;
    }
  }
  
  private void updateToolbarLogo()
  {
    int i = 0x2 & this.mDisplayOpts;
    Drawable localDrawable = null;
    if (i != 0)
    {
      if ((0x1 & this.mDisplayOpts) == 0) {
        break label51;
      }
      if (this.mLogo == null) {
        break label43;
      }
      localDrawable = this.mLogo;
    }
    for (;;)
    {
      this.mToolbar.setLogo(localDrawable);
      return;
      label43:
      localDrawable = this.mIcon;
      continue;
      label51:
      localDrawable = this.mIcon;
    }
  }
  
  public final boolean canShowOverflowMenu()
  {
    Toolbar localToolbar = this.mToolbar;
    return (localToolbar.getVisibility() == 0) && (localToolbar.mMenuView != null) && (localToolbar.mMenuView.mReserveOverflow);
  }
  
  public final void collapseActionView()
  {
    this.mToolbar.collapseActionView();
  }
  
  public final void dismissPopupMenus()
  {
    Toolbar localToolbar = this.mToolbar;
    if (localToolbar.mMenuView != null) {
      localToolbar.mMenuView.dismissPopupMenus();
    }
  }
  
  public final Context getContext()
  {
    return this.mToolbar.getContext();
  }
  
  public final int getDisplayOptions()
  {
    return this.mDisplayOpts;
  }
  
  public final int getHeight()
  {
    return this.mToolbar.getHeight();
  }
  
  public final Menu getMenu()
  {
    return this.mToolbar.getMenu();
  }
  
  public final int getNavigationMode()
  {
    return this.mNavigationMode;
  }
  
  public final CharSequence getTitle()
  {
    return this.mToolbar.getTitle();
  }
  
  public final ViewGroup getViewGroup()
  {
    return this.mToolbar;
  }
  
  public final boolean hasExpandedActionView()
  {
    return this.mToolbar.hasExpandedActionView();
  }
  
  public final boolean hideOverflowMenu()
  {
    Toolbar localToolbar = this.mToolbar;
    if (localToolbar.mMenuView != null)
    {
      ActionMenuView localActionMenuView = localToolbar.mMenuView;
      if ((localActionMenuView.mPresenter != null) && (localActionMenuView.mPresenter.hideOverflowMenu())) {}
      for (int i = 1; i != 0; i = 0) {
        return true;
      }
    }
    return false;
  }
  
  public final void initIndeterminateProgress()
  {
    Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
  }
  
  public final void initProgress()
  {
    Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
  }
  
  public final boolean isOverflowMenuShowPending()
  {
    Toolbar localToolbar = this.mToolbar;
    if (localToolbar.mMenuView != null)
    {
      ActionMenuView localActionMenuView = localToolbar.mMenuView;
      int j;
      if (localActionMenuView.mPresenter != null)
      {
        ActionMenuPresenter localActionMenuPresenter = localActionMenuView.mPresenter;
        if ((localActionMenuPresenter.mPostedOpenRunnable != null) || (localActionMenuPresenter.isOverflowMenuShowing()))
        {
          j = 1;
          if (j == 0) {
            break label68;
          }
        }
      }
      label68:
      for (int i = 1;; i = 0)
      {
        if (i == 0) {
          break label73;
        }
        return true;
        j = 0;
        break;
      }
    }
    label73:
    return false;
  }
  
  public final boolean isOverflowMenuShowing()
  {
    return this.mToolbar.isOverflowMenuShowing();
  }
  
  public final void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mToolbar.setBackgroundDrawable(paramDrawable);
  }
  
  public final void setCollapsible(boolean paramBoolean)
  {
    this.mToolbar.setCollapsible(paramBoolean);
  }
  
  public final void setDisplayOptions(int paramInt)
  {
    int i = paramInt ^ this.mDisplayOpts;
    this.mDisplayOpts = paramInt;
    if (i != 0)
    {
      if ((i & 0x4) != 0)
      {
        if ((paramInt & 0x4) == 0) {
          break label115;
        }
        updateNavigationIcon();
        updateHomeAccessibility();
      }
      if ((i & 0x3) != 0) {
        updateToolbarLogo();
      }
      if ((i & 0x8) != 0)
      {
        if ((paramInt & 0x8) == 0) {
          break label126;
        }
        this.mToolbar.setTitle(this.mTitle);
        this.mToolbar.setSubtitle(this.mSubtitle);
      }
    }
    for (;;)
    {
      if (((i & 0x10) != 0) && (this.mCustomView != null))
      {
        if ((paramInt & 0x10) == 0) {
          break label145;
        }
        this.mToolbar.addView(this.mCustomView);
      }
      return;
      label115:
      this.mToolbar.setNavigationIcon(null);
      break;
      label126:
      this.mToolbar.setTitle(null);
      this.mToolbar.setSubtitle(null);
    }
    label145:
    this.mToolbar.removeView(this.mCustomView);
  }
  
  public final void setEmbeddedTabView(ScrollingTabContainerView paramScrollingTabContainerView)
  {
    if ((this.mTabView != null) && (this.mTabView.getParent() == this.mToolbar)) {
      this.mToolbar.removeView(this.mTabView);
    }
    this.mTabView = paramScrollingTabContainerView;
    if ((paramScrollingTabContainerView != null) && (this.mNavigationMode == 2))
    {
      this.mToolbar.addView(this.mTabView, 0);
      Toolbar.LayoutParams localLayoutParams = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
      localLayoutParams.width = -2;
      localLayoutParams.height = -2;
      localLayoutParams.gravity = 8388691;
      paramScrollingTabContainerView.setAllowCollapse(true);
    }
  }
  
  public final void setIcon(int paramInt)
  {
    if (paramInt != 0) {}
    for (Drawable localDrawable = this.mTintManager.getDrawable(paramInt, false);; localDrawable = null)
    {
      setIcon(localDrawable);
      return;
    }
  }
  
  public final void setIcon(Drawable paramDrawable)
  {
    this.mIcon = paramDrawable;
    updateToolbarLogo();
  }
  
  public final void setLogo(int paramInt)
  {
    if (paramInt != 0) {}
    for (Drawable localDrawable = this.mTintManager.getDrawable(paramInt, false);; localDrawable = null)
    {
      setLogo(localDrawable);
      return;
    }
  }
  
  public final void setLogo(Drawable paramDrawable)
  {
    this.mLogo = paramDrawable;
    updateToolbarLogo();
  }
  
  public final void setMenu(Menu paramMenu, MenuPresenter.Callback paramCallback)
  {
    if (this.mActionMenuPresenter == null)
    {
      this.mActionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
      this.mActionMenuPresenter.mId = R.id.action_menu_presenter;
    }
    this.mActionMenuPresenter.mCallback = paramCallback;
    Toolbar localToolbar = this.mToolbar;
    MenuBuilder localMenuBuilder1 = (MenuBuilder)paramMenu;
    ActionMenuPresenter localActionMenuPresenter = this.mActionMenuPresenter;
    if ((localMenuBuilder1 != null) || (localToolbar.mMenuView != null))
    {
      localToolbar.ensureMenuView();
      MenuBuilder localMenuBuilder2 = localToolbar.mMenuView.mMenu;
      if (localMenuBuilder2 != localMenuBuilder1)
      {
        if (localMenuBuilder2 != null)
        {
          localMenuBuilder2.removeMenuPresenter(localToolbar.mOuterActionMenuPresenter);
          localMenuBuilder2.removeMenuPresenter(localToolbar.mExpandedMenuPresenter);
        }
        if (localToolbar.mExpandedMenuPresenter == null) {
          localToolbar.mExpandedMenuPresenter = new Toolbar.ExpandedActionViewMenuPresenter(localToolbar, (byte)0);
        }
        localActionMenuPresenter.mExpandedActionViewsExclusive = true;
        if (localMenuBuilder1 == null) {
          break label197;
        }
        localMenuBuilder1.addMenuPresenter(localActionMenuPresenter, localToolbar.mPopupContext);
        localMenuBuilder1.addMenuPresenter(localToolbar.mExpandedMenuPresenter, localToolbar.mPopupContext);
      }
    }
    for (;;)
    {
      localToolbar.mMenuView.setPopupTheme(localToolbar.mPopupTheme);
      localToolbar.mMenuView.setPresenter(localActionMenuPresenter);
      localToolbar.mOuterActionMenuPresenter = localActionMenuPresenter;
      return;
      label197:
      localActionMenuPresenter.initForMenu(localToolbar.mPopupContext, null);
      localToolbar.mExpandedMenuPresenter.initForMenu(localToolbar.mPopupContext, null);
      localActionMenuPresenter.updateMenuView(true);
      localToolbar.mExpandedMenuPresenter.updateMenuView(true);
    }
  }
  
  public final void setMenuCallbacks(MenuPresenter.Callback paramCallback, MenuBuilder.Callback paramCallback1)
  {
    Toolbar localToolbar = this.mToolbar;
    localToolbar.mActionMenuPresenterCallback = paramCallback;
    localToolbar.mMenuBuilderCallback = paramCallback1;
  }
  
  public final void setMenuPrepared()
  {
    this.mMenuPrepared = true;
  }
  
  public final void setNavigationContentDescription(int paramInt)
  {
    if (paramInt == 0) {}
    for (String str = null;; str = this.mToolbar.getContext().getString(paramInt))
    {
      this.mHomeDescription = str;
      updateHomeAccessibility();
      return;
    }
  }
  
  public final void setNavigationIcon(int paramInt)
  {
    if (paramInt != 0) {}
    for (Drawable localDrawable = this.mTintManager.getDrawable(paramInt, false);; localDrawable = null)
    {
      setNavigationIcon(localDrawable);
      return;
    }
  }
  
  public final void setNavigationIcon(Drawable paramDrawable)
  {
    this.mNavIcon = paramDrawable;
    updateNavigationIcon();
  }
  
  public final void setSubtitle(CharSequence paramCharSequence)
  {
    this.mSubtitle = paramCharSequence;
    if ((0x8 & this.mDisplayOpts) != 0) {
      this.mToolbar.setSubtitle(paramCharSequence);
    }
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mTitleSet = true;
    setTitleInt(paramCharSequence);
  }
  
  public final void setVisibility(int paramInt)
  {
    this.mToolbar.setVisibility(paramInt);
  }
  
  public final void setWindowCallback(Window.Callback paramCallback)
  {
    this.mWindowCallback = paramCallback;
  }
  
  public final void setWindowTitle(CharSequence paramCharSequence)
  {
    if (!this.mTitleSet) {
      setTitleInt(paramCharSequence);
    }
  }
  
  public final ViewPropertyAnimatorCompat setupAnimatorToVisibility(final int paramInt, long paramLong)
  {
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = ViewCompat.animate(this.mToolbar);
    if (paramInt == 0) {}
    for (float f = 1.0F;; f = 0.0F) {
      localViewPropertyAnimatorCompat.alpha(f).setDuration(paramLong).setListener(new ViewPropertyAnimatorListenerAdapter()
      {
        private boolean mCanceled = false;
        
        public final void onAnimationCancel(View paramAnonymousView)
        {
          this.mCanceled = true;
        }
        
        public final void onAnimationEnd(View paramAnonymousView)
        {
          if (!this.mCanceled) {
            ToolbarWidgetWrapper.this.mToolbar.setVisibility(paramInt);
          }
        }
        
        public final void onAnimationStart(View paramAnonymousView)
        {
          ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
        }
      });
    }
  }
  
  public final boolean showOverflowMenu()
  {
    return this.mToolbar.showOverflowMenu();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.widget.ToolbarWidgetWrapper
 * JD-Core Version:    0.7.0.1
 */