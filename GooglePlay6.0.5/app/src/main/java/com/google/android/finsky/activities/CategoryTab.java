package com.google.android.finsky.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.CardLookDecoration;
import com.google.android.finsky.adapters.CategoryAdapter;
import com.google.android.finsky.adapters.CategoryAdapterV2;
import com.google.android.finsky.adapters.QuickLinkHelper.QuickLinkInfo;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayClusterViewContentV2;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.layout.play.SelectableUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.BrowseLink;
import com.google.android.finsky.protos.Browse.BrowseTab;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.play.image.BitmapLoader;
import java.util.HashSet;

public final class CategoryTab
  implements ViewPagerTab
{
  private final BitmapLoader mBitmapLoader;
  private final Browse.BrowseLink[] mCategories;
  private final String mCategoriesTitle;
  private RecyclerView.Adapter mCategoryAdapter;
  private ViewGroup mCategoryView;
  private final Context mContext;
  private boolean mIsCurrentlySelected;
  private final LayoutInflater mLayoutInflater;
  private final NavigationManager mNavigationManager;
  private SelectableUiElementNode mParentNode;
  private final QuickLinkHelper.QuickLinkInfo[] mQuickLinks;
  private final Browse.QuickLink[] mQuickLinksV2;
  private final String mQuickLinksV2Title;
  private PlayRecyclerView mRecyclerView;
  private ObjectMap mRestoredInstanceState = ObjectMap.EMPTY;
  private final int mTabMode;
  private final String mTabTitle;
  private final DfeToc mToc;
  
  public CategoryTab(Context paramContext, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, LayoutInflater paramLayoutInflater, TabbedAdapter.TabData paramTabData, DfeToc paramDfeToc, int paramInt)
  {
    this.mContext = paramContext;
    this.mNavigationManager = paramNavigationManager;
    this.mBitmapLoader = paramBitmapLoader;
    this.mLayoutInflater = paramLayoutInflater;
    this.mToc = paramDfeToc;
    Browse.BrowseTab localBrowseTab = paramTabData.browseTab;
    this.mTabTitle = localBrowseTab.title;
    this.mQuickLinksV2Title = localBrowseTab.quickLinkTitle;
    this.mCategoriesTitle = localBrowseTab.categoriesTitle;
    this.mCategories = localBrowseTab.category;
    this.mQuickLinks = paramTabData.quickLinks;
    this.mQuickLinksV2 = localBrowseTab.quickLink;
    this.mParentNode = paramTabData.elementNode;
    this.mTabMode = paramInt;
  }
  
  public final View getView(int paramInt)
  {
    if (this.mCategoryView == null)
    {
      this.mCategoryView = ((ViewGroup)this.mLayoutInflater.inflate(2130968662, null));
      this.mRecyclerView = ((PlayRecyclerView)this.mCategoryView.findViewById(2131755329));
      this.mRecyclerView.setVisibility(0);
      if (!FinskyApp.get().getExperiments().isH20StoreEnabled()) {
        break label272;
      }
      final CategoryAdapterV2 localCategoryAdapterV2 = new CategoryAdapterV2(this.mContext, this.mCategories, this.mNavigationManager, paramInt, this.mToc, this.mBitmapLoader, this.mQuickLinksV2, this.mQuickLinksV2Title, this.mCategoriesTitle, this.mTabMode, this.mParentNode);
      this.mCategoryAdapter = localCategoryAdapterV2;
      if (this.mRestoredInstanceState.containsKey("CategoryTab.AdapterInstanceState"))
      {
        ObjectMap localObjectMap = (ObjectMap)this.mRestoredInstanceState.get("CategoryTab.AdapterInstanceState");
        if (localObjectMap != null) {
          localCategoryAdapterV2.mQuickLinksInstanceState = ((Bundle)localObjectMap.get("CategoryAdapterV2.QuickLinksViewState"));
        }
      }
      GridLayoutManager localGridLayoutManager = new GridLayoutManager(this.mContext);
      this.mRecyclerView.setLayoutManager(localGridLayoutManager);
      localGridLayoutManager.mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup()
      {
        public final int getSpanSize(int paramAnonymousInt)
        {
          if (localCategoryAdapterV2.displayViewInTwoColumns(paramAnonymousInt)) {
            return 1;
          }
          return 2;
        }
      };
      this.mRecyclerView.addItemDecoration(new GridMarginItemDecoration(localCategoryAdapterV2));
      this.mRecyclerView.addItemDecoration(new CardLookDecoration(this.mContext));
    }
    for (;;)
    {
      this.mRecyclerView.setAdapter(this.mCategoryAdapter);
      if (this.mRestoredInstanceState.containsKey("CategoryTab.RecyclerViewState")) {
        this.mRecyclerView.restoreInstanceState((Parcelable)this.mRestoredInstanceState.get("CategoryTab.RecyclerViewState"));
      }
      return this.mCategoryView;
      label272:
      this.mRecyclerView.setLayoutManager(new LinearLayoutManager());
      this.mCategoryAdapter = new CategoryAdapter(this.mContext, this.mCategories, this.mNavigationManager, paramInt, this.mToc, this.mBitmapLoader, this.mQuickLinks, this.mTabTitle, this.mTabMode, this.mParentNode);
    }
  }
  
  public final void onDestroy() {}
  
  public final ObjectMap onDestroyView()
  {
    ObjectMap localObjectMap1 = new ObjectMap();
    if ((this.mRecyclerView != null) && (this.mCategoryAdapter != null))
    {
      localObjectMap1.put("CategoryTab.RecyclerViewState", this.mRecyclerView.saveInstanceState());
      if ((this.mCategoryAdapter instanceof CategoryAdapterV2))
      {
        CategoryAdapterV2 localCategoryAdapterV2 = (CategoryAdapterV2)this.mCategoryAdapter;
        RecyclerView.ViewHolder[] arrayOfViewHolder = (RecyclerView.ViewHolder[])localCategoryAdapterV2.mActiveViewHolders.toArray(new RecyclerView.ViewHolder[localCategoryAdapterV2.mActiveViewHolders.size()]);
        for (int i = 0; i < arrayOfViewHolder.length; i++) {
          localCategoryAdapterV2.onViewRecycled(arrayOfViewHolder[i]);
        }
        ObjectMap localObjectMap2 = new ObjectMap();
        localObjectMap2.put("CategoryAdapterV2.QuickLinksViewState", localCategoryAdapterV2.mQuickLinksInstanceState);
        localObjectMap1.put("CategoryTab.AdapterInstanceState", localObjectMap2);
      }
    }
    this.mCategoryAdapter = null;
    this.mCategoryView = null;
    return localObjectMap1;
  }
  
  public final void onRestoreInstanceState(ObjectMap paramObjectMap)
  {
    if (paramObjectMap != null) {
      this.mRestoredInstanceState = paramObjectMap;
    }
  }
  
  public final void setTabSelected(boolean paramBoolean)
  {
    if (paramBoolean != this.mIsCurrentlySelected)
    {
      if (!paramBoolean) {
        break label54;
      }
      FinskyEventLog.startNewImpression(this.mParentNode);
      this.mParentNode.setNodeSelected(true);
      if (this.mParentNode.getPlayStoreUiElement().child.length == 0) {
        FinskyEventLog.requestImpressions(this.mCategoryView);
      }
    }
    for (;;)
    {
      this.mIsCurrentlySelected = paramBoolean;
      return;
      label54:
      this.mParentNode.setNodeSelected(false);
    }
  }
  
  private static final class GridMarginItemDecoration
    extends RecyclerView.ItemDecoration
  {
    private final CategoryAdapterV2 mCategoryAdapterV2;
    
    public GridMarginItemDecoration(CategoryAdapterV2 paramCategoryAdapterV2)
    {
      this.mCategoryAdapterV2 = paramCategoryAdapterV2;
    }
    
    public final void getItemOffsets$5c1923bd$3450f9fc(Rect paramRect, View paramView)
    {
      int i = 1;
      int j = 1;
      int k;
      int n;
      if ((paramView instanceof PlayClusterViewContentV2))
      {
        k = 0;
        if (i == 0) {
          break label92;
        }
        n = k;
        label23:
        if (j == 0) {
          break label98;
        }
      }
      for (;;)
      {
        paramRect.set(n, 0, k, 0);
        return;
        k = this.mCategoryAdapterV2.mSideMargin;
        int m = RecyclerView.getChildAdapterPosition(paramView);
        if (!this.mCategoryAdapterV2.displayViewInTwoColumns(m)) {
          break;
        }
        if ((m - this.mCategoryAdapterV2.mCategoryOffset) % 2 == 0)
        {
          j = 0;
          break;
        }
        i = 0;
        break;
        label92:
        n = 0;
        break label23;
        label98:
        k = 0;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.CategoryTab
 * JD-Core Version:    0.7.0.1
 */