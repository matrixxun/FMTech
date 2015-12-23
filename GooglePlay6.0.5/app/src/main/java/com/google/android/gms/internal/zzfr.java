package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzhb
public final class zzfr
  extends zzfs
  implements zzdm
{
  private final Context mContext;
  int mScreenHeight = -1;
  int mScreenWidth = -1;
  private final zzca zzCZ;
  DisplayMetrics zzDa;
  private float zzDb;
  private int zzDc;
  int zzDd = -1;
  int zzDe = -1;
  int zzDf = -1;
  int zzDg = -1;
  private final zzjo zzpX;
  private final WindowManager zzsw;
  
  public zzfr(zzjo paramzzjo, Context paramContext, zzca paramzzca)
  {
    super(paramzzjo);
    this.zzpX = paramzzjo;
    this.mContext = paramContext;
    this.zzCZ = paramzzca;
    this.zzsw = ((WindowManager)paramContext.getSystemService("window"));
  }
  
  public final void zza(zzjo paramzzjo, Map<String, String> paramMap)
  {
    this.zzDa = new DisplayMetrics();
    Display localDisplay = this.zzsw.getDefaultDisplay();
    localDisplay.getMetrics(this.zzDa);
    this.zzDb = this.zzDa.density;
    this.zzDc = localDisplay.getRotation();
    zzl.zzcX();
    this.mScreenWidth = zza.zzb(this.zzDa, this.zzDa.widthPixels);
    zzl.zzcX();
    this.mScreenHeight = zza.zzb(this.zzDa, this.zzDa.heightPixels);
    Activity localActivity = this.zzpX.zzhF();
    int i;
    int j;
    if ((localActivity == null) || (localActivity.getWindow() == null))
    {
      this.zzDd = this.mScreenWidth;
      this.zzDe = this.mScreenHeight;
      if (!this.zzpX.zzbb().zzuB) {
        break label635;
      }
      this.zzDf = this.mScreenWidth;
      this.zzDg = this.mScreenHeight;
      zza(this.mScreenWidth, this.mScreenHeight, this.zzDd, this.zzDe, this.zzDb, this.zzDc);
      zzfq.zza localzza = new zzfq.zza();
      zzca localzzca1 = this.zzCZ;
      Intent localIntent1 = new Intent("android.intent.action.DIAL");
      localIntent1.setData(Uri.parse("tel:"));
      localzza.zzCV = localzzca1.zza(localIntent1);
      zzca localzzca2 = this.zzCZ;
      Intent localIntent2 = new Intent("android.intent.action.VIEW");
      localIntent2.setData(Uri.parse("sms:"));
      localzza.zzCU = localzzca2.zza(localIntent2);
      localzza.zzCW = this.zzCZ.zzds();
      localzza.zzCX = this.zzCZ.zzdp();
      localzza.zzCY = true;
      zzfq localzzfq = new zzfq(localzza, (byte)0);
      this.zzpX.zzb("onDeviceFeaturesReceived", localzzfq.toJson());
      int[] arrayOfInt1 = new int[2];
      this.zzpX.getLocationOnScreen(arrayOfInt1);
      zzl.zzcX();
      i = zza.zzc(this.mContext, arrayOfInt1[0]);
      zzl.zzcX();
      j = zza.zzc(this.mContext, arrayOfInt1[1]);
      if (!(this.mContext instanceof Activity)) {
        break label721;
      }
    }
    label721:
    for (int k = zzp.zzbI().zzj((Activity)this.mContext)[0];; k = 0) {
      for (;;)
      {
        int m = j - k;
        int n = this.zzDf;
        int i1 = this.zzDg;
        try
        {
          JSONObject localJSONObject2 = new JSONObject().put("x", i).put("y", m).put("width", n).put("height", i1);
          this.zzpX.zzb("onDefaultPositionReceived", localJSONObject2);
          zzjp localzzjp = this.zzpX.zzhK();
          if (localzzjp.zzzC != null)
          {
            zzfn localzzfn = localzzjp.zzzC;
            localzzfn.zzCD = i;
            localzzfn.zzCE = j;
          }
          if (zzb.zze(2)) {
            zzb.i("Dispatching Ready Event.");
          }
          String str = this.zzpX.zzhN().afmaVersion;
          try
          {
            JSONObject localJSONObject1 = new JSONObject().put("js", str);
            this.zzpX.zzb("onReadyEventReceived", localJSONObject1);
            return;
          }
          catch (JSONException localJSONException2)
          {
            int[] arrayOfInt2;
            zzb.e("Error occured while dispatching ready Event.", localJSONException2);
            return;
          }
          zzp.zzbI();
          arrayOfInt2 = zziq.zzg(localActivity);
          zzl.zzcX();
          this.zzDd = zza.zzb(this.zzDa, arrayOfInt2[0]);
          zzl.zzcX();
          this.zzDe = zza.zzb(this.zzDa, arrayOfInt2[1]);
          break;
          label635:
          this.zzpX.measure(0, 0);
          zzl.zzcX();
          this.zzDf = zza.zzc(this.mContext, this.zzpX.getMeasuredWidth());
          zzl.zzcX();
          this.zzDg = zza.zzc(this.mContext, this.zzpX.getMeasuredHeight());
        }
        catch (JSONException localJSONException1)
        {
          for (;;)
          {
            zzb.e("Error occured while dispatching default position.", localJSONException1);
          }
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzfr
 * JD-Core Version:    0.7.0.1
 */