package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.android.volley.RequestQueue;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElementInfo;
import com.google.android.finsky.billing.creditcard.CreditCardType;
import com.google.android.finsky.billing.creditcard.EscrowRequest;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar.EscrowErrorListener;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar.EscrowResponseListener;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.ChallengeProto.CvnChallenge;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;

public final class CvcChallengeStep
  extends StepFragment<PurchaseFragment>
{
  private ChallengeProto.CvnChallenge mChallenge;
  private EditText mCodeEntry;
  private CreditCardType mCreditCardType;
  private View mMainView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(1270);
  
  private void handleClick(boolean paramBoolean)
  {
    PlayStore.PlayStoreUiElementInfo localPlayStoreUiElementInfo = null;
    if (paramBoolean)
    {
      localPlayStoreUiElementInfo = new PlayStore.PlayStoreUiElementInfo();
      localPlayStoreUiElementInfo.isImeAction = true;
      localPlayStoreUiElementInfo.hasIsImeAction = true;
    }
    ((PurchaseFragment)this.mParentFragment).logClick(1271, localPlayStoreUiElementInfo, this);
    PurchaseFragment localPurchaseFragment = (PurchaseFragment)this.mParentFragment;
    String str = this.mCodeEntry.getText().toString();
    localPurchaseFragment.mEscrowHandleParameterKey = this.mChallenge.escrowHandleParam;
    CheckoutPurchaseSidecar localCheckoutPurchaseSidecar = localPurchaseFragment.mSidecar;
    if (localCheckoutPurchaseSidecar.mState == 1) {
      FinskyLog.wtf("escrowCvcCode() called while RUNNING.", new Object[0]);
    }
    EscrowRequest localEscrowRequest = new EscrowRequest(str, new CheckoutPurchaseSidecar.EscrowResponseListener(localCheckoutPurchaseSidecar, (byte)0), new CheckoutPurchaseSidecar.EscrowErrorListener(localCheckoutPurchaseSidecar, (byte)0));
    FinskyApp.get().mRequestQueue.add(localEscrowRequest);
    localCheckoutPurchaseSidecar.setState(1, 6);
  }
  
  private void syncContinueButtonState()
  {
    boolean bool = true;
    if (this.mCreditCardType != null) {
      if (this.mCodeEntry.getText().length() != this.mCreditCardType.cvcLength) {}
    }
    for (;;)
    {
      ((PurchaseFragment)this.mParentFragment).setContinueButtonEnabled(bool);
      return;
      bool = false;
      continue;
      if (TextUtils.isEmpty(this.mCodeEntry.getText())) {
        bool = false;
      }
    }
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362830);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    handleClick(false);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mChallenge = ((ChallengeProto.CvnChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "CvcChallengeStep.challenge"));
    this.mCreditCardType = CreditCardType.getTypeForProtobufType(this.mChallenge.creditCardType);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968816, paramViewGroup, false);
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755173);
    if (!TextUtils.isEmpty(this.mChallenge.title)) {
      localTextView1.setText(this.mChallenge.title);
    }
    TextView localTextView2 = (TextView)this.mMainView.findViewById(2131755233);
    if (!TextUtils.isEmpty(this.mChallenge.descriptionHtml))
    {
      localTextView2.setText(Html.fromHtml(this.mChallenge.descriptionHtml));
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView2.setLinkTextColor(localTextView2.getTextColors());
    }
    for (;;)
    {
      this.mCodeEntry = ((EditText)this.mMainView.findViewById(2131755665));
      this.mCodeEntry.addTextChangedListener(new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable)
        {
          CvcChallengeStep.this.syncContinueButtonState();
        }
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      });
      if (this.mCreditCardType != null)
      {
        EditText localEditText = this.mCodeEntry;
        InputFilter[] arrayOfInputFilter = new InputFilter[1];
        arrayOfInputFilter[0] = new InputFilter.LengthFilter(this.mCreditCardType.cvcLength);
        localEditText.setFilters(arrayOfInputFilter);
      }
      this.mCodeEntry.setOnEditorActionListener(new TextView.OnEditorActionListener()
      {
        public final boolean onEditorAction(TextView paramAnonymousTextView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if ((paramAnonymousInt == 6) && (((PurchaseFragment)CvcChallengeStep.access$100(CvcChallengeStep.this)).isContinueButtonEnabled())) {
            CvcChallengeStep.access$200$6b1dbc75(CvcChallengeStep.this);
          }
          return false;
        }
      });
      ((ImageView)this.mMainView.findViewById(2131755666)).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (CvcChallengeStep.this.mFragmentManager.findFragmentByTag("CvcChallengeStep.cvc_popup") != null) {
            return;
          }
          UiUtils.hideKeyboard(CvcChallengeStep.this.getActivity(), CvcChallengeStep.this.mMainView);
          SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
          localBuilder.setLayoutId(2130968624).setPositiveId(2131361931);
          localBuilder.build().show(CvcChallengeStep.this.mFragmentManager, "CvcChallengeStep.cvc_popup");
        }
      });
      syncContinueButtonState();
      return this.mMainView;
      localTextView2.setVisibility(8);
    }
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    UiUtils.showKeyboard(getActivity(), this.mCodeEntry);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.CvcChallengeStep
 * JD-Core Version:    0.7.0.1
 */