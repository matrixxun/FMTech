package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuBuilder.Callback;
import android.support.v7.internal.view.menu.MenuBuilder.ItemInvoker;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.widget.ViewUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;

public class ActionMenuView
  extends LinearLayoutCompat
  implements MenuBuilder.ItemInvoker, MenuView
{
  MenuPresenter.Callback mActionMenuPresenterCallback;
  private boolean mFormatItems;
  private int mFormatItemsWidth;
  private int mGeneratedItemPadding;
  public MenuBuilder mMenu;
  MenuBuilder.Callback mMenuBuilderCallback;
  private int mMinCellSize;
  private OnMenuItemClickListener mOnMenuItemClickListener;
  private Context mPopupContext;
  private int mPopupTheme;
  public ActionMenuPresenter mPresenter;
  public boolean mReserveOverflow;
  
  public ActionMenuView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ActionMenuView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBaselineAligned(false);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mMinCellSize = ((int)(56.0F * f));
    this.mGeneratedItemPadding = ((int)(4.0F * f));
    this.mPopupContext = paramContext;
    this.mPopupTheme = 0;
  }
  
  private static LayoutParams generateDefaultLayoutParams()
  {
    LayoutParams localLayoutParams = new LayoutParams();
    localLayoutParams.gravity = 16;
    return localLayoutParams;
  }
  
  private LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected static LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if (paramLayoutParams != null)
    {
      if ((paramLayoutParams instanceof LayoutParams)) {}
      for (LayoutParams localLayoutParams = new LayoutParams((LayoutParams)paramLayoutParams);; localLayoutParams = new LayoutParams(paramLayoutParams))
      {
        if (localLayoutParams.gravity <= 0) {
          localLayoutParams.gravity = 16;
        }
        return localLayoutParams;
      }
    }
    return generateDefaultLayoutParams();
  }
  
  public static LayoutParams generateOverflowButtonLayoutParams()
  {
    LayoutParams localLayoutParams = generateDefaultLayoutParams();
    localLayoutParams.isOverflowButton = true;
    return localLayoutParams;
  }
  
  private boolean hasSupportDividerBeforeChildAt(int paramInt)
  {
    boolean bool1;
    if (paramInt == 0) {
      bool1 = false;
    }
    View localView2;
    do
    {
      return bool1;
      View localView1 = getChildAt(paramInt - 1);
      localView2 = getChildAt(paramInt);
      int i = getChildCount();
      bool1 = false;
      if (paramInt < i)
      {
        boolean bool2 = localView1 instanceof ActionMenuChildView;
        bool1 = false;
        if (bool2) {
          bool1 = false | ((ActionMenuChildView)localView1).needsDividerAfter();
        }
      }
    } while ((paramInt <= 0) || (!(localView2 instanceof ActionMenuChildView)));
    return bool1 | ((ActionMenuChildView)localView2).needsDividerBefore();
  }
  
  static int measureChildForCells(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt3) - paramInt4, View.MeasureSpec.getMode(paramInt3));
    ActionMenuItemView localActionMenuItemView;
    int j;
    label54:
    int k;
    if ((paramView instanceof ActionMenuItemView))
    {
      localActionMenuItemView = (ActionMenuItemView)paramView;
      if ((localActionMenuItemView == null) || (!localActionMenuItemView.hasText())) {
        break label178;
      }
      j = 1;
      k = 0;
      if (paramInt2 > 0) {
        if (j != 0)
        {
          k = 0;
          if (paramInt2 < 2) {}
        }
        else
        {
          paramView.measure(View.MeasureSpec.makeMeasureSpec(paramInt1 * paramInt2, -2147483648), i);
          int m = paramView.getMeasuredWidth();
          k = m / paramInt1;
          if (m % paramInt1 != 0) {
            k++;
          }
          if ((j != 0) && (k < 2)) {
            k = 2;
          }
        }
      }
      if ((localLayoutParams.isOverflowButton) || (j == 0)) {
        break label184;
      }
    }
    label178:
    label184:
    for (boolean bool = true;; bool = false)
    {
      localLayoutParams.expandable = bool;
      localLayoutParams.cellsUsed = k;
      paramView.measure(View.MeasureSpec.makeMeasureSpec(k * paramInt1, 1073741824), i);
      return k;
      localActionMenuItemView = null;
      break;
      j = 0;
      break label54;
    }
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return (paramLayoutParams != null) && ((paramLayoutParams instanceof LayoutParams));
  }
  
  public final void dismissPopupMenus()
  {
    if (this.mPresenter != null) {
      this.mPresenter.dismissPopupMenus();
    }
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    return false;
  }
  
  public Menu getMenu()
  {
    ActionMenuPresenter localActionMenuPresenter;
    if (this.mMenu == null)
    {
      Context localContext = getContext();
      this.mMenu = new MenuBuilder(localContext);
      this.mMenu.setCallback(new MenuBuilderCallback((byte)0));
      this.mPresenter = new ActionMenuPresenter(localContext);
      this.mPresenter.setReserveOverflow$1385ff();
      localActionMenuPresenter = this.mPresenter;
      if (this.mActionMenuPresenterCallback == null) {
        break label109;
      }
    }
    label109:
    for (Object localObject = this.mActionMenuPresenterCallback;; localObject = new ActionMenuPresenterCallback((byte)0))
    {
      localActionMenuPresenter.mCallback = ((MenuPresenter.Callback)localObject);
      this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
      this.mPresenter.setMenuView(this);
      return this.mMenu;
    }
  }
  
  public Drawable getOverflowIcon()
  {
    getMenu();
    ActionMenuPresenter localActionMenuPresenter = this.mPresenter;
    if (localActionMenuPresenter.mOverflowButton != null) {
      return localActionMenuPresenter.mOverflowButton.getDrawable();
    }
    if (localActionMenuPresenter.mPendingOverflowIconSet) {
      return localActionMenuPresenter.mPendingOverflowIcon;
    }
    return null;
  }
  
  public int getPopupTheme()
  {
    return this.mPopupTheme;
  }
  
  public int getWindowAnimations()
  {
    return 0;
  }
  
  public final void initialize(MenuBuilder paramMenuBuilder)
  {
    this.mMenu = paramMenuBuilder;
  }
  
  public final boolean invokeItem(MenuItemImpl paramMenuItemImpl)
  {
    return this.mMenu.performItemAction(paramMenuItemImpl, null, 0);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 8) {
      super.onConfigurationChanged(paramConfiguration);
    }
    if (this.mPresenter != null)
    {
      this.mPresenter.updateMenuView(false);
      if (this.mPresenter.isOverflowMenuShowing())
      {
        this.mPresenter.hideOverflowMenu();
        this.mPresenter.showOverflowMenu();
      }
    }
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    dismissPopupMenus();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.mFormatItems) {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    for (;;)
    {
      return;
      int i = getChildCount();
      int j = (paramInt4 - paramInt2) / 2;
      int k = getDividerWidth();
      int m = 0;
      int n = paramInt3 - paramInt1 - getPaddingRight() - getPaddingLeft();
      int i1 = 0;
      boolean bool = ViewUtils.isLayoutRtl(this);
      int i2 = 0;
      if (i2 < i)
      {
        View localView4 = getChildAt(i2);
        LayoutParams localLayoutParams3;
        int i23;
        int i26;
        int i25;
        if (localView4.getVisibility() != 8)
        {
          localLayoutParams3 = (LayoutParams)localView4.getLayoutParams();
          if (!localLayoutParams3.isOverflowButton) {
            break label239;
          }
          i23 = localView4.getMeasuredWidth();
          if (hasSupportDividerBeforeChildAt(i2)) {
            i23 += k;
          }
          int i24 = localView4.getMeasuredHeight();
          if (!bool) {
            break label212;
          }
          i26 = getPaddingLeft() + localLayoutParams3.leftMargin;
          i25 = i26 + i23;
          label167:
          int i27 = j - i24 / 2;
          int i28 = i27 + i24;
          localView4.layout(i26, i27, i25, i28);
          n -= i23;
          i1 = 1;
        }
        for (;;)
        {
          i2++;
          break;
          label212:
          i25 = getWidth() - getPaddingRight() - localLayoutParams3.rightMargin;
          i26 = i25 - i23;
          break label167;
          label239:
          n -= localView4.getMeasuredWidth() + localLayoutParams3.leftMargin + localLayoutParams3.rightMargin;
          hasSupportDividerBeforeChildAt(i2);
          m++;
        }
      }
      if ((i == 1) && (i1 == 0))
      {
        View localView3 = getChildAt(0);
        int i19 = localView3.getMeasuredWidth();
        int i20 = localView3.getMeasuredHeight();
        int i21 = (paramInt3 - paramInt1) / 2 - i19 / 2;
        int i22 = j - i20 / 2;
        localView3.layout(i21, i22, i21 + i19, i22 + i20);
        return;
      }
      int i3;
      label356:
      int i4;
      if (i1 != 0)
      {
        i3 = 0;
        i4 = m - i3;
        if (i4 <= 0) {
          break label525;
        }
      }
      int i6;
      label525:
      for (int i5 = n / i4;; i5 = 0)
      {
        i6 = Math.max(0, i5);
        if (!bool) {
          break label531;
        }
        int i13 = getWidth() - getPaddingRight();
        for (int i14 = 0; i14 < i; i14++)
        {
          View localView2 = getChildAt(i14);
          LayoutParams localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
          if ((localView2.getVisibility() != 8) && (!localLayoutParams2.isOverflowButton))
          {
            int i15 = i13 - localLayoutParams2.rightMargin;
            int i16 = localView2.getMeasuredWidth();
            int i17 = localView2.getMeasuredHeight();
            int i18 = j - i17 / 2;
            localView2.layout(i15 - i16, i18, i15, i18 + i17);
            i13 = i15 - (i6 + (i16 + localLayoutParams2.leftMargin));
          }
        }
        break;
        i3 = 1;
        break label356;
      }
      label531:
      int i7 = getPaddingLeft();
      for (int i8 = 0; i8 < i; i8++)
      {
        View localView1 = getChildAt(i8);
        LayoutParams localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
        if ((localView1.getVisibility() != 8) && (!localLayoutParams1.isOverflowButton))
        {
          int i9 = i7 + localLayoutParams1.leftMargin;
          int i10 = localView1.getMeasuredWidth();
          int i11 = localView1.getMeasuredHeight();
          int i12 = j - i11 / 2;
          localView1.layout(i9, i12, i9 + i10, i12 + i11);
          i7 = i9 + (i6 + (i10 + localLayoutParams1.rightMargin));
        }
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    boolean bool1 = this.mFormatItems;
    if (View.MeasureSpec.getMode(paramInt1) == 1073741824) {}
    int j;
    int m;
    int i1;
    int i3;
    int i4;
    int i5;
    int i6;
    int i7;
    for (boolean bool2 = true;; bool2 = false)
    {
      this.mFormatItems = bool2;
      if (bool1 != this.mFormatItems) {
        this.mFormatItemsWidth = 0;
      }
      int i = View.MeasureSpec.getSize(paramInt1);
      if ((this.mFormatItems) && (this.mMenu != null) && (i != this.mFormatItemsWidth))
      {
        this.mFormatItemsWidth = i;
        this.mMenu.onItemsChanged(true);
      }
      j = getChildCount();
      if ((!this.mFormatItems) || (j <= 0)) {
        break label1319;
      }
      m = View.MeasureSpec.getMode(paramInt2);
      int n = View.MeasureSpec.getSize(paramInt1);
      i1 = View.MeasureSpec.getSize(paramInt2);
      int i2 = getPaddingLeft() + getPaddingRight();
      i3 = getPaddingTop() + getPaddingBottom();
      i4 = getChildMeasureSpec(paramInt2, i3, -2);
      i5 = n - i2;
      i6 = i5 / this.mMinCellSize;
      i7 = i5 % this.mMinCellSize;
      if (i6 != 0) {
        break;
      }
      setMeasuredDimension(i5, 0);
      return;
    }
    int i8 = this.mMinCellSize + i7 / i6;
    int i9 = 0;
    int i10 = 0;
    int i11 = 0;
    int i12 = 0;
    int i13 = 0;
    long l1 = 0L;
    int i14 = getChildCount();
    int i15 = 0;
    int i41;
    boolean bool4;
    label353:
    int i42;
    label371:
    int i44;
    int i45;
    label409:
    int i35;
    label420:
    int i46;
    int i48;
    int i39;
    int i37;
    int i36;
    long l6;
    int i40;
    if (i15 < i14)
    {
      View localView4 = getChildAt(i15);
      if (localView4.getVisibility() == 8) {
        break label1441;
      }
      boolean bool3 = localView4 instanceof ActionMenuItemView;
      i41 = i12 + 1;
      if (bool3) {
        localView4.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
      }
      LayoutParams localLayoutParams6 = (LayoutParams)localView4.getLayoutParams();
      localLayoutParams6.expanded = false;
      localLayoutParams6.extraPixels = 0;
      localLayoutParams6.cellsUsed = 0;
      localLayoutParams6.expandable = false;
      localLayoutParams6.leftMargin = 0;
      localLayoutParams6.rightMargin = 0;
      if ((bool3) && (((ActionMenuItemView)localView4).hasText()))
      {
        bool4 = true;
        localLayoutParams6.preventEdgeOffset = bool4;
        if (!localLayoutParams6.isOverflowButton) {
          break label527;
        }
        i42 = 1;
        int i43 = measureChildForCells(localView4, i8, i42, i4, i3);
        i44 = Math.max(i10, i43);
        if (!localLayoutParams6.expandable) {
          break label1434;
        }
        i45 = i11 + 1;
        if (!localLayoutParams6.isOverflowButton) {
          break label1427;
        }
        i35 = 1;
        i46 = i6 - i43;
        int i47 = localView4.getMeasuredHeight();
        i48 = Math.max(i9, i47);
        if (i43 != 1) {
          break label1400;
        }
        long l8 = l1 | 1 << i15;
        i39 = i46;
        i37 = i44;
        int i49 = i45;
        i36 = i48;
        l6 = l8;
        i40 = i41;
        i11 = i49;
      }
    }
    for (;;)
    {
      i15++;
      i10 = i37;
      i9 = i36;
      i13 = i35;
      long l7 = l6;
      i12 = i40;
      i6 = i39;
      l1 = l7;
      break;
      bool4 = false;
      break label353;
      label527:
      i42 = i6;
      break label371;
      int i16;
      int i17;
      long l2;
      int i18;
      int i26;
      long l4;
      int i27;
      int i28;
      label583:
      LayoutParams localLayoutParams5;
      int i34;
      int i33;
      if ((i13 != 0) && (i12 == 2))
      {
        i16 = 1;
        i17 = 0;
        l2 = l1;
        i18 = i6;
        if ((i11 <= 0) || (i18 <= 0)) {
          break label873;
        }
        i26 = 2147483647;
        l4 = 0L;
        i27 = 0;
        i28 = 0;
        if (i28 >= i14) {
          break label692;
        }
        localLayoutParams5 = (LayoutParams)getChildAt(i28).getLayoutParams();
        if (!localLayoutParams5.expandable) {
          break label1389;
        }
        if (localLayoutParams5.cellsUsed >= i26) {
          break label659;
        }
        i34 = localLayoutParams5.cellsUsed;
        l4 = 1 << i28;
        i33 = 1;
      }
      label1036:
      for (;;)
      {
        i28++;
        i26 = i34;
        i27 = i33;
        break label583;
        i16 = 0;
        break;
        label659:
        if (localLayoutParams5.cellsUsed == i26)
        {
          l4 |= 1 << i28;
          i33 = i27 + 1;
          i34 = i26;
          continue;
          label692:
          l2 |= l4;
          int i30;
          int i31;
          long l5;
          label723:
          View localView3;
          LayoutParams localLayoutParams4;
          int i32;
          if (i27 <= i18)
          {
            int i29 = i26 + 1;
            i30 = 0;
            i31 = i18;
            l5 = l2;
            if (i30 < i14)
            {
              localView3 = getChildAt(i30);
              localLayoutParams4 = (LayoutParams)localView3.getLayoutParams();
              if ((l4 & 1 << i30) == 0L)
              {
                if (localLayoutParams4.cellsUsed != i29) {
                  break label1382;
                }
                l5 |= 1 << i30;
                i32 = i31;
              }
            }
          }
          for (;;)
          {
            i30++;
            i31 = i32;
            break label723;
            if ((i16 != 0) && (localLayoutParams4.preventEdgeOffset) && (i31 == 1)) {
              localView3.setPadding(i8 + this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
            }
            localLayoutParams4.cellsUsed = (1 + localLayoutParams4.cellsUsed);
            localLayoutParams4.expanded = true;
            i32 = i31 - 1;
            continue;
            l2 = l5;
            i17 = 1;
            i18 = i31;
            break;
            label873:
            long l3 = l2;
            int i19;
            float f1;
            if ((i13 == 0) && (i12 == 1))
            {
              i19 = 1;
              if ((i18 <= 0) || (l3 == 0L)) {
                break label1226;
              }
              int i22 = i12 - 1;
              if ((i18 >= i22) && (i19 == 0) && (i10 <= 1)) {
                break label1226;
              }
              f1 = Long.bitCount(l3);
              if (i19 != 0) {
                break label1375;
              }
              if (((1L & l3) != 0L) && (!((LayoutParams)getChildAt(0).getLayoutParams()).preventEdgeOffset)) {
                f1 -= 0.5F;
              }
              if (((l3 & 1 << i14 - 1) == 0L) || (((LayoutParams)getChildAt(i14 - 1).getLayoutParams()).preventEdgeOffset)) {
                break label1375;
              }
            }
            label1043:
            label1219:
            label1226:
            label1230:
            label1375:
            for (float f2 = f1 - 0.5F;; f2 = f1)
            {
              int i23;
              int i24;
              LayoutParams localLayoutParams3;
              int i25;
              if (f2 > 0.0F)
              {
                i23 = (int)(i18 * i8 / f2);
                i24 = 0;
                i20 = i17;
                if (i24 >= i14) {
                  break label1230;
                }
                if ((l3 & 1 << i24) == 0L) {
                  break label1219;
                }
                View localView2 = getChildAt(i24);
                localLayoutParams3 = (LayoutParams)localView2.getLayoutParams();
                if (!(localView2 instanceof ActionMenuItemView)) {
                  break label1150;
                }
                localLayoutParams3.extraPixels = i23;
                localLayoutParams3.expanded = true;
                if ((i24 == 0) && (!localLayoutParams3.preventEdgeOffset)) {
                  localLayoutParams3.leftMargin = (-i23 / 2);
                }
                i25 = 1;
              }
              for (;;)
              {
                i24++;
                i20 = i25;
                break label1043;
                i19 = 0;
                break;
                i23 = 0;
                break label1036;
                label1150:
                if (localLayoutParams3.isOverflowButton)
                {
                  localLayoutParams3.extraPixels = i23;
                  localLayoutParams3.expanded = true;
                  localLayoutParams3.rightMargin = (-i23 / 2);
                  i25 = 1;
                }
                else
                {
                  if (i24 != 0) {
                    localLayoutParams3.leftMargin = (i23 / 2);
                  }
                  if (i24 != i14 - 1) {
                    localLayoutParams3.rightMargin = (i23 / 2);
                  }
                  i25 = i20;
                }
              }
              int i20 = i17;
              if (i20 != 0) {
                for (int i21 = 0; i21 < i14; i21++)
                {
                  View localView1 = getChildAt(i21);
                  LayoutParams localLayoutParams2 = (LayoutParams)localView1.getLayoutParams();
                  if (localLayoutParams2.expanded) {
                    localView1.measure(View.MeasureSpec.makeMeasureSpec(i8 * localLayoutParams2.cellsUsed + localLayoutParams2.extraPixels, 1073741824), i4);
                  }
                }
              }
              if (m != 1073741824) {}
              for (;;)
              {
                setMeasuredDimension(i5, i9);
                return;
                for (int k = 0; k < j; k++)
                {
                  LayoutParams localLayoutParams1 = (LayoutParams)getChildAt(k).getLayoutParams();
                  localLayoutParams1.rightMargin = 0;
                  localLayoutParams1.leftMargin = 0;
                }
                super.onMeasure(paramInt1, paramInt2);
                return;
                i9 = i1;
              }
            }
            label1319:
            label1382:
            i32 = i31;
          }
        }
        else
        {
          label1389:
          i33 = i27;
          i34 = i26;
        }
      }
      label1400:
      i40 = i41;
      i11 = i45;
      i36 = i48;
      l6 = l1;
      i39 = i46;
      i37 = i44;
      continue;
      label1427:
      i35 = i13;
      break label420;
      label1434:
      i45 = i11;
      break label409;
      label1441:
      i35 = i13;
      i36 = i9;
      i37 = i10;
      int i38 = i12;
      l6 = l1;
      i39 = i6;
      i40 = i38;
    }
  }
  
  public void setExpandedActionViewsExclusive(boolean paramBoolean)
  {
    this.mPresenter.mExpandedActionViewsExclusive = paramBoolean;
  }
  
  public void setOnMenuItemClickListener(OnMenuItemClickListener paramOnMenuItemClickListener)
  {
    this.mOnMenuItemClickListener = paramOnMenuItemClickListener;
  }
  
  public void setOverflowIcon(Drawable paramDrawable)
  {
    getMenu();
    ActionMenuPresenter localActionMenuPresenter = this.mPresenter;
    if (localActionMenuPresenter.mOverflowButton != null)
    {
      localActionMenuPresenter.mOverflowButton.setImageDrawable(paramDrawable);
      return;
    }
    localActionMenuPresenter.mPendingOverflowIconSet = true;
    localActionMenuPresenter.mPendingOverflowIcon = paramDrawable;
  }
  
  public void setOverflowReserved(boolean paramBoolean)
  {
    this.mReserveOverflow = paramBoolean;
  }
  
  public void setPopupTheme(int paramInt)
  {
    if (this.mPopupTheme != paramInt)
    {
      this.mPopupTheme = paramInt;
      if (paramInt == 0) {
        this.mPopupContext = getContext();
      }
    }
    else
    {
      return;
    }
    this.mPopupContext = new ContextThemeWrapper(getContext(), paramInt);
  }
  
  public void setPresenter(ActionMenuPresenter paramActionMenuPresenter)
  {
    this.mPresenter = paramActionMenuPresenter;
    this.mPresenter.setMenuView(this);
  }
  
  public static abstract interface ActionMenuChildView
  {
    public abstract boolean needsDividerAfter();
    
    public abstract boolean needsDividerBefore();
  }
  
  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    private ActionMenuPresenterCallback() {}
    
    public final void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {}
    
    public final boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      return false;
    }
  }
  
  public static final class LayoutParams
    extends LinearLayoutCompat.LayoutParams
  {
    @ViewDebug.ExportedProperty
    public int cellsUsed;
    @ViewDebug.ExportedProperty
    public boolean expandable;
    boolean expanded;
    @ViewDebug.ExportedProperty
    public int extraPixels;
    @ViewDebug.ExportedProperty
    public boolean isOverflowButton;
    @ViewDebug.ExportedProperty
    public boolean preventEdgeOffset;
    
    public LayoutParams()
    {
      super(-2);
      this.isOverflowButton = false;
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.isOverflowButton = paramLayoutParams.isOverflowButton;
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
  }
  
  private final class MenuBuilderCallback
    implements MenuBuilder.Callback
  {
    private MenuBuilderCallback() {}
    
    public final boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
    {
      return (ActionMenuView.this.mOnMenuItemClickListener != null) && (ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(paramMenuItem));
    }
    
    public final void onMenuModeChange(MenuBuilder paramMenuBuilder)
    {
      if (ActionMenuView.this.mMenuBuilderCallback != null) {
        ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(paramMenuBuilder);
      }
    }
  }
  
  public static abstract interface OnMenuItemClickListener
  {
    public abstract boolean onMenuItemClick(MenuItem paramMenuItem);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ActionMenuView
 * JD-Core Version:    0.7.0.1
 */