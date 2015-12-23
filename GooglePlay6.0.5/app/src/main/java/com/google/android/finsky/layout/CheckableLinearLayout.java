package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import com.android.vending.R.styleable;

public class CheckableLinearLayout
  extends LinearLayout
  implements Checkable
{
  private CheckedTextView mCheckedTextView;
  private final int mResId;
  
  public CheckableLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CheckableLinearLayout);
    this.mResId = localTypedArray.getResourceId(0, 0);
    localTypedArray.recycle();
  }
  
  public boolean isChecked()
  {
    if (this.mCheckedTextView != null) {
      return this.mCheckedTextView.isChecked();
    }
    return false;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    if (this.mResId != 0) {
      this.mCheckedTextView = ((CheckedTextView)findViewById(this.mResId));
    }
  }
  
  public void setChecked(boolean paramBoolean)
  {
    if (this.mCheckedTextView != null) {
      this.mCheckedTextView.setChecked(paramBoolean);
    }
  }
  
  public void toggle()
  {
    if (this.mCheckedTextView != null) {
      this.mCheckedTextView.toggle();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.CheckableLinearLayout
 * JD-Core Version:    0.7.0.1
 */