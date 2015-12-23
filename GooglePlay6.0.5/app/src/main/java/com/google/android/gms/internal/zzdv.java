package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.ads.internal.zzp;
import java.util.List;

@zzhb
public final class zzdv
  extends zzim
{
  final zzjo zzpX;
  final zzdx zzzJ;
  private final String zzzK;
  
  zzdv(zzjo paramzzjo, zzdx paramzzdx, String paramString)
  {
    this.zzpX = paramzzjo;
    this.zzzJ = paramzzdx;
    this.zzzK = paramString;
    zzp.zzbW().zzzM.add(this);
  }
  
  public final void zzbB()
  {
    zziq.zzLh.post(new Runnable()
    {
      public final void run()
      {
        zzdw localzzdw = zzp.zzbW();
        zzdv localzzdv = zzdv.this;
        localzzdw.zzzM.remove(localzzdv);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzdv
 * JD-Core Version:    0.7.0.1
 */