package com.google.android.wallet.ui.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.android.wallet.uicomponents.R.string;

public final class WebViewDialogFragment
  extends BaseWalletUiComponentDialogFragment
{
  public static WebViewDialogFragment newInstance(String paramString, int paramInt)
  {
    Bundle localBundle = createArgs(paramInt);
    localBundle.putString("url", paramString);
    WebViewDialogFragment localWebViewDialogFragment = new WebViewDialogFragment();
    localWebViewDialogFragment.setArguments(localBundle);
    return localWebViewDialogFragment;
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    WebViewLayout localWebViewLayout = (WebViewLayout)getThemedLayoutInflater().inflate(R.layout.view_web_view_layout, null, false);
    localWebViewLayout.loadUrlWhenReady(this.mArguments.getString("url"), null);
    return new AlertDialogBuilderCompat(getThemedContext()).setView(localWebViewLayout).setPositiveButton(R.string.wallet_uic_close, null).create();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.WebViewDialogFragment
 * JD-Core Version:    0.7.0.1
 */