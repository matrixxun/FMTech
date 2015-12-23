package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.NumberFormat;

public class ReviewSnippetLayout
  extends LinearLayout
{
  public TextView mBody;
  public ReviewsTipHeaderLayout mHeader;
  public boolean mIsContentCentered;
  public final NumberFormat mNumberFormatter = NumberFormat.getIntegerInstance();
  
  public ReviewSnippetLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ReviewSnippetLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mBody = ((TextView)findViewById(2131756061));
    this.mHeader = ((ReviewsTipHeaderLayout)findViewById(2131756060));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1) - getPaddingLeft() - getPaddingRight();
    this.mHeader.measure(View.MeasureSpec.makeMeasureSpec(i, -2147483648), paramInt2);
    if (!this.mHeader.mFitsInOneLine) {
      this.mBody.setGravity(1);
    }
    for (this.mIsContentCentered = true;; this.mIsContentCentered = false)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
      this.mBody.setGravity(0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ReviewSnippetLayout
 * JD-Core Version:    0.7.0.1
 */