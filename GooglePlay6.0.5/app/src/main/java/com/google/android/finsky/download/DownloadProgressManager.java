package com.google.android.finsky.download;

import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class DownloadProgressManager
  implements DownloadManagerFacade.Listener
{
  final DownloadManagerFacade mDownloadManager;
  private Map<Uri, DownloadProgress> mDownloadProgressMap = null;
  private final DownloadQueue mDownloadQueue;
  final Handler mHandler;
  final HandlerThread mHandlerThread;
  
  public DownloadProgressManager(DownloadQueueImpl paramDownloadQueueImpl)
  {
    this.mDownloadQueue = paramDownloadQueueImpl;
    this.mDownloadManager = paramDownloadQueueImpl.mDownloadManager;
    this.mHandlerThread = BackgroundThreadFactory.createHandlerThread("Download progress manager runner");
    this.mHandlerThread.start();
    this.mHandler = new Handler(this.mHandlerThread.getLooper());
    this.mHandler.post(new Runnable()
    {
      public final void run()
      {
        DownloadProgressManager.this.onDownloadProgress();
      }
    });
  }
  
  public final void onChange()
  {
    onDownloadProgress();
  }
  
  final void onDownloadProgress()
  {
    Utils.ensureNotOnMainThread();
    HashSet localHashSet1 = new HashSet();
    if (this.mDownloadProgressMap != null) {
      localHashSet1.addAll(this.mDownloadProgressMap.keySet());
    }
    List localList = this.mDownloadManager.query(null, this);
    HashMap localHashMap = new HashMap();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      DownloadProgress localDownloadProgress = (DownloadProgress)localIterator.next();
      localHashMap.put(localDownloadProgress.contentUri, localDownloadProgress);
    }
    Map localMap = Collections.unmodifiableMap(localHashMap);
    if ((this.mDownloadProgressMap == null) || (!this.mDownloadProgressMap.equals(localMap)))
    {
      this.mDownloadProgressMap = localMap;
      HashSet localHashSet2 = new HashSet();
      if (this.mDownloadProgressMap != null) {
        localHashSet2.addAll(this.mDownloadProgressMap.keySet());
      }
      localHashSet1.removeAll(localHashSet2);
      ProgressRunnable localProgressRunnable = new ProgressRunnable(this.mDownloadQueue, localHashSet1, localHashSet2, localMap);
      new Handler(Looper.getMainLooper()).post(localProgressRunnable);
    }
  }
  
  private static final class ProgressRunnable
    implements Runnable
  {
    private final DownloadQueue downloadQueue;
    private Map<Uri, DownloadProgress> mProgressMap;
    private final Set<Uri> newUris;
    private final Set<Uri> oldUris;
    
    public ProgressRunnable(DownloadQueue paramDownloadQueue, Set<Uri> paramSet1, Set<Uri> paramSet2, Map<Uri, DownloadProgress> paramMap)
    {
      this.downloadQueue = paramDownloadQueue;
      this.oldUris = paramSet1;
      this.newUris = paramSet2;
      this.mProgressMap = paramMap;
    }
    
    public final void run()
    {
      Iterator localIterator1 = this.oldUris.iterator();
      while (localIterator1.hasNext())
      {
        Uri localUri2 = (Uri)localIterator1.next();
        Download localDownload2 = this.downloadQueue.getDownloadByContentUri(localUri2);
        if ((localDownload2 != null) && (localDownload2.getState() == 2)) {
          this.downloadQueue.cancel(localDownload2);
        }
      }
      Iterator localIterator2 = this.newUris.iterator();
      while (localIterator2.hasNext())
      {
        Uri localUri1 = (Uri)localIterator2.next();
        Download localDownload1 = this.downloadQueue.getDownloadByContentUri(localUri1);
        if (localDownload1 != null)
        {
          DownloadProgress localDownloadProgress1 = (DownloadProgress)this.mProgressMap.get(localUri1);
          switch (localDownloadProgress1.statusCode)
          {
          }
          label168:
          for (DownloadProgress localDownloadProgress2 = localDownloadProgress1;; localDownloadProgress2 = new DownloadProgress(localDownloadProgress1.contentUri, localDownloadProgress1.bytesCompleted, localDownloadProgress1.bytesTotal, 196))
          {
            this.downloadQueue.notifyProgress(localDownload1, localDownloadProgress2);
            break;
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = localDownload1;
            arrayOfObject[1] = Integer.valueOf(localDownloadProgress1.statusCode);
            arrayOfObject[2] = Integer.valueOf(localDownload1.getState());
            FinskyLog.d("%s: Caught error %d while state=%d", arrayOfObject);
            if (localDownload1.getState() != 2) {
              break;
            }
            localDownload1.setHttpStatus(localDownloadProgress1.statusCode);
            this.downloadQueue.setDownloadState(localDownload1, 5);
            break;
            if (!localDownload1.getWifiOnly()) {
              break label168;
            }
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadProgressManager
 * JD-Core Version:    0.7.0.1
 */