package com.google.android.finsky.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.IPackageDataObserver.Stub;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.IPackageInstallObserver.Stub;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.download.DownloadManagerFacade;
import com.google.android.finsky.download.DownloadQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class PackageManagerHelper
{
  public static int installExistingPackage(Context paramContext, String paramString)
    throws PackageManager.NameNotFoundException
  {
    try
    {
      int i = ((Integer)PackageManager.class.getMethod("installExistingPackage", new Class[] { String.class }).invoke(paramContext.getPackageManager(), new Object[] { paramString })).intValue();
      return i;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      FinskyLog.d("Cannot install existing packages on this platform", new Object[0]);
      return 979;
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        FinskyLog.wtf(localSecurityException, "Cannot install existing packages due to security exception", new Object[0]);
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      for (;;)
      {
        FinskyLog.wtf(localIllegalAccessException, "Cannot install existing packages due to reflection access exception", new Object[0]);
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      for (;;)
      {
        Throwable localThrowable = localInvocationTargetException.getCause();
        if ((localInvocationTargetException.getCause() instanceof PackageManager.NameNotFoundException)) {
          throw ((PackageManager.NameNotFoundException)localThrowable);
        }
        FinskyLog.wtf(localInvocationTargetException, "Cannot install existing packages due to reflection invocation exception", new Object[0]);
      }
    }
  }
  
  public static void installPackage(Context paramContext, Uri paramUri, PackageInstallObserver paramPackageInstallObserver, int paramInt)
  {
    IPackageInstallObserver.Stub local1 = new IPackageInstallObserver.Stub()
    {
      public final void packageInstalled(String paramAnonymousString, int paramAnonymousInt)
      {
        if (this.val$observer != null) {
          this.val$observer.packageInstalled(paramAnonymousString, paramAnonymousInt);
        }
      }
    };
    try
    {
      Class[] arrayOfClass = new Class[4];
      arrayOfClass[0] = Uri.class;
      arrayOfClass[1] = IPackageInstallObserver.class;
      arrayOfClass[2] = Integer.TYPE;
      arrayOfClass[3] = String.class;
      Method localMethod = PackageManager.class.getMethod("installPackage", arrayOfClass);
      PackageManager localPackageManager = paramContext.getPackageManager();
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = paramUri;
      arrayOfObject[1] = local1;
      arrayOfObject[2] = Integer.valueOf(paramInt);
      arrayOfObject[3] = "com.android.vending";
      localMethod.invoke(localPackageManager, arrayOfObject);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      FinskyLog.d("Cannot install packages on this platform", new Object[0]);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      FinskyLog.wtf(localSecurityException, "Cannot install packages due to security exception", new Object[0]);
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      FinskyLog.wtf(localIllegalAccessException, "Cannot install packages due to reflection access exception", new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      FinskyLog.wtf(localInvocationTargetException, "Cannot install packages due to reflection invocation exception", new Object[0]);
    }
  }
  
  public static void installPackage(Uri paramUri, long paramLong, String paramString1, InstallPackageListener paramInstallPackageListener, boolean paramBoolean, String paramString2)
  {
    Utils.executeMultiThreaded(new OnCompleteListenerNotifier(paramUri, paramLong, paramString1, paramInstallPackageListener, paramBoolean, paramString2, false, (byte)0), new Void[0]);
  }
  
  public static void uninstallPackage(String paramString, boolean paramBoolean)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    for (;;)
    {
      try
      {
        Class[] arrayOfClass = new Class[3];
        arrayOfClass[0] = String.class;
        arrayOfClass[1] = IPackageDeleteObserver.class;
        arrayOfClass[2] = Integer.TYPE;
        Method localMethod = PackageManager.class.getMethod("deletePackage", arrayOfClass);
        if (paramBoolean)
        {
          i = 4;
          PackageManager localPackageManager = localFinskyApp.getPackageManager();
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = paramString;
          arrayOfObject[1] = null;
          arrayOfObject[2] = Integer.valueOf(i);
          localMethod.invoke(localPackageManager, arrayOfObject);
          return;
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        FinskyLog.d("Cannot delete packages on this platform", new Object[0]);
        return;
      }
      catch (SecurityException localSecurityException)
      {
        FinskyLog.wtf(localSecurityException, "Cannot delete packages due to security exception", new Object[0]);
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        FinskyLog.wtf(localIllegalAccessException, "Cannot delete packages due to reflection access exception", new Object[0]);
        return;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        FinskyLog.wtf(localInvocationTargetException, "Cannot delete packages due to reflection invocation exception", new Object[0]);
        return;
      }
      int i = 0;
    }
  }
  
  public static abstract interface FreeSpaceListener
  {
    public abstract void onComplete(boolean paramBoolean);
  }
  
  public static abstract interface InstallPackageListener
  {
    public abstract void installFailed(int paramInt, String paramString);
    
    public abstract void installSucceeded();
  }
  
  private static final class OnCompleteListenerNotifier
    extends AsyncTask<Void, Void, Uri>
  {
    private final boolean mAllowDowngrade;
    private final Uri mContentUri;
    private volatile Sha1Util.DigestResult mDigestResult;
    private final String mExpectedSignature;
    private final long mExpectedSize;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final boolean mIsForwardLocked;
    private final String mPackageName;
    private final PackageManagerHelper.InstallPackageListener mPostInstallCallback;
    private volatile IOException mVerificationException;
    
    private OnCompleteListenerNotifier(Uri paramUri, long paramLong, String paramString1, PackageManagerHelper.InstallPackageListener paramInstallPackageListener, boolean paramBoolean1, String paramString2, boolean paramBoolean2)
    {
      this.mContentUri = paramUri;
      this.mExpectedSize = paramLong;
      this.mExpectedSignature = paramString1;
      this.mPostInstallCallback = paramInstallPackageListener;
      this.mIsForwardLocked = paramBoolean1;
      this.mPackageName = paramString2;
      this.mAllowDowngrade = paramBoolean2;
    }
    
    private Uri doInBackground$34e9db1e()
    {
      this.mVerificationException = null;
      if (this.mExpectedSize >= 0L) {}
      try
      {
        this.mDigestResult = Sha1Util.digest(FinskyApp.get().getContentResolver().openInputStream(this.mContentUri));
        return FinskyApp.get().mDownloadQueue.getDownloadManager().getFileUriForContentUri(this.mContentUri);
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          this.mVerificationException = localIOException;
        }
      }
    }
  }
  
  public static abstract interface PackageInstallObserver
  {
    public abstract void packageInstalled(String paramString, int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.PackageManagerHelper
 * JD-Core Version:    0.7.0.1
 */