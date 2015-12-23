package com.google.android.wallet.instrumentmanager.ui.redirect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.analytics.WebViewPageLoadEvent;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.analytics.util.PageImpressionTracker;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.instrumentmanager.common.util.ImUtils;
import com.google.android.wallet.ui.common.FormEventListener;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectForm;
import java.util.Collections;
import java.util.List;

public class PopupRedirectActivity
  extends AppCompatActivity
  implements UiNode, FormEventListener
{
  RedirectFragment mFragment;
  private byte[] mLogToken;
  PageImpressionTracker mPageImpressionTracker;
  private WalletUiElement mUiElement;
  
  public static Intent createIntent(Context paramContext, RedirectFormOuterClass.RedirectForm paramRedirectForm, String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfByte)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(paramContext.getPackageName(), PopupRedirectActivity.class.getName());
    localIntent.putExtra("formProto", ParcelableProto.forProto(paramRedirectForm));
    localIntent.putExtra("title", paramString);
    localIntent.putExtra("activityThemeResId", paramInt1);
    localIntent.putExtra("formThemeResId", paramInt2);
    localIntent.putExtra("logToken", paramArrayOfByte);
    return localIntent;
  }
  
  public List<UiNode> getChildren()
  {
    return Collections.singletonList(this.mFragment);
  }
  
  public UiNode getParentUiNode()
  {
    return null;
  }
  
  public WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final void notifyFormEvent(int paramInt, Bundle paramBundle)
  {
    switch (paramInt)
    {
    case 6: 
    case 9: 
    default: 
      throw new IllegalArgumentException("Unsupported formEvent: " + paramInt);
    case 8: 
      if (this.mFragment.isReadyToSubmit())
      {
        Intent localIntent3 = new Intent();
        RedirectFragment localRedirectFragment = this.mFragment;
        localIntent3.putExtra("formValue", ParcelableProto.forProto(localRedirectFragment.getRedirectFormValue$5a05ded8()));
        setResult(-1, localIntent3);
        finish();
      }
      return;
    case 7: 
      int j = paramBundle.getInt("FormEventListener.EXTRA_BACKGROUND_EVENT_TYPE");
      switch (j)
      {
      default: 
        throw new IllegalArgumentException("Unsupported analytics background event type: " + j);
      case 772: 
        WebViewPageLoadEvent localWebViewPageLoadEvent = (WebViewPageLoadEvent)paramBundle.getParcelable("FormEventListener.EXTRA_BACKGROUND_EVENT_DATA");
        if (localWebViewPageLoadEvent == null) {
          throw new IllegalArgumentException("WebViewPageLoad background events must include a WebViewPageLoadEvent.");
        }
        int m = paramBundle.getInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", -1);
        if (m == -1) {
          throw new IllegalArgumentException("WebViewPageLoad background events must include a resultCode");
        }
        AnalyticsUtil.createAndSendWebViewPageLoadBackgroundEvent(m, localWebViewPageLoadEvent, this.mLogToken);
        return;
      }
      int k = paramBundle.getInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", -1);
      if (k == -1) {
        throw new IllegalArgumentException("ProviderInstall background events must include a resultCode");
      }
      AnalyticsUtil.createAndSendResponseReceivedBackgroundEvent(776, k, this.mLogToken);
      return;
    case 4: 
      this.mPageImpressionTracker.trackImpressionIfNecessary(this);
      return;
    case 5: 
      Intent localIntent2 = new Intent();
      localIntent2.putExtra("errorDetails", paramBundle);
      setResult(1, localIntent2);
      finish();
      return;
    }
    int i = paramBundle.getInt("FormEventListener.EXTRA_RESULT_CODE", -1);
    if (i == -1) {
      throw new IllegalArgumentException("NotifyResultListener event must contain a result code.");
    }
    Bundle localBundle = paramBundle.getBundle("FormEventListener.EXTRA_RESULT_DATA");
    if (localBundle == null) {
      localBundle = Bundle.EMPTY;
    }
    Intent localIntent1 = new Intent();
    localIntent1.putExtra("resultCode", i);
    localIntent1.putExtra("resultData", localBundle);
    setResult(2, localIntent1);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    Intent localIntent = getIntent();
    setTheme(localIntent.getIntExtra("activityThemeResId", -1));
    super.onCreate(paramBundle);
    setTitle(localIntent.getStringExtra("title"));
    this.mFragment = ((RedirectFragment)getSupportFragmentManager().findFragmentById(16908290));
    if (this.mFragment == null)
    {
      this.mFragment = RedirectFragment.newInstance((RedirectFormOuterClass.RedirectForm)ParcelableProto.getProtoFromIntent(localIntent, "formProto"), localIntent.getIntExtra("formThemeResId", -1));
      getSupportFragmentManager().beginTransaction().add(16908290, this.mFragment).commit();
    }
    this.mLogToken = localIntent.getByteArrayExtra("logToken");
    this.mUiElement = new WalletUiElement(1746, this.mLogToken);
    if (paramBundle != null) {}
    for (this.mPageImpressionTracker = new PageImpressionTracker(paramBundle.getBoolean("impressionForPageTracked"));; this.mPageImpressionTracker = new PageImpressionTracker(false))
    {
      ImUtils.setScreenshotsEnabled(this, false);
      return;
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
    {
      setResult(0);
      finish();
      AnalyticsUtil.createAndSendClickEvent(this, -1, 1632);
      return true;
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("impressionForPageTracked", this.mPageImpressionTracker.mImpressionForPageTracked);
  }
  
  public void setParentUiNode(UiNode paramUiNode)
  {
    throw new UnsupportedOperationException("Top level UiNode doesn't support custom parents.");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.redirect.PopupRedirectActivity
 * JD-Core Version:    0.7.0.1
 */