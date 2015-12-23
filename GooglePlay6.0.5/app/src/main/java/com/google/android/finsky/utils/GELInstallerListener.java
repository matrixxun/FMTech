package com.google.android.finsky.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.finsky.config.G;
import com.google.android.finsky.installer.InstallerListener;
import com.google.android.finsky.receivers.Installer;
import com.google.android.play.utils.config.GservicesValue;

public final class GELInstallerListener
  implements InstallerListener
{
  protected static GELInstallerListener sInstance;
  protected final Context mContext;
  protected final Installer mInstaller;
  
  public GELInstallerListener(Context paramContext, Installer paramInstaller)
  {
    sInstance = this;
    this.mContext = paramContext;
    this.mInstaller = paramInstaller;
    this.mInstaller.addListener(this);
  }
  
  public final void onInstallPackageEvent(String paramString, int paramInt1, int paramInt2)
  {
    Intent localIntent = null;
    switch (paramInt1)
    {
    default: 
    case 0: 
    case 1: 
    case 4: 
      for (;;)
      {
        if (localIntent != null)
        {
          localIntent.setPackage((String)G.gelPackageName.get());
          localIntent.setData(Uri.fromParts("package", paramString, null));
          if (FinskyLog.DEBUG)
          {
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = localIntent.getData();
            arrayOfObject[1] = localIntent.getAction();
            arrayOfObject[2] = paramString;
            FinskyLog.d("GEL broadcast uri=[%s], action=[%s], for package=[%s]", arrayOfObject);
          }
          this.mContext.sendBroadcast(localIntent);
        }
        return;
        localIntent = new Intent("com.android.launcher.action.ACTION_PACKAGE_ENQUEUED");
        continue;
        localIntent = new Intent("com.android.launcher.action.ACTION_PACKAGE_DOWNLOADING");
        continue;
        localIntent = new Intent("com.android.launcher.action.ACTION_PACKAGE_INSTALLING");
      }
    }
    localIntent = new Intent("com.android.launcher.action.ACTION_PACKAGE_DEQUEUED");
    if (paramInt1 == 6) {}
    for (boolean bool = true;; bool = false)
    {
      localIntent.putExtra("com.android.launcher.action.INSTALL_COMPLETED", bool);
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.GELInstallerListener
 * JD-Core Version:    0.7.0.1
 */