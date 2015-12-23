package com.google.android.finsky.billing.iab;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.InAppBillingSetting;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils;
import com.google.android.finsky.billing.lightpurchase.IabV3Activity;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams.Builder;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.library.LibraryInAppEntry;
import com.google.android.finsky.library.LibraryInAppSubscriptionEntry;
import com.google.android.finsky.library.LibraryReplicators;
import com.google.android.finsky.protos.BuyInstruments.CheckIabPromoResponse;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.ConsumePurchaseResponse;
import com.google.android.finsky.protos.Details.BulkDetailsResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Purchase.InAppPurchaseInfo;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Md5Util;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public final class InAppBillingManager
{
  private static final int DETAILS_REQUEST_SKU_LIST_MAX_SIZE = ((Integer)G.iabV3SkuDetailsMaxSize.get()).intValue();
  private static final int MAX_PURCHASES_IN_RESPONSE = ((Integer)G.iabV3MaxPurchasesInResponse.get()).intValue();
  private static final long TIMEOUT_MS = ((Long)G.iabV3NetworkTimeoutMs.get()).longValue();
  private final Context mContext;
  private final DfeApi mDfeApi;
  private final Libraries mLibraries;
  private final LibraryReplicators mLibraryReplicators;
  
  public InAppBillingManager(Context paramContext, Libraries paramLibraries, LibraryReplicators paramLibraryReplicators, DfeApi paramDfeApi)
  {
    if (paramContext == null) {
      throw new IllegalArgumentException("context must not be null");
    }
    if (paramLibraries == null) {
      throw new IllegalArgumentException("libraries must not be null");
    }
    if (paramLibraryReplicators == null) {
      throw new IllegalArgumentException("libraryReplicators must not be null");
    }
    if (paramDfeApi.getAccount() == null) {
      throw new IllegalArgumentException("dfeApi must specify an account");
    }
    this.mContext = paramContext;
    this.mLibraries = paramLibraries;
    this.mLibraryReplicators = paramLibraryReplicators;
    this.mDfeApi = paramDfeApi;
  }
  
  private static String buildDetailsJson(DocV2 paramDocV2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localOffer = paramDocV2.offer[0];
      str1 = paramDocV2.backendDocid;
      localJSONObject.put("productId", DocUtils.extractSkuForInApp(str1));
      if (!str1.startsWith("inapp:")) {
        break label137;
      }
      str2 = "inapp";
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        Common.Offer localOffer;
        String str1;
        FinskyLog.wtf("Exception when creating json: %s", new Object[] { localJSONException });
        continue;
        String str2 = null;
      }
    }
    localJSONObject.put("type", str2);
    localJSONObject.put("price", localOffer.formattedAmount);
    if (localOffer.convertedPrice.length > 0)
    {
      localJSONObject.put("price_amount_micros", localOffer.convertedPrice[0].micros);
      localJSONObject.put("price_currency_code", localOffer.convertedPrice[0].currencyCode);
    }
    for (;;)
    {
      localJSONObject.put("title", paramDocV2.title);
      localJSONObject.put("description", Html.fromHtml(paramDocV2.descriptionHtml));
      return localJSONObject.toString();
      label137:
      if (!str1.startsWith("subs:")) {
        break label196;
      }
      str2 = "subs";
      break;
      localJSONObject.put("price_amount_micros", localOffer.micros);
      localJSONObject.put("price_currency_code", localOffer.currencyCode);
    }
  }
  
  private InAppBillingUtils.ResponseCode checkBillingEnabled(int paramInt)
  {
    if ((paramInt < 3) || (paramInt > 5))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.w("Unsupported billing API version: %d", arrayOfObject);
    }
    for (InAppBillingUtils.ResponseCode localResponseCode = InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE; localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK; localResponseCode = InAppBillingUtils.ResponseCode.RESULT_OK) {
      return localResponseCode;
    }
    if (!InAppBillingSetting.isEnabled(this.mDfeApi.getAccountName(), paramInt))
    {
      FinskyLog.w("Billing unavailable for this package and user.", new Object[0]);
      return InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE;
    }
    return InAppBillingUtils.ResponseCode.RESULT_OK;
  }
  
  private static InAppBillingUtils.ResponseCode checkTypeSupported(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      FinskyLog.w("Input Error: Non empty/null argument expected for type.", new Object[0]);
      return InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR;
    }
    if ((!TextUtils.equals(paramString, "inapp")) && (!TextUtils.equals(paramString, "subs")))
    {
      FinskyLog.w("Unknown item type specified %s", new Object[] { paramString });
      return InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE;
    }
    if ((paramString.equals("subs")) && (!((Boolean)G.iabV3SubscriptionsEnabled.get()).booleanValue()))
    {
      FinskyLog.w("Subscriptions are not supported", new Object[0]);
      return InAppBillingUtils.ResponseCode.RESULT_BILLING_UNAVAILABLE;
    }
    return InAppBillingUtils.ResponseCode.RESULT_OK;
  }
  
  private InAppBillingUtils.ResponseCode consumeIabPurchase(String paramString1, String paramString2)
  {
    final Semaphore localSemaphore = new Semaphore(0);
    final InAppBillingUtils.ResponseCode[] arrayOfResponseCode = new InAppBillingUtils.ResponseCode[1];
    arrayOfResponseCode[0] = InAppBillingUtils.ResponseCode.RESULT_OK;
    this.mDfeApi.consumePurchase$3e88e590(paramString2, paramString1, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramAnonymousVolleyError.toString();
        FinskyLog.w("Error response on confirmPurchase: %s", arrayOfObject);
        arrayOfResponseCode[0] = InAppBillingUtils.ResponseCode.RESULT_ERROR;
        localSemaphore.release();
      }
    });
    try
    {
      if (!localSemaphore.tryAcquire(TIMEOUT_MS, TimeUnit.MILLISECONDS))
      {
        InAppBillingUtils.ResponseCode localResponseCode = InAppBillingUtils.ResponseCode.RESULT_ERROR;
        return localResponseCode;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      return InAppBillingUtils.ResponseCode.RESULT_ERROR;
    }
    return arrayOfResponseCode[0];
  }
  
  private void fetchSkuDetails(String paramString1, ArrayList<String> paramArrayList, String paramString2, final Bundle paramBundle)
  {
    ArrayList localArrayList = Lists.newArrayList(paramArrayList.size());
    for (int i = 0; i != paramArrayList.size(); i++) {
      localArrayList.add(InAppBillingUtils.buildDocidStr((String)paramArrayList.get(i), paramString2, paramString1));
    }
    final boolean bool = FinskyApp.get().getExperiments(this.mDfeApi.getAccountName()).isEnabled(12603718L);
    final String[] arrayOfString = new String[1];
    final Semaphore localSemaphore = new Semaphore(0);
    Request localRequest = this.mDfeApi.getBulkDetails(localArrayList, false, paramString1, true, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        paramBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_ERROR.responseCode);
        localSemaphore.release();
      }
    });
    if (bool) {
      arrayOfString[0] = localRequest.getCacheKey();
    }
    try
    {
      if (!localSemaphore.tryAcquire(TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
        paramBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_ERROR.responseCode);
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      paramBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_ERROR.responseCode);
    }
  }
  
  private static <T> Pair<List<T>, String> getListAndContinuationToken(List<T> paramList, String paramString)
  {
    if (paramList.size() <= MAX_PURCHASES_IN_RESPONSE) {
      return Pair.create(paramList, null);
    }
    boolean bool = TextUtils.isEmpty(paramString);
    int i = 0;
    String str1;
    if (!bool)
    {
      str1 = new String(Base64.decode(paramString, 0));
      if (str1.startsWith("CONT-TOKEN-")) {
        break label132;
      }
      i = -1;
      if ((i < 0) || (i >= paramList.size())) {
        i = 0;
      }
    }
    int j = i + MAX_PURCHASES_IN_RESPONSE;
    if (j < paramList.size()) {}
    for (String str2 = Base64.encodeToString(("CONT-TOKEN-" + j).getBytes(), 0);; str2 = null)
    {
      return Pair.create(paramList.subList(i, j), str2);
      label132:
      i = Integer.parseInt(str1.substring(11));
      break;
      j = paramList.size();
    }
  }
  
  private boolean isDocumentInLibrary(String paramString1, String paramString2, String paramString3)
  {
    this.mLibraries.blockingLoad();
    return this.mLibraries.getAccountLibrary(this.mDfeApi.getAccount()).getInAppEntry(InAppBillingUtils.buildDocidStr(paramString2, paramString1, paramString3)) != null;
  }
  
  private PendingIntent makePurchaseIntent(int paramInt, String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4)
  {
    PackageInfo localPackageInfo = InAppBillingUtils.getPackageInfoWithSignatures(this.mContext, paramString1);
    Object localObject2;
    Intent localIntent;
    if (localPackageInfo == null)
    {
      localObject2 = null;
      if (!UiUtils.isAndroidTv()) {
        break label488;
      }
      Account localAccount = this.mDfeApi.getAccount();
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("TvIntentUtils.account", localAccount);
      localBundle.putParcelable("TvIntentUtils.purchaseParams", (Parcelable)localObject2);
      localBundle.putBoolean("TvIntentUtils.showOfferResolution", false);
      localBundle.putString("TvIntentUtils.breadCrumb", paramString1);
      localIntent = new Intent("com.google.android.finsky.tv.IABV3_PURCHASE");
      localIntent.putExtras(localBundle);
    }
    for (;;)
    {
      return PendingIntent.getActivity(this.mContext, 0, localIntent, 1073741824);
      String str1 = Md5Util.secureHash(localPackageInfo.signatures[0].toByteArray());
      Object localObject1 = null;
      if (paramList != null)
      {
        boolean bool2 = paramList.isEmpty();
        localObject1 = null;
        if (!bool2)
        {
          String[] arrayOfString = new String[paramList.size()];
          for (int k = 0; k < arrayOfString.length; k++) {
            arrayOfString[k] = InAppBillingUtils.buildDocidStr((String)paramList.get(k), paramString3, paramString1);
          }
          localObject1 = arrayOfString;
        }
      }
      String str2 = InAppBillingUtils.buildDocidStr(paramString2, paramString3, paramString1);
      PurchaseParams.Builder localBuilder = PurchaseParams.builder();
      int i;
      if ("inapp".equals(paramString3)) {
        i = 11;
      }
      for (;;)
      {
        Common.Docid localDocid = new Common.Docid();
        localDocid.backend = 3;
        localDocid.hasBackend = true;
        localDocid.type = i;
        localDocid.hasType = true;
        localDocid.backendDocid = str2;
        localDocid.hasBackendDocid = true;
        localBuilder.docid = localDocid;
        localBuilder.docidStr = str2;
        localBuilder.offerType = 1;
        int j = localPackageInfo.versionCode;
        localBuilder.inAppPurchaseInfo = new Purchase.InAppPurchaseInfo();
        localBuilder.inAppPurchaseInfo.billingApiVersion = paramInt;
        localBuilder.inAppPurchaseInfo.hasBillingApiVersion = true;
        if (!TextUtils.isEmpty(paramString1))
        {
          localBuilder.inAppPurchaseInfo.appPackageName = paramString1;
          localBuilder.inAppPurchaseInfo.hasAppPackageName = true;
        }
        localBuilder.inAppPurchaseInfo.appVersionCode = j;
        localBuilder.inAppPurchaseInfo.hasAppVersionCode = true;
        if (!TextUtils.isEmpty(str1))
        {
          localBuilder.inAppPurchaseInfo.appSignatureHash = str1;
          localBuilder.inAppPurchaseInfo.hasAppSignatureHash = true;
        }
        if (!TextUtils.isEmpty(paramString4))
        {
          localBuilder.inAppPurchaseInfo.developerPayload = paramString4;
          localBuilder.inAppPurchaseInfo.hasDeveloperPayload = true;
        }
        if (localObject1 != null) {
          localBuilder.inAppPurchaseInfo.oldDocid = localObject1;
        }
        localObject2 = localBuilder.build();
        break;
        boolean bool1 = "subs".equals(paramString3);
        i = 0;
        if (bool1) {
          i = 15;
        }
      }
      label488:
      localIntent = IabV3Activity.createIntent(this.mDfeApi.getAccount(), (PurchaseParams)localObject2);
    }
  }
  
  private InAppBillingUtils.ResponseCode performIabPromoCheck(final String paramString1, String paramString2)
  {
    final Semaphore localSemaphore = new Semaphore(0);
    final InAppBillingUtils.ResponseCode[] arrayOfResponseCode = new InAppBillingUtils.ResponseCode[1];
    arrayOfResponseCode[0] = InAppBillingUtils.ResponseCode.RESULT_ERROR;
    int i = 11;
    if (paramString2.equals("subs")) {
      i = 15;
    }
    this.mDfeApi.checkIabPromo(i, paramString1, CarrierBillingUtils.getSimIdentifier(), new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramAnonymousVolleyError.toString();
        FinskyLog.w("Error response on checkIabPromo: %s", arrayOfObject);
        arrayOfResponseCode[0] = InAppBillingUtils.ResponseCode.RESULT_ERROR;
        localSemaphore.release();
      }
    });
    try
    {
      if (!localSemaphore.tryAcquire(TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
        arrayOfResponseCode[0] = InAppBillingUtils.ResponseCode.RESULT_ERROR;
      }
      return arrayOfResponseCode[0];
    }
    catch (InterruptedException localInterruptedException)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localInterruptedException.getMessage();
      FinskyLog.d("Interrupted: %s", arrayOfObject);
    }
    return InAppBillingUtils.ResponseCode.RESULT_ERROR;
  }
  
  private void populatePurchasesForPackage(String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    AccountLibrary localAccountLibrary = this.mLibraries.getAccountLibrary(this.mDfeApi.getAccount());
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    if (paramString2.equals("inapp"))
    {
      Pair localPair2 = getListAndContinuationToken(localAccountLibrary.getInAppPurchasesForPackage(paramString1), paramString3);
      List localList2 = (List)localPair2.first;
      paramString3 = (String)localPair2.second;
      Iterator localIterator2 = localList2.iterator();
      while (localIterator2.hasNext())
      {
        LibraryInAppEntry localLibraryInAppEntry = (LibraryInAppEntry)localIterator2.next();
        localArrayList1.add(DocUtils.extractSkuForInApp(localLibraryInAppEntry.mDocId));
        localArrayList2.add(localLibraryInAppEntry.signedPurchaseData);
        localArrayList3.add(localLibraryInAppEntry.signature);
      }
    }
    if (paramString2.equals("subs"))
    {
      Pair localPair1 = getListAndContinuationToken(localAccountLibrary.getSubscriptionPurchasesForPackage(paramString1), paramString3);
      List localList1 = (List)localPair1.first;
      paramString3 = (String)localPair1.second;
      Iterator localIterator1 = localList1.iterator();
      while (localIterator1.hasNext())
      {
        LibraryInAppSubscriptionEntry localLibraryInAppSubscriptionEntry = (LibraryInAppSubscriptionEntry)localIterator1.next();
        localArrayList1.add(DocUtils.extractSkuForInApp(localLibraryInAppSubscriptionEntry.mDocId));
        localArrayList2.add(localLibraryInAppSubscriptionEntry.signedPurchaseData);
        localArrayList3.add(localLibraryInAppSubscriptionEntry.signature);
      }
    }
    paramBundle.putStringArrayList("INAPP_PURCHASE_ITEM_LIST", localArrayList1);
    paramBundle.putStringArrayList("INAPP_PURCHASE_DATA_LIST", localArrayList2);
    paramBundle.putStringArrayList("INAPP_DATA_SIGNATURE_LIST", localArrayList3);
    if (paramString3 != null) {
      paramBundle.putString("INAPP_CONTINUATION_TOKEN", paramString3);
    }
  }
  
  public final int consumePurchase(int paramInt, String paramString1, String paramString2)
  {
    InAppBillingUtils.ResponseCode localResponseCode = checkBillingEnabled(paramInt);
    if (localResponseCode != InAppBillingUtils.ResponseCode.RESULT_OK) {
      return localResponseCode.responseCode;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      FinskyLog.w("Input Error: Non empty/null argument expected for purchaseToken.", new Object[0]);
      return InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode;
    }
    return consumeIabPurchase(paramString1, paramString2).responseCode;
  }
  
  public final Bundle getBuyIntent(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Bundle localBundle = new Bundle();
    InAppBillingUtils.ResponseCode localResponseCode1 = checkBillingEnabled(paramInt);
    if (localResponseCode1 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode1.responseCode);
      return localBundle;
    }
    InAppBillingUtils.ResponseCode localResponseCode2 = checkTypeSupported(paramString3);
    if (localResponseCode2 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode2.responseCode);
      return localBundle;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      FinskyLog.w("Input Error: Non empty/null argument expected for sku.", new Object[0]);
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
      return localBundle;
    }
    if (isDocumentInLibrary(paramString3, paramString2, paramString1))
    {
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_ITEM_ALREADY_OWNED.responseCode);
      return localBundle;
    }
    localBundle.putParcelable("BUY_INTENT", makePurchaseIntent(paramInt, paramString1, null, paramString2, paramString3, paramString4));
    localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_OK.responseCode);
    return localBundle;
  }
  
  public final Bundle getBuyIntentToReplaceSkus(int paramInt, String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4)
  {
    Bundle localBundle = new Bundle();
    InAppBillingUtils.ResponseCode localResponseCode1 = checkBillingEnabled(paramInt);
    if (localResponseCode1 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode1.responseCode);
      return localBundle;
    }
    if (paramInt < 5)
    {
      FinskyLog.w("Input Error: getBuyIntentToReplaceSkus was introduced in API version 5.", new Object[0]);
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
      return localBundle;
    }
    InAppBillingUtils.ResponseCode localResponseCode2 = checkTypeSupported(paramString3);
    if (localResponseCode2 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode2.responseCode);
      return localBundle;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      FinskyLog.w("Input Error: Non empty/null argument expected for newSku.", new Object[0]);
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
      return localBundle;
    }
    if (isDocumentInLibrary(paramString3, paramString2, paramString1))
    {
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_ITEM_ALREADY_OWNED.responseCode);
      return localBundle;
    }
    localBundle.putParcelable("BUY_INTENT", makePurchaseIntent(paramInt, paramString1, paramList, paramString2, paramString3, paramString4));
    localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_OK.responseCode);
    return localBundle;
  }
  
  public final Bundle getPurchases(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    Bundle localBundle = new Bundle();
    InAppBillingUtils.ResponseCode localResponseCode1 = checkBillingEnabled(paramInt);
    if (localResponseCode1 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode1.responseCode);
      return localBundle;
    }
    InAppBillingUtils.ResponseCode localResponseCode2 = checkTypeSupported(paramString2);
    if (localResponseCode2 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode2.responseCode);
      return localBundle;
    }
    populatePurchasesForPackage(paramString1, paramString2, paramString3, localBundle);
    localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_OK.responseCode);
    return localBundle;
  }
  
  public final Bundle getSkuDetails(int paramInt, String paramString1, String paramString2, Bundle paramBundle)
  {
    Bundle localBundle = new Bundle();
    InAppBillingUtils.ResponseCode localResponseCode1 = checkBillingEnabled(paramInt);
    if (localResponseCode1 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode1.responseCode);
      return localBundle;
    }
    InAppBillingUtils.ResponseCode localResponseCode2 = checkTypeSupported(paramString2);
    if (localResponseCode2 != InAppBillingUtils.ResponseCode.RESULT_OK)
    {
      localBundle.putInt("RESPONSE_CODE", localResponseCode2.responseCode);
      return localBundle;
    }
    if (paramBundle == null)
    {
      FinskyLog.w("Input Error: Non-null argument expected for skusBundle.", new Object[0]);
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
      return localBundle;
    }
    ArrayList localArrayList = paramBundle.getStringArrayList("ITEM_ID_LIST");
    if (localArrayList == null)
    {
      FinskyLog.w("Input Error: skusBundle must contain an array associated with key %s.", new Object[] { "ITEM_ID_LIST" });
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
      return localBundle;
    }
    if (localArrayList.isEmpty())
    {
      FinskyLog.w("Input Error: skusBundle array associated with key %s cannot be empty.", new Object[] { "ITEM_ID_LIST" });
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
      return localBundle;
    }
    if (localArrayList.size() > DETAILS_REQUEST_SKU_LIST_MAX_SIZE)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = "ITEM_ID_LIST";
      arrayOfObject2[1] = Integer.valueOf(DETAILS_REQUEST_SKU_LIST_MAX_SIZE);
      FinskyLog.w("Input Error: skusBundle array associated with key %s cannot contain more than %d items.", arrayOfObject2);
      localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
      return localBundle;
    }
    for (int i = 0; i < localArrayList.size(); i++) {
      if (TextUtils.isEmpty((CharSequence)localArrayList.get(i)))
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = "ITEM_ID_LIST";
        arrayOfObject1[1] = Integer.valueOf(i);
        FinskyLog.w("Input Error: skusBundle array associated with key %s contains an empty/null sku at index %d.", arrayOfObject1);
        localBundle.putInt("RESPONSE_CODE", InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode);
        return localBundle;
      }
    }
    fetchSkuDetails(paramString1, localArrayList, paramString2, localBundle);
    return localBundle;
  }
  
  public final int isBillingSupported$6ef37c35(int paramInt, String paramString)
  {
    InAppBillingUtils.ResponseCode localResponseCode1 = checkBillingEnabled(paramInt);
    if (localResponseCode1 != InAppBillingUtils.ResponseCode.RESULT_OK) {
      return localResponseCode1.responseCode;
    }
    InAppBillingUtils.ResponseCode localResponseCode2 = checkTypeSupported(paramString);
    if (localResponseCode2 != InAppBillingUtils.ResponseCode.RESULT_OK) {
      return localResponseCode2.responseCode;
    }
    return localResponseCode2.responseCode;
  }
  
  public final int isPromoEligible(int paramInt, String paramString1, String paramString2)
    throws RemoteException
  {
    InAppBillingUtils.ResponseCode localResponseCode1 = checkBillingEnabled(paramInt);
    if (localResponseCode1 != InAppBillingUtils.ResponseCode.RESULT_OK) {
      return localResponseCode1.responseCode;
    }
    InAppBillingUtils.ResponseCode localResponseCode2 = checkTypeSupported(paramString2);
    if (localResponseCode2 != InAppBillingUtils.ResponseCode.RESULT_OK) {
      return localResponseCode2.responseCode;
    }
    if (paramInt < 4)
    {
      FinskyLog.w("Input Error: isPromoEligible was introduced in API version 4.", new Object[0]);
      return InAppBillingUtils.ResponseCode.RESULT_DEVELOPER_ERROR.responseCode;
    }
    return performIabPromoCheck(paramString1, paramString2).responseCode;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.iab.InAppBillingManager
 * JD-Core Version:    0.7.0.1
 */