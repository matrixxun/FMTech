package com.google.android.wallet.ui.common;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DelegatingWebViewClient
  extends WebViewClient
{
  Delegate mDelegate;
  
  public DelegatingWebViewClient() {}
  
  public DelegatingWebViewClient(Delegate paramDelegate)
  {
    this.mDelegate = paramDelegate;
  }
  
  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    if (this.mDelegate != null) {
      this.mDelegate.onPageFinished$49a27f1e(paramWebView);
    }
  }
  
  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    super.onPageStarted(paramWebView, paramString, paramBitmap);
    if (this.mDelegate != null) {
      this.mDelegate.onPageStarted$18fffb8c(paramWebView, paramString);
    }
  }
  
  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    if (this.mDelegate != null) {
      this.mDelegate.onReceivedError$2433fb81(paramWebView);
    }
  }
  
  public static abstract interface Delegate
  {
    public abstract void onPageFinished$49a27f1e(WebView paramWebView);
    
    public abstract void onPageStarted$18fffb8c(WebView paramWebView, String paramString);
    
    public abstract void onReceivedError$2433fb81(WebView paramWebView);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.DelegatingWebViewClient
 * JD-Core Version:    0.7.0.1
 */