package com.google.android.finsky.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;

public final class PermissionPolicies
{
  public static final class PermissionPolicyNotifier
    implements PackageMonitorReceiver.PackageStatusListener
  {
    public final void onPackageAdded(String paramString)
    {
      PermissionPolicies.PermissionPolicyService.startPermissionPolicyService(FinskyApp.get(), paramString, 0);
    }
    
    public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
    
    public final void onPackageChanged(String paramString) {}
    
    public final void onPackageFirstLaunch(String paramString) {}
    
    public final void onPackageRemoved(String paramString, boolean paramBoolean)
    {
      if (paramBoolean) {
        PermissionPolicies.PermissionPolicyService.startPermissionPolicyService(FinskyApp.get(), paramString, 1);
      }
    }
  }
  
  public static class PermissionPolicyService
    extends Service
  {
    private final ArrayList<String> removedPackages = new ArrayList();
    
    public static void startPermissionPolicyService(Context paramContext, String paramString, int paramInt)
    {
      if (Build.VERSION.SDK_INT >= 23)
      {
        Intent localIntent = new Intent(paramContext, PermissionPolicyService.class);
        localIntent.putExtra("package_name", paramString);
        localIntent.putExtra("package_event", paramInt);
        paramContext.startService(localIntent);
      }
    }
    
    public IBinder onBind(Intent paramIntent)
    {
      return null;
    }
    
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
    {
      String str1 = paramIntent.getStringExtra("package_name");
      int i = paramIntent.getIntExtra("package_event", -1);
      if ((i == 1) && (!TextUtils.isEmpty(str1))) {
        this.removedPackages.add(str1);
      }
      for (;;)
      {
        if (this.removedPackages.isEmpty()) {
          stopSelf(paramInt2);
        }
        return 3;
        if (i == 0)
        {
          boolean bool = this.removedPackages.remove(str1);
          PackageInfo localPackageInfo;
          int i4;
          for (;;)
          {
            int m;
            int i3;
            try
            {
              localPackageInfo = getPackageManager().getPackageInfo(str1, 4096);
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = localPackageInfo.packageName;
              FinskyLog.d("post-install permissions check for %s", arrayOfObject);
              String str2 = localPackageInfo.packageName;
              String[] arrayOfString1 = localPackageInfo.requestedPermissions;
              int j = localPackageInfo.applicationInfo.targetSdkVersion;
              if ((Build.VERSION.SDK_INT < 23) || (bool) || ((!FinskyApp.get().getExperiments().isEnabled(12605120L)) && (!((Boolean)G.systemAlertWindowPolicy2.get()).booleanValue())) || (j < 23)) {
                break label420;
              }
              PackageManager localPackageManager1 = FinskyApp.get().getPackageManager();
              if ((!FinskyApp.get().getPackageName().equals(localPackageManager1.getInstallerPackageName(str2))) || (arrayOfString1 == null)) {
                break label420;
              }
              int k = arrayOfString1.length;
              m = 0;
              if (m >= k) {
                break label420;
              }
              if (!"android.permission.SYSTEM_ALERT_WINDOW".equals(arrayOfString1[m])) {
                break label414;
              }
              n = 1;
              String str3 = localPackageInfo.packageName;
              String[] arrayOfString2 = localPackageInfo.requestedPermissions;
              int i1 = localPackageInfo.applicationInfo.targetSdkVersion;
              if ((Build.VERSION.SDK_INT != 23) || (!bool) || (!FinskyApp.get().getExperiments().isEnabled(12605124L)) || (i1 != 23)) {
                break label432;
              }
              PackageManager localPackageManager2 = FinskyApp.get().getPackageManager();
              if ((!FinskyApp.get().getPackageName().equals(localPackageManager2.getInstallerPackageName(str3))) || (arrayOfString2 == null)) {
                break label432;
              }
              int i2 = arrayOfString2.length;
              i3 = 0;
              if (i3 >= i2) {
                break label432;
              }
              if (!"android.permission.SYSTEM_ALERT_WINDOW".equals(arrayOfString2[i3])) {
                break label426;
              }
              i4 = 1;
              if (n == 0) {
                break label438;
              }
              PermissionPolicies.access$200(localPackageInfo);
              PermissionPolicies.access$300(133, str1, localPackageInfo.versionCode);
            }
            catch (PackageManager.NameNotFoundException localNameNotFoundException)
            {
              FinskyLog.e("Can't find package %s", new Object[] { str1 });
            }
            break;
            label414:
            m++;
            continue;
            label420:
            int n = 0;
            continue;
            label426:
            i3++;
            continue;
            label432:
            i4 = 0;
          }
          label438:
          if (i4 != 0)
          {
            PermissionPolicies.access$200(localPackageInfo);
            PermissionPolicies.access$300(134, str1, localPackageInfo.versionCode);
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PermissionPolicies
 * JD-Core Version:    0.7.0.1
 */