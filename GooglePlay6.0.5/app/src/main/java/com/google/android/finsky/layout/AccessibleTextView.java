package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.google.android.play.utils.PlayAccessibilityHelper;
import java.util.List;

public class AccessibleTextView
  extends TextView
{
  private final String mAccessibilityClassNameOverride;
  
  public AccessibleTextView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AccessibleTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AccessibleTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, com.android.vending.R.styleable.AccessibleTextView);
    this.mAccessibilityClassNameOverride = localTypedArray.getString(com.google.android.play.R.styleable.PlayAccessibilityHelper_playAccessibilityClassOverride);
    localTypedArray.recycle();
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    if (!isShown()) {}
    CharSequence localCharSequence;
    do
    {
      return false;
      localCharSequence = getText();
      if (TextUtils.isEmpty(localCharSequence)) {
        localCharSequence = getHint();
      }
    } while (TextUtils.isEmpty(localCharSequence));
    if (localCharSequence.length() > 500) {
      localCharSequence = localCharSequence.subSequence(0, 501);
    }
    paramAccessibilityEvent.getText().add(localCharSequence.toString().toLowerCase());
    return false;
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    PlayAccessibilityHelper.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo, this.mAccessibilityClassNameOverride);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.AccessibleTextView
 * JD-Core Version:    0.7.0.1
 */