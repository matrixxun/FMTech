package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.overlay.zzn;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.util.zzq;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@zzhb
public class zzjp
  extends WebViewClient
{
  private static final String[] zzMx = { "UNKNOWN", "HOST_LOOKUP", "UNSUPPORTED_AUTH_SCHEME", "AUTHENTICATION", "PROXY_AUTHENTICATION", "CONNECT", "IO", "TIMEOUT", "REDIRECT_LOOP", "UNSUPPORTED_SCHEME", "FAILED_SSL_HANDSHAKE", "BAD_URL", "FILE", "FILE_NOT_FOUND", "TOO_MANY_REQUESTS" };
  private static final String[] zzMy = { "NOT_YET_VALID", "EXPIRED", "ID_MISMATCH", "UNTRUSTED", "DATE_INVALID", "INVALID" };
  private zzft zzCK;
  public zza zzFI;
  zzg zzMA;
  zzb zzMB;
  boolean zzMC = false;
  boolean zzMD;
  zzn zzME;
  private final zzfr zzMF;
  boolean zzMG;
  private boolean zzMH;
  private boolean zzMI;
  private boolean zzMJ;
  private int zzMK;
  final HashMap<String, List<zzdm>> zzMz = new HashMap();
  protected zzjo zzpX;
  final Object zzqp = new Object();
  boolean zzsj;
  com.google.android.gms.ads.internal.client.zza zztS;
  zzdi zzyY;
  public zze zzzB;
  zzfn zzzC;
  zzdo zzzE;
  private zzdq zzzz;
  
  public zzjp(zzjo paramzzjo, boolean paramBoolean)
  {
    this(paramzzjo, paramBoolean, new zzfr(paramzzjo, paramzzjo.zzhG(), new zzca(paramzzjo.getContext())));
  }
  
  private zzjp(zzjo paramzzjo, boolean paramBoolean, zzfr paramzzfr)
  {
    this.zzpX = paramzzjo;
    this.zzsj = paramBoolean;
    this.zzMF = paramzzfr;
    this.zzzC = null;
  }
  
  private void zza(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    Flag localFlag = Flags.zzwX;
    if (!((Boolean)zzp.zzbR().zzd(localFlag)).booleanValue()) {
      return;
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("err", paramString1);
    localBundle.putString("code", paramString2);
    Uri localUri;
    if (!TextUtils.isEmpty(paramString3))
    {
      localUri = Uri.parse(paramString3);
      if (localUri.getHost() == null) {}
    }
    for (String str = localUri.getHost();; str = "")
    {
      localBundle.putString("host", str);
      zzp.zzbI();
      zziq.zza$6b82a487(paramContext, this.zzpX.zzhN().afmaVersion, "gmob-apps", localBundle);
      return;
    }
  }
  
  private void zza(AdOverlayInfoParcel paramAdOverlayInfoParcel)
  {
    if (this.zzzC != null) {}
    for (boolean bool1 = this.zzzC.zzeL();; bool1 = false)
    {
      zzp.zzbG();
      Context localContext = this.zzpX.getContext();
      boolean bool2 = false;
      if (!bool1) {
        bool2 = true;
      }
      if ((paramAdOverlayInfoParcel.zzEg == 4) && (paramAdOverlayInfoParcel.zzDZ == null))
      {
        if (paramAdOverlayInfoParcel.zzDY != null) {}
        zzp.zzbF();
        AdLauncherIntentInfoParcel localAdLauncherIntentInfoParcel = paramAdOverlayInfoParcel.zzDX;
        com.google.android.gms.ads.internal.overlay.zza.zza$1d97657b(localContext, localAdLauncherIntentInfoParcel);
        return;
      }
      Intent localIntent = new Intent();
      localIntent.setClassName(localContext, "com.google.android.gms.ads.AdActivity");
      localIntent.putExtra("com.google.android.gms.ads.internal.overlay.useClientJar", paramAdOverlayInfoParcel.zzrw.zzMa);
      localIntent.putExtra("shouldCallOnOverlayOpened", bool2);
      AdOverlayInfoParcel.zza(localIntent, paramAdOverlayInfoParcel);
      if (!zzq.zzdC(21)) {
        localIntent.addFlags(524288);
      }
      if (!(localContext instanceof Activity)) {
        localIntent.addFlags(268435456);
      }
      zzp.zzbI();
      zziq.zzb(localContext, localIntent);
      return;
    }
  }
  
  private void zza(String paramString, zzdm paramzzdm)
  {
    synchronized (this.zzqp)
    {
      Object localObject3 = (List)this.zzMz.get(paramString);
      if (localObject3 == null)
      {
        localObject3 = new CopyOnWriteArrayList();
        this.zzMz.put(paramString, localObject3);
      }
      ((List)localObject3).add(paramzzdm);
      return;
    }
  }
  
  private void zzh(Uri paramUri)
  {
    String str1 = paramUri.getPath();
    List localList = (List)this.zzMz.get(str1);
    if (localList != null)
    {
      zzp.zzbI();
      Map localMap = zziq.zze(paramUri);
      if (zzb.zze(2))
      {
        zzb.v("Received GMSG: " + str1);
        Iterator localIterator2 = localMap.keySet().iterator();
        while (localIterator2.hasNext())
        {
          String str2 = (String)localIterator2.next();
          zzb.v("  " + str2 + ": " + (String)localMap.get(str2));
        }
      }
      Iterator localIterator1 = localList.iterator();
      while (localIterator1.hasNext()) {
        ((zzdm)localIterator1.next()).zza(this.zzpX, localMap);
      }
    }
    zzb.v("No GMSG handler found for GMSG: " + paramUri);
  }
  
  private void zzic()
  {
    if ((this.zzFI != null) && (((this.zzMI) && (this.zzMK <= 0)) || (this.zzMJ)))
    {
      this.zzFI.zza$7d5558dd(this.zzpX);
      this.zzFI = null;
    }
    this.zzpX.zzhV();
  }
  
  public final void onLoadResource(WebView paramWebView, String paramString)
  {
    zzb.v("Loading resource: " + paramString);
    Uri localUri = Uri.parse(paramString);
    if (("gmsg".equalsIgnoreCase(localUri.getScheme())) && ("mobileads.google.com".equalsIgnoreCase(localUri.getHost()))) {
      zzh(localUri);
    }
  }
  
  public final void onPageFinished(WebView paramWebView, String paramString)
  {
    synchronized (this.zzqp)
    {
      if (this.zzMH)
      {
        zzb.v("Blank page loaded, 1...");
        this.zzpX.zzhP();
        return;
      }
      this.zzMI = true;
      zzic();
      return;
    }
  }
  
  public final void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    if ((paramInt < 0) && (-1 + -paramInt < zzMx.length)) {}
    for (String str = zzMx[(-1 + -paramInt)];; str = String.valueOf(paramInt))
    {
      zza(this.zzpX.getContext(), "http_err", str, paramString2);
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
      return;
    }
  }
  
  public final void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
  {
    int i;
    if (paramSslError != null)
    {
      i = paramSslError.getPrimaryError();
      if ((i < 0) || (i >= zzMy.length)) {
        break label65;
      }
    }
    label65:
    for (String str = zzMy[i];; str = String.valueOf(i))
    {
      zza(this.zzpX.getContext(), "ssl_err", str, zzp.zzbK().zza(paramSslError));
      super.onReceivedSslError(paramWebView, paramSslErrorHandler, paramSslError);
      return;
    }
  }
  
  public boolean shouldOverrideKeyEvent(WebView paramWebView, KeyEvent paramKeyEvent)
  {
    switch (paramKeyEvent.getKeyCode())
    {
    default: 
      return false;
    }
    return true;
  }
  
  public final boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    zzb.v("AdWebView shouldOverrideUrlLoading: " + paramString);
    Object localObject1 = Uri.parse(paramString);
    if (("gmsg".equalsIgnoreCase(((Uri)localObject1).getScheme())) && ("mobileads.google.com".equalsIgnoreCase(((Uri)localObject1).getHost()))) {
      zzh((Uri)localObject1);
    }
    for (;;)
    {
      return true;
      if ((this.zzMC) && (paramWebView == this.zzpX.getWebView()))
      {
        String str = ((Uri)localObject1).getScheme();
        if (("http".equalsIgnoreCase(str)) || ("https".equalsIgnoreCase(str))) {}
        for (int i = 1; i != 0; i = 0)
        {
          if (!this.zzMG)
          {
            this.zzMG = true;
            if (this.zztS != null)
            {
              Flag localFlag = Flags.zzwF;
              ((Boolean)zzp.zzbR().zzd(localFlag)).booleanValue();
            }
          }
          return super.shouldOverrideUrlLoading(paramWebView, paramString);
        }
      }
      if (!this.zzpX.getWebView().willNotDraw())
      {
        try
        {
          zzao localzzao = this.zzpX.zzhM();
          if ((localzzao != null) && (localzzao.isGoogleAdUrl((Uri)localObject1)))
          {
            Uri localUri = localzzao.zzb((Uri)localObject1, this.zzpX.getContext());
            localObject1 = localUri;
          }
          localObject2 = localObject1;
        }
        catch (zzap localzzap)
        {
          for (;;)
          {
            zzb.w("Unable to append parameter to URL: " + paramString);
            Object localObject2 = localObject1;
          }
          zzb.d("Action was blocked because no click was detected.");
        }
        if ((this.zzzB == null) || (this.zzzB.zzbs())) {
          zza(new AdLauncherIntentInfoParcel("android.intent.action.VIEW", localObject2.toString(), null, null, null, null, null));
        }
      }
      else
      {
        zzb.w("AdWebView unable to handle URL: " + paramString);
      }
    }
  }
  
  public final void zza(AdLauncherIntentInfoParcel paramAdLauncherIntentInfoParcel)
  {
    boolean bool = this.zzpX.zzhO();
    com.google.android.gms.ads.internal.client.zza localzza;
    zzg localzzg;
    if ((bool) && (!this.zzpX.zzbb().zzuB))
    {
      localzza = null;
      localzzg = null;
      if (!bool) {
        break label75;
      }
    }
    for (;;)
    {
      zza(new AdOverlayInfoParcel(paramAdLauncherIntentInfoParcel, localzza, localzzg, this.zzME, this.zzpX.zzhN()));
      return;
      localzza = this.zztS;
      break;
      label75:
      localzzg = this.zzMA;
    }
  }
  
  public final void zza(boolean paramBoolean, int paramInt)
  {
    if ((this.zzpX.zzhO()) && (!this.zzpX.zzbb().zzuB)) {}
    for (com.google.android.gms.ads.internal.client.zza localzza = null;; localzza = this.zztS)
    {
      zza(new AdOverlayInfoParcel(localzza, this.zzMA, this.zzME, this.zzpX, paramBoolean, paramInt, this.zzpX.zzhN()));
      return;
    }
  }
  
  public final void zza(boolean paramBoolean, int paramInt, String paramString)
  {
    boolean bool = this.zzpX.zzhO();
    com.google.android.gms.ads.internal.client.zza localzza;
    Object localObject;
    if ((bool) && (!this.zzpX.zzbb().zzuB))
    {
      localzza = null;
      localObject = null;
      if (!bool) {
        break label95;
      }
    }
    for (;;)
    {
      zza(new AdOverlayInfoParcel(localzza, (zzg)localObject, this.zzyY, this.zzME, this.zzpX, paramBoolean, paramInt, paramString, this.zzpX.zzhN(), this.zzzE));
      return;
      localzza = this.zztS;
      break;
      label95:
      localObject = new zzc(this.zzpX, this.zzMA);
    }
  }
  
  public final void zza(boolean paramBoolean, int paramInt, String paramString1, String paramString2)
  {
    boolean bool = this.zzpX.zzhO();
    com.google.android.gms.ads.internal.client.zza localzza;
    if ((bool) && (!this.zzpX.zzbb().zzuB))
    {
      localzza = null;
      if (!bool) {
        break label97;
      }
    }
    label97:
    for (Object localObject = null;; localObject = new zzc(this.zzpX, this.zzMA))
    {
      zza(new AdOverlayInfoParcel(localzza, (zzg)localObject, this.zzyY, this.zzME, this.zzpX, paramBoolean, paramInt, paramString1, paramString2, this.zzpX.zzhN(), this.zzzE));
      return;
      localzza = this.zztS;
      break;
    }
  }
  
  public final void zzb$5cad6de9(zzdi paramzzdi, zzn paramzzn, zzdo paramzzdo, zze paramzze)
  {
    if (paramzze == null) {
      paramzze = new zze((byte)0);
    }
    this.zzzC = new zzfn(this.zzpX, null);
    zza("/appEvent", new zzdh(paramzzdi));
    zza("/backButton", zzdl.zzzi);
    zza("/canOpenURLs", zzdl.zzza);
    zza("/canOpenIntents", zzdl.zzzb);
    zza("/click", zzdl.zzzc);
    zza("/close", zzdl.zzzd);
    zza("/customClose", zzdl.zzze);
    zza("/instrument", zzdl.zzzl);
    zza("/delayPageLoaded", new zzd((byte)0));
    zza("/httpTrack", zzdl.zzzf);
    zza("/log", zzdl.zzzg);
    zza("/mraid", new zzds(paramzze, this.zzzC));
    zza("/mraidLoaded", this.zzMF);
    zza("/open", new zzdt(paramzzdo, paramzze, this.zzzC));
    zza("/precache", zzdl.zzzk);
    zza("/touch", zzdl.zzzh);
    zza("/video", zzdl.zzzj);
    this.zztS = null;
    this.zzMA = null;
    this.zzyY = paramzzdi;
    this.zzzE = paramzzdo;
    this.zzME = paramzzn;
    this.zzzB = paramzze;
    this.zzCK = null;
    this.zzzz = null;
    this.zzMC = true;
    this.zzMG = false;
  }
  
  public final boolean zzcm()
  {
    synchronized (this.zzqp)
    {
      boolean bool = this.zzsj;
      return bool;
    }
  }
  
  public final void zze(zzjo paramzzjo)
  {
    this.zzpX = paramzzjo;
  }
  
  public final void zzhY()
  {
    synchronized (this.zzqp)
    {
      zzb.v("Loading blank page in WebView, 2...");
      this.zzMH = true;
      this.zzpX.zzaF("about:blank");
      return;
    }
  }
  
  public static abstract interface zza
  {
    public abstract void zza$7d5558dd(zzjo paramzzjo);
  }
  
  public static abstract interface zzb {}
  
  private static final class zzc
    implements zzg
  {
    private zzg zzMA;
    private zzjo zzMM;
    
    public zzc(zzjo paramzzjo, zzg paramzzg)
    {
      this.zzMM = paramzzjo;
      this.zzMA = paramzzg;
    }
    
    public final void zzbj()
    {
      this.zzMA.zzbj();
      this.zzMM.zzhE();
    }
    
    public final void zzbk()
    {
      this.zzMA.zzbk();
      this.zzMM.zzfp();
    }
  }
  
  private final class zzd
    implements zzdm
  {
    private zzd() {}
    
    public final void zza(zzjo paramzzjo, Map<String, String> paramMap)
    {
      if (paramMap.keySet().contains("start")) {
        zzjp.zza(zzjp.this);
      }
      do
      {
        return;
        if (paramMap.keySet().contains("stop"))
        {
          zzjp.zzb(zzjp.this);
          return;
        }
      } while (!paramMap.keySet().contains("cancel"));
      zzjp.zzc(zzjp.this);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjp
 * JD-Core Version:    0.7.0.1
 */