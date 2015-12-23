package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

public final class zzaz
  extends zzbb.zza
{
  private final zzan zzoU;
  private final zzao zzoV;
  private final zzal zzoW;
  private boolean zzoX = false;
  
  public zzaz(String paramString, Context paramContext, boolean paramBoolean)
  {
    this.zzoU = zzan.zza(paramString, paramContext, paramBoolean);
    Flag localFlag1 = Flags.gassEnabled;
    if (((Boolean)zzp.zzbR().zzd(localFlag1)).booleanValue()) {
      zzan.zzT();
    }
    Flag localFlag2 = Flags.gassEnableIntSignal;
    if (((Boolean)zzp.zzbR().zzd(localFlag2)).booleanValue()) {
      zzan.zzU();
    }
    this.zzoV = new zzao(this.zzoU);
    if (paramBoolean) {}
    for (zzal localzzal = null;; localzzal = new zzal(paramContext, new zzah(), new zzat(239)))
    {
      this.zzoW = localzzal;
      return;
    }
  }
  
  private zzd zza(zzd paramzzd1, zzd paramzzd2, boolean paramBoolean)
  {
    try
    {
      Uri localUri1 = (Uri)zze.zzu(paramzzd1);
      Context localContext = (Context)zze.zzu(paramzzd2);
      if (paramBoolean) {}
      Uri localUri2;
      for (Object localObject = this.zzoV.zza(localUri1, localContext, null, false);; localObject = localUri2)
      {
        return zze.zzI(localObject);
        localUri2 = this.zzoV.zzb(localUri1, localContext);
      }
      return null;
    }
    catch (zzap localzzap) {}
  }
  
  public final String getSignalsUrlKey()
  {
    return "ms";
  }
  
  public final void setAdSenseDomainAndPath(String paramString1, String paramString2)
  {
    zzao localzzao = this.zzoV;
    localzzao.zzoe = paramString1;
    localzzao.zzof = paramString2;
  }
  
  public final void setGoogleAdUrlSuffixes(String paramString)
  {
    this.zzoV.zzoh = paramString.split(",");
  }
  
  public final zzd zza(zzd paramzzd1, zzd paramzzd2)
  {
    return zza(paramzzd1, paramzzd2, true);
  }
  
  public final String zza(zzd paramzzd, String paramString)
  {
    Context localContext = (Context)zze.zzu(paramzzd);
    return this.zzoU.zza(localContext, paramString, true);
  }
  
  public final boolean zza(zzd paramzzd)
  {
    Uri localUri = (Uri)zze.zzu(paramzzd);
    return this.zzoV.zza(localUri);
  }
  
  public final zzd zzb(zzd paramzzd1, zzd paramzzd2)
  {
    return zza(paramzzd1, paramzzd2, false);
  }
  
  public final boolean zzb(zzd paramzzd)
  {
    Uri localUri = (Uri)zze.zzu(paramzzd);
    return this.zzoV.isGoogleAdUrl(localUri);
  }
  
  public final boolean zzb(String paramString, boolean paramBoolean)
  {
    if (this.zzoW == null) {
      return false;
    }
    AdvertisingIdClient.Info localInfo = new AdvertisingIdClient.Info(paramString, paramBoolean);
    this.zzoW.zznt = localInfo;
    this.zzoX = true;
    return true;
  }
  
  public final String zzc(zzd paramzzd)
  {
    Context localContext = (Context)zze.zzu(paramzzd);
    String str1 = this.zzoU.zza(localContext, null, false);
    if ((this.zzoW != null) && (this.zzoX))
    {
      String str2 = this.zzoW.zza(localContext, null, false);
      String str3 = this.zzoW.zza(str1, str2);
      this.zzoX = false;
      return str3;
    }
    return str1;
  }
  
  public final void zzd(zzd paramzzd)
  {
    MotionEvent localMotionEvent = (MotionEvent)zze.zzu(paramzzd);
    this.zzoV.addTouchEvent(localMotionEvent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzaz
 * JD-Core Version:    0.7.0.1
 */