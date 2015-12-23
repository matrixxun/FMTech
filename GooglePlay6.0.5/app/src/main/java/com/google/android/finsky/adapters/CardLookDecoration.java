package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import com.google.android.finsky.layout.DetailsPeekingSection;
import com.google.android.play.cardview.RoundRectDrawableWithShadow;

public final class CardLookDecoration
  extends RecyclerView.ItemDecoration
{
  private Drawable mCardDrawable = null;
  final Rect mCardStackBoundsRect = new Rect();
  private Paint mFlatFillPaint;
  private int mVerticalPadding;
  
  public CardLookDecoration(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    ColorStateList localColorStateList = localResources.getColorStateList(2131689682);
    int i = localResources.getDimensionPixelSize(2131492895);
    int j = localResources.getDimensionPixelSize(2131493068);
    int k = localResources.getDimensionPixelSize(2131493382);
    this.mCardDrawable = new RoundRectDrawableWithShadow(localResources, localColorStateList, i, k, j);
    this.mVerticalPadding = (j + k);
    this.mFlatFillPaint = new Paint();
    this.mFlatFillPaint.setStyle(Paint.Style.FILL);
    this.mFlatFillPaint.setColor(localResources.getColor(2131689637));
  }
  
  public final void onDraw$13fcd2ff(Canvas paramCanvas, RecyclerView paramRecyclerView)
  {
    this.mCardStackBoundsRect.setEmpty();
    int i = paramRecyclerView.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = paramRecyclerView.getChildAt(j);
      if ((localView instanceof CardSection))
      {
        boolean bool = localView instanceof DetailsPeekingSection;
        int k = 0;
        if (bool) {
          k = ((DetailsPeekingSection)localView).getTopPeekAmount();
        }
        this.mCardStackBoundsRect.union(localView.getLeft(), k + localView.getTop(), localView.getRight(), localView.getBottom());
      }
    }
    if (this.mCardStackBoundsRect.isEmpty()) {
      return;
    }
    Rect localRect1 = this.mCardStackBoundsRect;
    localRect1.left -= this.mVerticalPadding;
    Rect localRect2 = this.mCardStackBoundsRect;
    localRect2.top -= this.mVerticalPadding;
    Rect localRect3 = this.mCardStackBoundsRect;
    localRect3.right += this.mVerticalPadding;
    Rect localRect4 = this.mCardStackBoundsRect;
    localRect4.bottom += this.mVerticalPadding;
    this.mCardDrawable.setBounds(this.mCardStackBoundsRect);
    this.mCardDrawable.draw(paramCanvas);
  }
  
  public static abstract interface CardSection {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.CardLookDecoration
 * JD-Core Version:    0.7.0.1
 */