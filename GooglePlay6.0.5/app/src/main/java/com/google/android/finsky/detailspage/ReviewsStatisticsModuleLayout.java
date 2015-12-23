package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.finsky.layout.ForegroundLinearLayout;
import com.google.android.finsky.layout.HistogramView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;

public class ReviewsStatisticsModuleLayout
  extends ForegroundLinearLayout
  implements ModuleDividerItemDecoration.NoBottomDivider
{
  boolean mHasBinded;
  PlayStoreUiElementNode mParentNode;
  HistogramView mReviewStats;
  
  public ReviewsStatisticsModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ReviewsStatisticsModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mReviewStats = ((HistogramView)findViewById(2131756069));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ReviewsStatisticsModuleLayout
 * JD-Core Version:    0.7.0.1
 */