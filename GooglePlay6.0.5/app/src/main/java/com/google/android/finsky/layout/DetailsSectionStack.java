package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.vending.R.styleable;

public class DetailsSectionStack
  extends LinearLayout
{
  private final int mHalfSeparatorThickness;
  private final int mMaxSectionSeparatorAlpha;
  private float mSectionSeparatorAlphaMultiplier;
  private final int mSectionSeparatorColor;
  private final int mSectionSeparatorInset;
  private final Paint mSectionSeparatorPaint;
  private boolean mShowSeparators;
  
  public DetailsSectionStack(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsSectionStack(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.DetailsSectionStack);
    this.mSectionSeparatorInset = localTypedArray.getDimensionPixelSize(0, 0);
    localTypedArray.recycle();
    this.mSectionSeparatorPaint = new Paint();
    this.mSectionSeparatorColor = localResources.getColor(2131689680);
    this.mMaxSectionSeparatorAlpha = Color.alpha(this.mSectionSeparatorColor);
    this.mSectionSeparatorPaint.setColor(this.mSectionSeparatorColor);
    int i = localResources.getDimensionPixelSize(2131493415);
    this.mHalfSeparatorThickness = ((-1 + (i + 2)) / 2);
    this.mSectionSeparatorPaint.setStrokeWidth(i);
    this.mShowSeparators = true;
    this.mSectionSeparatorAlphaMultiplier = 1.0F;
    setWillNotDraw(false);
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    if (!this.mShowSeparators) {}
    Object localObject;
    label82:
    do
    {
      return;
      int i = 1;
      boolean bool = false;
      int j = getChildCount();
      localObject = null;
      int k = 0;
      if (k < j)
      {
        View localView = getChildAt(k);
        if (localView.getVisibility() == 0)
        {
          localObject = localView;
          if ((!bool) && (!(localView instanceof NoTopSeparator))) {
            break label82;
          }
          bool = localView instanceof NoBottomSeparator;
        }
        for (i = 0;; i = 0)
        {
          k++;
          break;
          if (i == 0)
          {
            int n = localView.getTop();
            paramCanvas.drawLine(localView.getLeft() + this.mSectionSeparatorInset, n, localView.getRight() - this.mSectionSeparatorInset, n, this.mSectionSeparatorPaint);
          }
          bool = localView instanceof NoBottomSeparator;
        }
      }
    } while (!(localObject instanceof NeedsTrailingSeparator));
    int m = localObject.getBottom() - this.mHalfSeparatorThickness;
    paramCanvas.drawLine(localObject.getLeft() + this.mSectionSeparatorInset, m, localObject.getRight() - this.mSectionSeparatorInset, m, this.mSectionSeparatorPaint);
  }
  
  public void setSectionSeparatorAlphaMultiplier(float paramFloat)
  {
    if (this.mSectionSeparatorAlphaMultiplier != paramFloat)
    {
      this.mSectionSeparatorAlphaMultiplier = paramFloat;
      int i = (int)(this.mSectionSeparatorAlphaMultiplier * this.mMaxSectionSeparatorAlpha) << 24 | 0xFFFFFF & this.mSectionSeparatorColor;
      this.mSectionSeparatorPaint.setColor(i);
      invalidate();
    }
  }
  
  public void setSeparatorsVisible(boolean paramBoolean)
  {
    if (this.mShowSeparators != paramBoolean)
    {
      this.mShowSeparators = paramBoolean;
      invalidate();
    }
  }
  
  public static abstract interface NeedsTrailingSeparator {}
  
  public static abstract interface NoBottomSeparator {}
  
  public static abstract interface NoTopSeparator {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsSectionStack
 * JD-Core Version:    0.7.0.1
 */