package com.google.android.finsky.download.obb;

import android.os.Build.VERSION;
import com.google.android.finsky.receivers.PackageMonitorReceiver.PackageStatusListener;
import java.io.File;

public final class ObbPackageTracker
  implements PackageMonitorReceiver.PackageStatusListener
{
  private final int GINGERBREAD_MR1 = 10;
  
  public final void onPackageAdded(String paramString) {}
  
  public final void onPackageAvailabilityChanged$1407608a(String[] paramArrayOfString) {}
  
  public final void onPackageChanged(String paramString) {}
  
  public final void onPackageFirstLaunch(String paramString) {}
  
  public final void onPackageRemoved(String paramString, boolean paramBoolean)
  {
    if ((Build.VERSION.SDK_INT <= 10) && (!paramBoolean))
    {
      File localFile = ObbFactory.getParentDirectory(paramString);
      if (localFile.exists())
      {
        File[] arrayOfFile = localFile.listFiles();
        int i = arrayOfFile.length;
        for (int j = 0; j < i; j++) {
          arrayOfFile[j].delete();
        }
        localFile.delete();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.obb.ObbPackageTracker
 * JD-Core Version:    0.7.0.1
 */