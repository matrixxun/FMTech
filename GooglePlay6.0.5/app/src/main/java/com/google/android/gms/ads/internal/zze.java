package com.google.android.gms.ads.internal;

import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zze
{
  private boolean zzpU;
  private boolean zzpV;
  
  public zze()
  {
    Flag localFlag = Flags.zzvR;
    this.zzpV = ((Boolean)zzp.zzbR().zzd(localFlag)).booleanValue();
  }
  
  public zze(byte paramByte)
  {
    this.zzpV = false;
  }
  
  public final boolean zzbs()
  {
    return (!this.zzpV) || (this.zzpU);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.zze
 * JD-Core Version:    0.7.0.1
 */