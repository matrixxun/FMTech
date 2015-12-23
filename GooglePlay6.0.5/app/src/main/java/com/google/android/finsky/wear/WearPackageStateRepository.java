package com.google.android.finsky.wear;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.finsky.utils.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public final class WearPackageStateRepository
  implements PackageStateRepository
{
  private static final PackageStateRepository.PackageState NOT_INSTALLED_MARKER = new PackageStateRepository.PackageState(null, null, false, false, false, false, -1, false);
  private static final Map<String, WearPackageStateRepository> sRepos = new HashMap();
  private final Context mContext;
  private final GoogleApiClient mGoogleApiClient;
  private boolean mIsLoaded;
  private long mLastUpdatedUptimeMs = -1L;
  private List<Runnable> mLoadedCallbacks;
  private final String mNodeId;
  private Handler mNotificationHandler;
  private Map<String, PackageStateRepository.PackageState> mPackageStates = null;
  
  private WearPackageStateRepository(Context paramContext, String paramString, GoogleApiClient paramGoogleApiClient)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mNodeId = paramString;
    this.mGoogleApiClient = paramGoogleApiClient;
    this.mNotificationHandler = new Handler(Looper.getMainLooper());
  }
  
  public static WearPackageStateRepository get(Context paramContext, String paramString, GoogleApiClient paramGoogleApiClient)
  {
    try
    {
      if (!sRepos.containsKey(paramString)) {
        sRepos.put(paramString, new WearPackageStateRepository(paramContext, paramString, paramGoogleApiClient));
      }
      WearPackageStateRepository localWearPackageStateRepository = (WearPackageStateRepository)sRepos.get(paramString);
      return localWearPackageStateRepository;
    }
    finally {}
  }
  
  private void handleDataItemsResult(DataItemBuffer paramDataItemBuffer)
  {
    for (;;)
    {
      try
      {
        Status localStatus = paramDataItemBuffer.zzUc;
        if (!localStatus.isSuccess())
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(localStatus.zzakr);
          arrayOfObject[1] = localStatus.zzanv;
          FinskyLog.w("Error %d getting data items. (%s)", arrayOfObject);
          return;
        }
        this.mPackageStates = new HashMap();
        String str = this.mNodeId + "/package_info";
        int i = 0;
        int j = paramDataItemBuffer.getCount();
        if (i < j)
        {
          DataItem localDataItem = (DataItem)paramDataItemBuffer.get(i);
          if (localDataItem.getUri().toString().contains(str))
          {
            PackageStateRepository.PackageState localPackageState = stateFromDataItem(localDataItem);
            this.mPackageStates.put(localPackageState.packageName, localPackageState);
          }
        }
        else
        {
          this.mLastUpdatedUptimeMs = SystemClock.uptimeMillis();
          continue;
        }
        i++;
      }
      finally {}
    }
  }
  
  @TargetApi(18)
  private static PackageStateRepository.PackageState stateFromDataItem(DataItem paramDataItem)
  {
    String str = WearskyUris.getPackageNameFromUri(paramDataItem.getUri());
    DataMap localDataMap = DataMap.fromByteArray(paramDataItem.getData());
    ArrayList localArrayList = localDataMap.getDataMapArrayList("signatures");
    if (localArrayList != null)
    {
      arrayOfString = new String[localArrayList.size()];
      int k = 0;
      int m = arrayOfString.length;
      while (k < m)
      {
        arrayOfString[k] = Sha1Util.secureHash(((DataMap)localArrayList.get(k)).getByteArray("signature"));
        k++;
      }
    }
    String[] arrayOfString = null;
    int i = localDataMap.getInt$505cff29("applicationFlags");
    boolean bool1;
    boolean bool2;
    label116:
    int j;
    boolean bool3;
    if ((i & 0x1) != 0)
    {
      bool1 = true;
      if ((i & 0x80) == 0) {
        break label167;
      }
      bool2 = true;
      j = localDataMap.getInt$505cff29("applicationEnabledSetting");
      if (j != 0) {
        break label173;
      }
      bool3 = false;
    }
    label167:
    label173:
    label209:
    for (;;)
    {
      boolean bool4 = false;
      return new PackageStateRepository.PackageState(str, arrayOfString, bool1, bool2, bool4, bool3, localDataMap.getInt$505cff29("versionCode"), false);
      bool1 = false;
      break;
      bool2 = false;
      break label116;
      if ((j == 3) || (j == 4)) {}
      for (bool3 = true;; bool3 = false)
      {
        if ((!bool3) && (j != 2)) {
          break label209;
        }
        bool4 = true;
        break;
      }
    }
  }
  
  public final boolean canLaunch(String paramString)
  {
    return false;
  }
  
  public final PackageStateRepository.PackageState get(String paramString)
  {
    try
    {
      PackageStateRepository.PackageState localPackageState1 = (PackageStateRepository.PackageState)this.mPackageStates.get(paramString);
      PackageStateRepository.PackageState localPackageState2 = NOT_INSTALLED_MARKER;
      if (localPackageState1 == localPackageState2) {
        localPackageState1 = null;
      }
      return localPackageState1;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final Collection<PackageStateRepository.PackageState> getAllBlocking()
  {
    Utils.ensureNotOnMainThread();
    final Semaphore localSemaphore = new Semaphore(0);
    load(new Runnable()
    {
      public final void run()
      {
        localSemaphore.release();
      }
    });
    try
    {
      localSemaphore.acquire();
      return this.mPackageStates.values();
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        FinskyLog.wtf(localInterruptedException, "Unexpected interruption", new Object[0]);
      }
    }
  }
  
  public final int getVersionCode(String paramString)
  {
    PackageStateRepository.PackageState localPackageState = get(paramString);
    if (localPackageState == null) {
      return -1;
    }
    return localPackageState.installedVersion;
  }
  
  public final String getVersionName(String paramString)
  {
    FinskyLog.wtf("Calling getVersionName on wearable package", new Object[0]);
    return "";
  }
  
  public final void invalidate(final String paramString)
  {
    try
    {
      String str = "wear://" + this.mNodeId + "/package_info/" + paramString;
      Wearable.DataApi.getDataItem(this.mGoogleApiClient, Uri.parse(str)).setResultCallback(new ResultCallback() {});
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final void load(Runnable paramRunnable)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 94	com/google/android/finsky/wear/WearPackageStateRepository:mIsLoaded	Z
    //   6: ifeq +15 -> 21
    //   9: aload_0
    //   10: getfield 77	com/google/android/finsky/wear/WearPackageStateRepository:mNotificationHandler	Landroid/os/Handler;
    //   13: aload_1
    //   14: invokevirtual 345	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   17: pop
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: aload_0
    //   22: getfield 87	com/google/android/finsky/wear/WearPackageStateRepository:mLoadedCallbacks	Ljava/util/List;
    //   25: ifnonnull +14 -> 39
    //   28: aload_0
    //   29: new 228	java/util/ArrayList
    //   32: dup
    //   33: invokespecial 346	java/util/ArrayList:<init>	()V
    //   36: putfield 87	com/google/android/finsky/wear/WearPackageStateRepository:mLoadedCallbacks	Ljava/util/List;
    //   39: aload_0
    //   40: getfield 87	com/google/android/finsky/wear/WearPackageStateRepository:mLoadedCallbacks	Ljava/util/List;
    //   43: aload_1
    //   44: invokeinterface 351 2 0
    //   49: pop
    //   50: aload_0
    //   51: getfield 87	com/google/android/finsky/wear/WearPackageStateRepository:mLoadedCallbacks	Ljava/util/List;
    //   54: invokeinterface 352 1 0
    //   59: iconst_1
    //   60: if_icmpne -42 -> 18
    //   63: getstatic 320	com/google/android/gms/wearable/Wearable:DataApi	Lcom/google/android/gms/wearable/DataApi;
    //   66: aload_0
    //   67: getfield 64	com/google/android/finsky/wear/WearPackageStateRepository:mGoogleApiClient	Lcom/google/android/gms/common/api/GoogleApiClient;
    //   70: invokeinterface 356 2 0
    //   75: new 358	com/google/android/finsky/wear/WearPackageStateRepository$1
    //   78: dup
    //   79: aload_0
    //   80: invokespecial 361	com/google/android/finsky/wear/WearPackageStateRepository$1:<init>	(Lcom/google/android/finsky/wear/WearPackageStateRepository;)V
    //   83: invokevirtual 341	com/google/android/gms/common/api/PendingResult:setResultCallback	(Lcom/google/android/gms/common/api/ResultCallback;)V
    //   86: goto -68 -> 18
    //   89: astore_2
    //   90: aload_0
    //   91: monitorexit
    //   92: aload_2
    //   93: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	this	WearPackageStateRepository
    //   0	94	1	paramRunnable	Runnable
    //   89	4	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	18	89	finally
    //   21	39	89	finally
    //   39	86	89	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearPackageStateRepository
 * JD-Core Version:    0.7.0.1
 */