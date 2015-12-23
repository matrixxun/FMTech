package android.support.v7.internal.view.menu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public abstract class BaseMenuPresenter
  implements MenuPresenter
{
  public MenuPresenter.Callback mCallback;
  public Context mContext;
  public int mId;
  protected LayoutInflater mInflater;
  private int mItemLayoutRes;
  public MenuBuilder mMenu;
  private int mMenuLayoutRes;
  public MenuView mMenuView;
  public Context mSystemContext;
  protected LayoutInflater mSystemInflater;
  
  public BaseMenuPresenter(Context paramContext, int paramInt1, int paramInt2)
  {
    this.mSystemContext = paramContext;
    this.mSystemInflater = LayoutInflater.from(paramContext);
    this.mMenuLayoutRes = paramInt1;
    this.mItemLayoutRes = paramInt2;
  }
  
  public abstract void bindItemView(MenuItemImpl paramMenuItemImpl, MenuView.ItemView paramItemView);
  
  public final boolean collapseItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public final boolean expandItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public boolean filterLeftoverView(ViewGroup paramViewGroup, int paramInt)
  {
    paramViewGroup.removeViewAt(paramInt);
    return true;
  }
  
  public boolean flagActionItems()
  {
    return false;
  }
  
  public View getItemView(MenuItemImpl paramMenuItemImpl, View paramView, ViewGroup paramViewGroup)
  {
    if ((paramView instanceof MenuView.ItemView)) {}
    for (MenuView.ItemView localItemView = (MenuView.ItemView)paramView;; localItemView = (MenuView.ItemView)this.mSystemInflater.inflate(this.mItemLayoutRes, paramViewGroup, false))
    {
      bindItemView(paramMenuItemImpl, localItemView);
      return (View)localItemView;
    }
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup)
  {
    if (this.mMenuView == null)
    {
      this.mMenuView = ((MenuView)this.mSystemInflater.inflate(this.mMenuLayoutRes, paramViewGroup, false));
      this.mMenuView.initialize(this.mMenu);
      updateMenuView(true);
    }
    return this.mMenuView;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(this.mContext);
    this.mMenu = paramMenuBuilder;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    if (this.mCallback != null) {
      this.mCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
    }
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (this.mCallback != null) {
      return this.mCallback.onOpenSubMenu(paramSubMenuBuilder);
    }
    return false;
  }
  
  public boolean shouldIncludeItem$361c3b3b(MenuItemImpl paramMenuItemImpl)
  {
    return true;
  }
  
  public void updateMenuView(boolean paramBoolean)
  {
    ViewGroup localViewGroup1 = (ViewGroup)this.mMenuView;
    if (localViewGroup1 == null) {}
    for (;;)
    {
      return;
      MenuBuilder localMenuBuilder = this.mMenu;
      int i = 0;
      if (localMenuBuilder != null)
      {
        this.mMenu.flagActionItems();
        ArrayList localArrayList = this.mMenu.getVisibleItems();
        int j = localArrayList.size();
        int k = 0;
        if (k < j)
        {
          MenuItemImpl localMenuItemImpl1 = (MenuItemImpl)localArrayList.get(k);
          View localView1;
          if (shouldIncludeItem$361c3b3b(localMenuItemImpl1))
          {
            localView1 = localViewGroup1.getChildAt(i);
            if (!(localView1 instanceof MenuView.ItemView)) {
              break label188;
            }
          }
          label188:
          for (MenuItemImpl localMenuItemImpl2 = ((MenuView.ItemView)localView1).getItemData();; localMenuItemImpl2 = null)
          {
            View localView2 = getItemView(localMenuItemImpl1, localView1, localViewGroup1);
            if (localMenuItemImpl1 != localMenuItemImpl2)
            {
              localView2.setPressed(false);
              ViewCompat.jumpDrawablesToCurrentState(localView2);
            }
            if (localView2 != localView1)
            {
              ViewGroup localViewGroup2 = (ViewGroup)localView2.getParent();
              if (localViewGroup2 != null) {
                localViewGroup2.removeView(localView2);
              }
              ((ViewGroup)this.mMenuView).addView(localView2, i);
            }
            i++;
            k++;
            break;
          }
        }
      }
      while (i < localViewGroup1.getChildCount()) {
        if (!filterLeftoverView(localViewGroup1, i)) {
          i++;
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.BaseMenuPresenter
 * JD-Core Version:    0.7.0.1
 */