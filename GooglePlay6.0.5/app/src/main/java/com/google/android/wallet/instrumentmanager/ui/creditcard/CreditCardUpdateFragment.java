package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ExpDateEditText;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.ImageWithCaptionView;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.commerce.payments.orchestration.proto.common.DateOuterClass.Date;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardUpdateForm;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.DateField;
import java.util.ArrayList;
import java.util.List;

public final class CreditCardUpdateFragment
  extends FormFragment<CreditCard.CreditCardUpdateForm>
  implements View.OnClickListener, UiNode
{
  TextView mCreditCardLabel;
  View mCvcHintImage;
  public FormEditText mCvcText;
  public ExpDateEditText mExpDateText;
  final ArrayList<FormFragment.FieldData> mFieldData = new ArrayList();
  private final WalletUiElement mUiElement = new WalletUiElement(1651);
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    if (!paramFormFieldMessage.formFieldReference.formId.equals(((CreditCard.CreditCardUpdateForm)this.mFormProto).id)) {
      return false;
    }
    switch (paramFormFieldMessage.formFieldReference.fieldId)
    {
    default: 
      throw new IllegalArgumentException("Unknown FormFieldMessage fieldId: " + paramFormFieldMessage.formFieldReference.fieldId);
    case 2: 
      this.mExpDateText.setError(paramFormFieldMessage.message);
    }
    for (;;)
    {
      return true;
      this.mExpDateText.setError(paramFormFieldMessage.message);
      continue;
      this.mCvcText.setError(paramFormFieldMessage.message);
    }
  }
  
  protected final void doEnableUi()
  {
    if (this.mExpDateText != null)
    {
      boolean bool = this.mUiEnabled;
      this.mExpDateText.setEnabled(bool);
      this.mCvcText.setEnabled(bool);
      this.mCvcHintImage.setEnabled(bool);
    }
  }
  
  public final List<UiNode> getChildren()
  {
    return null;
  }
  
  public final List<FormFragment.FieldData> getFieldsForValidationInOrder()
  {
    return this.mFieldData;
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean isReadyToSubmit()
  {
    return true;
  }
  
  public final void onClick(View paramView)
  {
    if ((paramView != this.mCvcHintImage) || (this.mFragmentManager.findFragmentByTag("CvcInfoDialog") != null)) {
      return;
    }
    CvcInfoDialogFragment.newInstance(this.mThemeResourceId).show(this.mFragmentManager, "CvcInfoDialog");
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_credit_card_update, null, false);
    this.mCreditCardLabel = ((TextView)localView.findViewById(R.id.credit_card_label));
    this.mCreditCardLabel.setText(((CreditCard.CreditCardUpdateForm)this.mFormProto).cardLabel);
    ((ImageWithCaptionView)localView.findViewById(R.id.card_logo)).setImageWithCaption(((CreditCard.CreditCardUpdateForm)this.mFormProto).icon, PaymentUtils.getImageLoader(getActivity().getApplicationContext()), ((Boolean)G.images.useWebPForFife.get()).booleanValue());
    this.mExpDateText = ((ExpDateEditText)localView.findViewById(R.id.exp_date));
    this.mCvcText = ((FormEditText)localView.findViewById(R.id.cvc));
    this.mFieldData.add(new FormFragment.FieldData(0, this.mExpDateText, null));
    FormEditText localFormEditText = this.mCvcText;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(((CreditCard.CreditCardUpdateForm)this.mFormProto).cvcLength);
    localFormEditText.setFilters(arrayOfInputFilter);
    this.mCvcHintImage = localView.findViewById(R.id.cvc_hint);
    this.mCvcHintImage.setOnClickListener(this);
    CvcValidator localCvcValidator = new CvcValidator(this.mCvcText, ((CreditCard.CreditCardUpdateForm)this.mFormProto).cvcLength);
    this.mCvcText.addValidator(localCvcValidator);
    this.mFieldData.add(new FormFragment.FieldData(0, this.mCvcText, null));
    UiFieldOuterClass.UiField localUiField = new UiFieldOuterClass.UiField();
    localUiField.isOptional = false;
    localUiField.label = getString(R.string.wallet_uic_exp_date);
    localUiField.dateField = new UiFieldOuterClass.UiField.DateField();
    localUiField.dateField.type = 2;
    localUiField.dateField.minDate = new DateOuterClass.Date();
    localUiField.dateField.minDate.month = ((CreditCard.CreditCardUpdateForm)this.mFormProto).minMonth;
    localUiField.dateField.minDate.year = ((CreditCard.CreditCardUpdateForm)this.mFormProto).minYear;
    localUiField.dateField.maxDate = new DateOuterClass.Date();
    localUiField.dateField.maxDate.month = ((CreditCard.CreditCardUpdateForm)this.mFormProto).maxMonth;
    localUiField.dateField.maxDate.year = ((CreditCard.CreditCardUpdateForm)this.mFormProto).maxYear;
    WalletUiUtils.applyUiFieldSpecificationToFormEditText(localUiField, this.mExpDateText);
    this.mCvcText.enableAutoAdvance(localCvcValidator, this.mCvcText, true);
    doEnableUi();
    return localView;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardUpdateFragment
 * JD-Core Version:    0.7.0.1
 */