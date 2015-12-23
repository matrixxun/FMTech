package com.google.android.play.cardview;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Build.VERSION;
import com.google.android.play.R.color;

public final class RoundRectDrawableWithShadow
  extends CardViewBackgroundDrawable
{
  private static RectF sCornerRect;
  private final RectF mCardBounds;
  private Paint mCornerShadowPaint;
  private Path mCornerShadowPath;
  private boolean mDirty = true;
  private Paint mEdgeShadowPaint;
  private float mRawShadowSize;
  private final int mShadowEndColor;
  private float mShadowSize;
  private final int mShadowStartColor;
  
  public RoundRectDrawableWithShadow(Resources paramResources, ColorStateList paramColorStateList, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super(paramColorStateList, paramFloat1, paramFloat3);
    this.mShadowStartColor = paramResources.getColor(R.color.play_card_shadow_start_color);
    this.mShadowEndColor = paramResources.getColor(R.color.play_card_shadow_end_color);
    setShadowSize(paramFloat2);
    this.mCornerShadowPaint = new Paint(5);
    this.mCornerShadowPaint.setStyle(Paint.Style.FILL);
    this.mCornerShadowPaint.setDither(true);
    this.mCardBounds = new RectF();
    this.mEdgeShadowPaint = new Paint(this.mCornerShadowPaint);
  }
  
  public final void draw(Canvas paramCanvas)
  {
    float f1;
    float f2;
    int i;
    if (this.mDirty)
    {
      Rect localRect = getBounds();
      this.mCardBounds.set(localRect);
      this.mCardBounds.inset(this.mInset, this.mInset);
      RectF localRectF1 = new RectF(-this.mCornerRadius, -this.mCornerRadius, this.mCornerRadius, this.mCornerRadius);
      RectF localRectF2 = new RectF(localRectF1);
      localRectF2.inset(-this.mShadowSize, -this.mShadowSize);
      if (this.mCornerShadowPath == null)
      {
        this.mCornerShadowPath = new Path();
        this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0F);
        this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0F);
        this.mCornerShadowPath.arcTo(localRectF2, 180.0F, 90.0F, false);
        this.mCornerShadowPath.arcTo(localRectF1, 270.0F, -90.0F, false);
        this.mCornerShadowPath.close();
        float f6 = this.mCornerRadius / (this.mCornerRadius + this.mShadowSize);
        if (this.mCornerRadius + this.mShadowSize > 0.0F)
        {
          Paint localPaint2 = this.mCornerShadowPaint;
          float f9 = this.mCornerRadius + this.mShadowSize;
          int[] arrayOfInt2 = new int[3];
          arrayOfInt2[0] = this.mShadowStartColor;
          arrayOfInt2[1] = this.mShadowStartColor;
          arrayOfInt2[2] = this.mShadowEndColor;
          localPaint2.setShader(new RadialGradient(0.0F, 0.0F, f9, arrayOfInt2, new float[] { 0.0F, f6, 1.0F }, Shader.TileMode.CLAMP));
        }
        Paint localPaint1 = this.mEdgeShadowPaint;
        float f7 = -this.mCornerRadius + this.mShadowSize;
        float f8 = -this.mCornerRadius - this.mShadowSize;
        int[] arrayOfInt1 = new int[3];
        arrayOfInt1[0] = this.mShadowStartColor;
        arrayOfInt1[1] = this.mShadowStartColor;
        arrayOfInt1[2] = this.mShadowEndColor;
        localPaint1.setShader(new LinearGradient(0.0F, f7, 0.0F, f8, arrayOfInt1, new float[] { 0.0F, 0.5F, 1.0F }, Shader.TileMode.CLAMP));
        this.mDirty = false;
      }
    }
    else
    {
      paramCanvas.translate(0.0F, this.mRawShadowSize / 2.0F);
      f1 = -this.mCornerRadius - this.mShadowSize;
      f2 = this.mCornerRadius + this.mRawShadowSize / 2.0F;
      if (this.mCardBounds.width() - 2.0F * f2 <= 0.0F) {
        break label851;
      }
      i = 1;
      label450:
      if (this.mCardBounds.height() - 2.0F * f2 <= 0.0F) {
        break label857;
      }
    }
    label851:
    label857:
    for (int j = 1;; j = 0)
    {
      int k = paramCanvas.save();
      paramCanvas.translate(f2 + this.mCardBounds.left, f2 + this.mCardBounds.top);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (i != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.width() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(k);
      int m = paramCanvas.save();
      paramCanvas.translate(this.mCardBounds.right - f2, this.mCardBounds.bottom - f2);
      paramCanvas.rotate(180.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (i != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.width() - 2.0F * f2, -this.mCornerRadius + this.mShadowSize, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(m);
      int n = paramCanvas.save();
      paramCanvas.translate(f2 + this.mCardBounds.left, this.mCardBounds.bottom - f2);
      paramCanvas.rotate(270.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (j != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.height() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(n);
      int i1 = paramCanvas.save();
      paramCanvas.translate(this.mCardBounds.right - f2, f2 + this.mCardBounds.top);
      paramCanvas.rotate(90.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (j != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.height() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(i1);
      paramCanvas.translate(0.0F, -this.mRawShadowSize / 2.0F);
      if (Build.VERSION.SDK_INT < 17) {
        break label863;
      }
      paramCanvas.drawRoundRect(this.mCardBounds, this.mCornerRadius, this.mCornerRadius, this.mPaint);
      return;
      this.mCornerShadowPath.reset();
      break;
      i = 0;
      break label450;
    }
    label863:
    if (sCornerRect == null) {
      sCornerRect = new RectF();
    }
    float f3 = 2.0F * this.mCornerRadius;
    float f4 = this.mCardBounds.width() - f3;
    float f5 = this.mCardBounds.height() - f3;
    sCornerRect.set(this.mCardBounds.left, this.mCardBounds.top, this.mCardBounds.left + 2.0F * this.mCornerRadius, this.mCardBounds.top + 2.0F * this.mCornerRadius);
    paramCanvas.drawArc(sCornerRect, 180.0F, 90.0F, true, this.mPaint);
    sCornerRect.offset(f4, 0.0F);
    paramCanvas.drawArc(sCornerRect, 270.0F, 90.0F, true, this.mPaint);
    sCornerRect.offset(0.0F, f5);
    paramCanvas.drawArc(sCornerRect, 0.0F, 90.0F, true, this.mPaint);
    sCornerRect.offset(-f4, 0.0F);
    paramCanvas.drawArc(sCornerRect, 90.0F, 90.0F, true, this.mPaint);
    paramCanvas.drawRect(this.mCardBounds.left + this.mCornerRadius, this.mCardBounds.top, this.mCardBounds.right - this.mCornerRadius, this.mCardBounds.top + this.mCornerRadius, this.mPaint);
    paramCanvas.drawRect(this.mCardBounds.left + this.mCornerRadius, this.mCardBounds.bottom - this.mCornerRadius, this.mCardBounds.right - this.mCornerRadius, this.mCardBounds.bottom, this.mPaint);
    paramCanvas.drawRect(this.mCardBounds.left, this.mCardBounds.top + this.mCornerRadius, this.mCardBounds.right, this.mCardBounds.bottom - this.mCornerRadius, this.mPaint);
  }
  
  public final int getOpacity()
  {
    return -1;
  }
  
  protected final void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    this.mDirty = true;
  }
  
  public final void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
    this.mCornerShadowPaint.setAlpha(paramInt);
    this.mEdgeShadowPaint.setAlpha(paramInt);
  }
  
  public final void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
    this.mCornerShadowPaint.setColorFilter(paramColorFilter);
    this.mEdgeShadowPaint.setColorFilter(paramColorFilter);
  }
  
  final void setShadowSize(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      throw new IllegalArgumentException("invalid shadow size");
    }
    if (this.mRawShadowSize == paramFloat) {
      return;
    }
    this.mRawShadowSize = paramFloat;
    this.mShadowSize = (1.5F * paramFloat);
    this.mDirty = true;
    invalidateSelf();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.RoundRectDrawableWithShadow
 * JD-Core Version:    0.7.0.1
 */