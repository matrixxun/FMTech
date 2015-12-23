package com.google.android.finsky.detailspage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.transition.Fade;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.DetailsDataBasedFragment;
import com.google.android.finsky.activities.ReviewFeedbackListener;
import com.google.android.finsky.activities.ReviewFeedbackListener.ReviewFeedbackRating;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.layout.ClusterContentConfiguratorRepository;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.actionbar.ActionBarBackgroundUpdater;
import com.google.android.finsky.layout.actionbar.ActionBarController;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout.FinskyConfigurator;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.hats.HatsUtils;
import com.google.android.play.headerlist.PlayHeaderListLayout;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.List;

public class DetailsFragment2
  extends DetailsDataBasedFragment
  implements ReviewFeedbackListener, SimpleAlertDialog.Listener, FinskyModule.FinskyModuleController, InstallerListener
{
  private ActionBarBackgroundUpdater mActionBarBackgroundUpdater;
  protected List<ModulesAdapter.Module> mAddedModules = new ArrayList();
  protected List<FinskyModule> mAllModules;
  protected HeroGraphicView mBackgroundView;
  private ClusterContentConfiguratorRepository mClusterConfiguratorRepository;
  private boolean mFetchSocialDetailsDocument;
  boolean mHasGeneratedDynamicModules;
  private boolean mHasSavedScrollState;
  protected FinskyHeaderListLayout mHeaderListLayout;
  protected HeroGraphicView mHeroGraphicView;
  private int mHeroSpacerHeight;
  private boolean mIsFirstTimeHeroBinding = true;
  protected boolean mIsInImageTransitionPhase;
  protected ModulesAdapter mModulesAdapter;
  private Boolean mPrevUseCombinedTitle;
  private RecyclerView.RecycledViewPool mRecycledViewPool;
  private RecyclerView mRecyclerView;
  protected String mRevealTransitionSharedName;
  private List<Class> mSavedModuleClasses;
  private List<FinskyModule.ModuleData> mSavedModuleData;
  private int mSavedScrollModuleHeight;
  private int mSavedScrollModuleIndex;
  private int mSavedScrollModuleOffset;
  private boolean mShowsHeroView;
  private SocialDetailsErrorListener mSocialDetailsErrorListener;
  private DfeApi mSocialDfeApi;
  private DfeDetails mSocialDfeDetails;
  private Boolean mUseCombinedTitle;
  
  public static DetailsFragment2 newInstance$653305c3(Document paramDocument, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    DetailsFragment2 localDetailsFragment2 = new DetailsFragment2();
    localDetailsFragment2.setDfeAccount(paramString3);
    localDetailsFragment2.setDfeTocAndUrl(localFinskyApp.mToc, paramString1);
    localDetailsFragment2.setArgument("finsky.DetailsDataBasedFragment.document", paramDocument);
    localDetailsFragment2.setArgument("finsky.DetailsFragment.continueUrl", paramString2);
    localDetailsFragment2.setArgument("finsky.DetailsFragment.acquisitionOverride", paramBoolean);
    return localDetailsFragment2;
  }
  
  protected final void addModule(FinskyModule paramFinskyModule)
  {
    if (this.mAddedModules.contains(paramFinskyModule))
    {
      FinskyLog.wtf("Trying to add a module that is already added", new Object[0]);
      return;
    }
    if (!isModuleReadyForDisplay(paramFinskyModule))
    {
      FinskyLog.wtf("Trying to add a module that is not ready for display", new Object[0]);
      return;
    }
    int i = 0;
    for (int j = 0; (j < this.mAllModules.size()) && (i != this.mAddedModules.size()) && (this.mAllModules.get(j) != paramFinskyModule); j++) {
      if (this.mAllModules.get(j) == this.mAddedModules.get(i)) {
        i++;
      }
    }
    this.mAddedModules.add(i, paramFinskyModule);
    ModulesAdapter localModulesAdapter = this.mModulesAdapter;
    localModulesAdapter.mModules.add(i, paramFinskyModule);
    localModulesAdapter.notifyItemInserted(i);
  }
  
  public final void broadcastData(String paramString, Object paramObject)
  {
    for (int i = 0; i < this.mAllModules.size(); i++) {
      ((FinskyModule)this.mAllModules.get(i)).onReceiveBroadcastData(paramString, paramObject);
    }
  }
  
  public final int getActionBarColor()
  {
    return CorpusResourceUtils.getPrimaryColor(this.mContext, this.mDocument.mDocument.backendId);
  }
  
  @TargetApi(22)
  protected final Transition getCustomExitTransition()
  {
    return new Fade();
  }
  
  public final int getLayoutRes()
  {
    return 2130968781;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 2;
  }
  
  protected boolean isModuleReadyForDisplay(FinskyModule paramFinskyModule)
  {
    return paramFinskyModule.readyForDisplay();
  }
  
  protected boolean isRunningSharedCoverTransition()
  {
    return false;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    if (this.mSocialDfeDetails != null)
    {
      this.mSocialDfeDetails.addDataChangedListener(this);
      this.mSocialDetailsErrorListener = new SocialDetailsErrorListener((byte)0);
      this.mSocialDfeDetails.addErrorListener(this.mSocialDetailsErrorListener);
    }
    super.onActivityCreated(paramBundle);
    if (this.mNavigationManager.canPromptSearchSurveyForCurrent()) {
      HatsUtils.showSurveyIfAvailable(2, this.mPageFragmentHost);
    }
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    for (int i = 0; i < this.mAllModules.size(); i++) {
      ((FinskyModule)this.mAllModules.get(i)).onActivityResult(paramInt1, paramInt2, paramIntent);
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance$1385ff();
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    Context localContext = paramViewGroup.getContext();
    this.mUseCombinedTitle = Boolean.valueOf(localContext.getResources().getBoolean(2131427338));
    Document localDocument = this.mDocument;
    int i = localDocument.mDocument.backendId;
    this.mShowsHeroView = HeroGraphicView.shouldShowDetailsHeroGraphic(localDocument, this.mUseWideLayout);
    this.mHeroSpacerHeight = HeroGraphicView.getDetailsHeroSpacerHeight(localContext, localDocument, this.mUseWideLayout);
    this.mHeaderListLayout = ((FinskyHeaderListLayout)this.mDataView);
    this.mHeaderListLayout.configure(new PlayHeaderListConfigurator(localContext, this.mShowsHeroView, this.mHeroSpacerHeight, i));
    this.mHeaderListLayout.setContentViewId(2131755590);
    this.mHeaderListLayout.setFloatingControlsBackground(new ColorDrawable(CorpusResourceUtils.getPrimaryColor(getActivity(), i)));
    ActionBarController localActionBarController = ((PageFragmentHost)getActivity()).getActionBarController();
    if (this.mShowsHeroView) {
      localActionBarController.enableStatusBarOverlay();
    }
    for (;;)
    {
      this.mActionBarBackgroundUpdater = new ActionBarBackgroundUpdater(getActivity().getWindow(), this.mHeaderListLayout);
      this.mHeaderListLayout.setOnLayoutChangedListener(this.mActionBarBackgroundUpdater);
      this.mActionBarBackgroundUpdater.updateActionBar();
      this.mHeroGraphicView = ((HeroGraphicView)this.mHeaderListLayout.findViewById(2131755591));
      this.mRecyclerView = ((RecyclerView)this.mHeaderListLayout.findViewById(2131755590));
      this.mRecyclerView.setSaveEnabled(false);
      if (!FinskyApp.get().getExperiments().isEnabled(12603629L)) {
        this.mRecyclerView.setScrollContainer(false);
      }
      this.mHeaderListLayout.setBackgroundViewForTouchPassthrough(this.mHeroGraphicView);
      FinskyApp.get().mInstaller.addListener(this);
      return localView;
      localActionBarController.disableStatusBarOverlay();
    }
  }
  
  public void onDestroyView()
  {
    int i = 0;
    int j = ((LinearLayoutManager)this.mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
    View localView = this.mRecyclerView.getChildAt(0);
    if (localView != null)
    {
      this.mHasSavedScrollState = true;
      this.mSavedScrollModuleIndex = this.mAllModules.indexOf(this.mAddedModules.get(j));
      this.mSavedScrollModuleOffset = localView.getTop();
      this.mSavedScrollModuleHeight = localView.getHeight();
    }
    this.mPrevUseCombinedTitle = this.mUseCombinedTitle;
    ModulesAdapter localModulesAdapter = this.mModulesAdapter;
    while (i < localModulesAdapter.mModules.size())
    {
      ModulesAdapter.Module localModule = (ModulesAdapter.Module)localModulesAdapter.mModules.get(i);
      if (localModule.mModuleViewHolder != null)
      {
        localModule.onRecycleView(localModule.mModuleViewHolder.itemView);
        localModule.mModuleViewHolder.module = null;
        localModule.mModuleViewHolder = null;
      }
      i++;
    }
    this.mSavedModuleClasses = new ArrayList();
    this.mSavedModuleData = new ArrayList();
    for (int k = 0; k < this.mAllModules.size(); k++)
    {
      FinskyModule localFinskyModule = (FinskyModule)this.mAllModules.get(k);
      this.mSavedModuleClasses.add(localFinskyModule.getClass());
      this.mSavedModuleData.add(localFinskyModule.onSaveModuleData());
      localFinskyModule.onDestroyModule();
    }
    if (this.mSocialDfeDetails != null)
    {
      this.mSocialDfeDetails.removeDataChangedListener(this);
      this.mSocialDfeDetails.removeErrorListener(this.mSocialDetailsErrorListener);
    }
    if (this.mHeaderListLayout != null) {
      this.mHeaderListLayout.detachIfNeeded();
    }
    if (this.mActionBarBackgroundUpdater != null) {
      this.mActionBarBackgroundUpdater.reset();
    }
    FinskyApp.get().mInstaller.removeListener(this);
    this.mHeaderListLayout = null;
    this.mActionBarBackgroundUpdater = null;
    this.mHeroGraphicView = null;
    this.mBackgroundView = null;
    this.mRecyclerView = null;
    this.mRecycledViewPool = null;
    this.mClusterConfiguratorRepository = null;
    this.mModulesAdapter = null;
    this.mAllModules.clear();
    this.mAddedModules.clear();
    super.onDestroyView();
  }
  
  public final void onEnterActionBarSearchMode()
  {
    this.mActionBarBackgroundUpdater.setSearchMode(true);
  }
  
  public final void onExitActionBarSearchMode()
  {
    this.mActionBarBackgroundUpdater.setSearchMode(false);
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    Document localDocument = this.mDocument;
    if ((localDocument != null) && (localDocument.mDocument.docType == 1) && (paramString.equals(localDocument.getAppDetails().packageName)))
    {
      if ((paramInt1 == 3) && (paramInt2 == 944)) {
        this.mNavigationManager.goToDocPage$6d245699(localDocument, this.mUrl, this.mArguments.getString("finsky.DetailsFragment.continueUrl"), this.mDfeApi.getAccountName());
      }
    }
    else {
      return;
    }
    rebindActionBar();
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    for (int i = 0; i < this.mAllModules.size(); i++) {
      ((FinskyModule)this.mAllModules.get(i)).onNegativeClick(paramInt, paramBundle);
    }
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    for (int i = 0; i < this.mAllModules.size(); i++) {
      ((FinskyModule)this.mAllModules.get(i)).onPositiveClick(paramInt, paramBundle);
    }
  }
  
  public final void onReviewFeedback(String paramString1, String paramString2, ReviewFeedbackListener.ReviewFeedbackRating paramReviewFeedbackRating)
  {
    for (int i = 0; i < this.mAllModules.size(); i++) {
      ((FinskyModule)this.mAllModules.get(i)).onReviewFeedback(paramString1, paramString2, paramReviewFeedbackRating);
    }
  }
  
  public final void rebindActionBar()
  {
    this.mActionBarController.enableActionBarOverlay();
    this.mPageFragmentHost.updateActionBarTitle(this.mDocument.mDocument.title);
    this.mPageFragmentHost.updateCurrentBackendId(this.mDocument.mDocument.backendId, true);
    this.mPageFragmentHost.switchToRegularActionBar();
  }
  
  protected final void rebindViews$79e5e33f()
  {
    Document localDocument1 = this.mDocument;
    if (localDocument1 == null) {}
    label43:
    label70:
    label229:
    label245:
    label506:
    label637:
    do
    {
      return;
      DfeDetails localDfeDetails1 = this.mDetailsData;
      DfeDetails localDfeDetails2;
      Document localDocument2;
      boolean bool;
      LinearLayoutManager localLinearLayoutManager;
      DfeApi localDfeApi;
      int i5;
      String str2;
      int i6;
      FinskyModule localFinskyModule2;
      if (this.mFetchSocialDetailsDocument)
      {
        localDfeDetails2 = this.mSocialDfeDetails;
        if (!this.mFetchSocialDetailsDocument) {
          break label485;
        }
        localDocument2 = this.mSocialDfeDetails.getDocument();
        if (!this.mFetchSocialDetailsDocument) {
          break label497;
        }
        if ((!hasDetailsDataLoaded()) || (!this.mSocialDfeDetails.isReady())) {
          break label491;
        }
        bool = true;
        if (this.mModulesAdapter != null) {
          break label637;
        }
        if (this.mModulesAdapter != null) {
          FinskyLog.wtf("Modules system is already set up", new Object[0]);
        }
        this.mRecyclerView.getContext();
        localLinearLayoutManager = new LinearLayoutManager();
        this.mRecyclerView.setLayoutManager(localLinearLayoutManager);
        this.mRecyclerView.addItemDecoration(new ModuleDividerItemDecoration(this.mContext));
        this.mRecyclerView.addItemDecoration(new ModuleMarginItemDecoration(this.mContext, this.mUseWideLayout));
        this.mRecyclerView.addItemDecoration(new DetailsClusterDecoration(this.mContext));
        if (this.mPrevUseCombinedTitle != null) {
          ModulesManager.remapModulesAfterRotation(this.mSavedModuleClasses, this.mSavedModuleData, localDocument1, this.mPrevUseCombinedTitle.booleanValue(), this.mUseCombinedTitle.booleanValue());
        }
        if (this.mSavedModuleClasses == null) {
          break label506;
        }
        this.mAllModules = ModulesManager.generateModules(this.mSavedModuleClasses);
        if (!this.mFetchSocialDetailsDocument) {
          break label524;
        }
        localDfeApi = FinskyApp.get().getDfeApi(null);
        FragmentActivity localFragmentActivity = getActivity();
        FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
        if ((!localFinskyExperiments.isH20StoreEnabled()) || (localFinskyExperiments.isEnabled(12604101L))) {
          break label533;
        }
        i5 = 1;
        if (i5 != 0)
        {
          this.mRecycledViewPool = new RecyclerView.RecycledViewPool();
          this.mClusterConfiguratorRepository = new ClusterContentConfiguratorRepository(localFragmentActivity);
        }
        if (!isRunningSharedCoverTransition()) {
          break label539;
        }
        str2 = this.mRevealTransitionSharedName;
        i6 = 0;
        int i7 = this.mAllModules.size();
        if (i6 >= i7) {
          break label551;
        }
        localFinskyModule2 = (FinskyModule)this.mAllModules.get(i6);
        localFinskyModule2.init(localFragmentActivity, this, this.mDfeToc, this.mDfeApi, localDfeApi, this.mBitmapLoader, this.mNavigationManager, this, this.mUrl, this.mArguments.getString("finsky.DetailsFragment.continueUrl"), this.mLibraries, this.mUseWideLayout, str2, this.mIsInImageTransitionPhase, this.mRecycledViewPool, this.mClusterConfiguratorRepository, this);
        if (this.mSavedModuleData == null) {
          break label545;
        }
      }
      for (FinskyModule.ModuleData localModuleData = (FinskyModule.ModuleData)this.mSavedModuleData.get(i6);; localModuleData = null)
      {
        localFinskyModule2.onRestoreModuleData(localModuleData);
        if (isModuleReadyForDisplay(localFinskyModule2)) {
          this.mAddedModules.add(localFinskyModule2);
        }
        i6++;
        break label326;
        localDfeDetails2 = localDfeDetails1;
        break;
        localDocument2 = localDocument1;
        break label43;
        bool = false;
        break label70;
        bool = hasDetailsDataLoaded();
        break label70;
        this.mAllModules = ModulesManager.generateStaticModules(localDocument1, this.mUseCombinedTitle.booleanValue());
        break label229;
        localDfeApi = this.mDfeApi;
        break label245;
        i5 = 0;
        break label281;
        str2 = null;
        break label323;
      }
      this.mModulesAdapter = new ModulesAdapter(this.mAddedModules);
      this.mRecyclerView.setAdapter(this.mModulesAdapter);
      if (i5 != 0) {
        this.mRecyclerView.setScrollingTouchSlop(1);
      }
      List localList;
      if (this.mHasSavedScrollState)
      {
        if (this.mSavedScrollModuleIndex == 0) {
          localLinearLayoutManager.scrollToPositionWithOffset(0, Math.min(this.mSavedScrollModuleOffset + this.mSavedScrollModuleHeight, this.mHeaderListLayout.getHeaderHeight()) - this.mHeaderListLayout.getHeaderHeight());
        }
      }
      else
      {
        if ((!bool) || (this.mHasGeneratedDynamicModules)) {
          break label848;
        }
        this.mHasGeneratedDynamicModules = true;
        localList = ModulesManager.generateDynamicModules(localDocument1, this.mUseCombinedTitle.booleanValue(), this.mAllModules);
        if (!isRunningSharedCoverTransition()) {
          break label842;
        }
      }
      for (String str1 = this.mRevealTransitionSharedName;; str1 = null)
      {
        for (int i3 = 0;; i3++)
        {
          int i4 = localList.size();
          if (i3 >= i4) {
            break;
          }
          ((FinskyModule)localList.get(i3)).init(getActivity(), this, this.mDfeToc, this.mDfeApi, this.mSocialDfeApi, this.mBitmapLoader, this.mNavigationManager, this, this.mUrl, this.mArguments.getString("finsky.DetailsFragment.continueUrl"), this.mLibraries, this.mUseWideLayout, str1, this.mIsInImageTransitionPhase, this.mRecycledViewPool, this.mClusterConfiguratorRepository, this);
        }
        int i8 = ModulesManager.remapModuleIndexAfterRotation(this.mSavedScrollModuleIndex, localDocument1, this.mPrevUseCombinedTitle.booleanValue(), this.mUseCombinedTitle.booleanValue());
        localLinearLayoutManager.scrollToPositionWithOffset(this.mAddedModules.indexOf(this.mAllModules.get(i8)), this.mSavedScrollModuleOffset);
        break;
      }
      for (int i = 0;; i++)
      {
        int j = this.mAllModules.size();
        if (i >= j) {
          break;
        }
        FinskyModule localFinskyModule1 = (FinskyModule)this.mAllModules.get(i);
        localFinskyModule1.bindModule(bool, localDocument1, localDfeDetails1, localDocument2, localDfeDetails2);
        if ((isModuleReadyForDisplay(localFinskyModule1)) && (!this.mAddedModules.contains(localFinskyModule1))) {
          addModule(localFinskyModule1);
        }
      }
    } while (this.mHeroGraphicView == null);
    label281:
    label323:
    label326:
    label485:
    label491:
    label497:
    this.mShowsHeroView = HeroGraphicView.shouldShowDetailsHeroGraphic(localDocument1, this.mUseWideLayout);
    label524:
    label533:
    label539:
    label545:
    label551:
    label842:
    label848:
    int k = this.mHeroSpacerHeight;
    this.mHeroSpacerHeight = HeroGraphicView.getDetailsHeroSpacerHeight(this.mContext, localDocument1, this.mUseWideLayout);
    if ((this.mHeroSpacerHeight != k) && (this.mHeaderListLayout != null)) {
      this.mHeaderListLayout.setTabMode(2, this.mHeroSpacerHeight);
    }
    HeroGraphicView localHeroGraphicView = this.mHeroGraphicView;
    BitmapLoader localBitmapLoader = this.mBitmapLoader;
    localHeroGraphicView.bindHero$2f5f9d70(HeroGraphicView.getDetailsHeroGraphic(localDocument1, this.mUseWideLayout), localDocument1, localBitmapLoader, HeroGraphicView.shouldPreferVideosAppForPlayback(localDocument1), this);
    int m = localDocument1.mDocument.docType;
    int n;
    if ((m == 2) || (m == 25) || (m == 24))
    {
      n = 1;
      if ((!this.mIsFirstTimeHeroBinding) || (this.mUseWideLayout) || (n == 0)) {
        break label1147;
      }
    }
    label1147:
    for (int i1 = 1;; i1 = 0)
    {
      if (i1 != 0)
      {
        int i2 = this.mHeaderListLayout.getHeaderHeight() - getResources().getDimensionPixelSize(2131493025);
        if (i2 > 0) {
          ((LinearLayoutManager)this.mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(0, -i2);
        }
      }
      this.mIsFirstTimeHeroBinding = false;
      return;
      n = 0;
      break;
    }
  }
  
  public final void refreshModule(FinskyModule paramFinskyModule, boolean paramBoolean)
  {
    if (this.mModulesAdapter == null) {}
    do
    {
      return;
      if (!this.mAllModules.contains(paramFinskyModule))
      {
        FinskyLog.wtf("FinskyModule does not belong to this page", new Object[0]);
        return;
      }
      if (!paramFinskyModule.readyForDisplay())
      {
        FinskyLog.wtf("FinskyModule that is not ready for display asked for refresh", new Object[0]);
        return;
      }
    } while (!isModuleReadyForDisplay(paramFinskyModule));
    if (!this.mAddedModules.contains(paramFinskyModule))
    {
      addModule(paramFinskyModule);
      return;
    }
    int i = this.mAddedModules.indexOf(paramFinskyModule);
    if (paramBoolean)
    {
      this.mModulesAdapter.notifyItemChanged(i);
      return;
    }
    ModulesAdapter localModulesAdapter = this.mModulesAdapter;
    ModulesAdapter.Module localModule = (ModulesAdapter.Module)localModulesAdapter.mModules.get(i);
    localModulesAdapter.mHandler.post(new ModulesAdapter.1(localModulesAdapter, localModule));
  }
  
  public final void removeModule(FinskyModule paramFinskyModule)
  {
    if (this.mModulesAdapter == null) {}
    while (!this.mAddedModules.contains(paramFinskyModule)) {
      return;
    }
    ModulesAdapter localModulesAdapter = this.mModulesAdapter;
    int i = this.mAddedModules.indexOf(paramFinskyModule);
    localModulesAdapter.mModules.remove(i);
    localModulesAdapter.notifyItemRemoved(i);
    this.mAddedModules.remove(paramFinskyModule);
  }
  
  protected final void requestData()
  {
    super.requestData();
    boolean bool = this.mArguments.getBoolean("finsky.DetailsFragment.acquisitionOverride");
    Document localDocument = this.mDocument;
    String str3;
    int i;
    if (bool)
    {
      Object[] arrayOfObject2 = new Object[1];
      if (localDocument != null)
      {
        str3 = localDocument.mDocument.docid;
        arrayOfObject2[0] = str3;
        FinskyLog.w("mAcquisitionOverride true for docId=%s - I hope it came from deep link!", arrayOfObject2);
      }
    }
    else
    {
      if (localDocument.mDocument.backendId != 3) {
        break label306;
      }
      i = 1;
      label69:
      this.mFetchSocialDetailsDocument = false;
      if (i != 0)
      {
        String str1 = this.mDfeApi.getAccountName();
        String str2 = FinskyApp.get().getCurrentAccountName();
        if (!str1.equals(str2))
        {
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = FinskyLog.scrubPii(str2);
          arrayOfObject1[1] = localDocument.mDocument.docid;
          FinskyLog.d("Using current account %s to fetch social details for %s", arrayOfObject1);
          this.mFetchSocialDetailsDocument = true;
          if (this.mSocialDfeDetails != null)
          {
            this.mSocialDfeDetails.removeDataChangedListener(this);
            this.mSocialDfeDetails.removeErrorListener(this.mSocialDetailsErrorListener);
          }
          this.mSocialDfeDetails = new DfeDetails(FinskyApp.get().getDfeApi(str2), this.mUrl, true, null);
          this.mSocialDfeDetails.addDataChangedListener(this);
          this.mSocialDetailsErrorListener = new SocialDetailsErrorListener((byte)0);
          this.mSocialDfeDetails.addErrorListener(this.mSocialDetailsErrorListener);
        }
        BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(509).setDocument(this.mDocument.mDocument.docid).setOperationSuccess(this.mFetchSocialDetailsDocument);
        FinskyApp.get().getEventLogger(str2).sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
      }
      if (!this.mFetchSocialDetailsDocument) {
        break label311;
      }
    }
    label306:
    label311:
    for (DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);; localDfeApi = this.mDfeApi)
    {
      this.mSocialDfeApi = localDfeApi;
      return;
      str3 = null;
      break;
      i = 0;
      break label69;
    }
  }
  
  private final class PlayHeaderListConfigurator
    extends FinskyHeaderListLayout.FinskyConfigurator
  {
    private int mDocBackend;
    private int mHeaderHeight;
    private boolean mIsShowingHeroImage;
    
    public PlayHeaderListConfigurator(Context paramContext, boolean paramBoolean, int paramInt1, int paramInt2)
    {
      super();
      this.mIsShowingHeroImage = paramBoolean;
      this.mHeaderHeight = paramInt1;
      this.mDocBackend = paramInt2;
    }
    
    protected final void addBackgroundView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      if (this.mIsShowingHeroImage)
      {
        DetailsFragment2.this.mBackgroundView = ((HeroGraphicView)paramLayoutInflater.inflate(2130968785, paramViewGroup, false));
        paramViewGroup.addView(DetailsFragment2.this.mBackgroundView);
      }
    }
    
    protected final void addContentView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      paramLayoutInflater.inflate(2130968783, paramViewGroup);
    }
    
    protected final boolean allowImmersiveBackground()
    {
      return this.mIsShowingHeroImage;
    }
    
    protected final boolean alwaysUseFloatingBackground()
    {
      return !this.mIsShowingHeroImage;
    }
    
    protected final float getBackgroundViewParallaxRatio()
    {
      return 0.5F;
    }
    
    protected final int getHeaderHeight()
    {
      return this.mHeaderHeight;
    }
    
    protected final int getListViewId()
    {
      return 2131755590;
    }
    
    protected final int getStatusBarOverlayColor()
    {
      return this.mContext.getResources().getColor(2131689730);
    }
    
    protected final int getStatusBarUnderlayColor()
    {
      return CorpusResourceUtils.getPrimaryColor(this.mContext, this.mDocBackend);
    }
    
    protected final int getToolbarTitleMode()
    {
      if (this.mIsShowingHeroImage) {
        return 0;
      }
      return 1;
    }
  }
  
  private final class SocialDetailsErrorListener
    implements Response.ErrorListener
  {
    private SocialDetailsErrorListener() {}
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramVolleyError.getClass();
      FinskyLog.e("Volley error while fetching social details doc: %s", arrayOfObject);
      DetailsFragment2.access$102$50048723(DetailsFragment2.this);
      DetailsFragment2.this.onDataChanged();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.DetailsFragment2
 * JD-Core Version:    0.7.0.1
 */