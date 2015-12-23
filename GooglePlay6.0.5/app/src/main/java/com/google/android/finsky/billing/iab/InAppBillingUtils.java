package com.google.android.finsky.billing.iab;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryInAppDetails;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryMutation;
import com.google.android.finsky.protos.LibraryUpdateProto.LibrarySubscriptionDetails;
import com.google.android.finsky.protos.LibraryUpdateProto.LibraryUpdate;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Iterator;
import java.util.List;

public final class InAppBillingUtils
{
  public static String buildDocidStr(String paramString1, String paramString2, String paramString3)
  {
    return paramString2 + ":" + paramString3 + ":" + paramString1;
  }
  
  /* Error */
  public static android.content.Intent createResultIntent(Context paramContext, com.google.android.finsky.billing.lightpurchase.PurchaseParams paramPurchaseParams, Bundle paramBundle, ResponseCode paramResponseCode)
  {
    // Byte code:
    //   0: new 32	android/content/Intent
    //   3: dup
    //   4: invokespecial 33	android/content/Intent:<init>	()V
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: aconst_null
    //   13: astore 6
    //   15: aload_2
    //   16: ifnull +60 -> 76
    //   19: aload_2
    //   20: ldc 35
    //   22: invokevirtual 41	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   25: astore 5
    //   27: aload_2
    //   28: ldc 43
    //   30: invokevirtual 41	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   33: astore 18
    //   35: aload 5
    //   37: ifnull +28 -> 65
    //   40: aload 18
    //   42: ifnull +23 -> 65
    //   45: aload 4
    //   47: ldc 45
    //   49: aload 5
    //   51: invokevirtual 49	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   54: pop
    //   55: aload 4
    //   57: ldc 51
    //   59: aload 18
    //   61: invokevirtual 49	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   64: pop
    //   65: aload_2
    //   66: ldc 53
    //   68: invokestatic 59	com/google/android/finsky/utils/ParcelableProto:getProtoFromBundle	(Landroid/os/Bundle;Ljava/lang/String;)Lcom/google/protobuf/nano/MessageNano;
    //   71: checkcast 61	com/google/android/finsky/protos/Buy$Money
    //   74: astore 6
    //   76: aload 4
    //   78: ldc 63
    //   80: aload_3
    //   81: invokevirtual 69	com/google/android/finsky/billing/iab/InAppBillingUtils$ResponseCode:ordinal	()I
    //   84: invokevirtual 72	android/content/Intent:putExtra	(Ljava/lang/String;I)Landroid/content/Intent;
    //   87: pop
    //   88: aload 5
    //   90: invokestatic 78	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   93: ifne +8 -> 101
    //   96: aload 6
    //   98: ifnonnull +6 -> 104
    //   101: aload 4
    //   103: areturn
    //   104: invokestatic 84	com/google/android/finsky/FinskyApp:get	()Lcom/google/android/finsky/FinskyApp;
    //   107: invokevirtual 88	com/google/android/finsky/FinskyApp:getExperiments	()Lcom/google/android/finsky/experiments/FinskyExperiments;
    //   110: ldc2_w 89
    //   113: invokevirtual 96	com/google/android/finsky/experiments/FinskyExperiments:isEnabled	(J)Z
    //   116: ifeq -15 -> 101
    //   119: aload_1
    //   120: getfield 102	com/google/android/finsky/billing/lightpurchase/PurchaseParams:docid	Lcom/google/android/finsky/protos/Common$Docid;
    //   123: getfield 108	com/google/android/finsky/protos/Common$Docid:type	I
    //   126: bipush 11
    //   128: if_icmpne -27 -> 101
    //   131: new 110	org/json/JSONObject
    //   134: dup
    //   135: aload 5
    //   137: invokespecial 113	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   140: astore 8
    //   142: aload 8
    //   144: ldc 115
    //   146: invokevirtual 116	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   149: astore 11
    //   151: aload 8
    //   153: ldc 118
    //   155: invokevirtual 116	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   158: astore 12
    //   160: new 120	com/google/android/gms/ads/measurement/GmpMeasurement$PlayStoreInAppPurchase
    //   163: dup
    //   164: aload 11
    //   166: aload 12
    //   168: ldc 122
    //   170: aload 6
    //   172: getfield 126	com/google/android/finsky/protos/Buy$Money:micros	J
    //   175: aload 6
    //   177: getfield 126	com/google/android/finsky/protos/Buy$Money:micros	J
    //   180: aload 6
    //   182: getfield 130	com/google/android/finsky/protos/Buy$Money:currencyCode	Ljava/lang/String;
    //   185: invokespecial 133	com/google/android/gms/ads/measurement/GmpMeasurement$PlayStoreInAppPurchase:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJLjava/lang/String;)V
    //   188: astore 13
    //   190: aload 13
    //   192: ldc 135
    //   194: invokestatic 141	com/google/android/gms/common/internal/zzx:zzb	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   197: pop
    //   198: aload_0
    //   199: invokestatic 147	com/google/android/gms/ads/measurement/GmpMeasurement$zza:zzZ	(Landroid/content/Context;)Lcom/google/android/gms/internal/zzjz;
    //   202: aload 13
    //   204: getfield 151	com/google/android/gms/ads/measurement/GmpMeasurement$PlayStoreInAppPurchase:zzNm	Landroid/os/Bundle;
    //   207: invokeinterface 157 2 0
    //   212: aconst_null
    //   213: invokestatic 161	com/google/android/finsky/billing/iab/InAppBillingUtils:logGmpMeasurementEvent	(Ljava/lang/Throwable;)V
    //   216: aload 4
    //   218: areturn
    //   219: astore 9
    //   221: iconst_1
    //   222: anewarray 4	java/lang/Object
    //   225: astore 10
    //   227: aload 10
    //   229: iconst_0
    //   230: aload 5
    //   232: invokestatic 166	com/google/android/finsky/utils/FinskyLog:scrubPii	(Ljava/lang/String;)Ljava/lang/String;
    //   235: aastore
    //   236: ldc 168
    //   238: aload 10
    //   240: invokestatic 172	com/google/android/finsky/utils/FinskyLog:wtf	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   243: aload 9
    //   245: invokestatic 161	com/google/android/finsky/billing/iab/InAppBillingUtils:logGmpMeasurementEvent	(Ljava/lang/Throwable;)V
    //   248: aload 4
    //   250: areturn
    //   251: astore 17
    //   253: ldc 174
    //   255: aload 17
    //   257: invokestatic 180	com/google/android/gms/ads/internal/util/client/zzb:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   260: goto -48 -> 212
    //   263: astore 14
    //   265: iconst_1
    //   266: anewarray 4	java/lang/Object
    //   269: astore 15
    //   271: aload 15
    //   273: iconst_0
    //   274: aload 14
    //   276: invokevirtual 183	com/google/android/gms/common/GooglePlayServicesNotAvailableException:getMessage	()Ljava/lang/String;
    //   279: aastore
    //   280: ldc 185
    //   282: aload 15
    //   284: invokestatic 187	com/google/android/finsky/utils/FinskyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   287: aload 14
    //   289: invokestatic 161	com/google/android/finsky/billing/iab/InAppBillingUtils:logGmpMeasurementEvent	(Ljava/lang/Throwable;)V
    //   292: aload 4
    //   294: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	295	0	paramContext	Context
    //   0	295	1	paramPurchaseParams	com.google.android.finsky.billing.lightpurchase.PurchaseParams
    //   0	295	2	paramBundle	Bundle
    //   0	295	3	paramResponseCode	ResponseCode
    //   7	286	4	localIntent	android.content.Intent
    //   10	221	5	str1	String
    //   13	168	6	localMoney	com.google.android.finsky.protos.Buy.Money
    //   140	12	8	localJSONObject	org.json.JSONObject
    //   219	25	9	localJSONException	org.json.JSONException
    //   225	14	10	arrayOfObject1	Object[]
    //   149	16	11	str2	String
    //   158	9	12	str3	String
    //   188	15	13	localPlayStoreInAppPurchase	com.google.android.gms.ads.measurement.GmpMeasurement.PlayStoreInAppPurchase
    //   263	25	14	localGooglePlayServicesNotAvailableException	com.google.android.gms.common.GooglePlayServicesNotAvailableException
    //   269	14	15	arrayOfObject2	Object[]
    //   251	5	17	localRemoteException	android.os.RemoteException
    //   33	27	18	str4	String
    // Exception table:
    //   from	to	target	type
    //   131	160	219	org/json/JSONException
    //   198	212	251	android/os/RemoteException
    //   190	198	263	com/google/android/gms/common/GooglePlayServicesNotAvailableException
    //   198	212	263	com/google/android/gms/common/GooglePlayServicesNotAvailableException
    //   253	260	263	com/google/android/gms/common/GooglePlayServicesNotAvailableException
  }
  
