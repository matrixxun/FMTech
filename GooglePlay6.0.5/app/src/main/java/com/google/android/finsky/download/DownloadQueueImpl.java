package com.google.android.finsky.download;

import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageManagerHelper.2;
import com.google.android.finsky.utils.PackageManagerHelper.FreeSpaceListener;
import com.google.android.finsky.utils.ParameterizedRunnable;
import com.google.android.finsky.utils.Utils;
import com.google.android.vending.remoting.api.VendingApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class DownloadQueueImpl
  implements DownloadQueue, DownloadQueueListener
{
  Context mContext;
  final DownloadManagerFacade mDownloadManager;
  DownloadProgressManager mDownloadProgressManager;
  LinkedList<DownloadQueueListener> mListeners;
  final int mMaxConcurrent = 1;
  LinkedHashMap<String, Download> mPendingQueue = new LinkedHashMap();
  private Uri mPreviousContentUri = null;
  private int mPreviousProgressStatus = -1;
  HashMap<String, Download> mRunningMap = new HashMap();
  
  private DownloadQueueImpl(Context paramContext, DownloadManagerFacade paramDownloadManagerFacade)
  {
    this.mDownloadManager = paramDownloadManagerFacade;
    this.mContext = paramContext;
    this.mListeners = new LinkedList();
    this.mListeners.add(this);
  }
  
  public DownloadQueueImpl(Context paramContext, DownloadManagerFacade paramDownloadManagerFacade, byte paramByte)
  {
    this(paramContext, paramDownloadManagerFacade);
  }
  
  private void notifyListeners(int paramInt, final Download paramDownload)
  {
    final DownloadProgress localDownloadProgress;
    if (paramDownload == null)
    {
      localDownloadProgress = null;
      label6:
      if (paramDownload != null) {
        break label72;
      }
    }
    label72:
    for (final int i = -1;; i = paramDownload.getHttpStatus()) {
      switch (paramInt)
      {
      default: 
        throw new IllegalStateException("Bad listener type.");
        localDownloadProgress = paramDownload.getProgress();
        break label6;
      }
    }
    Object localObject = new ListenerNotifier(paramInt, paramDownload)
    {
      public final void updateListener(DownloadQueueListener paramAnonymousDownloadQueueListener)
      {
        paramAnonymousDownloadQueueListener.onNotificationClicked(paramDownload);
      }
    };
    for (;;)
    {
      new Handler(Looper.getMainLooper()).post((Runnable)localObject);
      return;
      localObject = new ListenerNotifier(paramInt, paramDownload)
      {
        public final void updateListener(DownloadQueueListener paramAnonymousDownloadQueueListener)
        {
          paramAnonymousDownloadQueueListener.onComplete(paramDownload);
        }
      };
      continue;
      localObject = new ListenerNotifier(paramInt, paramDownload)
      {
        public final void updateListener(DownloadQueueListener paramAnonymousDownloadQueueListener)
        {
          paramAnonymousDownloadQueueListener.onProgress(paramDownload, localDownloadProgress);
        }
      };
      continue;
      localObject = new ListenerNotifier(paramInt, paramDownload)
      {
        public final void updateListener(DownloadQueueListener paramAnonymousDownloadQueueListener)
        {
          paramAnonymousDownloadQueueListener.onCancel(paramDownload);
        }
      };
      continue;
      localObject = new ListenerNotifier(paramInt, paramDownload)
      {
        public final void updateListener(DownloadQueueListener paramAnonymousDownloadQueueListener)
        {
          paramAnonymousDownloadQueueListener.onError(paramDownload, i);
        }
      };
      continue;
      localObject = new ListenerNotifier(paramInt, paramDownload)
      {
        public final void updateListener(DownloadQueueListener paramAnonymousDownloadQueueListener)
        {
          paramAnonymousDownloadQueueListener.onStart(paramDownload);
        }
      };
    }
  }
  
  private void remove(Download paramDownload)
  {
    FinskyLog.d("Download %s removed from DownloadQueue", new Object[] { paramDownload });
    String str = paramDownload.getUrl();
    if (this.mPendingQueue.containsKey(str))
    {
      this.mPendingQueue.remove(str);
      return;
    }
    this.mRunningMap.remove(paramDownload.getUrl());
    startNextDownload();
  }
  
  private void removeFromDownloadManager(Download paramDownload)
  {
    Uri localUri = paramDownload.getContentUri();
    if (localUri != null) {
      this.mDownloadManager.remove(localUri);
    }
  }
  
  private void startNextDownload()
  {
    if (this.mRunningMap.size() >= this.mMaxConcurrent) {
      return;
    }
    long l = 0L;
    Iterator localIterator = this.mPendingQueue.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      l = Math.max(((Download)this.mPendingQueue.get(str)).getMaximumSize(), l);
    }
    Context localContext = this.mContext;
    PackageManagerHelper.2 local2 = new PackageManagerHelper.2(new PurgeCacheCallback((byte)0));
    try
    {
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Long.TYPE;
      arrayOfClass[1] = IPackageDataObserver.class;
      Method localMethod = PackageManager.class.getMethod("freeStorageAndNotify", arrayOfClass);
      PackageManager localPackageManager = localContext.getPackageManager();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Long.valueOf(l);
      arrayOfObject[1] = local2;
      localMethod.invoke(localPackageManager, arrayOfObject);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      FinskyLog.d("Cannot freeStorageAndNotify on this platform", new Object[0]);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      FinskyLog.wtf(localSecurityException, "Cannot freeStorageAndNotify due to security exception", new Object[0]);
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      FinskyLog.wtf(localIllegalAccessException, "Cannot freeStorageAndNotify due to reflection access exception", new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      FinskyLog.wtf(localInvocationTargetException, "Cannot freeStorageAndNotify due to reflection invocation exception", new Object[0]);
    }
  }
  
  public final void add(Download paramDownload)
  {
    
    if (paramDownload.getState() != 0)
    {
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = paramDownload;
      arrayOfObject2[1] = paramDownload.getUrl();
      arrayOfObject2[2] = Integer.valueOf(paramDownload.getState());
      FinskyLog.wtf("Added download %s (url=%s) while in state %d", arrayOfObject2);
    }
    String str = paramDownload.getUrl();
    Download localDownload;
    if (this.mRunningMap.containsKey(str)) {
      localDownload = (Download)this.mRunningMap.get(str);
    }
    for (;;)
    {
      if (localDownload != null)
      {
        Object[] arrayOfObject1 = new Object[4];
        arrayOfObject1[0] = paramDownload;
        arrayOfObject1[1] = paramDownload.getUrl();
        arrayOfObject1[2] = localDownload;
        arrayOfObject1[3] = localDownload.getUrl();
        FinskyLog.wtf("Added download %s (url=%s) while existing found %s (url=%s)", arrayOfObject1);
      }
      FinskyLog.d("Download %s added to DownloadQueue", new Object[] { paramDownload });
      this.mPendingQueue.put(paramDownload.getUrl(), paramDownload);
      if (this.mDownloadProgressManager == null) {
        this.mDownloadProgressManager = new DownloadProgressManager(this);
      }
      setDownloadState(paramDownload, 1);
      startNextDownload();
      return;
      if (this.mPendingQueue.containsKey(str)) {
        localDownload = (Download)this.mPendingQueue.get(str);
      } else {
        localDownload = null;
      }
    }
  }
  
  public final void addListener(DownloadQueueListener paramDownloadQueueListener)
  {
    Utils.ensureOnMainThread();
    this.mListeners.add(paramDownloadQueueListener);
  }
  
  public final void addRecoveredDownload(Download paramDownload)
  {
    Utils.ensureOnMainThread();
    String str = paramDownload.getUrl();
    FinskyLog.d("Download queue recovering download %s.", new Object[] { paramDownload });
    setDownloadState(paramDownload, 2);
    this.mRunningMap.put(str, paramDownload);
    if (this.mDownloadProgressManager == null) {
      this.mDownloadProgressManager = new DownloadProgressManager(this);
    }
  }
  
  public final void cancel(Download paramDownload)
  {
    
    if ((paramDownload == null) || (paramDownload.isCompleted())) {
      return;
    }
    if (paramDownload.getState() == 2) {
      this.mDownloadManager.remove(paramDownload.getContentUri());
    }
    setDownloadState(paramDownload, 4);
  }
  
  public final Download getByPackageName(String paramString1, String paramString2)
  {
    
    if (TextUtils.isEmpty(paramString1)) {
      throw new IllegalArgumentException("empty packageName");
    }
    Iterator localIterator1 = this.mPendingQueue.values().iterator();
    while (localIterator1.hasNext())
    {
      Download localDownload2 = (Download)localIterator1.next();
      if (paramString1.equals(localDownload2.getPackageName()))
      {
        String str2 = localDownload2.getNodeId();
        if (((paramString2 == null) && (str2 == null)) || ((paramString2 != null) && (paramString2.equals(str2)))) {
          return localDownload2;
        }
      }
    }
    Iterator localIterator2 = this.mRunningMap.values().iterator();
    while (localIterator2.hasNext())
    {
      Download localDownload1 = (Download)localIterator2.next();
      if (paramString1.equals(localDownload1.getPackageName()))
      {
        String str1 = localDownload1.getNodeId();
        if (((paramString2 == null) && (str1 == null)) || ((paramString2 != null) && (paramString2.equals(str1)))) {
          return localDownload1;
        }
      }
    }
    return null;
  }
  
  public final Download getDownloadByContentUri(Uri paramUri)
  {
    
    String str;
    if (paramUri != null)
    {
      str = paramUri.toString();
      if (!TextUtils.isEmpty(str)) {
        break label26;
      }
    }
    label26:
    Download localDownload;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        return null;
        str = null;
        break;
        localIterator = this.mRunningMap.values().iterator();
      }
      localDownload = (Download)localIterator.next();
    } while (!paramUri.equals(localDownload.getContentUri()));
    return localDownload;
  }
  
  public final DownloadManagerFacade getDownloadManager()
  {
    return this.mDownloadManager;
  }
  
  public final List<DownloadProgress> getRunningDownloads()
  {
    return this.mDownloadManager.query(null, null);
  }
  
  public final void notifyClicked(Download paramDownload)
  {
    FinskyLog.d("%s: onNotificationClicked", new Object[] { paramDownload });
    notifyListeners(0, paramDownload);
  }
  
  public final void notifyProgress(Download paramDownload, DownloadProgress paramDownloadProgress)
  {
    if (paramDownloadProgress.equals(paramDownload.getProgress())) {
      return;
    }
    paramDownload.setProgress(paramDownloadProgress);
    int i;
    if ((paramDownloadProgress.statusCode == this.mPreviousProgressStatus) && (this.mPreviousContentUri != null))
    {
      boolean bool = this.mPreviousContentUri.equals(paramDownload.getContentUri());
      i = 0;
      if (bool) {}
    }
    else
    {
      i = 1;
    }
    if (i != 0)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramDownload;
      arrayOfObject[1] = paramDownloadProgress.toString();
      FinskyLog.d("%s: onProgress %s.", arrayOfObject);
      this.mPreviousContentUri = paramDownload.getContentUri();
      this.mPreviousProgressStatus = paramDownloadProgress.statusCode;
    }
    notifyListeners(2, paramDownload);
  }
  
  public final void onCancel(Download paramDownload)
  {
    FinskyLog.d("%s: onCancel", new Object[] { paramDownload });
    remove(paramDownload);
    removeFromDownloadManager(paramDownload);
  }
  
  public final void onComplete(Download paramDownload)
  {
    FinskyLog.d("%s: onComplete", new Object[] { paramDownload });
    remove(paramDownload);
  }
  
  public final void onError(Download paramDownload, int paramInt)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramDownload;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    FinskyLog.d("%s: onError %d.", arrayOfObject);
    if ((paramInt == 403) || (paramInt == 401)) {
      FinskyApp.get().getVendingApi().mApiContext.mReauthenticate = true;
    }
    remove(paramDownload);
    removeFromDownloadManager(paramDownload);
  }
  
  public final void onNotificationClicked(Download paramDownload) {}
  
  public final void onProgress(Download paramDownload, DownloadProgress paramDownloadProgress) {}
  
  public final void onStart(Download paramDownload)
  {
    FinskyLog.d("%s: onStart", new Object[] { paramDownload });
  }
  
  public final void release(Uri paramUri)
  {
    this.mDownloadManager.remove(paramUri);
  }
  
  public final void removeListener(DownloadQueueListener paramDownloadQueueListener)
  {
    Utils.ensureOnMainThread();
    this.mListeners.remove(paramDownloadQueueListener);
  }
  
  public final void setDownloadState(Download paramDownload, int paramInt)
  {
    paramDownload.setState(paramInt);
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("enum %d", arrayOfObject);
    case 0: 
    case 1: 
      return;
    case 2: 
      notifyListeners(4, paramDownload);
      return;
    case 4: 
      notifyListeners(3, paramDownload);
      return;
    case 5: 
      notifyListeners(5, paramDownload);
      return;
    }
    notifyListeners(1, paramDownload);
  }
  
  private abstract class ListenerNotifier
    implements Runnable
  {
    int mType;
    
    public ListenerNotifier(int paramInt)
    {
      this.mType = paramInt;
    }
    
    public void run()
    {
      Iterator localIterator = DownloadQueueImpl.this.mListeners.iterator();
      while (localIterator.hasNext())
      {
        DownloadQueueListener localDownloadQueueListener = (DownloadQueueListener)localIterator.next();
        try
        {
          updateListener(localDownloadQueueListener);
        }
        catch (Exception localException)
        {
          FinskyLog.wtf(localException, "Download listener threw an exception during " + this.mType, new Object[0]);
        }
      }
    }
    
    abstract void updateListener(DownloadQueueListener paramDownloadQueueListener);
  }
  
  private final class PurgeCacheCallback
    implements PackageManagerHelper.FreeSpaceListener
  {
    private PurgeCacheCallback() {}
    
    public final void onComplete(boolean paramBoolean)
    {
      if (!paramBoolean) {
        FinskyLog.w("Could not free required amount of space for download", new Object[0]);
      }
      new Handler(DownloadQueueImpl.this.mContext.getMainLooper()).post(new DownloadQueueImpl.StartNextDownloadRunnable(DownloadQueueImpl.this, (byte)0));
    }
  }
  
  private final class StartNextDownloadRunnable
    implements Runnable
  {
    private StartNextDownloadRunnable() {}
    
    public final void run()
    {
      
      if (DownloadQueueImpl.this.mRunningMap.size() >= DownloadQueueImpl.this.mMaxConcurrent) {}
      label203:
      do
      {
        return;
        LinkedList localLinkedList = new LinkedList();
        Iterator localIterator1 = DownloadQueueImpl.this.mPendingQueue.keySet().iterator();
        Download localDownload;
        for (;;)
        {
          boolean bool = localIterator1.hasNext();
          localObject = null;
          if (!bool) {
            break label203;
          }
          String str2 = (String)localIterator1.next();
          localDownload = (Download)DownloadQueueImpl.this.mPendingQueue.get(str2);
          localLinkedList.add(str2);
          if (localDownload.getState() == 1)
          {
            long l1 = localDownload.getMaximumSize();
            long l2 = Storage.dataPartitionAvailableSpace();
            long l3 = Storage.externalStorageAvailableSpace();
            if (localDownload.getRequestedDestination() != null)
            {
              if (l3 >= l1) {
                break;
              }
              localDownload.setHttpStatus(198);
              DownloadQueueImpl.this.setDownloadState(localDownload, 5);
            }
            else
            {
              if (l2 >= l1) {
                break;
              }
              localDownload.setHttpStatus(198);
              DownloadQueueImpl.this.setDownloadState(localDownload, 5);
            }
          }
        }
        Object localObject = localDownload;
        Iterator localIterator2 = localLinkedList.iterator();
        while (localIterator2.hasNext())
        {
          String str1 = (String)localIterator2.next();
          DownloadQueueImpl.this.mPendingQueue.remove(str1);
        }
        DownloadQueueImpl localDownloadQueueImpl = DownloadQueueImpl.this;
        if (localObject != null)
        {
          FinskyLog.d("Download %s starting", new Object[] { localObject });
          localDownloadQueueImpl.mRunningMap.put(localObject.getUrl(), localObject);
          DownloadManagerFacade localDownloadManagerFacade = localDownloadQueueImpl.mDownloadManager;
          DownloadQueueImpl.7 local7 = new DownloadQueueImpl.7(localDownloadQueueImpl, localObject);
          localDownloadManagerFacade.enqueue(localObject, local7);
        }
      } while ((DownloadQueueImpl.this.mRunningMap.size() != 0) || (DownloadQueueImpl.this.mDownloadProgressManager == null));
      DownloadProgressManager localDownloadProgressManager = DownloadQueueImpl.this.mDownloadProgressManager;
      Handler localHandler = localDownloadProgressManager.mHandler;
      DownloadProgressManager.2 local2 = new DownloadProgressManager.2(localDownloadProgressManager);
      localHandler.post(local2);
      DownloadQueueImpl.this.mDownloadProgressManager = null;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadQueueImpl
 * JD-Core Version:    0.7.0.1
 */