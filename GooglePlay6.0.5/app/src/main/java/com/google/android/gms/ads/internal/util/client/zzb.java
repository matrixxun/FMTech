package com.google.android.gms.ads.internal.util.client;

import android.util.Log;
import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zzb
{
  public static void d(String paramString)
  {
    if (zze(3)) {
      Log.d("Ads", paramString);
    }
  }
  
  public static void d(String paramString, Throwable paramThrowable)
  {
    if (zze(3)) {
      Log.d("Ads", paramString, paramThrowable);
    }
  }
  
  public static void e(String paramString)
  {
    if (zze(6)) {
      Log.e("Ads", paramString);
    }
  }
  
  public static void e(String paramString, Throwable paramThrowable)
  {
    if (zze(6)) {
      Log.e("Ads", paramString, paramThrowable);
    }
  }
  
  public static void i(String paramString)
  {
    if (zze(4)) {
      Log.i("Ads", paramString);
    }
  }
  
  public static void v(String paramString)
  {
    if (zze(2)) {
      Log.v("Ads", paramString);
    }
  }
  
  public static void w(String paramString)
  {
    if (zze(5)) {
      Log.w("Ads", paramString);
    }
  }
  
  public static void w(String paramString, Throwable paramThrowable)
  {
    if (zze(5)) {
      Log.w("Ads", paramString, paramThrowable);
    }
  }
  
  public static boolean zze(int paramInt)
  {
    if ((paramInt >= 5) || (Log.isLoggable("Ads", paramInt))) {
      if (paramInt == 2)
      {
        Flag localFlag = Flags.zzwT;
        if (!((Boolean)zzp.zzbR().zzd(localFlag)).booleanValue()) {}
      }
      else
      {
        return true;
      }
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.util.client.zzb
 * JD-Core Version:    0.7.0.1
 */