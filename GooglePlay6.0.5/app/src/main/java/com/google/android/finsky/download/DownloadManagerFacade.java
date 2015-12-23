package com.google.android.finsky.download;

import android.content.Intent;
import android.net.Uri;
import com.google.android.finsky.utils.ParameterizedRunnable;
import java.util.List;

public abstract interface DownloadManagerFacade
{
  public abstract void enqueue(Download paramDownload, ParameterizedRunnable<Uri> paramParameterizedRunnable);
  
  public abstract Uri getFileUriForContentUri(Uri paramUri);
  
  public abstract Uri getUriFromBroadcast(Intent paramIntent);
  
  public abstract List<DownloadProgress> query(Uri paramUri, Listener paramListener);
  
  public abstract void remove(Uri paramUri);
  
  public abstract void unregisterListener$669df7dd();
  
  public static abstract interface Listener
  {
    public abstract void onChange();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadManagerFacade
 * JD-Core Version:    0.7.0.1
 */