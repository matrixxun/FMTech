package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.util.Clock;

public final class zzy
  extends zzw
{
  zza zzbov;
  
  protected zzy(zzt paramzzt)
  {
    super(paramzzt);
  }
  
  protected final void onInitialize() {}
  
  public final void zzCU()
  {
    super.checkOnWorkerThread();
    super.zziF();
    zziL();
    if (!this.zzbkM.zzCC()) {
      return;
    }
    super.zzBW().zzCU();
  }
  
  public final void zza(final String paramString1, final String paramString2, Object paramObject)
  {
    zzx.zzcG(paramString1);
    final long l = super.getClock().currentTimeMillis();
    super.zzBX();
    zzae.zzeH(paramString2);
    if (paramObject != null)
    {
      super.zzBX().zzo(paramString2, paramObject);
      final Object localObject = super.zzBX().zzp(paramString2, paramObject);
      if (localObject != null) {
        super.zzBY().zzg(new Runnable()
        {
          public final void run()
          {
            zzy.zza(zzy.this, paramString1, paramString2, localObject, l);
          }
        });
      }
      return;
    }
    super.zzBY().zzg(new Runnable()
    {
      public final void run()
      {
        zzy.zza(zzy.this, paramString1, paramString2, null, l);
      }
    });
  }
  
  private final class zza
    implements Application.ActivityLifecycleCallbacks
  {
    private zza() {}
    
    public final void onActivityCreated(Activity paramActivity, Bundle paramBundle)
    {
      String str;
      try
      {
        zzy.this.zzBh().zzbne.zzeB("onActivityCreated");
        Intent localIntent = paramActivity.getIntent();
        if (localIntent == null) {
          return;
        }
        Uri localUri = localIntent.getData();
        if ((localUri == null) || (!localUri.isHierarchical())) {
          return;
        }
        str = localUri.getQueryParameter("referrer");
        if (TextUtils.isEmpty(str)) {
          return;
        }
        if (!str.contains("gclid"))
        {
          zzy.this.zzBh().zzbnd.zzeB("Activity created with data 'referrer' param without gclid");
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        zzy.this.zzBh().zzbmW.zzm("Throwable caught in onActivityCreated", localThrowable);
        return;
      }
      zzy.this.zzBh().zzbnd.zzm("Activity created with referrer", str);
      if (!TextUtils.isEmpty(str)) {
        zzy.this.zza("auto", "_ldl", str);
      }
    }
    
    public final void onActivityDestroyed(Activity paramActivity) {}
    
    public final void onActivityPaused(Activity paramActivity) {}
    
    public final void onActivityResumed(Activity paramActivity) {}
    
    public final void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public final void onActivityStarted(Activity paramActivity) {}
    
    public final void onActivityStopped(Activity paramActivity) {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.measurement.internal.zzy
 * JD-Core Version:    0.7.0.1
 */