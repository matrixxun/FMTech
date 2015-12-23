package com.google.android.gms.internal;

import android.app.Activity;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import com.google.android.gms.ads.internal.zzp;

@zzhb
public final class zzjb
{
  Activity zzLR;
  private boolean zzLS;
  boolean zzLT;
  boolean zzLU;
  private ViewTreeObserver.OnGlobalLayoutListener zzLV;
  private ViewTreeObserver.OnScrollChangedListener zzLW;
  
  public zzjb(Activity paramActivity, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    this.zzLR = paramActivity;
    this.zzLV = paramOnGlobalLayoutListener;
    this.zzLW = null;
  }
  
  final void zzhw()
  {
    if (this.zzLR == null) {}
    while (this.zzLS) {
      return;
    }
    if (this.zzLV != null)
    {
      zzp.zzbI();
      zziq.zza(this.zzLR, this.zzLV);
    }
    if (this.zzLW != null)
    {
      zzp.zzbI();
      zziq.zza(this.zzLR, this.zzLW);
    }
    this.zzLS = true;
  }
  
  final void zzhx()
  {
    if (this.zzLR == null) {}
    while (!this.zzLS) {
      return;
    }
    if (this.zzLV != null) {
      zzp.zzbK().zzb(this.zzLR, this.zzLV);
    }
    if (this.zzLW != null)
    {
      zzp.zzbI();
      zziq.zzb(this.zzLR, this.zzLW);
    }
    this.zzLS = false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjb
 * JD-Core Version:    0.7.0.1
 */