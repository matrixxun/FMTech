package com.google.android.finsky.utils;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;

public final class UninstallSubscriptionsTracker
  implements PackageMonitorReceiver.PackageStatusListener
{
  final Context mContext;
  DfeDetails mDfeDetails;
  final Libraries mLibraries;
  
  public UninstallSubscriptionsTracker(Context paramContext, Libraries paramLibraries, PackageMonitorReceiver paramPackageMonitorReceiver)
  {
    this.mLibraries = paramLibraries;
    this.mContext = paramContext;
    paramPackageMonitorReceiver.attach(this);
  }
  
  public final void onPackageAdded(String paramString) {}
  
  public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
  
  public final void onPackageChanged(String paramString) {}
  
  public final void onPackageFirstLaunch(String paramString) {}
  
  public final void onPackageRemoved(final String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {
      this.mLibraries.load(new Runnable()
      {
        public final void run()
        {
          UninstallSubscriptionsTracker localUninstallSubscriptionsTracker = UninstallSubscriptionsTracker.this;
          String str = paramString;
          if (DocUtils.hasAutoRenewingSubscriptions(localUninstallSubscriptionsTracker.mLibraries, str))
          {
            localUninstallSubscriptionsTracker.mDfeDetails = new DfeDetails(FinskyApp.get().getDfeApi(null), DfeUtils.createDetailsUrlFromId(str));
            localUninstallSubscriptionsTracker.mDfeDetails.addDataChangedListener(new UninstallSubscriptionsTracker.2(localUninstallSubscriptionsTracker, str));
          }
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.UninstallSubscriptionsTracker
 * JD-Core Version:    0.7.0.1
 */