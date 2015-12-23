package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.widget.CompoundButton;

public final class CompoundButtonCompat
{
  private static final CompoundButtonCompatImpl IMPL = new BaseCompoundButtonCompat();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 23)
    {
      IMPL = new Api23CompoundButtonImpl();
      return;
    }
    if (i >= 21)
    {
      IMPL = new LollipopCompoundButtonImpl();
      return;
    }
  }
  
  public static Drawable getButtonDrawable(CompoundButton paramCompoundButton)
  {
    return IMPL.getButtonDrawable(paramCompoundButton);
  }
  
  public static void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList)
  {
    IMPL.setButtonTintList(paramCompoundButton, paramColorStateList);
  }
  
  public static void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode)
  {
    IMPL.setButtonTintMode(paramCompoundButton, paramMode);
  }
  
  static final class Api23CompoundButtonImpl
    extends CompoundButtonCompat.LollipopCompoundButtonImpl
  {
    public final Drawable getButtonDrawable(CompoundButton paramCompoundButton)
    {
      return paramCompoundButton.getButtonDrawable();
    }
  }
  
  static class BaseCompoundButtonCompat
    implements CompoundButtonCompat.CompoundButtonCompatImpl
  {
    public Drawable getButtonDrawable(CompoundButton paramCompoundButton)
    {
      return CompoundButtonCompatDonut.getButtonDrawable(paramCompoundButton);
    }
    
    public void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList)
    {
      if ((paramCompoundButton instanceof TintableCompoundButton)) {
        ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintList(paramColorStateList);
      }
    }
    
    public void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode)
    {
      if ((paramCompoundButton instanceof TintableCompoundButton)) {
        ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintMode(paramMode);
      }
    }
  }
  
  static abstract interface CompoundButtonCompatImpl
  {
    public abstract Drawable getButtonDrawable(CompoundButton paramCompoundButton);
    
    public abstract void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList);
    
    public abstract void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode);
  }
  
  static class LollipopCompoundButtonImpl
    extends CompoundButtonCompat.BaseCompoundButtonCompat
  {
    public final void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList)
    {
      paramCompoundButton.setButtonTintList(paramColorStateList);
    }
    
    public final void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode)
    {
      paramCompoundButton.setButtonTintMode(paramMode);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.CompoundButtonCompat
 * JD-Core Version:    0.7.0.1
 */