package com.google.android.wallet.instrumentmanager.ui.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.instrumentmanager.R.color;
import com.google.android.wallet.instrumentmanager.ui.common.DropDownButtonAdapter;
import com.google.android.wallet.instrumentmanager.ui.common.DropDownItem;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.validator.AbstractValidator;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.ExcludedPanCategory;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.PanCategory;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.PanPrefixSet;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.FormattingSchemesOuterClass.TemplateFormattingScheme;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.FormattingSchemesOuterClass.TemplateFormattingScheme.InputCharacter;
import java.util.ArrayList;
import java.util.Arrays;

public class CardNumberEditText
  extends FormEditText
  implements View.OnClickListener
{
  private static final FormattingSchemesOuterClass.TemplateFormattingScheme PAN_TEMPLATE_DEFAULT;
  CardFormOuterClass.PanCategory[] mAllowedPanCategories;
  private int[] mAllowedPanCategoriesMatchResult;
  private String mCardNumber = "";
  private final TextWatcher mCardNumberWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      CardFormOuterClass.PanCategory localPanCategory = CardNumberEditText.this.mResolvedPanCategory;
      CardNumberEditText.access$400(CardNumberEditText.this);
      if (!PaymentUtils.panCategoryEquals(localPanCategory, CardNumberEditText.this.mResolvedPanCategory))
      {
        if ((CardNumberEditText.this.mResolvedPanCategory == null) || (CardNumberEditText.this.mResolvedPanCategory.format == null)) {
          break label213;
        }
        CardNumberEditText.this.setTemplateFormattingScheme(CardNumberEditText.this.mResolvedPanCategory.format);
        if (CardNumberEditText.this.mResolvedPanCategory.minLength >= 0) {
          CardNumberEditText.this.setMinMaxLength(CardNumberEditText.this.mResolvedPanCategory.minLength, CardNumberEditText.this.mTemplateInputIndices.size());
        }
      }
      for (;;)
      {
        if (CardNumberEditText.this.mOnPanCategoryChangedListener != null) {
          CardNumberEditText.this.mOnPanCategoryChangedListener.onPanCategoryChanged(CardNumberEditText.this.mResolvedPanCategory);
        }
        if (((CardNumberEditText.this.mHasAllowedPotentialMatch) || (CardNumberEditText.this.mResolvedPanCategory != null)) && ((!CardNumberEditText.this.isComplete()) || (CardNumberEditText.this.isValid()))) {
          break;
        }
        CardNumberEditText.this.setTextColor(CardNumberEditText.this.getResources().getColor(R.color.wallet_im_credit_card_invalid_text_color));
        WalletUiUtils.playShakeAnimationIfPossible$4709551c(CardNumberEditText.this.getContext(), CardNumberEditText.this);
        return;
        label213:
        CardNumberEditText.this.setTemplateFormattingScheme(CardNumberEditText.PAN_TEMPLATE_DEFAULT);
      }
      CardNumberEditText.this.setTextColor(CardNumberEditText.this.mOriginalTextColors);
    }
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  CardFormOuterClass.ExcludedPanCategory[] mExcludedPanCategories;
  private int[] mExcludedPanCategoriesMatchResult;
  private CardFormOuterClass.ExcludedPanCategory mFirstMatchingExcludedPanCategory;
  private boolean mHasAllowedPotentialMatch = true;
  String mInvalidPanMessage;
  DropDownItem mNfcDropDownItem;
  String mNoMatchPanMessage;
  DropDownItem mOcrDropDownItem;
  OnPanCategoryChangedListener mOnPanCategoryChangedListener;
  private ColorStateList mOriginalTextColors;
  private CardFormOuterClass.PanCategory mResolvedPanCategory;
  
  static
  {
    FormattingSchemesOuterClass.TemplateFormattingScheme localTemplateFormattingScheme = new FormattingSchemesOuterClass.TemplateFormattingScheme();
    PAN_TEMPLATE_DEFAULT = localTemplateFormattingScheme;
    FormattingSchemesOuterClass.TemplateFormattingScheme.InputCharacter[] arrayOfInputCharacter = new FormattingSchemesOuterClass.TemplateFormattingScheme.InputCharacter[1];
    arrayOfInputCharacter[0] = new FormattingSchemesOuterClass.TemplateFormattingScheme.InputCharacter();
    localTemplateFormattingScheme.inputCharacter = arrayOfInputCharacter;
    PAN_TEMPLATE_DEFAULT.inputCharacter[0].placeholderCharacter = "D";
    PAN_TEMPLATE_DEFAULT.template = "DDDD DDDD DDDD DDDD";
    PAN_TEMPLATE_DEFAULT.showTemplate = false;
  }
  
  public CardNumberEditText(Context paramContext)
  {
    super(paramContext);
    initializeViewAndListeners();
  }
  
  public CardNumberEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initializeViewAndListeners();
  }
  
  public CardNumberEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initializeViewAndListeners();
  }
  
  private static int calculatePanPrefixSetsMatchResult(CardFormOuterClass.PanPrefixSet[] paramArrayOfPanPrefixSet, String paramString)
  {
    int i = paramArrayOfPanPrefixSet.length;
    int j = 0;
    CardFormOuterClass.PanPrefixSet localPanPrefixSet;
    int k;
    int m;
    int n;
    int i1;
    int i5;
    if (j < i)
    {
      localPanPrefixSet = paramArrayOfPanPrefixSet[j];
      k = paramString.length();
      m = localPanPrefixSet.length;
      String str2;
      if (k >= m)
      {
        n = 1;
        boolean bool = localPanPrefixSet.firstDigits < 0L;
        i1 = 0;
        if (!bool) {
          break label120;
        }
        str2 = Long.toString(localPanPrefixSet.firstDigits);
        i1 = str2.length();
        if (k > i1) {
          break label111;
        }
        if (!str2.startsWith(paramString)) {
          break label105;
        }
        i5 = 0;
      }
      for (;;)
      {
        label91:
        if (i5 < 0) {
          break label365;
        }
        return i5;
        n = 0;
        break;
        label105:
        i5 = -1;
        continue;
        label111:
        if (!paramString.startsWith(str2)) {
          break label359;
        }
        label120:
        if (i1 != m - 1) {
          break label182;
        }
        if (n != 0) {
          break label140;
        }
        i5 = 0;
      }
    }
    label140:
    label182:
    label206:
    int i4;
    label327:
    label359:
    label365:
    label373:
    for (int i6 = 0;; i6 = i4)
    {
      int i7 = Character.getNumericValue(paramString.charAt(m - 1));
      if ((localPanPrefixSet.lastDigitBitset[i6] & 1 << i7) != 0)
      {
        i5 = localPanPrefixSet.length;
        break label91;
        long l5;
        long l3;
        if (n != 0)
        {
          l5 = Long.parseLong(paramString.substring(i1, m - 1));
          l3 = l5;
          i4 = Arrays.binarySearch(localPanPrefixSet.middleDigits, l3);
          if (i4 < 0) {
            break label327;
          }
        }
        do
        {
          if (n != 0) {
            break label373;
          }
          i5 = 0;
          break;
          int i2 = -1 + (m - k);
          String str1 = paramString.substring(i1);
          if (TextUtils.isEmpty(str1)) {}
          long l4;
          for (long l1 = 0L;; l1 = Long.parseLong(str1))
          {
            long l2 = 1L + l1;
            int i3 = 0;
            l3 = l1;
            l4 = l2;
            while (i3 < i2)
            {
              l3 *= 10L;
              l4 *= 10L;
              i3++;
            }
          }
          l5 = l4 - 1L;
          break label206;
          i4 = -1 + -i4;
        } while ((i4 != localPanPrefixSet.middleDigits.length) && (localPanPrefixSet.middleDigits[i4] <= l5));
      }
      i5 = -1;
      break label91;
      j++;
      break;
      return -1;
    }
  }
  
  private boolean hasDropDownButton()
  {
    return (getAdapter() != null) && (getAdapter().getCount() > 0);
  }
  
  private void initializeViewAndListeners()
  {
    setOriginalTextColors();
    setInputType(2);
    setTemplateFormattingScheme(PAN_TEMPLATE_DEFAULT);
    if (Build.VERSION.SDK_INT >= 17) {
      setTextDirection(3);
    }
    addTextChangedListenerToFront(this.mCardNumberWatcher);
    addOnTextChangeValidator(new AbstractValidator()
    {
      public final boolean isValid(TextView paramAnonymousTextView)
      {
        return CardNumberEditText.this.mFirstMatchingExcludedPanCategory == null;
      }
    });
    addOnTextChangeValidator(new AbstractValidator()
    {
      public final boolean isValid(TextView paramAnonymousTextView)
      {
        return (CardNumberEditText.this.mHasAllowedPotentialMatch) || (CardNumberEditText.this.mResolvedPanCategory != null);
      }
    });
    addValidator(new AbstractValidator()
    {
      public final boolean isValid(TextView paramAnonymousTextView)
      {
        return (CardNumberEditText.this.mResolvedPanCategory != null) && (PaymentUtils.passesChecksum(CardNumberEditText.this.mCardNumber, CardNumberEditText.this.mResolvedPanCategory.panChecksumType));
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
  
  public String getConcealedCardNumber()
  {
    if (this.mResolvedPanCategory != null) {}
    StringBuilder localStringBuilder;
    for (int i = this.mResolvedPanCategory.concealedVisibleDigitsCount;; i = 4)
    {
      int j = this.mTemplateInputIndices.size() - i;
      localStringBuilder = new StringBuilder(getText());
      int k = localStringBuilder.length();
      for (int m = 0; m < j; m++)
      {
        int n = ((Integer)this.mTemplateInputIndices.get(m)).intValue();
        if (n >= k) {
          break;
        }
        localStringBuilder.setCharAt(n, '•');
      }
    }
    return localStringBuilder.toString();
  }
  
  protected String getIncompleteErrorMessage()
  {
    return this.mInvalidPanMessage;
  }
  
  public CardFormOuterClass.PanCategory getPanCategory()
  {
    return this.mResolvedPanCategory;
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
  
  public void setAllowedPanCategories(CardFormOuterClass.PanCategory[] paramArrayOfPanCategory)
  {
    this.mAllowedPanCategories = paramArrayOfPanCategory;
    this.mAllowedPanCategoriesMatchResult = new int[paramArrayOfPanCategory.length];
  }
  
  public void setExcludedPanCategories(CardFormOuterClass.ExcludedPanCategory[] paramArrayOfExcludedPanCategory)
  {
    this.mExcludedPanCategories = paramArrayOfExcludedPanCategory;
    this.mExcludedPanCategoriesMatchResult = new int[paramArrayOfExcludedPanCategory.length];
  }
  
  public void setInvalidPanMessage(String paramString)
  {
    this.mInvalidPanMessage = paramString;
  }
  
  public void setNoMatchPanMessage(String paramString)
  {
    this.mNoMatchPanMessage = paramString;
  }
  
  public void setOnPanCategoryChangedListener(OnPanCategoryChangedListener paramOnPanCategoryChangedListener)
  {
    this.mOnPanCategoryChangedListener = paramOnPanCategoryChangedListener;
  }
  
  public final void showDropDownIfNecessary()
  {
    if ((hasDropDownButton()) && (enoughToFilter()) && (hasFocus()))
    {
      showDropDown();
      setError(null);
    }
  }
  
  public static abstract interface OnPanCategoryChangedListener
  {
    public abstract void onPanCategoryChanged(CardFormOuterClass.PanCategory paramPanCategory);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.card.CardNumberEditText
 * JD-Core Version:    0.7.0.1
 */