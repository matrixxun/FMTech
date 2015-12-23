package com.google.android.play.drawer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.google.android.play.R.color;
import com.google.android.play.R.id;

class PlayDrawerDownloadSwitchRow
  extends RelativeLayout
  implements View.OnClickListener, Checkable, CompoundButton.OnCheckedChangeListener
{
  static final boolean SUPPORTS_STYLED_SWITCH;
  TextView mActionTextView;
  private boolean mBroadcasting;
  private boolean mChecked;
  int mCheckedTextColor;
  OnCheckedChangeListener mListener;
  Switch mSwitch;
  private final View.OnTouchListener mSwitchTouchListener = new View.OnTouchListener()
  {
    public final boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
    {
      if (paramAnonymousMotionEvent.getActionMasked() == 0) {
        paramAnonymousView.getParent().requestDisallowInterceptTouchEvent(true);
      }
      return false;
    }
  };
  private int mUncheckedTextColor = getResources().getColor(R.color.play_fg_primary);
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (boolean bool = true;; bool = false)
    {
      SUPPORTS_STYLED_SWITCH = bool;
      return;
    }
  }
  
  public PlayDrawerDownloadSwitchRow(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayDrawerDownloadSwitchRow(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean isChecked()
  {
    return this.mChecked;
  }
  
  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    if (paramBoolean != this.mChecked) {
      setChecked(paramBoolean);
    }
  }
  
  public void onClick(View paramView)
  {
    toggle();
  }
  
  protected int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (this.mChecked) {
      mergeDrawableStates(arrayOfInt, new int[] { 16843014 });
    }
    return arrayOfInt;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mActionTextView = ((TextView)findViewById(R.id.action_text));
    setOnClickListener(this);
    View localView = findViewById(R.id.switch_button);
    if (localView != null)
    {
      this.mSwitch = ((Switch)localView);
      this.mSwitch.setOnCheckedChangeListener(this);
      this.mSwitch.setOnTouchListener(this.mSwitchTouchListener);
    }
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(PlayDrawerDownloadSwitchRow.class.getName());
    paramAccessibilityEvent.setChecked(this.mChecked);
  }
  
  @TargetApi(16)
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName(CheckBox.class.getName());
    paramAccessibilityNodeInfo.setCheckable(true);
    paramAccessibilityNodeInfo.setChecked(this.mChecked);
  }
  
  @SuppressLint({"NewApi"})
  public void setChecked(boolean paramBoolean)
  {
    if (this.mChecked != paramBoolean)
    {
      setCheckedNoCallbacks(paramBoolean);
      if (!this.mBroadcasting) {}
    }
    else
    {
      return;
    }
    if (Build.VERSION.SDK_INT >= 14) {
      sendAccessibilityEvent(2048);
    }
    this.mBroadcasting = true;
    if (this.mListener != null) {
      this.mListener.onCheckedChanged$77cff6e2(paramBoolean);
    }
    this.mBroadcasting = false;
  }
  
  @TargetApi(14)
  public final void setCheckedNoCallbacks(boolean paramBoolean)
  {
    this.mChecked = paramBoolean;
    if ((SUPPORTS_STYLED_SWITCH) && (this.mSwitch != null))
    {
      this.mSwitch.setChecked(paramBoolean);
      this.mSwitch.refreshDrawableState();
    }
    TextView localTextView = this.mActionTextView;
    if (paramBoolean) {}
    for (int i = this.mCheckedTextColor;; i = this.mUncheckedTextColor)
    {
      localTextView.setTextColor(i);
      return;
    }
  }
  
  public void toggle()
  {
    if (!this.mChecked) {}
    for (boolean bool = true;; bool = false)
    {
      setChecked(bool);
      return;
    }
  }
  
  public static abstract interface OnCheckedChangeListener
  {
    public abstract void onCheckedChanged$77cff6e2(boolean paramBoolean);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.drawer.PlayDrawerDownloadSwitchRow
 * JD-Core Version:    0.7.0.1
 */