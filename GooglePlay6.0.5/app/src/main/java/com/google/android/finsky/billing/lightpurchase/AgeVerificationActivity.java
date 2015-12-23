package com.google.android.finsky.billing.lightpurchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.lightpurchase.ageverification.AgeVerificationHostFragment;
import com.google.android.finsky.billing.lightpurchase.ageverification.AgeVerificationHostFragment.Listener;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.LoggingFragmentActivity;

public class AgeVerificationActivity
  extends LoggingFragmentActivity
  implements AgeVerificationHostFragment.Listener
{
  public static Intent createIntent(String paramString1, int paramInt, String paramString2)
  {
    Intent localIntent = new Intent(FinskyApp.get(), AgeVerificationActivity.class);
    localIntent.putExtra("authAccount", paramString1);
    localIntent.putExtra("AgeVerificationActivity.backend", paramInt);
    localIntent.putExtra("AgeVerificationActivity.docid_str", paramString2);
    return localIntent;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1400;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getLayoutInflater().inflate(2130968610, null));
    if (getSupportFragmentManager().findFragmentByTag("AgeVerificationActivity.host_fragment") == null)
    {
      Fragment localFragment = AgeVerificationHostFragment.newInstance(this.mAccountName, getIntent().getIntExtra("AgeVerificationActivity.backend", -1), getIntent().getStringExtra("AgeVerificationActivity.docid_str"));
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(2131755218, localFragment, "AgeVerificationActivity.host_fragment");
      localFragmentTransaction.commit();
    }
  }
  
  public final void onFinished(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = -1;; i = 0)
    {
      setResult(i);
      finish();
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.AgeVerificationActivity
 * JD-Core Version:    0.7.0.1
 */