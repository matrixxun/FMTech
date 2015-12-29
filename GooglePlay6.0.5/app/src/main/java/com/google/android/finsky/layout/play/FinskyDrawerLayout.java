package com.google.android.finsky.layout.play;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.DebugActivity;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.activities.TabbedBrowseFragment;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DotNotificationUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.GPlusDialogsHelper;
import com.google.android.finsky.utils.HelpFeedbackUtil;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.drawer.PlayDrawerAdapter;
import com.google.android.play.drawer.PlayDrawerLayout;
import com.google.android.play.drawer.PlayDrawerLayout.PlayDrawerContentClickListener;
import com.google.android.play.drawer.PlayDrawerLayout.PlayDrawerDownloadSwitchConfig;
import com.google.android.play.drawer.PlayDrawerLayout.PlayDrawerPrimaryAction;
import com.google.android.play.drawer.PlayDrawerLayout.PlayDrawerSecondaryAction;
import com.google.android.play.search.PlaySearch;
import com.google.android.play.utils.PlayCommonLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FinskyDrawerLayout
  extends PlayDrawerLayout
  implements PlayStoreUiElementNode, PlayDrawerLayout.PlayDrawerContentClickListener
{
  private final AccountManager mAccountManager;
  private int mCurrentBackendId;
  private DfeToc mDfeToc;
  public MainActivity mMainActivity;
  public NavigationManager mNavigationManager;
  private final OnAccountsUpdateListener mOnAccountsUpdateListener;
  private final Handler mRefreshHandler;
  private final Runnable mRefreshRunnable;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(5302);
  
  public FinskyDrawerLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public FinskyDrawerLayout(final Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mAccountManager = AccountManager.get(paramContext);
    this.mOnAccountsUpdateListener = new OnAccountsUpdateListener()
    {
      public final void onAccountsUpdated(Account[] paramAnonymousArrayOfAccount)
      {
        Utils.syncDebugActivityStatus(paramContext);
        FinskyDrawerLayout.this.refresh();
      }
    };
    this.mCurrentBackendId = 0;
    this.mRefreshRunnable = new Runnable()
    {
      public final void run()
      {
        FinskyDrawerLayout.this.refresh();
      }
    };
    this.mRefreshHandler = new Handler(Looper.myLooper());
  }
  
  private void createCommonSecondaryActions(List<PlayDrawerLayout.PlayDrawerSecondaryAction> paramList)
  {
    final Context localContext = getContext();
    paramList.add(new PlayDrawerLayout.PlayDrawerSecondaryAction(localContext.getString(2131362202), new Runnable()
    {
      public final void run()
      {
        FinskyDrawerLayout.this.logMenuClickEvent(112);
        HelpFeedbackUtil.launchHelpFeedback(FinskyDrawerLayout.this.mMainActivity);
      }
    }));
    if (FinskyApp.get().getExperiments().isEnabled(12602795L)) {
      paramList.add(new PlayDrawerLayout.PlayDrawerSecondaryAction(localContext.getString(2131362485), new Runnable()
      {
        public final void run()
        {
          FinskyDrawerLayout.this.logMenuClickEvent(129);
          HelpFeedbackUtil.launchParentGuide(FinskyDrawerLayout.this.mMainActivity);
        }
      }));
    }
    final String str1 = (String)G.legalNoticeMenuUrl.get();
    if (!TextUtils.isEmpty(str1))
    {
      String str2 = (String)G.legalNoticeMenuTitleOverride.get();
      if (TextUtils.isEmpty(str2)) {
        str2 = getContext().getString(2131362292);
      }
      paramList.add(new PlayDrawerLayout.PlayDrawerSecondaryAction(str2, new Runnable()
      {
        public final void run()
        {
          FinskyDrawerLayout.this.mNavigationManager.goToUrl(str1);
        }
      }));
    }
    if (((Boolean)G.debugOptionsEnabled.get()).booleanValue()) {
      paramList.add(new PlayDrawerLayout.PlayDrawerSecondaryAction(localContext.getString(2131363005), new Runnable()
      {
        public final void run()
        {
          localContext.startActivity(new Intent(localContext, DebugActivity.class));
        }
      }));
    }
  }
  
  private PlayDrawerLayout.PlayDrawerPrimaryAction getHomePrimaryAction(Context paramContext, int paramInt, boolean paramBoolean1, final boolean paramBoolean2, boolean paramBoolean3)
  {
    int i = 1;
    int j;
    int k;
    int m;
    if ((paramBoolean2) && (!paramBoolean3))
    {
      j = 2131362322;
      k = 2130837716;
      m = 2130837716;
      if ((paramInt != i) || (this.mCurrentBackendId != 3)) {}
    }
    do
    {
      for (;;)
      {
        new PlayDrawerLayout.PlayDrawerPrimaryAction(paramContext.getString(j), k, m, 2131689647, i, new Runnable()
        {
          public final void run()
          {
            if (paramBoolean2) {
              FinskyDrawerLayout.access$500(FinskyDrawerLayout.this, 3);
            }
            for (;;)
            {
              FinskyDrawerLayout.this.mNavigationManager.goToAggregatedHome(FinskyDrawerLayout.this.mDfeToc);
              return;
              FinskyDrawerLayout.this.logMenuClickEvent(131);
            }
          }
        });
        i = 0;
      }
      j = 2131362321;
      k = 2130837722;
      m = 2130837723;
    } while ((paramInt != i) && ((paramBoolean1) && (paramInt == 2)));
    for (;;)
    {
      i = 0;
    }
  }
  
  private boolean isEnterprise()
  {
    return (this.mDfeToc != null) && (this.mDfeToc.mToc.themeId == 1);
  }
  
  private void logMenuClickEvent(int paramInt)
  {
    PageFragment localPageFragment = this.mNavigationManager.getActivePage();
    FinskyApp.get().getEventLogger().logClickEvent(paramInt, null, localPageFragment);
  }
  
  private PlayDrawerLayout.PlayDrawerPrimaryAction makeMyCollectionAction(final int paramInt, boolean paramBoolean)
  {
    boolean bool1 = true;
    int i = -1;
    final Context localContext = getContext();
    String str = CorpusResourceUtils.getCorpusMyCollectionDescription(paramInt);
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    boolean bool2;
    boolean bool3;
    label54:
    Runnable local20;
    int j;
    label112:
    int k;
    label156:
    int m;
    if ((paramInt == 3) && (this.mNavigationManager.getCurrentPageType() == 3))
    {
      bool2 = bool1;
      if (paramInt == 3) {
        break label217;
      }
      bool3 = bool1;
      local20 = new Runnable()
      {
        public final void run()
        {
          int i;
          switch (paramInt)
          {
          case 3: 
          case 5: 
          default: 
            i = 103;
          }
          for (;;)
          {
            FinskyDrawerLayout.this.logMenuClickEvent(i);
            switch (paramInt)
            {
            case 3: 
            case 5: 
            default: 
              FinskyDrawerLayout.this.mNavigationManager.goToMyDownloads(FinskyApp.get().mToc, false);
              return;
              i = 106;
              continue;
              i = 105;
              continue;
              i = 104;
              continue;
              i = 107;
            }
          }
          if (!IntentUtils.isConsumptionAppInstalled(localContext.getPackageManager(), paramInt))
          {
            FinskyDrawerLayout.this.mNavigationManager.showAppNeededDialog(paramInt);
            return;
          }
          Intent localIntent = IntentUtils.buildConsumptionAppLaunchIntent(localContext, paramInt, FinskyApp.get().getCurrentAccountName());
          localContext.startActivity(localIntent);
        }
      };
      switch (paramInt)
      {
      case 5: 
      default: 
        j = i;
        switch (paramInt)
        {
        case 5: 
        default: 
          k = i;
          if ((paramBoolean) && (bool3)) {
            i = 2130837721;
          }
          m = CorpusResourceUtils.getSecondaryColorResId(paramInt);
          if ((!paramBoolean) || (isEnterprise())) {}
          break;
        }
        break;
      }
    }
    for (;;)
    {
      return new PlayDrawerLayout.PlayDrawerPrimaryAction(str, j, k, i, m, bool2, false, bool1, false, local20);
      bool2 = false;
      break;
      label217:
      bool3 = false;
      break label54;
      j = 2130837730;
      break label112;
      j = 2130837733;
      break label112;
      j = 2130837739;
      break label112;
      j = 2130837735;
      break label112;
      j = 2130837737;
      break label112;
      if (CorpusResourceUtils.isEnterpriseTheme())
      {
        k = 2130837732;
        break label156;
      }
      k = 2130837731;
      break label156;
      k = 2130837734;
      break label156;
      k = 2130837740;
      break label156;
      k = 2130837736;
      break label156;
      k = 2130837738;
      break label156;
      bool1 = false;
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mNavigationManager.getActivePage();
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onAccountListToggleButtonClicked(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 284;; i = 283)
    {
      logMenuClickEvent(i);
      return;
    }
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mAccountManager.addOnAccountsUpdatedListener(this.mOnAccountsUpdateListener, null, false);
  }
  
  public final boolean onCurrentAccountClicked(boolean paramBoolean, DocV2 paramDocV2)
  {
    if (!paramBoolean) {}
    do
    {
      do
      {
        return false;
        if (paramDocV2 != null) {
          break;
        }
      } while (!this.mDfeToc.mToc.gplusSignupEnabled);
      GPlusDialogsHelper.showGPlusSignUpDialog(this.mNavigationManager.getActivePage().mFragmentManager, getContext());
      return false;
    } while (TextUtils.isEmpty(paramDocV2.detailsUrl));
    this.mNavigationManager.goToDocPage(paramDocV2.detailsUrl);
    PageFragment localPageFragment = this.mNavigationManager.getActivePage();
    FinskyApp.get().getEventLogger().logClickEvent(123, null, localPageFragment);
    return true;
  }
  
  protected void onDetachedFromWindow()
  {
    this.mAccountManager.removeOnAccountsUpdatedListener(this.mOnAccountsUpdateListener);
    super.onDetachedFromWindow();
  }
  
  public final void onDrawerClosed(View paramView)
  {
    super.onDrawerClosed(paramView);
    this.mMainActivity.exitDrawerOpenMode();
  }
  
  public final void onDrawerOpened(View paramView)
  {
    super.onDrawerOpened(paramView);
    ActionBarHelper localActionBarHelper = this.mMainActivity.getActionBarHelper();
    if ((localActionBarHelper.mToolbar == null) || (localActionBarHelper.mMainMenuUiElementNode == null)) {}
    for (;;)
    {
      if (DotNotificationUtils.shouldShowAccountCompletionDotNotification())
      {
        GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(127, null, null, this);
        FinskyApp.get().getEventLogger().logPathImpression$7d139cbf(299, localGenericUiElementNode);
      }
      this.mMainActivity.enterDrawerOpenMode();
      return;
      View localView = localActionBarHelper.mToolbar.getSearchView().findViewById(2131755724);
      if (localView != null) {
        localView.setVisibility(8);
      }
      PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.accountCompletionMainMenuDotTapCount.get(FinskyApp.get().getCurrentAccountName());
      if (((Integer)localSharedPreference.get()).intValue() == 0) {
        localSharedPreference.put(Integer.valueOf(1));
      }
    }
  }
  
  public final boolean onPrimaryActionClicked(PlayDrawerLayout.PlayDrawerPrimaryAction paramPlayDrawerPrimaryAction)
  {
    if (!paramPlayDrawerPrimaryAction.isActive) {
      paramPlayDrawerPrimaryAction.actionSelectedRunnable.run();
    }
    return true;
  }
  
  public final boolean onSecondaryAccountClicked(String paramString)
  {
    View localView = this.mMainActivity.findViewById(2131756157);
    if (localView != null) {
      ((ViewGroup)localView.getParent()).removeView(localView);
    }
    this.mMainActivity.switchToAccount(paramString);
    return true;
  }
  
  public final boolean onSecondaryActionClicked(PlayDrawerLayout.PlayDrawerSecondaryAction paramPlayDrawerSecondaryAction)
  {
    paramPlayDrawerSecondaryAction.actionSelectedRunnable.run();
    return true;
  }
  
  public final void refresh()
  {
    this.mDfeToc = FinskyApp.get().mToc;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    label154:
    label212:
    label351:
    label487:
    label505:
    boolean bool11;
    label279:
    boolean bool12;
    label517:
    label523:
    boolean bool13;
    label641:
    label750:
    String str1;
    Account[] arrayOfAccount1;
    PlayDrawerAdapter localPlayDrawerAdapter;
    int i;
    boolean bool1;
    if ((FinskyApp.get().getExperiments().isH20StoreEnabled()) && (this.mDfeToc != null))
    {
      Context localContext3 = getContext();
      int i7 = this.mNavigationManager.getCurrentPageType();
      if (this.mDfeToc != null)
      {
        boolean bool9;
        NavigationManager localNavigationManager;
        boolean bool15;
        ArrayList localArrayList3;
        int i8;
        final Toc.CorpusMetadata localCorpusMetadata2;
        int i9;
        int i10;
        String str9;
        int i11;
        if (this.mDfeToc.getCorpusList().size() == 1)
        {
          bool9 = true;
          localArrayList1.add(getHomePrimaryAction(localContext3, i7, bool9, true, isEnterprise()));
          if (this.mCurrentBackendId == 3) {
            localArrayList1.add(makeMyCollectionAction(3, true));
          }
          localNavigationManager = this.mNavigationManager;
          if ((localNavigationManager.mBackStack != null) && (!localNavigationManager.mBackStack.isEmpty())) {
            break label487;
          }
          if (!TextUtils.isEmpty(this.mDfeToc.mToc.entertainmentHomeUrl))
          {
            int i12 = CorpusResourceUtils.getColoredBackendDrawable(13);
            String str10 = localContext3.getString(2131362121);
            int i13 = CorpusResourceUtils.getSecondaryColorResId(13);
            if ((i7 != 1) || (this.mCurrentBackendId != 13)) {
              break label505;
            }
            bool15 = true;
            localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(str10, i12, i12, i13, bool15, new Runnable()
            {
              public final void run()
              {
                FinskyDrawerLayout.this.logMenuClickEvent(133);
                FinskyDrawerLayout.this.mNavigationManager.resetBackstackToAppsHome();
                NavigationManager localNavigationManager = FinskyDrawerLayout.this.mNavigationManager;
                DfeToc localDfeToc = FinskyDrawerLayout.this.mDfeToc;
                if (localNavigationManager.canNavigate())
                {
                  String str = localDfeToc.mToc.entertainmentHomeUrl;
                  if (!localNavigationManager.maybeSwitchTabs$3024efe(localDfeToc, 13))
                  {
                    localNavigationManager.clearInternal();
                    localNavigationManager.showPage(1, str, TabbedBrowseFragment.newInstance$30e04d94(str, localNavigationManager.mActivity.getString(2131362120), 13, localDfeToc, NavigationManager.useFixedTabs(localDfeToc)), false, new View[0]);
                  }
                }
              }
            }));
          }
          localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction());
          localArrayList3 = new ArrayList();
          List localList = this.mDfeToc.getCorpusList();
          i8 = 0;
          if (i8 >= localList.size()) {
            break label523;
          }
          localCorpusMetadata2 = (Toc.CorpusMetadata)localList.get(i8);
          switch (localCorpusMetadata2.backend)
          {
          case 3: 
          case 5: 
          default: 
            i9 = 0;
            if (i9 != 0)
            {
              i10 = CorpusResourceUtils.getColoredBackendDrawable(localCorpusMetadata2.backend);
              str9 = localCorpusMetadata2.name;
              i11 = CorpusResourceUtils.getSecondaryColorResId(localCorpusMetadata2.backend);
              if ((this.mCurrentBackendId != localCorpusMetadata2.backend) || ((i7 != 1) && (i7 != 2))) {
                break label517;
              }
            }
            break;
          }
        }
        for (boolean bool14 = true;; bool14 = false)
        {
          localArrayList3.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(str9, i10, i10, i11, bool14, new Runnable()
          {
            public final void run()
            {
              FinskyDrawerLayout.access$500(FinskyDrawerLayout.this, localCorpusMetadata2.backend);
              FinskyDrawerLayout.this.mNavigationManager.resetBackstackToAppsHome();
              FinskyDrawerLayout.this.mNavigationManager.redirectToCorpus(localCorpusMetadata2.landingUrl, localCorpusMetadata2.name, localCorpusMetadata2.backend, FinskyDrawerLayout.this.mDfeToc);
            }
          }));
          if (this.mCurrentBackendId == localCorpusMetadata2.backend) {
            localArrayList3.add(makeMyCollectionAction(localCorpusMetadata2.backend, true));
          }
          i8++;
          break label279;
          bool9 = false;
          break;
          break label154;
          bool15 = false;
          break label212;
          i9 = 1;
          break label351;
        }
        if (!localArrayList3.isEmpty())
        {
          localArrayList1.addAll(localArrayList3);
          localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction());
        }
        final boolean bool10 = DotNotificationUtils.shouldShowAccountCompletionDotNotification();
        String str6 = localContext3.getString(2131362759);
        if (i7 != 13) {
          break label1006;
        }
        bool11 = true;
        localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(str6, 2130837728, 2130837729, 2131689647, bool11, false, bool10, new Runnable()
        {
          public final void run()
          {
            if (bool10)
            {
              DotNotificationUtils.resolveMyAccountLinkNotificationTapped();
              GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(127, null, null, jdField_this);
              FinskyApp.get().getEventLogger().logClickEvent(299, null, localGenericUiElementNode);
            }
            for (;;)
            {
              FinskyDrawerLayout.this.mNavigationManager.goToMyAccount();
              return;
              FinskyDrawerLayout.this.logMenuClickEvent(127);
            }
          }
        }));
        String str7 = localContext3.getString(2131362323);
        if (i7 != 10) {
          break label1012;
        }
        bool12 = true;
        localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(str7, 2130837750, 2130837751, 2131689647, bool12, new Runnable()
        {
          public final void run()
          {
            FinskyDrawerLayout.this.logMenuClickEvent(108);
            FinskyDrawerLayout.this.mNavigationManager.resetBackstackToAppsHome();
            FinskyDrawerLayout.this.mNavigationManager.goToMyWishlist();
          }
        }));
        localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(localContext3.getString(2131362638), 2130837745, 2130837746, 2131689647, false, new Runnable()
        {
          public final void run()
          {
            FinskyDrawerLayout.this.logMenuClickEvent(109);
            String str = FinskyApp.get().getCurrentAccountName();
            if (str == null)
            {
              FinskyLog.wtf("Redeem chosen with no current account.", new Object[0]);
              return;
            }
            FinskyDrawerLayout.this.mNavigationManager.goRedeem(str, null);
          }
        }));
        if (!TextUtils.isEmpty(this.mDfeToc.mToc.socialHomeUrl))
        {
          String str8 = localContext3.getString(2131362762);
          if (i7 != 12) {
            break label1018;
          }
          bool13 = true;
          PlayDrawerLayout.PlayDrawerPrimaryAction localPlayDrawerPrimaryAction2 = new PlayDrawerLayout.PlayDrawerPrimaryAction(str8, 2130837743, 2130837744, 2131689647, bool13, new Runnable()
          {
            public final void run()
            {
              FinskyDrawerLayout.this.logMenuClickEvent(124);
              FinskyDrawerLayout.this.mNavigationManager.resetBackstackToAppsHome();
              FinskyDrawerLayout.this.mNavigationManager.goToSocialHome(FinskyDrawerLayout.this.mDfeToc.mToc.socialHomeUrl, FinskyDrawerLayout.this.mDfeToc);
            }
          });
          localArrayList1.add(localPlayDrawerPrimaryAction2);
        }
      }
      localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(localContext3.getString(2131362722), 2130837747, 2130837748, 2131689647, false, new Runnable()
      {
        public final void run()
        {
          FinskyDrawerLayout.this.logMenuClickEvent(111);
          FinskyDrawerLayout.this.mNavigationManager.goToSettings();
        }
      }));
      createCommonSecondaryActions(localArrayList2);
      str1 = FinskyApp.get().getCurrentAccountName();
      arrayOfAccount1 = AccountHandler.getAccounts(getContext());
      super.checkIsConfigured();
      localPlayDrawerAdapter = this.mDrawerAdapter;
      i = arrayOfAccount1.length;
      if (i != 0) {
        break label1586;
      }
      localPlayDrawerAdapter.mCurrentAccount = null;
      localPlayDrawerAdapter.mNonCurrentAccounts = new Account[0];
      if (i <= 0) {
        break label1705;
      }
      bool1 = true;
      label892:
      localPlayDrawerAdapter.mHasAccounts = bool1;
      localPlayDrawerAdapter.mPrimaryActions.clear();
      localPlayDrawerAdapter.mSecondaryActions.clear();
      localPlayDrawerAdapter.mPrimaryActions.addAll(localArrayList1);
      localPlayDrawerAdapter.mSecondaryActions.addAll(localArrayList2);
      localPlayDrawerAdapter.mDownloadSwitchConfig = null;
      localPlayDrawerAdapter.mShowDownloadOnlyToggle = false;
      if (!localPlayDrawerAdapter.mShowDownloadOnlyToggle) {
        break label1711;
      }
    }
    label1065:
    label1454:
    label1711:
    for (boolean bool2 = null.isChecked;; bool2 = false)
    {
      localPlayDrawerAdapter.mDownloadOnlyEnabled = bool2;
      localPlayDrawerAdapter.notifyDataSetChanged();
      this.mDockedActionView.setVisibility(8);
      this.mDockedActionView.setOnClickListener(null);
      this.mMainActivity.syncDrawerArrowDrawable();
      return;
      label1006:
      bool11 = false;
      break;
      label1012:
      bool12 = false;
      break label641;
      label1018:
      bool13 = false;
      break label750;
      Context localContext2;
      int i1;
      int i2;
      boolean bool3;
      label1084:
      int i3;
      label1114:
      final Toc.CorpusMetadata localCorpusMetadata1;
      Object localObject;
      boolean bool4;
      label1202:
      boolean bool7;
      label1272:
      final boolean bool5;
      String str3;
      if (this.mDfeToc != null)
      {
        localContext2 = getContext();
        i1 = this.mNavigationManager.getCurrentPageType();
        if ((this.mCurrentBackendId != 0) && (this.mCurrentBackendId != 9)) {
          break label1442;
        }
        i2 = 1;
        if (this.mDfeToc.getCorpusList().size() != 1) {
          break label1448;
        }
        bool3 = true;
        localArrayList1.add(getHomePrimaryAction(localContext2, i1, bool3, false, isEnterprise()));
        if (i2 == 0) {
          break label1454;
        }
        i3 = 3;
        PlayDrawerLayout.PlayDrawerPrimaryAction localPlayDrawerPrimaryAction1 = makeMyCollectionAction(i3, false);
        if (localPlayDrawerPrimaryAction1 != null) {
          localArrayList1.add(localPlayDrawerPrimaryAction1);
        }
        if ((i2 == 0) && (!bool3))
        {
          localCorpusMetadata1 = this.mDfeToc.getCorpus(this.mCurrentBackendId);
          if (localCorpusMetadata1 != null) {
            break label1463;
          }
          localObject = null;
          if (localObject != null) {
            localArrayList1.add(localObject);
          }
        }
        String str2 = localContext2.getString(2131362323);
        if (i1 != 10) {
          break label1568;
        }
        bool4 = true;
        localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(str2, 2130837750, 2130837751, 2131689647, bool4, new Runnable()
        {
          public final void run()
          {
            FinskyDrawerLayout.this.logMenuClickEvent(108);
            FinskyDrawerLayout.this.mNavigationManager.goToMyWishlist();
          }
        }));
        if (!TextUtils.isEmpty(this.mDfeToc.mToc.socialHomeUrl))
        {
          String str4 = localContext2.getString(2131362762);
          if (i1 != 12) {
            break label1574;
          }
          bool7 = true;
          localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(str4, 2130837743, 2130837744, 2131689647, bool7, new Runnable()
          {
            public final void run()
            {
              FinskyDrawerLayout.this.logMenuClickEvent(124);
              FinskyDrawerLayout.this.mNavigationManager.goToSocialHome(FinskyDrawerLayout.this.mDfeToc.mToc.socialHomeUrl, FinskyDrawerLayout.this.mDfeToc);
            }
          }));
        }
        bool5 = DotNotificationUtils.shouldShowAccountCompletionDotNotification();
        str3 = localContext2.getString(2131362759);
        if (i1 != 13) {
          break label1580;
        }
      }
      for (boolean bool6 = true;; bool6 = false)
      {
        localArrayList1.add(new PlayDrawerLayout.PlayDrawerPrimaryAction(str3, 2130837728, 2130837729, 2131689647, bool6, false, bool5, new Runnable()
        {
          public final void run()
          {
            if (bool5)
            {
              DotNotificationUtils.resolveMyAccountLinkNotificationTapped();
              GenericUiElementNode localGenericUiElementNode = new GenericUiElementNode(127, null, null, jdField_this);
              FinskyApp.get().getEventLogger().logClickEvent(299, null, localGenericUiElementNode);
              FinskyDrawerLayout.this.mNavigationManager.goToMyAccount();
              return;
            }
            FinskyDrawerLayout.this.logMenuClickEvent(127);
            FinskyDrawerLayout.this.mNavigationManager.goToMyAccount();
          }
        }));
        Context localContext1 = getContext();
        localArrayList2.add(new PlayDrawerLayout.PlayDrawerSecondaryAction(localContext1.getString(2131362638), new Runnable()
        {
          public final void run()
          {
            FinskyDrawerLayout.this.logMenuClickEvent(109);
            String str = FinskyApp.get().getCurrentAccountName();
            if (str == null)
            {
              FinskyLog.wtf("Redeem chosen with no current account.", new Object[0]);
              return;
            }
            FinskyDrawerLayout.this.mNavigationManager.goRedeem(str, null);
          }
        }));
        localArrayList2.add(new PlayDrawerLayout.PlayDrawerSecondaryAction(localContext1.getString(2131362722), new Runnable()
        {
          public final void run()
          {
            FinskyDrawerLayout.this.logMenuClickEvent(111);
            FinskyDrawerLayout.this.mNavigationManager.goToSettings();
          }
        }));
        createCommonSecondaryActions(localArrayList2);
        break;
        i2 = 0;
        break label1065;
        bool3 = false;
        break label1084;
        i3 = this.mCurrentBackendId;
        break label1114;
        label1463:
        String str5;
        label1481:
        int i4;
        int i5;
        int i6;
        if (!TextUtils.isEmpty(localCorpusMetadata1.shopName))
        {
          str5 = localCorpusMetadata1.shopName;
          i4 = CorpusResourceUtils.getDrawerShopDrawable(this.mCurrentBackendId);
          i5 = CorpusResourceUtils.getColoredBackendDrawable(this.mCurrentBackendId);
          i6 = CorpusResourceUtils.getSecondaryColorResId(this.mCurrentBackendId);
          if (i1 != 2) {
            break label1562;
          }
        }
        for (boolean bool8 = true;; bool8 = false)
        {
          localObject = new PlayDrawerLayout.PlayDrawerPrimaryAction(str5, i4, i5, i6, bool8, new Runnable()
          {
            public final void run()
            {
              FinskyDrawerLayout.access$500(FinskyDrawerLayout.this, localCorpusMetadata1.backend);
              FinskyDrawerLayout.this.mNavigationManager.goToCorpusHome(localCorpusMetadata1.landingUrl, localCorpusMetadata1.name, FinskyDrawerLayout.this.mCurrentBackendId, FinskyDrawerLayout.this.mDfeToc);
            }
          });
          break;
          str5 = localContext2.getString(2131362761);
          break label1481;
        }
        bool4 = false;
        break label1202;
        bool7 = false;
        break label1272;
      }
      label1586:
      localPlayDrawerAdapter.mNonCurrentAccounts = new Account[i - 1];
      int j = arrayOfAccount1.length;
      int k = 0;
      int m = 0;
      label1609:
      Account localAccount;
      if (k < j)
      {
        localAccount = arrayOfAccount1[k];
        if (!str1.equals(localAccount.name)) {
          break label1649;
        }
        localPlayDrawerAdapter.mCurrentAccount = localAccount;
      }
      for (;;)
      {
        k++;
        break label1609;
        break;
        label1649:
        if (m == i - 1)
        {
          PlayCommonLog.wtf("current account not found in accounts", new Object[0]);
          localPlayDrawerAdapter.mCurrentAccount = localAccount;
          break;
        }
        Account[] arrayOfAccount2 = localPlayDrawerAdapter.mNonCurrentAccounts;
        int n = m + 1;
        arrayOfAccount2[m] = localAccount;
        m = n;
      }
      bool1 = false;
      break label892;
    }
  }
  
  public final void updateCurrentBackendId(int paramInt)
  {
    if (this.mCurrentBackendId != paramInt)
    {
      this.mCurrentBackendId = paramInt;
      this.mRefreshHandler.post(this.mRefreshRunnable);
    }
  }
}



/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar

 * Qualified Name:     com.google.android.finsky.layout.play.FinskyDrawerLayout

 * JD-Core Version:    0.7.0.1

 */