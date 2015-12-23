package com.google.android.gms.clearcut;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzaip.zzd;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzmr;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.ArrayList;
import java.util.TimeZone;

public final class ClearcutLogger
{
  public static final Api<Object> API = new Api("ClearcutLogger.API", zzTR, zzTQ);
  public static final ClearcutLoggerApi ClearcutLoggerApi = new zzmq();
  public static final Api.zzc<zzmr> zzTQ = new Api.zzc();
  public static final Api.zza<zzmr, Object> zzTR = new Api.zza() {};
  private final Context mContext;
  private final String mPackageName;
  private final int zzamo;
  private String zzamp;
  private int zzamq = -1;
  private String zzamr;
  private String zzams;
  private final boolean zzamt;
  private int zzamu = 0;
  public final ClearcutLoggerApi zzamv;
  private TimeZoneOffsetProvider zzamw;
  private final Clock zzri;
  
  @Deprecated
  public ClearcutLogger(Context paramContext, String paramString)
  {
    this(paramContext, paramString, null, null, ClearcutLoggerApi, zzh.zzrs());
  }
  
  private ClearcutLogger(Context paramContext, String paramString1, String paramString2, String paramString3, ClearcutLoggerApi paramClearcutLoggerApi, Clock paramClock)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mPackageName = paramContext.getPackageName();
    this.zzamo = zzaj(paramContext);
    this.zzamq = -1;
    this.zzamp = paramString1;
    this.zzamr = null;
    this.zzams = null;
    this.zzamt = false;
    this.zzamv = paramClearcutLoggerApi;
    this.zzri = paramClock;
    this.zzamw = new TimeZoneOffsetProvider();
    this.zzamu = 0;
    if (this.zzamt)
    {
      String str = this.zzamr;
      boolean bool = false;
      if (str == null) {
        bool = true;
      }
      zzx.zzb(bool, "can't be anonymous with an upload account");
    }
  }
  
  private static int zzaj(Context paramContext)
  {
    try
    {
      int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Log.wtf("ClearcutLogger", "This can't happen.");
    }
    return 0;
  }
  
  public final LogEventBuilder newEvent(byte[] paramArrayOfByte)
  {
    return new LogEventBuilder(paramArrayOfByte, '\000');
  }
  
  public final class LogEventBuilder
  {
    public final zzaip.zzd zzamA = new zzaip.zzd();
    private boolean zzamB = false;
    private String zzamp = ClearcutLogger.zzb(ClearcutLogger.this);
    private int zzamq = ClearcutLogger.zza(ClearcutLogger.this);
    private String zzamr = ClearcutLogger.zzc(ClearcutLogger.this);
    private String zzams = ClearcutLogger.zzd(ClearcutLogger.this);
    private int zzamu = ClearcutLogger.zze(ClearcutLogger.this);
    private final ClearcutLogger.MessageProducer zzamx;
    private ClearcutLogger.MessageProducer zzamy;
    private ArrayList<Integer> zzamz = null;
    
    private LogEventBuilder(byte[] paramArrayOfByte)
    {
      this(paramArrayOfByte, (byte)0);
    }
    
    private LogEventBuilder(byte[] paramArrayOfByte, byte paramByte)
    {
      this.zzamA.zzcjF = ClearcutLogger.zzf(ClearcutLogger.this).currentTimeMillis();
      this.zzamA.zzcjG = ClearcutLogger.zzf(ClearcutLogger.this).elapsedRealtime();
      zzaip.zzd localzzd = this.zzamA;
      ClearcutLogger.zzg(ClearcutLogger.this);
      long l = this.zzamA.zzcjF;
      localzzd.zzcjQ = (TimeZone.getDefault().getOffset(l) / 1000);
      if (paramArrayOfByte != null) {
        this.zzamA.zzcjL = paramArrayOfByte;
      }
      this.zzamx = null;
    }
    
    public final PendingResult<Status> log(GoogleApiClient paramGoogleApiClient)
    {
      if (this.zzamB) {
        throw new IllegalStateException("do not reuse LogEventBuilder");
      }
      this.zzamB = true;
      ClearcutLoggerApi localClearcutLoggerApi = ClearcutLogger.zzk(ClearcutLogger.this);
      LogEventParcelable localLogEventParcelable = new LogEventParcelable(new PlayLoggerContext(ClearcutLogger.zzi(ClearcutLogger.this), ClearcutLogger.zzj(ClearcutLogger.this), this.zzamq, this.zzamp, this.zzamr, this.zzams, ClearcutLogger.zzh(ClearcutLogger.this), this.zzamu), this.zzamA, this.zzamx, this.zzamy, ClearcutLogger.zzc(this.zzamz));
      return localClearcutLoggerApi.logEvent(paramGoogleApiClient, localLogEventParcelable);
    }
  }
  
  public static abstract interface MessageProducer
  {
    public abstract byte[] toProtoBytes();
  }
  
  public static final class TimeZoneOffsetProvider {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.clearcut.ClearcutLogger
 * JD-Core Version:    0.7.0.1
 */