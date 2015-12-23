package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.vending.R.styleable;

public class SeparatorLinearLayout
  extends LinearLayout
{
  private boolean mDrawSeparator;
  private final int mHalfSeparatorThickness;
  private final Paint mSeparatorPaint;
  private int mSeparatorPosition;
  
  public SeparatorLinearLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SeparatorLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setWillNotDraw(false);
    Resources localResources = paramContext.getResources();
    int j = localResources.getDimensionPixelSize(2131493415);
    this.mHalfSeparatorThickness = ((j + 1) / 2);
    this.mSeparatorPaint = new Paint();
    this.mSeparatorPaint.setColor(getSeparatorColor(localResources));
    this.mSeparatorPaint.setStrokeWidth(j);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SeparatorLinearLayout);
    if (!localTypedArray.getBoolean(i, false)) {}
    for (;;)
    {
      this.mDrawSeparator = i;
      this.mSeparatorPosition = localTypedArray.getInt(0, 2);
      localTypedArray.recycle();
      return;
      i = 0;
    }
  }
  
  protected int getSeparatorColor(Resources paramResources)
  {
    return paramResources.getColor(2131689680);
  }
  
  public final void hideSeparator()
  {
    if (this.mDrawSeparator)
    {
      this.mDrawSeparator = false;
      invalidate();
    }
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    if (this.mDrawSeparator)
    {
      if ((0x1 & this.mSeparatorPosition) != 0)
      {
        int j = this.mHalfSeparatorThickness;
        paramCanvas.drawLine(0.0F, j, getWidth(), j, this.mSeparatorPaint);
      }
      if ((0x2 & this.mSeparatorPosition) != 0)
      {
        int i = getHeight() - this.mHalfSeparatorThickness;
        paramCanvas.drawLine(0.0F, i, getWidth(), i, this.mSeparatorPaint);
      }
    }
    super.onDraw(paramCanvas);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SeparatorLinearLayout
 * JD-Core Version:    0.7.0.1
 */