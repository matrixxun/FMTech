package com.google.android.finsky.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.gcm.GCMRegistrar;

public final class GcmRegistrationIdHelper
{
  public static String getRegistrationId(Context paramContext)
  {
    int i = Build.VERSION.SDK_INT;
    if (i < 8) {
      throw new UnsupportedOperationException("Device must be at least API Level 8 (instead of " + i + ")");
    }
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      localPackageManager.getPackageInfo("com.google.android.gsf", 0);
      String str = GCMRegistrar.getRegistrationId(paramContext);
      if (TextUtils.isEmpty(str))
      {
        FinskyLog.d("Start requesting GCM Reg Id", new Object[0]);
        String[] arrayOfString = { "932144863878" };
        GCMRegistrar.resetBackoff(paramContext);
        GCMRegistrar.internalRegister(paramContext, arrayOfString);
        str = null;
      }
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new UnsupportedOperationException("Device does not have package com.google.android.gsf");
    }
  }
  
  public static void uploadIfNotRegistered(Context paramContext, DfeApi paramDfeApi)
  {
    String str = GCMRegistrar.getRegistrationId(paramContext);
    if (TextUtils.isEmpty(str)) {}
    while (str.equals(FinskyPreferences.gcmRegistrationIdOnServer.get())) {
      return;
    }
    FinskyLog.d("Uploading GcmRegistration Id because not registered yet", new Object[0]);
    DeviceConfigurationHelper.get().postUploadRequest(paramDfeApi, true, null);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GcmRegistrationIdHelper
 * JD-Core Version:    0.7.0.1
 */