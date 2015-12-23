package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.appcompat.R.attr;
import android.support.v7.internal.widget.TintManager;
import android.util.AttributeSet;
import android.widget.CheckBox;

public final class AppCompatCheckBox
  extends CheckBox
  implements TintableCompoundButton
{
  private AppCompatCompoundButtonHelper mCompoundButtonHelper;
  private TintManager mTintManager;
  
  public AppCompatCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.checkboxStyle);
  }
  
  private AppCompatCheckBox(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mTintManager = TintManager.get(paramContext);
    this.mCompoundButtonHelper = new AppCompatCompoundButtonHelper(this, this.mTintManager);
    this.mCompoundButtonHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
  
  public final int getCompoundPaddingLeft()
  {
    int i = super.getCompoundPaddingLeft();
    if (this.mCompoundButtonHelper != null) {
      i = this.mCompoundButtonHelper.getCompoundPaddingLeft(i);
    }
    return i;
  }
  
  public final ColorStateList getSupportButtonTintList()
  {
    if (this.mCompoundButtonHelper != null) {
      return this.mCompoundButtonHelper.mButtonTintList;
    }
    return null;
  }
  
  public final PorterDuff.Mode getSupportButtonTintMode()
  {
    if (this.mCompoundButtonHelper != null) {
      return this.mCompoundButtonHelper.mButtonTintMode;
    }
    return null;
  }
  
  public final void setButtonDrawable(int paramInt)
  {
    if (this.mTintManager != null) {}
    for (Drawable localDrawable = this.mTintManager.getDrawable(paramInt, false);; localDrawable = ContextCompat.getDrawable(getContext(), paramInt))
    {
      setButtonDrawable(localDrawable);
      return;
    }
  }
  
  public final void setButtonDrawable(Drawable paramDrawable)
  {
    super.setButtonDrawable(paramDrawable);
    if (this.mCompoundButtonHelper != null) {
      this.mCompoundButtonHelper.onSetButtonDrawable();
    }
  }
  
  public final void setSupportButtonTintList(ColorStateList paramColorStateList)
  {
    if (this.mCompoundButtonHelper != null) {
      this.mCompoundButtonHelper.setSupportButtonTintList(paramColorStateList);
    }
  }
  
  public final void setSupportButtonTintMode(PorterDuff.Mode paramMode)
  {
    if (this.mCompoundButtonHelper != null) {
      this.mCompoundButtonHelper.setSupportButtonTintMode(paramMode);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.AppCompatCheckBox
 * JD-Core Version:    0.7.0.1
 */