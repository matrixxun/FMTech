package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public final class zzmx
  implements zznj
{
  private final Context mContext;
  final Lock zzXP;
  private final zznd zzaoR;
  final zznf zzaoS;
  final zznf zzaoT;
  private final Map<Api.zzc<?>, zznf> zzaoU = new ArrayMap();
  private final Set<Object> zzaoV = Collections.newSetFromMap(new WeakHashMap());
  private final Api.zzb zzaoW;
  Bundle zzaoX;
  ConnectionResult zzaoY = null;
  ConnectionResult zzaoZ = null;
  private final AtomicInteger zzapa = new AtomicInteger(0);
  private int zzapb = 0;
  private final Looper zzoD;
  
  public zzmx(Context paramContext, zznd paramzznd, Lock paramLock, Looper paramLooper, GoogleApiAvailability paramGoogleApiAvailability, Map<Api.zzc<?>, Api.zzb> paramMap, zzf paramzzf, Map<Api<?>, Integer> paramMap1, Api.zza<? extends zzwz, zzxa> paramzza, ArrayList<zzmw> paramArrayList)
  {
    this.mContext = paramContext;
    this.zzaoR = paramzznd;
    this.zzXP = paramLock;
    this.zzoD = paramLooper;
    ArrayMap localArrayMap1 = new ArrayMap();
    ArrayMap localArrayMap2 = new ArrayMap();
    Iterator localIterator1 = paramMap.keySet().iterator();
    while (localIterator1.hasNext())
    {
      Api.zzc localzzc4 = (Api.zzc)localIterator1.next();
      Api.zzb localzzb = (Api.zzb)paramMap.get(localzzc4);
      if (localzzb.zzkc()) {
        localArrayMap1.put(localzzc4, localzzb);
      } else {
        localArrayMap2.put(localzzc4, localzzb);
      }
    }
    this.zzaoW = null;
    if (localArrayMap1.isEmpty()) {
      throw new IllegalStateException("CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
    }
    ArrayMap localArrayMap3 = new ArrayMap();
    ArrayMap localArrayMap4 = new ArrayMap();
    Iterator localIterator2 = paramMap1.keySet().iterator();
    while (localIterator2.hasNext())
    {
      Api localApi = (Api)localIterator2.next();
      Api.zzc localzzc3 = localApi.zzor();
      if (localArrayMap1.containsKey(localzzc3)) {
        localArrayMap3.put(localApi, paramMap1.get(localApi));
      } else if (localArrayMap2.containsKey(localzzc3)) {
        localArrayMap4.put(localApi, paramMap1.get(localApi));
      } else {
        throw new IllegalStateException("Each API in the apiTypeMap must have a corresponding client in the clients map.");
      }
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator3 = paramArrayList.iterator();
    while (localIterator3.hasNext())
    {
      zzmw localzzmw = (zzmw)localIterator3.next();
      if (localArrayMap3.containsKey(localzzmw.zzaoO)) {
        localArrayList1.add(localzzmw);
      } else if (localArrayMap4.containsKey(localzzmw.zzaoO)) {
        localArrayList2.add(localzzmw);
      } else {
        throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the apiTypeMap");
      }
    }
    zznj.zza local1 = new zznj.zza()
    {
      public final void zzcM(int paramAnonymousInt)
      {
        zzmx.this.zzXP.lock();
        try
        {
          zzmx.zza(zzmx.this, zzmx.this.zzaoT, paramAnonymousInt);
          return;
        }
        finally
        {
          zzmx.this.zzXP.unlock();
        }
      }
      
      public final void zze(ConnectionResult paramAnonymousConnectionResult)
      {
        zzmx.this.zzXP.lock();
        try
        {
          zzmx.this.zzaoY = paramAnonymousConnectionResult;
          zzmx.zzb(zzmx.this);
          return;
        }
        finally
        {
          zzmx.this.zzXP.unlock();
        }
      }
      
      /* Error */
      public final void zzo(Bundle paramAnonymousBundle)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 16	com/google/android/gms/internal/zzmx$1:zzapc	Lcom/google/android/gms/internal/zzmx;
        //   4: getfield 25	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
        //   7: invokeinterface 30 1 0
        //   12: aload_0
        //   13: getfield 16	com/google/android/gms/internal/zzmx$1:zzapc	Lcom/google/android/gms/internal/zzmx;
        //   16: astore_3
        //   17: aload_3
        //   18: getfield 56	com/google/android/gms/internal/zzmx:zzaoX	Landroid/os/Bundle;
        //   21: ifnonnull +38 -> 59
        //   24: aload_3
        //   25: aload_1
        //   26: putfield 56	com/google/android/gms/internal/zzmx:zzaoX	Landroid/os/Bundle;
        //   29: aload_0
        //   30: getfield 16	com/google/android/gms/internal/zzmx$1:zzapc	Lcom/google/android/gms/internal/zzmx;
        //   33: getstatic 61	com/google/android/gms/common/ConnectionResult:zzanu	Lcom/google/android/gms/common/ConnectionResult;
        //   36: putfield 47	com/google/android/gms/internal/zzmx:zzaoY	Lcom/google/android/gms/common/ConnectionResult;
        //   39: aload_0
        //   40: getfield 16	com/google/android/gms/internal/zzmx$1:zzapc	Lcom/google/android/gms/internal/zzmx;
        //   43: invokestatic 50	com/google/android/gms/internal/zzmx:zzb	(Lcom/google/android/gms/internal/zzmx;)V
        //   46: aload_0
        //   47: getfield 16	com/google/android/gms/internal/zzmx$1:zzapc	Lcom/google/android/gms/internal/zzmx;
        //   50: getfield 25	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
        //   53: invokeinterface 41 1 0
        //   58: return
        //   59: aload_1
        //   60: ifnull -31 -> 29
        //   63: aload_3
        //   64: getfield 56	com/google/android/gms/internal/zzmx:zzaoX	Landroid/os/Bundle;
        //   67: aload_1
        //   68: invokevirtual 66	android/os/Bundle:putAll	(Landroid/os/Bundle;)V
        //   71: goto -42 -> 29
        //   74: astore_2
        //   75: aload_0
        //   76: getfield 16	com/google/android/gms/internal/zzmx$1:zzapc	Lcom/google/android/gms/internal/zzmx;
        //   79: getfield 25	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
        //   82: invokeinterface 41 1 0
        //   87: aload_2
        //   88: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	89	0	this	1
        //   0	89	1	paramAnonymousBundle	Bundle
        //   74	14	2	localObject	Object
        //   16	48	3	localzzmx	zzmx
        // Exception table:
        //   from	to	target	type
        //   12	29	74	finally
        //   29	46	74	finally
        //   63	71	74	finally
      }
    };
    this.zzaoS = new zznf(paramContext, this.zzaoR, paramLock, paramLooper, paramGoogleApiAvailability, localArrayMap2, null, localArrayMap4, null, localArrayList2, local1);
    zznj.zza local2 = new zznj.zza()
    {
      public final void zzcM(int paramAnonymousInt)
      {
        zzmx.this.zzXP.lock();
        try
        {
          zzmx.zza(zzmx.this, zzmx.this.zzaoS, paramAnonymousInt);
          return;
        }
        finally
        {
          zzmx.this.zzXP.unlock();
        }
      }
      
      public final void zze(ConnectionResult paramAnonymousConnectionResult)
      {
        zzmx.this.zzXP.lock();
        try
        {
          zzmx.this.zzaoZ = paramAnonymousConnectionResult;
          zzmx.zzb(zzmx.this);
          return;
        }
        finally
        {
          zzmx.this.zzXP.unlock();
        }
      }
      
      public final void zzo(Bundle paramAnonymousBundle)
      {
        zzmx.this.zzXP.lock();
        try
        {
          zzmx.this.zzaoZ = ConnectionResult.zzanu;
          zzmx.zzb(zzmx.this);
          return;
        }
        finally
        {
          zzmx.this.zzXP.unlock();
        }
      }
    };
    this.zzaoT = new zznf(paramContext, this.zzaoR, paramLock, paramLooper, paramGoogleApiAvailability, localArrayMap1, paramzzf, localArrayMap3, paramzza, localArrayList1, local2);
    Iterator localIterator4 = localArrayMap2.keySet().iterator();
    while (localIterator4.hasNext())
    {
      Api.zzc localzzc2 = (Api.zzc)localIterator4.next();
      this.zzaoU.put(localzzc2, this.zzaoS);
    }
    Iterator localIterator5 = localArrayMap1.keySet().iterator();
    while (localIterator5.hasNext())
    {
      Api.zzc localzzc1 = (Api.zzc)localIterator5.next();
      this.zzaoU.put(localzzc1, this.zzaoT);
    }
  }
  
  private void zzc(ConnectionResult paramConnectionResult)
  {
    switch (this.zzapb)
    {
    default: 
      Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CALLBACK_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
    }
    for (;;)
    {
      this.zzapb = 0;
      return;
      this.zzaoR.zze(paramConnectionResult);
      zzoO();
    }
  }
  
  private boolean zzc(zzmu.zza<? extends Result, ? extends Api.zzb> paramzza)
  {
    Api.zzc localzzc = paramzza.zzalR;
    zzx.zzb(this.zzaoU.containsKey(localzzc), "GoogleApiClient is not configured to use the API required for this call.");
    return ((zznf)this.zzaoU.get(localzzc)).equals(this.zzaoT);
  }
  
  private static boolean zzd(ConnectionResult paramConnectionResult)
  {
    return (paramConnectionResult != null) && (paramConnectionResult.isSuccess());
  }
  
  private void zzoO()
  {
    Iterator localIterator = this.zzaoV.iterator();
    while (localIterator.hasNext()) {
      localIterator.next();
    }
    this.zzaoV.clear();
  }
  
  private boolean zzoP()
  {
    return (this.zzaoZ != null) && (this.zzaoZ.zzakr == 4);
  }
  
  private PendingIntent zzoQ()
  {
    if (this.zzaoW == null) {
      return null;
    }
    return PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zzaoR), this.zzaoW.zzks(), 134217728);
  }
  
  public final ConnectionResult blockingConnect()
  {
    throw new UnsupportedOperationException();
  }
  
  public final ConnectionResult blockingConnect(long paramLong, TimeUnit paramTimeUnit)
  {
    throw new UnsupportedOperationException();
  }
  
  public final void connect()
  {
    this.zzapb = 2;
    this.zzaoZ = null;
    this.zzaoY = null;
    this.zzaoS.connect();
    this.zzaoT.connect();
  }
  
  public final void disconnect()
  {
    this.zzaoZ = null;
    this.zzaoY = null;
    this.zzapb = 0;
    this.zzaoS.disconnect();
    this.zzaoT.disconnect();
    zzoO();
  }
  
  public final void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.append(paramString).append("authClient").println(":");
    this.zzaoT.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    paramPrintWriter.append(paramString).append("unauthClient").println(":");
    this.zzaoS.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }
  
  /* Error */
  public final boolean isConnected()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: aload_0
    //   3: getfield 74	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
    //   6: invokeinterface 321 1 0
    //   11: aload_0
    //   12: getfield 164	com/google/android/gms/internal/zzmx:zzaoS	Lcom/google/android/gms/internal/zznf;
    //   15: invokevirtual 323	com/google/android/gms/internal/zznf:isConnected	()Z
    //   18: ifeq +41 -> 59
    //   21: aload_0
    //   22: getfield 169	com/google/android/gms/internal/zzmx:zzaoT	Lcom/google/android/gms/internal/zznf;
    //   25: invokevirtual 323	com/google/android/gms/internal/zznf:isConnected	()Z
    //   28: ifne +20 -> 48
    //   31: aload_0
    //   32: invokespecial 191	com/google/android/gms/internal/zzmx:zzoP	()Z
    //   35: ifne +13 -> 48
    //   38: aload_0
    //   39: getfield 68	com/google/android/gms/internal/zzmx:zzapb	I
    //   42: istore_3
    //   43: iload_3
    //   44: iload_1
    //   45: if_icmpne +14 -> 59
    //   48: aload_0
    //   49: getfield 74	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
    //   52: invokeinterface 326 1 0
    //   57: iload_1
    //   58: ireturn
    //   59: iconst_0
    //   60: istore_1
    //   61: goto -13 -> 48
    //   64: astore_2
    //   65: aload_0
    //   66: getfield 74	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
    //   69: invokeinterface 326 1 0
    //   74: aload_2
    //   75: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	this	zzmx
    //   1	60	1	i	int
    //   64	11	2	localObject	Object
    //   42	4	3	j	int
    // Exception table:
    //   from	to	target	type
    //   11	43	64	finally
  }
  
  /* Error */
  public final boolean isConnecting()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 74	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
    //   4: invokeinterface 321 1 0
    //   9: aload_0
    //   10: getfield 164	com/google/android/gms/internal/zzmx:zzaoS	Lcom/google/android/gms/internal/zznf;
    //   13: invokevirtual 329	com/google/android/gms/internal/zznf:isConnecting	()Z
    //   16: ifne +23 -> 39
    //   19: aload_0
    //   20: getfield 169	com/google/android/gms/internal/zzmx:zzaoT	Lcom/google/android/gms/internal/zznf;
    //   23: invokevirtual 329	com/google/android/gms/internal/zznf:isConnecting	()Z
    //   26: ifeq +26 -> 52
    //   29: aload_0
    //   30: getfield 68	com/google/android/gms/internal/zzmx:zzapb	I
    //   33: istore_3
    //   34: iload_3
    //   35: iconst_2
    //   36: if_icmpne +16 -> 52
    //   39: iconst_1
    //   40: istore_2
    //   41: aload_0
    //   42: getfield 74	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
    //   45: invokeinterface 326 1 0
    //   50: iload_2
    //   51: ireturn
    //   52: iconst_0
    //   53: istore_2
    //   54: goto -13 -> 41
    //   57: astore_1
    //   58: aload_0
    //   59: getfield 74	com/google/android/gms/internal/zzmx:zzXP	Ljava/util/concurrent/locks/Lock;
    //   62: invokeinterface 326 1 0
    //   67: aload_1
    //   68: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	69	0	this	zzmx
    //   57	11	1	localObject	Object
    //   40	14	2	bool	boolean
    //   33	4	3	i	int
    // Exception table:
    //   from	to	target	type
    //   9	34	57	finally
  }
  
  public final <A extends Api.zzb, R extends Result, T extends zzmu.zza<R, A>> T zza(T paramT)
  {
    if (zzc(paramT))
    {
      if (zzoP())
      {
        paramT.zzE(new Status(4, null, zzoQ()));
        return paramT;
      }
      return this.zzaoT.zza(paramT);
    }
    return this.zzaoS.zza(paramT);
  }
  
  public final <A extends Api.zzb, T extends zzmu.zza<? extends Result, A>> T zzb(T paramT)
  {
    if (zzc(paramT))
    {
      if (zzoP())
      {
        paramT.zzE(new Status(4, null, zzoQ()));
        return paramT;
      }
      return this.zzaoT.zzb(paramT);
    }
    return this.zzaoS.zzb(paramT);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmx
 * JD-Core Version:    0.7.0.1
 */