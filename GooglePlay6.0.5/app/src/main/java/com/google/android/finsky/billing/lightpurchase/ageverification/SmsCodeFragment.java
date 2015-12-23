package com.google.android.finsky.billing.lightpurchase.ageverification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.finsky.fragments.LoggingFragment;
import com.google.android.finsky.protos.ChallengeProto.FormButton;
import com.google.android.finsky.protos.ChallengeProto.FormTextField;
import com.google.android.finsky.protos.ChallengeProto.SmsCodeChallenge;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.layout.PlayActionButton;

public final class SmsCodeFragment
  extends LoggingFragment
  implements View.OnClickListener
{
  private ChallengeProto.SmsCodeChallenge mChallenge;
  private EditText mCodeEntry;
  private View mMainView;
  private PlayActionButton mResendButton;
  private PlayActionButton mSubmitButton;
  
  private Listener getListener()
  {
    if ((this.mTarget instanceof Listener)) {
      return (Listener)this.mTarget;
    }
    if ((this.mParentFragment instanceof Listener)) {
      return (Listener)this.mParentFragment;
    }
    if ((getActivity() instanceof Listener)) {
      return (Listener)getActivity();
    }
    throw new IllegalStateException("No listener registered.");
  }
  
  public static SmsCodeFragment newInstance(String paramString, int paramInt, ChallengeProto.SmsCodeChallenge paramSmsCodeChallenge)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putInt("SmsCodeFragment.backend", paramInt);
    localBundle.putParcelable("SmsCodeFragment.challenge", ParcelableProto.forProto(paramSmsCodeChallenge));
    SmsCodeFragment localSmsCodeFragment = new SmsCodeFragment();
    localSmsCodeFragment.setArguments(localBundle);
    return localSmsCodeFragment;
  }
  
  private void syncSubmitButtonState()
  {
    PlayActionButton localPlayActionButton = this.mSubmitButton;
    if (!Utils.isEmptyOrSpaces(this.mCodeEntry.getText())) {}
    for (boolean bool = true;; bool = false)
    {
      localPlayActionButton.setEnabled(bool);
      return;
    }
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1403;
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mResendButton)
    {
      logClickEvent(1405);
      getListener().onResendSmsCode(this.mChallenge.resendCodeButton.actionUrl);
    }
    while (paramView != this.mSubmitButton) {
      return;
    }
    logClickEvent(1408);
    getListener().onVerifySmsCode(this.mChallenge.submitButton.actionUrl, this.mChallenge.smsCode.postParam, this.mCodeEntry.getText().toString());
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mChallenge = ((ChallengeProto.SmsCodeChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "SmsCodeFragment.challenge"));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968615, paramViewGroup, false);
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755173);
    TextView localTextView2;
    TextView localTextView3;
    if (!TextUtils.isEmpty(this.mChallenge.title))
    {
      localTextView1.setText(this.mChallenge.title);
      localTextView2 = (TextView)this.mMainView.findViewById(2131755211);
      if (TextUtils.isEmpty(this.mChallenge.descriptionHtml)) {
        break label399;
      }
      localTextView2.setText(Html.fromHtml(this.mChallenge.descriptionHtml));
      this.mCodeEntry = ((EditText)this.mMainView.findViewById(2131755235));
      if (this.mChallenge.smsCode == null) {
        break label419;
      }
      if (!TextUtils.isEmpty(this.mChallenge.smsCode.hint)) {
        this.mCodeEntry.setHint(this.mChallenge.smsCode.hint);
      }
      if (!TextUtils.isEmpty(this.mChallenge.smsCode.defaultValue)) {
        this.mCodeEntry.setText(this.mChallenge.smsCode.defaultValue);
      }
      this.mCodeEntry.addTextChangedListener(new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable)
        {
          SmsCodeFragment.this.syncSubmitButtonState();
        }
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      });
      localTextView3 = (TextView)this.mMainView.findViewById(2131755236);
      if (TextUtils.isEmpty(this.mChallenge.smsCode.error)) {
        break label409;
      }
      localTextView3.setText(this.mChallenge.smsCode.error);
      label245:
      int i = this.mArguments.getInt("SmsCodeFragment.backend");
      this.mSubmitButton = ((PlayActionButton)this.mMainView.findViewById(2131755302));
      if ((this.mChallenge.submitButton == null) || (TextUtils.isEmpty(this.mChallenge.submitButton.label))) {
        break label429;
      }
      this.mSubmitButton.configure(i, this.mChallenge.submitButton.label, this);
      this.mResendButton = ((PlayActionButton)this.mMainView.findViewById(2131755301));
      if ((this.mChallenge.resendCodeButton == null) || (TextUtils.isEmpty(this.mChallenge.resendCodeButton.label))) {
        break label439;
      }
      this.mResendButton.configure(i, this.mChallenge.resendCodeButton.label, this);
    }
    for (;;)
    {
      syncSubmitButtonState();
      return this.mMainView;
      throw new IllegalStateException("No title returned");
      label399:
      localTextView2.setVisibility(8);
      break;
      label409:
      localTextView3.setVisibility(8);
      break label245;
      label419:
      throw new IllegalStateException("No SMS code field returned.");
      label429:
      throw new IllegalStateException("No submit button returned.");
      label439:
      this.mResendButton.setVisibility(8);
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), this.mChallenge.title, this.mMainView);
  }
  
  public static abstract interface Listener
  {
    public abstract void onResendSmsCode(String paramString);
    
    public abstract void onVerifySmsCode(String paramString1, String paramString2, String paramString3);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.ageverification.SmsCodeFragment
 * JD-Core Version:    0.7.0.1
 */