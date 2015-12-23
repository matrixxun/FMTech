package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.util.zzq;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import org.json.JSONException;
import org.json.JSONObject;

@zzhb
public final class zzjs
  extends WebView
  implements ViewTreeObserver.OnGlobalLayoutListener, DownloadListener, zzjo
{
  private int mScreenHeight = -1;
  private int mScreenWidth = -1;
  private AdSizeParcel zzBH;
  private int zzDd = -1;
  private int zzDe = -1;
  private String zzEu = "";
  private Boolean zzKA;
  private final zza zzMP;
  private zzjp zzMQ;
  private com.google.android.gms.ads.internal.overlay.zzd zzMR;
  private boolean zzMS;
  private boolean zzMT;
  private boolean zzMU;
  private boolean zzMV;
  private int zzMW;
  private boolean zzMX = true;
  private zzcg zzMY;
  private zzcg zzMZ;
  private zzcg zzNa;
  private zzch zzNb;
  private com.google.android.gms.ads.internal.overlay.zzd zzNc;
  private Map<String, zzdx> zzNd;
  private final com.google.android.gms.ads.internal.zzd zzpH;
  private final VersionInfoParcel zzqn;
  private final Object zzqp = new Object();
  private zzjb zzse;
  private final WindowManager zzsw;
  private final zzao zzyv;
  
  private zzjs(zza paramzza, AdSizeParcel paramAdSizeParcel, boolean paramBoolean, zzao paramzzao, VersionInfoParcel paramVersionInfoParcel, zzci paramzzci, com.google.android.gms.ads.internal.zzd paramzzd)
  {
    super(paramzza);
    this.zzMP = paramzza;
    this.zzBH = paramAdSizeParcel;
    this.zzMU = paramBoolean;
    this.zzMW = -1;
    this.zzyv = paramzzao;
    this.zzqn = paramVersionInfoParcel;
    this.zzpH = paramzzd;
    this.zzsw = ((WindowManager)getContext().getSystemService("window"));
    setBackgroundColor(0);
    WebSettings localWebSettings = getSettings();
    localWebSettings.setAllowFileAccess(false);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setSavePassword(false);
    localWebSettings.setSupportMultipleWindows(true);
    localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    if (Build.VERSION.SDK_INT >= 21) {
      localWebSettings.setMixedContentMode(0);
    }
    localWebSettings.setUserAgentString(zzp.zzbI().zzd(paramzza, paramVersionInfoParcel.afmaVersion));
    zzp.zzbK().zza(getContext(), localWebSettings);
    setDownloadListener(this);
    zzig();
    if (zzq.zzdC(17)) {
      addJavascriptInterface(new zzjt(this), "googleAdsJsInterface");
    }
    this.zzse = new zzjb(this.zzMP.zzLR, this);
    zzik();
    this.zzNb = new zzch(new zzci("make_wv", this.zzBH.zzuA));
    zzci localzzci = this.zzNb.zzpz;
    synchronized (localzzci.zzqp)
    {
      localzzci.zzxF = paramzzci;
      this.zzMZ = zzce.zzb(this.zzNb.zzpz);
      this.zzNb.zza("native:view_create", this.zzMZ);
      this.zzNa = null;
      this.zzMY = null;
      return;
    }
  }
  
  private void zzaI(String paramString)
  {
    synchronized (this.zzqp)
    {
      if (!isDestroyed())
      {
        loadUrl(paramString);
        return;
      }
      zzb.w("The webview is destroyed. Ignoring action.");
    }
  }
  
  private void zzb(Boolean paramBoolean)
  {
    this.zzKA = paramBoolean;
    zzih localzzih = zzp.zzbL();
    synchronized (localzzih.zzqp)
    {
      localzzih.zzKA = paramBoolean;
      return;
    }
  }
  
  public static zzjs zzb$631f0cde(Context paramContext, AdSizeParcel paramAdSizeParcel, boolean paramBoolean, zzao paramzzao, VersionInfoParcel paramVersionInfoParcel, zzci paramzzci, com.google.android.gms.ads.internal.zzd paramzzd)
  {
    return new zzjs(new zza(paramContext), paramAdSizeParcel, true, null, paramVersionInfoParcel, null, null);
  }
  
  private Boolean zzgY()
  {
    synchronized (this.zzqp)
    {
      Boolean localBoolean = this.zzKA;
      return localBoolean;
    }
  }
  
  private boolean zzid()
  {
    if (!this.zzMQ.zzcm()) {
      return false;
    }
    zzp.zzbI();
    DisplayMetrics localDisplayMetrics = zziq.zza(this.zzsw);
    zzl.zzcX();
    int i = zza.zzb(localDisplayMetrics, localDisplayMetrics.widthPixels);
    zzl.zzcX();
    int j = zza.zzb(localDisplayMetrics, localDisplayMetrics.heightPixels);
    Activity localActivity = this.zzMP.zzLR;
    int k;
    int m;
    if ((localActivity == null) || (localActivity.getWindow() == null))
    {
      k = j;
      m = i;
      label82:
      if ((this.mScreenWidth == i) && (this.mScreenHeight == j) && (this.zzDd == m) && (this.zzDe == k)) {
        break label241;
      }
      if ((this.mScreenWidth == i) && (this.mScreenHeight == j)) {
        break label243;
      }
    }
    label241:
    label243:
    for (boolean bool = true;; bool = false)
    {
      this.mScreenWidth = i;
      this.mScreenHeight = j;
      this.zzDd = m;
      this.zzDe = k;
      new zzfs(this).zza(i, j, m, k, localDisplayMetrics.density, this.zzsw.getDefaultDisplay().getRotation());
      return bool;
      zzp.zzbI();
      int[] arrayOfInt = zziq.zzg(localActivity);
      zzl.zzcX();
      m = zza.zzb(localDisplayMetrics, arrayOfInt[0]);
      zzl.zzcX();
      k = zza.zzb(localDisplayMetrics, arrayOfInt[1]);
      break label82;
      break;
    }
  }
  
  private void zzif()
  {
    zzce.zza(this.zzNb.zzpz, this.zzMY, new String[] { "aeh" });
  }
  
  private void zzig()
  {
    for (;;)
    {
      synchronized (this.zzqp)
      {
        if ((this.zzMU) || (this.zzBH.zzuB))
        {
          if (Build.VERSION.SDK_INT < 14)
          {
            zzb.d("Disabling hardware acceleration on an overlay.");
            zzih();
            return;
          }
          zzb.d("Enabling hardware acceleration on an overlay.");
          zzii();
        }
      }
      if (Build.VERSION.SDK_INT < 18)
      {
        zzb.d("Disabling hardware acceleration on an AdView.");
        zzih();
      }
      else
      {
        zzb.d("Enabling hardware acceleration on an AdView.");
        zzii();
      }
    }
  }
  
  private void zzih()
  {
    synchronized (this.zzqp)
    {
      if (!this.zzMV) {
        zzp.zzbK().zzn(this);
      }
      this.zzMV = true;
      return;
    }
  }
  
  private void zzii()
  {
    synchronized (this.zzqp)
    {
      if (this.zzMV) {
        zzp.zzbK().zzm(this);
      }
      this.zzMV = false;
      return;
    }
  }
  
  private void zzik()
  {
    if (this.zzNb == null) {}
    zzci localzzci;
    do
    {
      return;
      localzzci = this.zzNb.zzpz;
    } while ((localzzci == null) || (zzp.zzbL().zzgU() == null));
    zzp.zzbL().zzgU().zzxo.offer(localzzci);
  }
  
  public final void destroy()
  {
    synchronized (this.zzqp)
    {
      zzik();
      zzjb localzzjb = this.zzse;
      localzzjb.zzLU = false;
      localzzjb.zzhx();
      if (this.zzMR != null)
      {
        this.zzMR.close();
        this.zzMR.onDestroy();
        this.zzMR = null;
      }
      zzjp localzzjp = this.zzMQ;
      synchronized (localzzjp.zzqp)
      {
        localzzjp.zzMz.clear();
        localzzjp.zztS = null;
        localzzjp.zzMA = null;
        localzzjp.zzFI = null;
        localzzjp.zzyY = null;
        localzzjp.zzMC = false;
        localzzjp.zzsj = false;
        localzzjp.zzMD = false;
        localzzjp.zzzE = null;
        localzzjp.zzME = null;
        localzzjp.zzMB = null;
        if (localzzjp.zzzC != null)
        {
          localzzjp.zzzC.zzp(true);
          localzzjp.zzzC = null;
        }
        localzzjp.zzMG = false;
        if (this.zzMT) {
          return;
        }
      }
    }
    zzp.zzbW();
    zzdw.zza(this);
    synchronized (this.zzqp)
    {
      if (this.zzNd != null)
      {
        Iterator localIterator = this.zzNd.values().iterator();
        while (localIterator.hasNext()) {
          ((zzdx)localIterator.next());
        }
      }
      this.zzMT = true;
      zzb.v("Initiating WebView self destruct sequence in 3...");
      this.zzMQ.zzhY();
      return;
    }
  }
  
  public final void evaluateJavascript(String paramString, ValueCallback<String> paramValueCallback)
  {
    synchronized (this.zzqp)
    {
      if (isDestroyed())
      {
        zzb.w("The webview is destroyed. Ignoring action.");
        if (paramValueCallback != null) {
          paramValueCallback.onReceiveValue(null);
        }
        return;
      }
      super.evaluateJavascript(paramString, paramValueCallback);
      return;
    }
  }
  
  public final View getView()
  {
    return this;
  }
  
  public final WebView getWebView()
  {
    return this;
  }
  
  public final boolean isDestroyed()
  {
    synchronized (this.zzqp)
    {
      boolean bool = this.zzMT;
      return bool;
    }
  }
  
  public final void loadData(String paramString1, String paramString2, String paramString3)
  {
    synchronized (this.zzqp)
    {
      if (!isDestroyed())
      {
        super.loadData(paramString1, paramString2, paramString3);
        return;
      }
      zzb.w("The webview is destroyed. Ignoring action.");
    }
  }
  
  public final void loadDataWithBaseURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    synchronized (this.zzqp)
    {
      if (!isDestroyed())
      {
        super.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
        return;
      }
      zzb.w("The webview is destroyed. Ignoring action.");
    }
  }
  
  public final void loadUrl(String paramString)
  {
    for (;;)
    {
      synchronized (this.zzqp)
      {
        boolean bool = isDestroyed();
        if (!bool) {
          try
          {
            super.loadUrl(paramString);
            return;
          }
          catch (Throwable localThrowable)
          {
            zzb.w("Could not call loadUrl. " + localThrowable);
            continue;
          }
        }
      }
      zzb.w("The webview is destroyed. Ignoring action.");
    }
  }
  
  protected final void onAttachedToWindow()
  {
    synchronized (this.zzqp)
    {
      super.onAttachedToWindow();
      if (!isDestroyed())
      {
        zzjb localzzjb = this.zzse;
        localzzjb.zzLT = true;
        if (localzzjb.zzLU) {
          localzzjb.zzhw();
        }
      }
      return;
    }
  }
  
  protected final void onDetachedFromWindow()
  {
    synchronized (this.zzqp)
    {
      if (!isDestroyed())
      {
        zzjb localzzjb = this.zzse;
        localzzjb.zzLT = false;
        localzzjb.zzhx();
      }
      super.onDetachedFromWindow();
      return;
    }
  }
  
  public final void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
  {
    try
    {
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.setDataAndType(Uri.parse(paramString1), paramString4);
      zzp.zzbI();
      zziq.zzb(getContext(), localIntent);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      zzb.d("Couldn't find an Activity to view url/mimetype: " + paramString1 + " / " + paramString4);
    }
  }
  
  protected final void onDraw(Canvas paramCanvas)
  {
    if (isDestroyed()) {}
    while ((Build.VERSION.SDK_INT == 21) && (paramCanvas.isHardwareAccelerated()) && (!isAttachedToWindow())) {
      return;
    }
    super.onDraw(paramCanvas);
  }
  
  public final void onGlobalLayout()
  {
    boolean bool = zzid();
    com.google.android.gms.ads.internal.overlay.zzd localzzd = zzhI();
    if ((localzzd != null) && (bool) && (localzzd.zzDN))
    {
      localzzd.zzDN = false;
      localzzd.zzfp();
    }
  }
  
  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 2147483647;
    synchronized (this.zzqp)
    {
      if (isDestroyed())
      {
        setMeasuredDimension(0, 0);
        return;
      }
      if ((isInEditMode()) || (this.zzMU) || (this.zzBH.zzuD) || (this.zzBH.zzuE))
      {
        super.onMeasure(paramInt1, paramInt2);
        return;
      }
    }
    if (this.zzBH.zzuB)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      this.zzsw.getDefaultDisplay().getMetrics(localDisplayMetrics);
      setMeasuredDimension(localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels);
      return;
    }
    int j = View.MeasureSpec.getMode(paramInt1);
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getMode(paramInt2);
    int n = View.MeasureSpec.getSize(paramInt2);
    if (j != -2147483648) {
      if (j == 1073741824) {
        break label369;
      }
    }
    for (;;)
    {
      if ((this.zzBH.widthPixels > i1) || (this.zzBH.heightPixels > i))
      {
        float f = this.zzMP.getResources().getDisplayMetrics().density;
        zzb.w("Not enough space to show ad. Needs " + (int)(this.zzBH.widthPixels / f) + "x" + (int)(this.zzBH.heightPixels / f) + " dp, but only has " + (int)(k / f) + "x" + (int)(n / f) + " dp.");
        if (getVisibility() != 8) {
          setVisibility(4);
        }
        setMeasuredDimension(0, 0);
      }
      for (;;)
      {
        return;
        if (getVisibility() != 8) {
          setVisibility(0);
        }
        setMeasuredDimension(this.zzBH.widthPixels, this.zzBH.heightPixels);
      }
      int i1 = i;
      break label373;
      label369:
      i1 = k;
      label373:
      if ((m == -2147483648) || (m == 1073741824)) {
        i = n;
      }
    }
  }
  
  public final void onPause()
  {
    if (isDestroyed()) {}
    for (;;)
    {
      return;
      try
      {
        if (zzq.zzdC(11))
        {
          super.onPause();
          return;
        }
      }
      catch (Exception localException)
      {
        zzb.e("Could not pause webview.", localException);
      }
    }
  }
  
  public final void onResume()
  {
    if (isDestroyed()) {}
    for (;;)
    {
      return;
      try
      {
        if (zzq.zzdC(11))
        {
          super.onResume();
          return;
        }
      }
      catch (Exception localException)
      {
        zzb.e("Could not resume webview.", localException);
      }
    }
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.zzyv != null) {
      this.zzyv.addTouchEvent(paramMotionEvent);
    }
    if (isDestroyed()) {
      return false;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public final void setContext(Context paramContext)
  {
    this.zzMP.setBaseContext(paramContext);
    this.zzse.zzLR = this.zzMP.zzLR;
  }
  
  public final void setRequestedOrientation(int paramInt)
  {
    synchronized (this.zzqp)
    {
      this.zzMW = paramInt;
      if (this.zzMR != null) {
        this.zzMR.setRequestedOrientation(this.zzMW);
      }
      return;
    }
  }
  
  public final void setWebViewClient(WebViewClient paramWebViewClient)
  {
    super.setWebViewClient(paramWebViewClient);
    if ((paramWebViewClient instanceof zzjp)) {
      this.zzMQ = ((zzjp)paramWebViewClient);
    }
  }
  
  public final void stopLoading()
  {
    if (isDestroyed()) {
      return;
    }
    try
    {
      super.stopLoading();
      return;
    }
    catch (Exception localException)
    {
      zzb.e("Could not stop loading webview.", localException);
    }
  }
  
  public final void zzD(boolean paramBoolean)
  {
    synchronized (this.zzqp)
    {
      this.zzMU = paramBoolean;
      zzig();
      return;
    }
  }
  
  public final void zzE(boolean paramBoolean)
  {
    synchronized (this.zzqp)
    {
      if (this.zzMR != null)
      {
        this.zzMR.zza(this.zzMQ.zzcm(), paramBoolean);
        return;
      }
      this.zzMS = paramBoolean;
    }
  }
  
  public final void zzF(int paramInt)
  {
    zzif();
    HashMap localHashMap = new HashMap(2);
    localHashMap.put("closetype", String.valueOf(paramInt));
    localHashMap.put("version", this.zzqn.afmaVersion);
    zzb("onhide", localHashMap);
  }
  
  public final void zzF(boolean paramBoolean)
  {
    synchronized (this.zzqp)
    {
      this.zzMX = paramBoolean;
      return;
    }
  }
  
  public final void zza(AdSizeParcel paramAdSizeParcel)
  {
    synchronized (this.zzqp)
    {
      this.zzBH = paramAdSizeParcel;
      requestLayout();
      return;
    }
  }
  
  public final void zzaF(String paramString)
  {
    synchronized (this.zzqp)
    {
      try
      {
        super.loadUrl(paramString);
        return;
      }
      catch (Throwable localThrowable)
      {
        for (;;)
        {
          zzb.w("Could not call loadUrl. " + localThrowable);
        }
      }
    }
  }
  
  public final void zzb(com.google.android.gms.ads.internal.overlay.zzd paramzzd)
  {
    synchronized (this.zzqp)
    {
      this.zzMR = paramzzd;
      return;
    }
  }
  
  public final void zzb(String paramString, Map<String, ?> paramMap)
  {
    try
    {
      JSONObject localJSONObject = zzp.zzbI().zzy(paramMap);
      zzb(paramString, localJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      zzb.w("Could not convert parameters to JSON.");
    }
  }
  
  /* Error */
  public final void zzb(String paramString, JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aload_2
    //   1: ifnonnull +11 -> 12
    //   4: new 791	org/json/JSONObject
    //   7: dup
    //   8: invokespecial 792	org/json/JSONObject:<init>	()V
    //   11: astore_2
    //   12: aload_2
    //   13: invokevirtual 793	org/json/JSONObject:toString	()Ljava/lang/String;
    //   16: astore_3
    //   17: new 558	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 794	java/lang/StringBuilder:<init>	()V
    //   24: astore 4
    //   26: aload 4
    //   28: ldc_w 796
    //   31: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: pop
    //   35: aload 4
    //   37: aload_1
    //   38: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: pop
    //   42: aload 4
    //   44: ldc_w 798
    //   47: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: pop
    //   51: aload 4
    //   53: ldc_w 800
    //   56: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload 4
    //   62: aload_3
    //   63: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload 4
    //   69: ldc_w 802
    //   72: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: new 558	java/lang/StringBuilder
    //   79: dup
    //   80: ldc_w 804
    //   83: invokespecial 562	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   86: aload 4
    //   88: invokevirtual 570	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual 570	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   97: invokestatic 528	com/google/android/gms/ads/internal/util/client/zzb:v	(Ljava/lang/String;)V
    //   100: aload 4
    //   102: invokevirtual 570	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: astore 11
    //   107: bipush 19
    //   109: invokestatic 191	com/google/android/gms/common/util/zzq:zzdC	(I)Z
    //   112: ifeq +157 -> 269
    //   115: aload_0
    //   116: invokespecial 806	com/google/android/gms/internal/zzjs:zzgY	()Ljava/lang/Boolean;
    //   119: ifnonnull +52 -> 171
    //   122: aload_0
    //   123: getfield 69	com/google/android/gms/internal/zzjs:zzqp	Ljava/lang/Object;
    //   126: astore 14
    //   128: aload 14
    //   130: monitorenter
    //   131: aload_0
    //   132: invokestatic 288	com/google/android/gms/ads/internal/zzp:zzbL	()Lcom/google/android/gms/internal/zzih;
    //   135: invokevirtual 807	com/google/android/gms/internal/zzih:zzgY	()Ljava/lang/Boolean;
    //   138: putfield 284	com/google/android/gms/internal/zzjs:zzKA	Ljava/lang/Boolean;
    //   141: aload_0
    //   142: getfield 284	com/google/android/gms/internal/zzjs:zzKA	Ljava/lang/Boolean;
    //   145: astore 16
    //   147: aload 16
    //   149: ifnonnull +19 -> 168
    //   152: aload_0
    //   153: ldc_w 809
    //   156: aconst_null
    //   157: invokevirtual 810	com/google/android/gms/internal/zzjs:evaluateJavascript	(Ljava/lang/String;Landroid/webkit/ValueCallback;)V
    //   160: aload_0
    //   161: iconst_1
    //   162: invokestatic 815	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   165: invokespecial 817	com/google/android/gms/internal/zzjs:zzb	(Ljava/lang/Boolean;)V
    //   168: aload 14
    //   170: monitorexit
    //   171: aload_0
    //   172: invokespecial 806	com/google/android/gms/internal/zzjs:zzgY	()Ljava/lang/Boolean;
    //   175: invokevirtual 820	java/lang/Boolean:booleanValue	()Z
    //   178: ifeq +68 -> 246
    //   181: aload_0
    //   182: getfield 69	com/google/android/gms/internal/zzjs:zzqp	Ljava/lang/Object;
    //   185: astore 12
    //   187: aload 12
    //   189: monitorenter
    //   190: aload_0
    //   191: invokevirtual 271	com/google/android/gms/internal/zzjs:isDestroyed	()Z
    //   194: ifne +35 -> 229
    //   197: aload_0
    //   198: aload 11
    //   200: aconst_null
    //   201: invokevirtual 810	com/google/android/gms/internal/zzjs:evaluateJavascript	(Ljava/lang/String;Landroid/webkit/ValueCallback;)V
    //   204: aload 12
    //   206: monitorexit
    //   207: return
    //   208: astore 17
    //   210: aload_0
    //   211: iconst_0
    //   212: invokestatic 815	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   215: invokespecial 817	com/google/android/gms/internal/zzjs:zzb	(Ljava/lang/Boolean;)V
    //   218: goto -50 -> 168
    //   221: astore 15
    //   223: aload 14
    //   225: monitorexit
    //   226: aload 15
    //   228: athrow
    //   229: ldc_w 276
    //   232: invokestatic 281	com/google/android/gms/ads/internal/util/client/zzb:w	(Ljava/lang/String;)V
    //   235: goto -31 -> 204
    //   238: astore 13
    //   240: aload 12
    //   242: monitorexit
    //   243: aload 13
    //   245: athrow
    //   246: aload_0
    //   247: new 558	java/lang/StringBuilder
    //   250: dup
    //   251: ldc_w 822
    //   254: invokespecial 562	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   257: aload 11
    //   259: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: invokevirtual 570	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   265: invokespecial 824	com/google/android/gms/internal/zzjs:zzaI	(Ljava/lang/String;)V
    //   268: return
    //   269: aload_0
    //   270: new 558	java/lang/StringBuilder
    //   273: dup
    //   274: ldc_w 822
    //   277: invokespecial 562	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   280: aload 11
    //   282: invokevirtual 609	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   285: invokevirtual 570	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   288: invokespecial 824	com/google/android/gms/internal/zzjs:zzaI	(Ljava/lang/String;)V
    //   291: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	292	0	this	zzjs
    //   0	292	1	paramString	String
    //   0	292	2	paramJSONObject	JSONObject
    //   16	47	3	str1	String
    //   24	77	4	localStringBuilder	java.lang.StringBuilder
    //   105	176	11	str2	String
    //   238	6	13	localObject2	Object
    //   126	98	14	localObject3	Object
    //   221	6	15	localObject4	Object
    //   145	3	16	localBoolean	Boolean
    //   208	1	17	localIllegalStateException	java.lang.IllegalStateException
    // Exception table:
    //   from	to	target	type
    //   152	168	208	java/lang/IllegalStateException
    //   131	147	221	finally
    //   152	168	221	finally
    //   168	171	221	finally
    //   210	218	221	finally
    //   223	226	221	finally
    //   190	204	238	finally
    //   204	207	238	finally
    //   229	235	238	finally
    //   240	243	238	finally
  }
  
  public final AdSizeParcel zzbb()
  {
    synchronized (this.zzqp)
    {
      AdSizeParcel localAdSizeParcel = this.zzBH;
      return localAdSizeParcel;
    }
  }
  
  public final void zzc(com.google.android.gms.ads.internal.overlay.zzd paramzzd)
  {
    synchronized (this.zzqp)
    {
      this.zzNc = paramzzd;
      return;
    }
  }
  
  public final void zzfp()
  {
    if (this.zzMY == null)
    {
      zzce.zza(this.zzNb.zzpz, this.zzNa, new String[] { "aes" });
      this.zzMY = zzce.zzb(this.zzNb.zzpz);
      this.zzNb.zza("native:view_show", this.zzMY);
    }
    HashMap localHashMap = new HashMap(1);
    localHashMap.put("version", this.zzqn.afmaVersion);
    zzb("onshow", localHashMap);
  }
  
  public final void zzhE()
  {
    zzif();
    HashMap localHashMap = new HashMap(1);
    localHashMap.put("version", this.zzqn.afmaVersion);
    zzb("onhide", localHashMap);
  }
  
  public final Activity zzhF()
  {
    return this.zzMP.zzLR;
  }
  
  public final Context zzhG()
  {
    return this.zzMP.zzNf;
  }
  
  public final com.google.android.gms.ads.internal.zzd zzhH()
  {
    return this.zzpH;
  }
  
  public final com.google.android.gms.ads.internal.overlay.zzd zzhI()
  {
    synchronized (this.zzqp)
    {
      com.google.android.gms.ads.internal.overlay.zzd localzzd = this.zzMR;
      return localzzd;
    }
  }
  
  public final com.google.android.gms.ads.internal.overlay.zzd zzhJ()
  {
    synchronized (this.zzqp)
    {
      com.google.android.gms.ads.internal.overlay.zzd localzzd = this.zzNc;
      return localzzd;
    }
  }
  
  public final zzjp zzhK()
  {
    return this.zzMQ;
  }
  
  public final boolean zzhL()
  {
    return this.zzMS;
  }
  
  public final zzao zzhM()
  {
    return this.zzyv;
  }
  
  public final VersionInfoParcel zzhN()
  {
    return this.zzqn;
  }
  
  public final boolean zzhO()
  {
    synchronized (this.zzqp)
    {
      boolean bool = this.zzMU;
      return bool;
    }
  }
  
  public final void zzhP()
  {
    synchronized (this.zzqp)
    {
      zzb.v("Destroying WebView!");
      zziq.zzLh.post(new Runnable()
      {
        public final void run()
        {
          zzjs.zza(zzjs.this);
        }
      });
      return;
    }
  }
  
  public final boolean zzhQ()
  {
    synchronized (this.zzqp)
    {
      zzce.zza(this.zzNb.zzpz, this.zzMY, new String[] { "aebb" });
      boolean bool = this.zzMX;
      return bool;
    }
  }
  
  public final zzjn zzhR()
  {
    return null;
  }
  
  public final zzcg zzhS()
  {
    return this.zzNa;
  }
  
  public final zzch zzhT()
  {
    return this.zzNb;
  }
  
  public final void zzhU()
  {
    zzjb localzzjb = this.zzse;
    localzzjb.zzLU = true;
    if (localzzjb.zzLT) {
      localzzjb.zzhw();
    }
  }
  
  public final void zzhV()
  {
    if (this.zzNa == null)
    {
      this.zzNa = zzce.zzb(this.zzNb.zzpz);
      this.zzNb.zza("native:view_load", this.zzNa);
    }
  }
  
  @zzhb
  public static final class zza
    extends MutableContextWrapper
  {
    Activity zzLR;
    Context zzNf;
    private Context zzsn;
    
    public zza(Context paramContext)
    {
      super();
      setBaseContext(paramContext);
    }
    
    public final Object getSystemService(String paramString)
    {
      return this.zzNf.getSystemService(paramString);
    }
    
    public final void setBaseContext(Context paramContext)
    {
      this.zzsn = paramContext.getApplicationContext();
      if ((paramContext instanceof Activity)) {}
      for (Activity localActivity = (Activity)paramContext;; localActivity = null)
      {
        this.zzLR = localActivity;
        this.zzNf = paramContext;
        super.setBaseContext(this.zzsn);
        return;
      }
    }
    
    public final void startActivity(Intent paramIntent)
    {
      if ((this.zzLR != null) && (!zzq.zzdC(21)))
      {
        this.zzLR.startActivity(paramIntent);
        return;
      }
      paramIntent.setFlags(268435456);
      this.zzsn.startActivity(paramIntent);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzjs
 * JD-Core Version:    0.7.0.1
 */