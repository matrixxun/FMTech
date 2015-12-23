package com.google.android.finsky.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;

public final class UninstallRefundTracker
  implements PackageMonitorReceiver.PackageStatusListener
{
  private final AppStates mAppStates;
  final Context mContext;
  
  public UninstallRefundTracker(Context paramContext, AppStates paramAppStates, PackageMonitorReceiver paramPackageMonitorReceiver)
  {
    this.mContext = paramContext;
    this.mAppStates = paramAppStates;
    paramPackageMonitorReceiver.attach(this);
  }
  
  public final void onPackageAdded(String paramString) {}
  
  public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
  
  public final void onPackageChanged(String paramString) {}
  
  public final void onPackageFirstLaunch(String paramString) {}
  
  public final void onPackageRemoved(final String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {
      this.mAppStates.load(new Runnable()
      {
        public final void run()
        {
          UninstallRefundTracker localUninstallRefundTracker = UninstallRefundTracker.this;
          String str = paramString;
          AppActionAnalyzer localAppActionAnalyzer = new AppActionAnalyzer(str, FinskyApp.get().mAppStates, FinskyApp.get().mLibraries);
          if (localAppActionAnalyzer.isRefundable) {
            AppSupport.silentRefund(null, str, localAppActionAnalyzer.refundAccount, false, new UninstallRefundTracker.2(localUninstallRefundTracker));
          }
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.UninstallRefundTracker
 * JD-Core Version:    0.7.0.1
 */