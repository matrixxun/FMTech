package com.google.android.finsky.receivers;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.installer.InstallerListener;
import java.util.List;

public abstract interface Installer
{
  public abstract void addListener(InstallerListener paramInstallerListener);
  
  public abstract void cancel(String paramString);
  
  public abstract void cancelAll();
  
  public abstract int extractInstallLocation(Document paramDocument);
  
  public abstract InstallerProgressReport getProgress(String paramString);
  
  public abstract int getState(String paramString);
  
  public abstract void promiseInstall$1718defc(String paramString1, String paramString2);
  
  public abstract void removeListener(InstallerListener paramInstallerListener);
  
  public abstract void requestInstall(String paramString1, int paramInt1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, int paramInt2, int paramInt3);
  
  public abstract void setDeliveryToken(String paramString1, String paramString2);
  
  public abstract void setEarlyUpdate(String paramString);
  
  public abstract void setMobileDataAllowed(String paramString);
  
  public abstract void setMobileDataProhibited(String paramString);
  
  public abstract void setOverrideForegroundCheck(String paramString);
  
  public abstract void setVisibility(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract void start(Runnable paramRunnable);
  
  public abstract void startDeferredInstalls();
  
  public abstract void uninstallAssetSilently(String paramString, boolean paramBoolean);
  
  public abstract void uninstallPackagesByUid$505cbf4b(String paramString);
  
  public abstract void updateInstalledApps$189ce961(List<Document> paramList, String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
  
  public abstract void updateSingleInstalledApp(String paramString1, int paramInt1, String paramString2, String paramString3, boolean paramBoolean, int paramInt2, int paramInt3);
  
  public static final class InstallerProgressReport
  {
    public final long bytesCompleted;
    public final long bytesTotal;
    public final int downloadStatus;
    public final int installerState;
    
    public InstallerProgressReport(int paramInt1, long paramLong1, long paramLong2, int paramInt2)
    {
      this.installerState = paramInt1;
      this.bytesCompleted = paramLong1;
      this.bytesTotal = paramLong2;
      this.downloadStatus = paramInt2;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.Installer
 * JD-Core Version:    0.7.0.1
 */