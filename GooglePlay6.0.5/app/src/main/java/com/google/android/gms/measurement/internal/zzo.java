package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.measurement.AppMeasurement;

public final class zzo
  extends zzw
{
  private final String zzauz = zzc.zzBv();
  private final long zzblY = zzc.zzBH();
  private final char zzbmV;
  public final zza zzbmW;
  private final zza zzbmX;
  private final zza zzbmY;
  public final zza zzbmZ;
  final zza zzbna;
  final zza zzbnb;
  final zza zzbnc;
  final zza zzbnd;
  public final zza zzbne;
  
  zzo(zzt paramzzt)
  {
    super(paramzzt);
    if (super.zzCa().isMainProcess())
    {
      if (zzc.isPackageSide()) {}
      for (char c2 = 'P';; c2 = 'C')
      {
        this.zzbmV = c2;
        this.zzbmW = new zza(6, false, false);
        this.zzbmX = new zza(6, true, false);
        this.zzbmY = new zza(6, false, true);
        this.zzbmZ = new zza(5, false, false);
        this.zzbna = new zza(5, true, false);
        this.zzbnb = new zza(5, false, true);
        this.zzbnc = new zza(4, false, false);
        this.zzbnd = new zza(3, false, false);
        this.zzbne = new zza(2, false, false);
        return;
      }
    }
    if (zzc.isPackageSide()) {}
    for (char c1 = 'p';; c1 = 'c')
    {
      this.zzbmV = c1;
      break;
    }
  }
  
  private static String zza(boolean paramBoolean, String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    if (paramString == null) {
      paramString = "";
    }
    String str1 = zzc(paramBoolean, paramObject1);
    String str2 = zzc(paramBoolean, paramObject2);
    String str3 = zzc(paramBoolean, paramObject3);
    StringBuilder localStringBuilder = new StringBuilder();
    String str4 = "";
    if (!TextUtils.isEmpty(paramString))
    {
      localStringBuilder.append(paramString);
      str4 = ": ";
    }
    if (!TextUtils.isEmpty(str1))
    {
      localStringBuilder.append(str4);
      localStringBuilder.append(str1);
      str4 = ", ";
    }
    if (!TextUtils.isEmpty(str2))
    {
      localStringBuilder.append(str4);
      localStringBuilder.append(str2);
      str4 = ", ";
    }
    if (!TextUtils.isEmpty(str3))
    {
      localStringBuilder.append(str4);
      localStringBuilder.append(str3);
    }
    return localStringBuilder.toString();
  }
  
  private static String zzc(boolean paramBoolean, Object paramObject)
  {
    if (paramObject == null) {
      return "";
    }
    if ((paramObject instanceof Integer)) {}
    for (Object localObject = Long.valueOf(((Integer)paramObject).intValue());; localObject = paramObject)
    {
      if ((localObject instanceof Long))
      {
        if (!paramBoolean) {
          return String.valueOf(localObject);
        }
        if (Math.abs(((Long)localObject).longValue()) < 100L) {
          return String.valueOf(localObject);
        }
        if (String.valueOf(localObject).charAt(0) == '-') {}
        for (String str5 = "-";; str5 = "")
        {
          String str6 = String.valueOf(Math.abs(((Long)localObject).longValue()));
          return str5 + Math.round(Math.pow(10.0D, -1 + str6.length())) + "..." + str5 + Math.round(Math.pow(10.0D, str6.length()) - 1.0D);
        }
      }
      if ((localObject instanceof Boolean)) {
        return String.valueOf(localObject);
      }
      if ((localObject instanceof Throwable))
      {
        Throwable localThrowable = (Throwable)localObject;
        StringBuilder localStringBuilder = new StringBuilder(localThrowable.toString());
        String str1 = zzeA(AppMeasurement.class.getCanonicalName());
        String str2 = zzeA(zzt.class.getCanonicalName());
        StackTraceElement[] arrayOfStackTraceElement = localThrowable.getStackTrace();
        int i = arrayOfStackTraceElement.length;
        for (int j = 0;; j++) {
          if (j < i)
          {
            StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
            if (!localStackTraceElement.isNativeMethod())
            {
              String str3 = localStackTraceElement.getClassName();
              if (str3 != null)
              {
                String str4 = zzeA(str3);
                if ((str4.equals(str1)) || (str4.equals(str2)))
                {
                  localStringBuilder.append(": ");
                  localStringBuilder.append(localStackTraceElement);
                }
              }
            }
          }
          else
          {
            return localStringBuilder.toString();
          }
        }
      }
      if (paramBoolean) {
        return "-";
      }
      return String.valueOf(localObject);
    }
  }
  
  private static String zzeA(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      paramString = "";
    }
    int i;
    do
    {
      return paramString;
      i = paramString.lastIndexOf('.');
    } while (i == -1);
    return paramString.substring(0, i);
  }
  
  protected final void onInitialize() {}
  
  public final String zzCv()
  {
    zzr.zzb localzzb = super.zzBZ().zzbns;
    localzzb.zzbnD.checkOnWorkerThread();
    localzzb.zzbnD.checkOnWorkerThread();
    long l1 = localzzb.getStartTimeMillis();
    long l2;
    Pair localPair;
    if (l1 == 0L)
    {
      localzzb.zzjs();
      l2 = 0L;
      if (l2 >= localzzb.zzRi) {
        break label82;
      }
      localPair = null;
    }
    for (;;)
    {
      if (localPair != null) {
        break label187;
      }
      return null;
      l2 = Math.abs(l1 - localzzb.zzbnD.getClock().currentTimeMillis());
      break;
      label82:
      if (l2 > 2L * localzzb.zzRi)
      {
        localzzb.zzjs();
        localPair = null;
      }
      else
      {
        String str = zzr.zzc(localzzb.zzbnD).getString(localzzb.zzbnG, null);
        long l3 = zzr.zzc(localzzb.zzbnD).getLong(localzzb.zzbnF, 0L);
        localzzb.zzjs();
        if ((str == null) || (l3 <= 0L)) {
          localPair = zzr.zzbnr;
        } else {
          localPair = new Pair(str, Long.valueOf(l3));
        }
      }
    }
    label187:
    return String.valueOf(localPair.second) + ":" + (String)localPair.first;
  }
  
  protected final void zza(int paramInt, boolean paramBoolean1, boolean paramBoolean2, String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    if ((!paramBoolean1) && (Log.isLoggable(this.zzauz, paramInt))) {
      zzo(paramInt, zza(false, paramString, paramObject1, paramObject2, paramObject3));
    }
    zzs localzzs;
    if ((!paramBoolean2) && (paramInt >= 5))
    {
      zzx.zzC(paramString);
      localzzs = this.zzbkM.zzbnZ;
      if ((localzzs == null) || (!localzzs.isInitialized()) || (localzzs.zzPm)) {
        zzo(6, "Scheduler not initialized or shutdown. Not logging error/warn.");
      }
    }
    else
    {
      return;
    }
    if (paramInt < 0) {}
    for (int i = 0;; i = paramInt)
    {
      if (i >= 9) {
        i = 8;
      }
      final String str = "1" + "01VDIWEA?".charAt(i) + this.zzbmV + this.zzblY + ":" + zza(true, paramString, paramObject1, paramObject2, paramObject3);
      if (str.length() > 1024) {
        str = paramString.substring(0, 1024);
      }
      localzzs.zzg(new Runnable()
      {
        public final void run()
        {
          zzr localzzr = zzo.this.zzbkM.zzBZ();
          if ((!localzzr.isInitialized()) || (localzzr.zzPm))
          {
            zzo.this.zzo(6, "Persisted config not initialized . Not logging error/warn.");
            return;
          }
          localzzr.zzbns.zzg(str, 1L);
        }
      });
      return;
    }
  }
  
  protected final void zzo(int paramInt, String paramString)
  {
    Log.println(paramInt, this.zzauz, paramString);
  }
  
  public final class zza
  {
    private final int mPriority;
    private final boolean zzbnh;
    private final boolean zzbni;
    
    zza(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mPriority = paramInt;
      this.zzbnh = paramBoolean1;
      this.zzbni = paramBoolean2;
    }
    
    public final void zzd(String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
    {
      zzo.this.zza(this.mPriority, this.zzbnh, this.zzbni, paramString, paramObject1, paramObject2, paramObject3);
    }
    
    public final void zze(String paramString, Object paramObject1, Object paramObject2)
    {
      zzo.this.zza(this.mPriority, this.zzbnh, this.zzbni, paramString, paramObject1, paramObject2, null);
    }
    
    public final void zzeB(String paramString)
    {
      zzo.this.zza(this.mPriority, this.zzbnh, this.zzbni, paramString, null, null, null);
    }
    
    public final void zzm(String paramString, Object paramObject)
    {
      zzo.this.zza(this.mPriority, this.zzbnh, this.zzbni, paramString, paramObject, null, null);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzo
 * JD-Core Version:    0.7.0.1
 */