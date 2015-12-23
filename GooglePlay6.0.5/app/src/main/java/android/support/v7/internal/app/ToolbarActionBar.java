package android.support.v7.internal.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnMenuVisibilityListener;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.layout;
import android.support.v7.appcompat.R.style;
import android.support.v7.internal.view.WindowCallbackWrapper;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.widget.DecorToolbar;
import android.support.v7.internal.widget.ToolbarWidgetWrapper;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window.Callback;
import android.widget.ListAdapter;
import java.util.ArrayList;

public final class ToolbarActionBar
  extends ActionBar
{
  DecorToolbar mDecorToolbar;
  private boolean mLastMenuVisibility;
  ListMenuPresenter mListMenuPresenter;
  private boolean mMenuCallbackSet;
  private final Toolbar.OnMenuItemClickListener mMenuClicker = new Toolbar.OnMenuItemClickListener()
  {
    public final boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
    {
      return ToolbarActionBar.this.mWindowCallback.onMenuItemSelected(0, paramAnonymousMenuItem);
    }
  };
  private final Runnable mMenuInvalidator = new Runnable()
  {
    public final void run()
    {
      ToolbarActionBar localToolbarActionBar = ToolbarActionBar.this;
      Menu localMenu = localToolbarActionBar.getMenu();
      if ((localMenu instanceof MenuBuilder)) {}
      for (localMenuBuilder = (MenuBuilder)localMenu;; localMenuBuilder = null)
      {
        if (localMenuBuilder != null) {
          localMenuBuilder.stopDispatchingItemsChanged();
        }
        try
        {
          localMenu.clear();
          if ((!localToolbarActionBar.mWindowCallback.onCreatePanelMenu(0, localMenu)) || (!localToolbarActionBar.mWindowCallback.onPreparePanel(0, null, localMenu))) {
            localMenu.clear();
          }
          return;
        }
        finally
        {
          if (localMenuBuilder == null) {
            break;
          }
          localMenuBuilder.startDispatchingItemsChanged();
        }
      }
    }
  };
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
  boolean mToolbarMenuPrepared;
  public Window.Callback mWindowCallback;
  
  public ToolbarActionBar(Toolbar paramToolbar, CharSequence paramCharSequence, Window.Callback paramCallback)
  {
    this.mDecorToolbar = new ToolbarWidgetWrapper(paramToolbar, false);
    this.mWindowCallback = new ToolbarCallbackWrapper(paramCallback);
    this.mDecorToolbar.setWindowCallback(this.mWindowCallback);
    paramToolbar.setOnMenuItemClickListener(this.mMenuClicker);
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public final void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener)
  {
    this.mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }
  
  public final boolean collapseActionView()
  {
    if (this.mDecorToolbar.hasExpandedActionView())
    {
      this.mDecorToolbar.collapseActionView();
      return true;
    }
    return false;
  }
  
  public final void dispatchMenuVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean == this.mLastMenuVisibility) {}
    for (;;)
    {
      return;
      this.mLastMenuVisibility = paramBoolean;
      int i = this.mMenuVisibilityListeners.size();
      for (int j = 0; j < i; j++) {
        ((ActionBar.OnMenuVisibilityListener)this.mMenuVisibilityListeners.get(j)).onMenuVisibilityChanged(paramBoolean);
      }
    }
  }
  
  public final int getDisplayOptions()
  {
    return this.mDecorToolbar.getDisplayOptions();
  }
  
  public final int getHeight()
  {
    return this.mDecorToolbar.getHeight();
  }
  
  final Menu getMenu()
  {
    if (!this.mMenuCallbackSet)
    {
      this.mDecorToolbar.setMenuCallbacks(new ActionMenuPresenterCallback((byte)0), new MenuBuilderCallback((byte)0));
      this.mMenuCallbackSet = true;
    }
    return this.mDecorToolbar.getMenu();
  }
  
  public final Context getThemedContext()
  {
    return this.mDecorToolbar.getContext();
  }
  
  public final void hide()
  {
    this.mDecorToolbar.setVisibility(8);
  }
  
  public final boolean invalidateOptionsMenu()
  {
    this.mDecorToolbar.getViewGroup().removeCallbacks(this.mMenuInvalidator);
    ViewCompat.postOnAnimation(this.mDecorToolbar.getViewGroup(), this.mMenuInvalidator);
    return true;
  }
  
  public final void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  public final boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Menu localMenu = getMenu();
    int i;
    if (localMenu != null)
    {
      if (paramKeyEvent == null) {
        break label54;
      }
      i = paramKeyEvent.getDeviceId();
      if (KeyCharacterMap.load(i).getKeyboardType() == 1) {
        break label60;
      }
    }
    label54:
    label60:
    for (boolean bool = true;; bool = false)
    {
      localMenu.setQwertyMode(bool);
      localMenu.performShortcut(paramInt, paramKeyEvent, 0);
      return true;
      i = -1;
      break;
    }
  }
  
  public final void setBackgroundDrawable(Drawable paramDrawable)
  {
    this.mDecorToolbar.setBackgroundDrawable(paramDrawable);
  }
  
  public final void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean) {}
  
  public final void setDisplayHomeAsUpEnabled(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 4;; i = 0)
    {
      setDisplayOptions(i, 4);
      return;
    }
  }
  
  public final void setDisplayOptions(int paramInt1, int paramInt2)
  {
    int i = this.mDecorToolbar.getDisplayOptions();
    this.mDecorToolbar.setDisplayOptions(paramInt1 & paramInt2 | i & (paramInt2 ^ 0xFFFFFFFF));
  }
  
  public final void setHomeActionContentDescription(int paramInt)
  {
    this.mDecorToolbar.setNavigationContentDescription(paramInt);
  }
  
  public final void setHomeAsUpIndicator(int paramInt)
  {
    this.mDecorToolbar.setNavigationIcon(paramInt);
  }
  
  public final void setHomeAsUpIndicator(Drawable paramDrawable)
  {
    this.mDecorToolbar.setNavigationIcon(paramDrawable);
  }
  
  public final void setLogo(Drawable paramDrawable)
  {
    this.mDecorToolbar.setLogo(paramDrawable);
  }
  
  public final void setShowHideAnimationEnabled(boolean paramBoolean) {}
  
  public final void setSubtitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setSubtitle(paramCharSequence);
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setTitle(paramCharSequence);
  }
  
  public final void setWindowTitle(CharSequence paramCharSequence)
  {
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public final void show()
  {
    this.mDecorToolbar.setVisibility(0);
  }
  
  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private boolean mClosingActionMenu;
    
    private ActionMenuPresenterCallback() {}
    
    public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      if (this.mClosingActionMenu) {
        return;
      }
      this.mClosingActionMenu = true;
      ToolbarActionBar.this.mDecorToolbar.dismissPopupMenus();
      if (ToolbarActionBar.this.mWindowCallback != null) {
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, paramMenuBuilder);
      }
      this.mClosingActionMenu = false;
    }
    
    public final boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if (ToolbarActionBar.this.mWindowCallback != null)
      {
        ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, paramMenuBuilder);
        return true;
      }
      return false;
    }
  }
  
  private final class MenuBuilderCallback
    implements MenuBuilder.Callback
  {
    private MenuBuilderCallback() {}
    
    public final boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      return false;
    }
    
    public final void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (ToolbarActionBar.this.mWindowCallback != null)
      {
        if (!ToolbarActionBar.this.mDecorToolbar.isOverflowMenuShowing()) {
          break label41;
        }
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(108, paramMenuBuilder);
      }
      label41:
      while (!ToolbarActionBar.this.mWindowCallback.onPreparePanel(0, null, paramMenuBuilder)) {
        return;
      }
      ToolbarActionBar.this.mWindowCallback.onMenuOpened(108, paramMenuBuilder);
    }
  }
  
  private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private PanelMenuPresenterCallback() {}
    
    public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      if (ToolbarActionBar.this.mWindowCallback != null) {
        ToolbarActionBar.this.mWindowCallback.onPanelClosed(0, paramMenuBuilder);
      }
    }
    
    public final boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if ((paramMenuBuilder == null) && (ToolbarActionBar.this.mWindowCallback != null)) {
        ToolbarActionBar.this.mWindowCallback.onMenuOpened(0, paramMenuBuilder);
      }
      return true;
    }
  }
  
  private final class ToolbarCallbackWrapper
    extends WindowCallbackWrapper
  {
    public ToolbarCallbackWrapper(Window.Callback paramCallback)
    {
      super();
    }
    
    public final View onCreatePanelView(int paramInt)
    {
      switch (paramInt)
      {
      }
      Menu localMenu;
      do
      {
        return super.onCreatePanelView(paramInt);
        localMenu = ToolbarActionBar.this.mDecorToolbar.getMenu();
      } while ((!onPreparePanel(paramInt, null, localMenu)) || (!onMenuOpened(paramInt, localMenu)));
      ToolbarActionBar localToolbarActionBar = ToolbarActionBar.this;
      MenuBuilder localMenuBuilder;
      Context localContext;
      Resources.Theme localTheme;
      if ((localToolbarActionBar.mListMenuPresenter == null) && ((localMenu instanceof MenuBuilder)))
      {
        localMenuBuilder = (MenuBuilder)localMenu;
        localContext = localToolbarActionBar.mDecorToolbar.getContext();
        TypedValue localTypedValue = new TypedValue();
        localTheme = localContext.getResources().newTheme();
        localTheme.setTo(localContext.getTheme());
        localTheme.resolveAttribute(R.attr.actionBarPopupTheme, localTypedValue, true);
        if (localTypedValue.resourceId != 0) {
          localTheme.applyStyle(localTypedValue.resourceId, true);
        }
        localTheme.resolveAttribute(R.attr.panelMenuListTheme, localTypedValue, true);
        if (localTypedValue.resourceId == 0) {
          break label261;
        }
        localTheme.applyStyle(localTypedValue.resourceId, true);
      }
      for (;;)
      {
        ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(localContext, 0);
        localContextThemeWrapper.getTheme().setTo(localTheme);
        localToolbarActionBar.mListMenuPresenter = new ListMenuPresenter(localContextThemeWrapper, R.layout.abc_list_menu_item_layout);
        localToolbarActionBar.mListMenuPresenter.mCallback = new ToolbarActionBar.PanelMenuPresenterCallback(localToolbarActionBar, (byte)0);
        localMenuBuilder.addMenuPresenter(localToolbarActionBar.mListMenuPresenter);
        if ((localMenu != null) && (localToolbarActionBar.mListMenuPresenter != null)) {
          break;
        }
        return null;
        label261:
        localTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      }
      if (localToolbarActionBar.mListMenuPresenter.getAdapter().getCount() > 0) {
        return (View)localToolbarActionBar.mListMenuPresenter.getMenuView(localToolbarActionBar.mDecorToolbar.getViewGroup());
      }
      return null;
    }
    
    public final boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      boolean bool = super.onPreparePanel(paramInt, paramView, paramMenu);
      if ((bool) && (!ToolbarActionBar.this.mToolbarMenuPrepared))
      {
        ToolbarActionBar.this.mDecorToolbar.setMenuPrepared();
        ToolbarActionBar.this.mToolbarMenuPrepared = true;
      }
      return bool;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.app.ToolbarActionBar
 * JD-Core Version:    0.7.0.1
 */