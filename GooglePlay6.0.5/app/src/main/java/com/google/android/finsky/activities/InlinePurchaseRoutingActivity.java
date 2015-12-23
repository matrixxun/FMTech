package com.google.android.finsky.activities;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.lightpurchase.LightPurchaseFlowActivity;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SignatureUtils;
import com.google.android.play.utils.config.GservicesValue;

public class InlinePurchaseRoutingActivity
  extends AuthenticatedActivity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Account localAccount = FinskyApp.get().getCurrentAccount();
    if (localAccount == null)
    {
      FinskyLog.w("No account configured on this device.", new Object[0]);
      finish();
      return;
    }
    String str1 = SignatureUtils.getCallingFirstPartyPackage(this);
    if ((str1 == null) && (!((Boolean)G.enableThirdPartyInlineAppInstalls.get()).booleanValue()))
    {
      FinskyLog.w("Called from untrusted package.", new Object[0]);
      finish();
      return;
    }
    Intent localIntent1 = getIntent();
    String str2 = localIntent1.getStringExtra("docid");
    if (TextUtils.isEmpty(str2))
    {
      FinskyLog.e("Missing docid.", new Object[0]);
      finish();
      return;
    }
    String str3 = localIntent1.getStringExtra("referrer");
    if (FinskyApp.get().getExperiments(localAccount.name).isEnabled(12603431L)) {}
    for (Intent localIntent2 = LightPurchaseFlowActivity.createExternalPurchaseIntent$109c371d(str2, str2, str3, 3, 1);; localIntent2 = InlineAppDetailsDialog.createIntent(str2, str3, str1, localIntent1.getStringExtra("referrer_url")))
    {
      localIntent2.setFlags(33554432);
      startActivity(localIntent2);
      finish();
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.InlinePurchaseRoutingActivity
 * JD-Core Version:    0.7.0.1
 */