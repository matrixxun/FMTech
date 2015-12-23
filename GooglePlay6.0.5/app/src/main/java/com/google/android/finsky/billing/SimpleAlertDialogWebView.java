package com.google.android.finsky.billing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.android.finsky.activities.SimpleAlertDialog.ConfigurableView;

public class SimpleAlertDialogWebView
  extends FrameLayout
  implements SimpleAlertDialog.ConfigurableView
{
  private String mUrl;
  
  public SimpleAlertDialogWebView(Context paramContext)
  {
    super(paramContext);
  }
  
  public SimpleAlertDialogWebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SimpleAlertDialogWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public final void configureView(Bundle paramBundle)
  {
    this.mUrl = paramBundle.getString("url_key");
    WebView localWebView = (WebView)findViewById(2131755273);
    localWebView.getSettings().setBuiltInZoomControls(true);
    localWebView.loadUrl(this.mUrl);
    localWebView.setWebViewClient(new WebViewClient()
    {
      public final void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
        SimpleAlertDialogWebView.this.findViewById(2131755289).setVisibility(8);
        paramAnonymousWebView.setVisibility(0);
      }
      
      public final void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        super.onPageStarted(paramAnonymousWebView, paramAnonymousString, paramAnonymousBitmap);
        if (paramAnonymousString.equals(SimpleAlertDialogWebView.this.mUrl))
        {
          SimpleAlertDialogWebView.this.findViewById(2131755289).setVisibility(0);
          paramAnonymousWebView.setVisibility(8);
        }
      }
      
      public final void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
      {
        super.onReceivedError(paramAnonymousWebView, paramAnonymousInt, paramAnonymousString1, paramAnonymousString2);
        paramAnonymousWebView.setVisibility(8);
        SimpleAlertDialogWebView.this.findViewById(2131755289).setVisibility(8);
        SimpleAlertDialogWebView.this.findViewById(2131755274).setVisibility(8);
      }
    });
  }
  
  public Bundle getResult()
  {
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.SimpleAlertDialogWebView
 * JD-Core Version:    0.7.0.1
 */