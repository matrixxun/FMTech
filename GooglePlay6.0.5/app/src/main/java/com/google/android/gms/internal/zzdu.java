package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.overlay.zzi;
import com.google.android.gms.ads.internal.overlay.zzk;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzx;
import java.util.Map;
import java.util.WeakHashMap;
import org.json.JSONObject;

@zzhb
public final class zzdu
  implements zzdm
{
  private final Map<zzjo, Integer> zzzI = new WeakHashMap();
  
  private static int zza(Context paramContext, Map<String, String> paramMap, String paramString, int paramInt)
  {
    String str = (String)paramMap.get(paramString);
    if (str != null) {}
    try
    {
      zzl.zzcX();
      int i = zza.zzb(paramContext, Integer.parseInt(str));
      paramInt = i;
      return paramInt;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      zzb.w("Could not parse " + paramString + " in a video GMSG: " + str);
    }
    return paramInt;
  }
  
  public final void zza(zzjo paramzzjo, Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("action");
    if (str1 == null) {
      zzb.w("Action missing from video GMSG.");
    }
    for (;;)
    {
      return;
      if (zzb.zze(3))
      {
        JSONObject localJSONObject = new JSONObject(paramMap);
        localJSONObject.remove("google.afma.Notify_dt");
        zzb.d("Video GMSG: " + str1 + " " + localJSONObject.toString());
      }
      if ("background".equals(str1))
      {
        String str4 = (String)paramMap.get("color");
        if (TextUtils.isEmpty(str4))
        {
          zzb.w("Color parameter missing from color video GMSG.");
          return;
        }
        int i3;
        try
        {
          i3 = Color.parseColor(str4);
          zzjn localzzjn2 = paramzzjo.zzhR();
          if (localzzjn2 != null)
          {
            zzk localzzk3 = localzzjn2.zzhD();
            if (localzzk3 != null)
            {
              localzzk3.setBackgroundColor(i3);
              return;
            }
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          zzb.w("Invalid color parameter in video GMSG.");
          return;
        }
        this.zzzI.put(paramzzjo, Integer.valueOf(i3));
        return;
      }
      zzjn localzzjn1 = paramzzjo.zzhR();
      if (localzzjn1 == null)
      {
        zzb.w("Could not get underlay container for a video GMSG.");
        return;
      }
      boolean bool1 = "new".equals(str1);
      boolean bool2 = "position".equals(str1);
      int i;
      int j;
      int k;
      int m;
      if ((bool1) || (bool2))
      {
        Context localContext1 = paramzzjo.getContext();
        i = zza(localContext1, paramMap, "x", 0);
        j = zza(localContext1, paramMap, "y", 0);
        k = zza(localContext1, paramMap, "w", -1);
        m = zza(localContext1, paramMap, "h", -1);
      }
      try
      {
        Integer.parseInt((String)paramMap.get("player"));
        label292:
        if ((bool1) && (localzzjn1.zzhD() == null))
        {
          if (localzzjn1.zzEK == null)
          {
            zzce.zza(localzzjn1.zzpX.zzhT().zzpz, localzzjn1.zzpX.zzhS(), new String[] { "vpr" });
            zzce.zzb(localzzjn1.zzpX.zzhT().zzpz);
            localzzjn1.zzEK = new zzk(localzzjn1.mContext, localzzjn1.zzpX);
            localzzjn1.zzMw.addView(localzzjn1.zzEK, 0, new ViewGroup.LayoutParams(-1, -1));
            localzzjn1.zzEK.zzd(i, j, k, m);
            localzzjn1.zzpX.zzhK().zzMC = false;
          }
          if (!this.zzzI.containsKey(paramzzjo)) {
            continue;
          }
          int n = ((Integer)this.zzzI.get(paramzzjo)).intValue();
          zzk localzzk1 = localzzjn1.zzhD();
          localzzk1.setBackgroundColor(n);
          localzzk1.zzfB();
          return;
        }
        zzx.zzcx("The underlay may only be modified from the UI thread.");
        if (localzzjn1.zzEK == null) {
          continue;
        }
        localzzjn1.zzEK.zzd(i, j, k, m);
        return;
        zzk localzzk2 = localzzjn1.zzhD();
        if (localzzk2 == null)
        {
          zzk.zzd(paramzzjo);
          return;
        }
        if ("click".equals(str1))
        {
          Context localContext2 = paramzzjo.getContext();
          int i1 = zza(localContext2, paramMap, "x", 0);
          int i2 = zza(localContext2, paramMap, "y", 0);
          long l = SystemClock.uptimeMillis();
          MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 0, i1, i2, 0);
          if (localzzk2.zzEm != null) {
            localzzk2.zzEm.dispatchTouchEvent(localMotionEvent);
          }
          localMotionEvent.recycle();
          return;
        }
        if ("currentTime".equals(str1))
        {
          String str3 = (String)paramMap.get("time");
          if (str3 == null)
          {
            zzb.w("Time parameter missing from currentTime video GMSG.");
            return;
          }
          try
          {
            Float.parseFloat(str3);
            return;
          }
          catch (NumberFormatException localNumberFormatException3)
          {
            zzb.w("Could not parse time parameter from currentTime video GMSG: " + str3);
            return;
          }
        }
        if ("hide".equals(str1))
        {
          localzzk2.setVisibility(4);
          return;
        }
        if ("load".equals(str1))
        {
          localzzk2.zzfA();
          return;
        }
        if ("mimetype".equals(str1))
        {
          localzzk2.setMimeType((String)paramMap.get("mimetype"));
          return;
        }
        if ("muted".equals(str1))
        {
          Boolean.parseBoolean((String)paramMap.get("muted"));
          return;
        }
        if (("pause".equals(str1)) || ("play".equals(str1))) {
          continue;
        }
        if ("show".equals(str1))
        {
          localzzk2.setVisibility(0);
          return;
        }
        if ("src".equals(str1))
        {
          localzzk2.zzzK = ((String)paramMap.get("src"));
          return;
        }
        if ("volume".equals(str1))
        {
          String str2 = (String)paramMap.get("volume");
          if (str2 == null)
          {
            zzb.w("Level parameter missing from volume video GMSG.");
            return;
          }
          try
          {
            Float.parseFloat(str2);
            return;
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            zzb.w("Could not parse volume parameter from volume video GMSG: " + str2);
            return;
          }
        }
        if ("watermark".equals(str1))
        {
          localzzk2.zzfB();
          return;
        }
        zzb.w("Unknown video action: " + str1);
        return;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        break label292;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzdu
 * JD-Core Version:    0.7.0.1
 */