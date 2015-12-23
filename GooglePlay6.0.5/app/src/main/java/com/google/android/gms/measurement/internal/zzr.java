package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.UUID;

final class zzr
  extends zzw
{
  static final Pair<String, Long> zzbnr = new Pair("", Long.valueOf(0L));
  private SharedPreferences zzRe;
  private final SecureRandom zzbnA = new SecureRandom();
  public final zzb zzbns = new zzb("health_monitor", zzc.getMonitoringSamplePeriodMillis(), (byte)0);
  public final zza zzbnt = new zza("last_upload");
  public final zza zzbnu = new zza("last_upload_attempt");
  public final zza zzbnv = new zza("backoff");
  public final zza zzbnw = new zza("last_delete_stale");
  private String zzbnx;
  private boolean zzbny;
  private long zzbnz;
  
  zzr(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  static String zzCy()
  {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
  
  private SharedPreferences zzCz()
  {
    checkOnWorkerThread();
    zziL();
    return this.zzRe;
  }
  
  private static MessageDigest zzbf(String paramString)
  {
    int i = 0;
    while (i < 2) {
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
        if (localMessageDigest != null) {
          return localMessageDigest;
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        i++;
      }
    }
    return null;
  }
  
  protected final void onInitialize()
  {
    this.zzRe = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
  }
  
  final boolean zzBk()
  {
    checkOnWorkerThread();
    return zzCz().getBoolean("measurement_enabled", true);
  }
  
  final Boolean zzCA()
  {
    checkOnWorkerThread();
    if (!zzCz().contains("use_service")) {
      return null;
    }
    return Boolean.valueOf(zzCz().getBoolean("use_service", false));
  }
  
  final Pair<String, Boolean> zzCw()
  {
    checkOnWorkerThread();
    long l = getClock().elapsedRealtime();
    if ((this.zzbnx != null) && (l < this.zzbnz)) {
      return new Pair(this.zzbnx, Boolean.valueOf(this.zzbny));
    }
    this.zzbnz = (l + zzc.zzBG());
    AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
    try
    {
      AdvertisingIdClient.Info localInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
      this.zzbnx = localInfo.zzpp;
      this.zzbny = localInfo.zzpq;
      AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
      return new Pair(this.zzbnx, Boolean.valueOf(this.zzbny));
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        zzBh().zzbnd.zzm("Unable to get advertising id", localThrowable);
        this.zzbnx = "";
      }
    }
  }
  
  final String zzCx()
  {
    String str = (String)zzCw().first;
    MessageDigest localMessageDigest = zzbf("MD5");
    if (localMessageDigest == null) {
      return null;
    }
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new BigInteger(1, localMessageDigest.digest(str.getBytes()));
    return String.format(localLocale, "%032X", arrayOfObject);
  }
  
  final void zzax(boolean paramBoolean)
  {
    checkOnWorkerThread();
    zzBh().zzbne.zzm("Setting useService", Boolean.valueOf(paramBoolean));
    SharedPreferences.Editor localEditor = zzCz().edit();
    localEditor.putBoolean("use_service", paramBoolean);
    localEditor.apply();
  }
  
  public final class zza
  {
    private long zzaSr;
    private final long zzbnB;
    private boolean zzbnC;
    private final String zzvB;
    
    public zza(String paramString)
    {
      zzx.zzcG(paramString);
      this.zzvB = paramString;
      this.zzbnB = 0L;
    }
    
    public final long get()
    {
      if (!this.zzbnC)
      {
        this.zzbnC = true;
        this.zzaSr = zzr.zza(zzr.this).getLong(this.zzvB, this.zzbnB);
      }
      return this.zzaSr;
    }
    
    public final void set(long paramLong)
    {
      SharedPreferences.Editor localEditor = zzr.zza(zzr.this).edit();
      localEditor.putLong(this.zzvB, paramLong);
      localEditor.apply();
      this.zzaSr = paramLong;
    }
  }
  
  public final class zzb
  {
    final long zzRi;
    final String zzbnE;
    final String zzbnF;
    final String zzbnG;
    
    private zzb(String paramString, long paramLong)
    {
      zzx.zzcG(paramString);
      if (paramLong > 0L) {}
      for (boolean bool = true;; bool = false)
      {
        zzx.zzab(bool);
        this.zzbnE = (paramString + ":start");
        this.zzbnF = (paramString + ":count");
        this.zzbnG = (paramString + ":value");
        this.zzRi = paramLong;
        return;
      }
    }
    
    final long getStartTimeMillis()
    {
      return zzr.zzc(zzr.this).getLong(this.zzbnE, 0L);
    }
    
    public final void zzg(String paramString, long paramLong)
    {
      zzr.this.checkOnWorkerThread();
      if (getStartTimeMillis() == 0L) {
        zzjs();
      }
      if (paramString == null) {
        paramString = "";
      }
      long l = zzr.zza(zzr.this).getLong(this.zzbnF, 0L);
      if (l <= 0L)
      {
        SharedPreferences.Editor localEditor2 = zzr.zza(zzr.this).edit();
        localEditor2.putString(this.zzbnG, paramString);
        localEditor2.putLong(this.zzbnF, paramLong);
        localEditor2.apply();
        return;
      }
      if ((0xFFFFFFFF & zzr.zzb(zzr.this).nextLong()) < paramLong * (9223372036854775807L / (l + paramLong))) {}
      for (int i = 1;; i = 0)
      {
        SharedPreferences.Editor localEditor1 = zzr.zza(zzr.this).edit();
        if (i != 0) {
          localEditor1.putString(this.zzbnG, paramString);
        }
        localEditor1.putLong(this.zzbnF, l + paramLong);
        localEditor1.apply();
        return;
      }
    }
    
    final void zzjs()
    {
      zzr.this.checkOnWorkerThread();
      long l = zzr.this.getClock().currentTimeMillis();
      SharedPreferences.Editor localEditor = zzr.zza(zzr.this).edit();
      localEditor.remove(this.zzbnF);
      localEditor.remove(this.zzbnG);
      localEditor.putLong(this.zzbnE, l);
      localEditor.apply();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzr
 * JD-Core Version:    0.7.0.1
 */