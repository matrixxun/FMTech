package com.google.android.wallet.ui.common;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v7.app.AlertController.AlertParams;
import android.view.ContextThemeWrapper;
import android.view.View;
import com.google.android.wallet.uicomponents.R.attr;

public final class AlertDialogBuilderCompat
{
  private final Context mContext;
  public final android.app.AlertDialog.Builder mPlatformBuilder;
  public final android.support.v7.app.AlertDialog.Builder mSupportBuilder;
  
  public AlertDialogBuilderCompat(Context paramContext)
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.uicDelegateToSupportLibAlertDialog;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(arrayOfInt);
    boolean bool = localTypedArray.getBoolean(0, true);
    localTypedArray.recycle();
    if (bool) {
      this.mSupportBuilder = new android.support.v7.app.AlertDialog.Builder(paramContext);
    }
    for (this.mPlatformBuilder = null;; this.mPlatformBuilder = new android.app.AlertDialog.Builder(paramContext))
    {
      this.mContext = paramContext;
      return;
      this.mSupportBuilder = null;
    }
  }
  
  @TargetApi(11)
  public AlertDialogBuilderCompat(Context paramContext, byte paramByte)
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.uicDelegateToSupportLibAlertDialog;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(arrayOfInt);
    boolean bool = localTypedArray.getBoolean(0, true);
    localTypedArray.recycle();
    if (bool)
    {
      this.mContext = paramContext;
      this.mSupportBuilder = new android.support.v7.app.AlertDialog.Builder(paramContext, 2131558814);
      this.mPlatformBuilder = null;
      return;
    }
    if (Build.VERSION.SDK_INT >= 11)
    {
      this.mContext = paramContext;
      this.mSupportBuilder = null;
      this.mPlatformBuilder = new android.app.AlertDialog.Builder(paramContext, 2131558814);
      return;
    }
    ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(paramContext, 2131558814);
    this.mContext = localContextThemeWrapper;
    this.mSupportBuilder = null;
    this.mPlatformBuilder = new android.app.AlertDialog.Builder(localContextThemeWrapper);
  }
  
  public final Dialog create()
  {
    if (this.mPlatformBuilder != null) {
      return this.mPlatformBuilder.create();
    }
    return this.mSupportBuilder.create();
  }
  
  public final AlertDialogBuilderCompat setCancelable(boolean paramBoolean)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setCancelable(paramBoolean);
      return this;
    }
    this.mSupportBuilder.P.mCancelable = paramBoolean;
    return this;
  }
  
  public final AlertDialogBuilderCompat setMessage(int paramInt)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setMessage(paramInt);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mMessage = localBuilder.P.mContext.getText(paramInt);
    return this;
  }
  
  public final AlertDialogBuilderCompat setMessage(CharSequence paramCharSequence)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setMessage(paramCharSequence);
      return this;
    }
    this.mSupportBuilder.P.mMessage = paramCharSequence;
    return this;
  }
  
  public final AlertDialogBuilderCompat setNegativeButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setNegativeButton(paramInt, paramOnClickListener);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mNegativeButtonText = localBuilder.P.mContext.getText(paramInt);
    localBuilder.P.mNegativeButtonListener = paramOnClickListener;
    return this;
  }
  
  public final AlertDialogBuilderCompat setNegativeButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setNegativeButton(paramCharSequence, paramOnClickListener);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mNegativeButtonText = paramCharSequence;
    localBuilder.P.mNegativeButtonListener = paramOnClickListener;
    return this;
  }
  
  public final AlertDialogBuilderCompat setPositiveButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setPositiveButton(paramInt, paramOnClickListener);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mPositiveButtonText = localBuilder.P.mContext.getText(paramInt);
    localBuilder.P.mPositiveButtonListener = paramOnClickListener;
    return this;
  }
  
  public final AlertDialogBuilderCompat setPositiveButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setPositiveButton(paramCharSequence, paramOnClickListener);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mPositiveButtonText = paramCharSequence;
    localBuilder.P.mPositiveButtonListener = paramOnClickListener;
    return this;
  }
  
  public final AlertDialogBuilderCompat setSingleChoiceItems(CharSequence[] paramArrayOfCharSequence, int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setSingleChoiceItems(paramArrayOfCharSequence, paramInt, paramOnClickListener);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mItems = paramArrayOfCharSequence;
    localBuilder.P.mOnClickListener = paramOnClickListener;
    localBuilder.P.mCheckedItem = paramInt;
    localBuilder.P.mIsSingleChoice = true;
    return this;
  }
  
  public final AlertDialogBuilderCompat setTitle(int paramInt)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setTitle(paramInt);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mTitle = localBuilder.P.mContext.getText(paramInt);
    return this;
  }
  
  public final AlertDialogBuilderCompat setTitle(CharSequence paramCharSequence)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setTitle(paramCharSequence);
      return this;
    }
    this.mSupportBuilder.setTitle(paramCharSequence);
    return this;
  }
  
  public final AlertDialogBuilderCompat setView(View paramView)
  {
    if (this.mPlatformBuilder != null)
    {
      this.mPlatformBuilder.setView(paramView);
      return this;
    }
    android.support.v7.app.AlertDialog.Builder localBuilder = this.mSupportBuilder;
    localBuilder.P.mView = paramView;
    localBuilder.P.mViewLayoutResId = 0;
    localBuilder.P.mViewSpacingSpecified = false;
    return this;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.AlertDialogBuilderCompat
 * JD-Core Version:    0.7.0.1
 */