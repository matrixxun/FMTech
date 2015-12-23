package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.android.wallet.uicomponents.R.id;

public class InlineSelectView
  extends LinearLayout
  implements View.OnClickListener, FieldValidatable
{
  ChildRowAccessibilityDelegate mChildRowAccessibilityDelegate = new ChildRowAccessibilityDelegate();
  private OnItemSelectedListener mOnItemSelectedListener;
  private boolean mRequired = true;
  private int mSelectedItemIndex = -1;
  
  public InlineSelectView(Context paramContext)
  {
    super(paramContext);
  }
  
  public InlineSelectView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  @TargetApi(11)
  public InlineSelectView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  @TargetApi(21)
  public InlineSelectView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void showSelectionIndicator(int paramInt, boolean paramBoolean)
  {
    View localView;
    if ((paramInt >= 0) && (paramInt < getChildCount()))
    {
      localView = getChildAt(paramInt).findViewById(R.id.selection_indicator);
      if (localView != null) {
        if (!paramBoolean) {
          break label42;
        }
      }
    }
    label42:
    for (int i = 0;; i = 4)
    {
      localView.setVisibility(i);
      return;
    }
  }
  
  public void addView(View paramView)
  {
    int i = getChildCount();
    super.addView(paramView);
    paramView.setTag(Integer.valueOf(i));
    paramView.setOnClickListener(this);
  }
  
  public CharSequence getError()
  {
    return null;
  }
  
  public int getSelectedItemIndex()
  {
    return this.mSelectedItemIndex;
  }
  
  public final boolean isValid()
  {
    return (!this.mRequired) || (this.mSelectedItemIndex >= 0);
  }
  
  public void onClick(View paramView)
  {
    setSelection(((Integer)paramView.getTag()).intValue());
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    Bundle localBundle = (Bundle)paramParcelable;
    super.onRestoreInstanceState(localBundle.getParcelable("superSavedInstanceState"));
    this.mSelectedItemIndex = localBundle.getInt("selectedItemIndex", -1);
    setSelection(this.mSelectedItemIndex);
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("superSavedInstanceState", super.onSaveInstanceState());
    localBundle.putInt("selectedItemIndex", this.mSelectedItemIndex);
    return localBundle;
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      WalletUiUtils.setViewsEnabledRecursive(getChildAt(i), paramBoolean);
      i++;
    }
  }
  
  public void setError(CharSequence paramCharSequence)
  {
    throw new IllegalArgumentException("Errors not supported on InlineSelectView.");
  }
  
  public void setOnItemSelectedListener(OnItemSelectedListener paramOnItemSelectedListener)
  {
    this.mOnItemSelectedListener = paramOnItemSelectedListener;
  }
  
  public void setRequired(boolean paramBoolean)
  {
    this.mRequired = paramBoolean;
  }
  
  public void setSelection(int paramInt)
  {
    showSelectionIndicator(this.mSelectedItemIndex, false);
    this.mSelectedItemIndex = paramInt;
    showSelectionIndicator(this.mSelectedItemIndex, true);
    if (this.mOnItemSelectedListener != null) {
      this.mOnItemSelectedListener.onItemSelected(this.mSelectedItemIndex);
    }
  }
  
  public final boolean validate()
  {
    return isValid();
  }
  
  final class ChildRowAccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    ChildRowAccessibilityDelegate() {}
    
    public final void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      boolean bool = true;
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      Integer localInteger = (Integer)paramView.getTag();
      AccessibilityNodeInfoCompat.IMPL.setCheckable(paramAccessibilityNodeInfoCompat.mInfo, bool);
      if (localInteger.intValue() == InlineSelectView.this.getSelectedItemIndex()) {}
      for (;;)
      {
        AccessibilityNodeInfoCompat.IMPL.setChecked(paramAccessibilityNodeInfoCompat.mInfo, bool);
        return;
        bool = false;
      }
    }
  }
  
  public static abstract interface OnItemSelectedListener
  {
    public abstract void onItemSelected(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.InlineSelectView
 * JD-Core Version:    0.7.0.1
 */