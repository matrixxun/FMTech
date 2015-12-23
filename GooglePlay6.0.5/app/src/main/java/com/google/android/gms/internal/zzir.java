package com.google.android.gms.internal;

import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import java.io.File;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;

@zzhb
public class zzir
{
  public String getDefaultUserAgent(Context paramContext)
  {
    return "";
  }
  
  public Drawable zza(Context paramContext, Bitmap paramBitmap, boolean paramBoolean, float paramFloat)
  {
    return new BitmapDrawable(paramContext.getResources(), paramBitmap);
  }
  
  public String zza(SslError paramSslError)
  {
    return "";
  }
  
  public void zza(ViewTreeObserver paramViewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    paramViewTreeObserver.removeGlobalOnLayoutListener(paramOnGlobalLayoutListener);
  }
  
  public boolean zza(DownloadManager.Request paramRequest)
  {
    return false;
  }
  
  public boolean zza(Context paramContext, WebSettings paramWebSettings)
  {
    return false;
  }
  
  public boolean zza(Window paramWindow)
  {
    return false;
  }
  
  public zzjp zzb(zzjo paramzzjo, boolean paramBoolean)
  {
    return new zzjp(paramzzjo, paramBoolean);
  }
  
  public void zzb(Activity paramActivity, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    Window localWindow = paramActivity.getWindow();
    if ((localWindow != null) && (localWindow.getDecorView() != null) && (localWindow.getDecorView().getViewTreeObserver() != null)) {
      zza(localWindow.getDecorView().getViewTreeObserver(), paramOnGlobalLayoutListener);
    }
  }
  
  public Set<String> zzf(Uri paramUri)
  {
    if (paramUri.isOpaque()) {
      return Collections.emptySet();
    }
    String str = paramUri.getEncodedQuery();
    if (str == null) {
      return Collections.emptySet();
    }
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    int i = 0;
    do
    {
      int j = str.indexOf('&', i);
      if (j == -1) {
        j = str.length();
      }
      int k = str.indexOf('=', i);
      if ((k > j) || (k == -1)) {
        k = j;
      }
      localLinkedHashSet.add(Uri.decode(str.substring(i, k)));
      i = j + 1;
    } while (i < str.length());
    return Collections.unmodifiableSet(localLinkedHashSet);
  }
  
  public WebChromeClient zzh(zzjo paramzzjo)
  {
    return null;
  }
  
  public int zzhl()
  {
    return 0;
  }
  
  public int zzhm()
  {
    return 1;
  }
  
  public int zzhn()
  {
    return 5;
  }
  
  public boolean zzm(View paramView)
  {
    return false;
  }
  
  public boolean zzn(View paramView)
  {
    return false;
  }
  
  public static class zza
    extends zzir
  {
    public zza()
    {
      super();
    }
    
    public boolean zza(DownloadManager.Request paramRequest)
    {
      paramRequest.setShowRunningNotification(true);
      return true;
    }
    
    public final int zzhl()
    {
      return 6;
    }
    
    public final int zzhm()
    {
      return 7;
    }
  }
  
  public static class zzb
    extends zzir.zza
  {
    public final boolean zza(DownloadManager.Request paramRequest)
    {
      paramRequest.allowScanningByMediaScanner();
      paramRequest.setNotificationVisibility(1);
      return true;
    }
    
    public boolean zza(final Context paramContext, WebSettings paramWebSettings)
    {
      super.zza(paramContext, paramWebSettings);
      if ((File)zzja.zzb(new Callable() {}) != null)
      {
        paramWebSettings.setAppCachePath(paramContext.getCacheDir().getAbsolutePath());
        paramWebSettings.setAppCacheMaxSize(0L);
        paramWebSettings.setAppCacheEnabled(true);
      }
      paramWebSettings.setDatabasePath(paramContext.getDatabasePath("com.google.android.gms.ads.db").getAbsolutePath());
      paramWebSettings.setDatabaseEnabled(true);
      paramWebSettings.setDomStorageEnabled(true);
      paramWebSettings.setDisplayZoomControls(false);
      paramWebSettings.setBuiltInZoomControls(true);
      paramWebSettings.setSupportZoom(true);
      paramWebSettings.setAllowContentAccess(false);
      return true;
    }
    
    public final boolean zza(Window paramWindow)
    {
      paramWindow.setFlags(16777216, 16777216);
      return true;
    }
    
    public final zzjp zzb(zzjo paramzzjo, boolean paramBoolean)
    {
      return new zzjv(paramzzjo, paramBoolean);
    }
    
    public final Set<String> zzf(Uri paramUri)
    {
      return paramUri.getQueryParameterNames();
    }
    
    public WebChromeClient zzh(zzjo paramzzjo)
    {
      return new zzju(paramzzjo);
    }
    
    public final boolean zzm(View paramView)
    {
      paramView.setLayerType(0, null);
      return true;
    }
    
    public final boolean zzn(View paramView)
    {
      paramView.setLayerType(1, null);
      return true;
    }
  }
  
