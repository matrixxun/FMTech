package com.google.android.wallet.ui.common;

public abstract interface FieldValidatable
{
  public abstract CharSequence getError();
  
  public abstract boolean isValid();
  
  public abstract void setError(CharSequence paramCharSequence);
  
  public abstract boolean validate();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.FieldValidatable
 * JD-Core Version:    0.7.0.1
 */