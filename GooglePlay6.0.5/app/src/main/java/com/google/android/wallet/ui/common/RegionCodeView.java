package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.wallet.common.address.AddressUtils;
import com.google.android.wallet.common.address.RegionCode;
import com.google.android.wallet.dependencygraph.TriggerComponent;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.uicomponents.R.id;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.CountrySelectorValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ComponentValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ValueChangedTrigger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegionCodeView
  extends FrameLayout
  implements TriggerComponent, RegionCodeSelectorSpinner.OnRegionCodeSelectedListener
{
  private FormHeaderOuterClass.FormHeader mFormHeader;
  private RegionCodeSelectorSpinner.OnRegionCodeSelectedListener mOnRegionCodeSelectedListener;
  private boolean mReadOnlyMode;
  private boolean mRegionCodesSet;
  private int mSelectedRegionCode;
  public RegionCodeSelectorSpinner mSpinner;
  public TextView mTextView;
  private TriggerListener mTriggerListener;
  private ArrayList<DependencyGraphOuterClass.TriggerValueReference> mValueChangedTriggers = new ArrayList();
  
  public RegionCodeView(Context paramContext)
  {
    super(paramContext);
  }
  
  public RegionCodeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public RegionCodeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  @TargetApi(21)
  public RegionCodeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  public final void addTriggers(ArrayList<DependencyGraphOuterClass.TriggerValueReference> paramArrayList)
  {
    int i = 0;
    int j = paramArrayList.size();
    while (i < j)
    {
      DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = (DependencyGraphOuterClass.TriggerValueReference)paramArrayList.get(i);
      if (localTriggerValueReference.triggerType == 1)
      {
        this.mValueChangedTriggers.add(localTriggerValueReference);
        i++;
      }
      else
      {
        throw new IllegalArgumentException("Unsupported trigger type: " + localTriggerValueReference.triggerType);
      }
    }
  }
  
  public final boolean checkTrigger(DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference)
  {
    if (paramTriggerValueReference.triggerType == 1) {
      return false;
    }
    throw new IllegalArgumentException("Unsupported trigger type: " + paramTriggerValueReference.triggerType);
  }
  
  public AddressFormOuterClass.CountrySelectorValue getFieldValue()
  {
    AddressFormOuterClass.CountrySelectorValue localCountrySelectorValue = new AddressFormOuterClass.CountrySelectorValue();
    localCountrySelectorValue.id = this.mFormHeader.id;
    localCountrySelectorValue.dataToken = this.mFormHeader.dataToken;
    localCountrySelectorValue.countryCode = RegionCode.toCountryCode(getSelectedRegionCode());
    return localCountrySelectorValue;
  }
  
  public int getSelectedRegionCode()
  {
    if (this.mReadOnlyMode) {
      return this.mSelectedRegionCode;
    }
    return this.mSpinner.getSelectedRegionCode();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSpinner = ((RegionCodeSelectorSpinner)findViewById(R.id.region_code_spinner));
    this.mTextView = ((TextView)findViewById(R.id.region_code_text));
  }
  
  public final void onRegionCodeSelected(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramInt1 != this.mSelectedRegionCode)) {}
    for (boolean bool = true;; bool = false)
    {
      this.mSelectedRegionCode = paramInt1;
      if (this.mOnRegionCodeSelectedListener != null) {
        this.mOnRegionCodeSelectedListener.onRegionCodeSelected(paramInt1, getId(), bool);
      }
      if (bool) {
        break;
      }
      return;
    }
    int i = 0;
    int j = this.mValueChangedTriggers.size();
    label67:
    DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference;
    if (i < j)
    {
      localTriggerValueReference = (DependencyGraphOuterClass.TriggerValueReference)this.mValueChangedTriggers.get(i);
      if (localTriggerValueReference.valueChangedTrigger.newValue != null) {
        break label116;
      }
      this.mTriggerListener.onTriggerOccurred(localTriggerValueReference);
    }
    for (;;)
    {
      i++;
      break label67;
      break;
      label116:
      if (Pattern.compile(localTriggerValueReference.valueChangedTrigger.newValue.valueStringRegex).matcher(RegionCode.toCountryCode(this.mSelectedRegionCode)).matches()) {
        this.mTriggerListener.onTriggerOccurred(localTriggerValueReference);
      }
    }
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    this.mSpinner.setEnabled(paramBoolean);
    this.mTextView.setEnabled(paramBoolean);
  }
  
  public void setFormHeader(FormHeaderOuterClass.FormHeader paramFormHeader)
  {
    this.mFormHeader = paramFormHeader;
  }
  
  public void setRegionCodeSelectedListener(RegionCodeSelectorSpinner.OnRegionCodeSelectedListener paramOnRegionCodeSelectedListener)
  {
    this.mOnRegionCodeSelectedListener = paramOnRegionCodeSelectedListener;
  }
  
  public void setRegionCodes(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt.length == 1)
    {
      this.mSpinner.setRegionCodeSelectedListener(null);
      this.mSpinner.setVisibility(8);
      this.mTextView.setText(AddressUtils.getDisplayCountryForDefaultLocale(paramArrayOfInt[0]));
      onRegionCodeSelected(paramArrayOfInt[0], getId(), false);
      this.mTextView.setVisibility(0);
    }
    for (this.mReadOnlyMode = true;; this.mReadOnlyMode = false)
    {
      this.mRegionCodesSet = true;
      return;
      this.mSpinner.setRegionCodeSelectedListener(this);
      this.mSpinner.setRegionCodes(paramArrayOfInt);
      this.mSpinner.setVisibility(0);
      this.mTextView.setVisibility(8);
    }
  }
  
  public final void setSelectedRegionCode$2563266(int paramInt)
  {
    if (!this.mRegionCodesSet) {
      throw new IllegalStateException("setRegionCodes() must be called before setSelectedRegionCode()");
    }
    this.mSelectedRegionCode = paramInt;
    if (!this.mReadOnlyMode) {
      this.mSpinner.setSelectedRegionCode(paramInt);
    }
  }
  
  public void setTriggerListener(TriggerListener paramTriggerListener)
  {
    this.mTriggerListener = paramTriggerListener;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.RegionCodeView
 * JD-Core Version:    0.7.0.1
 */