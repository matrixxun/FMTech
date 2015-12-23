package com.google.android.finsky.billing.carrierbilling.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressData.Builder;
import com.android.i18n.addressinput.AddressWidget;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo.Builder;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.InstrumentActivity;
import com.google.android.finsky.config.PurchaseAuthUtils;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.LoggingFragment;
import com.google.android.finsky.setup.SetupWizardNavBar;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.layout.PlayActionButton;

public final class AddCarrierBillingFragment
  extends LoggingFragment
  implements View.OnClickListener
{
  private Button mAcceptButton;
  private int mBillingUiMode;
  private Button mDeclineButton;
  private ImageButton mEditAddressButton;
  public AddCarrierBillingResultListener mListener;
  private SetupWizardNavBar mSetupWizardNavBar;
  private RadioGroup mSetupWizardTosSelection;
  private String mTosUrl;
  
  private void handleTosAccepted()
  {
    logClickEvent(848);
    this.mListener.onAddCarrierBillingResult(0);
  }
  
  private void handleTosDeclined()
  {
    logClickEvent(849);
    this.mListener.onAddCarrierBillingResult(1);
  }
  
  public static AddCarrierBillingFragment newInstance(int paramInt1, SubscriberInfo paramSubscriberInfo, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
  {
    AddCarrierBillingFragment localAddCarrierBillingFragment = new AddCarrierBillingFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("type", paramInt1);
    localBundle.putParcelable("prefill_address", paramSubscriberInfo);
    localBundle.putString("prefill_snippet", paramString2);
    localBundle.putString("tos_url", paramString1);
    localBundle.putString("carrier_name", paramString3);
    localBundle.putString("authAccount", paramString4);
    localBundle.putInt("ui_mode", paramInt2);
    localAddCarrierBillingFragment.setArguments(localBundle);
    return localAddCarrierBillingFragment;
  }
  
  private void setAddressToFull(View paramView, SubscriberInfo paramSubscriberInfo)
  {
    String str1 = getString(2131361901);
    ((TextView)paramView.findViewById(2131755320)).setText(str1);
    SubscriberInfo localSubscriberInfo;
    TextView localTextView;
    if (paramSubscriberInfo != null)
    {
      localSubscriberInfo = paramSubscriberInfo;
      AddressData localAddressData = new AddressData.Builder().setRecipient(localSubscriberInfo.mName).setAddressLine1(localSubscriberInfo.mAddress1).setAddressLine2(localSubscriberInfo.mAddress2).setLocality(localSubscriberInfo.mCity).setAdminArea(localSubscriberInfo.mState).setPostalCode(localSubscriberInfo.mPostalCode).setCountry(localSubscriberInfo.mCountry).build();
      localTextView = (TextView)paramView.findViewById(2131755321);
      if ((TextUtils.isEmpty(localAddressData.mRecipient)) && (TextUtils.isEmpty(localAddressData.mAddressLine1)) && (TextUtils.isEmpty(localAddressData.mAddressLine2)) && (TextUtils.isEmpty(localAddressData.mLocality)) && (TextUtils.isEmpty(localAddressData.mAdministrativeArea)) && (TextUtils.isEmpty(localAddressData.mPostalCode)) && (TextUtils.isEmpty(localAddressData.mPostalCountry))) {
        break label264;
      }
      localTextView.setVisibility(0);
      localTextView.setText(TextUtils.join("\n", AddressWidget.getFullEnvelopeAddress(localAddressData, getActivity().getBaseContext())));
    }
    for (;;)
    {
      String str2 = localSubscriberInfo.mIdentifier;
      if (Utils.isEmptyOrSpaces(str2)) {
        str2 = PhoneNumberUtils.formatNumber(BillingLocator.getLine1NumberFromTelephony());
      }
      showPhoneNumber((TextView)paramView.findViewById(2131755323), str2);
      return;
      localSubscriberInfo = new SubscriberInfo.Builder().build();
      break;
      label264:
      localTextView.setVisibility(8);
    }
  }
  
  private void setAddressToMinimalAddress(View paramView, SubscriberInfo paramSubscriberInfo)
  {
    String str1 = getString(2131361901);
    ((TextView)paramView.findViewById(2131755320)).setText(str1);
    String str2 = paramSubscriberInfo.mIdentifier;
    showPhoneNumber((TextView)paramView.findViewById(2131755323), str2);
  }
  
  private void setAddressToSnippet(View paramView, String paramString1, String paramString2)
  {
    String str1 = getString(2131361902, new Object[] { paramString2 });
    ((TextView)paramView.findViewById(2131755320)).setText(str1);
    ((TextView)paramView.findViewById(2131755321)).setText(paramString1);
    String str2 = BillingLocator.getLine1NumberFromTelephony();
    showPhoneNumber((TextView)paramView.findViewById(2131755323), str2);
  }
  
  private static void showAddressSection(View paramView, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      paramView.findViewById(2131755320).setVisibility(i);
      paramView.findViewById(2131755322).setVisibility(i);
      paramView.findViewById(2131755321).setVisibility(i);
      paramView.findViewById(2131755323).setVisibility(i);
      return;
    }
  }
  
  private static void showPhoneNumber(TextView paramTextView, String paramString)
  {
    if (!Utils.isEmptyOrSpaces(paramString))
    {
      paramTextView.setVisibility(0);
      paramTextView.setText(paramString);
      return;
    }
    paramTextView.setVisibility(8);
  }
  
  public final void enableUi(boolean paramBoolean)
  {
    if ((this.mSetupWizardNavBar == null) && (this.mAcceptButton == null)) {
      return;
    }
    if (this.mSetupWizardNavBar != null)
    {
      this.mSetupWizardNavBar.mNextButton.setEnabled(paramBoolean);
      this.mSetupWizardNavBar.mBackButton.setEnabled(paramBoolean);
    }
    for (;;)
    {
      this.mEditAddressButton.setEnabled(paramBoolean);
      return;
      this.mAcceptButton.setEnabled(paramBoolean);
      this.mDeclineButton.setEnabled(paramBoolean);
    }
  }
  
  protected final int getPlayStoreUiElementType()
  {
    if (this.mBillingUiMode == 0) {
      return 843;
    }
    return 895;
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mAcceptButton) {
      handleTosAccepted();
    }
    do
    {
      return;
      if (paramView == this.mDeclineButton)
      {
        handleTosDeclined();
        return;
      }
    } while (paramView != this.mEditAddressButton);
    logClickEvent(844);
    this.mListener.onAddCarrierBillingResult(2);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    String str1 = localBundle.getString("tos_url");
    if (!TextUtils.isEmpty(str1))
    {
      String str5 = getString(2131362800);
      if (!TextUtils.isEmpty(str5)) {
        str1 = str1.replace("%locale%", str5);
      }
      this.mTosUrl = BillingUtils.replaceLocale(str1);
    }
    this.mBillingUiMode = localBundle.getInt("ui_mode");
    int i;
    View localView;
    label148:
    SubscriberInfo localSubscriberInfo;
    String str2;
    String str3;
    String str4;
    label289:
    TextView localTextView1;
    label396:
    TextView localTextView2;
    int k;
    if (this.mBillingUiMode == 0)
    {
      i = 2130968657;
      localView = paramLayoutInflater.inflate(i, paramViewGroup, false);
      this.mSetupWizardNavBar = ((InstrumentActivity)getActivity()).mSetupWizardNavBar;
      if (this.mSetupWizardNavBar == null) {
        break label484;
      }
      this.mSetupWizardTosSelection = ((RadioGroup)localView.findViewById(2131756108));
      this.mSetupWizardNavBar.mNextButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if ((AddCarrierBillingFragment.this.mSetupWizardTosSelection.getVisibility() == 8) || (AddCarrierBillingFragment.this.mSetupWizardTosSelection.getCheckedRadioButtonId() == 2131756109))
          {
            AddCarrierBillingFragment.this.handleTosAccepted();
            return;
          }
          AddCarrierBillingFragment.this.handleTosDeclined();
        }
      });
      this.mEditAddressButton = ((ImageButton)localView.findViewById(2131755322));
      this.mEditAddressButton.setOnClickListener(this);
      int j = localBundle.getInt("type");
      localSubscriberInfo = (SubscriberInfo)localBundle.getParcelable("prefill_address");
      str2 = localBundle.getString("prefill_snippet");
      str3 = localBundle.getString("carrier_name");
      str4 = localBundle.getString("authAccount");
      switch (j)
      {
      default: 
        FinskyLog.d("Unexpected type " + j, new Object[0]);
        localTextView1 = (TextView)localView.findViewById(2131755324);
        if (this.mTosUrl != null)
        {
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = getString(2131361811);
          arrayOfObject[1] = this.mTosUrl;
          arrayOfObject[2] = str3;
          localTextView1.setText(Html.fromHtml(getString(2131362074, arrayOfObject)));
          localTextView1.setMovementMethod(LinkMovementMethod.getInstance());
          localTextView1.setLinkTextColor(localTextView1.getTextColors());
          localTextView1.setVisibility(0);
          if (this.mSetupWizardTosSelection != null) {
            this.mSetupWizardTosSelection.setVisibility(0);
          }
          ((TextView)localView.findViewById(2131755318)).setText(getString(2131361823, new Object[] { str3 }));
          localTextView2 = (TextView)localView.findViewById(2131755319);
          k = 1;
          if (!TextUtils.isEmpty(str4)) {
            break label784;
          }
          FinskyLog.wtf("Should have accountName available.", new Object[0]);
        }
        break;
      }
    }
    for (;;)
    {
      if (k == 0) {
        break label805;
      }
      localTextView2.setText(2131362488);
      return localView;
      i = 2130969101;
      break;
      label484:
      this.mAcceptButton = ((Button)localView.findViewById(2131755302));
      this.mDeclineButton = ((Button)localView.findViewById(2131755301));
      if ((this.mAcceptButton instanceof PlayActionButton)) {
        ((PlayActionButton)this.mAcceptButton).configure(3, 2131361811, this);
      }
      for (;;)
      {
        if (!FinskyApp.get().getExperiments(localBundle.getString("authAccount")).isEnabled(12603132L)) {
          break label594;
        }
        this.mDeclineButton.setVisibility(8);
        break;
        this.mAcceptButton.setOnClickListener(this);
        this.mAcceptButton.setText(2131361811);
      }
      label594:
      if ((this.mDeclineButton instanceof PlayActionButton))
      {
        ((PlayActionButton)this.mDeclineButton).configure(0, 2131362077, this);
        break label148;
      }
      this.mDeclineButton.setOnClickListener(this);
      this.mDeclineButton.setText(2131362077);
      break label148;
      showAddressSection(localView, true);
      setAddressToSnippet(localView, str2, str3);
      break label289;
      showAddressSection(localView, true);
      setAddressToSnippet(localView, str2, str3);
      break label289;
      showAddressSection(localView, true);
      setAddressToFull(localView, localSubscriberInfo);
      break label289;
      showAddressSection(localView, true);
      setAddressToFull(localView, localSubscriberInfo);
      break label289;
      showAddressSection(localView, true);
      setAddressToMinimalAddress(localView, localSubscriberInfo);
      break label289;
      showAddressSection(localView, true);
      setAddressToMinimalAddress(localView, localSubscriberInfo);
      break label289;
      showAddressSection(localView, false);
      break label289;
      localTextView1.setVisibility(8);
      if (this.mSetupWizardTosSelection == null) {
        break label396;
      }
      this.mSetupWizardTosSelection.setVisibility(8);
      break label396;
      label784:
      if (PurchaseAuthUtils.getPurchaseAuthType(str4) != 2) {
        k = 1;
      } else {
        k = 0;
      }
    }
    label805:
    localTextView2.setText(2131362489);
    return localView;
  }
  
  public static abstract interface AddCarrierBillingResultListener
  {
    public abstract void onAddCarrierBillingResult(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.fragment.AddCarrierBillingFragment
 * JD-Core Version:    0.7.0.1
 */