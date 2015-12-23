package com.google.android.finsky.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SpacerHeightProvider;
import com.google.android.finsky.activities.TabbedAdapter.TabDataListener;
import com.google.android.finsky.adapters.CardRecyclerViewAdapter;
import com.google.android.finsky.adapters.CardRecyclerViewAdapterV2;
import com.google.android.finsky.adapters.CardStreamMarginItemDecoration;
import com.google.android.finsky.adapters.EditorialRecyclerViewAdapter;
import com.google.android.finsky.adapters.QuickLinkHelper.QuickLinkInfo;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.EmptyContainer;
import com.google.android.finsky.protos.Template;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.play.image.FifeImageView;
import java.util.List;

public final class DfeRecyclerBinder
  extends ViewBinder<MultiDfeList>
  implements Response.ErrorListener, OnDataChangedListener
{
  public CardRecyclerViewAdapter mAdapter;
  private final ClientMutationCache mClientMutationCache;
  private ViewGroup mContentLayout;
  private final DfeApi mDfeApi;
  private boolean mHasLoadedAtLeastOnce;
  public PlayRecyclerView mRecyclerView;
  private TabbedAdapter.TabDataListener mTabDataListener;
  protected DfeToc mToc;
  
  public DfeRecyclerBinder(DfeToc paramDfeToc, DfeApi paramDfeApi, ClientMutationCache paramClientMutationCache)
  {
    this.mToc = paramDfeToc;
    this.mDfeApi = paramDfeApi;
    this.mClientMutationCache = paramClientMutationCache;
  }
  
  public final void bind(ViewGroup paramViewGroup, Document paramDocument, QuickLinkHelper.QuickLinkInfo[] paramArrayOfQuickLinkInfo, String paramString, int paramInt, Bundle paramBundle, TabbedAdapter.TabDataListener paramTabDataListener, PlayStoreUiElementNode paramPlayStoreUiElementNode, SpacerHeightProvider paramSpacerHeightProvider)
  {
    this.mContentLayout = paramViewGroup;
    this.mTabDataListener = paramTabDataListener;
    this.mRecyclerView = ((PlayRecyclerView)paramViewGroup.findViewById(2131755329));
    LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager();
    this.mRecyclerView.setLayoutManager(localLinearLayoutManager);
    if ((paramDocument == null) || ((paramDocument.getChildCount() == 0) && ((this.mData == null) || (((MultiDfeList)this.mData).mContainerList.getCount() == 0))))
    {
      View localView = this.mContentLayout.findViewById(2131755730);
      FifeImageView localFifeImageView;
      if (localView != null)
      {
        localFifeImageView = (FifeImageView)localView.findViewById(2131755746);
        if ((paramDocument == null) || (!paramDocument.hasImages(4))) {
          break label215;
        }
        Common.Image localImage = (Common.Image)paramDocument.getImages(4).get(0);
        localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
        localFifeImageView.setVisibility(0);
      }
      for (;;)
      {
        if ((paramDocument != null) && (paramDocument.hasEmptyContainer()))
        {
          String str = paramDocument.getTemplate().emptyContainer.emptyMessage;
          if (!TextUtils.isEmpty(str)) {
            ((TextView)localView.findViewById(2131755747)).setText(str);
          }
        }
        this.mRecyclerView.setEmptyView(localView);
        return;
        label215:
        localFifeImageView.setVisibility(8);
      }
    }
    if (this.mAdapter != null) {
      this.mAdapter.onDestroy();
    }
    boolean bool1;
    label247:
    int i;
    label289:
    Object localObject;
    if (paramBundle != null)
    {
      bool1 = true;
      if ((paramDocument.mDocument.annotations == null) || (paramDocument.mDocument.annotations.template == null) || (paramDocument.mDocument.annotations.template.editorialSeriesContainer == null)) {
        break label482;
      }
      i = 1;
      if (i == 0) {
        break label488;
      }
      localObject = new EditorialRecyclerViewAdapter(this.mContext, this.mDfeApi, this.mNavManager, this.mBitmapLoader, this.mToc, this.mClientMutationCache, paramDocument, (MultiDfeList)this.mData, bool1, paramPlayStoreUiElementNode);
      this.mAdapter = ((CardRecyclerViewAdapter)localObject);
      if (!this.mHasLoadedAtLeastOnce) {
        break label692;
      }
      this.mRecyclerView.setEmptyView(null);
    }
    for (;;)
    {
      this.mRecyclerView.setAdapter(this.mAdapter);
      this.mRecyclerView.getRecycledViewPool().setMaxRecycledViews(6, 20);
      FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
      if (((this.mAdapter instanceof CardRecyclerViewAdapterV2)) && (localFinskyExperiments.isH20StoreEnabled())) {
        this.mRecyclerView.setScrollingTouchSlop(1);
      }
      this.mRecyclerView.addItemDecoration(new CardStreamMarginItemDecoration(paramViewGroup.getContext()));
      if (this.mTabDataListener != null) {
        this.mTabDataListener.onTabDataReady(this.mAdapter);
      }
      if (paramBundle == null) {
        break;
      }
      this.mAdapter.onRestoreInstanceState(this.mRecyclerView, paramBundle);
      return;
      bool1 = false;
      break label247;
      label482:
      i = 0;
      break label289;
      label488:
      if ((paramDocument.getTemplate() != null) && (paramDocument.getTemplate().bundleContainer != null)) {}
      for (int j = 1;; j = 0)
      {
        if (j == 0) {
          break label567;
        }
        localObject = new BundleRecyclerViewAdapter(this.mContext, this.mDfeApi, this.mNavManager, this.mBitmapLoader, this.mToc, this.mClientMutationCache, paramDocument, (MultiDfeList)this.mData, bool1, paramPlayStoreUiElementNode);
        break;
      }
      label567:
      int k;
      if ((paramDocument.mDocument.annotations != null) && (paramDocument.mDocument.annotations.template != null) && (paramDocument.mDocument.annotations.template.recommendationsContainer != null))
      {
        k = 1;
        label609:
        if ((k == 0) && (!paramDocument.hasRecommendationsContainerWithHeaderTemplate())) {
          break label686;
        }
      }
      label686:
      for (boolean bool2 = true;; bool2 = false)
      {
        localObject = new CardRecyclerViewAdapterV2(this.mContext, this.mDfeApi, this.mNavManager, this.mBitmapLoader, this.mToc, this.mClientMutationCache, (MultiDfeList)this.mData, paramArrayOfQuickLinkInfo, paramString, bool1, bool2, paramInt, paramPlayStoreUiElementNode, paramSpacerHeightProvider);
        break;
        k = 0;
        break label609;
      }
      label692:
      this.mRecyclerView.setEmptyView(this.mContentLayout.findViewById(2131755730));
    }
  }
  
  public final void detachFromData()
  {
    if (this.mData != null)
    {
      ContainerList localContainerList = ((MultiDfeList)this.mData).mContainerList;
      localContainerList.removeDataChangedListener(this);
      localContainerList.removeErrorListener(this);
      this.mData = null;
    }
  }
  
  public final void onDataChanged()
  {
    if ((!this.mHasLoadedAtLeastOnce) && (this.mRecyclerView != null))
    {
      this.mRecyclerView.setEmptyView(this.mContentLayout.findViewById(2131755730));
      this.mHasLoadedAtLeastOnce = true;
    }
    if (this.mAdapter != null) {
      this.mAdapter.mObservable.notifyChanged();
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    if (this.mRecyclerView != null) {
      this.mAdapter.triggerFooterErrorMode();
    }
  }
  
  public final void setData(MultiDfeList paramMultiDfeList)
  {
    detachFromData();
    super.setData(paramMultiDfeList);
    this.mHasLoadedAtLeastOnce = false;
    if (this.mData != null)
    {
      ContainerList localContainerList = paramMultiDfeList.mContainerList;
      if (localContainerList != null)
      {
        localContainerList.addDataChangedListener(this);
        localContainerList.addErrorListener(this);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.DfeRecyclerBinder
 * JD-Core Version:    0.7.0.1
 */