package com.google.android.wallet.ui.common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.string;
import java.util.List;

public class FormSpinner
  extends AppCompatSpinner
  implements AdapterView.OnItemSelectedListener, FieldValidatable
{
  final ErrorHandler DEFAULT_ERROR_HANDLER = new DefaultErrorHandler(this);
  private ErrorHandler mErrorHandler = this.DEFAULT_ERROR_HANDLER;
  private String mLabel;
  private AdapterView.OnItemSelectedListener mOnItemSelectedListener;
  private boolean mPotentialErrorOnConfigChange;
  private boolean mRequired = true;
  public boolean mValidateWhenNotVisible = false;
  private boolean mViewLaidOutOnceAfterAdapterSet = false;
  private View mVisibilityMatchingView;
  
  public FormSpinner(Context paramContext)
  {
    super(paramContext);
    readAttrsAndInitializeListener(paramContext);
  }
  
  public FormSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttrsAndInitializeListener(paramContext);
  }
  
  public FormSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttrsAndInitializeListener(paramContext);
  }
  
  private void readAttrsAndInitializeListener(Context paramContext)
  {
    super.setOnItemSelectedListener(this);
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.internalUicValidateFieldsWhenNotVisible;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(arrayOfInt);
    this.mValidateWhenNotVisible = localTypedArray.getBoolean(0, false);
    localTypedArray.recycle();
  }
  
  public final void announceError()
  {
    WalletUiUtils.announceForAccessibility(this, getErrorTextForAccessibility());
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    if ((Build.VERSION.SDK_INT < 16) && (paramAccessibilityEvent.getEventType() == 8) && (getError() != null)) {
      paramAccessibilityEvent.getText().add(getErrorTextForAccessibility());
    }
    return true;
  }
  
  public CharSequence getError()
  {
    return this.mErrorHandler.getError();
  }
  
  String getErrorTextForAccessibility()
  {
    Resources localResources = getResources();
    int i = R.string.wallet_uic_accessibility_event_form_field_error;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mLabel;
    arrayOfObject[1] = getError();
    return localResources.getString(i, arrayOfObject);
  }
  
  public boolean isValid()
  {
    boolean bool;
    if ((!this.mRequired) || ((!this.mValidateWhenNotVisible) && (getVisibility() != 0))) {
      bool = true;
    }
    SpinnerAdapter localSpinnerAdapter;
    int i;
    do
    {
      do
      {
        return bool;
        localSpinnerAdapter = getAdapter();
        bool = false;
      } while (localSpinnerAdapter == null);
      i = getSelectedItemPosition();
      bool = false;
    } while (i == -1);
    if ((localSpinnerAdapter instanceof ListAdapter)) {
      return ((ListAdapter)localSpinnerAdapter).isEnabled(i);
    }
    return true;
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    if ((paramBoolean) && (getError() != null)) {
      announceError();
    }
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
  }
  
  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (isValid()) {
      validate();
    }
    if (this.mOnItemSelectedListener != null) {
      this.mOnItemSelectedListener.onItemSelected(paramAdapterView, paramView, paramInt, paramLong);
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((!this.mViewLaidOutOnceAfterAdapterSet) && (getAdapter() != null))
    {
      this.mViewLaidOutOnceAfterAdapterSet = true;
      if (this.mPotentialErrorOnConfigChange) {
        validate();
      }
    }
  }
  
  public void onNothingSelected(AdapterView<?> paramAdapterView)
  {
    if (isValid()) {
      validate();
    }
    if (this.mOnItemSelectedListener != null) {
      this.mOnItemSelectedListener.onNothingSelected(paramAdapterView);
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof Bundle)) {
      super.onRestoreInstanceState(paramParcelable);
    }
    int i;
    do
    {
      return;
      Bundle localBundle = (Bundle)paramParcelable;
      super.onRestoreInstanceState(localBundle.getParcelable("superSavedInstanceState"));
      this.mPotentialErrorOnConfigChange = localBundle.getBoolean("potentialErrorOnConfigChange");
      i = localBundle.getInt("selectedItemPosition");
    } while (getSelectedItemPosition() == i);
    setSelection(i);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("superSavedInstanceState", super.onSaveInstanceState());
    localBundle.putBoolean("potentialErrorOnConfigChange", this.mPotentialErrorOnConfigChange);
    localBundle.putInt("selectedItemPosition", getSelectedItemPosition());
    return localBundle;
  }
  
  public void setError(CharSequence paramCharSequence)
  {
    this.mErrorHandler.setError(paramCharSequence);
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.mErrorHandler = paramErrorHandler;
  }
  
  public void setLabel(String paramString)
  {
    this.mLabel = paramString;
  }
  
  public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener paramOnItemSelectedListener)
  {
    this.mOnItemSelectedListener = paramOnItemSelectedListener;
  }
  
  public void setRequired(boolean paramBoolean)
  {
    this.mRequired = paramBoolean;
  }
  
  public void setShouldValidateWhenNotVisible(boolean paramBoolean)
  {
    this.mValidateWhenNotVisible = paramBoolean;
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
  
  public final boolean validate()
  {
    if ((getAdapter() == null) || (getAdapter().isEmpty())) {
      throw new IllegalStateException("Must set non-empty adapter before validating");
    }
    this.mPotentialErrorOnConfigChange = true;
    if (isValid())
    {
      this.mErrorHandler.setError(null);
      return true;
    }
    this.mErrorHandler.setError(getContext().getString(R.string.wallet_uic_error_field_must_not_be_empty));
    return false;
  }
  
  static final class DefaultErrorHandler
    implements ErrorHandler
  {
    private FormSpinner mFormSpinner;
    
    DefaultErrorHandler(FormSpinner paramFormSpinner)
    {
      this.mFormSpinner = paramFormSpinner;
    }
    
    public final CharSequence getError()
    {
      View localView = this.mFormSpinner.getSelectedView();
      if ((localView instanceof TextView)) {
        return ((TextView)localView).getError();
      }
      return null;
    }
    
    public final void setError(CharSequence paramCharSequence)
    {
      View localView = this.mFormSpinner.getSelectedView();
      if ((localView instanceof TextView))
      {
        ((TextView)localView).setError(paramCharSequence);
        return;
      }
      Log.w("FormSpinner", "Cannot set error on view: " + localView);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.FormSpinner
 * JD-Core Version:    0.7.0.1
 */