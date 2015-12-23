package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.dependencygraph.ResultingActionComponent;
import com.google.android.wallet.dependencygraph.TriggerComponent;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.ui.address.PromptArrayAdapter;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ResultingActionReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ComponentValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ValueChangedTrigger;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.SelectField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.SelectField.Option;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SelectFieldView
  extends LinearLayout
  implements AdapterView.OnItemSelectedListener, ResultingActionComponent, TriggerComponent, FieldValidatable, InlineSelectView.OnItemSelectedListener
{
  private boolean mHasPrompt;
  InfoMessageTextView mInfoMessageDescription;
  InlineSelectView mInlineSelectView;
  private int mPreviouslySelectedOptionIndex;
  TextView mReadOnlyText;
  ImageWithCaptionView mReadOnlyTextIcon;
  FormSpinner mSpinner;
  private TriggerListener mTriggerListener;
  private UiFieldOuterClass.UiField mUiField;
  private LinkedList<DependencyGraphOuterClass.TriggerValueReference> mValueChangedTriggers = new LinkedList();
  
  public SelectFieldView(Context paramContext)
  {
    super(paramContext);
  }
  
  public SelectFieldView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  @TargetApi(11)
  public SelectFieldView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  @TargetApi(21)
  public SelectFieldView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private int adjustPositionForPrompt(int paramInt)
  {
    if (this.mHasPrompt) {
      paramInt--;
    }
    return paramInt;
  }
  
  private int getInitialSelection()
  {
    UiFieldOuterClass.UiField.SelectField localSelectField = this.mUiField.selectField;
    boolean bool = TextUtils.isEmpty(localSelectField.initialValue);
    int i = 0;
    int j;
    int k;
    if (!bool)
    {
      j = 0;
      k = localSelectField.option.length;
    }
    for (;;)
    {
      i = 0;
      if (j < k)
      {
        if (localSelectField.option[j].value.equals(localSelectField.initialValue)) {
          i = j;
        }
      }
      else {
        return i;
      }
      j++;
    }
  }
  
  private void onOptionSelected(int paramInt, boolean paramBoolean)
  {
    if (paramInt >= 0)
    {
      UiFieldOuterClass.UiField.SelectField.Option localOption = this.mUiField.selectField.option[paramInt];
      this.mInfoMessageDescription.setInfoMessage(localOption.extendedDescriptionInfo);
      int i;
      Iterator localIterator;
      if ((paramInt == this.mPreviouslySelectedOptionIndex) && (this.mSpinner.getVisibility() == 0))
      {
        i = 1;
        if ((!paramBoolean) && (i == 0)) {
          localIterator = this.mValueChangedTriggers.iterator();
        }
      }
      else
      {
        label178:
        for (;;)
        {
          if (!localIterator.hasNext()) {
            break label188;
          }
          DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = (DependencyGraphOuterClass.TriggerValueReference)localIterator.next();
          if (localTriggerValueReference.valueChangedTrigger.newValue == null)
          {
            this.mTriggerListener.onTriggerOccurred(localTriggerValueReference);
            continue;
            i = 0;
            break;
          }
          int[] arrayOfInt = localTriggerValueReference.valueChangedTrigger.newValue.valueId;
          int j = arrayOfInt.length;
          for (int k = 0;; k++)
          {
            if (k >= j) {
              break label178;
            }
            if (arrayOfInt[k] == localOption.uiReference)
            {
              this.mTriggerListener.onTriggerOccurred(localTriggerValueReference);
              break;
            }
          }
        }
      }
    }
    else
    {
      this.mInfoMessageDescription.setInfoMessage(null);
    }
    label188:
    this.mPreviouslySelectedOptionIndex = paramInt;
  }
  
  public final void addTriggers(ArrayList<DependencyGraphOuterClass.TriggerValueReference> paramArrayList)
  {
    int i = 0;
    int j = paramArrayList.size();
    while (i < j)
    {
      DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = (DependencyGraphOuterClass.TriggerValueReference)paramArrayList.get(i);
      switch (localTriggerValueReference.triggerType)
      {
      default: 
        throw new IllegalArgumentException("Unsupported trigger type: " + localTriggerValueReference.triggerType);
      }
      this.mValueChangedTriggers.add(localTriggerValueReference);
      i++;
    }
  }
  
  public final void applyResultingAction(DependencyGraphOuterClass.ResultingActionReference paramResultingActionReference, DependencyGraphOuterClass.TriggerValueReference[] paramArrayOfTriggerValueReference)
  {
    switch (paramResultingActionReference.actionType)
    {
    default: 
      throw new IllegalArgumentException("Unknown ResultingActionReference action type " + paramResultingActionReference.actionType);
    }
    if (this.mSpinner.getVisibility() == 0) {
      this.mSpinner.setSelection(getInitialSelection());
    }
    if (this.mInlineSelectView.getVisibility() == 0) {
      this.mInlineSelectView.setSelection(-1);
    }
  }
  
  public final boolean checkTrigger(DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference)
  {
    switch (paramTriggerValueReference.triggerType)
    {
    default: 
      throw new IllegalArgumentException("Unsupported trigger type: " + paramTriggerValueReference.triggerType);
    }
    return false;
  }
  
  public View getDisplayedFieldView()
  {
    if (this.mSpinner.getVisibility() == 0) {
      return this.mSpinner;
    }
    return this.mReadOnlyText;
  }
  
  public CharSequence getError()
  {
    if (this.mSpinner.getVisibility() == 0) {
      return this.mSpinner.getError();
    }
    return null;
  }
  
  public String getValue()
  {
    String str = "";
    UiFieldOuterClass.UiField.SelectField.Option[] arrayOfOption;
    if (isValid())
    {
      arrayOfOption = this.mUiField.selectField.option;
      if (this.mSpinner.getVisibility() != 0) {
        break label50;
      }
      str = arrayOfOption[adjustPositionForPrompt(this.mSpinner.getSelectedItemPosition())].value;
    }
    label50:
    int i;
    do
    {
      return str;
      if (this.mInlineSelectView.getVisibility() != 0) {
        break;
      }
      i = this.mInlineSelectView.getSelectedItemIndex();
    } while (i < 0);
    return arrayOfOption[i].value;
    return arrayOfOption[getInitialSelection()].value;
  }
  
  public final boolean isValid()
  {
    if (this.mSpinner.getVisibility() == 0) {
      return this.mSpinner.isValid();
    }
    if (this.mInlineSelectView.getVisibility() == 0) {
      return this.mInlineSelectView.isValid();
    }
    return true;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mSpinner = ((FormSpinner)findViewById(R.id.ui_field_spinner));
    this.mInlineSelectView = ((InlineSelectView)findViewById(R.id.ui_field_inline_select));
    this.mInfoMessageDescription = ((InfoMessageTextView)findViewById(R.id.ui_field_info_message_description));
    this.mReadOnlyTextIcon = ((ImageWithCaptionView)findViewById(R.id.ui_field_read_only_text_icon));
    this.mReadOnlyText = ((TextView)findViewById(R.id.ui_field_read_only_text));
  }
  
  public final void onItemSelected(int paramInt)
  {
    onOptionSelected(paramInt, false);
  }
  
  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i = adjustPositionForPrompt(paramInt);
    if (paramView == null) {}
    for (boolean bool = true;; bool = false)
    {
      onOptionSelected(i, bool);
      return;
    }
  }
  
  public void onNothingSelected(AdapterView<?> paramAdapterView)
  {
    this.mInfoMessageDescription.setInfoMessage(null);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    this.mSpinner.setEnabled(paramBoolean);
    this.mInlineSelectView.setEnabled(paramBoolean);
  }
  
  public void setError(CharSequence paramCharSequence)
  {
    if (this.mSpinner.getVisibility() == 0) {
      this.mSpinner.setError(paramCharSequence);
    }
    do
    {
      return;
      if (this.mReadOnlyText.getVisibility() == 0) {
        throw new IllegalArgumentException("Should never set error on a read only TextView.");
      }
    } while (this.mInlineSelectView.getVisibility() != 0);
    throw new IllegalArgumentException("Errors on inline list views are not supported");
  }
  
  public final void setSelectUiField(UiFieldOuterClass.UiField paramUiField, ImageLoader paramImageLoader)
  {
    if (paramUiField.selectField.option.length == 0) {
      throw new IllegalArgumentException("SelectField must contain options.");
    }
    if ((paramUiField.isDisabled) && (paramUiField.selectField.option.length > 1) && (TextUtils.isEmpty(paramUiField.selectField.initialValue))) {
      throw new IllegalArgumentException("Disabled field does not have clear indication of which option to display.");
    }
    this.mUiField = paramUiField;
    UiFieldOuterClass.UiField.SelectField localSelectField = this.mUiField.selectField;
    if (((paramUiField.isDisabled) || (localSelectField.option.length == 1)) && (localSelectField.displayType == 1))
    {
      this.mSpinner.setVisibility(8);
      this.mInlineSelectView.setVisibility(8);
      this.mReadOnlyText.setVisibility(0);
      UiFieldOuterClass.UiField.SelectField.Option localOption3 = localSelectField.option[getInitialSelection()];
      if (TextUtils.isEmpty(localOption3.displayValue)) {
        throw new IllegalArgumentException("Each option must be provided a display value.");
      }
      this.mReadOnlyText.setText(localOption3.displayValue);
      this.mInfoMessageDescription.setInfoMessage(localOption3.extendedDescriptionInfo);
      if (localOption3.icon != null)
      {
        if (TextUtils.isEmpty(localOption3.icon.imageUri)) {
          throw new IllegalArgumentException("Empty option icon url for option: " + localOption3.displayValue);
        }
        this.mReadOnlyTextIcon.setVisibility(0);
        this.mReadOnlyTextIcon.setImageWithCaption(localOption3.icon, paramImageLoader, ((Boolean)G.images.useWebPForFife.get()).booleanValue());
      }
      return;
    }
    if (localSelectField.displayType == 1)
    {
      this.mSpinner.setVisibility(0);
      this.mInlineSelectView.setVisibility(8);
      this.mReadOnlyTextIcon.setVisibility(8);
      this.mReadOnlyText.setVisibility(8);
      boolean bool2;
      ArrayList localArrayList;
      UiFieldOuterClass.UiField.SelectField.Option[] arrayOfOption2;
      int k;
      if ((TextUtils.isEmpty(localSelectField.initialValue)) && ((!TextUtils.isEmpty(localSelectField.unselectedDisplayValue)) || (!TextUtils.isEmpty(paramUiField.label))))
      {
        bool2 = true;
        this.mHasPrompt = bool2;
        localArrayList = new ArrayList(localSelectField.option.length);
        arrayOfOption2 = localSelectField.option;
        k = arrayOfOption2.length;
      }
      for (int m = 0;; m++)
      {
        if (m >= k) {
          break label467;
        }
        UiFieldOuterClass.UiField.SelectField.Option localOption2 = arrayOfOption2[m];
        if (TextUtils.isEmpty(localOption2.displayValue))
        {
          throw new IllegalArgumentException("Option at index " + localArrayList.size() + " has no display value.");
          bool2 = false;
          break;
        }
        localArrayList.add(localOption2.displayValue);
      }
      label467:
      String str2;
      Object localObject;
      label513:
      FormSpinner localFormSpinner;
      if (this.mHasPrompt) {
        if (TextUtils.isEmpty(localSelectField.unselectedDisplayValue))
        {
          str2 = paramUiField.label;
          localObject = new PromptArrayAdapter(getContext(), R.layout.view_row_spinner, R.id.description, localArrayList, str2);
          ((ArrayAdapter)localObject).setDropDownViewResource(R.layout.view_spinner_dropdown);
          this.mSpinner.setAdapter((SpinnerAdapter)localObject);
          this.mSpinner.setOnItemSelectedListener(this);
          this.mPreviouslySelectedOptionIndex = getInitialSelection();
          this.mSpinner.setSelection(getInitialSelection());
          localFormSpinner = this.mSpinner;
          if (this.mUiField.isOptional) {
            break label631;
          }
        }
      }
      label631:
      for (boolean bool3 = true;; bool3 = false)
      {
        localFormSpinner.setRequired(bool3);
        this.mSpinner.setPrompt(this.mUiField.label);
        return;
        str2 = localSelectField.unselectedDisplayValue;
        break;
        localObject = new ArrayAdapter(getContext(), R.layout.view_row_spinner, R.id.description, localArrayList);
        break label513;
      }
    }
    if (localSelectField.displayType == 2)
    {
      this.mSpinner.setVisibility(8);
      this.mInlineSelectView.setVisibility(0);
      this.mReadOnlyTextIcon.setVisibility(8);
      this.mReadOnlyText.setVisibility(8);
      LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
      for (UiFieldOuterClass.UiField.SelectField.Option localOption1 : localSelectField.option)
      {
        View localView = localLayoutInflater.inflate(R.layout.view_row_inline_select_field, null);
        ((TextView)localView.findViewById(R.id.description)).setText(localOption1.displayValue);
        if (localOption1.icon != null)
        {
          ImageWithCaptionView localImageWithCaptionView = (ImageWithCaptionView)localView.findViewById(R.id.icon);
          localImageWithCaptionView.setVisibility(0);
          localImageWithCaptionView.setLazyLoad(true);
          localImageWithCaptionView.setImageWithCaption(localOption1.icon, paramImageLoader, ((Boolean)G.images.useWebPForFife.get()).booleanValue());
        }
        InlineSelectView localInlineSelectView2 = this.mInlineSelectView;
        String str1 = localOption1.displayValue;
        localInlineSelectView2.addView(localView);
        localView.setContentDescription(str1);
        ViewCompat.setAccessibilityDelegate(localView, localInlineSelectView2.mChildRowAccessibilityDelegate);
      }
      this.mInlineSelectView.setOnItemSelectedListener(this);
      InlineSelectView localInlineSelectView1 = this.mInlineSelectView;
      if (!this.mUiField.isOptional) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        localInlineSelectView1.setRequired(bool1);
        return;
      }
    }
    throw new IllegalArgumentException("Unknown SelectField display type: " + localSelectField.displayType);
  }
  
  public void setTriggerListener(TriggerListener paramTriggerListener)
  {
    this.mTriggerListener = paramTriggerListener;
  }
  
  public final boolean validate()
  {
    if (this.mSpinner.getVisibility() == 0) {
      return this.mSpinner.validate();
    }
    if (this.mInlineSelectView.getVisibility() == 0) {
      return this.mInlineSelectView.validate();
    }
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.SelectFieldView
 * JD-Core Version:    0.7.0.1
 */