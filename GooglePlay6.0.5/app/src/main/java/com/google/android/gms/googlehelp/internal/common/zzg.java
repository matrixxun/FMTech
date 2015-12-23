package com.google.android.gms.googlehelp.internal.common;

import android.app.Activity;
import android.util.Log;
import com.google.android.gms.googlehelp.GoogleHelpLauncher;
import com.google.android.gms.googlehelp.GoogleHelpLauncher.2;
import com.google.android.gms.googlehelp.GoogleHelpLauncher.OnToggleFailedListener;
import com.google.android.gms.googlehelp.zzc;

public class zzg
  extends SimpleGoogleHelpCallbacks
{
  private final Activity zzbbq;
  private boolean zzbbr;
  
  public zzg(Activity paramActivity)
  {
    this.zzbbq = paramActivity;
  }
  
  public final void onPipClick()
  {
    if (this.zzbbr)
    {
      Log.d("gH_OnPipClickListener", "Double click gets discarded.");
      return;
    }
    this.zzbbr = true;
    GoogleHelpLauncher localGoogleHelpLauncher = new GoogleHelpLauncher(this.zzbbq);
    GoogleHelpLauncher.OnToggleFailedListener local1 = new GoogleHelpLauncher.OnToggleFailedListener()
    {
      public final void onToggleFailed()
      {
        Log.w("gH_OnPipClickListener", "Help toggling failed. Reset mIsToggling flag back to false.");
        zzg.zza$dfc16a(zzg.this);
      }
    };
    zzc.zza(localGoogleHelpLauncher.mApiClient, new GoogleHelpLauncher.2(localGoogleHelpLauncher, local1));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.zzg
 * JD-Core Version:    0.7.0.1
 */