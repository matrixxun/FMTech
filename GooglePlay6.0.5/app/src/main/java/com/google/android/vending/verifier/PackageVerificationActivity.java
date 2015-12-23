package com.google.android.vending.verifier;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;

public class PackageVerificationActivity
  extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setVisible(false);
    Intent localIntent = getIntent();
    String str = localIntent.getAction();
    int i;
    if (("android.intent.action.VIEW".equals(str)) || ("android.intent.action.INSTALL_PACKAGE".equals(str))) {
      if (Build.VERSION.SDK_INT >= 17)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(Build.VERSION.SDK_INT);
        FinskyLog.wtf("Skipping verification. Unexpected SDK=%d", arrayOfObject);
        i = 0;
        if (i == 0) {
          break label200;
        }
        FinskyLog.w("Verification requested through action=%s", new Object[] { str });
        localIntent.putExtra("com.google.android.vending.verifier.extra.FROM_VERIFICATION_ACTIVITY", true);
        PackageVerificationService.start(this, localIntent);
      }
    }
    for (;;)
    {
      finish();
      return;
      if (Settings.Secure.getInt(getContentResolver(), "package_verifier_enable", 1) != 1)
      {
        FinskyLog.d("Skipping verification. Disabled by user setting", new Object[0]);
        i = 0;
        break;
      }
      if (!((Boolean)G.antiMalwareActivityEnabled.get()).booleanValue())
      {
        FinskyLog.d("Skipping verification. Disabled by Gservices", new Object[0]);
        i = 0;
        break;
      }
      if (!FinskyApp.get().mInstallPolicies.hasNetwork())
      {
        FinskyLog.d("Skipping verification. Network inactive", new Object[0]);
        i = 0;
        break;
      }
      i = 1;
      break;
      label200:
      localIntent.setPackage("com.android.packageinstaller");
      localIntent.setComponent(new ComponentName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity"));
      startActivity(localIntent);
      continue;
      FinskyLog.w("Unexpected action %s", new Object[] { str });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.PackageVerificationActivity
 * JD-Core Version:    0.7.0.1
 */