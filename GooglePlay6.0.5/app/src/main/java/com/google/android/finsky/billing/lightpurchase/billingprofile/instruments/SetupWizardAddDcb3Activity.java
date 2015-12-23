package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.protos.CarrierBillingInstrumentStatus;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;

public class SetupWizardAddDcb3Activity
  extends AddDcb3BaseActivity
{
  public static Intent createIntent(String paramString, CarrierBillingInstrumentStatus paramCarrierBillingInstrumentStatus, SetupWizardUtils.SetupWizardParams paramSetupWizardParams)
  {
    Intent localIntent = new Intent(FinskyApp.get(), SetupWizardAddDcb3Activity.class);
    putIntentExtras(paramString, paramCarrierBillingInstrumentStatus, localIntent);
    localIntent.putExtra("InstrumentActivity.setup_wizard_params", paramSetupWizardParams);
    return localIntent;
  }
  
  public void finish()
  {
    super.finish();
    SetupWizardUtils.animateSliding(this, true);
  }
  
  protected final int getActivityLayout()
  {
    return 2130969107;
  }
  
  protected final int getBillingUiMode()
  {
    return 1;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 894;
  }
  
  public final void hideProgress()
  {
    findViewById(2131755740).setVisibility(0);
    findViewById(2131755316).setVisibility(4);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    SetupWizardUtils.SetupWizardParams localSetupWizardParams = (SetupWizardUtils.SetupWizardParams)getIntent().getParcelableExtra("InstrumentActivity.setup_wizard_params");
    if (localSetupWizardParams.mIsLightTheme) {}
    for (int i = 2131558753;; i = 2131558650)
    {
      setTheme(i);
      super.onCreate(paramBundle);
      SetupWizardUtils.configureBasicUi(this, localSetupWizardParams, 0, true, true, true);
      return;
    }
  }
  
  protected final boolean shouldDisableAppCompatDelegation()
  {
    return true;
  }
  
  public final void showProgress(int paramInt)
  {
    findViewById(2131755740).setVisibility(4);
    findViewById(2131755316).setVisibility(0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.SetupWizardAddDcb3Activity
 * JD-Core Version:    0.7.0.1
 */