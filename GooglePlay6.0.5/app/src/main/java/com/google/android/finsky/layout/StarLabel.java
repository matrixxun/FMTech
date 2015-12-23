package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class StarLabel
  extends View
{
  private int mHeight;
  private int mMaxStars;
  private int mNumStars;
  private final Drawable mStarDrawable;
  private final int mStarPadding;
  
  public StarLabel(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public StarLabel(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setNumStars(0);
    setWillNotDraw(false);
    Resources localResources = paramContext.getResources();
    this.mStarDrawable = ContextCompat.getDrawable(paramContext, 2130837889);
    this.mStarPadding = localResources.getDimensionPixelSize(2131493281);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mMaxStars <= 0) {}
    for (;;)
    {
      return;
      int i = this.mStarDrawable.getIntrinsicWidth();
      int j = getWidth();
      int k = (getHeight() - i) / 2;
      for (int m = 0; m < this.mNumStars; m++)
      {
        this.mStarDrawable.setBounds(j - i, k, j, k + i);
        this.mStarDrawable.draw(paramCanvas);
        j -= i + this.mStarPadding;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(this.mStarDrawable.getIntrinsicWidth() * this.mMaxStars + (-1 + this.mMaxStars) * this.mStarPadding, this.mHeight);
  }
  
  public void setMaxStars(int paramInt)
  {
    this.mMaxStars = paramInt;
  }
  
  public void setNumStars(int paramInt)
  {
    this.mNumStars = paramInt;
  }
  
  public void setStarHeight(int paramInt)
  {
    this.mHeight = paramInt;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.StarLabel
 * JD-Core Version:    0.7.0.1
 */