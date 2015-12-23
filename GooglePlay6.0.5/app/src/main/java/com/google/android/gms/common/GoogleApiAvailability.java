package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.ProgressBar;
import com.google.android.gms.R.string;
import com.google.android.gms.common.internal.zzn;

public final class GoogleApiAvailability
{
  public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE;
  private static final GoogleApiAvailability zzanx = new GoogleApiAvailability();
  
  public static int getApkVersion(Context paramContext)
  {
    return GooglePlayServicesUtil.getApkVersion(paramContext);
  }
  
  public static Intent getErrorResolutionIntent(Context paramContext, int paramInt, String paramString)
  {
    switch (paramInt)
    {
    default: 
      return null;
    case 1: 
    case 2: 
      return zzn.zzG("com.google.android.gms", zzj(paramContext, paramString));
    case 42: 
      return zzn.zzqz();
    }
    return zzn.zzcD("com.google.android.gms");
  }
  
  public static GoogleApiAvailability getInstance()
  {
    return zzanx;
  }
  
  public static int isGooglePlayServicesAvailable(Context paramContext)
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
    if (GooglePlayServicesUtil.isPlayServicesPossiblyUpdating(paramContext, i)) {
      i = 18;
    }
    return i;
  }
  
  public static boolean isPlayServicesPossiblyUpdating(Context paramContext, int paramInt)
  {
    return GooglePlayServicesUtil.isPlayServicesPossiblyUpdating(paramContext, paramInt);
  }
  
  public static boolean isUserResolvableError(int paramInt)
  {
    return GooglePlayServicesUtil.isUserRecoverableError(paramInt);
  }
  
  public static Dialog zza(Activity paramActivity, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    ProgressBar localProgressBar = new ProgressBar(paramActivity, null, 16842874);
    localProgressBar.setIndeterminate(true);
    localProgressBar.setVisibility(0);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramActivity);
    localBuilder.setView(localProgressBar);
    String str = GooglePlayServicesUtil.zzap(paramActivity);
    localBuilder.setMessage(paramActivity.getResources().getString(R.string.common_google_play_services_updating_text, new Object[] { str }));
    localBuilder.setTitle(R.string.common_google_play_services_updating_title);
    localBuilder.setPositiveButton("", null);
    AlertDialog localAlertDialog = localBuilder.create();
    GooglePlayServicesUtil.zza(paramActivity, paramOnCancelListener, "GooglePlayServicesUpdatingDialog", localAlertDialog);
    return localAlertDialog;
  }
  
  public static void zzal(Context paramContext)
    throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
  {
    GooglePlayServicesUtil.zzae(paramContext);
  }
  
  public static void zzam(Context paramContext)
  {
    GooglePlayServicesUtil.zzam(paramContext);
  }
  
  public static boolean zzi(Context paramContext, String paramString)
  {
    return GooglePlayServicesUtil.zzi(paramContext, paramString);
  }
  
  private static String zzj(Context paramContext, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("gcore_");
    localStringBuilder.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
    localStringBuilder.append("-");
    if (!TextUtils.isEmpty(paramString)) {
      localStringBuilder.append(paramString);
    }
    localStringBuilder.append("-");
    if (paramContext != null) {
      localStringBuilder.append(paramContext.getPackageName());
    }
    localStringBuilder.append("-");
    if (paramContext != null) {}
    try
    {
      localStringBuilder.append(paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode);
      label94:
      return localStringBuilder.toString();
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label94;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.GoogleApiAvailability
 * JD-Core Version:    0.7.0.1
 */