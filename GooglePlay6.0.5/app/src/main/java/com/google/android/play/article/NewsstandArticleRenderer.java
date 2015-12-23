package com.google.android.play.article;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NewsstandArticleRenderer
  extends WebView
{
  private static final String LOG_TAG = NewsstandArticleRenderer.class.getSimpleName();
  private static boolean sDidSetRenderPriority;
  OnArticleScrollListener mOnArticleScrollListener;
  
  public NewsstandArticleRenderer(Context paramContext)
  {
    this(paramContext, (byte)0);
  }
  
  private NewsstandArticleRenderer(Context paramContext, byte paramByte)
  {
    super(paramContext, null, 0);
    setWebChromeClient(new WebChromeClient());
    setWebViewClient(new NewsstandWebViewClient((byte)0));
    WebSettings localWebSettings = getSettings();
    localWebSettings.setAllowFileAccess(false);
    localWebSettings.setAppCacheEnabled(false);
    localWebSettings.setCacheMode(2);
    localWebSettings.setDatabaseEnabled(false);
    localWebSettings.setDomStorageEnabled(true);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setPluginState(WebSettings.PluginState.OFF);
    localWebSettings.setSupportZoom(true);
    localWebSettings.setUseWideViewPort(true);
    localWebSettings.setLoadWithOverviewMode(false);
    localWebSettings.setUserAgentString(localWebSettings.getUserAgentString() + " OnyxArticleView/1.0");
    if (!sDidSetRenderPriority)
    {
      sDidSetRenderPriority = true;
      localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    }
    setOnLongClickListener(new View.OnLongClickListener()
    {
      public final boolean onLongClick(View paramAnonymousView)
      {
        return true;
      }
    });
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  private static final class NewsstandWebViewClient
    extends WebViewClient
  {
    private static final Pattern sHttpPattern = Pattern.compile("https?", 2);
    
    private static boolean showInBrowser(Context paramContext, String paramString)
    {
      Uri localUri = Uri.parse(paramString);
      boolean bool1 = sHttpPattern.matcher(localUri.getScheme()).matches();
      boolean bool2 = false;
      Intent localIntent;
      if (bool1)
      {
        localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setFlags(268435456);
        localIntent.addCategory("android.intent.category.BROWSABLE");
        localIntent.setData(localUri);
      }
      try
      {
        paramContext.startActivity(localIntent);
        bool2 = true;
        return bool2;
      }
      catch (ActivityNotFoundException localActivityNotFoundException) {}
      return false;
    }
    
    public final boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      return (!TextUtils.isEmpty(paramString)) && (showInBrowser(paramWebView.getContext(), paramString));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.article.NewsstandArticleRenderer
 * JD-Core Version:    0.7.0.1
 */