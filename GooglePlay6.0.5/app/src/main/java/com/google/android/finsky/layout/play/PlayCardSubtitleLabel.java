package com.google.android.finsky.layout.play;

import android.content.Context;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import com.google.android.play.utils.PlayUtils;

public class PlayCardSubtitleLabel
  extends RelativeLayout
{
  protected View mLabel;
  protected View mSubtitle;
  
  public PlayCardSubtitleLabel(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayCardSubtitleLabel(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSubtitle = findViewById(2131755563);
    this.mLabel = findViewById(2131755513);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = getWidth();
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
      int j = this.mSubtitle.getMeasuredWidth();
      int k = PlayUtils.getViewLeftFromParentStart(i, j, bool, MarginLayoutParamsCompat.getMarginStart(localMarginLayoutParams1));
      this.mSubtitle.layout(k, 0, k + j, this.mSubtitle.getMeasuredHeight());
      ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
      int m = this.mSubtitle.getBaseline() - this.mLabel.getBaseline();
      int n = this.mLabel.getMeasuredWidth();
      int i1 = PlayUtils.getViewLeftFromParentEnd(i, n, bool, MarginLayoutParamsCompat.getMarginEnd(localMarginLayoutParams2));
      this.mLabel.layout(i1, m, i1 + n, m + this.mLabel.getMeasuredHeight());
      return;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = i - getPaddingLeft() - getPaddingRight();
    this.mLabel.measure(View.MeasureSpec.makeMeasureSpec(j, -2147483648), 0);
    this.mSubtitle.measure(0, 0);
    ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mSubtitle.getLayoutParams();
    ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mLabel.getLayoutParams();
    int k = Math.min(this.mSubtitle.getMeasuredWidth(), i - localMarginLayoutParams1.leftMargin - localMarginLayoutParams1.rightMargin - this.mLabel.getMeasuredWidth() - localMarginLayoutParams2.leftMargin - localMarginLayoutParams2.rightMargin);
    this.mSubtitle.measure(View.MeasureSpec.makeMeasureSpec(k, 1073741824), 0);
    setMeasuredDimension(i, this.mSubtitle.getMeasuredHeight());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardSubtitleLabel
 * JD-Core Version:    0.7.0.1
 */