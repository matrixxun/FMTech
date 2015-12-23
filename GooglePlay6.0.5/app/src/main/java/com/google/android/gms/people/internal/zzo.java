package com.google.android.gms.people.internal;

import android.util.Log;

public final class zzo
{
  private static volatile boolean zzbxI;
  
  public static void zzH(String paramString1, String paramString2)
  {
    if (zzdj(3)) {
      Log.d(paramString1, paramString2);
    }
  }
  
  public static void zzb(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (zzdj(5)) {
      Log.w(paramString1, paramString2, paramThrowable);
    }
  }
  
  public static boolean zzdj(int paramInt)
  {
    return (zzbxI) || (Log.isLoggable("PeopleService", paramInt));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzo
 * JD-Core Version:    0.7.0.1
 */