package com.google.android.wallet.ui.common.validator;

import android.text.TextUtils;
import android.widget.TextView;
import com.google.android.wallet.ui.common.FormEditText;

public final class RequiredValidator
  extends AbstractValidator
{
  public RequiredValidator(CharSequence paramCharSequence)
  {
    super(paramCharSequence);
  }
  
  public final boolean isValid(TextView paramTextView)
  {
    if ((paramTextView instanceof FormEditText)) {}
    for (int i = ((FormEditText)paramTextView).getValueLength(); i > 0; i = TextUtils.getTrimmedLength(paramTextView.getText())) {
      return true;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.validator.RequiredValidator
 * JD-Core Version:    0.7.0.1
 */