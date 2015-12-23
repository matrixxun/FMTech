package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.DisplayMetrics;
import android.view.View;
import com.google.android.finsky.utils.UiUtils;

public final class ModuleMarginItemDecoration
  extends RecyclerView.ItemDecoration
{
  private int mDefaultSideMargin;
  
  public ModuleMarginItemDecoration(Context paramContext, boolean paramBoolean)
  {
    this.mDefaultSideMargin = getDefaultSideMargin(paramContext.getResources(), paramBoolean);
  }
  
  public static int getDefaultSideMargin(Resources paramResources, boolean paramBoolean)
  {
    int i = paramResources.getDisplayMetrics().widthPixels;
    if (paramBoolean) {}
    for (int j = UiUtils.getGridColumnContentWidth(paramResources);; j = i) {
      return (i - Math.min(i, j)) / 2;
    }
  }
  
  public final void getItemOffsets$5c1923bd$3450f9fc(Rect paramRect, View paramView)
  {
    if ((paramView instanceof EdgeToEdge)) {}
    for (int i = 0;; i = this.mDefaultSideMargin)
    {
      if ((paramView instanceof MarginOffset)) {
        i += ((MarginOffset)paramView).getMarginOffset();
      }
      paramRect.set(i, 0, i, 0);
      return;
    }
  }
  
  public static abstract interface EdgeToEdge {}
  
  public static abstract interface MarginOffset
  {
    public abstract int getMarginOffset();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ModuleMarginItemDecoration
 * JD-Core Version:    0.7.0.1
 */