  public static Bundle extractExtraPurchaseData(LibraryUpdateProto.LibraryUpdate[] paramArrayOfLibraryUpdate, Common.Docid paramDocid)
  {
    if (paramArrayOfLibraryUpdate == null) {}
    for (;;)
    {
      return null;
      int i = paramArrayOfLibraryUpdate.length;
      for (int j = 0; j < i; j++)
      {
        LibraryUpdateProto.LibraryUpdate localLibraryUpdate = paramArrayOfLibraryUpdate[j];
        if ((localLibraryUpdate != null) && (localLibraryUpdate.mutation != null)) {
          for (LibraryUpdateProto.LibraryMutation localLibraryMutation : localLibraryUpdate.mutation) {
            if ((localLibraryMutation != null) && (localLibraryMutation.docid.type == 11) && (TextUtils.equals(localLibraryMutation.docid.backendDocid, paramDocid.backendDocid)) && (localLibraryMutation.inAppDetails != null))
            {
              LibraryUpdateProto.LibraryInAppDetails localLibraryInAppDetails = localLibraryMutation.inAppDetails;
              if ((localLibraryInAppDetails.hasSignature) && (localLibraryInAppDetails.hasSignedPurchaseData))
              {
                Bundle localBundle2 = new Bundle();
                localBundle2.putString("inapp_signed_purchase_data", localLibraryInAppDetails.signedPurchaseData);
                localBundle2.putString("inapp_purchase_data_signature", localLibraryInAppDetails.signature);
                return localBundle2;
              }
            }
            else if ((localLibraryMutation != null) && (localLibraryMutation.docid.type == 15) && (TextUtils.equals(localLibraryMutation.docid.backendDocid, paramDocid.backendDocid)) && (localLibraryMutation.subscriptionDetails != null))
            {
              LibraryUpdateProto.LibrarySubscriptionDetails localLibrarySubscriptionDetails = localLibraryMutation.subscriptionDetails;
              if ((localLibrarySubscriptionDetails.hasSignature) && (localLibrarySubscriptionDetails.hasSignedPurchaseData))
              {
                Bundle localBundle1 = new Bundle();
                localBundle1.putString("inapp_signed_purchase_data", localLibrarySubscriptionDetails.signedPurchaseData);
                localBundle1.putString("inapp_purchase_data_signature", localLibrarySubscriptionDetails.signature);
                return localBundle1;
              }
            }
          }
        }
      }
    }
  }
  
