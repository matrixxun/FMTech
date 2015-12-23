package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.ClearcutLogger.MessageProducer;
import com.google.android.gms.clearcut.ClearcutLoggerApi;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.zzh;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class zzmq
  implements ClearcutLoggerApi
{
  private static final Object zzamY = new Object();
  private static final zze zzana = new zze((byte)0);
  private static final long zzanb = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.MINUTES);
  private GoogleApiClient mApiClient = null;
  private final zza zzanc;
  private final Object zzand = new Object();
  private long zzane = 0L;
  private final long zzanf;
  private ScheduledFuture<?> zzang = null;
  private final Runnable zzanh = new Runnable()
  {
    public final void run()
    {
      synchronized (zzmq.zza(zzmq.this))
      {
        if ((zzmq.zzb(zzmq.this) <= zzmq.zzc(zzmq.this).elapsedRealtime()) && (zzmq.zzd(zzmq.this) != null))
        {
          Log.i("ClearcutLoggerApiImpl", "disconnect managed GoogleApiClient");
          zzmq.zzd(zzmq.this).disconnect();
          zzmq.zza$62977020(zzmq.this);
        }
        return;
      }
    }
  };
  private final Clock zzri;
  
  public zzmq()
  {
    this(new zzh(), zzanb, new zzb());
  }
  
  private zzmq(Clock paramClock, long paramLong, zza paramzza)
  {
    this.zzri = paramClock;
    this.zzanf = paramLong;
    this.zzanc = paramzza;
  }
  
  private static void zza(LogEventParcelable paramLogEventParcelable)
  {
    if ((paramLogEventParcelable.extensionProducer != null) && (paramLogEventParcelable.logEvent.zzcjL.length == 0)) {
      paramLogEventParcelable.logEvent.zzcjL = paramLogEventParcelable.extensionProducer.toProtoBytes();
    }
    if ((paramLogEventParcelable.clientVisualElementsProducer != null) && (paramLogEventParcelable.logEvent.zzcjS.length == 0)) {
      paramLogEventParcelable.logEvent.zzcjS = paramLogEventParcelable.clientVisualElementsProducer.toProtoBytes();
    }
    paramLogEventParcelable.logEventBytes = zzaik.toByteArray(paramLogEventParcelable.logEvent);
  }
  
  public final boolean flush$708acd79(long paramLong, TimeUnit paramTimeUnit)
  {
    try
    {
      boolean bool = zzana.zza(100L, paramTimeUnit);
      return bool;
    }
    catch (InterruptedException localInterruptedException)
    {
      Log.e("ClearcutLoggerApiImpl", "flush interrupted");
      Thread.currentThread().interrupt();
    }
    return false;
  }
  
  public final PendingResult<Status> logEvent(GoogleApiClient paramGoogleApiClient, LogEventParcelable paramLogEventParcelable)
  {
    zza(paramLogEventParcelable);
    zzana.increment();
    zzd localzzd = new zzd(paramLogEventParcelable, paramGoogleApiClient);
    localzzd.zza(new PendingResult.zza()
    {
      public final void zzC$e184e5d()
      {
        zzmq.zzoh().zzoi();
      }
    });
    return paramGoogleApiClient.zza(localzzd);
  }
  
  public static abstract interface zza {}
  
  public static final class zzb
    implements zzmq.zza
  {}
  
  static abstract class zzc<R extends Result>
    extends zzmu.zza<R, zzmr>
  {
    public zzc(GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
  }
  
  final class zzd
    extends zzmq.zzc<Status>
  {
    private final LogEventParcelable zzanq;
    
    zzd(LogEventParcelable paramLogEventParcelable, GoogleApiClient paramGoogleApiClient)
    {
      super();
      this.zzanq = paramLogEventParcelable;
    }
    
    public final boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof zzd)) {
        return false;
      }
      zzd localzzd = (zzd)paramObject;
      return this.zzanq.equals(localzzd.zzanq);
    }
    
    public final String toString()
    {
      return "MethodImpl(" + this.zzanq + ")";
    }
  }
  
  private static final class zze
  {
    private int mSize = 0;
    
    public final void increment()
    {
      try
      {
        this.mSize = (1 + this.mSize);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public final boolean zza(long paramLong, TimeUnit paramTimeUnit)
      throws InterruptedException
    {
      long l1 = System.currentTimeMillis();
      long l2 = TimeUnit.MILLISECONDS.convert(paramLong, paramTimeUnit);
      for (;;)
      {
        try
        {
          if (this.mSize == 0) {
            return true;
          }
          if (l2 <= 0L) {
            return false;
          }
        }
        finally {}
        wait(l2);
        long l3 = System.currentTimeMillis();
        l2 -= l3 - l1;
      }
    }
    
    public final void zzoi()
    {
      try
      {
        if (this.mSize == 0) {
          throw new RuntimeException("too many decrements");
        }
      }
      finally {}
      this.mSize = (-1 + this.mSize);
      if (this.mSize == 0) {
        notifyAll();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmq
 * JD-Core Version:    0.7.0.1
 */