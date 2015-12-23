package com.google.android.finsky.download;

import android.content.Context;

public final class DownloadManagerFactory
{
  public static DownloadManagerFacade getDownloadManager(Context paramContext)
  {
    return new DownloadManagerLegacyImpl(paramContext);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadManagerFactory
 * JD-Core Version:    0.7.0.1
 */