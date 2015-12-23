package com.google.android.finsky.local;

import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.protos.AndroidAppDeliveryData;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.AppFileMetadata;
import com.google.android.finsky.protos.FileMetadata;
import com.google.android.finsky.utils.FinskyLog;

public final class AssetUtils
{
  public static Obb extractObb(AndroidAppDeliveryData paramAndroidAppDeliveryData, String paramString, boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 1;
    }
    for (int j = 0;; j++)
    {
      if (j >= paramAndroidAppDeliveryData.additionalFile.length) {
        break label69;
      }
      AppFileMetadata localAppFileMetadata = paramAndroidAppDeliveryData.additionalFile[j];
      if (localAppFileMetadata.fileType == i)
      {
        return ObbFactory.create$3c875ae9(paramBoolean, paramString, localAppFileMetadata.versionCode, localAppFileMetadata.downloadUrl, localAppFileMetadata.size);
        i = 0;
        break;
      }
    }
    label69:
    return null;
  }
  
  public static String makeAssetId(String paramString, int paramInt)
  {
    return "v2:" + paramString + ":1:" + paramInt;
  }
  
  public static long totalDeliverySize(AppDetails paramAppDetails)
  {
    long l = 0L;
    int i = 0;
    if (i < paramAppDetails.file.length)
    {
      FileMetadata localFileMetadata = paramAppDetails.file[i];
      int j = localFileMetadata.fileType;
      switch (j)
      {
      default: 
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Integer.valueOf(j);
        arrayOfObject[1] = paramAppDetails.packageName;
        arrayOfObject[2] = Integer.valueOf(i);
        FinskyLog.w("Bad file type %d in %s entry# %d", arrayOfObject);
      case 0: 
        for (;;)
        {
          i++;
          break;
          l += localFileMetadata.size;
        }
      }
      if (j == 2) {}
      for (boolean bool = true;; bool = false)
      {
        Obb localObb = ObbFactory.create$3c875ae9(bool, paramAppDetails.packageName, localFileMetadata.versionCode, null, localFileMetadata.size);
        if (localObb.getState() != 4) {
          break;
        }
        l += localObb.getSize();
        break;
      }
    }
    return l;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.local.AssetUtils
 * JD-Core Version:    0.7.0.1
 */