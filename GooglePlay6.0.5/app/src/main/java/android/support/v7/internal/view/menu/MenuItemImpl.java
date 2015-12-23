package android.support.v7.internal.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider.VisibilityListener;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.internal.widget.TintManager;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug.CapturedViewProperty;
import java.util.ArrayList;

public final class MenuItemImpl
  implements SupportMenuItem
{
  static String sDeleteShortcutLabel;
  static String sEnterShortcutLabel;
  static String sPrependShortcutLabel;
  static String sSpaceShortcutLabel;
  public android.support.v4.view.ActionProvider mActionProvider;
  private View mActionView;
  private final int mCategoryOrder;
  private MenuItem.OnMenuItemClickListener mClickListener;
  private int mFlags = 16;
  private final int mGroup;
  private Drawable mIconDrawable;
  private int mIconResId = 0;
  private final int mId;
  private Intent mIntent;
  private boolean mIsActionViewExpanded = false;
  private Runnable mItemCallback;
  MenuBuilder mMenu;
  ContextMenu.ContextMenuInfo mMenuInfo;
  private MenuItemCompat.OnActionExpandListener mOnActionExpandListener;
  final int mOrdering;
  private char mShortcutAlphabeticChar;
  private char mShortcutNumericChar;
  int mShowAsAction = 0;
  private SubMenuBuilder mSubMenu;
  private CharSequence mTitle;
  private CharSequence mTitleCondensed;
  
  MenuItemImpl(MenuBuilder paramMenuBuilder, int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence, int paramInt5)
  {
    this.mMenu = paramMenuBuilder;
    this.mId = paramInt2;
    this.mGroup = paramInt1;
    this.mCategoryOrder = paramInt3;
    this.mOrdering = paramInt4;
    this.mTitle = paramCharSequence;
    this.mShowAsAction = paramInt5;
  }
  
  private SupportMenuItem setActionView(View paramView)
  {
    this.mActionView = paramView;
    this.mActionProvider = null;
    if ((paramView != null) && (paramView.getId() == -1) && (this.mId > 0)) {
      paramView.setId(this.mId);
    }
    this.mMenu.onItemActionRequestChanged$4587d488();
    return this;
  }
  
  private void setCheckedInt(boolean paramBoolean)
  {
    int i = this.mFlags;
    int j = 0xFFFFFFFD & this.mFlags;
    if (paramBoolean) {}
    for (int k = 2;; k = 0)
    {
      this.mFlags = (k | j);
      if (i != this.mFlags) {
        this.mMenu.onItemsChanged(false);
      }
      return;
    }
  }
  
  public final boolean collapseActionView()
  {
    if ((0x8 & this.mShowAsAction) == 0) {}
    do
    {
      return false;
      if (this.mActionView == null) {
        return true;
      }
    } while ((this.mOnActionExpandListener != null) && (!this.mOnActionExpandListener.onMenuItemActionCollapse(this)));
    return this.mMenu.collapseItemActionView(this);
  }
  
  public final boolean expandActionView()
  {
    if (!hasCollapsibleActionView()) {}
    while ((this.mOnActionExpandListener != null) && (!this.mOnActionExpandListener.onMenuItemActionExpand(this))) {
      return false;
    }
    return this.mMenu.expandItemActionView(this);
  }
  
  public final android.view.ActionProvider getActionProvider()
  {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
  }
  
  public final View getActionView()
  {
    if (this.mActionView != null) {
      return this.mActionView;
    }
    if (this.mActionProvider != null)
    {
      this.mActionView = this.mActionProvider.onCreateActionView(this);
      return this.mActionView;
    }
    return null;
  }
  
  public final char getAlphabeticShortcut()
  {
    return this.mShortcutAlphabeticChar;
  }
  
  public final int getGroupId()
  {
    return this.mGroup;
  }
  
  public final Drawable getIcon()
  {
    if (this.mIconDrawable != null) {
      return this.mIconDrawable;
    }
    if (this.mIconResId != 0)
    {
      Drawable localDrawable = TintManager.getDrawable(this.mMenu.mContext, this.mIconResId);
      this.mIconResId = 0;
      this.mIconDrawable = localDrawable;
      return localDrawable;
    }
    return null;
  }
  
  public final Intent getIntent()
  {
    return this.mIntent;
  }
  
  @ViewDebug.CapturedViewProperty
  public final int getItemId()
  {
    return this.mId;
  }
  
  public final ContextMenu.ContextMenuInfo getMenuInfo()
  {
    return this.mMenuInfo;
  }
  
  public final char getNumericShortcut()
  {
    return this.mShortcutNumericChar;
  }
  
  public final int getOrder()
  {
    return this.mCategoryOrder;
  }
  
  final char getShortcut()
  {
    if (this.mMenu.isQwertyMode()) {
      return this.mShortcutAlphabeticChar;
    }
    return this.mShortcutNumericChar;
  }
  
  public final SubMenu getSubMenu()
  {
    return this.mSubMenu;
  }
  
  public final android.support.v4.view.ActionProvider getSupportActionProvider()
  {
    return this.mActionProvider;
  }
  
  @ViewDebug.CapturedViewProperty
  public final CharSequence getTitle()
  {
    return this.mTitle;
  }
  
  public final CharSequence getTitleCondensed()
  {
    if (this.mTitleCondensed != null) {}
    for (Object localObject = this.mTitleCondensed;; localObject = this.mTitle)
    {
      if ((Build.VERSION.SDK_INT < 18) && (localObject != null) && (!(localObject instanceof String))) {
        localObject = ((CharSequence)localObject).toString();
      }
      return localObject;
    }
  }
  
  final CharSequence getTitleForItemView(MenuView.ItemView paramItemView)
  {
    if ((paramItemView != null) && (paramItemView.prefersCondensedTitle())) {
      return getTitleCondensed();
    }
    return getTitle();
  }
  
  public final boolean hasCollapsibleActionView()
  {
    int i = 0x8 & this.mShowAsAction;
    boolean bool = false;
    if (i != 0)
    {
      if ((this.mActionView == null) && (this.mActionProvider != null)) {
        this.mActionView = this.mActionProvider.onCreateActionView(this);
      }
      View localView = this.mActionView;
      bool = false;
      if (localView != null) {
        bool = true;
      }
    }
    return bool;
  }
  
  public final boolean hasSubMenu()
  {
    return this.mSubMenu != null;
  }
  
  public final boolean invoke()
  {
    if ((this.mClickListener != null) && (this.mClickListener.onMenuItemClick(this))) {}
    do
    {
      do
      {
        return true;
      } while (this.mMenu.dispatchMenuItemSelected(this.mMenu.getRootMenu(), this));
      if (this.mItemCallback != null)
      {
        this.mItemCallback.run();
        return true;
      }
      if (this.mIntent != null) {
        try
        {
          this.mMenu.mContext.startActivity(this.mIntent);
          return true;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", localActivityNotFoundException);
        }
      }
    } while ((this.mActionProvider != null) && (this.mActionProvider.onPerformDefaultAction()));
    return false;
  }
  
  public final boolean isActionButton()
  {
    return (0x20 & this.mFlags) == 32;
  }
  
  public final boolean isActionViewExpanded()
  {
    return this.mIsActionViewExpanded;
  }
  
  public final boolean isCheckable()
  {
    return (0x1 & this.mFlags) == 1;
  }
  
  public final boolean isChecked()
  {
    return (0x2 & this.mFlags) == 2;
  }
  
  public final boolean isEnabled()
  {
    return (0x10 & this.mFlags) != 0;
  }
  
  public final boolean isExclusiveCheckable()
  {
    return (0x4 & this.mFlags) != 0;
  }
  
  public final boolean isVisible()
  {
    if ((this.mActionProvider != null) && (this.mActionProvider.overridesItemVisibility())) {
      if (((0x8 & this.mFlags) != 0) || (!this.mActionProvider.isVisible())) {}
    }
    while ((0x8 & this.mFlags) == 0)
    {
      return true;
      return false;
    }
    return false;
  }
  
  public final boolean requestsActionButton()
  {
    return (0x1 & this.mShowAsAction) == 1;
  }
  
  public final boolean requiresActionButton()
  {
    return (0x2 & this.mShowAsAction) == 2;
  }
  
  public final MenuItem setActionProvider(android.view.ActionProvider paramActionProvider)
  {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
  }
  
  public final void setActionViewExpanded(boolean paramBoolean)
  {
    this.mIsActionViewExpanded = paramBoolean;
    this.mMenu.onItemsChanged(false);
  }
  
  public final MenuItem setAlphabeticShortcut(char paramChar)
  {
    if (this.mShortcutAlphabeticChar == paramChar) {
      return this;
    }
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar);
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public final MenuItem setCheckable(boolean paramBoolean)
  {
    int i = this.mFlags;
    int j = 0xFFFFFFFE & this.mFlags;
    if (paramBoolean) {}
    for (int k = 1;; k = 0)
    {
      this.mFlags = (k | j);
      if (i != this.mFlags) {
        this.mMenu.onItemsChanged(false);
      }
      return this;
    }
  }
  
  public final MenuItem setChecked(boolean paramBoolean)
  {
    if ((0x4 & this.mFlags) != 0)
    {
      MenuBuilder localMenuBuilder = this.mMenu;
      int i = getGroupId();
      int j = localMenuBuilder.mItems.size();
      int k = 0;
      if (k < j)
      {
        MenuItemImpl localMenuItemImpl = (MenuItemImpl)localMenuBuilder.mItems.get(k);
        if ((localMenuItemImpl.getGroupId() == i) && (localMenuItemImpl.isExclusiveCheckable()) && (localMenuItemImpl.isCheckable())) {
          if (localMenuItemImpl != this) {
            break label101;
          }
        }
        label101:
        for (boolean bool = true;; bool = false)
        {
          localMenuItemImpl.setCheckedInt(bool);
          k++;
          break;
        }
      }
    }
    else
    {
      setCheckedInt(paramBoolean);
    }
    return this;
  }
  
  public final MenuItem setEnabled(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (this.mFlags = (0x10 | this.mFlags);; this.mFlags = (0xFFFFFFEF & this.mFlags))
    {
      this.mMenu.onItemsChanged(false);
      return this;
    }
  }
  
  public final void setExclusiveCheckable(boolean paramBoolean)
  {
    int i = 0xFFFFFFFB & this.mFlags;
    if (paramBoolean) {}
    for (int j = 4;; j = 0)
    {
      this.mFlags = (j | i);
      return;
    }
  }
  
  public final MenuItem setIcon(int paramInt)
  {
    this.mIconDrawable = null;
    this.mIconResId = paramInt;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public final MenuItem setIcon(Drawable paramDrawable)
  {
    this.mIconResId = 0;
    this.mIconDrawable = paramDrawable;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public final MenuItem setIntent(Intent paramIntent)
  {
    this.mIntent = paramIntent;
    return this;
  }
  
  public final void setIsActionButton(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mFlags = (0x20 | this.mFlags);
      return;
    }
    this.mFlags = (0xFFFFFFDF & this.mFlags);
  }
  
  public final MenuItem setNumericShortcut(char paramChar)
  {
    if (this.mShortcutNumericChar == paramChar) {
      return this;
    }
    this.mShortcutNumericChar = paramChar;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener)
  {
    throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setOnActionExpandListener()");
  }
  
  public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mClickListener = paramOnMenuItemClickListener;
    return this;
  }
  
  public final MenuItem setShortcut(char paramChar1, char paramChar2)
  {
    this.mShortcutNumericChar = paramChar1;
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar2);
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public final void setShowAsAction(int paramInt)
  {
    switch (paramInt & 0x3)
    {
    default: 
      throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
    }
    this.mShowAsAction = paramInt;
    this.mMenu.onItemActionRequestChanged$4587d488();
  }
  
  public final void setSubMenu(SubMenuBuilder paramSubMenuBuilder)
  {
    this.mSubMenu = paramSubMenuBuilder;
    paramSubMenuBuilder.setHeaderTitle(getTitle());
  }
  
  public final SupportMenuItem setSupportActionProvider(android.support.v4.view.ActionProvider paramActionProvider)
  {
    if (this.mActionProvider != null)
    {
      android.support.v4.view.ActionProvider localActionProvider = this.mActionProvider;
      localActionProvider.mVisibilityListener = null;
      localActionProvider.mSubUiVisibilityListener = null;
    }
    this.mActionView = null;
    this.mActionProvider = paramActionProvider;
    this.mMenu.onItemsChanged(true);
    if (this.mActionProvider != null) {
      this.mActionProvider.setVisibilityListener(new ActionProvider.VisibilityListener()
      {
        public final void onActionProviderVisibilityChanged$1385ff()
        {
          MenuItemImpl.this.mMenu.onItemVisibleChanged$4587d488();
        }
      });
    }
    return this;
  }
  
  public final SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener paramOnActionExpandListener)
  {
    this.mOnActionExpandListener = paramOnActionExpandListener;
    return this;
  }
  
  public final MenuItem setTitle(int paramInt)
  {
    return setTitle(this.mMenu.mContext.getString(paramInt));
  }
  
  public final MenuItem setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    if (this.mSubMenu != null) {
      this.mSubMenu.setHeaderTitle(paramCharSequence);
    }
    return this;
  }
  
  public final MenuItem setTitleCondensed(CharSequence paramCharSequence)
  {
    this.mTitleCondensed = paramCharSequence;
    this.mMenu.onItemsChanged(false);
    return this;
  }
  
  public final MenuItem setVisible(boolean paramBoolean)
  {
    if (setVisibleInt(paramBoolean)) {
      this.mMenu.onItemVisibleChanged$4587d488();
    }
    return this;
  }
  
  final boolean setVisibleInt(boolean paramBoolean)
  {
    int i = this.mFlags;
    int j = 0xFFFFFFF7 & this.mFlags;
    if (paramBoolean) {}
    for (int k = 0;; k = 8)
    {
      this.mFlags = (k | j);
      int m = this.mFlags;
      boolean bool = false;
      if (i != m) {
        bool = true;
      }
      return bool;
    }
  }
  
  final boolean shouldShowShortcut()
  {
    return (this.mMenu.isShortcutsVisible()) && (getShortcut() != 0);
  }
  
  public final String toString()
  {
    if (this.mTitle != null) {
      return this.mTitle.toString();
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuItemImpl
 * JD-Core Version:    0.7.0.1
 */