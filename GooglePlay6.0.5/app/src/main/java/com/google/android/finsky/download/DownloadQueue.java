package com.google.android.finsky.download;

import android.net.Uri;
import java.util.List;

public abstract interface DownloadQueue
{
  public abstract void add(Download paramDownload);
  
  public abstract void addListener(DownloadQueueListener paramDownloadQueueListener);
  
  public abstract void addRecoveredDownload(Download paramDownload);
  
  public abstract void cancel(Download paramDownload);
  
  public abstract Download getByPackageName(String paramString1, String paramString2);
  
  public abstract Download getDownloadByContentUri(Uri paramUri);
  
  public abstract DownloadManagerFacade getDownloadManager();
  
  public abstract List<DownloadProgress> getRunningDownloads();
  
  public abstract void notifyClicked(Download paramDownload);
  
  public abstract void notifyProgress(Download paramDownload, DownloadProgress paramDownloadProgress);
  
  public abstract void release(Uri paramUri);
  
  public abstract void removeListener(DownloadQueueListener paramDownloadQueueListener);
  
  public abstract void setDownloadState(Download paramDownload, int paramInt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadQueue
 * JD-Core Version:    0.7.0.1
 */