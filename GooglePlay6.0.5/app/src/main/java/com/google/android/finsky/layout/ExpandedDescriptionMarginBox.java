package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.finsky.utils.UiUtils;

public class ExpandedDescriptionMarginBox
  extends FrameLayout
{
  private DetailsExpandedFrame mDetailsExpandedFrame;
  private final boolean mUseWideLayout;
  
  public ExpandedDescriptionMarginBox(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ExpandedDescriptionMarginBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mUseWideLayout = paramContext.getResources().getBoolean(2131427334);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mDetailsExpandedFrame = ((DetailsExpandedFrame)findViewById(2131755577));
    this.mDetailsExpandedFrame.setVisibility(0);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    if (this.mUseWideLayout) {}
    for (int k = UiUtils.getGridColumnContentWidth(getResources());; k = i)
    {
      int m = Math.min(i, k);
      this.mDetailsExpandedFrame.setScrollerWidth(m);
      int n = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
      this.mDetailsExpandedFrame.measure(paramInt1, n);
      setMeasuredDimension(i, j);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.ExpandedDescriptionMarginBox
 * JD-Core Version:    0.7.0.1
 */