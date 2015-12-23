package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.MeasureSpec;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.play.PlayCardViewBundleItem;
import com.google.android.finsky.layout.play.PlayCardViewBundleItemMedium;
import com.google.android.finsky.layout.play.PlayCardViewBundleItemSmall;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;

public class BundleCardClusterModuleLayout
  extends LinearLayout
{
  boolean mIsBound;
  private final int mMaxItemsPerRow = UiUtils.getFeaturedGridColumnCount(getResources(), 1.0D);
  private PlayCardViewBundleItemMedium mMediumCard;
  private ViewStub mMediumCardStub;
  private TextView mTitle;
  
  public BundleCardClusterModuleLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BundleCardClusterModuleLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = paramContext.getResources();
    int i = localResources.getDimensionPixelSize(2131492993);
    int j = localResources.getDimensionPixelSize(2131492956);
    int k = localResources.getDimensionPixelSize(2131493068);
    int m = i - j - k;
    ViewCompat.setPaddingRelative(this, m, getPaddingTop(), m, getPaddingBottom());
  }
  
  private static void bindOneCard(PlayCardViewBundleItem paramPlayCardViewBundleItem, Document paramDocument, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt)
  {
    paramPlayCardViewBundleItem.setCardType(paramInt);
    PlayCardUtils.bindCard((PlayCardViewBase)paramPlayCardViewBundleItem, paramDocument, null, paramBitmapLoader, paramNavigationManager, paramPlayStoreUiElementNode);
    paramPlayCardViewBundleItem.hideLabelIfNotOwned();
  }
  
  public final void bind(DfeList paramDfeList, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt)
  {
    this.mIsBound = true;
    String str = paramDfeList.mContainerDocument.mDocument.title;
    if (TextUtils.isEmpty(str)) {
      this.mTitle.setVisibility(8);
    }
    int i;
    for (;;)
    {
      i = paramDfeList.getCount();
      if ((i != 1) || (this.mMaxItemsPerRow <= 1)) {
        break label222;
      }
      for (int i5 = -1 + getChildCount(); i5 >= 2; i5--) {
        removeViewAt(i5);
      }
      this.mTitle.setVisibility(0);
      this.mTitle.setText(str);
    }
    if (this.mMediumCardStub != null)
    {
      this.mMediumCard = ((PlayCardViewBundleItemMedium)this.mMediumCardStub.inflate());
      this.mMediumCardStub = null;
    }
    this.mMediumCard.setVisibility(0);
    for (Object localObject = this.mMediumCard;; localObject = (PlayCardViewBundleItem)((BucketRow)getChildAt(2)).getChildAt(0))
    {
      bindOneCard((PlayCardViewBundleItem)localObject, (Document)paramDfeList.getItem(0), paramBitmapLoader, paramNavigationManager, paramPlayStoreUiElementNode, paramInt);
      for (int i2 = 1; i2 < i; i2++) {
        bindOneCard((PlayCardViewBundleItem)((BucketRow)getChildAt(2 + i2 / this.mMaxItemsPerRow)).getChildAt(i2 % this.mMaxItemsPerRow), (Document)paramDfeList.getItem(i2), paramBitmapLoader, paramNavigationManager, paramPlayStoreUiElementNode, paramInt);
      }
      label222:
      if (this.mMediumCard != null)
      {
        this.mMediumCard.clearCardState();
        this.mMediumCard.setVisibility(8);
      }
      int j = this.mMaxItemsPerRow;
      int k = 2 + (-1 + (i + j)) / j;
      for (int m = -1 + getChildCount(); m >= k; m--) {
        removeViewAt(m);
      }
      LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
      for (int n = getChildCount(); n < k; n++)
      {
        BucketRow localBucketRow2 = (BucketRow)localLayoutInflater.inflate(2130968653, this, false);
        for (int i4 = 0; i4 < this.mMaxItemsPerRow; i4++) {
          localLayoutInflater.inflate(2130968924, localBucketRow2);
        }
        addView(localBucketRow2);
      }
      int i1 = i % this.mMaxItemsPerRow;
      if (i1 > 0)
      {
        BucketRow localBucketRow1 = (BucketRow)getChildAt(k - 1);
        for (int i3 = i1; i3 < this.mMaxItemsPerRow; i3++) {
          ((PlayCardViewBundleItemSmall)localBucketRow1.getChildAt(i3)).clearCardState();
        }
      }
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitle = ((TextView)findViewById(2131755298));
    this.mMediumCardStub = ((ViewStub)findViewById(2131755299));
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((this.mMediumCard != null) && (this.mMediumCard.getVisibility() != 8))
    {
      LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.mMediumCard.getLayoutParams();
      localLayoutParams.width = (2 * (View.MeasureSpec.getSize(paramInt1) - getPaddingLeft() - getPaddingRight() - localLayoutParams.leftMargin - localLayoutParams.rightMargin) / this.mMaxItemsPerRow);
      localLayoutParams.height = (3 * localLayoutParams.width / 4);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.BundleCardClusterModuleLayout
 * JD-Core Version:    0.7.0.1
 */