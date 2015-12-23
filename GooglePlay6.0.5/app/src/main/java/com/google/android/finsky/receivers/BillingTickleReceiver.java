package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.billing.iab.MarketBillingService;
import com.google.android.finsky.utils.FinskyLog;

public class BillingTickleReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("asset_package");
    setResultCode(-1);
    if (paramIntent.hasCategory("com.android.vending.billing.IN_APP_NOTIFY"))
    {
      if (!MarketBillingService.sendNotify(paramContext, str, paramIntent.getStringExtra("notification_id"))) {
        setResultCode(0);
      }
      return;
    }
    FinskyLog.w("Intent does not contain a supported category; package: %s", new Object[] { str });
    setResultCode(0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.BillingTickleReceiver
 * JD-Core Version:    0.7.0.1
 */