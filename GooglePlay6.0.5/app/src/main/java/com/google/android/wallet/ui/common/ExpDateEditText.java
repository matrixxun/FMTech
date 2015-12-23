package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.uicomponents.R.string;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.FormattingSchemesOuterClass.TemplateFormattingScheme;
import java.util.regex.Pattern;

public class ExpDateEditText
  extends FormEditText
  implements InputFilter, TextView.OnEditorActionListener
{
  private String mDateDivider;
  private Pattern mDateDividerPattern;
  private final TextWatcher mExpDateTextWatcher = new TextWatcher()
  {
    private boolean ignoreTextChangeCallbacks = false;
    private boolean mDeleteEvent;
    private StringBuilder mValueStringBuilder;
    private int mValueStringInsertionIndex;
    
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      if (this.ignoreTextChangeCallbacks) {}
      int i;
      int j;
      do
      {
        return;
        i = ExpDateEditText.access$100(ExpDateEditText.this, this.mValueStringBuilder);
        String str = ExpDateEditText.this.getFormattedDate();
        this.ignoreTextChangeCallbacks = true;
        ExpDateEditText.this.setValue(str, false);
        this.ignoreTextChangeCallbacks = false;
        j = Selection.getSelectionStart(paramAnonymousEditable);
      } while (j != Selection.getSelectionEnd(paramAnonymousEditable));
      ExpDateEditText.access$300(ExpDateEditText.this, j + i, this.mDeleteEvent);
    }
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      if (this.ignoreTextChangeCallbacks) {
        return;
      }
      this.mDeleteEvent = false;
      this.mValueStringBuilder = new StringBuilder(paramAnonymousCharSequence);
      this.mValueStringInsertionIndex = paramAnonymousInt1;
      int i = 0;
      if (paramAnonymousInt2 == 1)
      {
        i = 0;
        if (paramAnonymousInt3 == 0)
        {
          this.mDeleteEvent = true;
          String str = paramAnonymousCharSequence.subSequence(paramAnonymousInt1, paramAnonymousInt1 + paramAnonymousInt2).toString();
          boolean bool = ExpDateEditText.this.mDateDivider.equals(str);
          i = 0;
          if (bool) {
            i = 1;
          }
        }
      }
      if (i != 0)
      {
        this.mValueStringBuilder.deleteCharAt(paramAnonymousInt1 - 1);
        this.mValueStringInsertionIndex = (-1 + this.mValueStringInsertionIndex);
        return;
      }
      this.mValueStringBuilder.delete(paramAnonymousInt1, paramAnonymousInt1 + paramAnonymousInt2);
    }
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      if (this.ignoreTextChangeCallbacks) {
        return;
      }
      this.mValueStringBuilder.insert(this.mValueStringInsertionIndex, paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt1 + paramAnonymousInt3);
    }
  };
  private String mExpMonth = "";
  private String mExpYear = "";
  
  public ExpDateEditText(Context paramContext)
  {
    super(paramContext);
    initializeViewAndListeners();
  }
  
  public ExpDateEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initializeViewAndListeners();
  }
  
  public ExpDateEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initializeViewAndListeners();
  }
  
  private static int countNumOccurrences(StringBuilder paramStringBuilder, String paramString)
  {
    int i = paramStringBuilder.indexOf(paramString);
    int j = 0;
    while (i != -1)
    {
      j++;
      i = paramStringBuilder.indexOf(paramString, i + paramString.length());
    }
    return j;
  }
  
  private String getFormattedDate()
  {
    if ((this.mExpMonth.length() > 0) || (!TextUtils.isEmpty(this.mExpYear))) {
      return this.mExpMonth + this.mDateDivider + this.mExpYear;
    }
    return this.mExpMonth;
  }
  
  private void initializeViewAndListeners()
  {
    this.mDateDivider = getResources().getString(R.string.wallet_uic_exp_date_separator);
    this.mDateDividerPattern = Pattern.compile(this.mDateDivider);
    setKeyListener(new SimpleKeyListener("0123456789" + this.mDateDivider));
    setSingleLine();
    setOnEditorActionListener(this);
    addTextChangedListenerToFront(this.mExpDateTextWatcher);
    setFilters(new InputFilter[] { this });
    if (Build.VERSION.SDK_INT >= 17) {
      setTextDirection(3);
    }
  }
  
  private boolean isSelectionInMonth(int paramInt)
  {
    return paramInt <= this.mExpMonth.length();
  }
  
  private void setExpMonth(String paramString)
  {
    String str = PaymentUtils.removeNonNumericDigits(paramString);
    if (str.length() > 2) {
      str = str.substring(0, 2);
    }
    this.mExpMonth = str;
  }
  
  private void setExpYear(String paramString)
  {
    String str = PaymentUtils.removeNonNumericDigits(paramString);
    if (str.length() > 2) {
      str = str.substring(-2 + str.length());
    }
    this.mExpYear = str;
  }
  
  @TargetApi(14)
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if ((Build.VERSION.SDK_INT >= 14) && (paramAccessibilityEvent.getEventType() == 8192))
    {
      int i = getText().length();
      if ((paramAccessibilityEvent.getFromIndex() == i) && (paramAccessibilityEvent.getToIndex() == i)) {
        return false;
      }
    }
    return super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
  }
  
  public CharSequence filter(CharSequence paramCharSequence, int paramInt1, int paramInt2, Spanned paramSpanned, int paramInt3, int paramInt4)
  {
    if (paramInt1 == paramInt2) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder().append(paramSpanned.subSequence(0, paramInt3)).append(paramCharSequence.subSequence(paramInt1, paramInt2)).append(paramSpanned.subSequence(paramInt4, paramSpanned.length()));
    int i = countNumOccurrences(localStringBuilder, this.mDateDivider);
    if ((i > 1) || ((i == 1) && (localStringBuilder.length() == this.mDateDivider.length()))) {
      return "";
    }
    String[] arrayOfString1 = this.mDateDividerPattern.split(localStringBuilder.toString(), 2);
    String[] arrayOfString2 = this.mDateDividerPattern.split(paramSpanned.toString(), 2);
    int j = arrayOfString1[0].indexOf(arrayOfString2[0]);
    if ((arrayOfString2[0].length() == 2) && (j != 0)) {
      return "";
    }
    if ((arrayOfString1.length > 1) && (arrayOfString1[1].length() > 2)) {
      return "";
    }
    return null;
  }
  
  public int getExpMonth()
  {
    try
    {
      int i = Integer.parseInt(this.mExpMonth);
      return i;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  public int getExpYear()
  {
    try
    {
      int i = Integer.parseInt(this.mExpYear);
      return i + 2000;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  public final boolean isComplete()
  {
    if (this.mExpYear.length() == 2)
    {
      if (!PaymentUtils.isMonthComplete(this.mExpMonth))
      {
        if (!this.mExpMonth.equals("1")) {
          break label77;
        }
        int i = getSelectionStart();
        int j = getText().toString().indexOf(this.mDateDivider);
        if ((j == -1) || (i <= j)) {
          break label72;
        }
      }
      label72:
      for (int k = 1; k != 0; k = 0) {
        return true;
      }
    }
    label77:
    return false;
  }
  
  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    }
    int i;
    int j;
    do
    {
      return false;
      i = paramTextView.getSelectionStart();
      j = paramTextView.getSelectionEnd();
    } while ((this.mExpMonth.length() <= 0) || (i != j) || (!isSelectionInMonth(i)));
    setSelection(paramTextView.getText().length());
    return true;
  }
  
  public final void setExpDate(String paramString1, String paramString2)
  {
    setExpMonth(paramString1);
    setExpYear(paramString2);
    setText(getFormattedDate());
  }
  
  public final void setMinMaxLength(int paramInt1, int paramInt2)
  {
    throw new UnsupportedOperationException("Setting minimum/maximum length is not supported for ExpDateEditText");
  }
  
  public void setTemplateFormattingScheme(FormattingSchemesOuterClass.TemplateFormattingScheme paramTemplateFormattingScheme)
  {
    throw new UnsupportedOperationException("Template formatting schemes are not supported for ExpDateEditText");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.ExpDateEditText
 * JD-Core Version:    0.7.0.1
 */