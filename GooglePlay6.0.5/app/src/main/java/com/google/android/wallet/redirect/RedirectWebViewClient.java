package com.google.android.wallet.redirect;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.analytics.WebViewPageLoadEvent;
import com.google.android.wallet.common.util.AndroidUtils;
import com.google.android.wallet.config.G.redirectwebviewlogs;
import com.google.android.wallet.ui.common.DelegatingWebViewClient;
import com.google.android.wallet.ui.common.FormEventListener;
import com.google.android.wallet.uicomponents.R.raw;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RedirectWebViewClient
  extends DelegatingWebViewClient
{
  private static String sInterceptJs;
  final HashSet<String> mAlreadyLoggedUrls;
  private DisplayMetrics mCachedDisplayMetrics;
  private int mCachedOrientation = -1;
  private final Context mContext;
  public FormEventListener mFormEventListener;
  private HtmlFormContent mInterceptedFormContent;
  private final Handler mMainThreadHandler;
  private final boolean mMustEnforceWhitelist;
  private final Pattern mNonTerminalUrlPattern;
  public RedirectListener mRedirectListener;
  final SimpleArrayMap<String, Long> mStartTimeMap;
  private final Pattern mTerminalUrlPattern;
  final HashSet<String> mTopLevelUrls;
  private final ArrayList<Pattern> mWhitelistPatterns;
  
  @TargetApi(11)
  public RedirectWebViewClient(Context paramContext, WebView paramWebView, String paramString1, String paramString2, boolean paramBoolean, String[] paramArrayOfString)
  {
    if (Build.VERSION.SDK_INT < 14) {
      throw new IllegalStateException("RedirectWebViewClient is not supported on API < 14.");
    }
    this.mContext = paramContext;
    this.mMainThreadHandler = new Handler();
    Pattern localPattern1;
    Pattern localPattern2;
    if (TextUtils.isEmpty(paramString1))
    {
      localPattern1 = null;
      this.mNonTerminalUrlPattern = localPattern1;
      boolean bool = TextUtils.isEmpty(paramString2);
      localPattern2 = null;
      if (!bool) {
        break label165;
      }
    }
    for (;;)
    {
      this.mTerminalUrlPattern = localPattern2;
      this.mMustEnforceWhitelist = paramBoolean;
      this.mWhitelistPatterns = buildWhitelist(paramArrayOfString);
      this.mStartTimeMap = new SimpleArrayMap(25);
      this.mAlreadyLoggedUrls = new HashSet(25);
      this.mTopLevelUrls = new HashSet(5);
      if (Build.VERSION.SDK_INT >= 19) {
        paramWebView.addJavascriptInterface(new InterceptJavascriptInterface(this), "comGoogleAndroidWalletInterceptor");
      }
      return;
      localPattern1 = Pattern.compile(paramString1);
      break;
      label165:
      localPattern2 = Pattern.compile(paramString2);
    }
  }
  
  private static ArrayList<Pattern> buildWhitelist(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfString.length);
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++) {
      localArrayList.add(Pattern.compile(paramArrayOfString[j]));
    }
    return localArrayList;
  }
  
  @TargetApi(11)
  private static WebResourceResponse createOkResponse()
  {
    Charset localCharset = Charset.defaultCharset();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream("".getBytes(localCharset));
    return new WebResourceResponse("text/html", localCharset.name(), localByteArrayInputStream);
  }
  
  private void logPageLoad(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    if ((this.mFormEventListener == null) || (this.mAlreadyLoggedUrls.contains(paramString1)) || (!((Boolean)G.redirectwebviewlogs.loggingEnabled.get()).booleanValue())) {
      return;
    }
    this.mAlreadyLoggedUrls.add(paramString1);
    WebViewPageLoadEvent localWebViewPageLoadEvent = new WebViewPageLoadEvent();
    Uri.Builder localBuilder = Uri.parse(paramString1).buildUpon();
    localBuilder.query(null);
    localBuilder.fragment(null);
    localWebViewPageLoadEvent.url = localBuilder.build().toString();
    if (this.mStartTimeMap.containsKey(paramString1)) {}
    for (long l = ((Long)this.mStartTimeMap.get(paramString1)).longValue();; l = -1L)
    {
      localWebViewPageLoadEvent.startTimestampMs = l;
      localWebViewPageLoadEvent.errorCode = paramInt2;
      if (paramString2 != null) {
        localWebViewPageLoadEvent.errorDescription = paramString2;
      }
      localWebViewPageLoadEvent.orientation = this.mContext.getResources().getConfiguration().orientation;
      DisplayMetrics localDisplayMetrics = this.mCachedDisplayMetrics;
      if ((localDisplayMetrics == null) || (localWebViewPageLoadEvent.orientation != this.mCachedOrientation))
      {
        localDisplayMetrics = AndroidUtils.getDisplayMetrics(this.mContext);
        this.mCachedOrientation = localWebViewPageLoadEvent.orientation;
        this.mCachedDisplayMetrics = localDisplayMetrics;
      }
      localWebViewPageLoadEvent.screenWidthPx = localDisplayMetrics.widthPixels;
      localWebViewPageLoadEvent.screenHeightPx = localDisplayMetrics.heightPixels;
      localWebViewPageLoadEvent.screenXDpi = localDisplayMetrics.xdpi;
      localWebViewPageLoadEvent.screenYDpi = localDisplayMetrics.ydpi;
      Bundle localBundle = new Bundle();
      localBundle.putInt("FormEventListener.EXTRA_BACKGROUND_EVENT_TYPE", 772);
      localBundle.putParcelable("FormEventListener.EXTRA_BACKGROUND_EVENT_DATA", localWebViewPageLoadEvent);
      localBundle.putInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", paramInt1);
      this.mFormEventListener.notifyFormEvent(7, localBundle);
      return;
    }
  }
  
  private void notifyListenerUrlIntercepted(final int paramInt, final RedirectListener paramRedirectListener, final String paramString, final HtmlFormContent paramHtmlFormContent)
  {
    if (paramRedirectListener == null) {
      return;
    }
    this.mMainThreadHandler.post(new Runnable()
    {
      public final void run()
      {
        switch (paramInt)
        {
        default: 
          throw new IllegalArgumentException("Unknown interception type: " + paramInt);
        case 1: 
          paramRedirectListener.onTerminalUrlIntercepted(paramString, paramHtmlFormContent);
          return;
        case 2: 
          paramRedirectListener.onNonTerminalUrlIntercepted(paramString, paramHtmlFormContent);
          return;
        }
        paramRedirectListener.onNonWhitelistedUrlIntercepted$552c4e01();
      }
    });
  }
  
  private static String readUtf8AndClose(InputStream paramInputStream)
    throws IOException
  {
    localStringBuilder = new StringBuilder(200);
    localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, "UTF-8"));
    try
    {
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        localStringBuilder.append(str);
      }
      try
      {
        localBufferedReader.close();
        throw localObject;
        try
        {
          localBufferedReader.close();
          return localStringBuilder.toString();
        }
        catch (IOException localIOException2)
        {
          break label62;
        }
      }
      catch (IOException localIOException1)
      {
        break label56;
      }
    }
    finally {}
  }
  
  public final void onLoadResource(WebView paramWebView, String paramString)
  {
    super.onLoadResource(paramWebView, paramString);
    if (this.mTopLevelUrls.contains(paramString)) {
      return;
    }
    if (((Boolean)G.redirectwebviewlogs.logResourceRequests.get()).booleanValue()) {
      logPageLoad(0, paramString, 0, null);
    }
    this.mStartTimeMap.remove(paramString);
    this.mAlreadyLoggedUrls.remove(paramString);
  }
  
  public final void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    if (paramString.startsWith("http"))
    {
      this.mInterceptedFormContent = null;
      if (sInterceptJs != null) {}
    }
    try
    {
      sInterceptJs = readUtf8AndClose(this.mContext.getResources().openRawResource(R.raw.redirect_intercept));
      paramWebView.loadUrl("javascript:" + sInterceptJs);
      logPageLoad(0, paramString, 0, null);
      this.mStartTimeMap.remove(paramString);
      this.mAlreadyLoggedUrls.remove(paramString);
      this.mTopLevelUrls.remove(paramString);
      return;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Failed to load intercept js file.", localIOException);
    }
  }
  
  public final void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    super.onPageStarted(paramWebView, paramString, paramBitmap);
    this.mTopLevelUrls.add(paramString);
  }
  
  public final void onReceivedError(WebView paramWebView, final int paramInt, final String paramString1, final String paramString2)
  {
    super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    logPageLoad(40, paramString2, paramInt, paramString1);
    final RedirectListener localRedirectListener = this.mRedirectListener;
    if (localRedirectListener != null) {
      this.mMainThreadHandler.post(new Runnable()
      {
        public final void run()
        {
          localRedirectListener.onErrorReceived$2498c652(paramInt);
        }
      });
    }
  }
  
  public final WebResourceResponse shouldInterceptRequest(WebView paramWebView, String paramString)
  {
    this.mStartTimeMap.put(paramString, Long.valueOf(System.currentTimeMillis()));
    int i;
    if ((this.mNonTerminalUrlPattern != null) && (this.mNonTerminalUrlPattern.matcher(paramString).matches()))
    {
      i = 1;
      if (i == 0) {
        break label108;
      }
      if ((this.mInterceptedFormContent == null) || (!this.mInterceptedFormContent.isValid())) {
        break label102;
      }
    }
    label102:
    for (int i1 = 1;; i1 = 0)
    {
      if (i1 == 0) {
        break label108;
      }
      notifyListenerUrlIntercepted(2, this.mRedirectListener, paramString, this.mInterceptedFormContent);
      logPageLoad(41, paramString, 0, null);
      return createOkResponse();
      i = 0;
      break;
    }
    label108:
    if ((this.mTerminalUrlPattern != null) && (this.mTerminalUrlPattern.matcher(paramString).matches())) {}
    for (int j = 1; j != 0; j = 0)
    {
      notifyListenerUrlIntercepted(1, this.mRedirectListener, paramString, this.mInterceptedFormContent);
      logPageLoad(42, paramString, 0, null);
      return createOkResponse();
    }
    int k = this.mWhitelistPatterns.size();
    int m = 0;
    int n;
    if (m < k) {
      if (((Pattern)this.mWhitelistPatterns.get(m)).matcher(paramString).matches()) {
        n = 1;
      }
    }
    for (;;)
    {
      if (n == 0)
      {
        if (this.mMustEnforceWhitelist)
        {
          notifyListenerUrlIntercepted(3, this.mRedirectListener, paramString, null);
          logPageLoad(43, paramString, 0, null);
          return createOkResponse();
          m++;
          break;
          n = 0;
          continue;
        }
        logPageLoad(44, paramString, 0, null);
      }
    }
    return super.shouldInterceptRequest(paramWebView, paramString);
  }
  
  public static abstract interface RedirectListener
  {
    public abstract void onErrorReceived$2498c652(int paramInt);
    
    public abstract void onNonTerminalUrlIntercepted(String paramString, HtmlFormContent paramHtmlFormContent);
    
    public abstract void onNonWhitelistedUrlIntercepted$552c4e01();
    
    public abstract void onTerminalUrlIntercepted(String paramString, HtmlFormContent paramHtmlFormContent);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.redirect.RedirectWebViewClient
 * JD-Core Version:    0.7.0.1
 */