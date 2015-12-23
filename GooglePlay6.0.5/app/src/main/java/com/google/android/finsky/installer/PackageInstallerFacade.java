package com.google.android.finsky.installer;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract interface PackageInstallerFacade
{
  public abstract void cancelSession(String paramString);
  
  public abstract void createSession(String paramString1, long paramLong, String paramString2, Bitmap paramBitmap, int paramInt);
  
  public abstract void finishStream(OutputStream paramOutputStream)
    throws IOException;
  
  public abstract int getAppIconSize();
  
  public abstract OutputStream getStream(String paramString1, String paramString2, long paramLong)
    throws IOException;
  
  public abstract boolean hasSession(String paramString);
  
  public abstract void install(String paramString, boolean paramBoolean, InstallListener paramInstallListener);
  
  public abstract void pruneSessions(List<String> paramList);
  
  public abstract void reportProgress(String paramString, long paramLong1, long paramLong2);
  
  public abstract boolean requireCopy(boolean paramBoolean);
  
  public abstract void setAppIcon(String paramString, Bitmap paramBitmap);
  
  public abstract void setInstallUri(String paramString, Uri paramUri);
  
  public abstract void uninstallPackage(String paramString, boolean paramBoolean);
  
  public static abstract interface InstallListener
  {
    public abstract void installFailed(int paramInt, String paramString);
    
    public abstract void installSucceeded();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.installer.PackageInstallerFacade
 * JD-Core Version:    0.7.0.1
 */