package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import java.util.Iterator;
import java.util.Set;

public final class zzg
{
  final String mName;
  final long zzalM;
  final String zzbkS;
  final String zzbmq;
  final long zzbmr;
  final EventParams zzbms;
  
  zzg(zzt paramzzt, String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2, EventParams paramEventParams)
  {
    zzx.zzcG(paramString2);
    zzx.zzcG(paramString3);
    zzx.zzC(paramEventParams);
    this.zzbkS = paramString2;
    this.mName = paramString3;
    if (TextUtils.isEmpty(paramString1)) {
      paramString1 = null;
    }
    this.zzbmq = paramString1;
    this.zzalM = paramLong1;
    this.zzbmr = paramLong2;
    if ((this.zzbmr != 0L) && (this.zzbmr > this.zzalM)) {
      paramzzt.zzBh().zzbmZ.zzeB("Event created with reverse previous/current timestamps");
    }
    this.zzbms = paramEventParams;
  }
  
  zzg(zzt paramzzt, String paramString1, String paramString2, String paramString3, long paramLong, Bundle paramBundle)
  {
    zzx.zzcG(paramString2);
    zzx.zzcG(paramString3);
    this.zzbkS = paramString2;
    this.mName = paramString3;
    if (TextUtils.isEmpty(paramString1)) {
      paramString1 = null;
    }
    this.zzbmq = paramString1;
    this.zzalM = paramLong;
    this.zzbmr = 0L;
    if ((this.zzbmr != 0L) && (this.zzbmr > this.zzalM)) {
      paramzzt.zzBh().zzbmZ.zzeB("Event created with reverse previous/current timestamps");
    }
    this.zzbms = zza(paramzzt, paramBundle);
  }
  
  private static EventParams zza(zzt paramzzt, Bundle paramBundle)
  {
    if ((paramBundle != null) && (!paramBundle.isEmpty()))
    {
      Bundle localBundle = new Bundle(paramBundle);
      Iterator localIterator = localBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str == null)
        {
          localIterator.remove();
        }
        else
        {
          paramzzt.zzBX();
          Object localObject1 = localBundle.get(str);
          if ((!TextUtils.isEmpty(str)) && (str.startsWith("_"))) {}
          Object localObject2;
          for (int i = zzc.zzBz();; i = zzc.zzBy())
          {
            localObject2 = zzae.zza(i, localObject1, false);
            if (localObject2 != null) {
              break label132;
            }
            localIterator.remove();
            break;
          }
          label132:
          paramzzt.zzBX().zza(localBundle, str, localObject2);
        }
      }
      return new EventParams(localBundle);
    }
    return new EventParams(new Bundle());
  }
  
  public final String toString()
  {
    return "Event{appId='" + this.zzbkS + '\'' + ", name='" + this.mName + '\'' + ", params=" + this.zzbms + '}';
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzg
 * JD-Core Version:    0.7.0.1
 */