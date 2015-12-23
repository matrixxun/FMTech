package com.google.android.finsky.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.layout.actionbar.ActionBarHelper;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar;
import com.google.android.finsky.layout.actionbar.FinskySearchToolbar.Configurator;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.protos.ChallengeProto.WebViewChallenge;
import com.google.android.finsky.utils.ParcelableProto;
import java.io.ByteArrayInputStream;

public class WebViewChallengeActivity
  extends ActionBarActivity
  implements ButtonBar.ClickListener
{
  private ChallengeProto.WebViewChallenge mChallenge;
  private boolean mIsFirstPageLoad = true;
  private FinskyEventLog mLogger;
  private final FakeNavigationManager mNavigationManager = new FakeNavigationManager(this)
  {
    public final boolean goUp()
    {
      WebViewChallengeActivity.this.cancel(null);
      return true;
    }
  };
  private GenericUiElementNode mNode;
  private WebView mWebView;
  
  private void cancel(String paramString)
  {
    PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = null;
    if (paramString != null)
    {
      localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
      localPlayStoreUiElementInfo.host = Uri.parse(paramString).getHost();
      localPlayStoreUiElementInfo.hasHost = true;
    }
    this.mLogger.logClickEventWithClientCookie(268, localPlayStoreUiElementInfo, this.mNode);
    setResult(0);
    finish();
  }
  
  public static Intent createIntent$14ebf4f1(String paramString, ChallengeProto.WebViewChallenge paramWebViewChallenge)
  {
    Intent localIntent = new Intent(FinskyApp.get(), WebViewChallengeActivity.class);
    localIntent.putExtra("authAccount", paramString);
    localIntent.putExtra("backupTitle", null);
    localIntent.putExtra("challenge", ParcelableProto.forProto(paramWebViewChallenge));
    return localIntent;
  }
  
  public void onBackPressed()
  {
    if (this.mWebView.canGoBack())
    {
      this.mWebView.goBack();
      return;
    }
    cancel(null);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    this.mChallenge = ((ChallengeProto.WebViewChallenge)ParcelableProto.getProtoFromIntent(getIntent(), "challenge"));
    String str1;
    ButtonBar localButtonBar;
    if (this.mChallenge.hasTitle)
    {
      str1 = this.mChallenge.title;
      if (TextUtils.isEmpty(str1)) {
        requestWindowFeature(1);
      }
      super.onCreate(paramBundle);
      setContentView(2130968710);
      Toolbar localToolbar = (Toolbar)findViewById(2131755196);
      if (localToolbar != null)
      {
        if ((localToolbar instanceof FinskySearchToolbar)) {
          ((FinskySearchToolbar)localToolbar).configure(new FinskySearchToolbar.Configurator(this));
        }
        setSupportActionBar(localToolbar);
      }
      FrameLayout localFrameLayout = (FrameLayout)findViewById(2131755234);
      getLayoutInflater().inflate(2130968638, localFrameLayout);
      String str2 = getIntent().getStringExtra("authAccount");
      this.mLogger = FinskyApp.get().getEventLogger(str2);
      ActionBarHelper localActionBarHelper = new ActionBarHelper(this.mNavigationManager, this);
      localActionBarHelper.updateCurrentBackendId(0, false);
      localActionBarHelper.updateActionBarMode(false, -1);
      localButtonBar = (ButtonBar)findViewById(2131755300);
      localButtonBar.setPositiveButtonVisible(false);
      if (!this.mChallenge.hasCancelButtonDisplayLabel) {
        break label416;
      }
      localButtonBar.setNegativeButtonTitle(this.mChallenge.cancelButtonDisplayLabel);
      label213:
      localButtonBar.setClickListener(this);
      if (!TextUtils.isEmpty(str1))
      {
        localActionBarHelper.updateDefaultTitle(str1);
        setTitle(str1);
      }
      this.mWebView = ((WebView)findViewById(2131755295));
      this.mWebView.setOnTouchListener(new View.OnTouchListener()
      {
        public final boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          switch (paramAnonymousMotionEvent.getAction())
          {
          }
          for (;;)
          {
            return false;
            if (!paramAnonymousView.hasFocus()) {
              paramAnonymousView.requestFocus();
            }
          }
        }
      });
      this.mWebView.getSettings().setJavaScriptEnabled(true);
      this.mWebView.getSettings().setAllowFileAccess(false);
      this.mWebView.setWebViewClient(new WebViewClient()
      {
        public final void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
          WebViewChallengeActivity.this.findViewById(2131755277).setVisibility(8);
          paramAnonymousWebView.setVisibility(0);
        }
        
        public final void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
        {
          if (!WebViewChallengeActivity.access$100(WebViewChallengeActivity.this, paramAnonymousString))
          {
            paramAnonymousWebView.stopLoading();
            WebViewChallengeActivity.this.cancel(null);
          }
          do
          {
            return;
            if (paramAnonymousString.matches(WebViewChallengeActivity.this.mChallenge.targetUrlRegexp))
            {
              WebViewChallengeActivity.access$300(WebViewChallengeActivity.this, paramAnonymousString);
              return;
            }
            if ((WebViewChallengeActivity.this.mChallenge.hasCancelUrlRegexp) && (paramAnonymousString.matches(WebViewChallengeActivity.this.mChallenge.cancelUrlRegexp)))
            {
              WebViewChallengeActivity.this.cancel(paramAnonymousString);
              return;
            }
            super.onPageStarted(paramAnonymousWebView, paramAnonymousString, paramAnonymousBitmap);
          } while (!WebViewChallengeActivity.this.mIsFirstPageLoad);
          WebViewChallengeActivity.this.findViewById(2131755277).setVisibility(0);
          paramAnonymousWebView.setVisibility(8);
          WebViewChallengeActivity.access$402$dcd6b4a(WebViewChallengeActivity.this);
        }
        
        @TargetApi(11)
        public final WebResourceResponse shouldInterceptRequest(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          if (!WebViewChallengeActivity.access$100(WebViewChallengeActivity.this, paramAnonymousString)) {
            return new WebResourceResponse("text/plain", "UTF-8", new ByteArrayInputStream(("Blocked non-HTTPS resource: " + paramAnonymousString).getBytes()));
          }
          return super.shouldInterceptRequest(paramAnonymousWebView, paramAnonymousString);
        }
        
        public final boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          if (!WebViewChallengeActivity.access$100(WebViewChallengeActivity.this, paramAnonymousString))
          {
            WebViewChallengeActivity.this.cancel(null);
            return true;
          }
          return super.shouldOverrideUrlLoading(paramAnonymousWebView, paramAnonymousString);
        }
      });
      String str3 = this.mChallenge.startUrl;
      String str4 = Uri.parse(str3).getHost();
      boolean bool = TextUtils.isEmpty(str4);
      PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = null;
      if (!bool)
      {
        localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
        localPlayStoreUiElementInfo.host = str4;
        localPlayStoreUiElementInfo.hasHost = true;
      }
      this.mNode = new GenericUiElementNode(316, null, localPlayStoreUiElementInfo, null);
      if (paramBundle != null) {
        break label426;
      }
      this.mLogger.logPathImpression(0L, this.mNode);
      this.mWebView.loadUrl(str3);
    }
    label416:
    label426:
    Bundle localBundle;
    do
    {
      return;
      str1 = getIntent().getStringExtra("backupTitle");
      break;
      localButtonBar.setVisibility(8);
      break label213;
      localBundle = paramBundle.getBundle("webview_state");
    } while (localBundle == null);
    this.mWebView.restoreState(localBundle);
  }
  
  public final void onNegativeButtonClick()
  {
    cancel(null);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
    {
      cancel(null);
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public final void onPositiveButtonClick() {}
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    Bundle localBundle = new Bundle();
    this.mWebView.saveState(localBundle);
    paramBundle.putBundle("webview_state", localBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.WebViewChallengeActivity
 * JD-Core Version:    0.7.0.1
 */