  static PackageInfo getPackageInfoWithSignatures(Context paramContext, String paramString)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 64);
      return localPackageInfo;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      FinskyLog.w("Cannot find package: %s", new Object[] { paramString });
    }
    return null;
  }
  
  public static Account getPreferredAccount(String paramString, Context paramContext)
  {
    AppStates localAppStates = FinskyApp.get().mAppStates;
    localAppStates.mStateStore.load();
    AppStates.AppState localAppState = localAppStates.getApp(paramString);
    if ((localAppState != null) && (localAppState.packageManagerState != null))
    {
      Libraries localLibraries = FinskyApp.get().mLibraries;
      localLibraries.blockingLoad();
      List localList = localLibraries.getAppOwners(paramString, localAppState.packageManagerState.certificateHashes);
      Account localAccount2;
      if (localList.size() > 0) {
        if (localAppState.installerData != null)
        {
          String str = localAppState.installerData.accountName;
          Iterator localIterator = localList.iterator();
          while (localIterator.hasNext())
          {
            Account localAccount3 = (Account)localIterator.next();
            if (TextUtils.equals(localAccount3.name, str))
            {
              Object[] arrayOfObject3 = new Object[2];
              arrayOfObject3[0] = localAppState.packageName;
              arrayOfObject3[1] = FinskyLog.scrubPii(str);
              FinskyLog.d("%s: Account determined from installer data - %s", arrayOfObject3);
              localAccount2 = localAccount3;
            }
          }
        }
      }
      while (localAccount2 != null)
      {
        return localAccount2;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = localAppState.packageName;
        arrayOfObject2[1] = FinskyLog.scrubPii(((Account)localList.get(0)).name);
        FinskyLog.d("%s: Account determined from library ownership - %s", arrayOfObject2);
        localAccount2 = (Account)localList.get(0);
        continue;
        localAccount2 = null;
      }
    }
    Account localAccount1 = AccountHandler.getFirstAccount(paramContext);
    if (localAccount1 != null)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramString;
      arrayOfObject1[1] = FinskyLog.scrubPii(localAccount1.name);
      FinskyLog.d("%s: Account from first account - %s", arrayOfObject1);
      return localAccount1;
    }
    FinskyLog.w("%s: No account found.", new Object[] { paramString });
    return null;
  }
  
  private static void logGmpMeasurementEvent(Throwable paramThrowable)
  {
    if (!FinskyApp.get().getExperiments().isEnabled(12604323L)) {
      return;
    }
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(1105);
    localBackgroundEventBuilder.setExceptionType(paramThrowable);
    FinskyApp.get().getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  public static ResponseCode validatePackageName(String paramString, PackageManager paramPackageManager, int paramInt)
  {
    if (TextUtils.isEmpty(paramString))
    {
      FinskyLog.w("Input Error: Non empty/null argument expected for packageName.", new Object[0]);
      return ResponseCode.RESULT_DEVELOPER_ERROR;
    }
    String[] arrayOfString = paramPackageManager.getPackagesForUid(paramInt);
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++) {
      if (paramString.equals(arrayOfString[j])) {
        return ResponseCode.RESULT_OK;
      }
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    FinskyLog.w("Package name %s does not match UID %d", arrayOfObject);
    return ResponseCode.RESULT_DEVELOPER_ERROR;
  }
  
  public static enum ResponseCode
  {
    final int responseCode;
    
    static
    {
      RESULT_SERVICE_UNAVAILABLE = new ResponseCode("RESULT_SERVICE_UNAVAILABLE", 2, 2);
      RESULT_BILLING_UNAVAILABLE = new ResponseCode("RESULT_BILLING_UNAVAILABLE", 3, 3);
      RESULT_ITEM_UNAVAILABLE = new ResponseCode("RESULT_ITEM_UNAVAILABLE", 4, 4);
      RESULT_DEVELOPER_ERROR = new ResponseCode("RESULT_DEVELOPER_ERROR", 5, 5);
      RESULT_ERROR = new ResponseCode("RESULT_ERROR", 6, 6);
      RESULT_ITEM_ALREADY_OWNED = new ResponseCode("RESULT_ITEM_ALREADY_OWNED", 7, 7);
      RESULT_ITEM_NOT_OWNED = new ResponseCode("RESULT_ITEM_NOT_OWNED", 8, 8);
      RESULT_PROMO_ELIGIBLE = new ResponseCode("RESULT_PROMO_ELIGIBLE", 9, 9);
      RESULT_NOT_PROMO_ELIGIBLE = new ResponseCode("RESULT_NOT_PROMO_ELIGIBLE", 10, 10);
      ResponseCode[] arrayOfResponseCode = new ResponseCode[11];
      arrayOfResponseCode[0] = RESULT_OK;
      arrayOfResponseCode[1] = RESULT_USER_CANCELED;
      arrayOfResponseCode[2] = RESULT_SERVICE_UNAVAILABLE;
      arrayOfResponseCode[3] = RESULT_BILLING_UNAVAILABLE;
      arrayOfResponseCode[4] = RESULT_ITEM_UNAVAILABLE;
      arrayOfResponseCode[5] = RESULT_DEVELOPER_ERROR;
      arrayOfResponseCode[6] = RESULT_ERROR;
      arrayOfResponseCode[7] = RESULT_ITEM_ALREADY_OWNED;
      arrayOfResponseCode[8] = RESULT_ITEM_NOT_OWNED;
      arrayOfResponseCode[9] = RESULT_PROMO_ELIGIBLE;
      arrayOfResponseCode[10] = RESULT_NOT_PROMO_ELIGIBLE;
      $VALUES = arrayOfResponseCode;
    }
    
    private ResponseCode(int paramInt)
    {
      this.responseCode = paramInt;
    }
    
    public final String toString()
    {
      return super.toString() + '(' + this.responseCode + ')';
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.iab.InAppBillingUtils
 * JD-Core Version:    0.7.0.1
 */