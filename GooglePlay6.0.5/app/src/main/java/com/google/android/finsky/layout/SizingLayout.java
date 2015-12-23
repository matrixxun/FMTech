package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.android.vending.R.styleable;
import com.google.android.finsky.utils.FinskyLog;

public class SizingLayout
  extends FrameLayout
{
  private final float mHeightMultiplier;
  private int mHeightSource;
  private final int mMaxHeight;
  private final int mMaxWidth;
  private final float mWidthMultiplier;
  private int mWidthSource;
  
  public SizingLayout(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public SizingLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SizingLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SizingLayout);
    setWidthSource(parseDimension(localTypedArray.getString(0)));
    setHeightSource(parseDimension(localTypedArray.getString(1)));
    this.mWidthMultiplier = localTypedArray.getFloat(2, 1.0F);
    this.mHeightMultiplier = localTypedArray.getFloat(3, 1.0F);
    this.mMaxWidth = localTypedArray.getDimensionPixelSize(4, 2147483647);
    this.mMaxHeight = localTypedArray.getDimensionPixelSize(5, 2147483647);
    localTypedArray.recycle();
  }
  
  private int computeMeasureSpec(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    if (this.mMaxWidth < View.MeasureSpec.getSize(paramInt1)) {
      paramInt1 = View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, View.MeasureSpec.getMode(paramInt1));
    }
    if (this.mMaxHeight < View.MeasureSpec.getSize(paramInt2)) {
      paramInt2 = View.MeasureSpec.makeMeasureSpec(this.mMaxHeight, View.MeasureSpec.getMode(paramInt2));
    }
    int i;
    int j;
    label63:
    int k;
    label77:
    int m;
    float f;
    label115:
    int i2;
    label134:
    int i3;
    if (paramBoolean)
    {
      i = this.mWidthSource;
      if (!paramBoolean) {
        break label199;
      }
      j = paramInt1;
      if (i != 0)
      {
        if (i != 1) {
          break label205;
        }
        k = paramInt1;
        m = View.MeasureSpec.getMode(k);
        if ((m == 1073741824) || (m == -2147483648))
        {
          int n = View.MeasureSpec.getSize(k);
          if (!paramBoolean) {
            break label211;
          }
          f = this.mWidthMultiplier;
          int i1 = (int)(f * n);
          if (!paramBoolean) {
            break label220;
          }
          i2 = ViewCompat.getMinimumWidth(this);
          i3 = Math.max(i1, i2);
          if (i != 1) {
            break label229;
          }
        }
      }
    }
    label199:
    label205:
    label211:
    label220:
    label229:
    for (int i4 = paramInt2;; i4 = paramInt1)
    {
      int i5 = View.MeasureSpec.getSize(i4);
      if (View.MeasureSpec.getMode(i4) == -2147483648) {
        i3 = Math.min(i3, i5);
      }
      j = View.MeasureSpec.makeMeasureSpec(i3, m);
      return j;
      i = this.mHeightSource;
      break;
      j = paramInt2;
      break label63;
      k = paramInt2;
      break label77;
      f = this.mHeightMultiplier;
      break label115;
      i2 = ViewCompat.getMinimumHeight(this);
      break label134;
    }
  }
  
  private static int parseDimension(String paramString)
  {
    if (paramString == null) {
      return 0;
    }
    if (paramString.toUpperCase().equals("WIDTH")) {
      return 1;
    }
    if (paramString.toUpperCase().equals("HEIGHT")) {
      return 2;
    }
    FinskyLog.wtf("Unsupported SizingLayout dimension " + paramString, new Object[0]);
    return 0;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = computeMeasureSpec(true, paramInt1, paramInt2);
    super.onMeasure(i, computeMeasureSpec(false, i, paramInt2));
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    super.onRtlPropertiesChanged(paramInt);
    requestLayout();
  }
  
  public void setHeightSource(int paramInt)
  {
    this.mHeightSource = paramInt;
  }
  
  public void setWidthSource(int paramInt)
  {
    this.mWidthSource = paramInt;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SizingLayout
 * JD-Core Version:    0.7.0.1
 */