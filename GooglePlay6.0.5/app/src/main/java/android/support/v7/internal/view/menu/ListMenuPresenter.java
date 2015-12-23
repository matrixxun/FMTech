package android.support.v7.internal.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertController.AlertParams;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.appcompat.R.layout;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;

public final class ListMenuPresenter
  implements MenuPresenter, AdapterView.OnItemClickListener
{
  MenuAdapter mAdapter;
  public MenuPresenter.Callback mCallback;
  Context mContext;
  LayoutInflater mInflater;
  private int mItemIndexOffset;
  int mItemLayoutRes;
  MenuBuilder mMenu;
  ExpandedMenuView mMenuView;
  int mThemeRes;
  
  private ListMenuPresenter(int paramInt)
  {
    this.mItemLayoutRes = paramInt;
    this.mThemeRes = 0;
  }
  
  public ListMenuPresenter(Context paramContext, int paramInt)
  {
    this(paramInt);
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(this.mContext);
  }
  
  public final boolean collapseItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public final boolean expandItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public final boolean flagActionItems()
  {
    return false;
  }
  
  public final ListAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new MenuAdapter();
    }
    return this.mAdapter;
  }
  
  public final MenuView getMenuView(ViewGroup paramViewGroup)
  {
    if (this.mMenuView == null)
    {
      this.mMenuView = ((ExpandedMenuView)this.mInflater.inflate(R.layout.abc_expanded_menu_layout, paramViewGroup, false));
      if (this.mAdapter == null) {
        this.mAdapter = new MenuAdapter();
      }
      this.mMenuView.setAdapter(this.mAdapter);
      this.mMenuView.setOnItemClickListener(this);
    }
    return this.mMenuView;
  }
  
  public final void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    if (this.mThemeRes != 0)
    {
      this.mContext = new ContextThemeWrapper(paramContext, this.mThemeRes);
      this.mInflater = LayoutInflater.from(this.mContext);
    }
    for (;;)
    {
      this.mMenu = paramMenuBuilder;
      if (this.mAdapter != null) {
        this.mAdapter.notifyDataSetChanged();
      }
      return;
      if (this.mContext != null)
      {
        this.mContext = paramContext;
        if (this.mInflater == null) {
          this.mInflater = LayoutInflater.from(this.mContext);
        }
      }
    }
  }
  
  public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    if (this.mCallback != null) {
      this.mCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
    }
  }
  
  public final void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mMenu.performItemAction(this.mAdapter.getItem(paramInt), this, 0);
  }
  
  public final boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (!paramSubMenuBuilder.hasVisibleItems()) {
      return false;
    }
    MenuDialogHelper localMenuDialogHelper = new MenuDialogHelper(paramSubMenuBuilder);
    MenuBuilder localMenuBuilder = localMenuDialogHelper.mMenu;
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localMenuBuilder.mContext);
    localMenuDialogHelper.mPresenter = new ListMenuPresenter(localBuilder.P.mContext, R.layout.abc_list_menu_item_layout);
    localMenuDialogHelper.mPresenter.mCallback = localMenuDialogHelper;
    localMenuDialogHelper.mMenu.addMenuPresenter(localMenuDialogHelper.mPresenter);
    ListAdapter localListAdapter = localMenuDialogHelper.mPresenter.getAdapter();
    localBuilder.P.mAdapter = localListAdapter;
    localBuilder.P.mOnClickListener = localMenuDialogHelper;
    View localView = localMenuBuilder.mHeaderView;
    if (localView != null) {
      localBuilder.P.mCustomTitleView = localView;
    }
    for (;;)
    {
      localBuilder.setOnKeyListener(localMenuDialogHelper);
      localMenuDialogHelper.mDialog = localBuilder.create();
      localMenuDialogHelper.mDialog.setOnDismissListener(localMenuDialogHelper);
      WindowManager.LayoutParams localLayoutParams = localMenuDialogHelper.mDialog.getWindow().getAttributes();
      localLayoutParams.type = 1003;
      localLayoutParams.flags = (0x20000 | localLayoutParams.flags);
      localMenuDialogHelper.mDialog.show();
      if (this.mCallback != null) {
        this.mCallback.onOpenSubMenu(paramSubMenuBuilder);
      }
      return true;
      Drawable localDrawable = localMenuBuilder.mHeaderIcon;
      localBuilder.P.mIcon = localDrawable;
      localBuilder.setTitle(localMenuBuilder.mHeaderTitle);
    }
  }
  
  public final void updateMenuView(boolean paramBoolean)
  {
    if (this.mAdapter != null) {
      this.mAdapter.notifyDataSetChanged();
    }
  }
  
  private final class MenuAdapter
    extends BaseAdapter
  {
    private int mExpandedIndex = -1;
    
    public MenuAdapter()
    {
      findExpandedIndex();
    }
    
    private void findExpandedIndex()
    {
      MenuItemImpl localMenuItemImpl = ListMenuPresenter.this.mMenu.mExpandedItem;
      if (localMenuItemImpl != null)
      {
        ArrayList localArrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
        int i = localArrayList.size();
        for (int j = 0; j < i; j++) {
          if ((MenuItemImpl)localArrayList.get(j) == localMenuItemImpl)
          {
            this.mExpandedIndex = j;
            return;
          }
        }
      }
      this.mExpandedIndex = -1;
    }
    
    public final int getCount()
    {
      int i = ListMenuPresenter.this.mMenu.getNonActionItems().size() - ListMenuPresenter.this.mItemIndexOffset;
      if (this.mExpandedIndex < 0) {
        return i;
      }
      return i - 1;
    }
    
    public final MenuItemImpl getItem(int paramInt)
    {
      ArrayList localArrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
      int i = paramInt + ListMenuPresenter.this.mItemIndexOffset;
      if ((this.mExpandedIndex >= 0) && (i >= this.mExpandedIndex)) {
        i++;
      }
      return (MenuItemImpl)localArrayList.get(i);
    }
    
    public final long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, paramViewGroup, false);
      }
      ((MenuView.ItemView)paramView).initialize$6b732f7b(getItem(paramInt));
      return paramView;
    }
    
    public final void notifyDataSetChanged()
    {
      findExpandedIndex();
      super.notifyDataSetChanged();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.ListMenuPresenter
 * JD-Core Version:    0.7.0.1
 */