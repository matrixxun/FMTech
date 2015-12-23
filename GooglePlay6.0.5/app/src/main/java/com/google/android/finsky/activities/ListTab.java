package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.CardRecyclerViewAdapter;
import com.google.android.finsky.adapters.EmptyRecyclerViewAdapter;
import com.google.android.finsky.adapters.QuickLinkHelper.QuickLinkInfo;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.DfeRecyclerBinder;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.LayoutSwitcher.RetryButtonListener;
import com.google.android.finsky.layout.SpacerHeightAwareFrameLayout;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.PlayRecyclerView;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.SelectableUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.BrowseTab;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.utils.config.GservicesValue;

public final class ListTab
  implements Response.ErrorListener, ViewPagerTab, OnDataChangedListener, LayoutSwitcher.RetryButtonListener, PlayStoreUiElementNode
{
  protected final ActionBarController mActionBarController;
  private DfeRecyclerBinder mBinder;
  private final ClientMutationCache mClientMutationCache;
  private Document mContainerDocument;
  private final DfeApi mDfeApi;
  private boolean mIsCurrentlySelected;
  private final LayoutInflater mLayoutInflater;
  private LayoutSwitcher mLayoutSwitcher;
  private final MultiDfeList mMultiDfeList;
  protected final NavigationManager mNavigationManager;
  private SelectableUiElementNode mParentNode;
  private final boolean mPreventTabPreloading;
  private final QuickLinkHelper.QuickLinkInfo[] mQuickLinks;
  private boolean mRecyclerViewBoundAlready = false;
  private ViewGroup mRecyclerViewTabWrapper;
  private ObjectMap mRestoredInstanceState = ObjectMap.EMPTY;
  boolean mShouldDeferDataDisplay;
  private final SpacerHeightProvider mSpacerHeightProvider;
  private final TabbedAdapter.TabDataListener mTabDataListener;
  private final int mTabMode;
  private final String mTitle;
  protected final DfeToc mToc;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(408);
  
  public ListTab(Context paramContext, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeApi paramDfeApi, LayoutInflater paramLayoutInflater, TabbedAdapter.TabData paramTabData, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, boolean paramBoolean, ActionBarController paramActionBarController, int paramInt, TabbedAdapter.TabDataListener paramTabDataListener, SpacerHeightProvider paramSpacerHeightProvider)
  {
    this.mLayoutInflater = paramLayoutInflater;
    this.mMultiDfeList = paramTabData.multiDfeList;
    this.mQuickLinks = paramTabData.quickLinks;
    int i;
    String str;
    label74:
    ContainerList localContainerList;
    int k;
    label178:
    int m;
    if (paramInt == 2)
    {
      i = 1;
      if (i == 0) {
        break label308;
      }
      str = paramTabData.browseTab.title;
      this.mTitle = str;
      this.mParentNode = paramTabData.elementNode;
      localContainerList = this.mMultiDfeList.mContainerList;
      localContainerList.mWindowDistance = (paramContext.getResources().getInteger(2131623938) * UiUtils.getFeaturedGridColumnCount(paramContext.getResources(), 1.0D));
      localContainerList.addDataChangedListener(this);
      localContainerList.addErrorListener(this);
      this.mShouldDeferDataDisplay = paramBoolean;
      int j = paramNavigationManager.getCurrentPageType();
      if ((!FinskyApp.get().getExperiments().isEnabled(12605163L)) && (!((Boolean)G.preventTabPreloadingOnHomePage.get()).booleanValue())) {
        break label314;
      }
      k = 1;
      if (j != 1) {
        break label320;
      }
      m = 1;
      label187:
      if ((k == 0) || (m == 0)) {
        break label326;
      }
    }
    label308:
    label314:
    label320:
    label326:
    for (boolean bool = true;; bool = false)
    {
      this.mPreventTabPreloading = bool;
      if (!this.mPreventTabPreloading) {
        localContainerList.startLoadItems();
      }
      this.mToc = paramDfeToc;
      this.mNavigationManager = paramNavigationManager;
      this.mDfeApi = paramDfeApi;
      this.mClientMutationCache = paramClientMutationCache;
      this.mActionBarController = paramActionBarController;
      this.mTabMode = paramInt;
      this.mTabDataListener = paramTabDataListener;
      this.mSpacerHeightProvider = paramSpacerHeightProvider;
      this.mBinder = new DfeRecyclerBinder(this.mToc, this.mDfeApi, this.mClientMutationCache);
      this.mBinder.init(paramContext, this.mNavigationManager, paramBitmapLoader);
      return;
      i = 0;
      break;
      str = null;
      break label74;
      k = 0;
      break label178;
      m = 0;
      break label187;
    }
  }
  
  private void setChildSpacerHeightProvider(int paramInt)
  {
    ((SpacerHeightAwareFrameLayout)this.mRecyclerViewTabWrapper.findViewById(paramInt)).setSpacerHeightProvider(this.mSpacerHeightProvider);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final View getView(int paramInt)
  {
    if (this.mRecyclerViewTabWrapper == null)
    {
      this.mRecyclerViewTabWrapper = ((ViewGroup)this.mLayoutInflater.inflate(2130969059, null));
      setChildSpacerHeightProvider(2131755697);
      setChildSpacerHeightProvider(2131755806);
      this.mLayoutSwitcher = new LayoutSwitcher(this.mRecyclerViewTabWrapper, 2131756027, 2131755806, 2131755697, this, 0);
      PlayRecyclerView localPlayRecyclerView = (PlayRecyclerView)this.mRecyclerViewTabWrapper.findViewById(2131755329);
      localPlayRecyclerView.getContext();
      localPlayRecyclerView.setLayoutManager(new LinearLayoutManager());
      localPlayRecyclerView.setAdapter(new EmptyRecyclerViewAdapter());
      syncViewToState();
    }
    return this.mRecyclerViewTabWrapper;
  }
  
  public final void onDataChanged()
  {
    syncViewToState();
  }
  
  public final void onDestroy()
  {
    DfeRecyclerBinder localDfeRecyclerBinder = this.mBinder;
    localDfeRecyclerBinder.detachFromData();
    if (localDfeRecyclerBinder.mAdapter != null)
    {
      localDfeRecyclerBinder.mAdapter.onDestroy();
      localDfeRecyclerBinder.mAdapter = null;
    }
    localDfeRecyclerBinder.mRecyclerView = null;
    ContainerList localContainerList = this.mMultiDfeList.mContainerList;
    localContainerList.removeDataChangedListener(this);
    localContainerList.removeErrorListener(this);
    this.mMultiDfeList.flushData();
    this.mRecyclerViewTabWrapper = null;
    this.mRecyclerViewBoundAlready = false;
  }
  
  public final ObjectMap onDestroyView()
  {
    if (this.mRecyclerViewTabWrapper != null)
    {
      PlayRecyclerView localPlayRecyclerView = (PlayRecyclerView)this.mRecyclerViewTabWrapper.findViewById(2131755329);
      if (localPlayRecyclerView.getVisibility() == 0)
      {
        ObjectMap localObjectMap = new ObjectMap();
        DfeRecyclerBinder localDfeRecyclerBinder = this.mBinder;
        Bundle localBundle = localObjectMap.mBundle;
        if (localDfeRecyclerBinder.mAdapter != null) {
          localDfeRecyclerBinder.mAdapter.onSaveInstanceState(localPlayRecyclerView, localBundle);
        }
        return localObjectMap;
      }
    }
    return null;
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    syncViewToState();
  }
  
  public final void onRestoreInstanceState(ObjectMap paramObjectMap)
  {
    if (this.mRestoredInstanceState != null) {
      this.mRestoredInstanceState = paramObjectMap;
    }
  }
  
  public final void onRetry()
  {
    ContainerList localContainerList = this.mMultiDfeList.mContainerList;
    localContainerList.resetItems();
    localContainerList.mCurrentRequest = null;
    localContainerList.startLoadItems();
    syncViewToState();
  }
  
  public final void setTabSelected(boolean paramBoolean)
  {
    if (paramBoolean != this.mIsCurrentlySelected)
    {
      if (!paramBoolean) {
        break label87;
      }
      ContainerList localContainerList = this.mMultiDfeList.mContainerList;
      if ((this.mPreventTabPreloading) && (!localContainerList.isReady()) && (!localContainerList.isInTransit())) {
        localContainerList.startLoadItems();
      }
      FinskyEventLog.startNewImpression(this.mParentNode);
      this.mParentNode.setNodeSelected(true);
      if (this.mParentNode.getPlayStoreUiElement().child.length == 0) {
        FinskyEventLog.requestImpressions(this.mRecyclerViewTabWrapper);
      }
    }
    for (;;)
    {
      this.mIsCurrentlySelected = paramBoolean;
      return;
      label87:
      this.mParentNode.setNodeSelected(false);
    }
  }
  
  final void syncViewToState()
  {
    if (this.mRecyclerViewTabWrapper == null) {}
    do
    {
      ContainerList localContainerList;
      do
      {
        return;
        localContainerList = this.mMultiDfeList.mContainerList;
        if (!localContainerList.inErrorState()) {
          break;
        }
      } while (this.mRecyclerViewBoundAlready);
      this.mLayoutSwitcher.switchToErrorMode(ErrorStrings.get(FinskyApp.get(), localContainerList.getVolleyError()));
      return;
      if ((this.mShouldDeferDataDisplay) || (!localContainerList.isReady())) {
        break;
      }
      if (this.mContainerDocument == null) {
        this.mContainerDocument = localContainerList.mContainerDocument;
      }
      if (this.mContainerDocument != null) {
        FinskyEventLog.setServerLogCookie(this.mUiElementProto, this.mContainerDocument.mDocument.serverLogsCookie);
      }
      this.mLayoutSwitcher.switchToDataMode();
    } while (this.mRecyclerViewBoundAlready);
    int i;
    DfeRecyclerBinder localDfeRecyclerBinder;
    ViewGroup localViewGroup;
    Document localDocument;
    QuickLinkHelper.QuickLinkInfo[] arrayOfQuickLinkInfo;
    String str;
    int j;
    if (this.mRestoredInstanceState != null)
    {
      i = 1;
      this.mBinder.setData(this.mMultiDfeList);
      localDfeRecyclerBinder = this.mBinder;
      localViewGroup = this.mRecyclerViewTabWrapper;
      localDocument = this.mContainerDocument;
      arrayOfQuickLinkInfo = this.mQuickLinks;
      str = this.mTitle;
      j = this.mTabMode;
      if (i == 0) {
        break label311;
      }
    }
    label311:
    for (Bundle localBundle = this.mRestoredInstanceState.mBundle;; localBundle = null)
    {
      localDfeRecyclerBinder.bind(localViewGroup, localDocument, arrayOfQuickLinkInfo, str, j, localBundle, this.mTabDataListener, this, this.mSpacerHeightProvider);
      if ((i == 0) && (Build.VERSION.SDK_INT >= 16))
      {
        PlayRecyclerView localPlayRecyclerView = (PlayRecyclerView)this.mRecyclerViewTabWrapper.findViewById(2131755329);
        Animation localAnimation = AnimationUtils.loadAnimation(localPlayRecyclerView.getContext(), 2131034138);
        localAnimation.setInterpolator(new FastOutSlowInInterpolator());
        localAnimation.setDuration(300L);
        LayoutAnimationController localLayoutAnimationController = new LayoutAnimationController(localAnimation);
        localLayoutAnimationController.setDelay(0.1F);
        localPlayRecyclerView.setLayoutAnimation(localLayoutAnimationController);
      }
      this.mRestoredInstanceState = null;
      this.mRecyclerViewBoundAlready = true;
      return;
      i = 0;
      break;
    }
    this.mLayoutSwitcher.switchToLoadingMode();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ListTab
 * JD-Core Version:    0.7.0.1
 */