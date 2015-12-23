package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.ListAdapter;
import com.google.android.wallet.common.util.AndroidUtils;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.dependencygraph.ResultingActionComponent;
import com.google.android.wallet.dependencygraph.TriggerComponent;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.ui.common.listeners.EditTextAutoAdvanceListener;
import com.google.android.wallet.ui.common.listeners.ValueChangedTriggerTextWatcher;
import com.google.android.wallet.ui.common.validator.AbstractValidator;
import com.google.android.wallet.ui.common.validator.AndValidator;
import com.google.android.wallet.ui.common.validator.ComposedValidator;
import com.google.android.wallet.ui.common.validator.InputLengthValidator;
import com.google.android.wallet.ui.common.validator.PatternValidator;
import com.google.android.wallet.ui.common.validator.RequiredValidator;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.string;
import com.google.android.wallet.uicomponents.R.styleable;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ResultingActionReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.FormattingSchemesOuterClass.TemplateFormattingScheme;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.FormattingSchemesOuterClass.TemplateFormattingScheme.InputCharacter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FormEditText
  extends AppCompatAutoCompleteTextView
  implements ResultingActionComponent, TriggerComponent, AutoAdvancer, Completable, ValidatableComponent
{
  private static final Pattern PATTERN_DIGITS_ONLY = Pattern.compile("\\d*");
  private boolean mAllowFullScreenIme;
  private Completable mAutoAdvanceCompletable;
  private FieldValidatable mAutoAdvanceFieldValidatable;
  public EditTextAutoAdvanceListener mAutoAdvanceListener;
  private boolean mAutoRetreat;
  private boolean mBlockCompletion;
  private String mCachedText;
  private int mCachedThreshold;
  private String mCachedValue;
  ErrorHandler mErrorHandler;
  private final ComposedValidator mFocusChangeAndValidateableValidator = new AndValidator(new AbstractValidator[0]);
  LinkedList<View.OnFocusChangeListener> mFocusChangeListenerList;
  HintHandler mHintHandler;
  private int mMaxFieldLength = -1;
  private InputLengthValidator mMinLengthValidator;
  private OnErrorListener mOnErrorlistener;
  private final TextWatcher mOnTextChangeValidationTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      if ((!FormEditText.this.mTextChangedValidator.isValid(FormEditText.this)) && (FormEditText.this.mTextChangedValidator.getErrorMessage() != null)) {
        FormEditText.this.setError(FormEditText.this.mTextChangedValidator.getErrorMessage());
      }
      while ((!FormEditText.this.isFocused()) || (paramAnonymousEditable == null) || (FormEditText.this.getError() == null)) {
        return;
      }
      FormEditText.this.setError(null);
      FormEditText.access$102(FormEditText.this, null);
    }
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  private FieldValidatable mOutOfFocusFieldValidatable = this;
  boolean mRequired = true;
  private String mRequiredError = null;
  private RequiredValidator mRequiredValidator;
  private CharSequence mSavedError;
  private boolean mShowTemplate;
  private String mTemplate;
  public final ArrayList<Integer> mTemplateInputIndices = new ArrayList();
  private FormattingSchemesOuterClass.TemplateFormattingScheme mTemplateSpec;
  TextWatcher mTemplateTextWatcher = new TextWatcher()
  {
    private int mValueStartIndex;
    private StringBuilder mValueStringBuilder;
    
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      FormEditText.this.setValue(this.mValueStringBuilder, false);
      if ((this.mValueStartIndex < 0) || (this.mValueStartIndex > FormEditText.this.mTemplateInputIndices.size())) {
        this.mValueStartIndex = FormEditText.this.mTemplateInputIndices.size();
      }
      FormEditText.this.setCursorPositionInValue(this.mValueStartIndex);
    }
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      this.mValueStringBuilder = new StringBuilder(paramAnonymousCharSequence.length());
      this.mValueStartIndex = -1;
      if ((FormEditText.this.getSelectionStart() == FormEditText.this.getSelectionEnd()) && (paramAnonymousInt2 == 1) && (paramAnonymousInt3 == 0))
      {
        int m = 0;
        int n = FormEditText.this.mTemplateInputIndices.size();
        if (m < n)
        {
          int i1 = ((Integer)FormEditText.this.mTemplateInputIndices.get(m)).intValue();
          if ((this.mValueStartIndex < 0) && (i1 >= paramAnonymousInt1 + 1))
          {
            if (this.mValueStringBuilder.length() <= 0) {
              break label174;
            }
            this.mValueStartIndex = (-1 + this.mValueStringBuilder.length());
            this.mValueStringBuilder.deleteCharAt(this.mValueStartIndex);
          }
          for (;;)
          {
            if (i1 >= paramAnonymousCharSequence.length()) {
              break label182;
            }
            this.mValueStringBuilder.append(paramAnonymousCharSequence.charAt(i1));
            m++;
            break;
            label174:
            this.mValueStartIndex = 0;
          }
        }
        label182:
        if (this.mValueStartIndex < 0)
        {
          this.mValueStartIndex = (-1 + this.mValueStringBuilder.length());
          this.mValueStringBuilder.deleteCharAt(this.mValueStartIndex);
        }
        return;
      }
      int i = 0;
      int j = FormEditText.this.mTemplateInputIndices.size();
      label230:
      int k;
      if (i < j)
      {
        k = ((Integer)FormEditText.this.mTemplateInputIndices.get(i)).intValue();
        if (k >= paramAnonymousInt1) {
          break label285;
        }
        this.mValueStringBuilder.append(paramAnonymousCharSequence.charAt(k));
      }
      for (;;)
      {
        i++;
        break label230;
        break;
        label285:
        if (this.mValueStartIndex < 0) {
          this.mValueStartIndex = this.mValueStringBuilder.length();
        }
        if (k >= paramAnonymousCharSequence.length()) {
          break;
        }
        if (k >= paramAnonymousInt1 + paramAnonymousInt2) {
          this.mValueStringBuilder.append(paramAnonymousCharSequence.charAt(k));
        }
      }
    }
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      if (this.mValueStartIndex >= 0)
      {
        this.mValueStringBuilder.insert(this.mValueStartIndex, paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt1 + paramAnonymousInt3);
        this.mValueStartIndex = (paramAnonymousInt3 + this.mValueStartIndex);
      }
    }
  };
  private final ComposedValidator mTextChangedValidator = new AndValidator(new AbstractValidator[0]);
  LinkedList<TextWatcher> mTextWatcherList;
  private final TextWatcher mTextWatcherWrapper = new TextWatcher()
  {
    private CharSequence mPreviousError;
    
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      if (FormEditText.this.mUserInputOnlyTextWatchersEnabled)
      {
        Iterator localIterator2 = FormEditText.this.mUserInputOnlyTextWatcherList.iterator();
        while (localIterator2.hasNext()) {
          ((TextWatcher)localIterator2.next()).afterTextChanged(paramAnonymousEditable);
        }
      }
      Iterator localIterator1 = FormEditText.this.getTextWatcherList().iterator();
      while (localIterator1.hasNext()) {
        ((TextWatcher)localIterator1.next()).afterTextChanged(paramAnonymousEditable);
      }
      if (FormEditText.this.mAutoAdvanceListener != null) {
        FormEditText.this.mAutoAdvanceListener.afterTextChanged(paramAnonymousEditable);
      }
      CharSequence localCharSequence = FormEditText.this.getError();
      if ((localCharSequence != null) && (!TextUtils.equals(localCharSequence, this.mPreviousError))) {
        FormEditText.this.announceError();
      }
      this.mPreviousError = localCharSequence;
    }
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      this.mPreviousError = FormEditText.this.getError();
      if (FormEditText.this.mUserInputOnlyTextWatchersEnabled)
      {
        Iterator localIterator2 = FormEditText.this.mUserInputOnlyTextWatcherList.iterator();
        while (localIterator2.hasNext()) {
          ((TextWatcher)localIterator2.next()).beforeTextChanged(paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
        }
      }
      Iterator localIterator1 = FormEditText.this.getTextWatcherList().iterator();
      while (localIterator1.hasNext()) {
        ((TextWatcher)localIterator1.next()).beforeTextChanged(paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }
      if (FormEditText.this.mAutoAdvanceListener != null) {
        FormEditText.this.mAutoAdvanceListener.beforeTextChanged(paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }
    }
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      if (FormEditText.this.mUserInputOnlyTextWatchersEnabled)
      {
        Iterator localIterator2 = FormEditText.this.mUserInputOnlyTextWatcherList.iterator();
        while (localIterator2.hasNext()) {
          ((TextWatcher)localIterator2.next()).onTextChanged(paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
        }
      }
      Iterator localIterator1 = FormEditText.this.getTextWatcherList().iterator();
      while (localIterator1.hasNext()) {
        ((TextWatcher)localIterator1.next()).onTextChanged(paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }
      if (FormEditText.this.mAutoAdvanceListener != null) {
        FormEditText.this.mAutoAdvanceListener.onTextChanged(paramAnonymousCharSequence, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
      }
    }
  };
  private TriggerListener mTriggerListener;
  public LinkedList<TextWatcher> mUserInputOnlyTextWatcherList = new LinkedList();
  private boolean mUserInputOnlyTextWatchersEnabled = true;
  public boolean mValidateWhenNotVisible = false;
  View mVisibilityMatchingView;
  
  public FormEditText(Context paramContext)
  {
    super(paramContext);
    readAttributesAndAddListeners(paramContext, null);
  }
  
  public FormEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributesAndAddListeners(paramContext, paramAttributeSet);
  }
  
  public FormEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributesAndAddListeners(paramContext, paramAttributeSet);
  }
  
  private LinkedList<View.OnFocusChangeListener> getFocusChangeList()
  {
    if (this.mFocusChangeListenerList == null) {
      this.mFocusChangeListenerList = new LinkedList();
    }
    return this.mFocusChangeListenerList;
  }
  
  private LinkedList<TextWatcher> getTextWatcherList()
  {
    if (this.mTextWatcherList == null) {
      this.mTextWatcherList = new LinkedList();
    }
    return this.mTextWatcherList;
  }
  
  private void onRequiredChanged(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.mRequiredValidator != null))
    {
      removeValidator(this.mRequiredValidator);
      this.mRequiredValidator = null;
    }
    if ((this.mRequired) && (this.mRequiredValidator == null))
    {
      this.mRequiredValidator = new RequiredValidator(this.mRequiredError);
      addValidator(this.mRequiredValidator);
    }
    while ((this.mRequired) || (this.mRequiredValidator == null)) {
      return;
    }
    removeValidator(this.mRequiredValidator);
    this.mRequiredValidator = null;
  }
  
  @TargetApi(11)
  private void readAttributesAndAddListeners(Context paramContext, AttributeSet paramAttributeSet)
  {
    int[] arrayOfInt1 = new int[2];
    arrayOfInt1[0] = 16843104;
    arrayOfInt1[1] = R.attr.internalUicAllowFullScreenIme;
    TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(paramAttributeSet, arrayOfInt1);
    this.mMaxFieldLength = localTypedArray1.getInt(0, -1);
    this.mAllowFullScreenIme = localTypedArray1.getBoolean(1, true);
    localTypedArray1.recycle();
    int[] arrayOfInt2 = new int[1];
    arrayOfInt2[0] = R.attr.internalUicValidateFieldsWhenNotVisible;
    TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(arrayOfInt2);
    boolean bool = localTypedArray2.getBoolean(0, false);
    localTypedArray2.recycle();
    TypedArray localTypedArray3 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WalletUicFormEditText);
    this.mRequired = localTypedArray3.getBoolean(R.styleable.WalletUicFormEditText_internalUicRequired, true);
    this.mValidateWhenNotVisible = localTypedArray3.getBoolean(R.styleable.WalletUicFormEditText_internalUicValidateWhenNotVisible, bool);
    String str = localTypedArray3.getString(R.styleable.WalletUicFormEditText_internalUicValidatorErrorString);
    Object localObject;
    switch (localTypedArray3.getInt(R.styleable.WalletUicFormEditText_internalUicValidatorType, 0))
    {
    default: 
      localObject = null;
      if (this.mRequired)
      {
        this.mRequiredError = localTypedArray3.getString(R.styleable.WalletUicFormEditText_internalUicRequiredErrorString);
        if (TextUtils.isEmpty(this.mRequiredError)) {
          this.mRequiredError = paramContext.getString(R.string.wallet_uic_error_field_must_not_be_empty);
        }
        onRequiredChanged(false);
      }
      if (localObject != null) {
        addValidator((AbstractValidator)localObject);
      }
      localTypedArray3.recycle();
      if (this.mAllowFullScreenIme) {
        setImeOptions(getImeOptions());
      }
      break;
    }
    for (;;)
    {
      super.addTextChangedListener(this.mTextWatcherWrapper);
      addTextChangedListenerToFront(this.mOnTextChangeValidationTextWatcher);
      setThreshold(2147483647);
      return;
      if (TextUtils.isEmpty(str)) {
        str = paramContext.getString(R.string.wallet_uic_error_only_numeric_digits_allowed);
      }
      localObject = new PatternValidator(str, PATTERN_DIGITS_ONLY);
      setInputType(2);
      break;
      localObject = new PatternValidator(str, Pattern.compile(localTypedArray3.getString(R.styleable.WalletUicFormEditText_internalUicValidatorRegexp)));
      break;
      if (TextUtils.isEmpty(str)) {
        str = paramContext.getString(R.string.wallet_uic_error_email_address_invalid);
      }
      localObject = new PatternValidator(str, Patterns.EMAIL_ADDRESS);
      setInputType(33);
      break;
      setImeOptions(0x10000000 | 0x2000000 | getImeOptions());
    }
  }
  
  public final void addOnAutoAdvanceListener(OnAutoAdvanceListener paramOnAutoAdvanceListener)
  {
    if (this.mAutoAdvanceListener != null) {
      this.mAutoAdvanceListener.mOnAutoAdvanceListenerList.add(paramOnAutoAdvanceListener);
    }
  }
  
  public final void addOnTextChangeValidator(AbstractValidator paramAbstractValidator)
  {
    addValidator(paramAbstractValidator);
    this.mTextChangedValidator.add(paramAbstractValidator);
  }
  
  public void addTextChangedListener(TextWatcher paramTextWatcher)
  {
    getTextWatcherList().addLast(paramTextWatcher);
  }
  
  public final void addTextChangedListenerToFront(TextWatcher paramTextWatcher)
  {
    getTextWatcherList().addFirst(paramTextWatcher);
  }
  
  public final void addTriggers(ArrayList<DependencyGraphOuterClass.TriggerValueReference> paramArrayList)
  {
    int i = 0;
    int j = paramArrayList.size();
    while (i < j)
    {
      DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = (DependencyGraphOuterClass.TriggerValueReference)paramArrayList.get(i);
      if (localTriggerValueReference.triggerType == 1)
      {
        this.mUserInputOnlyTextWatcherList.add(new ValueChangedTriggerTextWatcher(this, localTriggerValueReference, this.mTriggerListener));
        i++;
      }
      else
      {
        throw new IllegalArgumentException("Unsupported trigger type: " + localTriggerValueReference.triggerType);
      }
    }
  }
  
  public final void addValidator(AbstractValidator paramAbstractValidator)
  {
    this.mFocusChangeAndValidateableValidator.add(paramAbstractValidator);
  }
  
  @TargetApi(16)
  final void announceError()
  {
    if ((Build.VERSION.SDK_INT >= 16) && (AndroidUtils.isAccessibilityEnabled(getContext()))) {
      announceForAccessibility(getErrorTextForAccessibility());
    }
  }
  
  public final void applyResultingAction(DependencyGraphOuterClass.ResultingActionReference paramResultingActionReference, DependencyGraphOuterClass.TriggerValueReference[] paramArrayOfTriggerValueReference)
  {
    switch (paramResultingActionReference.actionType)
    {
    default: 
      throw new IllegalArgumentException("Unknown ResultingActionReference action type " + paramResultingActionReference.actionType);
    case 2: 
      setValue(null, true);
      return;
    }
    throw new IllegalArgumentException("FormEditText doesn't support ResultingActionReference action type SHOW_COMPONENT");
  }
  
  public void beginBatchEdit()
  {
    if (getError() != null) {
      setError(getError());
    }
    super.beginBatchEdit();
  }
  
  public final boolean checkTrigger(DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference)
  {
    if (paramTriggerValueReference.triggerType == 1) {
      return false;
    }
    throw new IllegalArgumentException("Unsupported trigger type: " + paramTriggerValueReference.triggerType);
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    if ((Build.VERSION.SDK_INT < 16) && (paramAccessibilityEvent.getEventType() == 8) && (getError() != null)) {
      paramAccessibilityEvent.setContentDescription(getErrorTextForAccessibility());
    }
    return true;
  }
  
  public final void enableAutoAdvance(Completable paramCompletable, FieldValidatable paramFieldValidatable, boolean paramBoolean)
  {
    if ((this.mAutoAdvanceListener != null) && (this.mAutoAdvanceCompletable == paramCompletable) && (this.mAutoAdvanceFieldValidatable == paramFieldValidatable)) {
      return;
    }
    this.mAutoAdvanceListener = new EditTextAutoAdvanceListener(this, paramCompletable, paramFieldValidatable);
    this.mAutoRetreat = paramBoolean;
    this.mAutoAdvanceCompletable = paramCompletable;
    this.mAutoAdvanceFieldValidatable = paramFieldValidatable;
  }
  
  public boolean enoughToFilter()
  {
    if (this.mBlockCompletion) {}
    while (getText().length() < this.mCachedThreshold) {
      return false;
    }
    return true;
  }
  
  String getAccessibilityAnnouncingContent()
  {
    String str = "";
    Editable localEditable;
    if (!TextUtils.isEmpty(getHint()))
    {
      str = getHint().toString();
      localEditable = getText();
      if ((TextUtils.isEmpty(localEditable)) || (TextUtils.isEmpty(str))) {
        break label91;
      }
      str = getResources().getString(R.string.wallet_uic_accessibility_event_form_field_text, new Object[] { str, localEditable });
    }
    label91:
    while (TextUtils.isEmpty(localEditable))
    {
      return str;
      if (TextUtils.isEmpty(getContentDescription())) {
        break;
      }
      str = getContentDescription().toString();
      break;
    }
    return localEditable.toString();
  }
  
  public int getCursorPositionInValue()
  {
    int i = getSelectionStart();
    if (this.mTemplate == null) {
      return i;
    }
    int j = Collections.binarySearch(this.mTemplateInputIndices, Integer.valueOf(i));
    if (j >= 0) {
      return j;
    }
    return -1 + -j;
  }
  
  public CharSequence getError()
  {
    if (this.mErrorHandler != null) {
      return this.mErrorHandler.getError();
    }
    return super.getError();
  }
  
  String getErrorTextForAccessibility()
  {
    if (TextUtils.isEmpty(getHint())) {}
    for (CharSequence localCharSequence = getContentDescription();; localCharSequence = getHint())
    {
      Resources localResources = getResources();
      int i = R.string.wallet_uic_accessibility_event_form_field_error;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = localCharSequence;
      arrayOfObject[1] = getError();
      return localResources.getString(i, arrayOfObject);
    }
  }
  
  public void getFocusedRect(Rect paramRect)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      super.getFocusedRect(paramRect);
      return;
    }
    Layout localLayout = getLayout();
    if (localLayout == null)
    {
      super.getFocusedRect(paramRect);
      return;
    }
    int i = localLayout.getLineForOffset(0);
    paramRect.top = localLayout.getLineTop(i);
    paramRect.bottom = localLayout.getLineBottom(i);
    paramRect.left = ((int)localLayout.getPrimaryHorizontal(0));
    paramRect.right = (1 + paramRect.left);
    paramRect.offset(getCompoundPaddingLeft(), getExtendedPaddingTop());
  }
  
  public CharSequence getHint()
  {
    CharSequence localCharSequence = super.getHint();
    if ((!TextUtils.isEmpty(localCharSequence)) || (this.mHintHandler == null)) {
      return localCharSequence;
    }
    return this.mHintHandler.getHint();
  }
  
  public String getIncompleteErrorMessage()
  {
    return getContext().getString(R.string.wallet_uic_error_field_must_be_complete);
  }
  
  public int getMaxFieldLength()
  {
    return this.mMaxFieldLength;
  }
  
  public String getRequiredError()
  {
    return this.mRequiredError;
  }
  
  public int getThreshold()
  {
    return this.mCachedThreshold;
  }
  
  public String getValue()
  {
    String str = getText().toString();
    if (this.mTemplate == null) {
      return str;
    }
    if (str.equals(this.mCachedText)) {
      return this.mCachedValue;
    }
    if ((this.mShowTemplate) && (str.isEmpty())) {
      str = this.mTemplate;
    }
    this.mCachedText = str;
    int i = this.mTemplateInputIndices.size();
    StringBuilder localStringBuilder = new StringBuilder(i);
    int j = this.mCachedText.length();
    for (int k = 0; (k < i) && (((Integer)this.mTemplateInputIndices.get(k)).intValue() < j); k++) {
      localStringBuilder.append(this.mCachedText.charAt(((Integer)this.mTemplateInputIndices.get(k)).intValue()));
    }
    this.mCachedValue = localStringBuilder.toString();
    return this.mCachedValue;
  }
  
  public int getValueLength()
  {
    Editable localEditable = getText();
    if (this.mTemplate == null)
    {
      k = TextUtils.getTrimmedLength(localEditable);
      return k;
    }
    if (localEditable.length() == 0) {
      return 0;
    }
    int i = this.mTemplateInputIndices.size();
    int j = localEditable.length();
    for (int k = 0;; k++)
    {
      if (k >= i) {
        return i;
      }
      int m = ((Integer)this.mTemplateInputIndices.get(k)).intValue();
      if ((m >= j) || (localEditable.charAt(m) == this.mTemplate.charAt(m))) {
        break;
      }
    }
    return i;
  }
  
  public boolean isComplete()
  {
    if (this.mTemplate != null) {
      if (getValueLength() < this.mTemplateInputIndices.size()) {}
    }
    while ((this.mMaxFieldLength >= 0) && (getValueLength() >= this.mMaxFieldLength))
    {
      return true;
      return false;
    }
    return false;
  }
  
  public final boolean isValid()
  {
    return ((!this.mValidateWhenNotVisible) && (getVisibility() != 0)) || (this.mFocusChangeAndValidateableValidator.isValid(this));
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    InputConnection localInputConnection = super.onCreateInputConnection(paramEditorInfo);
    if (localInputConnection != null) {
      return new FormEditTextInputConnection(localInputConnection);
    }
    return null;
  }
  
  @TargetApi(16)
  public void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    triggerAutocompleteSuggestionsIfPossible();
    if ((!paramBoolean) && (getError() == null) && (this.mOutOfFocusFieldValidatable != null)) {
      this.mOutOfFocusFieldValidatable.validate();
    }
    if ((paramBoolean) && (getError() != null)) {
      announceError();
    }
    if (this.mFocusChangeListenerList != null)
    {
      Iterator localIterator = this.mFocusChangeListenerList.iterator();
      while (localIterator.hasNext()) {
        ((View.OnFocusChangeListener)localIterator.next()).onFocusChange(this, paramBoolean);
      }
    }
  }
  
  @TargetApi(21)
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setText(getAccessibilityAnnouncingContent());
    if (Build.VERSION.SDK_INT >= 21) {
      paramAccessibilityNodeInfo.setError(null);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if ((TextUtils.isEmpty(super.getHint())) && (this.mHintHandler != null)) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        setHint(this.mHintHandler.getHint());
      }
      super.onMeasure(paramInt1, paramInt2);
      if (i != 0) {
        setHint(null);
      }
      setThreshold(this.mCachedThreshold);
      return;
    }
  }
  
  public void onPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onPopulateAccessibilityEvent(paramAccessibilityEvent);
    if (((Build.VERSION.SDK_INT >= 16) && (paramAccessibilityEvent.getEventType() == 32768)) || ((Build.VERSION.SDK_INT < 16) && (paramAccessibilityEvent.getEventType() == 8)))
    {
      List localList = paramAccessibilityEvent.getText();
      if (!localList.isEmpty()) {
        localList.remove(0);
      }
      localList.add(0, getAccessibilityAnnouncingContent());
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof Bundle))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    Bundle localBundle = (Bundle)paramParcelable;
    FormattingSchemesOuterClass.TemplateFormattingScheme localTemplateFormattingScheme = (FormattingSchemesOuterClass.TemplateFormattingScheme)ParcelableProto.getProtoFromBundle(localBundle, "templateSpec");
    if (localTemplateFormattingScheme != this.mTemplateSpec) {
      setTemplateFormattingScheme(localTemplateFormattingScheme);
    }
    boolean bool = this.mUserInputOnlyTextWatchersEnabled;
    this.mUserInputOnlyTextWatchersEnabled = false;
    super.onRestoreInstanceState(localBundle.getParcelable("superSavedInstanceState"));
    this.mUserInputOnlyTextWatchersEnabled = bool;
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("superSavedInstanceState", super.onSaveInstanceState());
    localBundle.putParcelable("templateSpec", ParcelableProto.forProto(this.mTemplateSpec));
    return localBundle;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!hasFocus()) && (TextUtils.isEmpty(getText()))) {}
    for (int i = 1;; i = 0)
    {
      boolean bool = super.onTouchEvent(paramMotionEvent);
      if ((this.mTemplate != null) && (i != 0) && (hasFocus())) {
        setSelection(((Integer)this.mTemplateInputIndices.get(0)).intValue());
      }
      return bool;
    }
  }
  
  public final void removeOnAutoAdvanceListener(OnAutoAdvanceListener paramOnAutoAdvanceListener)
  {
    if (this.mAutoAdvanceListener != null) {
      this.mAutoAdvanceListener.mOnAutoAdvanceListenerList.remove(paramOnAutoAdvanceListener);
    }
  }
  
  public void removeTextChangedListener(TextWatcher paramTextWatcher)
  {
    getTextWatcherList().remove(paramTextWatcher);
  }
  
  public final void removeValidator(AbstractValidator paramAbstractValidator)
  {
    this.mFocusChangeAndValidateableValidator.remove(paramAbstractValidator);
    this.mTextChangedValidator.remove(paramAbstractValidator);
  }
  
  public final void replaceTextAndPreventFilter(CharSequence paramCharSequence, boolean paramBoolean)
  {
    this.mBlockCompletion = true;
    if (isFocused()) {
      if (paramBoolean) {
        replaceText(paramCharSequence);
      }
    }
    for (;;)
    {
      this.mBlockCompletion = false;
      return;
      boolean bool = this.mUserInputOnlyTextWatchersEnabled;
      this.mUserInputOnlyTextWatchersEnabled = false;
      replaceText(paramCharSequence);
      this.mUserInputOnlyTextWatchersEnabled = bool;
      continue;
      if (paramBoolean) {
        setText(paramCharSequence);
      } else {
        setTextAndPreventUserInputOnlyTextWatchers(paramCharSequence);
      }
    }
  }
  
  public void setCursorPositionInValue(int paramInt)
  {
    if (this.mTemplate == null) {}
    for (;;)
    {
      setSelection(paramInt);
      return;
      if (TextUtils.isEmpty(getText())) {
        paramInt = 0;
      } else if (paramInt < this.mTemplateInputIndices.size()) {
        paramInt = Math.min(((Integer)this.mTemplateInputIndices.get(paramInt)).intValue(), getText().length());
      } else {
        paramInt = Math.min(1 + ((Integer)this.mTemplateInputIndices.get(-1 + this.mTemplateInputIndices.size())).intValue(), getText().length());
      }
    }
  }
  
  public void setError(CharSequence paramCharSequence, Drawable paramDrawable)
  {
    if ((Build.VERSION.SDK_INT < 11) && (!TextUtils.isEmpty(paramCharSequence)))
    {
      Object localObject;
      if ((paramCharSequence instanceof Spannable))
      {
        localObject = (Spannable)paramCharSequence;
        Object[] arrayOfObject = ((Spannable)localObject).getSpans(0, ((Spannable)localObject).length(), Object.class);
        if ((arrayOfObject != null) && (arrayOfObject.length != 0)) {
          throw new UnsupportedOperationException("Spans in error text are not supported due to Gingerbread limitations.");
        }
      }
      else
      {
        localObject = new SpannableString(paramCharSequence);
      }
      ((Spannable)localObject).setSpan(new ForegroundColorSpan(-16777216), 0, ((Spannable)localObject).length(), 0);
      paramCharSequence = (CharSequence)localObject;
    }
    if (this.mErrorHandler != null)
    {
      this.mErrorHandler.setError(paramCharSequence);
      return;
    }
    super.setError(paramCharSequence, paramDrawable);
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.mErrorHandler = paramErrorHandler;
  }
  
  public void setHintHandler(HintHandler paramHintHandler)
  {
    this.mHintHandler = paramHintHandler;
  }
  
  public void setMinMaxLength(int paramInt1, int paramInt2)
  {
    if (this.mTemplate != null)
    {
      if (paramInt2 != this.mTemplateInputIndices.size()) {
        throw new IllegalArgumentException("maxLength (" + paramInt2 + ") must be equal to the number of input placeholder characters in the template (" + this.mTemplateInputIndices.size() + ")");
      }
    }
    else if (paramInt2 > 0)
    {
      InputFilter[] arrayOfInputFilter = new InputFilter[1];
      arrayOfInputFilter[0] = new InputFilter.LengthFilter(paramInt2);
      setFilters(arrayOfInputFilter);
      this.mMaxFieldLength = paramInt2;
      enableAutoAdvance(this, this, false);
    }
    if (this.mMinLengthValidator != null) {
      removeValidator(this.mMinLengthValidator);
    }
    if (paramInt1 > 0)
    {
      this.mMinLengthValidator = new InputLengthValidator(paramInt1)
      {
        protected final boolean isRequired()
        {
          return FormEditText.this.mRequired;
        }
      };
      addValidator(this.mMinLengthValidator);
    }
  }
  
  public void setOnErrorChangeListener(OnErrorListener paramOnErrorListener)
  {
    this.mOnErrorlistener = paramOnErrorListener;
  }
  
  public void setOnFocusChangeListener(View.OnFocusChangeListener paramOnFocusChangeListener)
  {
    if (paramOnFocusChangeListener != null) {
      getFocusChangeList().add(paramOnFocusChangeListener);
    }
  }
  
  public void setOnOutOfFocusValidatable(FieldValidatable paramFieldValidatable)
  {
    this.mOutOfFocusFieldValidatable = paramFieldValidatable;
  }
  
  public void setRequired(boolean paramBoolean)
  {
    this.mRequired = paramBoolean;
    onRequiredChanged(false);
  }
  
  public void setRequiredError(String paramString)
  {
    this.mRequiredError = paramString;
    onRequiredChanged(true);
  }
  
  public void setShouldValidateWhenNotVisible(boolean paramBoolean)
  {
    this.mValidateWhenNotVisible = paramBoolean;
  }
  
  public void setTemplateFormattingScheme(FormattingSchemesOuterClass.TemplateFormattingScheme paramTemplateFormattingScheme)
  {
    if (this.mTemplateSpec == paramTemplateFormattingScheme) {
      return;
    }
    if (this.mMaxFieldLength >= 0) {
      throw new IllegalStateException("Max length cannot be set before a template.");
    }
    String str = getValue();
    int i = getCursorPositionInValue();
    if ((this.mTemplateSpec == null) && (paramTemplateFormattingScheme != null)) {
      this.mUserInputOnlyTextWatcherList.addFirst(this.mTemplateTextWatcher);
    }
    for (;;)
    {
      this.mTemplateSpec = paramTemplateFormattingScheme;
      this.mAutoAdvanceListener = null;
      this.mAutoRetreat = false;
      this.mTemplateInputIndices.clear();
      if (this.mMinLengthValidator != null) {
        removeValidator(this.mMinLengthValidator);
      }
      this.mCachedText = null;
      this.mCachedValue = null;
      if (paramTemplateFormattingScheme != null) {
        break;
      }
      this.mTemplate = null;
      setValue(str, false);
      setSelection(i);
      return;
      if ((this.mTemplateSpec != null) && (paramTemplateFormattingScheme == null)) {
        this.mUserInputOnlyTextWatcherList.remove(this.mTemplateTextWatcher);
      }
    }
    this.mShowTemplate = paramTemplateFormattingScheme.showTemplate;
    int j = paramTemplateFormattingScheme.inputCharacter.length;
    char[] arrayOfChar1 = new char[j];
    char[] arrayOfChar2 = new char[j];
    int k = 0;
    if (k < j)
    {
      arrayOfChar1[k] = paramTemplateFormattingScheme.inputCharacter[k].placeholderCharacter.charAt(0);
      if (this.mShowTemplate) {}
      for (int i2 = paramTemplateFormattingScheme.inputCharacter[k].displayCharacter.charAt(0);; i2 = 126)
      {
        arrayOfChar2[k] = i2;
        k++;
        break;
      }
    }
    char[] arrayOfChar3 = paramTemplateFormattingScheme.template.toCharArray();
    int m = 0;
    int n = arrayOfChar3.length;
    if (m < n) {
      for (int i1 = 0;; i1++) {
        if (i1 < j)
        {
          if (arrayOfChar3[m] == arrayOfChar1[i1])
          {
            arrayOfChar3[m] = arrayOfChar2[i1];
            this.mTemplateInputIndices.add(Integer.valueOf(m));
          }
        }
        else
        {
          m++;
          break;
        }
      }
    }
    if (this.mTemplateInputIndices.isEmpty()) {
      throw new IllegalArgumentException("The template formatting scheme must contain at least one input character");
    }
    this.mTemplate = String.valueOf(arrayOfChar3);
    setValue(str, false);
    if (hasFocus()) {
      setCursorPositionInValue(i);
    }
    enableAutoAdvance(this, this, false);
    this.mMinLengthValidator = new InputLengthValidator(this.mTemplateInputIndices.size())
    {
      protected final boolean isRequired()
      {
        return FormEditText.this.mRequired;
      }
    };
    addValidator(this.mMinLengthValidator);
  }
  
  public void setTextAndPreventUserInputOnlyTextWatchers(CharSequence paramCharSequence)
  {
    boolean bool = this.mUserInputOnlyTextWatchersEnabled;
    this.mUserInputOnlyTextWatchersEnabled = false;
    setText(paramCharSequence);
    this.mUserInputOnlyTextWatchersEnabled = bool;
  }
  
  public void setThreshold(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    Rect localRect = new Rect();
    getWindowVisibleDisplayFrame(localRect);
    int i = (int)getResources().getDisplayMetrics().density;
    if ((i != 0) && (localRect.height() / i > 140)) {}
    for (int j = 1; j != 0; j = 0)
    {
      this.mCachedThreshold = paramInt;
      return;
    }
    this.mCachedThreshold = 2147483647;
    dismissDropDown();
  }
  
  public void setTriggerListener(TriggerListener paramTriggerListener)
  {
    this.mTriggerListener = paramTriggerListener;
  }
  
  public final void setValue(CharSequence paramCharSequence, boolean paramBoolean)
  {
    if (this.mTemplate == null) {
      if (paramBoolean) {
        setText(paramCharSequence);
      }
    }
    do
    {
      return;
      setTextAndPreventUserInputOnlyTextWatchers(paramCharSequence);
      return;
      if (!TextUtils.isEmpty(paramCharSequence)) {
        break;
      }
    } while (TextUtils.isEmpty(getText()));
    if (paramBoolean)
    {
      setText(null);
      return;
    }
    setTextAndPreventUserInputOnlyTextWatchers(null);
    return;
    char[] arrayOfChar = this.mTemplate.toCharArray();
    int i = 0;
    int j = 0;
    int k = this.mTemplateInputIndices.size();
    String str;
    for (;;)
    {
      if (j < k)
      {
        if (j < paramCharSequence.length()) {
          break label149;
        }
        if (!this.mShowTemplate) {
          arrayOfChar = Arrays.copyOf(arrayOfChar, ((Integer)this.mTemplateInputIndices.get(j)).intValue());
        }
      }
      str = null;
      if (i != 0) {
        str = String.valueOf(arrayOfChar);
      }
      if (!paramBoolean) {
        break;
      }
      setText(str);
      return;
      label149:
      if (arrayOfChar[((Integer)this.mTemplateInputIndices.get(j)).intValue()] != paramCharSequence.charAt(j))
      {
        i = 1;
        arrayOfChar[((Integer)this.mTemplateInputIndices.get(j)).intValue()] = paramCharSequence.charAt(j);
      }
      j++;
    }
    setTextAndPreventUserInputOnlyTextWatchers(str);
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if (this.mVisibilityMatchingView != null) {
      this.mVisibilityMatchingView.setVisibility(paramInt);
    }
  }
  
  public void setVisibilityMatchingView(View paramView)
  {
    this.mVisibilityMatchingView = paramView;
  }
  
  public final boolean triggerAutocompleteSuggestionsIfPossible()
  {
    boolean bool1 = hasFocus();
    boolean bool2 = false;
    if (bool1)
    {
      boolean bool3 = enoughToFilter();
      bool2 = false;
      if (bool3)
      {
        int i = getWindowVisibility();
        bool2 = false;
        if (i != 8)
        {
          ListAdapter localListAdapter = getAdapter();
          bool2 = false;
          if (localListAdapter != null)
          {
            performFiltering(getText(), 0);
            showDropDown();
            bool2 = true;
          }
        }
      }
    }
    return bool2;
  }
  
  public final boolean validate()
  {
    boolean bool = isValid();
    if ((!bool) && (this.mFocusChangeAndValidateableValidator.getErrorMessage() != null)) {
      setError(this.mFocusChangeAndValidateableValidator.getErrorMessage());
    }
    while (getError() == null) {
      return bool;
    }
    setError(null);
    return bool;
  }
  
  private final class FormEditTextInputConnection
    extends InputConnectionWrapper
  {
    public FormEditTextInputConnection(InputConnection paramInputConnection)
    {
      super(true);
    }
    
    public final boolean commitText(CharSequence paramCharSequence, int paramInt)
    {
      FormEditText.access$102(FormEditText.this, FormEditText.this.getError());
      boolean bool = super.commitText(paramCharSequence, paramInt);
      if (FormEditText.this.mSavedError != null) {
        FormEditText.this.setError(FormEditText.this.mSavedError);
      }
      return bool;
    }
    
    public final boolean deleteSurroundingText(int paramInt1, int paramInt2)
    {
      if ((FormEditText.this.mAutoRetreat) && (paramInt1 == 1) && (paramInt2 == 0) && (FormEditText.this.getValueLength() == 0) && ((!AndroidUtils.isAccessibilityEnabled(FormEditText.this.getContext())) || (FormEditText.this.mAutoAdvanceFieldValidatable.validate()))) {
        if (Build.VERSION.SDK_INT < 14) {
          break label90;
        }
      }
      label90:
      for (View localView = FormEditText.this.focusSearch(1);; localView = FormEditText.this.focusSearch(33))
      {
        if (localView != null) {
          localView.requestFocus();
        }
        return super.deleteSurroundingText(paramInt1, paramInt2);
      }
    }
  }
  
  public static abstract interface OnErrorListener {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.FormEditText
 * JD-Core Version:    0.7.0.1
 */