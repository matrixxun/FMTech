package com.google.android.wallet.ui.common.validator;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ComposedValidator
  extends AbstractValidator
{
  protected final ArrayList<AbstractValidator> mValidators;
  
  public ComposedValidator(AbstractValidator... paramVarArgs)
  {
    super(null);
    this.mValidators = new ArrayList(Arrays.asList(paramVarArgs));
  }
  
  public final void add(AbstractValidator paramAbstractValidator)
  {
    if (paramAbstractValidator != null) {
      this.mValidators.add(paramAbstractValidator);
    }
  }
  
  public final boolean isEmpty()
  {
    return this.mValidators.isEmpty();
  }
  
  public final void remove(AbstractValidator paramAbstractValidator)
  {
    if (paramAbstractValidator != null) {
      this.mValidators.remove(paramAbstractValidator);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.validator.ComposedValidator
 * JD-Core Version:    0.7.0.1
 */