package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ErrorFooter
  extends IdentifiableViewGroup
{
  private TextView mErrorMessage;
  private Button mRetryButton;
  
  public ErrorFooter(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ErrorFooter(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configure(String paramString, View.OnClickListener paramOnClickListener)
  {
    this.mErrorMessage.setText(paramString);
    this.mRetryButton.setText(2131362364);
    this.mRetryButton.setOnClickListener(paramOnClickListener);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mErrorMessage = ((TextView)findViewById(2131755481));
    this.mRetryButton = ((Button)findViewById(2131755482));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = getPaddingLeft();
    int m = getPaddingRight();
    int n = getPaddingTop();
    int i1 = getPaddingBottom();
    int i2 = this.mRetryButton.getMeasuredWidth();
    int i3 = this.mRetryButton.getMeasuredHeight();
    int i4 = this.mErrorMessage.getMeasuredWidth();
    int i5 = this.mErrorMessage.getMeasuredHeight();
    int i6 = n + (j - n - i1 - i3) / 2;
    this.mRetryButton.layout(i - m - i2, i6, i - m, i6 + i3);
    int i7 = n + (j - n - i1 - i5) / 2;
    int i8 = k + (i - k - m - i2 - i4) / 2;
    this.mErrorMessage.layout(i8, i7, i8 + i4, i7 + i5);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = i - j - k;
    this.mRetryButton.measure(View.MeasureSpec.makeMeasureSpec(m / 2, -2147483648), 0);
    this.mErrorMessage.measure(View.MeasureSpec.makeMeasureSpec(m - this.mRetryButton.getMeasuredWidth(), -2147483648), 0);
    setMeasuredDimension(i, getPaddingTop() + getPaddingBottom() + Math.max(this.mErrorMessage.getMeasuredHeight(), this.mRetryButton.getMeasuredHeight()));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ErrorFooter
 * JD-Core Version:    0.7.0.1
 */