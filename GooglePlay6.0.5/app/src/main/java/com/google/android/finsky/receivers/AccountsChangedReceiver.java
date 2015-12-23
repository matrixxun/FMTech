package com.google.android.finsky.receivers;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.services.ContentSyncService;
import com.google.android.finsky.services.ContentSyncService.Facade;
import com.google.android.finsky.services.DailyHygiene;
import com.google.android.finsky.services.RestoreService;
import com.google.android.finsky.services.VpaService;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.VendingPreferences;

public class AccountsChangedReceiver
  extends BroadcastReceiver
{
  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    FinskyPreferences.autoUpdateFirstTimeForAccounts.put(Boolean.valueOf(true));
    FinskyApp.get().mLibraries.load(null);
    Account[] arrayOfAccount = AccountHandler.getAccounts(paramContext);
    String[] arrayOfString = VendingPreferences.getNewAccounts(arrayOfAccount);
    String str = (String)VendingPreferences.RESTORED_ANDROID_ID.get();
    if ((!TextUtils.isEmpty(str)) && (arrayOfString.length > 0))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(arrayOfString.length);
      FinskyLog.d("Restoring apps for %d new accounts.", arrayOfObject);
      RestoreService.restoreAccounts(paramContext, str, arrayOfString[0]);
    }
    if ((arrayOfAccount.length == 1) && (arrayOfString.length == 1)) {
      VpaService.startVpaForNewAccount();
    }
    VendingPreferences.saveCurrentAccountList(arrayOfAccount);
    DailyHygiene.schedule(paramContext, DailyHygiene.IMMEDIATE_DELAY_MS);
    ContentSyncService.get().scheduleSync();
    FinskyApp.get().clearCacheAsync(new Runnable()
    {
      public final void run()
      {
        String str = FinskyApp.get().getCurrentAccountName();
        if ((str != null) && (!AccountHandler.hasAccount(str, paramContext))) {
          System.exit(0);
        }
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.AccountsChangedReceiver
 * JD-Core Version:    0.7.0.1
 */