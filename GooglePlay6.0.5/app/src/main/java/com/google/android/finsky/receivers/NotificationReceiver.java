package com.google.android.finsky.receivers;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.NotificationManager;
import com.google.android.finsky.utils.Notifier;
import com.google.android.finsky.utils.PreregistrationHelper;

public class NotificationReceiver
  extends BroadcastReceiver
{
  public static Intent getNewUpdateClickedIntent()
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.NEW_UPDATE_CLICKED");
  }
  
  public static Intent getNewUpdateNeedApprovalClicked()
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.NEW_UPDATE_NEED_APPROVAL_CLICKED");
  }
  
  public static Intent getOutstandingUpdateClickedIntent()
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.OUTSTANDING_UPDATE_CLICKED");
  }
  
  public static Intent getPreregistrationReleasedClickedIntent(String paramString1, String paramString2)
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.PREREGISTRATION_RELEASED_CLICKED").putExtra("package_name", paramString1).putExtra("account_name", paramString2);
  }
  
  public static Intent getPreregistrationReleasedDeleteIntent(String paramString)
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.PREREGISTRATION_RELEASED_DELETE").putExtra("package_name", paramString);
  }
  
  public static Intent getSuccessfullyInstalledClickedIntent(String paramString1, String paramString2)
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.SUCCESSFULLY_INSTALLED_CLICKED").putExtra("package_name", paramString1).putExtra("continue_url", paramString2);
  }
  
  public static Intent getSuccessfullyUpdatedClickedIntent()
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.SUCCESSFULLY_UPDATED_CLICKED");
  }
  
  public static Intent getSuccessfullyUpdatedDeletedIntent()
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.SUCCESSFULLY_UPDATED_DELETED");
  }
  
  public static Intent getUpdateAllClicked()
  {
    return new Intent(FinskyApp.get(), NotificationReceiver.class).setAction("com.android.vending.UPDATE_ALL_CLICKED");
  }
  
  private static void logNotificationClick(int paramInt, String paramString)
  {
    if (paramString == null)
    {
      FinskyApp.get().getEventLogger().logClickEvent(paramInt, null, null);
      return;
    }
    PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
    localPlayStoreUiElementInfo.document = paramString;
    localPlayStoreUiElementInfo.hasDocument = true;
    FinskyApp.get().getEventLogger().logClickEventWithClientCookie(paramInt, localPlayStoreUiElementInfo, null);
  }
  
  private static void startMyDownloads(Context paramContext)
  {
    paramContext.startActivity(MainActivity.getMyDownloadsIntent(paramContext).setFlags(268435456));
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str1 = paramIntent.getAction();
    if ("com.android.vending.UPDATE_ALL_CLICKED".equals(str1)) {}
    do
    {
      try
      {
        PendingIntent.getBroadcast(paramContext, 0, new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 1073741824).send();
        FinskyApp.get().mNotificationHelper.hideUpdatesAvailableMessage();
        logNotificationClick(276, null);
        paramContext.startActivity(MainActivity.getMyDownloadsIntent$634a7f4e(paramContext).setFlags(268435456));
        return;
      }
      catch (PendingIntent.CanceledException localCanceledException)
      {
        for (;;)
        {
          FinskyLog.e(localCanceledException, "Error when broadcasting close system dialogs intent", new Object[0]);
        }
      }
      if ("com.android.vending.NEW_UPDATE_CLICKED".equals(str1))
      {
        logNotificationClick(900, null);
        startMyDownloads(paramContext);
        return;
      }
      if ("com.android.vending.SUCCESSFULLY_INSTALLED_CLICKED".equals(str1))
      {
        String str5 = paramIntent.getStringExtra("package_name");
        String str6 = paramIntent.getStringExtra("continue_url");
        logNotificationClick(901, str5);
        Intent localIntent2 = paramContext.getPackageManager().getLaunchIntentForPackage(str5);
        if (!TextUtils.isEmpty(str6)) {
          localIntent2 = IntentUtils.createContinueUrlIntent(str5, str6);
        }
        if (localIntent2 == null) {
          localIntent2 = NotificationManager.createDefaultClickIntent(paramContext, str5, null, null, DfeUtils.createDetailsUrlFromId(str5));
        }
        paramContext.startActivity(localIntent2.setFlags(268435456));
        return;
      }
      if ("com.android.vending.SUCCESSFULLY_UPDATED_CLICKED".equals(str1))
      {
        logNotificationClick(902, null);
        FinskyPreferences.successfulUpdateNotificationAppList.remove();
        paramContext.startActivity(MainActivity.getMyDownloadsIntent(paramContext).setFlags(268435456));
        return;
      }
      if ("com.android.vending.SUCCESSFULLY_UPDATED_DELETED".equals(str1))
      {
        FinskyPreferences.successfulUpdateNotificationAppList.remove();
        return;
      }
      if ("com.android.vending.OUTSTANDING_UPDATE_CLICKED".equals(str1))
      {
        logNotificationClick(903, null);
        startMyDownloads(paramContext);
        return;
      }
      if ("com.android.vending.NEW_UPDATE_NEED_APPROVAL_CLICKED".equals(str1))
      {
        logNotificationClick(904, null);
        startMyDownloads(paramContext);
        return;
      }
      if ("com.android.vending.PREREGISTRATION_RELEASED_CLICKED".equals(str1))
      {
        String str3 = paramIntent.getStringExtra("package_name");
        String str4 = paramIntent.getStringExtra("account_name");
        logNotificationClick(905, str3);
        FinskyApp.get().mPreregistrationHelper.acknowledgeNewRelease(str3);
        Intent localIntent1 = IntentUtils.createViewDocumentUrlIntent(paramContext, DfeUtils.createDetailsUrlFromId(str3)).setFlags(268435456);
        if (str4 != null) {
          localIntent1.putExtra("authAccount", str4);
        }
        paramContext.startActivity(localIntent1);
        return;
      }
    } while (!"com.android.vending.PREREGISTRATION_RELEASED_DELETE".equals(str1));
    String str2 = paramIntent.getStringExtra("package_name");
    FinskyApp.get().mPreregistrationHelper.acknowledgeNewRelease(str2);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.NotificationReceiver
 * JD-Core Version:    0.7.0.1
 */