package com.google.android.wallet.ui.common.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.google.android.wallet.ui.common.Completable;
import com.google.android.wallet.ui.common.FieldValidatable;
import com.google.android.wallet.ui.common.OnAutoAdvanceListener;
import com.google.android.wallet.ui.common.WalletUiUtils;
import java.util.ArrayList;
import java.util.List;

public final class EditTextAutoAdvanceListener
  implements TextWatcher, Runnable
{
  private final Completable mCompletable;
  private final EditText mEditText;
  private final FieldValidatable mFieldValidatable;
  private boolean mIsDeleteEvent;
  public final List<OnAutoAdvanceListener> mOnAutoAdvanceListenerList = new ArrayList();
  
  public EditTextAutoAdvanceListener(EditText paramEditText, Completable paramCompletable, FieldValidatable paramFieldValidatable)
  {
    this.mEditText = paramEditText;
    this.mCompletable = paramCompletable;
    this.mFieldValidatable = paramFieldValidatable;
  }
  
  public final void afterTextChanged(Editable paramEditable)
  {
    if (!this.mEditText.isFocused()) {}
    while ((this.mIsDeleteEvent) || (!this.mCompletable.isComplete()) || (!this.mFieldValidatable.validate())) {
      return;
    }
    int i = this.mOnAutoAdvanceListenerList.size();
    for (int j = 0; j < i; j++) {
      ((OnAutoAdvanceListener)this.mOnAutoAdvanceListenerList.get(j)).onAutoAdvance(this.mEditText);
    }
    this.mEditText.post(this);
  }
  
  public final void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public final void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 == 0) {}
    for (boolean bool = true;; bool = false)
    {
      this.mIsDeleteEvent = bool;
      return;
    }
  }
  
  public final void run()
  {
    WalletUiUtils.advanceFocusForForm(this.mEditText);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.listeners.EditTextAutoAdvanceListener
 * JD-Core Version:    0.7.0.1
 */