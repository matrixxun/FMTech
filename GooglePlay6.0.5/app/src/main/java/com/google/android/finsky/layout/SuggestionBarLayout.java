package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.utils.Utils;

public class SuggestionBarLayout
  extends IdentifiableRelativeLayout
{
  private boolean mFitsInOneLine;
  private int mHeaderHeight = getResources().getDimensionPixelSize(2131493076);
  private int mLayoutHeight;
  private int mSuggestionBarUnderlinePadding = getResources().getDimensionPixelSize(2131493523);
  private int mSuggestionBarVerticalPadding = getResources().getDimensionPixelSize(2131493524);
  private TextView mSuggestionLine1;
  private TextView mSuggestionLine2;
  private LinearLayout mSuggestionLineFull;
  private TextView mSuggestionLineQuery;
  private TextView mSuggestionLineText;
  private View mSuggestionUnderline;
  
  public SuggestionBarLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SuggestionBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSuggestionLineFull = ((LinearLayout)findViewById(2131756150));
    this.mSuggestionLineText = ((TextView)findViewById(2131756151));
    this.mSuggestionLineQuery = ((TextView)findViewById(2131756152));
    this.mSuggestionLine1 = ((TextView)findViewById(2131756153));
    this.mSuggestionLine2 = ((TextView)findViewById(2131756154));
    this.mSuggestionUnderline = findViewById(2131756155);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getWidth();
    int j = getHeight();
    int k = getPaddingLeft();
    int m = this.mSuggestionLineFull.getMeasuredWidth();
    int n = this.mSuggestionLine1.getMeasuredWidth();
    int i1 = this.mSuggestionLine2.getMeasuredWidth();
    int i2 = (j - this.mSuggestionUnderline.getMeasuredHeight()) / 2;
    if (this.mFitsInOneLine)
    {
      this.mSuggestionLineFull.setVisibility(0);
      this.mSuggestionLine1.setVisibility(8);
      this.mSuggestionLine2.setVisibility(8);
      int i7 = this.mSuggestionLineFull.getMeasuredHeight();
      this.mSuggestionLineFull.layout(k, i2 - i7 / 2, k + m, i2 + i7 / 2);
    }
    for (;;)
    {
      this.mSuggestionUnderline.layout(this.mSuggestionBarUnderlinePadding, this.mLayoutHeight - this.mSuggestionUnderline.getMeasuredHeight(), i - this.mSuggestionBarUnderlinePadding, this.mLayoutHeight);
      return;
      this.mSuggestionLineFull.setVisibility(8);
      this.mSuggestionLine1.setVisibility(0);
      this.mSuggestionLine2.setVisibility(0);
      int i3 = this.mSuggestionLine1.getMeasuredHeight();
      int i4 = this.mSuggestionLine2.getMeasuredHeight();
      int i5 = i2 - (i3 + i4) / 2;
      int i6 = i5 + i3;
      this.mSuggestionLine1.layout(k, i5, k + n, i6);
      this.mSuggestionLine2.layout(k, i6, k + i1, i6 + i4);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = i - j - k;
    int n = View.MeasureSpec.makeMeasureSpec(m, -2147483648);
    this.mSuggestionLineFull.measure(0, 0);
    this.mSuggestionLine1.measure(n, 0);
    this.mSuggestionLine2.measure(n, 0);
    this.mSuggestionUnderline.measure(0, View.MeasureSpec.makeMeasureSpec(this.mSuggestionUnderline.getLayoutParams().height, 1073741824));
    int i1 = this.mSuggestionLineFull.getMeasuredWidth();
    boolean bool = false;
    if (i1 <= m) {
      bool = true;
    }
    this.mFitsInOneLine = bool;
    if (this.mFitsInOneLine) {}
    for (int i2 = this.mSuggestionLineFull.getMeasuredHeight();; i2 = this.mSuggestionLine1.getMeasuredHeight() + this.mSuggestionLine2.getMeasuredHeight())
    {
      this.mLayoutHeight = i2;
      this.mLayoutHeight += 2 * this.mSuggestionBarVerticalPadding;
      this.mLayoutHeight += this.mSuggestionUnderline.getMeasuredHeight();
      if (this.mLayoutHeight < this.mHeaderHeight) {
        this.mLayoutHeight = this.mHeaderHeight;
      }
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), this.mLayoutHeight);
      return;
    }
  }
  
  public final void setDisplayLine(String paramString1, String paramString2)
  {
    String str = Utils.getItalicSafeString(paramString2);
    this.mSuggestionLine1.setText(paramString1);
    this.mSuggestionLine2.setText(str);
    this.mSuggestionLine2.setSelected(true);
    this.mSuggestionLineText.setText(paramString1);
    this.mSuggestionLineQuery.setText(str);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.SuggestionBarLayout
 * JD-Core Version:    0.7.0.1
 */