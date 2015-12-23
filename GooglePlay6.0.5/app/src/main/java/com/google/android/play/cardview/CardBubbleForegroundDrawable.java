package com.google.android.play.cardview;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;

public final class CardBubbleForegroundDrawable
  extends CardBubbleDrawableWithShadow
{
  private Path mFullOutlinePath = new Path();
  protected ColorStateList mOutlineColorStateList;
  private RectF mOutlineCornerRect = new RectF();
  private int mOutlineDefaultColor;
  private Paint mOutlinePaint;
  
  CardBubbleForegroundDrawable(Resources paramResources, int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramResources, paramResources.getColorStateList(paramInt1), paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt2, paramInt3, paramInt4);
    this.mOutlineColorStateList = paramResources.getColorStateList(R.color.play_overlay_highlight_outline);
    this.mOutlineDefaultColor = this.mOutlineColorStateList.getDefaultColor();
    this.mOutlinePaint = new Paint(5);
    this.mOutlinePaint.setColor(this.mOutlineDefaultColor);
    this.mOutlinePaint.setStrokeWidth(paramResources.getDimension(R.dimen.play_highlight_outline_thickness));
    this.mOutlinePaint.setStyle(Paint.Style.STROKE);
  }
  
  protected final void buildComponents(Rect paramRect)
  {
    super.buildComponents(paramRect);
    this.mFullOutlinePath.reset();
    this.mFullOutlinePath.setFillType(Path.FillType.EVEN_ODD);
    this.mFullOutlinePath.moveTo(this.mCardBounds.left + this.mCornerRadius, this.mCardBounds.top);
    if (this.mBubbleGravity == 48)
    {
      this.mFullOutlinePath.lineTo(this.mBubbleCenterX - this.mBubbleTriangleBaseSize / 2.0F, this.mCardBounds.top);
      this.mOutlineCornerRect.set(this.mBubbleCenterX - this.mCornerRadius, this.mCardBounds.top - this.mBubbleSize + this.mShadowSize / 2.0F, this.mBubbleCenterX + this.mCornerRadius, this.mCardBounds.top - this.mBubbleSize + this.mShadowSize / 2.0F + 2.0F * this.mCornerRadius);
      this.mFullOutlinePath.lineTo(this.mOutlineCornerRect.left, this.mOutlineCornerRect.top + this.mCornerRadius / 2.0F);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 225.0F, 90.0F, false);
      this.mFullOutlinePath.lineTo(this.mBubbleCenterX + this.mBubbleTriangleBaseSize / 2.0F, this.mCardBounds.top);
    }
    this.mFullOutlinePath.lineTo(this.mCardBounds.right - this.mCornerRadius, this.mCardBounds.top);
    if (this.mCornerRadius > 0.0F)
    {
      this.mOutlineCornerRect.set(this.mCardBounds.right - 2.0F * this.mCornerRadius, this.mCardBounds.top, this.mCardBounds.right, this.mCardBounds.top + 2.0F * this.mCornerRadius);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 270.0F, 90.0F, false);
    }
    if (this.mBubbleGravity == 5)
    {
      this.mFullOutlinePath.lineTo(this.mCardBounds.right, this.mBubbleCenterY - this.mBubbleTriangleBaseSize / 2.0F);
      this.mOutlineCornerRect.set(this.mCardBounds.right + this.mBubbleSize - this.mShadowSize / 2.0F - 2.0F * this.mCornerRadius, this.mBubbleCenterY - this.mCornerRadius, this.mCardBounds.right + this.mBubbleSize - this.mShadowSize / 2.0F, this.mBubbleCenterY + this.mCornerRadius);
      this.mFullOutlinePath.lineTo(this.mOutlineCornerRect.right - this.mCornerRadius / 2.0F, this.mOutlineCornerRect.top);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 315.0F, 90.0F, false);
      this.mFullOutlinePath.lineTo(this.mCardBounds.right, this.mBubbleCenterY + this.mBubbleTriangleBaseSize / 2.0F);
    }
    this.mFullOutlinePath.lineTo(this.mCardBounds.right, this.mCardBounds.bottom - this.mCornerRadius);
    if (this.mCornerRadius > 0.0F)
    {
      this.mOutlineCornerRect.set(this.mCardBounds.right - 2.0F * this.mCornerRadius, this.mCardBounds.bottom - 2.0F * this.mCornerRadius, this.mCardBounds.right, this.mCardBounds.bottom);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 0.0F, 90.0F, false);
    }
    if (this.mBubbleGravity == 80)
    {
      this.mFullOutlinePath.lineTo(this.mBubbleCenterX + this.mBubbleTriangleBaseSize / 2.0F, this.mCardBounds.bottom);
      this.mOutlineCornerRect.set(this.mBubbleCenterX - this.mCornerRadius, this.mCardBounds.bottom + this.mBubbleSize - this.mShadowSize / 2.0F - 2.0F * this.mCornerRadius, this.mBubbleCenterX + this.mCornerRadius, this.mCardBounds.bottom + this.mBubbleSize - this.mShadowSize / 2.0F);
      this.mFullOutlinePath.lineTo(this.mOutlineCornerRect.right, this.mOutlineCornerRect.bottom - this.mCornerRadius / 2.0F);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 45.0F, 90.0F, false);
      this.mFullOutlinePath.lineTo(this.mBubbleCenterX - this.mBubbleTriangleBaseSize / 2.0F, this.mCardBounds.bottom);
    }
    this.mFullOutlinePath.lineTo(this.mCardBounds.left + this.mCornerRadius, this.mCardBounds.bottom);
    if (this.mCornerRadius > 0.0F)
    {
      this.mOutlineCornerRect.set(this.mCardBounds.left, this.mCardBounds.bottom - 2.0F * this.mCornerRadius, this.mCardBounds.left + 2.0F * this.mCornerRadius, this.mCardBounds.bottom);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 90.0F, 90.0F, false);
    }
    if (this.mBubbleGravity == 3)
    {
      this.mFullOutlinePath.lineTo(this.mCardBounds.left, this.mBubbleCenterY + this.mBubbleTriangleBaseSize / 2.0F);
      this.mOutlineCornerRect.set(this.mCardBounds.left - this.mBubbleSize + this.mShadowSize / 2.0F, this.mBubbleCenterY - this.mCornerRadius, this.mCardBounds.left - this.mBubbleSize + this.mShadowSize / 2.0F + 2.0F * this.mCornerRadius, this.mBubbleCenterY + this.mCornerRadius);
      this.mFullOutlinePath.lineTo(this.mOutlineCornerRect.left + this.mCornerRadius / 2.0F, this.mOutlineCornerRect.bottom);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 135.0F, 90.0F, false);
      this.mFullOutlinePath.lineTo(this.mCardBounds.left, this.mBubbleCenterY - this.mBubbleTriangleBaseSize / 2.0F);
    }
    this.mFullOutlinePath.lineTo(this.mCardBounds.left, this.mCardBounds.top + this.mCornerRadius);
    if (this.mCornerRadius > 0.0F)
    {
      this.mOutlineCornerRect.set(this.mCardBounds.left, this.mCardBounds.top, this.mCardBounds.left + 2.0F * this.mCornerRadius, this.mCardBounds.top + 2.0F * this.mCornerRadius);
      this.mFullOutlinePath.arcTo(this.mOutlineCornerRect, 180.0F, 90.0F, false);
    }
    this.mFullOutlinePath.close();
  }
  
  public final void draw(Canvas paramCanvas)
  {
    if (this.mIsDirty)
    {
      buildComponents(getBounds());
      this.mIsDirty = false;
    }
    paramCanvas.drawPath(this.mFullOutlinePath, this.mPaint);
    paramCanvas.drawPath(this.mFullOutlinePath, this.mOutlinePaint);
  }
  
  public final boolean isStateful()
  {
    return (super.isStateful()) || ((this.mOutlineColorStateList != null) && (this.mOutlineColorStateList.isStateful()));
  }
  
  protected final boolean onStateChange(int[] paramArrayOfInt)
  {
    boolean bool = super.onStateChange(paramArrayOfInt);
    if ((this.mOutlineColorStateList != null) && (this.mOutlineColorStateList.isStateful()))
    {
      this.mOutlinePaint.setColor(this.mOutlineColorStateList.getColorForState(paramArrayOfInt, this.mOutlineDefaultColor));
      invalidateSelf();
      bool = true;
    }
    return bool;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.CardBubbleForegroundDrawable
 * JD-Core Version:    0.7.0.1
 */