  public static class zzc
    extends zzir.zzb
  {
    public final String zza(SslError paramSslError)
    {
      return paramSslError.getUrl();
    }
    
    public final WebChromeClient zzh(zzjo paramzzjo)
    {
      return new zzjw(paramzzjo);
    }
  }
  
  public static class zzd
    extends zzir.zzf
  {
    public final String getDefaultUserAgent(Context paramContext)
    {
      return WebSettings.getDefaultUserAgent(paramContext);
    }
    
    public final Drawable zza(Context paramContext, Bitmap paramBitmap, boolean paramBoolean, float paramFloat)
    {
      if ((!paramBoolean) || (paramFloat <= 0.0F) || (paramFloat > 25.0F)) {
        return new BitmapDrawable(paramContext.getResources(), paramBitmap);
      }
      try
      {
        Bitmap localBitmap1 = Bitmap.createScaledBitmap(paramBitmap, paramBitmap.getWidth(), paramBitmap.getHeight(), false);
        Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1);
        RenderScript localRenderScript = RenderScript.create(paramContext);
        ScriptIntrinsicBlur localScriptIntrinsicBlur = ScriptIntrinsicBlur.create(localRenderScript, Element.U8_4(localRenderScript));
        Allocation localAllocation1 = Allocation.createFromBitmap(localRenderScript, localBitmap1);
        Allocation localAllocation2 = Allocation.createFromBitmap(localRenderScript, localBitmap2);
        localScriptIntrinsicBlur.setRadius(paramFloat);
        localScriptIntrinsicBlur.setInput(localAllocation1);
        localScriptIntrinsicBlur.forEach(localAllocation2);
        localAllocation2.copyTo(localBitmap2);
        BitmapDrawable localBitmapDrawable = new BitmapDrawable(paramContext.getResources(), localBitmap2);
        return localBitmapDrawable;
      }
      catch (RuntimeException localRuntimeException) {}
      return new BitmapDrawable(paramContext.getResources(), paramBitmap);
    }
    
    public final boolean zza(Context paramContext, WebSettings paramWebSettings)
    {
      super.zza(paramContext, paramWebSettings);
      paramWebSettings.setMediaPlaybackRequiresUserGesture(false);
      return true;
    }
  }
  
  public static class zze
    extends zzir.zzd
  {
    public final int zzhn()
    {
      return 14;
    }
  }
  
  public static class zzf
    extends zzir.zzc
  {
    public final void zza(ViewTreeObserver paramViewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
    {
      paramViewTreeObserver.removeOnGlobalLayoutListener(paramOnGlobalLayoutListener);
    }
    
    public boolean zza(Context paramContext, WebSettings paramWebSettings)
    {
      super.zza(paramContext, paramWebSettings);
      paramWebSettings.setAllowFileAccessFromFileURLs(false);
      paramWebSettings.setAllowUniversalAccessFromFileURLs(false);
      return true;
    }
    
    public final void zzb(Activity paramActivity, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
    {
      Window localWindow = paramActivity.getWindow();
      if ((localWindow != null) && (localWindow.getDecorView() != null) && (localWindow.getDecorView().getViewTreeObserver() != null)) {
        localWindow.getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(paramOnGlobalLayoutListener);
      }
    }
  }
  
  public static final class zzg
    extends zzir.zze
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzir
 * JD-Core Version:    0.7.0.1
 */