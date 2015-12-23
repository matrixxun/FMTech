package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.ActionProvider.SubUiVisibilityListener;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.appcompat.R.attr;
import android.support.v7.appcompat.R.integer;
import android.support.v7.appcompat.R.layout;
import android.support.v7.internal.view.ActionBarPolicy;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.internal.view.menu.ActionMenuItemView.PopupCallback;
import android.support.v7.internal.view.menu.BaseMenuPresenter;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.view.menu.MenuView.ItemView;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;

public final class ActionMenuPresenter
  extends BaseMenuPresenter
  implements ActionProvider.SubUiVisibilityListener
{
  private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
  ActionButtonSubmenu mActionButtonPopup;
  private int mActionItemWidthLimit;
  public boolean mExpandedActionViewsExclusive;
  public int mMaxItems;
  public boolean mMaxItemsSet;
  private int mMinCellSize;
  int mOpenSubMenuId;
  OverflowMenuButton mOverflowButton;
  OverflowPopup mOverflowPopup;
  Drawable mPendingOverflowIcon;
  boolean mPendingOverflowIconSet;
  private ActionMenuPopupCallback mPopupCallback;
  final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback((byte)0);
  public OpenOverflowRunnable mPostedOpenRunnable;
  private boolean mReserveOverflow;
  private boolean mReserveOverflowSet;
  private View mScrapActionButtonView;
  private boolean mStrictWidthLimit;
  private int mWidthLimit;
  private boolean mWidthLimitSet;
  
  public ActionMenuPresenter(Context paramContext)
  {
    super(paramContext, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
  }
  
  public final void bindItemView(MenuItemImpl paramMenuItemImpl, MenuView.ItemView paramItemView)
  {
    paramItemView.initialize$6b732f7b(paramMenuItemImpl);
    ActionMenuView localActionMenuView = (ActionMenuView)this.mMenuView;
    ActionMenuItemView localActionMenuItemView = (ActionMenuItemView)paramItemView;
    localActionMenuItemView.setItemInvoker(localActionMenuView);
    if (this.mPopupCallback == null) {
      this.mPopupCallback = new ActionMenuPopupCallback((byte)0);
    }
    localActionMenuItemView.setPopupCallback(this.mPopupCallback);
  }
  
  public final boolean dismissPopupMenus()
  {
    return hideOverflowMenu() | hideSubMenus();
  }
  
  public final boolean filterLeftoverView(ViewGroup paramViewGroup, int paramInt)
  {
    if (paramViewGroup.getChildAt(paramInt) == this.mOverflowButton) {
      return false;
    }
    return super.filterLeftoverView(paramViewGroup, paramInt);
  }
  
  public final boolean flagActionItems()
  {
    ArrayList localArrayList = this.mMenu.getVisibleItems();
    int i = localArrayList.size();
    int j = this.mMaxItems;
    int k = this.mActionItemWidthLimit;
    int m = View.MeasureSpec.makeMeasureSpec(0, 0);
    ViewGroup localViewGroup = (ViewGroup)this.mMenuView;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    if (i4 < i)
    {
      MenuItemImpl localMenuItemImpl3 = (MenuItemImpl)localArrayList.get(i4);
      if (localMenuItemImpl3.requiresActionButton()) {
        n++;
      }
      for (;;)
      {
        if ((this.mExpandedActionViewsExclusive) && (localMenuItemImpl3.isActionViewExpanded())) {
          j = 0;
        }
        i4++;
        break;
        if (localMenuItemImpl3.requestsActionButton()) {
          i1++;
        } else {
          i3 = 1;
        }
      }
    }
    if ((this.mReserveOverflow) && ((i3 != 0) || (n + i1 > j))) {
      j--;
    }
    int i5 = j - n;
    SparseBooleanArray localSparseBooleanArray = this.mActionButtonGroups;
    localSparseBooleanArray.clear();
    boolean bool1 = this.mStrictWidthLimit;
    int i6 = 0;
    int i7 = 0;
    if (bool1)
    {
      i7 = k / this.mMinCellSize;
      int i15 = k % this.mMinCellSize;
      i6 = this.mMinCellSize + i15 / i7;
    }
    int i8 = 0;
    if (i8 < i)
    {
      MenuItemImpl localMenuItemImpl1 = (MenuItemImpl)localArrayList.get(i8);
      View localView2;
      if (localMenuItemImpl1.requiresActionButton())
      {
        localView2 = getItemView(localMenuItemImpl1, this.mScrapActionButtonView, localViewGroup);
        if (this.mScrapActionButtonView == null) {
          this.mScrapActionButtonView = localView2;
        }
        if (this.mStrictWidthLimit)
        {
          i7 -= ActionMenuView.measureChildForCells(localView2, i6, i7, m, 0);
          label293:
          int i13 = localView2.getMeasuredWidth();
          k -= i13;
          if (i2 == 0) {
            i2 = i13;
          }
          int i14 = localMenuItemImpl1.getGroupId();
          if (i14 != 0) {
            localSparseBooleanArray.put(i14, true);
          }
          localMenuItemImpl1.setIsActionButton(true);
        }
      }
      for (;;)
      {
        i8++;
        break;
        localView2.measure(m, m);
        break label293;
        if (localMenuItemImpl1.requestsActionButton())
        {
          int i9 = localMenuItemImpl1.getGroupId();
          boolean bool2 = localSparseBooleanArray.get(i9);
          boolean bool3;
          label414:
          View localView1;
          label482:
          boolean bool5;
          if (((i5 > 0) || (bool2)) && (k > 0) && ((!this.mStrictWidthLimit) || (i7 > 0)))
          {
            bool3 = true;
            if (bool3)
            {
              localView1 = getItemView(localMenuItemImpl1, this.mScrapActionButtonView, localViewGroup);
              if (this.mScrapActionButtonView == null) {
                this.mScrapActionButtonView = localView1;
              }
              if (!this.mStrictWidthLimit) {
                break label569;
              }
              int i12 = ActionMenuView.measureChildForCells(localView1, i6, i7, m, 0);
              i7 -= i12;
              if (i12 == 0) {
                bool3 = false;
              }
              int i11 = localView1.getMeasuredWidth();
              k -= i11;
              if (i2 == 0) {
                i2 = i11;
              }
              if (!this.mStrictWidthLimit) {
                break label587;
              }
              if (k < 0) {
                break label581;
              }
              bool5 = true;
              label520:
              bool3 &= bool5;
            }
            if ((!bool3) || (i9 == 0)) {
              break label614;
            }
            localSparseBooleanArray.put(i9, true);
          }
          for (;;)
          {
            if (bool3) {
              i5--;
            }
            localMenuItemImpl1.setIsActionButton(bool3);
            break;
            bool3 = false;
            break label414;
            label569:
            localView1.measure(m, m);
            break label482;
            label581:
            bool5 = false;
            break label520;
            label587:
            if (k + i2 > 0) {}
            for (boolean bool4 = true;; bool4 = false)
            {
              bool3 &= bool4;
              break;
            }
            label614:
            if (bool2)
            {
              localSparseBooleanArray.put(i9, false);
              for (int i10 = 0; i10 < i8; i10++)
              {
                MenuItemImpl localMenuItemImpl2 = (MenuItemImpl)localArrayList.get(i10);
                if (localMenuItemImpl2.getGroupId() == i9)
                {
                  if (localMenuItemImpl2.isActionButton()) {
                    i5++;
                  }
                  localMenuItemImpl2.setIsActionButton(false);
                }
              }
            }
          }
        }
        localMenuItemImpl1.setIsActionButton(false);
      }
    }
    return true;
  }
  
  public final View getItemView(MenuItemImpl paramMenuItemImpl, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramMenuItemImpl.getActionView();
    if ((localView == null) || (paramMenuItemImpl.hasCollapsibleActionView())) {
      localView = super.getItemView(paramMenuItemImpl, paramView, paramViewGroup);
    }
    if (paramMenuItemImpl.isActionViewExpanded()) {}
    for (int i = 8;; i = 0)
    {
      localView.setVisibility(i);
      ActionMenuView localActionMenuView = (ActionMenuView)paramViewGroup;
      ViewGroup.LayoutParams localLayoutParams = localView.getLayoutParams();
      if (!localActionMenuView.checkLayoutParams(localLayoutParams)) {
        localView.setLayoutParams(ActionMenuView.generateLayoutParams(localLayoutParams));
      }
      return localView;
    }
  }
  
  public final MenuView getMenuView(ViewGroup paramViewGroup)
  {
    MenuView localMenuView = super.getMenuView(paramViewGroup);
    ((ActionMenuView)localMenuView).setPresenter(this);
    return localMenuView;
  }
  
  public final boolean hideOverflowMenu()
  {
    if ((this.mPostedOpenRunnable != null) && (this.mMenuView != null))
    {
      ((View)this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
      this.mPostedOpenRunnable = null;
      return true;
    }
    OverflowPopup localOverflowPopup = this.mOverflowPopup;
    if (localOverflowPopup != null)
    {
      localOverflowPopup.dismiss();
      return true;
    }
    return false;
  }
  
  public final boolean hideSubMenus()
  {
    if (this.mActionButtonPopup != null)
    {
      this.mActionButtonPopup.dismiss();
      return true;
    }
    return false;
  }
  
  public final void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder)
  {
    boolean bool = true;
    super.initForMenu(paramContext, paramMenuBuilder);
    Resources localResources = paramContext.getResources();
    ActionBarPolicy localActionBarPolicy = ActionBarPolicy.get(paramContext);
    int i;
    if (!this.mReserveOverflowSet)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        this.mReserveOverflow = bool;
      }
    }
    else
    {
      if (!this.mWidthLimitSet) {
        this.mWidthLimit = (localActionBarPolicy.mContext.getResources().getDisplayMetrics().widthPixels / 2);
      }
      if (!this.mMaxItemsSet) {
        this.mMaxItems = localActionBarPolicy.mContext.getResources().getInteger(R.integer.abc_max_action_buttons);
      }
      i = this.mWidthLimit;
      if (!this.mReserveOverflow) {
        break label234;
      }
      if (this.mOverflowButton == null)
      {
        this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
        if (this.mPendingOverflowIconSet)
        {
          this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon);
          this.mPendingOverflowIcon = null;
          this.mPendingOverflowIconSet = false;
        }
        int j = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mOverflowButton.measure(j, j);
      }
      i -= this.mOverflowButton.getMeasuredWidth();
    }
    for (;;)
    {
      this.mActionItemWidthLimit = i;
      this.mMinCellSize = ((int)(56.0F * localResources.getDisplayMetrics().density));
      this.mScrapActionButtonView = null;
      return;
      if (!ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(localActionBarPolicy.mContext))) {
        break;
      }
      bool = false;
      break;
      label234:
      this.mOverflowButton = null;
    }
  }
  
  public final boolean isOverflowMenuShowing()
  {
    return (this.mOverflowPopup != null) && (this.mOverflowPopup.isShowing());
  }
  
  public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
  {
    dismissPopupMenus();
    super.onCloseMenu(paramMenuBuilder, paramBoolean);
  }
  
  public final boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder)
  {
    if (!paramSubMenuBuilder.hasVisibleItems()) {
      return false;
    }
    for (SubMenuBuilder localSubMenuBuilder = paramSubMenuBuilder; localSubMenuBuilder.mParentMenu != this.mMenu; localSubMenuBuilder = (SubMenuBuilder)localSubMenuBuilder.mParentMenu) {}
    MenuItem localMenuItem = localSubMenuBuilder.getItem();
    ViewGroup localViewGroup = (ViewGroup)this.mMenuView;
    int j;
    Object localObject;
    if (localViewGroup != null)
    {
      int i = localViewGroup.getChildCount();
      j = 0;
      if (j < i)
      {
        View localView = localViewGroup.getChildAt(j);
        if (((localView instanceof MenuView.ItemView)) && (((MenuView.ItemView)localView).getItemData() == localMenuItem)) {
          localObject = localView;
        }
      }
    }
    for (;;)
    {
      if (localObject == null)
      {
        if (this.mOverflowButton == null)
        {
          return false;
          j++;
          break;
          localObject = null;
          continue;
        }
        localObject = this.mOverflowButton;
      }
    }
    this.mOpenSubMenuId = paramSubMenuBuilder.getItem().getItemId();
    this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, paramSubMenuBuilder);
    this.mActionButtonPopup.mAnchorView = ((View)localObject);
    if (!this.mActionButtonPopup.tryShow()) {
      throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
    }
    super.onSubMenuSelected(paramSubMenuBuilder);
    return true;
  }
  
  public final void onSubUiVisibilityChanged(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      super.onSubMenuSelected(null);
      return;
    }
    this.mMenu.close(false);
  }
  
  public final void setMenuView(ActionMenuView paramActionMenuView)
  {
    this.mMenuView = paramActionMenuView;
    paramActionMenuView.mMenu = this.mMenu;
  }
  
  public final void setReserveOverflow$1385ff()
  {
    this.mReserveOverflow = true;
    this.mReserveOverflowSet = true;
  }
  
  public final boolean shouldIncludeItem$361c3b3b(MenuItemImpl paramMenuItemImpl)
  {
    return paramMenuItemImpl.isActionButton();
  }
  
  public final boolean showOverflowMenu()
  {
    if ((this.mReserveOverflow) && (!isOverflowMenuShowing()) && (this.mMenu != null) && (this.mMenuView != null) && (this.mPostedOpenRunnable == null) && (!this.mMenu.getNonActionItems().isEmpty()))
    {
      this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton));
      ((View)this.mMenuView).post(this.mPostedOpenRunnable);
      super.onSubMenuSelected(null);
      return true;
    }
    return false;
  }
  
  public final void updateMenuView(boolean paramBoolean)
  {
    ((View)this.mMenuView).getParent();
    super.updateMenuView(paramBoolean);
    ((View)this.mMenuView).requestLayout();
    if (this.mMenu != null)
    {
      MenuBuilder localMenuBuilder = this.mMenu;
      localMenuBuilder.flagActionItems();
      ArrayList localArrayList2 = localMenuBuilder.mActionItems;
      int k = localArrayList2.size();
      for (int m = 0; m < k; m++)
      {
        ActionProvider localActionProvider = ((MenuItemImpl)localArrayList2.get(m)).mActionProvider;
        if (localActionProvider != null) {
          localActionProvider.mSubUiVisibilityListener = this;
        }
      }
    }
    ArrayList localArrayList1;
    int i;
    int j;
    if (this.mMenu != null)
    {
      localArrayList1 = this.mMenu.getNonActionItems();
      boolean bool = this.mReserveOverflow;
      i = 0;
      if (bool)
      {
        i = 0;
        if (localArrayList1 != null)
        {
          j = localArrayList1.size();
          if (j != 1) {
            break label271;
          }
          if (((MenuItemImpl)localArrayList1.get(0)).isActionViewExpanded()) {
            break label265;
          }
          i = 1;
        }
      }
      label165:
      if (i == 0) {
        break label288;
      }
      if (this.mOverflowButton == null) {
        this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
      }
      ViewGroup localViewGroup = (ViewGroup)this.mOverflowButton.getParent();
      if (localViewGroup != this.mMenuView)
      {
        if (localViewGroup != null) {
          localViewGroup.removeView(this.mOverflowButton);
        }
        ((ActionMenuView)this.mMenuView).addView(this.mOverflowButton, ActionMenuView.generateOverflowButtonLayoutParams());
      }
    }
    for (;;)
    {
      ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
      return;
      localArrayList1 = null;
      break;
      label265:
      i = 0;
      break label165;
      label271:
      if (j > 0) {}
      for (i = 1;; i = 0) {
        break;
      }
      label288:
      if ((this.mOverflowButton != null) && (this.mOverflowButton.getParent() == this.mMenuView)) {
        ((ViewGroup)this.mMenuView).removeView(this.mOverflowButton);
      }
    }
  }
  
  private final class ActionButtonSubmenu
    extends MenuPopupHelper
  {
    private SubMenuBuilder mSubMenu;
    
    public ActionButtonSubmenu(Context paramContext, SubMenuBuilder paramSubMenuBuilder)
    {
      super(paramSubMenuBuilder, null, false, R.attr.actionOverflowMenuStyle);
      this.mSubMenu = paramSubMenuBuilder;
      Object localObject;
      int i;
      if (!((MenuItemImpl)paramSubMenuBuilder.getItem()).isActionButton())
      {
        if (ActionMenuPresenter.this.mOverflowButton == null)
        {
          localObject = (View)ActionMenuPresenter.this.mMenuView;
          this.mAnchorView = ((View)localObject);
        }
      }
      else
      {
        this.mPresenterCallback = ActionMenuPresenter.this.mPopupPresenterCallback;
        i = paramSubMenuBuilder.size();
      }
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
          this.mForceShowIcon = bool;
          return;
          localObject = ActionMenuPresenter.this.mOverflowButton;
          break;
        }
      }
    }
    
    public final void onDismiss()
    {
      super.onDismiss();
      ActionMenuPresenter.this.mActionButtonPopup = null;
      ActionMenuPresenter.this.mOpenSubMenuId = 0;
    }
  }
  
  private final class ActionMenuPopupCallback
    extends ActionMenuItemView.PopupCallback
  {
    private ActionMenuPopupCallback() {}
    
    public final ListPopupWindow getPopup()
    {
      if (ActionMenuPresenter.this.mActionButtonPopup != null) {
        return ActionMenuPresenter.this.mActionButtonPopup.mPopup;
      }
      return null;
    }
  }
  
  private final class OpenOverflowRunnable
    implements Runnable
  {
    private ActionMenuPresenter.OverflowPopup mPopup;
    
    public OpenOverflowRunnable(ActionMenuPresenter.OverflowPopup paramOverflowPopup)
    {
      this.mPopup = paramOverflowPopup;
    }
    
    public final void run()
    {
      MenuBuilder localMenuBuilder = ActionMenuPresenter.this.mMenu;
      if (localMenuBuilder.mCallback != null) {
        localMenuBuilder.mCallback.onMenuModeChange(localMenuBuilder);
      }
      View localView = (View)ActionMenuPresenter.this.mMenuView;
      if ((localView != null) && (localView.getWindowToken() != null) && (this.mPopup.tryShow())) {
        ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
      }
      ActionMenuPresenter.this.mPostedOpenRunnable = null;
    }
  }
  
  private final class OverflowMenuButton
    extends AppCompatImageView
    implements ActionMenuView.ActionMenuChildView
  {
    private final float[] mTempPts = new float[2];
    
    public OverflowMenuButton(Context paramContext)
    {
      super(null, R.attr.actionOverflowButtonStyle);
      setClickable(true);
      setFocusable(true);
      setVisibility(0);
      setEnabled(true);
      setOnTouchListener(new ListPopupWindow.ForwardingListener(this)
      {
        public final ListPopupWindow getPopup()
        {
          if (ActionMenuPresenter.this.mOverflowPopup == null) {
            return null;
          }
          return ActionMenuPresenter.this.mOverflowPopup.mPopup;
        }
        
        public final boolean onForwardingStarted()
        {
          ActionMenuPresenter.this.showOverflowMenu();
          return true;
        }
        
        public final boolean onForwardingStopped()
        {
          if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
            return false;
          }
          ActionMenuPresenter.this.hideOverflowMenu();
          return true;
        }
      });
    }
    
    public final boolean needsDividerAfter()
    {
      return false;
    }
    
    public final boolean needsDividerBefore()
    {
      return false;
    }
    
    public final boolean performClick()
    {
      if (super.performClick()) {
        return true;
      }
      playSoundEffect(0);
      ActionMenuPresenter.this.showOverflowMenu();
      return true;
    }
    
    protected final boolean setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      boolean bool = super.setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
      Drawable localDrawable1 = getDrawable();
      Drawable localDrawable2 = getBackground();
      if ((localDrawable1 != null) && (localDrawable2 != null))
      {
        int i = getWidth();
        int j = getHeight();
        int k = Math.max(i, j) / 2;
        int m = getPaddingLeft() - getPaddingRight();
        int n = getPaddingTop() - getPaddingBottom();
        int i1 = (i + m) / 2;
        int i2 = (j + n) / 2;
        DrawableCompat.setHotspotBounds(localDrawable2, i1 - k, i2 - k, i1 + k, i2 + k);
      }
      return bool;
    }
  }
  
  private final class OverflowPopup
    extends MenuPopupHelper
  {
    public OverflowPopup(Context paramContext, MenuBuilder paramMenuBuilder, View paramView)
    {
      super(paramMenuBuilder, paramView, true, R.attr.actionOverflowMenuStyle);
      this.mDropDownGravity = 8388613;
      this.mPresenterCallback = ActionMenuPresenter.this.mPopupPresenterCallback;
    }
    
    public final void onDismiss()
    {
      super.onDismiss();
      if (ActionMenuPresenter.this.mMenu != null) {
        ActionMenuPresenter.this.mMenu.close();
      }
      ActionMenuPresenter.this.mOverflowPopup = null;
    }
  }
  
  private final class PopupPresenterCallback
    implements MenuPresenter.Callback
  {
    private PopupPresenterCallback() {}
    
    public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      if ((paramMenuBuilder instanceof SubMenuBuilder)) {
        ((SubMenuBuilder)paramMenuBuilder).mParentMenu.close(false);
      }
      MenuPresenter.Callback localCallback = ActionMenuPresenter.this.mCallback;
      if (localCallback != null) {
        localCallback.onCloseMenu(paramMenuBuilder, paramBoolean);
      }
    }
    
    public final boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if (paramMenuBuilder == null) {
        return false;
      }
      ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)paramMenuBuilder).getItem().getItemId();
      MenuPresenter.Callback localCallback = ActionMenuPresenter.this.mCallback;
      if (localCallback != null) {
        return localCallback.onOpenSubMenu(paramMenuBuilder);
      }
      return false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ActionMenuPresenter
 * JD-Core Version:    0.7.0.1
 */