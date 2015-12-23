package android.support.v7.internal.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.dimen;
import android.support.v7.appcompat.R.layout;
import android.support.v7.widget.ListPopupWindow;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import java.util.ArrayList;

public class MenuPopupHelper
  implements MenuPresenter, View.OnKeyListener, ViewTreeObserver.OnGlobalLayoutListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener
{
  static final int ITEM_LAYOUT = R.layout.abc_popup_menu_item_layout;
  private final MenuAdapter mAdapter;
  public View mAnchorView;
  private int mContentWidth;
  private final Context mContext;
  public int mDropDownGravity = 0;
  public boolean mForceShowIcon;
  private boolean mHasContentWidth;
  private final LayoutInflater mInflater;
  private ViewGroup mMeasureParent;
  private final MenuBuilder mMenu;
  private final boolean mOverflowOnly;
  public ListPopupWindow mPopup;
  private final int mPopupMaxWidth;
  private final int mPopupStyleAttr;
  private final int mPopupStyleRes;
  public MenuPresenter.Callback mPresenterCallback;
  private ViewTreeObserver mTreeObserver;
  
  private MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView)
  {
    this(paramContext, paramMenuBuilder, paramView, false, R.attr.popupMenuStyle);
  }
  
  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView, boolean paramBoolean, int paramInt)
  {
    this(paramContext, paramMenuBuilder, paramView, paramBoolean, paramInt, (byte)0);
  }
  
  private MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView, boolean paramBoolean, int paramInt, byte paramByte)
  {
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(paramContext);
    this.mMenu = paramMenuBuilder;
    this.mAdapter = new MenuAdapter(this.mMenu);
    this.mOverflowOnly = paramBoolean;
    this.mPopupStyleAttr = paramInt;
    this.mPopupStyleRes = 0;
    Resources localResources = paramContext.getResources();
    this.mPopupMaxWidth = Math.max(localResources.getDisplayMetrics().widthPixels / 2, localResources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    this.mAnchorView = paramView;
    paramMenuBuilder.addMenuPresenter(this, paramContext);
  }
  
  public final boolean collapseItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public final void dismiss()
  {
    if (isShowing()) {
      this.mPopup.dismiss();
    }
  }
  
  public final boolean expandItemActionView$29f2911(MenuItemImpl paramMenuItemImpl)
  {
    return false;
  }
  
  public final boolean flagActionItems()
  {
    return false;
  }
  
  public final void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {}
  
  public final boolean isShowing()
  {
    return (this.mPopup != null) && (this.mPopup.mPopup.isShowing());
  }
  
  public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    if (paramMenuBuilder != this.mMenu) {}
    do
    {
      return;
      dismiss();
    } while (this.mPresenterCallback == null);
    this.mPresenterCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
  }
  
  public void onDismiss()
  {
    this.mPopup = null;
    this.mMenu.close();
    if (this.mTreeObserver != null)
    {
      if (!this.mTreeObserver.isAlive()) {
        this.mTreeObserver = this.mAnchorView.getViewTreeObserver();
      }
      this.mTreeObserver.removeGlobalOnLayoutListener(this);
      this.mTreeObserver = null;
    }
  }
  
  public void onGlobalLayout()
  {
    if (isShowing())
    {
      View localView = this.mAnchorView;
      if ((localView != null) && (localView.isShown())) {
        break label28;
      }
      dismiss();
    }
    label28:
    while (!isShowing()) {
      return;
    }
    this.mPopup.show();
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    MenuAdapter localMenuAdapter = this.mAdapter;
    localMenuAdapter.mAdapterMenu.performItemAction(localMenuAdapter.getItem(paramInt), null, 0);
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getAction() == 1) && (paramInt == 82))
    {
      dismiss();
      return true;
    }
    return false;
  }
  
  public final boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (paramSubMenuBuilder.hasVisibleItems())
    {
      MenuPopupHelper localMenuPopupHelper = new MenuPopupHelper(this.mContext, paramSubMenuBuilder, this.mAnchorView);
      localMenuPopupHelper.mPresenterCallback = this.mPresenterCallback;
      int i = paramSubMenuBuilder.size();
      for (int j = 0;; j++)
      {
        boolean bool = false;
        if (j < i)
        {
          MenuItem localMenuItem = paramSubMenuBuilder.getItem(j);
          if ((localMenuItem.isVisible()) && (localMenuItem.getIcon() != null)) {
            bool = true;
          }
        }
        else
        {
          localMenuPopupHelper.mForceShowIcon = bool;
          if (!localMenuPopupHelper.tryShow()) {
            break;
          }
          if (this.mPresenterCallback != null) {
            this.mPresenterCallback.onOpenSubMenu(paramSubMenuBuilder);
          }
          return true;
        }
      }
    }
    return false;
  }
  
  public final boolean tryShow()
  {
    this.mPopup = new ListPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
    this.mPopup.setOnDismissListener(this);
    this.mPopup.mItemClickListener = this;
    this.mPopup.setAdapter(this.mAdapter);
    this.mPopup.setModal$1385ff();
    View localView1 = this.mAnchorView;
    int n;
    View localView2;
    int i2;
    View localView3;
    label192:
    int i4;
    if (localView1 != null)
    {
      if (this.mTreeObserver == null) {}
      for (int i = 1;; i = 0)
      {
        this.mTreeObserver = localView1.getViewTreeObserver();
        if (i != 0) {
          this.mTreeObserver.addOnGlobalLayoutListener(this);
        }
        this.mPopup.mDropDownAnchorView = localView1;
        this.mPopup.mDropDownGravity = this.mDropDownGravity;
        if (!this.mHasContentWidth)
        {
          MenuAdapter localMenuAdapter = this.mAdapter;
          int j = View.MeasureSpec.makeMeasureSpec(0, 0);
          int k = View.MeasureSpec.makeMeasureSpec(0, 0);
          int m = localMenuAdapter.getCount();
          n = 0;
          int i1 = 0;
          localView2 = null;
          i2 = 0;
          if (n < m)
          {
            int i3 = localMenuAdapter.getItemViewType(n);
            if (i3 == i1) {
              break label334;
            }
            i1 = i3;
            localView3 = null;
            if (this.mMeasureParent == null) {
              this.mMeasureParent = new FrameLayout(this.mContext);
            }
            localView2 = localMenuAdapter.getView(n, localView3, this.mMeasureParent);
            localView2.measure(j, k);
            i4 = localView2.getMeasuredWidth();
            if (i4 < this.mPopupMaxWidth) {
              break;
            }
            i2 = this.mPopupMaxWidth;
          }
          this.mContentWidth = i2;
          this.mHasContentWidth = true;
        }
        this.mPopup.setContentWidth(this.mContentWidth);
        this.mPopup.setInputMethodMode$13462e();
        this.mPopup.show();
        this.mPopup.mDropDownList.setOnKeyListener(this);
        return true;
      }
    }
    return false;
    if (i4 > i2) {}
    for (;;)
    {
      n++;
      i2 = i4;
      break;
      label334:
      localView3 = localView2;
      break label192;
      i4 = i2;
    }
  }
  
  public final void updateMenuView(boolean paramBoolean)
  {
    this.mHasContentWidth = false;
    if (this.mAdapter != null) {
      this.mAdapter.notifyDataSetChanged();
    }
  }
  
  private final class MenuAdapter
    extends BaseAdapter
  {
    private MenuBuilder mAdapterMenu;
    private int mExpandedIndex = -1;
    
    public MenuAdapter(MenuBuilder paramMenuBuilder)
    {
      this.mAdapterMenu = paramMenuBuilder;
      findExpandedIndex();
    }
    
    private void findExpandedIndex()
    {
      MenuItemImpl localMenuItemImpl = MenuPopupHelper.this.mMenu.mExpandedItem;
      if (localMenuItemImpl != null)
      {
        ArrayList localArrayList = MenuPopupHelper.this.mMenu.getNonActionItems();
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
      if (MenuPopupHelper.this.mOverflowOnly) {}
      for (ArrayList localArrayList = this.mAdapterMenu.getNonActionItems(); this.mExpandedIndex < 0; localArrayList = this.mAdapterMenu.getVisibleItems()) {
        return localArrayList.size();
      }
      return -1 + localArrayList.size();
    }
    
    public final MenuItemImpl getItem(int paramInt)
    {
      if (MenuPopupHelper.this.mOverflowOnly) {}
      for (ArrayList localArrayList = this.mAdapterMenu.getNonActionItems();; localArrayList = this.mAdapterMenu.getVisibleItems())
      {
        if ((this.mExpandedIndex >= 0) && (paramInt >= this.mExpandedIndex)) {
          paramInt++;
        }
        return (MenuItemImpl)localArrayList.get(paramInt);
      }
    }
    
    public final long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = MenuPopupHelper.this.mInflater.inflate(MenuPopupHelper.ITEM_LAYOUT, paramViewGroup, false);
      }
      MenuView.ItemView localItemView = (MenuView.ItemView)paramView;
      if (MenuPopupHelper.this.mForceShowIcon) {
        ((ListMenuItemView)paramView).setForceShowIcon(true);
      }
      localItemView.initialize$6b732f7b(getItem(paramInt));
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
 * Qualified Name:     android.support.v7.internal.view.menu.MenuPopupHelper
 * JD-Core Version:    0.7.0.1
 */