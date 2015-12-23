package com.google.android.gms.internal;

import android.content.Context;
import android.view.ViewGroup;
import com.google.android.gms.ads.internal.overlay.zzk;
import com.google.android.gms.common.internal.zzx;

@zzhb
public final class zzjn
{
  final Context mContext;
  zzk zzEK;
  final ViewGroup zzMw;
  final zzjo zzpX;
  
  public zzjn(Context paramContext, ViewGroup paramViewGroup, zzjo paramzzjo)
  {
    this(paramContext, paramViewGroup, paramzzjo, (byte)0);
  }
  
  private zzjn(Context paramContext, ViewGroup paramViewGroup, zzjo paramzzjo, byte paramByte)
  {
    this.mContext = paramContext;
    this.zzMw = paramViewGroup;
    this.zzpX = paramzzjo;
    this.zzEK = null;
  }
  
  public final zzk zzhD()
  {
    zzx.zzcx("getAdVideoUnderlay must be called from the UI thread.");
    return this.zzEK;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjn
 * JD-Core Version:    0.7.0.1
 */