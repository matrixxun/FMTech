package android.support.v7.internal.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R.bool;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuBuilder
  implements SupportMenu
{
  private static final int[] sCategoryToOrder = { 1, 4, 5, 3, 2, 0 };
  public ArrayList<MenuItemImpl> mActionItems;
  public Callback mCallback;
  final Context mContext;
  private ContextMenu.ContextMenuInfo mCurrentMenuInfo;
  public int mDefaultShowAsAction = 0;
  MenuItemImpl mExpandedItem;
  Drawable mHeaderIcon;
  CharSequence mHeaderTitle;
  View mHeaderView;
  private boolean mIsActionItemsStale;
  private boolean mIsClosing = false;
  private boolean mIsVisibleItemsStale;
  ArrayList<MenuItemImpl> mItems;
  private boolean mItemsChangedWhileDispatchPrevented = false;
  private ArrayList<MenuItemImpl> mNonActionItems;
  boolean mOptionalIconsVisible = false;
  public boolean mOverrideVisibleItems;
  private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList();
  private boolean mPreventDispatchingItemsChanged = false;
  private boolean mQwertyMode;
  private final Resources mResources;
  private boolean mShortcutsVisible;
  private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList();
  private ArrayList<MenuItemImpl> mVisibleItems;
  
  public MenuBuilder(Context paramContext)
  {
    this.mContext = paramContext;
    this.mResources = paramContext.getResources();
    this.mItems = new ArrayList();
    this.mVisibleItems = new ArrayList();
    this.mIsVisibleItemsStale = i;
    this.mActionItems = new ArrayList();
    this.mNonActionItems = new ArrayList();
    this.mIsActionItemsStale = i;
    if ((this.mResources.getConfiguration().keyboard != i) && (this.mResources.getBoolean(R.bool.abc_config_showMenuShortcutsWhenKeyboardPresent))) {}
    for (;;)
    {
      this.mShortcutsVisible = i;
      return;
      i = 0;
    }
  }
  
  private MenuItem addInternal(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    int i = (0xFFFF0000 & paramInt3) >> 16;
    if ((i < 0) || (i >= sCategoryToOrder.length)) {
      throw new IllegalArgumentException("order does not contain a valid category.");
    }
    int j = sCategoryToOrder[i] << 16 | 0xFFFF & paramInt3;
    MenuItemImpl localMenuItemImpl = new MenuItemImpl(this, paramInt1, paramInt2, paramInt3, j, paramCharSequence, this.mDefaultShowAsAction);
    if (this.mCurrentMenuInfo != null) {
      localMenuItemImpl.mMenuInfo = this.mCurrentMenuInfo;
    }
    this.mItems.add(findInsertIndex(this.mItems, j), localMenuItemImpl);
    onItemsChanged(true);
    return localMenuItemImpl;
  }
  
  private static int findInsertIndex(ArrayList<MenuItemImpl> paramArrayList, int paramInt)
  {
    for (int i = -1 + paramArrayList.size(); i >= 0; i--) {
      if (((MenuItemImpl)paramArrayList.get(i)).mOrdering <= paramInt) {
        return i + 1;
      }
    }
    return 0;
  }
  
  private MenuItemImpl findItemWithShortcutForKey(int paramInt, KeyEvent paramKeyEvent)
  {
    ArrayList localArrayList = this.mTempShortcutItemList;
    localArrayList.clear();
    findItemsWithShortcutForKey(localArrayList, paramInt, paramKeyEvent);
    if (localArrayList.isEmpty()) {}
    for (;;)
    {
      return null;
      int i = paramKeyEvent.getMetaState();
      KeyCharacterMap.KeyData localKeyData = new KeyCharacterMap.KeyData();
      paramKeyEvent.getKeyData(localKeyData);
      int j = localArrayList.size();
      if (j == 1) {
        return (MenuItemImpl)localArrayList.get(0);
      }
      boolean bool = isQwertyMode();
      for (int k = 0; k < j; k++)
      {
        MenuItemImpl localMenuItemImpl = (MenuItemImpl)localArrayList.get(k);
        if (bool) {}
        for (int m = localMenuItemImpl.getAlphabeticShortcut(); ((m == localKeyData.meta[0]) && ((i & 0x2) == 0)) || ((m == localKeyData.meta[2]) && ((i & 0x2) != 0)) || ((bool) && (m == 8) && (paramInt == 67)); m = localMenuItemImpl.getNumericShortcut()) {
          return localMenuItemImpl;
        }
      }
    }
  }
  
  private void findItemsWithShortcutForKey(List<MenuItemImpl> paramList, int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = isQwertyMode();
    int i = paramKeyEvent.getMetaState();
    KeyCharacterMap.KeyData localKeyData = new KeyCharacterMap.KeyData();
    if ((!paramKeyEvent.getKeyData(localKeyData)) && (paramInt != 67)) {
      return;
    }
    int j = this.mItems.size();
    int k = 0;
    label49:
    MenuItemImpl localMenuItemImpl;
    if (k < j)
    {
      localMenuItemImpl = (MenuItemImpl)this.mItems.get(k);
      if (localMenuItemImpl.hasSubMenu()) {
        ((MenuBuilder)localMenuItemImpl.getSubMenu()).findItemsWithShortcutForKey(paramList, paramInt, paramKeyEvent);
      }
      if (!bool) {
        break label181;
      }
    }
    label181:
    for (int m = localMenuItemImpl.getAlphabeticShortcut();; m = localMenuItemImpl.getNumericShortcut())
    {
      if (((i & 0x5) == 0) && (m != 0) && ((m == localKeyData.meta[0]) || (m == localKeyData.meta[2]) || ((bool) && (m == 8) && (paramInt == 67))) && (localMenuItemImpl.isEnabled())) {
        paramList.add(localMenuItemImpl);
      }
      k++;
      break label49;
      break;
    }
  }
  
  private void removeItemAtInt(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= this.mItems.size())) {}
    do
    {
      return;
      this.mItems.remove(paramInt);
    } while (!paramBoolean);
    onItemsChanged(true);
  }
  
  public MenuItem add(int paramInt)
  {
    return addInternal(0, 0, 0, this.mResources.getString(paramInt));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return addInternal(paramInt1, paramInt2, paramInt3, this.mResources.getString(paramInt4));
  }
  
  public MenuItem add(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    return addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
  }
  
  public MenuItem add(CharSequence paramCharSequence)
  {
    return addInternal(0, 0, 0, paramCharSequence);
  }
  
  public int addIntentOptions(int paramInt1, int paramInt2, int paramInt3, ComponentName paramComponentName, Intent[] paramArrayOfIntent, Intent paramIntent, int paramInt4, MenuItem[] paramArrayOfMenuItem)
  {
    PackageManager localPackageManager = this.mContext.getPackageManager();
    List localList = localPackageManager.queryIntentActivityOptions(paramComponentName, paramArrayOfIntent, paramIntent, 0);
    int i;
    int j;
    label52:
    ResolveInfo localResolveInfo;
    if (localList != null)
    {
      i = localList.size();
      if ((paramInt4 & 0x1) == 0) {
        removeGroup(paramInt1);
      }
      j = 0;
      if (j >= i) {
        break label211;
      }
      localResolveInfo = (ResolveInfo)localList.get(j);
      if (localResolveInfo.specificIndex >= 0) {
        break label198;
      }
    }
    label198:
    for (Intent localIntent1 = paramIntent;; localIntent1 = paramArrayOfIntent[localResolveInfo.specificIndex])
    {
      Intent localIntent2 = new Intent(localIntent1);
      localIntent2.setComponent(new ComponentName(localResolveInfo.activityInfo.applicationInfo.packageName, localResolveInfo.activityInfo.name));
      MenuItem localMenuItem = add(paramInt1, paramInt2, paramInt3, localResolveInfo.loadLabel(localPackageManager)).setIcon(localResolveInfo.loadIcon(localPackageManager)).setIntent(localIntent2);
      if ((paramArrayOfMenuItem != null) && (localResolveInfo.specificIndex >= 0)) {
        paramArrayOfMenuItem[localResolveInfo.specificIndex] = localMenuItem;
      }
      j++;
      break label52;
      i = 0;
      break;
    }
    label211:
    return i;
  }
  
  public final void addMenuPresenter(MenuPresenter paramMenuPresenter)
  {
    addMenuPresenter(paramMenuPresenter, this.mContext);
  }
  
  public final void addMenuPresenter(MenuPresenter paramMenuPresenter, Context paramContext)
  {
    this.mPresenters.add(new WeakReference(paramMenuPresenter));
    paramMenuPresenter.initForMenu(paramContext, this);
    this.mIsActionItemsStale = true;
  }
  
  public SubMenu addSubMenu(int paramInt)
  {
    return addSubMenu(0, 0, 0, this.mResources.getString(paramInt));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return addSubMenu(paramInt1, paramInt2, paramInt3, this.mResources.getString(paramInt4));
  }
  
  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    MenuItemImpl localMenuItemImpl = (MenuItemImpl)addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
    SubMenuBuilder localSubMenuBuilder = new SubMenuBuilder(this.mContext, this, localMenuItemImpl);
    localMenuItemImpl.setSubMenu(localSubMenuBuilder);
    return localSubMenuBuilder;
  }
  
  public SubMenu addSubMenu(CharSequence paramCharSequence)
  {
    return addSubMenu(0, 0, 0, paramCharSequence);
  }
  
  public void clear()
  {
    if (this.mExpandedItem != null) {
      collapseItemActionView(this.mExpandedItem);
    }
    this.mItems.clear();
    onItemsChanged(true);
  }
  
  public void clearHeader()
  {
    this.mHeaderIcon = null;
    this.mHeaderTitle = null;
    this.mHeaderView = null;
    onItemsChanged(false);
  }
  
  public void close()
  {
    close(true);
  }
  
  public final void close(boolean paramBoolean)
  {
    if (this.mIsClosing) {
      return;
    }
    this.mIsClosing = true;
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if (localMenuPresenter == null) {
        this.mPresenters.remove(localWeakReference);
      } else {
        localMenuPresenter.onCloseMenu(this, paramBoolean);
      }
    }
    this.mIsClosing = false;
  }
  
  public boolean collapseItemActionView(MenuItemImpl paramMenuItemImpl)
  {
    boolean bool;
    if ((this.mPresenters.isEmpty()) || (this.mExpandedItem != paramMenuItemImpl)) {
      bool = false;
    }
    label97:
    do
    {
      return bool;
      bool = false;
      stopDispatchingItemsChanged();
      Iterator localIterator = this.mPresenters.iterator();
      do
      {
        MenuPresenter localMenuPresenter;
        for (;;)
        {
          if (!localIterator.hasNext()) {
            break label97;
          }
          WeakReference localWeakReference = (WeakReference)localIterator.next();
          localMenuPresenter = (MenuPresenter)localWeakReference.get();
          if (localMenuPresenter != null) {
            break;
          }
          this.mPresenters.remove(localWeakReference);
        }
        bool = localMenuPresenter.collapseItemActionView$29f2911(paramMenuItemImpl);
      } while (!bool);
      startDispatchingItemsChanged();
    } while (!bool);
    this.mExpandedItem = null;
    return bool;
  }
  
  boolean dispatchMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    return (this.mCallback != null) && (this.mCallback.onMenuItemSelected(paramMenuBuilder, paramMenuItem));
  }
  
  public boolean expandItemActionView(MenuItemImpl paramMenuItemImpl)
  {
    boolean bool;
    if (this.mPresenters.isEmpty()) {
      bool = false;
    }
    label89:
    do
    {
      return bool;
      bool = false;
      stopDispatchingItemsChanged();
      Iterator localIterator = this.mPresenters.iterator();
      do
      {
        MenuPresenter localMenuPresenter;
        for (;;)
        {
          if (!localIterator.hasNext()) {
            break label89;
          }
          WeakReference localWeakReference = (WeakReference)localIterator.next();
          localMenuPresenter = (MenuPresenter)localWeakReference.get();
          if (localMenuPresenter != null) {
            break;
          }
          this.mPresenters.remove(localWeakReference);
        }
        bool = localMenuPresenter.expandItemActionView$29f2911(paramMenuItemImpl);
      } while (!bool);
      startDispatchingItemsChanged();
    } while (!bool);
    this.mExpandedItem = paramMenuItemImpl;
    return bool;
  }
  
  public MenuItem findItem(int paramInt)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.getItemId() == paramInt) {
        return localMenuItemImpl;
      }
      if (localMenuItemImpl.hasSubMenu())
      {
        MenuItem localMenuItem = localMenuItemImpl.getSubMenu().findItem(paramInt);
        if (localMenuItem != null) {
          return localMenuItem;
        }
      }
    }
    return null;
  }
  
  public final void flagActionItems()
  {
    ArrayList localArrayList = getVisibleItems();
    if (!this.mIsActionItemsStale) {
      return;
    }
    boolean bool = false;
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if (localMenuPresenter == null) {
        this.mPresenters.remove(localWeakReference);
      } else {
        bool |= localMenuPresenter.flagActionItems();
      }
    }
    if (bool)
    {
      this.mActionItems.clear();
      this.mNonActionItems.clear();
      int i = localArrayList.size();
      int j = 0;
      if (j < i)
      {
        MenuItemImpl localMenuItemImpl = (MenuItemImpl)localArrayList.get(j);
        if (localMenuItemImpl.isActionButton()) {
          this.mActionItems.add(localMenuItemImpl);
        }
        for (;;)
        {
          j++;
          break;
          this.mNonActionItems.add(localMenuItemImpl);
        }
      }
    }
    else
    {
      this.mActionItems.clear();
      this.mNonActionItems.clear();
      this.mNonActionItems.addAll(getVisibleItems());
    }
    this.mIsActionItemsStale = false;
  }
  
  protected String getActionViewStatesKey()
  {
    return "android:menu:actionviewstates";
  }
  
  public MenuItem getItem(int paramInt)
  {
    return (MenuItem)this.mItems.get(paramInt);
  }
  
  public final ArrayList<MenuItemImpl> getNonActionItems()
  {
    flagActionItems();
    return this.mNonActionItems;
  }
  
  public MenuBuilder getRootMenu()
  {
    return this;
  }
  
  public final ArrayList<MenuItemImpl> getVisibleItems()
  {
    if (!this.mIsVisibleItemsStale) {
      return this.mVisibleItems;
    }
    this.mVisibleItems.clear();
    int i = this.mItems.size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.isVisible()) {
        this.mVisibleItems.add(localMenuItemImpl);
      }
    }
    this.mIsVisibleItemsStale = false;
    this.mIsActionItemsStale = true;
    return this.mVisibleItems;
  }
  
  public boolean hasVisibleItems()
  {
    if (this.mOverrideVisibleItems) {
      return true;
    }
    int i = size();
    for (int j = 0; j < i; j++) {
      if (((MenuItemImpl)this.mItems.get(j)).isVisible()) {
        return true;
      }
    }
    return false;
  }
  
  boolean isQwertyMode()
  {
    return this.mQwertyMode;
  }
  
  public boolean isShortcutKey(int paramInt, KeyEvent paramKeyEvent)
  {
    return findItemWithShortcutForKey(paramInt, paramKeyEvent) != null;
  }
  
  public boolean isShortcutsVisible()
  {
    return this.mShortcutsVisible;
  }
  
  final void onItemActionRequestChanged$4587d488()
  {
    this.mIsActionItemsStale = true;
    onItemsChanged(true);
  }
  
  final void onItemVisibleChanged$4587d488()
  {
    this.mIsVisibleItemsStale = true;
    onItemsChanged(true);
  }
  
  public final void onItemsChanged(boolean paramBoolean)
  {
    if (!this.mPreventDispatchingItemsChanged)
    {
      if (paramBoolean)
      {
        this.mIsVisibleItemsStale = true;
        this.mIsActionItemsStale = true;
      }
      if (!this.mPresenters.isEmpty())
      {
        stopDispatchingItemsChanged();
        Iterator localIterator = this.mPresenters.iterator();
        while (localIterator.hasNext())
        {
          WeakReference localWeakReference = (WeakReference)localIterator.next();
          MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
          if (localMenuPresenter == null) {
            this.mPresenters.remove(localWeakReference);
          } else {
            localMenuPresenter.updateMenuView(paramBoolean);
          }
        }
        startDispatchingItemsChanged();
      }
      return;
    }
    this.mItemsChangedWhileDispatchPrevented = true;
  }
  
  public boolean performIdentifierAction(int paramInt1, int paramInt2)
  {
    return performItemAction(findItem(paramInt1), null, paramInt2);
  }
  
  public final boolean performItemAction(MenuItem paramMenuItem, MenuPresenter paramMenuPresenter, int paramInt)
  {
    MenuItemImpl localMenuItemImpl = (MenuItemImpl)paramMenuItem;
    boolean bool1;
    if ((localMenuItemImpl == null) || (!localMenuItemImpl.isEnabled())) {
      bool1 = false;
    }
    label92:
    SubMenuBuilder localSubMenuBuilder;
    boolean bool3;
    label176:
    do
    {
      return bool1;
      bool1 = localMenuItemImpl.invoke();
      ActionProvider localActionProvider = localMenuItemImpl.mActionProvider;
      if ((localActionProvider != null) && (localActionProvider.hasSubMenu())) {}
      for (int i = 1;; i = 0)
      {
        if (!localMenuItemImpl.hasCollapsibleActionView()) {
          break label92;
        }
        bool1 |= localMenuItemImpl.expandActionView();
        if (!bool1) {
          break;
        }
        close(true);
        return bool1;
      }
      if ((!localMenuItemImpl.hasSubMenu()) && (i == 0)) {
        break label306;
      }
      close(false);
      if (!localMenuItemImpl.hasSubMenu()) {
        localMenuItemImpl.setSubMenu(new SubMenuBuilder(this.mContext, this, localMenuItemImpl));
      }
      localSubMenuBuilder = (SubMenuBuilder)localMenuItemImpl.getSubMenu();
      if (i != 0) {
        localActionProvider.onPrepareSubMenu(localSubMenuBuilder);
      }
      boolean bool2 = this.mPresenters.isEmpty();
      bool3 = false;
      if (!bool2) {
        break;
      }
      bool1 |= bool3;
    } while (bool1);
    close(true);
    return bool1;
    boolean bool4 = false;
    if (paramMenuPresenter != null) {
      bool4 = paramMenuPresenter.onSubMenuSelected(localSubMenuBuilder);
    }
    Iterator localIterator = this.mPresenters.iterator();
    boolean bool5 = bool4;
    label226:
    MenuPresenter localMenuPresenter;
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if (localMenuPresenter == null) {
        this.mPresenters.remove(localWeakReference);
      } else {
        if (bool5) {
          break label320;
        }
      }
    }
    label306:
    label320:
    for (boolean bool6 = localMenuPresenter.onSubMenuSelected(localSubMenuBuilder);; bool6 = bool5)
    {
      bool5 = bool6;
      break label226;
      bool3 = bool5;
      break label176;
      if ((paramInt & 0x1) != 0) {
        break;
      }
      close(true);
      return bool1;
    }
  }
  
  public boolean performShortcut(int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    MenuItemImpl localMenuItemImpl = findItemWithShortcutForKey(paramInt1, paramKeyEvent);
    boolean bool = false;
    if (localMenuItemImpl != null) {
      bool = performItemAction(localMenuItemImpl, null, paramInt2);
    }
    if ((paramInt2 & 0x2) != 0) {
      close(true);
    }
    return bool;
  }
  
  public void removeGroup(int paramInt)
  {
    int i = size();
    int j = 0;
    if (j < i) {
      if (((MenuItemImpl)this.mItems.get(j)).getGroupId() != paramInt) {}
    }
    for (;;)
    {
      if (j >= 0)
      {
        int k = this.mItems.size() - j;
        int n;
        for (int m = 0;; m = n)
        {
          n = m + 1;
          if ((m >= k) || (((MenuItemImpl)this.mItems.get(j)).getGroupId() != paramInt)) {
            break;
          }
          removeItemAtInt(j, false);
        }
        j++;
        break;
        j = -1;
        continue;
        onItemsChanged(true);
      }
    }
  }
  
  public void removeItem(int paramInt)
  {
    int i = size();
    int j = 0;
    if (j < i) {
      if (((MenuItemImpl)this.mItems.get(j)).getItemId() != paramInt) {}
    }
    for (int k = j;; k = -1)
    {
      removeItemAtInt(k, true);
      return;
      j++;
      break;
    }
  }
  
  public final void removeMenuPresenter(MenuPresenter paramMenuPresenter)
  {
    Iterator localIterator = this.mPresenters.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      MenuPresenter localMenuPresenter = (MenuPresenter)localWeakReference.get();
      if ((localMenuPresenter == null) || (localMenuPresenter == paramMenuPresenter)) {
        this.mPresenters.remove(localWeakReference);
      }
    }
  }
  
  public final void restoreActionViewStates(Bundle paramBundle)
  {
    if (paramBundle == null) {}
    MenuItem localMenuItem1;
    do
    {
      int k;
      do
      {
        return;
        SparseArray localSparseArray = paramBundle.getSparseParcelableArray(getActionViewStatesKey());
        int i = size();
        for (int j = 0; j < i; j++)
        {
          MenuItem localMenuItem2 = getItem(j);
          View localView = MenuItemCompat.getActionView(localMenuItem2);
          if ((localView != null) && (localView.getId() != -1)) {
            localView.restoreHierarchyState(localSparseArray);
          }
          if (localMenuItem2.hasSubMenu()) {
            ((SubMenuBuilder)localMenuItem2.getSubMenu()).restoreActionViewStates(paramBundle);
          }
        }
        k = paramBundle.getInt("android:menu:expandedactionview");
      } while (k <= 0);
      localMenuItem1 = findItem(k);
    } while (localMenuItem1 == null);
    MenuItemCompat.expandActionView(localMenuItem1);
  }
  
  public final void saveActionViewStates(Bundle paramBundle)
  {
    SparseArray localSparseArray = null;
    int i = size();
    for (int j = 0; j < i; j++)
    {
      MenuItem localMenuItem = getItem(j);
      View localView = MenuItemCompat.getActionView(localMenuItem);
      if ((localView != null) && (localView.getId() != -1))
      {
        if (localSparseArray == null) {
          localSparseArray = new SparseArray();
        }
        localView.saveHierarchyState(localSparseArray);
        if (MenuItemCompat.isActionViewExpanded(localMenuItem)) {
          paramBundle.putInt("android:menu:expandedactionview", localMenuItem.getItemId());
        }
      }
      if (localMenuItem.hasSubMenu()) {
        ((SubMenuBuilder)localMenuItem.getSubMenu()).saveActionViewStates(paramBundle);
      }
    }
    if (localSparseArray != null) {
      paramBundle.putSparseParcelableArray(getActionViewStatesKey(), localSparseArray);
    }
  }
  
  public void setCallback(Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  public void setGroupCheckable(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = this.mItems.size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.getGroupId() == paramInt)
      {
        localMenuItemImpl.setExclusiveCheckable(paramBoolean2);
        localMenuItemImpl.setCheckable(paramBoolean1);
      }
    }
  }
  
  public void setGroupEnabled(int paramInt, boolean paramBoolean)
  {
    int i = this.mItems.size();
    for (int j = 0; j < i; j++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(j);
      if (localMenuItemImpl.getGroupId() == paramInt) {
        localMenuItemImpl.setEnabled(paramBoolean);
      }
    }
  }
  
  public void setGroupVisible(int paramInt, boolean paramBoolean)
  {
    int i = this.mItems.size();
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      MenuItemImpl localMenuItemImpl = (MenuItemImpl)this.mItems.get(k);
      if ((localMenuItemImpl.getGroupId() == paramInt) && (localMenuItemImpl.setVisibleInt(paramBoolean))) {
        j = 1;
      }
    }
    if (j != 0) {
      onItemsChanged(true);
    }
  }
  
  protected final MenuBuilder setHeaderIconInt(Drawable paramDrawable)
  {
    setHeaderInternal$6c64639d(null, paramDrawable, null);
    return this;
  }
  
  final void setHeaderInternal$6c64639d(CharSequence paramCharSequence, Drawable paramDrawable, View paramView)
  {
    if (paramView != null)
    {
      this.mHeaderView = paramView;
      this.mHeaderTitle = null;
      this.mHeaderIcon = null;
    }
    for (;;)
    {
      onItemsChanged(false);
      return;
      if (paramCharSequence != null) {
        this.mHeaderTitle = paramCharSequence;
      }
      if (paramDrawable != null) {
        this.mHeaderIcon = paramDrawable;
      }
      this.mHeaderView = null;
    }
  }
  
  protected final MenuBuilder setHeaderTitleInt(CharSequence paramCharSequence)
  {
    setHeaderInternal$6c64639d(paramCharSequence, null, null);
    return this;
  }
  
  public void setQwertyMode(boolean paramBoolean)
  {
    this.mQwertyMode = paramBoolean;
    onItemsChanged(false);
  }
  
  public int size()
  {
    return this.mItems.size();
  }
  
  public final void startDispatchingItemsChanged()
  {
    this.mPreventDispatchingItemsChanged = false;
    if (this.mItemsChangedWhileDispatchPrevented)
    {
      this.mItemsChangedWhileDispatchPrevented = false;
      onItemsChanged(true);
    }
  }
  
  public final void stopDispatchingItemsChanged()
  {
    if (!this.mPreventDispatchingItemsChanged)
    {
      this.mPreventDispatchingItemsChanged = true;
      this.mItemsChangedWhileDispatchPrevented = false;
    }
  }
  
  public static abstract interface Callback
  {
    public abstract boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem);
    
    public abstract void onMenuModeChange(MenuBuilder paramMenuBuilder);
  }
  
  public static abstract interface ItemInvoker
  {
    public abstract boolean invokeItem(MenuItemImpl paramMenuItemImpl);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.MenuBuilder
 * JD-Core Version:    0.7.0.1
 */