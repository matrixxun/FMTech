package com.google.android.vending.verifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Process;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;

public class PackageVerificationReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    if (("android.intent.action.PACKAGE_NEEDS_VERIFICATION".equals(str)) || ("android.intent.action.PACKAGE_VERIFIED".equals(str)))
    {
      Bundle localBundle = paramIntent.getExtras();
      int i;
      int j;
      if (localBundle != null)
      {
        i = localBundle.getInt("android.content.pm.extra.VERIFICATION_ID");
        if (((Boolean)G.platformAntiMalwareEnabled.get()).booleanValue()) {
          break label103;
        }
        FinskyLog.d("Skipping verification because disabled", new Object[0]);
        j = 0;
      }
      while (j != 0)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        FinskyLog.d("Verification requested, id = %d", arrayOfObject1);
        PackageVerificationService.start(paramContext, paramIntent);
        return;
        label103:
        if (!FinskyApp.get().mInstallPolicies.hasNetwork())
        {
          FinskyLog.d("Skipping verification because network inactive", new Object[0]);
          j = 0;
        }
        else if (Build.VERSION.SDK_INT < 17)
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(Build.VERSION.SDK_INT);
          FinskyLog.d("Skipping verification because SDK=%d", arrayOfObject2);
          j = 0;
        }
        else if (localBundle.getInt("android.content.pm.extra.VERIFICATION_INSTALLER_UID") == Process.myUid())
        {
          FinskyLog.d("Skipping verification because own installation", new Object[0]);
          j = 0;
        }
        else
        {
          j = 1;
        }
      }
      paramContext.getPackageManager().verifyPendingInstall(i, 1);
      return;
    }
    FinskyLog.w("Unexpected action %s", new Object[] { str });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageVerificationReceiver
 * JD-Core Version:    0.7.0.1
 */