package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.CategoryRow;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.BrowseLink;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;

public final class CategoryAdapter
  extends RecyclerView.Adapter
{
  final int mBackendId;
  private final BitmapLoader mBitmapLoader;
  private final Browse.BrowseLink[] mCategories;
  private final LayoutInflater mLayoutInflater;
  private final int mLeadingExtraSpacerHeight;
  private final int mLeadingSpacerHeight;
  final NavigationManager mNavigationManager;
  private final int mNumQuickLinksPerRow;
  private final PlayStoreUiElementNode mParent;
  private final int mQuickLinkRowCount;
  private final int mQuickLinkRowPadding;
  private final QuickLinkHelper.QuickLinkInfo[] mQuickLinks;
  private final String mSubheaderTitle;
  private final ColorStateList mTextColor;
  final DfeToc mToc;
  
  public CategoryAdapter(Context paramContext, Browse.BrowseLink[] paramArrayOfBrowseLink, NavigationManager paramNavigationManager, int paramInt1, DfeToc paramDfeToc, BitmapLoader paramBitmapLoader, QuickLinkHelper.QuickLinkInfo[] paramArrayOfQuickLinkInfo, String paramString, int paramInt2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    this.mCategories = paramArrayOfBrowseLink;
    this.mNavigationManager = paramNavigationManager;
    this.mBackendId = paramInt1;
    this.mTextColor = CorpusResourceUtils.getSecondaryTextColor(paramContext, paramInt1);
    this.mToc = paramDfeToc;
    this.mBitmapLoader = paramBitmapLoader;
    this.mSubheaderTitle = paramString;
    Resources localResources = paramContext.getResources();
    float f = paramContext.getResources().getInteger(2131623949) / 100.0F;
    this.mNumQuickLinksPerRow = UiUtils.getFeaturedGridColumnCount(paramContext.getResources(), f);
    this.mQuickLinks = paramArrayOfQuickLinkInfo;
    if (this.mQuickLinks != null) {}
    for (int i = this.mQuickLinks.length;; i = 0)
    {
      int j = this.mNumQuickLinksPerRow;
      this.mQuickLinkRowCount = ((-1 + (i + j)) / j);
      this.mQuickLinkRowPadding = localResources.getDimensionPixelSize(2131493068);
      this.mLeadingSpacerHeight = FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(paramContext, paramInt2);
      this.mLeadingExtraSpacerHeight = localResources.getDimensionPixelSize(2131492937);
      this.mParent = paramPlayStoreUiElementNode;
      return;
    }
  }
  
  private View inflate$42ccc377(int paramInt, ViewGroup paramViewGroup)
  {
    return this.mLayoutInflater.inflate(paramInt, paramViewGroup, false);
  }
  
  public final int getItemCount()
  {
    int i = 2 + this.mCategories.length + this.mQuickLinkRowCount;
    if (this.mQuickLinkRowCount > 0) {}
    for (int j = 1;; j = 0) {
      return j + i;
    }
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final int getItemViewType(int paramInt)
  {
    int i = 1;
    if (paramInt == 0) {
      return 0;
    }
    int j = paramInt - 1;
    if (j == 0) {
      return i;
    }
    int k = j - 1;
    if ((this.mQuickLinkRowCount > 0) && (k < this.mQuickLinkRowCount)) {}
    for (int m = i; m != 0; m = 0) {
      return 3;
    }
    if ((this.mQuickLinkRowCount > 0) && (k == this.mQuickLinkRowCount)) {}
    while (i != 0)
    {
      return 4;
      i = 0;
    }
    return 2;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    int i = paramViewHolder.mItemViewType;
    View localView = paramViewHolder.itemView;
    switch (i)
    {
    default: 
      return;
    case 0: 
      localView.getLayoutParams().height = this.mLeadingSpacerHeight;
      localView.setId(2131755059);
      return;
    case 1: 
      localView.getLayoutParams().height = this.mLeadingExtraSpacerHeight;
      return;
    case 3: 
      QuickLinkHelper.bindQuickLinksRow(this.mToc, this.mNavigationManager, this.mBitmapLoader, (ViewGroup)localView, this.mQuickLinks, paramInt - 2, this.mNumQuickLinksPerRow, this.mParent);
      ViewCompat.setPaddingRelative(localView, ViewCompat.getPaddingStart(localView) + this.mQuickLinkRowPadding, localView.getPaddingTop(), ViewCompat.getPaddingEnd(localView) + this.mQuickLinkRowPadding, localView.getPaddingBottom());
      return;
    case 4: 
      ((TextView)localView).setText(this.mSubheaderTitle);
      return;
    }
    int j = 2 + this.mQuickLinkRowCount;
    if (this.mQuickLinkRowCount > 0) {}
    for (int k = 1;; k = 0)
    {
      int m = paramInt - (k + j);
      final CategoryRow localCategoryRow = (CategoryRow)localView;
      final Browse.BrowseLink localBrowseLink = this.mCategories[m];
      ColorStateList localColorStateList = this.mTextColor;
      PlayStoreUiElementNode localPlayStoreUiElementNode = this.mParent;
      localCategoryRow.mCategoryTitle.setText(localBrowseLink.name);
      localCategoryRow.mCategoryTitle.setTextColor(localColorStateList);
      localCategoryRow.mCategoryTitle.setContentDescription(localBrowseLink.name);
      localCategoryRow.mParentNode = localPlayStoreUiElementNode;
      FinskyEventLog.setServerLogCookie(localCategoryRow.mUiElementProto, localBrowseLink.serverLogsCookie);
      localCategoryRow.mParentNode.childImpression(localCategoryRow);
      localCategoryRow.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          CategoryAdapter.this.mNavigationManager.goBrowse(localBrowseLink.dataUrl, localBrowseLink.name, CategoryAdapter.this.mBackendId, CategoryAdapter.this.mToc, localCategoryRow);
        }
      });
      return;
    }
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView = null;
    switch (paramInt)
    {
    }
    for (;;)
    {
      return new PlayRecyclerView.ViewHolder(localView);
      localView = inflate$42ccc377(2130968994, paramViewGroup);
      continue;
      localView = QuickLinkHelper.inflateQuickLinksRow(paramViewGroup, this.mLayoutInflater, this.mNumQuickLinksPerRow);
      continue;
      localView = inflate$42ccc377(2130968661, paramViewGroup);
      continue;
      localView = inflate$42ccc377(2130968658, paramViewGroup);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.CategoryAdapter
 * JD-Core Version:    0.7.0.1
 */