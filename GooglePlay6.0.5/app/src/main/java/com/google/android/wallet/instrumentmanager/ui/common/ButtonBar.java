package com.google.android.wallet.instrumentmanager.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.SimpleArrayMap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.wallet.instrumentmanager.R.attr;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.styleable;
import com.google.android.wallet.ui.common.TouchDelegateGroup;
import com.google.android.wallet.ui.common.WalletUiUtils;
import java.util.Locale;

public class ButtonBar
  extends RelativeLayout
{
  private float mAlphaWhenPositiveButtonDisabled;
  private boolean mCapitalizeButtonText;
  public boolean mDefaultShowNegativeButton;
  public Button mExpandButton;
  private boolean mHideNegativeButtonText;
  ImageView mLogoImage;
  private boolean mLogoImageDefined;
  private int mMinimumTouchTargetSize;
  public Button mNegativeButton;
  public Button mPositiveButton;
  
  public ButtonBar(Context paramContext)
  {
    super(paramContext);
  }
  
  public ButtonBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  @TargetApi(11)
  public ButtonBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributes(paramContext, paramAttributeSet);
  }
  
  private void readAttributes(Context paramContext, AttributeSet paramAttributeSet)
  {
    TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.WalletImButtonBar);
    this.mCapitalizeButtonText = localTypedArray1.getBoolean(R.styleable.WalletImButtonBar_capitalizeButtonText, false);
    this.mDefaultShowNegativeButton = localTypedArray1.getBoolean(R.styleable.WalletImButtonBar_showNegativeButton, false);
    this.mHideNegativeButtonText = localTypedArray1.getBoolean(R.styleable.WalletImButtonBar_hideNegativeButtonText, false);
    localTypedArray1.recycle();
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.imButtonMinimumTouchTargetSize;
    TypedArray localTypedArray2 = paramContext.obtainStyledAttributes(paramAttributeSet, arrayOfInt);
    this.mMinimumTouchTargetSize = localTypedArray2.getDimensionPixelSize(0, -1);
    localTypedArray2.recycle();
  }
  
  private void updateButtonTouchTargetSize(TouchDelegateGroup paramTouchDelegateGroup, Button paramButton)
  {
    if ((paramButton.getVisibility() != 8) && ((paramButton.getHeight() < this.mMinimumTouchTargetSize) || (paramButton.getWidth() < this.mMinimumTouchTargetSize)))
    {
      localRect = new Rect();
      WalletUiUtils.getTouchTarget(localRect, paramButton, this.mMinimumTouchTargetSize, this.mMinimumTouchTargetSize);
      if (paramButton == null) {
        throw new NullPointerException("delegateView cannot be null");
      }
      paramTouchDelegateGroup.mTouchDelegates.put(paramButton, new TouchDelegate(localRect, paramButton));
    }
    while ((TouchDelegate)paramTouchDelegateGroup.mTouchDelegates.remove(paramButton) != paramTouchDelegateGroup.mCurrentTouchDelegate)
    {
      Rect localRect;
      return;
    }
    paramTouchDelegateGroup.mCurrentTouchDelegate = null;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mLogoImage = ((ImageView)findViewById(R.id.logo));
    Context localContext = getContext();
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = R.attr.imButtonBarIntegratorLogoDrawable;
    arrayOfInt[1] = R.attr.imPositiveButtonBarAlphaWhenDisabled;
    TypedArray localTypedArray = localContext.obtainStyledAttributes(arrayOfInt);
    TypedValue localTypedValue = localTypedArray.peekValue(0);
    this.mAlphaWhenPositiveButtonDisabled = localTypedArray.getFloat(1, -1.0F);
    localTypedArray.recycle();
    WalletUiUtils.setViewBackgroundOrHide(this.mLogoImage, localTypedValue);
    if (this.mLogoImage.getVisibility() == 0) {}
    for (boolean bool = true;; bool = false)
    {
      this.mLogoImageDefined = bool;
      this.mExpandButton = ((Button)findViewById(R.id.expand_btn));
      this.mPositiveButton = ((Button)findViewById(R.id.positive_btn));
      this.mNegativeButton = ((Button)findViewById(R.id.negative_btn));
      if (this.mCapitalizeButtonText)
      {
        Locale localLocale = getResources().getConfiguration().locale;
        this.mPositiveButton.setText(this.mPositiveButton.getText().toString().toUpperCase(localLocale));
        this.mNegativeButton.setText(this.mNegativeButton.getText().toString().toUpperCase(localLocale));
      }
      showNegativeButton(this.mDefaultShowNegativeButton);
      if (this.mHideNegativeButtonText) {
        this.mNegativeButton.setText(null);
      }
      return;
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    TouchDelegateGroup localTouchDelegateGroup;
    if (getTouchDelegate() == null)
    {
      localTouchDelegateGroup = new TouchDelegateGroup(this);
      setTouchDelegate(localTouchDelegateGroup);
    }
    for (;;)
    {
      updateButtonTouchTargetSize(localTouchDelegateGroup, this.mNegativeButton);
      updateButtonTouchTargetSize(localTouchDelegateGroup, this.mExpandButton);
      updateButtonTouchTargetSize(localTouchDelegateGroup, this.mPositiveButton);
      return;
      localTouchDelegateGroup = (TouchDelegateGroup)getTouchDelegate();
    }
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof Bundle))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    Bundle localBundle = (Bundle)paramParcelable;
    super.onRestoreInstanceState(localBundle.getParcelable("superSavedInstanceState"));
    this.mPositiveButton.setEnabled(localBundle.getBoolean("positiveButtonEnabled"));
    showNegativeButton(localBundle.getBoolean("negativeButtonShowing"));
    this.mExpandButton.setEnabled(localBundle.getBoolean("expandButtonEnabled"));
  }
  
  protected Parcelable onSaveInstanceState()
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("superSavedInstanceState", super.onSaveInstanceState());
    localBundle.putBoolean("positiveButtonEnabled", this.mPositiveButton.isEnabled());
    if (this.mNegativeButton.getVisibility() == 0) {}
    for (boolean bool = true;; bool = false)
    {
      localBundle.putBoolean("negativeButtonShowing", bool);
      localBundle.putBoolean("expandButtonEnabled", this.mExpandButton.isEnabled());
      return localBundle;
    }
  }
  
  public void setExpandButtonEnabled(boolean paramBoolean)
  {
    this.mExpandButton.setEnabled(paramBoolean);
  }
  
  public void setExpandButtonOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mExpandButton.setOnClickListener(paramOnClickListener);
  }
  
  public void setExpandButtonText(String paramString)
  {
    this.mExpandButton.setText(paramString);
  }
  
  public void setNegativeButtonOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mNegativeButton.setOnClickListener(paramOnClickListener);
  }
  
  @TargetApi(11)
  public void setPositiveButtonEnabled(boolean paramBoolean)
  {
    this.mPositiveButton.setEnabled(paramBoolean);
    Button localButton;
    if ((Build.VERSION.SDK_INT >= 11) && (this.mAlphaWhenPositiveButtonDisabled >= 0.0F))
    {
      localButton = this.mPositiveButton;
      if (!paramBoolean) {
        break label42;
      }
    }
    label42:
    for (float f = 1.0F;; f = this.mAlphaWhenPositiveButtonDisabled)
    {
      localButton.setAlpha(f);
      return;
    }
  }
  
  public void setPositiveButtonOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mPositiveButton.setOnClickListener(paramOnClickListener);
  }
  
  public void setPositiveButtonText(String paramString)
  {
    if (this.mCapitalizeButtonText) {
      paramString = paramString.toUpperCase(getResources().getConfiguration().locale);
    }
    this.mPositiveButton.setText(paramString);
  }
  
  public final void showExpandButton(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.mNegativeButton.getVisibility() == 0) {
        throw new IllegalStateException("Can't show expand button while negative button is visible.");
      }
      this.mExpandButton.setVisibility(0);
      if (this.mLogoImageDefined) {
        this.mLogoImage.setVisibility(8);
      }
    }
    do
    {
      return;
      this.mExpandButton.setVisibility(8);
    } while ((!this.mLogoImageDefined) || (this.mNegativeButton.getVisibility() == 0));
    this.mLogoImage.setVisibility(0);
  }
  
  public final void showNegativeButton(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.mExpandButton.getVisibility() == 0) {
        throw new IllegalStateException("Can't show negative button while expand button is visible.");
      }
      this.mNegativeButton.setVisibility(0);
      if (this.mLogoImageDefined) {
        this.mLogoImage.setVisibility(8);
      }
    }
    do
    {
      return;
      this.mNegativeButton.setVisibility(8);
    } while ((!this.mLogoImageDefined) || (this.mExpandButton.getVisibility() == 0));
    this.mLogoImage.setVisibility(0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.common.ButtonBar
 * JD-Core Version:    0.7.0.1
 */