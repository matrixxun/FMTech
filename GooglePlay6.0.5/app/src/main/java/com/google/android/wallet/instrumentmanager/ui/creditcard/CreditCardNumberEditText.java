package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.instrumentmanager.ui.common.DropDownButtonAdapter;
import com.google.android.wallet.instrumentmanager.ui.common.DropDownItem;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.validator.AbstractValidator;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.BinRange;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CardType;
import java.util.ArrayList;
import java.util.Arrays;

public class CreditCardNumberEditText
  extends FormEditText
  implements View.OnClickListener
{
  private static final int[] DEFAULT_DIGIT_GROUPING = { 4, 4, 4, 4 };
  CreditCard.CardType[] mAllowedCardTypes;
  private String mCardNumber = "";
  private Pair<CreditCard.CardType, CreditCard.BinRange> mCardTypeAndBinRange;
  private final TextWatcher mCreditCardNumberWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      String str = CreditCardNumberEditText.this.getFormattedCreditCardNumber(CreditCardNumberEditText.this.mCardNumber);
      if (!str.equals(paramAnonymousEditable.toString())) {
        paramAnonymousEditable.replace(0, paramAnonymousEditable.length(), str);
      }
    }
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      CreditCard.CardType localCardType1 = CreditCardNumberEditText.this.getCardType();
      CreditCardNumberEditText.access$300(CreditCardNumberEditText.this, CreditCardNumberEditText.this.getText().toString());
      CreditCard.CardType localCardType2 = CreditCardNumberEditText.this.getCardType();
      if ((CreditCardNumberEditText.this.mOnCreditCardTypeChangedListener != null) && (!PaymentUtils.cardTypeEquals(localCardType1, localCardType2))) {
        CreditCardNumberEditText.this.mOnCreditCardTypeChangedListener.onCreditCardTypeChanged(localCardType2);
      }
    }
  };
  private CreditCard.BinRange mInvalidBin;
  CreditCard.BinRange[] mInvalidBins;
  DropDownItem mNfcDropDownItem;
  DropDownItem mOcrDropDownItem;
  OnCreditCardTypeChangedListener mOnCreditCardTypeChangedListener;
  private ColorStateList mOriginalTextColors;
  
  public CreditCardNumberEditText(Context paramContext)
  {
    super(paramContext);
    initializeViewAndListeners(paramContext);
  }
  
  public CreditCardNumberEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initializeViewAndListeners(paramContext);
  }
  
  public CreditCardNumberEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initializeViewAndListeners(paramContext);
  }
  
  private static CreditCard.BinRange findFirstMatchingBin(CreditCard.BinRange[] paramArrayOfBinRange, String paramString)
  {
    CreditCard.BinRange localBinRange;
    if (paramArrayOfBinRange == null)
    {
      localBinRange = null;
      return localBinRange;
    }
    int i = paramString.length();
    int j = paramArrayOfBinRange.length;
    for (int k = 0;; k++)
    {
      if (k >= j) {
        break label90;
      }
      localBinRange = paramArrayOfBinRange[k];
      int m = localBinRange.start.length();
      if (i >= m)
      {
        String str = paramString.substring(0, m);
        if ((str.compareTo(localBinRange.start) >= 0) && (str.compareTo(localBinRange.end) <= 0)) {
          break;
        }
      }
    }
    label90:
    return null;
  }
  
  private int getCompleteCardNumberLength()
  {
    if (this.mCardTypeAndBinRange != null) {
      return ((CreditCard.BinRange)this.mCardTypeAndBinRange.second).cardNumberLength;
    }
    return 16;
  }
  
  private String getFormattedCreditCardNumber(String paramString)
  {
    if (this.mCardTypeAndBinRange != null) {}
    StringBuilder localStringBuilder;
    for (int[] arrayOfInt = ((CreditCard.BinRange)this.mCardTypeAndBinRange.second).digitGrouping;; arrayOfInt = DEFAULT_DIGIT_GROUPING)
    {
      localStringBuilder = new StringBuilder(-1 + (getCompleteCardNumberLength() + arrayOfInt.length));
      int i = 0;
      int j = 0;
      int k = 0;
      int m = paramString.length();
      while (i < m)
      {
        if (arrayOfInt[j] == k)
        {
          localStringBuilder.append(' ');
          k = 0;
          j++;
        }
        k++;
        localStringBuilder.append(paramString.charAt(i));
        i++;
      }
    }
    return localStringBuilder.toString();
  }
  
  private boolean hasDropDownButton()
  {
    return (getAdapter() != null) && (getAdapter().getCount() > 0);
  }
  
  private void initializeViewAndListeners(Context paramContext)
  {
    setKeyListener(DigitsKeyListener.getInstance("1234567890 "));
    setSingleLine();
    setOriginalTextColors();
    if (Build.VERSION.SDK_INT >= 17) {
      setTextDirection(3);
    }
    addTextChangedListenerToFront(this.mCreditCardNumberWatcher);
    addOnTextChangeValidator(new AbstractValidator()
    {
      public final boolean isValid(TextView paramAnonymousTextView)
      {
        return CreditCardNumberEditText.this.mInvalidBin == null;
      }
    });
    addValidator(new AbstractValidator(paramContext.getString(R.string.wallet_im_error_creditcard_number_invalid))
    {
      public final boolean isValid(TextView paramAnonymousTextView)
      {
        return (CreditCardNumberEditText.this.mCardTypeAndBinRange != null) && (CreditCardNumberEditText.this.isComplete()) && (PaymentUtils.passesChecksum(CreditCardNumberEditText.this.mCardNumber, 2));
      }
    });
  }
  
  private void setOriginalTextColors()
  {
    this.mOriginalTextColors = getTextColors();
  }
  
  final void addDropDownButton(DropDownItem paramDropDownItem)
  {
    if (getAdapter() == null)
    {
      ArrayList localArrayList = new ArrayList(2);
      localArrayList.add(paramDropDownItem);
      setAdapter(new DropDownButtonAdapter(getContext(), localArrayList));
      setOnClickListener(this);
      return;
    }
    ((DropDownButtonAdapter)getAdapter()).add(paramDropDownItem);
  }
  
  public boolean enoughToFilter()
  {
    return getText().length() == 0;
  }
  
  public String getCardNumber()
  {
    return this.mCardNumber;
  }
  
  public CreditCard.CardType getCardType()
  {
    if (this.mCardTypeAndBinRange != null) {
      return (CreditCard.CardType)this.mCardTypeAndBinRange.first;
    }
    return null;
  }
  
  public String getConcealedCardNumber()
  {
    int i = getCompleteCardNumberLength();
    int j = Math.min(this.mCardNumber.length(), i - 4);
    char[] arrayOfChar = new char[j];
    Arrays.fill(arrayOfChar, '•');
    StringBuilder localStringBuilder = new StringBuilder(i).append(arrayOfChar);
    if (j < this.mCardNumber.length()) {
      localStringBuilder.append(this.mCardNumber.substring(j));
    }
    return getFormattedCreditCardNumber(localStringBuilder.toString());
  }
  
  public final boolean isComplete()
  {
    return this.mCardNumber.length() == getCompleteCardNumberLength();
  }
  
  public void onClick(View paramView)
  {
    if (paramView == this) {
      showDropDownIfNecessary();
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    setOriginalTextColors();
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if (hasDropDownButton())
    {
      if (paramBoolean) {
        showDropDownIfNecessary();
      }
    }
    else {
      return;
    }
    dismissDropDown();
  }
  
  protected void replaceText(CharSequence paramCharSequence) {}
  
  public void setAllowedCardTypes(CreditCard.CardType[] paramArrayOfCardType)
  {
    this.mAllowedCardTypes = paramArrayOfCardType;
  }
  
  public void setInvalidBins(CreditCard.BinRange[] paramArrayOfBinRange)
  {
    this.mInvalidBins = paramArrayOfBinRange;
  }
  
  public void setOnCreditCardTypeChangedListener(OnCreditCardTypeChangedListener paramOnCreditCardTypeChangedListener)
  {
    this.mOnCreditCardTypeChangedListener = paramOnCreditCardTypeChangedListener;
  }
  
  public final void showDropDownIfNecessary()
  {
    if ((hasDropDownButton()) && (enoughToFilter()) && (hasFocus()))
    {
      showDropDown();
      setError(null);
    }
  }
  
  public static abstract interface OnCreditCardTypeChangedListener
  {
    public abstract void onCreditCardTypeChanged(CreditCard.CardType paramCardType);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardNumberEditText
 * JD-Core Version:    0.7.0.1
 */