package com.google.android.wallet.ui.common.validator;

import android.text.TextUtils;
import android.widget.TextView;
import com.google.android.wallet.ui.common.FormEditText;

public class InputLengthValidator
  extends AbstractValidator
{
  private final int mMinLength;
  
  public InputLengthValidator(int paramInt)
  {
    this.mMinLength = paramInt;
  }
  
  public boolean isRequired()
  {
    return true;
  }
  
  public final boolean isValid(TextView paramTextView)
  {
    int i;
    if ((paramTextView instanceof FormEditText))
    {
      i = ((FormEditText)paramTextView).getValueLength();
      if ((isRequired()) || (i != 0)) {
        break label39;
      }
    }
    label39:
    while (i >= this.mMinLength)
    {
      return true;
      i = TextUtils.getTrimmedLength(paramTextView.getText());
      break;
    }
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.validator.InputLengthValidator
 * JD-Core Version:    0.7.0.1
 */