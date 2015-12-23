package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import com.android.vending.R.styleable;
import com.google.android.play.utils.PlayAccessibilityHelper;
import java.util.List;

public class AccessibleLinearLayout
  extends SeparatorLinearLayout
{
  private final boolean mMarkAsButton;
  
  public AccessibleLinearLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AccessibleLinearLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AccessibleLinearLayout);
    this.mMarkAsButton = localTypedArray.getBoolean(0, false);
    localTypedArray.recycle();
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    boolean bool = super.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.getText().clear();
    return bool;
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    if ((this.mMarkAsButton) && (isClickable())) {
      PlayAccessibilityHelper.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo, Button.class.getName());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AccessibleLinearLayout
 * JD-Core Version:    0.7.0.1
 */