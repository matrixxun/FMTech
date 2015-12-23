package com.google.android.finsky.download;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import java.io.File;

public final class Storage
{
  public static long dataPartitionAvailableSpace()
  {
    return partitionAvailable(Environment.getDataDirectory().getPath());
  }
  
  public static long dataPartitionTotalSpace()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    if (Build.VERSION.SDK_INT >= 18) {
      return localStatFs.getTotalBytes();
    }
    return localStatFs.getBlockSize() * localStatFs.getBlockCount();
  }
  
  public static boolean externalStorageAvailable()
  {
    return "mounted".equals(Environment.getExternalStorageState());
  }
  
  public static long externalStorageAvailableSpace()
  {
    if (!externalStorageAvailable()) {
      return -1L;
    }
    return partitionAvailable(Environment.getExternalStorageDirectory().getPath());
  }
  
  @TargetApi(18)
  private static long partitionAvailable(String paramString)
  {
    StatFs localStatFs = new StatFs(paramString);
    if (Build.VERSION.SDK_INT >= 18) {
      return localStatFs.getAvailableBytes();
    }
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.Storage
 * JD-Core Version:    0.7.0.1
 */