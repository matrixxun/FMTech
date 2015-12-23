package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

public final class CardStreamMarginItemDecoration
  extends RecyclerView.ItemDecoration
{
  private int mCardInset;
  
  public CardStreamMarginItemDecoration(Context paramContext)
  {
    this.mCardInset = paramContext.getResources().getDimensionPixelSize(2131493068);
  }
  
  public final void getItemOffsets$5c1923bd$3450f9fc(Rect paramRect, View paramView)
  {
    if (paramView.getId() == 2131755461) {
      paramRect.set(-this.mCardInset, -this.mCardInset, -this.mCardInset, 0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.CardStreamMarginItemDecoration
 * JD-Core Version:    0.7.0.1
 */