package com.google.android.finsky.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.CardRecyclerViewAdapter;
import com.google.android.finsky.adapters.SearchAdapter;
import com.google.android.finsky.analytics.EventProtoCache;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreLogEvent;
import com.google.android.finsky.analytics.PlayStore.PlayStoreSearchEvent;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeSearch;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.HeaderLayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.navigationmanager.NavigationState;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.search.SearchLogger;
import com.google.android.finsky.utils.AdUtils;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import java.util.Stack;

public final class SearchFragment
  extends PageFragment
{
  private CardRecyclerViewAdapter mAdapter;
  private Button mClearFamilySafeSearchModeButton;
  private ClientMutationCache mClientMutationCache;
  private int mContextBackendId;
  private boolean mIsAdapterSet;
  private boolean mIsFamilySafeSearchModeEnabled;
  private String mQuery;
  private PlayRecyclerView mRecyclerView;
  private Bundle mRecyclerViewRestoreBundle = new Bundle();
  private boolean mRetriedSearch = false;
  private PlayStoreUiElementNode mSafeSearchButtonElementNode;
  private DfeSearch mSearchData;
  private String mSearchUrl;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(3);
  private boolean mhasLoggedSearchEvent;
  
  private boolean isDataReady()
  {
    return (this.mSearchData != null) && (this.mSearchData.isReady());
  }
  
  private String maybeAddFamilySearchFilterToSearchUrl(String paramString)
  {
    if (this.mIsFamilySafeSearchModeEnabled)
    {
      Uri.Builder localBuilder = Uri.parse(paramString).buildUpon();
      localBuilder.appendQueryParameter("fss", Boolean.toString(this.mIsFamilySafeSearchModeEnabled));
      paramString = localBuilder.build().toString();
    }
    return paramString;
  }
  
  public static SearchFragment newInstance(String paramString1, String paramString2, int paramInt)
  {
    SearchFragment localSearchFragment = new SearchFragment();
    localSearchFragment.setArgument("finsky.PageFragment.toc", FinskyApp.get().mToc);
    localSearchFragment.setArgument("SearchFragment.searchUrl", paramString2);
    if (paramString1 != null)
    {
      localSearchFragment.setArgument("SearchFragment.query", paramString1);
      if (paramInt < 0) {
        break label60;
      }
    }
    for (;;)
    {
      localSearchFragment.mArguments.putInt("SearchFragment.backendId", paramInt);
      return localSearchFragment;
      paramString1 = "";
      break;
      label60:
      paramInt = 0;
    }
  }
  
  private void rebindAdapter()
  {
    if (this.mRecyclerView == null) {
      FinskyLog.w("RecyclerView null, ignoring.", new Object[0]);
    }
    while (!isDataReady()) {
      return;
    }
    boolean bool1 = SearchAdapter.hasRestoreData(this.mRecyclerViewRestoreBundle);
    if (this.mAdapter == null)
    {
      FinskyEventLog.setServerLogCookie(this.mUiElementProto, this.mSearchData.getServerLogsCookie());
      if (this.mSearchData.mContainerDocument == null) {
        break label283;
      }
    }
    label283:
    for (byte[] arrayOfByte = this.mSearchData.mContainerDocument.mDocument.serverLogsCookie;; arrayOfByte = null)
    {
      GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(408, arrayOfByte, null, this);
      childImpression(localGenericUiElementNode);
      NavigationManager localNavigationManager = this.mNavigationManager;
      boolean bool2 = this.mSearchData.containsAd();
      if (!localNavigationManager.mBackStack.isEmpty())
      {
        NavigationState localNavigationState = (NavigationState)localNavigationManager.mBackStack.peek();
        if (localNavigationState.pageType == 7) {
          localNavigationState.canTriggerSearchSurvey = bool2;
        }
      }
      this.mAdapter = new SearchAdapter(this.mContext, this.mDfeApi, this.mNavigationManager, this.mBitmapLoader, this.mDfeToc, FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName()), new MultiDfeList(this.mSearchData), bool1, this.mIsFamilySafeSearchModeEnabled, localGenericUiElementNode);
      if (this.mIsAdapterSet) {
        break;
      }
      this.mIsAdapterSet = true;
      this.mRecyclerView.setAdapter(this.mAdapter);
      if (bool1)
      {
        this.mAdapter.onRestoreInstanceState(this.mRecyclerView, this.mRecyclerViewRestoreBundle);
        this.mRecyclerViewRestoreBundle.clear();
      }
      this.mRecyclerView.setEmptyView(this.mDataView.findViewById(2131755730));
      return;
    }
    this.mAdapter.updateAdapterData(this.mSearchData);
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new HeaderLayoutSwitcher(paramContentFrame, this);
  }
  
  public final int getActionBarColor()
  {
    return getResources().getColor(2131689681);
  }
  
  @TargetApi(22)
  protected final Transition getCustomExitTransition()
  {
    return new Fade();
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
    if ((this.mSearchData != null) && (this.mSearchData.hasBackendId())) {
      this.mPageFragmentHost.updateCurrentBackendId(this.mSearchData.getBackendId(), true);
    }
    this.mRecyclerView = ((PlayRecyclerView)this.mDataView.findViewById(2131756095));
    this.mRecyclerView.setVisibility(0);
    this.mRecyclerView.setSaveEnabled(false);
    this.mRecyclerView.setLayoutManager(new LinearLayoutManager());
    rebindActionBar();
    TextView localTextView = (TextView)this.mDataView.findViewById(2131755747);
    if (this.mIsFamilySafeSearchModeEnabled)
    {
      Resources localResources2 = getResources();
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mQuery;
      localTextView.setText(localResources2.getString(2131362376, arrayOfObject2));
      if (isDataReady()) {
        break label319;
      }
      switchToLoading();
      requestData();
      rebindActionBar();
    }
    for (;;)
    {
      this.mActionBarController.enableActionBarOverlay();
      this.mClearFamilySafeSearchModeButton = ((Button)this.mDataView.findViewById(2131756096));
      this.mClearFamilySafeSearchModeButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(298, null, SearchFragment.this);
          SearchFragment.this.mClientMutationCache.mFamilySafeSearchMode = false;
          SearchFragment.this.mNavigationManager.goToSearch$36098d51(SearchFragment.this.mQuery, SearchFragment.this.mContextBackendId);
        }
      });
      if (this.mIsFamilySafeSearchModeEnabled) {
        this.mClientMutationCache.mFamilySafeSearchMode = this.mIsFamilySafeSearchModeEnabled;
      }
      if (this.mClearFamilySafeSearchModeButton != null)
      {
        if (!this.mIsFamilySafeSearchModeEnabled) {
          break label326;
        }
        this.mClearFamilySafeSearchModeButton.setVisibility(0);
        if (this.mSafeSearchButtonElementNode == null)
        {
          this.mSafeSearchButtonElementNode = new GenericUiElementNode(298, null, null, this);
          FinskyApp.get().getEventLogger().logPathImpression(0L, this.mSafeSearchButtonElementNode);
        }
      }
      return;
      Resources localResources1 = getResources();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mQuery;
      localTextView.setText(localResources1.getString(2131362385, arrayOfObject1));
      break;
      label319:
      rebindAdapter();
    }
    label326:
    this.mClearFamilySafeSearchModeButton.setVisibility(8);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mClientMutationCache = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName());
    this.mIsFamilySafeSearchModeEnabled = this.mClientMutationCache.mFamilySafeSearchMode;
    this.mQuery = this.mArguments.getString("SearchFragment.query");
    this.mSearchUrl = maybeAddFamilySearchFilterToSearchUrl(this.mArguments.getString("SearchFragment.searchUrl"));
    this.mContextBackendId = this.mArguments.getInt("SearchFragment.backendId");
    AdUtils.instantiateAdShieldIfNeededAndRun(getActivity(), null);
    setRetainInstance$1385ff();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContentFrame localContentFrame = (ContentFrame)super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    FinskyHeaderListLayout localFinskyHeaderListLayout = (FinskyHeaderListLayout)this.mDataView;
    localFinskyHeaderListLayout.configure(new FinskyHeaderListLayout.FinskyConfigurator(localFinskyHeaderListLayout.getContext())
    {
      protected final void addContentView(LayoutInflater paramAnonymousLayoutInflater, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousLayoutInflater.inflate(2130969092, paramAnonymousViewGroup);
      }
      
      protected final int getHeaderShadowMode()
      {
        return 2;
      }
      
      protected final int getListViewId()
      {
        return 2131756095;
      }
    });
    localFinskyHeaderListLayout.setContentViewId(2131756094);
    return localContentFrame;
  }
  
  public final void onDataChanged()
  {
    int i = 2131362385;
    if ((this.mSearchData.isReady()) && (this.mSearchData.mContainerDocument == null)) {
      if (!this.mRetriedSearch)
      {
        String str1 = maybeAddFamilySearchFilterToSearchUrl(DfeUtils.formSearchUrl(this.mQuery, 0));
        if (!str1.equals(this.mSearchUrl))
        {
          int j = this.mSearchData.getBackendId();
          Resources localResources2 = getResources();
          switch (j)
          {
          case 5: 
          default: 
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = this.mQuery;
            String str2 = localResources2.getString(i, arrayOfObject2);
            UiUtils.sendAccessibilityEventWithText(this.mContext, str2, this.mRecyclerView);
            this.mRetriedSearch = true;
            this.mSearchUrl = str1;
            this.mSearchData.removeDataChangedListener(this);
            this.mSearchData.removeErrorListener(this);
            this.mSearchData = null;
            this.mhasLoggedSearchEvent = false;
            requestData();
          }
        }
      }
    }
    do
    {
      return;
      i = 2131362386;
      break;
      i = 2131362387;
      break;
      i = 2131362390;
      break;
      i = 2131362388;
      break;
      i = 2131362389;
      break;
      Context localContext = this.mContext;
      Resources localResources1 = getResources();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mQuery;
      UiUtils.sendAccessibilityEventWithText(localContext, localResources1.getString(i, arrayOfObject1), this.mRecyclerView);
      FinskyApp.get().getEventLogger().logBackgroundEvent(523, null);
    } while (!this.mSearchData.isReady());
    super.onDataChanged();
  }
  
  public final void onDestroyView()
  {
    if ((this.mAdapter != null) && (this.mRecyclerView != null) && (this.mRecyclerView.getVisibility() == 0)) {
      this.mAdapter.onSaveInstanceState(this.mRecyclerView, this.mRecyclerViewRestoreBundle);
    }
    if (this.mRecyclerView != null) {
      this.mRecyclerView.setRecyclerListener(null);
    }
    this.mRecyclerView = null;
    if (this.mAdapter != null)
    {
      this.mAdapter.onDestroy();
      this.mAdapter = null;
    }
    this.mIsAdapterSet = false;
    this.mClearFamilySafeSearchModeButton = null;
    ((MainActivity)this.mPageFragmentHost).getActionBarHelper().setDefaultSearchQuery("");
    if ((this.mDataView instanceof PlayHeaderListLayout)) {
      ((PlayHeaderListLayout)this.mDataView).detachIfNeeded();
    }
    super.onDestroyView();
  }
  
  public final void onEnterActionBarSearchMode() {}
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    SearchLogger.logSearchVolleyError(523, paramVolleyError);
    super.onErrorResponse(paramVolleyError);
  }
  
  public final void onExitActionBarSearchMode() {}
  
  public final void rebindActionBar()
  {
    ActionBarHelper localActionBarHelper = ((MainActivity)this.mPageFragmentHost).getActionBarHelper();
    if ((this.mSearchData != null) && (this.mSearchData.hasBackendId())) {}
    for (int i = this.mSearchData.getBackendId(); (i == 3) && (this.mQuery.startsWith("pub:")); i = this.mContextBackendId)
    {
      Resources localResources2 = this.mContext.getResources();
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mQuery.replaceFirst("pub:", "");
      String str = localResources2.getString(2131361855, arrayOfObject2);
      this.mPageFragmentHost.updateActionBarTitle(str);
      this.mPageFragmentHost.updateCurrentBackendId(i, true);
      this.mPageFragmentHost.switchToSearchBoxOnlyActionBar(2);
      localActionBarHelper.setDefaultSearchQuery(this.mQuery);
      return;
    }
    Resources localResources1 = getResources();
    if (localResources1.getBoolean(2131427334)) {}
    for (int j = 2131362715;; j = 2131362943)
    {
      PageFragmentHost localPageFragmentHost = this.mPageFragmentHost;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mQuery;
      localPageFragmentHost.updateActionBarTitle(localResources1.getString(j, arrayOfObject1));
      break;
    }
  }
  
  protected final void rebindViews()
  {
    rebindAdapter();
    rebindActionBar();
  }
  
  protected final void requestData()
  {
    if (this.mSearchData == null)
    {
      this.mSearchData = new DfeSearch(this.mDfeApi, this.mQuery, this.mSearchUrl);
      this.mSearchData.addDataChangedListener(this);
      this.mSearchData.addErrorListener(this);
      if (!this.mhasLoggedSearchEvent)
      {
        PlayStore.PlayStoreSearchEvent localPlayStoreSearchEvent = FinskyEventLog.obtainPlayStoreSearchEvent();
        if (this.mQuery != null)
        {
          localPlayStoreSearchEvent.query = this.mQuery;
          localPlayStoreSearchEvent.hasQuery = true;
        }
        if (this.mSearchUrl != null)
        {
          localPlayStoreSearchEvent.queryUrl = this.mSearchUrl;
          localPlayStoreSearchEvent.hasQueryUrl = true;
        }
        FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
        if (FinskyEventLog.shouldSendEventToLogcat()) {
          FinskyEventLog.dumpSearchEvent(localPlayStoreSearchEvent);
        }
        PlayStore.PlayStoreLogEvent localPlayStoreLogEvent = localFinskyEventLog.mProtoCache.obtainPlayStoreLogEvent();
        localPlayStoreLogEvent.search = localPlayStoreSearchEvent;
        localFinskyEventLog.serializeAndWrite("5", localPlayStoreLogEvent);
        this.mhasLoggedSearchEvent = true;
      }
    }
    this.mSearchData.startLoadItems();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.SearchFragment
 * JD-Core Version:    0.7.0.1
 */