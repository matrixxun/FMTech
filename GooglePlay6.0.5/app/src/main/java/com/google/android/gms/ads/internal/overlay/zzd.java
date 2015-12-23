package com.google.android.gms.ads.internal.overlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gms.ads.internal.InterstitialAdParameterParcel;
import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzfv.zza;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zzim;
import com.google.android.gms.internal.zziq;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zzit;
import com.google.android.gms.internal.zzjo;
import com.google.android.gms.internal.zzjp;
import com.google.android.gms.internal.zzjp.zza;
import com.google.android.gms.internal.zzjr;
import com.google.android.gms.internal.zzjs;
import java.util.Collections;

@zzhb
public final class zzd
  extends zzfv.zza
  implements zzo
{
  static final int zzDE = Color.argb(0, 0, 0, 0);
  public final Activity mActivity;
  public RelativeLayout zzCM;
  AdOverlayInfoParcel zzDF;
  zzc zzDG;
  public zzm zzDH;
  public boolean zzDI = false;
  public FrameLayout zzDJ;
  public WebChromeClient.CustomViewCallback zzDK;
  boolean zzDL = false;
  boolean zzDM = false;
  public boolean zzDN = false;
  int zzDO = 0;
  public boolean zzDP;
  private boolean zzDQ = false;
  private boolean zzDR = true;
  zzjo zzpX;
  
  public zzd(Activity paramActivity)
  {
    this.mActivity = paramActivity;
  }
  
  private void zzfn()
  {
    if ((!this.mActivity.isFinishing()) || (this.zzDQ)) {}
    do
    {
      return;
      this.zzDQ = true;
      if (this.zzpX != null)
      {
        int i = this.zzDO;
        this.zzpX.zzF(i);
        this.zzCM.removeView(this.zzpX.getView());
        if (this.zzDG != null)
        {
          this.zzpX.setContext(this.zzDG.context);
          this.zzpX.zzD(false);
          this.zzDG.zzDU.addView(this.zzpX.getView(), this.zzDG.index, this.zzDG.zzDT);
          this.zzDG = null;
        }
        this.zzpX = null;
      }
    } while ((this.zzDF == null) || (this.zzDF.zzDZ == null));
    this.zzDF.zzDZ.zzbj();
  }
  
  private void zzx(boolean paramBoolean)
    throws zzd.zza
  {
    if (!this.zzDP) {
      this.mActivity.requestWindowFeature(1);
    }
    Window localWindow = this.mActivity.getWindow();
    if (localWindow == null) {
      throw new zza("Invalid activity, no window available.");
    }
    if ((!this.zzDM) || ((this.zzDF.zzEj != null) && (this.zzDF.zzEj.zzqG))) {
      localWindow.setFlags(1024, 1024);
    }
    boolean bool1 = this.zzDF.zzEa.zzhK().zzcm();
    this.zzDN = false;
    if (bool1)
    {
      if (this.zzDF.orientation == zzp.zzbK().zzhl())
      {
        int j = this.mActivity.getResources().getConfiguration().orientation;
        boolean bool3 = false;
        if (j == 1) {
          bool3 = true;
        }
        this.zzDN = bool3;
      }
    }
    else
    {
      zzb.d("Delay onShow to next orientation change: " + this.zzDN);
      setRequestedOrientation(this.zzDF.orientation);
      if (zzp.zzbK().zza(localWindow)) {
        zzb.d("Hardware acceleration on the AdActivity window enabled.");
      }
      if (this.zzDM) {
        break label612;
      }
      this.zzCM.setBackgroundColor(-16777216);
      label216:
      this.mActivity.setContentView(this.zzCM);
      this.zzDP = true;
      if (!paramBoolean) {
        break label679;
      }
      zzp.zzbJ();
      zzjr localzzjr = new zzjr(zzjs.zzb$631f0cde(this.mActivity, this.zzDF.zzEa.zzbb(), true, null, this.zzDF.zzrw, null, null));
      localzzjr.setWebViewClient(zzp.zzbK().zzb(localzzjr, bool1));
      localzzjr.setWebChromeClient(zzp.zzbK().zzh(localzzjr));
      this.zzpX = localzzjr;
      this.zzpX.zzhK().zzb$5cad6de9(this.zzDF.zzEb, this.zzDF.zzEf, this.zzDF.zzEh, this.zzDF.zzEa.zzhK().zzzB);
      this.zzpX.zzhK().zzFI = new zzjp.zza()
      {
        public final void zza$7d5558dd(zzjo paramAnonymouszzjo)
        {
          paramAnonymouszzjo.zzfp();
        }
      };
      if (this.zzDF.url == null) {
        break label625;
      }
      this.zzpX.loadUrl(this.zzDF.url);
      label410:
      if (this.zzDF.zzEa != null) {
        this.zzDF.zzEa.zzc(this);
      }
    }
    for (;;)
    {
      this.zzpX.zzb(this);
      ViewParent localViewParent = this.zzpX.getParent();
      if ((localViewParent != null) && ((localViewParent instanceof ViewGroup))) {
        ((ViewGroup)localViewParent).removeView(this.zzpX.getView());
      }
      if (this.zzDM) {
        this.zzpX.setBackgroundColor(zzDE);
      }
      this.zzCM.addView(this.zzpX.getView(), -1, -1);
      if ((!paramBoolean) && (!this.zzDN)) {
        zzfp();
      }
      zzw(bool1);
      if (this.zzpX.zzhL()) {
        zza(bool1, true);
      }
      return;
      if (this.zzDF.orientation != zzp.zzbK().zzhm()) {
        break;
      }
      int i = this.mActivity.getResources().getConfiguration().orientation;
      boolean bool2 = false;
      if (i == 2) {
        bool2 = true;
      }
      this.zzDN = bool2;
      break;
      label612:
      this.zzCM.setBackgroundColor(zzDE);
      break label216;
      label625:
      if (this.zzDF.zzEe != null)
      {
        this.zzpX.loadDataWithBaseURL(this.zzDF.zzEc, this.zzDF.zzEe, "text/html", "UTF-8", null);
        break label410;
      }
      throw new zza("No URL or HTML to display in ad overlay.");
      label679:
      this.zzpX = this.zzDF.zzEa;
      this.zzpX.setContext(this.mActivity);
    }
  }
  
  public final void close()
  {
    this.zzDO = 2;
    this.mActivity.finish();
  }
  
  public final void onBackPressed()
  {
    this.zzDO = 0;
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    boolean bool = false;
    if (paramBundle != null) {
      bool = paramBundle.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false);
    }
    this.zzDL = bool;
    Activity localActivity;
    AdLauncherIntentInfoParcel localAdLauncherIntentInfoParcel;
    do
    {
      try
      {
        this.zzDF = AdOverlayInfoParcel.zzb(this.mActivity.getIntent());
        if (this.zzDF == null) {
          throw new zza("Could not get info for ad overlay.");
        }
      }
      catch (zza localzza)
      {
        zzb.w(localzza.getMessage());
        this.zzDO = 3;
        this.mActivity.finish();
        return;
      }
      if (this.zzDF.zzrw.zzLZ > 7500000) {
        this.zzDO = 3;
      }
      if (this.mActivity.getIntent() != null) {
        this.zzDR = this.mActivity.getIntent().getBooleanExtra("shouldCallOnOverlayOpened", true);
      }
      if (this.zzDF.zzEj != null) {}
      for (this.zzDM = this.zzDF.zzEj.zzqF;; this.zzDM = false)
      {
        Flag localFlag = Flags.zzxj;
        if ((((Boolean)zzp.zzbR().zzd(localFlag)).booleanValue()) && (this.zzDM) && (this.zzDF.zzEj.zzqH != null)) {
          new zzd((byte)0).zzhf();
        }
        if (paramBundle == null)
        {
          if ((this.zzDF.zzDZ != null) && (this.zzDR)) {
            this.zzDF.zzDZ.zzbk();
          }
          if ((this.zzDF.zzEg == 1) || (this.zzDF.zzDY == null)) {}
        }
        this.zzCM = new zzb(this.mActivity, this.zzDF.zzEi);
        switch (this.zzDF.zzEg)
        {
        default: 
          throw new zza("Could not determine ad overlay type.");
        }
      }
      zzx(false);
      return;
      this.zzDG = new zzc(this.zzDF.zzEa);
      zzx(false);
      return;
      zzx(true);
      return;
      if (this.zzDL)
      {
        this.zzDO = 3;
        this.mActivity.finish();
        return;
      }
      zzp.zzbF();
      localActivity = this.mActivity;
      localAdLauncherIntentInfoParcel = this.zzDF.zzDX;
    } while (zza.zza$1d97657b(localActivity, localAdLauncherIntentInfoParcel));
    this.zzDO = 3;
    this.mActivity.finish();
  }
  
  public final void onDestroy()
  {
    if (this.zzpX != null) {
      this.zzCM.removeView(this.zzpX.getView());
    }
    zzfn();
  }
  
  public final void onPause()
  {
    zzfj();
    if ((this.zzpX != null) && ((!this.mActivity.isFinishing()) || (this.zzDG == null)))
    {
      zzp.zzbK();
      zzjo localzzjo = this.zzpX;
      if (localzzjo != null) {
        localzzjo.onPause();
      }
    }
    zzfn();
  }
  
  public final void onRestart() {}
  
  public final void onResume()
  {
    if ((this.zzDF != null) && (this.zzDF.zzEg == 4))
    {
      if (!this.zzDL) {
        break label76;
      }
      this.zzDO = 3;
      this.mActivity.finish();
    }
    while ((this.zzpX != null) && (!this.zzpX.isDestroyed()))
    {
      zzp.zzbK();
      zzjo localzzjo = this.zzpX;
      if (localzzjo != null) {
        localzzjo.onResume();
      }
      return;
      label76:
      this.zzDL = true;
    }
    zzb.w("The webview does not exit. Ignoring action.");
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.zzDL);
  }
  
  public final void onStart() {}
  
  public final void onStop()
  {
    zzfn();
  }
  
  public final void setRequestedOrientation(int paramInt)
  {
    this.mActivity.setRequestedOrientation(paramInt);
  }
  
  public final void zza(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.zzDH != null) {
      this.zzDH.zza(paramBoolean1, paramBoolean2);
    }
  }
  
  public final void zzaQ()
  {
    this.zzDP = true;
  }
  
  public final void zzfj()
  {
    if ((this.zzDF != null) && (this.zzDI)) {
      setRequestedOrientation(this.zzDF.orientation);
    }
    if (this.zzDJ != null)
    {
      this.mActivity.setContentView(this.zzCM);
      this.zzDP = true;
      this.zzDJ.removeAllViews();
      this.zzDJ = null;
    }
    if (this.zzDK != null)
    {
      this.zzDK.onCustomViewHidden();
      this.zzDK = null;
    }
    this.zzDI = false;
  }
  
  public final void zzfk()
  {
    this.zzDO = 1;
    this.mActivity.finish();
  }
  
  public final boolean zzfl()
  {
    this.zzDO = 0;
    boolean bool;
    if (this.zzpX == null) {
      bool = true;
    }
    do
    {
      return bool;
      bool = this.zzpX.zzhQ();
    } while (bool);
    this.zzpX.zzb("onbackblocked", Collections.emptyMap());
    return bool;
  }
  
  public void zzfp()
  {
    this.zzpX.zzfp();
  }
  
  public final void zzw(boolean paramBoolean)
  {
    int i;
    RelativeLayout.LayoutParams localLayoutParams;
    if (paramBoolean)
    {
      i = 50;
      this.zzDH = new zzm(this.mActivity, i, this);
      localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams.addRule(10);
      if (!paramBoolean) {
        break label90;
      }
    }
    label90:
    for (int j = 11;; j = 9)
    {
      localLayoutParams.addRule(j);
      this.zzDH.zza(paramBoolean, this.zzDF.zzEd);
      this.zzCM.addView(this.zzDH, localLayoutParams);
      return;
      i = 32;
      break;
    }
  }
  
  @zzhb
  private static final class zza
    extends Exception
  {
    public zza(String paramString)
    {
      super();
    }
  }
  
  @zzhb
  static final class zzb
    extends RelativeLayout
  {
    zzit zzsd;
    
    public zzb(Context paramContext, String paramString)
    {
      super();
      this.zzsd = new zzit(paramContext, paramString);
    }
    
    public final boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      zzit localzzit = this.zzsd;
      int i = paramMotionEvent.getHistorySize();
      for (int j = 0; j < i; j++) {
        localzzit.zza(paramMotionEvent.getActionMasked(), paramMotionEvent.getHistoricalX(0, j), paramMotionEvent.getHistoricalY(0, j));
      }
      localzzit.zza(paramMotionEvent.getActionMasked(), paramMotionEvent.getX(), paramMotionEvent.getY());
      return false;
    }
  }
  
  @zzhb
  public static final class zzc
  {
    public final Context context;
    public final int index;
    public final ViewGroup.LayoutParams zzDT;
    public final ViewGroup zzDU;
    
    public zzc(zzjo paramzzjo)
      throws zzd.zza
    {
      this.zzDT = paramzzjo.getLayoutParams();
      ViewParent localViewParent = paramzzjo.getParent();
      this.context = paramzzjo.zzhG();
      if ((localViewParent != null) && ((localViewParent instanceof ViewGroup)))
      {
        this.zzDU = ((ViewGroup)localViewParent);
        this.index = this.zzDU.indexOfChild(paramzzjo.getView());
        this.zzDU.removeView(paramzzjo.getView());
        paramzzjo.zzD(true);
        return;
      }
      throw new zzd.zza("Could not get the parent of the WebView for an overlay.");
    }
  }
  
  @zzhb
  private final class zzd
    extends zzim
  {
    private zzd() {}
    
    public final void zzbB()
    {
      zzp.zzbI();
      Bitmap localBitmap = zziq.zze(zzd.zza(zzd.this), zzd.this.zzDF.zzEj.zzqH);
      if (localBitmap != null)
      {
        final Drawable localDrawable = zzp.zzbK().zza(zzd.zza(zzd.this), localBitmap, zzd.this.zzDF.zzEj.zzqI, zzd.this.zzDF.zzEj.zzqJ);
        zziq.zzLh.post(new Runnable()
        {
          public final void run()
          {
            zzd.zza(zzd.this).getWindow().setBackgroundDrawable(localDrawable);
          }
        });
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.ads.internal.overlay.zzd
 * JD-Core Version:    0.7.0.1
 */