package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.TextView;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.ui.common.Completable;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.validator.AbstractValidator;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CardType;

public final class CvcValidator
  extends AbstractValidator
  implements Completable
{
  private final CreditCardNumberEditText mCardNumberText;
  private final int mCvcLength;
  private final FormEditText mCvcText;
  private final int mMaxFieldLength;
  
  public CvcValidator(FormEditText paramFormEditText, int paramInt)
  {
    this.mCvcText = paramFormEditText;
    this.mCardNumberText = null;
    this.mCvcLength = paramInt;
    this.mMaxFieldLength = paramInt;
  }
  
  public CvcValidator(FormEditText paramFormEditText, CreditCardNumberEditText paramCreditCardNumberEditText)
  {
    this.mCvcText = paramFormEditText;
    this.mCardNumberText = paramCreditCardNumberEditText;
    this.mCvcLength = -1;
    this.mMaxFieldLength = 4;
  }
  
  private boolean isLengthCorrect()
  {
    if (TextUtils.isEmpty(this.mCvcText.getText())) {}
    CreditCard.CardType localCardType;
    do
    {
      do
      {
        return false;
        if (this.mCvcLength == -1) {
          break;
        }
      } while (this.mCvcText.getText().length() != this.mCvcLength);
      return true;
      localCardType = this.mCardNumberText.getCardType();
    } while ((localCardType == null) || (this.mCvcText.getText().length() != localCardType.cvcLength));
    return true;
  }
  
  public final boolean isComplete()
  {
    return (isLengthCorrect()) || (this.mCvcText.getText().length() == this.mMaxFieldLength);
  }
  
  public final boolean isValid(TextView paramTextView)
  {
    if (TextUtils.isEmpty(paramTextView.getText()))
    {
      this.mErrorMessage = null;
      return true;
    }
    if (!isLengthCorrect())
    {
      this.mErrorMessage = paramTextView.getContext().getString(R.string.wallet_im_error_cvc_invalid);
      return false;
    }
    this.mErrorMessage = null;
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CvcValidator
 * JD-Core Version:    0.7.0.1
 */