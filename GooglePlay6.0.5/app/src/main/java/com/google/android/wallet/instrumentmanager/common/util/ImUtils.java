package com.google.android.wallet.instrumentmanager.common.util;

import android.app.Activity;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.google.android.wallet.common.pub.SecurePaymentsPayload.SecurePaymentsData;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.instrumentmanager.ui.card.CardFragment;
import com.google.android.wallet.instrumentmanager.ui.card.CardFragment.CardFormFieldData;
import com.google.android.wallet.instrumentmanager.ui.card.CardNumberEditText;
import com.google.android.wallet.instrumentmanager.ui.creditcard.AddCreditCardFragment;
import com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardNumberEditText;
import com.google.android.wallet.instrumentmanager.ui.dcb.VerifyAssociationFragment;
import com.google.android.wallet.instrumentmanager.ui.redirect.DummyFormFragment;
import com.google.android.wallet.instrumentmanager.ui.redirect.RedirectFragment;
import com.google.android.wallet.instrumentmanager.ui.simple.ImSimpleFragment;
import com.google.android.wallet.instrumentmanager.ui.usernamepassword.ImUsernamePasswordFragment;
import com.google.android.wallet.ui.address.AddressEntryFragment;
import com.google.android.wallet.ui.common.ExpDateEditText;
import com.google.android.wallet.ui.common.Form;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.OtpFieldFragment;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.simple.SimpleFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.InstrumentFormOuterClass.InstrumentFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardFieldValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardSubform;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardSubformValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.PanCategory;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CardType;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.Dcb.DcbVerifyAssociationForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.Dcb.DcbVerifyAssociationFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.redirect.RedirectFormOuterClass.RedirectForm;
import com.google.moneta.orchestration.ui.common.SecureDataMappingOuterClass.SecureDataMapping;
import java.util.ArrayList;

