package com.google.android.finsky.billing.instrumentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.LoggingFragmentActivity;
import com.google.android.wallet.common.api.WalletRequestQueue;
import com.google.android.wallet.common.pub.BaseOrchestrationFragment.ResultListener;
import com.google.android.wallet.instrumentmanager.pub.InstrumentManagerFragment;
import com.google.android.wallet.nfc.NfcIntentForwarder;

public abstract class InstrumentManagerBaseActivity
  extends LoggingFragmentActivity
  implements BaseOrchestrationFragment.ResultListener
{
  private Fragment getInstrumentManagerFragment()
  {
    return getSupportFragmentManager().findFragmentById(2131755234);
  }
  
  protected static void putIntentExtras(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, Bundle paramBundle, Intent paramIntent)
  {
    LoggingFragmentActivity.addAccountNameExtra(paramIntent, paramString);
    paramIntent.putExtra("common_token", paramArrayOfByte1);
    paramIntent.putExtra("action_token", paramArrayOfByte2);
    paramIntent.putExtra("instrument_manager_args", paramBundle);
  }
  
  private void updateActivityResultFromOrchestrationResult(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException("Unexpected InstrumentManager resultCode: " + paramInt);
    case 50: 
      String str = paramBundle.getString("com.google.android.wallet.instrumentmanager.INSTRUMENT_ID");
      Intent localIntent = new Intent();
      localIntent.putExtra("instrument_id", str);
      setResult(-1, localIntent);
      return;
    }
    setResult(0);
  }
  
  protected abstract int getInstrumentManagerThemeResourceId();
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968800);
    com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventDispatcher.sListener = new InstrumentManagerLogger(this, this.mEventLog);
    WalletRequestQueue.setApiRequestQueue(FinskyApp.get().mRequestQueue);
    WalletRequestQueue.setImageRequestQueue(FinskyApp.get().mBitmapRequestQueue);
    if (getInstrumentManagerFragment() == null)
    {
      byte[] arrayOfByte1 = getIntent().getByteArrayExtra("common_token");
      byte[] arrayOfByte2 = getIntent().getByteArrayExtra("action_token");
      Bundle localBundle = (Bundle)getIntent().getParcelableExtra("instrument_manager_args");
      InstrumentManagerFragment localInstrumentManagerFragment = InstrumentManagerFragment.newInstance(AccountHandler.findAccount(this.mAccountName, this), arrayOfByte1, arrayOfByte2, getInstrumentManagerThemeResourceId(), localBundle);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(2131755234, localInstrumentManagerFragment);
      localFragmentTransaction.commit();
    }
  }
  
  protected void onDestroy()
  {
    com.google.android.wallet.instrumentmanager.pub.analytics.InstrumentManagerAnalyticsEventDispatcher.sListener = null;
    super.onDestroy();
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    Fragment localFragment = getInstrumentManagerFragment();
    NfcIntentForwarder localNfcIntentForwarder;
    if ((localFragment instanceof NfcIntentForwarder))
    {
      localNfcIntentForwarder = (NfcIntentForwarder)localFragment;
      if (localNfcIntentForwarder != null)
      {
        if (paramIntent == null) {
          break label82;
        }
        String str = paramIntent.getAction();
        if ((!str.equals("android.nfc.action.TAG_DISCOVERED")) && (!str.equals("android.nfc.action.TECH_DISCOVERED")) && (!str.equals("android.nfc.action.NDEF_DISCOVERED"))) {
          break label82;
        }
      }
    }
    label82:
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        localNfcIntentForwarder.forwardNfcIntent(paramIntent);
      }
      return;
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
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.instrumentmanager.InstrumentManagerBaseActivity
 * JD-Core Version:    0.7.0.1
 */