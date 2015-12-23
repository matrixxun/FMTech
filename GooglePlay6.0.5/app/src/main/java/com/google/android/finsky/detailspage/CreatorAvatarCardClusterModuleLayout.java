package com.google.android.finsky.detailspage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.DetailsAvatarClusterHeader;
import com.google.android.finsky.layout.play.PlayCardClusterViewHeader;
import com.google.android.finsky.protos.Badge;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.play.image.BitmapLoader;

public class CreatorAvatarCardClusterModuleLayout
  extends CardClusterModuleLayout
{
  public CreatorAvatarCardClusterModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CreatorAvatarCardClusterModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private int getCardYOffset()
  {
    DetailsAvatarClusterHeader localDetailsAvatarClusterHeader = (DetailsAvatarClusterHeader)this.mHeaderView;
    return localDetailsAvatarClusterHeader.getBackgroundImageTopMargin() + 2 * localDetailsAvatarClusterHeader.getBackgroundImageHeight() / 3;
  }
  
  public final void bind$3617df9(ClusterContentBinder paramClusterContentBinder, BitmapLoader paramBitmapLoader, int paramInt, String paramString1, String paramString2, boolean paramBoolean, Common.Image paramImage1, Common.Image paramImage2, Badge paramBadge, View.OnClickListener paramOnClickListener1, View.OnClickListener paramOnClickListener2)
  {
    super.bind(paramClusterContentBinder, paramInt, paramString1, null, paramString2, paramBoolean, paramOnClickListener1);
    ((DetailsAvatarClusterHeader)this.mHeaderView).setContent(paramBitmapLoader, paramInt, paramImage2, paramImage1, paramBadge, paramString1, null, paramString2, paramOnClickListener2);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = getPaddingLeft();
    int j = getPaddingTop();
    this.mHeaderView.layout(i, j, i + this.mHeaderView.getMeasuredWidth(), j + this.mHeaderView.getMeasuredHeight());
    int k = j + getCardYOffset();
    this.mBucketRowFirst.layout(i, k, i + this.mBucketRowFirst.getMeasuredWidth(), k + this.mBucketRowFirst.getMeasuredHeight());
    if (this.mBucketRowSecond != null)
    {
      int m = this.mBucketRowFirst.getBottom();
      this.mBucketRowSecond.layout(i, m, i + this.mBucketRowSecond.getMeasuredWidth(), m + this.mBucketRowSecond.getMeasuredHeight());
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = this.mBucketRowFirst.getMeasuredHeight() + getCardYOffset();
    if (this.mBucketRowSecond != null) {
      m += this.mBucketRowSecond.getMeasuredHeight();
    }
    if (k == 1073741824) {
      m = j;
    }
    for (;;)
    {
      setMeasuredDimension(i, m + getPaddingTop() + getPaddingBottom());
      return;
      if (k == -2147483648) {
        m = Math.min(m, j);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.CreatorAvatarCardClusterModuleLayout
 * JD-Core Version:    0.7.0.1
 */