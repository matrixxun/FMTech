package com.google.android.wallet.instrumentmanager.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.google.android.wallet.instrumentmanager.R.styleable;

public class InstrumentManagerRootLinearLayout
  extends LinearLayout
{
  private int mMaxWidthPixels;
  
  public InstrumentManagerRootLinearLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public InstrumentManagerRootLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  @TargetApi(11)
  public InstrumentManagerRootLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  @TargetApi(21)
  public InstrumentManagerRootLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  private void readAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WalletImMaxWidthView);
    this.mMaxWidthPixels = localTypedArray.getDimensionPixelSize(R.styleable.WalletImMaxWidthView_maxWidth, 0);
    localTypedArray.recycle();
  }
  
  public View focusSearch(View paramView, int paramInt)
  {
    return FocusFinder.getInstance().findNextFocus(this, paramView, paramInt);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    if ((this.mMaxWidthPixels > 0) && (this.mMaxWidthPixels < i))
    {
      int j = View.MeasureSpec.getMode(paramInt1);
      paramInt1 = View.MeasureSpec.makeMeasureSpec(this.mMaxWidthPixels, j);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.InstrumentManagerRootLinearLayout
 * JD-Core Version:    0.7.0.1
 */