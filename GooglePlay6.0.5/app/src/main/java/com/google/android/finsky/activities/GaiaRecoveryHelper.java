package com.google.android.finsky.activities;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.play.utils.config.GservicesValue;

public final class GaiaRecoveryHelper
{
  private static String sCurrentAccountName = null;
  private static PendingIntent sGaiaAuthIntent = null;
  
  private static PendingIntent getRecoveryIntentIfExists()
  {
    if (TextUtils.equals(FinskyApp.get().getCurrentAccountName(), sCurrentAccountName)) {
      return sGaiaAuthIntent;
    }
    return null;
  }
  
  public static void launchGaiaRecoveryDialog$18e2bb50(Resources paramResources, FragmentManager paramFragmentManager, String paramString)
  {
    if (!shouldShowGaiaRecoveryDialog()) {
      return;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramResources.getString(2131362062);
    String str = paramResources.getString(2131362183, arrayOfObject);
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessage(str).setPositiveId(2131362062).setCanceledOnTouchOutside(false).setEventLog(307, null, 247, 248, null).setCallback(null, 37, null);
    localBuilder.build().show(paramFragmentManager, paramString);
  }
  
  public static void onPositiveGaiaRecoveryDialogResponse()
  {
    PendingIntent localPendingIntent = getRecoveryIntentIfExists();
    if (localPendingIntent == null)
    {
      FinskyLog.wtf("Called Gaia recovery flow but PendingIntent didn't exist.", new Object[0]);
      return;
    }
    try
    {
      localPendingIntent.send();
      return;
    }
    catch (PendingIntent.CanceledException localCanceledException)
    {
      FinskyLog.e(localCanceledException, "PendingIntent for GAIA auth was canceled", new Object[0]);
      return;
    }
    finally
    {
      sGaiaAuthIntent = null;
    }
  }
  
  public static void prefetchAndCacheGaiaAuthRecoveryIntent(Context paramContext, String paramString)
  {
    if (!((Boolean)G.enableGaiaRecovery.get()).booleanValue()) {
      FinskyLog.d("Skipping fetching recovery intent -- gaia recovery disabled", new Object[0]);
    }
    do
    {
      return;
      if (sGaiaAuthIntent == null) {
        break;
      }
    } while (TextUtils.equals(sCurrentAccountName, paramString));
    sCurrentAccountName = null;
    sGaiaAuthIntent = null;
    sCurrentAccountName = paramString;
    if (paramString == null)
    {
      FinskyLog.d("Skipping fetching recovery intent -- no user set.", new Object[0]);
      return;
    }
    new AsyncTask()
    {
      private static PendingIntent doInBackground(Context... paramAnonymousVarArgs)
      {
        try
        {
          Context localContext = paramAnonymousVarArgs[0];
          if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(localContext) != 0)
          {
            FinskyLog.e("GooglePlayServices is not available.", new Object[0]);
            return null;
          }
          PendingIntent localPendingIntent2 = GoogleAuthUtil.getRecoveryIfSuggested$12f08959(localContext, GaiaRecoveryHelper.sCurrentAccountName);
          localPendingIntent1 = localPendingIntent2;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            PendingIntent localPendingIntent1 = null;
          }
        }
        return localPendingIntent1;
      }
    }.execute(new Context[] { paramContext });
  }
  
  public static boolean shouldShowGaiaRecoveryDialog()
  {
    return getRecoveryIntentIfExists() != null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.GaiaRecoveryHelper
 * JD-Core Version:    0.7.0.1
 */