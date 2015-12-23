package com.google.android.finsky.billing.instrumentmanager;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;

public class SetupWizardInstrumentManagerActivity
  extends InstrumentManagerBaseActivity
{
  private SetupWizardUtils.SetupWizardParams mSetupWizardParams;
  
  public static Intent createIntent(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, Bundle paramBundle, SetupWizardUtils.SetupWizardParams paramSetupWizardParams)
  {
    Intent localIntent = new Intent(FinskyApp.get(), SetupWizardInstrumentManagerActivity.class);
    InstrumentManagerBaseActivity.putIntentExtras(paramString, paramArrayOfByte1, paramArrayOfByte2, paramBundle, localIntent);
    localIntent.putExtra("setupWizardParams", paramSetupWizardParams);
    return localIntent;
  }
  
  public void finish()
  {
    super.finish();
    SetupWizardUtils.animateSliding(this, true);
  }
  
  protected final int getInstrumentManagerThemeResourceId()
  {
    return BillingUtils.getInstrumentManagerThemeResourceId(this.mSetupWizardParams);
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1601;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    this.mSetupWizardParams = ((SetupWizardUtils.SetupWizardParams)getIntent().getParcelableExtra("setupWizardParams"));
    if (this.mSetupWizardParams.mIsLightTheme) {}
    for (int i = 2131558753;; i = 2131558650)
    {
      setTheme(i);
      super.onCreate(paramBundle);
      SetupWizardUtils.configureBasicUiWithoutNavBarOrImage(this, this.mSetupWizardParams, true);
      if (SetupWizardUtils.shouldUseMaterialTheme()) {
        SetupWizardUtils.configureBasicMaterialUiWithoutNavBarOrImage(this, this.mSetupWizardParams, true);
      }
      return;
    }
  }
  
  protected final boolean shouldDisableAppCompatDelegation()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.instrumentmanager.SetupWizardInstrumentManagerActivity
 * JD-Core Version:    0.7.0.1
 */