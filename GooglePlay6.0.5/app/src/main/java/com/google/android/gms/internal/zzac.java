package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import java.io.File;

public final class zzac
{
  public static zzl zza$575a9856(Context paramContext)
  {
    File localFile = new File(paramContext.getCacheDir(), "volley");
    Object localObject1 = "volley/0";
    try
    {
      String str1 = paramContext.getPackageName();
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(str1, 0);
      String str2 = str1 + "/" + localPackageInfo.versionCode;
      localObject1 = str2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      label68:
      Object localObject2;
      break label68;
    }
    if (Build.VERSION.SDK_INT >= 9) {}
    for (localObject2 = new zzz();; localObject2 = new zzw(AndroidHttpClient.newInstance((String)localObject1)))
    {
      zzt localzzt = new zzt((zzy)localObject2);
      zzl localzzl = new zzl(new zzv(localFile, (byte)0), localzzt, (byte)0);
      localzzl.start();
      return localzzl;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzac
 * JD-Core Version:    0.7.0.1
 */