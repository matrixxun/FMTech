package com.google.android.finsky.installer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.finsky.utils.PackageManagerHelper.InstallPackageListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public final class PackageInstallerLegacyImpl
  implements PackageInstallerFacade
{
  Uri mContentUri = null;
  private final Context mContext;
  String mInstallingPackageName = null;
  File mOutputFile = null;
  
  public PackageInstallerLegacyImpl(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public final void cancelSession(String paramString)
  {
    try
    {
      if (paramString.equals(this.mInstallingPackageName))
      {
        if (this.mOutputFile != null)
        {
          this.mOutputFile.delete();
          this.mOutputFile = null;
        }
        if (this.mContentUri != null) {
          this.mContentUri = null;
        }
        this.mInstallingPackageName = null;
      }
      return;
    }
    finally {}
  }
  
  public final void createSession(String paramString1, long paramLong, String paramString2, Bitmap paramBitmap, int paramInt) {}
  
  public final void finishStream(OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.flush();
    paramOutputStream.close();
  }
  
  public final int getAppIconSize()
  {
    return -1;
  }
  
  public final OutputStream getStream(String paramString1, String paramString2, long paramLong)
    throws IOException
  {
    int i = 1;
    File localFile1;
    try
    {
      if (this.mOutputFile != null)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = this.mOutputFile.getName();
        arrayOfObject2[1] = this.mInstallingPackageName;
        FinskyLog.wtf("Already streaming file %s for %s", arrayOfObject2);
      }
      if (this.mContentUri != null)
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = this.mContentUri;
        arrayOfObject1[1] = this.mInstallingPackageName;
        FinskyLog.wtf("Already tracking file %s for %s", arrayOfObject1);
        this.mOutputFile = null;
      }
      localFile1 = this.mContext.getCacheDir();
      if (!localFile1.setExecutable(true, false))
      {
        FinskyLog.w("Could not make executable %s", new Object[] { localFile1 });
        throw new IOException("Could not make cache dir executable");
      }
    }
    finally {}
    File localFile2 = new File(localFile1, "copies");
    localFile2.mkdirs();
    if ((localFile2.setExecutable(true, false)) && (localFile2.setReadable(true, false))) {}
    for (;;)
    {
      if (i == 0)
      {
        FinskyLog.w("Could not make readable %s", new Object[] { localFile2 });
        throw new IOException("Could not make destination dir readable");
      }
      File localFile3 = File.createTempFile(paramString1, ".apk", localFile2);
      if (!localFile3.setReadable(true, false))
      {
        FinskyLog.w("Could not make readable %s", new Object[] { localFile3 });
        localFile3.delete();
        throw new IOException("Could not make destination file writeable");
      }
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile3);
        this.mInstallingPackageName = paramString1;
        this.mOutputFile = localFile3;
        return localFileOutputStream;
      }
      catch (IOException localIOException)
      {
        localFile3.delete();
        throw localIOException;
      }
      i = 0;
    }
  }
  
  public final boolean hasSession(String paramString)
  {
    return false;
  }
  
  public final void install(String paramString, boolean paramBoolean, final PackageInstallerFacade.InstallListener paramInstallListener)
  {
    try
    {
      if (this.mOutputFile != null) {}
      for (Uri localUri = Uri.fromFile(this.mOutputFile);; localUri = this.mContentUri)
      {
        PackageManagerHelper.installPackage(localUri, -1L, null, new PackageManagerHelper.InstallPackageListener()
        {
          private void commonCleanup()
          {
            synchronized (PackageInstallerLegacyImpl.this)
            {
              if (PackageInstallerLegacyImpl.this.mOutputFile != null)
              {
                PackageInstallerLegacyImpl.this.mOutputFile.delete();
                PackageInstallerLegacyImpl.this.mOutputFile = null;
              }
              if (PackageInstallerLegacyImpl.this.mContentUri != null) {
                PackageInstallerLegacyImpl.this.mContentUri = null;
              }
              PackageInstallerLegacyImpl.this.mInstallingPackageName = null;
              return;
            }
          }
          
          public final void installFailed(int paramAnonymousInt, String paramAnonymousString)
          {
            synchronized (PackageInstallerLegacyImpl.this)
            {
              commonCleanup();
              paramInstallListener.installFailed(paramAnonymousInt, paramAnonymousString);
              return;
            }
          }
          
          public final void installSucceeded()
          {
            commonCleanup();
            paramInstallListener.installSucceeded();
          }
        }, paramBoolean, paramString);
        return;
        if (this.mContentUri == null) {
          break;
        }
      }
      throw new IllegalStateException("No file or URI to install from");
    }
    finally {}
  }
  
  public final void pruneSessions(List<String> paramList) {}
  
  public final void reportProgress(String paramString, long paramLong1, long paramLong2) {}
  
  public final boolean requireCopy(boolean paramBoolean)
  {
    return paramBoolean;
  }
  
  public final void setAppIcon(String paramString, Bitmap paramBitmap) {}
  
  public final void setInstallUri(String paramString, Uri paramUri)
  {
    if (this.mOutputFile != null)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = this.mOutputFile.getName();
      arrayOfObject2[1] = this.mInstallingPackageName;
      FinskyLog.wtf("Already streaming file %s for %s", arrayOfObject2);
    }
    if (this.mContentUri != null)
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = this.mContentUri;
      arrayOfObject1[1] = this.mInstallingPackageName;
      FinskyLog.wtf("Already tracking file %s for %s", arrayOfObject1);
      this.mOutputFile = null;
    }
    this.mInstallingPackageName = paramString;
    this.mContentUri = paramUri;
  }
  
  public final void uninstallPackage(String paramString, boolean paramBoolean)
  {
    PackageManagerHelper.uninstallPackage(paramString, paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.installer.PackageInstallerLegacyImpl
 * JD-Core Version:    0.7.0.1
 */