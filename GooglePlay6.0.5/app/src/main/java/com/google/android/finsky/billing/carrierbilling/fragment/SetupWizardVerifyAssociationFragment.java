package com.google.android.finsky.billing.carrierbilling.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.InstrumentActivity;
import com.google.android.finsky.fragments.LoggingFragment;
import com.google.android.finsky.setup.SetupWizardNavBar;

public final class SetupWizardVerifyAssociationFragment
  extends LoggingFragment
  implements View.OnClickListener
{
  private Button mCancelButton;
  public VerifyAssociationDialogFragment.VerifyAssociationListener mListener;
  
  public static SetupWizardVerifyAssociationFragment newInstance(String paramString)
  {
    SetupWizardVerifyAssociationFragment localSetupWizardVerifyAssociationFragment = new SetupWizardVerifyAssociationFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localSetupWizardVerifyAssociationFragment.setArguments(localBundle);
    return localSetupWizardVerifyAssociationFragment;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 897;
  }
  
  public final void onClick(View paramView)
  {
    if ((paramView == this.mCancelButton) && (this.mListener != null)) {
      this.mListener.onVerifyCancel();
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130969119, paramViewGroup, false);
    ((TextView)localView.findViewById(2131756130)).setText(getResources().getString(2131361865));
    SetupWizardNavBar localSetupWizardNavBar = ((InstrumentActivity)getActivity()).mSetupWizardNavBar;
    if (localSetupWizardNavBar != null) {
      localSetupWizardNavBar.mNextButton.setEnabled(false);
    }
    for (this.mCancelButton = localSetupWizardNavBar.mBackButton;; this.mCancelButton = ((Button)localView.findViewById(2131756119)))
    {
      this.mCancelButton.setOnClickListener(this);
      this.mCancelButton.setText(2131362093);
      return localView;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.fragment.SetupWizardVerifyAssociationFragment
 * JD-Core Version:    0.7.0.1
 */