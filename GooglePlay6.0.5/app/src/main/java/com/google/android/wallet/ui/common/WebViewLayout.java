package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewStub;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.config.G;
import com.google.android.wallet.uicomponents.R.dimen;
import com.google.android.wallet.uicomponents.R.id;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

public class WebViewLayout
  extends RelativeLayout
  implements View.OnKeyListener, View.OnTouchListener, UiNode, DelegatingWebViewClient.Delegate
{
  private boolean mAttachedToWindow;
  TextView mErrorText;
  View mHeaderView;
  private String mInitialPostBody;
  private String mInitialUrl;
  private boolean mPageLoaded;
  private UiNode mParentUiNode;
  View mProgressBar;
  private final WalletUiElement mUiElement = new WalletUiElement(1630);
  WebView mWebView;
  DelegatingWebViewClient mWebViewClient = new DelegatingWebViewClient(this);
  
  public WebViewLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public WebViewLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public WebViewLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  @TargetApi(21)
  public WebViewLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void loadUrl(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2))
    {
      this.mWebView.loadUrl(paramString1);
      return;
    }
    try
    {
      this.mWebView.postUrl(paramString1, paramString2.getBytes("UTF-8"));
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      if (((Boolean)G.allowPiiLogging.get()).booleanValue()) {
        throw new IllegalStateException("Couldn't post to url: " + paramString1 + " with data: " + paramString2, localUnsupportedEncodingException);
      }
      throw new IllegalStateException("Couldn't post to url: " + paramString1);
    }
  }
  
  public List<UiNode> getChildren()
  {
    if ((this.mHeaderView instanceof UiNode)) {
      return Collections.singletonList((UiNode)this.mHeaderView);
    }
    return null;
  }
  
  public UiNode getParentUiNode()
  {
    return this.mParentUiNode;
  }
  
  public WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public WebView getWebView()
  {
    return this.mWebView;
  }
  
  public final void loadUrlWhenReady(String paramString1, String paramString2)
  {
    if (!this.mAttachedToWindow)
    {
      this.mInitialUrl = paramString1;
      this.mInitialPostBody = paramString2;
      return;
    }
    loadUrl(paramString1, paramString2);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mAttachedToWindow = true;
    if (TextUtils.isEmpty(this.mWebView.getOriginalUrl()))
    {
      this.mPageLoaded = false;
      if (!TextUtils.isEmpty(this.mInitialUrl)) {
        loadUrl(this.mInitialUrl, this.mInitialPostBody);
      }
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mErrorText = ((TextView)findViewById(R.id.error_msg));
    this.mProgressBar = ((ViewStub)findViewById(R.id.progress_bar)).inflate();
    this.mWebView = new SecureWebViewWithKeyboardSupport(getContext(), (byte)0);
    this.mWebView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.mWebView.setId(R.id.web_view_layout_web_view);
    this.mWebView.setVisibility(8);
    this.mWebView.setOnKeyListener(this);
    this.mWebView.setOnTouchListener(this);
    WebSettings localWebSettings = this.mWebView.getSettings();
    localWebSettings.setSupportZoom(true);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setAllowFileAccess(false);
    localWebSettings.setBuiltInZoomControls(true);
    if (Build.VERSION.SDK_INT >= 11) {
      localWebSettings.setDisplayZoomControls(false);
    }
    this.mWebView.setWebViewClient(this.mWebViewClient);
    addView(this.mWebView);
    this.mWebView.requestFocus();
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (this.mWebView.canGoBack()))
    {
      this.mWebView.goBack();
      return true;
    }
    return false;
  }
  
  public final void onPageFinished$49a27f1e(WebView paramWebView)
  {
    this.mProgressBar.setVisibility(8);
    this.mErrorText.setVisibility(8);
    paramWebView.setVisibility(0);
    this.mPageLoaded = true;
  }
  
  public final void onPageStarted$18fffb8c(WebView paramWebView, String paramString)
  {
    if (paramString.equals(this.mInitialUrl))
    {
      this.mProgressBar.setVisibility(0);
      this.mErrorText.setVisibility(8);
      paramWebView.setVisibility(8);
    }
  }
  
  public final void onReceivedError$2433fb81(WebView paramWebView)
  {
    this.mProgressBar.setVisibility(8);
    this.mErrorText.setVisibility(0);
    paramWebView.setVisibility(8);
    this.mPageLoaded = false;
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof Bundle)) {
      super.onRestoreInstanceState(paramParcelable);
    }
    Bundle localBundle;
    do
    {
      return;
      localBundle = (Bundle)paramParcelable;
      super.onRestoreInstanceState(localBundle.getParcelable("superSavedInstanceState"));
      this.mPageLoaded = localBundle.getBoolean("pageLoaded");
    } while ((this.mPageLoaded) && (this.mWebView.restoreState(localBundle) != null));
    this.mPageLoaded = false;
    loadUrl(this.mInitialUrl, this.mInitialPostBody);
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (Build.VERSION.SDK_INT <= 10) {
      switch (paramMotionEvent.getAction())
      {
      }
    }
    for (;;)
    {
      if (paramMotionEvent.getPointerCount() > 1) {
        paramView.getParent().requestDisallowInterceptTouchEvent(true);
      }
      return false;
      if (!paramView.hasFocus()) {
        paramView.requestFocus();
      }
    }
  }
  
  public void setHeaderView(View paramView)
  {
    if (this.mHeaderView != null) {
      throw new IllegalArgumentException("Only a single header view is supported.");
    }
    this.mHeaderView = paramView;
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
    localLayoutParams.bottomMargin = ((int)getResources().getDimension(R.dimen.wallet_uic_web_view_layout_header_bottom_margin));
    this.mHeaderView.setLayoutParams(localLayoutParams);
    ((RelativeLayout.LayoutParams)this.mWebView.getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.web_view_layout_header);
    ((RelativeLayout.LayoutParams)this.mProgressBar.getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.web_view_layout_header);
    ((RelativeLayout.LayoutParams)this.mErrorText.getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.web_view_layout_header);
    this.mHeaderView.setId(R.id.web_view_layout_header);
    addView(this.mHeaderView, 0);
  }
  
  public void setParentUiNode(UiNode paramUiNode)
  {
    this.mParentUiNode = paramUiNode;
  }
  
  public void setUserAgent(String paramString)
  {
    this.mWebView.getSettings().setUserAgentString(paramString);
  }
  
  public void setWebViewClient(DelegatingWebViewClient paramDelegatingWebViewClient)
  {
    if (paramDelegatingWebViewClient != null) {}
    for (;;)
    {
      this.mWebViewClient = paramDelegatingWebViewClient;
      this.mWebViewClient.mDelegate = this;
      if (this.mWebView != null) {
        this.mWebView.setWebViewClient(this.mWebViewClient);
      }
      return;
      paramDelegatingWebViewClient = new DelegatingWebViewClient();
    }
  }
  
  private static final class SecureWebViewWithKeyboardSupport
    extends WebView
  {
    private SecureWebViewWithKeyboardSupport(Context paramContext)
    {
      super();
    }
    
    @TargetApi(11)
    protected final void onAttachedToWindow()
    {
      super.onAttachedToWindow();
      if ((Build.VERSION.SDK_INT >= 11) && (Build.VERSION.SDK_INT < 19))
      {
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
      }
    }
    
    public final boolean onCheckIsTextEditor()
    {
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.WebViewLayout
 * JD-Core Version:    0.7.0.1
 */