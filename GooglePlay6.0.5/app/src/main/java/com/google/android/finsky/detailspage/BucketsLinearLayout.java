package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.ForegroundLinearLayout;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.utils.PlayCorpusUtils;
import java.util.List;

public abstract class BucketsLinearLayout<T>
  extends ForegroundLinearLayout
{
  protected String mDocId;
  protected TextView mFooter;
  protected boolean mHasBinded;
  protected final int mItemsMaxRows;
  protected final int mItemsPerRow;
  protected LayoutInflater mLayoutInflater;
  protected NavigationManager mNavigationManager;
  protected PlayStoreUiElementNode mParentNode;
  protected TextView mSectionTitle;
  
  public BucketsLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    Resources localResources = paramContext.getResources();
    this.mItemsPerRow = getItemsPerRow(localResources);
    this.mItemsMaxRows = getMaxRows(localResources);
  }
  
  protected final void bindRows(List<T> paramList, Document paramDocument, boolean paramBoolean, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mNavigationManager = paramNavigationManager;
    this.mDocId = paramDocument.mDocument.docid;
    this.mHasBinded = true;
    int i;
    int j;
    label48:
    LayoutInflater localLayoutInflater;
    int n;
    int i2;
    if (this.mFooter != null)
    {
      i = 1;
      if (this.mSectionTitle == null) {
        break label216;
      }
      j = 1;
      int k = j + i;
      if (getChildCount() > k) {
        removeViews(1, getChildCount() - k);
      }
      localLayoutInflater = LayoutInflater.from(getContext());
      int m = paramList.size();
      n = Math.min(getMaxItemsToShow(), m);
      int i1 = this.mItemsPerRow;
      i2 = (-1 + (n + i1)) / i1;
    }
    for (int i3 = 0;; i3++)
    {
      if (i3 >= i2) {
        break label270;
      }
      BucketRow localBucketRow = (BucketRow)localLayoutInflater.inflate(getBucketRowLayout(), this, false);
      localBucketRow.setSameChildHeight(true);
      int i4 = 0;
      label157:
      if (i4 < this.mItemsPerRow)
      {
        int i5 = i4 + i3 * this.mItemsPerRow;
        if (i5 < n) {
          localBucketRow.addView(buildItemView(paramList.get(i5), paramDocument, this));
        }
        for (;;)
        {
          i4++;
          break label157;
          i = 0;
          break;
          label216:
          j = 0;
          break label48;
          View localView = new View(getContext());
          localView.setVisibility(4);
          localBucketRow.addView(localView);
        }
      }
      addView(localBucketRow, getChildCount() - i);
    }
    label270:
    if (this.mSectionTitle != null)
    {
      if (!paramBoolean) {
        break label360;
      }
      this.mSectionTitle.setText(getSectionTitleText());
      this.mSectionTitle.setVisibility(0);
    }
    for (;;)
    {
      if (this.mFooter != null)
      {
        if (!shouldShowFooter(paramList)) {
          break;
        }
        Context localContext = getContext();
        this.mFooter.setVisibility(0);
        this.mFooter.setText(getFooterText());
        this.mFooter.setTextColor(PlayCorpusUtils.getPrimaryTextColor(localContext, paramDocument.mDocument.backendId));
      }
      return;
      label360:
      this.mSectionTitle.setVisibility(8);
    }
    this.mFooter.setVisibility(8);
  }
  
  protected abstract View buildItemView(T paramT, Document paramDocument, ViewGroup paramViewGroup);
  
  protected abstract int getBucketRowLayout();
  
  protected String getFooterText()
  {
    return null;
  }
  
  protected TextView getFooterView()
  {
    return null;
  }
  
  protected abstract int getItemsPerRow(Resources paramResources);
  
  public int getMaxItemsToShow()
  {
    return this.mItemsPerRow * this.mItemsMaxRows;
  }
  
  protected abstract int getMaxRows(Resources paramResources);
  
  protected String getSectionTitleText()
  {
    return null;
  }
  
  protected TextView getSectionTitleView()
  {
    return null;
  }
  
  public final boolean hasBinded()
  {
    return this.mHasBinded;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSectionTitle = getSectionTitleView();
    this.mFooter = getFooterView();
  }
  
  protected boolean shouldShowFooter(List<T> paramList)
  {
    return paramList.size() > getMaxItemsToShow();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.BucketsLinearLayout
 * JD-Core Version:    0.7.0.1
 */