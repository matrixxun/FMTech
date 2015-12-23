package com.google.android.finsky.billing.giftcard.steps;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.finsky.activities.ErrorDialog;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.GetBillingCountriesAction;
import com.google.android.finsky.billing.giftcard.RedeemCodeFragment;
import com.google.android.finsky.billing.giftcard.RedeemCodeSidecar;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.layout.BillingAddress;
import com.google.android.finsky.layout.BillingAddress.BillingCountryChangeListener;
import com.google.android.finsky.protos.Address;
import com.google.android.finsky.protos.BillingAddressSpec;
import com.google.android.finsky.protos.ChallengeProto.AddressChallenge;
import com.google.android.finsky.protos.ChallengeProto.Country;
import com.google.android.finsky.protos.ChallengeProto.FormCheckbox;
import com.google.android.finsky.protos.ChallengeProto.InputValidationError;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class AddressChallengeStep
  extends StepFragment<RedeemCodeFragment>
{
  private BillingAddressSpec mAddressSpec;
  private BillingAddress mBillingAddress;
  private int mBillingUiMode;
  private ChallengeProto.AddressChallenge mChallenge;
  private List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> mCountries;
  private ViewGroup mMainView;
  private PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(1104);
  private Bundle mSavedInstanceState;
  
  private void setupWidgets(Bundle paramBundle)
  {
    this.mBillingAddress.setBillingCountries(this.mCountries);
    if (paramBundle != null)
    {
      this.mBillingAddress.restoreInstanceState(paramBundle);
      return;
    }
    if ((this.mChallenge.address != null) && (!TextUtils.isEmpty(this.mChallenge.address.postalCountry)))
    {
      VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry2 = BillingUtils.findCountry(this.mChallenge.address.postalCountry, this.mCountries);
      this.mBillingAddress.setAddressSpec(localCountry2, this.mAddressSpec, this.mChallenge.address);
    }
    for (;;)
    {
      new Handler(Looper.getMainLooper()).post(new Runnable()
      {
        public final void run()
        {
          for (ChallengeProto.InputValidationError localInputValidationError : AddressChallengeStep.this.mChallenge.errorInputField) {
            AddressChallengeStep.this.mBillingAddress.displayError(localInputValidationError);
          }
        }
      });
      return;
      VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry1 = BillingUtils.findCountry(BillingUtils.getDefaultCountry(getActivity(), null), this.mCountries);
      this.mBillingAddress.setAddressSpec(localCountry1, this.mAddressSpec, null);
    }
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return getString(2131362062);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    logClick(1105, null);
    List localList = this.mBillingAddress.getAddressValidationErrors();
    this.mBillingAddress.clearErrorMessage();
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      ChallengeProto.InputValidationError localInputValidationError = (ChallengeProto.InputValidationError)localIterator.next();
      TextView localTextView2 = this.mBillingAddress.displayError(localInputValidationError);
      if (localTextView2 != null) {
        localArrayList1.add(localTextView2);
      }
    }
    TextView localTextView1 = (TextView)BillingUtils.getTopMostView(this.mMainView, localArrayList1);
    if (localTextView1 != null) {
      localTextView1.requestFocus();
    }
    int i = localList.size();
    Address localAddress = null;
    if (i == 0) {
      localAddress = this.mBillingAddress.getAddress();
    }
    String[] arrayOfString;
    RedeemCodeSidecar localRedeemCodeSidecar;
    if (localAddress != null)
    {
      RedeemCodeFragment localRedeemCodeFragment = (RedeemCodeFragment)this.mParentFragment;
      ArrayList localArrayList2 = new ArrayList();
      int j = this.mChallenge.checkbox.length;
      for (int k = 0; k < j; k++) {
        if (((CheckBox)this.mMainView.findViewWithTag(this.mChallenge.checkbox[k])).isChecked()) {
          localArrayList2.add(this.mChallenge.checkbox[k].id);
        }
      }
      arrayOfString = (String[])localArrayList2.toArray(new String[localArrayList2.size()]);
      localRedeemCodeSidecar = localRedeemCodeFragment.mSidecar;
      if (localRedeemCodeSidecar.mState != 4)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(localRedeemCodeSidecar.mState);
        FinskyLog.wtf("Invalid state: %d", arrayOfObject);
      }
    }
    else
    {
      return;
    }
    localRedeemCodeSidecar.mRequest.address = localAddress;
    localRedeemCodeSidecar.mRequest.addressCheckedCheckboxId = arrayOfString;
    localRedeemCodeSidecar.sendRedemptionRequest();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mChallenge = ((ChallengeProto.AddressChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "ConfirmationStep.challenge"));
    this.mBillingUiMode = this.mArguments.getInt("ui_mode");
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mSavedInstanceState = paramBundle;
    int i;
    TextView localTextView2;
    if (this.mBillingUiMode == 0)
    {
      i = 2130969061;
      this.mMainView = ((ViewGroup)paramLayoutInflater.inflate(i, paramViewGroup, false));
      this.mAddressSpec = new BillingAddressSpec();
      int j = this.mChallenge.requiredField.length;
      this.mAddressSpec.requiredField = new int[j];
      System.arraycopy(this.mChallenge.requiredField, 0, this.mAddressSpec.requiredField, 0, j);
      if ((!TextUtils.isEmpty(this.mChallenge.errorHtml)) && (paramBundle == null)) {
        this.mMainView.post(new Runnable()
        {
          public final void run()
          {
            ErrorDialog.show(AddressChallengeStep.this.mFragmentManager, null, AddressChallengeStep.this.mChallenge.errorHtml, false);
          }
        });
      }
      TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755173);
      if (!TextUtils.isEmpty(this.mChallenge.title))
      {
        if (localTextView1 != null) {
          localTextView1.setText(this.mChallenge.title);
        }
        if (this.mBillingUiMode == 1) {
          getActivity().setTitle(this.mChallenge.title);
        }
      }
      localTextView2 = (TextView)this.mMainView.findViewById(2131755211);
      if (TextUtils.isEmpty(this.mChallenge.descriptionHtml)) {
        break label380;
      }
      localTextView2.setText(Html.fromHtml(this.mChallenge.descriptionHtml));
      localTextView2.setLinkTextColor(localTextView2.getTextColors());
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
    }
    for (;;)
    {
      LayoutInflater localLayoutInflater = LayoutInflater.from(getActivity());
      ViewGroup localViewGroup = (ViewGroup)this.mMainView.findViewById(2131755214);
      int k = 1 + localViewGroup.indexOfChild(localTextView2);
      for (int m = 0; m < this.mChallenge.checkbox.length; m++)
      {
        ChallengeProto.FormCheckbox localFormCheckbox = this.mChallenge.checkbox[m];
        CheckBox localCheckBox = (CheckBox)localLayoutInflater.inflate(2130968626, this.mMainView, false);
        localCheckBox.setText(localFormCheckbox.description);
        localCheckBox.setTag(localFormCheckbox);
        localCheckBox.setChecked(localFormCheckbox.checked);
        localViewGroup.addView(localCheckBox, k + m);
      }
      i = 2130969109;
      break;
      label380:
      localTextView2.setVisibility(8);
    }
    this.mBillingAddress = ((BillingAddress)this.mMainView.findViewById(2131755272));
    this.mBillingAddress.setBillingCountryChangeListener(new BillingAddress.BillingCountryChangeListener()
    {
      public final void onBillingCountryChanged(VendingProtos.PurchaseMetadataResponseProto.Countries.Country paramAnonymousCountry)
      {
        AddressChallengeStep.this.mBillingAddress.setAddressSpec(paramAnonymousCountry, AddressChallengeStep.this.mAddressSpec, null);
      }
    });
    if (this.mChallenge.supportedCountry.length > 0)
    {
      this.mCountries = Lists.newArrayList(this.mChallenge.supportedCountry.length);
      for (ChallengeProto.Country localCountry : this.mChallenge.supportedCountry)
      {
        VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry1 = new VendingProtos.PurchaseMetadataResponseProto.Countries.Country();
        localCountry1.countryCode = localCountry.regionCode;
        localCountry1.hasCountryCode = true;
        localCountry1.countryName = localCountry.displayName;
        localCountry1.hasCountryName = true;
        this.mCountries.add(localCountry1);
      }
      setupWidgets(this.mSavedInstanceState);
    }
    for (;;)
    {
      return this.mMainView;
      new GetBillingCountriesAction().run(this.mArguments.getString("authAccount"), new Runnable()
      {
        public final void run()
        {
          AddressChallengeStep.access$300(AddressChallengeStep.this);
        }
      });
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mBillingAddress != null) {
      this.mBillingAddress.saveInstanceState(paramBundle);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.giftcard.steps.AddressChallengeStep
 * JD-Core Version:    0.7.0.1
 */