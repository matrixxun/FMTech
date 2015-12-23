package com.google.android.wallet.ui.common.validator;

import android.widget.TextView;

public abstract class AbstractValidator
{
  public CharSequence mErrorMessage;
  
  public AbstractValidator()
  {
    this(null);
  }
  
  public AbstractValidator(CharSequence paramCharSequence)
  {
    this.mErrorMessage = paramCharSequence;
  }
  
  public CharSequence getErrorMessage()
  {
    return this.mErrorMessage;
  }
  
  public abstract boolean isValid(TextView paramTextView);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.validator.AbstractValidator
 * JD-Core Version:    0.7.0.1
 */