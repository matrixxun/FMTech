package com.google.android.gms.ads.internal.overlay;

import android.os.Handler;
import android.widget.FrameLayout;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zziq;

@zzhb
public final class zzq
  implements Runnable
{
  public boolean mCancelled = false;
  private zzk zzEK;
  
  zzq(zzk paramzzk)
  {
    this.zzEK = paramzzk;
  }
  
  public final void run()
  {
    if (!this.mCancelled)
    {
      zzk localzzk = this.zzEK;
      if (localzzk.zzEm != null)
      {
        long l = localzzk.zzEm.getCurrentPosition();
        if ((localzzk.zzEq != l) && (l > 0L))
        {
          if (localzzk.zzfF()) {
            localzzk.zzEk.removeView(localzzk.zzEp);
          }
          float f = (float)l / 1000.0F;
          String[] arrayOfString = new String[2];
          arrayOfString[0] = "time";
          arrayOfString[1] = String.valueOf(f);
          localzzk.zza("timeupdate", arrayOfString);
          localzzk.zzEq = l;
        }
      }
      zzfL();
    }
  }
  
  public final void zzfL()
  {
    zziq.zzLh.postDelayed(this, 250L);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.zzq
 * JD-Core Version:    0.7.0.1
 */