package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.billing.iab.PendingNotificationsService;
import com.google.android.finsky.services.DailyHygiene;

public class BootCompletedReceiver
  extends BroadcastReceiver
{
  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    
    if (AccountHandler.getFirstAccount(paramContext) == null) {
      return;
    }
    FinskyApp.get().clearCacheAsync(new Runnable()
    {
      public final void run()
      {
        Intent localIntent = new Intent(PendingNotificationsService.ACTION_RESTART_ALARM);
        localIntent.setClass(paramContext, PendingNotificationsService.class);
        paramContext.startService(localIntent);
        DailyHygiene.schedule(paramContext, DailyHygiene.BOOT_DELAY_MS);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.BootCompletedReceiver
 * JD-Core Version:    0.7.0.1
 */