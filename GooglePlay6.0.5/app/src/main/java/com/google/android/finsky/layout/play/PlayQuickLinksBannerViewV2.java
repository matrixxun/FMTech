package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.android.finsky.adapters.QuickLinkHelper.QuickLinkInfo;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.play.image.BitmapLoader;

public class PlayQuickLinksBannerViewV2
  extends PlayClusterView
{
  private boolean mConfigured;
  private int mFillWidthThreshold;
  private LinearLayout mItems;
  private int mMinWidthDouble;
  private int mMinWidthSingle;
  private int mPaddingLeft;
  
  public PlayQuickLinksBannerViewV2(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayQuickLinksBannerViewV2(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private int applyWidthToChildren(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = this.mItems.getChildCount();
    int k = 0;
    if (k < j)
    {
      PlayQuickLinksBannerItemPillView localPlayQuickLinksBannerItemPillView = (PlayQuickLinksBannerItemPillView)this.mItems.getChildAt(k);
      int m = paramInt2 + localPlayQuickLinksBannerItemPillView.getMeasuredWidth();
      if (m < paramInt1) {}
      for (int n = paramInt1 - m;; n = paramInt2)
      {
        localPlayQuickLinksBannerItemPillView.setAdditionalWidth(n);
        i += n;
        k++;
        break;
      }
    }
    return i;
  }
  
  public final void bindContent(BitmapLoader paramBitmapLoader, QuickLinkHelper.QuickLinkInfo[] paramArrayOfQuickLinkInfo, final NavigationManager paramNavigationManager, final DfeToc paramDfeToc, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    configureLogging(null, paramPlayStoreUiElementNode);
    logEmptyClusterImpression();
    if (this.mConfigured) {}
    for (;;)
    {
      return;
      this.mConfigured = true;
      LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
      int i = paramArrayOfQuickLinkInfo.length;
      for (int j = 0; j < i; j++)
      {
        final Browse.QuickLink localQuickLink = paramArrayOfQuickLinkInfo[j].mQuickLink;
        final PlayQuickLinksBannerItemPillView localPlayQuickLinksBannerItemPillView = (PlayQuickLinksBannerItemPillView)localLayoutInflater.inflate(2130969011, this.mItems, false);
        localPlayQuickLinksBannerItemPillView.bindData(localQuickLink, paramBitmapLoader, getPlayStoreUiElementNode());
        localPlayQuickLinksBannerItemPillView.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            paramNavigationManager.resolveLink(localQuickLink.link, localQuickLink.name, localQuickLink.backendId, paramDfeToc, localPlayQuickLinksBannerItemPillView);
          }
        });
        this.mItems.addView(localPlayQuickLinksBannerItemPillView);
      }
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 429;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mItems = ((LinearLayout)findViewById(2131755951));
    this.mPaddingLeft = this.mItems.getPaddingLeft();
    Resources localResources = getResources();
    this.mMinWidthSingle = localResources.getDimensionPixelSize(2131493480);
    this.mMinWidthDouble = localResources.getDimensionPixelSize(2131493479);
    this.mFillWidthThreshold = localResources.getDimensionPixelSize(2131493478);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    applyWidthToChildren(0, 0);
    super.onMeasure(paramInt1, paramInt2);
    int i = this.mItems.getChildCount();
    int j = getMeasuredWidth();
    int k = 0;
    int m = 0;
    while (m < i)
    {
      int i3 = this.mItems.getChildAt(m).getMeasuredWidth();
      int i4 = k + i3;
      if (i4 <= j)
      {
        k = i4;
        m++;
      }
      else
      {
        int i5 = j - this.mPaddingLeft;
        int i6;
        if (k + Math.round(0.25F * i3) <= j) {
          i6 = i5 - Math.max(k + Math.round(0.75F * i3), j);
        }
        for (int i7 = m; i6 < 10; i7 = m - 1)
        {
          return;
          i6 = i5 - (k - Math.round(0.25F * this.mItems.getChildAt(m - 1).getMeasuredWidth()));
        }
        applyWidthToChildren(0, 2 * (i6 / (1 + i7 * 2)));
        this.mItems.setPadding(this.mPaddingLeft, this.mItems.getPaddingTop(), this.mItems.getPaddingRight(), this.mItems.getPaddingBottom());
        super.onMeasure(paramInt1, paramInt2);
        return;
      }
    }
    int n = k;
    int i1;
    if (i == 1) {
      i1 = this.mMinWidthSingle;
    }
    for (;;)
    {
      if (i1 > 0) {
        k += applyWidthToChildren(i1, 0);
      }
      int i2 = j - k;
      if ((i2 < this.mFillWidthThreshold) && (i2 > 10)) {
        k = n + applyWidthToChildren(i1, i2 / i);
      }
      if (k >= j)
      {
        k = n;
        applyWidthToChildren(0, 0);
      }
      this.mItems.setPadding((j - k) / 2, this.mItems.getPaddingTop(), this.mItems.getPaddingRight(), this.mItems.getPaddingBottom());
      super.onMeasure(paramInt1, paramInt2);
      return;
      i1 = 0;
      if (i == 2) {
        i1 = this.mMinWidthDouble;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayQuickLinksBannerViewV2
 * JD-Core Version:    0.7.0.1
 */