package com.google.android.play.cardview;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.RectF;

final class RoundRectDrawable
  extends CardViewBackgroundDrawable
{
  private final RectF mBoundsF = new RectF();
  private final Rect mBoundsI = new Rect();
  
  RoundRectDrawable(ColorStateList paramColorStateList, float paramFloat1, float paramFloat2)
  {
    super(paramColorStateList, paramFloat1, paramFloat2);
  }
  
  public final void draw(Canvas paramCanvas)
  {
    paramCanvas.drawRoundRect(this.mBoundsF, this.mCornerRadius, this.mCornerRadius, this.mPaint);
  }
  
  @TargetApi(21)
  public final void getOutline(Outline paramOutline)
  {
    paramOutline.setRoundRect(this.mBoundsI, this.mCornerRadius);
  }
  
  protected final void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    this.mBoundsI.set(paramRect);
    this.mBoundsI.inset((int)Math.ceil(this.mInset), (int)Math.ceil(this.mInset));
    this.mBoundsF.set(this.mBoundsI);
  }
  
  public final void setAlpha(int paramInt) {}
  
  public final void setColorFilter(ColorFilter paramColorFilter) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.RoundRectDrawable
 * JD-Core Version:    0.7.0.1
 */