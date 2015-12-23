package com.google.android.finsky.billing.lightpurchase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PurchaseData;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.DfeRequest;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.billing.InstantPurchaseUtils.InstantPurchaseParams;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.protos.Acquisition.OpenDocAction;
import com.google.android.finsky.protos.Acquisition.PostAcquisitionPrompt;
import com.google.android.finsky.protos.Acquisition.PostSuccessAction;
import com.google.android.finsky.protos.ChallengeProto.Challenge;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PlayAccountProto.CachedPaymentsLegalDocument;
import com.google.android.finsky.protos.PlayAccountProto.PlayAccountUserPurchaseCache;
import com.google.android.finsky.protos.Purchase.AuthenticationInfo;
import com.google.android.finsky.protos.Purchase.ChangeSubscription;
import com.google.android.finsky.protos.Purchase.ClientCart;
import com.google.android.finsky.protos.Purchase.CommitPurchaseRecoverableError;
import com.google.android.finsky.protos.Purchase.CommitPurchaseResponse;
import com.google.android.finsky.protos.Purchase.FopDeclinedRecoverableError;
import com.google.android.finsky.protos.Purchase.InstantPurchaseRecoverableError;
import com.google.android.finsky.protos.Purchase.InstantPurchaseResponse;
import com.google.android.finsky.protos.Purchase.PreparePurchaseResponse;
import com.google.android.finsky.protos.Purchase.PurchaseStatus;
import com.google.android.finsky.protos.Purchase.SplitTenderInfo;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.ParcelableProtoArray;
import com.google.android.finsky.utils.UserSettingsCache;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class CheckoutPurchaseSidecar
  extends SidecarFragment
{
  public Purchase.AuthenticationInfo mAuthenticationInfo;
  Purchase.ClientCart mCart;
  public boolean mCartExpandedAtStart;
  Purchase.ChangeSubscription mChangeSubscription;
  CheckoutPurchaseError mCheckoutPurchaseError;
  ChallengeProto.Challenge mCompleteChallenge;
  DfeRequest<?> mCompletePurchaseRequest;
  long mCompletePurchaseStartedMs;
  String mCvcEscrowHandle;
  DfeApi mDfeApi;
  private FinskyEventLog mEventLogger;
  Bundle mExtraPurchaseData;
  GiftEmailParams mGiftEmailParams;
  public boolean mIsInstantPurchase;
  PlayAccountProto.CachedPaymentsLegalDocument[] mLegalDocuments;
  Acquisition.PostAcquisitionPrompt mPostAcquisitionPrompt;
  ChallengeProto.Challenge mPrepareChallenge;
  private DfeRequest<?> mPrepareRequest;
  private long mPrepareStartedMs;
  PurchaseParams mPurchaseParams;
  byte[] mServerLogsCookie;
  VolleyError mVolleyError;
  
  public CheckoutPurchaseSidecar()
  {
    setRetainInstance$1385ff();
  }
  
  static Map<String, String> bundleToMap(Bundle paramBundle)
  {
    Object localObject;
    if (paramBundle == null) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      localObject = new HashMap();
      Iterator localIterator = paramBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        ((Map)localObject).put(str, paramBundle.getString(str));
      }
    }
  }
  
  private BackgroundEventBuilder getBackgroundEventBuilder(int paramInt, InstantPurchaseUtils.InstantPurchaseParams paramInstantPurchaseParams)
  {
    boolean bool = this.mIsInstantPurchase;
    PlayStore.PurchaseData localPurchaseData = null;
    if (bool)
    {
      localPurchaseData = new PlayStore.PurchaseData();
      localPurchaseData.experienceType = 1;
      localPurchaseData.hasExperienceType = true;
    }
    Object localObject = this.mServerLogsCookie;
    if (paramInstantPurchaseParams != null)
    {
      if (localPurchaseData == null) {
        localPurchaseData = new PlayStore.PurchaseData();
      }
      int[] arrayOfInt = new int[paramInstantPurchaseParams.mClientDisabledReasons.size()];
      for (int i = 0; i < paramInstantPurchaseParams.mClientDisabledReasons.size(); i++) {
        arrayOfInt[i] = ((Integer)paramInstantPurchaseParams.mClientDisabledReasons.get(i)).intValue();
      }
      localPurchaseData.instantPurchaseClientDisabledReasons = arrayOfInt;
      byte[] arrayOfByte = paramInstantPurchaseParams.mServerLogsCookie;
      if ((arrayOfByte != null) && (arrayOfByte.length > 0)) {
        localObject = arrayOfByte;
      }
    }
    if ((this.mCart != null) && (this.mCart.splitTenderInfo != null))
    {
      if (localPurchaseData == null) {
        localPurchaseData = new PlayStore.PurchaseData();
      }
      localPurchaseData.splitTenderApplied = this.mCart.splitTenderInfo.splitApplied;
      localPurchaseData.hasSplitTenderApplied = true;
    }
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(paramInt).setDocument(this.mPurchaseParams.docidStr).setOfferType(this.mPurchaseParams.offerType).setServerLogsCookie((byte[])localObject);
    localBackgroundEventBuilder.event.purchaseData = localPurchaseData;
    return localBackgroundEventBuilder;
  }
  
  private long getCompletePurchaseServerLatencyMs()
  {
    if (this.mCompletePurchaseRequest == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(this.mIsInstantPurchase);
      FinskyLog.wtf("Unexpected null complete request: %b", arrayOfObject);
      return -1L;
    }
    return this.mCompletePurchaseRequest.mServerLatencyMs;
  }
  
  private long getPrepareServerLatencyMs()
  {
    if (this.mPrepareRequest == null)
    {
      FinskyLog.wtf("Unexpected null prepare request.", new Object[0]);
      return -1L;
    }
    return this.mPrepareRequest.mServerLatencyMs;
  }
  
  private long getTimeElapsedForCompletePurchaseMs()
  {
    if (this.mCompletePurchaseStartedMs <= 0L)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(this.mIsInstantPurchase);
      FinskyLog.wtf("Complete not started: %b", arrayOfObject);
      return -1L;
    }
    return System.currentTimeMillis() - this.mCompletePurchaseStartedMs;
  }
  
  private long getTimeElapsedSincePrepareMs()
  {
    if (this.mPrepareStartedMs <= 0L)
    {
      FinskyLog.wtf("Prepare not started.", new Object[0]);
      return 0L;
    }
    return System.currentTimeMillis() - this.mPrepareStartedMs;
  }
  
  public final void confirmAuthChoiceSelected()
  {
    if ((this.mState != 2) && (this.mSubstate != 7))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.mState);
      arrayOfObject[1] = Integer.valueOf(this.mSubstate);
      FinskyLog.wtf("confirmAuthChoiceSelected() called in state %d and substate %d", arrayOfObject);
    }
    setState(2, 8);
  }
  
  public final void handlePrepareResponse(Purchase.PreparePurchaseResponse paramPreparePurchaseResponse)
  {
    int i = 5;
    if (this.mIsInstantPurchase)
    {
      this.mIsInstantPurchase = false;
      log(302, null);
    }
    this.mPrepareChallenge = null;
    this.mCart = null;
    this.mChangeSubscription = null;
    this.mCheckoutPurchaseError = null;
    this.mServerLogsCookie = paramPreparePurchaseResponse.serverLogsCookie;
    Purchase.PurchaseStatus localPurchaseStatus = paramPreparePurchaseResponse.purchaseStatus;
    log(303, localPurchaseStatus.statusCode, getPrepareServerLatencyMs(), getTimeElapsedSincePrepareMs());
    switch (localPurchaseStatus.statusCode)
    {
    case 1: 
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(localPurchaseStatus.statusCode);
      FinskyLog.wtf("Unknown status returned from server: %d", arrayOfObject);
      this.mCheckoutPurchaseError = new CheckoutPurchaseError();
      setState(3, i);
      return;
    case 2: 
      this.mPrepareChallenge = paramPreparePurchaseResponse.challenge;
      this.mCart = paramPreparePurchaseResponse.cart;
      setState(6, 0);
      return;
    case 3: 
      this.mCheckoutPurchaseError = new CheckoutPurchaseError(localPurchaseStatus.permissionError, localPurchaseStatus.errorMessageHtml);
      setState(3, i);
      return;
    }
    this.mCart = paramPreparePurchaseResponse.cart;
    this.mChangeSubscription = paramPreparePurchaseResponse.changeSubscription;
    if (this.mChangeSubscription == null) {}
    for (;;)
    {
      setState(i, 0);
      return;
      i = 4;
    }
  }
  
  public final void installApp()
  {
    Boolean localBoolean = this.mPurchaseParams.appEverExternallyHosted;
    if (localBoolean != null) {
      FinskyApp.get().mInstallPolicies.captureEverExternallyHosted(this.mPurchaseParams.docidStr, localBoolean.booleanValue());
    }
    FinskyApp.get().mInstallerDataStore.setContinueUrl(this.mPurchaseParams.docidStr, this.mPurchaseParams.appContinueUrl);
    FinskyApp.get().mInstaller.requestInstall(this.mPurchaseParams.docidStr, this.mPurchaseParams.appVersionCode, this.mDfeApi.getAccountName(), this.mCart.title, false, "single_install", 2, this.mPurchaseParams.appInstallLocation);
  }
  
  void log(int paramInt1, int paramInt2, long paramLong1, long paramLong2)
  {
    this.mEventLogger.sendBackgroundEventToSinks(getBackgroundEventBuilder(paramInt1, null).setErrorCode(paramInt2).setServerLatencyMs(paramLong1).setClientLatencyMs(paramLong2).event);
  }
  
  void log(int paramInt, InstantPurchaseUtils.InstantPurchaseParams paramInstantPurchaseParams)
  {
    this.mEventLogger.sendBackgroundEventToSinks(getBackgroundEventBuilder(paramInt, paramInstantPurchaseParams).event);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    this.mDfeApi = FinskyApp.get().getDfeApi(this.mArguments.getString("authAccount"));
    this.mPurchaseParams = ((PurchaseParams)this.mArguments.getParcelable("CheckoutPurchaseSidecar.purchaseParams"));
    this.mEventLogger = FinskyApp.get().getEventLogger(this.mDfeApi.getAccount());
    super.onCreate(paramBundle);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("CheckoutPurchaseSidecar.authenticationInfo", ParcelableProto.forProto(this.mAuthenticationInfo));
    paramBundle.putParcelable("CheckoutPurchaseSidecar.cart", ParcelableProto.forProto(this.mCart));
    paramBundle.putParcelable("CheckoutPurchaseSidecar.changeSubscription", ParcelableProto.forProto(this.mChangeSubscription));
    paramBundle.putBoolean("CheckoutPurchaseSidecar.isInstantPurchase", this.mIsInstantPurchase);
    paramBundle.putBoolean("CheckoutPurchaseSidecar.cartExpandedAtStart", this.mCartExpandedAtStart);
    paramBundle.putParcelable("CheckoutPurchaseSidecar.prepareChallenge", ParcelableProto.forProto(this.mPrepareChallenge));
    paramBundle.putParcelable("CheckoutPurchaseSidecar.completeChallenge", ParcelableProto.forProto(this.mCompleteChallenge));
    paramBundle.putBundle("CheckoutPurchaseSidecar.extraPurchaseData", this.mExtraPurchaseData);
    paramBundle.putParcelable("CheckoutPurchaseSidecar.post_acquisition_prompt", ParcelableProto.forProto(this.mPostAcquisitionPrompt));
    paramBundle.putParcelable("CheckoutPurchaseSidecar.checkoutPurchaseError", this.mCheckoutPurchaseError);
    paramBundle.putByteArray("CheckoutPurchaseSidecar.serverLogsCookie", this.mServerLogsCookie);
    paramBundle.putParcelable("CheckoutPurchaseSidecar.legalDocuments", ParcelableProtoArray.forProtoArray(this.mLegalDocuments));
    paramBundle.putParcelable("CheckoutPurchaseSidecar.giftEmailParams", this.mGiftEmailParams);
  }
  
  public final void prepare(String paramString, VoucherParams paramVoucherParams, Bundle paramBundle, Purchase.AuthenticationInfo paramAuthenticationInfo, InstantPurchaseUtils.InstantPurchaseParams paramInstantPurchaseParams, Boolean paramBoolean, Map<String, String> paramMap)
  {
    this.mIsInstantPurchase = false;
    this.mAuthenticationInfo = paramAuthenticationInfo;
    log(302, paramInstantPurchaseParams);
    Map localMap = bundleToMap(paramBundle);
    localMap.putAll(paramMap);
    this.mPrepareStartedMs = System.currentTimeMillis();
    if (FinskyApp.get().getExperiments().isEnabled(12603427L))
    {
      PlayAccountProto.PlayAccountUserPurchaseCache localPlayAccountUserPurchaseCache = UserSettingsCache.getPlayAccountUserPurchaseCache(this.mDfeApi.getAccountName());
      if (localPlayAccountUserPurchaseCache != null) {
        localMap.put("upc", DfeUtils.base64Encode(MessageNano.toByteArray(localPlayAccountUserPurchaseCache)));
      }
    }
    String str = null;
    if (paramInstantPurchaseParams != null)
    {
      str = paramInstantPurchaseParams.getClientDisabledReasonsString();
      byte[] arrayOfByte = paramInstantPurchaseParams.mServerLogsCookie;
      if ((arrayOfByte != null) && (arrayOfByte.length > 0)) {
        localMap.put("upcsc", DfeUtils.base64Encode(arrayOfByte));
      }
    }
    if (paramBoolean != null) {
      localMap.put("st", paramBoolean.toString());
    }
    this.mPrepareRequest = this.mDfeApi.preparePurchase(this.mPurchaseParams.docidStr, this.mPurchaseParams.offerType, this.mPurchaseParams.offerId, this.mPurchaseParams.inAppPurchaseInfo, this.mAuthenticationInfo, paramString, this.mPurchaseParams.indirectProvisioningType, paramVoucherParams, this.mPurchaseParams.appVersionCode, str, localMap, new PrepareListener((byte)0), new PrepareErrorListener((byte)0));
    this.mCompletePurchaseStartedMs = 0L;
    this.mCompletePurchaseRequest = null;
    setState(1, 1);
  }
  
  protected final void restoreFromSavedInstanceState(Bundle paramBundle)
  {
    super.restoreFromSavedInstanceState(paramBundle);
    this.mAuthenticationInfo = ((Purchase.AuthenticationInfo)ParcelableProto.getProtoFromBundle(paramBundle, "CheckoutPurchaseSidecar.authenticationInfo"));
    this.mCart = ((Purchase.ClientCart)ParcelableProto.getProtoFromBundle(paramBundle, "CheckoutPurchaseSidecar.cart"));
    this.mChangeSubscription = ((Purchase.ChangeSubscription)ParcelableProto.getProtoFromBundle(paramBundle, "CheckoutPurchaseSidecar.changeSubscription"));
    this.mIsInstantPurchase = paramBundle.getBoolean("CheckoutPurchaseSidecar.isInstantPurchase");
    this.mCartExpandedAtStart = paramBundle.getBoolean("CheckoutPurchaseSidecar.cartExpandedAtStart");
    this.mPrepareChallenge = ((ChallengeProto.Challenge)ParcelableProto.getProtoFromBundle(paramBundle, "CheckoutPurchaseSidecar.prepareChallenge"));
    this.mCompleteChallenge = ((ChallengeProto.Challenge)ParcelableProto.getProtoFromBundle(paramBundle, "CheckoutPurchaseSidecar.completeChallenge"));
    this.mExtraPurchaseData = paramBundle.getBundle("CheckoutPurchaseSidecar.extraPurchaseData");
    this.mPostAcquisitionPrompt = ((Acquisition.PostAcquisitionPrompt)ParcelableProto.getProtoFromBundle(paramBundle, "CheckoutPurchaseSidecar.post_acquisition_prompt"));
    this.mCheckoutPurchaseError = ((CheckoutPurchaseError)paramBundle.getParcelable("CheckoutPurchaseSidecar.checkoutPurchaseError"));
    this.mServerLogsCookie = paramBundle.getByteArray("CheckoutPurchaseSidecar.serverLogsCookie");
    this.mLegalDocuments = ((PlayAccountProto.CachedPaymentsLegalDocument[])ParcelableProtoArray.getProtoArrayFromBundle(paramBundle, "CheckoutPurchaseSidecar.legalDocuments"));
    this.mGiftEmailParams = ((GiftEmailParams)paramBundle.getParcelable("CheckoutPurchaseSidecar.giftEmailParams"));
  }
  
  public final void updateGiftEmailParams(GiftEmailParams paramGiftEmailParams)
  {
    if (this.mGiftEmailParams == null)
    {
      this.mGiftEmailParams = paramGiftEmailParams;
      setState(13, 0);
      return;
    }
    setState(14, 0);
    this.mGiftEmailParams = null;
  }
  
  private final class CommitListener
    implements Response.Listener<Purchase.CommitPurchaseResponse>
  {
    private CommitListener() {}
  }
  
  private final class CompletePurchaseErrorListener
    implements Response.ErrorListener
  {
    private CompletePurchaseErrorListener() {}
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      CheckoutPurchaseSidecar.access$2602(CheckoutPurchaseSidecar.this, paramVolleyError);
      CheckoutPurchaseSidecar.access$2900(CheckoutPurchaseSidecar.this, 305, paramVolleyError, CheckoutPurchaseSidecar.this.getCompletePurchaseServerLatencyMs(), CheckoutPurchaseSidecar.this.getTimeElapsedForCompletePurchaseMs());
      CheckoutPurchaseSidecar.access$3300$2f730cd3(CheckoutPurchaseSidecar.this);
    }
  }
  
  private final class EscrowErrorListener
    implements Response.ErrorListener
  {
    private EscrowErrorListener() {}
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      CheckoutPurchaseSidecar.access$2602(CheckoutPurchaseSidecar.this, paramVolleyError);
      CheckoutPurchaseSidecar.access$2900(CheckoutPurchaseSidecar.this, 306, paramVolleyError, -1L, -1L);
      CheckoutPurchaseSidecar.access$3400$2f730cd3(CheckoutPurchaseSidecar.this);
    }
  }
  
  private final class EscrowResponseListener
    implements Response.Listener<String>
  {
    private EscrowResponseListener() {}
  }
  
  private final class InstantPurchaseListener
    implements Response.Listener<Purchase.InstantPurchaseResponse>
  {
    private InstantPurchaseListener() {}
  }
  
  private final class PrepareErrorListener
    implements Response.ErrorListener
  {
    private PrepareErrorListener() {}
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      CheckoutPurchaseSidecar.access$2602(CheckoutPurchaseSidecar.this, paramVolleyError);
      CheckoutPurchaseSidecar.access$2900(CheckoutPurchaseSidecar.this, 303, paramVolleyError, CheckoutPurchaseSidecar.this.getPrepareServerLatencyMs(), CheckoutPurchaseSidecar.this.getTimeElapsedSincePrepareMs());
      CheckoutPurchaseSidecar.access$3000$2f730cd3(CheckoutPurchaseSidecar.this);
    }
  }
  
  private final class PrepareListener
    implements Response.Listener<Purchase.PreparePurchaseResponse>
  {
    private PrepareListener() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar
 * JD-Core Version:    0.7.0.1
 */