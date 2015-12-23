package com.google.android.finsky.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.protos.DeliveryResponse;
import com.google.android.finsky.protos.SelfUpdateResponse;

public final class SelfUpdateScheduler
  implements DownloadQueueListener
{
  private static String sCertificateHash;
  private static String sCertificateHashSelfUpdateMD5;
  final DownloadQueue mDownloadQueue;
  String mDownloadSignature = null;
  long mDownloadSize = -1L;
  PlayStore.AppData mLogAppData = null;
  private int mMarketVersion;
  private ApplicationDismissedDeferrer mOnAppExitDeferrer;
  Download mUpdateDownload = null;
  public boolean mUpdateInProgress = false;
  
  public SelfUpdateScheduler(DownloadQueue paramDownloadQueue, int paramInt)
  {
    this.mDownloadQueue = paramDownloadQueue;
    this.mMarketVersion = paramInt;
  }
  
  private static void computeClientHashes(Context paramContext)
  {
    String str = paramContext.getPackageName();
    try
    {
      byte[] arrayOfByte = paramContext.getPackageManager().getPackageInfo(str, 64).signatures[0].toByteArray();
      sCertificateHashSelfUpdateMD5 = Md5Util.secureHash(arrayOfByte);
      sCertificateHash = Sha1Util.secureHash(arrayOfByte);
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      FinskyLog.wtf("Unable to find package info for %s - %s", new Object[] { str, localNameNotFoundException });
      sCertificateHashSelfUpdateMD5 = "signature-hash-NameNotFoundException";
      sCertificateHash = "certificate-hash-NameNotFoundException";
    }
  }
  
  private static String getCertificateHash(Context paramContext)
  {
    try
    {
      if (sCertificateHash == null) {
        computeClientHashes(paramContext);
      }
      String str = sCertificateHash;
      return str;
    }
    finally {}
  }
  
  public static String getCertificateHashSelfUpdateMD5(Context paramContext)
  {
    try
    {
      if (sCertificateHashSelfUpdateMD5 == null) {
        computeClientHashes(paramContext);
      }
      String str = sCertificateHashSelfUpdateMD5;
      return str;
    }
    finally {}
  }
  
  public static int getNewVersion(SelfUpdateResponse paramSelfUpdateResponse)
  {
    int i = -1;
    if (paramSelfUpdateResponse.hasLatestClientVersionCode) {
      i = paramSelfUpdateResponse.latestClientVersionCode;
    }
    return i;
  }
  
  public final boolean checkForSelfUpdate(int paramInt, String paramString)
  {
    if (this.mUpdateInProgress)
    {
      FinskyLog.d("Skipping DFE self-update check as there is an update already queued.", new Object[0]);
      return true;
    }
    if (this.mMarketVersion >= paramInt)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(this.mMarketVersion);
      arrayOfObject2[1] = Integer.valueOf(paramInt);
      FinskyLog.d("Skipping DFE self-update. Local Version [%d] >= Server Version [%d]", arrayOfObject2);
      return false;
    }
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(this.mMarketVersion);
    arrayOfObject1[1] = Integer.valueOf(paramInt);
    FinskyLog.d("Starting DFE self-update from local version [%d] to server version [%d]", arrayOfObject1);
    this.mUpdateInProgress = true;
    this.mLogAppData = new PlayStore.AppData();
    this.mLogAppData.oldVersion = this.mMarketVersion;
    this.mLogAppData.hasOldVersion = true;
    this.mLogAppData.version = paramInt;
    this.mLogAppData.hasVersion = true;
    this.mLogAppData.systemApp = true;
    this.mLogAppData.hasSystemApp = true;
    FinskyApp localFinskyApp = FinskyApp.get();
    String str1 = localFinskyApp.getPackageName();
    String str2 = getCertificateHashSelfUpdateMD5(localFinskyApp);
    String str3 = getCertificateHash(localFinskyApp);
    FinskyApp.get().getDfeApi(paramString).delivery$5df18dd4(str1, null, Integer.valueOf(paramInt), null, null, str2, str3, null, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        SelfUpdateScheduler.this.mUpdateInProgress = false;
        FinskyLog.w("SelfUpdate error - %s", new Object[] { paramAnonymousVolleyError });
      }
    });
    return true;
  }
  
  public final void onCancel(Download paramDownload) {}
  
  public final void onComplete(final Download paramDownload)
  {
    if (paramDownload != this.mUpdateDownload)
    {
      FinskyLog.d("Self-update ignoring completed download %s", new Object[] { paramDownload });
      return;
    }
    final String str = FinskyApp.get().getPackageName();
    FinskyApp.get().getEventLogger().logBackgroundEvent(102, str, null, 0, null, this.mLogAppData);
    this.mUpdateDownload = null;
    if (this.mOnAppExitDeferrer != null)
    {
      FinskyLog.d("Self-update package Uri was already assigned!", new Object[0]);
      return;
    }
    FinskyLog.d("Self-update ready to be installed, waiting for market to close.", new Object[0]);
    this.mOnAppExitDeferrer = new ApplicationDismissedDeferrer(FinskyApp.get());
    this.mOnAppExitDeferrer.runOnApplicationClose(new Runnable()
    {
      public final void run()
      {
        PackageManagerHelper.InstallPackageListener local1 = new PackageManagerHelper.InstallPackageListener()
        {
          public final void installFailed(int paramAnonymous2Int, String paramAnonymous2String)
          {
            FinskyApp.get().getEventLogger().logBackgroundEvent(111, SelfUpdateScheduler.3.this.val$packageName, null, paramAnonymous2Int, paramAnonymous2String, SelfUpdateScheduler.this.mLogAppData);
          }
          
          public final void installSucceeded()
          {
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = SelfUpdateScheduler.3.this.val$packageName;
            FinskyLog.wtf("Unexpected install success for %s", arrayOfObject);
          }
        };
        PackageManagerHelper.installPackage(paramDownload.getContentUri(), SelfUpdateScheduler.this.mDownloadSize, SelfUpdateScheduler.this.mDownloadSignature, local1, false, "");
      }
    }, 10000);
  }
  
  public final void onError(Download paramDownload, int paramInt)
  {
    if (paramDownload == this.mUpdateDownload)
    {
      this.mUpdateInProgress = false;
      FinskyApp.get().getEventLogger().logBackgroundEvent(104, FinskyApp.get().getPackageName(), null, paramInt, null, this.mLogAppData);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.e("Self-update failed because of HTTP error code: %d", arrayOfObject);
    }
  }
  
  public final void onNotificationClicked(Download paramDownload) {}
  
  public final void onProgress(Download paramDownload, DownloadProgress paramDownloadProgress) {}
  
  public final void onStart(Download paramDownload)
  {
    if (paramDownload == this.mUpdateDownload) {
      FinskyApp.get().getEventLogger().logBackgroundEvent(101, FinskyApp.get().getPackageName(), null, 0, null, this.mLogAppData);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.SelfUpdateScheduler
 * JD-Core Version:    0.7.0.1
 */