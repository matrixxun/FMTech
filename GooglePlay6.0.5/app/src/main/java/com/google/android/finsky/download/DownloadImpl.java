package com.google.android.finsky.download;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;

public final class DownloadImpl
  implements Download
{
  private long mActualSize;
  private Uri mContentUri;
  private final String mCookieName;
  private final String mCookieValue;
  private final Uri mFileUri;
  private int mHttpStatus;
  private final boolean mInvisible;
  private DownloadProgress mLastProgress;
  private final long mMaximumSize;
  private final String mNodeId;
  private final Obb mObb;
  private final String mPackageName;
  int mState;
  private final String mTitle;
  private final String mUrl;
  private final boolean mWifiOnly;
  
  public DownloadImpl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Uri paramUri, long paramLong1, long paramLong2, Obb paramObb, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mUrl = paramString1;
    this.mTitle = paramString2;
    this.mPackageName = paramString3;
    this.mNodeId = paramString4;
    this.mCookieName = paramString5;
    this.mCookieValue = paramString6;
    this.mFileUri = paramUri;
    this.mActualSize = paramLong1;
    this.mMaximumSize = paramLong2;
    this.mObb = paramObb;
    this.mWifiOnly = paramBoolean1;
    this.mInvisible = paramBoolean2;
    setState(0);
  }
  
  public final boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof DownloadImpl)) {
      return false;
    }
    DownloadImpl localDownloadImpl = (DownloadImpl)paramObject;
    return this.mUrl.equals(localDownloadImpl.mUrl);
  }
  
  public final Uri getContentUri()
  {
    Utils.ensureOnMainThread();
    return this.mContentUri;
  }
  
  public final String getCookieName()
  {
    return this.mCookieName;
  }
  
  public final String getCookieValue()
  {
    return this.mCookieValue;
  }
  
  public final String getDocIdForLog()
  {
    if (this.mObb != null) {
      return "obb-for-" + this.mObb.getPackage();
    }
    if (this.mPackageName == null) {
      return "com.android.vending";
    }
    return this.mPackageName;
  }
  
  public final int getHttpStatus()
  {
    return this.mHttpStatus;
  }
  
  public final boolean getInvisible()
  {
    return this.mInvisible;
  }
  
  public final long getMaximumSize()
  {
    return this.mMaximumSize;
  }
  
  public final String getNodeId()
  {
    return this.mNodeId;
  }
  
  public final Obb getObb()
  {
    return this.mObb;
  }
  
  public final String getPackageName()
  {
    return this.mPackageName;
  }
  
  public final DownloadProgress getProgress()
  {
    Utils.ensureOnMainThread();
    return this.mLastProgress;
  }
  
  public final Uri getRequestedDestination()
  {
    return this.mFileUri;
  }
  
  public final int getState()
  {
    Utils.ensureOnMainThread();
    return this.mState;
  }
  
  public final String getTitle()
  {
    return this.mTitle;
  }
  
  public final String getUrl()
  {
    return this.mUrl;
  }
  
  public final boolean getWifiOnly()
  {
    return this.mWifiOnly;
  }
  
  public final int hashCode()
  {
    return this.mUrl.hashCode();
  }
  
  public final boolean isCompleted()
  {
    return (this.mState == 4) || (this.mState == 5) || (this.mState == 3);
  }
  
  public final boolean isObb()
  {
    return this.mObb != null;
  }
  
  public final void setContentUri(Uri paramUri)
  {
    this.mContentUri = paramUri;
  }
  
  public final void setHttpStatus(int paramInt)
  {
    this.mHttpStatus = paramInt;
  }
  
  public final void setProgress(DownloadProgress paramDownloadProgress)
  {
    Utils.ensureOnMainThread();
    this.mLastProgress = paramDownloadProgress;
    if ((this.mActualSize == -1L) && (paramDownloadProgress != null) && (paramDownloadProgress.bytesTotal > 0L)) {
      this.mActualSize = paramDownloadProgress.bytesTotal;
    }
  }
  
  public final void setState(int paramInt)
  {
    if (isCompleted()) {
      throw new IllegalStateException("Received state update when already completed.");
    }
    if (this.mState == paramInt)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = this;
      arrayOfObject2[1] = Integer.valueOf(this.mState);
      FinskyLog.d("Duplicate state set for '%s' (%d). Already in that state", arrayOfObject2);
    }
    for (;;)
    {
      this.mState = paramInt;
      return;
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = this;
      arrayOfObject1[1] = Integer.valueOf(this.mState);
      arrayOfObject1[2] = Integer.valueOf(paramInt);
      FinskyLog.d("%s from %d to %d.", arrayOfObject1);
    }
  }
  
  public final String toString()
  {
    String str = getDocIdForLog();
    if (this.mNodeId == null) {
      return str;
    }
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = str;
    arrayOfObject[1] = this.mNodeId;
    return String.format("%s (node %s)", arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadImpl
 * JD-Core Version:    0.7.0.1
 */