package com.google.android.play.layout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.play.R.color;
import com.google.android.play.R.drawable;
import com.google.android.play.R.styleable;
import com.google.android.play.utils.PlayAccessibilityHelper;
import com.google.android.play.utils.PlayCorpusUtils;

public class PlayActionButton
  extends Button
{
  private int mActionBottomPadding;
  private int mActionStyle;
  private int mActionTopPadding;
  private int mActionXPadding;
  private boolean mDrawAsLabel;
  private int mOriginalBackendId;
  private String mOriginalText;
  private int mPriority;
  private boolean mUseAllCapsInLabelMode;
  
  public PlayActionButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayActionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PlayActionButton);
    this.mDrawAsLabel = localTypedArray.getBoolean(R.styleable.PlayActionButton_draw_as_label, false);
    this.mUseAllCapsInLabelMode = localTypedArray.getBoolean(R.styleable.PlayActionButton_use_all_caps_in_label_mode, true);
    this.mActionStyle = localTypedArray.getInt(R.styleable.PlayActionButton_action_style, 0);
    this.mActionXPadding = localTypedArray.getDimensionPixelSize(R.styleable.PlayActionButton_action_xpadding, 0);
    this.mActionTopPadding = localTypedArray.getDimensionPixelSize(R.styleable.PlayActionButton_action_top_padding, 0);
    this.mActionBottomPadding = localTypedArray.getDimensionPixelSize(R.styleable.PlayActionButton_action_bottom_padding, 0);
    this.mPriority = localTypedArray.getInt(R.styleable.PlayActionButton_local_priority, -1);
    localTypedArray.recycle();
  }
  
  private void syncAppearance()
  {
    String str;
    Context localContext;
    int j;
    label55:
    int k;
    if ((this.mOriginalText == null) || ((this.mDrawAsLabel) && (!this.mUseAllCapsInLabelMode)))
    {
      str = this.mOriginalText;
      setText(str);
      localContext = getContext();
      if (!this.mDrawAsLabel) {
        break label149;
      }
      if (!isClickable()) {
        break label134;
      }
      j = R.drawable.play_highlight_overlay_light;
      setBackgroundResource(j);
      if (this.mActionStyle != 2) {
        break label140;
      }
      k = 0;
      label72:
      setTextColor(PlayCorpusUtils.getPrimaryTextColor(localContext, k));
      if ((!this.mDrawAsLabel) || (isClickable())) {
        break label259;
      }
    }
    label259:
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        break label264;
      }
      ViewCompat.setPaddingRelative(this, this.mActionXPadding, this.mActionTopPadding, this.mActionXPadding, this.mActionBottomPadding);
      return;
      str = this.mOriginalText.toUpperCase();
      break;
      label134:
      j = 0;
      break label55;
      label140:
      k = this.mOriginalBackendId;
      break label72;
      label149:
      switch (this.mActionStyle)
      {
      default: 
        break;
      case 0: 
        setBackgroundResource(PlayCorpusUtils.getPlayActionButtonBackgroundDrawable$1a54e363(this.mOriginalBackendId));
        setTextColor(getResources().getColorStateList(R.color.play_action_button_text));
        break;
      case 1: 
        setBackgroundResource(R.drawable.play_action_button_secondary);
        setTextColor(PlayCorpusUtils.getPrimaryTextColor(localContext, this.mOriginalBackendId));
        break;
      case 2: 
        setBackgroundResource(PlayCorpusUtils.getPlayActionButtonBackgroundSecondaryDrawable$1a54e363(this.mOriginalBackendId));
        setTextColor(PlayCorpusUtils.getPrimaryTextColor(localContext, this.mOriginalBackendId));
        break;
      }
    }
    label264:
    ViewCompat.setPaddingRelative(this, 0, 0, 0, 0);
  }
  
  public final void configure(int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener)
  {
    configure(paramInt1, getResources().getString(paramInt2), paramOnClickListener);
  }
  
  public final void configure(int paramInt, String paramString, View.OnClickListener paramOnClickListener)
  {
    this.mOriginalText = paramString;
    this.mOriginalBackendId = paramInt;
    int i;
    if (paramOnClickListener != null)
    {
      i = 1;
      if (i == 0) {
        break label48;
      }
      setFocusable(true);
      super.setOnClickListener(paramOnClickListener);
      setClickable(true);
    }
    for (;;)
    {
      syncAppearance();
      return;
      i = 0;
      break;
      label48:
      setFocusable(false);
      super.setOnClickListener(null);
      setClickable(false);
    }
  }
  
  public int getPriority()
  {
    return this.mPriority;
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    int i;
    if ((this.mDrawAsLabel) && (!isClickable()))
    {
      i = 1;
      if (i == 0) {
        break label42;
      }
    }
    label42:
    for (String str = TextView.class.getName();; str = null)
    {
      PlayAccessibilityHelper.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo, str);
      return;
      i = 0;
      break;
    }
  }
  
  public final void resetClickListener()
  {
    super.setOnClickListener(null);
    setClickable(false);
    setFocusable(false);
  }
  
  public void setActionStyle(int paramInt)
  {
    if (this.mActionStyle != paramInt)
    {
      this.mActionStyle = paramInt;
      syncAppearance();
    }
  }
  
  public void setDrawAsLabel(boolean paramBoolean)
  {
    if (this.mDrawAsLabel != paramBoolean)
    {
      this.mDrawAsLabel = paramBoolean;
      syncAppearance();
    }
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    Log.e("PlayCommon", "Don't call PlayActionButton.setOnClickListener() directly");
    Log.wtf("PlayCommon", "Don't call PlayActionButton.setOnClickListener() directly");
    throw new UnsupportedOperationException("Call PlayActionButton.configure()");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayActionButton
 * JD-Core Version:    0.7.0.1
 */