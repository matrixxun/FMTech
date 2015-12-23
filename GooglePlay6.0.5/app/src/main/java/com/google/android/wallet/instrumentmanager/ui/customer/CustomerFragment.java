package com.google.android.wallet.instrumentmanager.ui.customer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import com.google.android.wallet.analytics.SimpleUiNode;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.address.AddressUtils;
import com.google.android.wallet.common.address.RegionCode;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.common.util.ImUtils;
import com.google.android.wallet.instrumentmanager.ui.common.ImInfoMessageTextView;
import com.google.android.wallet.instrumentmanager.ui.creditcard.AddCreditCardFragment;
import com.google.android.wallet.instrumentmanager.ui.creditcard.AddCreditCardFragment.OnAddCreditCardFragmentStateChangedListener;
import com.google.android.wallet.nfc.NfcIntentForwarder;
import com.google.android.wallet.ui.address.AddressEntryFragment;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ClickSpan.OnClickListener;
import com.google.android.wallet.ui.common.Form;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.InfoMessageTextView;
import com.google.android.wallet.ui.common.RegionCodeSelectorSpinner.OnRegionCodeSelectedListener;
import com.google.android.wallet.ui.common.RegionCodeView;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.WebViewDialogFragment;
import com.google.android.wallet.ui.tax.TaxInfoEntryFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.CountrySelector;
import com.google.commerce.payments.orchestration.proto.ui.common.components.customer.CustomerFormOuterClass.CustomerForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageSetOuterClass.LegalMessageSet;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import java.util.ArrayList;
import java.util.List;

