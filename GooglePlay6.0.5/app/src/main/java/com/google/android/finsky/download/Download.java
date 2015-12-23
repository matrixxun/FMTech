package com.google.android.finsky.download;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;

public abstract interface Download
{
  public abstract Uri getContentUri();
  
  public abstract String getCookieName();
  
  public abstract String getCookieValue();
  
  public abstract String getDocIdForLog();
  
  public abstract int getHttpStatus();
  
  public abstract boolean getInvisible();
  
  public abstract long getMaximumSize();
  
  public abstract String getNodeId();
  
  public abstract Obb getObb();
  
  public abstract String getPackageName();
  
  public abstract DownloadProgress getProgress();
  
  public abstract Uri getRequestedDestination();
  
  public abstract int getState();
  
  public abstract String getTitle();
  
  public abstract String getUrl();
  
  public abstract boolean getWifiOnly();
  
  public abstract boolean isCompleted();
  
  public abstract boolean isObb();
  
  public abstract void setContentUri(Uri paramUri);
  
  public abstract void setHttpStatus(int paramInt);
  
  public abstract void setProgress(DownloadProgress paramDownloadProgress);
  
  public abstract void setState(int paramInt);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.Download
 * JD-Core Version:    0.7.0.1
 */