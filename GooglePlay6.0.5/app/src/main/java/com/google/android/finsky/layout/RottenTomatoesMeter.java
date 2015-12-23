package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.play.utils.PlayUtils;

public class RottenTomatoesMeter
  extends View
{
  private final int mAccentFillColor;
  private final int mBackgroundFillColor;
  private int mCurrentPercentValue;
  private final Paint mFillPaint;
  
  public RottenTomatoesMeter(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public RottenTomatoesMeter(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    this.mBackgroundFillColor = localResources.getColor(2131689637);
    this.mAccentFillColor = localResources.getColor(2131689639);
    this.mFillPaint = new Paint();
    this.mFillPaint.setStyle(Paint.Style.FILL);
    this.mFillPaint.setAntiAlias(true);
    setWillNotDraw(false);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    int i = getWidth();
    int j = getHeight();
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int k = i * this.mCurrentPercentValue / 100;
      if (k > 0)
      {
        int i1 = PlayUtils.getViewLeftFromParentStart(i, k, bool, 0);
        this.mFillPaint.setColor(this.mAccentFillColor);
        paramCanvas.drawRect(i1, 0.0F, i1 + k, j, this.mFillPaint);
      }
      int m = i - k;
      if (m > 0)
      {
        int n = PlayUtils.getViewLeftFromParentEnd(i, m, bool, 0);
        this.mFillPaint.setColor(this.mBackgroundFillColor);
        paramCanvas.drawRect(n, 0.0F, n + m, j, this.mFillPaint);
      }
      return;
    }
  }
  
  public void setPercentValue(int paramInt)
  {
    if (this.mCurrentPercentValue != paramInt)
    {
      this.mCurrentPercentValue = paramInt;
      invalidate();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.RottenTomatoesMeter
 * JD-Core Version:    0.7.0.1
 */