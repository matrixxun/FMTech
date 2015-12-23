package com.google.android.wallet.ui.common;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.google.android.wallet.ui.common.validator.AbstractValidator;
import com.google.android.wallet.uicomponents.R.string;
import com.google.commerce.payments.orchestration.proto.common.DateOuterClass.Date;
import java.util.GregorianCalendar;

public final class ExpDateValidator
  extends AbstractValidator
{
  private final ExpDateEditText mExpDateEditText;
  private final GregorianCalendar mMaxDate;
  private final GregorianCalendar mMinDate;
  
  public ExpDateValidator(ExpDateEditText paramExpDateEditText, DateOuterClass.Date paramDate1, DateOuterClass.Date paramDate2)
  {
    this.mExpDateEditText = paramExpDateEditText;
    this.mMinDate = new GregorianCalendar(paramDate1.year, -1 + paramDate1.month, 1);
    this.mMaxDate = new GregorianCalendar(paramDate2.year, -1 + paramDate2.month, 1);
  }
  
  public final boolean isValid(TextView paramTextView)
  {
    if (TextUtils.isEmpty(paramTextView.getText())) {
      return true;
    }
    int i = this.mExpDateEditText.getExpMonth();
    int j = this.mExpDateEditText.getExpYear();
    int k;
    if ((i <= 0) || (i > 12)) {
      k = 2;
    }
    for (;;)
    {
      switch (k)
      {
      default: 
        throw new IllegalArgumentException("Unexpected expiration date error!");
        if (j == 0)
        {
          k = 3;
        }
        else
        {
          if (j < 100) {
            j += 2000;
          }
          GregorianCalendar localGregorianCalendar = new GregorianCalendar(j, i - 1, 1);
          if (localGregorianCalendar.compareTo(this.mMinDate) < 0) {
            k = -1;
          } else if (localGregorianCalendar.compareTo(this.mMaxDate) > 0) {
            k = 1;
          } else {
            k = 0;
          }
        }
        break;
      }
    }
    this.mErrorMessage = null;
    return true;
    this.mErrorMessage = this.mExpDateEditText.getContext().getString(R.string.wallet_uic_error_expired_credit_card);
    return false;
    this.mErrorMessage = this.mExpDateEditText.getContext().getString(R.string.wallet_uic_error_year_must_not_be_empty);
    return false;
    this.mErrorMessage = this.mExpDateEditText.getContext().getString(R.string.wallet_uic_error_year_invalid);
    return false;
    this.mErrorMessage = this.mExpDateEditText.getContext().getString(R.string.wallet_uic_error_month_invalid);
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.ExpDateValidator
 * JD-Core Version:    0.7.0.1
 */