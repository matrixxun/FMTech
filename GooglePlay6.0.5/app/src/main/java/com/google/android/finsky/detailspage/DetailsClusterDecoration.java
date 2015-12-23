package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

public final class DetailsClusterDecoration
  extends RecyclerView.ItemDecoration
{
  private final int mCardInset;
  private final int mClusterTopPeeking;
  private final Paint mFlatFillPaint;
  private final boolean mUseWideLayout;
  
  public DetailsClusterDecoration(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    this.mClusterTopPeeking = localResources.getDimensionPixelSize(2131493280);
    this.mCardInset = localResources.getDimensionPixelSize(2131493385);
    this.mUseWideLayout = localResources.getBoolean(2131427334);
    this.mFlatFillPaint = new Paint();
    this.mFlatFillPaint.setStyle(Paint.Style.FILL);
    this.mFlatFillPaint.setColor(localResources.getColor(2131689637));
  }
  
  public final void onDraw$13fcd2ff(Canvas paramCanvas, RecyclerView paramRecyclerView)
  {
    int i = paramRecyclerView.getChildCount();
    int j = 0;
    int k = 0;
    if (k < i)
    {
      View localView = paramRecyclerView.getChildAt(k);
      if ((localView instanceof FlatFillSection))
      {
        int m = localView.getTop();
        if (j == 0) {
          m -= this.mClusterTopPeeking;
        }
        paramCanvas.drawRect(0.0F, m, paramRecyclerView.getWidth(), localView.getBottom(), this.mFlatFillPaint);
      }
      for (j = 1;; j = 0)
      {
        k++;
        break;
      }
    }
  }
  
  public static abstract interface FlatFillSection {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.DetailsClusterDecoration
 * JD-Core Version:    0.7.0.1
 */