package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import com.google.android.gms.R.string;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.util.zzq;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GooglePlayServicesUtil
{
  @Deprecated
  public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 8296000;
  public static boolean zzanH = false;
  public static boolean zzanI = false;
  private static int zzanJ = -1;
  private static String zzanK = null;
  private static Integer zzanL = null;
  static final AtomicBoolean zzanM = new AtomicBoolean();
  private static final AtomicBoolean zzanN = new AtomicBoolean();
  private static final Object zzqK = new Object();
  
  @Deprecated
  public static int getApkVersion(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo("com.google.android.gms", 0);
      return localPackageInfo.versionCode;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
    }
    return 0;
  }
  
  @Deprecated
  public static Dialog getErrorDialog$65f13a54(int paramInt, Activity paramActivity)
  {
    return zza(paramInt, paramActivity, null, -1, null);
  }
  
  public static Context getRemoteContext(Context paramContext)
  {
    try
    {
      Context localContext = paramContext.createPackageContext("com.google.android.gms", 3);
      return localContext;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public static Resources getRemoteResource(Context paramContext)
  {
    try
    {
      Resources localResources = paramContext.getPackageManager().getResourcesForApplication("com.google.android.gms");
      return localResources;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return null;
  }
  
  public static boolean honorsDebugCertificates(PackageManager paramPackageManager)
  {
    if (!zzb(paramPackageManager)) {
      if (!zzanH) {
        break label23;
      }
    }
    label23:
    for (boolean bool = zzanI; !bool; bool = "user".equals(Build.TYPE)) {
      return true;
    }
    return false;
  }
  
  @Deprecated
  public static int isGooglePlayServicesAvailable(Context paramContext)
  {
    if (com.google.android.gms.common.internal.zzd.zzasZ) {
      return 0;
    }
    PackageManager localPackageManager = paramContext.getPackageManager();
    Integer localInteger;
    for (;;)
    {
      try
      {
        paramContext.getResources().getString(R.string.common_google_play_services_unknown_issue);
        if (("com.google.android.gms".equals(paramContext.getPackageName())) || (zzanN.get())) {
          break label281;
        }
      }
      catch (Throwable localThrowable)
      {
        synchronized (zzqK)
        {
          if (zzanK == null)
          {
            zzanK = paramContext.getPackageName();
            try
            {
              Bundle localBundle = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData;
              if (localBundle == null) {
                continue;
              }
              zzanL = Integer.valueOf(localBundle.getInt("com.google.android.gms.version"));
            }
            catch (PackageManager.NameNotFoundException localNameNotFoundException4)
            {
              Log.wtf("GooglePlayServicesUtil", "This should never happen.", localNameNotFoundException4);
              continue;
            }
            localInteger = zzanL;
            if (localInteger != null) {
              break;
            }
            throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            localThrowable = localThrowable;
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
            continue;
            zzanL = null;
          }
        }
      }
      if (!zzanK.equals(paramContext.getPackageName())) {
        throw new IllegalArgumentException("isGooglePlayServicesAvailable should only be called with Context from your application's package. A previous call used package '" + zzanK + "' and this call used package '" + paramContext.getPackageName() + "'.");
      }
    }
    if (localInteger.intValue() != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
      throw new IllegalStateException("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + localInteger + ".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version" + "\" android:value=\"@integer/google_play_services_version\" />");
    }
    label281:
    PackageInfo localPackageInfo;
    try
    {
      localPackageInfo = localPackageManager.getPackageInfo("com.google.android.gms", 64);
      GoogleSignatureVerifier.getInstance();
      if (com.google.android.gms.common.util.zzd.zzax(paramContext))
      {
        if (GoogleSignatureVerifier.zza(localPackageInfo, zzc.zzcg.zzanG) != null) {
          break label410;
        }
        Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
        return 9;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException1)
    {
      Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
      return 1;
    }
    zzc.zza localzza;
    try
    {
      localzza = GoogleSignatureVerifier.zza(localPackageManager.getPackageInfo("com.android.vending", 8256), zzc.zzcg.zzanG);
      if (localzza == null)
      {
        Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
        return 9;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException2)
    {
      Log.w("GooglePlayServicesUtil", "Google Play Store is neither installed nor updating.");
      return 9;
    }
    if (GoogleSignatureVerifier.zza(localPackageInfo, new zzc.zza[] { localzza }) == null)
    {
      Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
      return 9;
    }
    label410:
    int i = com.google.android.gms.common.util.zzd.zzdA(GOOGLE_PLAY_SERVICES_VERSION_CODE);
    if (com.google.android.gms.common.util.zzd.zzdA(localPackageInfo.versionCode) < i)
    {
      Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires " + GOOGLE_PLAY_SERVICES_VERSION_CODE + " but found " + localPackageInfo.versionCode);
      return 2;
    }
    Object localObject1 = localPackageInfo.applicationInfo;
    if (localObject1 == null) {}
    try
    {
      ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo("com.google.android.gms", 0);
      localObject1 = localApplicationInfo;
      if (!((ApplicationInfo)localObject1).enabled) {
        return 3;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException3)
    {
      Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", localNameNotFoundException3);
      return 1;
    }
    return 0;
  }
  
  @Deprecated
  public static boolean isPlayServicesPossiblyUpdating(Context paramContext, int paramInt)
  {
    if (paramInt == 18) {
      return true;
    }
    if (paramInt == 1) {
      return zzi(paramContext, "com.google.android.gms");
    }
    return false;
  }
  
  public static boolean isSidewinderDevice(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    return (zzq.zzdC(21)) && (localPackageManager.hasSystemFeature("cn.google"));
  }
  
  @Deprecated
  public static boolean isUserRecoverableError(int paramInt)
  {
    switch (paramInt)
    {
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    default: 
      return false;
    }
    return true;
  }
  
  public static boolean showErrorDialogFragment(int paramInt1, Activity paramActivity, Fragment paramFragment, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    Dialog localDialog = zza(paramInt1, paramActivity, paramFragment, paramInt2, paramOnCancelListener);
    if (localDialog == null) {
      return false;
    }
    zza(paramActivity, paramOnCancelListener, "GooglePlayServicesErrorDialog", localDialog);
    return true;
  }
  
  @Deprecated
  public static boolean showErrorDialogFragment$70a48c07(int paramInt, Activity paramActivity)
  {
    return showErrorDialogFragment(paramInt, paramActivity, null, 0, null);
  }
  
  private static Dialog zza(int paramInt1, Activity paramActivity, Fragment paramFragment, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    if (paramInt1 == 0) {
      return null;
    }
    if ((com.google.android.gms.common.util.zzd.zzax(paramActivity)) && (paramInt1 == 2)) {
      paramInt1 = 42;
    }
    if (zzq.zzdC(14))
    {
      TypedValue localTypedValue = new TypedValue();
      paramActivity.getTheme().resolveAttribute(16843529, localTypedValue, true);
      if (!"Theme.Dialog.Alert".equals(paramActivity.getResources().getResourceEntryName(localTypedValue.resourceId))) {}
    }
    for (AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramActivity, 5);; localBuilder = null)
    {
      if (localBuilder == null) {
        localBuilder = new AlertDialog.Builder(paramActivity);
      }
      String str1 = zzap(paramActivity);
      Resources localResources1 = paramActivity.getResources();
      String str2;
      Intent localIntent;
      zzh localzzh;
      label266:
      Resources localResources2;
      String str3;
      label326:
      Resources localResources3;
      Object localObject;
      switch (paramInt1)
      {
      default: 
        str2 = localResources1.getString(R.string.common_google_play_services_unknown_issue);
        localBuilder.setMessage(str2);
        if (paramOnCancelListener != null) {
          localBuilder.setOnCancelListener(paramOnCancelListener);
        }
        GoogleApiAvailability.getInstance();
        localIntent = GoogleApiAvailability.getErrorResolutionIntent(paramActivity, paramInt1, "d");
        if (paramFragment == null)
        {
          localzzh = new zzh(paramActivity, localIntent, paramInt2);
          localResources2 = paramActivity.getResources();
          switch (paramInt1)
          {
          default: 
            str3 = localResources2.getString(17039370);
            if (str3 != null) {
              localBuilder.setPositiveButton(str3, localzzh);
            }
            localResources3 = paramActivity.getResources();
            localObject = null;
            switch (paramInt1)
            {
            default: 
              Log.e("GoogleApiAvailability", "Unexpected error code " + paramInt1);
            }
            break;
          }
        }
        break;
      }
      for (;;)
      {
        if (localObject != null) {
          localBuilder.setTitle((CharSequence)localObject);
        }
        return localBuilder.create();
        int j;
        label546:
        int k;
        if (localResources1 != null) {
          if ((0xF & localResources1.getConfiguration().screenLayout) > 3)
          {
            j = 1;
            if ((!zzq.zzdC(11)) || (j == 0))
            {
              Configuration localConfiguration = localResources1.getConfiguration();
              if (!zzq.zzdC(13)) {
                break label647;
              }
              if (((0xF & localConfiguration.screenLayout) > 3) || (localConfiguration.smallestScreenWidthDp < 600)) {
                break label641;
              }
              k = 1;
              label600:
              if (k == 0) {
                break label653;
              }
            }
          }
        }
        label641:
        label647:
        label653:
        for (int i = 1;; i = 0)
        {
          if (i == 0) {
            break label659;
          }
          str2 = localResources1.getString(R.string.common_google_play_services_install_text_tablet, new Object[] { str1 });
          break;
          j = 0;
          break label546;
          k = 0;
          break label600;
          k = 0;
          break label600;
        }
        label659:
        str2 = localResources1.getString(R.string.common_google_play_services_install_text_phone, new Object[] { str1 });
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_enable_text, new Object[] { str1 });
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_updating_text, new Object[] { str1 });
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_update_text, new Object[] { str1 });
        break;
        str2 = localResources1.getString(R.string.common_android_wear_update_text, new Object[] { str1 });
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_unsupported_text, new Object[] { str1 });
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_network_error_text);
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_invalid_account_text);
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_api_unavailable_text, new Object[] { str1 });
        break;
        str2 = localResources1.getString(R.string.common_google_play_services_sign_in_failed_text);
        break;
        localzzh = new zzh(paramFragment, localIntent, paramInt2);
        break label266;
        str3 = localResources2.getString(R.string.common_google_play_services_install_button);
        break label326;
        str3 = localResources2.getString(R.string.common_google_play_services_enable_button);
        break label326;
        str3 = localResources2.getString(R.string.common_google_play_services_update_button);
        break label326;
        localObject = localResources3.getString(R.string.common_google_play_services_install_title);
        continue;
        localObject = localResources3.getString(R.string.common_google_play_services_enable_title);
        continue;
        localObject = localResources3.getString(R.string.common_google_play_services_updating_title);
        continue;
        localObject = localResources3.getString(R.string.common_google_play_services_update_title);
        continue;
        localObject = localResources3.getString(R.string.common_android_wear_update_title);
        continue;
        Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
        localObject = localResources3.getString(R.string.common_google_play_services_unsupported_title);
        continue;
        Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
        localObject = localResources3.getString(R.string.common_google_play_services_network_error_title);
        continue;
        Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
        localObject = null;
        continue;
        Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
        localObject = null;
        continue;
        Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
        localObject = localResources3.getString(R.string.common_google_play_services_invalid_account_title);
        continue;
        Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
        localObject = null;
        continue;
        Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
        localObject = null;
        continue;
        Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
        localObject = localResources3.getString(R.string.common_google_play_services_sign_in_failed_title);
      }
    }
  }
  
  public static void zza(Activity paramActivity, DialogInterface.OnCancelListener paramOnCancelListener, String paramString, Dialog paramDialog)
  {
    if ((paramActivity instanceof FragmentActivity))
    {
      android.support.v4.app.FragmentManager localFragmentManager1 = ((FragmentActivity)paramActivity).getSupportFragmentManager();
      SupportErrorDialogFragment.newInstance(paramDialog, paramOnCancelListener).show(localFragmentManager1, paramString);
      return;
    }
    if (zzq.zzdC(11))
    {
      android.app.FragmentManager localFragmentManager = paramActivity.getFragmentManager();
      ErrorDialogFragment.newInstance(paramDialog, paramOnCancelListener).show(localFragmentManager, paramString);
      return;
    }
    throw new RuntimeException("This Activity does not support Fragments.");
  }
  
  public static boolean zza(Context paramContext, int paramInt, String paramString)
  {
    AppOpsManager localAppOpsManager;
    if (zzq.zzdC(19)) {
      localAppOpsManager = (AppOpsManager)paramContext.getSystemService("appops");
    }
    for (;;)
    {
      try
      {
        localAppOpsManager.checkPackage(paramInt, paramString);
        bool = true;
        return bool;
      }
      catch (SecurityException localSecurityException) {}
      String[] arrayOfString = paramContext.getPackageManager().getPackagesForUid(paramInt);
      boolean bool = false;
      if (paramString != null)
      {
        bool = false;
        if (arrayOfString != null) {
          for (int i = 0;; i++)
          {
            int j = arrayOfString.length;
            bool = false;
            if (i >= j) {
              break;
            }
            if (paramString.equals(arrayOfString[i])) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  
  @Deprecated
  public static void zzae(Context paramContext)
    throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
  {
    GoogleApiAvailability.getInstance();
    int i = GoogleApiAvailability.isGooglePlayServicesAvailable(paramContext);
    if (i != 0)
    {
      GoogleApiAvailability.getInstance();
      Intent localIntent = GoogleApiAvailability.getErrorResolutionIntent(paramContext, i, "e");
      Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + i);
      if (localIntent == null) {
        throw new GooglePlayServicesNotAvailableException(i);
      }
      throw new GooglePlayServicesRepairableException(i, "Google Play Services not available", localIntent);
    }
  }
  
  @Deprecated
  public static void zzam(Context paramContext)
  {
    if (zzanM.getAndSet(true)) {
      return;
    }
    try
    {
      ((NotificationManager)paramContext.getSystemService("notification")).cancel(10436);
      return;
    }
    catch (SecurityException localSecurityException) {}
  }
  
  public static String zzap(Context paramContext)
  {
    String str = paramContext.getApplicationInfo().name;
    PackageManager localPackageManager;
    if (TextUtils.isEmpty(str))
    {
      str = paramContext.getPackageName();
      localPackageManager = paramContext.getApplicationContext().getPackageManager();
    }
    try
    {
      ApplicationInfo localApplicationInfo2 = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 0);
      localApplicationInfo1 = localApplicationInfo2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        ApplicationInfo localApplicationInfo1 = null;
      }
    }
    if (localApplicationInfo1 != null) {
      str = localPackageManager.getApplicationLabel(localApplicationInfo1).toString();
    }
    return str;
  }
  
  private static boolean zzb(PackageManager paramPackageManager)
  {
    for (boolean bool = true;; bool = false) {
      synchronized (zzqK)
      {
        int i = zzanJ;
        if (i == -1) {}
        try
        {
          PackageInfo localPackageInfo = paramPackageManager.getPackageInfo("com.google.android.gms", 64);
          GoogleSignatureVerifier.getInstance();
          zzc.zza[] arrayOfzza = new zzc.zza[1];
          arrayOfzza[0] = zzc.zzanz[1];
          if (GoogleSignatureVerifier.zza(localPackageInfo, arrayOfzza) != null) {}
          for (zzanJ = 1; zzanJ != 0; zzanJ = 0) {
            return bool;
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          for (;;)
          {
            zzanJ = 0;
          }
        }
      }
    }
  }
  
  public static boolean zze(Context paramContext, int paramInt)
  {
    if (zza(paramContext, paramInt, "com.google.android.gms"))
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      if (GoogleSignatureVerifier.getInstance().isPackageGoogleSigned(localPackageManager, "com.google.android.gms")) {
        return true;
      }
    }
    return false;
  }
  
  static boolean zzi(Context paramContext, String paramString)
  {
    boolean bool1 = true;
    if (zzq.zzdC(21))
    {
      Iterator localIterator = paramContext.getPackageManager().getPackageInstaller().getAllSessions().iterator();
      while (localIterator.hasNext()) {
        if (paramString.equals(((PackageInstaller.SessionInfo)localIterator.next()).getAppPackageName())) {
          return bool1;
        }
      }
    }
    if (zzq.zzdC(18))
    {
      Bundle localBundle = ((UserManager)paramContext.getSystemService("user")).getApplicationRestrictions(paramContext.getPackageName());
      if ((localBundle == null) || (!"true".equals(localBundle.getString("restricted_profile")))) {}
    }
    while (bool1)
    {
      return false;
      bool1 = false;
    }
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      boolean bool2 = localPackageManager.getApplicationInfo(paramString, 8192).enabled;
      return bool2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.GooglePlayServicesUtil
 * JD-Core Version:    0.7.0.1
 */