package com.google.android.finsky.billing.challenge;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.google.android.finsky.activities.ErrorDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.GetBillingCountriesAction;
import com.google.android.finsky.layout.BillingAddress;
import com.google.android.finsky.layout.BillingAddress.BillingCountryChangeListener;
import com.google.android.finsky.protos.Address;
import com.google.android.finsky.protos.BillingAddressSpec;
import com.google.android.finsky.protos.ChallengeProto.AddressChallenge;
import com.google.android.finsky.protos.ChallengeProto.Country;
import com.google.android.finsky.protos.ChallengeProto.FormCheckbox;
import com.google.android.finsky.protos.ChallengeProto.InputValidationError;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class AddressChallengeFragment
  extends Fragment
  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SimpleAlertDialog.Listener
{
  private BillingAddressSpec mAddressSpec;
  private BillingAddress mBillingAddress;
  private Button mCancelButton;
  private ChallengeProto.AddressChallenge mChallenge;
  private List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> mCountries;
  AddressChallengeResultListener mListener;
  private ViewGroup mMainView;
  private Bundle mPreviousState;
  private Button mSaveButton;
  private Bundle mSavedInstanceState;
  
  private void initializeCountriesFromChallenge()
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
    syncContinueButton();
    setupWidgets(this.mSavedInstanceState);
  }
  
  private void loadBillingCountries()
  {
    if (this.mListener != null) {
      this.mListener.onInitializing();
    }
    new GetBillingCountriesAction().run(this.mArguments.getString("authAccount"), new Runnable()
    {
      public final void run()
      {
        AddressChallengeFragment.access$500(AddressChallengeFragment.this);
      }
    });
  }
  
  public static AddressChallengeFragment newInstance(String paramString, ChallengeProto.AddressChallenge paramAddressChallenge, Bundle paramBundle)
  {
    AddressChallengeFragment localAddressChallengeFragment = new AddressChallengeFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("address_challenge", ParcelableProto.forProto(paramAddressChallenge));
    localAddressChallengeFragment.setArguments(localBundle);
    localAddressChallengeFragment.mPreviousState = paramBundle;
    return localAddressChallengeFragment;
  }
  
  private void saveMyState(Bundle paramBundle)
  {
    for (int i = 0; i < this.mChallenge.checkbox.length; i++)
    {
      CheckBox localCheckBox = (CheckBox)this.mMainView.findViewWithTag(this.mChallenge.checkbox[i]);
      paramBundle.putBoolean("checkbox_" + i, localCheckBox.isChecked());
    }
    if (this.mBillingAddress != null) {
      this.mBillingAddress.saveInstanceState(paramBundle);
    }
  }
  
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
          for (ChallengeProto.InputValidationError localInputValidationError : AddressChallengeFragment.this.mChallenge.errorInputField) {
            AddressChallengeFragment.this.mBillingAddress.displayError(localInputValidationError);
          }
        }
      });
      return;
      VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry1 = BillingUtils.findCountry(BillingUtils.getDefaultCountry(getActivity(), null), this.mCountries);
      this.mBillingAddress.setAddressSpec(localCountry1, this.mAddressSpec, null);
    }
  }
  
  private void syncContinueButton()
  {
    boolean bool = true;
    int i = 0;
    if (i < this.mChallenge.checkbox.length)
    {
      ChallengeProto.FormCheckbox localFormCheckbox = this.mChallenge.checkbox[i];
      CheckBox localCheckBox = (CheckBox)this.mMainView.findViewWithTag(localFormCheckbox);
      if ((bool) && ((!localFormCheckbox.required) || (localCheckBox.isChecked()))) {}
      for (bool = true;; bool = false)
      {
        i++;
        break;
      }
    }
    this.mSaveButton.setEnabled(bool);
  }
  
  public final void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    syncContinueButton();
  }
  
  public final void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
    case 2131755302: 
      Address localAddress;
      do
      {
        return;
        List localList = this.mBillingAddress.getAddressValidationErrors();
        this.mBillingAddress.clearErrorMessage();
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          ChallengeProto.InputValidationError localInputValidationError = (ChallengeProto.InputValidationError)localIterator.next();
          TextView localTextView2 = this.mBillingAddress.displayError(localInputValidationError);
          if (localTextView2 != null) {
            localArrayList.add(localTextView2);
          }
        }
        TextView localTextView1 = (TextView)BillingUtils.getTopMostView(this.mMainView, localArrayList);
        if (localTextView1 != null) {
          localTextView1.requestFocus();
        }
        int i = localList.size();
        localAddress = null;
        if (i == 0) {
          localAddress = this.mBillingAddress.getAddress();
        }
      } while (localAddress == null);
      AddressChallengeResultListener localAddressChallengeResultListener = this.mListener;
      int j = this.mChallenge.checkbox.length;
      boolean[] arrayOfBoolean = new boolean[j];
      for (int k = 0; k < j; k++) {
        arrayOfBoolean[k] = ((CheckBox)this.mMainView.findViewWithTag(this.mChallenge.checkbox[k])).isChecked();
      }
      localAddressChallengeResultListener.onAddressChallengeResult(0, localAddress, arrayOfBoolean);
      return;
    }
    this.mListener.onAddressChallengeResult(2, null, null);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (paramViewGroup == null) {
      return null;
    }
    this.mSavedInstanceState = paramBundle;
    this.mMainView = ((ViewGroup)paramLayoutInflater.inflate(2130968627, paramViewGroup, false));
    this.mChallenge = ((ChallengeProto.AddressChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "address_challenge"));
    this.mAddressSpec = new BillingAddressSpec();
    int i = this.mChallenge.requiredField.length;
    this.mAddressSpec.requiredField = new int[i];
    System.arraycopy(this.mChallenge.requiredField, 0, this.mAddressSpec.requiredField, 0, i);
    if ((!TextUtils.isEmpty(this.mChallenge.errorHtml)) && (paramBundle == null)) {
      this.mMainView.post(new Runnable()
      {
        public final void run()
        {
          ErrorDialog.show(AddressChallengeFragment.this.mFragmentManager, null, AddressChallengeFragment.this.mChallenge.errorHtml, false);
        }
      });
    }
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755270);
    TextView localTextView2;
    label220:
    ViewGroup localViewGroup;
    int j;
    int k;
    label258:
    CheckBox localCheckBox;
    if (!TextUtils.isEmpty(this.mChallenge.title))
    {
      localTextView1.setText(this.mChallenge.title);
      localTextView2 = (TextView)this.mMainView.findViewById(2131755271);
      if (TextUtils.isEmpty(this.mChallenge.descriptionHtml)) {
        break label373;
      }
      localTextView2.setText(Html.fromHtml(this.mChallenge.descriptionHtml));
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
      LayoutInflater localLayoutInflater = LayoutInflater.from(getActivity());
      localViewGroup = (ViewGroup)this.mMainView.findViewById(2131755214);
      j = 1 + localViewGroup.indexOfChild(localTextView2);
      k = 0;
      if (k >= this.mChallenge.checkbox.length) {
        break label451;
      }
      ChallengeProto.FormCheckbox localFormCheckbox = this.mChallenge.checkbox[k];
      localCheckBox = (CheckBox)localLayoutInflater.inflate(2130968626, this.mMainView, false);
      localCheckBox.setText(localFormCheckbox.description);
      localCheckBox.setTag(localFormCheckbox);
      if ((paramBundle != null) || (this.mPreviousState != null)) {
        break label383;
      }
      localCheckBox.setChecked(localFormCheckbox.checked);
    }
    for (;;)
    {
      localCheckBox.setOnCheckedChangeListener(this);
      localViewGroup.addView(localCheckBox, j + k);
      k++;
      break label258;
      localTextView1.setVisibility(8);
      break;
      label373:
      localTextView2.setVisibility(8);
      break label220;
      label383:
      if (this.mPreviousState != null) {
        localCheckBox.setChecked(this.mPreviousState.getBoolean("checkbox_" + k));
      } else {
        localCheckBox.setChecked(paramBundle.getBoolean("checkbox_" + k));
      }
    }
    label451:
    this.mBillingAddress = ((BillingAddress)this.mMainView.findViewById(2131755272));
    this.mBillingAddress.setBillingCountryChangeListener(new BillingAddress.BillingCountryChangeListener()
    {
      public final void onBillingCountryChanged(VendingProtos.PurchaseMetadataResponseProto.Countries.Country paramAnonymousCountry)
      {
        AddressChallengeFragment.this.mBillingAddress.setAddressSpec(paramAnonymousCountry, AddressChallengeFragment.this.mAddressSpec, null);
        if (AddressChallengeFragment.this.mListener != null)
        {
          Bundle localBundle = new Bundle();
          AddressChallengeFragment.this.saveMyState(localBundle);
          AddressChallengeFragment.this.mListener.onCountryChanged(paramAnonymousCountry.countryCode, localBundle);
        }
      }
    });
    this.mSaveButton = ((Button)this.mMainView.findViewById(2131755302));
    this.mSaveButton.setOnClickListener(this);
    this.mSaveButton.setEnabled(false);
    this.mSaveButton.setText(2131362062);
    this.mCancelButton = ((Button)this.mMainView.findViewById(2131755301));
    this.mCancelButton.setOnClickListener(this);
    this.mCancelButton.setText(2131361915);
    if (this.mChallenge.supportedCountry.length > 0) {
      initializeCountriesFromChallenge();
    }
    for (;;)
    {
      return this.mMainView;
      loadBillingCountries();
    }
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    this.mListener.onAddressChallengeResult(2, null, null);
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    loadBillingCountries();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    saveMyState(paramBundle);
  }
  
  public static abstract interface AddressChallengeResultListener
  {
    public abstract void onAddressChallengeResult(int paramInt, Address paramAddress, boolean[] paramArrayOfBoolean);
    
    public abstract void onCountryChanged(String paramString, Bundle paramBundle);
    
    public abstract void onInitialized();
    
    public abstract void onInitializing();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.challenge.AddressChallengeFragment
 * JD-Core Version:    0.7.0.1
 */