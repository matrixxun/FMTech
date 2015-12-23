package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.util.Clock;
import java.util.Map;

@zzhb
public final class zzdk
  implements zzdm
{
  public final void zza(zzjo paramzzjo, Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("action");
    String str5;
    String str6;
    String str7;
    if ("tick".equals(str1))
    {
      str5 = (String)paramMap.get("label");
      str6 = (String)paramMap.get("start_label");
      str7 = (String)paramMap.get("timestamp");
      if (TextUtils.isEmpty(str5)) {
        zzb.w("No label given for CSI tick.");
      }
    }
    label250:
    do
    {
      return;
      if (TextUtils.isEmpty(str7))
      {
        zzb.w("No timestamp given for CSI tick.");
        return;
      }
      for (;;)
      {
        long l4;
        zzci localzzci3;
        zzcg localzzcg;
        String[] arrayOfString;
        zzci localzzci4;
        try
        {
          long l1 = Long.parseLong(str7);
          long l2 = zzp.zzbM().currentTimeMillis();
          long l3 = zzp.zzbM().elapsedRealtime();
          l4 = l3 + (l1 - l2);
          if (TextUtils.isEmpty(str6)) {
            str6 = "native:view_load";
          }
          zzch localzzch = paramzzjo.zzhT();
          localzzci3 = localzzch.zzpz;
          localzzcg = (zzcg)localzzch.zzxA.get(str6);
          arrayOfString = new String[] { str5 };
          if ((localzzci3 == null) || (localzzcg == null))
          {
            Map localMap = localzzch.zzxA;
            localzzci4 = localzzch.zzpz;
            if (localzzci4 != null) {
              break label250;
            }
            localObject = null;
            localMap.put(str5, localObject);
            return;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          zzb.w("Malformed timestamp for CSI tick.", localNumberFormatException);
          return;
        }
        localzzci3.zza(localzzcg, l4, arrayOfString);
        continue;
        Object localObject = localzzci4.zzf(l4);
      }
      if ("experiment".equals(str1))
      {
        String str4 = (String)paramMap.get("value");
        if (TextUtils.isEmpty(str4))
        {
          zzb.w("No value given for CSI experiment.");
          return;
        }
        zzci localzzci2 = paramzzjo.zzhT().zzpz;
        if (localzzci2 == null)
        {
          zzb.w("No ticker for WebView, dropping experiment ID.");
          return;
        }
        localzzci2.zzf("e", str4);
        return;
      }
    } while (!"extra".equals(str1));
    String str2 = (String)paramMap.get("name");
    String str3 = (String)paramMap.get("value");
    if (TextUtils.isEmpty(str3))
    {
      zzb.w("No value given for CSI extra.");
      return;
    }
    if (TextUtils.isEmpty(str2))
    {
      zzb.w("No name given for CSI extra.");
      return;
    }
    zzci localzzci1 = paramzzjo.zzhT().zzpz;
    if (localzzci1 == null)
    {
      zzb.w("No ticker for WebView, dropping extra parameter.");
      return;
    }
    localzzci1.zzf(str2, str3);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzdk
 * JD-Core Version:    0.7.0.1
 */