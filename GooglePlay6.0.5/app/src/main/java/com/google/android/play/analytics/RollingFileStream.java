package com.google.android.play.analytics;

import android.util.Log;
import com.google.android.play.utils.Assertions;
import com.google.android.play.utils.FileModifiedDateComparator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class RollingFileStream<T>
{
  final WriteCallbacks<T> mCallbacks;
  File mCurrentOutputFile;
  private final File mDirectory;
  private final String mFileNamePrefix;
  private final String mFileNameSuffix;
  FileOutputStream mFileOutputStream;
  private final long mMaxStorageSize;
  final ArrayList<File> mReadFiles;
  final long mRecommendedFileSize;
  private final int mRecommendedTempFilesCountThreshold;
  final ArrayList<File> mWrittenFiles;
  
  public RollingFileStream(File paramFile, String paramString1, String paramString2, long paramLong1, long paramLong2, int paramInt, WriteCallbacks<T> paramWriteCallbacks)
  {
    boolean bool2;
    boolean bool3;
    if (paramLong1 > 0L)
    {
      bool2 = bool1;
      Assertions.checkArgument(bool2, "recommendedFileSize must be positive");
      if (paramLong2 <= 0L) {
        break label199;
      }
      bool3 = bool1;
      label36:
      Assertions.checkArgument(bool3, "maxStorageSize must be positive");
      if (paramWriteCallbacks == null) {
        break label205;
      }
    }
    for (;;)
    {
      Assertions.checkArgument(bool1, "callbacks cannot be null");
      this.mDirectory = paramFile;
      this.mFileNamePrefix = paramString1;
      this.mFileNameSuffix = paramString2;
      this.mRecommendedFileSize = paramLong1;
      this.mMaxStorageSize = paramLong2;
      this.mRecommendedTempFilesCountThreshold = paramInt;
      this.mCallbacks = paramWriteCallbacks;
      createNewOutputFile();
      if (this.mCurrentOutputFile == null) {
        Log.e("RollingFileStream", "Could not create a temp file with prefix: \"" + this.mFileNamePrefix + "\" and suffix: \"" + this.mFileNameSuffix + "\" in dir: \"" + this.mDirectory.getAbsolutePath() + "\".");
      }
      this.mWrittenFiles = new ArrayList();
      this.mReadFiles = new ArrayList();
      loadWrittenFiles();
      ensureMaxStorageSizeLimit();
      return;
      bool2 = false;
      break;
      label199:
      bool3 = false;
      break label36;
      label205:
      bool1 = false;
    }
  }
  
  private void loadWrittenFiles()
  {
    if (!this.mDirectory.exists()) {
      this.mDirectory.mkdirs();
    }
    Assertions.checkState(this.mDirectory.isDirectory(), "Expected a directory for path: " + this.mDirectory.getAbsolutePath());
    this.mWrittenFiles.clear();
    File[] arrayOfFile = this.mDirectory.listFiles();
    int i = arrayOfFile.length;
    int j = 0;
    if (j < i)
    {
      File localFile = arrayOfFile[j];
      if ((localFile.isFile()) && (!localFile.equals(this.mCurrentOutputFile)))
      {
        if (localFile.length() != 0L) {
          break label122;
        }
        localFile.delete();
      }
      for (;;)
      {
        j++;
        break;
        label122:
        this.mWrittenFiles.add(localFile);
      }
    }
    Collections.sort(this.mWrittenFiles, FileModifiedDateComparator.INSTANCE);
  }
  
  static byte[] toByteArray(File paramFile)
    throws IOException
  {
    long l = paramFile.length();
    if (l > 2147483647L) {
      throw new OutOfMemoryError("Too large to fit in a byte array: " + l);
    }
    if (l == 0L) {
      return new byte[0];
    }
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    int i = (int)l;
    byte[] arrayOfByte;
    for (;;)
    {
      int j;
      int k;
      try
      {
        arrayOfByte = new byte[i];
        j = 0;
        if (j >= arrayOfByte.length) {
          break;
        }
        k = localFileInputStream.read(arrayOfByte, j, arrayOfByte.length - j);
        if (k == -1) {
          throw new IOException("Unexpected EOS: " + arrayOfByte.length + ", " + j);
        }
      }
      finally
      {
        localFileInputStream.close();
      }
      j += k;
    }
    localFileInputStream.close();
    return arrayOfByte;
  }
  
  final void createNewOutputFile()
  {
    if (!this.mDirectory.exists()) {
      this.mDirectory.mkdirs();
    }
    this.mCurrentOutputFile = null;
    try
    {
      this.mCurrentOutputFile = File.createTempFile(this.mFileNamePrefix, this.mFileNameSuffix, this.mDirectory);
      this.mFileOutputStream = new FileOutputStream(this.mCurrentOutputFile);
      this.mCallbacks.onNewOutputFile();
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      if (this.mCurrentOutputFile != null) {
        this.mCurrentOutputFile.delete();
      }
      this.mCurrentOutputFile = null;
      return;
    }
    catch (IOException localIOException) {}
  }
  
  public final void deleteAllReadFiles()
  {
    Iterator localIterator = this.mReadFiles.iterator();
    while (localIterator.hasNext()) {
      ((File)localIterator.next()).delete();
    }
    this.mReadFiles.clear();
  }
  
  final void ensureMaxStorageSizeLimit()
  {
    long l = 0L;
    Iterator localIterator1 = this.mReadFiles.iterator();
    while (localIterator1.hasNext()) {
      l += ((File)localIterator1.next()).length();
    }
    Iterator localIterator2 = this.mWrittenFiles.iterator();
    while (localIterator2.hasNext()) {
      l += ((File)localIterator2.next()).length();
    }
    if (this.mCurrentOutputFile != null) {
      l += this.mCurrentOutputFile.length();
    }
    int i = 0;
    while (l > this.mMaxStorageSize)
    {
      i++;
      if (this.mReadFiles.size() > 0)
      {
        File localFile2 = (File)this.mReadFiles.remove(0);
        l -= localFile2.length();
        localFile2.delete();
      }
      else if (this.mWrittenFiles.size() > 0)
      {
        File localFile1 = (File)this.mWrittenFiles.remove(0);
        l -= localFile1.length();
        localFile1.delete();
      }
      else if (this.mCurrentOutputFile != null)
      {
        l -= this.mCurrentOutputFile.length();
        this.mCurrentOutputFile.delete();
        this.mCurrentOutputFile = null;
      }
    }
    if (i > 0) {
      Log.d("RollingFileStream", i + " files were purged due to exceeding total storage size of: " + this.mMaxStorageSize);
    }
  }
  
  public final boolean flushCurrentFile()
  {
    if (this.mCurrentOutputFile == null) {}
    int i;
    do
    {
      return false;
      i = 1 + (this.mWrittenFiles.size() + this.mReadFiles.size());
    } while ((this.mRecommendedTempFilesCountThreshold > 0) && (i >= this.mRecommendedTempFilesCountThreshold));
    try
    {
      this.mFileOutputStream.close();
      this.mWrittenFiles.add(this.mCurrentOutputFile);
      ensureMaxStorageSizeLimit();
      this.mCurrentOutputFile = null;
      this.mFileOutputStream = null;
      return true;
    }
    catch (IOException localIOException) {}
    return false;
  }
  
  public final void markAllFilesAsUnread()
  {
    this.mWrittenFiles.addAll(this.mReadFiles);
    Collections.sort(this.mWrittenFiles, FileModifiedDateComparator.INSTANCE);
    this.mReadFiles.clear();
  }
  
  public static abstract interface WriteCallbacks<T>
  {
    public abstract void onNewOutputFile();
    
    public abstract void onWrite(T paramT, OutputStream paramOutputStream)
      throws IOException;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.analytics.RollingFileStream
 * JD-Core Version:    0.7.0.1
 */