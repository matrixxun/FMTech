package com.google.android.play.cardview;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public abstract class CardViewBackgroundDrawable
  extends Drawable
{
  protected ColorStateList mColorStateList;
  protected float mCornerRadius;
  private int mDefaultColor;
  protected float mInset;
  protected Paint mPaint;
  
  CardViewBackgroundDrawable(ColorStateList paramColorStateList, float paramFloat1, float paramFloat2)
  {
    this.mCornerRadius = paramFloat1;
    this.mColorStateList = paramColorStateList;
    this.mDefaultColor = this.mColorStateList.getDefaultColor();
    this.mPaint = new Paint(5);
    this.mPaint.setColor(this.mDefaultColor);
    this.mInset = paramFloat2;
  }
  
  public int getOpacity()
  {
    return -1;
  }
  
  public boolean isStateful()
  {
    return (this.mColorStateList != null) && (this.mColorStateList.isStateful());
  }
  
  protected boolean onStateChange(int[] paramArrayOfInt)
  {
    if ((this.mColorStateList != null) && (this.mColorStateList.isStateful()))
    {
      this.mPaint.setColor(this.mColorStateList.getColorForState(paramArrayOfInt, this.mDefaultColor));
      invalidateSelf();
      return true;
    }
    return super.onStateChange(paramArrayOfInt);
  }
  
  public final void setBackgroundColor(int paramInt)
  {
    this.mColorStateList = null;
    this.mPaint.setColor(paramInt);
    invalidateSelf();
  }
  
  public final void setBackgroundColorStateList(ColorStateList paramColorStateList)
  {
    this.mColorStateList = paramColorStateList;
    this.mPaint.setColor(this.mColorStateList.getColorForState(getState(), this.mColorStateList.getDefaultColor()));
    invalidateSelf();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.cardview.CardViewBackgroundDrawable
 * JD-Core Version:    0.7.0.1
 */