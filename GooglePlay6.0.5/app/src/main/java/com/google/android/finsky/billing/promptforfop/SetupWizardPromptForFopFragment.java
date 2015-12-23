package com.google.android.finsky.billing.promptforfop;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;

public final class SetupWizardPromptForFopFragment
  extends PromptForFopFragment
{
  private SetupWizardUtils.SetupWizardParams mSetupWizardParams;
  
  public static Fragment newInstance(Account paramAccount, byte[] paramArrayOfByte, SetupWizardUtils.SetupWizardParams paramSetupWizardParams)
  {
    SetupWizardPromptForFopFragment localSetupWizardPromptForFopFragment = new SetupWizardPromptForFopFragment();
    Bundle localBundle = buildArgumentsBundle(paramAccount, paramArrayOfByte);
    localBundle.putParcelable("setup_wizard_params", paramSetupWizardParams);
    localSetupWizardPromptForFopFragment.setArguments(localBundle);
    return localSetupWizardPromptForFopFragment;
  }
  
  protected final void configureContinueButtonStyle(View paramView)
  {
    if ((paramView instanceof Button))
    {
      ((Button)paramView).setText(getString(2131362062));
      return;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramView.getClass().getSimpleName();
    FinskyLog.wtf("Unexpected continue button type: %s", arrayOfObject);
  }
  
  protected final int determineUiMode()
  {
    if (SetupWizardUtils.shouldUseMaterialTheme()) {
      return 2;
    }
    return 1;
  }
  
  protected final int getActionEntryLayout()
  {
    return 2130969105;
  }
  
  protected final int getBillingProfileRequestEnum()
  {
    return 3;
  }
  
  protected final int getCreditCardEventType()
  {
    return 360;
  }
  
  protected final int getDcbEventType()
  {
    return 361;
  }
  
  protected final int getGenericInstrumentEventType()
  {
    return 367;
  }
  
  protected final int getMainLayout()
  {
    return 2130969108;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 893;
  }
  
  protected final int getPrimerStringId()
  {
    return 2131362751;
  }
  
  protected final int getRedeemEventType()
  {
    return 362;
  }
  
  protected final SetupWizardUtils.SetupWizardParams getSetupWizardParams()
  {
    return this.mSetupWizardParams;
  }
  
  protected final int getTopupEventType()
  {
    return 363;
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSetupWizardParams = ((SetupWizardUtils.SetupWizardParams)this.mArguments.getParcelable("setup_wizard_params"));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.promptforfop.SetupWizardPromptForFopFragment
 * JD-Core Version:    0.7.0.1
 */