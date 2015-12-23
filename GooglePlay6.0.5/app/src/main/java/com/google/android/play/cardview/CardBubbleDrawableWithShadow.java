package com.google.android.play.cardview;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
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

public class CardBubbleDrawableWithShadow
  extends CardViewBackgroundDrawable
{
  private static final float SIN_BUBBLE_SIDE = (float)Math.sin(0.7853981633974483D);
  private static final float TAN_BUBBLE_SIDE = (float)Math.tan(0.7853981633974483D);
  private static RectF sArrowTipCornerRect;
  private static RectF sCornerRect;
  private float mBubbleAngle;
  protected float mBubbleCenterX;
  protected float mBubbleCenterY;
  protected int mBubbleGravity;
  protected int mBubbleOffset;
  private Path mBubblePath = new Path();
  protected int mBubblePosition;
  private final Matrix mBubbleRotationMatrix;
  protected float mBubbleSize;
  protected float mBubbleTriangleBaseSize;
  protected final RectF mCardBounds = new RectF();
  private Paint mCornerShadowPaint;
  private final Path mCornerShadowPath;
  private Paint mEdgeShadowPaint;
  protected boolean mIsDirty = true;
  private float mRawShadowSize;
  private final int mShadowEndColor;
  protected float mShadowSize;
  private final int mShadowStartColor;
  
  CardBubbleDrawableWithShadow(Resources paramResources, ColorStateList paramColorStateList, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramColorStateList, paramFloat1, paramFloat2);
    this.mShadowStartColor = paramResources.getColor(R.color.play_card_shadow_start_color);
    this.mShadowEndColor = paramResources.getColor(R.color.play_card_shadow_end_color);
    this.mBubbleGravity = paramInt1;
    this.mBubbleOffset = paramInt2;
    this.mBubblePosition = paramInt3;
    if (paramFloat3 < 0.0F) {
      throw new IllegalArgumentException("invalid shadow size");
    }
    if (this.mRawShadowSize != paramFloat3)
    {
      this.mRawShadowSize = paramFloat3;
      this.mShadowSize = (1.5F * paramFloat3);
      this.mIsDirty = true;
      invalidateSelf();
    }
    if (paramFloat4 <= 0.0F) {
      throw new IllegalArgumentException("invalid bubble size");
    }
    if (this.mBubbleSize != paramFloat4)
    {
      this.mBubbleSize = paramFloat4;
      this.mIsDirty = true;
      invalidateSelf();
    }
    this.mCornerShadowPaint = new Paint(5);
    this.mCornerShadowPaint.setStyle(Paint.Style.FILL);
    this.mCornerShadowPaint.setDither(true);
    this.mEdgeShadowPaint = new Paint(this.mCornerShadowPaint);
    this.mBubbleRotationMatrix = new Matrix();
    this.mCornerShadowPath = new Path();
  }
  
  private void drawBubbleShadow(Canvas paramCanvas, float paramFloat1, float paramFloat2)
  {
    paramCanvas.rotate(this.mBubbleAngle);
    paramCanvas.translate(-this.mBubbleTriangleBaseSize / 2.0F + paramFloat2 / 2.0F - this.mShadowSize, this.mBubbleSize - paramFloat1 / 2.0F + this.mShadowSize);
    paramCanvas.rotate(-45.0F);
    float f = (this.mBubbleSize + this.mShadowSize) / SIN_BUBBLE_SIDE - this.mCornerRadius;
    paramCanvas.drawRect(0.0F, paramFloat1, f, -this.mCornerRadius, this.mEdgeShadowPaint);
    paramCanvas.translate(f, 0.0F);
    paramCanvas.rotate(90.0F);
    paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
    paramCanvas.drawRect(0.0F, paramFloat1, f, -this.mCornerRadius, this.mEdgeShadowPaint);
  }
  
  protected void buildComponents(Rect paramRect)
  {
    this.mCardBounds.set(paramRect);
    this.mCardBounds.inset(this.mInset, this.mInset);
    this.mBubbleTriangleBaseSize = (2.0F * (this.mCornerRadius + (this.mBubbleSize - this.mCornerRadius) / TAN_BUBBLE_SIDE));
    int i = this.mBubbleOffset;
    int j = (int)this.mBubbleTriangleBaseSize / 2;
    label99:
    int m;
    if ((this.mBubbleGravity == 48) || (this.mBubbleGravity == 80)) {
      if (this.mBubblePosition == 0)
      {
        i = (int)(i - this.mCardBounds.width() / 2.0F);
        int k = (int)this.mCardBounds.width() / 2 - j;
        m = j + -((int)this.mCardBounds.width() / 2);
        if (k >= i) {
          break label219;
        }
        i = k;
      }
    }
    for (;;)
    {
      switch (this.mBubbleGravity)
      {
      default: 
        throw new IllegalArgumentException("Bubble gravity can only be on of TOP, BOTTOM, LEFT or RIGHT");
        if (this.mBubblePosition != 2) {
          break label99;
        }
        i = (int)(i + this.mCardBounds.width() / 2.0F);
        break label99;
        label219:
        if (m > i)
        {
          i = m;
          continue;
          if ((this.mBubbleGravity == 3) || (this.mBubbleGravity == 5))
          {
            if (this.mBubblePosition == 0) {
              i = (int)(i - this.mCardBounds.height() / 2.0F);
            }
            int i1;
            for (;;)
            {
              int n = (int)this.mCardBounds.height() / 2 - j;
              i1 = j + -((int)this.mCardBounds.height() / 2);
              if (n >= i) {
                break label334;
              }
              i = n;
              break;
              if (this.mBubblePosition == 2) {
                i = (int)(i + this.mCardBounds.height() / 2.0F);
              }
            }
            if (i1 > i) {
              i = i1;
            }
          }
        }
        label334:
        break;
      }
    }
    this.mBubbleAngle = 0.0F;
    RectF localRectF6 = this.mCardBounds;
    localRectF6.top += this.mBubbleSize;
    this.mBubbleCenterY = (this.mCardBounds.top - this.mBubbleSize / 2.0F);
    this.mBubbleCenterX = (this.mCardBounds.left + this.mCardBounds.width() / 2.0F + i);
    for (;;)
    {
      this.mBubbleRotationMatrix.reset();
      this.mBubbleRotationMatrix.setRotate(this.mBubbleAngle);
      RectF localRectF2 = new RectF(-this.mCornerRadius, -this.mCornerRadius, this.mCornerRadius, this.mCornerRadius);
      RectF localRectF3 = new RectF(localRectF2);
      localRectF3.inset(-this.mShadowSize, -this.mShadowSize);
      this.mCornerShadowPath.reset();
      this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
      this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0F);
      this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0F);
      this.mCornerShadowPath.arcTo(localRectF3, 180.0F, 90.0F, false);
      this.mCornerShadowPath.arcTo(localRectF2, 270.0F, -90.0F, false);
      this.mCornerShadowPath.close();
      float f1 = this.mCornerRadius / (this.mCornerRadius + this.mShadowSize);
      if (this.mCornerRadius + this.mShadowSize > 0.0F)
      {
        Paint localPaint2 = this.mCornerShadowPaint;
        float f7 = this.mCornerRadius + this.mShadowSize;
        int[] arrayOfInt2 = new int[3];
        arrayOfInt2[0] = this.mShadowStartColor;
        arrayOfInt2[1] = this.mShadowStartColor;
        arrayOfInt2[2] = this.mShadowEndColor;
        localPaint2.setShader(new RadialGradient(0.0F, 0.0F, f7, arrayOfInt2, new float[] { 0.0F, f1, 1.0F }, Shader.TileMode.CLAMP));
      }
      Paint localPaint1 = this.mEdgeShadowPaint;
      float f2 = -this.mCornerRadius + this.mShadowSize;
      float f3 = -this.mCornerRadius - this.mShadowSize;
      int[] arrayOfInt1 = new int[3];
      arrayOfInt1[0] = this.mShadowStartColor;
      arrayOfInt1[1] = this.mShadowStartColor;
      arrayOfInt1[2] = this.mShadowEndColor;
      localPaint1.setShader(new LinearGradient(0.0F, f2, 0.0F, f3, arrayOfInt1, new float[] { 0.0F, 0.5F, 1.0F }, Shader.TileMode.CLAMP));
      if (sCornerRect == null) {
        sCornerRect = new RectF();
      }
      if (sArrowTipCornerRect == null) {
        sArrowTipCornerRect = new RectF();
      }
      this.mBubblePath.reset();
      this.mBubblePath.setFillType(Path.FillType.EVEN_ODD);
      float f4 = 1.0F + this.mBubbleSize / 2.0F;
      float f5 = this.mShadowSize / 2.0F + (f4 - this.mBubbleSize - 1.0F);
      float f6 = -this.mBubbleTriangleBaseSize / 2.0F;
      sArrowTipCornerRect.set(-this.mCornerRadius, f5, this.mCornerRadius, f5 + 2.0F * this.mCornerRadius);
      this.mBubblePath.moveTo(f6, f4);
      this.mBubblePath.lineTo(sArrowTipCornerRect.left, sArrowTipCornerRect.top + this.mCornerRadius / 2.0F);
      this.mBubblePath.arcTo(sArrowTipCornerRect, 225.0F, 90.0F, false);
      this.mBubblePath.lineTo(f6 + this.mBubbleTriangleBaseSize, f4);
      this.mBubblePath.close();
      if (this.mBubbleAngle != 0.0F) {
        this.mBubblePath.transform(this.mBubbleRotationMatrix);
      }
      return;
      this.mBubbleAngle = -90.0F;
      RectF localRectF5 = this.mCardBounds;
      localRectF5.left += this.mBubbleSize;
      this.mBubbleCenterX = (this.mCardBounds.left - this.mBubbleSize / 2.0F);
      this.mBubbleCenterY = (this.mCardBounds.top + this.mCardBounds.height() / 2.0F + i);
      continue;
      this.mBubbleAngle = 90.0F;
      RectF localRectF4 = this.mCardBounds;
      localRectF4.right -= this.mBubbleSize;
      this.mBubbleCenterX = (this.mCardBounds.right + this.mBubbleSize / 2.0F);
      this.mBubbleCenterY = (this.mCardBounds.top + this.mCardBounds.height() / 2.0F + i);
      continue;
      this.mBubbleAngle = 180.0F;
      RectF localRectF1 = this.mCardBounds;
      localRectF1.bottom -= this.mBubbleSize;
      this.mBubbleCenterY = (this.mCardBounds.bottom + this.mBubbleSize / 2.0F);
      this.mBubbleCenterX = (this.mCardBounds.left + this.mCardBounds.width() / 2.0F + i);
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.mIsDirty)
    {
      buildComponents(getBounds());
      this.mIsDirty = false;
    }
    paramCanvas.translate(0.0F, this.mRawShadowSize / 2.0F);
    float f1 = -this.mCornerRadius - this.mShadowSize;
    float f2 = this.mCornerRadius + this.mRawShadowSize / 2.0F;
    int i;
    int j;
    if (this.mCardBounds.width() - 2.0F * f2 > 0.0F)
    {
      i = 1;
      if (this.mCardBounds.height() - 2.0F * f2 <= 0.0F) {
        break label682;
      }
      j = 1;
      label92:
      int k = paramCanvas.save();
      paramCanvas.translate(f2 + this.mCardBounds.left, f2 + this.mCardBounds.top);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (i != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.width() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(k);
      if (this.mBubbleGravity == 48)
      {
        int i6 = paramCanvas.save();
        paramCanvas.translate(this.mBubbleCenterX, f2 + this.mShadowSize);
        drawBubbleShadow(paramCanvas, f1, f2);
        paramCanvas.restoreToCount(i6);
      }
      int m = paramCanvas.save();
      paramCanvas.translate(this.mCardBounds.right - f2, this.mCardBounds.bottom - f2);
      paramCanvas.rotate(180.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (i != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.width() - 2.0F * f2, -this.mCornerRadius + this.mShadowSize, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(m);
      if (this.mBubbleGravity == 5)
      {
        int i5 = paramCanvas.save();
        paramCanvas.translate(this.mCardBounds.right + this.mBubbleSize, this.mBubbleCenterY);
        drawBubbleShadow(paramCanvas, f1, f2);
        paramCanvas.restoreToCount(i5);
      }
      int n = paramCanvas.save();
      paramCanvas.translate(f2 + this.mCardBounds.left, this.mCardBounds.bottom - f2);
      paramCanvas.rotate(270.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (j != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.height() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(n);
      if (this.mBubbleGravity == 3)
      {
        int i4 = paramCanvas.save();
        paramCanvas.translate(this.mInset, this.mBubbleCenterY);
        drawBubbleShadow(paramCanvas, f1, f2);
        paramCanvas.restoreToCount(i4);
      }
      int i1 = paramCanvas.save();
      paramCanvas.translate(this.mCardBounds.right - f2, f2 + this.mCardBounds.top);
      paramCanvas.rotate(90.0F);
      paramCanvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
      if (j != 0) {
        paramCanvas.drawRect(0.0F, f1, this.mCardBounds.height() - 2.0F * f2, -this.mCornerRadius, this.mEdgeShadowPaint);
      }
      paramCanvas.restoreToCount(i1);
      if (this.mBubbleGravity == 80)
      {
        int i3 = paramCanvas.save();
        paramCanvas.translate(this.mBubbleCenterX, this.mCardBounds.bottom + this.mBubbleSize);
        drawBubbleShadow(paramCanvas, f1, f2);
        paramCanvas.restoreToCount(i3);
      }
      paramCanvas.translate(0.0F, -this.mRawShadowSize / 2.0F);
      if (Build.VERSION.SDK_INT < 17) {
        break label688;
      }
      paramCanvas.drawRoundRect(this.mCardBounds, this.mCornerRadius, this.mCornerRadius, this.mPaint);
    }
    for (;;)
    {
      int i2 = paramCanvas.save();
      paramCanvas.translate(this.mBubbleCenterX, this.mBubbleCenterY);
      paramCanvas.drawPath(this.mBubblePath, this.mPaint);
      paramCanvas.restoreToCount(i2);
      return;
      i = 0;
      break;
      label682:
      j = 0;
      break label92;
      label688:
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
  }
  
  public final float getBubbleSize()
  {
    return this.mBubbleSize;
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mIsDirty = true;
  }
  
  public void setBounds(Rect paramRect)
  {
    super.setBounds(paramRect);
    this.mIsDirty = true;
  }
  
  public final void setBubbleGravity(int paramInt)
  {
    this.mBubbleGravity = paramInt;
    this.mIsDirty = true;
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.CardBubbleDrawableWithShadow
 * JD-Core Version:    0.7.0.1
 */