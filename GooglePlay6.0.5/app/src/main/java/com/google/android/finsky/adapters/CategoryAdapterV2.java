package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.CategoryRowV2;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfigurator;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayQuickLinksBannerCircleItemViewLarge;
import com.google.android.finsky.layout.play.PlayQuickLinksBannerView;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.BrowseLink;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.image.ThumbnailUtils;
import com.google.android.play.image.AvatarCropTransformation;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.image.BitmapLoader.BitmapContainer;
import com.google.android.play.image.BitmapLoader.BitmapLoadedHandler;
import com.google.android.play.image.FifeImageView;
import java.util.HashSet;

public final class CategoryAdapterV2
  extends RecyclerView.Adapter
{
  public HashSet<RecyclerView.ViewHolder> mActiveViewHolders;
  final int mBackendId;
  final BitmapLoader mBitmapLoader;
  private final Browse.BrowseLink[] mCategories;
  public final int mCategoryOffset;
  private final String mCategorySubheaderTitle;
  final Context mContext;
  private final int mDefaultFillColor;
  private final boolean mDisplayCategoriesInTwoColumns;
  private final int mFooterHeight;
  private final int mHorizontalMargin;
  private final LayoutInflater mLayoutInflater;
  private final int mLeadingExtraSpacerHeight;
  private final int mLeadingSpacerHeight;
  final NavigationManager mNavigationManager;
  private final PlayStoreUiElementNode mParentNode;
  private final int mQuickLinkRowCount;
  final Browse.QuickLink[] mQuickLinks;
  private CategoryQuickLinksContentBinder mQuickLinksContentBinder;
  private CategoryQuickLinksContentConfigurator mQuickLinksContentConfigurator;
  public Bundle mQuickLinksInstanceState;
  private final String mQuickLinksSubheaderTitle;
  public final int mSideMargin;
  final DfeToc mToc;
  private final int mVerticalMargin;
  
  public CategoryAdapterV2(Context paramContext, Browse.BrowseLink[] paramArrayOfBrowseLink, NavigationManager paramNavigationManager, int paramInt1, DfeToc paramDfeToc, BitmapLoader paramBitmapLoader, Browse.QuickLink[] paramArrayOfQuickLink, String paramString1, String paramString2, int paramInt2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    this.mCategories = paramArrayOfBrowseLink;
    this.mNavigationManager = paramNavigationManager;
    this.mBackendId = paramInt1;
    this.mToc = paramDfeToc;
    this.mBitmapLoader = paramBitmapLoader;
    this.mQuickLinksSubheaderTitle = paramString1;
    this.mCategorySubheaderTitle = paramString2;
    this.mContext = paramContext;
    this.mDefaultFillColor = CorpusResourceUtils.getPrimaryColor(paramContext, paramInt1);
    Resources localResources = this.mContext.getResources();
    this.mDisplayCategoriesInTwoColumns = localResources.getBoolean(2131427332);
    this.mQuickLinks = paramArrayOfQuickLink;
    int i;
    int j;
    if (this.mQuickLinks != null)
    {
      i = this.mQuickLinks.length;
      if (i <= 1) {
        break label266;
      }
      j = 1;
      label116:
      this.mQuickLinkRowCount = j;
      this.mCategoryOffset = (5 + 2 * this.mQuickLinkRowCount);
      this.mLeadingSpacerHeight = FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(paramContext, paramInt2);
      this.mLeadingExtraSpacerHeight = localResources.getDimensionPixelSize(2131492937);
      this.mVerticalMargin = localResources.getDimensionPixelSize(2131492974);
      this.mHorizontalMargin = localResources.getDimensionPixelSize(2131492972);
      this.mFooterHeight = (2 * this.mVerticalMargin);
      this.mParentNode = paramPlayStoreUiElementNode;
      if (!localResources.getBoolean(2131427334)) {
        break label272;
      }
    }
    label266:
    label272:
    for (this.mSideMargin = ((localResources.getDisplayMetrics().widthPixels - UiUtils.getGridColumnContentWidth(localResources)) / 2);; this.mSideMargin = this.mHorizontalMargin)
    {
      this.mQuickLinksContentConfigurator = new CategoryQuickLinksContentConfigurator();
      this.mQuickLinksContentBinder = new CategoryQuickLinksContentBinder((byte)0);
      this.mActiveViewHolders = new HashSet();
      return;
      i = 0;
      break;
      j = 0;
      break label116;
    }
  }
  
  private boolean displayEmptyCategory()
  {
    return (this.mDisplayCategoriesInTwoColumns) && (this.mCategories.length % 2 == 1);
  }
  
  private View inflate$42ccc377(int paramInt, ViewGroup paramViewGroup)
  {
    return this.mLayoutInflater.inflate(paramInt, paramViewGroup, false);
  }
  
  public final boolean displayViewInTwoColumns(int paramInt)
  {
    if (!this.mDisplayCategoriesInTwoColumns) {}
    int i;
    do
    {
      return false;
      i = getItemViewType(paramInt);
    } while ((i != 6) && (i != 7));
    return true;
  }
  
  public final int getItemCount()
  {
    int i = this.mCategoryOffset + this.mCategories.length;
    if (displayEmptyCategory()) {}
    for (int j = 1;; j = 0) {
      return 2 + (j + i);
    }
  }
  
  public final int getItemViewType(int paramInt)
  {
    int i = 8;
    if (paramInt == 0) {
      i = 0;
    }
    int i2;
    do
    {
      int n;
      do
      {
        return i;
        int j = paramInt - 1;
        if (j == 0) {
          return 1;
        }
        int k = j - 1;
        if (this.mQuickLinkRowCount > 0)
        {
          if (k == 0) {
            return 2;
          }
          int i3 = k - 1;
          if (i3 == 0) {
            return 3;
          }
          k = i3 - this.mQuickLinkRowCount;
        }
        if (k == 0) {
          return 4;
        }
        int m = k - 1;
        if (m == 0) {
          return 5;
        }
        n = m - 1;
      } while (n == 0);
      int i1 = n - 1;
      if ((i1 >= 0) && (i1 < this.mCategories.length)) {
        return 6;
      }
      i2 = i1 - this.mCategories.length;
      if (displayEmptyCategory())
      {
        if (i2 == 0) {
          return 7;
        }
        i2--;
      }
    } while (i2 == 0);
    return 9;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    this.mActiveViewHolders.add(paramViewHolder);
    int i = paramViewHolder.mItemViewType;
    View localView = paramViewHolder.itemView;
    switch (i)
    {
    case 0: 
    case 1: 
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    default: 
      return;
    case 2: 
      ((TextView)localView).setText(this.mQuickLinksSubheaderTitle);
      return;
    case 3: 
      PlayQuickLinksBannerView localPlayQuickLinksBannerView = (PlayQuickLinksBannerView)localView;
      localPlayQuickLinksBannerView.setContentHorizontalPadding(this.mSideMargin);
      PlayStoreUiElementNode localPlayStoreUiElementNode2 = this.mParentNode;
      if (localPlayQuickLinksBannerView.mUiElementProto == null)
      {
        localPlayQuickLinksBannerView.mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(429);
        FinskyEventLog.setServerLogCookie(localPlayQuickLinksBannerView.mUiElementProto, null);
      }
      localPlayQuickLinksBannerView.mParentNode = localPlayStoreUiElementNode2;
      this.mQuickLinksContentBinder.mParentNode = localPlayQuickLinksBannerView;
      int m = UiUtils.getFeaturedGridColumnCount(this.mContext.getResources(), 1.0D);
      localPlayQuickLinksBannerView.createContent(this.mQuickLinksContentBinder, this.mQuickLinksContentConfigurator, m, true, null, this.mQuickLinksInstanceState, null);
      return;
    case 4: 
      ((TextView)localView).setText(this.mCategorySubheaderTitle);
      return;
    }
    int j = paramInt - this.mCategoryOffset;
    final CategoryRowV2 localCategoryRowV2 = (CategoryRowV2)localView;
    final Browse.BrowseLink localBrowseLink = this.mCategories[j];
    if (localBrowseLink.image != null) {}
    for (int k = UiUtils.getFillColor(localBrowseLink.image, this.mDefaultFillColor);; k = this.mDefaultFillColor)
    {
      AvatarCropTransformation localAvatarCropTransformation = AvatarCropTransformation.getNoRingAvatarCrop(this.mContext.getResources(), k);
      PlayStoreUiElementNode localPlayStoreUiElementNode1 = this.mParentNode;
      localCategoryRowV2.mCategoryTitle.setText(localBrowseLink.name);
      if ((localBrowseLink.image != null) && (localBrowseLink.image.hasImageUrl))
      {
        String str = localBrowseLink.image.imageUrl;
        BitmapLoader localBitmapLoader = FinskyApp.get().mBitmapLoader;
        localCategoryRowV2.mCategoryIcon.setBitmapTransformation(localAvatarCropTransformation);
        localCategoryRowV2.mCategoryIcon.setImage(str, true, localBitmapLoader);
      }
      localCategoryRowV2.mParentNode = localPlayStoreUiElementNode1;
      FinskyEventLog.setServerLogCookie(localCategoryRowV2.mUiElementProto, localBrowseLink.serverLogsCookie);
      localCategoryRowV2.mParentNode.childImpression(localCategoryRowV2);
      localCategoryRowV2.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          CategoryAdapterV2.this.mNavigationManager.goBrowse(localBrowseLink.dataUrl, localBrowseLink.name, CategoryAdapterV2.this.mBackendId, CategoryAdapterV2.this.mToc, localCategoryRowV2);
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
      localView.getLayoutParams().height = this.mLeadingSpacerHeight;
      localView.setId(2131755059);
      continue;
      localView = inflate$42ccc377(2130968994, paramViewGroup);
      localView.getLayoutParams().height = this.mLeadingExtraSpacerHeight;
      continue;
      localView = inflate$42ccc377(2130968994, paramViewGroup);
      localView.getLayoutParams().height = this.mVerticalMargin;
      continue;
      localView = inflate$42ccc377(2130968994, paramViewGroup);
      localView.getLayoutParams().height = this.mFooterHeight;
      continue;
      localView = inflate$42ccc377(2130968661, paramViewGroup);
      continue;
      localView = inflate$42ccc377(2130969008, paramViewGroup);
      continue;
      localView = inflate$42ccc377(2130968659, paramViewGroup);
      continue;
      localView = inflate$42ccc377(2130968660, paramViewGroup);
      continue;
      localView = inflate$42ccc377(2130968660, paramViewGroup);
      localView.getLayoutParams().height = this.mContext.getResources().getDimensionPixelSize(2131492975);
    }
  }
  
  public final void onViewRecycled(RecyclerView.ViewHolder paramViewHolder)
  {
    this.mActiveViewHolders.remove(paramViewHolder);
    int i = paramViewHolder.mItemViewType;
    View localView = paramViewHolder.itemView;
    PlayQuickLinksBannerView localPlayQuickLinksBannerView;
    if (i == 3)
    {
      localPlayQuickLinksBannerView = (PlayQuickLinksBannerView)localView;
      if (this.mQuickLinksInstanceState != null) {
        break label78;
      }
      this.mQuickLinksInstanceState = new Bundle();
    }
    for (;;)
    {
      localPlayQuickLinksBannerView.onSaveInstanceState(this.mQuickLinksInstanceState);
      if ((localView instanceof Recyclable)) {
        ((Recyclable)localView).onRecycle();
      }
      return;
      label78:
      this.mQuickLinksInstanceState.clear();
    }
  }
  
  private final class CategoryQuickLinksContentBinder
    implements ClusterContentBinder<PlayQuickLinksBannerCircleItemViewLarge>
  {
    PlayStoreUiElementNode mParentNode;
    
    private CategoryQuickLinksContentBinder() {}
    
    public final int getAvailableChildCount()
    {
      return CategoryAdapterV2.this.mQuickLinks.length;
    }
    
    public final String getChildContentSourceId()
    {
      return "category_quick_links";
    }
    
    public final float getChildCoverAspectRatio(int paramInt)
    {
      return 1.0F;
    }
    
    public final int getChildLayoutId$134621()
    {
      return 2130969010;
    }
    
    public final boolean isMoreDataAvailable()
    {
      return false;
    }
    
    public final BitmapLoader.BitmapContainer preloadChildCoverImage(int paramInt1, int paramInt2, int paramInt3, BitmapLoader.BitmapLoadedHandler paramBitmapLoadedHandler, int[] paramArrayOfInt)
    {
      return ThumbnailUtils.preloadCoverImage(CategoryAdapterV2.this.mContext, CategoryAdapterV2.this.mQuickLinks[paramInt1].image, CategoryAdapterV2.this.mBitmapLoader, paramInt2, paramInt3, paramBitmapLoadedHandler);
    }
  }
  
  private final class CategoryQuickLinksContentConfigurator
    implements ClusterContentConfigurator
  {
    final int mQuickLinksBannerContentMargin;
    final int mQuickLinksBannerContentMinHeight;
    final int mQuickLinksBannerImageSize;
    final int mQuickLinksBannerItemWidth;
    final int mQuickLinksBannerPaddingBottom;
    
    public CategoryQuickLinksContentConfigurator()
    {
      Resources localResources = CategoryAdapterV2.this.mContext.getResources();
      this.mQuickLinksBannerImageSize = localResources.getDimensionPixelSize(2131493036);
      this.mQuickLinksBannerContentMinHeight = localResources.getDimensionPixelSize(2131493332);
      this.mQuickLinksBannerContentMargin = localResources.getDimensionPixelSize(2131493487);
      this.mQuickLinksBannerPaddingBottom = localResources.getDimensionPixelSize(2131493483);
      this.mQuickLinksBannerItemWidth = localResources.getDimensionPixelSize(2131493035);
    }
    
    public final int getChildHeight(float paramFloat1, float paramFloat2, int paramInt)
    {
      return this.mQuickLinksBannerImageSize + this.mQuickLinksBannerContentMinHeight + 2 * this.mQuickLinksBannerContentMargin;
    }
    
    public final float getChildPeekingAmount()
    {
      return 0.25F;
    }
    
    public final int getChildVerticalOffset(float paramFloat1, float paramFloat2, int paramInt)
    {
      return 0;
    }
    
    public final float getChildWidthMultiplier()
    {
      return 1.0F;
    }
    
    public final int getChildWidthPolicy()
    {
      return 2;
    }
    
    public final int getClusterHeight(int paramInt, float paramFloat)
    {
      return this.mQuickLinksBannerImageSize + this.mQuickLinksBannerContentMinHeight + 2 * this.mQuickLinksBannerContentMargin + this.mQuickLinksBannerPaddingBottom;
    }
    
    public final int getFixedChildWidth$255f288(int paramInt)
    {
      return this.mQuickLinksBannerItemWidth;
    }
    
    public final float getPrimaryChildAspectRatio(ClusterContentBinder paramClusterContentBinder)
    {
      return 1.0F;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.CategoryAdapterV2
 * JD-Core Version:    0.7.0.1
 */