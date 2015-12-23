package com.google.android.finsky.activities;

import android.app.Activity;
import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public class AppCrashProxy
  extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    localIntent.setComponent(null);
    int i = 1;
    if (Build.VERSION.SDK_INT < 14)
    {
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(Build.VERSION.SDK_INT);
      FinskyLog.d("SDK level %d too low for feedback package", arrayOfObject3);
      if (i == 0) {
        break label450;
      }
      localIntent.setPackage((String)G.legacyFeedbackPackage.get());
    }
    for (;;)
    {
      startActivity(localIntent);
      finish();
      return;
      String str1 = (String)G.feedbackPackage.get();
      PackageStateRepository.PackageState localPackageState = FinskyApp.get().mPackageStateRepository.get(str1);
      PackageManager localPackageManager = getPackageManager();
      localIntent.setPackage(str1);
      ApplicationErrorReport localApplicationErrorReport = (ApplicationErrorReport)localIntent.getParcelableExtra("android.intent.extra.BUG_REPORT");
      if (localPackageState == null)
      {
        FinskyLog.w("Feedback package %s not installed", new Object[] { str1 });
        break;
      }
      if (localPackageState.isDisabled)
      {
        FinskyLog.w("Feedback package %s disabled", new Object[] { str1 });
        break;
      }
      if (localPackageState.installedVersion < ((Integer)G.feedbackPackageMinimumVersion.get()).intValue())
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = str1;
        arrayOfObject2[1] = Integer.valueOf(localPackageState.installedVersion);
        FinskyLog.d("Feedback package %s version %d too old", arrayOfObject2);
        break;
      }
      if (localPackageManager.queryBroadcastReceivers(localIntent, 0).size() < 0)
      {
        FinskyLog.w("No receiver found in %s", new Object[] { str1 });
        break;
      }
      if (localApplicationErrorReport == null)
      {
        FinskyLog.w("Crash report missing", new Object[0]);
        break;
      }
      if (TextUtils.isEmpty(localApplicationErrorReport.packageName))
      {
        FinskyLog.w("Crash bug report missing package name", new Object[0]);
        break;
      }
      for (;;)
      {
        int k;
        try
        {
          String[] arrayOfString = localPackageManager.getPackagesForUid(localPackageManager.getApplicationInfo(localPackageState.packageName, 0).uid);
          int j = arrayOfString.length;
          k = 0;
          int m = 0;
          if (k < j)
          {
            String str2 = arrayOfString[k];
            if (!localApplicationErrorReport.packageName.equals(str2)) {
              break label444;
            }
            m = 1;
            FinskyLog.w("Package %s has crashed but is related to feedback package %s", new Object[] { str2, str1 });
          }
          if (m != 0) {
            break;
          }
          i = 0;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = localApplicationErrorReport.packageName;
          arrayOfObject1[1] = str1;
          FinskyLog.d("Sending feedback for crashed %s to %s", arrayOfObject1);
          i = 0;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          FinskyLog.wtf("Exception, feedback package %s not found", new Object[] { str1 });
        }
        break;
        label444:
        k++;
      }
      label450:
      localIntent.setPackage((String)G.feedbackPackage.get());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.AppCrashProxy
 * JD-Core Version:    0.7.0.1
 */