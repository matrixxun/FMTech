package com.google.android.finsky.billing.iab;

import android.accounts.Account;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.IBinder;
import com.android.vending.billing.IMarketBillingService.Stub;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.InAppBillingSetting;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.VendingProtos.AckNotificationsRequestProto;
import com.google.android.finsky.protos.VendingProtos.AckNotificationsResponseProto;
import com.google.android.finsky.protos.VendingProtos.InAppPurchaseInformationRequestProto;
import com.google.android.finsky.protos.VendingProtos.InAppPurchaseInformationResponseProto;
import com.google.android.finsky.protos.VendingProtos.InAppRestoreTransactionsRequestProto;
import com.google.android.finsky.protos.VendingProtos.InAppRestoreTransactionsResponseProto;
import com.google.android.finsky.protos.VendingProtos.SignatureHashProto;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.Md5Util;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingRequest;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MarketBillingService
  extends Service
{
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  private static Random sRandom = new Random();
  private final IMarketBillingService.Stub mBinder = new Stub();
  BillingNotifier mNotifier = new BillingNotifier(this);
  PackageManager mPackageManager;
  
  public static boolean sendNotify(Context paramContext, String paramString1, String paramString2)
  {
    Intent localIntent = IntentUtils.createIntentForReceiver(paramContext.getPackageManager(), paramString1, new Intent("com.android.vending.billing.IN_APP_NOTIFY"));
    if (localIntent == null) {
      return false;
    }
    localIntent.putExtra("notification_id", paramString2);
    paramContext.sendBroadcast(localIntent);
    return true;
  }
  
  public static boolean sendResponseCode(Context paramContext, String paramString, long paramLong, InAppBillingUtils.ResponseCode paramResponseCode)
  {
    Intent localIntent = IntentUtils.createIntentForReceiver(paramContext.getPackageManager(), paramString, new Intent("com.android.vending.billing.RESPONSE_CODE"));
    if (localIntent == null)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramResponseCode.name();
      arrayOfObject2[1] = paramString;
      FinskyLog.d("Response %s cannot be delivered to %s. Intent does not resolve.", arrayOfObject2);
      return false;
    }
    Object[] arrayOfObject1 = new Object[3];
    arrayOfObject1[0] = paramResponseCode.name();
    arrayOfObject1[1] = Long.valueOf(paramLong);
    arrayOfObject1[2] = paramString;
    FinskyLog.d("Sending response %s for request %d to %s.", arrayOfObject1);
    localIntent.putExtra("request_id", paramLong);
    localIntent.putExtra("response_code", paramResponseCode.ordinal());
    paramContext.sendBroadcast(localIntent);
    return true;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mPackageManager = getPackageManager();
  }
  
  public void onDestroy()
  {
    super.onDestroy();
  }
  
  protected static final class BillingNotifier
  {
    MarketBillingService mService;
    
    public BillingNotifier(MarketBillingService paramMarketBillingService)
    {
      this.mService = paramMarketBillingService;
    }
    
    protected final boolean sendPurchaseStateChanged(String paramString1, String paramString2, String paramString3)
    {
      Intent localIntent = IntentUtils.createIntentForReceiver(this.mService.mPackageManager, paramString1, new Intent("com.android.vending.billing.PURCHASE_STATE_CHANGED"));
      if (localIntent == null) {
        return false;
      }
      localIntent.putExtra("inapp_signed_data", paramString2);
      localIntent.putExtra("inapp_signature", paramString3);
      this.mService.sendBroadcast(localIntent);
      return true;
    }
    
    protected final boolean sendResponseCode(String paramString, long paramLong, InAppBillingUtils.ResponseCode paramResponseCode)
    {
      return MarketBillingService.sendResponseCode(this.mService, paramString, paramLong, paramResponseCode);
    }
  }
  
  private static enum BillingRequest
  {
    static
    {
      GET_PURCHASE_INFORMATION = new BillingRequest("GET_PURCHASE_INFORMATION", 2);
      RESTORE_TRANSACTIONS = new BillingRequest("RESTORE_TRANSACTIONS", 3);
      CONFIRM_NOTIFICATIONS = new BillingRequest("CONFIRM_NOTIFICATIONS", 4);
      BillingRequest[] arrayOfBillingRequest = new BillingRequest[5];
      arrayOfBillingRequest[0] = CHECK_BILLING_SUPPORTED;
      arrayOfBillingRequest[1] = REQUEST_PURCHASE;
      arrayOfBillingRequest[2] = GET_PURCHASE_INFORMATION;
      arrayOfBillingRequest[3] = RESTORE_TRANSACTIONS;
      arrayOfBillingRequest[4] = CONFIRM_NOTIFICATIONS;
      $VALUES = arrayOfBillingRequest;
    }
    
    private BillingRequest() {}
  }
  
  final class Stub
    extends IMarketBillingService.Stub
  {
    Stub() {}
    
    private static boolean argumentsMatch(Bundle paramBundle, String[] paramArrayOfString1, String[] paramArrayOfString2)
    {
      Set localSet = paramBundle.keySet();
      HashSet localHashSet1 = new HashSet();
      HashSet localHashSet2 = new HashSet();
      localHashSet1.add("BILLING_REQUEST");
      localHashSet1.add("API_VERSION");
      int i = paramArrayOfString1.length;
      for (int j = 0; j < i; j++) {
        localHashSet1.add(paramArrayOfString1[j]);
      }
      int k = paramArrayOfString2.length;
      for (int m = 0; m < k; m++) {
        localHashSet2.add(paramArrayOfString2[m]);
      }
      localSet.removeAll(localHashSet2);
      return localSet.equals(localHashSet1);
    }
    
    private static boolean argumentsMatchExactly(Bundle paramBundle, String... paramVarArgs)
    {
      return argumentsMatch(paramBundle, paramVarArgs, MarketBillingService.EMPTY_STRING_ARRAY);
    }
    
    private static MarketBillingService.BillingRequest getBillingRequest(Bundle paramBundle)
    {
      String str = paramBundle.getString("BILLING_REQUEST");
      if (str == null)
      {
        FinskyLog.w("Received bundle without billing request type", new Object[0]);
        return null;
      }
      try
      {
        MarketBillingService.BillingRequest localBillingRequest = MarketBillingService.BillingRequest.valueOf(str);
        return localBillingRequest;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        FinskyLog.w("Unknown billing request type: %s", new Object[] { str });
      }
      return null;
    }
    
    private static long getNextInAppRequestId()
    {
      return 0xFFFFFFFF & MarketBillingService.sRandom.nextLong();
    }
    
    private PackageInfo getPackageInfo(String paramString)
    {
      try
      {
        PackageInfo localPackageInfo = MarketBillingService.this.mPackageManager.getPackageInfo(paramString, 64);
        return localPackageInfo;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        FinskyLog.w("cannot find package name: %s", new Object[] { paramString });
      }
      return null;
    }
    
    private static boolean isBillingEnabledForAccount(Account paramAccount, int paramInt)
    {
      return (paramAccount != null) && (InAppBillingSetting.isEnabled(paramAccount.name, paramInt));
    }
    
    private static VendingProtos.SignatureHashProto makeSignatureHash(PackageInfo paramPackageInfo)
    {
      VendingProtos.SignatureHashProto localSignatureHashProto = new VendingProtos.SignatureHashProto();
      localSignatureHashProto.packageName = paramPackageInfo.packageName;
      localSignatureHashProto.hasPackageName = true;
      localSignatureHashProto.versionCode = paramPackageInfo.versionCode;
      localSignatureHashProto.hasVersionCode = true;
      localSignatureHashProto.hash = Md5Util.secureHashBytes(paramPackageInfo.signatures[0].toByteArray());
      return localSignatureHashProto;
    }
    
    private static Bundle setResponseCode(Bundle paramBundle, InAppBillingUtils.ResponseCode paramResponseCode)
    {
      paramBundle.putInt("RESPONSE_CODE", paramResponseCode.ordinal());
      return paramBundle;
    }
    
    private static InAppBillingUtils.ResponseCode updateWithRequestId(Bundle paramBundle, long paramLong)
    {
      if (paramLong == -1L) {
        return InAppBillingUtils.ResponseCode.RESULT_ERROR;
      }
      paramBundle.putLong("REQUEST_ID", paramLong);
      return InAppBillingUtils.ResponseCode.RESULT_OK;
    }
    
    public final Bundle sendBillingRequest(Bundle paramBundle)
    {
      MarketBillingService.BillingRequest localBillingRequest = getBillingRequest(paramBundle);
      Bundle localBundle1;
      if (localBillingRequest == null)
      {
        localBundle1 = new Bundle();
        localBundle1.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.ordinal());
        MarketBillingService.this.stopSelf();
        return localBundle1;
      }
      int i = paramBundle.getInt("API_VERSION", -1);
      final String str1 = paramBundle.getString("PACKAGE_NAME");
      String str2 = paramBundle.getString("ITEM_TYPE");
      long l1 = paramBundle.getLong("NONCE");
      String[] arrayOfString = paramBundle.getStringArray("NOTIFY_IDS");
      Bundle localBundle2 = new Bundle();
      InAppBillingUtils.ResponseCode localResponseCode;
      if (i <= 0)
      {
        FinskyLog.w("No billing API version given!", new Object[0]);
        localResponseCode = InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR;
        label107:
        if (localResponseCode == InAppBillingUtils.ResponseCode.RESULT_OK) {
          break label233;
        }
        label115:
        if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
          break label419;
        }
      }
      switch (MarketBillingService.1.$SwitchMap$com$google$android$finsky$billing$iab$MarketBillingService$BillingRequest[localBillingRequest.ordinal()])
      {
      default: 
        FinskyLog.wtf("enum %s", new Object[] { localBillingRequest });
      case 1: 
        for (localBundle1 = setResponseCode(localBundle2, InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR);; localBundle1 = setResponseCode(localBundle2, InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR))
        {
          break;
          if (i > 2)
          {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = Integer.valueOf(i);
            FinskyLog.d("Unsupported (future) billing API version: %d", arrayOfObject2);
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE;
            break label107;
          }
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_OK;
          break label107;
          label233:
          if (str1 == null)
          {
            FinskyLog.w("No packageName given!", new Object[0]);
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR;
            break label115;
          }
          if (!isBillingEnabledForAccount(MarketBillingService.getPreferredAccount(MarketBillingService.this, str1), i))
          {
            FinskyLog.d("Billing unavailable for this package.", new Object[0]);
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE;
            break label115;
          }
          if (getPackageInfo(str1) == null)
          {
            FinskyLog.d("No package info for %s", new Object[] { str1 });
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_ERROR;
            break label115;
          }
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_OK;
          break label115;
          if (argumentsMatch(paramBundle, new String[] { "PACKAGE_NAME" }, new String[] { "ITEM_TYPE" })) {
            break label367;
          }
        }
        label367:
        if (str2 != null) {
          break;
        }
      }
      for (String str3 = "inapp";; str3 = str2)
      {
        if ((!str3.equals("inapp")) && (!str3.equals("subs")))
        {
          FinskyLog.d("Unknown item type specified %s", new Object[] { str3 });
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE;
        }
        for (;;)
        {
          label419:
          localBundle1 = setResponseCode(localBundle2, localResponseCode);
          break;
          if (i == 1)
          {
            if (!str3.equals("inapp"))
            {
              Object[] arrayOfObject1 = new Object[2];
              arrayOfObject1[0] = str3;
              arrayOfObject1[1] = Integer.valueOf(i);
              FinskyLog.d("Item type %s not supported in billing api version %d", arrayOfObject1);
              localResponseCode = InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR;
            }
          }
          else if ((i == 2) && (str3.equals("subs")) && (!((Boolean)G.subscriptionsEnabled.get()).booleanValue()))
          {
            localResponseCode = InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE;
            continue;
          }
          localResponseCode = InAppBillingUtils.ResponseCode.RESULT_OK;
        }
        FinskyLog.w("IABv2 is deprecated", new Object[0]);
        localBundle1 = setResponseCode(localBundle2, InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
        break;
        if (!argumentsMatchExactly(paramBundle, new String[] { "PACKAGE_NAME", "NONCE", "NOTIFY_IDS" }))
        {
          localBundle1 = setResponseCode(localBundle2, InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR);
          break;
        }
        PackageInfo localPackageInfo3 = getPackageInfo(str1);
        long l7;
        if (localPackageInfo3 == null) {
          l7 = -1L;
        }
        for (;;)
        {
          localResponseCode = updateWithRequestId(localBundle2, l7);
          break;
          final long l6 = getNextInAppRequestId();
          Account localAccount3 = MarketBillingService.getPreferredAccount(MarketBillingService.this, str1);
          if (!isBillingEnabledForAccount(localAccount3, i))
          {
            MarketBillingService.this.mNotifier.sendResponseCode(str1, l6, InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
            l7 = l6;
          }
          else
          {
            VendingProtos.InAppPurchaseInformationRequestProto localInAppPurchaseInformationRequestProto = new VendingProtos.InAppPurchaseInformationRequestProto();
            localInAppPurchaseInformationRequestProto.billingApiVersion = i;
            localInAppPurchaseInformationRequestProto.hasBillingApiVersion = true;
            localInAppPurchaseInformationRequestProto.signatureHash = makeSignatureHash(localPackageInfo3);
            localInAppPurchaseInformationRequestProto.signatureAlgorithm = "SHA1withRSA";
            localInAppPurchaseInformationRequestProto.hasSignatureAlgorithm = true;
            localInAppPurchaseInformationRequestProto.nonce = l1;
            localInAppPurchaseInformationRequestProto.hasNonce = true;
            localInAppPurchaseInformationRequestProto.notificationId = arrayOfString;
            VendingApi localVendingApi3 = FinskyApp.get().getVendingApi(localAccount3);
            Response.Listener local1 = new Response.Listener() {};
            NotifyingErrorListener localNotifyingErrorListener3 = new NotifyingErrorListener(str1, l6);
            VendingRequest localVendingRequest3 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.InAppPurchaseInformationRequestProto.class, localInAppPurchaseInformationRequestProto, VendingProtos.InAppPurchaseInformationResponseProto.class, local1, localVendingApi3.mApiContext, localNotifyingErrorListener3);
            localVendingApi3.mRequestQueue.add(localVendingRequest3);
            l7 = l6;
          }
        }
        if (!argumentsMatchExactly(paramBundle, new String[] { "PACKAGE_NAME", "NONCE" }))
        {
          localBundle1 = setResponseCode(localBundle2, InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR);
          break;
        }
        PackageInfo localPackageInfo2 = getPackageInfo(str1);
        long l5;
        if (localPackageInfo2 == null) {
          l5 = -1L;
        }
        for (;;)
        {
          localResponseCode = updateWithRequestId(localBundle2, l5);
          break;
          final long l4 = getNextInAppRequestId();
          Account localAccount2 = MarketBillingService.getPreferredAccount(MarketBillingService.this, str1);
          if (!isBillingEnabledForAccount(localAccount2, i))
          {
            MarketBillingService.this.mNotifier.sendResponseCode(str1, l4, InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
            l5 = l4;
          }
          else
          {
            VendingProtos.InAppRestoreTransactionsRequestProto localInAppRestoreTransactionsRequestProto = new VendingProtos.InAppRestoreTransactionsRequestProto();
            localInAppRestoreTransactionsRequestProto.billingApiVersion = i;
            localInAppRestoreTransactionsRequestProto.hasBillingApiVersion = true;
            localInAppRestoreTransactionsRequestProto.signatureHash = makeSignatureHash(localPackageInfo2);
            localInAppRestoreTransactionsRequestProto.signatureAlgorithm = "SHA1withRSA";
            localInAppRestoreTransactionsRequestProto.hasSignatureAlgorithm = true;
            localInAppRestoreTransactionsRequestProto.nonce = l1;
            localInAppRestoreTransactionsRequestProto.hasNonce = true;
            VendingApi localVendingApi2 = FinskyApp.get().getVendingApi(localAccount2);
            Response.Listener local3 = new Response.Listener() {};
            NotifyingErrorListener localNotifyingErrorListener2 = new NotifyingErrorListener(str1, l4);
            VendingRequest localVendingRequest2 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.InAppRestoreTransactionsRequestProto.class, localInAppRestoreTransactionsRequestProto, VendingProtos.InAppRestoreTransactionsResponseProto.class, local3, localVendingApi2.mApiContext, localNotifyingErrorListener2);
            localVendingApi2.mRequestQueue.add(localVendingRequest2);
            l5 = l4;
          }
        }
        if (!argumentsMatchExactly(paramBundle, new String[] { "PACKAGE_NAME", "NOTIFY_IDS" }))
        {
          localBundle1 = setResponseCode(localBundle2, InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR);
          break;
        }
        PackageInfo localPackageInfo1 = getPackageInfo(str1);
        long l3;
        if (localPackageInfo1 == null) {
          l3 = -1L;
        }
        for (;;)
        {
          localResponseCode = updateWithRequestId(localBundle2, l3);
          break;
          final long l2 = getNextInAppRequestId();
          Account localAccount1 = MarketBillingService.getPreferredAccount(MarketBillingService.this, str1);
          if (!isBillingEnabledForAccount(localAccount1, i))
          {
            MarketBillingService.this.mNotifier.sendResponseCode(str1, l2, InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE);
            l3 = l2;
          }
          else
          {
            VendingProtos.AckNotificationsRequestProto localAckNotificationsRequestProto = new VendingProtos.AckNotificationsRequestProto();
            localAckNotificationsRequestProto.signatureHash = makeSignatureHash(localPackageInfo1);
            localAckNotificationsRequestProto.notificationId = arrayOfString;
            VendingApi localVendingApi1 = FinskyApp.get().getVendingApi(localAccount1);
            Response.Listener local2 = new Response.Listener() {};
            NotifyingErrorListener localNotifyingErrorListener1 = new NotifyingErrorListener(str1, l2);
            VendingRequest localVendingRequest1 = VendingRequest.make("https://android.clients.google.com/vending/api/ApiRequest", VendingProtos.AckNotificationsRequestProto.class, localAckNotificationsRequestProto, VendingProtos.AckNotificationsResponseProto.class, local2, localVendingApi1.mApiContext, localNotifyingErrorListener1);
            localVendingRequest1.mAvoidBulkCancel = true;
            localVendingApi1.mRequestQueue.add(localVendingRequest1);
            l3 = l2;
          }
        }
      }
    }
    
    private final class NotifyingErrorListener
      implements Response.ErrorListener
    {
      private final String mPackageName;
      private final long mRequestId;
      
      public NotifyingErrorListener(String paramString, long paramLong)
      {
        this.mPackageName = paramString;
        this.mRequestId = paramLong;
      }
      
      public final void onErrorResponse(VolleyError paramVolleyError)
      {
        FinskyLog.e("Server error on InAppPurchaseInformationRequest: %s", new Object[] { paramVolleyError });
        MarketBillingService.this.mNotifier.sendResponseCode(this.mPackageName, this.mRequestId, InAppBillingUtils.ResponseCode.RESULT_SERVICE_UNAVAILABLE);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.iab.MarketBillingService
 * JD-Core Version:    0.7.0.1
 */