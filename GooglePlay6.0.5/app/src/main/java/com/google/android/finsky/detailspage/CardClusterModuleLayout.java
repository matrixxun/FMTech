package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.play.PlayCardClusterViewHeader;
import com.google.android.finsky.utils.UiUtils;

public class CardClusterModuleLayout
  extends LinearLayout
  implements DetailsClusterDecoration.FlatFillSection, ModuleDividerItemDecoration.NoBottomDivider, ModuleDividerItemDecoration.NoTopDivider, ModuleMarginItemDecoration.MarginOffset
{
  protected BucketRow mBucketRowFirst;
  protected BucketRow mBucketRowSecond;
  protected PlayCardClusterViewHeader mHeaderView;
  private final int mMarginOffset;
  private final int mMaxItemsPerRow;
  
  public CardClusterModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CardClusterModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    if (localResources.getBoolean(2131427334)) {}
    for (this.mMarginOffset = (-localResources.getDimensionPixelSize(2131493385));; this.mMarginOffset = 0)
    {
      this.mMaxItemsPerRow = getMaxItemsPerRow(getResources());
      return;
    }
  }
  
  private void inflateCards(BucketRow paramBucketRow)
  {
    Context localContext = getContext();
    LayoutInflater localLayoutInflater = LayoutInflater.from(localContext);
    Resources localResources = localContext.getResources();
    for (int i = 0; i < this.mMaxItemsPerRow; i++) {
      paramBucketRow.addView(localLayoutInflater.inflate(getCardLayoutId(localResources), paramBucketRow, false));
    }
  }
  
  public final void bind(ClusterContentBinder paramClusterContentBinder, int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean, View.OnClickListener paramOnClickListener)
  {
    this.mHeaderView.setContent(paramInt, paramString1, null, paramString3, paramOnClickListener);
    for (int i = 0; i < this.mMaxItemsPerRow; i++) {
      paramClusterContentBinder.bindChild(this.mBucketRowFirst.getChildAt(i), i);
    }
    if (paramBoolean)
    {
      if (this.mBucketRowSecond == null)
      {
        this.mBucketRowSecond = ((BucketRow)LayoutInflater.from(getContext()).inflate(2130968653, this, false));
        addView(this.mBucketRowSecond);
        inflateCards(this.mBucketRowSecond);
      }
      j = 0;
      for (k = 0; k < this.mMaxItemsPerRow; k++)
      {
        localView = this.mBucketRowSecond.getChildAt(k);
        paramClusterContentBinder.bindChild(localView, k + this.mMaxItemsPerRow);
        if (localView.getVisibility() == 0) {
          j = 1;
        }
      }
      localBucketRow = this.mBucketRowSecond;
      if (j != 0)
      {
        m = 0;
        localBucketRow.setVisibility(m);
      }
    }
    while (this.mBucketRowSecond == null) {
      for (;;)
      {
        int j;
        int k;
        View localView;
        BucketRow localBucketRow;
        return;
        int m = 8;
      }
    }
    this.mBucketRowSecond.setVisibility(8);
  }
  
  protected int getCardLayoutId(Resources paramResources)
  {
    if (paramResources.getBoolean(2131427339)) {
      return 2130968939;
    }
    return 2130968954;
  }
  
  public int getMarginOffset()
  {
    return this.mMarginOffset;
  }
  
  public final int getMaxItemsInLayout(boolean paramBoolean)
  {
    int i = getMaxItemsPerRow(getResources());
    if (paramBoolean) {
      i *= 2;
    }
    return i;
  }
  
  protected int getMaxItemsPerRow(Resources paramResources)
  {
    return UiUtils.getDetailsCardColumnCount(paramResources);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHeaderView = ((PlayCardClusterViewHeader)findViewById(2131755380));
    this.mBucketRowFirst = ((BucketRow)findViewById(2131755259));
    inflateCards(this.mBucketRowFirst);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.CardClusterModuleLayout
 * JD-Core Version:    0.7.0.1
 */