package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zznx<T>
{
  static zza zzari = null;
  private static int zzarj = 0;
  private static String zzark = "com.google.android.providers.gsf.permission.READ_GSERVICES";
  private static final Object zzqK = new Object();
  private T zzQD = null;
  protected final String zzvB;
  protected final T zzvC;
  
  protected zznx(String paramString, T paramT)
  {
    this.zzvB = paramString;
    this.zzvC = paramT;
  }
  
  public static boolean isInitialized()
  {
    return zzari != null;
  }
  
  public static zznx<String> zzE(String paramString1, String paramString2)
  {
    new zznx(paramString1, paramString2) {};
  }
  
  public static zznx<Integer> zza(String paramString, Integer paramInteger)
  {
    new zznx(paramString, paramInteger) {};
  }
  
  public static zznx<Long> zza(String paramString, Long paramLong)
  {
    new zznx(paramString, paramLong) {};
  }
  
  public static zznx<Boolean> zzj(String paramString, boolean paramBoolean)
  {
    new zznx(paramString, Boolean.valueOf(true)) {};
  }
  
  public static int zzpx()
  {
    return zzarj;
  }
  
  public final T get()
  {
    if (this.zzQD != null) {
      return this.zzQD;
    }
    return zzcn$9543ced();
  }
  
  public final T getBinderSafe()
  {
    long l = Binder.clearCallingIdentity();
    try
    {
      Object localObject2 = get();
      return localObject2;
    }
    finally
    {
      Binder.restoreCallingIdentity(l);
    }
  }
  
  protected abstract T zzcn$9543ced();
  
  private static abstract interface zza
  {
    public abstract Long getLong$4885d6e9();
    
    public abstract String getString$7157d249();
    
    public abstract Boolean zza$6de378eb();
    
    public abstract Integer zzb$1b7f1b3f();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznx
 * JD-Core Version:    0.7.0.1
 */