package com.google.android.finsky.billing.promptforfop;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.billing.PromptForFopHelper;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.LoggingFragmentActivity;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.FinskyPreferences;

public abstract class PromptForFopBaseActivity
  extends LoggingFragmentActivity
  implements PromptForFopFragment.Listener
{
  protected Account mAccount;
  protected byte[] mServerLogsCookie;
  
  protected static void putIntentExtras(Account paramAccount, byte[] paramArrayOfByte, Intent paramIntent)
  {
    paramIntent.putExtra("PromptForFopBaseActivity.account", paramAccount);
    addAccountNameExtra(paramIntent, paramAccount.name);
    paramIntent.putExtra("PromptForFopBaseActivity.server_logs_cookie", paramArrayOfByte);
  }
  
  protected abstract Fragment createContentFragment();
  
  protected abstract void displayMessage(int paramInt1, int paramInt2);
  
  protected abstract int getActivityLayout();
  
  protected abstract int getAlreadySetupEventType();
  
  protected abstract int getBillingProfileErrorEventType();
  
  protected abstract int getSnoozeEventType();
  
  public final void onAlreadySetup()
  {
    this.mEventLog.logBackgroundEvent(getAlreadySetupEventType(), this.mServerLogsCookie);
    PromptForFopHelper.expireHasNoFop(this.mAccount.name);
    displayMessage(2131362732, 1003);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getActivityLayout());
    this.mAccount = ((Account)getIntent().getParcelableExtra("PromptForFopBaseActivity.account"));
    if (this.mAccount == null) {
      this.mAccount = AccountHandler.findAccount(this.mAccountName, this);
    }
    this.mServerLogsCookie = getIntent().getByteArrayExtra("PromptForFopBaseActivity.server_logs_cookie");
    if (paramBundle == null)
    {
      String str = this.mAccount.name;
      PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.promptForFopNumFopSelectorImpressions.get(str);
      localSharedPreference.put(Integer.valueOf(1 + ((Integer)localSharedPreference.get()).intValue()));
    }
  }
  
  public final void onFatalError$552c4e01()
  {
    this.mEventLog.logBackgroundEvent(getBillingProfileErrorEventType(), this.mServerLogsCookie);
    displayMessage(2131362730, 1004);
  }
  
  public final void onInstrumentCreated()
  {
    String str = this.mAccount.name;
    FinskyPreferences.promptForFopAddedFop.get(str).put(Boolean.valueOf(true));
    displayMessage(2131362732, 1005);
  }
  
  public final void onNoneSelected()
  {
    this.mEventLog.logBackgroundEvent(getSnoozeEventType(), this.mServerLogsCookie);
    PromptForFopHelper.snooze(this.mAccount.name);
    setResult(-1);
    finish();
  }
  
  protected void onResume()
  {
    super.onResume();
    if (getSupportFragmentManager().findFragmentByTag("PromptForFopBaseActivity.fragment") == null)
    {
      Fragment localFragment = createContentFragment();
      getSupportFragmentManager().beginTransaction().add(2131755234, localFragment, "PromptForFopBaseActivity.fragment").commit();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.promptforfop.PromptForFopBaseActivity
 * JD-Core Version:    0.7.0.1
 */