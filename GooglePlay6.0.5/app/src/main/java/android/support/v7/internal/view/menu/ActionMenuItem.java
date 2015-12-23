package android.support.v7.internal.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

public final class ActionMenuItem
  implements SupportMenuItem
{
  private final int mCategoryOrder;
  private MenuItem.OnMenuItemClickListener mClickListener;
  private Context mContext;
  private int mFlags = 16;
  private final int mGroup;
  private Drawable mIconDrawable;
  private int mIconResId = 0;
  private final int mId;
  private Intent mIntent;
  private final int mOrdering;
  private char mShortcutAlphabeticChar;
  private char mShortcutNumericChar;
  private CharSequence mTitle;
  private CharSequence mTitleCondensed;
  
  public ActionMenuItem(Context paramContext, CharSequence paramCharSequence)
  {
    this.mContext = paramContext;
    this.mId = 16908332;
    this.mGroup = 0;
    this.mCategoryOrder = 0;
    this.mOrdering = 0;
    this.mTitle = paramCharSequence;
  }
  
  public final boolean collapseActionView()
  {
    return false;
  }
  
  public final boolean expandActionView()
  {
    return false;
  }
  
  public final android.view.ActionProvider getActionProvider()
  {
    throw new UnsupportedOperationException();
  }
  
  public final View getActionView()
  {
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
    return this.mIconDrawable;
  }
  
  public final Intent getIntent()
  {
    return this.mIntent;
  }
  
  public final int getItemId()
  {
    return this.mId;
  }
  
  public final ContextMenu.ContextMenuInfo getMenuInfo()
  {
    return null;
  }
  
  public final char getNumericShortcut()
  {
    return this.mShortcutNumericChar;
  }
  
  public final int getOrder()
  {
    return this.mOrdering;
  }
  
  public final SubMenu getSubMenu()
  {
    return null;
  }
  
  public final android.support.v4.view.ActionProvider getSupportActionProvider()
  {
    return null;
  }
  
  public final CharSequence getTitle()
  {
    return this.mTitle;
  }
  
  public final CharSequence getTitleCondensed()
  {
    if (this.mTitleCondensed != null) {
      return this.mTitleCondensed;
    }
    return this.mTitle;
  }
  
  public final boolean hasSubMenu()
  {
    return false;
  }
  
  public final boolean isActionViewExpanded()
  {
    return false;
  }
  
  public final boolean isCheckable()
  {
    return (0x1 & this.mFlags) != 0;
  }
  
  public final boolean isChecked()
  {
    return (0x2 & this.mFlags) != 0;
  }
  
  public final boolean isEnabled()
  {
    return (0x10 & this.mFlags) != 0;
  }
  
  public final boolean isVisible()
  {
    return (0x8 & this.mFlags) == 0;
  }
  
  public final MenuItem setActionProvider(android.view.ActionProvider paramActionProvider)
  {
    throw new UnsupportedOperationException();
  }
  
  public final MenuItem setAlphabeticShortcut(char paramChar)
  {
    this.mShortcutAlphabeticChar = paramChar;
    return this;
  }
  
  public final MenuItem setCheckable(boolean paramBoolean)
  {
    int i = 0xFFFFFFFE & this.mFlags;
    if (paramBoolean) {}
    for (int j = 1;; j = 0)
    {
      this.mFlags = (j | i);
      return this;
    }
  }
  
  public final MenuItem setChecked(boolean paramBoolean)
  {
    int i = 0xFFFFFFFD & this.mFlags;
    if (paramBoolean) {}
    for (int j = 2;; j = 0)
    {
      this.mFlags = (j | i);
      return this;
    }
  }
  
  public final MenuItem setEnabled(boolean paramBoolean)
  {
    int i = 0xFFFFFFEF & this.mFlags;
    if (paramBoolean) {}
    for (int j = 16;; j = 0)
    {
      this.mFlags = (j | i);
      return this;
    }
  }
  
  public final MenuItem setIcon(int paramInt)
  {
    this.mIconResId = paramInt;
    this.mIconDrawable = ContextCompat.getDrawable(this.mContext, paramInt);
    return this;
  }
  
  public final MenuItem setIcon(Drawable paramDrawable)
  {
    this.mIconDrawable = paramDrawable;
    this.mIconResId = 0;
    return this;
  }
  
  public final MenuItem setIntent(Intent paramIntent)
  {
    this.mIntent = paramIntent;
    return this;
  }
  
  public final MenuItem setNumericShortcut(char paramChar)
  {
    this.mShortcutNumericChar = paramChar;
    return this;
  }
  
  public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener)
  {
    throw new UnsupportedOperationException();
  }
  
  public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mClickListener = paramOnMenuItemClickListener;
    return this;
  }
  
  public final MenuItem setShortcut(char paramChar1, char paramChar2)
  {
    this.mShortcutNumericChar = paramChar1;
    this.mShortcutAlphabeticChar = paramChar2;
    return this;
  }
  
  public final void setShowAsAction(int paramInt) {}
  
  public final SupportMenuItem setSupportActionProvider(android.support.v4.view.ActionProvider paramActionProvider)
  {
    throw new UnsupportedOperationException();
  }
  
  public final SupportMenuItem setSupportOnActionExpandListener(MenuItemCompat.OnActionExpandListener paramOnActionExpandListener)
  {
    return this;
  }
  
  public final MenuItem setTitle(int paramInt)
  {
    this.mTitle = this.mContext.getResources().getString(paramInt);
    return this;
  }
  
  public final MenuItem setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    return this;
  }
  
  public final MenuItem setTitleCondensed(CharSequence paramCharSequence)
  {
    this.mTitleCondensed = paramCharSequence;
    return this;
  }
  
  public final MenuItem setVisible(boolean paramBoolean)
  {
    int i = 0x8 & this.mFlags;
    if (paramBoolean) {}
    for (int j = 0;; j = 8)
    {
      this.mFlags = (j | i);
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.ActionMenuItem
 * JD-Core Version:    0.7.0.1
 */