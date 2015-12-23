package com.google.android.finsky.billing.carrierbilling.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.LoggingDialogFragment;
import com.google.android.finsky.layout.ButtonBar;
import com.google.android.finsky.layout.ButtonBar.ClickListener;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public final class CarrierBillingPasswordDialogFragment
  extends LoggingDialogFragment
  implements ButtonBar.ClickListener
{
  public CarrierBillingPasswordResultListener mListener;
  private View mMainPasswordView;
  private EditText mPasswordEditText;
  private View mProgressIndicator;
  
  public static CarrierBillingPasswordDialogFragment newInstance(String paramString1, String paramString2, String paramString3)
  {
    CarrierBillingPasswordDialogFragment localCarrierBillingPasswordDialogFragment = new CarrierBillingPasswordDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString1);
    localBundle.putString("password_prompt", paramString2);
    localBundle.putString("password_forgot_url", paramString3);
    localCarrierBillingPasswordDialogFragment.setArguments(localBundle);
    return localCarrierBillingPasswordDialogFragment;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 820;
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    this.mListener.onCarrierBillingPasswordResult(2, null, getActivity().getBaseContext());
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    View localView = View.inflate(getActivity(), 2130968655, null);
    Bundle localBundle = this.mArguments;
    String str1 = localBundle.getString("password_prompt");
    String str2 = localBundle.getString("password_forgot_url");
    this.mMainPasswordView = localView.findViewById(2131755308);
    this.mProgressIndicator = localView.findViewById(2131755289);
    this.mMainPasswordView.setVisibility(0);
    this.mProgressIndicator.setVisibility(8);
    TextView localTextView1 = (TextView)localView.findViewById(2131755310);
    TextView localTextView2 = (TextView)localView.findViewById(2131755312);
    this.mPasswordEditText = ((EditText)localView.findViewById(2131755311));
    ButtonBar localButtonBar = (ButtonBar)localView.findViewById(2131755300);
    localButtonBar.setClickListener(this);
    localButtonBar.setPositiveButtonTitle(2131361911);
    ViewCompat.setPaddingRelative(localButtonBar, 2, 4, 2, localButtonBar.getPaddingBottom());
    if (FinskyApp.get().getExperiments(localBundle.getString("authAccount")).isEnabled(12603132L)) {
      localButtonBar.setNegativeButtonVisible(false);
    }
    localTextView1.setText(str1);
    if (TextUtils.isEmpty(str2)) {
      localTextView2.setVisibility(8);
    }
    for (;;)
    {
      return new AlertDialogBuilderCompat(getActivity()).setTitle(2131362117).setView(localView).create();
      localTextView2.setText(Html.fromHtml("<a href=\"" + str2 + "\">" + getString(2131362178) + "</a>"));
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
    }
  }
  
  public final void onNegativeButtonClick()
  {
    logClickEvent(822);
    this.mListener.onCarrierBillingPasswordResult(2, null, getActivity().getBaseContext());
  }
  
  public final void onPositiveButtonClick()
  {
    logClickEvent(821);
    String str = this.mPasswordEditText.getText().toString();
    if (TextUtils.isEmpty(str))
    {
      Toast.makeText(getActivity(), 2131362115, 0).show();
      return;
    }
    this.mListener.onCarrierBillingPasswordResult(0, str, getActivity().getBaseContext());
  }
  
  public static abstract interface CarrierBillingPasswordResultListener
  {
    public abstract void onCarrierBillingPasswordResult(int paramInt, String paramString, Context paramContext);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingPasswordDialogFragment
 * JD-Core Version:    0.7.0.1
 */