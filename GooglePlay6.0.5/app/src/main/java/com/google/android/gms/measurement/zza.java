package com.google.android.gms.measurement;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R.string;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;

public final class zza
{
  private static volatile zza zzbkV;
  private final String zzbkS;
  private final Status zzbkT;
  private final boolean zzbkU;
  
  private zza(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    String str1 = localResources.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
    int i = localResources.getIdentifier("google_app_measurement_enable", "integer", str1);
    int j;
    if ((i == 0) || (localResources.getInteger(i) != 0))
    {
      this.zzbkU = bool;
      j = localResources.getIdentifier("google_app_id", "string", str1);
      if (j != 0) {
        break label111;
      }
      if (!this.zzbkU) {
        break label101;
      }
    }
    label101:
    for (this.zzbkT = new Status(10, "Missing an expected resource: 'R.string.google_app_id' for initializing Google services.  Possible causes are missing google-services.json or com.google.gms.google-services gradle plugin.");; this.zzbkT = Status.zzaoz)
    {
      this.zzbkS = null;
      return;
      bool = false;
      break;
    }
    label111:
    String str2 = localResources.getString(j);
    if (TextUtils.isEmpty(str2))
    {
      if (this.zzbkU) {}
      for (this.zzbkT = new Status(10, "The resource 'R.string.google_app_id' is invalid, expected an app  identifier and found: '" + str2 + "'.");; this.zzbkT = Status.zzaoz)
      {
        this.zzbkS = null;
        return;
      }
    }
    this.zzbkS = str2;
    this.zzbkT = Status.zzaoz;
  }
  
  public static String zzBi()
  {
    if (zzbkV == null) {
      try
      {
        if (zzbkV == null) {
          throw new IllegalStateException("Initialize must be called before getGoogleAppId.");
        }
      }
      finally {}
    }
    zza localzza = zzbkV;
    if (!localzza.zzbkT.isSuccess()) {
      throw new IllegalStateException("Initialize must be successful before calling getGoogleAppId.  The result of the previous call to initialize was: '" + localzza.zzbkT + "'.");
    }
    return localzza.zzbkS;
  }
  
  public static boolean zzBk()
  {
    if (zzbkV == null) {
      try
      {
        if (zzbkV == null) {
          throw new IllegalStateException("Initialize must be called before isMeasurementEnabled.");
        }
      }
      finally {}
    }
    zza localzza = zzbkV;
    if (!localzza.zzbkT.isSuccess()) {
      throw new IllegalStateException("Initialize must be successful before calling isMeasurementEnabledInternal.  The result of the previous call to initialize was: '" + localzza.zzbkT + "'.");
    }
    return localzza.zzbkU;
  }
  
  public static Status zzaS(Context paramContext)
  {
    zzx.zzb(paramContext, "Context must not be null.");
    if (zzbkV == null) {}
    try
    {
      if (zzbkV == null) {
        zzbkV = new zza(paramContext);
      }
      return zzbkV.zzbkT;
    }
    finally {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.zza
 * JD-Core Version:    0.7.0.1
 */