package com.google.android.finsky.billing;

import android.content.Context;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class PreAppDownloadWarnings
{
  public static boolean isPostponeDownloadUntilWifiEnabled(Context paramContext)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    return (localFinskyApp.mInstallPolicies.hasMobileNetwork()) && ((GooglePlayServicesUtil.isSidewinderDevice(paramContext)) || (localFinskyApp.getExperiments().isEnabled(12604154L)));
  }
  
  public static void prohibitMobileDownloadIfWifiOnly(Context paramContext, String paramString)
  {
    if ((isPostponeDownloadUntilWifiEnabled(paramContext)) && (((Integer)FinskyPreferences.downloadNetworkPreference.get()).intValue() == 3)) {
      FinskyApp.get().mInstaller.setMobileDataProhibited(paramString);
    }
  }
  
  public static boolean showDownloadNetworkDialog(Context paramContext, int paramInt)
  {
    return (paramInt == 1) && (!FinskyApp.get().mInstallPolicies.isWifiNetwork()) && (isPostponeDownloadUntilWifiEnabled(paramContext)) && (((Integer)FinskyPreferences.downloadNetworkPreference.get()).intValue() == 1);
  }
  
  public static abstract interface Listener
  {
    public abstract void onDoAcquisition();
    
    public abstract void onDownloadCancel();
    
    public abstract void onDownloadOk(boolean paramBoolean1, boolean paramBoolean2);
    
    public abstract void onSetupWifi();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.PreAppDownloadWarnings
 * JD-Core Version:    0.7.0.1
 */