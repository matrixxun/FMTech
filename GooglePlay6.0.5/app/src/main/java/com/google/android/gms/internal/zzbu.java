package com.google.android.gms.internal;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.util.zzq;
import java.util.Iterator;
import java.util.List;

@zzhb
public final class zzbu
  extends Thread
{
  private boolean zzal;
  private final Object zzqp;
  final int zzth;
  final int zztj;
  private boolean zztt;
  private final zzbt zztu;
  final zzbs zztv;
  final zzha zztw;
  private final int zztx;
  final int zzty;
  final int zztz;
  
  private boolean zzcM()
  {
    for (;;)
    {
      int i;
      int j;
      try
      {
        Context localContext = this.zztu.mContext;
        if (localContext == null) {
          return false;
        }
        ActivityManager localActivityManager = (ActivityManager)localContext.getSystemService("activity");
        KeyguardManager localKeyguardManager = (KeyguardManager)localContext.getSystemService("keyguard");
        if ((localActivityManager != null) && (localKeyguardManager != null))
        {
          List localList = localActivityManager.getRunningAppProcesses();
          if (localList == null) {
            return false;
          }
          Iterator localIterator = localList.iterator();
          if (localIterator.hasNext())
          {
            ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
            if (Process.myPid() != localRunningAppProcessInfo.pid) {
              continue;
            }
            if (localRunningAppProcessInfo.importance != 100) {
              break label178;
            }
            i = 1;
            if ((i != 0) && (!localKeyguardManager.inKeyguardRestrictedInputMode()))
            {
              PowerManager localPowerManager = (PowerManager)localContext.getSystemService("power");
              if (localPowerManager == null)
              {
                j = 0;
                break label171;
              }
              boolean bool = localPowerManager.isScreenOn();
              j = bool;
              break label171;
            }
          }
          return false;
        }
      }
      catch (Throwable localThrowable)
      {
        return false;
      }
      return false;
      label171:
      if (j != 0)
      {
        return true;
        label178:
        i = 0;
      }
    }
  }
  
  public final void run()
  {
    while (!this.zzal) {
      try
      {
        if (zzcM())
        {
          localActivity = this.zztu.mActivity;
          if (localActivity == null) {
            zzb.d("ContentFetchThread: no activity");
          }
        }
      }
      catch (Throwable localThrowable)
      {
        Activity localActivity;
        zzb.e("Error in ContentFetchTask", localThrowable);
        this.zztw.zza(localThrowable, true);
        synchronized (this.zzqp)
        {
          for (;;)
          {
            boolean bool = this.zztt;
            if (!bool) {
              break;
            }
            try
            {
              zzb.d("ContentFetchTask: waiting");
              this.zzqp.wait();
            }
            catch (InterruptedException localInterruptedException) {}
          }
          if (localActivity != null)
          {
            Window localWindow = localActivity.getWindow();
            final View localView1 = null;
            if (localWindow != null)
            {
              View localView2 = localActivity.getWindow().getDecorView();
              localView1 = null;
              if (localView2 != null) {
                localView1 = localActivity.getWindow().getDecorView().findViewById(16908290);
              }
            }
            if ((localView1 != null) && (localView1 != null)) {
              localView1.post(new Runnable()
              {
                public final void run()
                {
                  zzbu localzzbu = zzbu.this;
                  View localView = localView1;
                  try
                  {
                    zzbr localzzbr = new zzbr(localzzbu.zzth, localzzbu.zzty, localzzbu.zztj, localzzbu.zztz);
                    zzbu.zza localzza = localzzbu.zza(localView, localzzbr);
                    localzzbr.zzcI();
                    if ((localzza.zztG == 0) && (localzza.zztH == 0)) {
                      return;
                    }
                    if (((localzza.zztH != 0) || (localzzbr.zztm != 0)) && ((localzza.zztH != 0) || (!localzzbu.zztv.zza(localzzbr))))
                    {
                      zzbs localzzbs = localzzbu.zztv;
                      synchronized (localzzbs.zzqp)
                      {
                        if (localzzbs.zzts.size() >= 10)
                        {
                          zzb.d("Queue is full, current size = " + localzzbs.zzts.size());
                          localzzbs.zzts.remove(0);
                        }
                        int i = localzzbs.zztr;
                        localzzbs.zztr = (i + 1);
                        localzzbr.zztn = i;
                        localzzbs.zzts.add(localzzbr);
                        return;
                      }
                    }
                    return;
                  }
                  catch (Exception localException)
                  {
                    zzb.e("Exception in fetchContentOnUIThread", localException);
                    localzzbu.zztw.zza(localException, true);
                  }
                }
              });
            }
          }
          for (;;)
          {
            Thread.sleep(1000 * this.zztx);
            break;
            zzb.d("ContentFetchTask: sleeping");
            synchronized (this.zzqp)
            {
              this.zztt = true;
              zzb.d("ContentFetchThread: paused, mPause = " + this.zztt);
            }
          }
        }
      }
    }
  }
  
  final zza zza(View paramView, final zzbr paramzzbr)
  {
    int i = 0;
    if (paramView == null) {
      return new zza(0, 0);
    }
    if (((paramView instanceof TextView)) && (!(paramView instanceof EditText)))
    {
      CharSequence localCharSequence = ((TextView)paramView).getText();
      if (!TextUtils.isEmpty(localCharSequence))
      {
        paramzzbr.zzx(localCharSequence.toString());
        return new zza(1, 0);
      }
      return new zza(0, 0);
    }
    if (((paramView instanceof WebView)) && (!(paramView instanceof zzjo)))
    {
      paramzzbr.zzcH();
      final WebView localWebView = (WebView)paramView;
      if (!zzq.zzdC(19)) {}
      for (int m = 0; m != 0; m = 1)
      {
        return new zza(0, 1);
        paramzzbr.zzcH();
        localWebView.post(new Runnable()
        {
          ValueCallback<String> zztC = new ValueCallback() {};
          
          public final void run()
          {
            if (localWebView.getSettings().getJavaScriptEnabled()) {}
            try
            {
              localWebView.evaluateJavascript("(function() { return  {text:document.body.innerText}})();", this.zztC);
              return;
            }
            catch (Throwable localThrowable)
            {
              this.zztC.onReceiveValue("");
            }
          }
        });
      }
      return new zza(0, 0);
    }
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int j = 0;
      int k = 0;
      while (i < localViewGroup.getChildCount())
      {
        zza localzza = zza(localViewGroup.getChildAt(i), paramzzbr);
        k += localzza.zztG;
        j += localzza.zztH;
        i++;
      }
      return new zza(k, j);
    }
    return new zza(0, 0);
  }
  
  @zzhb
  final class zza
  {
    final int zztG;
    final int zztH;
    
    zza(int paramInt1, int paramInt2)
    {
      this.zztG = paramInt1;
      this.zztH = paramInt2;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzbu
 * JD-Core Version:    0.7.0.1
 */