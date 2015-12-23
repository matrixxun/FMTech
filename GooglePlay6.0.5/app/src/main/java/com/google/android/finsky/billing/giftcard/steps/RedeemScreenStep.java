package com.google.android.finsky.billing.giftcard.steps;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.giftcard.RedeemCodeFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;

public final class RedeemScreenStep
  extends StepFragment<RedeemCodeFragment>
{
  private int mBillingUiMode;
  public EditText mCodeEntry;
  public String mErrorMessageHtml;
  private TextView mErrorMessageView;
  private View mMainView;
  private PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(881);
  
  private void logClickAndRedeemCode(boolean paramBoolean)
  {
    PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = null;
    if (paramBoolean)
    {
      localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
      localPlayStoreUiElementInfo.isImeAction = true;
      localPlayStoreUiElementInfo.hasIsImeAction = true;
    }
    logClick(882, localPlayStoreUiElementInfo);
    Editable localEditable = this.mCodeEntry.getText();
    ((RedeemCodeFragment)this.mParentFragment).redeem(localEditable.toString());
  }
  
  public static RedeemScreenStep newInstance(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString1);
    localBundle.putString("RedeemCodeActivity.prefill_code", paramString2);
    localBundle.putString("RedeemCodeFragment.error_message_html", paramString3);
    localBundle.putInt("ui_mode", paramInt);
    RedeemScreenStep localRedeemScreenStep = new RedeemScreenStep();
    localRedeemScreenStep.setArguments(localBundle);
    return localRedeemScreenStep;
  }
  
  private void syncContinueButtonState()
  {
    RedeemCodeFragment localRedeemCodeFragment = (RedeemCodeFragment)this.mParentFragment;
    if (!TextUtils.isEmpty(this.mCodeEntry.getText())) {}
    for (boolean bool = true;; bool = false)
    {
      localRedeemCodeFragment.setContinueButtonEnabled(bool);
      return;
    }
  }
  
  public final void fireErrorEvents()
  {
    UiUtils.playShakeAnimationIfPossible(getActivity(), this.mCodeEntry);
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), this.mErrorMessageView.getText().toString(), this.mMainView);
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362637);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClickAndRedeemCode(false);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mBillingUiMode = this.mArguments.getInt("ui_mode");
    if (paramBundle != null)
    {
      this.mErrorMessageHtml = paramBundle.getString("RedeemCodeFragment.error_message_html");
      return;
    }
    this.mErrorMessageHtml = this.mArguments.getString("RedeemCodeFragment.error_message_html");
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (this.mBillingUiMode == 1) {
      getActivity().setTitle(getString(2131362640));
    }
    if (this.mBillingUiMode == 0) {}
    for (int i = 2130969063;; i = 2130969112)
    {
      this.mMainView = paramLayoutInflater.inflate(i, paramViewGroup, false);
      this.mCodeEntry = ((EditText)this.mMainView.findViewById(2131756030));
      String str1 = this.mArguments.getString("RedeemCodeActivity.prefill_code");
      if ((paramBundle == null) && (!TextUtils.isEmpty(str1))) {
        this.mCodeEntry.setText(str1);
      }
      ((TextView)this.mMainView.findViewById(2131755621)).setText(Utils.getDisplayAccountName(this.mArguments.getString("authAccount"), getContext()));
      String str2 = BillingUtils.replaceLocale((String)G.redeemTermsAndConditionsUrl.get());
      TextView localTextView = (TextView)this.mMainView.findViewById(2131755232);
      localTextView.setText(Html.fromHtml(getString(2131362639, new Object[] { str2 })));
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView.setLinkTextColor(localTextView.getTextColors());
      final ColorStateList localColorStateList = this.mCodeEntry.getTextColors();
      this.mCodeEntry.addTextChangedListener(new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable)
        {
          RedeemScreenStep.this.mCodeEntry.setTextColor(localColorStateList);
          RedeemScreenStep.this.syncContinueButtonState();
        }
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      });
      this.mCodeEntry.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public final boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousInt == 6) {
            RedeemScreenStep.access$200$41e90f12(RedeemScreenStep.this);
          }
          return false;
        }
      });
      this.mErrorMessageView = ((TextView)this.mMainView.findViewById(2131755236));
      syncContinueButtonState();
      syncErrorTextView();
      return this.mMainView;
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("RedeemCodeFragment.error_message_html", this.mErrorMessageHtml);
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (paramBundle == null)
    {
      UiUtils.showKeyboard(getActivity(), this.mCodeEntry);
      if (this.mErrorMessageHtml != null) {
        fireErrorEvents();
      }
    }
  }
  
  public final void syncErrorTextView()
  {
    if (this.mMainView == null)
    {
      FinskyLog.wtf("Null mMainView.", new Object[0]);
      return;
    }
    if (this.mErrorMessageHtml != null)
    {
      this.mErrorMessageView.setText(Html.fromHtml(this.mErrorMessageHtml));
      this.mErrorMessageView.setVisibility(0);
      this.mErrorMessageView.setMovementMethod(LinkMovementMethod.getInstance());
      this.mErrorMessageView.setLinkTextColor(this.mErrorMessageView.getTextColors());
      this.mCodeEntry.setTextColor(getResources().getColor(2131689695));
      return;
    }
    this.mErrorMessageView.setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.steps.RedeemScreenStep
 * JD-Core Version:    0.7.0.1
 */