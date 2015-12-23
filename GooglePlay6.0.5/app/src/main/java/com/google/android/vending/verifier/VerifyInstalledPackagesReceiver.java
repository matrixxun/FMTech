package com.google.android.vending.verifier;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;

public class VerifyInstalledPackagesReceiver
  extends BroadcastReceiver
{
  public static void verifyInstalledPackages(Context paramContext)
  {
    paramContext.sendBroadcast(new Intent("com.google.android.vending.verifier.intent.action.VERIFY_INSTALLED_PACKAGES"));
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    if ("com.google.android.vending.verifier.intent.action.VERIFY_INSTALLED_PACKAGES".equals(str))
    {
      int k;
      if (!((Boolean)G.platformAntiMalwareEnabled.get()).booleanValue())
      {
        FinskyLog.d("Skipping verification because disabled", new Object[0]);
        k = 0;
      }
      for (;;)
      {
        if (k != 0)
        {
          FinskyLog.d("Verify installed apps requested", new Object[0]);
          PackageVerificationService.start(paramContext, paramIntent);
        }
        return;
        if (!FinskyApp.get().mInstallPolicies.hasNetwork())
        {
          FinskyLog.d("Skipping verification because network inactive", new Object[0]);
          k = 0;
        }
        else
        {
          ContentResolver localContentResolver = paramContext.getContentResolver();
          int i;
          if (Build.VERSION.SDK_INT >= 17)
          {
            i = Settings.Global.getInt(localContentResolver, "package_verifier_enable", 1);
            label112:
            if (i == 0) {
              break label153;
            }
          }
          label153:
          for (int j = 1;; j = 0)
          {
            if (j != 0) {
              break label159;
            }
            FinskyLog.d("Skipping verification because verify apps is not enabled", new Object[0]);
            k = 0;
            break;
            i = Settings.Secure.getInt(localContentResolver, "package_verifier_enable", 1);
            break label112;
          }
          label159:
          k = 1;
        }
      }
    }
    if ("com.google.android.vending.verifier.intent.action.REMOVAL_REQUEST_RESPONSE".equals(str))
    {
      FinskyLog.d("Handle removal request responses requested", new Object[0]);
      PackageVerificationService.start(paramContext, paramIntent);
      return;
    }
    FinskyLog.w("Unexpected action %s", new Object[] { str });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.VerifyInstalledPackagesReceiver
 * JD-Core Version:    0.7.0.1
 */