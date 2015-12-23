package com.google.android.finsky.activities.mywishlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.CardRecyclerViewAdapter;
import com.google.android.finsky.adapters.EmptyRecyclerViewAdapter;
import com.google.android.finsky.adapters.MyWishlistRecyclerViewAdapter;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.HeaderLayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.WishlistHelper;
import com.google.android.play.headerlist.PlayHeaderListLayout;

public final class MyWishlistFragment
  extends PageFragment
{
  private MyWishlistRecyclerViewAdapter mAdapter;
  private String mBreadcrumb;
  private DfeList mDfeList;
  private boolean mIsAdapterSet;
  private long mLastLoadedTimeMs;
  private Libraries mLibraries;
  private PlayRecyclerView mMyWishlistView;
  private Bundle mRecyclerViewRestoreBundle = new Bundle();
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(5);
  
  private void clearDfeList()
  {
    if (this.mDfeList != null)
    {
      this.mDfeList.removeDataChangedListener(this);
      this.mDfeList.removeErrorListener(this);
      this.mDfeList = null;
    }
  }
  
  public static MyWishlistFragment newInstance()
  {
    MyWishlistFragment localMyWishlistFragment = new MyWishlistFragment();
    localMyWishlistFragment.setArgument("finsky.PageFragment.toc", FinskyApp.get().mToc);
    return localMyWishlistFragment;
  }
  
  private void rebindAdapter()
  {
    if (this.mMyWishlistView == null)
    {
      FinskyLog.w("Recycler view null, ignoring.", new Object[0]);
      return;
    }
    boolean bool = CardRecyclerViewAdapter.hasRestoreData(this.mRecyclerViewRestoreBundle);
    if (this.mAdapter == null)
    {
      byte[] arrayOfByte = this.mDfeList.mContainerDocument.mDocument.serverLogsCookie;
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, arrayOfByte);
      ClientMutationCache localClientMutationCache = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName());
      this.mAdapter = new MyWishlistRecyclerViewAdapter(this.mContext, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, this.mDfeToc, localClientMutationCache, new MultiDfeList(this.mDfeList), this, bool);
      WishlistHelper.addWishlistStatusListener(this.mAdapter);
    }
    if (!this.mIsAdapterSet)
    {
      this.mIsAdapterSet = true;
      this.mMyWishlistView.setAdapter(this.mAdapter);
      if (bool)
      {
        this.mAdapter.onRestoreInstanceState(this.mMyWishlistView, this.mRecyclerViewRestoreBundle);
        this.mRecyclerViewRestoreBundle.clear();
      }
      this.mMyWishlistView.setEmptyView(this.mDataView.findViewById(2131755730));
      return;
    }
    this.mAdapter.updateAdapterData(this.mDfeList);
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new HeaderLayoutSwitcher(paramContentFrame, this);
  }
  
  protected final int getDefaultHeaderShadowMode()
  {
    return 3;
  }
  
  protected final int getLayoutRes()
  {
    return 2130968781;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mBreadcrumb = this.mContext.getString(2131362323);
    this.mMyWishlistView = ((PlayRecyclerView)this.mDataView.findViewById(2131755329));
    this.mMyWishlistView.setVisibility(0);
    this.mMyWishlistView.setSaveEnabled(false);
    LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager();
    this.mMyWishlistView.setLayoutManager(localLinearLayoutManager);
    this.mMyWishlistView.setAdapter(new EmptyRecyclerViewAdapter());
    rebindActionBar();
    if (WishlistHelper.hasMutationOccurredSince(this.mLastLoadedTimeMs))
    {
      clearDfeList();
      this.mRecyclerViewRestoreBundle.clear();
    }
    boolean bool;
    if (this.mDfeList == null)
    {
      bool = false;
      if (bool) {
        break label147;
      }
      requestData();
      switchToLoading();
    }
    for (;;)
    {
      this.mActionBarController.enableActionBarOverlay();
      return;
      bool = this.mDfeList.isReady();
      break;
      label147:
      rebindAdapter();
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
    this.mLibraries = FinskyApp.get().mLibraries;
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContentFrame localContentFrame = (ContentFrame)super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    FinskyHeaderListLayout localFinskyHeaderListLayout = (FinskyHeaderListLayout)this.mDataView;
    localFinskyHeaderListLayout.configure(new FinskyHeaderListLayout.FinskyConfigurator(localFinskyHeaderListLayout.getContext())
    {
      protected final void addContentView(LayoutInflater paramAnonymousLayoutInflater, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousLayoutInflater.inflate(2130969189, paramAnonymousViewGroup);
      }
      
      protected final int getListViewId()
      {
        return 2131755329;
      }
    });
    localFinskyHeaderListLayout.setContentViewId(2131756235);
    return localContentFrame;
  }
  
  public final void onDataChanged()
  {
    super.onDataChanged();
    rebindAdapter();
  }
  
  public final void onDestroyView()
  {
    if ((this.mMyWishlistView != null) && (this.mMyWishlistView.getVisibility() == 0) && (this.mAdapter != null)) {
      this.mAdapter.onSaveInstanceState(this.mMyWishlistView, this.mRecyclerViewRestoreBundle);
    }
    this.mMyWishlistView = null;
    if (this.mAdapter != null)
    {
      this.mAdapter.onDestroy();
      WishlistHelper.removeWishlistStatusListener(this.mAdapter);
      this.mAdapter = null;
      this.mIsAdapterSet = false;
    }
    if ((this.mDataView instanceof PlayHeaderListLayout)) {
      ((PlayHeaderListLayout)this.mDataView).detachIfNeeded();
    }
    super.onDestroyView();
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mBreadcrumb);
    this.mPageFragmentHost.updateCurrentBackendId(0, true);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews() {}
  
  protected final void requestData()
  {
    clearDfeList();
    byte[] arrayOfByte = this.mLibraries.getAccountLibrary(FinskyApp.get().getCurrentAccount()).getServerToken("u-wl");
    String str = this.mDfeApi.getLibraryUrl(0, "u-wl", 7, arrayOfByte);
    this.mDfeList = new DfeList(this.mDfeApi, str, true);
    this.mDfeList.addDataChangedListener(this);
    this.mDfeList.addErrorListener(this);
    this.mDfeList.startLoadItems();
    this.mLastLoadedTimeMs = System.currentTimeMillis();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.mywishlist.MyWishlistFragment
 * JD-Core Version:    0.7.0.1
 */