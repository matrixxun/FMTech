package com.google.android.wallet.ui.tax;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.FormSpinner;
import com.google.android.wallet.ui.common.UiFieldBuilder;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.tax.TaxInfoFormOuterClass.TaxInfoForm;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import java.util.ArrayList;
import java.util.List;

public final class TaxInfoEntryFragment
  extends FormFragment<TaxInfoFormOuterClass.TaxInfoForm>
  implements AdapterView.OnItemSelectedListener
{
  protected ArrayList<FormFragment.FieldData<View>> mFieldData = new ArrayList();
  FormSpinner mTaxInfoFormSpinner;
  protected LinearLayout mTaxInfoTextFields;
  private final WalletUiElement mUiElement = new WalletUiElement(1666);
  
  public static TaxInfoEntryFragment newInstance(TaxInfoFormOuterClass.TaxInfoForm[] paramArrayOfTaxInfoForm, int paramInt1, int paramInt2)
  {
    if ((paramArrayOfTaxInfoForm == null) || (paramArrayOfTaxInfoForm.length == 0)) {
      throw new IllegalArgumentException("At least one tax form should be provided");
    }
    if ((paramInt1 < 0) || (paramInt1 >= paramArrayOfTaxInfoForm.length)) {
      throw new IllegalArgumentException("Initial tax form index: " + paramInt1 + " is outside of tax forms valid range: [0," + paramArrayOfTaxInfoForm.length + ")");
    }
    TaxInfoEntryFragment localTaxInfoEntryFragment = new TaxInfoEntryFragment();
    Bundle localBundle = FormFragment.createArgs(paramInt2);
    localBundle.putParcelableArrayList("formProtos", ParcelableProto.forProtoArray(paramArrayOfTaxInfoForm));
    localBundle.putInt("initialFormProtoIndex", paramInt1);
    localTaxInfoEntryFragment.setArguments(localBundle);
    return localTaxInfoEntryFragment;
  }
  
  private void showCurrentTaxFormFields()
  {
    this.mTaxInfoTextFields.removeAllViews();
    this.mFieldData.clear();
    UiFieldOuterClass.UiField[] arrayOfUiField = ((TaxInfoFormOuterClass.TaxInfoForm)this.mFormProtos.get(this.mCurrentFormIndex)).taxInfoField;
    int i = 0;
    int j = arrayOfUiField.length;
    while (i < j)
    {
      UiFieldBuilder localUiFieldBuilder = new UiFieldBuilder(arrayOfUiField[i], this.mThemedInflater);
      localUiFieldBuilder.mViewId = (i + 1);
      View localView = localUiFieldBuilder.build();
      this.mFieldData.add(new FormFragment.FieldData(arrayOfUiField[i].uiReference, localView, WalletUiUtils.getInitialValue(arrayOfUiField[i])));
      this.mTaxInfoTextFields.addView(localView);
      i++;
    }
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    TaxInfoFormOuterClass.TaxInfoForm localTaxInfoForm = (TaxInfoFormOuterClass.TaxInfoForm)this.mFormProtos.get(this.mCurrentFormIndex);
    if (!paramFormFieldMessage.formFieldReference.formId.equals(localTaxInfoForm.id)) {
      return false;
    }
    if (paramFormFieldMessage.formFieldReference.fieldId != 1) {
      throw new IllegalArgumentException("TaxInfoForm does not support field with id: " + paramFormFieldMessage.formFieldReference.fieldId);
    }
    int i = paramFormFieldMessage.formFieldReference.repeatedFieldIndex;
    if ((i < 0) || (i >= localTaxInfoForm.taxInfoField.length)) {
      throw new IllegalArgumentException("FormFieldMessage repeatedFieldIndex: " + i + " is out of range [0," + localTaxInfoForm.taxInfoField.length + ")");
    }
    WalletUiUtils.setUiFieldError(getUiFieldViewAtIndex(i), paramFormFieldMessage.message);
    return true;
  }
  
  public final void doEnableUi()
  {
    if (this.mTaxInfoFormSpinner == null) {}
    for (;;)
    {
      return;
      boolean bool = this.mUiEnabled;
      this.mTaxInfoFormSpinner.setEnabled(bool);
      int i = 0;
      int j = this.mTaxInfoTextFields.getChildCount();
      while (i < j)
      {
        getUiFieldViewAtIndex(i).setEnabled(bool);
        i++;
      }
    }
  }
  
  public final List<UiNode> getChildren()
  {
    return null;
  }
  
  public final List<FormFragment.FieldData<View>> getFieldsForValidationInOrder()
  {
    return this.mFieldData;
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public View getUiFieldViewAtIndex(int paramInt)
  {
    return this.mTaxInfoTextFields.getChildAt(paramInt);
  }
  
  public final boolean isReadyToSubmit()
  {
    return true;
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.internalUicTaxInfoEntryRootLayout;
    TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt);
    int i = localTypedArray.getResourceId(0, R.layout.fragment_tax_info_entry);
    localTypedArray.recycle();
    View localView = paramLayoutInflater.inflate(i, null, false);
    this.mTaxInfoFormSpinner = ((FormSpinner)localView.findViewById(R.id.tax_info_forms_spinner));
    this.mTaxInfoTextFields = ((LinearLayout)localView.findViewById(R.id.tax_info_fields_container));
    showCurrentTaxFormFields();
    if (this.mFormProtos.size() > 1)
    {
      this.mTaxInfoFormSpinner.setVisibility(0);
      int j = this.mFormProtos.size();
      String[] arrayOfString = new String[j];
      for (int k = 0; k < j; k++) {
        arrayOfString[k] = ((TaxInfoFormOuterClass.TaxInfoForm)this.mFormProtos.get(k)).label;
      }
      ArrayAdapter localArrayAdapter = new ArrayAdapter(getActivity(), R.layout.view_row_spinner, R.id.description, arrayOfString);
      localArrayAdapter.setDropDownViewResource(R.layout.view_spinner_dropdown);
      this.mTaxInfoFormSpinner.setAdapter(localArrayAdapter);
      this.mTaxInfoFormSpinner.setSelection(this.mCurrentFormIndex);
      this.mTaxInfoFormSpinner.setOnItemSelectedListener(this);
    }
    doEnableUi();
    return localView;
  }
  
  public final void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mCurrentFormIndex != paramInt)
    {
      this.mCurrentFormIndex = paramInt;
      showCurrentTaxFormFields();
    }
  }
  
  public final void onNothingSelected(AdapterView<?> paramAdapterView) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.tax.TaxInfoEntryFragment
 * JD-Core Version:    0.7.0.1
 */