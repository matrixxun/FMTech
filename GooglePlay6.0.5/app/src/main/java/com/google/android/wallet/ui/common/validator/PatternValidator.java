package com.google.android.wallet.ui.common.validator;

import android.text.TextUtils;
import android.widget.TextView;
import com.google.android.wallet.ui.common.FormEditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PatternValidator
  extends AbstractValidator
{
  private final Pattern mPattern;
  
  public PatternValidator(CharSequence paramCharSequence, Pattern paramPattern)
  {
    super(paramCharSequence);
    if (paramPattern == null) {
      throw new IllegalArgumentException("pattern must not be null");
    }
    this.mPattern = paramPattern;
  }
  
  public final boolean isValid(TextView paramTextView)
  {
    boolean bool2;
    if ((paramTextView instanceof FormEditText))
    {
      FormEditText localFormEditText = (FormEditText)paramTextView;
      if (localFormEditText.getValueLength() != 0)
      {
        boolean bool3 = this.mPattern.matcher(localFormEditText.getValue()).matches();
        bool2 = false;
        if (!bool3) {}
      }
      else
      {
        bool2 = true;
      }
    }
    boolean bool1;
    do
    {
      return bool2;
      if (TextUtils.isEmpty(paramTextView.getText())) {
        break;
      }
      bool1 = this.mPattern.matcher(paramTextView.getText()).matches();
      bool2 = false;
    } while (!bool1);
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.validator.PatternValidator
 * JD-Core Version:    0.7.0.1
 */