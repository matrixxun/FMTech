package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParameterizedRunnable;
import java.util.ArrayList;
import java.util.List;

public final class PackageMonitorReceiver
{
  private final List<PackageStatusListener> mListeners = new ArrayList();
  
  public final void attach(PackageStatusListener paramPackageStatusListener)
  {
    this.mListeners.add(paramPackageStatusListener);
  }
  
  public final void detach(PackageStatusListener paramPackageStatusListener)
  {
    this.mListeners.remove(paramPackageStatusListener);
  }
  
  final void notifyListeners(ParameterizedRunnable<PackageStatusListener> paramParameterizedRunnable)
  {
    for (int i = -1 + this.mListeners.size(); i >= 0; i--) {
      paramParameterizedRunnable.run(this.mListeners.get(i));
    }
  }
  
  public static abstract interface PackageStatusListener
  {
    public abstract void onPackageAdded(String paramString);
    
    public abstract void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString);
    
    public abstract void onPackageChanged(String paramString);
    
    public abstract void onPackageFirstLaunch(String paramString);
    
    public abstract void onPackageRemoved(String paramString, boolean paramBoolean);
  }
  
  public static class RegisteredReceiver
    extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      boolean bool1 = true;
      int i = 0;
      PackageMonitorReceiver localPackageMonitorReceiver = FinskyApp.get().mPackageMonitorReceiver;
      String str1 = paramIntent.getAction();
      PackageStateRepository localPackageStateRepository = FinskyApp.get().mPackageStateRepository;
      boolean bool2 = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(str1);
      boolean bool3 = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(str1);
      if ((bool2) || (bool3))
      {
        String[] arrayOfString = paramIntent.getStringArrayExtra("android.intent.extra.changed_package_list");
        int j = arrayOfString.length;
        while (i < j)
        {
          localPackageStateRepository.invalidate(arrayOfString[i]);
          i++;
        }
        localPackageMonitorReceiver.notifyListeners(new PackageMonitorReceiver.1(localPackageMonitorReceiver, arrayOfString, bool2));
        return;
      }
      Uri localUri = paramIntent.getData();
      String str2;
      if (localUri != null)
      {
        str2 = localUri.getSchemeSpecificPart();
        label130:
        if (str2 == null) {
          break label198;
        }
        localPackageStateRepository.invalidate(str2);
        if (!"android.intent.action.PACKAGE_REMOVED".equals(str1)) {
          break label205;
        }
        Bundle localBundle = paramIntent.getExtras();
        if ((localBundle == null) || (!localBundle.getBoolean("android.intent.extra.REPLACING", false))) {
          break label200;
        }
      }
      for (;;)
      {
        localPackageMonitorReceiver.notifyListeners(new PackageMonitorReceiver.2(localPackageMonitorReceiver, str2, bool1));
        return;
        str2 = null;
        break label130;
        label198:
        break;
        label200:
        bool1 = false;
      }
      label205:
      if ("android.intent.action.PACKAGE_ADDED".equals(str1))
      {
        localPackageMonitorReceiver.notifyListeners(new PackageMonitorReceiver.3(localPackageMonitorReceiver, str2));
        ExpireLaunchUrlReceiver.schedule(paramContext, str2);
        return;
      }
      if ("android.intent.action.PACKAGE_CHANGED".equals(str1))
      {
        localPackageMonitorReceiver.notifyListeners(new PackageMonitorReceiver.4(localPackageMonitorReceiver, str2));
        return;
      }
      if ("android.intent.action.PACKAGE_FIRST_LAUNCH".equals(str1))
      {
        localPackageMonitorReceiver.notifyListeners(new PackageMonitorReceiver.5(localPackageMonitorReceiver, str2));
        return;
      }
      Object[] arrayOfObject = new Object[bool1];
      arrayOfObject[0] = str1;
      FinskyLog.w("Unhandled intent type action type: %s", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.PackageMonitorReceiver
 * JD-Core Version:    0.7.0.1
 */