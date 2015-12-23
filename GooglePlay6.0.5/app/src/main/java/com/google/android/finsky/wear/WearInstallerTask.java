package com.google.android.finsky.wear;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.AppData;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.GmsCoreHelper;
import com.google.android.finsky.appstate.InstallerDataStore;
import com.google.android.finsky.appstate.InstallerDataStore.InstallerData;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.appstate.WriteThroughInstallerDataStore;
import com.google.android.finsky.config.G;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadImpl;
import com.google.android.finsky.download.DownloadQueue;
import com.google.android.finsky.installer.InstallPolicies;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.AndroidAppDeliveryData;
import com.google.android.finsky.protos.DeliveryResponse;
import com.google.android.finsky.protos.HttpCookie;
import com.google.android.finsky.protos.SplitDeliveryData;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SelfUpdateScheduler;
import com.google.android.finsky.utils.Sha1Util.DigestResult;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.play.utils.config.GservicesValue;
import com.google.android.volley.DisplayMessageError;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class WearInstallerTask
{
  private static final String[] SUPPORTED_PATCH_FORMATS = null;
  private SplitDeliveryData mActiveSplit = null;
  long mApkCompleted;
  private long mApkSize;
  final AppStates mAppStates;
  private final Context mContext;
  private final DownloadQueue mDownloadQueue;
  int mDownloadStatus;
  private final GoogleApiClient mGoogleApiClient;
  private final InstallPolicies mInstallPolicies;
  private final WearInstaller mInstaller;
  final InstallerDataStore mInstallerDataStore;
  boolean mIsUpdate;
  PlayStore.AppData mLogAppData;
  private boolean mMobileDataAllowed;
  private File mOutputFile;
  int mRecoveredIntoState;
  boolean mShowCompletionNotifications;
  boolean mShowErrorNotifications;
  boolean mShowProgress;
  String mTitle;
  private long mTotalSize;
  public final String nodeId;
  public final String packageName;
  
  public WearInstallerTask(String paramString1, String paramString2, Context paramContext, WearInstaller paramWearInstaller, AppStates paramAppStates, DownloadQueue paramDownloadQueue, InstallPolicies paramInstallPolicies, GoogleApiClient paramGoogleApiClient)
  {
    this.packageName = paramString1;
    this.nodeId = paramString2;
    this.mContext = paramContext;
    this.mInstaller = paramWearInstaller;
    this.mAppStates = paramAppStates;
    this.mDownloadQueue = paramDownloadQueue;
    this.mInstallPolicies = paramInstallPolicies;
    this.mGoogleApiClient = paramGoogleApiClient;
    this.mInstallerDataStore = paramAppStates.mStateStore;
  }
  
  private void continueInstaller(final AppStates.AppState paramAppState)
  {
    InstallerDataStore.InstallerData localInstallerData = paramAppState.installerData;
    FinskyApp.get().getEventLogger().logBackgroundEvent(106, this.packageName, null, 0, null, this.mLogAppData);
    final String str1 = this.nodeId + "-" + System.currentTimeMillis();
    final String str2 = this.packageName + "-v" + localInstallerData.desiredVersion + "-" + this.nodeId;
    try
    {
      ParcelFileDescriptor[] arrayOfParcelFileDescriptor = ParcelFileDescriptor.createPipe();
      ParcelFileDescriptor localParcelFileDescriptor1 = arrayOfParcelFileDescriptor[0];
      ParcelFileDescriptor localParcelFileDescriptor2 = arrayOfParcelFileDescriptor[1];
      final String str3 = "/wearable_info/" + this.packageName + "/" + str2;
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = this.packageName;
      arrayOfObject1[1] = this.nodeId;
      arrayOfObject1[2] = str3;
      FinskyLog.d("Writing Asset to install %s (%s) to %s", arrayOfObject1);
      PutDataMapRequest localPutDataMapRequest = PutDataMapRequest.create(str3);
      localPutDataMapRequest.zzcew.putAsset("asset", Asset.createFromFd(localParcelFileDescriptor1));
      Wearable.DataApi.putDataItem(this.mGoogleApiClient, localPutDataMapRequest.asPutDataRequest()).setResultCallback(new ResultCallback() {});
      AsyncTask local7 = new AsyncTask()
      {
        /* Error */
        private static Throwable doInBackground(Object... paramAnonymousVarArgs)
        {
          // Byte code:
          //   0: aload_0
          //   1: iconst_0
          //   2: aaload
          //   3: checkcast 36	java/io/File
          //   6: astore_1
          //   7: aload_0
          //   8: iconst_1
          //   9: aaload
          //   10: checkcast 38	android/os/ParcelFileDescriptor
          //   13: astore_2
          //   14: aconst_null
          //   15: astore_3
          //   16: aconst_null
          //   17: astore 4
          //   19: new 40	java/io/FileInputStream
          //   22: dup
          //   23: aload_1
          //   24: invokespecial 43	java/io/FileInputStream:<init>	(Ljava/io/File;)V
          //   27: astore 5
          //   29: new 45	android/os/ParcelFileDescriptor$AutoCloseOutputStream
          //   32: dup
          //   33: aload_2
          //   34: invokespecial 48	android/os/ParcelFileDescriptor$AutoCloseOutputStream:<init>	(Landroid/os/ParcelFileDescriptor;)V
          //   37: astore 6
          //   39: aload 5
          //   41: aload 6
          //   43: invokestatic 54	com/google/android/finsky/utils/Utils:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)V
          //   46: aload 6
          //   48: invokevirtual 59	java/io/OutputStream:flush	()V
          //   51: aload 5
          //   53: invokestatic 63	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   56: aload 6
          //   58: invokestatic 63	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   61: aconst_null
          //   62: areturn
          //   63: astore 7
          //   65: aload_3
          //   66: invokestatic 63	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   69: aload 4
          //   71: invokestatic 63	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   74: aload 7
          //   76: areturn
          //   77: astore 8
          //   79: aload_3
          //   80: invokestatic 63	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   83: aload 4
          //   85: invokestatic 63	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   88: aload 8
          //   90: athrow
          //   91: astore 8
          //   93: aload 5
          //   95: astore_3
          //   96: aconst_null
          //   97: astore 4
          //   99: goto -20 -> 79
          //   102: astore 8
          //   104: aload 6
          //   106: astore 4
          //   108: aload 5
          //   110: astore_3
          //   111: goto -32 -> 79
          //   114: astore 7
          //   116: aload 5
          //   118: astore_3
          //   119: aconst_null
          //   120: astore 4
          //   122: goto -57 -> 65
          //   125: astore 7
          //   127: aload 6
          //   129: astore 4
          //   131: aload 5
          //   133: astore_3
          //   134: goto -69 -> 65
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	137	0	paramAnonymousVarArgs	Object[]
          //   6	18	1	localFile	File
          //   13	21	2	localParcelFileDescriptor	ParcelFileDescriptor
          //   15	119	3	localObject1	Object
          //   17	113	4	localObject2	Object
          //   27	105	5	localFileInputStream	java.io.FileInputStream
          //   37	91	6	localAutoCloseOutputStream	android.os.ParcelFileDescriptor.AutoCloseOutputStream
          //   63	12	7	localThrowable1	Throwable
          //   114	1	7	localThrowable2	Throwable
          //   125	1	7	localThrowable3	Throwable
          //   77	12	8	localObject3	Object
          //   91	1	8	localObject4	Object
          //   102	1	8	localObject5	Object
          // Exception table:
          //   from	to	target	type
          //   19	29	63	java/lang/Throwable
          //   19	29	77	finally
          //   29	39	91	finally
          //   39	51	102	finally
          //   29	39	114	java/lang/Throwable
          //   39	51	125	java/lang/Throwable
        }
      };
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = this.mOutputFile;
      arrayOfObject2[1] = localParcelFileDescriptor2;
      Utils.executeMultiThreaded(local7, arrayOfObject2);
      return;
    }
    catch (IOException localIOException)
    {
      FinskyLog.wtf("Could not create a pipe.", new Object[] { localIOException });
      cancelCleanupState(paramAppState);
    }
  }
  
  private void deleteStream()
  {
    if (this.mOutputFile != null)
    {
      this.mOutputFile.delete();
      this.mOutputFile = null;
    }
  }
  
  private OutputStream getStream()
    throws IOException
  {
    File localFile1 = new File(new File(this.mContext.getCacheDir(), "wear"), Uri.encode(this.nodeId));
    localFile1.mkdirs();
    File localFile2 = new File(localFile1, this.packageName + ".apk");
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
      this.mOutputFile = localFile2;
      return localFileOutputStream;
    }
    catch (IOException localIOException)
    {
      localFile2.delete();
      throw localIOException;
    }
  }
  
  private void startNextDownload(InstallerDataStore.InstallerData paramInstallerData)
  {
    if (InstallPolicies.isFreeSpaceSufficient(paramInstallerData.deliveryData.downloadSize, Environment.getDataDirectory(), FinskyApp.get().getContentResolver())) {}
    for (int i = 0; i != 0; i = 1)
    {
      return;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.packageName;
      arrayOfObject[1] = this.nodeId;
      FinskyLog.e("Cancel download of %s (%s) because insufficient free space", arrayOfObject);
      cancel$1385ff();
      FinskyApp.get().getEventLogger().logBackgroundEvent(112, this.packageName, "no-internal-storage", 908, null, this.mLogAppData);
    }
    AndroidAppDeliveryData localAndroidAppDeliveryData = paramInstallerData.deliveryData;
    SplitDeliveryData localSplitDeliveryData = new SplitDeliveryData();
    localSplitDeliveryData.downloadSize = localAndroidAppDeliveryData.downloadSize;
    localSplitDeliveryData.gzippedDownloadSize = localAndroidAppDeliveryData.gzippedDownloadSize;
    localSplitDeliveryData.downloadUrl = localAndroidAppDeliveryData.downloadUrl;
    localSplitDeliveryData.signature = localAndroidAppDeliveryData.signature;
    localSplitDeliveryData.downloadUrl = localAndroidAppDeliveryData.downloadUrl;
    localSplitDeliveryData.gzippedDownloadUrl = localAndroidAppDeliveryData.gzippedDownloadUrl;
    localSplitDeliveryData.patchData = localAndroidAppDeliveryData.patchData;
    this.mActiveSplit = localSplitDeliveryData;
    this.mInstallerDataStore.setActiveSplitId(this.packageName, this.packageName);
    String str1 = paramInstallerData.title;
    String str2 = paramInstallerData.packageName;
    HttpCookie localHttpCookie = localAndroidAppDeliveryData.downloadAuthCookie[0];
    int j = paramInstallerData.flags;
    int k = j & 0xFFFFEDFB;
    String str3 = this.mActiveSplit.downloadUrl;
    long l = this.mActiveSplit.downloadSize;
    if (k != j) {
      this.mInstallerDataStore.setFlags(str2, k);
    }
    String str4 = this.nodeId;
    String str5 = localHttpCookie.name;
    String str6 = localHttpCookie.value;
    boolean bool1;
    if (!this.mMobileDataAllowed)
    {
      bool1 = true;
      if (this.mShowProgress) {
        break label392;
      }
    }
    label392:
    for (boolean bool2 = true;; bool2 = false)
    {
      DownloadImpl localDownloadImpl = new DownloadImpl(str3, str1, str2, str4, str5, str6, null, l, l, null, bool1, bool2);
      this.mDownloadQueue.add(localDownloadImpl);
      FinskyApp.get().getEventLogger().logBackgroundEvent(100, localDownloadImpl.getDocIdForLog(), null, 0, null, this.mLogAppData);
      setInstallerState(40, null);
      return;
      bool1 = false;
      break;
    }
  }
  
  final void advanceState()
  {
    final AppStates.AppState localAppState = this.mAppStates.getApp(this.packageName);
    if ((localAppState == null) || (localAppState.installerData == null))
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = this.packageName;
      arrayOfObject1[1] = this.nodeId;
      FinskyLog.wtf("Unexpected missing installer data for %s (%s)", arrayOfObject1);
      cancel$1385ff();
      return;
    }
    InstallerDataStore.InstallerData localInstallerData1 = localAppState.installerData;
    switch (localInstallerData1.installerState)
    {
    case 45: 
    default: 
      Object[] arrayOfObject5 = new Object[3];
      arrayOfObject5[0] = Integer.valueOf(localInstallerData1.installerState);
      arrayOfObject5[1] = this.packageName;
      arrayOfObject5[2] = this.nodeId;
      FinskyLog.wtf("Bad state %d for %s (%s)", arrayOfObject5);
      cancel$1385ff();
      return;
    case 0: 
      int i = localInstallerData1.desiredVersion;
      PackageStateRepository.PackageState localPackageState1 = localAppState.packageManagerState;
      if (localPackageState1 != null) {}
      for (int j = localPackageState1.installedVersion;; j = -1)
      {
        if (j < i) {
          break label246;
        }
        if (j > i) {
          this.mAppStates.mStateStore.setDesiredVersion(this.packageName, j);
        }
        setInstallerState(70, null);
        break;
      }
      int k;
      if (localInstallerData1.deliveryData == null) {
        k = 0;
      }
      while (k == 0)
      {
        InstallerDataStore.InstallerData localInstallerData3 = localAppState.installerData;
        final String str3 = localInstallerData3.packageName;
        int m = localInstallerData3.desiredVersion;
        String str4 = localInstallerData3.deliveryToken;
        String str5 = localInstallerData3.accountForUpdate;
        FinskyApp localFinskyApp = FinskyApp.get();
        boolean bool1 = TextUtils.isEmpty(str5);
        Account localAccount = null;
        if (!bool1)
        {
          localAccount = AccountHandler.findAccount(str5, localFinskyApp);
          if (localAccount == null)
          {
            Object[] arrayOfObject4 = new Object[2];
            arrayOfObject4[0] = FinskyLog.scrubPii(str5);
            arrayOfObject4[1] = str3;
            FinskyLog.w("Account %s for update of %s no longer exists.", arrayOfObject4);
            this.mInstallerDataStore.setAccountForUpdate(str3, null);
          }
        }
        if (localAccount == null)
        {
          str5 = localInstallerData3.accountName;
          localAccount = AccountHandler.findAccount(str5, localFinskyApp);
        }
        if (localAccount == null)
        {
          FinskyLog.d("Invalid account %s", new Object[] { str5 });
          cancel$1385ff();
          logAndNotifyDownloadError(str3, 906, null);
          return;
          long l2 = localInstallerData1.deliveryDataTimestampMs;
          if ((l2 != 0L) && (l2 + ((Long)G.deliveryDataExpirationMs.get()).longValue() < System.currentTimeMillis()))
          {
            this.mInstallerDataStore.setDeliveryData(this.packageName, null, 0L);
            int n = localInstallerData1.flags;
            int i1 = n & 0xFFFFFDFB;
            if (i1 != n) {
              this.mInstallerDataStore.setFlags(this.packageName, i1);
            }
            k = 0;
          }
          else
          {
            k = 1;
          }
        }
        else
        {
          PackageStateRepository.PackageState localPackageState2 = localAppState.packageManagerState;
          String str6 = null;
          if (localPackageState2 != null) {
            str6 = localAppState.packageManagerState.certificateHashes[0];
          }
          boolean bool2 = localFinskyApp.getPackageName().equals(str3);
          String str7 = null;
          if (bool2) {
            str7 = SelfUpdateScheduler.getCertificateHashSelfUpdateMD5(localFinskyApp);
          }
          Response.Listener local2 = new Response.Listener() {};
          Response.ErrorListener local3 = new Response.ErrorListener()
          {
            public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
            {
              int i = InstallPolicies.volleyErrorToInstallerError(paramAnonymousVolleyError);
              if ((paramAnonymousVolleyError instanceof DisplayMessageError)) {}
              for (String str = ((DisplayMessageError)paramAnonymousVolleyError).mDisplayErrorHtml;; str = null)
              {
                Object[] arrayOfObject = new Object[2];
                arrayOfObject[0] = Integer.valueOf(i);
                arrayOfObject[1] = str;
                FinskyLog.d("Received VolleyError %d (%s)", arrayOfObject);
                WearInstallerTask.this.cancel$1385ff();
                FinskyApp.get().getEventLogger().logBackgroundEvent(104, str3, null, i, null, WearInstallerTask.this.mLogAppData);
                return;
              }
            }
          };
          byte[] arrayOfByte = localFinskyApp.mLibraries.getAccountLibrary(localAccount).getServerToken(AccountLibrary.LIBRARY_ID_APPS);
          WearServerApiFactory.getDfeApi(str5, this.nodeId).delivery$5df18dd4(str3, arrayOfByte, Integer.valueOf(m), null, SUPPORTED_PATCH_FORMATS, str7, str6, str4, local2, local3);
          setInstallerState(10, localInstallerData3.downloadUri);
          return;
        }
      }
    case 10: 
      processDeliveryData(localInstallerData1, false);
    case 40: 
      startNextDownload(localInstallerData1);
      return;
    case 50: 
      final InstallerDataStore.InstallerData localInstallerData2 = localAppState.installerData;
      String str1 = localInstallerData2.downloadUri;
      final Uri localUri = Uri.parse(str1);
      setInstallerState(52, localUri);
      final long l1 = this.mActiveSplit.downloadSize;
      String str2 = this.mActiveSplit.signature;
      Object[] arrayOfObject3 = new Object[4];
      arrayOfObject3[0] = this.packageName;
      arrayOfObject3[1] = this.nodeId;
      arrayOfObject3[2] = str1;
      arrayOfObject3[3] = Long.valueOf(l1);
      FinskyLog.d("Prepare to copy %s (%s) from %s (expect %d bytes)", arrayOfObject3);
      Utils.executeMultiThreaded(new AsyncTask()
      {
        /* Error */
        private Sha1Util.DigestResult doInBackground$1a3e6397()
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 23	com/google/android/finsky/wear/WearInstallerTask$4:val$installerData	Lcom/google/android/finsky/appstate/InstallerDataStore$InstallerData;
          //   4: getfield 43	com/google/android/finsky/appstate/InstallerDataStore$InstallerData:packageName	Ljava/lang/String;
          //   7: astore_1
          //   8: aconst_null
          //   9: astore_2
          //   10: aconst_null
          //   11: astore_3
          //   12: aload_0
          //   13: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   16: invokestatic 47	com/google/android/finsky/wear/WearInstallerTask:access$600	(Lcom/google/android/finsky/wear/WearInstallerTask;)Landroid/content/Context;
          //   19: invokevirtual 53	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
          //   22: aload_0
          //   23: getfield 25	com/google/android/finsky/wear/WearInstallerTask$4:val$downloadUri	Landroid/net/Uri;
          //   26: invokevirtual 59	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
          //   29: astore 7
          //   31: aload 7
          //   33: astore_2
          //   34: aload_0
          //   35: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   38: invokestatic 63	com/google/android/finsky/wear/WearInstallerTask:access$800	(Lcom/google/android/finsky/wear/WearInstallerTask;)Ljava/io/OutputStream;
          //   41: astore 10
          //   43: aload 10
          //   45: astore_3
          //   46: new 65	com/google/android/finsky/utils/Sha1Util$DigestStream
          //   49: dup
          //   50: aload_3
          //   51: invokespecial 68	com/google/android/finsky/utils/Sha1Util$DigestStream:<init>	(Ljava/io/OutputStream;)V
          //   54: astore 11
          //   56: aload_2
          //   57: aload 11
          //   59: invokestatic 74	com/google/android/finsky/utils/Utils:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)V
          //   62: aload 11
          //   64: invokevirtual 77	com/google/android/finsky/utils/Sha1Util$DigestStream:getDigest	()Lcom/google/android/finsky/utils/Sha1Util$DigestResult;
          //   67: astore 14
          //   69: aload_3
          //   70: invokestatic 80	com/google/android/finsky/wear/WearInstallerTask:access$900$7b8d1819	(Ljava/io/OutputStream;)V
          //   73: aload_2
          //   74: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   77: aload_3
          //   78: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   81: aload 14
          //   83: areturn
          //   84: astore 5
          //   86: aload_0
          //   87: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   90: aload_1
          //   91: ldc 86
          //   93: iconst_0
          //   94: invokestatic 90	com/google/android/finsky/wear/WearInstallerTask:access$700	(Lcom/google/android/finsky/wear/WearInstallerTask;Ljava/lang/String;Ljava/lang/String;I)V
          //   97: iconst_1
          //   98: anewarray 92	java/lang/Object
          //   101: astore 6
          //   103: aload 6
          //   105: iconst_0
          //   106: aload_0
          //   107: getfield 25	com/google/android/finsky/wear/WearInstallerTask$4:val$downloadUri	Landroid/net/Uri;
          //   110: aastore
          //   111: ldc 94
          //   113: aload 6
          //   115: invokestatic 100	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
          //   118: aconst_null
          //   119: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   122: aconst_null
          //   123: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   126: aconst_null
          //   127: areturn
          //   128: astore 8
          //   130: aload_0
          //   131: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   134: aload_1
          //   135: ldc 102
          //   137: iconst_0
          //   138: invokestatic 90	com/google/android/finsky/wear/WearInstallerTask:access$700	(Lcom/google/android/finsky/wear/WearInstallerTask;Ljava/lang/String;Ljava/lang/String;I)V
          //   141: iconst_3
          //   142: anewarray 92	java/lang/Object
          //   145: astore 9
          //   147: aload 9
          //   149: iconst_0
          //   150: aload_1
          //   151: aastore
          //   152: aload 9
          //   154: iconst_1
          //   155: aload_0
          //   156: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   159: getfield 105	com/google/android/finsky/wear/WearInstallerTask:nodeId	Ljava/lang/String;
          //   162: aastore
          //   163: aload 9
          //   165: iconst_2
          //   166: aload 8
          //   168: aastore
          //   169: ldc 107
          //   171: aload 9
          //   173: invokestatic 100	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
          //   176: aload_2
          //   177: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   180: aconst_null
          //   181: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   184: aconst_null
          //   185: areturn
          //   186: astore 12
          //   188: aload_0
          //   189: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   192: aload_1
          //   193: ldc 109
          //   195: iconst_0
          //   196: invokestatic 90	com/google/android/finsky/wear/WearInstallerTask:access$700	(Lcom/google/android/finsky/wear/WearInstallerTask;Ljava/lang/String;Ljava/lang/String;I)V
          //   199: iconst_3
          //   200: anewarray 92	java/lang/Object
          //   203: astore 13
          //   205: aload 13
          //   207: iconst_0
          //   208: aload_1
          //   209: aastore
          //   210: aload 13
          //   212: iconst_1
          //   213: aload_0
          //   214: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   217: getfield 105	com/google/android/finsky/wear/WearInstallerTask:nodeId	Ljava/lang/String;
          //   220: aastore
          //   221: aload 13
          //   223: iconst_2
          //   224: aload 12
          //   226: aastore
          //   227: ldc 107
          //   229: aload 13
          //   231: invokestatic 100	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
          //   234: aload_2
          //   235: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   238: aload_3
          //   239: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   242: aconst_null
          //   243: areturn
          //   244: astore 15
          //   246: aload_0
          //   247: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   250: aload_1
          //   251: ldc 111
          //   253: iconst_0
          //   254: invokestatic 90	com/google/android/finsky/wear/WearInstallerTask:access$700	(Lcom/google/android/finsky/wear/WearInstallerTask;Ljava/lang/String;Ljava/lang/String;I)V
          //   257: iconst_3
          //   258: anewarray 92	java/lang/Object
          //   261: astore 16
          //   263: aload 16
          //   265: iconst_0
          //   266: aload_1
          //   267: aastore
          //   268: aload 16
          //   270: iconst_1
          //   271: aload_0
          //   272: getfield 21	com/google/android/finsky/wear/WearInstallerTask$4:this$0	Lcom/google/android/finsky/wear/WearInstallerTask;
          //   275: getfield 105	com/google/android/finsky/wear/WearInstallerTask:nodeId	Ljava/lang/String;
          //   278: aastore
          //   279: aload 16
          //   281: iconst_2
          //   282: aload 15
          //   284: aastore
          //   285: ldc 113
          //   287: aload 16
          //   289: invokestatic 100	com/google/android/finsky/utils/FinskyLog:w	(Ljava/lang/String;[Ljava/lang/Object;)V
          //   292: aload_2
          //   293: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   296: aload_3
          //   297: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   300: aconst_null
          //   301: areturn
          //   302: astore 4
          //   304: aload_2
          //   305: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   308: aload_3
          //   309: invokestatic 84	com/google/android/finsky/utils/Utils:safeClose	(Ljava/io/Closeable;)V
          //   312: aload 4
          //   314: athrow
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	315	0	this	4
          //   7	260	1	str	String
          //   9	296	2	localObject1	Object
          //   11	298	3	localObject2	Object
          //   302	11	4	localObject3	Object
          //   84	1	5	localFileNotFoundException	java.io.FileNotFoundException
          //   101	13	6	arrayOfObject1	Object[]
          //   29	3	7	localInputStream	java.io.InputStream
          //   128	39	8	localIOException1	IOException
          //   145	27	9	arrayOfObject2	Object[]
          //   41	3	10	localOutputStream	OutputStream
          //   54	9	11	localDigestStream	com.google.android.finsky.utils.Sha1Util.DigestStream
          //   186	39	12	localIOException2	IOException
          //   203	27	13	arrayOfObject3	Object[]
          //   67	15	14	localDigestResult	Sha1Util.DigestResult
          //   244	39	15	localIOException3	IOException
          //   261	27	16	arrayOfObject4	Object[]
          // Exception table:
          //   from	to	target	type
          //   12	31	84	java/io/FileNotFoundException
          //   34	43	128	java/io/IOException
          //   46	69	186	java/io/IOException
          //   69	73	244	java/io/IOException
          //   12	31	302	finally
          //   34	43	302	finally
          //   46	69	302	finally
          //   69	73	302	finally
          //   86	118	302	finally
          //   130	176	302	finally
          //   188	234	302	finally
          //   246	292	302	finally
        }
      }, new Void[0]);
      return;
    case 60: 
      label246:
      if (!((Boolean)G.wearGmsVersionDependencyEnabled.get()).booleanValue())
      {
        continueInstaller(localAppState);
        return;
      }
      if (GmsCoreHelper.isGmsCore(this.packageName))
      {
        continueInstaller(localAppState);
        return;
      }
      AsyncTask local5 = new AsyncTask()
      {
        private Bundle doInBackground(File... paramAnonymousVarArgs)
        {
          try
          {
            File localFile = paramAnonymousVarArgs[0];
            PackageInfo localPackageInfo = WearInstallerTask.this.mContext.getPackageManager().getPackageArchiveInfo(localFile.getAbsolutePath(), 128);
            Bundle localBundle = null;
            if (localPackageInfo != null) {
              localBundle = localPackageInfo.applicationInfo.metaData;
            }
            return localBundle;
          }
          catch (Exception localException)
          {
            WearInstallerTask.access$1400(WearInstallerTask.this, 991, localException.toString());
          }
          return null;
        }
      };
      File[] arrayOfFile = new File[1];
      arrayOfFile[0] = this.mOutputFile;
      Utils.executeMultiThreaded(local5, arrayOfFile);
      return;
    }
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = this.packageName;
    arrayOfObject2[1] = this.nodeId;
    FinskyLog.d("Installation of %s (%s) now waiting for wearable to process", arrayOfObject2);
    this.mInstaller.taskFinished(this);
  }
  
  public final void cancel$1385ff()
  {
    AppStates.AppState localAppState = this.mAppStates.getApp(this.packageName);
    if ((localAppState != null) && (localAppState.installerData != null) && (localAppState.installerData.installerState >= 50))
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.packageName;
      arrayOfObject[1] = this.nodeId;
      FinskyLog.d("Cannot cancel installing %s (%s) - too late", arrayOfObject);
      return;
    }
    cancelCleanupState(localAppState);
  }
  
  final void cancelCleanupState(AppStates.AppState paramAppState)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.packageName;
    arrayOfObject[1] = this.nodeId;
    FinskyLog.d("Cancel running installation of %s (%s)", arrayOfObject);
    Download localDownload = this.mDownloadQueue.getByPackageName(this.packageName, this.nodeId);
    if (localDownload != null) {
      this.mDownloadQueue.cancel(localDownload);
    }
    deleteStream();
    this.mInstaller.clearInstallerState(paramAppState);
    this.mInstaller.taskFinished(this);
  }
  
  final void logAndNotifyDownloadError(String paramString, int paramInt, Exception paramException)
  {
    FinskyApp.get().getEventLogger().logBackgroundEvent(104, paramString, null, paramInt, null, this.mLogAppData);
  }
  
  final void processDeliveryData(InstallerDataStore.InstallerData paramInstallerData, boolean paramBoolean)
  {
    boolean bool1 = true;
    this.mApkSize = paramInstallerData.deliveryData.downloadSize;
    this.mTotalSize = this.mApkSize;
    this.mApkCompleted = 0L;
    int i = paramInstallerData.flags;
    if ((i & 0x800) != 0) {
      this.mMobileDataAllowed = false;
    }
    boolean bool2;
    label57:
    do
    {
      return;
      if ((i & 0x2) == 0) {
        break;
      }
      bool2 = bool1;
      this.mMobileDataAllowed = bool2;
    } while ((!paramBoolean) || (this.mMobileDataAllowed));
    if (this.mTotalSize < this.mInstallPolicies.mMaxBytesOverMobileRecommended) {}
    for (;;)
    {
      this.mMobileDataAllowed = bool1;
      if (!this.mMobileDataAllowed) {
        break;
      }
      WearInstaller localWearInstaller = this.mInstaller;
      String str1 = this.nodeId;
      String str2 = this.packageName;
      WriteThroughInstallerDataStore localWriteThroughInstallerDataStore = localWearInstaller.mWearAppStatesFactory.createAppStates(str1).mStateStore;
      InstallerDataStore.InstallerData localInstallerData = localWriteThroughInstallerDataStore.get(str2);
      int j = 0;
      if (localInstallerData != null) {
        j = localInstallerData.flags;
      }
      int k = j | 0x2;
      if (k == j) {
        break;
      }
      localWriteThroughInstallerDataStore.setFlags(str2, k);
      return;
      bool2 = false;
      break label57;
      bool1 = false;
    }
  }
  
  final void setInstallerState(int paramInt, Uri paramUri)
  {
    if (paramUri != null) {}
    for (String str = paramUri.toString();; str = null)
    {
      setInstallerState(paramInt, str);
      return;
    }
  }
  
  final void setInstallerState(int paramInt, String paramString)
  {
    this.mInstallerDataStore.setInstallerState(this.packageName, paramInt, paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearInstallerTask
 * JD-Core Version:    0.7.0.1
 */