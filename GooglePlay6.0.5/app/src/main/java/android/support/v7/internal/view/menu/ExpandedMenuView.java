package android.support.v7.internal.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public final class ExpandedMenuView
  extends ListView
  implements MenuBuilder.ItemInvoker, MenuView, AdapterView.OnItemClickListener
{
  private static final int[] TINT_ATTRS = { 16842964, 16843049 };
  private int mAnimations;
  private MenuBuilder mMenu;
  
  public ExpandedMenuView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842868);
  }
  
  public ExpandedMenuView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet);
    setOnItemClickListener(this);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes$1a6c1917(paramContext, paramAttributeSet, TINT_ATTRS, paramInt);
    if (localTintTypedArray.hasValue(0)) {
      setBackgroundDrawable(localTintTypedArray.getDrawable(0));
    }
    if (localTintTypedArray.hasValue(1)) {
      setDivider(localTintTypedArray.getDrawable(1));
    }
    localTintTypedArray.mWrapped.recycle();
  }
  
  public final int getWindowAnimations()
  {
    return this.mAnimations;
  }
  
  public final void initialize(MenuBuilder paramMenuBuilder)
  {
    this.mMenu = paramMenuBuilder;
  }
  
  public final boolean invokeItem(MenuItemImpl paramMenuItemImpl)
  {
    return this.mMenu.performItemAction(paramMenuItemImpl, null, 0);
  }
  
  protected final void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    setChildrenDrawingCacheEnabled(false);
  }
  
  public final void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    invokeItem((MenuItemImpl)getAdapter().getItem(paramInt));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.internal.view.menu.ExpandedMenuView
 * JD-Core Version:    0.7.0.1
 */