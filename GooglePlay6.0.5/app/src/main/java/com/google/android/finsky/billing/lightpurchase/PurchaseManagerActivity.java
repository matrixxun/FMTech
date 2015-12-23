package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.LoggingFragmentActivity;
import com.google.android.finsky.protos.ChallengeProto.PurchaseManagerChallenge;
import com.google.android.finsky.protos.ChallengeProto.PurchaseManagerPayload;
import com.google.android.finsky.protos.ChallengeProto.PurchaseManagerPayload.SecureData;
import com.google.android.wallet.common.pub.BaseOrchestrationFragment.ResultListener;
import com.google.android.wallet.common.pub.SecurePaymentsPayload;
import com.google.android.wallet.common.pub.SecurePaymentsPayload.SecurePaymentsData;
import com.google.android.wallet.purchasemanager.pub.PurchaseManagerFragment;

public class PurchaseManagerActivity
  extends LoggingFragmentActivity
  implements BaseOrchestrationFragment.ResultListener
{
  private Account mAccount;
  private boolean mIsChallengeFinishedEventLogged;
  
  public static Intent createIntent(Account paramAccount, ChallengeProto.PurchaseManagerChallenge paramPurchaseManagerChallenge)
  {
    ChallengeProto.PurchaseManagerPayload localPurchaseManagerPayload = paramPurchaseManagerChallenge.buyerActionPayload;
    int i = localPurchaseManagerPayload.secureData.length;
    SecurePaymentsPayload.SecurePaymentsData[] arrayOfSecurePaymentsData = new SecurePaymentsPayload.SecurePaymentsData[i];
    for (int j = 0; j < i; j++)
    {
      ChallengeProto.PurchaseManagerPayload.SecureData localSecureData = localPurchaseManagerPayload.secureData[j];
      arrayOfSecurePaymentsData[j] = new SecurePaymentsPayload.SecurePaymentsData(localSecureData.key, localSecureData.value);
    }
    SecurePaymentsPayload localSecurePaymentsPayload = new SecurePaymentsPayload(localPurchaseManagerPayload.opaqueToken, arrayOfSecurePaymentsData);
    Intent localIntent = new Intent(FinskyApp.get(), PurchaseManagerActivity.class);
    localIntent.putExtra("PurchaseManagerActivity.account", paramAccount);
    localIntent.putExtra("PurchaseManagerActivity.securePaymentPayload", localSecurePaymentsPayload);
    LoggingFragmentActivity.addAccountNameExtra(localIntent, paramAccount.name);
    return localIntent;
  }
  
  private void updateActivityResultFromOrchestrationResult(int paramInt, Bundle paramBundle)
  {
    int i = 0;
    switch (paramInt)
    {
    default: 
      throw new RuntimeException("Unexpected Orchestration Result");
    case 50: 
      Bundle localBundle = new Bundle();
      localBundle.putParcelable("PurchaseManagerActivity.securePaymentsPayload", paramBundle.getParcelable("com.google.android.wallet.purchasemanager.EXTRA_SECURE_PAYMENTS_PAYLOAD"));
      Intent localIntent = new Intent();
      localIntent.putExtra("challenge_response", localBundle);
      setResult(-1, localIntent);
    }
    for (;;)
    {
      if (!this.mIsChallengeFinishedEventLogged)
      {
        this.mIsChallengeFinishedEventLogged = true;
        FinskyApp.get().getEventLogger(this.mAccount).logBackgroundEvent(775, null, null, i, null, null);
      }
      return;
      i = paramInt;
      setResult(0);
    }
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1619;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    this.mAccount = ((Account)localIntent.getParcelableExtra("PurchaseManagerActivity.account"));
    if (getSupportFragmentManager().findFragmentByTag("PurchaseManagerActivity.fragment") == null)
    {
      PurchaseManagerFragment localPurchaseManagerFragment = PurchaseManagerFragment.newInstance(this.mAccount, (SecurePaymentsPayload)localIntent.getParcelableExtra("PurchaseManagerActivity.securePaymentPayload"), BillingUtils.getInstrumentManagerThemeResourceId(), Bundle.EMPTY);
      getSupportFragmentManager().beginTransaction().add(2131755234, localPurchaseManagerFragment, "PurchaseManagerActivity.fragment").commit();
      FinskyApp.get().getEventLogger(this.mAccount).logBackgroundEvent(774, null, null, 0, null, null);
    }
    if (paramBundle != null) {
      this.mIsChallengeFinishedEventLogged = paramBundle.getBoolean("PurchaseManagerActivity.isChallengeFinishedEventLogged");
    }
  }
  
  public final void onOrchestrationResult(int paramInt, Bundle paramBundle)
  {
    updateActivityResultFromOrchestrationResult(paramInt, paramBundle);
    finish();
  }
  
  public final void onQueuedOrchestrationResult(int paramInt, Bundle paramBundle)
  {
    updateActivityResultFromOrchestrationResult(50, paramBundle);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("PurchaseManagerActivity.isChallengeFinishedEventLogged", this.mIsChallengeFinishedEventLogged);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.PurchaseManagerActivity
 * JD-Core Version:    0.7.0.1
 */