public final class ImUtils
{
  public static void applySecureData(InstrumentFormOuterClass.InstrumentForm paramInstrumentForm, SecurePaymentsPayload.SecurePaymentsData[] paramArrayOfSecurePaymentsData, SecureDataMappingOuterClass.SecureDataMapping[] paramArrayOfSecureDataMapping)
  {
    if (paramArrayOfSecurePaymentsData.length != paramArrayOfSecureDataMapping.length)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(paramArrayOfSecurePaymentsData.length);
      arrayOfObject2[1] = Integer.valueOf(paramArrayOfSecureDataMapping.length);
      throw new IllegalArgumentException(String.format("Lengths of SecurePaymentsData (%d) and SecureDataMapping (%d) do not match", arrayOfObject2));
    }
    ArrayList localArrayList = new ArrayList(paramArrayOfSecurePaymentsData.length);
    int i = paramArrayOfSecurePaymentsData.length;
    int j = 0;
    if (j < i)
    {
      SecurePaymentsPayload.SecurePaymentsData localSecurePaymentsData = paramArrayOfSecurePaymentsData[j];
      int n = paramArrayOfSecureDataMapping.length;
      for (int i1 = 0;; i1++) {
        if (i1 < n)
        {
          SecureDataMappingOuterClass.SecureDataMapping localSecureDataMapping = paramArrayOfSecureDataMapping[i1];
          if (localSecurePaymentsData.key == localSecureDataMapping.secureDataKey) {
            localArrayList.add(new MappedSecureData(localSecureDataMapping.secureDataField, localSecurePaymentsData.value));
          }
        }
        else
        {
          j++;
          break;
        }
      }
    }
    if (localArrayList.size() != paramArrayOfSecurePaymentsData.length) {
      throw new IllegalArgumentException("SecurePaymentsData and SecureDataMapping are mismatched.");
    }
    int k = 0;
    int m = localArrayList.size();
    if (k < m)
    {
      MappedSecureData localMappedSecureData = (MappedSecureData)localArrayList.get(k);
      if ((paramInstrumentForm.redirect != null) && (localMappedSecureData.formFieldReference.formId.equals(paramInstrumentForm.redirect.formHeader.id)))
      {
        switch (localMappedSecureData.formFieldReference.fieldId)
        {
        default: 
          throw new IllegalArgumentException("Unsupported SecureData fieldId: " + localMappedSecureData.formFieldReference.fieldId);
        case 1: 
          paramInstrumentForm.redirect.initialUrl = localMappedSecureData.secureData;
        }
        for (;;)
        {
          k++;
          break;
          paramInstrumentForm.redirect.initialPostBody = localMappedSecureData.secureData;
        }
      }
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = localMappedSecureData.formFieldReference.formId;
      arrayOfObject1[1] = Integer.valueOf(localMappedSecureData.formFieldReference.fieldId);
      throw new IllegalArgumentException(String.format("Unsupported SecureData FormFieldReference formId=%s fieldId=%d", arrayOfObject1));
    }
  }
  
  public static FormFragment createFragmentForInstrumentForm(InstrumentFormOuterClass.InstrumentForm paramInstrumentForm, int paramInt)
  {
    if (paramInstrumentForm.creditCard != null) {
      return AddCreditCardFragment.newInstance(paramInstrumentForm.creditCard, paramInt);
    }
    if (paramInstrumentForm.card != null) {
      return CardFragment.newInstance(paramInstrumentForm.card, paramInt);
    }
    if (paramInstrumentForm.usernamePassword != null) {
      return ImUsernamePasswordFragment.newInstance(paramInstrumentForm.usernamePassword, paramInt);
    }
    if (paramInstrumentForm.dcbVerifyAssociation != null) {
      return VerifyAssociationFragment.newInstance(paramInstrumentForm.dcbVerifyAssociation, paramInt);
    }
    if (paramInstrumentForm.simpleForm != null) {
      return ImSimpleFragment.newInstance(paramInstrumentForm.simpleForm, paramInt);
    }
    if (paramInstrumentForm.redirect != null)
    {
      if (paramInstrumentForm.redirect.displayType == 1) {
        return RedirectFragment.newInstance(paramInstrumentForm.redirect, paramInt);
      }
      throw new IllegalArgumentException("Unsupported redirect form display type: " + paramInstrumentForm.redirect.displayType);
    }
    throw new IllegalArgumentException("Instrument form did not contain a recognized subform.");
  }
  
  public static InstrumentFormOuterClass.InstrumentFormValue getInstrumentFormValueFromFragment(Form paramForm, Bundle paramBundle)
  {
    InstrumentFormOuterClass.InstrumentFormValue localInstrumentFormValue = new InstrumentFormOuterClass.InstrumentFormValue();
    if ((paramForm instanceof AddCreditCardFragment))
    {
      AddCreditCardFragment localAddCreditCardFragment = (AddCreditCardFragment)paramForm;
      String str1 = localAddCreditCardFragment.mCreditCardNumberText.getCardNumber();
      String str2 = PaymentUtils.removeNonNumericDigits(localAddCreditCardFragment.mCvcText.getText().toString());
      String str3 = str1.substring(Math.max(0, -4 + str1.length()));
      CreditCard.CardType localCardType = localAddCreditCardFragment.mCreditCardNumberText.getCardType();
      CreditCard.CreditCardFormValue localCreditCardFormValue = new CreditCard.CreditCardFormValue();
      localCreditCardFormValue.id = ((CreditCard.CreditCardForm)localAddCreditCardFragment.mFormProto).id;
      localCreditCardFormValue.expirationMonth = localAddCreditCardFragment.mExpDateText.getExpMonth();
      localCreditCardFormValue.expirationYear = localAddCreditCardFragment.mExpDateText.getExpYear();
      if (localCardType != null) {
        localCreditCardFormValue.typeToken = localCardType.typeToken;
      }
      localCreditCardFormValue.lastFourDigits = str3;
      localCreditCardFormValue.cardNumber = str1;
      localCreditCardFormValue.cvc = str2;
      localCreditCardFormValue.billingAddress = localAddCreditCardFragment.mAddressEntryFragment.getAddressFormValue$64352f99();
      if (localAddCreditCardFragment.mLegalMessage != null) {
        localCreditCardFormValue.legalDocData = localAddCreditCardFragment.mLegalMessage.opaqueData;
      }
      localInstrumentFormValue.creditCard = localCreditCardFormValue;
      return localInstrumentFormValue;
    }
    if ((paramForm instanceof CardFragment))
    {
      CardFragment localCardFragment = (CardFragment)paramForm;
      CardFormOuterClass.CardFormValue localCardFormValue = new CardFormOuterClass.CardFormValue();
      localCardFormValue.id = ((CardFormOuterClass.CardForm)localCardFragment.mFormProto).formHeader.id;
      localCardFormValue.token = ((CardFormOuterClass.CardForm)localCardFragment.mFormProto).formHeader.dataToken;
      localCardFormValue.accountNumber = localCardFragment.mCardNumberText.getCardNumber();
      CardFormOuterClass.PanCategory localPanCategory = localCardFragment.mCardNumberText.getPanCategory();
      if (localPanCategory != null)
      {
        localCardFormValue.panCategoryToken = localPanCategory.panCategoryToken;
        int i = localPanCategory.cardSubformIndex.length;
        localCardFormValue.cardSubformValue = new CardFormOuterClass.CardSubformValue[i];
        for (int j = 0; j < i; j++)
        {
          int k = localPanCategory.cardSubformIndex[j];
          CardFormOuterClass.CardSubform localCardSubform = ((CardFormOuterClass.CardForm)localCardFragment.mFormProto).cardSubform[k];
          CardFormOuterClass.CardSubformValue localCardSubformValue = new CardFormOuterClass.CardSubformValue();
          localCardSubformValue.id = localCardSubform.formHeader.id;
          localCardSubformValue.token = localCardSubform.formHeader.dataToken;
          ArrayList localArrayList = (ArrayList)localCardFragment.mCardFormFieldDataList.get(k);
          int m = localCardSubform.cardField.length;
          localCardSubformValue.cardFieldValue = new CardFormOuterClass.CardFieldValue[m];
          int n = 0;
          if (n < m)
          {
            CardFragment.CardFormFieldData localCardFormFieldData = (CardFragment.CardFormFieldData)localArrayList.get(n);
            CardFormOuterClass.CardFieldValue localCardFieldValue = new CardFormOuterClass.CardFieldValue();
            switch (localCardFormFieldData.mFieldType)
            {
            case 3: 
            default: 
              throw new IllegalStateException("Unknown field type " + localCardFormFieldData.mFieldType + " in SimpleForm.");
            case 1: 
              localCardFieldValue.uiFieldValue = WalletUiUtils.createUiFieldValue((View)localCardFragment.mUiFields.get(localCardFormFieldData.mIndexInFieldType), localCardSubform.cardField[n].uiField);
            }
            for (;;)
            {
              localCardSubformValue.cardFieldValue[n] = localCardFieldValue;
              n++;
              break;
              localCardFieldValue.addressFormValue = ((AddressEntryFragment)localCardFragment.mAddressFragments.get(localCardFormFieldData.mIndexInFieldType)).getAddressFormValue$64352f99();
              continue;
              localCardFieldValue.otpFieldValue = ((OtpFieldFragment)localCardFragment.mOtpFields.get(localCardFormFieldData.mIndexInFieldType)).getFieldValue(paramBundle);
            }
          }
          if (localCardSubform.legalMessage != null) {
            localCardSubformValue.legalDocData = localCardSubform.legalMessage.opaqueData;
          }
          localCardFormValue.cardSubformValue[j] = localCardSubformValue;
        }
      }
      localInstrumentFormValue.card = localCardFormValue;
      return localInstrumentFormValue;
    }
    if ((paramForm instanceof ImUsernamePasswordFragment))
    {
      localInstrumentFormValue.usernamePassword = ((ImUsernamePasswordFragment)paramForm).getUsernamePasswordFormValue$695fcf2();
      return localInstrumentFormValue;
    }
    if ((paramForm instanceof VerifyAssociationFragment))
    {
      VerifyAssociationFragment localVerifyAssociationFragment = (VerifyAssociationFragment)paramForm;
      Dcb.DcbVerifyAssociationFormValue localDcbVerifyAssociationFormValue = new Dcb.DcbVerifyAssociationFormValue();
      if (((Dcb.DcbVerifyAssociationForm)localVerifyAssociationFragment.mFormProto).requiredMessage != null) {
        localDcbVerifyAssociationFormValue.legalDocData = ((Dcb.DcbVerifyAssociationForm)localVerifyAssociationFragment.mFormProto).requiredMessage.opaqueData;
      }
      localInstrumentFormValue.dcbVerifyAssociation = localDcbVerifyAssociationFormValue;
      return localInstrumentFormValue;
    }
    if ((paramForm instanceof SimpleFragment))
    {
      localInstrumentFormValue.simpleForm = ((SimpleFragment)paramForm).getSimpleFormValue(paramBundle);
      return localInstrumentFormValue;
    }
    if ((paramForm instanceof RedirectFragment))
    {
      localInstrumentFormValue.redirect = ((RedirectFragment)paramForm).getRedirectFormValue$5a05ded8();
      return localInstrumentFormValue;
    }
    if ((paramForm instanceof DummyFormFragment))
    {
      localInstrumentFormValue.redirect = ((DummyFormFragment)paramForm).mRedirectFormValue;
      return localInstrumentFormValue;
    }
    throw new IllegalArgumentException("The provided form " + paramForm + " is not a recognized instrument form.");
  }
  
  public static boolean isRedirectForm(InstrumentFormOuterClass.InstrumentForm paramInstrumentForm)
  {
    return (paramInstrumentForm != null) && (paramInstrumentForm.redirect != null);
  }
  
  public static void setScreenshotsEnabled(Activity paramActivity, boolean paramBoolean)
  {
    Window localWindow;
    if ((Build.VERSION.SDK_INT >= 11) && ("user".equals(Build.TYPE)))
    {
      localWindow = paramActivity.getWindow();
      if (localWindow != null)
      {
        if (!paramBoolean) {
          break label41;
        }
        localWindow.clearFlags(8192);
      }
    }
    return;
    label41:
    localWindow.addFlags(8192);
  }
  
  protected static final class MappedSecureData
  {
    public final FormFieldReferenceOuterClass.FormFieldReference formFieldReference;
    public final String secureData;
    
    public MappedSecureData(FormFieldReferenceOuterClass.FormFieldReference paramFormFieldReference, String paramString)
    {
      this.formFieldReference = paramFormFieldReference;
      this.secureData = paramString;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.common.util.ImUtils
 * JD-Core Version:    0.7.0.1
 */