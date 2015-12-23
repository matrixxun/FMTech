package com.google.android.wallet.ui.common;

import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;

public abstract interface Form
  extends FormValidatable
{
  public abstract boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage);
  
  public abstract void enableUi(boolean paramBoolean);
  
  public abstract boolean focusOnFirstErroneousFormField();
  
  public abstract boolean handleErrorMessageDismissed(String paramString, int paramInt);
  
  public abstract boolean isReadyToSubmit();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.Form
 * JD-Core Version:    0.7.0.1
 */