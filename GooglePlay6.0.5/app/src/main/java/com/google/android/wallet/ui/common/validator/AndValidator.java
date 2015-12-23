package com.google.android.wallet.ui.common.validator;

import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;

public final class AndValidator
  extends ComposedValidator
{
  public AndValidator(AbstractValidator... paramVarArgs)
  {
    super(paramVarArgs);
  }
  
  public final boolean isValid(TextView paramTextView)
  {
    Iterator localIterator = this.mValidators.iterator();
    while (localIterator.hasNext())
    {
      AbstractValidator localAbstractValidator = (AbstractValidator)localIterator.next();
      if (!localAbstractValidator.isValid(paramTextView))
      {
        this.mErrorMessage = localAbstractValidator.getErrorMessage();
        return false;
      }
    }
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.validator.AndValidator
 * JD-Core Version:    0.7.0.1
 */