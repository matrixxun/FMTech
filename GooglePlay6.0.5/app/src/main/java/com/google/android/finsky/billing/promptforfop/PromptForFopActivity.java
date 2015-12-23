package com.google.android.finsky.billing.promptforfop;

import android.accounts.Account;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.google.android.finsky.FinskyApp;

public class PromptForFopActivity
  extends PromptForFopBaseActivity
  implements PromptForFopMessageFragment.Listener
{
  public static Intent createIntent(Account paramAccount, byte[] paramArrayOfByte)
  {
    Intent localIntent = new Intent(FinskyApp.get(), PromptForFopActivity.class);
    putIntentExtras(paramAccount, paramArrayOfByte, localIntent);
    return localIntent;
  }
  
  protected final Fragment createContentFragment()
  {
    return PromptForFopFragment.newInstance(this.mAccount, this.mServerLogsCookie);
  }
  
  protected final void displayMessage(int paramInt1, int paramInt2)
  {
    PromptForFopMessageFragment localPromptForFopMessageFragment = PromptForFopMessageFragment.newInstance(this.mAccount.name, getString(paramInt1), paramInt2);
    getSupportFragmentManager().beginTransaction().replace(2131755234, localPromptForFopMessageFragment, "PromptForFopBaseActivity.fragment").commit();
  }
  
  protected final int getActivityLayout()
  {
    return 2130969024;
  }
  
  protected final int getAlreadySetupEventType()
  {
    return 355;
  }
  
  protected final int getBillingProfileErrorEventType()
  {
    return 356;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1001;
  }
  
  protected final int getSnoozeEventType()
  {
    return 354;
  }
  
  public final void onContinueClicked()
  {
    setResult(-1);
    finish();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.promptforfop.PromptForFopActivity
 * JD-Core Version:    0.7.0.1
 */