public final class CustomerFragment
  extends FormFragment<CustomerFormOuterClass.CustomerForm>
  implements UiNode, AddCreditCardFragment.OnAddCreditCardFragmentStateChangedListener, NfcIntentForwarder, ClickSpan.OnClickListener, RegionCodeSelectorSpinner.OnRegionCodeSelectedListener
{
  public ArrayList<FormFragment.FieldData<Form>> mFieldData = new ArrayList();
  public LegalMessageOuterClass.LegalMessage mLegalMessage;
  ImInfoMessageTextView mLegalMessageText;
  public RegionCodeView mRegionCodeView;
  private int[] mRegionCodes;
  int mSelectedRegionCode = 0;
  private final WalletUiElement mUiElement = new WalletUiElement(1665);
  
  private void setLegalMessage(LegalMessageOuterClass.LegalMessage paramLegalMessage)
  {
    this.mLegalMessage = paramLegalMessage;
    ImInfoMessageTextView localImInfoMessageTextView = this.mLegalMessageText;
    if (paramLegalMessage == null) {}
    for (InfoMessageOuterClass.InfoMessage localInfoMessage = null;; localInfoMessage = paramLegalMessage.messageText)
    {
      localImInfoMessageTextView.setInfoMessage(localInfoMessage);
      notifyFormEvent(6, Bundle.EMPTY);
      return;
    }
  }
  
  @TargetApi(14)
  public final void animateViewsBelow$4868d6cf(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      this.mLegalMessageText.setTranslationY(paramInt);
      this.mLegalMessageText.animate().translationY(0.0F).setStartDelay(150L).start();
      this.mLegalMessageText.animate().setStartDelay(0L);
    }
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    if (paramFormFieldMessage.formFieldReference.formId.equals(((CustomerFormOuterClass.CustomerForm)this.mFormProto).id)) {
      throw new IllegalArgumentException("Unknown FormFieldMessage fieldId: " + paramFormFieldMessage.formFieldReference.fieldId);
    }
    int i = 0;
    int j = this.mFieldData.size();
    while (i < j)
    {
      if (((Form)((FormFragment.FieldData)this.mFieldData.get(i)).field).applyFormFieldMessage(paramFormFieldMessage)) {
        return true;
      }
      i++;
    }
    return false;
  }
  
  public final void doEnableUi()
  {
    if (this.mLegalMessageText == null) {
      return;
    }
    boolean bool = this.mUiEnabled;
    if (this.mRegionCodeView != null) {
      this.mRegionCodeView.setEnabled(bool);
    }
    int i = 0;
    int j = this.mFieldData.size();
    while (i < j)
    {
      ((Form)((FormFragment.FieldData)this.mFieldData.get(i)).field).enableUi(bool);
      i++;
    }
    this.mLegalMessageText.setEnabled(bool);
  }
  
  public final void forwardNfcIntent(Intent paramIntent)
  {
    Fragment localFragment = getChildFragmentManager().findFragmentById(R.id.instrument_form_fragment_holder);
    if ((localFragment instanceof NfcIntentForwarder)) {
      ((NfcIntentForwarder)localFragment).forwardNfcIntent(paramIntent);
    }
  }
  
  public final String getButtonBarExpandButtonText()
  {
    return this.mLegalMessageText.getExpandLabel();
  }
  
  public final List<UiNode> getChildren()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.mRegionCodeView != null) {
      localArrayList.add(new SimpleUiNode(1668, this));
    }
    int i = 0;
    int j = this.mFieldData.size();
    while (i < j)
    {
      Form localForm = (Form)((FormFragment.FieldData)this.mFieldData.get(i)).field;
      if ((localForm instanceof UiNode)) {
        localArrayList.add((UiNode)localForm);
      }
      i++;
    }
    if (this.mLegalMessage != null) {
      localArrayList.add(this.mLegalMessageText);
    }
    return localArrayList;
  }
  
  public final List<FormFragment.FieldData<Form>> getFieldsForValidationInOrder()
  {
    return this.mFieldData;
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean handleErrorMessageDismissed(String paramString, int paramInt)
  {
    int i = 0;
    int j = this.mFieldData.size();
    while (i < j)
    {
      if (((Form)((FormFragment.FieldData)this.mFieldData.get(i)).field).handleErrorMessageDismissed(paramString, paramInt)) {
        return true;
      }
      i++;
    }
    return false;
  }
  
  public final boolean isReadyToSubmit()
  {
    int i = 0;
    int j = this.mFieldData.size();
    while (i < j)
    {
      if (!((Form)((FormFragment.FieldData)this.mFieldData.get(i)).field).isReadyToSubmit()) {
        return false;
      }
      i++;
    }
    return true;
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Fragment localFragment = getChildFragmentManager().findFragmentById(R.id.instrument_form_fragment_holder);
    if ((localFragment instanceof AddCreditCardFragment)) {
      localFragment.onActivityResult(paramInt1, paramInt2, paramIntent);
    }
  }
  
  public final void onButtonBarExpandButtonClicked()
  {
    this.mLegalMessageText.expand(true);
  }
  
  public final void onClick(View paramView, String paramString)
  {
    if ((paramView != this.mLegalMessageText) || (this.mFragmentManager.findFragmentByTag("tagTosWebViewDialog") != null)) {
      return;
    }
    WebViewDialogFragment.newInstance(paramString, this.mThemeResourceId).show(this.mFragmentManager, "tagTosWebViewDialog");
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mSelectedRegionCode = paramBundle.getInt("selectedRegionCode", 0);
      if (this.mSelectedRegionCode != 0) {
        this.mLegalMessage = PaymentUtils.findLegalMessageByCountry(((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalMessages, RegionCode.toCountryCode(this.mSelectedRegionCode));
      }
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_customer, null, false);
    this.mLegalMessageText = ((ImInfoMessageTextView)localView.findViewById(R.id.customer_legal_message_text));
    this.mLegalMessageText.setParentUiNode(this);
    this.mLegalMessageText.setUrlClickListener(this);
    if ((((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalCountrySelector == null) && (((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalAddressForm == null)) {
      if (((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalMessages != null) {
        break label135;
      }
    }
    label135:
    for (LegalMessageOuterClass.LegalMessage localLegalMessage = null;; localLegalMessage = ((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalMessages.defaultMessage)
    {
      setLegalMessage(localLegalMessage);
      if (((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalCountrySelector == null) {
        break label241;
      }
      if (paramBundle != null) {
        break label585;
      }
      if (((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalCountrySelector.allowedCountryCode.length > 0) {
        break;
      }
      throw new IllegalArgumentException("LegalCountrySelector's allowed country codes cannot be empty");
    }
    label585:
    for (this.mRegionCodes = AddressUtils.scrubAndSortRegionCodes(AddressUtils.stringArrayToRegionCodeArray(((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalCountrySelector.allowedCountryCode));; this.mRegionCodes = paramBundle.getIntArray("regionCodes"))
    {
      this.mRegionCodeView = ((RegionCodeView)localView.findViewById(R.id.legal_country_selector));
      this.mRegionCodeView.setVisibility(0);
      this.mRegionCodeView.setRegionCodeSelectedListener(this);
      this.mRegionCodeView.setRegionCodes(this.mRegionCodes);
      this.mRegionCodeView.setSelectedRegionCode$2563266(RegionCode.toRegionCode(((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalCountrySelector.initialCountryCode));
      label241:
      if (((CustomerFormOuterClass.CustomerForm)this.mFormProto).taxInfoForm.length > 0)
      {
        localView.findViewById(R.id.tax_info_fragment_holder).setVisibility(0);
        TaxInfoEntryFragment localTaxInfoEntryFragment = (TaxInfoEntryFragment)getChildFragmentManager().findFragmentById(R.id.tax_info_fragment_holder);
        if (localTaxInfoEntryFragment == null)
        {
          localTaxInfoEntryFragment = TaxInfoEntryFragment.newInstance(((CustomerFormOuterClass.CustomerForm)this.mFormProto).taxInfoForm, ((CustomerFormOuterClass.CustomerForm)this.mFormProto).initialTaxInfoForm, this.mThemeResourceId);
          getChildFragmentManager().beginTransaction().add(R.id.tax_info_fragment_holder, localTaxInfoEntryFragment).commit();
        }
        this.mFieldData.add(new FormFragment.FieldData(0, localTaxInfoEntryFragment));
      }
      if (((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalAddressForm != null)
      {
        localView.findViewById(R.id.legal_address_entry_fragment_holder).setVisibility(0);
        AddressEntryFragment localAddressEntryFragment = (AddressEntryFragment)getChildFragmentManager().findFragmentById(R.id.legal_address_entry_fragment_holder);
        if (localAddressEntryFragment == null)
        {
          localAddressEntryFragment = AddressEntryFragment.newInstance(((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalAddressForm, this.mThemeResourceId);
          getChildFragmentManager().beginTransaction().add(R.id.legal_address_entry_fragment_holder, localAddressEntryFragment).commit();
        }
        localAddressEntryFragment.mOnRegionCodeSelectedListener = this;
        this.mFieldData.add(new FormFragment.FieldData(0, localAddressEntryFragment));
      }
      if (((CustomerFormOuterClass.CustomerForm)this.mFormProto).instrumentForm != null)
      {
        localView.findViewById(R.id.instrument_form_fragment_holder).setVisibility(0);
        FormFragment localFormFragment = (FormFragment)getChildFragmentManager().findFragmentById(R.id.instrument_form_fragment_holder);
        if (localFormFragment == null)
        {
          localFormFragment = ImUtils.createFragmentForInstrumentForm(((CustomerFormOuterClass.CustomerForm)this.mFormProto).instrumentForm, this.mThemeResourceId);
          getChildFragmentManager().beginTransaction().add(R.id.instrument_form_fragment_holder, localFormFragment).commit();
          if ((localFormFragment instanceof AddCreditCardFragment)) {
            ((AddCreditCardFragment)localFormFragment).mOnStateChangedListener = this;
          }
        }
        this.mFieldData.add(new FormFragment.FieldData(0, localFormFragment));
      }
      doEnableUi();
      return localView;
    }
  }
  
  public final void onRegionCodeSelected(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.mSelectedRegionCode != paramInt1)
    {
      this.mSelectedRegionCode = paramInt1;
      String str = RegionCode.toCountryCode(paramInt1);
      if ((((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalCountrySelector != null) && (paramInt2 == this.mRegionCodeView.getId()) && (!((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalCountrySelector.initialCountryCode.equals(str)))
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("FormEventListener.EXTRA_FORM_ID", ((CustomerFormOuterClass.CustomerForm)this.mFormProto).id);
        localBundle.putInt("FormEventListener.EXTRA_FIELD_ID", 1);
        notifyFormEvent(3, localBundle);
      }
      setLegalMessage(PaymentUtils.findLegalMessageByCountry(((CustomerFormOuterClass.CustomerForm)this.mFormProto).legalMessages, str));
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putIntArray("regionCodes", this.mRegionCodes);
    paramBundle.putInt("selectedRegionCode", this.mSelectedRegionCode);
  }
  
  public final void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
    notifyFormEvent(6, Bundle.EMPTY);
  }
  
  public final boolean shouldShowButtonBarExpandButton()
  {
    return (!this.mLegalMessageText.mInlineExpandLabel) && (!this.mLegalMessageText.mExpanded);
  }
  
  @TargetApi(14)
  public final void showViewsBelow$71dafd8f(boolean paramBoolean1, boolean paramBoolean2, int paramInt, long paramLong)
  {
    if (paramBoolean2)
    {
      if (Build.VERSION.SDK_INT >= 14) {
        this.mLegalMessageText.animate().setStartDelay(paramLong);
      }
      if (paramBoolean1) {
        WalletUiUtils.animateViewAppearing(this.mLegalMessageText, paramInt, 0);
      }
      for (;;)
      {
        if (Build.VERSION.SDK_INT >= 14) {
          this.mLegalMessageText.animate().setStartDelay(0L);
        }
        return;
        WalletUiUtils.animateViewDisappearingToGone(this.mLegalMessageText, paramInt, 0);
      }
    }
    ImInfoMessageTextView localImInfoMessageTextView = this.mLegalMessageText;
    int i = 0;
    if (paramBoolean1) {}
    for (;;)
    {
      localImInfoMessageTextView.setVisibility(i);
      return;
      i = 8;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.customer.CustomerFragment
 * JD-Core Version:    0.7.0.1
 */