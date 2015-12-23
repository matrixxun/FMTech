package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.overlay.zzk;
import com.google.android.gms.ads.internal.overlay.zzq;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import java.util.Map;
import org.json.JSONObject;

@zzhb
public final class zzjr
  extends FrameLayout
  implements zzjo
{
  private final zzjo zzMN;
  private final zzjn zzMO;
  
  public zzjr(zzjo paramzzjo)
  {
    super(paramzzjo.getContext());
    this.zzMN = paramzzjo;
    this.zzMO = new zzjn(paramzzjo.zzhG(), this, this);
    zzjp localzzjp = this.zzMN.zzhK();
    if (localzzjp != null) {
      localzzjp.zze(this);
    }
    addView(this.zzMN.getView());
  }
  
  public final View getView()
  {
    return this;
  }
  
  public final WebView getWebView()
  {
    return this.zzMN.getWebView();
  }
  
  public final boolean isDestroyed()
  {
    return this.zzMN.isDestroyed();
  }
  
  public final void loadDataWithBaseURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.zzMN.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public final void loadUrl(String paramString)
  {
    this.zzMN.loadUrl(paramString);
  }
  
  public final void onPause()
  {
    zzx.zzcx("onPause must be called from the UI thread.");
    this.zzMN.onPause();
  }
  
  public final void onResume()
  {
    this.zzMN.onResume();
  }
  
  public final void setBackgroundColor(int paramInt)
  {
    this.zzMN.setBackgroundColor(paramInt);
  }
  
  public final void setContext(Context paramContext)
  {
    this.zzMN.setContext(paramContext);
  }
  
  public final void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.zzMN.setOnClickListener(paramOnClickListener);
  }
  
  public final void setOnTouchListener(View.OnTouchListener paramOnTouchListener)
  {
    this.zzMN.setOnTouchListener(paramOnTouchListener);
  }
  
  public final void setRequestedOrientation(int paramInt)
  {
    this.zzMN.setRequestedOrientation(paramInt);
  }
  
  public final void setWebChromeClient(WebChromeClient paramWebChromeClient)
  {
    this.zzMN.setWebChromeClient(paramWebChromeClient);
  }
  
  public final void setWebViewClient(WebViewClient paramWebViewClient)
  {
    this.zzMN.setWebViewClient(paramWebViewClient);
  }
  
  public final void zzD(boolean paramBoolean)
  {
    this.zzMN.zzD(paramBoolean);
  }
  
  public final void zzE(boolean paramBoolean)
  {
    this.zzMN.zzE(paramBoolean);
  }
  
  public final void zzF(int paramInt)
  {
    this.zzMN.zzF(paramInt);
  }
  
  public final void zzF(boolean paramBoolean)
  {
    this.zzMN.zzF(paramBoolean);
  }
  
  public final void zza(AdSizeParcel paramAdSizeParcel)
  {
    this.zzMN.zza(paramAdSizeParcel);
  }
  
  public final void zzaF(String paramString)
  {
    this.zzMN.zzaF(paramString);
  }
  
  public final void zzb(com.google.android.gms.ads.internal.overlay.zzd paramzzd)
  {
    this.zzMN.zzb(paramzzd);
  }
  
  public final void zzb(String paramString, Map<String, ?> paramMap)
  {
    this.zzMN.zzb(paramString, paramMap);
  }
  
  public final void zzb(String paramString, JSONObject paramJSONObject)
  {
    this.zzMN.zzb(paramString, paramJSONObject);
  }
  
  public final AdSizeParcel zzbb()
  {
    return this.zzMN.zzbb();
  }
  
  public final void zzc(com.google.android.gms.ads.internal.overlay.zzd paramzzd)
  {
    this.zzMN.zzc(paramzzd);
  }
  
  public final void zzfp()
  {
    this.zzMN.zzfp();
  }
  
  public final void zzhE()
  {
    this.zzMN.zzhE();
  }
  
  public final Activity zzhF()
  {
    return this.zzMN.zzhF();
  }
  
  public final Context zzhG()
  {
    return this.zzMN.zzhG();
  }
  
  public final com.google.android.gms.ads.internal.zzd zzhH()
  {
    return this.zzMN.zzhH();
  }
  
  public final com.google.android.gms.ads.internal.overlay.zzd zzhI()
  {
    return this.zzMN.zzhI();
  }
  
  public final com.google.android.gms.ads.internal.overlay.zzd zzhJ()
  {
    return this.zzMN.zzhJ();
  }
  
  public final zzjp zzhK()
  {
    return this.zzMN.zzhK();
  }
  
  public final boolean zzhL()
  {
    return this.zzMN.zzhL();
  }
  
  public final zzao zzhM()
  {
    return this.zzMN.zzhM();
  }
  
  public final VersionInfoParcel zzhN()
  {
    return this.zzMN.zzhN();
  }
  
  public final boolean zzhO()
  {
    return this.zzMN.zzhO();
  }
  
  public final void zzhP()
  {
    zzjn localzzjn = this.zzMO;
    zzx.zzcx("onDestroy must be called from the UI thread.");
    if (localzzjn.zzEK != null)
    {
      zzk localzzk = localzzjn.zzEK;
      zzq localzzq = localzzk.zzEl;
      localzzq.mCancelled = true;
      zziq.zzLh.removeCallbacks(localzzq);
      if ((localzzk.zzpX.zzhF() != null) && (localzzk.zzEn) && (!localzzk.zzEo))
      {
        localzzk.zzpX.zzhF().getWindow().clearFlags(128);
        localzzk.zzEn = false;
      }
    }
    this.zzMN.zzhP();
  }
  
  public final boolean zzhQ()
  {
    return this.zzMN.zzhQ();
  }
  
  public final zzjn zzhR()
  {
    return this.zzMO;
  }
  
  public final zzcg zzhS()
  {
    return this.zzMN.zzhS();
  }
  
  public final zzch zzhT()
  {
    return this.zzMN.zzhT();
  }
  
  public final void zzhU()
  {
    this.zzMN.zzhU();
  }
  
  public final void zzhV()
  {
    this.zzMN.zzhV();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjr
 * JD-Core Version:    0.7.0.1
 */