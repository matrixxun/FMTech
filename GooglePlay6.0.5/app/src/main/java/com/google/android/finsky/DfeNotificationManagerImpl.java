package com.google.android.finsky;

import android.accounts.Account;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeNotificationManager;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.billing.iab.MarketBillingService;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.AccountsProvider;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.protos.AckNotificationResponse;
import com.google.android.finsky.protos.AndroidAppDeliveryData;
import com.google.android.finsky.protos.AndroidAppNotificationData;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.InAppNotificationData;
import com.google.android.finsky.protos.LibraryDirtyData;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryMutation;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryUpdate;
import com.google.android.finsky.protos.Notification;
import com.google.android.finsky.protos.PurchaseDeclinedData;
import com.google.android.finsky.protos.PurchaseRemovalData;
import com.google.android.finsky.protos.UserNotificationData;
import com.google.android.finsky.protos.UserSettingDirtyData;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.ExternalReferrer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.StoreTypeValidator;
import com.google.android.finsky.utils.UserSettingsCache;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DfeNotificationManagerImpl
  implements DfeNotificationManager
{
  private final AccountsProvider mAccounts;
  final AppStates mAppStates;
  final Context mContext;
  final List<String> mHandledNotifications = new ArrayList();
  final Installer mInstaller;
  final LibraryReplicators mLibraryReplicators;
  final Notifier mNotifier;
  final List<String> mPendingAcks = new ArrayList();
  
  public DfeNotificationManagerImpl(Context paramContext, Installer paramInstaller, Notifier paramNotifier, AppStates paramAppStates, LibraryReplicators paramLibraryReplicators, AccountsProvider paramAccountsProvider)
  {
    this.mInstaller = paramInstaller;
    this.mNotifier = paramNotifier;
    this.mContext = paramContext;
    this.mAppStates = paramAppStates;
    this.mLibraryReplicators = paramLibraryReplicators;
    this.mAccounts = paramAccountsProvider;
    loadPendingAcks();
  }
  
  private void ackNotification(final String paramString)
  {
    FinskyApp.get().getDfeApi(null).ackNotification(paramString, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramString;
        FinskyLog.d("Error acking notification [%s]", arrayOfObject);
      }
    });
  }
  
  private void ackPendingNotifications(String paramString)
  {
    ackNotification(paramString);
    Iterator localIterator = this.mPendingAcks.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!str.equals(paramString)) {
        ackNotification(str);
      }
    }
  }
  
  private void loadPendingAcks()
  {
    String str = (String)FinskyPreferences.dfeNotificationPendingAcks.get();
    if (!TextUtils.isEmpty(str))
    {
      String[] arrayOfString = str.split(",");
      for (int i = 0; i < arrayOfString.length; i++)
      {
        this.mPendingAcks.add(arrayOfString[i]);
        this.mHandledNotifications.add(arrayOfString[i]);
      }
    }
  }
  
  final void handleNotification(final Notification paramNotification)
  {
    final String str1 = paramNotification.notificationId;
    if (this.mHandledNotifications.contains(str1))
    {
      FinskyLog.d("Notification [%s] ignored, already handled.", new Object[] { str1 });
      ackPendingNotifications(str1);
      return;
    }
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(paramNotification.notificationType);
    arrayOfObject1[1] = str1;
    FinskyLog.d("Handling notification type=[%s], id=[%s]", arrayOfObject1);
    Runnable local2 = new Runnable()
    {
      public final void run()
      {
        switch (paramNotification.notificationType)
        {
        case 7: 
        default: 
          Object[] arrayOfObject7 = new Object[1];
          arrayOfObject7[0] = Integer.valueOf(paramNotification.notificationType);
          FinskyLog.e("Unhandled notification type [%s]", arrayOfObject7);
        }
        for (;;)
        {
          DfeNotificationManagerImpl.this.mHandledNotifications.add(str1);
          while (DfeNotificationManagerImpl.this.mPendingAcks.size() >= 10) {
            DfeNotificationManagerImpl.this.mPendingAcks.remove(0);
          }
          DfeNotificationManagerImpl localDfeNotificationManagerImpl6 = DfeNotificationManagerImpl.this;
          Notification localNotification6 = paramNotification;
          AndroidAppNotificationData localAndroidAppNotificationData = localNotification6.appData;
          if (localNotification6.appData == null)
          {
            FinskyLog.d("Ignoring PurchaseDeliveryNotification because AppData was null.", new Object[0]);
          }
          else if (localNotification6.appDeliveryData == null)
          {
            FinskyLog.d("Ignoring PurchaseDeliveryNotification because delivery data was null", new Object[0]);
          }
          else
          {
            String str4 = localNotification6.docid.backendDocid;
            if (!localNotification6.appDeliveryData.serverInitiated)
            {
              FinskyLog.d("Ignoring PurchaseDeliveryNotification with !server_initiated: pkg=%s", new Object[] { str4 });
            }
            else
            {
              PackageStateRepository.PackageState localPackageState2 = localDfeNotificationManagerImpl6.mAppStates.mPackageManager.get(str4);
              if (localPackageState2 != null) {}
              for (int j = localPackageState2.installedVersion;; j = -1)
              {
                if (j < localAndroidAppNotificationData.versionCode) {
                  break label398;
                }
                Object[] arrayOfObject6 = new Object[3];
                arrayOfObject6[0] = str4;
                arrayOfObject6[1] = Integer.valueOf(localAndroidAppNotificationData.versionCode);
                arrayOfObject6[2] = Integer.valueOf(j);
                FinskyLog.d("Skip remote install of %s because %d is not newer than %d", arrayOfObject6);
                PlayStore.AppData localAppData3 = new PlayStore.AppData();
                localAppData3.version = localAndroidAppNotificationData.versionCode;
                localAppData3.hasVersion = true;
                if (j >= 0)
                {
                  localAppData3.oldVersion = j;
                  localAppData3.hasOldVersion = true;
                }
                if (localPackageState2 != null)
                {
                  localAppData3.systemApp = localPackageState2.isSystemApp;
                  localAppData3.hasSystemApp = true;
                }
                FinskyApp.get().getEventLogger().logBackgroundEvent(112, str4, "older-version", 0, null, localAppData3);
                break;
              }
              label398:
              String str5 = localNotification6.userEmail;
              PlayStore.AppData localAppData2 = new PlayStore.AppData();
              localAppData2.version = localAndroidAppNotificationData.versionCode;
              localAppData2.hasVersion = true;
              FinskyApp.get().getEventLogger().logBackgroundEvent(201, str4, null, 0, null, localAppData2);
              if ((FinskyApp.get().getExperiments().isEnabled(12604059L)) && (!TextUtils.isEmpty(localAndroidAppNotificationData.installReferrer)))
              {
                FinskyLog.d("Capturing referrer for %s from notification", new Object[] { str4 });
                ExternalReferrer.saveExternalReferrer(localAndroidAppNotificationData.installReferrer, localNotification6.docid);
              }
              localDfeNotificationManagerImpl6.mInstaller.requestInstall(str4, localAndroidAppNotificationData.versionCode, str5, localNotification6.docTitle, false, "tickle", 2, 0);
              continue;
              DfeNotificationManagerImpl localDfeNotificationManagerImpl5 = DfeNotificationManagerImpl.this;
              Notification localNotification5 = paramNotification;
              String str2 = localNotification5.docid.backendDocid;
              boolean bool;
              label578:
              String str3;
              if ((localNotification5.purchaseRemovalData != null) && (localNotification5.purchaseRemovalData.malicious))
              {
                bool = true;
                str3 = localNotification5.docTitle;
                Object[] arrayOfObject5 = new Object[2];
                arrayOfObject5[0] = str2;
                arrayOfObject5[1] = Boolean.valueOf(bool);
                FinskyLog.d("Removing package '%s'. Malicious='%s'", arrayOfObject5);
                PackageStateRepository.PackageState localPackageState1 = localDfeNotificationManagerImpl5.mAppStates.mPackageManager.get(str2);
                PlayStore.AppData localAppData1 = null;
                if (localPackageState1 != null)
                {
                  localAppData1 = new PlayStore.AppData();
                  localAppData1.oldVersion = localPackageState1.installedVersion;
                  localAppData1.hasOldVersion = true;
                  localAppData1.systemApp = localPackageState1.isSystemApp;
                  localAppData1.hasSystemApp = true;
                }
                FinskyApp.get().getEventLogger().logBackgroundEvent(202, str2, null, 0, null, localAppData1);
                if (localPackageState1 != null)
                {
                  if (!bool) {
                    break label746;
                  }
                  localDfeNotificationManagerImpl5.mNotifier.showMaliciousAssetRemovedMessage$16da05f7(str3);
                }
              }
              for (;;)
              {
                if (!bool) {
                  break label763;
                }
                localDfeNotificationManagerImpl5.mInstaller.uninstallPackagesByUid$505cbf4b(str2);
                break;
                bool = false;
                break label578;
                label746:
                localDfeNotificationManagerImpl5.mNotifier.showNormalAssetRemovedMessage(str3, str2);
              }
              label763:
              localDfeNotificationManagerImpl5.mInstaller.uninstallAssetSilently(str2, true);
              continue;
              Notification localNotification4 = paramNotification;
              int i = localNotification4.purchaseDeclinedData.reason;
              String str1 = localNotification4.docid.backendDocid;
              Object[] arrayOfObject4 = new Object[2];
              arrayOfObject4[0] = str1;
              arrayOfObject4[1] = Integer.valueOf(i);
              FinskyLog.d("Received PURCHASE_DECLINED tickle for %s reason=%d", arrayOfObject4);
              FinskyApp.get().getEventLogger().logBackgroundEvent(200, str1, String.valueOf(i), 0, null, null);
              continue;
              DfeNotificationManagerImpl localDfeNotificationManagerImpl4 = DfeNotificationManagerImpl.this;
              UserNotificationData localUserNotificationData = paramNotification.userNotificationData;
              localDfeNotificationManagerImpl4.mNotifier.showMessage$14e1ec6d(localUserNotificationData.notificationTitle, localUserNotificationData.notificationText);
              continue;
              DfeNotificationManagerImpl localDfeNotificationManagerImpl3 = DfeNotificationManagerImpl.this;
              Notification localNotification3 = paramNotification;
              MarketBillingService.sendNotify(localDfeNotificationManagerImpl3.mContext, localNotification3.docid.backendDocid, localNotification3.inAppNotificationData.inAppNotificationId);
              continue;
              DfeNotificationManagerImpl localDfeNotificationManagerImpl2 = DfeNotificationManagerImpl.this;
              Notification localNotification2 = paramNotification;
              if (localNotification2.libraryDirtyData == null)
              {
                Object[] arrayOfObject3 = new Object[1];
                arrayOfObject3[0] = localNotification2.notificationId;
                FinskyLog.e("Received LibraryDirty notification without LibraryDirtyData: id=%s", arrayOfObject3);
              }
              else
              {
                Account localAccount2 = AccountHandler.findAccount(localNotification2.userEmail, localDfeNotificationManagerImpl2.mContext);
                if (localAccount2 == null)
                {
                  Object[] arrayOfObject2 = new Object[2];
                  arrayOfObject2[0] = localNotification2.notificationId;
                  arrayOfObject2[1] = FinskyLog.scrubPii(localNotification2.userEmail);
                  FinskyLog.e("Received LibraryDirty notification for invalid account: id=%s, account=%s", arrayOfObject2);
                }
                else
                {
                  String[] arrayOfString = new String[1];
                  if (localNotification2.libraryDirtyData.libraryId.length() > 0) {
                    arrayOfString[0] = localNotification2.libraryDirtyData.libraryId;
                  }
                  for (;;)
                  {
                    localDfeNotificationManagerImpl2.mLibraryReplicators.replicateAccount(localAccount2, arrayOfString, null, "notification-" + localNotification2.notificationId);
                    break;
                    arrayOfString[0] = AccountLibrary.getLibraryIdFromBackend(localNotification2.libraryDirtyData.backend);
                  }
                  DfeNotificationManagerImpl localDfeNotificationManagerImpl1 = DfeNotificationManagerImpl.this;
                  Notification localNotification1 = paramNotification;
                  if (FinskyApp.get().getExperiments().isEnabled(12604495L))
                  {
                    FinskyLog.w("Ignoring UserSettingsDirty notification due to disabled user settings.", new Object[0]);
                  }
                  else
                  {
                    Account localAccount1 = AccountHandler.findAccount(localNotification1.userEmail, localDfeNotificationManagerImpl1.mContext);
                    if (localAccount1 == null)
                    {
                      Object[] arrayOfObject1 = new Object[2];
                      arrayOfObject1[0] = localNotification1.notificationId;
                      arrayOfObject1[1] = FinskyLog.scrubPii(localNotification1.userEmail);
                      FinskyLog.e("UserSettingsDirty notification has invalid account: id=%s, account=%s", arrayOfObject1);
                    }
                    else
                    {
                      if (localNotification1.userSettingDirtyData != null) {
                        UserSettingsCache.updateConsistencyToken(localAccount1.name, localNotification1.userSettingDirtyData.consistencyTokens);
                      }
                      UserSettingsCache.updateUserSettings(localAccount1.name);
                    }
                  }
                }
              }
            }
          }
        }
        DfeNotificationManagerImpl.this.mPendingAcks.add(str1);
        DfeNotificationManagerImpl.access$1000(DfeNotificationManagerImpl.this);
        DfeNotificationManagerImpl.this.ackPendingNotifications(str1);
      }
    };
    Account localAccount;
    LibraryUpdateProto.LibraryUpdate localLibraryUpdate;
    int j;
    int k;
    if (paramNotification.libraryUpdate != null)
    {
      String str2 = paramNotification.userEmail;
      localAccount = this.mAccounts.getAccount(str2);
      if (localAccount == null) {
        break label281;
      }
      FinskyLog.d("Processing notification library update.", new Object[0]);
      localLibraryUpdate = paramNotification.libraryUpdate;
      LibraryUpdateProto.LibraryMutation[] arrayOfLibraryMutation = localLibraryUpdate.mutation;
      int i = arrayOfLibraryMutation.length;
      j = 0;
      if (j >= i) {
        break label216;
      }
      LibraryUpdateProto.LibraryMutation localLibraryMutation = arrayOfLibraryMutation[j];
      if (!DocUtils.isInAppDocid(localLibraryMutation.docid)) {
        break label210;
      }
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localLibraryMutation.docid.backendDocid;
      FinskyLog.d("Encountered IAB item in notification: %s.", arrayOfObject2);
      k = 1;
      label188:
      if (k == 0) {
        break label222;
      }
      FinskyLog.wtf("Ignoring notification LibraryUpdate with IAB mutations.", new Object[0]);
    }
    for (;;)
    {
      local2.run();
      return;
      label210:
      j++;
      break;
      label216:
      k = 0;
      break label188;
      label222:
      this.mLibraryReplicators.applyLibraryUpdates(localAccount, "notification (type=[" + paramNotification.notificationType + "],id=[" + paramNotification.notificationId + "])", local2, new LibraryUpdateProto.LibraryUpdate[] { localLibraryUpdate });
      return;
      label281:
      FinskyLog.d("Could not process library update for unknown account.", new Object[0]);
    }
  }
  
  public final void processNotification(final Notification paramNotification)
  {
    if (!StoreTypeValidator.isValid(this.mContext))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramNotification.notificationType);
      FinskyLog.d("Dropping notification type=%d because store type invalid", arrayOfObject);
      return;
    }
    if ((Looper.myLooper() == Looper.getMainLooper()) && (this.mAppStates.mStateStore.isLoaded()))
    {
      handleNotification(paramNotification);
      return;
    }
    this.mAppStates.load(new Runnable()
    {
      public final void run()
      {
        DfeNotificationManagerImpl.this.handleNotification(paramNotification);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.DfeNotificationManagerImpl
 * JD-Core Version:    0.7.0.1
 */