package com.google.android.finsky.download;

import android.net.Uri;
import com.google.android.finsky.utils.Objects;

public final class DownloadProgress
{
  public final long bytesCompleted;
  public final long bytesTotal;
  public final Uri contentUri;
  public final int statusCode;
  
  public DownloadProgress(Uri paramUri, long paramLong1, long paramLong2, int paramInt)
  {
    this.contentUri = paramUri;
    this.bytesCompleted = paramLong1;
    this.bytesTotal = paramLong2;
    this.statusCode = paramInt;
  }
  
  public final boolean equals(Object paramObject)
  {
    boolean bool1 = false;
    if (paramObject != null)
    {
      boolean bool2 = paramObject instanceof DownloadProgress;
      bool1 = false;
      if (bool2)
      {
        DownloadProgress localDownloadProgress = (DownloadProgress)paramObject;
        boolean bool3 = this.bytesCompleted < localDownloadProgress.bytesCompleted;
        bool1 = false;
        if (!bool3)
        {
          boolean bool4 = this.bytesTotal < localDownloadProgress.bytesTotal;
          bool1 = false;
          if (!bool4)
          {
            int i = this.statusCode;
            int j = localDownloadProgress.statusCode;
            bool1 = false;
            if (i == j)
            {
              boolean bool5 = Objects.equal(this.contentUri, localDownloadProgress.contentUri);
              bool1 = false;
              if (bool5) {
                bool1 = true;
              }
            }
          }
        }
      }
    }
    return bool1;
  }
  
  public final int hashCode()
  {
    return this.contentUri.hashCode();
  }
  
  public final String toString()
  {
    return this.bytesCompleted + "/" + this.bytesTotal + " Status: " + this.statusCode;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadProgress
 * JD-Core Version:    0.7.0.1
 */