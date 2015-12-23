package com.google.android.finsky.billing.lightpurchase.billingprofile;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingUtils;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.billing.instrumentmanager.InstrumentManagerActivity;
import com.google.android.finsky.config.G;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfile;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfileOption;
import com.google.android.finsky.protos.BuyInstruments.BillingProfileResponse;
import com.google.android.finsky.protos.CarrierBillingInstrumentStatus;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.wallet.common.pub.OrchestrationUtil;
import java.util.HashMap;
import java.util.Map;

public final class BillingProfileSidecar
  extends SidecarFragment
  implements Response.ErrorListener, Response.Listener<BuyInstruments.BillingProfileResponse>
{
  public static final Uri EDIT_FOOTER_URI = Uri.parse((String)G.editPaymentMethodUrl.get());
  public Account mAccount;
  private byte[] mBackgroundEventServerLogsCookie;
  private int mBillingProfileFlow;
  private BuyInstruments.BillingProfileResponse mBillingProfileResponse;
  private int mCreditCardEventType;
  private int mDcbEventType;
  private DfeApi mDfeApi;
  private int mEditEventType;
  public String mErrorMessageHtml;
  private FinskyEventLog mEventLog;
  private Map<String, String> mExtraPostParams;
  public String mLastUpdatedInstrumentId;
  private int mPaypalEventType;
  private String mPurchaseContextToken;
  private Intent mRedeemCodeIntent;
  public RedeemCodeResult mRedeemCodeResult;
  private int mRedeemEventType;
  public SetupWizardUtils.SetupWizardParams mSetupWizardParams;
  public String mStoredValueInstrumentId;
  private int mTopupEventType;
  public VolleyError mVolleyError;
  
  private void logResponse(int paramInt, Throwable paramThrowable)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(344);
    if (paramInt == 0) {
      localBackgroundEventBuilder.setOperationSuccess(true);
    }
    for (;;)
    {
      localBackgroundEventBuilder.setBillingProfileFlow(this.mBillingProfileFlow);
      this.mEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
      return;
      localBackgroundEventBuilder.setOperationSuccess(false).setErrorCode(paramInt).setExceptionType(paramThrowable);
    }
  }
  
  public static BillingProfileSidecar newInstance(Account paramAccount, String paramString, SetupWizardUtils.SetupWizardParams paramSetupWizardParams, Intent paramIntent, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, byte[] paramArrayOfByte)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("BillingProfileSidecar.account", paramAccount);
    localBundle.putString("BillingProfileSidecar.purchaseContextToken", paramString);
    localBundle.putParcelable("BillingProfileSidecar.setupWizardParams", paramSetupWizardParams);
    localBundle.putParcelable("BillingProfileSidecar.redeemCodeIntent", paramIntent);
    localBundle.putInt("BillingProfileSidecar.billingProfileFlow", paramInt1);
    localBundle.putInt("BillingProfileSidecar.creditCardEventType", paramInt2);
    localBundle.putInt("BillingProfileSidecar.dcbEventType", paramInt3);
    localBundle.putInt("BillingProfileSidecar.paypalEventType", paramInt4);
    localBundle.putInt("BillingProfileSidecar.redeemEventType", paramInt5);
    localBundle.putInt("BillingProfileSidecar.topupEventType", paramInt6);
    localBundle.putInt("BillingProfileSidecar.editEventType", paramInt7);
    localBundle.putByteArray("BillingProfileSidecar.backgroundEventServerLogsCookies", paramArrayOfByte);
    BillingProfileSidecar localBillingProfileSidecar = new BillingProfileSidecar();
    localBillingProfileSidecar.setArguments(localBundle);
    return localBillingProfileSidecar;
  }
  
  public final FopActionEntry billingProfileOptionToActionEntry(final BillingProfileProtos.BillingProfileOption paramBillingProfileOption, final byte[] paramArrayOfByte, final PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    switch (paramBillingProfileOption.type)
    {
    case 5: 
    default: 
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = Integer.valueOf(paramBillingProfileOption.type);
      arrayOfObject3[1] = paramBillingProfileOption.displayTitle;
      FinskyLog.w("Skipping unknown option: type=%d, displayTitle=%s", arrayOfObject3);
      return null;
    case 2: 
      if ((paramBillingProfileOption.carrierBillingInstrumentStatus != null) && (paramBillingProfileOption.carrierBillingInstrumentStatus.apiVersion == 3)) {
        new FopActionEntry(paramBillingProfileOption, new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            BillingProfileSidecar.this.mEventLog.logClickEvent(811, paramBillingProfileOption.serverLogsCookie, paramPlayStoreUiElementNode);
            BillingProfileSidecar.access$100(BillingProfileSidecar.this, paramBillingProfileOption.carrierBillingInstrumentStatus);
          }
        }, 811);
      }
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramBillingProfileOption.displayTitle;
      FinskyLog.w("Skipping unknown DCB type, displayTitle=%s", arrayOfObject2);
      return null;
    case 3: 
      new FopActionEntry(paramBillingProfileOption, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          BillingProfileSidecar.this.mEventLog.logClickEvent(812, paramBillingProfileOption.serverLogsCookie, paramPlayStoreUiElementNode);
          BillingProfileSidecar.access$200(BillingProfileSidecar.this);
        }
      }, 812);
    case 4: 
      new FopActionEntry(paramBillingProfileOption, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          BillingProfileSidecar.this.mEventLog.logClickEvent(813, paramBillingProfileOption.serverLogsCookie, paramPlayStoreUiElementNode);
          BillingProfileSidecar.access$300(BillingProfileSidecar.this, paramBillingProfileOption.topupInfo);
        }
      }, 813);
    }
    final int i;
    final int j;
    if ("CREDIT_CARD".equals(paramBillingProfileOption.typeName))
    {
      i = 810;
      j = 7;
    }
    for (;;)
    {
      new FopActionEntry(paramBillingProfileOption, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          BillingProfileSidecar.this.mEventLog.logClickEvent(i, paramBillingProfileOption.serverLogsCookie, paramPlayStoreUiElementNode);
          BillingProfileSidecar.access$400(BillingProfileSidecar.this, paramArrayOfByte, paramBillingProfileOption.paymentsIntegratorInstrumentToken, j);
        }
      }, i);
      if ("PAYPAL".equals(paramBillingProfileOption.typeName))
      {
        i = 814;
        j = 8;
      }
      else if ("CARRIER_BILLING".equals(paramBillingProfileOption.typeName))
      {
        i = 811;
        j = 9;
      }
      else
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramBillingProfileOption.typeName;
        FinskyLog.wtf("Unexpected typeName=%s", arrayOfObject1);
        i = -1;
        j = 6;
      }
    }
  }
  
  public final void editInstrument(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    startActivityForResult(InstrumentManagerActivity.createIntent(this.mAccount.name, paramArrayOfByte2, paramArrayOfByte1, Bundle.EMPTY), 10);
  }
  
  public final BillingProfileProtos.BillingProfile getBillingProfile()
  {
    if (this.mBillingProfileResponse == null) {
      return null;
    }
    return this.mBillingProfileResponse.billingProfile;
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    int i;
    if (paramInt2 == -1) {
      switch (paramInt1)
      {
      case 3: 
      case 6: 
      default: 
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt1);
        FinskyLog.wtf("Unexpected requestCode=%d", arrayOfObject);
        i = -1;
        switch (paramInt1)
        {
        }
        break;
      }
    }
    for (;;)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      i = this.mCreditCardEventType;
      break;
      i = this.mDcbEventType;
      break;
      i = this.mRedeemEventType;
      break;
      i = this.mTopupEventType;
      break;
      i = this.mPaypalEventType;
      break;
      i = this.mEditEventType;
      break;
      String str4 = paramIntent.getStringExtra("instrument_id");
      if (!TextUtils.isEmpty(str4))
      {
        this.mEventLog.logBackgroundEvent(i, this.mBackgroundEventServerLogsCookie);
        this.mLastUpdatedInstrumentId = str4;
        setState(4, 0);
        continue;
        RedeemCodeResult localRedeemCodeResult = (RedeemCodeResult)paramIntent.getParcelableExtra("RedeemCodeBaseActivity.redeem_code_result");
        if (localRedeemCodeResult != null)
        {
          this.mEventLog.logBackgroundEvent(i, this.mBackgroundEventServerLogsCookie);
          this.mRedeemCodeResult = localRedeemCodeResult;
          String str3 = this.mRedeemCodeResult.mStoredValueInstrumentId;
          if (TextUtils.isEmpty(str3))
          {
            setState(6, 0);
          }
          else
          {
            this.mStoredValueInstrumentId = str3;
            setState(5, 1);
            continue;
            this.mEventLog.logBackgroundEvent(i, this.mBackgroundEventServerLogsCookie);
            setState(5, 2);
            continue;
            String str2 = paramIntent.getStringExtra("instrument_id");
            if (!TextUtils.isEmpty(str2))
            {
              this.mEventLog.logBackgroundEvent(i, this.mBackgroundEventServerLogsCookie);
              this.mLastUpdatedInstrumentId = str2;
              setState(4, 0);
              continue;
              String str1 = paramIntent.getStringExtra("instrument_id");
              if (!TextUtils.isEmpty(str1))
              {
                this.mEventLog.logBackgroundEvent(i, this.mBackgroundEventServerLogsCookie);
                this.mLastUpdatedInstrumentId = str1;
                setState(7, 0);
                continue;
                this.mEventLog.logBackgroundEvent(0, null);
                setState(8, 0);
              }
            }
          }
        }
      }
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    this.mAccount = ((Account)localBundle.getParcelable("BillingProfileSidecar.account"));
    this.mPurchaseContextToken = localBundle.getString("BillingProfileSidecar.purchaseContextToken");
    this.mSetupWizardParams = ((SetupWizardUtils.SetupWizardParams)localBundle.getParcelable("BillingProfileSidecar.setupWizardParams"));
    this.mRedeemCodeIntent = ((Intent)localBundle.getParcelable("BillingProfileSidecar.redeemCodeIntent"));
    this.mDfeApi = FinskyApp.get().getDfeApi(this.mAccount.name);
    this.mBillingProfileFlow = localBundle.getInt("BillingProfileSidecar.billingProfileFlow");
    this.mCreditCardEventType = localBundle.getInt("BillingProfileSidecar.creditCardEventType");
    this.mDcbEventType = localBundle.getInt("BillingProfileSidecar.dcbEventType");
    this.mPaypalEventType = localBundle.getInt("BillingProfileSidecar.paypalEventType");
    this.mRedeemEventType = localBundle.getInt("BillingProfileSidecar.redeemEventType");
    this.mTopupEventType = localBundle.getInt("BillingProfileSidecar.topupEventType");
    this.mEditEventType = localBundle.getInt("BillingProfileSidecar.editEventType");
    this.mBackgroundEventServerLogsCookie = localBundle.getByteArray("BillingProfileSidecar.backgroundEventServerLogsCookies");
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccount);
    super.onCreate(paramBundle);
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    logResponse(1, paramVolleyError);
    this.mVolleyError = paramVolleyError;
    setState(3, 4);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("BillingProfileSidecar.billingProfileResponse", ParcelableProto.forProto(this.mBillingProfileResponse));
  }
  
  public final void requestBillingProfile()
  {
    if (this.mExtraPostParams == null)
    {
      this.mExtraPostParams = new HashMap();
      int i = BillingUtils.getInstrumentManagerThemeResourceId(this.mSetupWizardParams);
      CarrierBillingUtils.addPrepareOrBillingProfileParams(true, this.mExtraPostParams);
      this.mExtraPostParams.put("bpif", String.valueOf(this.mBillingProfileFlow));
      this.mExtraPostParams.put("bppcc", Base64.encodeToString(OrchestrationUtil.createClientToken(getActivity(), i), 8));
    }
    Map localMap = this.mExtraPostParams;
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(343);
    localBackgroundEventBuilder.setBillingProfileFlow(this.mBillingProfileFlow);
    this.mEventLog.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
    setState(1, 0);
    this.mDfeApi.billingProfile(this.mPurchaseContextToken, localMap, this, this);
  }
  
  protected final void restoreFromSavedInstanceState(Bundle paramBundle)
  {
    super.restoreFromSavedInstanceState(paramBundle);
    this.mBillingProfileResponse = ((BuyInstruments.BillingProfileResponse)ParcelableProto.getProtoFromBundle(paramBundle, "BillingProfileSidecar.billingProfileResponse"));
  }
  
  public final boolean shouldLogScreen()
  {
    return !getActivity().isFinishing();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileSidecar
 * JD-Core Version:    0.7.0.1
 */