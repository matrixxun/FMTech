package com.google.android.finsky.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReviewsTipHeaderLayout
  extends LinearLayout
{
  boolean mFitsInOneLine;
  private View mTipHeaderFullLine;
  private TextView mTipInfoFullLine;
  private TextView mTipInfoLine2;
  private TextView mTipTitleFullLine;
  private TextView mTipTitleLine1;
  public boolean mUseTwoLineLayout;
  
  public ReviewsTipHeaderLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ReviewsTipHeaderLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTipHeaderFullLine = findViewById(2131756072);
    this.mTipTitleFullLine = ((TextView)findViewById(2131756073));
    this.mTipInfoFullLine = ((TextView)findViewById(2131756074));
    this.mTipTitleLine1 = ((TextView)findViewById(2131756075));
    this.mTipInfoLine2 = ((TextView)findViewById(2131756076));
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    if (this.mFitsInOneLine)
    {
      int i4 = this.mTipHeaderFullLine.getMeasuredWidth();
      this.mTipHeaderFullLine.setVisibility(0);
      this.mTipTitleLine1.setVisibility(8);
      this.mTipInfoLine2.setVisibility(8);
      int i5 = this.mTipHeaderFullLine.getMeasuredHeight();
      int i6 = getPaddingLeft();
      int i7 = getPaddingTop();
      this.mTipHeaderFullLine.layout(i6, i7, i6 + i4, i7 + i5);
      return;
    }
    this.mTipHeaderFullLine.setVisibility(8);
    this.mTipTitleLine1.setVisibility(0);
    this.mTipInfoLine2.setVisibility(0);
    int j = this.mTipTitleLine1.getMeasuredHeight();
    int k = this.mTipInfoLine2.getMeasuredHeight();
    int m = getPaddingTop();
    int n = m + j;
    int i1 = (i - getPaddingLeft() - getPaddingRight()) / 2;
    int i2 = i1 - this.mTipTitleLine1.getMeasuredWidth() / 2;
    int i3 = i1 - this.mTipInfoLine2.getMeasuredWidth() / 2;
    this.mTipTitleLine1.layout(i2, m, i2 + i, n);
    this.mTipInfoLine2.layout(i3, n, i3 + i, n + k);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = i - j - k;
    int n = View.MeasureSpec.makeMeasureSpec(m, -2147483648);
    if (!this.mUseTwoLineLayout) {
      this.mTipHeaderFullLine.measure(0, 0);
    }
    int i1 = this.mTipHeaderFullLine.getMeasuredWidth();
    boolean bool;
    if ((!this.mUseTwoLineLayout) && (i1 <= m))
    {
      bool = true;
      this.mFitsInOneLine = bool;
      if (!this.mFitsInOneLine) {
        break label152;
      }
      this.mTipHeaderFullLine.measure(View.MeasureSpec.makeMeasureSpec(m, 1073741824), 0);
      label105:
      if (!this.mFitsInOneLine) {
        break label175;
      }
    }
    label152:
    label175:
    for (int i2 = this.mTipHeaderFullLine.getMeasuredHeight();; i2 = this.mTipTitleLine1.getMeasuredHeight() + this.mTipInfoLine2.getMeasuredHeight())
    {
      int i3 = i2 + (getPaddingTop() + getPaddingBottom());
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), i3);
      return;
      bool = false;
      break;
      this.mTipTitleLine1.measure(n, 0);
      this.mTipInfoLine2.measure(n, 0);
      break label105;
    }
  }
  
  public final void setTipInfo(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    this.mTipTitleFullLine.setText(paramCharSequence1);
    this.mTipTitleLine1.setText(paramCharSequence1);
    if (!TextUtils.isEmpty(paramCharSequence2))
    {
      this.mTipInfoFullLine.setVisibility(0);
      this.mTipInfoFullLine.setText(paramCharSequence2);
      this.mTipInfoLine2.setVisibility(0);
      this.mTipInfoLine2.setText(paramCharSequence2);
      return;
    }
    this.mTipInfoFullLine.setVisibility(4);
    this.mTipInfoLine2.setVisibility(4);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ReviewsTipHeaderLayout
 * JD-Core Version:    0.7.0.1
 */