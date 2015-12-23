package com.google.android.finsky.download.obb;

import java.io.File;

public final class ObbFactory
{
  public static File sObbMasterDirectory;
  
  public static Obb create$3c875ae9(boolean paramBoolean, String paramString1, int paramInt, String paramString2, long paramLong)
  {
    return new ObbImpl(paramBoolean, paramString1, paramInt, paramString2, paramLong, 4);
  }
  
  public static File getParentDirectory(String paramString)
  {
    return new File(sObbMasterDirectory, paramString);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.obb.ObbFactory
 * JD-Core Version:    0.7.0.1
 */