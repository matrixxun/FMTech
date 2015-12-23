package com.google.android.gms.internal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
import android.view.View;
import android.view.WindowManager.BadTokenException;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;

@zzhb
public class zzju
  extends WebChromeClient
{
  private final zzjo zzpX;
  
  public zzju(zzjo paramzzjo)
  {
    this.zzpX = paramzzjo;
  }
  
  private static Context zza(WebView paramWebView)
  {
    Object localObject;
    if (!(paramWebView instanceof zzjo)) {
      localObject = paramWebView.getContext();
    }
    zzjo localzzjo;
    do
    {
      return localObject;
      localzzjo = (zzjo)paramWebView;
      localObject = localzzjo.zzhF();
    } while (localObject != null);
    return localzzjo.getContext();
  }
  
  private static boolean zza(Context paramContext, String paramString1, String paramString2, String paramString3, JsResult paramJsResult, JsPromptResult paramJsPromptResult, boolean paramBoolean)
  {
    try
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
      localBuilder.setTitle(paramString1);
      if (paramBoolean)
      {
        LinearLayout localLinearLayout = new LinearLayout(paramContext);
        localLinearLayout.setOrientation(1);
        TextView localTextView = new TextView(paramContext);
        localTextView.setText(paramString2);
        final EditText localEditText = new EditText(paramContext);
        localEditText.setText(paramString3);
        localLinearLayout.addView(localTextView);
        localLinearLayout.addView(localEditText);
        localBuilder.setView(localLinearLayout).setPositiveButton(17039370, new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            this.zzNh.confirm(localEditText.getText().toString());
          }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            this.zzNh.cancel();
          }
        }).setOnCancelListener(new DialogInterface.OnCancelListener()
        {
          public final void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            this.zzNh.cancel();
          }
        }).create().show();
        return true;
      }
      localBuilder.setMessage(paramString2).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          this.zzNg.confirm();
        }
      }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          this.zzNg.cancel();
        }
      }).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public final void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          this.zzNg.cancel();
        }
      }).create().show();
      return true;
    }
    catch (WindowManager.BadTokenException localBadTokenException)
    {
      zzb.w("Fail to display Dialog.", localBadTokenException);
    }
    return true;
  }
  
  public final void onCloseWindow(WebView paramWebView)
  {
    if (!(paramWebView instanceof zzjo))
    {
      zzb.w("Tried to close a WebView that wasn't an AdWebView.");
      return;
    }
    zzd localzzd = ((zzjo)paramWebView).zzhI();
    if (localzzd == null)
    {
      zzb.w("Tried to close an AdWebView not associated with an overlay.");
      return;
    }
    localzzd.close();
  }
  
  public final boolean onConsoleMessage(ConsoleMessage paramConsoleMessage)
  {
    String str = "JS: " + paramConsoleMessage.message() + " (" + paramConsoleMessage.sourceId() + ":" + paramConsoleMessage.lineNumber() + ")";
    if (str.contains("Application Cache")) {
      return super.onConsoleMessage(paramConsoleMessage);
    }
    switch (7.zzNj[paramConsoleMessage.messageLevel().ordinal()])
    {
    default: 
      zzb.i(str);
    }
    for (;;)
    {
      return super.onConsoleMessage(paramConsoleMessage);
      zzb.e(str);
      continue;
      zzb.w(str);
      continue;
      zzb.i(str);
      continue;
      zzb.d(str);
    }
  }
  
  public final boolean onCreateWindow(WebView paramWebView, boolean paramBoolean1, boolean paramBoolean2, Message paramMessage)
  {
    WebView.WebViewTransport localWebViewTransport = (WebView.WebViewTransport)paramMessage.obj;
    WebView localWebView = new WebView(paramWebView.getContext());
    localWebView.setWebViewClient(this.zzpX.zzhK());
    localWebViewTransport.setWebView(localWebView);
    paramMessage.sendToTarget();
    return true;
  }
  
  public final void onExceededDatabaseQuota(String paramString1, String paramString2, long paramLong1, long paramLong2, long paramLong3, WebStorage.QuotaUpdater paramQuotaUpdater)
  {
    long l = 5242880L - paramLong3;
    if (l <= 0L)
    {
      paramQuotaUpdater.updateQuota(paramLong1);
      return;
    }
    if (paramLong1 == 0L)
    {
      if ((paramLong2 <= l) && (paramLong2 <= 1048576L)) {}
      for (;;)
      {
        paramQuotaUpdater.updateQuota(paramLong2);
        return;
        paramLong2 = 0L;
      }
    }
    if (paramLong2 == 0L) {
      paramLong1 = Math.min(paramLong1 + Math.min(131072L, l), 1048576L);
    }
    for (;;)
    {
      paramLong2 = paramLong1;
      break;
      if (paramLong2 <= Math.min(1048576L - paramLong1, l)) {
        paramLong1 += paramLong2;
      }
    }
  }
  
  public final void onGeolocationPermissionsShowPrompt(String paramString, GeolocationPermissions.Callback paramCallback)
  {
    if (paramCallback != null)
    {
      zzp.zzbI();
      if (!zziq.zza(this.zzpX.getContext().getPackageManager(), this.zzpX.getContext().getPackageName(), "android.permission.ACCESS_FINE_LOCATION"))
      {
        zzp.zzbI();
        if (!zziq.zza(this.zzpX.getContext().getPackageManager(), this.zzpX.getContext().getPackageName(), "android.permission.ACCESS_COARSE_LOCATION")) {
          break label92;
        }
      }
    }
    label92:
    for (boolean bool = true;; bool = false)
    {
      paramCallback.invoke(paramString, bool, true);
      return;
    }
  }
  
  public final void onHideCustomView()
  {
    zzd localzzd = this.zzpX.zzhI();
    if (localzzd == null)
    {
      zzb.w("Could not get ad overlay when hiding custom view.");
      return;
    }
    localzzd.zzfj();
  }
  
  public final boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
  {
    return zza(zza(paramWebView), paramString1, paramString2, null, paramJsResult, null, false);
  }
  
  public final boolean onJsBeforeUnload(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
  {
    return zza(zza(paramWebView), paramString1, paramString2, null, paramJsResult, null, false);
  }
  
  public final boolean onJsConfirm(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
  {
    return zza(zza(paramWebView), paramString1, paramString2, null, paramJsResult, null, false);
  }
  
  public final boolean onJsPrompt(WebView paramWebView, String paramString1, String paramString2, String paramString3, JsPromptResult paramJsPromptResult)
  {
    return zza(zza(paramWebView), paramString1, paramString2, paramString3, null, paramJsPromptResult, true);
  }
  
  public final void onReachedMaxAppCacheSize(long paramLong1, long paramLong2, WebStorage.QuotaUpdater paramQuotaUpdater)
  {
    long l1 = 5242880L - paramLong2;
    long l2 = 131072L + paramLong1;
    if (l1 < l2)
    {
      paramQuotaUpdater.updateQuota(0L);
      return;
    }
    paramQuotaUpdater.updateQuota(l2);
  }
  
  public final void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
  {
    zza(paramView, -1, paramCustomViewCallback);
  }
  
  protected final void zza(View paramView, int paramInt, WebChromeClient.CustomViewCallback paramCustomViewCallback)
  {
    zzd localzzd = this.zzpX.zzhI();
    if (localzzd == null)
    {
      zzb.w("Could not get ad overlay when showing custom view.");
      paramCustomViewCallback.onCustomViewHidden();
      return;
    }
    localzzd.zzDJ = new FrameLayout(localzzd.mActivity);
    localzzd.zzDJ.setBackgroundColor(-16777216);
    localzzd.zzDJ.addView(paramView, -1, -1);
    localzzd.mActivity.setContentView(localzzd.zzDJ);
    localzzd.zzDP = true;
    localzzd.zzDK = paramCustomViewCallback;
    localzzd.zzDI = true;
    localzzd.setRequestedOrientation(paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzju
 * JD-Core Version:    0.7.0.1
 */