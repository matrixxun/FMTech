package com.google.android.finsky.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class DetailsSummaryDynamic
  extends ViewGroup
{
  private View mButtonContainer;
  private View mDownloadProgressPanel;
  private View mSummaryStatus;
  
  public DetailsSummaryDynamic(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsSummaryDynamic(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mButtonContainer = findViewById(2131755406);
    this.mDownloadProgressPanel = findViewById(2131755407);
    this.mSummaryStatus = findViewById(2131755408);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = getChildAt(j);
      if (localView.getVisibility() != 8) {
        localView.layout(0, 0, localView.getMeasuredWidth(), localView.getMeasuredHeight());
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    this.mButtonContainer.measure(paramInt1, paramInt2);
    int j = this.mButtonContainer.getMeasuredWidth();
    int k = this.mButtonContainer.getMeasuredHeight();
    int m = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
    int n;
    int i1;
    if ((this.mDownloadProgressPanel.getVisibility() == 0) && (i == 1073741824))
    {
      this.mDownloadProgressPanel.measure(paramInt1, paramInt2);
      n = Math.max(j, this.mDownloadProgressPanel.getMeasuredWidth());
      i1 = Math.max(k, this.mDownloadProgressPanel.getMeasuredHeight());
      if ((this.mSummaryStatus.getVisibility() != 0) || (i != 1073741824)) {
        break label160;
      }
      this.mSummaryStatus.measure(paramInt1, paramInt2);
    }
    for (;;)
    {
      setMeasuredDimension(Math.max(n, this.mSummaryStatus.getMeasuredWidth()), Math.max(i1, this.mSummaryStatus.getMeasuredHeight()));
      return;
      this.mDownloadProgressPanel.measure(m, paramInt2);
      break;
      label160:
      this.mSummaryStatus.measure(m, paramInt2);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsSummaryDynamic
 * JD-Core Version:    0.7.0.1
 */