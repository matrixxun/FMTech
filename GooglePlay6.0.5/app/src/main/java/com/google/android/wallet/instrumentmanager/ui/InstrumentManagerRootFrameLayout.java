package com.google.android.wallet.instrumentmanager.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.wallet.instrumentmanager.R.styleable;

public class InstrumentManagerRootFrameLayout
  extends FrameLayout
{
  private int mMaxWidthPixels;
  
  public InstrumentManagerRootFrameLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public InstrumentManagerRootFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  public InstrumentManagerRootFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
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
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.InstrumentManagerRootFrameLayout
 * JD-Core Version:    0.7.0.1
 */