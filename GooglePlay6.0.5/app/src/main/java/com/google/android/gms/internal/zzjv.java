package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzhb
public final class zzjv
  extends zzjp
{
  public zzjv(zzjo paramzzjo, boolean paramBoolean)
  {
    super(paramzzjo, paramBoolean);
  }
  
  public final WebResourceResponse shouldInterceptRequest(WebView paramWebView, String paramString)
  {
    for (;;)
    {
      try
      {
        if (!"mraid.js".equalsIgnoreCase(new File(paramString).getName())) {
          return super.shouldInterceptRequest(paramWebView, paramString);
        }
        if (!(paramWebView instanceof zzjo))
        {
          zzb.w("Tried to intercept request from a WebView that wasn't an AdWebView.");
          return super.shouldInterceptRequest(paramWebView, paramString);
        }
        localzzjo = (zzjo)paramWebView;
        localzzjp = localzzjo.zzhK();
      }
      catch (IOException localIOException)
      {
        zzjo localzzjo;
        zzjp localzzjp;
        String str3;
        zzb.w("Could not fetch MRAID JS. " + localIOException.getMessage());
        return super.shouldInterceptRequest(paramWebView, paramString);
        if (!localzzjo.zzhO()) {
          continue;
        }
        Flag localFlag2 = Flags.zzwn;
        String str1 = (String)zzp.zzbR().zzd(localFlag2);
        continue;
        Flag localFlag1 = Flags.zzwm;
        str1 = (String)zzp.zzbR().zzd(localFlag1);
        continue;
        WebResourceResponse localWebResourceResponse = new WebResourceResponse("application/javascript", "UTF-8", new ByteArrayInputStream(str3.getBytes("UTF-8")));
        return localWebResourceResponse;
      }
      catch (InterruptedException localInterruptedException)
      {
        continue;
      }
      catch (TimeoutException localTimeoutException)
      {
        continue;
      }
      catch (ExecutionException localExecutionException)
      {
        continue;
      }
      synchronized (localzzjp.zzqp)
      {
        localzzjp.zzMC = false;
        localzzjp.zzsj = true;
        zziq.runOnUiThread(new zzjp.1(localzzjp));
        if (localzzjo.zzbb().zzuB)
        {
          Flag localFlag3 = Flags.zzwo;
          str1 = (String)zzp.zzbR().zzd(localFlag3);
          zzb.v("shouldInterceptRequest(" + str1 + ")");
          Context localContext = localzzjo.getContext();
          String str2 = this.zzpX.zzhN().afmaVersion;
          HashMap localHashMap = new HashMap();
          localHashMap.put("User-Agent", zzp.zzbI().zzd(localContext, str2));
          localHashMap.put("Cache-Control", "max-stale=3600");
          zziv localzziv = new zziv(localContext);
          zziv.zzc localzzc = new zziv.zzc(localzziv, (byte)0);
          zziv.3 local3 = new zziv.3(localzziv, str1, localzzc, new zziv.2(localzziv, str1, localzzc), localHashMap);
          zziv.zzLD.zze(local3);
          str3 = (String)localzzc.get(60L, TimeUnit.SECONDS);
          if (str3 != null) {
            continue;
          }
          return null;
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjv
 * JD-Core Version:    0.7.0.1
 */