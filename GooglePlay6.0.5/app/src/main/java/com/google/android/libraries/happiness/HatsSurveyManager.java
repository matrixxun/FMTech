package com.google.android.libraries.happiness;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HatsSurveyManager
{
  private static final String TAG = HatsSurveyManager.class.getSimpleName();
  protected HatsSurveyClientImpl mClient;
  private final Context mContext;
  private HatsSurveyDialog mDialog;
  private final Handler mHandler;
  private final int mLayout;
  private final int mLayoutId;
  protected HatsSurveyParams mParams;
  protected final WebView mWebView;
  
  public HatsSurveyManager(Context paramContext, HatsSurveyClient paramHatsSurveyClient, HatsSurveyParams paramHatsSurveyParams)
  {
    if (paramContext == null) {
      throw new NullPointerException();
    }
    if (paramHatsSurveyClient == null) {
      throw new NullPointerException();
    }
    this.mContext = paramContext;
    this.mWebView = new WebView(this.mContext);
    this.mHandler = new Handler(this.mContext.getMainLooper());
    this.mParams = paramHatsSurveyParams;
    this.mClient = new HatsSurveyClientImpl(paramHatsSurveyClient, this.mHandler, this.mWebView);
    this.mLayout = 2130969130;
    this.mLayoutId = 2131756156;
    WebSettings localWebSettings = this.mWebView.getSettings();
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setDatabaseEnabled(false);
    localWebSettings.setDomStorageEnabled(true);
    String str1 = this.mParams.getParam("user_agent");
    if (str1 != null) {
      localWebSettings.setUserAgentString(str1);
    }
    this.mWebView.addJavascriptInterface(this.mClient, "_402m_native");
    this.mWebView.setOnLongClickListener(new View.OnLongClickListener()
    {
      public final boolean onLongClick(View paramAnonymousView)
      {
        return true;
      }
    });
    this.mWebView.setWebChromeClient(new WebChromeClient()
    {
      public final boolean onConsoleMessage(ConsoleMessage paramAnonymousConsoleMessage)
      {
        return true;
      }
    });
    this.mWebView.setWebViewClient(new WebViewClient()
    {
      public final void onLoadResource(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        super.onLoadResource(paramAnonymousWebView, paramAnonymousString);
      }
      
      public final void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
      {
        Log.w(HatsSurveyManager.TAG, paramAnonymousString1 + " : " + paramAnonymousString2);
      }
    });
    String str2 = this.mParams.getParam("survey_url");
    if (str2 != null) {}
    label476:
    for (;;)
    {
      try
      {
        String str3 = new URL(str2).getHost().toLowerCase();
        int i = str3.indexOf("google.");
        int j = 0;
        if (i < 0)
        {
          String str4 = "." + str3.substring(j);
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss zzz", Locale.US);
          localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
          Calendar localCalendar = Calendar.getInstance();
          localCalendar.add(1, 1);
          String str5 = localSimpleDateFormat.format(Long.valueOf(localCalendar.getTimeInMillis()));
          str6 = this.mParams.getParam("zwieback_cookie");
          if (str6 != null)
          {
            if (!str6.startsWith("NID=")) {
              break label476;
            }
            CookieSyncManager.createInstance(this.mWebView.getContext().getApplicationContext());
            CookieManager localCookieManager = CookieManager.getInstance();
            localCookieManager.setAcceptCookie(true);
            localCookieManager.setCookie(str4, str6 + "; expires=" + str5 + "; path=/; domain=" + str4 + "; HttpOnly");
            CookieSyncManager.getInstance().sync();
          }
        }
        else
        {
          j = i;
          continue;
        }
        String str6 = "NID=" + str6;
      }
      catch (MalformedURLException localMalformedURLException)
      {
        throw new IllegalArgumentException("Value for HatsSurveyParams.SURVEY_URL_KEY is an invalid URL: " + str2);
      }
    }
  }
  
  private static String wrapNativeCallbackJS(String paramString, String[] paramArrayOfString)
  {
    if (paramArrayOfString == null) {}
    for (String str = "";; str = TextUtils.join(", ", Arrays.asList(paramArrayOfString))) {
      return String.format("_402m['%s'] = function(%s) { _402m_native.%s(%s); };\n", new Object[] { paramString, str, paramString, str });
    }
  }
  
  public final void declineSurvey()
  {
    this.mWebView.loadUrl("javascript:try { _402.close(true) } catch(e) {}");
  }
  
  public final DialogFragment getSurveyDialog()
  {
    if (this.mDialog == null)
    {
      this.mDialog = new HatsSurveyDialog();
      Bundle localBundle = new Bundle();
      this.mDialog.setArguments(localBundle);
      this.mDialog.mOnCancelAction = new Runnable()
      {
        public final void run()
        {
          if (!HatsSurveyManager.this.mClient.isComplete())
          {
            HatsSurveyManager.this.declineSurvey();
            HatsSurveyManager.this.mClient.onSurveyCanceled();
          }
        }
      };
      HatsSurveyDialog localHatsSurveyDialog1 = this.mDialog;
      localHatsSurveyDialog1.mWebView = this.mWebView;
      localHatsSurveyDialog1.bindWebview();
      HatsSurveyDialog localHatsSurveyDialog2 = this.mDialog;
      localHatsSurveyDialog2.mStyle = 2;
      if ((localHatsSurveyDialog2.mStyle == 2) || (localHatsSurveyDialog2.mStyle == 3)) {
        localHatsSurveyDialog2.mTheme = 16973913;
      }
      localHatsSurveyDialog2.mTheme = 16973913;
    }
    return this.mDialog;
  }
  
  public final void requestSurvey()
  {
    if (Build.VERSION.SDK_INT >= 11) {
      this.mWebView.onResume();
    }
    this.mWebView.loadUrl("about:blank");
    String str1 = this.mParams.getParam("site_id");
    String str2 = "<!doctype><html><head><meta name=\"viewport\" content=\"initial-scale=1.0,user-scalable=no\"><script>_402m = {};" + wrapNativeCallbackJS("onWindowError", null) + "window.onerror=function(){_402m.onWindowError();};" + wrapNativeCallbackJS("onSurveyReady", null) + wrapNativeCallbackJS("onSurveyComplete", new String[] { "justAnswered", "unused" }) + wrapNativeCallbackJS("onSurveyCanceled", null) + "_402m['onSurveyResponse'] = function(response) {var surveyId = _402.params.svyid;_402m_native.onSurveyResponse(response, surveyId);};\n" + this.mParams.toJS("_402m") + "</script>" + String.format("<script src=\"%s?site=%s&force_http=1\"></script>", new Object[] { this.mParams.getParam("survey_url"), str1 }) + "</head><body></body></html>";
    this.mWebView.loadData(str2, "text/html", null);
  }
  
  protected static class HatsSurveyClientImpl
    implements HatsSurveyClient
  {
    private boolean isComplete = false;
    private final HatsSurveyClient mClient;
    private final Handler mHandler;
    private final WebView mWebView;
    
    public HatsSurveyClientImpl(HatsSurveyClient paramHatsSurveyClient, Handler paramHandler, WebView paramWebView)
    {
      this.mClient = paramHatsSurveyClient;
      this.mHandler = paramHandler;
      this.mWebView = paramWebView;
    }
    
    public boolean isComplete()
    {
      return this.isComplete;
    }
    
    @JavascriptInterface
    public void onSurveyCanceled()
    {
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          HatsSurveyManager.HatsSurveyClientImpl.this.mClient.onSurveyCanceled();
        }
      });
    }
    
    @JavascriptInterface
    public void onSurveyComplete(final boolean paramBoolean1, final boolean paramBoolean2)
    {
      this.isComplete = true;
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          HatsSurveyManager.HatsSurveyClientImpl.this.mClient.onSurveyComplete(paramBoolean1, paramBoolean2);
        }
      });
    }
    
    @JavascriptInterface
    public void onSurveyReady()
    {
      this.isComplete = false;
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          HatsSurveyManager.HatsSurveyClientImpl.this.mClient.onSurveyReady();
        }
      });
    }
    
    @JavascriptInterface
    public void onSurveyResponse(final String paramString1, final String paramString2)
    {
      if (!paramString1.contains("t=a")) {
        return;
      }
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          HatsSurveyManager.HatsSurveyClientImpl.this.mClient.onSurveyResponse(paramString1, paramString2);
        }
      });
    }
    
    @JavascriptInterface
    public void onWindowError()
    {
      this.isComplete = false;
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          HatsSurveyManager.HatsSurveyClientImpl.this.mClient.onWindowError();
        }
      });
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.happiness.HatsSurveyManager
 * JD-Core Version:    0.7.0.1
 */