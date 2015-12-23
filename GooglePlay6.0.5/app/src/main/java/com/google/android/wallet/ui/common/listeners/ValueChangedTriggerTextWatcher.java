package com.google.android.wallet.ui.common.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ComponentValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference.ValueChangedTrigger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValueChangedTriggerTextWatcher
  implements TextWatcher
{
  private final EditText mEditText;
  private final TriggerListener mListener;
  private final Pattern mPattern;
  private final DependencyGraphOuterClass.TriggerValueReference mTrigger;
  
  public ValueChangedTriggerTextWatcher(EditText paramEditText, DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference, TriggerListener paramTriggerListener)
  {
    this.mEditText = paramEditText;
    this.mTrigger = paramTriggerValueReference;
    this.mListener = paramTriggerListener;
    if (paramTriggerValueReference.valueChangedTrigger.newValue != null)
    {
      this.mPattern = Pattern.compile(paramTriggerValueReference.valueChangedTrigger.newValue.valueStringRegex);
      return;
    }
    this.mPattern = null;
  }
  
  public final void afterTextChanged(Editable paramEditable)
  {
    if (this.mPattern == null) {
      this.mListener.onTriggerOccurred(this.mTrigger);
    }
    Object localObject;
    do
    {
      return;
      localObject = paramEditable;
      if ((this.mEditText instanceof FormEditText)) {
        localObject = ((FormEditText)this.mEditText).getValue();
      }
    } while (!this.mPattern.matcher((CharSequence)localObject).matches());
    this.mListener.onTriggerOccurred(this.mTrigger);
  }
  
  public final void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public final void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.listeners.ValueChangedTriggerTextWatcher
 * JD-Core Version:    0.7.0.1
 */