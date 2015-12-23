package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class zzk<T>
  implements Comparable<zzk<T>>
{
  private final zzs.zza zzC;
  final int zzD;
  final String zzE;
  final int zzF;
  final zzm.zza zzG;
  Integer zzH;
  zzl zzI;
  boolean zzJ;
  boolean zzK;
  boolean zzL;
  private long zzM;
  zzo zzN;
  zzb.zza zzO;
  
  public zzk(int paramInt, String paramString, zzm.zza paramzza)
  {
    zzs.zza localzza;
    String str;
    if (zzs.zza.zzaj)
    {
      localzza = new zzs.zza();
      this.zzC = localzza;
      this.zzJ = true;
      this.zzK = false;
      this.zzL = false;
      this.zzM = 0L;
      this.zzO = null;
      this.zzD = 0;
      this.zzE = paramString;
      this.zzG = paramzza;
      this.zzN = new zzd();
      if (TextUtils.isEmpty(paramString)) {
        break label126;
      }
      Uri localUri = Uri.parse(paramString);
      if (localUri == null) {
        break label126;
      }
      str = localUri.getHost();
      if (str == null) {
        break label126;
      }
    }
    label126:
    for (int i = str.hashCode();; i = 0)
    {
      this.zzF = i;
      return;
      localzza = null;
      break;
    }
  }
  
  protected static zzr zzb(zzr paramzzr)
  {
    return paramzzr;
  }
  
  public static String zzo()
  {
    return "application/x-www-form-urlencoded; charset=UTF-8";
  }
  
  public Map<String, String> getHeaders()
    throws zza
  {
    return Collections.emptyMap();
  }
  
  public String toString()
  {
    String str1 = "0x" + Integer.toHexString(this.zzF);
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.zzK) {}
    for (String str2 = "[X] ";; str2 = "[ ] ") {
      return str2 + this.zzE + " " + str1 + " " + zza.zzT + " " + this.zzH;
    }
  }
  
  protected abstract zzm<T> zza(zzi paramzzi);
  
  protected abstract void zza(T paramT);
  
  public final void zzc(String paramString)
  {
    if (zzs.zza.zzaj) {
      this.zzC.zza(paramString, Thread.currentThread().getId());
    }
    while (this.zzM != 0L) {
      return;
    }
    this.zzM = SystemClock.elapsedRealtime();
  }
  
  final void zzd(final String paramString)
  {
    zzl localzzl;
    if (this.zzI != null)
    {
      localzzl = this.zzI;
      synchronized (localzzl.zzZ)
      {
        localzzl.zzZ.remove(this);
        synchronized (localzzl.zzae)
        {
          Iterator localIterator = localzzl.zzae.iterator();
          if (localIterator.hasNext()) {
            localIterator.next();
          }
        }
      }
      if (!this.zzJ) {}
    }
    label277:
    long l1;
    do
    {
      final long l2;
      synchronized (localzzl.zzY)
      {
        String str = this.zzE;
        Queue localQueue = (Queue)localzzl.zzY.remove(str);
        if (localQueue != null)
        {
          if (zzs.DEBUG)
          {
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = Integer.valueOf(localQueue.size());
            arrayOfObject2[1] = str;
            zzs.zza("Releasing %d waiting requests for cacheKey=%s.", arrayOfObject2);
          }
          localzzl.zzaa.addAll(localQueue);
        }
        if (!zzs.zza.zzaj) {
          break label277;
        }
        l2 = Thread.currentThread().getId();
        if (Looper.myLooper() != Looper.getMainLooper())
        {
          new Handler(Looper.getMainLooper()).post(new Runnable()
          {
            public final void run()
            {
              zzk.zzd(zzk.this).zza(paramString, l2);
              zzk.zzd(zzk.this).zzd(toString());
            }
          });
          return;
        }
      }
      this.zzC.zza(paramString, l2);
      this.zzC.zzd(toString());
      return;
      l1 = SystemClock.elapsedRealtime() - this.zzM;
    } while (l1 < 3000L);
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Long.valueOf(l1);
    arrayOfObject1[1] = toString();
    zzs.zzb("%d ms: %s", arrayOfObject1);
  }
  
  public final int zzs()
  {
    return this.zzN.zzd();
  }
  
  public static enum zza
  {
    static
    {
      zza[] arrayOfzza = new zza[4];
      arrayOfzza[0] = zzS;
      arrayOfzza[1] = zzT;
      arrayOfzza[2] = zzU;
      arrayOfzza[3] = zzV;
      zzW = arrayOfzza;
    }
    
    private zza() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzk
 * JD-Core Version:    0.7.0.1
 */