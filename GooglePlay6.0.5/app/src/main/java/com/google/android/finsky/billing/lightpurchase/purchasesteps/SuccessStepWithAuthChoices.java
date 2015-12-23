package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.config.PurchaseAuthUtils;
import com.google.android.play.layout.PlayActionButton;

public final class SuccessStepWithAuthChoices
  extends StepFragment<PurchaseFragment>
  implements View.OnClickListener
{
  private String mAccountName;
  private int mBackend;
  private PlayActionButton mEveryTimeButton;
  private PlayActionButton mSessionButton;
  private View mStepFragmentView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(1250);
  private boolean mUsedPinBasedAuth;
  
  public static SuccessStepWithAuthChoices newInstance(String paramString, int paramInt, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    SuccessStepWithAuthChoices localSuccessStepWithAuthChoices = new SuccessStepWithAuthChoices();
    localBundle.putString("authAccount", paramString);
    localBundle.putInt("SuccessStepWithAuthChoices.backend", paramInt);
    localBundle.putBoolean("SuccessStepWithAuthChoices.usedPinBasedAuth", paramBoolean);
    localSuccessStepWithAuthChoices.setArguments(localBundle);
    return localSuccessStepWithAuthChoices;
  }
  
  private void setPurchaseAuth(int paramInt)
  {
    PurchaseAuthUtils.setAndLogPurchaseAuth(this.mAccountName, paramInt, null, FinskyApp.get().getEventLogger(this.mAccountName), "success-step-with-choices");
  }
  
  public final boolean allowButtonBar()
  {
    return false;
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return null;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mEveryTimeButton)
    {
      logClick(1251, null);
      setPurchaseAuth(2);
    }
    for (;;)
    {
      PurchaseFragment localPurchaseFragment = (PurchaseFragment)this.mParentFragment;
      if (localPurchaseFragment != null) {
        localPurchaseFragment.mSidecar.confirmAuthChoiceSelected();
      }
      return;
      if (paramView == this.mSessionButton)
      {
        logClick(1252, null);
        setPurchaseAuth(1);
      }
    }
  }
  
  public final void onContinueButtonClicked() {}
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = this.mArguments;
    this.mAccountName = localBundle.getString("authAccount");
    this.mBackend = localBundle.getInt("SuccessStepWithAuthChoices.backend");
    this.mUsedPinBasedAuth = localBundle.getBoolean("SuccessStepWithAuthChoices.usedPinBasedAuth", false);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mStepFragmentView = paramLayoutInflater.inflate(2130968824, paramViewGroup, false);
    TextView localTextView = (TextView)this.mStepFragmentView.findViewById(2131755211);
    if (this.mUsedPinBasedAuth) {}
    for (int i = 2131362495;; i = 2131362494)
    {
      localTextView.setText(i);
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
      this.mEveryTimeButton = ((PlayActionButton)this.mStepFragmentView.findViewById(2131755694));
      this.mSessionButton = ((PlayActionButton)this.mStepFragmentView.findViewById(2131755696));
      this.mEveryTimeButton.configure(this.mBackend, 2131362602, this);
      this.mSessionButton.configure(this.mBackend, 2131362603, this);
      return this.mStepFragmentView;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.SuccessStepWithAuthChoices
 * JD-Core Version:    0.7.0.1
 */