package com.google.android.finsky.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout.LayoutParams;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.layout.ContentFrame;
import com.google.android.finsky.layout.ControlsContainerBackground;
import com.google.android.finsky.layout.ControlsContainerBackgroundCoordinator;
import com.google.android.finsky.layout.FinskyTabStrip;
import com.google.android.finsky.layout.HeaderLayoutSwitcher;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.InsetsFrameLayout;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.layout.actionbar.ActionBarBackgroundUpdater;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.layout.play.FinskyViewPager;
import com.google.android.finsky.layout.play.FinskyViewPager.MeasureOverrider;
import com.google.android.finsky.layout.play.FinskyViewPagerScroller;
import com.google.android.finsky.layout.play.PlayHighlightsOverlayView;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.BrowseResponse;
import com.google.android.finsky.protos.Browse.BrowseTab;
import com.google.android.finsky.protos.PrivacySetting;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.protos.UserContext;
import com.google.android.finsky.transition.PageFade;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.IntMath;
import com.google.android.finsky.utils.LocationHelper;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.hats.HatsUtils;
import com.google.android.libraries.bind.bidi.BidiPagingHelper;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import com.google.android.play.headerlist.PlayHeaderListLayout.OnTabSelectedListener;
import com.google.android.play.headerlist.PlayHeaderListTabStrip;
import com.google.android.play.search.PlaySearchToolbar;
import com.google.android.play.utils.config.GservicesValue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TabbedBrowseFragment
  extends UrlBasedPageFragment
  implements ViewPager.OnPageChangeListener, SpacerHeightProvider, TabbedAdapter.TabDataListener, PlayHeaderListLayout.OnTabSelectedListener
{
  private ActionBarBackgroundUpdater mActionBarBackgroundUpdater;
  public int mBackendId = 0;
  private int mBackgroundType;
  private ViewGroup mBackgroundViewGroup;
  private String mBreadcrumb;
  public DfeBrowse mBrowseData;
  public ControlsContainerBackgroundCoordinator mControlsContainerBackgroundCoordinator;
  private boolean mDataIsReady;
  private ObjectMap mFragmentObjectMap = new ObjectMap();
  private boolean mHasHighlightsPager;
  private FinskyHeaderListLayout mHeaderListLayout;
  private int mHeaderShadowMode = 3;
  private HeroGraphicView mHeroGraphicView;
  private ViewGroup mHeroViewRoot;
  private DfeList[] mHighlightsData;
  private PlayHighlightsOverlayView mHighlightsOverlay;
  private FinskyViewPager mHighlightsPager;
  private HighlightsPagerAdapter mHighlightsPagerAdapter;
  private boolean mIgnoreHighlights;
  private int mLeadingSpacerHeight;
  private int mRestorePrevSelectedTabLogicalIndex = -1;
  private FinskyTabStrip mTabStrip;
  public TabbedAdapter mTabbedAdapter;
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(1);
  private boolean mUseFixedTabs;
  private UserContext mUserContext;
  public FinskyViewPager mViewPager;
  
  private void advanceState()
  {
    if ((this.mBrowseData == null) || (!this.mBrowseData.isReady()))
    {
      this.mIgnoreHighlights = false;
      this.mBrowseData = new DfeBrowse(this.mDfeApi, this.mUrl, this.mUserContext);
      this.mBrowseData.addDataChangedListener(this);
      this.mBrowseData.addErrorListener(this);
    }
    int i;
    for (;;)
    {
      return;
      i = this.mBrowseData.mBrowseResponse.landingTabIndex;
      Browse.BrowseTab[] arrayOfBrowseTab = this.mBrowseData.mBrowseResponse.browseTab;
      if ((this.mIgnoreHighlights) || (TextUtils.isEmpty(arrayOfBrowseTab[i].highlightsBannerUrl))) {
        break label237;
      }
      if ((this.mHighlightsData != null) && (this.mHighlightsData[i].isReady())) {
        break;
      }
      this.mHighlightsData = new DfeList[arrayOfBrowseTab.length];
      for (int j = 0; j < arrayOfBrowseTab.length; j++)
      {
        DfeList localDfeList = new DfeList(this.mDfeApi, arrayOfBrowseTab[j].highlightsBannerUrl, false);
        this.mHighlightsData[j] = localDfeList;
        if (j == this.mBrowseData.mBrowseResponse.landingTabIndex)
        {
          localDfeList.addDataChangedListener(this);
          localDfeList.addErrorListener(this);
          localDfeList.startLoadItems();
        }
      }
    }
    int k = this.mHighlightsData[i].getCount();
    boolean bool = false;
    if (k > 0) {
      bool = true;
    }
    this.mHasHighlightsPager = bool;
    label237:
    PlayStore.PlayStoreUiElement localPlayStoreUiElement = this.mUiElementProto;
    DfeBrowse localDfeBrowse = this.mBrowseData;
    if ((localDfeBrowse.mBrowseResponse == null) || (localDfeBrowse.mBrowseResponse.serverLogsCookie.length == 0)) {}
    for (byte[] arrayOfByte = null;; arrayOfByte = localDfeBrowse.mBrowseResponse.serverLogsCookie)
    {
      FinskyEventLog.setServerLogCookie(localPlayStoreUiElement, arrayOfByte);
      this.mDataIsReady = true;
      return;
    }
  }
  
  private ColorDrawable getBackgroundColorDrawable()
  {
    return new ColorDrawable(CorpusResourceUtils.getPrimaryColor(getActivity(), this.mBackendId));
  }
  
  private boolean hasFixedTabs()
  {
    return (this.mBrowseData.mBrowseResponse.browseTab.length > 1) && (this.mUseFixedTabs);
  }
  
  private boolean isSearchBoxOnly()
  {
    if (this.mHasHighlightsPager) {}
    for (;;)
    {
      return true;
      DfeToc localDfeToc = this.mDfeToc;
      String str = this.mUrl;
      if ((TextUtils.equals(str, localDfeToc.mToc.homeUrl)) || (TextUtils.equals(str, localDfeToc.mToc.entertainmentHomeUrl))) {}
      for (int i = 1; (i == 0) || (localDfeToc.getCorpusList().size() <= 1); i = 0) {
        return false;
      }
    }
  }
  
  public static TabbedBrowseFragment newInstance$30e04d94(String paramString1, String paramString2, int paramInt, DfeToc paramDfeToc, boolean paramBoolean)
  {
    TabbedBrowseFragment localTabbedBrowseFragment = new TabbedBrowseFragment();
    if (paramInt >= 0) {
      localTabbedBrowseFragment.mBackendId = paramInt;
    }
    if (!TextUtils.isEmpty(paramString2)) {
      localTabbedBrowseFragment.mBreadcrumb = paramString2;
    }
    localTabbedBrowseFragment.setDfeTocAndUrl(paramDfeToc, paramString1);
    localTabbedBrowseFragment.setArgument("TabbedBrowseFragment.UseFixedTabs", paramBoolean);
    localTabbedBrowseFragment.mUseFixedTabs = paramBoolean;
    return localTabbedBrowseFragment;
  }
  
  protected final LayoutSwitcher createLayoutSwitcher(ContentFrame paramContentFrame)
  {
    return new HeaderLayoutSwitcher(paramContentFrame, this);
  }
  
  public final int getActionBarColor()
  {
    if ((isSearchBoxOnly()) || (!this.mDataIsReady)) {
      return getResources().getColor(2131689681);
    }
    return CorpusResourceUtils.getPrimaryColor(getActivity(), this.mBackendId);
  }
  
  @TargetApi(22)
  protected final Transition getCustomExitTransition()
  {
    return new PageFade(this.mBackendId);
  }
  
  protected final int getDefaultHeaderShadowMode()
  {
    return this.mHeaderShadowMode;
  }
  
  protected final int getLayoutRes()
  {
    return 2130968781;
  }
  
  public final int getLeadingSpacerHeight()
  {
    return this.mLeadingSpacerHeight;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (!this.mDataIsReady)
    {
      switchToLoading();
      advanceState();
      rebindActionBar();
      this.mActionBarController.enableActionBarOverlay();
      if (this.mBackendId != 3) {
        break label57;
      }
      HatsUtils.showSurveyIfAvailable(3, this.mPageFragmentHost);
    }
    label57:
    while (this.mBackendId != 0)
    {
      return;
      rebindViews();
      break;
    }
    HatsUtils.showSurveyIfAvailable(1, this.mPageFragmentHost);
  }
  
  public final boolean onBackPressed()
  {
    if (!FinskyApp.get().getExperiments().isH20StoreEnabled()) {
      return super.onBackPressed();
    }
    if ((this.mNavigationManager.getCurrentPageType() == 1) && (this.mBackendId != 3))
    {
      DfeToc localDfeToc = this.mDfeToc;
      if (TextUtils.equals(this.mUrl, localDfeToc.mToc.homeUrl))
      {
        FinskyApp.get().getEventLogger().logClickEvent(600, null, this.mNavigationManager.getActivePage());
        this.mNavigationManager.goToAggregatedHome(localDfeToc);
        return true;
      }
    }
    return super.onBackPressed();
  }
  
  public final void onBeforeTabSelected(int paramInt)
  {
    TabbedAdapter.TabSelectionTracker.access$300$4437258e(this.mTabbedAdapter.mTabSelectionTracker);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    int i = 1;
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
    this.mUseFixedTabs = this.mArguments.getBoolean("TabbedBrowseFragment.UseFixedTabs");
    int j;
    Integer localInteger;
    int k;
    label113:
    int n;
    if (isSearchBoxOnly())
    {
      j = i;
      this.mBackgroundType = j;
      if (!FinskyApp.get().getExperiments().isEnabled(12603100L)) {
        break label229;
      }
      String str = FinskyApp.get().getCurrentAccountName();
      localInteger = (Integer)FinskyPreferences.locationSuggestionsEnabled.get(str).get();
      PrivacySetting localPrivacySetting = this.mDfeToc.getUserPrivacySetting(i);
      if (localPrivacySetting == null) {
        break label229;
      }
      if (!localPrivacySetting.enabledByDefault) {
        break label199;
      }
      if (localInteger.intValue() == 2) {
        break label193;
      }
      k = i;
      if (k == 0) {
        break label241;
      }
      String[] arrayOfString = Utils.commaUnpackStrings((String)G.homeBrowseUrlsForUserContextHeader.get());
      int m = arrayOfString.length;
      n = 0;
      label140:
      if (n >= m) {
        break label241;
      }
      if (!arrayOfString[n].equals(this.mUrl)) {
        break label235;
      }
    }
    for (;;)
    {
      if (i == 0) {
        break label246;
      }
      this.mUserContext = new UserContext();
      this.mUserContext.location = LocationHelper.getLocationProto();
      return;
      j = 0;
      break;
      label193:
      k = 0;
      break label113;
      label199:
      if ((localInteger.intValue() == i) || (localInteger.intValue() == 3))
      {
        k = i;
        break label113;
      }
      k = 0;
      break label113;
      label229:
      k = 0;
      break label113;
      label235:
      n++;
      break label140;
      label241:
      i = 0;
    }
    label246:
    this.mUserContext = null;
  }
  
  public final void onDataChanged()
  {
    if (!this.mDataIsReady) {
      advanceState();
    }
    if (this.mDataIsReady) {
      super.onDataChanged();
    }
  }
  
  public final void onDestroyView()
  {
    ObjectMap localObjectMap2;
    TabbedAdapter localTabbedAdapter;
    if ((this.mDataIsReady) && (this.mViewPager != null))
    {
      this.mRestorePrevSelectedTabLogicalIndex = BidiPagingHelper.getLogicalPosition(this.mTabbedAdapter, this.mViewPager.getCurrentItem());
      localObjectMap2 = new ObjectMap();
      localTabbedAdapter = this.mTabbedAdapter;
      localTabbedAdapter.mIsViewDestroyed = true;
      if ((localTabbedAdapter.mTabDataList != null) && (!localTabbedAdapter.mTabDataList.isEmpty())) {
        break label286;
      }
    }
    label286:
    ArrayList localArrayList;
    for (Object localObject = null;; localObject = localArrayList)
    {
      localObjectMap2.put("TabbedAdapter.TabInstanceStates", localObject);
      localObjectMap2.put("TabbedAdapter.TabDfeLists", localTabbedAdapter.getMultiDfeLists());
      this.mFragmentObjectMap.put("TabbedBrowseFragment.AdapterState", localObjectMap2);
      this.mPageFragmentHost.hideSatisfactionSurveyV2();
      if (this.mHeaderListLayout != null)
      {
        this.mHeaderListLayout.setOnPageChangeListener(null);
        this.mHeaderListLayout.setOnTabSelectedListener(null);
      }
      if (this.mViewPager != null)
      {
        this.mViewPager.setAdapter(null);
        this.mViewPager = null;
      }
      this.mTabbedAdapter = null;
      if (this.mHeaderListLayout != null) {
        this.mHeaderListLayout.detachIfNeeded();
      }
      if (this.mActionBarBackgroundUpdater != null)
      {
        this.mActionBarBackgroundUpdater.reset();
        this.mActionBarBackgroundUpdater = null;
      }
      this.mBackgroundViewGroup = null;
      this.mHeroGraphicView = null;
      if (this.mHighlightsPagerAdapter != null)
      {
        ObjectMap localObjectMap1 = new ObjectMap();
        this.mHighlightsPagerAdapter.onDestroyView(localObjectMap1);
        this.mFragmentObjectMap.put("TabbedBrowseFragment.HighlightsState", localObjectMap1);
      }
      this.mHighlightsPager = null;
      this.mHighlightsPagerAdapter = null;
      this.mHighlightsOverlay = null;
      this.mHeroViewRoot = null;
      this.mTabStrip = null;
      if (this.mControlsContainerBackgroundCoordinator != null)
      {
        this.mControlsContainerBackgroundCoordinator.detach();
        this.mControlsContainerBackgroundCoordinator = null;
      }
      super.onDestroyView();
      return;
      localArrayList = new ArrayList();
      Iterator localIterator = localTabbedAdapter.mTabDataList.iterator();
      while (localIterator.hasNext())
      {
        TabbedAdapter.TabData localTabData = (TabbedAdapter.TabData)localIterator.next();
        if (localTabData.viewPagerTab != null) {
          localArrayList.add(localTabData.viewPagerTab.onDestroyView());
        } else {
          localArrayList.add(localTabData.instanceState);
        }
      }
    }
  }
  
  public final void onEnterActionBarSearchMode()
  {
    if ((this.mTabbedAdapter != null) && (this.mTabbedAdapter.getCount() == 1))
    {
      super.onEnterActionBarSearchMode();
      if (this.mActionBarBackgroundUpdater != null) {
        this.mActionBarBackgroundUpdater.setSearchMode(true);
      }
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    if ((this.mBrowseData != null) && (this.mBrowseData.isReady()))
    {
      this.mIgnoreHighlights = true;
      this.mHighlightsData = null;
      advanceState();
      if (this.mDataIsReady) {
        super.onDataChanged();
      }
      return;
    }
    super.onErrorResponse(paramVolleyError);
  }
  
  public final void onExitActionBarSearchMode()
  {
    if ((this.mTabbedAdapter != null) && (this.mTabbedAdapter.getCount() == 1))
    {
      super.onExitActionBarSearchMode();
      if (this.mActionBarBackgroundUpdater != null) {
        this.mActionBarBackgroundUpdater.setSearchMode(false);
      }
    }
  }
  
  public final void onPageScrollStateChanged(int paramInt)
  {
    TabbedAdapter.TabSelectionTracker.access$400(this.mTabbedAdapter.mTabSelectionTracker, paramInt);
  }
  
  public final void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
  
  public final void onPageSelected(int paramInt)
  {
    int i = BidiPagingHelper.getLogicalPosition(this.mTabbedAdapter, paramInt);
    String str = this.mTabbedAdapter.getPageTitle(i);
    if ((!TextUtils.isEmpty(str)) && (this.mNavigationManager != null) && (this.mNavigationManager.isOnBrowsePage())) {
      UiUtils.sendAccessibilityEventWithText(this.mContext, this.mContext.getString(2131361816, new Object[] { str }), this.mViewPager);
    }
  }
  
  public final void onTabDataReady(final BackgroundViewConfigurator paramBackgroundViewConfigurator)
  {
    int i = 1;
    if (this.mHasHighlightsPager) {
      return;
    }
    if ((this.mTabbedAdapter.getCount() == i) && (paramBackgroundViewConfigurator.hasBackgroundView()))
    {
      this.mHeroGraphicView.setVisibility(0);
      this.mHeaderListLayout.post(new Runnable()
      {
        public final void run()
        {
          paramBackgroundViewConfigurator.configureBackgroundView(TabbedBrowseFragment.this.mHeroGraphicView, TabbedBrowseFragment.this.mBackendId);
        }
      });
      this.mHeaderListLayout.setAlwaysUseFloatingBackground(false);
      this.mHeaderListLayout.setHeaderShadowMode(paramBackgroundViewConfigurator.getHeaderShadowMode());
      Resources localResources = this.mContext.getResources();
      int k = HeroGraphicView.getSpacerHeight(this.mContext, localResources.getDisplayMetrics().widthPixels, i, 0.0F) + (int)(0.3F * localResources.getDimensionPixelSize(2131492936));
      if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
        k -= UiUtils.getStatusBarHeight(this.mContext);
      }
      this.mHeaderListLayout.setTabMode(2, k);
      this.mActionBarBackgroundUpdater = new ActionBarBackgroundUpdater(getActivity().getWindow(), this.mHeaderListLayout);
      this.mHeaderListLayout.setOnLayoutChangedListener(this.mActionBarBackgroundUpdater);
      this.mActionBarBackgroundUpdater.updateActionBar();
      return;
    }
    this.mHeroGraphicView.setVisibility(4);
    FinskyHeaderListLayout localFinskyHeaderListLayout = this.mHeaderListLayout;
    if (this.mBackgroundType != i) {}
    for (;;)
    {
      localFinskyHeaderListLayout.setAlwaysUseFloatingBackground(i);
      this.mHeaderListLayout.setOnLayoutChangedListener(null);
      return;
      int j = 0;
    }
  }
  
  public final void onTabSelected(int paramInt)
  {
    onTabSelectedInternal(paramInt, true);
  }
  
  public void onTabSelectedInternal(int paramInt, boolean paramBoolean)
  {
    int i = BidiPagingHelper.getLogicalPosition(this.mTabbedAdapter, paramInt);
    if (paramBoolean)
    {
      TabbedAdapter localTabbedAdapter = this.mTabbedAdapter;
      if ((i >= 0) && (i < localTabbedAdapter.mTabDataList.size())) {
        FinskyApp.get().getEventLogger().logClickEvent(((TabbedAdapter.TabData)localTabbedAdapter.mTabDataList.get(i)).elementNode);
      }
    }
    ControlsContainerBackgroundCoordinator localControlsContainerBackgroundCoordinator;
    ColorDrawable localColorDrawable;
    HighlightsPagerAdapter localHighlightsPagerAdapter1;
    Document localDocument;
    label219:
    PlayHighlightsOverlayView localPlayHighlightsOverlayView2;
    HighlightsPagerAdapter localHighlightsPagerAdapter2;
    if (hasFixedTabs())
    {
      this.mBackendId = this.mBrowseData.mBrowseResponse.browseTab[i].backendId;
      this.mPageFragmentHost.updateCurrentBackendId(this.mBackendId, true);
      localControlsContainerBackgroundCoordinator = this.mControlsContainerBackgroundCoordinator;
      int j = this.mBackendId;
      localColorDrawable = new ColorDrawable(CorpusResourceUtils.getPrimaryColor(localControlsContainerBackgroundCoordinator.mContext, j));
      if (localControlsContainerBackgroundCoordinator.mBackground != null) {
        break label320;
      }
      localControlsContainerBackgroundCoordinator.mQueuedBackgroundDrawable = localColorDrawable;
      this.mHeaderListLayout.setStatusBarColors(this.mContext.getResources().getColor(2131689730), Color.alpha(0));
      if (this.mHasHighlightsPager)
      {
        this.mHighlightsPager.setCurrentItem(paramInt);
        PlayHighlightsOverlayView localPlayHighlightsOverlayView1 = this.mHighlightsOverlay;
        localHighlightsPagerAdapter1 = this.mHighlightsPagerAdapter;
        if ((i >= 0) && (i < localHighlightsPagerAdapter1.mHighlightsTabList.size())) {
          break label345;
        }
        localDocument = null;
        int k = this.mHighlightsPagerAdapter.getHighlightBannerCountForPage(i);
        if (i != localPlayHighlightsOverlayView1.mCurrentHighlightsLogicalSection)
        {
          localPlayHighlightsOverlayView1.onPagesConfigurationChanged(k);
          localPlayHighlightsOverlayView1.mCurrentHighlightsLogicalSection = i;
          localPlayHighlightsOverlayView1.loadTitles(localDocument);
        }
        localPlayHighlightsOverlayView2 = this.mHighlightsOverlay;
        localHighlightsPagerAdapter2 = this.mHighlightsPagerAdapter;
        if ((i >= 0) && (i < localHighlightsPagerAdapter2.mHighlightsTabList.size())) {
          break label365;
        }
      }
    }
    label320:
    label345:
    label365:
    HighlightsPagerAdapter.HighlightsTab localHighlightsTab;
    for (int m = 0;; m = localHighlightsTab.translatePosition(localHighlightsTab.mCurrentBanner))
    {
      localPlayHighlightsOverlayView2.setCurrentPage(m);
      this.mHighlightsPagerAdapter.setCurrentPage(i);
      this.mHighlightsPager.setBackgroundColor(CorpusResourceUtils.getPrimaryColor(this.mContext, 0));
      return;
      localControlsContainerBackgroundCoordinator.mQueuedBackgroundDrawable = null;
      localControlsContainerBackgroundCoordinator.mBackground.setBackgroundDrawable(localColorDrawable, localControlsContainerBackgroundCoordinator.mLastTouchX, true);
      break;
      localDocument = HighlightsPagerAdapter.HighlightsTab.access$300((HighlightsPagerAdapter.HighlightsTab)localHighlightsPagerAdapter1.mHighlightsTabList.get(i));
      break label219;
      localHighlightsTab = (HighlightsPagerAdapter.HighlightsTab)localHighlightsPagerAdapter2.mHighlightsTabList.get(i);
    }
  }
  
  public final void rebindActionBar()
  {
    this.mPageFragmentHost.updateActionBarTitle(this.mBreadcrumb);
    this.mPageFragmentHost.updateCurrentBackendId(this.mBackendId, this.mDataIsReady);
    if (isSearchBoxOnly())
    {
      localResources = this.mContext.getResources();
      if ((FinskyApp.get().getExperiments().isH20StoreEnabled()) && (localResources.getBoolean(2131427336)))
      {
        i = IntMath.heightToWidth$4868d301(localResources.getDimensionPixelSize(2131492939) - localResources.getDimensionPixelSize(2131493027));
        j = 2 * localResources.getDimensionPixelOffset(2131492893);
        this.mPageFragmentHost.overrideSearchBoxWidth(i - j);
      }
      this.mPageFragmentHost.switchToSearchBoxOnlyActionBar(1);
    }
    while (!this.mDataIsReady)
    {
      Resources localResources;
      int i;
      int j;
      return;
    }
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  public final void rebindViews()
  {
    ClientMutationCache localClientMutationCache;
    boolean bool2;
    if ((this.mBrowseData != null) && (this.mBrowseData.isFamilySafeSearchModeDefined()))
    {
      localClientMutationCache = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName());
      DfeBrowse localDfeBrowse = this.mBrowseData;
      if ((!localDfeBrowse.isFamilySafeSearchModeDefined()) || (!localDfeBrowse.mBrowseResponse.isFamilySafe)) {
        break label1172;
      }
      bool2 = true;
    }
    for (;;)
    {
      localClientMutationCache.mFamilySafeSearchMode = bool2;
      final Resources localResources1 = getResources();
      this.mHeaderListLayout = ((FinskyHeaderListLayout)this.mDataView);
      this.mHeaderListLayout.configure(new PlayHeaderListConfigurator(this.mHeaderListLayout.getContext(), this.mBackgroundType));
      this.mHeaderListLayout.setContentViewId(2131755589);
      this.mHeaderListLayout.setBackgroundViewForTouchPassthrough(this.mBackgroundViewGroup);
      if (this.mBackgroundType != 1)
      {
        this.mHeaderListLayout.setAlwaysUseFloatingBackground(true);
        this.mHeaderListLayout.setFloatingControlsBackground(getBackgroundColorDrawable());
      }
      this.mBackendId = this.mBrowseData.mBrowseResponse.backendId;
      String str = this.mBrowseData.mBrowseResponse.title;
      Toc.CorpusMetadata localCorpusMetadata;
      label207:
      LayoutInflater localLayoutInflater1;
      final int i;
      label392:
      boolean bool1;
      label563:
      LayoutInflater localLayoutInflater2;
      final int i2;
      FinskyViewPager localFinskyViewPager;
      AccelerateDecelerateInterpolator localAccelerateDecelerateInterpolator;
      if (str == null)
      {
        localCorpusMetadata = this.mDfeToc.getCorpus(this.mBackendId);
        if (localCorpusMetadata == null) {
          str = "";
        }
      }
      else
      {
        this.mBreadcrumb = str;
        rebindActionBar();
        if ((!TextUtils.isEmpty(this.mBreadcrumb)) && (this.mNavigationManager != null) && (this.mNavigationManager.isOnBrowsePage())) {
          UiUtils.sendAccessibilityEventWithText(this.mContext, this.mBreadcrumb, this.mView);
        }
        localLayoutInflater1 = getActivity().getLayoutInflater();
        this.mTabbedAdapter = new TabbedAdapter(this.mContext, localLayoutInflater1, this.mNavigationManager, this.mDfeToc, this.mDfeApi, FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName()), this.mUserContext, this.mBitmapLoader, this.mBrowseData.mBrowseResponse.browseTab, this.mBrowseData.mBrowseResponse.quickLink, this.mBrowseData.mBrowseResponse.quickLinkTabIndex, this.mBrowseData.mBrowseResponse.quickLinkFallbackTabIndex, this.mBackendId, (ObjectMap)this.mFragmentObjectMap.get("TabbedBrowseFragment.AdapterState"), this, this.mActionBarController, this, this);
        if (this.mTabbedAdapter.getCount() <= 1) {
          break label1211;
        }
        i = 0;
        this.mTabStrip.setAnimateOnTabClick(hasFixedTabs());
        if ((hasFixedTabs()) || (this.mHasHighlightsPager))
        {
          FinskyTabStrip localFinskyTabStrip = this.mTabStrip;
          localFinskyTabStrip.mEnableFixedTabs = true;
          Resources localResources2 = localFinskyTabStrip.getResources();
          localFinskyTabStrip.setSelectedUnderlineThickness(localResources2.getDimensionPixelSize(2131493304));
          localFinskyTabStrip.mUseFixedTabWideLayout = localResources2.getBoolean(2131427334);
          if (localFinskyTabStrip.mUseFixedTabWideLayout)
          {
            View localView = localFinskyTabStrip.findViewById(2131755916);
            FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)localView.getLayoutParams();
            localLayoutParams.gravity = 17;
            localView.setLayoutParams(localLayoutParams);
          }
          localFinskyTabStrip.requestLayout();
          this.mTabStrip.notifyPagerAdapterChanged();
          this.mHeaderListLayout.setAlwaysUseFloatingBackground(false);
          ControlsContainerBackgroundCoordinator localControlsContainerBackgroundCoordinator = this.mControlsContainerBackgroundCoordinator;
          localControlsContainerBackgroundCoordinator.mHeaderListLayout = this.mHeaderListLayout;
          localControlsContainerBackgroundCoordinator.updateBackgroundHeightAndFades();
        }
        FinskyHeaderListLayout localFinskyHeaderListLayout1 = this.mHeaderListLayout;
        if (this.mBackendId == 9) {
          break label1217;
        }
        bool1 = true;
        localFinskyHeaderListLayout1.setShouldUseScrollLocking(bool1);
        this.mViewPager = ((FinskyViewPager)this.mDataView.findViewById(2131755589));
        this.mViewPager.setAdapter(this.mTabbedAdapter);
        this.mViewPager.setPageMargin(localResources1.getDimensionPixelSize(2131493528));
        this.mHeaderListLayout.mTabStrip.notifyPagerAdapterChanged();
        this.mHeaderListLayout.setOnPageChangeListener(this);
        this.mHeaderListLayout.setOnTabSelectedListener(this);
        if (!hasFixedTabs()) {
          this.mHeaderListLayout.setStatusBarColors(localResources1.getColor(2131689730), CorpusResourceUtils.getPrimaryColor(this.mContext, this.mBackendId));
        }
        localLayoutInflater2 = LayoutInflater.from(getContext());
        if ((this.mHasHighlightsPager) || (hasFixedTabs()))
        {
          this.mViewPager.mAreTouchEventsDisabled = true;
          this.mHeaderListLayout.setHeaderMode(0);
        }
        if (!this.mHasHighlightsPager) {
          break label1261;
        }
        this.mHighlightsPagerAdapter = new HighlightsPagerAdapter(this.mDfeApi, this.mHighlightsData, this.mContext, localLayoutInflater1, this.mBitmapLoader, this.mNavigationManager, this.mTabbedAdapter, (ObjectMap)this.mFragmentObjectMap.get("TabbedBrowseFragment.HighlightsState"));
        this.mHighlightsPager = ((FinskyViewPager)localLayoutInflater2.inflate(2130968787, this.mBackgroundViewGroup, false));
        if (i != 2) {
          break label1223;
        }
        i2 = 0;
        this.mHighlightsPager.setMeasureOverrider(new FinskyViewPager.MeasureOverrider()
        {
          public final Pair<Integer, Integer> overrideMeasure$2816499f(int paramAnonymousInt)
          {
            int i = localResources1.getDimensionPixelSize(2131493027);
            if (localResources1.getBoolean(2131427336)) {}
            for (int j = localResources1.getDimensionPixelSize(2131492939);; j = i + 9 * View.MeasureSpec.getSize(paramAnonymousInt) / 16)
            {
              TabbedBrowseFragment.access$002(TabbedBrowseFragment.this, j + i2);
              if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
                TabbedBrowseFragment.access$020(TabbedBrowseFragment.this, UiUtils.getStatusBarHeight(TabbedBrowseFragment.this.mContext));
              }
              TabbedBrowseFragment.this.mHeaderListLayout.setTabMode(i, TabbedBrowseFragment.this.mLeadingSpacerHeight);
              if (TabbedBrowseFragment.this.mHighlightsOverlay != null) {
                TabbedBrowseFragment.this.mHighlightsOverlay.setHighlightWidth(IntMath.heightToWidth$4868d301(j - i));
              }
              return Pair.create(Integer.valueOf(paramAnonymousInt), Integer.valueOf(View.MeasureSpec.makeMeasureSpec(j, 1073741824)));
            }
          }
        });
        this.mHighlightsPager.setAdapter(this.mHighlightsPagerAdapter);
        this.mHighlightsPager.setPageTransformer$382b7817(new CrossfadeTransformer(this.mHighlightsPagerAdapter));
        this.mHighlightsPager.mAreTouchEventsDisabled = true;
        localFinskyViewPager = this.mHighlightsPager;
        localAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
      }
      try
      {
        Field localField = ViewPager.class.getDeclaredField("mScroller");
        localField.setAccessible(true);
        localField.set(localFinskyViewPager, new FinskyViewPagerScroller(localFinskyViewPager.getContext(), localAccelerateDecelerateInterpolator, 500));
        FinskyHeaderListLayout localFinskyHeaderListLayout3 = this.mHeaderListLayout;
        if (this.mHighlightsPagerAdapter.getCount() > 1)
        {
          i3 = 3;
          localFinskyHeaderListLayout3.setHeaderShadowMode(i3);
          this.mHeaderListLayout.setAlwaysUseFloatingBackground(false);
          this.mHighlightsPager.setClickable(true);
          this.mHeaderListLayout.setBackgroundViewForTouchPassthrough(this.mHighlightsPager);
          this.mBackgroundViewGroup.addView(this.mHighlightsPager);
          if ((this.mHighlightsOverlay == null) && (this.mHeroViewRoot != null))
          {
            this.mHighlightsOverlay = ((PlayHighlightsOverlayView)localLayoutInflater1.inflate(2130968991, this.mHeroViewRoot, false));
            this.mHeroViewRoot.getLayoutParams().width = -1;
            this.mHeroViewRoot.getLayoutParams().height = -1;
            this.mHeroViewRoot.addView(this.mHighlightsOverlay);
            this.mHighlightsOverlay.setPadding(0, 0, 0, i2);
            this.mHighlightsOverlay.onPagesConfigurationChanged(this.mHighlightsPagerAdapter.getHighlightBannerCountForPage(0));
            this.mHighlightsPagerAdapter.setHighlightsPageListener(this.mHighlightsOverlay);
          }
          if (this.mBackgroundType != 1) {
            this.mHeaderListLayout.setFloatingControlsBackground(getBackgroundColorDrawable());
          }
          if (this.mRestorePrevSelectedTabLogicalIndex == -1) {
            break label1353;
          }
          m = this.mRestorePrevSelectedTabLogicalIndex;
          this.mRestorePrevSelectedTabLogicalIndex = -1;
          int n = BidiPagingHelper.getLogicalPosition(this.mTabbedAdapter, this.mViewPager.getCurrentItem());
          i1 = BidiPagingHelper.getVisualPosition(this.mTabbedAdapter, m);
          if (n != m) {
            break label1368;
          }
          onTabSelectedInternal(i1, false);
          this.mLayoutSwitcher.switchToDataMode();
          this.mAttachToFrameRunnable.run();
          return;
          label1172:
          bool2 = false;
          continue;
          if (!this.mNavigationManager.canGoUp())
          {
            str = this.mContext.getString(2131362289);
            break label207;
          }
          str = localCorpusMetadata.name;
          break label207;
          label1211:
          i = 2;
          break label392;
          label1217:
          bool1 = false;
          break label563;
          label1223:
          i2 = localResources1.getDimensionPixelSize(2131493424);
        }
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        for (;;)
        {
          int i1;
          FinskyLog.wtf("Error setting animation parameters", new Object[] { localNoSuchFieldException });
          continue;
          int i3 = 2;
          continue;
          this.mHeroGraphicView = ((HeroGraphicView)localLayoutInflater2.inflate(2130968785, this.mBackgroundViewGroup, false));
          this.mHeroGraphicView.setVisibility(4);
          this.mBackgroundViewGroup.addView(this.mHeroGraphicView);
          Context localContext = this.mContext;
          int j = this.mHeaderListLayout.getActionBarHeight();
          this.mLeadingSpacerHeight = FinskyHeaderListLayout.getMinimumHeaderHeight(localContext, i, 0, j);
          FinskyHeaderListLayout localFinskyHeaderListLayout2 = this.mHeaderListLayout;
          int k = this.mLeadingSpacerHeight;
          localFinskyHeaderListLayout2.setTabMode(i, k);
          continue;
          int m = this.mBrowseData.mBrowseResponse.landingTabIndex;
          continue;
          this.mViewPager.setCurrentItem(i1, false);
          onTabSelectedInternal(i1, false);
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        label1353:
        label1368:
        break label1237;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        label1237:
        label1261:
        break label1237;
      }
    }
  }
  
  protected final void requestData()
  {
    advanceState();
  }
  
  protected final boolean shouldDelayAttachingDataView()
  {
    return true;
  }
  
  public static abstract interface BackgroundViewConfigurator
  {
    public abstract void configureBackgroundView(HeroGraphicView paramHeroGraphicView, int paramInt);
    
    public abstract int getHeaderShadowMode();
    
    public abstract boolean hasBackgroundView();
  }
  
  private final class PlayHeaderListConfigurator
    extends FinskyHeaderListLayout.FinskyConfigurator
  {
    private final int mBackgroundMode;
    
    public PlayHeaderListConfigurator(Context paramContext, int paramInt)
    {
      super();
      this.mBackgroundMode = paramInt;
    }
    
    protected final void addBackgroundView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      TabbedBrowseFragment.access$602(TabbedBrowseFragment.this, paramViewGroup);
    }
    
    protected final void addContentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      paramLayoutInflater.inflate(2130968782, paramViewGroup);
    }
    
    protected final void addHeroView$39fc0c(ViewGroup paramViewGroup)
    {
      TabbedBrowseFragment.access$902(TabbedBrowseFragment.this, paramViewGroup);
    }
    
    protected final boolean allowImmersiveBackground()
    {
      return true;
    }
    
    protected final boolean alwaysUseFloatingBackground()
    {
      return false;
    }
    
    protected final float getBackgroundViewParallaxRatio()
    {
      return 0.8F;
    }
    
    protected final int getContentProtectionMode()
    {
      if (TabbedBrowseFragment.this.mHasHighlightsPager) {
        return 1;
      }
      return super.getContentProtectionMode();
    }
    
    protected final int getHeaderMode()
    {
      return 1;
    }
    
    protected final int getHeaderShadowMode()
    {
      if (this.mBackgroundMode != 1) {
        return 3;
      }
      return 2;
    }
    
    protected final int getHeroAnimationMode()
    {
      if (FinskyApp.get().getExperiments().isEnabled(12603505L)) {
        return 3;
      }
      return super.getHeroAnimationMode();
    }
    
    public final int getLeadingSpacerHeight()
    {
      return TabbedBrowseFragment.this.mLeadingSpacerHeight;
    }
    
    protected final int getListViewId()
    {
      return 2131755329;
    }
    
    protected final int getStatusBarOverlayColor()
    {
      return this.mContext.getResources().getColor(2131689730);
    }
    
    protected final int getStatusBarUnderlayColor()
    {
      return CorpusResourceUtils.getPrimaryColor(this.mContext, TabbedBrowseFragment.this.mBackendId);
    }
    
    protected final int getTabPaddingMode()
    {
      return 1;
    }
    
    protected final int getToolBarHeight(Context paramContext)
    {
      if ((TabbedBrowseFragment.this.hasFixedTabs()) || (TabbedBrowseFragment.this.mHasHighlightsPager)) {
        return PlaySearchToolbar.getToolbarHeight(this.mContext) + TabbedBrowseFragment.this.getResources().getDimensionPixelSize(2131493013);
      }
      return super.getToolBarHeight(paramContext);
    }
    
    protected final int getViewPagerId()
    {
      return 2131755589;
    }
    
    protected final boolean hasViewPager()
    {
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.TabbedBrowseFragment
 * JD-Core Version:    0.7.0.1
 */