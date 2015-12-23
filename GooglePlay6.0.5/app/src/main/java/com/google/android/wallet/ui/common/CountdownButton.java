package com.google.android.wallet.ui.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView.BufferType;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.dependencygraph.ResultingActionComponent;
import com.google.android.wallet.dependencygraph.TriggerComponent;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass.Button;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.ResultingActionReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import java.util.ArrayList;
import java.util.Locale;

public class CountdownButton
  extends AppCompatButton
  implements View.OnClickListener, ResultingActionComponent, TriggerComponent, Runnable
{
  ButtonContainerOuterClass.Button mButtonSpec;
  private boolean mCapitalizeButtonText;
  private boolean mIsAttachedToWindow;
  private View.OnClickListener mOnClickListener;
  private boolean mRequestedEnabledState;
  private long mTimeWhenRefreshStartedMs = -1L;
  private TriggerListener mTriggerListener;
  private DependencyGraphOuterClass.TriggerValueReference mTriggerValueReference;
  
  public CountdownButton(Context paramContext)
  {
    super(paramContext);
    initializeAndRegisterListeners(null);
  }
  
  public CountdownButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initializeAndRegisterListeners(paramAttributeSet);
  }
  
  public CountdownButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initializeAndRegisterListeners(paramAttributeSet);
  }
  
  private boolean clearRefreshStateIfNecessary()
  {
    if (this.mTimeWhenRefreshStartedMs != -1L)
    {
      this.mTimeWhenRefreshStartedMs = -1L;
      super.setEnabled(this.mRequestedEnabledState);
      removeCallbacks(this);
      if (!TextUtils.isEmpty(this.mButtonSpec.buttonTextAfterRefresh)) {
        setText(this.mButtonSpec.buttonTextAfterRefresh);
      }
      for (;;)
      {
        return true;
        setText(this.mButtonSpec.buttonTextBeforeClick);
      }
    }
    return false;
  }
  
  private void initializeAndRegisterListeners(AttributeSet paramAttributeSet)
  {
    Context localContext = getContext();
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.uicFormButtonTextCapitalized;
    TypedArray localTypedArray = localContext.obtainStyledAttributes(paramAttributeSet, arrayOfInt);
    this.mCapitalizeButtonText = localTypedArray.getBoolean(0, false);
    localTypedArray.recycle();
    super.setOnClickListener(this);
  }
  
  private static long roundToNearestSecond(long paramLong)
  {
    return 1000L * ((500L + paramLong) / 1000L);
  }
  
  public final void addTriggers(ArrayList<DependencyGraphOuterClass.TriggerValueReference> paramArrayList)
  {
    if (paramArrayList.size() != 1) {
      throw new IllegalArgumentException("Should only contain a single click trigger");
    }
    DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = (DependencyGraphOuterClass.TriggerValueReference)paramArrayList.get(0);
    if (localTriggerValueReference.triggerType != 2) {
      throw new IllegalArgumentException("Unsupported trigger type: " + localTriggerValueReference.triggerType);
    }
    this.mTriggerValueReference = localTriggerValueReference;
  }
  
  public final void applyResultingAction(DependencyGraphOuterClass.ResultingActionReference paramResultingActionReference, DependencyGraphOuterClass.TriggerValueReference[] paramArrayOfTriggerValueReference)
  {
    switch (paramResultingActionReference.actionType)
    {
    default: 
      throw new IllegalArgumentException("Unsupported resulting action type: " + paramResultingActionReference.actionType);
    }
    clearRefreshStateIfNecessary();
  }
  
  public final boolean checkTrigger(DependencyGraphOuterClass.TriggerValueReference paramTriggerValueReference)
  {
    if (paramTriggerValueReference.triggerType == 2) {
      return false;
    }
    throw new IllegalArgumentException("Unsupported trigger type: " + paramTriggerValueReference.triggerType);
  }
  
  public boolean hasOnClickListeners()
  {
    return (super.hasOnClickListeners()) && (this.mOnClickListener != null);
  }
  
  protected void onAttachedToWindow()
  {
    this.mIsAttachedToWindow = true;
    super.onAttachedToWindow();
    if (this.mButtonSpec == null) {}
    do
    {
      return;
      if (this.mTimeWhenRefreshStartedMs != -1L)
      {
        super.setEnabled(false);
        run();
        return;
      }
    } while (!TextUtils.isEmpty(getText()));
    setText(this.mButtonSpec.buttonTextBeforeClick);
    super.setEnabled(this.mButtonSpec.enabled);
  }
  
  public void onClick(View paramView)
  {
    if (this.mButtonSpec.resendTimeMs > 0)
    {
      super.setEnabled(false);
      this.mTimeWhenRefreshStartedMs = SystemClock.elapsedRealtime();
      this.mRequestedEnabledState = true;
      long l = roundToNearestSecond(this.mButtonSpec.resendTimeMs);
      Locale localLocale = getResources().getConfiguration().locale;
      String str = this.mButtonSpec.buttonTextDuringRefresh;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(l / 1000L);
      setText(String.format(localLocale, str, arrayOfObject));
      postDelayed(this, Math.min(l, 1000L));
    }
    for (;;)
    {
      if (this.mOnClickListener != null) {
        this.mOnClickListener.onClick(paramView);
      }
      if ((this.mTriggerListener != null) && (this.mTriggerValueReference != null)) {
        this.mTriggerListener.onTriggerOccurred(this.mTriggerValueReference);
      }
      return;
      if (!TextUtils.isEmpty(this.mButtonSpec.buttonTextAfterRefresh)) {
        setText(this.mButtonSpec.buttonTextAfterRefresh);
      } else {
        setText(this.mButtonSpec.buttonTextBeforeClick);
      }
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mIsAttachedToWindow = false;
    removeCallbacks(this);
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    int i = 1;
    if (!(paramParcelable instanceof Bundle)) {
      super.onRestoreInstanceState(paramParcelable);
    }
    for (;;)
    {
      return;
      Bundle localBundle = (Bundle)paramParcelable;
      super.onRestoreInstanceState(localBundle.getParcelable("superSavedInstanceState"));
      ButtonContainerOuterClass.Button localButton1 = (ButtonContainerOuterClass.Button)ParcelableProto.getProtoFromBundle(localBundle, "buttonSpec");
      if (this.mButtonSpec == null) {
        this.mButtonSpec = localButton1;
      }
      ButtonContainerOuterClass.Button localButton2 = this.mButtonSpec;
      if (localButton1 == localButton2) {}
      while (i != 0)
      {
        this.mTimeWhenRefreshStartedMs = localBundle.getLong("timeWhenRefreshStartedMs");
        this.mRequestedEnabledState = localBundle.getBoolean("requestedEnabledState");
        setText(localBundle.getCharSequence("text"));
        return;
        if ((localButton1 == null) || (localButton2 == null)) {
          i = 0;
        } else if ((!localButton1.buttonTextAfterRefresh.equals(localButton2.buttonTextAfterRefresh)) || (!localButton1.buttonTextBeforeClick.equals(localButton2.buttonTextBeforeClick)) || (!localButton1.buttonTextDuringRefresh.equals(localButton2.buttonTextDuringRefresh)) || (localButton1.resendTimeMs != localButton2.resendTimeMs) || (localButton1.uiReference != localButton2.uiReference) || (localButton1.enabled != localButton2.enabled)) {
          i = 0;
        }
      }
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("superSavedInstanceState", super.onSaveInstanceState());
    localBundle.putParcelable("buttonSpec", ParcelableProto.forProto(this.mButtonSpec));
    localBundle.putLong("timeWhenRefreshStartedMs", this.mTimeWhenRefreshStartedMs);
    localBundle.putBoolean("requestedEnabledState", this.mRequestedEnabledState);
    localBundle.putCharSequence("text", getText());
    return localBundle;
  }
  
  public void run()
  {
    if (this.mButtonSpec.resendTimeMs <= 0) {
      throw new IllegalStateException("Timer based text changes not needed!");
    }
    long l1 = SystemClock.elapsedRealtime();
    long l2 = roundToNearestSecond(this.mTimeWhenRefreshStartedMs + this.mButtonSpec.resendTimeMs - l1);
    if (l2 > 0L)
    {
      Locale localLocale = getResources().getConfiguration().locale;
      String str = this.mButtonSpec.buttonTextDuringRefresh;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(l2 / 1000L);
      setText(String.format(localLocale, str, arrayOfObject));
      postDelayed(this, Math.min(l2, 1000L));
      return;
    }
    clearRefreshStateIfNecessary();
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    if (this.mTimeWhenRefreshStartedMs != -1L)
    {
      this.mRequestedEnabledState = paramBoolean;
      return;
    }
    super.setEnabled(paramBoolean);
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mOnClickListener = paramOnClickListener;
  }
  
  public void setText(CharSequence paramCharSequence, TextView.BufferType paramBufferType)
  {
    if ((this.mCapitalizeButtonText) && (!TextUtils.isEmpty(paramCharSequence)))
    {
      Locale localLocale = getResources().getConfiguration().locale;
      paramCharSequence = paramCharSequence.toString().toUpperCase(localLocale);
    }
    super.setText(paramCharSequence, paramBufferType);
  }
  
  public void setTriggerListener(TriggerListener paramTriggerListener)
  {
    this.mTriggerListener = paramTriggerListener;
  }
  
  public void setUiSpecification(ButtonContainerOuterClass.Button paramButton)
  {
    if (TextUtils.isEmpty(paramButton.buttonTextBeforeClick)) {
      throw new IllegalArgumentException("Button spec without initial text received.");
    }
    if (paramButton.resendTimeMs > 0)
    {
      if (TextUtils.isEmpty(paramButton.buttonTextDuringRefresh)) {
        throw new IllegalArgumentException("Re-send timer w/o a refresh message received.");
      }
      if (paramButton.resendTimeMs < 1000) {
        throw new IllegalArgumentException("Re-send timer less then 1 second which is the minimum displayable unit.");
      }
    }
    this.mButtonSpec = paramButton;
    if (this.mIsAttachedToWindow)
    {
      setText(this.mButtonSpec.buttonTextBeforeClick);
      super.setEnabled(this.mButtonSpec.enabled);
      removeCallbacks(this);
      this.mTimeWhenRefreshStartedMs = -1L;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.CountdownButton
 * JD-Core Version:    0.7.0.1
 */