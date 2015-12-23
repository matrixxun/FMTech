package com.google.android.finsky.billing.lightpurchase.billingprofile.instruments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;

public class SetupWizardRedeemCodeActivity
  extends RedeemCodeBaseActivity
{
  private SetupWizardUtils.SetupWizardParams mSetupWizardParam;
  private TextView mTitleView;
  
  public static Intent createIntent(String paramString, SetupWizardUtils.SetupWizardParams paramSetupWizardParams)
  {
    Intent localIntent = new Intent(FinskyApp.get(), SetupWizardRedeemCodeActivity.class);
    putIntentExtras(localIntent, paramString, 4, -1, null, 0, null, null);
    localIntent.putExtra("setup_wizard_params", paramSetupWizardParams);
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
  
  protected final int getInstrumentManagerThemeResourceId()
  {
    return BillingUtils.getInstrumentManagerThemeResourceId(this.mSetupWizardParam);
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 898;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    this.mSetupWizardParam = ((SetupWizardUtils.SetupWizardParams)getIntent().getParcelableExtra("setup_wizard_params"));
    if (this.mSetupWizardParam.mIsLightTheme) {}
    for (int i = 2131558753;; i = 2131558650)
    {
      setTheme(i);
      super.onCreate(paramBundle);
      this.mTitleView = ((TextView)findViewById(2131755173));
      SetupWizardUtils.configureBasicUi(this, this.mSetupWizardParam, 0, true, true, true);
      return;
    }
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.mTitleView.setText(paramCharSequence);
  }
  
  protected final boolean shouldDisableAppCompatDelegation()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.SetupWizardRedeemCodeActivity
 * JD-Core Version:    0.7.0.1
 */