package com.google.android.play.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

public final class PulsatingDotDrawable
  extends Drawable
  implements Animatable
{
  protected boolean mIsAnimating;
  protected final float mMinRadius;
  protected final long mOffsetMs;
  protected final Paint mPaint;
  protected final long mPeriodMs = 800L;
  protected final float mRadiusSpan;
  protected long mStartMs;
  
  public PulsatingDotDrawable(int paramInt, long paramLong)
  {
    this.mOffsetMs = paramLong;
    this.mMinRadius = Math.max(0.0F, 0.1F);
    this.mRadiusSpan = (Math.min(1.0F, 1.0F) - 0.1F);
    this.mPaint = new Paint();
    this.mPaint.setAntiAlias(true);
    this.mPaint.setColor(paramInt);
    this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
  }
  
  public final void draw(Canvas paramCanvas)
  {
    int i = paramCanvas.getWidth();
    int j = paramCanvas.getHeight();
    paramCanvas.saveLayerAlpha(0.0F, 0.0F, i, j, 255, 31);
    float f1 = 0.5F + 0.5F * (float)Math.sin(6.283185307179586D * ((float)((System.currentTimeMillis() + this.mOffsetMs) % this.mPeriodMs) / (float)this.mPeriodMs));
    float f2 = (this.mMinRadius + f1 * this.mRadiusSpan) * Math.min(i, j) / 2.0F;
    paramCanvas.drawCircle(i / 2.0F, j / 2.0F, f2, this.mPaint);
    paramCanvas.restore();
    if (isRunning()) {
      invalidateSelf();
    }
  }
  
  public final int getOpacity()
  {
    return -3;
  }
  
  public final boolean isRunning()
  {
    return this.mIsAnimating;
  }
  
  public final void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
  }
  
  public final void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
  }
  
  public final void start()
  {
    if (!isRunning())
    {
      this.mStartMs = System.currentTimeMillis();
      this.mIsAnimating = true;
      invalidateSelf();
    }
  }
  
  public final void stop()
  {
    this.mIsAnimating = false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.widget.PulsatingDotDrawable
 * JD-Core Version:    0.7.0.1
 */