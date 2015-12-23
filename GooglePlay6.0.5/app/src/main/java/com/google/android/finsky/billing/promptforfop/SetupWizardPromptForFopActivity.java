package com.google.android.finsky.billing.promptforfop;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;

public class SetupWizardPromptForFopActivity
  extends PromptForFopBaseActivity
{
  private SetupWizardUtils.SetupWizardParams mSetupWizardParams;
  
  public static Intent createExternalSetupWizardIntent(Account paramAccount)
  {
    Intent localIntent = new Intent(FinskyApp.get(), SetupWizardPromptForFopActivity.class);
    putIntentExtras(paramAccount, null, localIntent);
    localIntent.putExtra("via_create_intent", true);
    return localIntent;
  }
  
  protected final Fragment createContentFragment()
  {
    return SetupWizardPromptForFopFragment.newInstance(this.mAccount, this.mServerLogsCookie, this.mSetupWizardParams);
  }
  
  protected final void displayMessage(int paramInt1, int paramInt2)
  {
    setResult(-1);
    finish();
  }
  
  public void finish()
  {
    super.finish();
    SetupWizardUtils.animateSliding(this, false);
  }
  
  protected final int getActivityLayout()
  {
    return 2130969107;
  }
  
  protected final int getAlreadySetupEventType()
  {
    return 365;
  }
  
  protected final int getBillingProfileErrorEventType()
  {
    return 366;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 892;
  }
  
  protected final int getSnoozeEventType()
  {
    return 364;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    Intent localIntent = getIntent();
    if ((!localIntent.getBooleanExtra("via_create_intent", false)) && ("com.android.vending.billing.ADD_CREDIT_CARD".equals(localIntent.getAction())))
    {
      if (!localIntent.hasExtra("authAccount"))
      {
        FinskyLog.e("No account name passed.", new Object[0]);
        setResult(-1);
        finish();
        return;
      }
      String str = localIntent.getStringExtra("authAccount");
      Account localAccount = AccountHandler.findAccount(str, this);
      if (localAccount == null)
      {
        FinskyLog.e("Cannot find the account: %s", new Object[] { str });
        setResult(-1);
        finish();
        return;
      }
      putIntentExtras(localAccount, null, localIntent);
    }
    this.mSetupWizardParams = ((SetupWizardUtils.SetupWizardParams)localIntent.getParcelableExtra("setup_wizard_params"));
    if (this.mSetupWizardParams == null) {
      this.mSetupWizardParams = new SetupWizardUtils.SetupWizardParams(localIntent);
    }
    if (this.mSetupWizardParams.mIsLightTheme) {}
    for (int i = 2131558753;; i = 2131558650)
    {
      setTheme(i);
      super.onCreate(paramBundle);
      ((TextView)findViewById(2131755173)).setText(2131362743);
      SetupWizardUtils.configureBasicUi(this, this.mSetupWizardParams, 0, false, false, false);
      return;
    }
  }
  
  protected final boolean shouldDisableAppCompatDelegation()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.promptforfop.SetupWizardPromptForFopActivity
 * JD-Core Version:    0.7.0.1
 */