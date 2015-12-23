package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.appcompat.R.styleable;
import android.support.v7.graphics.drawable.DrawableUtils;
import android.support.v7.internal.widget.TintManager;
import android.util.AttributeSet;
import android.widget.CompoundButton;

final class AppCompatCompoundButtonHelper
{
  ColorStateList mButtonTintList = null;
  PorterDuff.Mode mButtonTintMode = null;
  private boolean mHasButtonTint = false;
  private boolean mHasButtonTintMode = false;
  private boolean mSkipNextApply;
  private final TintManager mTintManager;
  private final CompoundButton mView;
  
  AppCompatCompoundButtonHelper(CompoundButton paramCompoundButton, TintManager paramTintManager)
  {
    this.mView = paramCompoundButton;
    this.mTintManager = paramTintManager;
  }
  
  private void applyButtonTint()
  {
    Drawable localDrawable1 = CompoundButtonCompat.getButtonDrawable(this.mView);
    if ((localDrawable1 != null) && ((this.mHasButtonTint) || (this.mHasButtonTintMode)))
    {
      Drawable localDrawable2 = DrawableCompat.wrap(localDrawable1).mutate();
      if (this.mHasButtonTint) {
        DrawableCompat.setTintList(localDrawable2, this.mButtonTintList);
      }
      if (this.mHasButtonTintMode) {
        DrawableCompat.setTintMode(localDrawable2, this.mButtonTintMode);
      }
      if (localDrawable2.isStateful()) {
        localDrawable2.setState(this.mView.getDrawableState());
      }
      this.mView.setButtonDrawable(localDrawable2);
    }
  }
  
  final int getCompoundPaddingLeft(int paramInt)
  {
    if (Build.VERSION.SDK_INT < 17)
    {
      Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
      if (localDrawable != null) {
        paramInt += localDrawable.getIntrinsicWidth();
      }
    }
    return paramInt;
  }
  
  final void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = this.mView.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.CompoundButton, paramInt, 0);
    try
    {
      if (localTypedArray.hasValue(R.styleable.CompoundButton_android_button))
      {
        int i = localTypedArray.getResourceId(R.styleable.CompoundButton_android_button, 0);
        if (i != 0) {
          this.mView.setButtonDrawable(this.mTintManager.getDrawable(i, false));
        }
      }
      if (localTypedArray.hasValue(R.styleable.CompoundButton_buttonTint)) {
        CompoundButtonCompat.setButtonTintList(this.mView, localTypedArray.getColorStateList(R.styleable.CompoundButton_buttonTint));
      }
      if (localTypedArray.hasValue(R.styleable.CompoundButton_buttonTintMode)) {
        CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode$49602678(localTypedArray.getInt(R.styleable.CompoundButton_buttonTintMode, -1)));
      }
      return;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  final void onSetButtonDrawable()
  {
    if (this.mSkipNextApply)
    {
      this.mSkipNextApply = false;
      return;
    }
    this.mSkipNextApply = true;
    applyButtonTint();
  }
  
  final void setSupportButtonTintList(ColorStateList paramColorStateList)
  {
    this.mButtonTintList = paramColorStateList;
    this.mHasButtonTint = true;
    applyButtonTint();
  }
  
  final void setSupportButtonTintMode(PorterDuff.Mode paramMode)
  {
    this.mButtonTintMode = paramMode;
    this.mHasButtonTintMode = true;
    applyButtonTint();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatCompoundButtonHelper
 * JD-Core Version:    0.7.0.1
 */