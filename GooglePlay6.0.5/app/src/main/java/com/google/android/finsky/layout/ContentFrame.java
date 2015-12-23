package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.vending.R.styleable;

public class ContentFrame
  extends InsetsFrameLayout
{
  private ViewGroup mDataLayout;
  private final LayoutInflater mInflater;
  
  public ContentFrame(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public ContentFrame(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ContentFrame(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    View localView1 = this.mInflater.inflate(2130968895, this, false);
    localView1.setVisibility(8);
    addView(localView1);
    View localView2 = this.mInflater.inflate(2130968894, this, false);
    localView2.setVisibility(8);
    addView(localView2);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ContentFrame);
    int i = localTypedArray.getResourceId(0, 0);
    int j = localTypedArray.getResourceId(1, 0);
    localTypedArray.recycle();
    if (i != 0) {
      addView(inflateDataLayout(this.mInflater, i, j));
    }
  }
  
  public final ViewGroup inflateDataLayout(LayoutInflater paramLayoutInflater, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0) {
      return null;
    }
    this.mDataLayout = ((ViewGroup)paramLayoutInflater.inflate(paramInt1, this, false));
    if (paramInt2 != 0) {
      this.mDataLayout.setId(paramInt2);
    }
    return this.mDataLayout;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ContentFrame
 * JD-Core Version:    0.7.0.1
 */