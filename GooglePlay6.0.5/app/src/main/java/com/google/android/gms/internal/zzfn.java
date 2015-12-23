package com.google.android.gms.internal;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.common.util.zza;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

@zzhb
public final class zzfn
  extends zzfs
{
  static final Set<String> zzCA = Collections.unmodifiableSet(new zza(Arrays.asList(new String[] { "top-left", "top-right", "top-center", "center", "bottom-left", "bottom-right", "bottom-center" })));
  AdSizeParcel zzBH;
  String zzCB = "top-right";
  boolean zzCC = true;
  int zzCD = 0;
  int zzCE = 0;
  int zzCF = 0;
  int zzCG = 0;
  final Activity zzCH;
  ImageView zzCI;
  LinearLayout zzCJ;
  private zzft zzCK;
  PopupWindow zzCL;
  RelativeLayout zzCM;
  ViewGroup zzCN;
  int zzoM = -1;
  int zzoN = -1;
  final zzjo zzpX;
  final Object zzqp = new Object();
  
  public zzfn(zzjo paramzzjo, zzft paramzzft)
  {
    super(paramzzjo, "resize");
    this.zzpX = paramzzjo;
    this.zzCH = paramzzjo.zzhF();
    this.zzCK = null;
  }
  
  public final boolean zzeL()
  {
    for (;;)
    {
      synchronized (this.zzqp)
      {
        if (this.zzCL != null)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public final void zzp(boolean paramBoolean)
  {
    synchronized (this.zzqp)
    {
      if (this.zzCL != null)
      {
        this.zzCL.dismiss();
        this.zzCM.removeView(this.zzpX.getView());
        if (this.zzCN != null)
        {
          this.zzCN.removeView(this.zzCI);
          this.zzCN.addView(this.zzpX.getView());
          this.zzpX.zza(this.zzBH);
        }
        if (paramBoolean) {
          zzan("default");
        }
        this.zzCL = null;
        this.zzCM = null;
        this.zzCN = null;
        this.zzCJ = null;
      }
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzfn
 * JD-Core Version:    0.7.0.1
 */