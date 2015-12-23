package com.google.android.wallet.ui.common;

import com.google.android.wallet.ui.common.validator.AbstractValidator;

public abstract interface ValidatableComponent
  extends FieldValidatable
{
  public abstract void addValidator(AbstractValidator paramAbstractValidator);
  
  public abstract void removeValidator(AbstractValidator paramAbstractValidator);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.ValidatableComponent
 * JD-Core Version:    0.7.0.1
 */