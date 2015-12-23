package com.google.android.wallet.instrumentmanager.ui.redirect;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.util.ErrorUtils;
import com.google.android.wallet.config.G;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.redirect.HtmlFormContent;
import com.google.android.wallet.redirect.RedirectWebViewClient;
import com.google.android.wallet.redirect.RedirectWebViewClient.RedirectListener;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.WebViewLayout;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectFormValue;
import java.util.Collections;
import java.util.List;

public final class RedirectFragment
  extends FormFragment<RedirectFormOuterClass.RedirectForm>
  implements ProviderInstaller.ProviderInstallListener, RedirectWebViewClient.RedirectListener
{
  HtmlFormContent mFormContentFromWebView;
  String mNonTerminalUrlFromWebView;
  private boolean mProviderInstallerSucceeded;
  private boolean mScrollViewOriginalFillViewport;
  String mTerminalUrlFromWebView;
  private final WalletUiElement mUiElement = new WalletUiElement(1745);
  private RedirectWebViewClient mWebViewClient;
  WebViewLayout mWebViewLayout;
  private int mWindowOriginalHeight;
  
  public static RedirectFragment newInstance(RedirectFormOuterClass.RedirectForm paramRedirectForm, int paramInt)
  {
    RedirectFragment localRedirectFragment = new RedirectFragment();
    localRedirectFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramRedirectForm));
    return localRedirectFragment;
  }
  
  private void notifyShowError(String paramString, int paramInt)
  {
    this.mTerminalUrlFromWebView = null;
    this.mNonTerminalUrlFromWebView = null;
    this.mFormContentFromWebView = null;
    Bundle localBundle = new Bundle();
    String str = getString(R.string.wallet_im_error_title);
    if (paramInt == 501) {}
    for (int i = R.string.wallet_uic_retry;; i = 17039370)
    {
      notifyFormEvent(5, ErrorUtils.addErrorDetailsToBundle(localBundle, paramInt, str, paramString, null, getString(i)));
      return;
    }
  }
  
  private void onProviderInstallerNotAvailable()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("FormEventListener.EXTRA_RESULT_CODE", 51);
    notifyFormEvent(10, localBundle);
  }
  
  private static String parseInitialPostBody(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    Object localObject = null;
    try
    {
      HtmlFormContent localHtmlFormContent1 = new HtmlFormContent("POST", paramString);
      localHtmlFormContent2 = localHtmlFormContent1;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        HtmlFormContent localHtmlFormContent2 = null;
      }
      if (!((Boolean)G.allowPiiLogging.get()).booleanValue()) {
        break label90;
      }
      throw new IllegalArgumentException("Invalid initial post body: " + paramString, localIllegalArgumentException);
      throw new IllegalArgumentException("Invalid initial post body.");
    }
    if ((localObject == null) && (localHtmlFormContent2 != null) && (localHtmlFormContent2.isValid())) {
      return localHtmlFormContent2.toUrlEncodedString();
    }
  }
  
  private void sendAnalyticsBackgroundEvent$255f295(int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("FormEventListener.EXTRA_BACKGROUND_EVENT_TYPE", 776);
    localBundle.putInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", paramInt);
    notifyFormEvent(7, localBundle);
  }
  
  private void setWindowHeight(int paramInt)
  {
    Window localWindow = getActivity().getWindow();
    if (localWindow == null) {
      return;
    }
    WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
    localLayoutParams.height = paramInt;
    localWindow.setAttributes(localLayoutParams);
  }
  
  private boolean wasNonTerminalUrlReached()
  {
    return !TextUtils.isEmpty(this.mNonTerminalUrlFromWebView);
  }
  
  private boolean wasTerminalUrlReached()
  {
    return !TextUtils.isEmpty(this.mTerminalUrlFromWebView);
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    return false;
  }
  
  protected final void doEnableUi()
  {
    if (this.mWebViewLayout != null) {
      this.mWebViewLayout.setEnabled(this.mUiEnabled);
    }
  }
  
  public final List<UiNode> getChildren()
  {
    return null;
  }
  
  public final List<FormFragment.FieldData> getFieldsForValidationInOrder()
  {
    return Collections.EMPTY_LIST;
  }
  
  public final RedirectFormOuterClass.RedirectFormValue getRedirectFormValue$5a05ded8()
  {
    RedirectFormOuterClass.RedirectFormValue localRedirectFormValue = new RedirectFormOuterClass.RedirectFormValue();
    localRedirectFormValue.id = ((RedirectFormOuterClass.RedirectForm)this.mFormProto).formHeader.id;
    localRedirectFormValue.dataToken = ((RedirectFormOuterClass.RedirectForm)this.mFormProto).formHeader.dataToken;
    if (wasNonTerminalUrlReached()) {
      localRedirectFormValue.nonTerminalUrl = this.mNonTerminalUrlFromWebView;
    }
    for (;;)
    {
      if ((this.mFormContentFromWebView != null) && (this.mFormContentFromWebView.isPost())) {
        localRedirectFormValue.interceptedPostBody = this.mFormContentFromWebView.toUrlEncodedString();
      }
      return localRedirectFormValue;
      if (!wasTerminalUrlReached()) {
        break;
      }
      localRedirectFormValue.terminalUrl = this.mTerminalUrlFromWebView;
    }
    throw new IllegalStateException("Unknown RedirectFormValue state.");
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean isReadyToSubmit()
  {
    return (wasNonTerminalUrlReached()) || (wasTerminalUrlReached());
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mScrollViewOriginalFillViewport = WalletUiUtils.setAncestorScrollViewFillViewport(this.mWebViewLayout, true);
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 6000)
    {
      ProviderInstaller.installIfNeededAsync(getActivity(), this);
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (this.mWebViewClient != null)
    {
      this.mWebViewClient.mRedirectListener = this;
      this.mWebViewClient.mFormEventListener = this;
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_redirect, null, false);
    this.mWebViewLayout = ((WebViewLayout)localView.findViewById(R.id.web_view_layout));
    if (!TextUtils.isEmpty(((RedirectFormOuterClass.RedirectForm)this.mFormProto).userAgent)) {
      this.mWebViewLayout.setUserAgent(((RedirectFormOuterClass.RedirectForm)this.mFormProto).userAgent);
    }
    this.mWebViewClient = new RedirectWebViewClient(getActivity(), this.mWebViewLayout.getWebView(), ((RedirectFormOuterClass.RedirectForm)this.mFormProto).interceptNonTerminalUrlRegex, ((RedirectFormOuterClass.RedirectForm)this.mFormProto).interceptTerminalUrlRegex, ((RedirectFormOuterClass.RedirectForm)this.mFormProto).mustEnforceWhitelist, ((RedirectFormOuterClass.RedirectForm)this.mFormProto).whitelistUrlRegex);
    this.mWebViewClient.mRedirectListener = this;
    this.mWebViewClient.mFormEventListener = this;
    this.mWebViewLayout.setWebViewClient(this.mWebViewClient);
    if (paramBundle != null) {
      this.mProviderInstallerSucceeded = paramBundle.getBoolean("providerInstallerSucceeded");
    }
    if (this.mProviderInstallerSucceeded) {
      doEnableUi();
    }
    for (;;)
    {
      Window localWindow = getActivity().getWindow();
      if ((localWindow != null) && (localWindow.getAttributes().height == -2))
      {
        this.mWindowOriginalHeight = localWindow.getAttributes().height;
        setWindowHeight(-1);
      }
      return localView;
      enableUi(false);
      ProviderInstaller.installIfNeededAsync(getActivity(), this);
    }
  }
  
  public final void onDestroyView()
  {
    super.onDestroyView();
    WalletUiUtils.setAncestorScrollViewFillViewport(this.mWebViewLayout, this.mScrollViewOriginalFillViewport);
    if (this.mWindowOriginalHeight != 0) {
      setWindowHeight(this.mWindowOriginalHeight);
    }
  }
  
  public final void onDetach()
  {
    super.onDetach();
    this.mWebViewClient.mRedirectListener = null;
    this.mWebViewClient.mFormEventListener = null;
  }
  
  public final void onErrorReceived$2498c652(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (int i = 0; i != 0; i = 1)
    {
      notifyShowError(getString(R.string.wallet_im_redirect_form_error_retry), 501);
      return;
    }
    notifyShowError(getString(R.string.wallet_im_redirect_form_error_no_retry), 2);
  }
  
  public final void onNonTerminalUrlIntercepted(String paramString, HtmlFormContent paramHtmlFormContent)
  {
    this.mNonTerminalUrlFromWebView = paramString;
    this.mTerminalUrlFromWebView = null;
    this.mFormContentFromWebView = paramHtmlFormContent;
    notifyFormEvent(8, Bundle.EMPTY);
  }
  
  public final void onNonWhitelistedUrlIntercepted$552c4e01()
  {
    notifyShowError(getString(R.string.wallet_im_redirect_form_whitelist_error), 2);
  }
  
  public final void onProviderInstallFailed$10b55c15(int paramInt)
  {
    sendAnalyticsBackgroundEvent$255f295(paramInt);
    GoogleApiAvailability.getInstance();
    if (GoogleApiAvailability.isUserResolvableError(paramInt))
    {
      GooglePlayServicesUtil.showErrorDialogFragment(paramInt, getActivity(), this, 6000, new DialogInterface.OnCancelListener()
      {
        public final void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          AnalyticsUtil.createAndSendClickEvent(RedirectFragment.this, 1636, 1622);
          RedirectFragment.this.onProviderInstallerNotAvailable();
        }
      });
      AnalyticsUtil.createAndSendImpressionEvent(this, 1636);
      return;
    }
    onProviderInstallerNotAvailable();
  }
  
  public final void onProviderInstalled()
  {
    this.mProviderInstallerSucceeded = true;
    enableUi(true);
    this.mWebViewLayout.loadUrlWhenReady(((RedirectFormOuterClass.RedirectForm)this.mFormProto).initialUrl, parseInitialPostBody(((RedirectFormOuterClass.RedirectForm)this.mFormProto).initialPostBody));
    sendAnalyticsBackgroundEvent$255f295(0);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("providerInstallerSucceeded", this.mProviderInstallerSucceeded);
  }
  
  public final void onTerminalUrlIntercepted(String paramString, HtmlFormContent paramHtmlFormContent)
  {
    this.mTerminalUrlFromWebView = paramString;
    this.mNonTerminalUrlFromWebView = null;
    this.mFormContentFromWebView = paramHtmlFormContent;
    notifyFormEvent(8, Bundle.EMPTY);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.redirect.RedirectFragment
 * JD-Core Version:    0.7.0.1
 */