package com.google.android.finsky.billing.carrierbilling.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressData.Builder;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblems;
import com.android.i18n.addressinput.AddressWidget;
import com.android.i18n.addressinput.AddressWidget.Listener;
import com.android.i18n.addressinput.FormOptions;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.AddressMetadataCacheManager;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo.Builder;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.InstrumentActivity;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.fragments.LoggingFragment;
import com.google.android.finsky.layout.AddressFieldsLayout;
import com.google.android.finsky.setup.SetupWizardNavBar;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.layout.PlayActionButton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class EditCarrierBillingFragment
  extends LoggingFragment
  implements View.OnClickListener
{
  private int mAddressMode;
  private AddressWidget mAddressWidget;
  private int mBillingUiMode;
  private Button mCancelButton;
  private ViewGroup mEditSection;
  public EditCarrierBillingResultListener mListener;
  private TextView mNameView;
  private EditText mPhoneNumberEditView;
  private Button mSaveButton;
  
  private void displayErrors(Collection<Integer> paramCollection)
  {
    Object localObject1 = null;
    int i = 0;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Object localObject2;
      switch (((Integer)localIterator.next()).intValue())
      {
      default: 
        break;
      case 0: 
        localObject2 = this.mNameView;
        setTextViewError((TextView)localObject2, getString(2131362360), 2131362272);
      case 1: 
      case 3: 
      case 4: 
      case 5: 
      case 2: 
      case 6: 
      case 7: 
        for (;;)
        {
          if (localObject1 != null) {
            break label354;
          }
          localObject1 = localObject2;
          i = BillingUtils.getViewOffsetToChild(this.mEditSection, (View)localObject2);
          break;
          localObject2 = this.mAddressWidget.getViewForField(AddressField.ADDRESS_LINE_1);
          if (localObject2 != null)
          {
            setTextViewError((TextView)localObject2, this.mAddressWidget.getNameForField(AddressField.ADDRESS_LINE_1), 2131362260);
            continue;
            localObject2 = this.mAddressWidget.getViewForField(AddressField.ADDRESS_LINE_2);
            if (localObject2 != null)
            {
              setTextViewError((TextView)localObject2, this.mAddressWidget.getNameForField(AddressField.ADDRESS_LINE_2), 2131362260);
              continue;
              localObject2 = this.mAddressWidget.getViewForField(AddressField.LOCALITY);
              if (localObject2 != null)
              {
                setTextViewError((TextView)localObject2, this.mAddressWidget.getNameForField(AddressField.LOCALITY), 2131362262);
                continue;
                localObject2 = this.mAddressWidget.getViewForField(AddressField.POSTAL_CODE);
                if (localObject2 != null)
                {
                  this.mAddressWidget.displayErrorMessageForInvalidEntryIn(AddressField.POSTAL_CODE);
                  continue;
                  localObject2 = this.mAddressWidget.getViewForField(AddressField.ADMIN_AREA);
                  if (localObject2 != null)
                  {
                    this.mAddressWidget.displayErrorMessageForInvalidEntryIn(AddressField.ADMIN_AREA);
                    continue;
                    int j = this.mPhoneNumberEditView.getVisibility();
                    localObject2 = null;
                    if (j == 0)
                    {
                      localObject2 = this.mPhoneNumberEditView;
                      setTextViewError((TextView)localObject2, getString(2131362538), 2131362274);
                    }
                  }
                }
              }
            }
          }
        }
        label354:
        if (localObject2 != null)
        {
          int k = BillingUtils.getViewOffsetToChild(this.mEditSection, (View)localObject2);
          if (k < i)
          {
            localObject1 = localObject2;
            i = k;
          }
        }
        break;
      }
    }
    if (localObject1 != null) {
      localObject1.requestFocus();
    }
  }
  
  private String getPhoneNumber()
  {
    return this.mPhoneNumberEditView.getText().toString();
  }
  
  private SubscriberInfo getReturnAddress()
  {
    AddressData localAddressData = this.mAddressWidget.getAddressData();
    SubscriberInfo.Builder localBuilder = new SubscriberInfo.Builder();
    localBuilder.name = this.mNameView.getText().toString();
    localBuilder.postalCode = localAddressData.mPostalCode;
    localBuilder.country = localAddressData.mPostalCountry;
    localBuilder.identifier = getPhoneNumber();
    if (this.mAddressMode == 0)
    {
      localBuilder.address1 = localAddressData.mAddressLine1;
      localBuilder.address2 = localAddressData.mAddressLine2;
      localBuilder.city = localAddressData.mLocality;
      localBuilder.state = localAddressData.mAdministrativeArea;
    }
    return localBuilder.build();
  }
  
  public static EditCarrierBillingFragment newInstance(String paramString, int paramInt1, SubscriberInfo paramSubscriberInfo, ArrayList<Integer> paramArrayList, int paramInt2)
  {
    EditCarrierBillingFragment localEditCarrierBillingFragment = new EditCarrierBillingFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putParcelable("prefill_address", paramSubscriberInfo);
    localBundle.putInt("type", paramInt1);
    localBundle.putIntegerArrayList("highlight_address", paramArrayList);
    localBundle.putInt("ui_mode", paramInt2);
    localEditCarrierBillingFragment.setArguments(localBundle);
    return localEditCarrierBillingFragment;
  }
  
  private void setTextViewError(TextView paramTextView, String paramString, int paramInt)
  {
    UiUtils.setErrorOnTextView(paramTextView, paramString, getString(paramInt));
  }
  
  private void showAddressEditView(View paramView, AddressData paramAddressData)
  {
    FormOptions localFormOptions = BillingUtils.getAddressFormOptions(this.mAddressMode);
    AddressFieldsLayout localAddressFieldsLayout = (AddressFieldsLayout)paramView.findViewById(2131755267);
    if (paramAddressData == null) {}
    for (String str = null;; str = paramAddressData.mPostalCountry)
    {
      this.mAddressWidget = new AddressWidget(getActivity(), localAddressFieldsLayout, localFormOptions, new AddressMetadataCacheManager(FinskyApp.get().mCache), str);
      this.mAddressWidget.mListener = new AddressWidget.Listener()
      {
        public final void onInitialized()
        {
          ArrayList localArrayList = EditCarrierBillingFragment.this.mArguments.getIntegerArrayList("highlight_address");
          if (localArrayList != null) {
            EditCarrierBillingFragment.this.displayErrors(EditCarrierBillingFragment.access$000$6baef20c(localArrayList));
          }
        }
      };
      this.mAddressWidget.renderFormWithSavedAddress(paramAddressData);
      return;
    }
  }
  
  private void showPhoneNumberEditView(String paramString)
  {
    this.mPhoneNumberEditView.setVisibility(0);
    if (!Utils.isEmptyOrSpaces(paramString)) {
      this.mPhoneNumberEditView.setText(paramString);
    }
  }
  
  protected final int getPlayStoreUiElementType()
  {
    if (this.mBillingUiMode == 0) {
      return 845;
    }
    return 896;
  }
  
  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (paramBundle != null) {
      this.mAddressWidget.restoreInstanceState(paramBundle);
    }
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mSaveButton)
    {
      str1 = this.mNameView.getText().toString();
      str2 = getPhoneNumber();
      localAddressProblems = this.mAddressWidget.getAddressProblems();
      i = this.mAddressMode;
      localArrayList = new ArrayList();
      if (Utils.isEmptyOrSpaces(str1)) {
        localArrayList.add(Integer.valueOf(0));
      }
      if (Utils.isEmptyOrSpaces(str2)) {
        localArrayList.add(Integer.valueOf(7));
      }
      localIterator = localAddressProblems.mProblems.entrySet().iterator();
      if (localIterator.hasNext())
      {
        localEntry = (Map.Entry)localIterator.next();
        if (1 == i) {
          switch (com.google.android.finsky.billing.carrierbilling.PhoneCarrierBillingUtils.1.$SwitchMap$com$android$i18n$addressinput$AddressField[((AddressField)localEntry.getKey()).ordinal()])
          {
          }
        }
        localAddressField = (AddressField)localEntry.getKey();
        switch (com.google.android.finsky.billing.carrierbilling.PhoneCarrierBillingUtils.1.$SwitchMap$com$android$i18n$addressinput$AddressField[localAddressField.ordinal()])
        {
        default: 
          j = -1;
        }
        for (;;)
        {
          localArrayList.add(Integer.valueOf(j));
          break;
          j = 3;
          continue;
          j = 4;
          continue;
          j = 5;
          continue;
          j = 6;
          continue;
          j = 2;
          continue;
          j = 1;
        }
      }
      logClickEvent(846);
      if (localArrayList.isEmpty())
      {
        UiUtils.hideKeyboard(getActivity(), this.mEditSection);
        this.mListener.onEditCarrierBillingResult(getReturnAddress());
      }
    }
    while (paramView != this.mCancelButton)
    {
      String str1;
      String str2;
      AddressProblems localAddressProblems;
      int i;
      ArrayList localArrayList;
      Iterator localIterator;
      Map.Entry localEntry;
      AddressField localAddressField;
      int j;
      return;
      displayErrors(localArrayList);
      return;
    }
    logClickEvent(847);
    this.mListener.onEditCarrierBillingResult(null);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    Bundle localBundle = this.mArguments;
    this.mBillingUiMode = this.mArguments.getInt("ui_mode");
    int i;
    View localView;
    ViewGroup localViewGroup;
    if (this.mBillingUiMode == 0)
    {
      i = 2130968631;
      localView = paramLayoutInflater.inflate(i, paramViewGroup, false);
      localViewGroup = (ViewGroup)localView.findViewById(2131755280);
      localViewGroup.setFocusableInTouchMode(true);
      localViewGroup.requestFocus();
      this.mEditSection = localViewGroup;
      this.mNameView = ((EditText)localViewGroup.findViewById(2131755281));
      this.mPhoneNumberEditView = ((EditText)localViewGroup.findViewById(2131755283));
      SetupWizardNavBar localSetupWizardNavBar = ((InstrumentActivity)getActivity()).mSetupWizardNavBar;
      if (localSetupWizardNavBar == null) {
        break label342;
      }
      this.mSaveButton = localSetupWizardNavBar.mNextButton;
      this.mCancelButton = localSetupWizardNavBar.mBackButton;
      label138:
      if (!(this.mSaveButton instanceof PlayActionButton)) {
        break label375;
      }
      ((PlayActionButton)this.mSaveButton).configure(3, 2131362709, this);
      label163:
      if (!FinskyApp.get().getExperiments(localBundle.getString("authAccount")).isEnabled(12603132L)) {
        break label396;
      }
      this.mCancelButton.setVisibility(8);
    }
    for (;;)
    {
      this.mAddressMode = localBundle.getInt("type");
      SubscriberInfo localSubscriberInfo = (SubscriberInfo)localBundle.getParcelable("prefill_address");
      if (localSubscriberInfo == null) {
        break label445;
      }
      String str1 = localSubscriberInfo.mName;
      this.mNameView.setText(str1);
      String str2 = localSubscriberInfo.mIdentifier;
      if (Utils.isEmptyOrSpaces(str2)) {
        str2 = PhoneNumberUtils.formatNumber(BillingLocator.getLine1NumberFromTelephony());
      }
      showPhoneNumberEditView(str2);
      showAddressEditView(localViewGroup, new AddressData.Builder().setAddressLine1(localSubscriberInfo.mAddress1).setAddressLine2(localSubscriberInfo.mAddress2).setLocality(localSubscriberInfo.mCity).setAdminArea(localSubscriberInfo.mState).setPostalCode(localSubscriberInfo.mPostalCode).setCountry(localSubscriberInfo.mCountry).build());
      return localView;
      i = 2130969100;
      break;
      label342:
      this.mSaveButton = ((Button)localView.findViewById(2131755302));
      this.mCancelButton = ((Button)localView.findViewById(2131755301));
      break label138;
      label375:
      this.mSaveButton.setOnClickListener(this);
      this.mSaveButton.setText(2131362709);
      break label163;
      label396:
      if ((this.mCancelButton instanceof PlayActionButton))
      {
        ((PlayActionButton)this.mCancelButton).configure(0, 2131361915, this);
      }
      else
      {
        this.mCancelButton.setOnClickListener(this);
        this.mCancelButton.setText(2131361915);
      }
    }
    label445:
    showPhoneNumberEditView(PhoneNumberUtils.formatNumber(BillingLocator.getLine1NumberFromTelephony()));
    showAddressEditView(localViewGroup, null);
    return localView;
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mAddressWidget.saveInstanceState(paramBundle);
  }
  
  public static abstract interface EditCarrierBillingResultListener
  {
    public abstract void onEditCarrierBillingResult(SubscriberInfo paramSubscriberInfo);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.carrierbilling.fragment.EditCarrierBillingFragment
 * JD-Core Version:    0.7.0.1
 */