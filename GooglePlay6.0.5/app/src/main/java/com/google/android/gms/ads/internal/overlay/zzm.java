package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zzm
  extends FrameLayout
  implements View.OnClickListener
{
  private final ImageButton zzEs;
  private final zzo zzEt;
  
  public zzm(Context paramContext, int paramInt, zzo paramzzo)
  {
    super(paramContext);
    this.zzEt = paramzzo;
    setOnClickListener(this);
    this.zzEs = new ImageButton(paramContext);
    this.zzEs.setImageResource(17301527);
    this.zzEs.setBackgroundColor(0);
    this.zzEs.setOnClickListener(this);
    this.zzEs.setPadding(0, 0, 0, 0);
    this.zzEs.setContentDescription("Interstitial close button");
    zzl.zzcX();
    int i = zza.zzb(paramContext, paramInt);
    addView(this.zzEs, new FrameLayout.LayoutParams(i, i, 17));
  }
  
  public final void onClick(View paramView)
  {
    if (this.zzEt != null) {
      this.zzEt.zzfk();
    }
  }
  
  public final void zza(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2)
    {
      if (paramBoolean1)
      {
        this.zzEs.setVisibility(4);
        return;
      }
      this.zzEs.setVisibility(8);
      return;
    }
    this.zzEs.setVisibility(0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.zzm
 * JD-Core Version:    0.7.0.1
 */