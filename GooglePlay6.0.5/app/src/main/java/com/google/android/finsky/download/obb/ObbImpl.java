package com.google.android.finsky.download.obb;

import com.google.android.finsky.download.Storage;
import com.google.android.finsky.utils.FinskyLog;
import java.io.File;

final class ObbImpl
  implements Obb
{
  private final String TEMP_OBB_FILE_PREFIX = "temp.";
  private String mFileName;
  private final boolean mIsPatch;
  private final String mPackageName;
  private long mSize;
  private int mState = -1;
  private String mUrl;
  private int mVersionCode;
  
  ObbImpl(boolean paramBoolean, String paramString1, int paramInt1, String paramString2, long paramLong, int paramInt2)
  {
    this.mIsPatch = paramBoolean;
    this.mVersionCode = paramInt1;
    this.mUrl = paramString2;
    this.mSize = paramLong;
    this.mPackageName = paramString1;
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.mIsPatch) {}
    for (String str = "patch";; str = "main")
    {
      localStringBuilder.append(str).append(".").append(this.mVersionCode).append(".").append(this.mPackageName).append(".obb");
      this.mFileName = localStringBuilder.toString();
      this.mState = 4;
      return;
    }
  }
  
  private void logFinalizeProblem(String paramString)
  {
    File localFile1;
    try
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramString;
      arrayOfObject1[1] = toString();
      FinskyLog.w("Failure %s while finalizing %s", arrayOfObject1);
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = this.mFileName;
      arrayOfObject2[1] = Long.valueOf(this.mSize);
      FinskyLog.w(" file=%s, size=%d", arrayOfObject2);
      localFile1 = ObbFactory.getParentDirectory(this.mPackageName);
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = localFile1.getAbsolutePath();
      FinskyLog.w(" Contents of %s:", arrayOfObject3);
      if (!localFile1.exists())
      {
        FinskyLog.w(" (Does not exist)", new Object[0]);
        return;
      }
      if (!localFile1.isDirectory())
      {
        FinskyLog.w(" (Is not a directory)", new Object[0]);
        return;
      }
    }
    catch (Exception localException)
    {
      FinskyLog.wtf(localException, "Unexpected exception", new Object[0]);
      return;
    }
    File[] arrayOfFile = localFile1.listFiles();
    if (arrayOfFile == null)
    {
      FinskyLog.w(" (listFiles() returned null)", new Object[0]);
      return;
    }
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      File localFile2 = arrayOfFile[j];
      Object[] arrayOfObject4 = new Object[2];
      arrayOfObject4[0] = localFile2.getName();
      arrayOfObject4[1] = Long.valueOf(localFile2.length());
      FinskyLog.w("  name=%s size=%d", arrayOfObject4);
    }
  }
  
  private void setState(int paramInt)
  {
    if (paramInt == 5)
    {
      this.mVersionCode = -1;
      this.mUrl = "";
      this.mSize = -1L;
      this.mFileName = "";
    }
    this.mState = paramInt;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    ObbImpl localObbImpl;
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      localObbImpl = (ObbImpl)paramObject;
      if (this.mFileName != null)
      {
        if (this.mFileName.equals(localObbImpl.mFileName)) {}
      }
      else {
        while (localObbImpl.mFileName != null) {
          return false;
        }
      }
      if (this.mIsPatch != localObbImpl.mIsPatch) {
        return false;
      }
      if (this.mVersionCode != localObbImpl.mVersionCode) {
        return false;
      }
      if (this.mUrl != null)
      {
        if (this.mUrl.equals(localObbImpl.mUrl)) {}
      }
      else {
        while (localObbImpl.mUrl != null) {
          return false;
        }
      }
      if (this.mSize != localObbImpl.mSize) {
        return false;
      }
      if (this.mState >= 0)
      {
        if (this.mState == localObbImpl.mState) {}
      }
      else {
        while (localObbImpl.mState >= 0) {
          return false;
        }
      }
      if (this.mPackageName == null) {
        break;
      }
    } while (this.mPackageName.equals(localObbImpl.mPackageName));
    for (;;)
    {
      return false;
      if (localObbImpl.mPackageName == null) {
        break;
      }
    }
  }
  
  public final boolean finalizeTempFile()
  {
    File localFile1 = getFile();
    if (localFile1 == null)
    {
      logFinalizeProblem("main file null");
      return false;
    }
    File localFile2 = getTempFile();
    if (localFile2 == null)
    {
      logFinalizeProblem("temp file null");
      return false;
    }
    if (localFile2.length() != this.mSize)
    {
      logFinalizeProblem("size mismatch: tempfile size=" + String.valueOf(localFile2.length()));
      return false;
    }
    if (!localFile2.renameTo(localFile1))
    {
      logFinalizeProblem("renameTo() returned false");
      return false;
    }
    return true;
  }
  
  public final File getFile()
  {
    if ((!Storage.externalStorageAvailable()) || (this.mSize <= 0L)) {
      return null;
    }
    File localFile = ObbFactory.getParentDirectory(this.mPackageName);
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return new File(localFile, this.mFileName);
  }
  
  public final String getPackage()
  {
    return this.mPackageName;
  }
  
  public final long getSize()
  {
    return this.mSize;
  }
  
  public final int getState()
  {
    return this.mState;
  }
  
  public final File getTempFile()
  {
    File localFile = getFile();
    if (localFile == null) {
      return null;
    }
    return new File(localFile.getParentFile(), "temp." + localFile.getName());
  }
  
  public final String getUrl()
  {
    return this.mUrl;
  }
  
  public final boolean isFinalized()
  {
    File localFile1 = getFile();
    if (localFile1 == null) {
      logFinalizeProblem("main file null");
    }
    File localFile2;
    do
    {
      return false;
      localFile2 = getTempFile();
      if (localFile2 == null)
      {
        logFinalizeProblem("temp file null");
        return false;
      }
    } while ((localFile2.exists()) || (localFile1.length() != this.mSize));
    return true;
  }
  
  public final boolean isPatch()
  {
    return this.mIsPatch;
  }
  
  public final void syncStateWithStorage()
  {
    if (this.mState == 5) {
      return;
    }
    if (!Storage.externalStorageAvailable())
    {
      setState(4);
      return;
    }
    File localFile = getFile();
    int i = 0;
    if (localFile != null)
    {
      boolean bool1 = localFile.exists();
      i = 0;
      if (bool1)
      {
        boolean bool2 = localFile.length() < this.mSize;
        i = 0;
        if (!bool2) {
          i = 1;
        }
      }
    }
    if (i == 0)
    {
      setState(4);
      return;
    }
    setState(3);
  }
  
  public final String toString()
  {
    Object[] arrayOfObject = new Object[4];
    String str1;
    String str2;
    if (this.mIsPatch)
    {
      str1 = "Patch";
      arrayOfObject[0] = str1;
      arrayOfObject[1] = this.mPackageName;
      arrayOfObject[2] = Integer.valueOf(this.mVersionCode);
      int i = this.mState;
      switch (i)
      {
      default: 
        str2 = Integer.toString(i);
      }
    }
    for (;;)
    {
      arrayOfObject[3] = str2;
      return String.format("%s: %s v:%d %s", arrayOfObject);
      str1 = "Main";
      break;
      str2 = "DOWNLOAD_PENDING";
      continue;
      str2 = "DOWNLOADING";
      continue;
      str2 = "DOWNLOADED";
      continue;
      str2 = "NOT_ON_STORAGE";
      continue;
      str2 = "NOT_APPLICABLE";
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.obb.ObbImpl
 * JD-Core Version:    0.7.0.1
 */