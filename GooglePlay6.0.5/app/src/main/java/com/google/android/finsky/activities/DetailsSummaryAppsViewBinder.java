package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.DownloadProgressHelper;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.layout.DetailsSummaryDynamic;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.CertificateSet;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.ExpireLaunchUrlReceiver;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.receivers.Installer.InstallerProgressReport;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;
import com.google.android.finsky.utils.AppSupport;
import com.google.android.finsky.utils.AppSupport.RefundListener;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.PreregistrationHelper;
import com.google.android.finsky.utils.PreregistrationHelper.PreregistrationStatusListener;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayActionButton;
import java.util.List;

public final class DetailsSummaryAppsViewBinder
  extends DetailsSummaryViewBinder
  implements InstallerListener, PackageMonitorReceiver.PackageStatusListener, PreregistrationHelper.PreregistrationStatusListener
{
  private final AppStates mAppStates;
  final Installer mInstaller;
  private boolean mIsShowingUnregisterButton;
  private final Libraries mLibraries;
  private boolean mListenersAdded;
  private final PackageMonitorReceiver mPackageMonitorReceiver;
  private boolean mTrackPackageStatus;
  
  public DetailsSummaryAppsViewBinder(DfeToc paramDfeToc, Account paramAccount, PackageMonitorReceiver paramPackageMonitorReceiver, Installer paramInstaller, AppStates paramAppStates, Libraries paramLibraries)
  {
    super(paramDfeToc, paramAccount);
    this.mPackageMonitorReceiver = paramPackageMonitorReceiver;
    this.mInstaller = paramInstaller;
    this.mAppStates = paramAppStates;
    this.mLibraries = paramLibraries;
  }
  
  private void attachListeners()
  {
    if (this.mTrackPackageStatus)
    {
      this.mPackageMonitorReceiver.detach(this);
      this.mPackageMonitorReceiver.attach(this);
      if (!this.mListenersAdded)
      {
        this.mInstaller.addListener(this);
        FinskyApp.get().mPreregistrationHelper.mPreregistrationStatusListeners.add(this);
        this.mListenersAdded = true;
      }
    }
  }
  
  private void confirmRefundApp(String paramString1, String paramString2, boolean paramBoolean)
  {
    FragmentManagerImpl localFragmentManagerImpl = this.mContainerFragment.mFragmentManager;
    if (localFragmentManagerImpl.findFragmentByTag("refund_confirm") != null) {
      return;
    }
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessageId(2131362812).setPositiveId(2131362937).setNegativeId(2131362370);
    Bundle localBundle = new Bundle();
    localBundle.putString("package_name", paramString1);
    localBundle.putString("account_name", paramString2);
    localBundle.putBoolean("try_uninstall", paramBoolean);
    localBuilder.setCallback(this.mContainerFragment, 4, localBundle);
    localBuilder.build().show(localFragmentManagerImpl, "refund_confirm");
  }
  
  private void refreshByPackageName(String paramString)
  {
    if ((this.mDoc != null) && (this.mDoc.getAppDetails() != null) && (this.mDoc.getAppDetails().packageName.equals(paramString))) {
      syncDynamicSection();
    }
  }
  
  public final void bind(Document paramDocument, boolean paramBoolean, View... paramVarArgs)
  {
    super.bind(paramDocument, paramBoolean, paramVarArgs);
    attachListeners();
  }
  
  public final void init(Context paramContext, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, PageFragment paramPageFragment, boolean paramBoolean1, String paramString1, String paramString2, boolean paramBoolean2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super.init(paramContext, paramNavigationManager, paramBitmapLoader, paramPageFragment, paramBoolean1, paramString1, paramString2, paramBoolean2, paramPlayStoreUiElementNode);
    this.mTrackPackageStatus = paramBoolean1;
    attachListeners();
  }
  
  public final void onDestroyView()
  {
    this.mPackageMonitorReceiver.detach(this);
    if (this.mListenersAdded)
    {
      this.mInstaller.removeListener(this);
      FinskyApp.get().mPreregistrationHelper.mPreregistrationStatusListeners.remove(this);
      this.mListenersAdded = false;
    }
    super.onDestroyView();
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    if ((this.mDoc != null) && (paramString.equals(this.mDoc.getAppDetails().packageName)) && (this.mContainerFragment.isAdded())) {
      refresh();
    }
  }
  
  public final void onPackageAdded(String paramString)
  {
    refreshByPackageName(paramString);
  }
  
  public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
  
  public final void onPackageChanged(String paramString)
  {
    refreshByPackageName(paramString);
  }
  
  public final void onPackageFirstLaunch(String paramString) {}
  
  public final void onPackageRemoved(String paramString, boolean paramBoolean) {}
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    super.onPositiveClick(paramInt, paramBundle);
    switch (paramInt)
    {
    case 3: 
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Unexpected requestCode %d", arrayOfObject);
    case 2: 
    case 1: 
      String str3;
      do
      {
        return;
        str3 = paramBundle.getString("package_name");
      } while (this.mInstaller == null);
      this.mInstaller.uninstallAssetSilently(str3, false);
      refresh();
      return;
    }
    String str1 = paramBundle.getString("package_name");
    String str2 = paramBundle.getString("account_name");
    boolean bool = paramBundle.getBoolean("try_uninstall");
    AppSupport.silentRefund(this.mContainerFragment.mFragmentManager, str1, str2, bool, new AppSupport.RefundListener()
    {
      public final void onRefundComplete(boolean paramAnonymousBoolean)
      {
        DetailsSummaryAppsViewBinder.this.mIsPendingRefund = false;
        DetailsSummaryAppsViewBinder.this.refresh();
      }
      
      public final void onRefundStart()
      {
        DetailsSummaryAppsViewBinder.this.mIsPendingRefund = true;
        DetailsSummaryAppsViewBinder.this.refresh();
      }
    });
  }
  
  public final void onPreregistrationStatusChanged$44c588bf(String paramString)
  {
    if ((this.mDoc != null) && (this.mDoc.isPreregistration()) && (this.mDoc.mDocument.docid.equals(paramString))) {
      refresh();
    }
  }
  
  protected final void setupActionButtons(boolean paramBoolean)
  {
    PlayActionButton localPlayActionButton1 = (PlayActionButton)findViewById(2131755378);
    PlayActionButton localPlayActionButton2 = (PlayActionButton)findViewById(2131755374);
    PlayActionButton localPlayActionButton3 = (PlayActionButton)findViewById(2131755372);
    PlayActionButton localPlayActionButton4 = (PlayActionButton)findViewById(2131755373);
    PlayActionButton localPlayActionButton5 = (PlayActionButton)findViewById(2131755376);
    localPlayActionButton2.setVisibility(8);
    localPlayActionButton1.setVisibility(8);
    localPlayActionButton3.setVisibility(8);
    localPlayActionButton4.setVisibility(8);
    localPlayActionButton5.setVisibility(8);
    if ((this.mHideDynamicFeatures) || (paramBoolean)) {
      return;
    }
    final String str = this.mDoc.getAppDetails().packageName;
    final AppActionAnalyzer localAppActionAnalyzer = new AppActionAnalyzer(str, this.mAppStates, this.mLibraries);
    int i;
    int i1;
    label189:
    label212:
    Document localDocument;
    int m;
    label270:
    int k;
    Object localObject;
    label396:
    boolean bool2;
    label492:
    final PreregistrationHelper localPreregistrationHelper;
    if (localAppActionAnalyzer.isUninstallable())
    {
      final boolean bool3 = DocUtils.hasAutoRenewingSubscriptions(this.mLibraries, str);
      localPlayActionButton3.setVisibility(0);
      i = 0 + 1;
      int n = this.mDoc.mDocument.backendId;
      if (localAppActionAnalyzer.isRefundable)
      {
        i1 = 2131362641;
        localPlayActionButton3.configure(n, i1, new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            DetailsSummaryAppsViewBinder.this.mEventLogger.logClickEvent(215, null, DetailsSummaryAppsViewBinder.this.mParentNode);
            DetailsSummaryAppsViewBinder.access$100(DetailsSummaryAppsViewBinder.this, str, localAppActionAnalyzer.isRefundable, localAppActionAnalyzer.refundAccount, localAppActionAnalyzer.isInstalledSystemApp, localAppActionAnalyzer.isInstalledOwnedPackage, bool3);
          }
        });
        AccountLibrary localAccountLibrary = this.mLibraries.getAccountLibrary(this.mDetailsAccount);
        if (!localAppActionAnalyzer.hasUpdateAvailable(this.mDoc))
        {
          localDocument = this.mDoc;
          if ((localAppActionAnalyzer.isInstalled) && (!localAppActionAnalyzer.isInstalledSystemApp) && (!localAppActionAnalyzer.isInstalledOwnedPackage)) {
            break label791;
          }
          m = 0;
          if (m == 0) {}
        }
        else if ((LibraryUtils.isAvailable(this.mDoc, this.mDfeToc, localAccountLibrary)) && (!localAppActionAnalyzer.isDisabled))
        {
          localPlayActionButton4.setVisibility(0);
          i++;
          localPlayActionButton4.configure(this.mDoc.mDocument.backendId, 2131362817, this.mNavigationManager.getBuyImmediateClickListener(this.mDetailsAccount, this.mDoc, 1, null, this.mContinueUrl, 217, null));
        }
        if (i < 2)
        {
          localPlayActionButton2.setVisibility(0);
          k = -1;
          if (!localAppActionAnalyzer.isLaunchable) {
            break label897;
          }
          if (!localAppActionAnalyzer.isContinueLaunch) {
            break label868;
          }
          k = 2131362062;
          localObject = new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              DetailsSummaryAppsViewBinder.this.mEventLogger.logClickEvent(219, null, DetailsSummaryAppsViewBinder.this.mParentNode);
              DetailsSummaryAppsViewBinder.this.mNavigationManager.openItem(DetailsSummaryAppsViewBinder.this.mDetailsAccount, DetailsSummaryAppsViewBinder.this.mDoc, true);
              ExpireLaunchUrlReceiver.cancel(DetailsSummaryAppsViewBinder.this.mContext, DetailsSummaryAppsViewBinder.this.mDoc.mDocument.docid);
              FinskyApp.get().mInstallerDataStore.setContinueUrl(DetailsSummaryAppsViewBinder.this.mDoc.mDocument.docid, null);
            }
          };
          if (localPlayActionButton2.getVisibility() == 0)
          {
            i++;
            localPlayActionButton2.configure(this.mDoc.mDocument.backendId, k, (View.OnClickListener)localObject);
          }
        }
        if ((!localAppActionAnalyzer.isInstalled) && (!this.mDoc.isPreregistration()) && (LibraryUtils.isAvailable(this.mDoc, this.mDfeToc, this.mLibraries)))
        {
          Account localAccount = LibraryUtils.getOwnerWithCurrentAccount(this.mDoc, FinskyApp.get().mLibraries, this.mDetailsAccount);
          localPlayActionButton1.setVisibility(0);
          if (localAccount == null) {
            break label935;
          }
          bool2 = true;
          int j = getBuyButtonLoggingElementType$25dacd7(bool2);
          i++;
          localPlayActionButton1.configure(this.mDoc.mDocument.backendId, getBuyButtonString$3da8d3c4(bool2), this.mNavigationManager.getBuyImmediateClickListener(this.mDetailsAccount, this.mDoc, 1, null, this.mContinueUrl, j, null));
        }
        if ((i == 0) && (this.mDoc.isPreregistration()))
        {
          localPreregistrationHelper = FinskyApp.get().mPreregistrationHelper;
          if (!localPreregistrationHelper.isPreregistered(this.mDoc.mDocument.docid, this.mDfeApi.getAccount())) {
            break label941;
          }
          this.mIsShowingUnregisterButton = true;
          localPlayActionButton5.setVisibility(0);
          localPlayActionButton5.configure(this.mDoc.mDocument.backendId, 2131362598, new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              DetailsSummaryAppsViewBinder.this.mEventLogger.logClickEvent(296, null, DetailsSummaryAppsViewBinder.this.mParentNode);
              localPreregistrationHelper.processPreregistration(DetailsSummaryAppsViewBinder.this.mDoc, DetailsSummaryAppsViewBinder.this.mDfeApi, false, DetailsSummaryAppsViewBinder.this.mContainerFragment.mFragmentManager, DetailsSummaryAppsViewBinder.this.mContext);
            }
          });
        }
      }
    }
    for (;;)
    {
      syncButtonSectionVisibility();
      if (this.mButtonSection.getVisibility() != 0) {
        break;
      }
      ((TextView)this.mDynamicSection.findViewById(2131755408)).setVisibility(4);
      return;
      i1 = 2131362807;
      break label189;
      if ((!localAppActionAnalyzer.isUninstallable()) && (localAppActionAnalyzer.isActiveDeviceAdmin))
      {
        localPlayActionButton3.setVisibility(0);
        i = 0 + 1;
        localPlayActionButton3.configure(this.mDoc.mDocument.backendId, 2131362075, new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FragmentManagerImpl localFragmentManagerImpl = DetailsSummaryAppsViewBinder.this.mContainerFragment.mFragmentManager;
            if (localFragmentManagerImpl.findFragmentByTag("deactivate_dialog") != null) {
              return;
            }
            DetailsSummaryAppsViewBinder.this.mEventLogger.logClickEvent(216, null, DetailsSummaryAppsViewBinder.this.mParentNode);
            SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
            localBuilder.setMessageId(2131362076).setPositiveId(2131362418);
            localBuilder.build().show(localFragmentManagerImpl, "deactivate_dialog");
          }
        });
        break label212;
      }
      boolean bool1 = localAppActionAnalyzer.isRefundable;
      i = 0;
      if (!bool1) {
        break label212;
      }
      localPlayActionButton3.setVisibility(0);
      i = 0 + 1;
      localPlayActionButton3.configure(this.mDoc.mDocument.backendId, 2131362641, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          DetailsSummaryAppsViewBinder.this.mEventLogger.logClickEvent(214, null, DetailsSummaryAppsViewBinder.this.mParentNode);
          DetailsSummaryAppsViewBinder.access$200$69a4b3f6(DetailsSummaryAppsViewBinder.this, str, localAppActionAnalyzer.refundAccount);
        }
      });
      break label212;
      label791:
      CertificateSet[] arrayOfCertificateSet = localDocument.getAppDetails().certificateSet;
      if (AppActionAnalyzer.hasCertificateMatch(localAppActionAnalyzer.certificateHashes, arrayOfCertificateSet))
      {
        if ((!localDocument.getAppDetails().hasVersionCode) || (localDocument.getAppDetails().versionCode <= localAppActionAnalyzer.installedVersion))
        {
          m = 0;
          break label270;
        }
        if (!localDocument.needsCheckoutFlow(1))
        {
          m = 1;
          break label270;
        }
      }
      m = 0;
      break label270;
      label868:
      k = 2131362438;
      localObject = this.mNavigationManager.getOpenClickListener(this.mDoc, this.mDetailsAccount, this.mContainerFragment);
      break label396;
      label897:
      if (localAppActionAnalyzer.isDisabled)
      {
        localObject = new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            DetailsSummaryAppsViewBinder.this.mEventLogger.logClickEvent(220, null, DetailsSummaryAppsViewBinder.this.mParentNode);
            String str = DetailsSummaryAppsViewBinder.this.mDoc.mDocument.docid;
            DetailsSummaryAppsViewBinder.this.mContext.getPackageManager().setApplicationEnabledSetting(str, 1, 0);
          }
        };
        k = 2131362113;
        break label396;
      }
      localPlayActionButton2.setVisibility(8);
      localObject = null;
      break label396;
      label935:
      bool2 = false;
      break label492;
      label941:
      this.mIsShowingUnregisterButton = false;
      localPlayActionButton5.setVisibility(0);
      localPlayActionButton5.configure(this.mDoc.mDocument.backendId, 2131362590, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          DetailsSummaryAppsViewBinder.this.mEventLogger.logClickEvent(295, null, DetailsSummaryAppsViewBinder.this.mParentNode);
          localPreregistrationHelper.processPreregistration(DetailsSummaryAppsViewBinder.this.mDoc, DetailsSummaryAppsViewBinder.this.mDfeApi, true, DetailsSummaryAppsViewBinder.this.mContainerFragment.mFragmentManager, DetailsSummaryAppsViewBinder.this.mContext);
        }
      });
    }
  }
  
  protected final void showDynamicStatus(int paramInt)
  {
    super.showDynamicStatus(paramInt);
    this.mDynamicSection.findViewById(2131755407).setVisibility(4);
    setupActionButtons(true);
  }
  
  protected final void syncDynamicSection()
  {
    if (this.mDoc.mDocument.backendId != 3)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.mDoc.mDocument.backendId);
      arrayOfObject[1] = this.mDoc;
      FinskyLog.wtf("Unexpected doc backend %s", arrayOfObject);
      super.syncDynamicSection();
    }
    final String str;
    do
    {
      return;
      str = this.mDoc.getAppDetails().packageName;
    } while (TextUtils.isEmpty(str));
    final ViewGroup localViewGroup = (ViewGroup)this.mDynamicSection.findViewById(2131755407);
    Installer.InstallerProgressReport localInstallerProgressReport = this.mInstaller.getProgress(str);
    TextView localTextView3;
    switch (localInstallerProgressReport.installerState)
    {
    case 1: 
    case 2: 
    default: 
      localViewGroup.setVisibility(0);
      TextView localTextView1 = (TextView)localViewGroup.findViewById(2131755389);
      TextView localTextView2 = (TextView)localViewGroup.findViewById(2131755388);
      ProgressBar localProgressBar = (ProgressBar)localViewGroup.findViewById(2131755277);
      localTextView3 = (TextView)findViewById(2131755401);
      DownloadProgressHelper.configureDownloadProgressUi(this.mContext, localInstallerProgressReport, localTextView1, localTextView2, localProgressBar);
      if (localInstallerProgressReport.installerState != 2) {
        break;
      }
    }
    for (int i = 8;; i = 0)
    {
      localTextView3.setVisibility(i);
      ((ImageView)localViewGroup.findViewById(2131755387)).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          DetailsSummaryAppsViewBinder.this.mInstaller.cancel(str);
          localViewGroup.setVisibility(4);
        }
      });
      ((TextView)findViewById(2131755262)).setSelected(false);
      setupActionButtons(true);
      return;
      showDynamicStatus(2131362250);
      return;
      showDynamicStatus(2131362815);
      return;
      localViewGroup.setVisibility(4);
      super.syncDynamicSection();
      return;
    }
  }
  
  protected final void updateButtonActionStyle()
  {
    super.updateButtonActionStyle();
    if (this.mIsShowingUnregisterButton) {
      ((PlayActionButton)findViewById(2131755376)).setActionStyle(2);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.DetailsSummaryAppsViewBinder
 * JD-Core Version:    0.7.0.1
 */