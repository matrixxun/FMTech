package com.google.android.finsky.navigationmanager;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.volley.Response.Listener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.activities.DetailsDataBasedFragment;
import com.google.android.finsky.activities.ErrorDialog;
import com.google.android.finsky.activities.FreeSongOfTheDayFragment;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.activities.PeopleDetailsFragment;
import com.google.android.finsky.activities.ReviewsActivity;
import com.google.android.finsky.activities.ScreenshotsActivity;
import com.google.android.finsky.activities.ScreenshotsActivityV2;
import com.google.android.finsky.activities.SearchFragment;
import com.google.android.finsky.activities.TabbedAdapter;
import com.google.android.finsky.activities.TabbedBrowseFragment;
import com.google.android.finsky.activities.myaccount.MyAccountFragment;
import com.google.android.finsky.activities.myapps.MyAppsTabbedFragment;
import com.google.android.finsky.activities.mywishlist.MyWishlistFragment;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeBrowse;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.LightPurchaseFlowActivity;
import com.google.android.finsky.billing.lightpurchase.PurchaseActivity;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams.Builder;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.RedeemCodeActivity;
import com.google.android.finsky.detailspage.DetailsFragment2;
import com.google.android.finsky.detailspage.DetailsFragment2LollipopMR1;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.DeepLinkShimFragment;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.ControlsContainerBackgroundCoordinator;
import com.google.android.finsky.layout.play.FinskyViewPager;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Browse.BrowseResponse;
import com.google.android.finsky.protos.Browse.BrowseTab;
import com.google.android.finsky.protos.CallToAction;
import com.google.android.finsky.protos.Containers.ContainerMetadata;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Link;
import com.google.android.finsky.protos.RateSuggestedContentResponse;
import com.google.android.finsky.protos.ResolveLink.DirectPurchase;
import com.google.android.finsky.protos.ResolveLink.HelpCenter;
import com.google.android.finsky.protos.ResolveLink.RedeemGiftCard;
import com.google.android.finsky.protos.ResolveLink.ResolvedLink;
import com.google.android.finsky.protos.SongDetails;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.AdUtils;
import com.google.android.finsky.utils.DetailsShimFragment;
import com.google.android.finsky.utils.DocUtils.OfferFilter;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.HelpFeedbackUtil;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.MainThreadStack;
import com.google.android.libraries.bind.bidi.BidiPagingHelper;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class NavigationManager
{
  private static final boolean RESPECT_TASKS_IN_UP;
  public MainActivity mActivity;
  public final Stack<NavigationState> mBackStack = new MainThreadStack();
  public FragmentManager mFragmentManager;
  private boolean mShouldFallBackToAppsHome = false;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (boolean bool = true;; bool = false)
    {
      RESPECT_TASKS_IN_UP = bool;
      return;
    }
  }
  
  public NavigationManager(MainActivity paramMainActivity)
  {
    init(paramMainActivity);
  }
  
  public static boolean areTransitionsEnabled()
  {
    return Build.VERSION.SDK_INT >= 22;
  }
  
  private static Fragment getNewDetailsFragmentInstance(Document paramDocument, String paramString1, String paramString2, String paramString3, boolean paramBoolean, View paramView)
  {
    if (areTransitionsEnabled()) {
      return DetailsFragment2LollipopMR1.newInstance(paramDocument, paramString1, paramString2, paramString3, paramBoolean, paramView);
    }
    return DetailsFragment2.newInstance$653305c3(paramDocument, paramString1, paramString2, paramString3, paramBoolean);
  }
  
  private boolean goBack(boolean paramBoolean)
  {
    if ((this.mActivity == null) || (this.mActivity.mStateSaved)) {
      return false;
    }
    if (paramBoolean) {
      FinskyApp.get().getEventLogger().logClickEvent(600, null, getActivePage());
    }
    int i;
    if ((!this.mShouldFallBackToAppsHome) || (!FinskyApp.get().getExperiments().isH20StoreEnabled())) {
      i = 0;
    }
    while (i != 0)
    {
      goToAggregatedHome(FinskyApp.get().mToc);
      return true;
      if ((this.mBackStack.size() != 1) || (this.mFragmentManager.getBackStackEntryCount() != 1))
      {
        i = 0;
      }
      else
      {
        PageFragment localPageFragment = getActivePage();
        if ((isHomeHome()) && (((TabbedBrowseFragment)localPageFragment).mBackendId == 3)) {
          i = 0;
        } else {
          i = 1;
        }
      }
    }
    try
    {
      FinskyLog.startTiming();
      this.mBackStack.pop();
      this.mFragmentManager.popBackStack();
      this.mBackStack.peek();
      return true;
    }
    catch (EmptyStackException localEmptyStackException) {}
    return false;
  }
  
  private void goToDocPage(Document paramDocument, String paramString1, String paramString2, String paramString3, boolean paramBoolean, View paramView)
  {
    if (!canNavigate()) {
      return;
    }
    int i = paramDocument.mDocument.docType;
    if ((paramDocument.mDocument.backendId == 2) && (paramDocument.hasAntennaInfo()))
    {
      showPage(5, paramString1, getNewDetailsFragmentInstance(paramDocument, paramString1, null, null, false, paramView), paramBoolean, new View[] { paramView });
      return;
    }
    if ((paramDocument.mDocument.backendId == 2) && (paramDocument.hasDealOfTheDayInfo()))
    {
      showPage(5, paramString1, FreeSongOfTheDayFragment.newInstance(paramDocument, paramString1), paramBoolean, new View[] { paramView });
      return;
    }
    switch (i)
    {
    default: 
      showPage(5, paramString1, getNewDetailsFragmentInstance(paramDocument, paramString1, paramString2, null, false, paramView), paramBoolean, new View[] { paramView });
      return;
    case 7: 
      Resources localResources = this.mActivity.getResources();
      ErrorDialog.show(this.mActivity.getSupportFragmentManager(), localResources.getString(2131362123), localResources.getString(2131362816), false);
      return;
    case 1: 
      String str1 = paramDocument.getAppDetails().packageName;
      String str2 = FinskyApp.get().getCurrentAccountName();
      boolean bool;
      if (!TextUtils.isEmpty(paramString3))
      {
        bool = true;
        if (!bool) {
          break label329;
        }
      }
      label329:
      for (String str3 = paramString3;; str3 = AppActionAnalyzer.getAppDetailsAccount(str1, str2, FinskyApp.get().mAppStates, FinskyApp.get().mLibraries))
      {
        if ((bool) || (!str2.equals(str3)))
        {
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = FinskyLog.scrubPii(str3);
          arrayOfObject[1] = str1;
          arrayOfObject[2] = Boolean.valueOf(bool);
          FinskyLog.d("Selecting account %s for package %s. overriding=[%s]", arrayOfObject);
        }
        showPage(5, paramString1, getNewDetailsFragmentInstance(paramDocument, paramString1, paramString2, str3, bool, paramView), paramBoolean, new View[] { paramView });
        return;
        bool = false;
        break;
      }
    }
    showPage(11, paramString1, PeopleDetailsFragment.newInstance(paramDocument, paramString1), paramBoolean, new View[] { paramView });
  }
  
  public static boolean hasClickListener(Document paramDocument)
  {
    return (paramDocument.hasLinkAnnotation()) || (paramDocument.hasAntennaInfo()) || (!TextUtils.isEmpty(paramDocument.mDocument.detailsUrl)) || ((paramDocument.hasContainerAnnotation()) && (!TextUtils.isEmpty(paramDocument.mDocument.containerMetadata.browseUrl)));
  }
  
  private boolean isHomeHome()
  {
    int i = getCurrentPageType();
    return ((getActivePage() instanceof TabbedBrowseFragment)) && ((i == 1) || (i == 0));
  }
  
  private void resolveLink(ResolveLink.ResolvedLink paramResolvedLink, String paramString1, int paramInt, DfeToc paramDfeToc, PlayStoreUiElementNode paramPlayStoreUiElementNode, String paramString2)
  {
    if (canNavigate())
    {
      FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
      if (TextUtils.isEmpty(paramResolvedLink.browseUrl)) {
        break label42;
      }
      goBrowse(paramResolvedLink.browseUrl, paramString1, paramInt, paramDfeToc, null);
    }
    label42:
    ResolveLink.HelpCenter localHelpCenter;
    do
    {
      do
      {
        return;
        if (!TextUtils.isEmpty(paramResolvedLink.detailsUrl))
        {
          goToDocPage(paramResolvedLink.detailsUrl);
          return;
        }
        if (paramResolvedLink.directPurchase != null)
        {
          PurchaseParams.Builder localBuilder = PurchaseParams.builder();
          localBuilder.docidStr = paramResolvedLink.directPurchase.purchaseDocid;
          localBuilder.docid = paramResolvedLink.docid;
          localBuilder.offerType = paramResolvedLink.directPurchase.offerType;
          localBuilder.voucherId = paramString2;
          PurchaseParams localPurchaseParams = localBuilder.build();
          Intent localIntent = PurchaseActivity.createIntent(FinskyApp.get().getCurrentAccount(), localPurchaseParams, null, null);
          this.mActivity.startActivity(localIntent);
          return;
        }
        if (!TextUtils.isEmpty(paramResolvedLink.homeUrl))
        {
          goToAggregatedHome(paramDfeToc, paramResolvedLink.homeUrl);
          return;
        }
        if (paramResolvedLink.redeemGiftCard != null)
        {
          goRedeem(FinskyApp.get().getCurrentAccountName(), paramResolvedLink.redeemGiftCard);
          return;
        }
        if (!TextUtils.isEmpty(paramResolvedLink.searchUrl))
        {
          goToSearch(paramResolvedLink.searchUrl, paramResolvedLink.query, paramResolvedLink.backend, null);
          return;
        }
        if (!TextUtils.isEmpty(paramResolvedLink.myAccountUrl))
        {
          goToMyAccount();
          return;
        }
      } while (paramResolvedLink.helpCenter == null);
      localHelpCenter = paramResolvedLink.helpCenter;
    } while (TextUtils.isEmpty(localHelpCenter.contextId));
    HelpFeedbackUtil.launchGoogleHelp(getActivityForResolveLink(), localHelpCenter.contextId);
  }
  
  public static boolean useFixedTabs(DfeToc paramDfeToc)
  {
    return (paramDfeToc.mToc.themeId != 1) && (FinskyApp.get().getExperiments().isEnabled(12603505L));
  }
  
  public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    this.mFragmentManager.addOnBackStackChangedListener(paramOnBackStackChangedListener);
  }
  
  public void buy(Account paramAccount, Document paramDocument, int paramInt, DocUtils.OfferFilter paramOfferFilter, String paramString)
  {
    if (canNavigate())
    {
      Intent localIntent = LightPurchaseFlowActivity.createIntent$202310a9(paramAccount, paramDocument, paramInt, paramOfferFilter, paramDocument.mDocument.serverLogsCookie, paramString, null);
      this.mActivity.startActivityForResult(localIntent, 33);
    }
  }
  
  public boolean canGoUp()
  {
    if ((isBackStackEmpty()) || (this.mBackStack.size() == 0)) {}
    DfeToc localDfeToc;
    do
    {
      NavigationState localNavigationState;
      do
      {
        return false;
        localNavigationState = (NavigationState)this.mBackStack.peek();
      } while ((localNavigationState.pageType == 1) || (localNavigationState.pageType == 17));
      if (localNavigationState.pageType != 2) {
        return true;
      }
      localDfeToc = getActivePage().mDfeToc;
    } while ((localDfeToc == null) || (localDfeToc.getCorpusList().size() <= 1));
    return true;
  }
  
  public final boolean canNavigate()
  {
    return (this.mActivity != null) && (!this.mActivity.mStateSaved);
  }
  
  public final boolean canPromptSearchSurveyForCurrent()
  {
    int i = this.mBackStack.size();
    if (i < 2) {
      return false;
    }
    return ((NavigationState)this.mBackStack.elementAt(i - 2)).canTriggerSearchSurvey;
  }
  
  public boolean canSearch()
  {
    switch (getCurrentPageType())
    {
    default: 
      return true;
    }
    return false;
  }
  
  public final void clear()
  {
    clearInternal();
    this.mShouldFallBackToAppsHome = false;
  }
  
  public final void clearInternal()
  {
    this.mBackStack.removeAllElements();
    while (this.mFragmentManager.getBackStackEntryCount() > 0) {
      this.mFragmentManager.popBackStackImmediate();
    }
  }
  
  public PageFragment getActivePage()
  {
    return (PageFragment)this.mFragmentManager.findFragmentById(2131755234);
  }
  
  public Activity getActivityForResolveLink()
  {
    return this.mActivity;
  }
  
  public final View.OnClickListener getBuyImmediateClickListener(final Account paramAccount, final Document paramDocument, final int paramInt1, final DocUtils.OfferFilter paramOfferFilter, final String paramString, final int paramInt2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (paramPlayStoreUiElementNode != null) {}
    for (Object localObject = paramPlayStoreUiElementNode;; localObject = getActivePage()) {
      new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          FinskyApp.get().getEventLogger().logClickEvent(paramInt2, null, this.val$rootNode);
          NavigationManager.this.buy(paramAccount, paramDocument, paramInt1, paramOfferFilter, paramString);
        }
      };
    }
  }
  
  public final View.OnClickListener getClickListener(Document paramDocument, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    return getClickListener(paramDocument, paramPlayStoreUiElementNode, null, -1, null);
  }
  
  public final View.OnClickListener getClickListener(final Document paramDocument, final PlayStoreUiElementNode paramPlayStoreUiElementNode, final String paramString, final int paramInt, final View paramView)
  {
    if (!hasClickListener(paramDocument)) {
      return null;
    }
    if (paramDocument.hasLinkAnnotation())
    {
      if (paramDocument.getLinkAnnotation().resolvedLink != null)
      {
        final DfeToc localDfeToc = FinskyApp.get().mToc;
        new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            if (!TextUtils.isEmpty(this.val$resolvedLink.browseUrl)) {
              NavigationManager.this.goBrowse(this.val$resolvedLink.browseUrl, paramDocument.mDocument.title, paramDocument.mDocument.backendId, localDfeToc, paramPlayStoreUiElementNode);
            }
            do
            {
              return;
              if (!TextUtils.isEmpty(this.val$resolvedLink.searchUrl))
              {
                NavigationManager.this.goToSearch(this.val$resolvedLink.searchUrl, paramString, paramInt, paramPlayStoreUiElementNode);
                return;
              }
              if (!TextUtils.isEmpty(this.val$resolvedLink.detailsUrl))
              {
                FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
                NavigationManager.this.goToDocPage(this.val$resolvedLink.detailsUrl);
                return;
              }
              if (!TextUtils.isEmpty(this.val$resolvedLink.homeUrl))
              {
                if (FinskyApp.get().getExperiments().isH20StoreEnabled())
                {
                  NavigationManager.this.goToAggregatedHome(localDfeToc, this.val$resolvedLink.homeUrl);
                  return;
                }
                NavigationManager.this.goBrowse(this.val$resolvedLink.homeUrl, paramDocument.mDocument.title, paramDocument.mDocument.backendId, localDfeToc, paramPlayStoreUiElementNode);
                return;
              }
            } while (this.val$resolvedLink.redeemGiftCard == null);
            FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
            NavigationManager.this.goRedeem(FinskyApp.get().getCurrentAccountName(), this.val$resolvedLink.redeemGiftCard);
          }
        };
      }
      new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramDocument.getLinkAnnotation().uri));
          if (IntentUtils.shouldConsumptionAppHandleIntentDirectly(paramDocument.mDocument.backendId, localIntent)) {
            localIntent.setPackage(IntentUtils.getPackageName(paramDocument.mDocument.backendId));
          }
          paramAnonymousView.getContext().startActivity(localIntent);
          FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
        }
      };
    }
    if ((paramDocument.hasContainerAnnotation()) && (!TextUtils.isEmpty(paramDocument.mDocument.containerMetadata.browseUrl))) {
      new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          String str = paramDocument.mDocument.containerMetadata.browseUrl;
          NavigationManager.this.goBrowse(str, null, paramDocument.mDocument.backendId, FinskyApp.get().mToc, paramPlayStoreUiElementNode);
        }
      };
    }
    new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        AdUtils.trackCardClick(paramAnonymousView.getContext(), paramDocument, "22", paramAnonymousView.getWidth(), paramAnonymousView.getHeight());
        String str = paramDocument.mDocument.detailsUrl;
        Document localDocument = paramDocument;
        int i = localDocument.mDocument.docType;
        int j = 0;
        if (i == 12)
        {
          if (j != 0) {
            break label139;
          }
          NavigationManager.this.goToDocPage(str);
        }
        for (;;)
        {
          FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
          return;
          SongDetails localSongDetails = localDocument.getSongDetails();
          j = 0;
          if (localSongDetails != null) {
            break;
          }
          boolean bool = localDocument.mDocument.detailsReusable;
          j = 0;
          if (!bool) {
            break;
          }
          int k = localDocument.mDocument.docType;
          j = 0;
          if (k == 15) {
            break;
          }
          j = 1;
          break;
          label139:
          if ((NavigationManager.areTransitionsEnabled()) && (paramView != null))
          {
            NavigationManager.this.goToDocPage(paramDocument, paramView);
            FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
            return;
          }
          NavigationManager.this.goToDocPage(paramDocument);
        }
      }
    };
  }
  
  public final Document getCurrentDocument()
  {
    if (this.mFragmentManager == null) {}
    PageFragment localPageFragment;
    do
    {
      return null;
      localPageFragment = getActivePage();
    } while ((localPageFragment == null) || (!(localPageFragment instanceof DetailsDataBasedFragment)));
    return ((DetailsDataBasedFragment)localPageFragment).mDocument;
  }
  
  public final int getCurrentPageType()
  {
    if (this.mBackStack.isEmpty()) {
      return 0;
    }
    return ((NavigationState)this.mBackStack.peek()).pageType;
  }
  
  public final View.OnClickListener getOpenClickListener(final Document paramDocument, final Account paramAccount, final PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        FinskyApp.get().getEventLogger().logClickEvent(218, null, paramPlayStoreUiElementNode);
        NavigationManager.this.openItem(paramAccount, paramDocument, false);
      }
    };
  }
  
  public boolean goBack()
  {
    return goBack(true);
  }
  
  public void goBrowse(String paramString1, String paramString2, int paramInt, DfeToc paramDfeToc, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (canNavigate())
    {
      FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
      if (paramString1.equals(paramDfeToc.mToc.socialHomeUrl)) {
        goToSocialHome(paramString1, paramDfeToc);
      }
    }
    else
    {
      return;
    }
    int i = 4;
    if ((paramDfeToc != null) && (paramDfeToc.getCorpus(paramString1) != null)) {
      i = 2;
    }
    showPage(i, paramString1, TabbedBrowseFragment.newInstance$30e04d94(paramString1, paramString2, paramInt, paramDfeToc, false), false, new View[0]);
  }
  
  public final void goRedeem(String paramString, ResolveLink.RedeemGiftCard paramRedeemGiftCard)
  {
    String str1 = null;
    String str2 = null;
    if (paramRedeemGiftCard != null)
    {
      boolean bool1 = TextUtils.isEmpty(paramRedeemGiftCard.prefillCode);
      str2 = null;
      if (!bool1) {
        str2 = paramRedeemGiftCard.prefillCode;
      }
      boolean bool2 = TextUtils.isEmpty(paramRedeemGiftCard.partnerPayload);
      str1 = null;
      if (!bool2) {
        str1 = paramRedeemGiftCard.partnerPayload;
      }
    }
    Intent localIntent = RedeemCodeActivity.createIntent(paramString, 3, str2, str1);
    getActivityForResolveLink().startActivityForResult(localIntent, 34);
  }
  
  public final void goToAggregatedHome(DfeToc paramDfeToc)
  {
    if (paramDfeToc == null) {}
    do
    {
      return;
      if (!canNavigate()) {
        break;
      }
    } while (maybeSwitchTabs$3024efe(paramDfeToc, 3));
    if (paramDfeToc.getCorpusList().size() == 1)
    {
      clearInternal();
      Toc.CorpusMetadata localCorpusMetadata = (Toc.CorpusMetadata)paramDfeToc.getCorpusList().get(0);
      TabbedBrowseFragment localTabbedBrowseFragment = TabbedBrowseFragment.newInstance$30e04d94(localCorpusMetadata.landingUrl, localCorpusMetadata.name, localCorpusMetadata.backend, paramDfeToc, useFixedTabs(paramDfeToc));
      showPage(2, localCorpusMetadata.landingUrl, localTabbedBrowseFragment, true, new View[0]);
      return;
    }
    goToAggregatedHome(paramDfeToc, paramDfeToc.mToc.homeUrl);
    return;
    this.mActivity.restartOnResume();
  }
  
  public final void goToAggregatedHome(DfeToc paramDfeToc, String paramString)
  {
    String str = this.mActivity.getString(2131362289);
    int i;
    if (FinskyApp.get().getExperiments().isH20StoreEnabled()) {
      if (paramString.equals(paramDfeToc.mToc.entertainmentHomeUrl)) {
        i = 13;
      }
    }
    while (maybeSwitchTabs$3024efe(paramDfeToc, i))
    {
      return;
      if (TextUtils.isEmpty(paramDfeToc.mToc.entertainmentHomeUrl))
      {
        Iterator localIterator = paramDfeToc.getCorpusList().iterator();
        for (;;)
        {
          if (localIterator.hasNext())
          {
            Toc.CorpusMetadata localCorpusMetadata = (Toc.CorpusMetadata)localIterator.next();
            if (paramString.equals(localCorpusMetadata.landingUrl))
            {
              i = localCorpusMetadata.backend;
              break;
            }
          }
        }
      }
      i = 3;
      continue;
      i = 0;
    }
    clearInternal();
    showPage(1, paramString, TabbedBrowseFragment.newInstance$30e04d94(paramString, str, i, paramDfeToc, useFixedTabs(paramDfeToc)), true, new View[0]);
  }
  
  public final void goToAllReviews(Document paramDocument, String paramString, boolean paramBoolean)
  {
    if ((this.mActivity == null) || (this.mActivity.mStateSaved)) {
      return;
    }
    Intent localIntent = ReviewsActivity.getIntent(this.mActivity, paramDocument, paramString, paramBoolean);
    PageFragment localPageFragment = getActivePage();
    if (localPageFragment != null)
    {
      localPageFragment.startActivityForResult(localIntent, 45);
      return;
    }
    this.mActivity.startActivityForResult(localIntent, 45);
  }
  
  public final void goToCorpusHome(String paramString1, String paramString2, int paramInt, DfeToc paramDfeToc)
  {
    if (canNavigate()) {
      showPage(2, paramString1, TabbedBrowseFragment.newInstance$30e04d94(paramString1, paramString2, paramInt, paramDfeToc, false), false, new View[0]);
    }
  }
  
  public final void goToDocPage(Document paramDocument)
  {
    goToDocPage(paramDocument, paramDocument.mDocument.detailsUrl, null, null, false, null);
  }
  
  public final void goToDocPage(Document paramDocument, View paramView)
  {
    goToDocPage(paramDocument, paramDocument.mDocument.detailsUrl, null, null, false, paramView);
  }
  
  public void goToDocPage(String paramString)
  {
    if (!canNavigate()) {
      return;
    }
    showPage(6, paramString, DetailsShimFragment.newInstance(paramString, null, null, null), false, new View[0]);
  }
  
  public final void goToDocPage(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (!canNavigate()) {
      return;
    }
    showPage(6, paramString1, DetailsShimFragment.newInstance(paramString1, paramString2, paramString3, paramString4), false, new View[0]);
  }
  
  public final void goToDocPage$6d245699(Document paramDocument, String paramString1, String paramString2, String paramString3)
  {
    goToDocPage(paramDocument, paramString1, paramString2, paramString3, true, null);
  }
  
  public void goToImagesLightbox(Document paramDocument, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((this.mActivity == null) || (this.mActivity.mStateSaved)) {
      return;
    }
    if (FinskyApp.get().getExperiments().isEnabled(12602819L))
    {
      ScreenshotsActivityV2.show(this.mActivity, paramDocument, paramInt1, paramInt2);
      return;
    }
    ScreenshotsActivity.show(this.mActivity, paramDocument, paramInt1, paramInt2, paramBoolean);
  }
  
  public final void goToMyAccount()
  {
    if (canNavigate()) {
      showPage(13, null, MyAccountFragment.newInstance(), false, new View[0]);
    }
  }
  
  public final void goToMyDownloads(DfeToc paramDfeToc, boolean paramBoolean)
  {
    if (canNavigate()) {
      showPage(3, null, MyAppsTabbedFragment.newInstance(paramDfeToc, paramBoolean), false, new View[0]);
    }
  }
  
  public final void goToMyWishlist()
  {
    if (canNavigate()) {
      showPage(10, null, MyWishlistFragment.newInstance(), false, new View[0]);
    }
  }
  
  public final void goToScreenshots(Document paramDocument, int paramInt, boolean paramBoolean)
  {
    goToImagesLightbox(paramDocument, paramInt, 1, paramBoolean);
  }
  
  public final void goToSearch(String paramString1, String paramString2, int paramInt, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    if (canNavigate())
    {
      showPage(7, paramString1, SearchFragment.newInstance(paramString2, paramString1, paramInt), false, new View[0]);
      FinskyApp.get().getEventLogger().logClickEvent(paramPlayStoreUiElementNode);
    }
  }
  
  public final void goToSearch$36098d51(String paramString, int paramInt)
  {
    goToSearch(DfeUtils.formSearchUrl(paramString, paramInt), paramString, paramInt, null);
  }
  
  public final void goToSettings()
  {
    this.mActivity.openSettings(null);
  }
  
  public final void goToSocialHome(String paramString, DfeToc paramDfeToc)
  {
    if ((canNavigate()) && (!TextUtils.isEmpty(paramDfeToc.mToc.socialHomeUrl))) {
      showPage(12, paramString, TabbedBrowseFragment.newInstance$30e04d94(paramString, FinskyApp.get().getString(2131362762), 9, paramDfeToc, false), false, new View[0]);
    }
  }
  
  public final void goToUrl(String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    localIntent.putExtra("com.android.browser.application_id", this.mActivity.getPackageName());
    this.mActivity.startActivity(localIntent);
  }
  
  public boolean goUp()
  {
    if ((this.mActivity == null) || (this.mActivity.mStateSaved) || (this.mBackStack.isEmpty())) {
      return true;
    }
    FinskyApp.get().getEventLogger().logClickEvent(602, null, getActivePage());
    DfeToc localDfeToc1 = FinskyApp.get().mToc;
    if (RESPECT_TASKS_IN_UP)
    {
      Document localDocument2 = getCurrentDocument();
      int k;
      if (localDocument2 != null)
      {
        k = localDocument2.mDocument.backendId;
        if ((localDfeToc1 == null) || (k < 0) || (localDfeToc1.getCorpus(k) == null) || (k == 0) || (k == 9)) {
          break label170;
        }
      }
      label170:
      for (Intent localIntent = IntentUtils.createCorpusIntent(this.mActivity, localDocument2.mDocument.backendId, localDfeToc1);; localIntent = IntentUtils.createAggregatedHomeIntent(this.mActivity))
      {
        if (!NavUtils.shouldUpRecreateTask(this.mActivity, localIntent)) {
          break label182;
        }
        TaskStackBuilder.create(this.mActivity).addNextIntent(localIntent).startActivities();
        ActivityCompat.finishAffinity(this.mActivity);
        return true;
        k = -1;
        break;
      }
    }
    label182:
    switch (((NavigationState)this.mBackStack.peek()).pageType)
    {
    }
    for (;;)
    {
      return true;
      if ((this.mActivity != null) && (!this.mActivity.mStateSaved))
      {
        DfeToc localDfeToc2 = FinskyApp.get().mToc;
        if (localDfeToc2 != null)
        {
          while (!this.mBackStack.isEmpty())
          {
            int j = ((NavigationState)this.mBackStack.peek()).pageType;
            if ((j != 5) && (j != 6)) {
              break;
            }
            this.mBackStack.pop();
          }
          if (!this.mBackStack.isEmpty())
          {
            this.mFragmentManager.popBackStack(((NavigationState)this.mBackStack.peek()).backstackName, 0);
          }
          else
          {
            this.mFragmentManager.popBackStack(this.mFragmentManager.getBackStackEntryAt$71bc1da8().getName(), 1);
            Document localDocument1 = getCurrentDocument();
            if (localDocument1 != null)
            {
              int i = localDocument1.mDocument.backendId;
              Toc.CorpusMetadata localCorpusMetadata = localDfeToc2.getCorpus(i);
              if (localCorpusMetadata != null)
              {
                redirectToCorpus(localCorpusMetadata.landingUrl, localCorpusMetadata.name, i, localDfeToc2);
                continue;
              }
            }
            goToAggregatedHome(localDfeToc2);
            continue;
            return goBack(false);
            goToAggregatedHome(localDfeToc1);
            continue;
            goToAggregatedHome(localDfeToc1);
          }
        }
      }
    }
  }
  
  public final void handleDeepLink(Uri paramUri, String paramString)
  {
    showPage(9, null, DeepLinkShimFragment.newInstance(paramUri, paramString), false, new View[0]);
  }
  
  public void init(MainActivity paramMainActivity)
  {
    this.mActivity = paramMainActivity;
    this.mFragmentManager = this.mActivity.getSupportFragmentManager();
  }
  
  public boolean isBackStackEmpty()
  {
    return this.mFragmentManager.getBackStackEntryCount() == 0;
  }
  
  public final boolean isOnBrowsePage()
  {
    int i = getCurrentPageType();
    return (i == 1) || (i == 2) || (i == 4);
  }
  
  public final boolean maybeSwitchTabs$3024efe(DfeToc paramDfeToc, int paramInt)
  {
    int i;
    int k;
    if (paramDfeToc.getCorpusList().size() > 1)
    {
      i = 1;
      boolean bool1 = FinskyApp.get().getExperiments().isEnabled(12603505L);
      if ((!isHomeHome()) || (i == 0) || (!bool1)) {
        break label245;
      }
      TabbedBrowseFragment localTabbedBrowseFragment = (TabbedBrowseFragment)getActivePage();
      if ((localTabbedBrowseFragment.mBrowseData == null) || (localTabbedBrowseFragment.mViewPager == null) || (!FinskyApp.get().getExperiments().isEnabled(12603505L))) {
        break label239;
      }
      Browse.BrowseTab[] arrayOfBrowseTab = localTabbedBrowseFragment.mBrowseData.mBrowseResponse.browseTab;
      k = 0;
      label101:
      if (k >= arrayOfBrowseTab.length) {
        break label239;
      }
      if (paramInt != arrayOfBrowseTab[k].backendId) {
        break label233;
      }
      ControlsContainerBackgroundCoordinator localControlsContainerBackgroundCoordinator = localTabbedBrowseFragment.mControlsContainerBackgroundCoordinator;
      boolean bool2 = localTabbedBrowseFragment.mTabbedAdapter.mIsRtl;
      localControlsContainerBackgroundCoordinator.mTabStrip.getLocationOnScreen(localControlsContainerBackgroundCoordinator.mLocationOnScreen);
      localControlsContainerBackgroundCoordinator.mLastTouchX = localControlsContainerBackgroundCoordinator.mLocationOnScreen[0];
      if (bool2) {
        localControlsContainerBackgroundCoordinator.mLastTouchX += localControlsContainerBackgroundCoordinator.mTabStrip.getWidth();
      }
      int m = BidiPagingHelper.getVisualPosition(localTabbedBrowseFragment.mTabbedAdapter, k);
      localTabbedBrowseFragment.mViewPager.setCurrentItem(m, true);
      localTabbedBrowseFragment.onTabSelectedInternal(m, true);
    }
    label233:
    label239:
    for (int j = 1;; j = 0)
    {
      if (j == 0) {
        break label245;
      }
      return true;
      i = 0;
      break;
      k++;
      break label101;
    }
    label245:
    return false;
  }
  
  public void openItem(Account paramAccount, Document paramDocument, boolean paramBoolean)
  {
    int i = 1;
    if (!ConsumptionUtils.openItem(this.mActivity, paramAccount, paramDocument, this.mFragmentManager, null, i, null)) {}
    for (;;)
    {
      if ((i != 0) && (paramBoolean)) {
        this.mActivity.finish();
      }
      return;
      i = 0;
    }
  }
  
  public final void popBackStack()
  {
    if (!this.mBackStack.isEmpty()) {
      this.mBackStack.pop();
    }
    this.mFragmentManager.popBackStack();
  }
  
  public final void redirectToCorpus(String paramString1, String paramString2, int paramInt, DfeToc paramDfeToc)
  {
    switch (paramInt)
    {
    default: 
      if (TextUtils.isEmpty(paramDfeToc.mToc.entertainmentHomeUrl))
      {
        goToAggregatedHome(paramDfeToc, paramString1);
        return;
      }
      break;
    case 3: 
    case 13: 
      goToAggregatedHome(paramDfeToc, paramString1);
      return;
    }
    goToCorpusHome(paramString1, paramString2, paramInt, paramDfeToc);
  }
  
  public final void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    this.mFragmentManager.removeOnBackStackChangedListener(paramOnBackStackChangedListener);
  }
  
  public final void resetBackstackToAppsHome()
  {
    if (!isHomeHome()) {
      clearInternal();
    }
    this.mShouldFallBackToAppsHome = true;
  }
  
  public final void resolveCallToAction(CallToAction paramCallToAction, DfeApi paramDfeApi, DfeToc paramDfeToc, PackageManager paramPackageManager)
  {
    if (paramCallToAction.type == 1) {
      paramDfeApi.rateSuggestedContent(paramCallToAction.dismissalUrl, new Response.Listener() {}, null);
    }
    while (paramCallToAction.type != 2) {
      return;
    }
    resolveLink(paramCallToAction.link, paramDfeToc, paramPackageManager);
  }
  
  public final void resolveLink(Link paramLink, DfeToc paramDfeToc, PackageManager paramPackageManager)
  {
    resolveLink(paramLink, null, paramDfeToc, paramPackageManager);
  }
  
  public final void resolveLink(Link paramLink, String paramString, DfeToc paramDfeToc, PackageManager paramPackageManager)
  {
    resolveLink(paramLink, paramString, paramDfeToc, paramPackageManager, null);
  }
  
  public final void resolveLink(Link paramLink, String paramString1, DfeToc paramDfeToc, PackageManager paramPackageManager, String paramString2)
  {
    Activity localActivity = getActivityForResolveLink();
    if (paramLink.resolvedLink != null)
    {
      resolveLink(paramLink.resolvedLink, paramString1, -1, paramDfeToc, null, paramString2);
      return;
    }
    if (paramLink.hasUriBackend)
    {
      int i = paramLink.uriBackend;
      if (!IntentUtils.isConsumptionAppInstalled(paramPackageManager, i))
      {
        showAppNeededDialog(i);
        return;
      }
      if (!TextUtils.isEmpty(paramLink.uri))
      {
        Intent localIntent2 = IntentUtils.buildConsumptionAppUrlIntent$5928b7f1(i, paramLink.uri, FinskyApp.get().getCurrentAccountName());
        if (!IntentUtils.canConsumptionAppHandleIntent(i, localIntent2))
        {
          showAppNeededDialog(i);
          return;
        }
        localActivity.startActivity(localIntent2);
        return;
      }
      localActivity.startActivity(IntentUtils.buildConsumptionAppLaunchIntent(localActivity, i, FinskyApp.get().getCurrentAccountName()));
      return;
    }
    Intent localIntent1 = new Intent("android.intent.action.VIEW");
    localIntent1.setData(Uri.parse(paramLink.uri));
    localActivity.startActivity(localIntent1);
  }
  
  public final void resolveLink(ResolveLink.ResolvedLink paramResolvedLink, String paramString, int paramInt, DfeToc paramDfeToc, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    resolveLink(paramResolvedLink, paramString, paramInt, paramDfeToc, paramPlayStoreUiElementNode, null);
  }
  
  public final void setActionBarOverlayEnabledForCurrent(boolean paramBoolean)
  {
    if (this.mBackStack.isEmpty()) {
      return;
    }
    ((NavigationState)this.mBackStack.peek()).isActionBarOverlayEnabled = paramBoolean;
  }
  
  public final void setStatusBarOverlayEnabledForCurrent(boolean paramBoolean)
  {
    if (this.mBackStack.isEmpty()) {
      return;
    }
    ((NavigationState)this.mBackStack.peek()).isStatusBarOverlayEnabled = paramBoolean;
  }
  
  public final void showAppNeededDialog(int paramInt)
  {
    ConsumptionUtils.showAppNeededDialog(this.mActivity, paramInt, this.mFragmentManager, null, 1);
  }
  
  public final void showPage(int paramInt, String paramString, Fragment paramFragment, boolean paramBoolean, View... paramVarArgs)
  {
    FinskyLog.startTiming();
    FragmentTransaction localFragmentTransaction = this.mFragmentManager.beginTransaction();
    if (areTransitionsEnabled())
    {
      int i = paramVarArgs.length;
      int j = 0;
      if (j < i)
      {
        View localView = paramVarArgs[j];
        if (localView != null) {}
        for (String str = localView.getTransitionName();; str = null)
        {
          if (!TextUtils.isEmpty(str)) {
            localFragmentTransaction.addSharedElement(localView, str);
          }
          j++;
          break;
        }
      }
    }
    localFragmentTransaction.setTransition$9d93138();
    localFragmentTransaction.replace(2131755234, paramFragment);
    if (paramBoolean) {
      popBackStack();
    }
    NavigationState localNavigationState = new NavigationState(paramInt, null, paramString);
    localFragmentTransaction.addToBackStack(localNavigationState.backstackName);
    this.mBackStack.push(localNavigationState);
    localFragmentTransaction.commit();
  }
  
  public static abstract interface UsesGenericTransition {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.navigationmanager.NavigationManager
 * JD-Core Version:    0.7.0.1
 */