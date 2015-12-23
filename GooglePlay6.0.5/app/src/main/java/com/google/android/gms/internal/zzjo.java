package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import java.util.Map;
import org.json.JSONObject;

@zzhb
public abstract interface zzjo
{
  public abstract Context getContext();
  
  public abstract ViewGroup.LayoutParams getLayoutParams();
  
  public abstract void getLocationOnScreen(int[] paramArrayOfInt);
  
  public abstract int getMeasuredHeight();
  
  public abstract int getMeasuredWidth();
  
  public abstract ViewParent getParent();
  
  public abstract View getView();
  
  public abstract WebView getWebView();
  
  public abstract boolean isDestroyed();
  
  public abstract void loadDataWithBaseURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract void loadUrl(String paramString);
  
  public abstract void measure(int paramInt1, int paramInt2);
  
  public abstract void onPause();
  
  public abstract void onResume();
  
  public abstract void setBackgroundColor(int paramInt);
  
  public abstract void setContext(Context paramContext);
  
  public abstract void setOnClickListener(View.OnClickListener paramOnClickListener);
  
  public abstract void setOnTouchListener(View.OnTouchListener paramOnTouchListener);
  
  public abstract void setRequestedOrientation(int paramInt);
  
  public abstract void setWebChromeClient(WebChromeClient paramWebChromeClient);
  
  public abstract void setWebViewClient(WebViewClient paramWebViewClient);
  
  public abstract void zzD(boolean paramBoolean);
  
  public abstract void zzE(boolean paramBoolean);
  
  public abstract void zzF(int paramInt);
  
  public abstract void zzF(boolean paramBoolean);
  
  public abstract void zza(AdSizeParcel paramAdSizeParcel);
  
  public abstract void zzaF(String paramString);
  
  public abstract void zzb(com.google.android.gms.ads.internal.overlay.zzd paramzzd);
  
  public abstract void zzb(String paramString, Map<String, ?> paramMap);
  
  public abstract void zzb(String paramString, JSONObject paramJSONObject);
  
  public abstract AdSizeParcel zzbb();
  
  public abstract void zzc(com.google.android.gms.ads.internal.overlay.zzd paramzzd);
  
  public abstract void zzfp();
  
  public abstract void zzhE();
  
  public abstract Activity zzhF();
  
  public abstract Context zzhG();
  
  public abstract com.google.android.gms.ads.internal.zzd zzhH();
  
  public abstract com.google.android.gms.ads.internal.overlay.zzd zzhI();
  
  public abstract com.google.android.gms.ads.internal.overlay.zzd zzhJ();
  
  public abstract zzjp zzhK();
  
  public abstract boolean zzhL();
  
  public abstract zzao zzhM();
  
  public abstract VersionInfoParcel zzhN();
  
  public abstract boolean zzhO();
  
  public abstract void zzhP();
  
  public abstract boolean zzhQ();
  
  public abstract zzjn zzhR();
  
  public abstract zzcg zzhS();
  
  public abstract zzch zzhT();
  
  public abstract void zzhU();
  
  public abstract void zzhV();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjo
 * JD-Core Version:    0.7.0.1
 */