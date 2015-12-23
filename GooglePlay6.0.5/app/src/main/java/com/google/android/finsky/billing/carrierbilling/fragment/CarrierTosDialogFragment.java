package com.google.android.finsky.billing.carrierbilling.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.LoggingDialogFragment;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class CarrierTosDialogFragment
  extends LoggingDialogFragment
  implements ButtonBar.ClickListener
{
  private ButtonBar mButtonBar;
  public CarrierTosResultListener mListener;
  private String mTosUrl;
  private CarrierTosWebViewClient mTosWebViewclient;
  
  public static CarrierTosDialogFragment newInstance(String paramString1, String paramString2, String paramString3)
  {
    CarrierTosDialogFragment localCarrierTosDialogFragment = new CarrierTosDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString1);
    localBundle.putString("carrier_tos_url", paramString2);
    localBundle.putString("carrier_name", paramString3);
    localCarrierTosDialogFragment.setArguments(localBundle);
    return localCarrierTosDialogFragment;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 823;
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    this.mListener.onCarrierTosResult(2);
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    View localView = View.inflate(getActivity(), 2130968656, null);
    Bundle localBundle = this.mArguments;
    String str1 = localBundle.getString("carrier_tos_url");
    String str2 = getString(2131362800);
    if (!TextUtils.isEmpty(str2)) {
      str1 = str1.replace("%locale%", str2);
    }
    this.mTosUrl = BillingUtils.replaceLocale(str1);
    this.mButtonBar = ((ButtonBar)localView.findViewById(2131755300));
    this.mButtonBar.setClickListener(this);
    this.mButtonBar.setPositiveButtonTitle(2131361811);
    this.mButtonBar.setPositiveButtonEnabled(false);
    if (FinskyApp.get().getExperiments(localBundle.getString("authAccount")).isEnabled(12603132L)) {
      this.mButtonBar.setNegativeButtonVisible(false);
    }
    for (;;)
    {
      WebView localWebView = (WebView)localView.findViewById(2131755315);
      this.mTosWebViewclient = new CarrierTosWebViewClient(localView.findViewById(2131755316), localView.findViewById(2131755314));
      localWebView.setWebViewClient(this.mTosWebViewclient);
      localWebView.loadUrl(this.mTosUrl);
      localWebView.getSettings().setSupportZoom(false);
      String str3 = localBundle.getString("carrier_name");
      return new AlertDialogBuilderCompat(getActivity()).setTitle(getString(2131361923, new Object[] { str3 })).setView(localView).create();
      this.mButtonBar.setNegativeButtonTitle(2131362077);
    }
  }
  
  public final void onNegativeButtonClick()
  {
    logClickEvent(825);
    this.mListener.onCarrierTosResult(2);
  }
  
  public final void onPositiveButtonClick()
  {
    logClickEvent(824);
    this.mListener.onCarrierTosResult(0);
  }
  
  public static abstract interface CarrierTosResultListener
  {
    public abstract void onCarrierTosResult(int paramInt);
  }
  
  private final class CarrierTosWebViewClient
    extends WebViewClient
  {
    private final View mProgress;
    private boolean mReceivedError;
    private final View mTosDisplayView;
    
    public CarrierTosWebViewClient(View paramView1, View paramView2)
    {
      this.mProgress = paramView1;
      this.mTosDisplayView = paramView2;
      this.mReceivedError = false;
    }
    
    public final void onPageFinished(WebView paramWebView, String paramString)
    {
      paramWebView.setVisibility(0);
      this.mTosDisplayView.setVisibility(0);
      if (!this.mReceivedError) {
        CarrierTosDialogFragment.this.mButtonBar.setPositiveButtonEnabled(true);
      }
      this.mProgress.setVisibility(8);
    }
    
    public final void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      FinskyLog.w("Web error: (" + paramString2 + ") " + paramString1, new Object[0]);
      this.mReceivedError = true;
      CarrierTosDialogFragment.this.mListener.onCarrierTosResult(1);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.fragment.CarrierTosDialogFragment
 * JD-Core Version:    0.7.0.1
 */