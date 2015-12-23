package com.google.android.gms.ads.internal;

import android.os.Build.VERSION;
import com.google.android.gms.ads.internal.config.zzd;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.purchase.zzi;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzcd;
import com.google.android.gms.internal.zzdw;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzet;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zzhk;
import com.google.android.gms.internal.zzih;
import com.google.android.gms.internal.zziq;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zzir.zza;
import com.google.android.gms.internal.zzir.zzb;
import com.google.android.gms.internal.zzir.zzc;
import com.google.android.gms.internal.zzir.zzd;
import com.google.android.gms.internal.zzir.zze;
import com.google.android.gms.internal.zzir.zzf;
import com.google.android.gms.internal.zzir.zzg;
import com.google.android.gms.internal.zziw;
import com.google.android.gms.internal.zzjq;

@zzhb
public final class zzp
{
  private static final Object zzqK = new Object();
  private static zzp zzqZ;
  private final com.google.android.gms.ads.internal.request.zza zzra = new com.google.android.gms.ads.internal.request.zza();
  private final com.google.android.gms.ads.internal.overlay.zza zzrb = new com.google.android.gms.ads.internal.overlay.zza();
  private final com.google.android.gms.ads.internal.overlay.zze zzrc = new com.google.android.gms.ads.internal.overlay.zze();
  private final zzgr zzrd = new zzgr();
  private final zziq zzre = new zziq();
  private final zzjq zzrf = new zzjq();
  private final zzir zzrg;
  private final zzih zzrh;
  private final Clock zzri;
  private final zzcd zzrj;
  private final zzhk zzrk;
  private final com.google.android.gms.ads.internal.config.zze zzrl;
  private final zzd zzrm;
  private final zzf zzrn;
  private final zzi zzro;
  private final zzee zzrp;
  private final zziw zzrq;
  private final zzet zzrr;
  private final zzdw zzrs;
  
  static
  {
    zzp localzzp = new zzp();
    synchronized (zzqK)
    {
      zzqZ = localzzp;
      return;
    }
  }
  
  protected zzp()
  {
    int i = Build.VERSION.SDK_INT;
    Object localObject;
    if (i >= 19) {
      localObject = new zzir.zzg();
    }
    for (;;)
    {
      this.zzrg = ((zzir)localObject);
      this.zzrh = new zzih();
      this.zzri = new zzh();
      this.zzrj = new zzcd();
      this.zzrk = new zzhk();
      this.zzrl = new com.google.android.gms.ads.internal.config.zze();
      this.zzrm = new zzd();
      this.zzrn = new zzf();
      this.zzro = new zzi();
      this.zzrp = new zzee();
      this.zzrq = new zziw();
      this.zzrr = new zzet();
      this.zzrs = new zzdw();
      return;
      if (i >= 18) {
        localObject = new zzir.zze();
      } else if (i >= 17) {
        localObject = new zzir.zzd();
      } else if (i >= 16) {
        localObject = new zzir.zzf();
      } else if (i >= 14) {
        localObject = new zzir.zzc();
      } else if (i >= 11) {
        localObject = new zzir.zzb();
      } else if (i >= 9) {
        localObject = new zzir.zza();
      } else {
        localObject = new zzir();
      }
    }
  }
  
  private static zzp zzbD()
  {
    synchronized (zzqK)
    {
      zzp localzzp = zzqZ;
      return localzzp;
    }
  }
  
  public static com.google.android.gms.ads.internal.overlay.zza zzbF()
  {
    return zzbD().zzrb;
  }
  
  public static com.google.android.gms.ads.internal.overlay.zze zzbG()
  {
    return zzbD().zzrc;
  }
  
  public static zziq zzbI()
  {
    return zzbD().zzre;
  }
  
  public static zzjq zzbJ()
  {
    return zzbD().zzrf;
  }
  
  public static zzir zzbK()
  {
    return zzbD().zzrg;
  }
  
  public static zzih zzbL()
  {
    return zzbD().zzrh;
  }
  
  public static Clock zzbM()
  {
    return zzbD().zzri;
  }
  
  public static com.google.android.gms.ads.internal.config.zze zzbP()
  {
    return zzbD().zzrl;
  }
  
  public static zzd zzbQ()
  {
    return zzbD().zzrm;
  }
  
  public static zzf zzbR()
  {
    return zzbD().zzrn;
  }
  
  public static zzi zzbS()
  {
    return zzbD().zzro;
  }
  
  public static zzdw zzbW()
  {
    return zzbD().zzrs;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.zzp
 * JD-Core Version:    0.7.0.1
 */