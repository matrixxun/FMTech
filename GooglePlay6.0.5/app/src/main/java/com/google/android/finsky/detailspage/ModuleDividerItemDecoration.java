package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

public final class ModuleDividerItemDecoration
  extends RecyclerView.ItemDecoration
{
  private final int mHalfSeparatorThickness;
  private final Paint mModuleDividerPaint;
  private final int mSectionSeparatorInset;
  
  public ModuleDividerItemDecoration(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    this.mSectionSeparatorInset = localResources.getDimensionPixelSize(2131492993);
    this.mModuleDividerPaint = new Paint();
    this.mModuleDividerPaint.setColor(-16777216);
    int i = localResources.getDimensionPixelSize(2131493415);
    this.mHalfSeparatorThickness = ((-1 + (i + 2)) / 2);
    this.mModuleDividerPaint.setStrokeWidth(i);
  }
  
  public final void getItemOffsets$5c1923bd$3450f9fc(Rect paramRect, View paramView)
  {
    paramRect.set(0, 0, 0, 0);
  }
  
  public final void onDrawOver$13fcd2ff(Canvas paramCanvas, RecyclerView paramRecyclerView)
  {
    boolean bool = false;
    float f1 = 1.0F;
    int i = paramRecyclerView.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = paramRecyclerView.getChildAt(j);
      if ((!bool) && (!(localView instanceof NoTopDivider)))
      {
        int k = localView.getTop() + Math.round(ViewCompat.getTranslationY(localView));
        float f2 = Math.min(f1, ViewCompat.getAlpha(localView));
        this.mModuleDividerPaint.setAlpha(Math.round(38.0F * f2));
        paramCanvas.drawLine(localView.getLeft() + this.mSectionSeparatorInset, k, localView.getRight() - this.mSectionSeparatorInset, k, this.mModuleDividerPaint);
      }
      bool = localView instanceof NoBottomDivider;
      f1 = ViewCompat.getAlpha(localView);
    }
  }
  
  public static abstract interface NoBottomDivider {}
  
  public static abstract interface NoTopDivider {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ModuleDividerItemDecoration
 * JD-Core Version:    0.7.0.1
 */